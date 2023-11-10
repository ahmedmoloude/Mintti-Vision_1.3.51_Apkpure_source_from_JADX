package com.itextpdf.text.pdf;

import com.itextpdf.text.BaseColor;

class PdfColor extends PdfArray {
    PdfColor(int i, int i2, int i3) {
        super((PdfObject) new PdfNumber(((double) (i & 255)) / 255.0d));
        add((PdfObject) new PdfNumber(((double) (i2 & 255)) / 255.0d));
        add((PdfObject) new PdfNumber(((double) (i3 & 255)) / 255.0d));
    }

    PdfColor(BaseColor baseColor) {
        this(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue());
    }
}
