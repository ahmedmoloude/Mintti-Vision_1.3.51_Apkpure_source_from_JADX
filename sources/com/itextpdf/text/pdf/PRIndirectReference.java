package com.itextpdf.text.pdf;

import java.io.IOException;
import java.io.OutputStream;

public class PRIndirectReference extends PdfIndirectReference {
    protected PdfReader reader;

    public PRIndirectReference(PdfReader pdfReader, int i, int i2) {
        this.type = 10;
        this.number = i;
        this.generation = i2;
        this.reader = pdfReader;
    }

    public PRIndirectReference(PdfReader pdfReader, int i) {
        this(pdfReader, i, 0);
    }

    public void toPdf(PdfWriter pdfWriter, OutputStream outputStream) throws IOException {
        if (pdfWriter != null) {
            int newObjectNumber = pdfWriter.getNewObjectNumber(this.reader, this.number, this.generation);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(newObjectNumber);
            stringBuffer.append(" ");
            stringBuffer.append(this.reader.isAppendable() ? this.generation : 0);
            stringBuffer.append(" R");
            outputStream.write(PdfEncodings.convertToBytes(stringBuffer.toString(), (String) null));
            return;
        }
        super.toPdf((PdfWriter) null, outputStream);
    }

    public PdfReader getReader() {
        return this.reader;
    }

    public void setNumber(int i, int i2) {
        this.number = i;
        this.generation = i2;
    }
}
