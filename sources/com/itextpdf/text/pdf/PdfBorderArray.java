package com.itextpdf.text.pdf;

public class PdfBorderArray extends PdfArray {
    public PdfBorderArray(float f, float f2, float f3) {
        this(f, f2, f3, (PdfDashPattern) null);
    }

    public PdfBorderArray(float f, float f2, float f3, PdfDashPattern pdfDashPattern) {
        super((PdfObject) new PdfNumber(f));
        add((PdfObject) new PdfNumber(f2));
        add((PdfObject) new PdfNumber(f3));
        if (pdfDashPattern != null) {
            add((PdfObject) pdfDashPattern);
        }
    }
}
