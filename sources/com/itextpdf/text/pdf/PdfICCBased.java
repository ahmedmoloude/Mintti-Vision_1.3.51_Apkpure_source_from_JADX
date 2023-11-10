package com.itextpdf.text.pdf;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.error_messages.MessageLocalization;

public class PdfICCBased extends PdfStream {
    public PdfICCBased(ICC_Profile iCC_Profile) {
        this(iCC_Profile, -1);
    }

    public PdfICCBased(ICC_Profile iCC_Profile, int i) {
        try {
            int numComponents = iCC_Profile.getNumComponents();
            if (numComponents == 1) {
                put(PdfName.ALTERNATE, PdfName.DEVICEGRAY);
            } else if (numComponents == 3) {
                put(PdfName.ALTERNATE, PdfName.DEVICERGB);
            } else if (numComponents == 4) {
                put(PdfName.ALTERNATE, PdfName.DEVICECMYK);
            } else {
                throw new PdfException(MessageLocalization.getComposedMessage("1.component.s.is.not.supported", numComponents));
            }
            put(PdfName.f610N, new PdfNumber(numComponents));
            this.bytes = iCC_Profile.getData();
            put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
            flateCompress(i);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }
}
