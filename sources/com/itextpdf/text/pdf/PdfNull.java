package com.itextpdf.text.pdf;

public class PdfNull extends PdfObject {
    private static final String CONTENT = "null";
    public static final PdfNull PDFNULL = new PdfNull();

    public String toString() {
        return CONTENT;
    }

    public PdfNull() {
        super(8, CONTENT);
    }
}
