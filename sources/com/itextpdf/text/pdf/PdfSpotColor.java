package com.itextpdf.text.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.error_messages.MessageLocalization;

public class PdfSpotColor implements ICachedColorSpace, IPdfSpecialColorSpace {
    public ColorDetails altColorDetails;
    public BaseColor altcs;
    public PdfName name;

    public PdfSpotColor(String str, BaseColor baseColor) {
        this.name = new PdfName(str);
        this.altcs = baseColor;
    }

    public ColorDetails[] getColorantDetails(PdfWriter pdfWriter) {
        if (this.altColorDetails == null) {
            BaseColor baseColor = this.altcs;
            if ((baseColor instanceof ExtendedColor) && ((ExtendedColor) baseColor).getType() == 7) {
                this.altColorDetails = pdfWriter.addSimple((ICachedColorSpace) ((LabColor) this.altcs).getLabColorSpace());
            }
        }
        return new ColorDetails[]{this.altColorDetails};
    }

    public BaseColor getAlternativeCS() {
        return this.altcs;
    }

    public PdfName getName() {
        return this.name;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public PdfObject getSpotObject(PdfWriter pdfWriter) {
        return getPdfObject(pdfWriter);
    }

    public PdfObject getPdfObject(PdfWriter pdfWriter) {
        PdfFunction pdfFunction;
        PdfArray pdfArray = new PdfArray((PdfObject) PdfName.SEPARATION);
        pdfArray.add((PdfObject) this.name);
        BaseColor baseColor = this.altcs;
        if (baseColor instanceof ExtendedColor) {
            int i = ((ExtendedColor) baseColor).type;
            if (i == 1) {
                pdfArray.add((PdfObject) PdfName.DEVICEGRAY);
                PdfWriter pdfWriter2 = pdfWriter;
                pdfFunction = PdfFunction.type2(pdfWriter2, new float[]{0.0f, 1.0f}, (float[]) null, new float[]{1.0f}, new float[]{((GrayColor) this.altcs).getGray()}, 1.0f);
            } else if (i == 2) {
                pdfArray.add((PdfObject) PdfName.DEVICECMYK);
                CMYKColor cMYKColor = (CMYKColor) this.altcs;
                PdfWriter pdfWriter3 = pdfWriter;
                pdfFunction = PdfFunction.type2(pdfWriter3, new float[]{0.0f, 1.0f}, (float[]) null, new float[]{0.0f, 0.0f, 0.0f, 0.0f}, new float[]{cMYKColor.getCyan(), cMYKColor.getMagenta(), cMYKColor.getYellow(), cMYKColor.getBlack()}, 1.0f);
            } else if (i == 7) {
                LabColor labColor = (LabColor) this.altcs;
                ColorDetails colorDetails = this.altColorDetails;
                if (colorDetails != null) {
                    pdfArray.add((PdfObject) colorDetails.getIndirectReference());
                } else {
                    pdfArray.add(labColor.getLabColorSpace().getPdfObject(pdfWriter));
                }
                PdfWriter pdfWriter4 = pdfWriter;
                pdfFunction = PdfFunction.type2(pdfWriter4, new float[]{0.0f, 1.0f}, (float[]) null, new float[]{100.0f, 0.0f, 0.0f}, new float[]{labColor.getL(), labColor.getA(), labColor.getB()}, 1.0f);
            } else {
                throw new RuntimeException(MessageLocalization.getComposedMessage("only.rgb.gray.and.cmyk.are.supported.as.alternative.color.spaces", new Object[0]));
            }
        } else {
            pdfArray.add((PdfObject) PdfName.DEVICERGB);
            PdfWriter pdfWriter5 = pdfWriter;
            pdfFunction = PdfFunction.type2(pdfWriter5, new float[]{0.0f, 1.0f}, (float[]) null, new float[]{1.0f, 1.0f, 1.0f}, new float[]{((float) this.altcs.getRed()) / 255.0f, ((float) this.altcs.getGreen()) / 255.0f, ((float) this.altcs.getBlue()) / 255.0f}, 1.0f);
        }
        pdfArray.add((PdfObject) pdfFunction.getReference());
        return pdfArray;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PdfSpotColor)) {
            return false;
        }
        PdfSpotColor pdfSpotColor = (PdfSpotColor) obj;
        return this.altcs.equals(pdfSpotColor.altcs) && this.name.equals(pdfSpotColor.name);
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.altcs.hashCode();
    }
}
