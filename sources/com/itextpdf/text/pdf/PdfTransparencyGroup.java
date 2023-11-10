package com.itextpdf.text.pdf;

public class PdfTransparencyGroup extends PdfDictionary {
    public PdfTransparencyGroup() {
        put(PdfName.f642S, PdfName.TRANSPARENCY);
    }

    public void setIsolated(boolean z) {
        if (z) {
            put(PdfName.f598I, PdfBoolean.PDFTRUE);
        } else {
            remove(PdfName.f598I);
        }
    }

    public void setKnockout(boolean z) {
        if (z) {
            put(PdfName.f605K, PdfBoolean.PDFTRUE);
        } else {
            remove(PdfName.f605K);
        }
    }
}
