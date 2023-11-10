package com.itextpdf.text.pdf;

import com.itextpdf.text.pdf.collection.PdfCollectionItem;
import java.io.IOException;
import java.io.OutputStream;

public class PdfFileSpecification extends PdfDictionary {
    protected PdfIndirectReference ref;
    protected PdfWriter writer;

    public PdfFileSpecification() {
        super(PdfName.FILESPEC);
    }

    public static PdfFileSpecification url(PdfWriter pdfWriter, String str) {
        PdfFileSpecification pdfFileSpecification = new PdfFileSpecification();
        pdfFileSpecification.writer = pdfWriter;
        pdfFileSpecification.put(PdfName.f587FS, PdfName.URL);
        pdfFileSpecification.put(PdfName.f580F, new PdfString(str));
        return pdfFileSpecification;
    }

    public static PdfFileSpecification fileEmbedded(PdfWriter pdfWriter, String str, String str2, byte[] bArr) throws IOException {
        return fileEmbedded(pdfWriter, str, str2, bArr, 9);
    }

    public static PdfFileSpecification fileEmbedded(PdfWriter pdfWriter, String str, String str2, byte[] bArr, int i) throws IOException {
        return fileEmbedded(pdfWriter, str, str2, bArr, (String) null, (PdfDictionary) null, i);
    }

    public static PdfFileSpecification fileEmbedded(PdfWriter pdfWriter, String str, String str2, byte[] bArr, boolean z) throws IOException {
        return fileEmbedded(pdfWriter, str, str2, bArr, (String) null, (PdfDictionary) null, z ? 9 : 0);
    }

    public static PdfFileSpecification fileEmbedded(PdfWriter pdfWriter, String str, String str2, byte[] bArr, boolean z, String str3, PdfDictionary pdfDictionary) throws IOException {
        return fileEmbedded(pdfWriter, str, str2, bArr, str3, pdfDictionary, z ? 9 : 0);
    }

    /* JADX WARNING: type inference failed for: r6v1 */
    /* JADX WARNING: type inference failed for: r6v2, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r6v3, types: [com.itextpdf.text.pdf.PdfIndirectReference, com.itextpdf.text.pdf.PdfObject] */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010a A[SYNTHETIC, Splitter:B:49:0x010a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.text.pdf.PdfFileSpecification fileEmbedded(com.itextpdf.text.pdf.PdfWriter r4, java.lang.String r5, java.lang.String r6, byte[] r7, java.lang.String r8, com.itextpdf.text.pdf.PdfDictionary r9, int r10) throws java.io.IOException {
        /*
            com.itextpdf.text.pdf.PdfFileSpecification r0 = new com.itextpdf.text.pdf.PdfFileSpecification
            r0.<init>()
            r0.writer = r4
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.f580F
            com.itextpdf.text.pdf.PdfString r2 = new com.itextpdf.text.pdf.PdfString
            r2.<init>((java.lang.String) r6)
            r0.put(r1, r2)
            r1 = 0
            r0.setUnicodeFileName(r6, r1)
            r6 = 0
            if (r7 != 0) goto L_0x0079
            com.itextpdf.text.pdf.PdfIndirectReference r2 = r4.getPdfIndirectReference()     // Catch:{ all -> 0x0107 }
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x0107 }
            r3.<init>(r5)     // Catch:{ all -> 0x0107 }
            boolean r3 = r3.canRead()     // Catch:{ all -> 0x0107 }
            if (r3 == 0) goto L_0x002e
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ all -> 0x0107 }
            r1.<init>(r5)     // Catch:{ all -> 0x0107 }
            r6 = r1
            goto L_0x0071
        L_0x002e:
            java.lang.String r3 = "file:/"
            boolean r3 = r5.startsWith(r3)     // Catch:{ all -> 0x0107 }
            if (r3 != 0) goto L_0x0067
            java.lang.String r3 = "http://"
            boolean r3 = r5.startsWith(r3)     // Catch:{ all -> 0x0107 }
            if (r3 != 0) goto L_0x0067
            java.lang.String r3 = "https://"
            boolean r3 = r5.startsWith(r3)     // Catch:{ all -> 0x0107 }
            if (r3 != 0) goto L_0x0067
            java.lang.String r3 = "jar:"
            boolean r3 = r5.startsWith(r3)     // Catch:{ all -> 0x0107 }
            if (r3 == 0) goto L_0x004f
            goto L_0x0067
        L_0x004f:
            java.io.InputStream r6 = com.itextpdf.text.p018io.StreamUtil.getResourceStream(r5)     // Catch:{ all -> 0x0107 }
            if (r6 == 0) goto L_0x0056
            goto L_0x0071
        L_0x0056:
            java.io.IOException r4 = new java.io.IOException     // Catch:{ all -> 0x0107 }
            java.lang.String r7 = "1.not.found.as.file.or.resource"
            r8 = 1
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x0107 }
            r8[r1] = r5     // Catch:{ all -> 0x0107 }
            java.lang.String r5 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r7, (java.lang.Object[]) r8)     // Catch:{ all -> 0x0107 }
            r4.<init>(r5)     // Catch:{ all -> 0x0107 }
            throw r4     // Catch:{ all -> 0x0107 }
        L_0x0067:
            java.net.URL r1 = new java.net.URL     // Catch:{ all -> 0x0107 }
            r1.<init>(r5)     // Catch:{ all -> 0x0107 }
            java.io.InputStream r5 = r1.openStream()     // Catch:{ all -> 0x0107 }
            r6 = r5
        L_0x0071:
            com.itextpdf.text.pdf.PdfEFStream r5 = new com.itextpdf.text.pdf.PdfEFStream     // Catch:{ all -> 0x0107 }
            r5.<init>(r6, r4)     // Catch:{ all -> 0x0107 }
            r1 = r6
            r6 = r2
            goto L_0x007f
        L_0x0079:
            com.itextpdf.text.pdf.PdfEFStream r5 = new com.itextpdf.text.pdf.PdfEFStream     // Catch:{ all -> 0x0107 }
            r5.<init>(r7)     // Catch:{ all -> 0x0107 }
            r1 = r6
        L_0x007f:
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.TYPE     // Catch:{ all -> 0x0104 }
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.EMBEDDEDFILE     // Catch:{ all -> 0x0104 }
            r5.put(r2, r3)     // Catch:{ all -> 0x0104 }
            r5.flateCompress(r10)     // Catch:{ all -> 0x0104 }
            com.itextpdf.text.pdf.PdfDictionary r10 = new com.itextpdf.text.pdf.PdfDictionary     // Catch:{ all -> 0x0104 }
            r10.<init>()     // Catch:{ all -> 0x0104 }
            if (r9 == 0) goto L_0x0093
            r10.merge(r9)     // Catch:{ all -> 0x0104 }
        L_0x0093:
            com.itextpdf.text.pdf.PdfName r9 = com.itextpdf.text.pdf.PdfName.MODDATE     // Catch:{ all -> 0x0104 }
            boolean r9 = r10.contains(r9)     // Catch:{ all -> 0x0104 }
            if (r9 != 0) goto L_0x00a5
            com.itextpdf.text.pdf.PdfName r9 = com.itextpdf.text.pdf.PdfName.MODDATE     // Catch:{ all -> 0x0104 }
            com.itextpdf.text.pdf.PdfDate r2 = new com.itextpdf.text.pdf.PdfDate     // Catch:{ all -> 0x0104 }
            r2.<init>()     // Catch:{ all -> 0x0104 }
            r10.put(r9, r2)     // Catch:{ all -> 0x0104 }
        L_0x00a5:
            if (r7 != 0) goto L_0x00ad
            com.itextpdf.text.pdf.PdfName r9 = com.itextpdf.text.pdf.PdfName.PARAMS     // Catch:{ all -> 0x0104 }
            r5.put(r9, r6)     // Catch:{ all -> 0x0104 }
            goto L_0x00c0
        L_0x00ad:
            com.itextpdf.text.pdf.PdfName r9 = com.itextpdf.text.pdf.PdfName.SIZE     // Catch:{ all -> 0x0104 }
            com.itextpdf.text.pdf.PdfNumber r2 = new com.itextpdf.text.pdf.PdfNumber     // Catch:{ all -> 0x0104 }
            int r3 = r5.getRawLength()     // Catch:{ all -> 0x0104 }
            r2.<init>((int) r3)     // Catch:{ all -> 0x0104 }
            r10.put(r9, r2)     // Catch:{ all -> 0x0104 }
            com.itextpdf.text.pdf.PdfName r9 = com.itextpdf.text.pdf.PdfName.PARAMS     // Catch:{ all -> 0x0104 }
            r5.put(r9, r10)     // Catch:{ all -> 0x0104 }
        L_0x00c0:
            if (r8 == 0) goto L_0x00cc
            com.itextpdf.text.pdf.PdfName r9 = com.itextpdf.text.pdf.PdfName.SUBTYPE     // Catch:{ all -> 0x0104 }
            com.itextpdf.text.pdf.PdfName r2 = new com.itextpdf.text.pdf.PdfName     // Catch:{ all -> 0x0104 }
            r2.<init>((java.lang.String) r8)     // Catch:{ all -> 0x0104 }
            r5.put(r9, r2)     // Catch:{ all -> 0x0104 }
        L_0x00cc:
            com.itextpdf.text.pdf.PdfIndirectObject r8 = r4.addToBody(r5)     // Catch:{ all -> 0x0104 }
            com.itextpdf.text.pdf.PdfIndirectReference r8 = r8.getIndirectReference()     // Catch:{ all -> 0x0104 }
            if (r7 != 0) goto L_0x00ea
            r5.writeLength()     // Catch:{ all -> 0x0104 }
            com.itextpdf.text.pdf.PdfName r7 = com.itextpdf.text.pdf.PdfName.SIZE     // Catch:{ all -> 0x0104 }
            com.itextpdf.text.pdf.PdfNumber r9 = new com.itextpdf.text.pdf.PdfNumber     // Catch:{ all -> 0x0104 }
            int r5 = r5.getRawLength()     // Catch:{ all -> 0x0104 }
            r9.<init>((int) r5)     // Catch:{ all -> 0x0104 }
            r10.put(r7, r9)     // Catch:{ all -> 0x0104 }
            r4.addToBody((com.itextpdf.text.pdf.PdfObject) r10, (com.itextpdf.text.pdf.PdfIndirectReference) r6)     // Catch:{ all -> 0x0104 }
        L_0x00ea:
            if (r1 == 0) goto L_0x00ef
            r1.close()     // Catch:{ Exception -> 0x00ef }
        L_0x00ef:
            com.itextpdf.text.pdf.PdfDictionary r4 = new com.itextpdf.text.pdf.PdfDictionary
            r4.<init>()
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.f580F
            r4.put(r5, r8)
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.f662UF
            r4.put(r5, r8)
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.f579EF
            r0.put(r5, r4)
            return r0
        L_0x0104:
            r4 = move-exception
            r6 = r1
            goto L_0x0108
        L_0x0107:
            r4 = move-exception
        L_0x0108:
            if (r6 == 0) goto L_0x010d
            r6.close()     // Catch:{ Exception -> 0x010d }
        L_0x010d:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfFileSpecification.fileEmbedded(com.itextpdf.text.pdf.PdfWriter, java.lang.String, java.lang.String, byte[], java.lang.String, com.itextpdf.text.pdf.PdfDictionary, int):com.itextpdf.text.pdf.PdfFileSpecification");
    }

    public static PdfFileSpecification fileExtern(PdfWriter pdfWriter, String str) {
        PdfFileSpecification pdfFileSpecification = new PdfFileSpecification();
        pdfFileSpecification.writer = pdfWriter;
        pdfFileSpecification.put(PdfName.f580F, new PdfString(str));
        pdfFileSpecification.setUnicodeFileName(str, false);
        return pdfFileSpecification;
    }

    public PdfIndirectReference getReference() throws IOException {
        PdfIndirectReference pdfIndirectReference = this.ref;
        if (pdfIndirectReference != null) {
            return pdfIndirectReference;
        }
        PdfIndirectReference indirectReference = this.writer.addToBody(this).getIndirectReference();
        this.ref = indirectReference;
        return indirectReference;
    }

    public void setMultiByteFileName(byte[] bArr) {
        put(PdfName.f580F, new PdfString(bArr).setHexWriting(true));
    }

    public void setUnicodeFileName(String str, boolean z) {
        put(PdfName.f662UF, new PdfString(str, z ? PdfObject.TEXT_UNICODE : PdfObject.TEXT_PDFDOCENCODING));
    }

    public void setVolatile(boolean z) {
        put(PdfName.f664V, new PdfBoolean(z));
    }

    public void addDescription(String str, boolean z) {
        put(PdfName.DESC, new PdfString(str, z ? PdfObject.TEXT_UNICODE : PdfObject.TEXT_PDFDOCENCODING));
    }

    public void addCollectionItem(PdfCollectionItem pdfCollectionItem) {
        put(PdfName.f564CI, pdfCollectionItem);
    }

    public void toPdf(PdfWriter pdfWriter, OutputStream outputStream) throws IOException {
        PdfWriter.checkPdfIsoConformance(pdfWriter, 10, this);
        super.toPdf(pdfWriter, outputStream);
    }
}
