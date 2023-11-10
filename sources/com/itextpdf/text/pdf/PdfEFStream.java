package com.itextpdf.text.pdf;

import java.io.InputStream;

public class PdfEFStream extends PdfStream {
    public PdfEFStream(InputStream inputStream, PdfWriter pdfWriter) {
        super(inputStream, pdfWriter);
    }

    public PdfEFStream(byte[] bArr) {
        super(bArr);
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
            if (r1 == 0) goto L_0x0089
            boolean r3 = r1.isEmbeddedFilesOnly()
            if (r3 == 0) goto L_0x0089
            com.itextpdf.text.pdf.PdfArray r3 = new com.itextpdf.text.pdf.PdfArray
            r3.<init>()
            com.itextpdf.text.pdf.PdfArray r4 = new com.itextpdf.text.pdf.PdfArray
            r4.<init>()
            com.itextpdf.text.pdf.PdfDictionary r5 = new com.itextpdf.text.pdf.PdfDictionary
            r5.<init>()
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.NAME
            com.itextpdf.text.pdf.PdfName r7 = com.itextpdf.text.pdf.PdfName.STDCF
            r5.put(r6, r7)
            com.itextpdf.text.pdf.PdfName r6 = com.itextpdf.text.pdf.PdfName.CRYPT
            r3.add((com.itextpdf.text.pdf.PdfObject) r6)
            r4.add((com.itextpdf.text.pdf.PdfObject) r5)
            boolean r5 = r8.compressed
            if (r5 == 0) goto L_0x007f
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.FLATEDECODE
            r3.add((com.itextpdf.text.pdf.PdfObject) r5)
            com.itextpdf.text.pdf.PdfNull r5 = new com.itextpdf.text.pdf.PdfNull
            r5.<init>()
            r4.add((com.itextpdf.text.pdf.PdfObject) r5)
        L_0x007f:
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.FILTER
            r8.put(r5, r3)
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.DECODEPARMS
            r8.put(r3, r4)
        L_0x0089:
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.LENGTH
            com.itextpdf.text.pdf.PdfObject r3 = r8.get(r3)
            if (r1 == 0) goto L_0x00b7
            if (r3 == 0) goto L_0x00b7
            boolean r4 = r3.isNumber()
            if (r4 == 0) goto L_0x00b7
            r4 = r3
            com.itextpdf.text.pdf.PdfNumber r4 = (com.itextpdf.text.pdf.PdfNumber) r4
            int r4 = r4.intValue()
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.LENGTH
            com.itextpdf.text.pdf.PdfNumber r6 = new com.itextpdf.text.pdf.PdfNumber
            int r4 = r1.calculateStreamSize(r4)
            r6.<init>((int) r4)
            r8.put(r5, r6)
            r8.superToPdf(r9, r10)
            com.itextpdf.text.pdf.PdfName r9 = com.itextpdf.text.pdf.PdfName.LENGTH
            r8.put(r9, r3)
            goto L_0x00ba
        L_0x00b7:
            r8.superToPdf(r9, r10)
        L_0x00ba:
            byte[] r9 = STARTSTREAM
            r10.write(r9)
            java.io.InputStream r9 = r8.inputStream
            if (r9 == 0) goto L_0x0117
            r8.rawLength = r2
            com.itextpdf.text.pdf.OutputStreamCounter r9 = new com.itextpdf.text.pdf.OutputStreamCounter
            r9.<init>(r10)
            if (r1 == 0) goto L_0x00d2
            com.itextpdf.text.pdf.OutputStreamEncryption r1 = r1.getEncryptionStream(r9)
            r3 = r1
            goto L_0x00d4
        L_0x00d2:
            r1 = r9
            r3 = r0
        L_0x00d4:
            boolean r4 = r8.compressed
            if (r4 == 0) goto L_0x00eb
            java.util.zip.Deflater r0 = new java.util.zip.Deflater
            int r4 = r8.compressionLevel
            r0.<init>(r4)
            java.util.zip.DeflaterOutputStream r4 = new java.util.zip.DeflaterOutputStream
            r5 = 32768(0x8000, float:4.5918E-41)
            r4.<init>(r1, r0, r5)
            r5 = r4
            r4 = r0
            r0 = r5
            goto L_0x00ed
        L_0x00eb:
            r4 = r0
            r5 = r1
        L_0x00ed:
            r1 = 4192(0x1060, float:5.874E-42)
            byte[] r6 = new byte[r1]
        L_0x00f1:
            java.io.InputStream r1 = r8.inputStream
            int r1 = r1.read(r6)
            if (r1 > 0) goto L_0x010e
            if (r0 == 0) goto L_0x0101
            r0.finish()
            r4.end()
        L_0x0101:
            if (r3 == 0) goto L_0x0106
            r3.finish()
        L_0x0106:
            long r0 = r9.getCounter()
            int r9 = (int) r0
            r8.inputStreamLength = r9
            goto L_0x0141
        L_0x010e:
            r5.write(r6, r2, r1)
            int r7 = r8.rawLength
            int r7 = r7 + r1
            r8.rawLength = r7
            goto L_0x00f1
        L_0x0117:
            if (r1 != 0) goto L_0x0129
            java.io.ByteArrayOutputStream r9 = r8.streamBytes
            if (r9 == 0) goto L_0x0123
            java.io.ByteArrayOutputStream r9 = r8.streamBytes
            r9.writeTo(r10)
            goto L_0x0141
        L_0x0123:
            byte[] r9 = r8.bytes
            r10.write(r9)
            goto L_0x0141
        L_0x0129:
            java.io.ByteArrayOutputStream r9 = r8.streamBytes
            if (r9 == 0) goto L_0x0138
            java.io.ByteArrayOutputStream r9 = r8.streamBytes
            byte[] r9 = r9.toByteArray()
            byte[] r9 = r1.encryptByteArray(r9)
            goto L_0x013e
        L_0x0138:
            byte[] r9 = r8.bytes
            byte[] r9 = r1.encryptByteArray(r9)
        L_0x013e:
            r10.write(r9)
        L_0x0141:
            byte[] r9 = ENDSTREAM
            r10.write(r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfEFStream.toPdf(com.itextpdf.text.pdf.PdfWriter, java.io.OutputStream):void");
    }
}
