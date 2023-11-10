package com.itextpdf.text.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Locale;

public class PdfDeviceNColor implements ICachedColorSpace, IPdfSpecialColorSpace {
    ColorDetails[] colorantsDetails;
    PdfSpotColor[] spotColors;

    public PdfDeviceNColor(PdfSpotColor[] pdfSpotColorArr) {
        this.spotColors = pdfSpotColorArr;
    }

    public int getNumberOfColorants() {
        return this.spotColors.length;
    }

    public PdfSpotColor[] getSpotColors() {
        return this.spotColors;
    }

    public ColorDetails[] getColorantDetails(PdfWriter pdfWriter) {
        if (this.colorantsDetails == null) {
            PdfSpotColor[] pdfSpotColorArr = this.spotColors;
            this.colorantsDetails = new ColorDetails[pdfSpotColorArr.length];
            int i = 0;
            for (PdfSpotColor addSimple : pdfSpotColorArr) {
                this.colorantsDetails[i] = pdfWriter.addSimple((ICachedColorSpace) addSimple);
                i++;
            }
        }
        return this.colorantsDetails;
    }

    public PdfObject getPdfObject(PdfWriter pdfWriter) {
        float f;
        float f2;
        float f3;
        char c;
        PdfWriter pdfWriter2 = pdfWriter;
        PdfArray pdfArray = new PdfArray((PdfObject) PdfName.DEVICEN);
        PdfArray pdfArray2 = new PdfArray();
        int i = 2;
        float[] fArr = new float[(this.spotColors.length * 2)];
        PdfDictionary pdfDictionary = new PdfDictionary();
        int length = this.spotColors.length;
        int[] iArr = new int[2];
        int i2 = 1;
        iArr[1] = length;
        iArr[0] = 4;
        float[][] fArr2 = (float[][]) Array.newInstance(float.class, iArr);
        String str = "";
        String str2 = str;
        int i3 = 0;
        while (i3 < length) {
            PdfSpotColor pdfSpotColor = this.spotColors[i3];
            int i4 = i3 * 2;
            fArr[i4] = 0.0f;
            float f4 = 1.0f;
            fArr[i4 + 1] = 1.0f;
            pdfArray2.add((PdfObject) pdfSpotColor.getName());
            if (pdfDictionary.get(pdfSpotColor.getName()) == null) {
                if (this.colorantsDetails != null) {
                    pdfDictionary.put(pdfSpotColor.getName(), this.colorantsDetails[i3].getIndirectReference());
                } else {
                    pdfDictionary.put(pdfSpotColor.getName(), pdfSpotColor.getPdfObject(pdfWriter2));
                }
                BaseColor alternativeCS = pdfSpotColor.getAlternativeCS();
                if (alternativeCS instanceof ExtendedColor) {
                    int i5 = ((ExtendedColor) alternativeCS).type;
                    if (i5 == 1) {
                        fArr2[0][i3] = 0.0f;
                        fArr2[1][i3] = 0.0f;
                        fArr2[i][i3] = 0.0f;
                        fArr2[3][i3] = 1.0f - ((GrayColor) alternativeCS).getGray();
                    } else if (i5 == i) {
                        CMYKColor cMYKColor = (CMYKColor) alternativeCS;
                        fArr2[0][i3] = cMYKColor.getCyan();
                        fArr2[1][i3] = cMYKColor.getMagenta();
                        fArr2[i][i3] = cMYKColor.getYellow();
                        fArr2[3][i3] = cMYKColor.getBlack();
                    } else if (i5 == 7) {
                        CMYKColor cmyk = ((LabColor) alternativeCS).toCmyk();
                        fArr2[0][i3] = cmyk.getCyan();
                        fArr2[1][i3] = cmyk.getMagenta();
                        fArr2[i][i3] = cmyk.getYellow();
                        fArr2[3][i3] = cmyk.getBlack();
                    } else {
                        throw new RuntimeException(MessageLocalization.getComposedMessage("only.rgb.gray.and.cmyk.are.supported.as.alternative.color.spaces", new Object[0]));
                    }
                } else {
                    float red = (float) alternativeCS.getRed();
                    float green = (float) alternativeCS.getGreen();
                    float blue = (float) alternativeCS.getBlue();
                    if (red == 0.0f && green == 0.0f && blue == 0.0f) {
                        c = 0;
                        f3 = 0.0f;
                        f2 = 0.0f;
                        f = 0.0f;
                    } else {
                        float f5 = 1.0f - (red / 255.0f);
                        float f6 = 1.0f - (green / 255.0f);
                        float f7 = 1.0f - (blue / 255.0f);
                        float min = Math.min(f5, Math.min(f6, f7));
                        float f8 = 1.0f - min;
                        f2 = (f5 - min) / f8;
                        f = (f6 - min) / f8;
                        f3 = (f7 - min) / f8;
                        f4 = min;
                        c = 0;
                    }
                    fArr2[c][i3] = f2;
                    fArr2[1][i3] = f;
                    fArr2[2][i3] = f3;
                    fArr2[3][i3] = f4;
                }
                str2 = str2 + "pop ";
                i3++;
                i = 2;
            } else {
                throw new RuntimeException(MessageLocalization.getComposedMessage("devicen.component.names.shall.be.different", new Object[0]));
            }
        }
        pdfArray.add((PdfObject) pdfArray2);
        String format = String.format(Locale.US, "1.000000 %d 1 roll ", new Object[]{Integer.valueOf(length + 1)});
        pdfArray.add((PdfObject) PdfName.DEVICECMYK);
        String str3 = format + format + format + format;
        int i6 = length + 4;
        int i7 = i6;
        while (i7 > length) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            Locale locale = Locale.US;
            Object[] objArr = new Object[i2];
            objArr[0] = Integer.valueOf(i7);
            sb.append(String.format(locale, "%d -1 roll ", objArr));
            String sb2 = sb.toString();
            for (int i8 = length; i8 > 0; i8--) {
                sb2 = sb2 + String.format(Locale.US, "%d index %f mul 1.000000 cvr exch sub mul ", new Object[]{Integer.valueOf(i8), Float.valueOf(fArr2[i6 - i7][length - i8])});
            }
            str = sb2 + String.format(Locale.US, "1.000000 cvr exch sub %d 1 roll ", new Object[]{Integer.valueOf(i7)});
            i7--;
            i2 = 1;
        }
        pdfArray.add((PdfObject) PdfFunction.type4(pdfWriter2, fArr, new float[]{0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f}, "{ " + str3 + str + str2 + "}").getReference());
        PdfDictionary pdfDictionary2 = new PdfDictionary();
        pdfDictionary2.put(PdfName.SUBTYPE, PdfName.NCHANNEL);
        pdfDictionary2.put(PdfName.COLORANTS, pdfDictionary);
        pdfArray.add((PdfObject) pdfDictionary2);
        return pdfArray;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof PdfDeviceNColor) && Arrays.equals(this.spotColors, ((PdfDeviceNColor) obj).spotColors);
    }

    public int hashCode() {
        return Arrays.hashCode(this.spotColors);
    }
}
