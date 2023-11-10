package com.itextpdf.text.pdf;

import com.itextpdf.text.DocWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class PdfStream extends PdfDictionary {
    public static final int BEST_COMPRESSION = 9;
    public static final int BEST_SPEED = 1;
    public static final int DEFAULT_COMPRESSION = -1;
    static final byte[] ENDSTREAM;
    public static final int NO_COMPRESSION = 0;
    static final int SIZESTREAM;
    static final byte[] STARTSTREAM;
    protected boolean compressed = false;
    protected int compressionLevel = 0;
    protected InputStream inputStream;
    protected int inputStreamLength = -1;
    protected int rawLength;
    protected PdfIndirectReference ref;
    protected ByteArrayOutputStream streamBytes = null;
    protected PdfWriter writer;

    static {
        byte[] iSOBytes = DocWriter.getISOBytes("stream\n");
        STARTSTREAM = iSOBytes;
        byte[] iSOBytes2 = DocWriter.getISOBytes("\nendstream");
        ENDSTREAM = iSOBytes2;
        SIZESTREAM = iSOBytes.length + iSOBytes2.length;
    }

    public PdfStream(byte[] bArr) {
        this.type = 7;
        this.bytes = bArr;
        this.rawLength = bArr.length;
        put(PdfName.LENGTH, new PdfNumber(bArr.length));
    }

    public PdfStream(InputStream inputStream2, PdfWriter pdfWriter) {
        this.type = 7;
        this.inputStream = inputStream2;
        this.writer = pdfWriter;
        this.ref = pdfWriter.getPdfIndirectReference();
        put(PdfName.LENGTH, this.ref);
    }

    protected PdfStream() {
        this.type = 7;
    }

    public void writeLength() throws IOException {
        if (this.inputStream != null) {
            int i = this.inputStreamLength;
            if (i != -1) {
                this.writer.addToBody((PdfObject) new PdfNumber(i), this.ref, false);
                return;
            }
            throw new IOException(MessageLocalization.getComposedMessage("writelength.can.only.be.called.after.output.of.the.stream.body", new Object[0]));
        }
        throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("writelength.can.only.be.called.in.a.contructed.pdfstream.inputstream.pdfwriter", new Object[0]));
    }

    public int getRawLength() {
        return this.rawLength;
    }

    public void flateCompress() {
        flateCompress(-1);
    }

    public void flateCompress(int i) {
        if (Document.compress && !this.compressed) {
            this.compressionLevel = i;
            if (this.inputStream != null) {
                this.compressed = true;
                return;
            }
            PdfObject pdfObject = PdfReader.getPdfObject(get(PdfName.FILTER));
            if (pdfObject != null) {
                if (pdfObject.isName()) {
                    if (PdfName.FLATEDECODE.equals(pdfObject)) {
                        return;
                    }
                } else if (!pdfObject.isArray()) {
                    throw new RuntimeException(MessageLocalization.getComposedMessage("stream.could.not.be.compressed.filter.is.not.a.name.or.array", new Object[0]));
                } else if (((PdfArray) pdfObject).contains(PdfName.FLATEDECODE)) {
                    return;
                }
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Deflater deflater = new Deflater(i);
                DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
                ByteArrayOutputStream byteArrayOutputStream2 = this.streamBytes;
                if (byteArrayOutputStream2 != null) {
                    byteArrayOutputStream2.writeTo(deflaterOutputStream);
                } else {
                    deflaterOutputStream.write(this.bytes);
                }
                deflaterOutputStream.close();
                deflater.end();
                this.streamBytes = byteArrayOutputStream;
                this.bytes = null;
                put(PdfName.LENGTH, new PdfNumber(this.streamBytes.size()));
                if (pdfObject == null) {
                    put(PdfName.FILTER, PdfName.FLATEDECODE);
                } else {
                    PdfArray pdfArray = new PdfArray(pdfObject);
                    pdfArray.add(0, PdfName.FLATEDECODE);
                    put(PdfName.FILTER, pdfArray);
                }
                this.compressed = true;
            } catch (IOException e) {
                throw new ExceptionConverter(e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void superToPdf(PdfWriter pdfWriter, OutputStream outputStream) throws IOException {
        super.toPdf(pdfWriter, outputStream);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: com.itextpdf.text.pdf.OutputStreamEncryption} */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0045, code lost:
        if (com.itextpdf.text.pdf.PdfName.CRYPT.equals(r3.getPdfObject(0)) != false) goto L_0x002b;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void toPdf(com.itextpdf.text.pdf.PdfWriter r9, java.io.OutputStream r10) throws java.io.IOException {
        /*
            r8 = this;
            java.io.InputStream r0 = r8.inputStream
            if (r0 == 0) goto L_0x000f
            boolean r0 = r8.compressed
            if (r0 == 0) goto L_0x000f
            com.itextpdf.text.pdf.PdfName r0 = com.itextpdf.text.pdf.PdfName.FILTER
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.FLATEDECODE
            r8.put(r0, r1)
        L_0x000f:
            r0 = 0
            if (r9 == 0) goto L_0x0017
            com.itextpdf.text.pdf.PdfEncryption r1 = r9.getEncryption()
            goto L_0x0018
        L_0x0017:
            r1 = r0
        L_0x0018:
            r2 = 0
            if (r1 == 0) goto L_0x0048
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.FILTER
            com.itextpdf.text.pdf.PdfObject r3 = r8.get(r3)
            if (r3 == 0) goto L_0x0048
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.CRYPT
            boolean r4 = r4.equals(r3)
            if (r4 == 0) goto L_0x002d
        L_0x002b:
            r1 = r0
            goto L_0x0048
        L_0x002d:
            boolean r4 = r3.isArray()
            if (r4 == 0) goto L_0x0048
            com.itextpdf.text.pdf.PdfArray r3 = (com.itextpdf.text.pdf.PdfArray) r3
            boolean r4 = r3.isEmpty()
            if (r4 != 0) goto L_0x0048
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.CRYPT
            com.itextpdf.text.pdf.PdfObject r3 = r3.getPdfObject(r2)
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x0048
            goto L_0x002b
        L_0x0048:
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.LENGTH
            com.itextpdf.text.pdf.PdfObject r3 = r8.get(r3)
            if (r1 == 0) goto L_0x0076
            if (r3 == 0) goto L_0x0076
            boolean r4 = r3.isNumber()
            if (r4 == 0) goto L_0x0076
            r4 = r3
            com.itextpdf.text.pdf.PdfNumber r4 = (com.itextpdf.text.pdf.PdfNumber) r4
            int r4 = r4.intValue()
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.LENGTH
            com.itextpdf.text.pdf.PdfNumber r6 = new com.itextpdf.text.pdf.PdfNumber
            int r4 = r1.calculateStreamSize(r4)
            r6.<init>((int) r4)
            r8.put(r5, r6)
            r8.superToPdf(r9, r10)
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.LENGTH
            r8.put(r4, r3)
            goto L_0x0079
        L_0x0076:
            r8.superToPdf(r9, r10)
        L_0x0079:
            r3 = 9
            com.itextpdf.text.pdf.PdfWriter.checkPdfIsoConformance(r9, r3, r8)
            byte[] r9 = STARTSTREAM
            r10.write(r9)
            java.io.InputStream r9 = r8.inputStream
            if (r9 == 0) goto L_0x00e1
            r8.rawLength = r2
            com.itextpdf.text.pdf.OutputStreamCounter r9 = new com.itextpdf.text.pdf.OutputStreamCounter
            r9.<init>(r10)
            if (r1 == 0) goto L_0x009c
            boolean r3 = r1.isEmbeddedFilesOnly()
            if (r3 != 0) goto L_0x009c
            com.itextpdf.text.pdf.OutputStreamEncryption r1 = r1.getEncryptionStream(r9)
            r3 = r1
            goto L_0x009e
        L_0x009c:
            r1 = r9
            r3 = r0
        L_0x009e:
            boolean r4 = r8.compressed
            if (r4 == 0) goto L_0x00b5
            java.util.zip.Deflater r0 = new java.util.zip.Deflater
            int r4 = r8.compressionLevel
            r0.<init>(r4)
            java.util.zip.DeflaterOutputStream r4 = new java.util.zip.DeflaterOutputStream
            r5 = 32768(0x8000, float:4.5918E-41)
            r4.<init>(r1, r0, r5)
            r5 = r4
            r4 = r0
            r0 = r5
            goto L_0x00b7
        L_0x00b5:
            r4 = r0
            r5 = r1
        L_0x00b7:
            r1 = 4192(0x1060, float:5.874E-42)
            byte[] r6 = new byte[r1]
        L_0x00bb:
            java.io.InputStream r1 = r8.inputStream
            int r1 = r1.read(r6)
            if (r1 > 0) goto L_0x00d8
            if (r0 == 0) goto L_0x00cb
            r0.finish()
            r4.end()
        L_0x00cb:
            if (r3 == 0) goto L_0x00d0
            r3.finish()
        L_0x00d0:
            long r0 = r9.getCounter()
            int r9 = (int) r0
            r8.inputStreamLength = r9
            goto L_0x010d
        L_0x00d8:
            r5.write(r6, r2, r1)
            int r7 = r8.rawLength
            int r7 = r7 + r1
            r8.rawLength = r7
            goto L_0x00bb
        L_0x00e1:
            if (r1 == 0) goto L_0x0100
            boolean r9 = r1.isEmbeddedFilesOnly()
            if (r9 != 0) goto L_0x0100
            java.io.ByteArrayOutputStream r9 = r8.streamBytes
            if (r9 == 0) goto L_0x00f6
            byte[] r9 = r9.toByteArray()
            byte[] r9 = r1.encryptByteArray(r9)
            goto L_0x00fc
        L_0x00f6:
            byte[] r9 = r8.bytes
            byte[] r9 = r1.encryptByteArray(r9)
        L_0x00fc:
            r10.write(r9)
            goto L_0x010d
        L_0x0100:
            java.io.ByteArrayOutputStream r9 = r8.streamBytes
            if (r9 == 0) goto L_0x0108
            r9.writeTo(r10)
            goto L_0x010d
        L_0x0108:
            byte[] r9 = r8.bytes
            r10.write(r9)
        L_0x010d:
            byte[] r9 = ENDSTREAM
            r10.write(r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfStream.toPdf(com.itextpdf.text.pdf.PdfWriter, java.io.OutputStream):void");
    }

    public void writeContent(OutputStream outputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = this.streamBytes;
        if (byteArrayOutputStream != null) {
            byteArrayOutputStream.writeTo(outputStream);
        } else if (this.bytes != null) {
            outputStream.write(this.bytes);
        }
    }

    public String toString() {
        if (get(PdfName.TYPE) == null) {
            return "Stream";
        }
        return "Stream of type: " + get(PdfName.TYPE);
    }
}
