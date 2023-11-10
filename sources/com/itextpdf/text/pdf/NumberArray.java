package com.itextpdf.text.pdf;

import java.util.List;

public class NumberArray extends PdfArray {
    public NumberArray(float... fArr) {
        for (float pdfNumber : fArr) {
            add((PdfObject) new PdfNumber(pdfNumber));
        }
    }

    public NumberArray(List<PdfNumber> list) {
        for (PdfNumber add : list) {
            add((PdfObject) add);
        }
    }
}
