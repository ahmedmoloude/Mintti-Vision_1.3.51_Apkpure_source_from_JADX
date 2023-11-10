package com.itextpdf.text.pdf;

import com.itextpdf.text.error_messages.MessageLocalization;

public class PdfBorderDictionary extends PdfDictionary {
    public static final int STYLE_BEVELED = 2;
    public static final int STYLE_DASHED = 1;
    public static final int STYLE_INSET = 3;
    public static final int STYLE_SOLID = 0;
    public static final int STYLE_UNDERLINE = 4;

    public PdfBorderDictionary(float f, int i, PdfDashPattern pdfDashPattern) {
        put(PdfName.f668W, new PdfNumber(f));
        if (i == 0) {
            put(PdfName.f642S, PdfName.f642S);
        } else if (i == 1) {
            if (pdfDashPattern != null) {
                put(PdfName.f567D, pdfDashPattern);
            }
            put(PdfName.f642S, PdfName.f567D);
        } else if (i == 2) {
            put(PdfName.f642S, PdfName.f551B);
        } else if (i == 3) {
            put(PdfName.f642S, PdfName.f598I);
        } else if (i == 4) {
            put(PdfName.f642S, PdfName.f660U);
        } else {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.border.style", new Object[0]));
        }
    }

    public PdfBorderDictionary(float f, int i) {
        this(f, i, (PdfDashPattern) null);
    }
}
