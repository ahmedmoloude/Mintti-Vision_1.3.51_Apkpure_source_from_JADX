package com.itextpdf.text.pdf;

import com.itextpdf.text.DocWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.exceptions.InvalidPdfException;
import com.itextpdf.text.exceptions.UnsupportedPdfException;
import com.itextpdf.text.log.Counter;
import com.itextpdf.text.log.CounterFactory;
import com.itextpdf.text.log.Level;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.p018io.RandomAccessSource;
import com.itextpdf.text.p018io.RandomAccessSourceFactory;
import com.itextpdf.text.p018io.WindowRandomAccessSource;
import com.itextpdf.text.pdf.FilterHandlers;
import com.itextpdf.text.pdf.IntHashtable;
import com.itextpdf.text.pdf.PRTokeniser;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.interfaces.PdfViewerPreferences;
import com.itextpdf.text.pdf.internal.PdfViewerPreferencesImp;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDecryptionProcess;
import com.p020kl.healthmonitor.C1633R2;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.InflaterInputStream;
import kotlin.UByte;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.RecipientInformation;

public class PdfReader implements PdfViewerPreferences {
    protected static Counter COUNTER = null;
    private static final Logger LOGGER;
    public static boolean debugmode = false;
    static final byte[] endobj = PdfEncodings.convertToBytes("endobj", (String) null);
    static final byte[] endstream = PdfEncodings.convertToBytes("endstream", (String) null);
    static final PdfName[] pageInhCandidates = {PdfName.MEDIABOX, PdfName.ROTATE, PdfName.RESOURCES, PdfName.CROPBOX};
    public static boolean unethicalreading = false;
    protected PRAcroForm acroForm;
    protected boolean acroFormParsed;
    private boolean appendable;
    protected PdfDictionary catalog;
    protected Certificate certificate;
    protected Key certificateKey;
    protected String certificateKeyProvider;
    protected boolean consolidateNamedDestinations;
    private PRIndirectReference cryptoRef;
    protected PdfEncryption decrypt;
    protected boolean encrypted;
    private boolean encryptionError;
    protected long eofPos;
    protected ExternalDecryptionProcess externalDecryptionProcess;
    private long fileLength;
    protected int freeXref;
    private boolean hybridXref;
    protected long lastXref;
    /* access modifiers changed from: private */
    public int lastXrefPartial;
    protected boolean newXrefType;
    private int objGen;
    private int objNum;
    protected HashMap<Integer, IntHashtable> objStmMark;
    protected LongHashtable objStmToOffset;
    private boolean ownerPasswordUsed;
    protected long pValue;
    protected PageRefs pageRefs;
    /* access modifiers changed from: private */
    public boolean partial;
    protected byte[] password;
    protected char pdfVersion;
    protected int rValue;
    private int readDepth;
    protected boolean rebuilt;
    protected boolean remoteToLocalNamedDestinations;
    PdfDictionary rootPages;
    protected boolean sharedStreams;
    protected ArrayList<PdfString> strings;
    protected boolean tampered;
    protected PRTokeniser tokens;
    protected PdfDictionary trailer;
    private final PdfViewerPreferencesImp viewerPreferences;
    protected long[] xref;
    protected ArrayList<PdfObject> xrefObj;

    static {
        Class<PdfReader> cls = PdfReader.class;
        LOGGER = LoggerFactory.getLogger((Class<?>) cls);
        COUNTER = CounterFactory.getCounter(cls);
    }

    /* access modifiers changed from: protected */
    public Counter getCounter() {
        return COUNTER;
    }

    private PdfReader(RandomAccessSource randomAccessSource, boolean z, byte[] bArr, Certificate certificate2, Key key, String str, ExternalDecryptionProcess externalDecryptionProcess2, boolean z2) throws IOException {
        this.acroForm = null;
        this.acroFormParsed = false;
        this.encrypted = false;
        this.rebuilt = false;
        this.tampered = false;
        this.password = null;
        this.certificateKey = null;
        this.certificate = null;
        this.certificateKeyProvider = null;
        this.externalDecryptionProcess = null;
        this.strings = new ArrayList<>();
        this.sharedStreams = true;
        this.consolidateNamedDestinations = false;
        this.remoteToLocalNamedDestinations = false;
        this.lastXrefPartial = -1;
        this.viewerPreferences = new PdfViewerPreferencesImp();
        this.readDepth = 0;
        this.certificate = certificate2;
        this.certificateKey = key;
        this.certificateKeyProvider = str;
        this.externalDecryptionProcess = externalDecryptionProcess2;
        this.password = bArr;
        this.partial = z;
        try {
            this.tokens = getOffsetTokeniser(randomAccessSource);
            if (z) {
                readPdfPartial();
            } else {
                readPdf();
            }
            getCounter().read(this.fileLength);
        } catch (IOException e) {
            if (z2) {
                randomAccessSource.close();
            }
            throw e;
        }
    }

    public PdfReader(String str) throws IOException {
        this(str, (byte[]) null);
    }

    public PdfReader(String str, byte[] bArr) throws IOException {
        this(str, bArr, false);
    }

    public PdfReader(String str, byte[] bArr, boolean z) throws IOException {
        this(new RandomAccessSourceFactory().setForceRead(false).setUsePlainRandomAccess(Document.plainRandomAccess).createBestSource(str), z, bArr, (Certificate) null, (Key) null, (String) null, (ExternalDecryptionProcess) null, true);
    }

    public PdfReader(byte[] bArr) throws IOException {
        this(bArr, (byte[]) null);
    }

    public PdfReader(byte[] bArr, byte[] bArr2) throws IOException {
        this(new RandomAccessSourceFactory().createSource(bArr), false, bArr2, (Certificate) null, (Key) null, (String) null, (ExternalDecryptionProcess) null, true);
    }

    public PdfReader(String str, Certificate certificate2, Key key, String str2) throws IOException {
        this(new RandomAccessSourceFactory().setForceRead(false).setUsePlainRandomAccess(Document.plainRandomAccess).createBestSource(str), false, (byte[]) null, certificate2, key, str2, (ExternalDecryptionProcess) null, true);
    }

    public PdfReader(String str, Certificate certificate2, ExternalDecryptionProcess externalDecryptionProcess2) throws IOException {
        this(new RandomAccessSourceFactory().setForceRead(false).setUsePlainRandomAccess(Document.plainRandomAccess).createBestSource(str), false, (byte[]) null, certificate2, (Key) null, (String) null, externalDecryptionProcess2, true);
    }

    public PdfReader(byte[] bArr, Certificate certificate2, ExternalDecryptionProcess externalDecryptionProcess2) throws IOException {
        this(new RandomAccessSourceFactory().setForceRead(false).setUsePlainRandomAccess(Document.plainRandomAccess).createSource(bArr), false, (byte[]) null, certificate2, (Key) null, (String) null, externalDecryptionProcess2, true);
    }

    public PdfReader(InputStream inputStream, Certificate certificate2, ExternalDecryptionProcess externalDecryptionProcess2) throws IOException {
        this(new RandomAccessSourceFactory().setForceRead(false).setUsePlainRandomAccess(Document.plainRandomAccess).createSource(inputStream), false, (byte[]) null, certificate2, (Key) null, (String) null, externalDecryptionProcess2, true);
    }

    public PdfReader(URL url) throws IOException {
        this(url, (byte[]) null);
    }

    public PdfReader(URL url, byte[] bArr) throws IOException {
        this(new RandomAccessSourceFactory().createSource(url), false, bArr, (Certificate) null, (Key) null, (String) null, (ExternalDecryptionProcess) null, true);
    }

    public PdfReader(InputStream inputStream, byte[] bArr) throws IOException {
        this(new RandomAccessSourceFactory().createSource(inputStream), false, bArr, (Certificate) null, (Key) null, (String) null, (ExternalDecryptionProcess) null, false);
    }

    public PdfReader(InputStream inputStream) throws IOException {
        this(inputStream, (byte[]) null);
    }

    public PdfReader(RandomAccessFileOrArray randomAccessFileOrArray, byte[] bArr) throws IOException {
        this(randomAccessFileOrArray, bArr, true);
    }

    public PdfReader(RandomAccessFileOrArray randomAccessFileOrArray, byte[] bArr, boolean z) throws IOException {
        this(randomAccessFileOrArray.getByteSource(), z, bArr, (Certificate) null, (Key) null, (String) null, (ExternalDecryptionProcess) null, false);
    }

    public PdfReader(PdfReader pdfReader) {
        this.acroForm = null;
        this.acroFormParsed = false;
        this.encrypted = false;
        this.rebuilt = false;
        this.tampered = false;
        this.password = null;
        this.certificateKey = null;
        this.certificate = null;
        this.certificateKeyProvider = null;
        this.externalDecryptionProcess = null;
        this.strings = new ArrayList<>();
        this.sharedStreams = true;
        this.consolidateNamedDestinations = false;
        this.remoteToLocalNamedDestinations = false;
        this.lastXrefPartial = -1;
        this.viewerPreferences = new PdfViewerPreferencesImp();
        this.readDepth = 0;
        this.appendable = pdfReader.appendable;
        this.consolidateNamedDestinations = pdfReader.consolidateNamedDestinations;
        this.encrypted = pdfReader.encrypted;
        this.rebuilt = pdfReader.rebuilt;
        this.sharedStreams = pdfReader.sharedStreams;
        this.tampered = pdfReader.tampered;
        this.password = pdfReader.password;
        this.pdfVersion = pdfReader.pdfVersion;
        this.eofPos = pdfReader.eofPos;
        this.freeXref = pdfReader.freeXref;
        this.lastXref = pdfReader.lastXref;
        this.newXrefType = pdfReader.newXrefType;
        this.tokens = new PRTokeniser(pdfReader.tokens.getSafeFile());
        if (pdfReader.decrypt != null) {
            this.decrypt = new PdfEncryption(pdfReader.decrypt);
        }
        this.pValue = pdfReader.pValue;
        this.rValue = pdfReader.rValue;
        this.xrefObj = new ArrayList<>(pdfReader.xrefObj);
        for (int i = 0; i < pdfReader.xrefObj.size(); i++) {
            this.xrefObj.set(i, duplicatePdfObject(pdfReader.xrefObj.get(i), this));
        }
        this.pageRefs = new PageRefs(pdfReader.pageRefs, this);
        PdfDictionary pdfDictionary = (PdfDictionary) duplicatePdfObject(pdfReader.trailer, this);
        this.trailer = pdfDictionary;
        PdfDictionary asDict = pdfDictionary.getAsDict(PdfName.ROOT);
        this.catalog = asDict;
        this.rootPages = asDict.getAsDict(PdfName.PAGES);
        this.fileLength = pdfReader.fileLength;
        this.partial = pdfReader.partial;
        this.hybridXref = pdfReader.hybridXref;
        this.objStmToOffset = pdfReader.objStmToOffset;
        this.xref = pdfReader.xref;
        this.cryptoRef = (PRIndirectReference) duplicatePdfObject(pdfReader.cryptoRef, this);
        this.ownerPasswordUsed = pdfReader.ownerPasswordUsed;
    }

    private static PRTokeniser getOffsetTokeniser(RandomAccessSource randomAccessSource) throws IOException {
        PRTokeniser pRTokeniser = new PRTokeniser(new RandomAccessFileOrArray(randomAccessSource));
        int headerOffset = pRTokeniser.getHeaderOffset();
        return headerOffset != 0 ? new PRTokeniser(new RandomAccessFileOrArray((RandomAccessSource) new WindowRandomAccessSource(randomAccessSource, (long) headerOffset))) : pRTokeniser;
    }

    public RandomAccessFileOrArray getSafeFile() {
        return this.tokens.getSafeFile();
    }

    /* access modifiers changed from: protected */
    public PdfReaderInstance getPdfReaderInstance(PdfWriter pdfWriter) {
        return new PdfReaderInstance(this, pdfWriter);
    }

    public int getNumberOfPages() {
        return this.pageRefs.size();
    }

    public PdfDictionary getCatalog() {
        return this.catalog;
    }

    public PRAcroForm getAcroForm() {
        if (!this.acroFormParsed) {
            this.acroFormParsed = true;
            PdfObject pdfObject = this.catalog.get(PdfName.ACROFORM);
            if (pdfObject != null) {
                try {
                    PRAcroForm pRAcroForm = new PRAcroForm(this);
                    this.acroForm = pRAcroForm;
                    pRAcroForm.readAcroForm((PdfDictionary) getPdfObject(pdfObject));
                } catch (Exception unused) {
                    this.acroForm = null;
                }
            }
        }
        return this.acroForm;
    }

    public int getPageRotation(int i) {
        return getPageRotation(this.pageRefs.getPageNRelease(i));
    }

    /* access modifiers changed from: package-private */
    public int getPageRotation(PdfDictionary pdfDictionary) {
        PdfNumber asNumber = pdfDictionary.getAsNumber(PdfName.ROTATE);
        if (asNumber == null) {
            return 0;
        }
        int intValue = asNumber.intValue() % 360;
        return intValue < 0 ? intValue + 360 : intValue;
    }

    public Rectangle getPageSizeWithRotation(int i) {
        return getPageSizeWithRotation(this.pageRefs.getPageNRelease(i));
    }

    public Rectangle getPageSizeWithRotation(PdfDictionary pdfDictionary) {
        Rectangle pageSize = getPageSize(pdfDictionary);
        for (int pageRotation = getPageRotation(pdfDictionary); pageRotation > 0; pageRotation -= 90) {
            pageSize = pageSize.rotate();
        }
        return pageSize;
    }

    public Rectangle getPageSize(int i) {
        return getPageSize(this.pageRefs.getPageNRelease(i));
    }

    public Rectangle getPageSize(PdfDictionary pdfDictionary) {
        return getNormalizedRectangle(pdfDictionary.getAsArray(PdfName.MEDIABOX));
    }

    public Rectangle getCropBox(int i) {
        PdfDictionary pageNRelease = this.pageRefs.getPageNRelease(i);
        PdfArray pdfArray = (PdfArray) getPdfObjectRelease(pageNRelease.get(PdfName.CROPBOX));
        if (pdfArray == null) {
            return getPageSize(pageNRelease);
        }
        return getNormalizedRectangle(pdfArray);
    }

    public Rectangle getBoxSize(int i, String str) {
        PdfArray pdfArray;
        PdfDictionary pageNRelease = this.pageRefs.getPageNRelease(i);
        if (str.equals("trim")) {
            pdfArray = (PdfArray) getPdfObjectRelease(pageNRelease.get(PdfName.TRIMBOX));
        } else if (str.equals("art")) {
            pdfArray = (PdfArray) getPdfObjectRelease(pageNRelease.get(PdfName.ARTBOX));
        } else if (str.equals("bleed")) {
            pdfArray = (PdfArray) getPdfObjectRelease(pageNRelease.get(PdfName.BLEEDBOX));
        } else if (str.equals("crop")) {
            pdfArray = (PdfArray) getPdfObjectRelease(pageNRelease.get(PdfName.CROPBOX));
        } else {
            pdfArray = str.equals("media") ? (PdfArray) getPdfObjectRelease(pageNRelease.get(PdfName.MEDIABOX)) : null;
        }
        if (pdfArray == null) {
            return null;
        }
        return getNormalizedRectangle(pdfArray);
    }

    public HashMap<String, String> getInfo() {
        HashMap<String, String> hashMap = new HashMap<>();
        PdfDictionary asDict = this.trailer.getAsDict(PdfName.INFO);
        if (asDict == null) {
            return hashMap;
        }
        for (PdfName next : asDict.getKeys()) {
            PdfObject pdfObject = getPdfObject(asDict.get(next));
            if (pdfObject != null) {
                String pdfObject2 = pdfObject.toString();
                int type = pdfObject.type();
                if (type == 3) {
                    pdfObject2 = ((PdfString) pdfObject).toUnicodeString();
                } else if (type == 4) {
                    pdfObject2 = PdfName.decodeName(pdfObject2);
                }
                hashMap.put(PdfName.decodeName(next.toString()), pdfObject2);
            }
        }
        return hashMap;
    }

    public static Rectangle getNormalizedRectangle(PdfArray pdfArray) {
        float floatValue = ((PdfNumber) getPdfObjectRelease(pdfArray.getPdfObject(0))).floatValue();
        float floatValue2 = ((PdfNumber) getPdfObjectRelease(pdfArray.getPdfObject(1))).floatValue();
        float floatValue3 = ((PdfNumber) getPdfObjectRelease(pdfArray.getPdfObject(2))).floatValue();
        float floatValue4 = ((PdfNumber) getPdfObjectRelease(pdfArray.getPdfObject(3))).floatValue();
        return new Rectangle(Math.min(floatValue, floatValue3), Math.min(floatValue2, floatValue4), Math.max(floatValue, floatValue3), Math.max(floatValue2, floatValue4));
    }

    public boolean isTagged() {
        PdfDictionary asDict = this.catalog.getAsDict(PdfName.MARKINFO);
        if (asDict == null || !PdfBoolean.PDFTRUE.equals(asDict.getAsBoolean(PdfName.MARKED)) || this.catalog.getAsDict(PdfName.STRUCTTREEROOT) == null) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void readPdf() throws IOException {
        this.fileLength = this.tokens.getFile().length();
        this.pdfVersion = this.tokens.checkPdfHeader();
        try {
            readXref();
        } catch (Exception e) {
            try {
                this.rebuilt = true;
                rebuildXref();
                this.lastXref = -1;
            } catch (Exception e2) {
                throw new InvalidPdfException(MessageLocalization.getComposedMessage("rebuild.failed.1.original.message.2", e2.getMessage(), e.getMessage()));
            }
        }
        try {
            readDocObj();
        } catch (Exception e3) {
            if (e3 instanceof BadPasswordException) {
                throw new BadPasswordException(e3.getMessage());
            } else if (this.rebuilt || this.encryptionError) {
                throw new InvalidPdfException(e3.getMessage());
            } else {
                this.rebuilt = true;
                this.encrypted = false;
                try {
                    rebuildXref();
                    this.lastXref = -1;
                    readDocObj();
                } catch (Exception e4) {
                    throw new InvalidPdfException(MessageLocalization.getComposedMessage("rebuild.failed.1.original.message.2", e4.getMessage(), e3.getMessage()));
                }
            }
        }
        this.strings.clear();
        readPages();
        removeUnusedObjects();
    }

    /* access modifiers changed from: protected */
    public void readPdfPartial() throws IOException {
        this.fileLength = this.tokens.getFile().length();
        this.pdfVersion = this.tokens.checkPdfHeader();
        try {
            readXref();
        } catch (Exception e) {
            try {
                this.rebuilt = true;
                rebuildXref();
                this.lastXref = -1;
            } catch (Exception e2) {
                throw new InvalidPdfException(MessageLocalization.getComposedMessage("rebuild.failed.1.original.message.2", e2.getMessage(), e.getMessage()), e2);
            }
        }
        readDocObjPartial();
        readPages();
    }

    private boolean equalsArray(byte[] bArr, byte[] bArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    private void readDecryptedDocObj() throws IOException {
        PdfObject pdfObject;
        byte[] bArr;
        byte[] bArr2;
        byte[] bArr3;
        byte[] bArr4;
        int i;
        int i2;
        int i3;
        int i4;
        PdfArray pdfArray;
        byte[] bArr5;
        boolean z;
        MessageDigest messageDigest;
        int i5;
        int i6;
        if (!this.encrypted && (pdfObject = this.trailer.get(PdfName.ENCRYPT)) != null && !pdfObject.toString().equals("null")) {
            this.encryptionError = true;
            this.encrypted = true;
            PdfDictionary pdfDictionary = (PdfDictionary) getPdfObject(pdfObject);
            PdfArray asArray = this.trailer.getAsArray(PdfName.f600ID);
            if (asArray != null) {
                PdfObject pdfObject2 = asArray.getPdfObject(0);
                this.strings.remove(pdfObject2);
                bArr = DocWriter.getISOBytes(pdfObject2.toString());
                if (asArray.size() > 1) {
                    this.strings.remove(asArray.getPdfObject(1));
                }
            } else {
                bArr = null;
            }
            if (bArr == null) {
                bArr = new byte[0];
            }
            PdfObject pdfObjectRelease = getPdfObjectRelease(pdfDictionary.get(PdfName.FILTER));
            int i7 = 2;
            if (pdfObjectRelease.equals(PdfName.STANDARD)) {
                String pdfObject3 = pdfDictionary.get(PdfName.f660U).toString();
                this.strings.remove(pdfDictionary.get(PdfName.f660U));
                byte[] iSOBytes = DocWriter.getISOBytes(pdfObject3);
                String pdfObject4 = pdfDictionary.get(PdfName.f617O).toString();
                this.strings.remove(pdfDictionary.get(PdfName.f617O));
                byte[] iSOBytes2 = DocWriter.getISOBytes(pdfObject4);
                if (pdfDictionary.contains(PdfName.f619OE)) {
                    this.strings.remove(pdfDictionary.get(PdfName.f619OE));
                }
                if (pdfDictionary.contains(PdfName.f661UE)) {
                    this.strings.remove(pdfDictionary.get(PdfName.f661UE));
                }
                if (pdfDictionary.contains(PdfName.PERMS)) {
                    this.strings.remove(pdfDictionary.get(PdfName.PERMS));
                }
                PdfObject pdfObject5 = pdfDictionary.get(PdfName.f623P);
                if (pdfObject5.isNumber()) {
                    byte[] bArr6 = iSOBytes;
                    this.pValue = ((PdfNumber) pdfObject5).longValue();
                    PdfObject pdfObject6 = pdfDictionary.get(PdfName.f632R);
                    if (pdfObject6.isNumber()) {
                        int intValue = ((PdfNumber) pdfObject6).intValue();
                        this.rValue = intValue;
                        if (intValue == 2) {
                            i6 = 0;
                        } else if (intValue == 3) {
                            PdfObject pdfObject7 = pdfDictionary.get(PdfName.LENGTH);
                            if (pdfObject7.isNumber()) {
                                int intValue2 = ((PdfNumber) pdfObject7).intValue();
                                if (intValue2 > 128 || intValue2 < 40 || intValue2 % 8 != 0) {
                                    throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.length.value", new Object[0]));
                                }
                                i5 = intValue2;
                                i6 = 1;
                                i2 = i6;
                                bArr2 = iSOBytes2;
                                i = i5;
                                bArr3 = bArr6;
                                bArr4 = null;
                            } else {
                                throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.length.value", new Object[0]));
                            }
                        } else if (intValue == 4) {
                            PdfDictionary pdfDictionary2 = (PdfDictionary) pdfDictionary.get(PdfName.f562CF);
                            if (pdfDictionary2 != null) {
                                PdfDictionary pdfDictionary3 = (PdfDictionary) pdfDictionary2.get(PdfName.STDCF);
                                if (pdfDictionary3 != null) {
                                    if (PdfName.f665V2.equals(pdfDictionary3.get(PdfName.CFM))) {
                                        i7 = 1;
                                    } else if (!PdfName.AESV2.equals(pdfDictionary3.get(PdfName.CFM))) {
                                        throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("no.compatible.encryption.found", new Object[0]));
                                    }
                                    PdfObject pdfObject8 = pdfDictionary.get(PdfName.ENCRYPTMETADATA);
                                    if (pdfObject8 != null && pdfObject8.toString().equals(PdfBoolean.FALSE)) {
                                        i7 |= 8;
                                    }
                                    i6 = i7;
                                } else {
                                    throw new InvalidPdfException(MessageLocalization.getComposedMessage("stdcf.not.found.encryption", new Object[0]));
                                }
                            } else {
                                throw new InvalidPdfException(MessageLocalization.getComposedMessage("cf.not.found.encryption", new Object[0]));
                            }
                        } else if (intValue == 5) {
                            PdfObject pdfObject9 = pdfDictionary.get(PdfName.ENCRYPTMETADATA);
                            i6 = (pdfObject9 == null || !pdfObject9.toString().equals(PdfBoolean.FALSE)) ? 3 : 11;
                        } else {
                            throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("unknown.encryption.type.r.eq.1", this.rValue));
                        }
                        i5 = 0;
                        i2 = i6;
                        bArr2 = iSOBytes2;
                        i = i5;
                        bArr3 = bArr6;
                        bArr4 = null;
                    } else {
                        throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.r.value", new Object[0]));
                    }
                } else {
                    throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.p.value", new Object[0]));
                }
            } else if (pdfObjectRelease.equals(PdfName.PUBSEC)) {
                PdfObject pdfObject10 = pdfDictionary.get(PdfName.f664V);
                if (pdfObject10.isNumber()) {
                    int intValue3 = ((PdfNumber) pdfObject10).intValue();
                    if (intValue3 == 1) {
                        pdfArray = (PdfArray) pdfDictionary.get(PdfName.RECIPIENTS);
                        i4 = 0;
                        i3 = 40;
                    } else if (intValue3 == 2) {
                        PdfObject pdfObject11 = pdfDictionary.get(PdfName.LENGTH);
                        if (pdfObject11.isNumber()) {
                            int intValue4 = ((PdfNumber) pdfObject11).intValue();
                            if (intValue4 > 128 || intValue4 < 40 || intValue4 % 8 != 0) {
                                throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.length.value", new Object[0]));
                            }
                            i3 = intValue4;
                            pdfArray = (PdfArray) pdfDictionary.get(PdfName.RECIPIENTS);
                            i4 = 1;
                        } else {
                            throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.length.value", new Object[0]));
                        }
                    } else if (intValue3 == 4 || intValue3 == 5) {
                        PdfDictionary pdfDictionary4 = (PdfDictionary) pdfDictionary.get(PdfName.f562CF);
                        if (pdfDictionary4 != null) {
                            PdfDictionary pdfDictionary5 = (PdfDictionary) pdfDictionary4.get(PdfName.DEFAULTCRYPTFILTER);
                            if (pdfDictionary5 != null) {
                                if (PdfName.f665V2.equals(pdfDictionary5.get(PdfName.CFM))) {
                                    i4 = 1;
                                } else if (PdfName.AESV2.equals(pdfDictionary5.get(PdfName.CFM))) {
                                    i4 = 2;
                                } else if (PdfName.AESV3.equals(pdfDictionary5.get(PdfName.CFM))) {
                                    i4 = 3;
                                    i3 = 256;
                                    PdfObject pdfObject12 = pdfDictionary5.get(PdfName.ENCRYPTMETADATA);
                                    if (pdfObject12 != null && pdfObject12.toString().equals(PdfBoolean.FALSE)) {
                                        i4 |= 8;
                                    }
                                    pdfArray = (PdfArray) pdfDictionary5.get(PdfName.RECIPIENTS);
                                } else {
                                    throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("no.compatible.encryption.found", new Object[0]));
                                }
                                i3 = 128;
                                PdfObject pdfObject122 = pdfDictionary5.get(PdfName.ENCRYPTMETADATA);
                                i4 |= 8;
                                pdfArray = (PdfArray) pdfDictionary5.get(PdfName.RECIPIENTS);
                            } else {
                                throw new InvalidPdfException(MessageLocalization.getComposedMessage("defaultcryptfilter.not.found.encryption", new Object[0]));
                            }
                        } else {
                            throw new InvalidPdfException(MessageLocalization.getComposedMessage("cf.not.found.encryption", new Object[0]));
                        }
                    } else {
                        throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("unknown.encryption.type.v.eq.1", intValue3));
                    }
                    try {
                        X509CertificateHolder x509CertificateHolder = new X509CertificateHolder(this.certificate.getEncoded());
                        if (this.externalDecryptionProcess == null) {
                            int i8 = 0;
                            z = false;
                            bArr5 = null;
                            while (i8 < pdfArray.size()) {
                                PdfObject pdfObject13 = pdfArray.getPdfObject(i8);
                                this.strings.remove(pdfObject13);
                                try {
                                    for (RecipientInformation recipientInformation : new CMSEnvelopedData(pdfObject13.getBytes()).getRecipientInfos().getRecipients()) {
                                        if (recipientInformation.getRID().match(x509CertificateHolder) && !z) {
                                            bArr5 = PdfEncryptor.getContent(recipientInformation, (PrivateKey) this.certificateKey, this.certificateKeyProvider);
                                            z = true;
                                        }
                                    }
                                    i8++;
                                } catch (Exception e) {
                                    throw new ExceptionConverter(e);
                                }
                            }
                        } else {
                            boolean z2 = false;
                            int i9 = 0;
                            bArr5 = null;
                            while (i9 < pdfArray.size()) {
                                PdfObject pdfObject14 = pdfArray.getPdfObject(i9);
                                this.strings.remove(pdfObject14);
                                try {
                                    RecipientInformation recipientInformation2 = new CMSEnvelopedData(pdfObject14.getBytes()).getRecipientInfos().get(this.externalDecryptionProcess.getCmsRecipientId());
                                    if (recipientInformation2 != null) {
                                        bArr5 = recipientInformation2.getContent(this.externalDecryptionProcess.getCmsRecipient());
                                        z2 = true;
                                    }
                                    i9++;
                                } catch (Exception e2) {
                                    throw new ExceptionConverter(e2);
                                }
                            }
                            z = z2;
                        }
                        if (!z || bArr5 == null) {
                            throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("bad.certificate.and.key", new Object[0]));
                        }
                        if ((i4 & 7) == 3) {
                            try {
                                messageDigest = MessageDigest.getInstance("SHA-256");
                            } catch (Exception e3) {
                                throw new ExceptionConverter(e3);
                            }
                        } else {
                            messageDigest = MessageDigest.getInstance(DigestAlgorithms.SHA1);
                        }
                        messageDigest.update(bArr5, 0, 20);
                        for (int i10 = 0; i10 < pdfArray.size(); i10++) {
                            messageDigest.update(pdfArray.getPdfObject(i10).getBytes());
                        }
                        if ((i4 & 8) != 0) {
                            messageDigest.update(new byte[]{-1, -1, -1, -1});
                        }
                        i = i3;
                        bArr3 = null;
                        bArr2 = null;
                        int i11 = i4;
                        bArr4 = messageDigest.digest();
                        i2 = i11;
                    } catch (Exception e4) {
                        throw new ExceptionConverter(e4);
                    }
                } else {
                    throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.v.value", new Object[0]));
                }
            } else {
                i2 = 0;
                i = 0;
                bArr4 = null;
                bArr3 = null;
                bArr2 = null;
            }
            PdfEncryption pdfEncryption = new PdfEncryption();
            this.decrypt = pdfEncryption;
            pdfEncryption.setCryptoMode(i2, i);
            if (pdfObjectRelease.equals(PdfName.STANDARD)) {
                if (this.rValue == 5) {
                    this.ownerPasswordUsed = this.decrypt.readKey(pdfDictionary, this.password);
                    this.pValue = this.decrypt.getPermissions();
                } else {
                    byte[] bArr7 = bArr3;
                    this.decrypt.setupByOwnerPassword(bArr, this.password, bArr3, bArr2, this.pValue);
                    byte[] bArr8 = this.decrypt.userKey;
                    int i12 = this.rValue;
                    int i13 = 32;
                    if (!equalsArray(bArr7, bArr8, (i12 == 3 || i12 == 4) ? 16 : 32)) {
                        this.decrypt.setupByUserPassword(bArr, this.password, bArr2, this.pValue);
                        byte[] bArr9 = this.decrypt.userKey;
                        int i14 = this.rValue;
                        if (i14 == 3 || i14 == 4) {
                            i13 = 16;
                        }
                        if (!equalsArray(bArr7, bArr9, i13)) {
                            throw new BadPasswordException(MessageLocalization.getComposedMessage("bad.user.password", new Object[0]));
                        }
                    } else {
                        this.ownerPasswordUsed = true;
                    }
                }
            } else if (pdfObjectRelease.equals(PdfName.PUBSEC)) {
                if ((i2 & 7) == 3) {
                    this.decrypt.setKey(bArr4);
                } else {
                    this.decrypt.setupByEncryptionKey(bArr4, i);
                }
                this.ownerPasswordUsed = true;
            }
            for (int i15 = 0; i15 < this.strings.size(); i15++) {
                this.strings.get(i15).decrypt(this);
            }
            if (pdfObject.isIndirect()) {
                PRIndirectReference pRIndirectReference = (PRIndirectReference) pdfObject;
                this.cryptoRef = pRIndirectReference;
                this.xrefObj.set(pRIndirectReference.getNumber(), (Object) null);
            }
            this.encryptionError = false;
        }
    }

    public static PdfObject getPdfObjectRelease(PdfObject pdfObject) {
        PdfObject pdfObject2 = getPdfObject(pdfObject);
        releaseLastXrefPartial(pdfObject);
        return pdfObject2;
    }

    public static PdfObject getPdfObject(PdfObject pdfObject) {
        PdfObject pdfBoolean;
        if (pdfObject == null) {
            return null;
        }
        if (!pdfObject.isIndirect()) {
            return pdfObject;
        }
        try {
            PRIndirectReference pRIndirectReference = (PRIndirectReference) pdfObject;
            int number = pRIndirectReference.getNumber();
            boolean z = pRIndirectReference.getReader().appendable;
            PdfObject pdfObject2 = pRIndirectReference.getReader().getPdfObject(number);
            if (pdfObject2 == null) {
                return null;
            }
            if (z) {
                int type = pdfObject2.type();
                if (type == 1) {
                    pdfBoolean = new PdfBoolean(((PdfBoolean) pdfObject2).booleanValue());
                } else if (type == 4) {
                    pdfBoolean = new PdfName(pdfObject2.getBytes());
                } else if (type != 8) {
                    pdfObject2.setIndRef(pRIndirectReference);
                } else {
                    pdfBoolean = new PdfNull();
                }
                pdfObject2 = pdfBoolean;
                pdfObject2.setIndRef(pRIndirectReference);
            }
            return pdfObject2;
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public static PdfObject getPdfObjectRelease(PdfObject pdfObject, PdfObject pdfObject2) {
        PdfObject pdfObject3 = getPdfObject(pdfObject, pdfObject2);
        releaseLastXrefPartial(pdfObject);
        return pdfObject3;
    }

    public static PdfObject getPdfObject(PdfObject pdfObject, PdfObject pdfObject2) {
        PRIndirectReference indRef;
        PdfObject pdfObject3;
        if (pdfObject == null) {
            return null;
        }
        if (pdfObject.isIndirect()) {
            return getPdfObject(pdfObject);
        }
        if (!(pdfObject2 == null || (indRef = pdfObject2.getIndRef()) == null || !indRef.getReader().isAppendable())) {
            int type = pdfObject.type();
            if (type == 1) {
                pdfObject3 = new PdfBoolean(((PdfBoolean) pdfObject).booleanValue());
            } else if (type != 4) {
                if (type == 8) {
                    pdfObject = new PdfNull();
                }
                pdfObject.setIndRef(indRef);
            } else {
                pdfObject3 = new PdfName(pdfObject.getBytes());
            }
            pdfObject = pdfObject3;
            pdfObject.setIndRef(indRef);
        }
        return pdfObject;
    }

    public PdfObject getPdfObjectRelease(int i) {
        PdfObject pdfObject = getPdfObject(i);
        releaseLastXrefPartial();
        return pdfObject;
    }

    public PdfObject getPdfObject(int i) {
        try {
            this.lastXrefPartial = -1;
            if (i >= 0) {
                if (i < this.xrefObj.size()) {
                    PdfObject pdfObject = this.xrefObj.get(i);
                    if (this.partial) {
                        if (pdfObject == null) {
                            if (i * 2 >= this.xref.length) {
                                return null;
                            }
                            PdfObject readSingleObject = readSingleObject(i);
                            this.lastXrefPartial = -1;
                            if (readSingleObject != null) {
                                this.lastXrefPartial = i;
                            }
                            return readSingleObject;
                        }
                    }
                    return pdfObject;
                }
            }
            return null;
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public void resetLastXrefPartial() {
        this.lastXrefPartial = -1;
    }

    public void releaseLastXrefPartial() {
        int i;
        if (this.partial && (i = this.lastXrefPartial) != -1) {
            this.xrefObj.set(i, (Object) null);
            this.lastXrefPartial = -1;
        }
    }

    public static void releaseLastXrefPartial(PdfObject pdfObject) {
        int i;
        if (pdfObject != null && pdfObject.isIndirect() && (pdfObject instanceof PRIndirectReference)) {
            PRIndirectReference pRIndirectReference = (PRIndirectReference) pdfObject;
            PdfReader reader = pRIndirectReference.getReader();
            if (reader.partial && (i = reader.lastXrefPartial) != -1 && i == pRIndirectReference.getNumber()) {
                reader.xrefObj.set(reader.lastXrefPartial, (Object) null);
            }
            reader.lastXrefPartial = -1;
        }
    }

    private void setXrefPartialObject(int i, PdfObject pdfObject) {
        if (this.partial && i >= 0) {
            this.xrefObj.set(i, pdfObject);
        }
    }

    public PRIndirectReference addPdfObject(PdfObject pdfObject) {
        this.xrefObj.add(pdfObject);
        return new PRIndirectReference(this, this.xrefObj.size() - 1);
    }

    /* access modifiers changed from: protected */
    public void readPages() throws IOException {
        PdfDictionary asDict = this.trailer.getAsDict(PdfName.ROOT);
        this.catalog = asDict;
        if (asDict != null) {
            PdfDictionary asDict2 = asDict.getAsDict(PdfName.PAGES);
            this.rootPages = asDict2;
            if (asDict2 == null || !PdfName.PAGES.equals(this.rootPages.get(PdfName.TYPE))) {
                if (debugmode) {
                    Logger logger = LOGGER;
                    if (logger.isLogging(Level.ERROR)) {
                        logger.error(MessageLocalization.getComposedMessage("the.document.has.no.page.root", new Object[0]));
                    }
                }
                throw new InvalidPdfException(MessageLocalization.getComposedMessage("the.document.has.no.page.root", new Object[0]));
            }
            this.pageRefs = new PageRefs(this, (C15211) null);
            return;
        }
        throw new InvalidPdfException(MessageLocalization.getComposedMessage("the.document.has.no.catalog.object", new Object[0]));
    }

    /* access modifiers changed from: protected */
    public void readDocObjPartial() throws IOException {
        ArrayList<PdfObject> arrayList = new ArrayList<>(this.xref.length / 2);
        this.xrefObj = arrayList;
        arrayList.addAll(Collections.nCopies(this.xref.length / 2, (Object) null));
        readDecryptedDocObj();
        LongHashtable longHashtable = this.objStmToOffset;
        if (longHashtable != null) {
            long[] keys = longHashtable.getKeys();
            for (long j : keys) {
                int i = (int) (2 * j);
                this.objStmToOffset.put(j, this.xref[i]);
                this.xref[i] = -1;
            }
        }
    }

    /* access modifiers changed from: protected */
    public PdfObject readSingleObject(int i) throws IOException {
        this.strings.clear();
        int i2 = i * 2;
        long[] jArr = this.xref;
        long j = jArr[i2];
        PdfObject pdfObject = null;
        if (j < 0) {
            return null;
        }
        int i3 = i2 + 1;
        if (jArr[i3] > 0) {
            j = this.objStmToOffset.get(jArr[i3]);
        }
        if (j == 0) {
            return null;
        }
        this.tokens.seek(j);
        this.tokens.nextValidToken();
        if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
            this.tokens.throwError(MessageLocalization.getComposedMessage("invalid.object.number", new Object[0]));
        }
        this.objNum = this.tokens.intValue();
        this.tokens.nextValidToken();
        if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
            this.tokens.throwError(MessageLocalization.getComposedMessage("invalid.generation.number", new Object[0]));
        }
        this.objGen = this.tokens.intValue();
        this.tokens.nextValidToken();
        if (!this.tokens.getStringValue().equals("obj")) {
            this.tokens.throwError(MessageLocalization.getComposedMessage("token.obj.expected", new Object[0]));
        }
        try {
            PdfObject readPRObject = readPRObject();
            for (int i4 = 0; i4 < this.strings.size(); i4++) {
                this.strings.get(i4).decrypt(this);
            }
            if (readPRObject.isStream()) {
                checkPRStreamLength((PRStream) readPRObject);
            }
            pdfObject = readPRObject;
        } catch (IOException e) {
            if (debugmode) {
                Logger logger = LOGGER;
                if (logger.isLogging(Level.ERROR)) {
                    logger.error(e.getMessage(), e);
                }
            } else {
                throw e;
            }
        }
        long[] jArr2 = this.xref;
        if (jArr2[i3] > 0) {
            pdfObject = readOneObjStm((PRStream) pdfObject, (int) jArr2[i2]);
        }
        this.xrefObj.set(i, pdfObject);
        return pdfObject;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0045, code lost:
        r8 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.pdf.PdfObject readOneObjStm(com.itextpdf.text.pdf.PRStream r8, int r9) throws java.io.IOException {
        /*
            r7 = this;
            com.itextpdf.text.pdf.PdfName r0 = com.itextpdf.text.pdf.PdfName.FIRST
            com.itextpdf.text.pdf.PdfNumber r0 = r8.getAsNumber(r0)
            int r0 = r0.intValue()
            com.itextpdf.text.pdf.PRTokeniser r1 = r7.tokens
            com.itextpdf.text.pdf.RandomAccessFileOrArray r1 = r1.getFile()
            byte[] r8 = getStreamBytes(r8, r1)
            com.itextpdf.text.pdf.PRTokeniser r1 = r7.tokens
            com.itextpdf.text.pdf.PRTokeniser r2 = new com.itextpdf.text.pdf.PRTokeniser
            com.itextpdf.text.pdf.RandomAccessFileOrArray r3 = new com.itextpdf.text.pdf.RandomAccessFileOrArray
            com.itextpdf.text.io.RandomAccessSourceFactory r4 = new com.itextpdf.text.io.RandomAccessSourceFactory
            r4.<init>()
            com.itextpdf.text.io.RandomAccessSource r8 = r4.createSource((byte[]) r8)
            r3.<init>((com.itextpdf.text.p018io.RandomAccessSource) r8)
            r2.<init>(r3)
            r7.tokens = r2
            r8 = 1
            int r9 = r9 + r8
            r2 = 0
            r3 = 0
            r4 = 0
        L_0x0030:
            if (r3 >= r9) goto L_0x0067
            com.itextpdf.text.pdf.PRTokeniser r8 = r7.tokens     // Catch:{ all -> 0x0065 }
            boolean r8 = r8.nextToken()     // Catch:{ all -> 0x0065 }
            if (r8 != 0) goto L_0x003b
            goto L_0x0067
        L_0x003b:
            com.itextpdf.text.pdf.PRTokeniser r8 = r7.tokens     // Catch:{ all -> 0x0065 }
            com.itextpdf.text.pdf.PRTokeniser$TokenType r8 = r8.getTokenType()     // Catch:{ all -> 0x0065 }
            com.itextpdf.text.pdf.PRTokeniser$TokenType r5 = com.itextpdf.text.pdf.PRTokeniser.TokenType.NUMBER     // Catch:{ all -> 0x0065 }
            if (r8 == r5) goto L_0x0047
        L_0x0045:
            r8 = 0
            goto L_0x0067
        L_0x0047:
            com.itextpdf.text.pdf.PRTokeniser r8 = r7.tokens     // Catch:{ all -> 0x0065 }
            boolean r8 = r8.nextToken()     // Catch:{ all -> 0x0065 }
            if (r8 != 0) goto L_0x0050
            goto L_0x0067
        L_0x0050:
            com.itextpdf.text.pdf.PRTokeniser r5 = r7.tokens     // Catch:{ all -> 0x0065 }
            com.itextpdf.text.pdf.PRTokeniser$TokenType r5 = r5.getTokenType()     // Catch:{ all -> 0x0065 }
            com.itextpdf.text.pdf.PRTokeniser$TokenType r6 = com.itextpdf.text.pdf.PRTokeniser.TokenType.NUMBER     // Catch:{ all -> 0x0065 }
            if (r5 == r6) goto L_0x005b
            goto L_0x0045
        L_0x005b:
            com.itextpdf.text.pdf.PRTokeniser r4 = r7.tokens     // Catch:{ all -> 0x0065 }
            int r4 = r4.intValue()     // Catch:{ all -> 0x0065 }
            int r4 = r4 + r0
            int r3 = r3 + 1
            goto L_0x0030
        L_0x0065:
            r8 = move-exception
            goto L_0x00a4
        L_0x0067:
            if (r8 == 0) goto L_0x0096
            com.itextpdf.text.pdf.PRTokeniser r8 = r7.tokens     // Catch:{ all -> 0x0065 }
            long r2 = (long) r4     // Catch:{ all -> 0x0065 }
            r8.seek(r2)     // Catch:{ all -> 0x0065 }
            com.itextpdf.text.pdf.PRTokeniser r8 = r7.tokens     // Catch:{ all -> 0x0065 }
            r8.nextToken()     // Catch:{ all -> 0x0065 }
            com.itextpdf.text.pdf.PRTokeniser r8 = r7.tokens     // Catch:{ all -> 0x0065 }
            com.itextpdf.text.pdf.PRTokeniser$TokenType r8 = r8.getTokenType()     // Catch:{ all -> 0x0065 }
            com.itextpdf.text.pdf.PRTokeniser$TokenType r9 = com.itextpdf.text.pdf.PRTokeniser.TokenType.NUMBER     // Catch:{ all -> 0x0065 }
            if (r8 != r9) goto L_0x008a
            com.itextpdf.text.pdf.PdfNumber r8 = new com.itextpdf.text.pdf.PdfNumber     // Catch:{ all -> 0x0065 }
            com.itextpdf.text.pdf.PRTokeniser r9 = r7.tokens     // Catch:{ all -> 0x0065 }
            java.lang.String r9 = r9.getStringValue()     // Catch:{ all -> 0x0065 }
            r8.<init>((java.lang.String) r9)     // Catch:{ all -> 0x0065 }
            goto L_0x0093
        L_0x008a:
            com.itextpdf.text.pdf.PRTokeniser r8 = r7.tokens     // Catch:{ all -> 0x0065 }
            r8.seek(r2)     // Catch:{ all -> 0x0065 }
            com.itextpdf.text.pdf.PdfObject r8 = r7.readPRObject()     // Catch:{ all -> 0x0065 }
        L_0x0093:
            r7.tokens = r1
            return r8
        L_0x0096:
            com.itextpdf.text.exceptions.InvalidPdfException r8 = new com.itextpdf.text.exceptions.InvalidPdfException     // Catch:{ all -> 0x0065 }
            java.lang.String r9 = "error.reading.objstm"
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ all -> 0x0065 }
            java.lang.String r9 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r9, (java.lang.Object[]) r0)     // Catch:{ all -> 0x0065 }
            r8.<init>(r9)     // Catch:{ all -> 0x0065 }
            throw r8     // Catch:{ all -> 0x0065 }
        L_0x00a4:
            r7.tokens = r1
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfReader.readOneObjStm(com.itextpdf.text.pdf.PRStream, int):com.itextpdf.text.pdf.PdfObject");
    }

    public double dumpPerc() {
        int i = 0;
        for (int i2 = 0; i2 < this.xrefObj.size(); i2++) {
            if (this.xrefObj.get(i2) != null) {
                i++;
            }
        }
        return (((double) i) * 100.0d) / ((double) this.xrefObj.size());
    }

    /* access modifiers changed from: protected */
    public void readDocObj() throws IOException {
        PdfObject pdfObject;
        ArrayList arrayList = new ArrayList();
        int i = 2;
        ArrayList<PdfObject> arrayList2 = new ArrayList<>(this.xref.length / 2);
        this.xrefObj = arrayList2;
        arrayList2.addAll(Collections.nCopies(this.xref.length / 2, (Object) null));
        while (true) {
            long[] jArr = this.xref;
            if (i < jArr.length) {
                long j = jArr[i];
                if (j > 0 && jArr[i + 1] <= 0) {
                    this.tokens.seek(j);
                    this.tokens.nextValidToken();
                    if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
                        this.tokens.throwError(MessageLocalization.getComposedMessage("invalid.object.number", new Object[0]));
                    }
                    this.objNum = this.tokens.intValue();
                    this.tokens.nextValidToken();
                    if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
                        this.tokens.throwError(MessageLocalization.getComposedMessage("invalid.generation.number", new Object[0]));
                    }
                    this.objGen = this.tokens.intValue();
                    this.tokens.nextValidToken();
                    if (!this.tokens.getStringValue().equals("obj")) {
                        this.tokens.throwError(MessageLocalization.getComposedMessage("token.obj.expected", new Object[0]));
                    }
                    try {
                        pdfObject = readPRObject();
                        if (pdfObject.isStream()) {
                            arrayList.add((PRStream) pdfObject);
                        }
                    } catch (IOException e) {
                        if (debugmode) {
                            Logger logger = LOGGER;
                            if (logger.isLogging(Level.ERROR)) {
                                logger.error(e.getMessage(), e);
                            }
                            pdfObject = null;
                        } else {
                            throw e;
                        }
                    }
                    this.xrefObj.set(i / 2, pdfObject);
                }
                i += 2;
            } else {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    checkPRStreamLength((PRStream) arrayList.get(i2));
                }
                readDecryptedDocObj();
                HashMap<Integer, IntHashtable> hashMap = this.objStmMark;
                if (hashMap != null) {
                    for (Map.Entry next : hashMap.entrySet()) {
                        int intValue = ((Integer) next.getKey()).intValue();
                        readObjStm((PRStream) this.xrefObj.get(intValue), (IntHashtable) next.getValue());
                        this.xrefObj.set(intValue, (Object) null);
                    }
                    this.objStmMark = null;
                }
                this.xref = null;
                return;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x008a, code lost:
        r12 = r14 - r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00d7, code lost:
        if (r12 < 0) goto L_0x00db;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void checkPRStreamLength(com.itextpdf.text.pdf.PRStream r19) throws java.io.IOException {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            com.itextpdf.text.pdf.PRTokeniser r2 = r0.tokens
            long r2 = r2.length()
            long r4 = r19.getOffset()
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.LENGTH
            com.itextpdf.text.pdf.PdfObject r6 = r1.get(r6)
            com.itextpdf.text.pdf.PdfObject r6 = getPdfObjectRelease((com.itextpdf.text.pdf.PdfObject) r6)
            java.lang.String r7 = "endstream"
            r8 = 0
            r9 = 1
            r10 = 0
            if (r6 == 0) goto L_0x0067
            int r12 = r6.type()
            r13 = 2
            if (r12 != r13) goto L_0x0067
            com.itextpdf.text.pdf.PdfNumber r6 = (com.itextpdf.text.pdf.PdfNumber) r6
            int r6 = r6.intValue()
            long r12 = (long) r6
            long r14 = r12 + r4
            r16 = 20
            long r2 = r2 - r16
            int r6 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1))
            if (r6 <= 0) goto L_0x0039
            goto L_0x0068
        L_0x0039:
            com.itextpdf.text.pdf.PRTokeniser r2 = r0.tokens
            r2.seek(r14)
            com.itextpdf.text.pdf.PRTokeniser r2 = r0.tokens
            r3 = 20
            java.lang.String r2 = r2.readString(r3)
            java.lang.String r3 = "\nendstream"
            boolean r3 = r2.startsWith(r3)
            if (r3 != 0) goto L_0x0065
            java.lang.String r3 = "\r\nendstream"
            boolean r3 = r2.startsWith(r3)
            if (r3 != 0) goto L_0x0065
            java.lang.String r3 = "\rendstream"
            boolean r3 = r2.startsWith(r3)
            if (r3 != 0) goto L_0x0065
            boolean r2 = r2.startsWith(r7)
            if (r2 != 0) goto L_0x0065
            goto L_0x0068
        L_0x0065:
            r9 = 0
            goto L_0x0068
        L_0x0067:
            r12 = r10
        L_0x0068:
            if (r9 == 0) goto L_0x00da
            r2 = 16
            byte[] r3 = new byte[r2]
            com.itextpdf.text.pdf.PRTokeniser r6 = r0.tokens
            r6.seek(r4)
        L_0x0073:
            com.itextpdf.text.pdf.PRTokeniser r6 = r0.tokens
            long r14 = r6.getFilePointer()
            com.itextpdf.text.pdf.PRTokeniser r6 = r0.tokens
            boolean r6 = r6.readLineSegment(r3, r8)
            if (r6 != 0) goto L_0x0082
            goto L_0x00ae
        L_0x0082:
            byte[] r6 = endstream
            boolean r6 = equalsn(r3, r6)
            if (r6 == 0) goto L_0x008d
        L_0x008a:
            long r12 = r14 - r4
            goto L_0x00ae
        L_0x008d:
            byte[] r6 = endobj
            boolean r6 = equalsn(r3, r6)
            if (r6 == 0) goto L_0x0073
            com.itextpdf.text.pdf.PRTokeniser r3 = r0.tokens
            r8 = 16
            long r8 = r14 - r8
            r3.seek(r8)
            com.itextpdf.text.pdf.PRTokeniser r3 = r0.tokens
            java.lang.String r2 = r3.readString(r2)
            int r2 = r2.indexOf(r7)
            if (r2 < 0) goto L_0x008a
            long r2 = (long) r2
            long r8 = r8 + r2
            r14 = r8
            goto L_0x008a
        L_0x00ae:
            com.itextpdf.text.pdf.PRTokeniser r2 = r0.tokens
            r3 = 2
            long r3 = r14 - r3
            r2.seek(r3)
            com.itextpdf.text.pdf.PRTokeniser r2 = r0.tokens
            int r2 = r2.read()
            r3 = 13
            r4 = 1
            if (r2 != r3) goto L_0x00c4
            long r12 = r12 - r4
        L_0x00c4:
            com.itextpdf.text.pdf.PRTokeniser r2 = r0.tokens
            long r14 = r14 - r4
            r2.seek(r14)
            com.itextpdf.text.pdf.PRTokeniser r2 = r0.tokens
            int r2 = r2.read()
            r3 = 10
            if (r2 != r3) goto L_0x00d5
            long r12 = r12 - r4
        L_0x00d5:
            int r2 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r2 >= 0) goto L_0x00da
            goto L_0x00db
        L_0x00da:
            r10 = r12
        L_0x00db:
            int r2 = (int) r10
            r1.setLength(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfReader.checkPRStreamLength(com.itextpdf.text.pdf.PRStream):void");
    }

    /* access modifiers changed from: protected */
    public void readObjStm(PRStream pRStream, IntHashtable intHashtable) throws IOException {
        Object obj;
        if (pRStream != null) {
            int intValue = pRStream.getAsNumber(PdfName.FIRST).intValue();
            int intValue2 = pRStream.getAsNumber(PdfName.f610N).intValue();
            byte[] streamBytes = getStreamBytes(pRStream, this.tokens.getFile());
            PRTokeniser pRTokeniser = this.tokens;
            this.tokens = new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(streamBytes)));
            try {
                int[] iArr = new int[intValue2];
                int[] iArr2 = new int[intValue2];
                boolean z = true;
                int i = 0;
                while (true) {
                    if (i >= intValue2) {
                        break;
                    }
                    z = this.tokens.nextToken();
                    if (!z) {
                        break;
                    } else if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
                        break;
                    } else {
                        iArr2[i] = this.tokens.intValue();
                        z = this.tokens.nextToken();
                        if (!z) {
                            break;
                        } else if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
                            break;
                        } else {
                            iArr[i] = this.tokens.intValue() + intValue;
                            i++;
                        }
                    }
                }
                z = false;
                if (z) {
                    for (int i2 = 0; i2 < intValue2; i2++) {
                        if (intHashtable.containsKey(i2)) {
                            this.tokens.seek((long) iArr[i2]);
                            this.tokens.nextToken();
                            if (this.tokens.getTokenType() == PRTokeniser.TokenType.NUMBER) {
                                obj = new PdfNumber(this.tokens.getStringValue());
                            } else {
                                this.tokens.seek((long) iArr[i2]);
                                obj = readPRObject();
                            }
                            this.xrefObj.set(iArr2[i2], obj);
                        }
                    }
                    return;
                }
                throw new InvalidPdfException(MessageLocalization.getComposedMessage("error.reading.objstm", new Object[0]));
            } finally {
                this.tokens = pRTokeniser;
            }
        }
    }

    public static PdfObject killIndirect(PdfObject pdfObject) {
        if (pdfObject == null || pdfObject.isNull()) {
            return null;
        }
        PdfObject pdfObjectRelease = getPdfObjectRelease(pdfObject);
        if (pdfObject.isIndirect()) {
            PRIndirectReference pRIndirectReference = (PRIndirectReference) pdfObject;
            PdfReader reader = pRIndirectReference.getReader();
            int number = pRIndirectReference.getNumber();
            reader.xrefObj.set(number, (Object) null);
            if (reader.partial) {
                reader.xref[number * 2] = -1;
            }
        }
        return pdfObjectRelease;
    }

    private void ensureXrefSize(int i) {
        if (i != 0) {
            long[] jArr = this.xref;
            if (jArr == null) {
                this.xref = new long[i];
            } else if (jArr.length < i) {
                long[] jArr2 = new long[i];
                System.arraycopy(jArr, 0, jArr2, 0, jArr.length);
                this.xref = jArr2;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void readXref() throws IOException {
        this.hybridXref = false;
        this.newXrefType = false;
        PRTokeniser pRTokeniser = this.tokens;
        pRTokeniser.seek(pRTokeniser.getStartxref());
        this.tokens.nextToken();
        if (this.tokens.getStringValue().equals("startxref")) {
            this.tokens.nextToken();
            if (this.tokens.getTokenType() == PRTokeniser.TokenType.NUMBER) {
                long longValue = this.tokens.longValue();
                this.lastXref = longValue;
                this.eofPos = this.tokens.getFilePointer();
                try {
                    if (readXRefStream(longValue)) {
                        this.newXrefType = true;
                        return;
                    }
                } catch (Exception unused) {
                }
                this.xref = null;
                this.tokens.seek(longValue);
                PdfDictionary readXrefSection = readXrefSection();
                this.trailer = readXrefSection;
                while (true) {
                    PdfNumber pdfNumber = (PdfNumber) readXrefSection.get(PdfName.PREV);
                    if (pdfNumber != null) {
                        if (pdfNumber.longValue() != longValue) {
                            longValue = pdfNumber.longValue();
                            this.tokens.seek(longValue);
                            readXrefSection = readXrefSection();
                        } else {
                            throw new InvalidPdfException(MessageLocalization.getComposedMessage("trailer.prev.entry.points.to.its.own.cross.reference.section", new Object[0]));
                        }
                    } else {
                        return;
                    }
                }
            } else {
                throw new InvalidPdfException(MessageLocalization.getComposedMessage("startxref.is.not.followed.by.a.number", new Object[0]));
            }
        } else {
            throw new InvalidPdfException(MessageLocalization.getComposedMessage("startxref.not.found", new Object[0]));
        }
    }

    /* access modifiers changed from: protected */
    public PdfDictionary readXrefSection() throws IOException {
        this.tokens.nextValidToken();
        if (!this.tokens.getStringValue().equals("xref")) {
            this.tokens.throwError(MessageLocalization.getComposedMessage("xref.subsection.not.found", new Object[0]));
        }
        while (true) {
            this.tokens.nextValidToken();
            if (this.tokens.getStringValue().equals("trailer")) {
                break;
            }
            if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
                this.tokens.throwError(MessageLocalization.getComposedMessage("object.number.of.the.first.object.in.this.xref.subsection.not.found", new Object[0]));
            }
            int intValue = this.tokens.intValue();
            this.tokens.nextValidToken();
            if (this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
                this.tokens.throwError(MessageLocalization.getComposedMessage("number.of.entries.in.this.xref.subsection.not.found", new Object[0]));
            }
            int intValue2 = this.tokens.intValue() + intValue;
            if (intValue == 1) {
                long filePointer = this.tokens.getFilePointer();
                this.tokens.nextValidToken();
                long longValue = this.tokens.longValue();
                this.tokens.nextValidToken();
                int intValue3 = this.tokens.intValue();
                if (longValue == 0 && intValue3 == 65535) {
                    intValue--;
                    intValue2--;
                }
                this.tokens.seek(filePointer);
            }
            ensureXrefSize(intValue2 * 2);
            while (intValue < intValue2) {
                this.tokens.nextValidToken();
                long longValue2 = this.tokens.longValue();
                this.tokens.nextValidToken();
                this.tokens.intValue();
                this.tokens.nextValidToken();
                int i = intValue * 2;
                if (this.tokens.getStringValue().equals("n")) {
                    long[] jArr = this.xref;
                    if (jArr[i] == 0 && jArr[i + 1] == 0) {
                        jArr[i] = longValue2;
                    }
                } else if (this.tokens.getStringValue().equals("f")) {
                    long[] jArr2 = this.xref;
                    if (jArr2[i] == 0 && jArr2[i + 1] == 0) {
                        jArr2[i] = -1;
                    }
                } else {
                    this.tokens.throwError(MessageLocalization.getComposedMessage("invalid.cross.reference.entry.in.this.xref.subsection", new Object[0]));
                }
                intValue++;
            }
        }
        PdfDictionary pdfDictionary = (PdfDictionary) readPRObject();
        ensureXrefSize(((PdfNumber) pdfDictionary.get(PdfName.SIZE)).intValue() * 2);
        PdfObject pdfObject = pdfDictionary.get(PdfName.XREFSTM);
        if (pdfObject != null && pdfObject.isNumber()) {
            try {
                readXRefStream((long) ((PdfNumber) pdfObject).intValue());
                this.newXrefType = true;
                this.hybridXref = true;
            } catch (IOException e) {
                this.xref = null;
                throw e;
            }
        }
        return pdfDictionary;
    }

    /* access modifiers changed from: protected */
    public boolean readXRefStream(long j) throws IOException {
        PdfArray pdfArray;
        long j2;
        int i;
        int i2;
        int[] iArr;
        this.tokens.seek(j);
        char c = 0;
        if (!this.tokens.nextToken() || this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER) {
            return false;
        }
        int intValue = this.tokens.intValue();
        if (!this.tokens.nextToken() || this.tokens.getTokenType() != PRTokeniser.TokenType.NUMBER || !this.tokens.nextToken() || !this.tokens.getStringValue().equals("obj")) {
            return false;
        }
        PdfObject readPRObject = readPRObject();
        if (!readPRObject.isStream()) {
            return false;
        }
        PRStream pRStream = (PRStream) readPRObject;
        if (!PdfName.XREF.equals(pRStream.get(PdfName.TYPE))) {
            return false;
        }
        if (this.trailer == null) {
            PdfDictionary pdfDictionary = new PdfDictionary();
            this.trailer = pdfDictionary;
            pdfDictionary.putAll(pRStream);
        }
        pRStream.setLength(((PdfNumber) pRStream.get(PdfName.LENGTH)).intValue());
        int intValue2 = ((PdfNumber) pRStream.get(PdfName.SIZE)).intValue();
        PdfObject pdfObject = pRStream.get(PdfName.INDEX);
        char c2 = 1;
        if (pdfObject == null) {
            pdfArray = new PdfArray();
            pdfArray.add(new int[]{0, intValue2});
        } else {
            pdfArray = (PdfArray) pdfObject;
        }
        PdfArray pdfArray2 = (PdfArray) pRStream.get(PdfName.f668W);
        PdfObject pdfObject2 = pRStream.get(PdfName.PREV);
        long longValue = pdfObject2 != null ? ((PdfNumber) pdfObject2).longValue() : -1;
        ensureXrefSize(intValue2 * 2);
        if (this.objStmMark == null && !this.partial) {
            this.objStmMark = new HashMap<>();
        }
        if (this.objStmToOffset == null && this.partial) {
            this.objStmToOffset = new LongHashtable();
        }
        byte[] streamBytes = getStreamBytes(pRStream, this.tokens.getFile());
        int[] iArr2 = new int[3];
        for (int i3 = 0; i3 < 3; i3++) {
            iArr2[i3] = pdfArray2.getAsNumber(i3).intValue();
        }
        int i4 = 0;
        int i5 = 0;
        while (i4 < pdfArray.size()) {
            int intValue3 = pdfArray.getAsNumber(i4).intValue();
            int intValue4 = pdfArray.getAsNumber(i4 + 1).intValue();
            ensureXrefSize((intValue3 + intValue4) * 2);
            while (true) {
                int i6 = intValue4 - 1;
                if (intValue4 <= 0) {
                    break;
                }
                if (iArr2[c] > 0) {
                    int i7 = 0;
                    i = 0;
                    while (i7 < iArr2[c]) {
                        int i8 = (i << 8) + (streamBytes[i5] & UByte.MAX_VALUE);
                        i7++;
                        i5++;
                        i = i8;
                    }
                } else {
                    i = 1;
                }
                byte[] bArr = streamBytes;
                long j3 = 0;
                int i9 = 0;
                while (i9 < iArr2[c2]) {
                    j3 = (j3 << 8) + ((long) (bArr[i5] & UByte.MAX_VALUE));
                    i9++;
                    i5++;
                    c2 = 1;
                }
                PdfArray pdfArray3 = pdfArray;
                int i10 = 0;
                int i11 = 0;
                char c3 = 2;
                while (i10 < iArr2[c3]) {
                    int i12 = (i11 << 8) + (bArr[i5] & UByte.MAX_VALUE);
                    i10++;
                    i5++;
                    c3 = 2;
                    i11 = i12;
                }
                int i13 = intValue3 * 2;
                long[] jArr = this.xref;
                if (jArr[i13] == 0) {
                    int i14 = i13 + 1;
                    if (jArr[i14] == 0) {
                        if (i != 0) {
                            i2 = i5;
                            if (i != 1) {
                                if (i == 2) {
                                    iArr = iArr2;
                                    jArr[i13] = (long) i11;
                                    jArr[i14] = j3;
                                    if (this.partial) {
                                        this.objStmToOffset.put(j3, 0);
                                    } else {
                                        Integer valueOf = Integer.valueOf((int) j3);
                                        IntHashtable intHashtable = this.objStmMark.get(valueOf);
                                        if (intHashtable == null) {
                                            IntHashtable intHashtable2 = new IntHashtable();
                                            intHashtable2.put(i11, 1);
                                            this.objStmMark.put(valueOf, intHashtable2);
                                        } else {
                                            intHashtable.put(i11, 1);
                                        }
                                    }
                                }
                                iArr = iArr2;
                            } else {
                                iArr = iArr2;
                                jArr[i13] = j3;
                            }
                        } else {
                            i2 = i5;
                            iArr = iArr2;
                            jArr[i13] = -1;
                        }
                        intValue3++;
                        iArr2 = iArr;
                        streamBytes = bArr;
                        pdfArray = pdfArray3;
                        i5 = i2;
                        c = 0;
                        c2 = 1;
                        intValue4 = i6;
                    }
                }
                i2 = i5;
                iArr = iArr2;
                intValue3++;
                iArr2 = iArr;
                streamBytes = bArr;
                pdfArray = pdfArray3;
                i5 = i2;
                c = 0;
                c2 = 1;
                intValue4 = i6;
            }
            byte[] bArr2 = streamBytes;
            PdfArray pdfArray4 = pdfArray;
            int[] iArr3 = iArr2;
            i4 += 2;
            c = 0;
            c2 = 1;
        }
        int i15 = intValue * 2;
        int i16 = i15 + 1;
        long[] jArr2 = this.xref;
        if (i16 < jArr2.length && jArr2[i15] == 0 && jArr2[i16] == 0) {
            j2 = -1;
            jArr2[i15] = -1;
        } else {
            j2 = -1;
        }
        if (longValue == j2) {
            return true;
        }
        return readXRefStream(longValue);
    }

    /* access modifiers changed from: protected */
    public void rebuildXref() throws IOException {
        int i = 0;
        this.hybridXref = false;
        this.newXrefType = false;
        long j = 0;
        this.tokens.seek(0);
        long[][] jArr = new long[1024][];
        String str = null;
        this.trailer = null;
        byte[] bArr = new byte[64];
        while (true) {
            long filePointer = this.tokens.getFilePointer();
            if (!this.tokens.readLineSegment(bArr, true)) {
                break;
            }
            if (bArr[i] == 116) {
                if (PdfEncodings.convertToString(bArr, str).startsWith("trailer")) {
                    this.tokens.seek(filePointer);
                    this.tokens.nextToken();
                    long filePointer2 = this.tokens.getFilePointer();
                    try {
                        PdfDictionary pdfDictionary = (PdfDictionary) readPRObject();
                        if (pdfDictionary.get(PdfName.ROOT) != null) {
                            this.trailer = pdfDictionary;
                        } else {
                            this.tokens.seek(filePointer2);
                        }
                    } catch (Exception unused) {
                        this.tokens.seek(filePointer2);
                    }
                }
            } else if (bArr[i] >= 48 && bArr[i] <= 57) {
                long[] checkObjectStart = PRTokeniser.checkObjectStart(bArr);
                if (checkObjectStart != null) {
                    long j2 = checkObjectStart[i];
                    long j3 = checkObjectStart[1];
                    long[][] jArr2 = jArr;
                    if (j2 >= ((long) jArr.length)) {
                        long[][] jArr3 = new long[((int) (2 * j2))][];
                        System.arraycopy(jArr2, 0, jArr3, 0, (int) j);
                        jArr = jArr3;
                    } else {
                        jArr = jArr2;
                    }
                    if (j2 >= j) {
                        j = 1 + j2;
                    }
                    int i2 = (int) j2;
                    if (jArr[i2] == null || j3 >= jArr[i2][1]) {
                        checkObjectStart[0] = filePointer;
                        jArr[i2] = checkObjectStart;
                    }
                    i = 0;
                    str = null;
                }
            }
            jArr = jArr;
            i = 0;
            str = null;
        }
        if (this.trailer != null) {
            this.xref = new long[((int) (2 * j))];
            for (int i3 = 0; ((long) i3) < j; i3++) {
                long[] jArr4 = jArr[i3];
                if (jArr4 != null) {
                    this.xref[i3 * 2] = jArr4[i];
                }
            }
            return;
        }
        throw new InvalidPdfException(MessageLocalization.getComposedMessage("trailer.not.found", new Object[i]));
    }

    /* access modifiers changed from: protected */
    public PdfDictionary readDictionary() throws IOException {
        PdfDictionary pdfDictionary = new PdfDictionary();
        while (true) {
            this.tokens.nextValidToken();
            if (this.tokens.getTokenType() == PRTokeniser.TokenType.END_DIC) {
                return pdfDictionary;
            }
            if (this.tokens.getTokenType() != PRTokeniser.TokenType.NAME) {
                PRTokeniser pRTokeniser = this.tokens;
                pRTokeniser.throwError(MessageLocalization.getComposedMessage("dictionary.key.1.is.not.a.name", pRTokeniser.getStringValue()));
            }
            PdfName pdfName = new PdfName(this.tokens.getStringValue(), false);
            PdfObject readPRObject = readPRObject();
            int i = -readPRObject.type();
            if (i == PRTokeniser.TokenType.END_DIC.ordinal()) {
                this.tokens.throwError(MessageLocalization.getComposedMessage("unexpected.gt.gt", new Object[0]));
            }
            if (i == PRTokeniser.TokenType.END_ARRAY.ordinal()) {
                this.tokens.throwError(MessageLocalization.getComposedMessage("unexpected.close.bracket", new Object[0]));
            }
            pdfDictionary.put(pdfName, readPRObject);
        }
    }

    /* access modifiers changed from: protected */
    public PdfArray readArray() throws IOException {
        PdfArray pdfArray = new PdfArray();
        while (true) {
            PdfObject readPRObject = readPRObject();
            int i = -readPRObject.type();
            if (i == PRTokeniser.TokenType.END_ARRAY.ordinal()) {
                return pdfArray;
            }
            if (i == PRTokeniser.TokenType.END_DIC.ordinal()) {
                this.tokens.throwError(MessageLocalization.getComposedMessage("unexpected.gt.gt", new Object[0]));
            }
            pdfArray.add(readPRObject);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00e9 A[LOOP:1: B:34:0x00e9->B:72:0x00e9, LOOP_START] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0124  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.pdf.PdfObject readPRObject() throws java.io.IOException {
        /*
            r6 = this;
            com.itextpdf.text.pdf.PRTokeniser r0 = r6.tokens
            r0.nextValidToken()
            com.itextpdf.text.pdf.PRTokeniser r0 = r6.tokens
            com.itextpdf.text.pdf.PRTokeniser$TokenType r0 = r0.getTokenType()
            int[] r1 = com.itextpdf.text.pdf.PdfReader.C15211.$SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType
            int r2 = r0.ordinal()
            r1 = r1[r2]
            r2 = 0
            r3 = 1
            switch(r1) {
                case 1: goto L_0x00b2;
                case 2: goto L_0x00a3;
                case 3: goto L_0x0097;
                case 4: goto L_0x0072;
                case 5: goto L_0x0051;
                case 6: goto L_0x003f;
                case 7: goto L_0x0030;
                default: goto L_0x0018;
            }
        L_0x0018:
            com.itextpdf.text.pdf.PRTokeniser r1 = r6.tokens
            java.lang.String r1 = r1.getStringValue()
            java.lang.String r4 = "null"
            boolean r4 = r4.equals(r1)
            if (r4 == 0) goto L_0x012d
            int r0 = r6.readDepth
            if (r0 != 0) goto L_0x012a
            com.itextpdf.text.pdf.PdfNull r0 = new com.itextpdf.text.pdf.PdfNull
            r0.<init>()
            return r0
        L_0x0030:
            java.io.IOException r0 = new java.io.IOException
            java.lang.Object[] r1 = new java.lang.Object[r2]
            java.lang.String r2 = "unexpected.end.of.file"
            java.lang.String r1 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r2, (java.lang.Object[]) r1)
            r0.<init>(r1)
            throw r0
        L_0x003f:
            com.itextpdf.text.pdf.PRTokeniser r0 = r6.tokens
            int r0 = r0.getReference()
            com.itextpdf.text.pdf.PRIndirectReference r1 = new com.itextpdf.text.pdf.PRIndirectReference
            com.itextpdf.text.pdf.PRTokeniser r2 = r6.tokens
            int r2 = r2.getGeneration()
            r1.<init>(r6, r0, r2)
            return r1
        L_0x0051:
            java.util.Map<java.lang.String, com.itextpdf.text.pdf.PdfName> r0 = com.itextpdf.text.pdf.PdfName.staticNames
            com.itextpdf.text.pdf.PRTokeniser r1 = r6.tokens
            java.lang.String r1 = r1.getStringValue()
            java.lang.Object r0 = r0.get(r1)
            com.itextpdf.text.pdf.PdfName r0 = (com.itextpdf.text.pdf.PdfName) r0
            int r1 = r6.readDepth
            if (r1 <= 0) goto L_0x0066
            if (r0 == 0) goto L_0x0066
            return r0
        L_0x0066:
            com.itextpdf.text.pdf.PdfName r0 = new com.itextpdf.text.pdf.PdfName
            com.itextpdf.text.pdf.PRTokeniser r1 = r6.tokens
            java.lang.String r1 = r1.getStringValue()
            r0.<init>(r1, r2)
            return r0
        L_0x0072:
            com.itextpdf.text.pdf.PdfString r0 = new com.itextpdf.text.pdf.PdfString
            com.itextpdf.text.pdf.PRTokeniser r1 = r6.tokens
            java.lang.String r1 = r1.getStringValue()
            r2 = 0
            r0.<init>(r1, r2)
            com.itextpdf.text.pdf.PRTokeniser r1 = r6.tokens
            boolean r1 = r1.isHexString()
            com.itextpdf.text.pdf.PdfString r0 = r0.setHexWriting(r1)
            int r1 = r6.objNum
            int r2 = r6.objGen
            r0.setObjNum(r1, r2)
            java.util.ArrayList<com.itextpdf.text.pdf.PdfString> r1 = r6.strings
            if (r1 == 0) goto L_0x0096
            r1.add(r0)
        L_0x0096:
            return r0
        L_0x0097:
            com.itextpdf.text.pdf.PdfNumber r0 = new com.itextpdf.text.pdf.PdfNumber
            com.itextpdf.text.pdf.PRTokeniser r1 = r6.tokens
            java.lang.String r1 = r1.getStringValue()
            r0.<init>((java.lang.String) r1)
            return r0
        L_0x00a3:
            int r0 = r6.readDepth
            int r0 = r0 + r3
            r6.readDepth = r0
            com.itextpdf.text.pdf.PdfArray r0 = r6.readArray()
            int r1 = r6.readDepth
            int r1 = r1 - r3
            r6.readDepth = r1
            return r0
        L_0x00b2:
            int r0 = r6.readDepth
            int r0 = r0 + r3
            r6.readDepth = r0
            com.itextpdf.text.pdf.PdfDictionary r0 = r6.readDictionary()
            int r1 = r6.readDepth
            int r1 = r1 - r3
            r6.readDepth = r1
            com.itextpdf.text.pdf.PRTokeniser r1 = r6.tokens
            long r1 = r1.getFilePointer()
        L_0x00c6:
            com.itextpdf.text.pdf.PRTokeniser r3 = r6.tokens
            boolean r3 = r3.nextToken()
            if (r3 == 0) goto L_0x00d8
            com.itextpdf.text.pdf.PRTokeniser r4 = r6.tokens
            com.itextpdf.text.pdf.PRTokeniser$TokenType r4 = r4.getTokenType()
            com.itextpdf.text.pdf.PRTokeniser$TokenType r5 = com.itextpdf.text.pdf.PRTokeniser.TokenType.COMMENT
            if (r4 == r5) goto L_0x00c6
        L_0x00d8:
            if (r3 == 0) goto L_0x0124
            com.itextpdf.text.pdf.PRTokeniser r3 = r6.tokens
            java.lang.String r3 = r3.getStringValue()
            java.lang.String r4 = "stream"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0124
        L_0x00e9:
            com.itextpdf.text.pdf.PRTokeniser r1 = r6.tokens
            int r1 = r1.read()
            r2 = 32
            if (r1 == r2) goto L_0x00e9
            r2 = 9
            if (r1 == r2) goto L_0x00e9
            if (r1 == 0) goto L_0x00e9
            r2 = 12
            if (r1 == r2) goto L_0x00e9
            r2 = 10
            if (r1 == r2) goto L_0x0107
            com.itextpdf.text.pdf.PRTokeniser r1 = r6.tokens
            int r1 = r1.read()
        L_0x0107:
            if (r1 == r2) goto L_0x010e
            com.itextpdf.text.pdf.PRTokeniser r2 = r6.tokens
            r2.backOnePosition(r1)
        L_0x010e:
            com.itextpdf.text.pdf.PRStream r1 = new com.itextpdf.text.pdf.PRStream
            com.itextpdf.text.pdf.PRTokeniser r2 = r6.tokens
            long r2 = r2.getFilePointer()
            r1.<init>((com.itextpdf.text.pdf.PdfReader) r6, (long) r2)
            r1.putAll(r0)
            int r0 = r6.objNum
            int r2 = r6.objGen
            r1.setObjNum(r0, r2)
            return r1
        L_0x0124:
            com.itextpdf.text.pdf.PRTokeniser r3 = r6.tokens
            r3.seek(r1)
            return r0
        L_0x012a:
            com.itextpdf.text.pdf.PdfNull r0 = com.itextpdf.text.pdf.PdfNull.PDFNULL
            return r0
        L_0x012d:
            java.lang.String r4 = "true"
            boolean r4 = r4.equals(r1)
            if (r4 == 0) goto L_0x0143
            int r0 = r6.readDepth
            if (r0 != 0) goto L_0x0140
            com.itextpdf.text.pdf.PdfBoolean r0 = new com.itextpdf.text.pdf.PdfBoolean
            r0.<init>((boolean) r3)
            return r0
        L_0x0140:
            com.itextpdf.text.pdf.PdfBoolean r0 = com.itextpdf.text.pdf.PdfBoolean.PDFTRUE
            return r0
        L_0x0143:
            java.lang.String r3 = "false"
            boolean r1 = r3.equals(r1)
            if (r1 == 0) goto L_0x0158
            int r0 = r6.readDepth
            if (r0 != 0) goto L_0x0155
            com.itextpdf.text.pdf.PdfBoolean r0 = new com.itextpdf.text.pdf.PdfBoolean
            r0.<init>((boolean) r2)
            return r0
        L_0x0155:
            com.itextpdf.text.pdf.PdfBoolean r0 = com.itextpdf.text.pdf.PdfBoolean.PDFFALSE
            return r0
        L_0x0158:
            com.itextpdf.text.pdf.PdfLiteral r1 = new com.itextpdf.text.pdf.PdfLiteral
            int r0 = r0.ordinal()
            int r0 = -r0
            com.itextpdf.text.pdf.PRTokeniser r2 = r6.tokens
            java.lang.String r2 = r2.getStringValue()
            r1.<init>((int) r0, (java.lang.String) r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfReader.readPRObject():com.itextpdf.text.pdf.PdfObject");
    }

    /* renamed from: com.itextpdf.text.pdf.PdfReader$1 */
    static /* synthetic */ class C15211 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|(3:13|14|16)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.itextpdf.text.pdf.PRTokeniser$TokenType[] r0 = com.itextpdf.text.pdf.PRTokeniser.TokenType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType = r0
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.START_DIC     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.START_ARRAY     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.NUMBER     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.STRING     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType     // Catch:{ NoSuchFieldError -> 0x003e }
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.NAME     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.REF     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$PRTokeniser$TokenType     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.itextpdf.text.pdf.PRTokeniser$TokenType r1 = com.itextpdf.text.pdf.PRTokeniser.TokenType.ENDOFFILE     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfReader.C15211.<clinit>():void");
        }
    }

    public static byte[] FlateDecode(byte[] bArr) {
        byte[] FlateDecode = FlateDecode(bArr, true);
        return FlateDecode == null ? FlateDecode(bArr, false) : FlateDecode;
    }

    public static byte[] decodePredictor(byte[] bArr, PdfObject pdfObject) {
        byte[] bArr2 = bArr;
        if (pdfObject == null || !pdfObject.isDictionary()) {
            return bArr2;
        }
        PdfDictionary pdfDictionary = (PdfDictionary) pdfObject;
        PdfObject pdfObject2 = getPdfObject(pdfDictionary.get(PdfName.PREDICTOR));
        if (pdfObject2 == null || !pdfObject2.isNumber()) {
            return bArr2;
        }
        int intValue = ((PdfNumber) pdfObject2).intValue();
        if (intValue < 10 && intValue != 2) {
            return bArr2;
        }
        PdfObject pdfObject3 = getPdfObject(pdfDictionary.get(PdfName.COLUMNS));
        int intValue2 = (pdfObject3 == null || !pdfObject3.isNumber()) ? 1 : ((PdfNumber) pdfObject3).intValue();
        PdfObject pdfObject4 = getPdfObject(pdfDictionary.get(PdfName.COLORS));
        int intValue3 = (pdfObject4 == null || !pdfObject4.isNumber()) ? 1 : ((PdfNumber) pdfObject4).intValue();
        PdfObject pdfObject5 = getPdfObject(pdfDictionary.get(PdfName.BITSPERCOMPONENT));
        int intValue4 = (pdfObject5 == null || !pdfObject5.isNumber()) ? 8 : ((PdfNumber) pdfObject5).intValue();
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr2));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr2.length);
        int i = (intValue3 * intValue4) / 8;
        int i2 = (((intValue3 * intValue2) * intValue4) + 7) / 8;
        byte[] bArr3 = new byte[i2];
        byte[] bArr4 = new byte[i2];
        if (intValue == 2) {
            if (intValue4 == 8) {
                int length = bArr2.length / i2;
                for (int i3 = 0; i3 < length; i3++) {
                    int i4 = i3 * i2;
                    for (int i5 = i + 0; i5 < i2; i5++) {
                        int i6 = i4 + i5;
                        bArr2[i6] = (byte) (bArr2[i6] + bArr2[i6 - i]);
                    }
                }
            }
            return bArr2;
        }
        while (true) {
            try {
                int read = dataInputStream.read();
                if (read < 0) {
                    return byteArrayOutputStream.toByteArray();
                }
                dataInputStream.readFully(bArr3, 0, i2);
                if (read != 0) {
                    if (read == 1) {
                        for (int i7 = i; i7 < i2; i7++) {
                            bArr3[i7] = (byte) (bArr3[i7] + bArr3[i7 - i]);
                        }
                    } else if (read == 2) {
                        for (int i8 = 0; i8 < i2; i8++) {
                            bArr3[i8] = (byte) (bArr3[i8] + bArr4[i8]);
                        }
                    } else if (read == 3) {
                        for (int i9 = 0; i9 < i; i9++) {
                            bArr3[i9] = (byte) (bArr3[i9] + (bArr4[i9] / 2));
                        }
                        for (int i10 = i; i10 < i2; i10++) {
                            bArr3[i10] = (byte) (bArr3[i10] + (((bArr3[i10 - i] & UByte.MAX_VALUE) + (bArr4[i10] & UByte.MAX_VALUE)) / 2));
                        }
                    } else if (read == 4) {
                        for (int i11 = 0; i11 < i; i11++) {
                            bArr3[i11] = (byte) (bArr3[i11] + bArr4[i11]);
                        }
                        for (int i12 = i; i12 < i2; i12++) {
                            int i13 = i12 - i;
                            byte b = bArr3[i13] & UByte.MAX_VALUE;
                            byte b2 = bArr4[i12] & UByte.MAX_VALUE;
                            byte b3 = bArr4[i13] & UByte.MAX_VALUE;
                            int i14 = (b + b2) - b3;
                            int abs = Math.abs(i14 - b);
                            int abs2 = Math.abs(i14 - b2);
                            int abs3 = Math.abs(i14 - b3);
                            if (abs > abs2 || abs > abs3) {
                                b = abs2 <= abs3 ? b2 : b3;
                            }
                            bArr3[i12] = (byte) (bArr3[i12] + ((byte) b));
                        }
                    } else {
                        throw new RuntimeException(MessageLocalization.getComposedMessage("png.filter.unknown", new Object[0]));
                    }
                }
                try {
                    byteArrayOutputStream.write(bArr3);
                } catch (IOException unused) {
                }
                byte[] bArr5 = bArr4;
                bArr4 = bArr3;
                bArr3 = bArr5;
            } catch (Exception unused2) {
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static byte[] FlateDecode(byte[] bArr, boolean z) {
        InflaterInputStream inflaterInputStream = new InflaterInputStream(new ByteArrayInputStream(bArr));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr2 = new byte[(z ? 4092 : 1)];
        while (true) {
            try {
                int read = inflaterInputStream.read(bArr2);
                if (read >= 0) {
                    byteArrayOutputStream.write(bArr2, 0, read);
                } else {
                    inflaterInputStream.close();
                    byteArrayOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            } catch (Exception unused) {
                if (z) {
                    return null;
                }
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static byte[] ASCIIHexDecode(byte[] bArr) {
        byte b;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        boolean z = true;
        int i = 0;
        int i2 = 0;
        while (i < bArr.length && (b = bArr[i] & UByte.MAX_VALUE) != 62) {
            if (!PRTokeniser.isWhitespace(b)) {
                int hex = PRTokeniser.getHex(b);
                if (hex != -1) {
                    if (z) {
                        i2 = hex;
                    } else {
                        byteArrayOutputStream.write((byte) ((i2 << 4) + hex));
                    }
                    z = !z;
                } else {
                    throw new RuntimeException(MessageLocalization.getComposedMessage("illegal.character.in.asciihexdecode", new Object[0]));
                }
            }
            i++;
        }
        if (!z) {
            byteArrayOutputStream.write((byte) (i2 << 4));
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] ASCII85Decode(byte[] bArr) {
        byte b;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int[] iArr = new int[5];
        int i = 0;
        int i2 = 0;
        while (i < bArr.length && (b = bArr[i] & UByte.MAX_VALUE) != 126) {
            if (!PRTokeniser.isWhitespace(b)) {
                if (b == 122 && i2 == 0) {
                    byteArrayOutputStream.write(0);
                    byteArrayOutputStream.write(0);
                    byteArrayOutputStream.write(0);
                    byteArrayOutputStream.write(0);
                } else if (b < 33 || b > 117) {
                    throw new RuntimeException(MessageLocalization.getComposedMessage("illegal.character.in.ascii85decode", new Object[0]));
                } else {
                    iArr[i2] = b - 33;
                    i2++;
                    if (i2 == 5) {
                        int i3 = 0;
                        for (int i4 = 0; i4 < 5; i4++) {
                            i3 = (i3 * 85) + iArr[i4];
                        }
                        byteArrayOutputStream.write((byte) (i3 >> 24));
                        byteArrayOutputStream.write((byte) (i3 >> 16));
                        byteArrayOutputStream.write((byte) (i3 >> 8));
                        byteArrayOutputStream.write((byte) i3);
                        i2 = 0;
                    }
                }
            }
            i++;
        }
        if (i2 == 2) {
            byteArrayOutputStream.write((byte) (((((((((iArr[0] * 85) * 85) * 85) * 85) + (((iArr[1] * 85) * 85) * 85)) + 614125) + C1633R2.styleable.StateListDrawable_android_variablePadding) + 85) >> 24));
        } else if (i2 == 3) {
            int i5 = (iArr[0] * 85 * 85 * 85 * 85) + (iArr[1] * 85 * 85 * 85) + (iArr[2] * 85 * 85) + C1633R2.styleable.StateListDrawable_android_variablePadding + 85;
            byteArrayOutputStream.write((byte) (i5 >> 24));
            byteArrayOutputStream.write((byte) (i5 >> 16));
        } else if (i2 == 4) {
            int i6 = (iArr[0] * 85 * 85 * 85 * 85) + (iArr[1] * 85 * 85 * 85) + (iArr[2] * 85 * 85) + (iArr[3] * 85) + 85;
            byteArrayOutputStream.write((byte) (i6 >> 24));
            byteArrayOutputStream.write((byte) (i6 >> 16));
            byteArrayOutputStream.write((byte) (i6 >> 8));
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] LZWDecode(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new LZWDecoder().decode(bArr, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public boolean isRebuilt() {
        return this.rebuilt;
    }

    public PdfDictionary getPageN(int i) {
        PdfDictionary pageN = this.pageRefs.getPageN(i);
        if (pageN == null) {
            return null;
        }
        if (this.appendable) {
            pageN.setIndRef(this.pageRefs.getPageOrigRef(i));
        }
        return pageN;
    }

    public PdfDictionary getPageNRelease(int i) {
        PdfDictionary pageN = getPageN(i);
        this.pageRefs.releasePage(i);
        return pageN;
    }

    public void releasePage(int i) {
        this.pageRefs.releasePage(i);
    }

    public void resetReleasePage() {
        this.pageRefs.resetReleasePage();
    }

    public PRIndirectReference getPageOrigRef(int i) {
        return this.pageRefs.getPageOrigRef(i);
    }

    public byte[] getPageContent(int i, RandomAccessFileOrArray randomAccessFileOrArray) throws IOException {
        PdfDictionary pageNRelease = getPageNRelease(i);
        if (pageNRelease == null) {
            return null;
        }
        PdfObject pdfObjectRelease = getPdfObjectRelease(pageNRelease.get(PdfName.CONTENTS));
        if (pdfObjectRelease == null) {
            return new byte[0];
        }
        if (pdfObjectRelease.isStream()) {
            return getStreamBytes((PRStream) pdfObjectRelease, randomAccessFileOrArray);
        }
        if (!pdfObjectRelease.isArray()) {
            return new byte[0];
        }
        PdfArray pdfArray = (PdfArray) pdfObjectRelease;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i2 = 0; i2 < pdfArray.size(); i2++) {
            PdfObject pdfObjectRelease2 = getPdfObjectRelease(pdfArray.getPdfObject(i2));
            if (pdfObjectRelease2 != null && pdfObjectRelease2.isStream()) {
                byteArrayOutputStream.write(getStreamBytes((PRStream) pdfObjectRelease2, randomAccessFileOrArray));
                if (i2 != pdfArray.size() - 1) {
                    byteArrayOutputStream.write(10);
                }
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: com.itextpdf.text.pdf.RandomAccessFileOrArray} */
    /* JADX WARNING: type inference failed for: r0v0, types: [byte[], com.itextpdf.text.pdf.RandomAccessFileOrArray] */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0058, code lost:
        if (r0 != 0) goto L_0x0068;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005a, code lost:
        r0 = ((com.itextpdf.text.pdf.PRStream) r3).getReader().getSafeFile();
        r0.reOpen();
        r0 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0068, code lost:
        r2.write(getStreamBytes((com.itextpdf.text.pdf.PRStream) r3, r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0077, code lost:
        if (r1 == (r5.size() - 1)) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0079, code lost:
        r2.write(10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x007e, code lost:
        r0 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x007e, code lost:
        r0 = r0;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] getPageContent(com.itextpdf.text.pdf.PdfDictionary r5) throws java.io.IOException {
        /*
            r0 = 0
            if (r5 != 0) goto L_0x0004
            return r0
        L_0x0004:
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.CONTENTS     // Catch:{ all -> 0x008e }
            com.itextpdf.text.pdf.PdfObject r5 = r5.get(r1)     // Catch:{ all -> 0x008e }
            com.itextpdf.text.pdf.PdfObject r5 = getPdfObjectRelease((com.itextpdf.text.pdf.PdfObject) r5)     // Catch:{ all -> 0x008e }
            r1 = 0
            if (r5 != 0) goto L_0x0014
            byte[] r5 = new byte[r1]     // Catch:{ all -> 0x008e }
            return r5
        L_0x0014:
            boolean r2 = r5.isStream()     // Catch:{ all -> 0x008e }
            if (r2 == 0) goto L_0x0034
            r1 = r5
            com.itextpdf.text.pdf.PRStream r1 = (com.itextpdf.text.pdf.PRStream) r1     // Catch:{ all -> 0x008e }
            com.itextpdf.text.pdf.PdfReader r1 = r1.getReader()     // Catch:{ all -> 0x008e }
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r1.getSafeFile()     // Catch:{ all -> 0x008e }
            r0.reOpen()     // Catch:{ all -> 0x008e }
            com.itextpdf.text.pdf.PRStream r5 = (com.itextpdf.text.pdf.PRStream) r5     // Catch:{ all -> 0x008e }
            byte[] r5 = getStreamBytes(r5, r0)     // Catch:{ all -> 0x008e }
            if (r0 == 0) goto L_0x0033
            r0.close()     // Catch:{ Exception -> 0x0033 }
        L_0x0033:
            return r5
        L_0x0034:
            boolean r2 = r5.isArray()     // Catch:{ all -> 0x008e }
            if (r2 == 0) goto L_0x008b
            com.itextpdf.text.pdf.PdfArray r5 = (com.itextpdf.text.pdf.PdfArray) r5     // Catch:{ all -> 0x008e }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x008e }
            r2.<init>()     // Catch:{ all -> 0x008e }
        L_0x0041:
            int r3 = r5.size()     // Catch:{ all -> 0x008e }
            if (r1 >= r3) goto L_0x0081
            com.itextpdf.text.pdf.PdfObject r3 = r5.getPdfObject(r1)     // Catch:{ all -> 0x008e }
            com.itextpdf.text.pdf.PdfObject r3 = getPdfObjectRelease((com.itextpdf.text.pdf.PdfObject) r3)     // Catch:{ all -> 0x008e }
            if (r3 == 0) goto L_0x007e
            boolean r4 = r3.isStream()     // Catch:{ all -> 0x008e }
            if (r4 != 0) goto L_0x0058
            goto L_0x007e
        L_0x0058:
            if (r0 != 0) goto L_0x0068
            r4 = r3
            com.itextpdf.text.pdf.PRStream r4 = (com.itextpdf.text.pdf.PRStream) r4     // Catch:{ all -> 0x008e }
            com.itextpdf.text.pdf.PdfReader r4 = r4.getReader()     // Catch:{ all -> 0x008e }
            com.itextpdf.text.pdf.RandomAccessFileOrArray r0 = r4.getSafeFile()     // Catch:{ all -> 0x008e }
            r0.reOpen()     // Catch:{ all -> 0x008e }
        L_0x0068:
            com.itextpdf.text.pdf.PRStream r3 = (com.itextpdf.text.pdf.PRStream) r3     // Catch:{ all -> 0x008e }
            byte[] r3 = getStreamBytes(r3, r0)     // Catch:{ all -> 0x008e }
            r2.write(r3)     // Catch:{ all -> 0x008e }
            int r3 = r5.size()     // Catch:{ all -> 0x008e }
            int r3 = r3 + -1
            if (r1 == r3) goto L_0x007e
            r3 = 10
            r2.write(r3)     // Catch:{ all -> 0x008e }
        L_0x007e:
            int r1 = r1 + 1
            goto L_0x0041
        L_0x0081:
            byte[] r5 = r2.toByteArray()     // Catch:{ all -> 0x008e }
            if (r0 == 0) goto L_0x008a
            r0.close()     // Catch:{ Exception -> 0x008a }
        L_0x008a:
            return r5
        L_0x008b:
            byte[] r5 = new byte[r1]     // Catch:{ all -> 0x008e }
            return r5
        L_0x008e:
            r5 = move-exception
            if (r0 == 0) goto L_0x0094
            r0.close()     // Catch:{ Exception -> 0x0094 }
        L_0x0094:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfReader.getPageContent(com.itextpdf.text.pdf.PdfDictionary):byte[]");
    }

    public PdfDictionary getPageResources(int i) {
        return getPageResources(getPageN(i));
    }

    public PdfDictionary getPageResources(PdfDictionary pdfDictionary) {
        return pdfDictionary.getAsDict(PdfName.RESOURCES);
    }

    public byte[] getPageContent(int i) throws IOException {
        RandomAccessFileOrArray safeFile = getSafeFile();
        try {
            safeFile.reOpen();
            return getPageContent(i, safeFile);
        } finally {
            try {
                safeFile.close();
            } catch (Exception unused) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public void killXref(PdfObject pdfObject) {
        if (pdfObject != null) {
            if (!(pdfObject instanceof PdfIndirectReference) || pdfObject.isIndirect()) {
                int type = pdfObject.type();
                if (type == 5) {
                    PdfArray pdfArray = (PdfArray) pdfObject;
                    for (int i = 0; i < pdfArray.size(); i++) {
                        killXref(pdfArray.getPdfObject(i));
                    }
                } else if (type == 6 || type == 7) {
                    PdfDictionary pdfDictionary = (PdfDictionary) pdfObject;
                    for (PdfName pdfName : pdfDictionary.getKeys()) {
                        killXref(pdfDictionary.get(pdfName));
                    }
                } else if (type == 10) {
                    int number = ((PRIndirectReference) pdfObject).getNumber();
                    this.xrefObj.set(number, (Object) null);
                    this.freeXref = number;
                    killXref(this.xrefObj.get(number));
                }
            }
        }
    }

    public void setPageContent(int i, byte[] bArr) {
        setPageContent(i, bArr, -1);
    }

    public void setPageContent(int i, byte[] bArr, int i2) {
        setPageContent(i, bArr, i2, false);
    }

    public void setPageContent(int i, byte[] bArr, int i2, boolean z) {
        PdfDictionary pageN = getPageN(i);
        if (pageN != null) {
            PdfObject pdfObject = pageN.get(PdfName.CONTENTS);
            this.freeXref = -1;
            if (z) {
                killXref(pdfObject);
            }
            if (this.freeXref == -1) {
                this.xrefObj.add((Object) null);
                this.freeXref = this.xrefObj.size() - 1;
            }
            pageN.put(PdfName.CONTENTS, new PRIndirectReference(this, this.freeXref));
            this.xrefObj.set(this.freeXref, new PRStream(this, bArr, i2));
        }
    }

    public static byte[] decodeBytes(byte[] bArr, PdfDictionary pdfDictionary) throws IOException {
        return decodeBytes(bArr, pdfDictionary, FilterHandlers.getDefaultFilterHandlers());
    }

    public static byte[] decodeBytes(byte[] bArr, PdfDictionary pdfDictionary, Map<PdfName, FilterHandlers.FilterHandler> map) throws IOException {
        PdfObject pdfObjectRelease = getPdfObjectRelease(pdfDictionary.get(PdfName.FILTER));
        ArrayList<PdfObject> arrayList = new ArrayList<>();
        if (pdfObjectRelease != null) {
            if (pdfObjectRelease.isName()) {
                arrayList.add(pdfObjectRelease);
            } else if (pdfObjectRelease.isArray()) {
                arrayList = ((PdfArray) pdfObjectRelease).getArrayList();
            }
        }
        ArrayList<PdfObject> arrayList2 = new ArrayList<>();
        PdfObject pdfObjectRelease2 = getPdfObjectRelease(pdfDictionary.get(PdfName.DECODEPARMS));
        if (pdfObjectRelease2 == null || (!pdfObjectRelease2.isDictionary() && !pdfObjectRelease2.isArray())) {
            pdfObjectRelease2 = getPdfObjectRelease(pdfDictionary.get(PdfName.f573DP));
        }
        if (pdfObjectRelease2 != null) {
            if (pdfObjectRelease2.isDictionary()) {
                arrayList2.add(pdfObjectRelease2);
            } else if (pdfObjectRelease2.isArray()) {
                arrayList2 = ((PdfArray) pdfObjectRelease2).getArrayList();
            }
        }
        int i = 0;
        while (i < arrayList.size()) {
            PdfName pdfName = (PdfName) arrayList.get(i);
            FilterHandlers.FilterHandler filterHandler = map.get(pdfName);
            if (filterHandler != null) {
                PdfDictionary pdfDictionary2 = null;
                if (i < arrayList2.size()) {
                    PdfObject pdfObject = getPdfObject(arrayList2.get(i));
                    if (pdfObject instanceof PdfDictionary) {
                        pdfDictionary2 = (PdfDictionary) pdfObject;
                    } else if (pdfObject != null && !(pdfObject instanceof PdfNull) && (!(pdfObject instanceof PdfLiteral) || !Arrays.equals("null".getBytes(), ((PdfLiteral) pdfObject).getBytes()))) {
                        throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("the.decode.parameter.type.1.is.not.supported", pdfObject.getClass().toString()));
                    }
                }
                bArr = filterHandler.decode(bArr, pdfName, pdfDictionary2, pdfDictionary);
                i++;
            } else {
                throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("the.filter.1.is.not.supported", pdfName));
            }
        }
        return bArr;
    }

    public static byte[] getStreamBytes(PRStream pRStream, RandomAccessFileOrArray randomAccessFileOrArray) throws IOException {
        return decodeBytes(getStreamBytesRaw(pRStream, randomAccessFileOrArray), pRStream);
    }

    public static byte[] getStreamBytes(PRStream pRStream) throws IOException {
        RandomAccessFileOrArray safeFile = pRStream.getReader().getSafeFile();
        try {
            safeFile.reOpen();
            return getStreamBytes(pRStream, safeFile);
        } finally {
            try {
                safeFile.close();
            } catch (Exception unused) {
            }
        }
    }

    public static byte[] getStreamBytesRaw(PRStream pRStream, RandomAccessFileOrArray randomAccessFileOrArray) throws IOException {
        PdfReader reader = pRStream.getReader();
        if (pRStream.getOffset() < 0) {
            return pRStream.getBytes();
        }
        byte[] bArr = new byte[pRStream.getLength()];
        randomAccessFileOrArray.seek(pRStream.getOffset());
        randomAccessFileOrArray.readFully(bArr);
        PdfEncryption decrypt2 = reader.getDecrypt();
        if (decrypt2 != null) {
            PdfObject pdfObjectRelease = getPdfObjectRelease(pRStream.get(PdfName.FILTER));
            ArrayList<PdfObject> arrayList = new ArrayList<>();
            if (pdfObjectRelease != null) {
                if (pdfObjectRelease.isName()) {
                    arrayList.add(pdfObjectRelease);
                } else if (pdfObjectRelease.isArray()) {
                    arrayList = ((PdfArray) pdfObjectRelease).getArrayList();
                }
            }
            boolean z = false;
            int i = 0;
            while (true) {
                if (i < arrayList.size()) {
                    PdfObject pdfObjectRelease2 = getPdfObjectRelease(arrayList.get(i));
                    if (pdfObjectRelease2 != null && pdfObjectRelease2.toString().equals("/Crypt")) {
                        z = true;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            if (!z) {
                decrypt2.setHashKey(pRStream.getObjNum(), pRStream.getObjGen());
                return decrypt2.decryptByteArray(bArr);
            }
        }
        return bArr;
    }

    public static byte[] getStreamBytesRaw(PRStream pRStream) throws IOException {
        RandomAccessFileOrArray safeFile = pRStream.getReader().getSafeFile();
        try {
            safeFile.reOpen();
            return getStreamBytesRaw(pRStream, safeFile);
        } finally {
            try {
                safeFile.close();
            } catch (Exception unused) {
            }
        }
    }

    public void eliminateSharedStreams() {
        PdfObject pdfObject;
        if (this.sharedStreams) {
            this.sharedStreams = false;
            if (this.pageRefs.size() != 1) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                IntHashtable intHashtable = new IntHashtable();
                for (int i = 1; i <= this.pageRefs.size(); i++) {
                    PdfDictionary pageN = this.pageRefs.getPageN(i);
                    if (!(pageN == null || (pdfObject = getPdfObject(pageN.get(PdfName.CONTENTS))) == null)) {
                        if (pdfObject.isStream()) {
                            PRIndirectReference pRIndirectReference = (PRIndirectReference) pageN.get(PdfName.CONTENTS);
                            if (intHashtable.containsKey(pRIndirectReference.getNumber())) {
                                arrayList.add(pRIndirectReference);
                                arrayList2.add(new PRStream((PRStream) pdfObject, (PdfDictionary) null));
                            } else {
                                intHashtable.put(pRIndirectReference.getNumber(), 1);
                            }
                        } else if (pdfObject.isArray()) {
                            PdfArray pdfArray = (PdfArray) pdfObject;
                            for (int i2 = 0; i2 < pdfArray.size(); i2++) {
                                PRIndirectReference pRIndirectReference2 = (PRIndirectReference) pdfArray.getPdfObject(i2);
                                if (intHashtable.containsKey(pRIndirectReference2.getNumber())) {
                                    arrayList.add(pRIndirectReference2);
                                    arrayList2.add(new PRStream((PRStream) getPdfObject((PdfObject) pRIndirectReference2), (PdfDictionary) null));
                                } else {
                                    intHashtable.put(pRIndirectReference2.getNumber(), 1);
                                }
                            }
                        }
                    }
                }
                if (!arrayList2.isEmpty()) {
                    for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                        this.xrefObj.add(arrayList2.get(i3));
                        ((PRIndirectReference) arrayList.get(i3)).setNumber(this.xrefObj.size() - 1, 0);
                    }
                }
            }
        }
    }

    public boolean isTampered() {
        return this.tampered;
    }

    public void setTampered(boolean z) {
        this.tampered = z;
        this.pageRefs.keepPages();
    }

    public byte[] getMetadata() throws IOException {
        PdfObject pdfObject = getPdfObject(this.catalog.get(PdfName.METADATA));
        if (!(pdfObject instanceof PRStream)) {
            return null;
        }
        RandomAccessFileOrArray safeFile = getSafeFile();
        try {
            safeFile.reOpen();
            return getStreamBytes((PRStream) pdfObject, safeFile);
        } finally {
            try {
                safeFile.close();
            } catch (Exception unused) {
            }
        }
    }

    public long getLastXref() {
        return this.lastXref;
    }

    public int getXrefSize() {
        return this.xrefObj.size();
    }

    public long getEofPos() {
        return this.eofPos;
    }

    public char getPdfVersion() {
        return this.pdfVersion;
    }

    public boolean isEncrypted() {
        return this.encrypted;
    }

    public long getPermissions() {
        return this.pValue;
    }

    public boolean is128Key() {
        return this.rValue == 3;
    }

    public PdfDictionary getTrailer() {
        return this.trailer;
    }

    /* access modifiers changed from: package-private */
    public PdfEncryption getDecrypt() {
        return this.decrypt;
    }

    static boolean equalsn(byte[] bArr, byte[] bArr2) {
        int length = bArr2.length;
        for (int i = 0; i < length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    static boolean existsName(PdfDictionary pdfDictionary, PdfName pdfName, PdfName pdfName2) {
        PdfObject pdfObjectRelease = getPdfObjectRelease(pdfDictionary.get(pdfName));
        if (pdfObjectRelease == null || !pdfObjectRelease.isName()) {
            return false;
        }
        return ((PdfName) pdfObjectRelease).equals(pdfName2);
    }

    static String getFontName(PdfDictionary pdfDictionary) {
        PdfObject pdfObjectRelease;
        if (pdfDictionary == null || (pdfObjectRelease = getPdfObjectRelease(pdfDictionary.get(PdfName.BASEFONT))) == null || !pdfObjectRelease.isName()) {
            return null;
        }
        return PdfName.decodeName(pdfObjectRelease.toString());
    }

    static String getSubsetPrefix(PdfDictionary pdfDictionary) {
        String fontName;
        if (pdfDictionary == null || (fontName = getFontName(pdfDictionary)) == null || fontName.length() < 8 || fontName.charAt(6) != '+') {
            return null;
        }
        for (int i = 0; i < 6; i++) {
            char charAt = fontName.charAt(i);
            if (charAt < 'A' || charAt > 'Z') {
                return null;
            }
        }
        return fontName;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0068, code lost:
        r6 = r6.getAsDict(0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int shuffleSubsetNames() {
        /*
            r12 = this;
            r0 = 0
            r1 = 1
            r2 = 0
        L_0x0003:
            java.util.ArrayList<com.itextpdf.text.pdf.PdfObject> r3 = r12.xrefObj
            int r3 = r3.size()
            if (r1 >= r3) goto L_0x0107
            com.itextpdf.text.pdf.PdfObject r3 = r12.getPdfObjectRelease((int) r1)
            if (r3 == 0) goto L_0x0103
            boolean r4 = r3.isDictionary()
            if (r4 != 0) goto L_0x0019
            goto L_0x0103
        L_0x0019:
            com.itextpdf.text.pdf.PdfDictionary r3 = (com.itextpdf.text.pdf.PdfDictionary) r3
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.TYPE
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.FONT
            boolean r4 = existsName(r3, r4, r5)
            if (r4 != 0) goto L_0x0027
            goto L_0x0103
        L_0x0027:
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.SUBTYPE
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.TYPE1
            boolean r4 = existsName(r3, r4, r5)
            r5 = 7
            if (r4 != 0) goto L_0x00c8
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.SUBTYPE
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.MMTYPE1
            boolean r4 = existsName(r3, r4, r6)
            if (r4 != 0) goto L_0x00c8
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.SUBTYPE
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.TRUETYPE
            boolean r4 = existsName(r3, r4, r6)
            if (r4 == 0) goto L_0x0048
            goto L_0x00c8
        L_0x0048:
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.SUBTYPE
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.TYPE0
            boolean r4 = existsName(r3, r4, r6)
            if (r4 == 0) goto L_0x0103
            java.lang.String r4 = getSubsetPrefix(r3)
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.DESCENDANTFONTS
            com.itextpdf.text.pdf.PdfArray r6 = r3.getAsArray(r6)
            if (r6 != 0) goto L_0x0060
            goto L_0x0103
        L_0x0060:
            boolean r7 = r6.isEmpty()
            if (r7 == 0) goto L_0x0068
            goto L_0x0103
        L_0x0068:
            com.itextpdf.text.pdf.PdfDictionary r6 = r6.getAsDict(r0)
            java.lang.String r7 = getSubsetPrefix(r6)
            if (r7 != 0) goto L_0x0074
            goto L_0x0103
        L_0x0074:
            java.lang.String r8 = com.itextpdf.text.pdf.BaseFont.createSubsetPrefix()
            if (r4 == 0) goto L_0x0097
            com.itextpdf.text.pdf.PdfName r9 = com.itextpdf.text.pdf.PdfName.BASEFONT
            com.itextpdf.text.pdf.PdfName r10 = new com.itextpdf.text.pdf.PdfName
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r8)
            java.lang.String r4 = r4.substring(r5)
            r11.append(r4)
            java.lang.String r4 = r11.toString()
            r10.<init>((java.lang.String) r4)
            r3.put(r9, r10)
        L_0x0097:
            r12.setXrefPartialObject(r1, r3)
            com.itextpdf.text.pdf.PdfName r3 = new com.itextpdf.text.pdf.PdfName
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r8)
            java.lang.String r5 = r7.substring(r5)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>((java.lang.String) r4)
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.BASEFONT
            r6.put(r4, r3)
            int r2 = r2 + 1
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.FONTDESCRIPTOR
            com.itextpdf.text.pdf.PdfDictionary r4 = r6.getAsDict(r4)
            if (r4 != 0) goto L_0x00c2
            goto L_0x0103
        L_0x00c2:
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.FONTNAME
            r4.put(r5, r3)
            goto L_0x0103
        L_0x00c8:
            java.lang.String r4 = getSubsetPrefix(r3)
            if (r4 != 0) goto L_0x00cf
            goto L_0x0103
        L_0x00cf:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = com.itextpdf.text.pdf.BaseFont.createSubsetPrefix()
            r6.append(r7)
            java.lang.String r4 = r4.substring(r5)
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            com.itextpdf.text.pdf.PdfName r5 = new com.itextpdf.text.pdf.PdfName
            r5.<init>((java.lang.String) r4)
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.BASEFONT
            r3.put(r4, r5)
            r12.setXrefPartialObject(r1, r3)
            int r2 = r2 + 1
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.FONTDESCRIPTOR
            com.itextpdf.text.pdf.PdfDictionary r3 = r3.getAsDict(r4)
            if (r3 != 0) goto L_0x00fe
            goto L_0x0103
        L_0x00fe:
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.FONTNAME
            r3.put(r4, r5)
        L_0x0103:
            int r1 = r1 + 1
            goto L_0x0003
        L_0x0107:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfReader.shuffleSubsetNames():int");
    }

    public int createFakeFontSubsets() {
        String fontName;
        int i = 0;
        for (int i2 = 1; i2 < this.xrefObj.size(); i2++) {
            PdfObject pdfObjectRelease = getPdfObjectRelease(i2);
            if (pdfObjectRelease != null && pdfObjectRelease.isDictionary()) {
                PdfDictionary pdfDictionary = (PdfDictionary) pdfObjectRelease;
                if (existsName(pdfDictionary, PdfName.TYPE, PdfName.FONT) && ((existsName(pdfDictionary, PdfName.SUBTYPE, PdfName.TYPE1) || existsName(pdfDictionary, PdfName.SUBTYPE, PdfName.MMTYPE1) || existsName(pdfDictionary, PdfName.SUBTYPE, PdfName.TRUETYPE)) && getSubsetPrefix(pdfDictionary) == null && (fontName = getFontName(pdfDictionary)) != null)) {
                    String str = BaseFont.createSubsetPrefix() + fontName;
                    PdfDictionary pdfDictionary2 = (PdfDictionary) getPdfObjectRelease(pdfDictionary.get(PdfName.FONTDESCRIPTOR));
                    if (!(pdfDictionary2 == null || (pdfDictionary2.get(PdfName.FONTFILE) == null && pdfDictionary2.get(PdfName.FONTFILE2) == null && pdfDictionary2.get(PdfName.FONTFILE3) == null))) {
                        PdfDictionary asDict = pdfDictionary.getAsDict(PdfName.FONTDESCRIPTOR);
                        PdfName pdfName = new PdfName(str);
                        pdfDictionary.put(PdfName.BASEFONT, pdfName);
                        asDict.put(PdfName.FONTNAME, pdfName);
                        setXrefPartialObject(i2, pdfDictionary);
                        i++;
                    }
                }
            }
        }
        return i;
    }

    private static PdfArray getNameArray(PdfObject pdfObject) {
        PdfObject pdfObjectRelease;
        PdfObject pdfObjectRelease2;
        if (pdfObject == null || (pdfObjectRelease = getPdfObjectRelease(pdfObject)) == null) {
            return null;
        }
        if (pdfObjectRelease.isArray()) {
            return (PdfArray) pdfObjectRelease;
        }
        if (!pdfObjectRelease.isDictionary() || (pdfObjectRelease2 = getPdfObjectRelease(((PdfDictionary) pdfObjectRelease).get(PdfName.f567D))) == null || !pdfObjectRelease2.isArray()) {
            return null;
        }
        return (PdfArray) pdfObjectRelease2;
    }

    public HashMap<Object, PdfObject> getNamedDestination() {
        return getNamedDestination(false);
    }

    public HashMap<Object, PdfObject> getNamedDestination(boolean z) {
        HashMap<Object, PdfObject> namedDestinationFromNames = getNamedDestinationFromNames(z);
        namedDestinationFromNames.putAll(getNamedDestinationFromStrings());
        return namedDestinationFromNames;
    }

    public HashMap<String, PdfObject> getNamedDestinationFromNames() {
        return new HashMap<>(getNamedDestinationFromNames(false));
    }

    public HashMap<Object, PdfObject> getNamedDestinationFromNames(boolean z) {
        PdfDictionary pdfDictionary;
        HashMap<Object, PdfObject> hashMap = new HashMap<>();
        if (this.catalog.get(PdfName.DESTS) == null || (pdfDictionary = (PdfDictionary) getPdfObjectRelease(this.catalog.get(PdfName.DESTS))) == null) {
            return hashMap;
        }
        for (PdfName next : pdfDictionary.getKeys()) {
            PdfArray nameArray = getNameArray(pdfDictionary.get(next));
            if (nameArray != null) {
                if (z) {
                    hashMap.put(next, nameArray);
                } else {
                    hashMap.put(PdfName.decodeName(next.toString()), nameArray);
                }
            }
        }
        return hashMap;
    }

    public HashMap<String, PdfObject> getNamedDestinationFromStrings() {
        PdfDictionary pdfDictionary;
        PdfDictionary pdfDictionary2;
        if (this.catalog.get(PdfName.NAMES) == null || (pdfDictionary = (PdfDictionary) getPdfObjectRelease(this.catalog.get(PdfName.NAMES))) == null || (pdfDictionary2 = (PdfDictionary) getPdfObjectRelease(pdfDictionary.get(PdfName.DESTS))) == null) {
            return new HashMap<>();
        }
        HashMap<String, PdfObject> readTree = PdfNameTree.readTree(pdfDictionary2);
        Iterator<Map.Entry<String, PdfObject>> it = readTree.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            PdfArray nameArray = getNameArray((PdfObject) next.getValue());
            if (nameArray != null) {
                next.setValue(nameArray);
            } else {
                it.remove();
            }
        }
        return readTree;
    }

    public void removeFields() {
        this.pageRefs.resetReleasePage();
        for (int i = 1; i <= this.pageRefs.size(); i++) {
            PdfDictionary pageN = this.pageRefs.getPageN(i);
            PdfArray asArray = pageN.getAsArray(PdfName.ANNOTS);
            if (asArray == null) {
                this.pageRefs.releasePage(i);
            } else {
                int i2 = 0;
                while (i2 < asArray.size()) {
                    PdfObject pdfObjectRelease = getPdfObjectRelease(asArray.getPdfObject(i2));
                    if (pdfObjectRelease != null && pdfObjectRelease.isDictionary() && PdfName.WIDGET.equals(((PdfDictionary) pdfObjectRelease).get(PdfName.SUBTYPE))) {
                        asArray.remove(i2);
                        i2--;
                    }
                    i2++;
                }
                if (asArray.isEmpty()) {
                    pageN.remove(PdfName.ANNOTS);
                } else {
                    this.pageRefs.releasePage(i);
                }
            }
        }
        this.catalog.remove(PdfName.ACROFORM);
        this.pageRefs.resetReleasePage();
    }

    public void removeAnnotations() {
        this.pageRefs.resetReleasePage();
        for (int i = 1; i <= this.pageRefs.size(); i++) {
            PdfDictionary pageN = this.pageRefs.getPageN(i);
            if (pageN.get(PdfName.ANNOTS) == null) {
                this.pageRefs.releasePage(i);
            } else {
                pageN.remove(PdfName.ANNOTS);
            }
        }
        this.catalog.remove(PdfName.ACROFORM);
        this.pageRefs.resetReleasePage();
    }

    public ArrayList<PdfAnnotation.PdfImportedLink> getLinks(int i) {
        this.pageRefs.resetReleasePage();
        ArrayList<PdfAnnotation.PdfImportedLink> arrayList = new ArrayList<>();
        PdfDictionary pageN = this.pageRefs.getPageN(i);
        if (pageN.get(PdfName.ANNOTS) != null) {
            PdfArray asArray = pageN.getAsArray(PdfName.ANNOTS);
            for (int i2 = 0; i2 < asArray.size(); i2++) {
                PdfDictionary pdfDictionary = (PdfDictionary) getPdfObjectRelease(asArray.getPdfObject(i2));
                if (PdfName.LINK.equals(pdfDictionary.get(PdfName.SUBTYPE))) {
                    arrayList.add(new PdfAnnotation.PdfImportedLink(pdfDictionary));
                }
            }
        }
        this.pageRefs.releasePage(i);
        this.pageRefs.resetReleasePage();
        return arrayList;
    }

    private void iterateBookmarks(PdfObject pdfObject, HashMap<Object, PdfObject> hashMap) {
        while (pdfObject != null) {
            replaceNamedDestination(pdfObject, hashMap);
            PdfDictionary pdfDictionary = (PdfDictionary) getPdfObjectRelease(pdfObject);
            PdfObject pdfObject2 = pdfDictionary.get(PdfName.FIRST);
            if (pdfObject2 != null) {
                iterateBookmarks(pdfObject2, hashMap);
            }
            pdfObject = pdfDictionary.get(PdfName.NEXT);
        }
    }

    public void makeRemoteNamedDestinationsLocal() {
        if (!this.remoteToLocalNamedDestinations) {
            this.remoteToLocalNamedDestinations = true;
            HashMap<Object, PdfObject> namedDestination = getNamedDestination(true);
            if (!namedDestination.isEmpty()) {
                for (int i = 1; i <= this.pageRefs.size(); i++) {
                    PdfObject pdfObject = this.pageRefs.getPageN(i).get(PdfName.ANNOTS);
                    PdfArray pdfArray = (PdfArray) getPdfObject(pdfObject);
                    int i2 = this.lastXrefPartial;
                    releaseLastXrefPartial();
                    if (pdfArray == null) {
                        this.pageRefs.releasePage(i);
                    } else {
                        boolean z = false;
                        for (int i3 = 0; i3 < pdfArray.size(); i3++) {
                            PdfObject pdfObject2 = pdfArray.getPdfObject(i3);
                            if (convertNamedDestination(pdfObject2, namedDestination) && !pdfObject2.isIndirect()) {
                                z = true;
                            }
                        }
                        if (z) {
                            setXrefPartialObject(i2, pdfArray);
                        }
                        if (!z || pdfObject.isIndirect()) {
                            this.pageRefs.releasePage(i);
                        }
                    }
                }
            }
        }
    }

    private boolean convertNamedDestination(PdfObject pdfObject, HashMap<Object, PdfObject> hashMap) {
        PdfObject pdfObject2;
        PdfObject pdfObjectRelease;
        PdfObject pdfObject3 = getPdfObject(pdfObject);
        int i = this.lastXrefPartial;
        releaseLastXrefPartial();
        if (pdfObject3 == null || !pdfObject3.isDictionary() || (pdfObject2 = getPdfObject(((PdfDictionary) pdfObject3).get(PdfName.f545A))) == null) {
            return false;
        }
        int i2 = this.lastXrefPartial;
        releaseLastXrefPartial();
        PdfDictionary pdfDictionary = (PdfDictionary) pdfObject2;
        if (!PdfName.GOTOR.equals((PdfName) getPdfObjectRelease(pdfDictionary.get(PdfName.f642S))) || (pdfObjectRelease = getPdfObjectRelease(pdfDictionary.get(PdfName.f567D))) == null) {
            return false;
        }
        boolean isName = pdfObjectRelease.isName();
        Object obj = pdfObjectRelease;
        if (!isName) {
            obj = pdfObjectRelease.isString() ? pdfObjectRelease.toString() : null;
        }
        if (((PdfArray) hashMap.get(obj)) == null) {
            return false;
        }
        pdfDictionary.remove(PdfName.f580F);
        pdfDictionary.remove(PdfName.NEWWINDOW);
        pdfDictionary.put(PdfName.f642S, PdfName.GOTO);
        setXrefPartialObject(i2, pdfObject2);
        setXrefPartialObject(i, pdfObject3);
        return true;
    }

    public void consolidateNamedDestinations() {
        if (!this.consolidateNamedDestinations) {
            this.consolidateNamedDestinations = true;
            HashMap<Object, PdfObject> namedDestination = getNamedDestination(true);
            if (!namedDestination.isEmpty()) {
                for (int i = 1; i <= this.pageRefs.size(); i++) {
                    PdfObject pdfObject = this.pageRefs.getPageN(i).get(PdfName.ANNOTS);
                    PdfArray pdfArray = (PdfArray) getPdfObject(pdfObject);
                    int i2 = this.lastXrefPartial;
                    releaseLastXrefPartial();
                    if (pdfArray == null) {
                        this.pageRefs.releasePage(i);
                    } else {
                        boolean z = false;
                        for (int i3 = 0; i3 < pdfArray.size(); i3++) {
                            PdfObject pdfObject2 = pdfArray.getPdfObject(i3);
                            if (replaceNamedDestination(pdfObject2, namedDestination) && !pdfObject2.isIndirect()) {
                                z = true;
                            }
                        }
                        if (z) {
                            setXrefPartialObject(i2, pdfArray);
                        }
                        if (!z || pdfObject.isIndirect()) {
                            this.pageRefs.releasePage(i);
                        }
                    }
                }
                PdfDictionary pdfDictionary = (PdfDictionary) getPdfObjectRelease(this.catalog.get(PdfName.OUTLINES));
                if (pdfDictionary != null) {
                    iterateBookmarks(pdfDictionary.get(PdfName.FIRST), namedDestination);
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: com.itextpdf.text.pdf.PdfObject} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean replaceNamedDestination(com.itextpdf.text.pdf.PdfObject r9, java.util.HashMap<java.lang.Object, com.itextpdf.text.pdf.PdfObject> r10) {
        /*
            r8 = this;
            com.itextpdf.text.pdf.PdfObject r9 = getPdfObject((com.itextpdf.text.pdf.PdfObject) r9)
            int r0 = r8.lastXrefPartial
            r8.releaseLastXrefPartial()
            if (r9 == 0) goto L_0x00a0
            boolean r1 = r9.isDictionary()
            if (r1 == 0) goto L_0x00a0
            r1 = r9
            com.itextpdf.text.pdf.PdfDictionary r1 = (com.itextpdf.text.pdf.PdfDictionary) r1
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.DEST
            com.itextpdf.text.pdf.PdfObject r2 = r1.get(r2)
            com.itextpdf.text.pdf.PdfObject r2 = getPdfObjectRelease((com.itextpdf.text.pdf.PdfObject) r2)
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L_0x0046
            boolean r5 = r2.isName()
            if (r5 == 0) goto L_0x0029
            goto L_0x0035
        L_0x0029:
            boolean r5 = r2.isString()
            if (r5 == 0) goto L_0x0034
            java.lang.String r2 = r2.toString()
            goto L_0x0035
        L_0x0034:
            r2 = r3
        L_0x0035:
            java.lang.Object r10 = r10.get(r2)
            com.itextpdf.text.pdf.PdfArray r10 = (com.itextpdf.text.pdf.PdfArray) r10
            if (r10 == 0) goto L_0x00a0
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.DEST
            r1.put(r2, r10)
            r8.setXrefPartialObject(r0, r9)
            return r4
        L_0x0046:
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.f545A
            com.itextpdf.text.pdf.PdfObject r1 = r1.get(r2)
            com.itextpdf.text.pdf.PdfObject r1 = getPdfObject((com.itextpdf.text.pdf.PdfObject) r1)
            if (r1 == 0) goto L_0x00a0
            int r2 = r8.lastXrefPartial
            r8.releaseLastXrefPartial()
            r5 = r1
            com.itextpdf.text.pdf.PdfDictionary r5 = (com.itextpdf.text.pdf.PdfDictionary) r5
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.f642S
            com.itextpdf.text.pdf.PdfObject r6 = r5.get(r6)
            com.itextpdf.text.pdf.PdfObject r6 = getPdfObjectRelease((com.itextpdf.text.pdf.PdfObject) r6)
            com.itextpdf.text.pdf.PdfName r6 = (com.itextpdf.text.pdf.PdfName) r6
            com.itextpdf.text.pdf.PdfName r7 = com.itextpdf.text.pdf.PdfName.GOTO
            boolean r6 = r7.equals(r6)
            if (r6 == 0) goto L_0x00a0
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.f567D
            com.itextpdf.text.pdf.PdfObject r6 = r5.get(r6)
            com.itextpdf.text.pdf.PdfObject r6 = getPdfObjectRelease((com.itextpdf.text.pdf.PdfObject) r6)
            if (r6 == 0) goto L_0x008c
            boolean r7 = r6.isName()
            if (r7 == 0) goto L_0x0082
            r3 = r6
            goto L_0x008c
        L_0x0082:
            boolean r7 = r6.isString()
            if (r7 == 0) goto L_0x008c
            java.lang.String r3 = r6.toString()
        L_0x008c:
            java.lang.Object r10 = r10.get(r3)
            com.itextpdf.text.pdf.PdfArray r10 = (com.itextpdf.text.pdf.PdfArray) r10
            if (r10 == 0) goto L_0x00a0
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.f567D
            r5.put(r3, r10)
            r8.setXrefPartialObject(r2, r1)
            r8.setXrefPartialObject(r0, r9)
            return r4
        L_0x00a0:
            r9 = 0
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfReader.replaceNamedDestination(com.itextpdf.text.pdf.PdfObject, java.util.HashMap):boolean");
    }

    protected static PdfDictionary duplicatePdfDictionary(PdfDictionary pdfDictionary, PdfDictionary pdfDictionary2, PdfReader pdfReader) {
        if (pdfDictionary2 == null) {
            pdfDictionary2 = new PdfDictionary(pdfDictionary.size());
        }
        for (PdfName next : pdfDictionary.getKeys()) {
            pdfDictionary2.put(next, duplicatePdfObject(pdfDictionary.get(next), pdfReader));
        }
        return pdfDictionary2;
    }

    protected static PdfObject duplicatePdfObject(PdfObject pdfObject, PdfReader pdfReader) {
        if (pdfObject == null) {
            return null;
        }
        int type = pdfObject.type();
        if (type == 5) {
            PdfArray pdfArray = (PdfArray) pdfObject;
            PdfArray pdfArray2 = new PdfArray(pdfArray.size());
            ListIterator<PdfObject> listIterator = pdfArray.listIterator();
            while (listIterator.hasNext()) {
                pdfArray2.add(duplicatePdfObject(listIterator.next(), pdfReader));
            }
            return pdfArray2;
        } else if (type == 6) {
            return duplicatePdfDictionary((PdfDictionary) pdfObject, (PdfDictionary) null, pdfReader);
        } else {
            if (type == 7) {
                PRStream pRStream = (PRStream) pdfObject;
                PRStream pRStream2 = new PRStream(pRStream, (PdfDictionary) null, pdfReader);
                duplicatePdfDictionary(pRStream, pRStream2, pdfReader);
                return pRStream2;
            } else if (type != 10) {
                return pdfObject;
            } else {
                PRIndirectReference pRIndirectReference = (PRIndirectReference) pdfObject;
                return new PRIndirectReference(pdfReader, pRIndirectReference.getNumber(), pRIndirectReference.getGeneration());
            }
        }
    }

    public void close() {
        try {
            this.tokens.close();
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: java.lang.Object[]} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00e9, code lost:
        r0.push(r1);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0124  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0137  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeUnusedNode(com.itextpdf.text.pdf.PdfObject r13, boolean[] r14) {
        /*
            r12 = this;
            java.util.Stack r0 = new java.util.Stack
            r0.<init>()
            r0.push(r13)
        L_0x0008:
            boolean r13 = r0.empty()
            if (r13 != 0) goto L_0x0147
            java.lang.Object r13 = r0.pop()
            if (r13 != 0) goto L_0x0015
            goto L_0x0008
        L_0x0015:
            boolean r1 = r13 instanceof com.itextpdf.text.pdf.PdfObject
            r2 = 2
            r3 = 0
            r4 = 0
            r5 = 1
            if (r1 == 0) goto L_0x0065
            com.itextpdf.text.pdf.PdfObject r13 = (com.itextpdf.text.pdf.PdfObject) r13
            int r1 = r13.type()
            r6 = 5
            if (r1 == r6) goto L_0x0058
            r6 = 6
            if (r1 == r6) goto L_0x0045
            r6 = 7
            if (r1 == r6) goto L_0x0045
            r2 = 10
            if (r1 == r2) goto L_0x0031
            goto L_0x0008
        L_0x0031:
            com.itextpdf.text.pdf.PRIndirectReference r13 = (com.itextpdf.text.pdf.PRIndirectReference) r13
            int r1 = r13.getNumber()
            boolean r2 = r14[r1]
            if (r2 != 0) goto L_0x0008
            r14[r1] = r5
            com.itextpdf.text.pdf.PdfObject r13 = getPdfObjectRelease((com.itextpdf.text.pdf.PdfObject) r13)
            r0.push(r13)
            goto L_0x0008
        L_0x0045:
            com.itextpdf.text.pdf.PdfDictionary r13 = (com.itextpdf.text.pdf.PdfDictionary) r13
            int r1 = r13.size()
            com.itextpdf.text.pdf.PdfName[] r1 = new com.itextpdf.text.pdf.PdfName[r1]
            java.util.Set r6 = r13.getKeys()
            r6.toArray(r1)
            r6 = r1
            r1 = r13
            r13 = r3
            goto L_0x0060
        L_0x0058:
            com.itextpdf.text.pdf.PdfArray r13 = (com.itextpdf.text.pdf.PdfArray) r13
            java.util.ArrayList r13 = r13.getArrayList()
            r1 = r3
            r6 = r1
        L_0x0060:
            r7 = 0
            r11 = r3
            r3 = r13
            r13 = r11
            goto L_0x0095
        L_0x0065:
            java.lang.Object[] r13 = (java.lang.Object[]) r13
            java.lang.Object[] r13 = (java.lang.Object[]) r13
            r1 = r13[r4]
            boolean r1 = r1 instanceof java.util.ArrayList
            if (r1 == 0) goto L_0x0080
            r1 = r13[r4]
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            r6 = r13[r5]
            java.lang.Integer r6 = (java.lang.Integer) r6
            int r6 = r6.intValue()
            r7 = r6
            r6 = r3
            r3 = r1
            r1 = r6
            goto L_0x0095
        L_0x0080:
            r1 = r13[r4]
            com.itextpdf.text.pdf.PdfName[] r1 = (com.itextpdf.text.pdf.PdfName[]) r1
            com.itextpdf.text.pdf.PdfName[] r1 = (com.itextpdf.text.pdf.PdfName[]) r1
            r6 = r13[r5]
            com.itextpdf.text.pdf.PdfDictionary r6 = (com.itextpdf.text.pdf.PdfDictionary) r6
            r7 = r13[r2]
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            r11 = r6
            r6 = r1
            r1 = r11
        L_0x0095:
            if (r3 == 0) goto L_0x00ee
        L_0x0097:
            int r1 = r3.size()
            if (r7 >= r1) goto L_0x0008
            java.lang.Object r1 = r3.get(r7)
            com.itextpdf.text.pdf.PdfObject r1 = (com.itextpdf.text.pdf.PdfObject) r1
            boolean r6 = r1.isIndirect()
            if (r6 == 0) goto L_0x00cc
            r6 = r1
            com.itextpdf.text.pdf.PRIndirectReference r6 = (com.itextpdf.text.pdf.PRIndirectReference) r6
            int r6 = r6.getNumber()
            java.util.ArrayList<com.itextpdf.text.pdf.PdfObject> r8 = r12.xrefObj
            int r8 = r8.size()
            if (r6 >= r8) goto L_0x00c4
            boolean r8 = r12.partial
            if (r8 != 0) goto L_0x00cc
            java.util.ArrayList<com.itextpdf.text.pdf.PdfObject> r8 = r12.xrefObj
            java.lang.Object r6 = r8.get(r6)
            if (r6 != 0) goto L_0x00cc
        L_0x00c4:
            com.itextpdf.text.pdf.PdfNull r1 = com.itextpdf.text.pdf.PdfNull.PDFNULL
            r3.set(r7, r1)
            int r7 = r7 + 1
            goto L_0x0097
        L_0x00cc:
            if (r13 != 0) goto L_0x00de
            java.lang.Object[] r13 = new java.lang.Object[r2]
            r13[r4] = r3
            int r7 = r7 + 1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r7)
            r13[r5] = r2
            r0.push(r13)
            goto L_0x00e9
        L_0x00de:
            int r7 = r7 + 1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r7)
            r13[r5] = r2
            r0.push(r13)
        L_0x00e9:
            r0.push(r1)
            goto L_0x0008
        L_0x00ee:
            int r3 = r6.length
            if (r7 >= r3) goto L_0x0008
            r3 = r6[r7]
            com.itextpdf.text.pdf.PdfObject r8 = r1.get(r3)
            boolean r9 = r8.isIndirect()
            if (r9 == 0) goto L_0x0122
            r9 = r8
            com.itextpdf.text.pdf.PRIndirectReference r9 = (com.itextpdf.text.pdf.PRIndirectReference) r9
            int r9 = r9.getNumber()
            if (r9 < 0) goto L_0x011a
            java.util.ArrayList<com.itextpdf.text.pdf.PdfObject> r10 = r12.xrefObj
            int r10 = r10.size()
            if (r9 >= r10) goto L_0x011a
            boolean r10 = r12.partial
            if (r10 != 0) goto L_0x0122
            java.util.ArrayList<com.itextpdf.text.pdf.PdfObject> r10 = r12.xrefObj
            java.lang.Object r9 = r10.get(r9)
            if (r9 != 0) goto L_0x0122
        L_0x011a:
            com.itextpdf.text.pdf.PdfNull r8 = com.itextpdf.text.pdf.PdfNull.PDFNULL
            r1.put(r3, r8)
            int r7 = r7 + 1
            goto L_0x00ee
        L_0x0122:
            if (r13 != 0) goto L_0x0137
            r13 = 3
            java.lang.Object[] r13 = new java.lang.Object[r13]
            r13[r4] = r6
            r13[r5] = r1
            int r7 = r7 + 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r7)
            r13[r2] = r1
            r0.push(r13)
            goto L_0x0142
        L_0x0137:
            int r7 = r7 + 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r7)
            r13[r2] = r1
            r0.push(r13)
        L_0x0142:
            r0.push(r8)
            goto L_0x0008
        L_0x0147:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfReader.removeUnusedNode(com.itextpdf.text.pdf.PdfObject, boolean[]):void");
    }

    public int removeUnusedObjects() {
        int size = this.xrefObj.size();
        boolean[] zArr = new boolean[size];
        removeUnusedNode(this.trailer, zArr);
        int i = 0;
        if (this.partial) {
            for (int i2 = 1; i2 < size; i2++) {
                if (!zArr[i2]) {
                    long[] jArr = this.xref;
                    int i3 = i2 * 2;
                    jArr[i3] = -1;
                    jArr[i3 + 1] = 0;
                    this.xrefObj.set(i2, (Object) null);
                    i++;
                }
            }
        } else {
            for (int i4 = 1; i4 < size; i4++) {
                if (!zArr[i4]) {
                    this.xrefObj.set(i4, (Object) null);
                    i++;
                }
            }
        }
        return i;
    }

    public AcroFields getAcroFields() {
        return new AcroFields(this, (PdfWriter) null);
    }

    public String getJavaScript(RandomAccessFileOrArray randomAccessFileOrArray) throws IOException {
        PdfDictionary pdfDictionary;
        PdfObject pdfObjectRelease;
        PdfDictionary pdfDictionary2 = (PdfDictionary) getPdfObjectRelease(this.catalog.get(PdfName.NAMES));
        if (pdfDictionary2 == null || (pdfDictionary = (PdfDictionary) getPdfObjectRelease(pdfDictionary2.get(PdfName.JAVASCRIPT))) == null) {
            return null;
        }
        HashMap<String, PdfObject> readTree = PdfNameTree.readTree(pdfDictionary);
        String[] strArr = (String[]) readTree.keySet().toArray(new String[readTree.size()]);
        Arrays.sort(strArr);
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : strArr) {
            PdfDictionary pdfDictionary3 = (PdfDictionary) getPdfObjectRelease(readTree.get(str));
            if (!(pdfDictionary3 == null || (pdfObjectRelease = getPdfObjectRelease(pdfDictionary3.get(PdfName.f604JS))) == null)) {
                if (pdfObjectRelease.isString()) {
                    stringBuffer.append(((PdfString) pdfObjectRelease).toUnicodeString());
                    stringBuffer.append(10);
                } else if (pdfObjectRelease.isStream()) {
                    byte[] streamBytes = getStreamBytes((PRStream) pdfObjectRelease, randomAccessFileOrArray);
                    if (streamBytes.length >= 2 && streamBytes[0] == -2 && streamBytes[1] == -1) {
                        stringBuffer.append(PdfEncodings.convertToString(streamBytes, PdfObject.TEXT_UNICODE));
                    } else {
                        stringBuffer.append(PdfEncodings.convertToString(streamBytes, PdfObject.TEXT_PDFDOCENCODING));
                    }
                    stringBuffer.append(10);
                }
            }
        }
        return stringBuffer.toString();
    }

    public String getJavaScript() throws IOException {
        RandomAccessFileOrArray safeFile = getSafeFile();
        try {
            safeFile.reOpen();
            return getJavaScript(safeFile);
        } finally {
            try {
                safeFile.close();
            } catch (Exception unused) {
            }
        }
    }

    public void selectPages(String str) {
        selectPages(SequenceList.expand(str, getNumberOfPages()));
    }

    public void selectPages(List<Integer> list) {
        selectPages(list, true);
    }

    /* access modifiers changed from: protected */
    public void selectPages(List<Integer> list, boolean z) {
        this.pageRefs.selectPages(list);
        if (z) {
            removeUnusedObjects();
        }
    }

    public void setViewerPreferences(int i) {
        this.viewerPreferences.setViewerPreferences(i);
        setViewerPreferences(this.viewerPreferences);
    }

    public void addViewerPreference(PdfName pdfName, PdfObject pdfObject) {
        this.viewerPreferences.addViewerPreference(pdfName, pdfObject);
        setViewerPreferences(this.viewerPreferences);
    }

    public void setViewerPreferences(PdfViewerPreferencesImp pdfViewerPreferencesImp) {
        pdfViewerPreferencesImp.addToCatalog(this.catalog);
    }

    public int getSimpleViewerPreferences() {
        return PdfViewerPreferencesImp.getViewerPreferences(this.catalog).getPageLayoutAndMode();
    }

    public boolean isAppendable() {
        return this.appendable;
    }

    public void setAppendable(boolean z) {
        this.appendable = z;
        if (z) {
            getPdfObject(this.trailer.get(PdfName.ROOT));
        }
    }

    public boolean isNewXrefType() {
        return this.newXrefType;
    }

    public long getFileLength() {
        return this.fileLength;
    }

    public boolean isHybridXref() {
        return this.hybridXref;
    }

    static class PageRefs {
        private boolean keepPages;
        private int lastPageRead;
        private ArrayList<PdfDictionary> pageInh;
        private Set<PdfObject> pagesNodes;
        private final PdfReader reader;
        private ArrayList<PRIndirectReference> refsn;
        private IntHashtable refsp;
        private int sizep;

        /* synthetic */ PageRefs(PdfReader pdfReader, C15211 r2) throws IOException {
            this(pdfReader);
        }

        private PageRefs(PdfReader pdfReader) throws IOException {
            this.lastPageRead = -1;
            this.pagesNodes = new HashSet();
            this.reader = pdfReader;
            if (pdfReader.partial) {
                this.refsp = new IntHashtable();
                this.sizep = ((PdfNumber) PdfReader.getPdfObjectRelease(pdfReader.rootPages.get(PdfName.COUNT))).intValue();
                return;
            }
            readPages();
        }

        PageRefs(PageRefs pageRefs, PdfReader pdfReader) {
            this.lastPageRead = -1;
            this.pagesNodes = new HashSet();
            this.reader = pdfReader;
            this.sizep = pageRefs.sizep;
            if (pageRefs.refsn != null) {
                this.refsn = new ArrayList<>(pageRefs.refsn);
                for (int i = 0; i < this.refsn.size(); i++) {
                    ArrayList<PRIndirectReference> arrayList = this.refsn;
                    arrayList.set(i, (PRIndirectReference) PdfReader.duplicatePdfObject(arrayList.get(i), pdfReader));
                }
                return;
            }
            this.refsp = (IntHashtable) pageRefs.refsp.clone();
        }

        /* access modifiers changed from: package-private */
        public int size() {
            ArrayList<PRIndirectReference> arrayList = this.refsn;
            if (arrayList != null) {
                return arrayList.size();
            }
            return this.sizep;
        }

        /* access modifiers changed from: package-private */
        public void readPages() throws IOException {
            if (this.refsn == null) {
                this.refsp = null;
                this.refsn = new ArrayList<>();
                this.pageInh = new ArrayList<>();
                iteratePages((PRIndirectReference) this.reader.catalog.get(PdfName.PAGES));
                this.pageInh = null;
                this.reader.rootPages.put(PdfName.COUNT, new PdfNumber(this.refsn.size()));
            }
        }

        /* access modifiers changed from: package-private */
        public void reReadPages() throws IOException {
            this.refsn = null;
            readPages();
        }

        public PdfDictionary getPageN(int i) {
            return (PdfDictionary) PdfReader.getPdfObject((PdfObject) getPageOrigRef(i));
        }

        public PdfDictionary getPageNRelease(int i) {
            PdfDictionary pageN = getPageN(i);
            releasePage(i);
            return pageN;
        }

        public PRIndirectReference getPageOrigRefRelease(int i) {
            PRIndirectReference pageOrigRef = getPageOrigRef(i);
            releasePage(i);
            return pageOrigRef;
        }

        public PRIndirectReference getPageOrigRef(int i) {
            int i2 = i - 1;
            if (i2 < 0) {
                return null;
            }
            try {
                if (i2 >= size()) {
                    return null;
                }
                ArrayList<PRIndirectReference> arrayList = this.refsn;
                if (arrayList != null) {
                    return arrayList.get(i2);
                }
                int i3 = this.refsp.get(i2);
                if (i3 == 0) {
                    PRIndirectReference singlePage = getSinglePage(i2);
                    if (this.reader.lastXrefPartial == -1) {
                        this.lastPageRead = -1;
                    } else {
                        this.lastPageRead = i2;
                    }
                    int unused = this.reader.lastXrefPartial = -1;
                    this.refsp.put(i2, singlePage.getNumber());
                    if (this.keepPages) {
                        this.lastPageRead = -1;
                    }
                    return singlePage;
                }
                if (this.lastPageRead != i2) {
                    this.lastPageRead = -1;
                }
                if (this.keepPages) {
                    this.lastPageRead = -1;
                }
                return new PRIndirectReference(this.reader, i3);
            } catch (Exception e) {
                throw new ExceptionConverter(e);
            }
        }

        /* access modifiers changed from: package-private */
        public void keepPages() {
            IntHashtable intHashtable = this.refsp;
            if (intHashtable != null && !this.keepPages) {
                this.keepPages = true;
                intHashtable.clear();
            }
        }

        public void releasePage(int i) {
            int i2;
            if (this.refsp != null && i - 1 >= 0 && i2 < size() && i2 == this.lastPageRead) {
                this.lastPageRead = -1;
                int unused = this.reader.lastXrefPartial = this.refsp.get(i2);
                this.reader.releaseLastXrefPartial();
                this.refsp.remove(i2);
            }
        }

        public void resetReleasePage() {
            if (this.refsp != null) {
                this.lastPageRead = -1;
            }
        }

        /* access modifiers changed from: package-private */
        public void insertPage(int i, PRIndirectReference pRIndirectReference) {
            int i2 = i - 1;
            ArrayList<PRIndirectReference> arrayList = this.refsn;
            if (arrayList == null) {
                this.sizep++;
                this.lastPageRead = -1;
                if (i2 >= size()) {
                    this.refsp.put(size(), pRIndirectReference.getNumber());
                    return;
                }
                IntHashtable intHashtable = new IntHashtable((this.refsp.size() + 1) * 2);
                Iterator<IntHashtable.Entry> entryIterator = this.refsp.getEntryIterator();
                while (entryIterator.hasNext()) {
                    IntHashtable.Entry next = entryIterator.next();
                    int key = next.getKey();
                    if (key >= i2) {
                        key++;
                    }
                    intHashtable.put(key, next.getValue());
                }
                intHashtable.put(i2, pRIndirectReference.getNumber());
                this.refsp = intHashtable;
            } else if (i2 >= arrayList.size()) {
                this.refsn.add(pRIndirectReference);
            } else {
                this.refsn.add(i2, pRIndirectReference);
            }
        }

        private void pushPageAttributes(PdfDictionary pdfDictionary) {
            PdfDictionary pdfDictionary2 = new PdfDictionary();
            if (!this.pageInh.isEmpty()) {
                ArrayList<PdfDictionary> arrayList = this.pageInh;
                pdfDictionary2.putAll(arrayList.get(arrayList.size() - 1));
            }
            for (int i = 0; i < PdfReader.pageInhCandidates.length; i++) {
                PdfObject pdfObject = pdfDictionary.get(PdfReader.pageInhCandidates[i]);
                if (pdfObject != null) {
                    pdfDictionary2.put(PdfReader.pageInhCandidates[i], pdfObject);
                }
            }
            this.pageInh.add(pdfDictionary2);
        }

        private void popPageAttributes() {
            ArrayList<PdfDictionary> arrayList = this.pageInh;
            arrayList.remove(arrayList.size() - 1);
        }

        private void iteratePages(PRIndirectReference pRIndirectReference) throws IOException {
            int i = 0;
            if (this.pagesNodes.add(PdfReader.getPdfObject((PdfObject) pRIndirectReference))) {
                PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObject((PdfObject) pRIndirectReference);
                if (pdfDictionary != null) {
                    PdfArray asArray = pdfDictionary.getAsArray(PdfName.KIDS);
                    if (asArray == null) {
                        pdfDictionary.put(PdfName.TYPE, PdfName.PAGE);
                        ArrayList<PdfDictionary> arrayList = this.pageInh;
                        PdfDictionary pdfDictionary2 = arrayList.get(arrayList.size() - 1);
                        for (PdfName next : pdfDictionary2.getKeys()) {
                            if (pdfDictionary.get(next) == null) {
                                pdfDictionary.put(next, pdfDictionary2.get(next));
                            }
                        }
                        if (pdfDictionary.get(PdfName.MEDIABOX) == null) {
                            pdfDictionary.put(PdfName.MEDIABOX, new PdfArray(new float[]{0.0f, 0.0f, PageSize.LETTER.getRight(), PageSize.LETTER.getTop()}));
                        }
                        this.refsn.add(pRIndirectReference);
                        return;
                    }
                    pdfDictionary.put(PdfName.TYPE, PdfName.PAGES);
                    pushPageAttributes(pdfDictionary);
                    while (true) {
                        if (i >= asArray.size()) {
                            break;
                        }
                        PdfObject pdfObject = asArray.getPdfObject(i);
                        if (!pdfObject.isIndirect()) {
                            while (i < asArray.size()) {
                                asArray.remove(i);
                            }
                        } else {
                            iteratePages((PRIndirectReference) pdfObject);
                            i++;
                        }
                    }
                    popPageAttributes();
                    return;
                }
                return;
            }
            throw new InvalidPdfException(MessageLocalization.getComposedMessage("illegal.pages.tree", new Object[0]));
        }

        /* access modifiers changed from: protected */
        public PRIndirectReference getSinglePage(int i) {
            PdfDictionary pdfDictionary = new PdfDictionary();
            PdfDictionary pdfDictionary2 = this.reader.rootPages;
            int i2 = 0;
            while (true) {
                for (int i3 = 0; i3 < PdfReader.pageInhCandidates.length; i3++) {
                    PdfObject pdfObject = pdfDictionary2.get(PdfReader.pageInhCandidates[i3]);
                    if (pdfObject != null) {
                        pdfDictionary.put(PdfReader.pageInhCandidates[i3], pdfObject);
                    }
                }
                ListIterator<PdfObject> listIterator = ((PdfArray) PdfReader.getPdfObjectRelease(pdfDictionary2.get(PdfName.KIDS))).listIterator();
                while (true) {
                    if (!listIterator.hasNext()) {
                        break;
                    }
                    PRIndirectReference pRIndirectReference = (PRIndirectReference) listIterator.next();
                    PdfDictionary pdfDictionary3 = (PdfDictionary) PdfReader.getPdfObject((PdfObject) pRIndirectReference);
                    int access$300 = this.reader.lastXrefPartial;
                    PdfObject pdfObjectRelease = PdfReader.getPdfObjectRelease(pdfDictionary3.get(PdfName.COUNT));
                    int unused = this.reader.lastXrefPartial = access$300;
                    int intValue = ((pdfObjectRelease == null || pdfObjectRelease.type() != 2) ? 1 : ((PdfNumber) pdfObjectRelease).intValue()) + i2;
                    if (i >= intValue) {
                        this.reader.releaseLastXrefPartial();
                        i2 = intValue;
                    } else if (pdfObjectRelease == null) {
                        pdfDictionary3.mergeDifferent(pdfDictionary);
                        return pRIndirectReference;
                    } else {
                        this.reader.releaseLastXrefPartial();
                        pdfDictionary2 = pdfDictionary3;
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        public void selectPages(List<Integer> list) {
            IntHashtable intHashtable = new IntHashtable();
            ArrayList arrayList = new ArrayList();
            int size = size();
            for (Integer next : list) {
                int intValue = next.intValue();
                if (intValue >= 1 && intValue <= size && intHashtable.put(intValue, 1) == 0) {
                    arrayList.add(next);
                }
            }
            if (this.reader.partial) {
                for (int i = 1; i <= size; i++) {
                    getPageOrigRef(i);
                    resetReleasePage();
                }
            }
            PRIndirectReference pRIndirectReference = (PRIndirectReference) this.reader.catalog.get(PdfName.PAGES);
            PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObject((PdfObject) pRIndirectReference);
            ArrayList<PRIndirectReference> arrayList2 = new ArrayList<>(arrayList.size());
            PdfArray pdfArray = new PdfArray();
            boolean z = false;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                int intValue2 = ((Integer) arrayList.get(i2)).intValue();
                PRIndirectReference pageOrigRef = getPageOrigRef(intValue2);
                resetReleasePage();
                pdfArray.add((PdfObject) pageOrigRef);
                arrayList2.add(pageOrigRef);
                getPageN(intValue2).put(PdfName.PARENT, pRIndirectReference);
            }
            AcroFields acroFields = this.reader.getAcroFields();
            if (acroFields.getFields().size() > 0) {
                z = true;
            }
            for (int i3 = 1; i3 <= size; i3++) {
                if (!intHashtable.containsKey(i3)) {
                    if (z) {
                        acroFields.removeFieldsFromPage(i3);
                    }
                    int number = getPageOrigRef(i3).getNumber();
                    this.reader.xrefObj.set(number, (Object) null);
                    if (this.reader.partial) {
                        int i4 = number * 2;
                        this.reader.xref[i4] = -1;
                        this.reader.xref[i4 + 1] = 0;
                    }
                }
            }
            pdfDictionary.put(PdfName.COUNT, new PdfNumber(arrayList.size()));
            pdfDictionary.put(PdfName.KIDS, pdfArray);
            this.refsp = null;
            this.refsn = arrayList2;
        }
    }

    /* access modifiers changed from: package-private */
    public PdfIndirectReference getCryptoRef() {
        PRIndirectReference pRIndirectReference = this.cryptoRef;
        if (pRIndirectReference == null) {
            return null;
        }
        return new PdfIndirectReference(0, pRIndirectReference.getNumber(), this.cryptoRef.getGeneration());
    }

    public boolean hasUsageRights() {
        PdfDictionary asDict = this.catalog.getAsDict(PdfName.PERMS);
        if (asDict == null) {
            return false;
        }
        if (asDict.contains(PdfName.f663UR) || asDict.contains(PdfName.UR3)) {
            return true;
        }
        return false;
    }

    public void removeUsageRights() {
        PdfDictionary asDict = this.catalog.getAsDict(PdfName.PERMS);
        if (asDict != null) {
            asDict.remove(PdfName.f663UR);
            asDict.remove(PdfName.UR3);
            if (asDict.size() == 0) {
                this.catalog.remove(PdfName.PERMS);
            }
        }
    }

    public int getCertificationLevel() {
        PdfDictionary asDict;
        PdfArray asArray;
        PdfDictionary asDict2;
        PdfDictionary asDict3;
        PdfNumber asNumber;
        PdfDictionary asDict4 = this.catalog.getAsDict(PdfName.PERMS);
        if (asDict4 == null || (asDict = asDict4.getAsDict(PdfName.DOCMDP)) == null || (asArray = asDict.getAsArray(PdfName.REFERENCE)) == null || asArray.size() == 0 || (asDict2 = asArray.getAsDict(0)) == null || (asDict3 = asDict2.getAsDict(PdfName.TRANSFORMPARAMS)) == null || (asNumber = asDict3.getAsNumber(PdfName.f623P)) == null) {
            return 0;
        }
        return asNumber.intValue();
    }

    public final boolean isOpenedWithFullPermissions() {
        return !this.encrypted || this.ownerPasswordUsed || unethicalreading;
    }

    public int getCryptoMode() {
        PdfEncryption pdfEncryption = this.decrypt;
        if (pdfEncryption == null) {
            return -1;
        }
        return pdfEncryption.getCryptoMode();
    }

    public boolean isMetadataEncrypted() {
        PdfEncryption pdfEncryption = this.decrypt;
        if (pdfEncryption == null) {
            return false;
        }
        return pdfEncryption.isMetadataEncrypted();
    }

    public byte[] computeUserPassword() {
        if (!this.encrypted || !this.ownerPasswordUsed) {
            return null;
        }
        return this.decrypt.computeUserPassword(this.password);
    }
}
