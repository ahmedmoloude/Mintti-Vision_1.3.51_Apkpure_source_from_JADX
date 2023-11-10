package com.itextpdf.text.pdf;

import com.itextpdf.text.BaseColor;

public class LabColor extends ExtendedColor {

    /* renamed from: a */
    private float f531a;

    /* renamed from: b */
    private float f532b;

    /* renamed from: l */
    private float f533l;
    PdfLabColor labColorSpace;

    public LabColor(PdfLabColor pdfLabColor, float f, float f2, float f3) {
        super(7);
        this.labColorSpace = pdfLabColor;
        this.f533l = f;
        this.f531a = f2;
        this.f532b = f3;
        BaseColor lab2Rgb = pdfLabColor.lab2Rgb(f, f2, f3);
        setValue(lab2Rgb.getRed(), lab2Rgb.getGreen(), lab2Rgb.getBlue(), 255);
    }

    public PdfLabColor getLabColorSpace() {
        return this.labColorSpace;
    }

    public float getL() {
        return this.f533l;
    }

    public float getA() {
        return this.f531a;
    }

    public float getB() {
        return this.f532b;
    }

    public BaseColor toRgb() {
        return this.labColorSpace.lab2Rgb(this.f533l, this.f531a, this.f532b);
    }

    /* access modifiers changed from: package-private */
    public CMYKColor toCmyk() {
        return this.labColorSpace.lab2Cmyk(this.f533l, this.f531a, this.f532b);
    }
}
