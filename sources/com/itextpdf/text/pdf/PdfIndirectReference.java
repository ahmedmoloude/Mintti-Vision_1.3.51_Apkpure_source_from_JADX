package com.itextpdf.text.pdf;

import java.io.IOException;
import java.io.OutputStream;

public class PdfIndirectReference extends PdfObject {
    protected int generation;
    protected int number;

    protected PdfIndirectReference() {
        super(0);
        this.generation = 0;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    PdfIndirectReference(int r2, int r3, int r4) {
        /*
            r1 = this;
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            r2.append(r3)
            java.lang.String r0 = " "
            r2.append(r0)
            r2.append(r4)
            java.lang.String r0 = " R"
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            r0 = 0
            r1.<init>((int) r0, (java.lang.String) r2)
            r1.generation = r0
            r1.number = r3
            r1.generation = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfIndirectReference.<init>(int, int, int):void");
    }

    protected PdfIndirectReference(int i, int i2) {
        this(i, i2, 0);
    }

    public int getNumber() {
        return this.number;
    }

    public int getGeneration() {
        return this.generation;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.number);
        stringBuffer.append(" ");
        stringBuffer.append(this.generation);
        stringBuffer.append(" R");
        return stringBuffer.toString();
    }

    public void toPdf(PdfWriter pdfWriter, OutputStream outputStream) throws IOException {
        outputStream.write(PdfEncodings.convertToBytes(toString(), (String) null));
    }
}
