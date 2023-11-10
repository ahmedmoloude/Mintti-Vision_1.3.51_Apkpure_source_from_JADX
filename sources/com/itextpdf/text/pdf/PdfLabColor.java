package com.itextpdf.text.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.util.Arrays;

public class PdfLabColor implements ICachedColorSpace {
    float[] blackPoint;
    float[] range;
    float[] whitePoint;

    public PdfLabColor() {
        this.whitePoint = new float[]{0.9505f, 1.0f, 1.089f};
        this.blackPoint = null;
        this.range = null;
    }

    public PdfLabColor(float[] fArr) {
        this.whitePoint = new float[]{0.9505f, 1.0f, 1.089f};
        this.blackPoint = null;
        this.range = null;
        if (fArr == null || fArr.length != 3 || fArr[0] < 1.0E-6f || fArr[2] < 1.0E-6f || fArr[1] < 0.999999f || fArr[1] > 1.000001f) {
            throw new RuntimeException(MessageLocalization.getComposedMessage("lab.cs.white.point", new Object[0]));
        }
        this.whitePoint = fArr;
    }

    public PdfLabColor(float[] fArr, float[] fArr2) {
        this(fArr);
        this.blackPoint = fArr2;
    }

    public PdfLabColor(float[] fArr, float[] fArr2, float[] fArr3) {
        this(fArr, fArr2);
        this.range = fArr3;
    }

    public PdfObject getPdfObject(PdfWriter pdfWriter) {
        PdfArray pdfArray = new PdfArray((PdfObject) PdfName.LAB);
        PdfDictionary pdfDictionary = new PdfDictionary();
        float[] fArr = this.whitePoint;
        if (fArr == null || fArr.length != 3 || fArr[0] < 1.0E-6f || fArr[2] < 1.0E-6f || fArr[1] < 0.999999f || fArr[1] > 1.000001f) {
            throw new RuntimeException(MessageLocalization.getComposedMessage("lab.cs.white.point", new Object[0]));
        }
        pdfDictionary.put(PdfName.WHITEPOINT, new PdfArray(this.whitePoint));
        float[] fArr2 = this.blackPoint;
        if (fArr2 != null) {
            if (fArr2.length != 3 || fArr2[0] < -1.0E-6f || fArr2[1] < -1.0E-6f || fArr2[2] < -1.0E-6f) {
                throw new RuntimeException(MessageLocalization.getComposedMessage("lab.cs.black.point", new Object[0]));
            }
            pdfDictionary.put(PdfName.BLACKPOINT, new PdfArray(this.blackPoint));
        }
        float[] fArr3 = this.range;
        if (fArr3 != null) {
            if (fArr3.length != 4 || fArr3[0] > fArr3[1] || fArr3[2] > fArr3[3]) {
                throw new RuntimeException(MessageLocalization.getComposedMessage("lab.cs.range", new Object[0]));
            }
            pdfDictionary.put(PdfName.RANGE, new PdfArray(this.range));
        }
        pdfArray.add((PdfObject) pdfDictionary);
        return pdfArray;
    }

    public BaseColor lab2Rgb(float f, float f2, float f3) {
        double[] lab2RgbLinear = lab2RgbLinear(f, f2, f3);
        return new BaseColor((float) lab2RgbLinear[0], (float) lab2RgbLinear[1], (float) lab2RgbLinear[2]);
    }

    /* access modifiers changed from: package-private */
    public CMYKColor lab2Cmyk(float f, float f2, float f3) {
        double d;
        double d2;
        double[] lab2RgbLinear = lab2RgbLinear(f, f2, f3);
        double d3 = lab2RgbLinear[0];
        double d4 = lab2RgbLinear[1];
        double d5 = lab2RgbLinear[2];
        double d6 = 1.0d;
        double d7 = 0.0d;
        if (d3 == 0.0d && d4 == 0.0d && f3 == 0.0f) {
            d2 = 0.0d;
            d = 0.0d;
        } else {
            double d8 = 1.0d - d3;
            double d9 = 1.0d - d4;
            double d10 = 1.0d - d5;
            double min = Math.min(d8, Math.min(d9, d10));
            double d11 = 1.0d - min;
            double d12 = (d8 - min) / d11;
            d = (d9 - min) / d11;
            double d13 = (d10 - min) / d11;
            d6 = min;
            d7 = d12;
            d2 = d13;
        }
        return new CMYKColor((float) d7, (float) d, (float) d2, (float) d6);
    }

    /* access modifiers changed from: protected */
    public double[] lab2RgbLinear(float f, float f2, float f3) {
        float f4;
        float f5;
        float[] fArr = this.range;
        if (fArr == null || fArr.length != 4) {
            f4 = f2;
            f5 = f3;
        } else {
            f4 = f2 < fArr[0] ? fArr[0] : f2;
            if (f4 > fArr[1]) {
                f4 = fArr[1];
            }
            float f6 = f3 < fArr[2] ? fArr[2] : f3;
            f5 = f6 > fArr[3] ? fArr[3] : f6;
        }
        double d = ((double) (f + 16.0f)) / 116.0d;
        double d2 = (((double) f4) / 500.0d) + d;
        double d3 = d - (((double) f5) / 200.0d);
        double d4 = d2 > 0.20689655172413793d ? ((double) this.whitePoint[0]) * d2 * d2 * d2 : (d2 - 0.13793103448275862d) * 3.0d * 0.04280618311533888d * ((double) this.whitePoint[0]);
        double d5 = d > 0.20689655172413793d ? ((double) this.whitePoint[1]) * d * d * d : ((double) this.whitePoint[1]) * (d - 0.13793103448275862d) * 3.0d * 0.04280618311533888d;
        double d6 = d3 > 0.20689655172413793d ? ((double) this.whitePoint[2]) * d3 * d3 * d3 : (d3 - 0.13793103448275862d) * 3.0d * 0.04280618311533888d * ((double) this.whitePoint[2]);
        double[] dArr = {((3.241d * d4) - (1.5374d * d5)) - (0.4986d * d6), (((-d4) * 0.9692d) + (1.876d * d5)) - (0.0416d * d6), ((d4 * 0.0556d) - (d5 * 0.204d)) + (d6 * 1.057d)};
        for (int i = 0; i < 3; i++) {
            dArr[i] = dArr[i] <= 0.0031308d ? dArr[i] * 12.92d : (Math.pow(dArr[i], 0.4166666666666667d) * 1.055d) - 0.055d;
            if (dArr[i] < 0.0d) {
                dArr[i] = 0.0d;
            } else if (dArr[i] > 1.0d) {
                dArr[i] = 1.0d;
            }
        }
        return dArr;
    }

    public LabColor rgb2lab(BaseColor baseColor) {
        double red = (double) (((float) baseColor.getRed()) / 255.0f);
        double green = (double) (((float) baseColor.getGreen()) / 255.0f);
        double blue = (double) (((float) baseColor.getBlue()) / 255.0f);
        double pow = red > 0.04045d ? Math.pow((red + 0.055d) / 1.055d, 2.2d) : red / 12.92d;
        double pow2 = green > 0.04045d ? Math.pow((green + 0.055d) / 1.055d, 2.2d) : green / 12.92d;
        double pow3 = blue > 0.04045d ? Math.pow((blue + 0.055d) / 1.055d, 2.2d) : blue / 12.92d;
        double d = (0.2126d * pow) + (0.7152d * pow2) + (0.0722d * pow3);
        return new LabColor(this, ((float) Math.round(((fXyz(d / ((double) this.whitePoint[1])) * 116.0d) - 16.0d) * 1000.0d)) / 1000.0f, ((float) Math.round(((fXyz((((0.4124d * pow) + (0.3576d * pow2)) + (0.1805d * pow3)) / ((double) this.whitePoint[0])) - fXyz(d / ((double) this.whitePoint[1]))) * 500.0d) * 1000.0d)) / 1000.0f, ((float) Math.round(((fXyz(d / ((double) this.whitePoint[1])) - fXyz((((pow * 0.0193d) + (pow2 * 0.1192d)) + (pow3 * 0.9505d)) / ((double) this.whitePoint[2]))) * 200.0d) * 1000.0d)) / 1000.0f);
    }

    private static double fXyz(double d) {
        return d > 0.008856d ? Math.pow(d, 0.3333333333333333d) : (d * 7.787d) + 0.13793103448275862d;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PdfLabColor)) {
            return false;
        }
        PdfLabColor pdfLabColor = (PdfLabColor) obj;
        return Arrays.equals(this.blackPoint, pdfLabColor.blackPoint) && Arrays.equals(this.range, pdfLabColor.range) && Arrays.equals(this.whitePoint, pdfLabColor.whitePoint);
    }

    public int hashCode() {
        int hashCode = Arrays.hashCode(this.whitePoint) * 31;
        float[] fArr = this.blackPoint;
        int i = 0;
        int hashCode2 = (hashCode + (fArr != null ? Arrays.hashCode(fArr) : 0)) * 31;
        float[] fArr2 = this.range;
        if (fArr2 != null) {
            i = Arrays.hashCode(fArr2);
        }
        return hashCode2 + i;
    }
}
