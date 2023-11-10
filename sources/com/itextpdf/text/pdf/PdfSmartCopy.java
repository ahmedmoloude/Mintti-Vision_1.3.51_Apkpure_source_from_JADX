package com.itextpdf.text.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.log.Counter;
import com.itextpdf.text.log.CounterFactory;
import com.itextpdf.text.pdf.PdfCopy;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import kotlin.UByte;

public class PdfSmartCopy extends PdfCopy {
    protected Counter COUNTER;
    private final HashMap<RefKey, Integer> serialized;
    private HashMap<ByteStore, PdfIndirectReference> streamMap;

    /* access modifiers changed from: protected */
    public Counter getCounter() {
        return this.COUNTER;
    }

    public PdfSmartCopy(Document document, OutputStream outputStream) throws DocumentException {
        super(document, outputStream);
        this.streamMap = null;
        this.serialized = new HashMap<>();
        this.COUNTER = CounterFactory.getCounter(PdfSmartCopy.class);
        this.streamMap = new HashMap<>();
    }

    /* access modifiers changed from: protected */
    public PdfIndirectReference copyIndirect(PRIndirectReference pRIndirectReference) throws IOException, BadPdfFormatException {
        ByteStore byteStore;
        PdfIndirectReference pdfIndirectReference;
        PdfObject pdfObjectRelease;
        PdfObject pdfObjectRelease2 = PdfReader.getPdfObjectRelease((PdfObject) pRIndirectReference);
        boolean z = true;
        if (pdfObjectRelease2.isStream()) {
            byteStore = new ByteStore((PRStream) pdfObjectRelease2, this.serialized);
            PdfIndirectReference pdfIndirectReference2 = this.streamMap.get(byteStore);
            if (pdfIndirectReference2 != null) {
                return pdfIndirectReference2;
            }
        } else if (pdfObjectRelease2.isDictionary()) {
            byteStore = new ByteStore((PdfDictionary) pdfObjectRelease2, this.serialized);
            PdfIndirectReference pdfIndirectReference3 = this.streamMap.get(byteStore);
            if (pdfIndirectReference3 != null) {
                return pdfIndirectReference3;
            }
        } else {
            byteStore = null;
            z = false;
        }
        RefKey refKey = new RefKey(pRIndirectReference);
        PdfCopy.IndirectReferences indirectReferences = (PdfCopy.IndirectReferences) this.indirects.get(refKey);
        if (indirectReferences != null) {
            pdfIndirectReference = indirectReferences.getRef();
            if (indirectReferences.getCopied()) {
                return pdfIndirectReference;
            }
        } else {
            PdfIndirectReference pdfIndirectReference4 = this.body.getPdfIndirectReference();
            PdfCopy.IndirectReferences indirectReferences2 = new PdfCopy.IndirectReferences(pdfIndirectReference4);
            this.indirects.put(refKey, indirectReferences2);
            pdfIndirectReference = pdfIndirectReference4;
            indirectReferences = indirectReferences2;
        }
        if (pdfObjectRelease2.isDictionary() && (pdfObjectRelease = PdfReader.getPdfObjectRelease(((PdfDictionary) pdfObjectRelease2).get(PdfName.TYPE))) != null && PdfName.PAGE.equals(pdfObjectRelease)) {
            return pdfIndirectReference;
        }
        indirectReferences.setCopied();
        if (z) {
            this.streamMap.put(byteStore, pdfIndirectReference);
        }
        addToBody(copyObject(pdfObjectRelease2), pdfIndirectReference);
        return pdfIndirectReference;
    }

    public void freeReader(PdfReader pdfReader) throws IOException {
        this.serialized.clear();
        super.freeReader(pdfReader);
    }

    public void addPage(PdfImportedPage pdfImportedPage) throws IOException, BadPdfFormatException {
        if (this.currentPdfReaderInstance.getReader() != this.reader) {
            this.serialized.clear();
        }
        super.addPage(pdfImportedPage);
    }

    static class ByteStore {

        /* renamed from: b */
        private final byte[] f685b;
        private final int hash;
        private MessageDigest md5;

        private void serObject(PdfObject pdfObject, int i, ByteBuffer byteBuffer, HashMap<RefKey, Integer> hashMap) throws IOException {
            PdfIndirectReference pdfIndirectReference;
            if (i > 0) {
                if (pdfObject == null) {
                    byteBuffer.append("$Lnull");
                    return;
                }
                ByteBuffer byteBuffer2 = null;
                if (pdfObject.isIndirect()) {
                    PdfIndirectReference pdfIndirectReference2 = (PdfIndirectReference) pdfObject;
                    RefKey refKey = new RefKey(pdfIndirectReference2);
                    if (hashMap.containsKey(refKey)) {
                        byteBuffer.append(hashMap.get(refKey).intValue());
                        return;
                    }
                    PdfIndirectReference pdfIndirectReference3 = pdfIndirectReference2;
                    byteBuffer2 = byteBuffer;
                    byteBuffer = new ByteBuffer();
                    pdfIndirectReference = pdfIndirectReference3;
                } else {
                    pdfIndirectReference = null;
                }
                PdfObject pdfObject2 = PdfReader.getPdfObject(pdfObject);
                if (pdfObject2.isStream()) {
                    byteBuffer.append("$B");
                    serDic((PdfDictionary) pdfObject2, i - 1, byteBuffer, hashMap);
                    if (i > 0) {
                        this.md5.reset();
                        byteBuffer.append(this.md5.digest(PdfReader.getStreamBytesRaw((PRStream) pdfObject2)));
                    }
                } else if (pdfObject2.isDictionary()) {
                    serDic((PdfDictionary) pdfObject2, i - 1, byteBuffer, hashMap);
                } else if (pdfObject2.isArray()) {
                    serArray((PdfArray) pdfObject2, i - 1, byteBuffer, hashMap);
                } else if (pdfObject2.isString()) {
                    byteBuffer.append("$S").append(pdfObject2.toString());
                } else if (pdfObject2.isName()) {
                    byteBuffer.append("$N").append(pdfObject2.toString());
                } else {
                    byteBuffer.append("$L").append(pdfObject2.toString());
                }
                if (byteBuffer2 != null) {
                    RefKey refKey2 = new RefKey(pdfIndirectReference);
                    if (!hashMap.containsKey(refKey2)) {
                        hashMap.put(refKey2, Integer.valueOf(calculateHash(byteBuffer.getBuffer())));
                    }
                    byteBuffer2.append(byteBuffer);
                }
            }
        }

        private void serDic(PdfDictionary pdfDictionary, int i, ByteBuffer byteBuffer, HashMap<RefKey, Integer> hashMap) throws IOException {
            byteBuffer.append("$D");
            if (i > 0) {
                Object[] array = pdfDictionary.getKeys().toArray();
                Arrays.sort(array);
                for (int i2 = 0; i2 < array.length; i2++) {
                    if (!array[i2].equals(PdfName.f623P) || (!pdfDictionary.get((PdfName) array[i2]).isIndirect() && !pdfDictionary.get((PdfName) array[i2]).isDictionary())) {
                        serObject((PdfObject) array[i2], i, byteBuffer, hashMap);
                        serObject(pdfDictionary.get((PdfName) array[i2]), i, byteBuffer, hashMap);
                    }
                }
            }
        }

        private void serArray(PdfArray pdfArray, int i, ByteBuffer byteBuffer, HashMap<RefKey, Integer> hashMap) throws IOException {
            byteBuffer.append("$A");
            if (i > 0) {
                for (int i2 = 0; i2 < pdfArray.size(); i2++) {
                    serObject(pdfArray.getPdfObject(i2), i, byteBuffer, hashMap);
                }
            }
        }

        ByteStore(PRStream pRStream, HashMap<RefKey, Integer> hashMap) throws IOException {
            try {
                this.md5 = MessageDigest.getInstance("MD5");
                ByteBuffer byteBuffer = new ByteBuffer();
                serObject(pRStream, 100, byteBuffer, hashMap);
                byte[] byteArray = byteBuffer.toByteArray();
                this.f685b = byteArray;
                this.hash = calculateHash(byteArray);
                this.md5 = null;
            } catch (Exception e) {
                throw new ExceptionConverter(e);
            }
        }

        ByteStore(PdfDictionary pdfDictionary, HashMap<RefKey, Integer> hashMap) throws IOException {
            try {
                this.md5 = MessageDigest.getInstance("MD5");
                ByteBuffer byteBuffer = new ByteBuffer();
                serObject(pdfDictionary, 100, byteBuffer, hashMap);
                byte[] byteArray = byteBuffer.toByteArray();
                this.f685b = byteArray;
                this.hash = calculateHash(byteArray);
                this.md5 = null;
            } catch (Exception e) {
                throw new ExceptionConverter(e);
            }
        }

        private static int calculateHash(byte[] bArr) {
            int i = 0;
            for (byte b : bArr) {
                i = (i * 31) + (b & UByte.MAX_VALUE);
            }
            return i;
        }

        public boolean equals(Object obj) {
            if ((obj instanceof ByteStore) && hashCode() == obj.hashCode()) {
                return Arrays.equals(this.f685b, ((ByteStore) obj).f685b);
            }
            return false;
        }

        public int hashCode() {
            return this.hash;
        }
    }
}
