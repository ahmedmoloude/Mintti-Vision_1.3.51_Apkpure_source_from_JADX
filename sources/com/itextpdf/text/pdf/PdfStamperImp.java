package com.itextpdf.text.pdf;

import com.itextpdf.awt.geom.Point;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Version;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.log.Counter;
import com.itextpdf.text.log.CounterFactory;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.collection.PdfCollection;
import com.itextpdf.text.pdf.internal.PdfViewerPreferencesImp;
import com.itextpdf.text.xml.xmp.PdfProperties;
import com.itextpdf.text.xml.xmp.XmpBasicProperties;
import com.itextpdf.xmp.XMPException;
import com.itextpdf.xmp.XMPMeta;
import com.itextpdf.xmp.XMPMetaFactory;
import com.itextpdf.xmp.options.SerializeOptions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

class PdfStamperImp extends PdfWriter {
    protected Counter COUNTER = CounterFactory.getCounter(PdfStamper.class);
    private double[] DEFAULT_MATRIX = {1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d};
    protected AcroFields acroFields;
    protected boolean append;
    protected boolean closed = false;
    protected HashSet<PdfTemplate> fieldTemplates = new HashSet<>();
    protected boolean fieldsAdded = false;
    protected RandomAccessFileOrArray file;
    protected boolean flat = false;
    protected boolean flatFreeText = false;
    protected boolean flatannotations = false;
    protected int initialXrefSize;
    protected IntHashtable marked;
    IntHashtable myXref = new IntHashtable();
    protected int[] namePtr = {0};
    protected HashMap<Object, PdfObject> namedDestinations = new HashMap<>();
    protected PdfAction openAction;
    private boolean originalLayersAreRead = false;
    HashMap<PdfDictionary, PageStamp> pagesToContent = new HashMap<>();
    protected HashSet<String> partialFlattening = new HashSet<>();
    PdfReader reader;
    HashMap<PdfReader, RandomAccessFileOrArray> readers2file = new HashMap<>();
    HashMap<PdfReader, IntHashtable> readers2intrefs = new HashMap<>();
    private boolean rotateContents = true;
    protected int sigFlags = 0;
    protected boolean useVp = false;
    protected PdfViewerPreferencesImp viewerPreferences = new PdfViewerPreferencesImp();

    /* access modifiers changed from: protected */
    public Counter getCounter() {
        return this.COUNTER;
    }

    protected PdfStamperImp(PdfReader pdfReader, OutputStream outputStream, char c, boolean z) throws DocumentException, IOException {
        super(new PdfDocument(), outputStream);
        if (!pdfReader.isOpenedWithFullPermissions()) {
            throw new BadPasswordException(MessageLocalization.getComposedMessage("pdfreader.not.opened.with.owner.password", new Object[0]));
        } else if (!pdfReader.isTampered()) {
            pdfReader.setTampered(true);
            this.reader = pdfReader;
            this.file = pdfReader.getSafeFile();
            this.append = z;
            if (pdfReader.isEncrypted() && (z || PdfReader.unethicalreading)) {
                this.crypto = new PdfEncryption(pdfReader.getDecrypt());
            }
            if (z) {
                if (!pdfReader.isRebuilt()) {
                    this.pdf_version.setAppendmode(true);
                    if (c == 0) {
                        this.pdf_version.setPdfVersion(pdfReader.getPdfVersion());
                    } else {
                        this.pdf_version.setPdfVersion(c);
                    }
                    byte[] bArr = new byte[8192];
                    while (true) {
                        int read = this.file.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        this.f439os.write(bArr, 0, read);
                    }
                    this.prevxref = pdfReader.getLastXref();
                    pdfReader.setAppendable(true);
                } else {
                    throw new DocumentException(MessageLocalization.getComposedMessage("append.mode.requires.a.document.without.errors.even.if.recovery.was.possible", new Object[0]));
                }
            } else if (c == 0) {
                super.setPdfVersion(pdfReader.getPdfVersion());
            } else {
                super.setPdfVersion(c);
            }
            if (pdfReader.isTagged()) {
                setTagged();
            }
            super.open();
            this.pdf.addWriter(this);
            if (z) {
                this.body.setRefnum(pdfReader.getXrefSize());
                this.marked = new IntHashtable();
                if (pdfReader.isNewXrefType()) {
                    this.fullCompression = true;
                }
                if (pdfReader.isHybridXref()) {
                    this.fullCompression = false;
                }
            }
            this.initialXrefSize = pdfReader.getXrefSize();
            readColorProfile();
        } else {
            throw new DocumentException(MessageLocalization.getComposedMessage("the.original.document.was.reused.read.it.again.from.file", new Object[0]));
        }
    }

    /* access modifiers changed from: protected */
    public void readColorProfile() {
        PdfArray asArray = this.reader.getCatalog().getAsArray(PdfName.OUTPUTINTENTS);
        if (asArray != null) {
            PdfArray pdfArray = asArray;
            if (pdfArray.size() > 0) {
                PdfStream pdfStream = null;
                int i = 0;
                while (i < pdfArray.size() && ((r3 = pdfArray.getAsDict(i)) == null || (pdfStream = r3.getAsStream(PdfName.DESTOUTPUTPROFILE)) == null)) {
                    i++;
                }
                if (pdfStream instanceof PRStream) {
                    try {
                        this.colorProfile = ICC_Profile.getInstance(PdfReader.getStreamBytes((PRStream) pdfStream));
                    } catch (IOException e) {
                        throw new ExceptionConverter(e);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setViewerPreferences() {
        this.reader.setViewerPreferences(this.viewerPreferences);
        markUsed(this.reader.getTrailer().get(PdfName.ROOT));
    }

    /* access modifiers changed from: protected */
    public void close(Map<String, String> map) throws IOException {
        String str;
        PdfIndirectReference pdfIndirectReference;
        byte[] bArr;
        StringBuffer stringBuffer;
        if (!this.closed) {
            if (this.useVp) {
                setViewerPreferences();
            }
            if (this.flat) {
                flatFields();
            }
            if (this.flatFreeText) {
                flatFreeTextFields();
            }
            if (this.flatannotations) {
                flattenAnnotations();
            }
            addFieldResources();
            PdfDictionary catalog = this.reader.getCatalog();
            getPdfVersion().addToCatalog(catalog);
            PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObject(catalog.get(PdfName.ACROFORM), this.reader.getCatalog());
            AcroFields acroFields2 = this.acroFields;
            if (acroFields2 != null && acroFields2.getXfa().isChanged()) {
                markUsed((PdfObject) pdfDictionary);
                if (!this.flat) {
                    this.acroFields.getXfa().setXfa(this);
                }
            }
            if (!(this.sigFlags == 0 || pdfDictionary == null)) {
                pdfDictionary.put(PdfName.SIGFLAGS, new PdfNumber(this.sigFlags));
                markUsed((PdfObject) pdfDictionary);
                markUsed((PdfObject) catalog);
            }
            this.closed = true;
            addSharedObjectsToBody();
            setOutlines();
            setJavaScript();
            addFileAttachments();
            if (this.extraCatalog != null) {
                catalog.mergeDifferent(this.extraCatalog);
            }
            if (this.openAction != null) {
                catalog.put(PdfName.OPENACTION, this.openAction);
            }
            if (this.pdf.pageLabels != null) {
                catalog.put(PdfName.PAGELABELS, this.pdf.pageLabels.getDictionary(this));
            }
            if (!this.documentOCG.isEmpty()) {
                fillOCProperties(false);
                PdfDictionary asDict = catalog.getAsDict(PdfName.OCPROPERTIES);
                if (asDict == null) {
                    this.reader.getCatalog().put(PdfName.OCPROPERTIES, this.OCProperties);
                } else {
                    asDict.put(PdfName.OCGS, this.OCProperties.get(PdfName.OCGS));
                    PdfDictionary asDict2 = asDict.getAsDict(PdfName.f567D);
                    if (asDict2 == null) {
                        asDict2 = new PdfDictionary();
                        asDict.put(PdfName.f567D, asDict2);
                    }
                    asDict2.put(PdfName.ORDER, this.OCProperties.getAsDict(PdfName.f567D).get(PdfName.ORDER));
                    asDict2.put(PdfName.RBGROUPS, this.OCProperties.getAsDict(PdfName.f567D).get(PdfName.RBGROUPS));
                    asDict2.put(PdfName.OFF, this.OCProperties.getAsDict(PdfName.f567D).get(PdfName.OFF));
                    asDict2.put(PdfName.f550AS, this.OCProperties.getAsDict(PdfName.f567D).get(PdfName.f550AS));
                }
                PdfWriter.checkPdfIsoConformance(this, 7, this.OCProperties);
            }
            PdfIndirectReference asIndirectObject = this.reader.getTrailer().getAsIndirectObject(PdfName.INFO);
            int number = asIndirectObject != null ? asIndirectObject.getNumber() : -1;
            PdfDictionary asDict3 = this.reader.getTrailer().getAsDict(PdfName.INFO);
            PdfStream pdfStream = null;
            String unicodeString = (asDict3 == null || asDict3.get(PdfName.PRODUCER) == null) ? null : asDict3.getAsString(PdfName.PRODUCER).toUnicodeString();
            Version instance = Version.getInstance();
            if (unicodeString == null || instance.getVersion().indexOf(instance.getProduct()) == -1) {
                str = instance.getVersion();
            } else {
                int indexOf = unicodeString.indexOf("; modified using");
                if (indexOf == -1) {
                    stringBuffer = new StringBuffer(unicodeString);
                } else {
                    stringBuffer = new StringBuffer(unicodeString.substring(0, indexOf));
                }
                stringBuffer.append("; modified using ");
                stringBuffer.append(instance.getVersion());
                str = stringBuffer.toString();
            }
            PdfDictionary pdfDictionary2 = new PdfDictionary();
            if (asDict3 != null) {
                for (PdfName next : asDict3.getKeys()) {
                    pdfDictionary2.put(next, PdfReader.getPdfObject(asDict3.get(next)));
                }
            }
            if (map != null) {
                for (Map.Entry next2 : map.entrySet()) {
                    PdfName pdfName = new PdfName((String) next2.getKey());
                    String str2 = (String) next2.getValue();
                    if (str2 == null) {
                        pdfDictionary2.remove(pdfName);
                    } else {
                        pdfDictionary2.put(pdfName, new PdfString(str2, PdfObject.TEXT_UNICODE));
                    }
                }
            }
            PdfDate pdfDate = new PdfDate();
            pdfDictionary2.put(PdfName.MODDATE, pdfDate);
            pdfDictionary2.put(PdfName.PRODUCER, new PdfString(str, PdfObject.TEXT_UNICODE));
            if (!this.append) {
                pdfIndirectReference = addToBody((PdfObject) pdfDictionary2, false).getIndirectReference();
            } else if (asIndirectObject == null) {
                pdfIndirectReference = addToBody((PdfObject) pdfDictionary2, false).getIndirectReference();
            } else {
                pdfIndirectReference = addToBody((PdfObject) pdfDictionary2, asIndirectObject.getNumber(), false).getIndirectReference();
            }
            PdfObject pdfObject = PdfReader.getPdfObject(catalog.get(PdfName.METADATA));
            if (pdfObject == null || !pdfObject.isStream()) {
                bArr = null;
            } else {
                bArr = PdfReader.getStreamBytesRaw((PRStream) pdfObject);
                PdfReader.killIndirect(catalog.get(PdfName.METADATA));
            }
            if (this.xmpMetadata != null) {
                bArr = this.xmpMetadata;
            } else if (this.xmpWriter != null) {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    PdfProperties.setProducer(this.xmpWriter.getXmpMeta(), str);
                    XmpBasicProperties.setModDate(this.xmpWriter.getXmpMeta(), pdfDate.getW3CDate());
                    XmpBasicProperties.setMetaDataDate(this.xmpWriter.getXmpMeta(), pdfDate.getW3CDate());
                    this.xmpWriter.serialize(byteArrayOutputStream);
                    this.xmpWriter.close();
                    pdfStream = new PdfStream(byteArrayOutputStream.toByteArray());
                } catch (XMPException unused) {
                    this.xmpWriter = null;
                }
            }
            if (pdfStream == null && bArr != null) {
                try {
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    if (map != null) {
                        if (this.xmpMetadata == null) {
                            createXmpWriter(byteArrayOutputStream2, pdfDictionary2).close();
                            pdfStream = new PdfStream(byteArrayOutputStream2.toByteArray());
                        }
                    }
                    XMPMeta parseFromBuffer = XMPMetaFactory.parseFromBuffer(bArr);
                    PdfProperties.setProducer(parseFromBuffer, str);
                    XmpBasicProperties.setModDate(parseFromBuffer, pdfDate.getW3CDate());
                    XmpBasicProperties.setMetaDataDate(parseFromBuffer, pdfDate.getW3CDate());
                    SerializeOptions serializeOptions = new SerializeOptions();
                    serializeOptions.setPadding(2000);
                    XMPMetaFactory.serialize(parseFromBuffer, byteArrayOutputStream2, serializeOptions);
                    pdfStream = new PdfStream(byteArrayOutputStream2.toByteArray());
                } catch (XMPException unused2) {
                    pdfStream = new PdfStream(bArr);
                } catch (IOException unused3) {
                    pdfStream = new PdfStream(bArr);
                }
            }
            if (pdfStream != null) {
                pdfStream.put(PdfName.TYPE, PdfName.METADATA);
                pdfStream.put(PdfName.SUBTYPE, PdfName.XML);
                if (this.crypto != null && !this.crypto.isMetadataEncrypted()) {
                    PdfArray pdfArray = new PdfArray();
                    pdfArray.add((PdfObject) PdfName.CRYPT);
                    pdfStream.put(PdfName.FILTER, pdfArray);
                }
                if (!this.append || pdfObject == null) {
                    catalog.put(PdfName.METADATA, this.body.add(pdfStream).getIndirectReference());
                    markUsed((PdfObject) catalog);
                } else {
                    this.body.add((PdfObject) pdfStream, (PdfIndirectReference) pdfObject.getIndRef());
                }
            }
            if (!this.namedDestinations.isEmpty()) {
                updateNamedDestinations();
            }
            close(pdfIndirectReference, number);
        }
    }

    /* access modifiers changed from: protected */
    public void close(PdfIndirectReference pdfIndirectReference, int i) throws IOException {
        PdfObject pdfObject;
        int i2 = i;
        alterContents();
        int number = ((PRIndirectReference) this.reader.trailer.get(PdfName.ROOT)).getNumber();
        if (this.append) {
            int[] keys = this.marked.getKeys();
            for (int i3 = 0; i3 < keys.length; i3++) {
                int i4 = keys[i3];
                PdfObject pdfObjectRelease = this.reader.getPdfObjectRelease(i4);
                if (!(pdfObjectRelease == null || i2 == i4 || i4 >= this.initialXrefSize)) {
                    addToBody(pdfObjectRelease, (PdfIndirectReference) pdfObjectRelease.getIndRef(), i4 != number);
                }
            }
            for (int i5 = this.initialXrefSize; i5 < this.reader.getXrefSize(); i5++) {
                PdfObject pdfObject2 = this.reader.getPdfObject(i5);
                if (pdfObject2 != null) {
                    addToBody(pdfObject2, getNewObjectNumber(this.reader, i5, 0));
                }
            }
        } else {
            int i6 = 1;
            while (i6 < this.reader.getXrefSize()) {
                PdfObject pdfObjectRelease2 = this.reader.getPdfObjectRelease(i6);
                if (!(pdfObjectRelease2 == null || i2 == i6)) {
                    addToBody(pdfObjectRelease2, getNewObjectNumber(this.reader, i6, 0), i6 != number);
                }
                i6++;
            }
        }
        PdfIndirectReference pdfIndirectReference2 = null;
        if (this.crypto != null) {
            if (this.append) {
                pdfIndirectReference2 = this.reader.getCryptoRef();
            } else {
                pdfIndirectReference2 = addToBody((PdfObject) this.crypto.getEncryptionDictionary(), false).getIndirectReference();
            }
            pdfObject = this.crypto.getFileID(true);
        } else {
            PdfArray asArray = this.reader.trailer.getAsArray(PdfName.f600ID);
            if (asArray == null || asArray.getAsString(0) == null) {
                pdfObject = PdfEncryption.createInfoId(PdfEncryption.createDocumentId(), true);
            } else {
                pdfObject = PdfEncryption.createInfoId(asArray.getAsString(0).getBytes(), true);
            }
        }
        PdfIndirectReference pdfIndirectReference3 = new PdfIndirectReference(0, getNewObjectNumber(this.reader, ((PRIndirectReference) this.reader.trailer.get(PdfName.ROOT)).getNumber(), 0));
        this.body.writeCrossReferenceTable(this.f439os, pdfIndirectReference3, pdfIndirectReference, pdfIndirectReference2, pdfObject, this.prevxref);
        if (this.fullCompression) {
            writeKeyInfo(this.f439os);
            this.f439os.write(getISOBytes("startxref\n"));
            this.f439os.write(getISOBytes(String.valueOf(this.body.offset())));
            this.f439os.write(getISOBytes("\n%%EOF\n"));
        } else {
            new PdfWriter.PdfTrailer(this.body.size(), this.body.offset(), pdfIndirectReference3, pdfIndirectReference, pdfIndirectReference2, pdfObject, this.prevxref).toPdf(this, this.f439os);
        }
        this.f439os.flush();
        if (isCloseStream()) {
            this.f439os.close();
        }
        getCounter().written(this.f439os.getCounter());
    }

    /* access modifiers changed from: package-private */
    public void applyRotation(PdfDictionary pdfDictionary, ByteBuffer byteBuffer) {
        if (this.rotateContents) {
            Rectangle pageSizeWithRotation = this.reader.getPageSizeWithRotation(pdfDictionary);
            int rotation = pageSizeWithRotation.getRotation();
            if (rotation == 90) {
                byteBuffer.append(PdfContents.ROTATE90);
                byteBuffer.append(pageSizeWithRotation.getTop());
                byteBuffer.append(' ').append('0').append(PdfContents.ROTATEFINAL);
            } else if (rotation == 180) {
                byteBuffer.append(PdfContents.ROTATE180);
                byteBuffer.append(pageSizeWithRotation.getRight());
                byteBuffer.append(' ');
                byteBuffer.append(pageSizeWithRotation.getTop());
                byteBuffer.append(PdfContents.ROTATEFINAL);
            } else if (rotation == 270) {
                byteBuffer.append(PdfContents.ROTATE270);
                byteBuffer.append('0').append(' ');
                byteBuffer.append(pageSizeWithRotation.getRight());
                byteBuffer.append(PdfContents.ROTATEFINAL);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void alterContents() throws IOException {
        PdfArray pdfArray;
        for (PageStamp next : this.pagesToContent.values()) {
            PdfDictionary pdfDictionary = next.pageN;
            markUsed((PdfObject) pdfDictionary);
            PdfObject pdfObject = PdfReader.getPdfObject(pdfDictionary.get(PdfName.CONTENTS), pdfDictionary);
            if (pdfObject == null) {
                pdfArray = new PdfArray();
                pdfDictionary.put(PdfName.CONTENTS, pdfArray);
            } else if (pdfObject.isArray()) {
                PdfArray pdfArray2 = new PdfArray((PdfArray) pdfObject);
                pdfDictionary.put(PdfName.CONTENTS, pdfArray2);
                pdfArray = pdfArray2;
            } else if (pdfObject.isStream()) {
                pdfArray = new PdfArray();
                pdfArray.add(pdfDictionary.get(PdfName.CONTENTS));
                pdfDictionary.put(PdfName.CONTENTS, pdfArray);
            } else {
                pdfArray = new PdfArray();
                pdfDictionary.put(PdfName.CONTENTS, pdfArray);
            }
            ByteBuffer byteBuffer = new ByteBuffer();
            if (next.under != null) {
                byteBuffer.append(PdfContents.SAVESTATE);
                applyRotation(pdfDictionary, byteBuffer);
                byteBuffer.append(next.under.getInternalBuffer());
                byteBuffer.append(PdfContents.RESTORESTATE);
            }
            if (next.over != null) {
                byteBuffer.append(PdfContents.SAVESTATE);
            }
            PdfStream pdfStream = new PdfStream(byteBuffer.toByteArray());
            pdfStream.flateCompress(this.compressionLevel);
            pdfArray.addFirst(addToBody(pdfStream).getIndirectReference());
            byteBuffer.reset();
            if (next.over != null) {
                byteBuffer.append(' ');
                byteBuffer.append(PdfContents.RESTORESTATE);
                ByteBuffer internalBuffer = next.over.getInternalBuffer();
                byteBuffer.append(internalBuffer.getBuffer(), 0, next.replacePoint);
                byteBuffer.append(PdfContents.SAVESTATE);
                applyRotation(pdfDictionary, byteBuffer);
                byteBuffer.append(internalBuffer.getBuffer(), next.replacePoint, internalBuffer.size() - next.replacePoint);
                byteBuffer.append(PdfContents.RESTORESTATE);
                PdfStream pdfStream2 = new PdfStream(byteBuffer.toByteArray());
                pdfStream2.flateCompress(this.compressionLevel);
                pdfArray.add((PdfObject) addToBody(pdfStream2).getIndirectReference());
            }
            alterResources(next);
        }
    }

    /* access modifiers changed from: package-private */
    public void alterResources(PageStamp pageStamp) {
        pageStamp.pageN.put(PdfName.RESOURCES, pageStamp.pageResources.getResources());
    }

    /* access modifiers changed from: protected */
    public int getNewObjectNumber(PdfReader pdfReader, int i, int i2) {
        IntHashtable intHashtable = this.readers2intrefs.get(pdfReader);
        if (intHashtable != null) {
            int i3 = intHashtable.get(i);
            if (i3 != 0) {
                return i3;
            }
            int indirectReferenceNumber = getIndirectReferenceNumber();
            intHashtable.put(i, indirectReferenceNumber);
            return indirectReferenceNumber;
        } else if (this.currentPdfReaderInstance != null) {
            return this.currentPdfReaderInstance.getNewObjectNumber(i, i2);
        } else {
            if (this.append && i < this.initialXrefSize) {
                return i;
            }
            int i4 = this.myXref.get(i);
            if (i4 != 0) {
                return i4;
            }
            int indirectReferenceNumber2 = getIndirectReferenceNumber();
            this.myXref.put(i, indirectReferenceNumber2);
            return indirectReferenceNumber2;
        }
    }

    /* access modifiers changed from: package-private */
    public RandomAccessFileOrArray getReaderFile(PdfReader pdfReader) {
        if (this.readers2intrefs.containsKey(pdfReader)) {
            RandomAccessFileOrArray randomAccessFileOrArray = this.readers2file.get(pdfReader);
            if (randomAccessFileOrArray != null) {
                return randomAccessFileOrArray;
            }
            return pdfReader.getSafeFile();
        } else if (this.currentPdfReaderInstance == null) {
            return this.file;
        } else {
            return this.currentPdfReaderInstance.getReaderFile();
        }
    }

    public void registerReader(PdfReader pdfReader, boolean z) throws IOException {
        if (!this.readers2intrefs.containsKey(pdfReader)) {
            this.readers2intrefs.put(pdfReader, new IntHashtable());
            if (z) {
                RandomAccessFileOrArray safeFile = pdfReader.getSafeFile();
                this.readers2file.put(pdfReader, safeFile);
                safeFile.reOpen();
            }
        }
    }

    public void unRegisterReader(PdfReader pdfReader) {
        if (this.readers2intrefs.containsKey(pdfReader)) {
            this.readers2intrefs.remove(pdfReader);
            RandomAccessFileOrArray randomAccessFileOrArray = this.readers2file.get(pdfReader);
            if (randomAccessFileOrArray != null) {
                this.readers2file.remove(pdfReader);
                try {
                    randomAccessFileOrArray.close();
                } catch (Exception unused) {
                }
            }
        }
    }

    static void findAllObjects(PdfReader pdfReader, PdfObject pdfObject, IntHashtable intHashtable) {
        if (pdfObject != null) {
            int type = pdfObject.type();
            if (type == 5) {
                PdfArray pdfArray = (PdfArray) pdfObject;
                for (int i = 0; i < pdfArray.size(); i++) {
                    findAllObjects(pdfReader, pdfArray.getPdfObject(i), intHashtable);
                }
            } else if (type == 6 || type == 7) {
                PdfDictionary pdfDictionary = (PdfDictionary) pdfObject;
                for (PdfName pdfName : pdfDictionary.getKeys()) {
                    findAllObjects(pdfReader, pdfDictionary.get(pdfName), intHashtable);
                }
            } else if (type == 10) {
                PRIndirectReference pRIndirectReference = (PRIndirectReference) pdfObject;
                if (pdfReader == pRIndirectReference.getReader() && !intHashtable.containsKey(pRIndirectReference.getNumber())) {
                    intHashtable.put(pRIndirectReference.getNumber(), 1);
                    findAllObjects(pdfReader, PdfReader.getPdfObject(pdfObject), intHashtable);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00cd, code lost:
        r6 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00cd, code lost:
        r6 = r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addComments(com.itextpdf.text.pdf.FdfReader r12) throws java.io.IOException {
        /*
            r11 = this;
            java.util.HashMap<com.itextpdf.text.pdf.PdfReader, com.itextpdf.text.pdf.IntHashtable> r0 = r11.readers2intrefs
            boolean r0 = r0.containsKey(r12)
            if (r0 == 0) goto L_0x0009
            return
        L_0x0009:
            com.itextpdf.text.pdf.PdfDictionary r0 = r12.getCatalog()
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.FDF
            com.itextpdf.text.pdf.PdfDictionary r0 = r0.getAsDict(r1)
            if (r0 != 0) goto L_0x0016
            return
        L_0x0016:
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.ANNOTS
            com.itextpdf.text.pdf.PdfArray r0 = r0.getAsArray(r1)
            if (r0 == 0) goto L_0x011f
            int r1 = r0.size()
            if (r1 != 0) goto L_0x0026
            goto L_0x011f
        L_0x0026:
            r1 = 0
            r11.registerReader(r12, r1)
            com.itextpdf.text.pdf.IntHashtable r2 = new com.itextpdf.text.pdf.IntHashtable
            r2.<init>()
            java.util.HashMap r3 = new java.util.HashMap
            r3.<init>()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r5 = 0
        L_0x003a:
            int r6 = r0.size()
            r7 = 3
            if (r5 >= r6) goto L_0x008a
            com.itextpdf.text.pdf.PdfObject r6 = r0.getPdfObject(r5)
            com.itextpdf.text.pdf.PdfObject r8 = com.itextpdf.text.pdf.PdfReader.getPdfObject((com.itextpdf.text.pdf.PdfObject) r6)
            com.itextpdf.text.pdf.PdfDictionary r8 = (com.itextpdf.text.pdf.PdfDictionary) r8
            com.itextpdf.text.pdf.PdfName r9 = com.itextpdf.text.pdf.PdfName.PAGE
            com.itextpdf.text.pdf.PdfNumber r9 = r8.getAsNumber(r9)
            if (r9 == 0) goto L_0x0087
            int r9 = r9.intValue()
            com.itextpdf.text.pdf.PdfReader r10 = r11.reader
            int r10 = r10.getNumberOfPages()
            if (r9 < r10) goto L_0x0060
            goto L_0x0087
        L_0x0060:
            findAllObjects(r12, r6, r2)
            r4.add(r6)
            int r9 = r6.type()
            r10 = 10
            if (r9 != r10) goto L_0x0087
            com.itextpdf.text.pdf.PdfName r9 = com.itextpdf.text.pdf.PdfName.f616NM
            com.itextpdf.text.pdf.PdfObject r8 = r8.get(r9)
            com.itextpdf.text.pdf.PdfObject r8 = com.itextpdf.text.pdf.PdfReader.getPdfObject((com.itextpdf.text.pdf.PdfObject) r8)
            if (r8 == 0) goto L_0x0087
            int r9 = r8.type()
            if (r9 != r7) goto L_0x0087
            java.lang.String r7 = r8.toString()
            r3.put(r7, r6)
        L_0x0087:
            int r5 = r5 + 1
            goto L_0x003a
        L_0x008a:
            int[] r0 = r2.getKeys()
            r2 = 0
        L_0x008f:
            int r5 = r0.length
            if (r2 >= r5) goto L_0x00d7
            r5 = r0[r2]
            com.itextpdf.text.pdf.PdfObject r6 = r12.getPdfObject((int) r5)
            int r8 = r6.type()
            r9 = 6
            if (r8 != r9) goto L_0x00cd
            r8 = r6
            com.itextpdf.text.pdf.PdfDictionary r8 = (com.itextpdf.text.pdf.PdfDictionary) r8
            com.itextpdf.text.pdf.PdfName r9 = com.itextpdf.text.pdf.PdfName.IRT
            com.itextpdf.text.pdf.PdfObject r9 = r8.get(r9)
            com.itextpdf.text.pdf.PdfObject r9 = com.itextpdf.text.pdf.PdfReader.getPdfObject((com.itextpdf.text.pdf.PdfObject) r9)
            if (r9 == 0) goto L_0x00cd
            int r10 = r9.type()
            if (r10 != r7) goto L_0x00cd
            java.lang.String r9 = r9.toString()
            java.lang.Object r9 = r3.get(r9)
            com.itextpdf.text.pdf.PdfObject r9 = (com.itextpdf.text.pdf.PdfObject) r9
            if (r9 == 0) goto L_0x00cd
            com.itextpdf.text.pdf.PdfDictionary r6 = new com.itextpdf.text.pdf.PdfDictionary
            r6.<init>()
            r6.merge(r8)
            com.itextpdf.text.pdf.PdfName r8 = com.itextpdf.text.pdf.PdfName.IRT
            r6.put(r8, r9)
        L_0x00cd:
            int r5 = r11.getNewObjectNumber(r12, r5, r1)
            r11.addToBody((com.itextpdf.text.pdf.PdfObject) r6, (int) r5)
            int r2 = r2 + 1
            goto L_0x008f
        L_0x00d7:
            int r12 = r4.size()
            if (r1 >= r12) goto L_0x011f
            java.lang.Object r12 = r4.get(r1)
            com.itextpdf.text.pdf.PdfObject r12 = (com.itextpdf.text.pdf.PdfObject) r12
            com.itextpdf.text.pdf.PdfObject r0 = com.itextpdf.text.pdf.PdfReader.getPdfObject((com.itextpdf.text.pdf.PdfObject) r12)
            com.itextpdf.text.pdf.PdfDictionary r0 = (com.itextpdf.text.pdf.PdfDictionary) r0
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.PAGE
            com.itextpdf.text.pdf.PdfNumber r0 = r0.getAsNumber(r2)
            com.itextpdf.text.pdf.PdfReader r2 = r11.reader
            int r0 = r0.intValue()
            int r0 = r0 + 1
            com.itextpdf.text.pdf.PdfDictionary r0 = r2.getPageN(r0)
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.ANNOTS
            com.itextpdf.text.pdf.PdfObject r2 = r0.get(r2)
            com.itextpdf.text.pdf.PdfObject r2 = com.itextpdf.text.pdf.PdfReader.getPdfObject(r2, r0)
            com.itextpdf.text.pdf.PdfArray r2 = (com.itextpdf.text.pdf.PdfArray) r2
            if (r2 != 0) goto L_0x0116
            com.itextpdf.text.pdf.PdfArray r2 = new com.itextpdf.text.pdf.PdfArray
            r2.<init>()
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.ANNOTS
            r0.put(r3, r2)
            r11.markUsed((com.itextpdf.text.pdf.PdfObject) r0)
        L_0x0116:
            r11.markUsed((com.itextpdf.text.pdf.PdfObject) r2)
            r2.add((com.itextpdf.text.pdf.PdfObject) r12)
            int r1 = r1 + 1
            goto L_0x00d7
        L_0x011f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfStamperImp.addComments(com.itextpdf.text.pdf.FdfReader):void");
    }

    /* access modifiers changed from: package-private */
    public PageStamp getPageStamp(int i) {
        PdfDictionary pageN = this.reader.getPageN(i);
        PageStamp pageStamp = this.pagesToContent.get(pageN);
        if (pageStamp != null) {
            return pageStamp;
        }
        PageStamp pageStamp2 = new PageStamp(this, this.reader, pageN);
        this.pagesToContent.put(pageN, pageStamp2);
        return pageStamp2;
    }

    /* access modifiers changed from: package-private */
    public PdfContentByte getUnderContent(int i) {
        if (i < 1 || i > this.reader.getNumberOfPages()) {
            return null;
        }
        PageStamp pageStamp = getPageStamp(i);
        if (pageStamp.under == null) {
            pageStamp.under = new StampContent(this, pageStamp);
        }
        return pageStamp.under;
    }

    /* access modifiers changed from: package-private */
    public PdfContentByte getOverContent(int i) {
        if (i < 1 || i > this.reader.getNumberOfPages()) {
            return null;
        }
        PageStamp pageStamp = getPageStamp(i);
        if (pageStamp.over == null) {
            pageStamp.over = new StampContent(this, pageStamp);
        }
        return pageStamp.over;
    }

    /* access modifiers changed from: package-private */
    public void correctAcroFieldPages(int i) {
        if (this.acroFields != null && i <= this.reader.getNumberOfPages()) {
            for (AcroFields.Item next : this.acroFields.getFields().values()) {
                for (int i2 = 0; i2 < next.size(); i2++) {
                    int intValue = next.getPage(i2).intValue();
                    if (intValue >= i) {
                        next.forcePage(i2, intValue + 1);
                    }
                }
            }
        }
    }

    private static void moveRectangle(PdfDictionary pdfDictionary, PdfReader pdfReader, int i, PdfName pdfName, String str) {
        Rectangle boxSize = pdfReader.getBoxSize(i, str);
        if (boxSize == null) {
            pdfDictionary.remove(pdfName);
        } else {
            pdfDictionary.put(pdfName, new PdfRectangle(boxSize));
        }
    }

    /* access modifiers changed from: package-private */
    public void replacePage(PdfReader pdfReader, int i, int i2) {
        PdfDictionary pageN = this.reader.getPageN(i2);
        if (!this.pagesToContent.containsKey(pageN)) {
            PdfImportedPage importedPage = getImportedPage(pdfReader, i);
            PdfDictionary pageNRelease = this.reader.getPageNRelease(i2);
            pageNRelease.remove(PdfName.RESOURCES);
            pageNRelease.remove(PdfName.CONTENTS);
            moveRectangle(pageNRelease, pdfReader, i, PdfName.MEDIABOX, "media");
            moveRectangle(pageNRelease, pdfReader, i, PdfName.CROPBOX, "crop");
            moveRectangle(pageNRelease, pdfReader, i, PdfName.TRIMBOX, "trim");
            moveRectangle(pageNRelease, pdfReader, i, PdfName.ARTBOX, "art");
            moveRectangle(pageNRelease, pdfReader, i, PdfName.BLEEDBOX, "bleed");
            pageNRelease.put(PdfName.ROTATE, new PdfNumber(pdfReader.getPageRotation(i)));
            getOverContent(i2).addTemplate((PdfTemplate) importedPage, 0.0f, 0.0f);
            PageStamp pageStamp = this.pagesToContent.get(pageN);
            pageStamp.replacePoint = pageStamp.over.getInternalBuffer().size();
            return;
        }
        throw new IllegalStateException(MessageLocalization.getComposedMessage("this.page.cannot.be.replaced.new.content.was.already.added", new Object[0]));
    }

    /* access modifiers changed from: package-private */
    public void insertPage(int i, Rectangle rectangle) {
        PRIndirectReference pRIndirectReference;
        PdfDictionary pdfDictionary;
        Rectangle rectangle2 = new Rectangle(rectangle);
        int rotation = rectangle2.getRotation() % 360;
        PdfDictionary pdfDictionary2 = new PdfDictionary(PdfName.PAGE);
        pdfDictionary2.put(PdfName.RESOURCES, new PdfDictionary());
        pdfDictionary2.put(PdfName.ROTATE, new PdfNumber(rotation));
        pdfDictionary2.put(PdfName.MEDIABOX, new PdfRectangle(rectangle2, rotation));
        PRIndirectReference addPdfObject = this.reader.addPdfObject(pdfDictionary2);
        if (i > this.reader.getNumberOfPages()) {
            PdfReader pdfReader = this.reader;
            pRIndirectReference = new PRIndirectReference(this.reader, ((PRIndirectReference) pdfReader.getPageNRelease(pdfReader.getNumberOfPages()).get(PdfName.PARENT)).getNumber());
            pdfDictionary = (PdfDictionary) PdfReader.getPdfObject((PdfObject) pRIndirectReference);
            PdfArray pdfArray = (PdfArray) PdfReader.getPdfObject(pdfDictionary.get(PdfName.KIDS), pdfDictionary);
            pdfArray.add((PdfObject) addPdfObject);
            markUsed((PdfObject) pdfArray);
            this.reader.pageRefs.insertPage(i, addPdfObject);
        } else {
            if (i < 1) {
                i = 1;
            }
            PdfDictionary pageN = this.reader.getPageN(i);
            PRIndirectReference pageOrigRef = this.reader.getPageOrigRef(i);
            this.reader.releasePage(i);
            PRIndirectReference pRIndirectReference2 = new PRIndirectReference(this.reader, ((PRIndirectReference) pageN.get(PdfName.PARENT)).getNumber());
            pdfDictionary = (PdfDictionary) PdfReader.getPdfObject((PdfObject) pRIndirectReference2);
            PdfArray pdfArray2 = (PdfArray) PdfReader.getPdfObject(pdfDictionary.get(PdfName.KIDS), pdfDictionary);
            int size = pdfArray2.size();
            int number = pageOrigRef.getNumber();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    break;
                } else if (number == ((PRIndirectReference) pdfArray2.getPdfObject(i2)).getNumber()) {
                    pdfArray2.add(i2, addPdfObject);
                    break;
                } else {
                    i2++;
                }
            }
            if (size != pdfArray2.size()) {
                markUsed((PdfObject) pdfArray2);
                this.reader.pageRefs.insertPage(i, addPdfObject);
                correctAcroFieldPages(i);
                pRIndirectReference = pRIndirectReference2;
            } else {
                throw new RuntimeException(MessageLocalization.getComposedMessage("internal.inconsistence", new Object[0]));
            }
        }
        pdfDictionary2.put(PdfName.PARENT, pRIndirectReference);
        while (pdfDictionary != null) {
            markUsed((PdfObject) pdfDictionary);
            pdfDictionary.put(PdfName.COUNT, new PdfNumber(((PdfNumber) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.COUNT))).intValue() + 1));
            pdfDictionary = pdfDictionary.getAsDict(PdfName.PARENT);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isRotateContents() {
        return this.rotateContents;
    }

    /* access modifiers changed from: package-private */
    public void setRotateContents(boolean z) {
        this.rotateContents = z;
    }

    /* access modifiers changed from: package-private */
    public boolean isContentWritten() {
        return this.body.size() > 1;
    }

    /* access modifiers changed from: package-private */
    public AcroFields getAcroFields() {
        if (this.acroFields == null) {
            this.acroFields = new AcroFields(this.reader, this);
        }
        return this.acroFields;
    }

    /* access modifiers changed from: package-private */
    public void setFormFlattening(boolean z) {
        this.flat = z;
    }

    /* access modifiers changed from: package-private */
    public void setFreeTextFlattening(boolean z) {
        this.flatFreeText = z;
    }

    /* access modifiers changed from: package-private */
    public boolean partialFormFlattening(String str) {
        getAcroFields();
        if (this.acroFields.getXfa().isXfaPresent()) {
            throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("partial.form.flattening.is.not.supported.with.xfa.forms", new Object[0]));
        } else if (!this.acroFields.getFields().containsKey(str)) {
            return false;
        } else {
            this.partialFlattening.add(str);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x029c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void flatFields() {
        /*
            r21 = this;
            r0 = r21
            boolean r1 = r0.append
            r2 = 0
            if (r1 != 0) goto L_0x0413
            r21.getAcroFields()
            com.itextpdf.text.pdf.AcroFields r1 = r0.acroFields
            java.util.Map r1 = r1.getFields()
            boolean r3 = r0.fieldsAdded
            if (r3 == 0) goto L_0x0036
            java.util.HashSet<java.lang.String> r3 = r0.partialFlattening
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L_0x0036
            java.util.Set r3 = r1.keySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x0024:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0036
            java.lang.Object r4 = r3.next()
            java.lang.String r4 = (java.lang.String) r4
            java.util.HashSet<java.lang.String> r5 = r0.partialFlattening
            r5.add(r4)
            goto L_0x0024
        L_0x0036:
            com.itextpdf.text.pdf.PdfReader r3 = r0.reader
            com.itextpdf.text.pdf.PdfDictionary r3 = r3.getCatalog()
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.ACROFORM
            com.itextpdf.text.pdf.PdfDictionary r3 = r3.getAsDict(r4)
            if (r3 == 0) goto L_0x0051
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.FIELDS
            com.itextpdf.text.pdf.PdfObject r5 = r3.get(r5)
            com.itextpdf.text.pdf.PdfObject r3 = com.itextpdf.text.pdf.PdfReader.getPdfObject(r5, r3)
            com.itextpdf.text.pdf.PdfArray r3 = (com.itextpdf.text.pdf.PdfArray) r3
            goto L_0x0052
        L_0x0051:
            r3 = 0
        L_0x0052:
            java.util.Set r1 = r1.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x005a:
            boolean r5 = r1.hasNext()
            r6 = 1
            if (r5 == 0) goto L_0x039e
            java.lang.Object r5 = r1.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.lang.Object r7 = r5.getKey()
            java.lang.String r7 = (java.lang.String) r7
            java.util.HashSet<java.lang.String> r8 = r0.partialFlattening
            boolean r8 = r8.isEmpty()
            if (r8 != 0) goto L_0x007e
            java.util.HashSet<java.lang.String> r8 = r0.partialFlattening
            boolean r8 = r8.contains(r7)
            if (r8 != 0) goto L_0x007e
            goto L_0x005a
        L_0x007e:
            java.lang.Object r5 = r5.getValue()
            com.itextpdf.text.pdf.AcroFields$Item r5 = (com.itextpdf.text.pdf.AcroFields.Item) r5
            r8 = 0
        L_0x0085:
            int r9 = r5.size()
            if (r8 >= r9) goto L_0x005a
            com.itextpdf.text.pdf.PdfDictionary r9 = r5.getMerged(r8)
            com.itextpdf.text.pdf.PdfName r10 = com.itextpdf.text.pdf.PdfName.f580F
            com.itextpdf.text.pdf.PdfNumber r10 = r9.getAsNumber(r10)
            if (r10 == 0) goto L_0x009c
            int r10 = r10.intValue()
            goto L_0x009d
        L_0x009c:
            r10 = 0
        L_0x009d:
            java.lang.Integer r11 = r5.getPage(r8)
            int r11 = r11.intValue()
            if (r11 >= r6) goto L_0x00a9
            goto L_0x0398
        L_0x00a9:
            com.itextpdf.text.pdf.PdfName r12 = com.itextpdf.text.pdf.PdfName.f549AP
            com.itextpdf.text.pdf.PdfDictionary r12 = r9.getAsDict(r12)
            if (r12 == 0) goto L_0x00c0
            com.itextpdf.text.pdf.PdfName r13 = com.itextpdf.text.pdf.PdfName.f610N
            com.itextpdf.text.pdf.PdfStream r13 = r12.getAsStream(r13)
            if (r13 != 0) goto L_0x00c1
            com.itextpdf.text.pdf.PdfName r13 = com.itextpdf.text.pdf.PdfName.f610N
            com.itextpdf.text.pdf.PdfDictionary r13 = r12.getAsDict(r13)
            goto L_0x00c1
        L_0x00c0:
            r13 = 0
        L_0x00c1:
            com.itextpdf.text.pdf.AcroFields r14 = r0.acroFields
            boolean r14 = r14.isGenerateAppearances()
            r4 = 3
            r15 = 2
            if (r14 == 0) goto L_0x0197
            if (r12 == 0) goto L_0x017d
            if (r13 != 0) goto L_0x00d1
            goto L_0x017d
        L_0x00d1:
            boolean r14 = r13.isStream()
            if (r14 == 0) goto L_0x0229
            com.itextpdf.text.pdf.PdfStream r13 = (com.itextpdf.text.pdf.PdfStream) r13
            com.itextpdf.text.pdf.PdfName r14 = com.itextpdf.text.pdf.PdfName.BBOX
            com.itextpdf.text.pdf.PdfArray r14 = r13.getAsArray(r14)
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.RECT
            com.itextpdf.text.pdf.PdfArray r6 = r9.getAsArray(r6)
            if (r14 == 0) goto L_0x0229
            if (r6 == 0) goto L_0x0229
            com.itextpdf.text.pdf.PdfNumber r17 = r6.getAsNumber(r15)
            float r17 = r17.floatValue()
            com.itextpdf.text.pdf.PdfNumber r18 = r6.getAsNumber(r2)
            float r18 = r18.floatValue()
            float r17 = r17 - r18
            com.itextpdf.text.pdf.PdfNumber r18 = r14.getAsNumber(r15)
            float r18 = r18.floatValue()
            com.itextpdf.text.pdf.PdfNumber r19 = r14.getAsNumber(r2)
            float r19 = r19.floatValue()
            float r18 = r18 - r19
            com.itextpdf.text.pdf.PdfNumber r19 = r6.getAsNumber(r4)
            float r19 = r19.floatValue()
            r15 = 1
            com.itextpdf.text.pdf.PdfNumber r6 = r6.getAsNumber(r15)
            float r6 = r6.floatValue()
            float r19 = r19 - r6
            com.itextpdf.text.pdf.PdfNumber r6 = r14.getAsNumber(r4)
            float r6 = r6.floatValue()
            com.itextpdf.text.pdf.PdfNumber r14 = r14.getAsNumber(r15)
            float r14 = r14.floatValue()
            float r6 = r6 - r14
            r14 = 2139095039(0x7f7fffff, float:3.4028235E38)
            r15 = 0
            int r20 = (r18 > r15 ? 1 : (r18 == r15 ? 0 : -1))
            if (r20 == 0) goto L_0x013c
            float r17 = r17 / r18
            goto L_0x013f
        L_0x013c:
            r17 = 2139095039(0x7f7fffff, float:3.4028235E38)
        L_0x013f:
            float r17 = java.lang.Math.abs(r17)
            int r18 = (r6 > r15 ? 1 : (r6 == r15 ? 0 : -1))
            if (r18 == 0) goto L_0x0149
            float r14 = r19 / r6
        L_0x0149:
            float r6 = java.lang.Math.abs(r14)
            r14 = 1065353216(0x3f800000, float:1.0)
            int r16 = (r17 > r14 ? 1 : (r17 == r14 ? 0 : -1))
            if (r16 != 0) goto L_0x0157
            int r14 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1))
            if (r14 == 0) goto L_0x0229
        L_0x0157:
            com.itextpdf.text.pdf.NumberArray r14 = new com.itextpdf.text.pdf.NumberArray
            r4 = 6
            float[] r4 = new float[r4]
            r4[r2] = r17
            r16 = 1
            r4[r16] = r15
            r16 = 2
            r4[r16] = r15
            r16 = 3
            r4[r16] = r6
            r6 = 4
            r4[r6] = r15
            r6 = 5
            r4[r6] = r15
            r14.<init>((float[]) r4)
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.MATRIX
            r13.put(r4, r14)
            r0.markUsed((com.itextpdf.text.pdf.PdfObject) r13)
            goto L_0x0229
        L_0x017d:
            com.itextpdf.text.pdf.AcroFields r4 = r0.acroFields     // Catch:{ DocumentException | IOException -> 0x0194 }
            r4.regenerateField(r7)     // Catch:{ DocumentException | IOException -> 0x0194 }
            com.itextpdf.text.pdf.AcroFields r4 = r0.acroFields     // Catch:{ DocumentException | IOException -> 0x0194 }
            com.itextpdf.text.pdf.AcroFields$Item r4 = r4.getFieldItem(r7)     // Catch:{ DocumentException | IOException -> 0x0194 }
            com.itextpdf.text.pdf.PdfDictionary r4 = r4.getMerged(r8)     // Catch:{ DocumentException | IOException -> 0x0194 }
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.f549AP     // Catch:{ DocumentException | IOException -> 0x0194 }
            com.itextpdf.text.pdf.PdfDictionary r12 = r4.getAsDict(r6)     // Catch:{ DocumentException | IOException -> 0x0194 }
            goto L_0x0229
        L_0x0194:
            goto L_0x0229
        L_0x0197:
            if (r12 == 0) goto L_0x0229
            if (r13 == 0) goto L_0x0229
            com.itextpdf.text.pdf.PdfDictionary r13 = (com.itextpdf.text.pdf.PdfDictionary) r13
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.BBOX
            com.itextpdf.text.pdf.PdfArray r4 = r13.getAsArray(r4)
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.RECT
            com.itextpdf.text.pdf.PdfArray r6 = r9.getAsArray(r6)
            if (r4 == 0) goto L_0x0229
            if (r6 == 0) goto L_0x0229
            r13 = 2
            com.itextpdf.text.pdf.PdfNumber r14 = r4.getAsNumber(r13)
            float r14 = r14.floatValue()
            com.itextpdf.text.pdf.PdfNumber r15 = r4.getAsNumber(r2)
            float r15 = r15.floatValue()
            float r14 = r14 - r15
            com.itextpdf.text.pdf.PdfNumber r13 = r6.getAsNumber(r13)
            float r13 = r13.floatValue()
            com.itextpdf.text.pdf.PdfNumber r15 = r6.getAsNumber(r2)
            float r15 = r15.floatValue()
            float r13 = r13 - r15
            float r14 = r14 - r13
            r13 = 3
            com.itextpdf.text.pdf.PdfNumber r15 = r4.getAsNumber(r13)
            float r15 = r15.floatValue()
            r2 = 1
            com.itextpdf.text.pdf.PdfNumber r4 = r4.getAsNumber(r2)
            float r4 = r4.floatValue()
            float r15 = r15 - r4
            com.itextpdf.text.pdf.PdfNumber r4 = r6.getAsNumber(r13)
            float r4 = r4.floatValue()
            com.itextpdf.text.pdf.PdfNumber r6 = r6.getAsNumber(r2)
            float r2 = r6.floatValue()
            float r4 = r4 - r2
            float r15 = r15 - r4
            float r2 = java.lang.Math.abs(r14)
            r4 = 1065353216(0x3f800000, float:1.0)
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 > 0) goto L_0x0208
            float r2 = java.lang.Math.abs(r15)
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x0229
        L_0x0208:
            com.itextpdf.text.pdf.AcroFields r2 = r0.acroFields     // Catch:{ DocumentException | IOException -> 0x0194 }
            r4 = 1
            r2.setGenerateAppearances(r4)     // Catch:{ DocumentException | IOException -> 0x0194 }
            com.itextpdf.text.pdf.AcroFields r2 = r0.acroFields     // Catch:{ DocumentException | IOException -> 0x0194 }
            r2.regenerateField(r7)     // Catch:{ DocumentException | IOException -> 0x0194 }
            com.itextpdf.text.pdf.AcroFields r2 = r0.acroFields     // Catch:{ DocumentException | IOException -> 0x0194 }
            r4 = 0
            r2.setGenerateAppearances(r4)     // Catch:{ DocumentException | IOException -> 0x0194 }
            com.itextpdf.text.pdf.AcroFields r2 = r0.acroFields     // Catch:{ DocumentException | IOException -> 0x0194 }
            com.itextpdf.text.pdf.AcroFields$Item r2 = r2.getFieldItem(r7)     // Catch:{ DocumentException | IOException -> 0x0194 }
            com.itextpdf.text.pdf.PdfDictionary r2 = r2.getMerged(r8)     // Catch:{ DocumentException | IOException -> 0x0194 }
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.f549AP     // Catch:{ DocumentException | IOException -> 0x0194 }
            com.itextpdf.text.pdf.PdfDictionary r12 = r2.getAsDict(r4)     // Catch:{ DocumentException | IOException -> 0x0194 }
        L_0x0229:
            if (r12 == 0) goto L_0x02bf
            r2 = r10 & 4
            if (r2 == 0) goto L_0x02bf
            r2 = r10 & 2
            if (r2 != 0) goto L_0x02bf
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.f610N
            com.itextpdf.text.pdf.PdfObject r2 = r12.get(r2)
            if (r2 == 0) goto L_0x0299
            com.itextpdf.text.pdf.PdfObject r4 = com.itextpdf.text.pdf.PdfReader.getPdfObject((com.itextpdf.text.pdf.PdfObject) r2)
            boolean r6 = r2 instanceof com.itextpdf.text.pdf.PdfIndirectReference
            if (r6 == 0) goto L_0x0251
            boolean r6 = r2.isIndirect()
            if (r6 != 0) goto L_0x0251
            com.itextpdf.text.pdf.PdfAppearance r4 = new com.itextpdf.text.pdf.PdfAppearance
            com.itextpdf.text.pdf.PdfIndirectReference r2 = (com.itextpdf.text.pdf.PdfIndirectReference) r2
            r4.<init>((com.itextpdf.text.pdf.PdfIndirectReference) r2)
            goto L_0x029a
        L_0x0251:
            boolean r6 = r4 instanceof com.itextpdf.text.pdf.PdfStream
            if (r6 == 0) goto L_0x0266
            com.itextpdf.text.pdf.PdfDictionary r4 = (com.itextpdf.text.pdf.PdfDictionary) r4
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.SUBTYPE
            com.itextpdf.text.pdf.PdfName r10 = com.itextpdf.text.pdf.PdfName.FORM
            r4.put(r6, r10)
            com.itextpdf.text.pdf.PdfAppearance r4 = new com.itextpdf.text.pdf.PdfAppearance
            com.itextpdf.text.pdf.PdfIndirectReference r2 = (com.itextpdf.text.pdf.PdfIndirectReference) r2
            r4.<init>((com.itextpdf.text.pdf.PdfIndirectReference) r2)
            goto L_0x029a
        L_0x0266:
            if (r4 == 0) goto L_0x0299
            boolean r2 = r4.isDictionary()
            if (r2 == 0) goto L_0x0299
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.f550AS
            com.itextpdf.text.pdf.PdfName r2 = r9.getAsName(r2)
            if (r2 == 0) goto L_0x0299
            com.itextpdf.text.pdf.PdfDictionary r4 = (com.itextpdf.text.pdf.PdfDictionary) r4
            com.itextpdf.text.pdf.PdfObject r2 = r4.get(r2)
            com.itextpdf.text.pdf.PdfIndirectReference r2 = (com.itextpdf.text.pdf.PdfIndirectReference) r2
            if (r2 == 0) goto L_0x0299
            com.itextpdf.text.pdf.PdfAppearance r4 = new com.itextpdf.text.pdf.PdfAppearance
            r4.<init>((com.itextpdf.text.pdf.PdfIndirectReference) r2)
            boolean r6 = r2.isIndirect()
            if (r6 == 0) goto L_0x029a
            com.itextpdf.text.pdf.PdfObject r2 = com.itextpdf.text.pdf.PdfReader.getPdfObject((com.itextpdf.text.pdf.PdfObject) r2)
            com.itextpdf.text.pdf.PdfDictionary r2 = (com.itextpdf.text.pdf.PdfDictionary) r2
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.SUBTYPE
            com.itextpdf.text.pdf.PdfName r10 = com.itextpdf.text.pdf.PdfName.FORM
            r2.put(r6, r10)
            goto L_0x029a
        L_0x0299:
            r4 = 0
        L_0x029a:
            if (r4 == 0) goto L_0x02bf
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.RECT
            com.itextpdf.text.pdf.PdfArray r2 = r9.getAsArray(r2)
            com.itextpdf.text.Rectangle r2 = com.itextpdf.text.pdf.PdfReader.getNormalizedRectangle(r2)
            com.itextpdf.text.pdf.PdfContentByte r6 = r0.getOverContent(r11)
            java.lang.String r9 = "Q "
            r6.setLiteral((java.lang.String) r9)
            float r9 = r2.getLeft()
            float r2 = r2.getBottom()
            r6.addTemplate((com.itextpdf.text.pdf.PdfTemplate) r4, (float) r9, (float) r2)
            java.lang.String r2 = "q "
            r6.setLiteral((java.lang.String) r2)
        L_0x02bf:
            java.util.HashSet<java.lang.String> r2 = r0.partialFlattening
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x02c9
            goto L_0x0398
        L_0x02c9:
            com.itextpdf.text.pdf.PdfReader r2 = r0.reader
            com.itextpdf.text.pdf.PdfDictionary r2 = r2.getPageN(r11)
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.ANNOTS
            com.itextpdf.text.pdf.PdfArray r4 = r2.getAsArray(r4)
            if (r4 != 0) goto L_0x02d9
            goto L_0x0398
        L_0x02d9:
            r6 = 0
        L_0x02da:
            int r9 = r4.size()
            if (r6 >= r9) goto L_0x0384
            com.itextpdf.text.pdf.PdfObject r9 = r4.getPdfObject(r6)
            boolean r10 = r9.isIndirect()
            if (r10 != 0) goto L_0x02ed
        L_0x02ea:
            r13 = 1
            goto L_0x0381
        L_0x02ed:
            com.itextpdf.text.pdf.PdfIndirectReference r10 = r5.getWidgetRef(r8)
            boolean r11 = r10.isIndirect()
            if (r11 != 0) goto L_0x02f8
            goto L_0x02ea
        L_0x02f8:
            com.itextpdf.text.pdf.PRIndirectReference r9 = (com.itextpdf.text.pdf.PRIndirectReference) r9
            int r9 = r9.getNumber()
            com.itextpdf.text.pdf.PRIndirectReference r10 = (com.itextpdf.text.pdf.PRIndirectReference) r10
            int r11 = r10.getNumber()
            if (r9 != r11) goto L_0x02ea
            int r9 = r6 + -1
            r4.remove(r6)
        L_0x030b:
            com.itextpdf.text.pdf.PdfObject r6 = com.itextpdf.text.pdf.PdfReader.getPdfObject((com.itextpdf.text.pdf.PdfObject) r10)
            com.itextpdf.text.pdf.PdfDictionary r6 = (com.itextpdf.text.pdf.PdfDictionary) r6
            com.itextpdf.text.pdf.PdfName r11 = com.itextpdf.text.pdf.PdfName.PARENT
            com.itextpdf.text.pdf.PdfObject r6 = r6.get(r11)
            com.itextpdf.text.pdf.PRIndirectReference r6 = (com.itextpdf.text.pdf.PRIndirectReference) r6
            com.itextpdf.text.pdf.PdfReader.killIndirect(r10)
            if (r6 != 0) goto L_0x0345
            r6 = 0
        L_0x031f:
            int r11 = r3.size()
            if (r6 >= r11) goto L_0x0343
            com.itextpdf.text.pdf.PdfObject r11 = r3.getPdfObject(r6)
            boolean r12 = r11.isIndirect()
            if (r12 == 0) goto L_0x0340
            com.itextpdf.text.pdf.PRIndirectReference r11 = (com.itextpdf.text.pdf.PRIndirectReference) r11
            int r11 = r11.getNumber()
            int r12 = r10.getNumber()
            if (r11 != r12) goto L_0x0340
            r3.remove(r6)
            int r6 = r6 + -1
        L_0x0340:
            r11 = 1
            int r6 = r6 + r11
            goto L_0x031f
        L_0x0343:
            r13 = 1
            goto L_0x037d
        L_0x0345:
            com.itextpdf.text.pdf.PdfObject r11 = com.itextpdf.text.pdf.PdfReader.getPdfObject((com.itextpdf.text.pdf.PdfObject) r6)
            com.itextpdf.text.pdf.PdfDictionary r11 = (com.itextpdf.text.pdf.PdfDictionary) r11
            com.itextpdf.text.pdf.PdfName r12 = com.itextpdf.text.pdf.PdfName.KIDS
            com.itextpdf.text.pdf.PdfArray r11 = r11.getAsArray(r12)
            r12 = 0
        L_0x0352:
            int r13 = r11.size()
            if (r12 >= r13) goto L_0x0376
            com.itextpdf.text.pdf.PdfObject r13 = r11.getPdfObject(r12)
            boolean r14 = r13.isIndirect()
            if (r14 == 0) goto L_0x0373
            com.itextpdf.text.pdf.PRIndirectReference r13 = (com.itextpdf.text.pdf.PRIndirectReference) r13
            int r13 = r13.getNumber()
            int r14 = r10.getNumber()
            if (r13 != r14) goto L_0x0373
            r11.remove(r12)
            int r12 = r12 + -1
        L_0x0373:
            r13 = 1
            int r12 = r12 + r13
            goto L_0x0352
        L_0x0376:
            r13 = 1
            boolean r10 = r11.isEmpty()
            if (r10 != 0) goto L_0x037f
        L_0x037d:
            r6 = r9
            goto L_0x0381
        L_0x037f:
            r10 = r6
            goto L_0x030b
        L_0x0381:
            int r6 = r6 + r13
            goto L_0x02da
        L_0x0384:
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L_0x0398
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.ANNOTS
            com.itextpdf.text.pdf.PdfObject r4 = r2.get(r4)
            com.itextpdf.text.pdf.PdfReader.killIndirect(r4)
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.ANNOTS
            r2.remove(r4)
        L_0x0398:
            int r8 = r8 + 1
            r2 = 0
            r6 = 1
            goto L_0x0085
        L_0x039e:
            boolean r1 = r0.fieldsAdded
            if (r1 != 0) goto L_0x0412
            java.util.HashSet<java.lang.String> r1 = r0.partialFlattening
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x0412
            r15 = 1
        L_0x03ab:
            com.itextpdf.text.pdf.PdfReader r1 = r0.reader
            int r1 = r1.getNumberOfPages()
            if (r15 > r1) goto L_0x040f
            com.itextpdf.text.pdf.PdfReader r1 = r0.reader
            com.itextpdf.text.pdf.PdfDictionary r1 = r1.getPageN(r15)
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.ANNOTS
            com.itextpdf.text.pdf.PdfArray r2 = r1.getAsArray(r2)
            if (r2 != 0) goto L_0x03c3
            r3 = 1
            goto L_0x040c
        L_0x03c3:
            r4 = 0
        L_0x03c4:
            int r3 = r2.size()
            if (r4 >= r3) goto L_0x03f7
            com.itextpdf.text.pdf.PdfObject r3 = r2.getDirectObject(r4)
            boolean r5 = r3 instanceof com.itextpdf.text.pdf.PdfIndirectReference
            if (r5 == 0) goto L_0x03d9
            boolean r5 = r3.isIndirect()
            if (r5 != 0) goto L_0x03d9
            goto L_0x03f4
        L_0x03d9:
            boolean r5 = r3.isDictionary()
            if (r5 == 0) goto L_0x03ef
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.WIDGET
            com.itextpdf.text.pdf.PdfDictionary r3 = (com.itextpdf.text.pdf.PdfDictionary) r3
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.SUBTYPE
            com.itextpdf.text.pdf.PdfObject r3 = r3.get(r6)
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x03f4
        L_0x03ef:
            r2.remove(r4)
            int r4 = r4 + -1
        L_0x03f4:
            r3 = 1
            int r4 = r4 + r3
            goto L_0x03c4
        L_0x03f7:
            r3 = 1
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x040c
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.ANNOTS
            com.itextpdf.text.pdf.PdfObject r2 = r1.get(r2)
            com.itextpdf.text.pdf.PdfReader.killIndirect(r2)
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.ANNOTS
            r1.remove(r2)
        L_0x040c:
            int r15 = r15 + 1
            goto L_0x03ab
        L_0x040f:
            r21.eliminateAcroformObjects()
        L_0x0412:
            return
        L_0x0413:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r3 = "field.flattening.is.not.supported.in.append.mode"
            java.lang.String r2 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r3, (java.lang.Object[]) r2)
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfStamperImp.flatFields():void");
    }

    /* access modifiers changed from: package-private */
    public void eliminateAcroformObjects() {
        PdfObject pdfObject = this.reader.getCatalog().get(PdfName.ACROFORM);
        if (pdfObject != null) {
            PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObject(pdfObject);
            this.reader.killXref(pdfDictionary.get(PdfName.XFA));
            pdfDictionary.remove(PdfName.XFA);
            PdfObject pdfObject2 = pdfDictionary.get(PdfName.FIELDS);
            if (pdfObject2 != null) {
                PdfDictionary pdfDictionary2 = new PdfDictionary();
                pdfDictionary2.put(PdfName.KIDS, pdfObject2);
                sweepKids(pdfDictionary2);
                PdfReader.killIndirect(pdfObject2);
                pdfDictionary.put(PdfName.FIELDS, new PdfArray());
            }
            pdfDictionary.remove(PdfName.SIGFLAGS);
            pdfDictionary.remove(PdfName.NEEDAPPEARANCES);
            pdfDictionary.remove(PdfName.f574DR);
        }
    }

    /* access modifiers changed from: package-private */
    public void sweepKids(PdfObject pdfObject) {
        PdfArray pdfArray;
        PdfObject killIndirect = PdfReader.killIndirect(pdfObject);
        if (killIndirect != null && killIndirect.isDictionary() && (pdfArray = (PdfArray) PdfReader.killIndirect(((PdfDictionary) killIndirect).get(PdfName.KIDS))) != null) {
            for (int i = 0; i < pdfArray.size(); i++) {
                sweepKids(pdfArray.getPdfObject(i));
            }
        }
    }

    public void setFlatAnnotations(boolean z) {
        this.flatannotations = z;
    }

    /* access modifiers changed from: protected */
    public void flattenAnnotations() {
        flattenAnnotations(false);
    }

    private void flattenAnnotations(boolean z) {
        PdfObject pdfObject;
        PdfName asName;
        PdfIndirectReference pdfIndirectReference;
        if (!this.append) {
            for (int i = 1; i <= this.reader.getNumberOfPages(); i++) {
                PdfDictionary pageN = this.reader.getPageN(i);
                PdfArray asArray = pageN.getAsArray(PdfName.ANNOTS);
                if (asArray != null) {
                    int i2 = 0;
                    while (i2 < asArray.size()) {
                        PdfObject directObject = asArray.getDirectObject(i2);
                        if ((!(directObject instanceof PdfIndirectReference) || directObject.isIndirect()) && (directObject instanceof PdfDictionary)) {
                            PdfDictionary pdfDictionary = (PdfDictionary) directObject;
                            if (z) {
                                if (!pdfDictionary.get(PdfName.SUBTYPE).equals(PdfName.FREETEXT)) {
                                }
                            } else if (pdfDictionary.get(PdfName.SUBTYPE).equals(PdfName.WIDGET)) {
                            }
                            PdfNumber asNumber = pdfDictionary.getAsNumber(PdfName.f580F);
                            int intValue = asNumber != null ? asNumber.intValue() : 0;
                            if (!((intValue & 4) == 0 || (intValue & 2) != 0 || (pdfObject = pdfDictionary.get(PdfName.f549AP)) == null)) {
                                if (pdfObject instanceof PdfIndirectReference) {
                                    pdfObject = PdfReader.getPdfObject(pdfObject);
                                }
                                PdfDictionary pdfDictionary2 = (PdfDictionary) pdfObject;
                                PdfObject pdfObject2 = pdfDictionary2.get(PdfName.f610N);
                                PdfStream asStream = pdfDictionary2.getAsStream(PdfName.f610N);
                                PdfAppearance pdfAppearance = null;
                                PdfObject pdfObject3 = PdfReader.getPdfObject(pdfObject2);
                                if ((pdfObject2 instanceof PdfIndirectReference) && !pdfObject2.isIndirect()) {
                                    pdfAppearance = new PdfAppearance((PdfIndirectReference) pdfObject2);
                                } else if (pdfObject3 instanceof PdfStream) {
                                    ((PdfDictionary) pdfObject3).put(PdfName.SUBTYPE, PdfName.FORM);
                                    pdfAppearance = new PdfAppearance((PdfIndirectReference) pdfObject2);
                                } else if (!(!pdfObject3.isDictionary() || (asName = pdfDictionary2.getAsName(PdfName.f550AS)) == null || (pdfIndirectReference = (PdfIndirectReference) ((PdfDictionary) pdfObject3).get(asName)) == null)) {
                                    pdfAppearance = new PdfAppearance(pdfIndirectReference);
                                    if (pdfIndirectReference.isIndirect()) {
                                        ((PdfDictionary) PdfReader.getPdfObject((PdfObject) pdfIndirectReference)).put(PdfName.SUBTYPE, PdfName.FORM);
                                    }
                                }
                                PdfAppearance pdfAppearance2 = pdfAppearance;
                                if (pdfAppearance2 != null) {
                                    Rectangle normalizedRectangle = PdfReader.getNormalizedRectangle(pdfDictionary.getAsArray(PdfName.RECT));
                                    Rectangle normalizedRectangle2 = PdfReader.getNormalizedRectangle(asStream.getAsArray(PdfName.BBOX));
                                    PdfContentByte overContent = getOverContent(i);
                                    overContent.setLiteral("Q ");
                                    if (asStream.getAsArray(PdfName.MATRIX) == null || Arrays.equals(this.DEFAULT_MATRIX, asStream.getAsArray(PdfName.MATRIX).asDoubleArray())) {
                                        overContent.addTemplate((PdfTemplate) pdfAppearance2, normalizedRectangle.getWidth() / normalizedRectangle2.getWidth(), 0.0f, 0.0f, normalizedRectangle.getHeight() / normalizedRectangle2.getHeight(), normalizedRectangle.getLeft(), normalizedRectangle.getBottom());
                                    } else {
                                        Rectangle transformBBoxByMatrix = transformBBoxByMatrix(normalizedRectangle2, asStream.getAsArray(PdfName.MATRIX).asDoubleArray());
                                        overContent.addTemplate((PdfTemplate) pdfAppearance2, normalizedRectangle.getWidth() / transformBBoxByMatrix.getWidth(), 0.0f, 0.0f, normalizedRectangle.getHeight() / transformBBoxByMatrix.getHeight(), normalizedRectangle.getLeft(), normalizedRectangle.getBottom());
                                    }
                                    overContent.setLiteral("q ");
                                    asArray.remove(i2);
                                    i2--;
                                }
                            }
                        }
                        i2++;
                    }
                    if (asArray.isEmpty()) {
                        PdfReader.killIndirect(pageN.get(PdfName.ANNOTS));
                        pageN.remove(PdfName.ANNOTS);
                    }
                }
            }
        } else if (z) {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("freetext.flattening.is.not.supported.in.append.mode", new Object[0]));
        } else {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("annotation.flattening.is.not.supported.in.append.mode", new Object[0]));
        }
    }

    private Rectangle transformBBoxByMatrix(Rectangle rectangle, double[] dArr) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        double[] dArr2 = dArr;
        Point transformPoint = transformPoint((double) rectangle.getLeft(), (double) rectangle.getBottom(), dArr2);
        arrayList.add(Double.valueOf(transformPoint.f387x));
        arrayList2.add(Double.valueOf(transformPoint.f388y));
        Point transformPoint2 = transformPoint((double) rectangle.getRight(), (double) rectangle.getTop(), dArr2);
        arrayList.add(Double.valueOf(transformPoint2.f387x));
        arrayList2.add(Double.valueOf(transformPoint2.f388y));
        Point transformPoint3 = transformPoint((double) rectangle.getLeft(), (double) rectangle.getTop(), dArr2);
        arrayList.add(Double.valueOf(transformPoint3.f387x));
        arrayList2.add(Double.valueOf(transformPoint3.f388y));
        Point transformPoint4 = transformPoint((double) rectangle.getRight(), (double) rectangle.getBottom(), dArr2);
        arrayList.add(Double.valueOf(transformPoint4.f387x));
        arrayList2.add(Double.valueOf(transformPoint4.f388y));
        return new Rectangle(((Double) Collections.min(arrayList)).floatValue(), ((Double) Collections.min(arrayList2)).floatValue(), ((Double) Collections.max(arrayList)).floatValue(), ((Double) Collections.max(arrayList2)).floatValue());
    }

    private Point transformPoint(double d, double d2, double[] dArr) {
        Point point = new Point();
        point.f387x = (dArr[0] * d) + (dArr[2] * d2) + dArr[4];
        point.f388y = (dArr[1] * d) + (dArr[3] * d2) + dArr[5];
        return point;
    }

    /* access modifiers changed from: protected */
    public void flatFreeTextFields() {
        flattenAnnotations(true);
    }

    public PdfIndirectReference getPageReference(int i) {
        PRIndirectReference pageOrigRef = this.reader.getPageOrigRef(i);
        if (pageOrigRef != null) {
            return pageOrigRef;
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.page.number.1", i));
    }

    public void addAnnotation(PdfAnnotation pdfAnnotation) {
        throw new RuntimeException(MessageLocalization.getComposedMessage("unsupported.in.this.context.use.pdfstamper.addannotation", new Object[0]));
    }

    /* access modifiers changed from: package-private */
    public void addDocumentField(PdfIndirectReference pdfIndirectReference) {
        PdfDictionary catalog = this.reader.getCatalog();
        PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObject(catalog.get(PdfName.ACROFORM), catalog);
        if (pdfDictionary == null) {
            pdfDictionary = new PdfDictionary();
            catalog.put(PdfName.ACROFORM, pdfDictionary);
            markUsed((PdfObject) catalog);
        }
        PdfArray pdfArray = (PdfArray) PdfReader.getPdfObject(pdfDictionary.get(PdfName.FIELDS), pdfDictionary);
        if (pdfArray == null) {
            pdfArray = new PdfArray();
            pdfDictionary.put(PdfName.FIELDS, pdfArray);
            markUsed((PdfObject) pdfDictionary);
        }
        if (!pdfDictionary.contains(PdfName.f568DA)) {
            pdfDictionary.put(PdfName.f568DA, new PdfString("/Helv 0 Tf 0 g "));
            markUsed((PdfObject) pdfDictionary);
        }
        pdfArray.add((PdfObject) pdfIndirectReference);
        markUsed((PdfObject) pdfArray);
    }

    /* access modifiers changed from: protected */
    public void addFieldResources() throws IOException {
        if (!this.fieldTemplates.isEmpty()) {
            PdfDictionary catalog = this.reader.getCatalog();
            PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObject(catalog.get(PdfName.ACROFORM), catalog);
            if (pdfDictionary == null) {
                pdfDictionary = new PdfDictionary();
                catalog.put(PdfName.ACROFORM, pdfDictionary);
                markUsed((PdfObject) catalog);
            }
            PdfDictionary pdfDictionary2 = (PdfDictionary) PdfReader.getPdfObject(pdfDictionary.get(PdfName.f574DR), pdfDictionary);
            if (pdfDictionary2 == null) {
                pdfDictionary2 = new PdfDictionary();
                pdfDictionary.put(PdfName.f574DR, pdfDictionary2);
                markUsed((PdfObject) pdfDictionary);
            }
            markUsed((PdfObject) pdfDictionary2);
            Iterator<PdfTemplate> it = this.fieldTemplates.iterator();
            while (it.hasNext()) {
                PdfFormField.mergeResources(pdfDictionary2, (PdfDictionary) it.next().getResources(), this);
            }
            PdfDictionary asDict = pdfDictionary2.getAsDict(PdfName.FONT);
            if (asDict == null) {
                asDict = new PdfDictionary();
                pdfDictionary2.put(PdfName.FONT, asDict);
            }
            if (!asDict.contains(PdfName.HELV)) {
                PdfDictionary pdfDictionary3 = new PdfDictionary(PdfName.FONT);
                pdfDictionary3.put(PdfName.BASEFONT, PdfName.HELVETICA);
                pdfDictionary3.put(PdfName.ENCODING, PdfName.WIN_ANSI_ENCODING);
                pdfDictionary3.put(PdfName.NAME, PdfName.HELV);
                pdfDictionary3.put(PdfName.SUBTYPE, PdfName.TYPE1);
                asDict.put(PdfName.HELV, addToBody(pdfDictionary3).getIndirectReference());
            }
            if (!asDict.contains(PdfName.ZADB)) {
                PdfDictionary pdfDictionary4 = new PdfDictionary(PdfName.FONT);
                pdfDictionary4.put(PdfName.BASEFONT, PdfName.ZAPFDINGBATS);
                pdfDictionary4.put(PdfName.NAME, PdfName.ZADB);
                pdfDictionary4.put(PdfName.SUBTYPE, PdfName.TYPE1);
                asDict.put(PdfName.ZADB, addToBody(pdfDictionary4).getIndirectReference());
            }
            if (pdfDictionary.get(PdfName.f568DA) == null) {
                pdfDictionary.put(PdfName.f568DA, new PdfString("/Helv 0 Tf 0 g "));
                markUsed((PdfObject) pdfDictionary);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void expandFields(PdfFormField pdfFormField, ArrayList<PdfAnnotation> arrayList) {
        arrayList.add(pdfFormField);
        ArrayList<PdfFormField> kids = pdfFormField.getKids();
        if (kids != null) {
            for (int i = 0; i < kids.size(); i++) {
                expandFields(kids.get(i), arrayList);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00d9 A[Catch:{ IOException -> 0x016f }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0136 A[Catch:{ IOException -> 0x016f }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addAnnotation(com.itextpdf.text.pdf.PdfAnnotation r11, com.itextpdf.text.pdf.PdfDictionary r12) {
        /*
            r10 = this;
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ IOException -> 0x016f }
            r0.<init>()     // Catch:{ IOException -> 0x016f }
            boolean r1 = r11.isForm()     // Catch:{ IOException -> 0x016f }
            if (r1 == 0) goto L_0x001e
            r1 = 1
            r10.fieldsAdded = r1     // Catch:{ IOException -> 0x016f }
            r10.getAcroFields()     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfFormField r11 = (com.itextpdf.text.pdf.PdfFormField) r11     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfFormField r1 = r11.getParent()     // Catch:{ IOException -> 0x016f }
            if (r1 == 0) goto L_0x001a
            return
        L_0x001a:
            r10.expandFields(r11, r0)     // Catch:{ IOException -> 0x016f }
            goto L_0x0021
        L_0x001e:
            r0.add(r11)     // Catch:{ IOException -> 0x016f }
        L_0x0021:
            r11 = 0
        L_0x0022:
            int r1 = r0.size()     // Catch:{ IOException -> 0x016f }
            if (r11 >= r1) goto L_0x016e
            java.lang.Object r1 = r0.get(r11)     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfAnnotation r1 = (com.itextpdf.text.pdf.PdfAnnotation) r1     // Catch:{ IOException -> 0x016f }
            int r2 = r1.getPlaceInPage()     // Catch:{ IOException -> 0x016f }
            if (r2 <= 0) goto L_0x003e
            com.itextpdf.text.pdf.PdfReader r12 = r10.reader     // Catch:{ IOException -> 0x016f }
            int r2 = r1.getPlaceInPage()     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfDictionary r12 = r12.getPageN(r2)     // Catch:{ IOException -> 0x016f }
        L_0x003e:
            boolean r2 = r1.isForm()     // Catch:{ IOException -> 0x016f }
            if (r2 == 0) goto L_0x0065
            boolean r2 = r1.isUsed()     // Catch:{ IOException -> 0x016f }
            if (r2 != 0) goto L_0x0055
            java.util.HashSet r2 = r1.getTemplates()     // Catch:{ IOException -> 0x016f }
            if (r2 == 0) goto L_0x0055
            java.util.HashSet<com.itextpdf.text.pdf.PdfTemplate> r3 = r10.fieldTemplates     // Catch:{ IOException -> 0x016f }
            r3.addAll(r2)     // Catch:{ IOException -> 0x016f }
        L_0x0055:
            r2 = r1
            com.itextpdf.text.pdf.PdfFormField r2 = (com.itextpdf.text.pdf.PdfFormField) r2     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfFormField r3 = r2.getParent()     // Catch:{ IOException -> 0x016f }
            if (r3 != 0) goto L_0x0065
            com.itextpdf.text.pdf.PdfIndirectReference r2 = r2.getIndirectReference()     // Catch:{ IOException -> 0x016f }
            r10.addDocumentField(r2)     // Catch:{ IOException -> 0x016f }
        L_0x0065:
            boolean r2 = r1.isAnnotation()     // Catch:{ IOException -> 0x016f }
            if (r2 == 0) goto L_0x015a
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.ANNOTS     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfObject r2 = r12.get(r2)     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfObject r2 = com.itextpdf.text.pdf.PdfReader.getPdfObject(r2, r12)     // Catch:{ IOException -> 0x016f }
            if (r2 == 0) goto L_0x0081
            boolean r3 = r2.isArray()     // Catch:{ IOException -> 0x016f }
            if (r3 != 0) goto L_0x007e
            goto L_0x0081
        L_0x007e:
            com.itextpdf.text.pdf.PdfArray r2 = (com.itextpdf.text.pdf.PdfArray) r2     // Catch:{ IOException -> 0x016f }
            goto L_0x008e
        L_0x0081:
            com.itextpdf.text.pdf.PdfArray r2 = new com.itextpdf.text.pdf.PdfArray     // Catch:{ IOException -> 0x016f }
            r2.<init>()     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.ANNOTS     // Catch:{ IOException -> 0x016f }
            r12.put(r3, r2)     // Catch:{ IOException -> 0x016f }
            r10.markUsed((com.itextpdf.text.pdf.PdfObject) r12)     // Catch:{ IOException -> 0x016f }
        L_0x008e:
            com.itextpdf.text.pdf.PdfIndirectReference r3 = r1.getIndirectReference()     // Catch:{ IOException -> 0x016f }
            r2.add((com.itextpdf.text.pdf.PdfObject) r3)     // Catch:{ IOException -> 0x016f }
            r10.markUsed((com.itextpdf.text.pdf.PdfObject) r2)     // Catch:{ IOException -> 0x016f }
            boolean r2 = r1.isUsed()     // Catch:{ IOException -> 0x016f }
            if (r2 != 0) goto L_0x015a
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.RECT     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfObject r2 = r1.get(r2)     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfRectangle r2 = (com.itextpdf.text.pdf.PdfRectangle) r2     // Catch:{ IOException -> 0x016f }
            if (r2 == 0) goto L_0x015a
            float r3 = r2.left()     // Catch:{ IOException -> 0x016f }
            r4 = 0
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 != 0) goto L_0x00c9
            float r3 = r2.right()     // Catch:{ IOException -> 0x016f }
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 != 0) goto L_0x00c9
            float r3 = r2.top()     // Catch:{ IOException -> 0x016f }
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 != 0) goto L_0x00c9
            float r3 = r2.bottom()     // Catch:{ IOException -> 0x016f }
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x015a
        L_0x00c9:
            com.itextpdf.text.pdf.PdfReader r3 = r10.reader     // Catch:{ IOException -> 0x016f }
            int r3 = r3.getPageRotation((com.itextpdf.text.pdf.PdfDictionary) r12)     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfReader r4 = r10.reader     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.Rectangle r4 = r4.getPageSizeWithRotation((com.itextpdf.text.pdf.PdfDictionary) r12)     // Catch:{ IOException -> 0x016f }
            r5 = 90
            if (r3 == r5) goto L_0x0136
            r5 = 180(0xb4, float:2.52E-43)
            if (r3 == r5) goto L_0x0107
            r5 = 270(0x10e, float:3.78E-43)
            if (r3 == r5) goto L_0x00e2
            goto L_0x015a
        L_0x00e2:
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.RECT     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfRectangle r5 = new com.itextpdf.text.pdf.PdfRectangle     // Catch:{ IOException -> 0x016f }
            float r6 = r2.bottom()     // Catch:{ IOException -> 0x016f }
            float r7 = r4.getRight()     // Catch:{ IOException -> 0x016f }
            float r8 = r2.left()     // Catch:{ IOException -> 0x016f }
            float r7 = r7 - r8
            float r8 = r2.top()     // Catch:{ IOException -> 0x016f }
            float r4 = r4.getRight()     // Catch:{ IOException -> 0x016f }
            float r2 = r2.right()     // Catch:{ IOException -> 0x016f }
            float r4 = r4 - r2
            r5.<init>(r6, r7, r8, r4)     // Catch:{ IOException -> 0x016f }
            r1.put(r3, r5)     // Catch:{ IOException -> 0x016f }
            goto L_0x015a
        L_0x0107:
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.RECT     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfRectangle r5 = new com.itextpdf.text.pdf.PdfRectangle     // Catch:{ IOException -> 0x016f }
            float r6 = r4.getRight()     // Catch:{ IOException -> 0x016f }
            float r7 = r2.left()     // Catch:{ IOException -> 0x016f }
            float r6 = r6 - r7
            float r7 = r4.getTop()     // Catch:{ IOException -> 0x016f }
            float r8 = r2.bottom()     // Catch:{ IOException -> 0x016f }
            float r7 = r7 - r8
            float r8 = r4.getRight()     // Catch:{ IOException -> 0x016f }
            float r9 = r2.right()     // Catch:{ IOException -> 0x016f }
            float r8 = r8 - r9
            float r4 = r4.getTop()     // Catch:{ IOException -> 0x016f }
            float r2 = r2.top()     // Catch:{ IOException -> 0x016f }
            float r4 = r4 - r2
            r5.<init>(r6, r7, r8, r4)     // Catch:{ IOException -> 0x016f }
            r1.put(r3, r5)     // Catch:{ IOException -> 0x016f }
            goto L_0x015a
        L_0x0136:
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.RECT     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfRectangle r5 = new com.itextpdf.text.pdf.PdfRectangle     // Catch:{ IOException -> 0x016f }
            float r6 = r4.getTop()     // Catch:{ IOException -> 0x016f }
            float r7 = r2.top()     // Catch:{ IOException -> 0x016f }
            float r6 = r6 - r7
            float r7 = r2.right()     // Catch:{ IOException -> 0x016f }
            float r4 = r4.getTop()     // Catch:{ IOException -> 0x016f }
            float r8 = r2.bottom()     // Catch:{ IOException -> 0x016f }
            float r4 = r4 - r8
            float r2 = r2.left()     // Catch:{ IOException -> 0x016f }
            r5.<init>(r6, r7, r4, r2)     // Catch:{ IOException -> 0x016f }
            r1.put(r3, r5)     // Catch:{ IOException -> 0x016f }
        L_0x015a:
            boolean r2 = r1.isUsed()     // Catch:{ IOException -> 0x016f }
            if (r2 != 0) goto L_0x016a
            r1.setUsed()     // Catch:{ IOException -> 0x016f }
            com.itextpdf.text.pdf.PdfIndirectReference r2 = r1.getIndirectReference()     // Catch:{ IOException -> 0x016f }
            r10.addToBody((com.itextpdf.text.pdf.PdfObject) r1, (com.itextpdf.text.pdf.PdfIndirectReference) r2)     // Catch:{ IOException -> 0x016f }
        L_0x016a:
            int r11 = r11 + 1
            goto L_0x0022
        L_0x016e:
            return
        L_0x016f:
            r11 = move-exception
            com.itextpdf.text.ExceptionConverter r12 = new com.itextpdf.text.ExceptionConverter
            r12.<init>(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfStamperImp.addAnnotation(com.itextpdf.text.pdf.PdfAnnotation, com.itextpdf.text.pdf.PdfDictionary):void");
    }

    /* access modifiers changed from: package-private */
    public void addAnnotation(PdfAnnotation pdfAnnotation, int i) {
        if (pdfAnnotation.isAnnotation()) {
            pdfAnnotation.setPage(i);
        }
        addAnnotation(pdfAnnotation, this.reader.getPageN(i));
    }

    private void outlineTravel(PRIndirectReference pRIndirectReference) {
        while (pRIndirectReference != null) {
            PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObjectRelease((PdfObject) pRIndirectReference);
            PRIndirectReference pRIndirectReference2 = (PRIndirectReference) pdfDictionary.get(PdfName.FIRST);
            if (pRIndirectReference2 != null) {
                outlineTravel(pRIndirectReference2);
            }
            PdfReader.killIndirect(pdfDictionary.get(PdfName.DEST));
            PdfReader.killIndirect(pdfDictionary.get(PdfName.f545A));
            PdfReader.killIndirect(pRIndirectReference);
            pRIndirectReference = (PRIndirectReference) pdfDictionary.get(PdfName.NEXT);
        }
    }

    /* access modifiers changed from: package-private */
    public void deleteOutlines() {
        PdfDictionary catalog = this.reader.getCatalog();
        PdfObject pdfObject = catalog.get(PdfName.OUTLINES);
        if (pdfObject != null) {
            if (pdfObject instanceof PRIndirectReference) {
                PRIndirectReference pRIndirectReference = (PRIndirectReference) pdfObject;
                outlineTravel(pRIndirectReference);
                PdfReader.killIndirect(pRIndirectReference);
            }
            catalog.remove(PdfName.OUTLINES);
            markUsed((PdfObject) catalog);
        }
    }

    /* access modifiers changed from: protected */
    public void setJavaScript() throws IOException {
        HashMap<String, PdfObject> documentLevelJS = this.pdf.getDocumentLevelJS();
        if (!documentLevelJS.isEmpty()) {
            PdfDictionary catalog = this.reader.getCatalog();
            PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObject(catalog.get(PdfName.NAMES), catalog);
            if (pdfDictionary == null) {
                pdfDictionary = new PdfDictionary();
                catalog.put(PdfName.NAMES, pdfDictionary);
                markUsed((PdfObject) catalog);
            }
            markUsed((PdfObject) pdfDictionary);
            pdfDictionary.put(PdfName.JAVASCRIPT, addToBody(PdfNameTree.writeTree(documentLevelJS, this)).getIndirectReference());
        }
    }

    /* access modifiers changed from: protected */
    public void addFileAttachments() throws IOException {
        HashMap<String, PdfObject> documentFileAttachment = this.pdf.getDocumentFileAttachment();
        if (!documentFileAttachment.isEmpty()) {
            PdfDictionary catalog = this.reader.getCatalog();
            PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObject(catalog.get(PdfName.NAMES), catalog);
            if (pdfDictionary == null) {
                pdfDictionary = new PdfDictionary();
                catalog.put(PdfName.NAMES, pdfDictionary);
                markUsed((PdfObject) catalog);
            }
            markUsed((PdfObject) pdfDictionary);
            HashMap<String, PdfObject> readTree = PdfNameTree.readTree((PdfDictionary) PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.EMBEDDEDFILES)));
            for (Map.Entry next : documentFileAttachment.entrySet()) {
                int i = 0;
                StringBuilder sb = new StringBuilder((String) next.getKey());
                while (readTree.containsKey(sb.toString())) {
                    i++;
                    sb.append(" ");
                    sb.append(i);
                }
                readTree.put(sb.toString(), next.getValue());
            }
            PdfDictionary writeTree = PdfNameTree.writeTree(readTree, this);
            PdfObject pdfObject = pdfDictionary.get(PdfName.EMBEDDEDFILES);
            if (pdfObject != null) {
                PdfReader.killIndirect(pdfObject);
            }
            pdfDictionary.put(PdfName.EMBEDDEDFILES, addToBody(writeTree).getIndirectReference());
        }
    }

    /* access modifiers changed from: package-private */
    public void makePackage(PdfCollection pdfCollection) {
        this.reader.getCatalog().put(PdfName.COLLECTION, pdfCollection);
    }

    /* access modifiers changed from: protected */
    public void setOutlines() throws IOException {
        if (this.newBookmarks != null) {
            deleteOutlines();
            if (!this.newBookmarks.isEmpty()) {
                PdfDictionary catalog = this.reader.getCatalog();
                writeOutlines(catalog, catalog.get(PdfName.DESTS) != null);
                markUsed((PdfObject) catalog);
            }
        }
    }

    public void setViewerPreferences(int i) {
        this.useVp = true;
        this.viewerPreferences.setViewerPreferences(i);
    }

    public void addViewerPreference(PdfName pdfName, PdfObject pdfObject) {
        this.useVp = true;
        this.viewerPreferences.addViewerPreference(pdfName, pdfObject);
    }

    public void setSigFlags(int i) {
        this.sigFlags = i | this.sigFlags;
    }

    public void setPageAction(PdfName pdfName, PdfAction pdfAction) throws PdfException {
        throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("use.setpageaction.pdfname.actiontype.pdfaction.action.int.page", new Object[0]));
    }

    /* access modifiers changed from: package-private */
    public void setPageAction(PdfName pdfName, PdfAction pdfAction, int i) throws PdfException {
        if (pdfName.equals(PAGE_OPEN) || pdfName.equals(PAGE_CLOSE)) {
            PdfDictionary pageN = this.reader.getPageN(i);
            PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObject(pageN.get(PdfName.f546AA), pageN);
            if (pdfDictionary == null) {
                pdfDictionary = new PdfDictionary();
                pageN.put(PdfName.f546AA, pdfDictionary);
                markUsed((PdfObject) pageN);
            }
            pdfDictionary.put(pdfName, pdfAction);
            markUsed((PdfObject) pdfDictionary);
            return;
        }
        throw new PdfException(MessageLocalization.getComposedMessage("invalid.page.additional.action.type.1", pdfName.toString()));
    }

    public void setDuration(int i) {
        throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("use.setpageaction.pdfname.actiontype.pdfaction.action.int.page", new Object[0]));
    }

    public void setTransition(PdfTransition pdfTransition) {
        throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("use.setpageaction.pdfname.actiontype.pdfaction.action.int.page", new Object[0]));
    }

    /* access modifiers changed from: package-private */
    public void setDuration(int i, int i2) {
        PdfDictionary pageN = this.reader.getPageN(i2);
        if (i < 0) {
            pageN.remove(PdfName.DUR);
        } else {
            pageN.put(PdfName.DUR, new PdfNumber(i));
        }
        markUsed((PdfObject) pageN);
    }

    /* access modifiers changed from: package-private */
    public void setTransition(PdfTransition pdfTransition, int i) {
        PdfDictionary pageN = this.reader.getPageN(i);
        if (pdfTransition == null) {
            pageN.remove(PdfName.TRANS);
        } else {
            pageN.put(PdfName.TRANS, pdfTransition.getTransitionDictionary());
        }
        markUsed((PdfObject) pageN);
    }

    /* access modifiers changed from: protected */
    public void markUsed(PdfObject pdfObject) {
        PRIndirectReference pRIndirectReference;
        if (this.append && pdfObject != null) {
            if (pdfObject.type() == 10) {
                pRIndirectReference = (PRIndirectReference) pdfObject;
            } else {
                pRIndirectReference = pdfObject.getIndRef();
            }
            if (pRIndirectReference != null) {
                this.marked.put(pRIndirectReference.getNumber(), 1);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void markUsed(int i) {
        if (this.append) {
            this.marked.put(i, 1);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isAppend() {
        return this.append;
    }

    public PdfReader getPdfReader() {
        return this.reader;
    }

    public void setAdditionalAction(PdfName pdfName, PdfAction pdfAction) throws PdfException {
        if (pdfName.equals(DOCUMENT_CLOSE) || pdfName.equals(WILL_SAVE) || pdfName.equals(DID_SAVE) || pdfName.equals(WILL_PRINT) || pdfName.equals(DID_PRINT)) {
            PdfDictionary asDict = this.reader.getCatalog().getAsDict(PdfName.f546AA);
            if (asDict == null) {
                if (pdfAction != null) {
                    asDict = new PdfDictionary();
                    this.reader.getCatalog().put(PdfName.f546AA, asDict);
                } else {
                    return;
                }
            }
            markUsed((PdfObject) asDict);
            if (pdfAction == null) {
                asDict.remove(pdfName);
            } else {
                asDict.put(pdfName, pdfAction);
            }
        } else {
            throw new PdfException(MessageLocalization.getComposedMessage("invalid.additional.action.type.1", pdfName.toString()));
        }
    }

    public void setOpenAction(PdfAction pdfAction) {
        this.openAction = pdfAction;
    }

    public void setOpenAction(String str) {
        throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("open.actions.by.name.are.not.supported", new Object[0]));
    }

    public void setThumbnail(Image image) {
        throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("use.pdfstamper.setthumbnail", new Object[0]));
    }

    /* access modifiers changed from: package-private */
    public void setThumbnail(Image image, int i) throws PdfException, DocumentException {
        PdfIndirectReference imageReference = getImageReference(addDirectImageSimple(image));
        this.reader.resetReleasePage();
        this.reader.getPageN(i).put(PdfName.THUMB, imageReference);
        this.reader.resetReleasePage();
    }

    public PdfContentByte getDirectContentUnder() {
        throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("use.pdfstamper.getundercontent.or.pdfstamper.getovercontent", new Object[0]));
    }

    public PdfContentByte getDirectContent() {
        throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("use.pdfstamper.getundercontent.or.pdfstamper.getovercontent", new Object[0]));
    }

    /* access modifiers changed from: protected */
    public void readOCProperties() {
        PdfDictionary asDict;
        if (this.documentOCG.isEmpty() && (asDict = this.reader.getCatalog().getAsDict(PdfName.OCPROPERTIES)) != null) {
            PdfArray asArray = asDict.getAsArray(PdfName.OCGS);
            HashMap hashMap = new HashMap();
            ListIterator<PdfObject> listIterator = asArray.listIterator();
            while (listIterator.hasNext()) {
                PdfIndirectReference pdfIndirectReference = (PdfIndirectReference) listIterator.next();
                PdfLayer pdfLayer = new PdfLayer((String) null);
                pdfLayer.setRef(pdfIndirectReference);
                pdfLayer.setOnPanel(false);
                pdfLayer.merge((PdfDictionary) PdfReader.getPdfObject((PdfObject) pdfIndirectReference));
                hashMap.put(pdfIndirectReference.toString(), pdfLayer);
            }
            PdfDictionary asDict2 = asDict.getAsDict(PdfName.f567D);
            PdfArray asArray2 = asDict2.getAsArray(PdfName.OFF);
            if (asArray2 != null) {
                ListIterator<PdfObject> listIterator2 = asArray2.listIterator();
                while (listIterator2.hasNext()) {
                    ((PdfLayer) hashMap.get(((PdfIndirectReference) listIterator2.next()).toString())).setOn(false);
                }
            }
            PdfArray asArray3 = asDict2.getAsArray(PdfName.ORDER);
            if (asArray3 != null) {
                addOrder((PdfLayer) null, asArray3, hashMap);
            }
            this.documentOCG.addAll(hashMap.values());
            this.OCGRadioGroup = asDict2.getAsArray(PdfName.RBGROUPS);
            if (this.OCGRadioGroup == null) {
                this.OCGRadioGroup = new PdfArray();
            }
            this.OCGLocked = asDict2.getAsArray(PdfName.LOCKED);
            if (this.OCGLocked == null) {
                this.OCGLocked = new PdfArray();
            }
        }
    }

    private void addOrder(PdfLayer pdfLayer, PdfArray pdfArray, Map<String, PdfLayer> map) {
        int i = 0;
        while (i < pdfArray.size()) {
            PdfObject pdfObject = pdfArray.getPdfObject(i);
            if (pdfObject.isIndirect()) {
                PdfLayer pdfLayer2 = map.get(pdfObject.toString());
                if (pdfLayer2 != null) {
                    pdfLayer2.setOnPanel(true);
                    registerLayer(pdfLayer2);
                    if (pdfLayer != null) {
                        pdfLayer.addChild(pdfLayer2);
                    }
                    int i2 = i + 1;
                    if (pdfArray.size() > i2 && pdfArray.getPdfObject(i2).isArray()) {
                        addOrder(pdfLayer2, (PdfArray) pdfArray.getPdfObject(i2), map);
                        i = i2;
                    }
                }
            } else if (pdfObject.isArray()) {
                PdfArray pdfArray2 = (PdfArray) pdfObject;
                if (!pdfArray2.isEmpty()) {
                    PdfObject pdfObject2 = pdfArray2.getPdfObject(0);
                    if (pdfObject2.isString()) {
                        PdfLayer pdfLayer3 = new PdfLayer(pdfObject2.toString());
                        pdfLayer3.setOnPanel(true);
                        registerLayer(pdfLayer3);
                        if (pdfLayer != null) {
                            pdfLayer.addChild(pdfLayer3);
                        }
                        PdfArray pdfArray3 = new PdfArray();
                        ListIterator<PdfObject> listIterator = pdfArray2.listIterator();
                        while (listIterator.hasNext()) {
                            pdfArray3.add(listIterator.next());
                        }
                        addOrder(pdfLayer3, pdfArray3, map);
                    } else {
                        addOrder(pdfLayer, (PdfArray) pdfObject2, map);
                    }
                } else {
                    return;
                }
            } else {
                continue;
            }
            i++;
        }
    }

    public Map<String, PdfLayer> getPdfLayers() {
        String str;
        if (!this.originalLayersAreRead) {
            this.originalLayersAreRead = true;
            readOCProperties();
        }
        HashMap hashMap = new HashMap();
        Iterator it = this.documentOCG.iterator();
        while (it.hasNext()) {
            PdfLayer pdfLayer = (PdfLayer) ((PdfOCG) it.next());
            if (pdfLayer.getTitle() == null) {
                str = pdfLayer.getAsString(PdfName.NAME).toString();
            } else {
                str = pdfLayer.getTitle();
            }
            if (hashMap.containsKey(str)) {
                int i = 2;
                String str2 = str + "(" + 2 + ")";
                while (hashMap.containsKey(str2)) {
                    i++;
                    str2 = str + "(" + i + ")";
                }
                str = str2;
            }
            hashMap.put(str, pdfLayer);
        }
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    public void registerLayer(PdfOCG pdfOCG) {
        if (!this.originalLayersAreRead) {
            this.originalLayersAreRead = true;
            readOCProperties();
        }
        super.registerLayer(pdfOCG);
    }

    public void createXmpMetadata() {
        try {
            this.xmpWriter = createXmpWriter((ByteArrayOutputStream) null, this.reader.getInfo());
            this.xmpMetadata = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public HashMap<Object, PdfObject> getNamedDestinations() {
        return this.namedDestinations;
    }

    /* access modifiers changed from: protected */
    public void updateNamedDestinations() throws IOException {
        PdfDictionary asDict = this.reader.getCatalog().getAsDict(PdfName.NAMES);
        if (asDict != null) {
            asDict = asDict.getAsDict(PdfName.DESTS);
        }
        if (asDict == null) {
            asDict = this.reader.getCatalog().getAsDict(PdfName.DESTS);
        }
        if (asDict == null) {
            asDict = new PdfDictionary();
            PdfDictionary pdfDictionary = new PdfDictionary();
            asDict.put(PdfName.NAMES, new PdfArray());
            pdfDictionary.put(PdfName.DESTS, asDict);
            this.reader.getCatalog().put(PdfName.NAMES, pdfDictionary);
        }
        PdfArray lastChildInNameTree = getLastChildInNameTree(asDict);
        for (Object next : this.namedDestinations.keySet()) {
            lastChildInNameTree.add((PdfObject) new PdfString(next.toString()));
            lastChildInNameTree.add((PdfObject) addToBody(this.namedDestinations.get(next), getPdfIndirectReference()).getIndirectReference());
        }
    }

    private PdfArray getLastChildInNameTree(PdfDictionary pdfDictionary) {
        PdfArray asArray = pdfDictionary.getAsArray(PdfName.KIDS);
        if (asArray != null) {
            return getLastChildInNameTree(asArray.getAsDict(asArray.size() - 1));
        }
        return pdfDictionary.getAsArray(PdfName.NAMES);
    }

    static class PageStamp {
        StampContent over;
        PdfDictionary pageN;
        PageResources pageResources;
        int replacePoint = 0;
        StampContent under;

        PageStamp(PdfStamperImp pdfStamperImp, PdfReader pdfReader, PdfDictionary pdfDictionary) {
            this.pageN = pdfDictionary;
            this.pageResources = new PageResources();
            this.pageResources.setOriginalResources(pdfDictionary.getAsDict(PdfName.RESOURCES), pdfStamperImp.namePtr);
        }
    }
}
