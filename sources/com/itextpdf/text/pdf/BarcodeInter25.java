package com.itextpdf.text.pdf;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class BarcodeInter25 extends Barcode {
    private static final byte[][] BARS = {new byte[]{0, 0, 1, 1, 0}, new byte[]{1, 0, 0, 0, 1}, new byte[]{0, 1, 0, 0, 1}, new byte[]{1, 1, 0, 0, 0}, new byte[]{0, 0, 1, 0, 1}, new byte[]{1, 0, 1, 0, 0}, new byte[]{0, 1, 1, 0, 0}, new byte[]{0, 0, 0, 1, 1}, new byte[]{1, 0, 0, 1, 0}, new byte[]{0, 1, 0, 1, 0}};

    public BarcodeInter25() {
        try {
            this.f504x = 0.8f;
            this.f503n = 2.0f;
            this.font = BaseFont.createFont("Helvetica", "winansi", false);
            this.size = 8.0f;
            this.baseline = this.size;
            this.barHeight = this.size * 3.0f;
            this.textAlignment = 1;
            this.generateChecksum = false;
            this.checksumText = false;
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public static String keepNumbers(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt >= '0' && charAt <= '9') {
                stringBuffer.append(charAt);
            }
        }
        return stringBuffer.toString();
    }

    public static char getChecksum(String str) {
        int i = 3;
        int i2 = 0;
        for (int length = str.length() - 1; length >= 0; length--) {
            i2 += (str.charAt(length) - '0') * i;
            i ^= 2;
        }
        return (char) (((10 - (i2 % 10)) % 10) + 48);
    }

    public static byte[] getBarsInter25(String str) {
        String keepNumbers = keepNumbers(str);
        if ((keepNumbers.length() & 1) == 0) {
            byte[] bArr = new byte[((keepNumbers.length() * 5) + 7)];
            bArr[0] = 0;
            bArr[1] = 0;
            bArr[2] = 0;
            int i = 4;
            bArr[3] = 0;
            int length = keepNumbers.length() / 2;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = i2 * 2;
                byte[][] bArr2 = BARS;
                byte[] bArr3 = bArr2[keepNumbers.charAt(i3) - '0'];
                byte[] bArr4 = bArr2[keepNumbers.charAt(i3 + 1) - '0'];
                for (int i4 = 0; i4 < 5; i4++) {
                    int i5 = i + 1;
                    bArr[i] = bArr3[i4];
                    i = i5 + 1;
                    bArr[i5] = bArr4[i4];
                }
            }
            int i6 = i + 1;
            bArr[i] = 1;
            bArr[i6] = 0;
            bArr[i6 + 1] = 0;
            return bArr;
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.text.length.must.be.even", new Object[0]));
    }

    public Rectangle getBarcodeSize() {
        float f;
        float f2;
        float f3 = 0.0f;
        if (this.font != null) {
            if (this.baseline > 0.0f) {
                f2 = this.baseline - this.font.getFontDescriptor(3, this.size);
            } else {
                f2 = (-this.baseline) + this.size;
            }
            float f4 = f2;
            String str = this.code;
            if (this.generateChecksum && this.checksumText) {
                str = str + getChecksum(str);
            }
            BaseFont baseFont = this.font;
            if (this.altText != null) {
                str = this.altText;
            }
            float f5 = f4;
            f3 = baseFont.getWidthPoint(str, this.size);
            f = f5;
        } else {
            f = 0.0f;
        }
        int length = keepNumbers(this.code).length();
        if (this.generateChecksum) {
            length++;
        }
        return new Rectangle(Math.max((((float) length) * ((this.f504x * 3.0f) + (this.f504x * 2.0f * this.f503n))) + ((this.f503n + 6.0f) * this.f504x), f3), this.barHeight + f);
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00e2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.Rectangle placeBarcode(com.itextpdf.text.pdf.PdfContentByte r12, com.itextpdf.text.BaseColor r13, com.itextpdf.text.BaseColor r14) {
        /*
            r11 = this;
            java.lang.String r0 = r11.code
            com.itextpdf.text.pdf.BaseFont r1 = r11.font
            r2 = 0
            if (r1 == 0) goto L_0x0031
            boolean r1 = r11.generateChecksum
            if (r1 == 0) goto L_0x0022
            boolean r1 = r11.checksumText
            if (r1 == 0) goto L_0x0022
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            char r0 = getChecksum(r0)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
        L_0x0022:
            com.itextpdf.text.pdf.BaseFont r1 = r11.font
            java.lang.String r3 = r11.altText
            if (r3 == 0) goto L_0x002a
            java.lang.String r0 = r11.altText
        L_0x002a:
            float r3 = r11.size
            float r1 = r1.getWidthPoint((java.lang.String) r0, (float) r3)
            goto L_0x0032
        L_0x0031:
            r1 = 0
        L_0x0032:
            java.lang.String r3 = r11.code
            java.lang.String r3 = keepNumbers(r3)
            boolean r4 = r11.generateChecksum
            if (r4 == 0) goto L_0x004f
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r3)
            char r3 = getChecksum(r3)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
        L_0x004f:
            int r4 = r3.length()
            float r4 = (float) r4
            r5 = 1077936128(0x40400000, float:3.0)
            float r6 = r11.f504x
            float r6 = r6 * r5
            float r5 = r11.f504x
            r7 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 * r7
            float r8 = r11.f503n
            float r5 = r5 * r8
            float r6 = r6 + r5
            float r4 = r4 * r6
            r5 = 1086324736(0x40c00000, float:6.0)
            float r6 = r11.f503n
            float r6 = r6 + r5
            float r5 = r11.f504x
            float r6 = r6 * r5
            float r4 = r4 + r6
            int r5 = r11.textAlignment
            if (r5 == 0) goto L_0x008b
            r6 = 2
            if (r5 == r6) goto L_0x0082
            int r5 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r5 <= 0) goto L_0x007f
            float r1 = r1 - r4
            float r1 = r1 / r7
            goto L_0x008c
        L_0x007f:
            float r4 = r4 - r1
            float r4 = r4 / r7
            goto L_0x0089
        L_0x0082:
            int r5 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r5 <= 0) goto L_0x0088
            float r1 = r1 - r4
            goto L_0x008c
        L_0x0088:
            float r4 = r4 - r1
        L_0x0089:
            r1 = 0
            goto L_0x008d
        L_0x008b:
            r1 = 0
        L_0x008c:
            r4 = 0
        L_0x008d:
            com.itextpdf.text.pdf.BaseFont r5 = r11.font
            if (r5 == 0) goto L_0x00ae
            float r5 = r11.baseline
            int r5 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r5 > 0) goto L_0x009d
            float r5 = r11.barHeight
            float r6 = r11.baseline
            float r5 = r5 - r6
            goto L_0x00af
        L_0x009d:
            com.itextpdf.text.pdf.BaseFont r2 = r11.font
            r5 = 3
            float r6 = r11.size
            float r2 = r2.getFontDescriptor(r5, r6)
            float r2 = -r2
            float r5 = r11.baseline
            float r5 = r5 + r2
            r10 = r5
            r5 = r2
            r2 = r10
            goto L_0x00af
        L_0x00ae:
            r5 = 0
        L_0x00af:
            byte[] r3 = getBarsInter25(r3)
            if (r13 == 0) goto L_0x00b8
            r12.setColorFill(r13)
        L_0x00b8:
            r13 = 0
            r6 = 1
        L_0x00ba:
            int r7 = r3.length
            if (r13 >= r7) goto L_0x00db
            byte r7 = r3[r13]
            if (r7 != 0) goto L_0x00c4
            float r7 = r11.f504x
            goto L_0x00ca
        L_0x00c4:
            float r7 = r11.f504x
            float r8 = r11.f503n
            float r7 = r7 * r8
        L_0x00ca:
            if (r6 == 0) goto L_0x00d5
            float r8 = r11.inkSpreading
            float r8 = r7 - r8
            float r9 = r11.barHeight
            r12.rectangle((float) r1, (float) r2, (float) r8, (float) r9)
        L_0x00d5:
            r6 = r6 ^ 1
            float r1 = r1 + r7
            int r13 = r13 + 1
            goto L_0x00ba
        L_0x00db:
            r12.fill()
            com.itextpdf.text.pdf.BaseFont r13 = r11.font
            if (r13 == 0) goto L_0x00fa
            if (r14 == 0) goto L_0x00e7
            r12.setColorFill(r14)
        L_0x00e7:
            r12.beginText()
            com.itextpdf.text.pdf.BaseFont r13 = r11.font
            float r14 = r11.size
            r12.setFontAndSize(r13, r14)
            r12.setTextMatrix(r4, r5)
            r12.showText((java.lang.String) r0)
            r12.endText()
        L_0x00fa:
            com.itextpdf.text.Rectangle r12 = r11.getBarcodeSize()
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.BarcodeInter25.placeBarcode(com.itextpdf.text.pdf.PdfContentByte, com.itextpdf.text.BaseColor, com.itextpdf.text.BaseColor):com.itextpdf.text.Rectangle");
    }

    public Image createAwtImage(Color color, Color color2) {
        int rgb = color.getRGB();
        int rgb2 = color2.getRGB();
        Canvas canvas = new Canvas();
        String keepNumbers = keepNumbers(this.code);
        if (this.generateChecksum) {
            keepNumbers = keepNumbers + getChecksum(keepNumbers);
        }
        int length = keepNumbers.length();
        int i = (int) this.f503n;
        int i2 = (length * ((i * 2) + 3)) + i + 6;
        byte[] barsInter25 = getBarsInter25(keepNumbers);
        int i3 = (int) this.barHeight;
        int i4 = i2 * i3;
        int[] iArr = new int[i4];
        boolean z = true;
        int i5 = 0;
        for (int i6 = 0; i6 < barsInter25.length; i6++) {
            int i7 = barsInter25[i6] == 0 ? 1 : i;
            int i8 = z ? rgb : rgb2;
            z = !z;
            int i9 = 0;
            while (i9 < i7) {
                iArr[i5] = i8;
                i9++;
                i5++;
            }
        }
        for (int i10 = i2; i10 < i4; i10 += i2) {
            System.arraycopy(iArr, 0, iArr, i10, i2);
        }
        return canvas.createImage(new MemoryImageSource(i2, i3, iArr, 0, i2));
    }
}
