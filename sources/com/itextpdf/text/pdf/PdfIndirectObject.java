package com.itextpdf.text.pdf;

import com.itextpdf.text.DocWriter;
import java.io.IOException;
import java.io.OutputStream;

public class PdfIndirectObject {
    static final byte[] ENDOBJ;
    static final int SIZEOBJ;
    static final byte[] STARTOBJ;
    protected int generation;
    protected int number;
    protected PdfObject object;
    protected PdfWriter writer;

    static {
        byte[] iSOBytes = DocWriter.getISOBytes(" obj\n");
        STARTOBJ = iSOBytes;
        byte[] iSOBytes2 = DocWriter.getISOBytes("\nendobj\n");
        ENDOBJ = iSOBytes2;
        SIZEOBJ = iSOBytes.length + iSOBytes2.length;
    }

    protected PdfIndirectObject(int i, PdfObject pdfObject, PdfWriter pdfWriter) {
        this(i, 0, pdfObject, pdfWriter);
    }

    PdfIndirectObject(PdfIndirectReference pdfIndirectReference, PdfObject pdfObject, PdfWriter pdfWriter) {
        this(pdfIndirectReference.getNumber(), pdfIndirectReference.getGeneration(), pdfObject, pdfWriter);
    }

    PdfIndirectObject(int i, int i2, PdfObject pdfObject, PdfWriter pdfWriter) {
        this.generation = 0;
        this.writer = pdfWriter;
        this.number = i;
        this.generation = i2;
        this.object = pdfObject;
        PdfEncryption encryption = pdfWriter != null ? pdfWriter.getEncryption() : null;
        if (encryption != null) {
            encryption.setHashKey(i, i2);
        }
    }

    public PdfIndirectReference getIndirectReference() {
        return new PdfIndirectReference(this.object.type(), this.number, this.generation);
    }

    /* access modifiers changed from: protected */
    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(DocWriter.getISOBytes(String.valueOf(this.number)));
        outputStream.write(32);
        outputStream.write(DocWriter.getISOBytes(String.valueOf(this.generation)));
        outputStream.write(STARTOBJ);
        this.object.toPdf(this.writer, outputStream);
        outputStream.write(ENDOBJ);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.number);
        stringBuffer.append(' ');
        stringBuffer.append(this.generation);
        stringBuffer.append(" R: ");
        PdfObject pdfObject = this.object;
        stringBuffer.append(pdfObject != null ? pdfObject.toString() : "null");
        return stringBuffer.toString();
    }
}
