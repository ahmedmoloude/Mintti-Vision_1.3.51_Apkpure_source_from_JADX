package com.itextpdf.text.pdf;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class BarcodeCodabar extends Barcode {
    private static final byte[][] BARS = {new byte[]{0, 0, 0, 0, 0, 1, 1}, new byte[]{0, 0, 0, 0, 1, 1, 0}, new byte[]{0, 0, 0, 1, 0, 0, 1}, new byte[]{1, 1, 0, 0, 0, 0, 0}, new byte[]{0, 0, 1, 0, 0, 1, 0}, new byte[]{1, 0, 0, 0, 0, 1, 0}, new byte[]{0, 1, 0, 0, 0, 0, 1}, new byte[]{0, 1, 0, 0, 1, 0, 0}, new byte[]{0, 1, 1, 0, 0, 0, 0}, new byte[]{1, 0, 0, 1, 0, 0, 0}, new byte[]{0, 0, 0, 1, 1, 0, 0}, new byte[]{0, 0, 1, 1, 0, 0, 0}, new byte[]{1, 0, 0, 0, 1, 0, 1}, new byte[]{1, 0, 1, 0, 0, 0, 1}, new byte[]{1, 0, 1, 0, 1, 0, 0}, new byte[]{0, 0, 1, 0, 1, 0, 1}, new byte[]{0, 0, 1, 1, 0, 1, 0}, new byte[]{0, 1, 0, 1, 0, 0, 1}, new byte[]{0, 0, 0, 1, 0, 1, 1}, new byte[]{0, 0, 0, 1, 1, 1, 0}};
    private static final String CHARS = "0123456789-$:/.+ABCD";
    private static final int START_STOP_IDX = 16;

    public BarcodeCodabar() {
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
            this.startStopText = false;
            this.codeType = 12;
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public static byte[] getBarsCodabar(String str) {
        String upperCase = str.toUpperCase();
        int length = upperCase.length();
        if (length >= 2) {
            if (CHARS.indexOf(upperCase.charAt(0)) >= 16) {
                int i = length - 1;
                if (CHARS.indexOf(upperCase.charAt(i)) >= 16) {
                    byte[] bArr = new byte[((upperCase.length() * 8) - 1)];
                    int i2 = 0;
                    while (i2 < length) {
                        int indexOf = CHARS.indexOf(upperCase.charAt(i2));
                        if (indexOf >= 16 && i2 > 0 && i2 < i) {
                            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("in.codabar.start.stop.characters.are.only.allowed.at.the.extremes", new Object[0]));
                        } else if (indexOf >= 0) {
                            System.arraycopy(BARS[indexOf], 0, bArr, i2 * 8, 7);
                            i2++;
                        } else {
                            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.character.1.is.illegal.in.codabar", (int) upperCase.charAt(i2)));
                        }
                    }
                    return bArr;
                }
            }
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("codabar.must.have.one.of.abcd.as.start.stop.character", new Object[0]));
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("codabar.must.have.at.least.a.start.and.stop.character", new Object[0]));
    }

    public static String calculateChecksum(String str) {
        if (str.length() < 2) {
            return str;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            i += CHARS.indexOf(upperCase.charAt(i2));
        }
        StringBuilder sb = new StringBuilder();
        int i3 = length - 1;
        sb.append(str.substring(0, i3));
        sb.append(CHARS.charAt((((i + 15) / 16) * 16) - i));
        sb.append(str.substring(i3));
        return sb.toString();
    }

    public Rectangle getBarcodeSize() {
        float f;
        float f2;
        String str = this.code;
        if (this.generateChecksum && this.checksumText) {
            str = calculateChecksum(this.code);
        }
        if (!this.startStopText) {
            str = str.substring(1, str.length() - 1);
        }
        float f3 = 0.0f;
        if (this.font != null) {
            if (this.baseline > 0.0f) {
                f2 = this.baseline - this.font.getFontDescriptor(3, this.size);
            } else {
                f2 = (-this.baseline) + this.size;
            }
            float f4 = f2;
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
        String str2 = this.code;
        if (this.generateChecksum) {
            str2 = calculateChecksum(this.code);
        }
        byte[] barsCodabar = getBarsCodabar(str2);
        int i = 0;
        for (byte b : barsCodabar) {
            i += b;
        }
        return new Rectangle(Math.max(this.f504x * (((float) (barsCodabar.length - i)) + (((float) i) * this.f503n)), f3), this.barHeight + f);
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00ca  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.Rectangle placeBarcode(com.itextpdf.text.pdf.PdfContentByte r12, com.itextpdf.text.BaseColor r13, com.itextpdf.text.BaseColor r14) {
        /*
            r11 = this;
            java.lang.String r0 = r11.code
            boolean r1 = r11.generateChecksum
            if (r1 == 0) goto L_0x0010
            boolean r1 = r11.checksumText
            if (r1 == 0) goto L_0x0010
            java.lang.String r0 = r11.code
            java.lang.String r0 = calculateChecksum(r0)
        L_0x0010:
            boolean r1 = r11.startStopText
            r2 = 1
            if (r1 != 0) goto L_0x001e
            int r1 = r0.length()
            int r1 = r1 - r2
            java.lang.String r0 = r0.substring(r2, r1)
        L_0x001e:
            com.itextpdf.text.pdf.BaseFont r1 = r11.font
            r3 = 0
            if (r1 == 0) goto L_0x0032
            com.itextpdf.text.pdf.BaseFont r1 = r11.font
            java.lang.String r4 = r11.altText
            if (r4 == 0) goto L_0x002b
            java.lang.String r0 = r11.altText
        L_0x002b:
            float r4 = r11.size
            float r1 = r1.getWidthPoint((java.lang.String) r0, (float) r4)
            goto L_0x0033
        L_0x0032:
            r1 = 0
        L_0x0033:
            boolean r4 = r11.generateChecksum
            if (r4 == 0) goto L_0x003e
            java.lang.String r4 = r11.code
            java.lang.String r4 = calculateChecksum(r4)
            goto L_0x0040
        L_0x003e:
            java.lang.String r4 = r11.code
        L_0x0040:
            byte[] r4 = getBarsCodabar(r4)
            r5 = 0
            r6 = 0
            r7 = 0
        L_0x0047:
            int r8 = r4.length
            if (r6 >= r8) goto L_0x0050
            byte r8 = r4[r6]
            int r7 = r7 + r8
            int r6 = r6 + 1
            goto L_0x0047
        L_0x0050:
            int r6 = r4.length
            int r6 = r6 - r7
            float r8 = r11.f504x
            float r6 = (float) r6
            float r7 = (float) r7
            float r9 = r11.f503n
            float r7 = r7 * r9
            float r6 = r6 + r7
            float r8 = r8 * r6
            int r6 = r11.textAlignment
            if (r6 == 0) goto L_0x0079
            r7 = 2
            if (r6 == r7) goto L_0x0070
            r6 = 1073741824(0x40000000, float:2.0)
            int r7 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r7 <= 0) goto L_0x006d
            float r1 = r1 - r8
            float r1 = r1 / r6
            goto L_0x007a
        L_0x006d:
            float r8 = r8 - r1
            float r8 = r8 / r6
            goto L_0x0077
        L_0x0070:
            int r6 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r6 <= 0) goto L_0x0076
            float r1 = r1 - r8
            goto L_0x007a
        L_0x0076:
            float r8 = r8 - r1
        L_0x0077:
            r1 = 0
            goto L_0x007b
        L_0x0079:
            r1 = 0
        L_0x007a:
            r8 = 0
        L_0x007b:
            com.itextpdf.text.pdf.BaseFont r6 = r11.font
            if (r6 == 0) goto L_0x009c
            float r6 = r11.baseline
            int r6 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r6 > 0) goto L_0x008b
            float r6 = r11.barHeight
            float r7 = r11.baseline
            float r6 = r6 - r7
            goto L_0x009d
        L_0x008b:
            com.itextpdf.text.pdf.BaseFont r3 = r11.font
            r6 = 3
            float r7 = r11.size
            float r3 = r3.getFontDescriptor(r6, r7)
            float r3 = -r3
            float r6 = r11.baseline
            float r6 = r6 + r3
            r10 = r6
            r6 = r3
            r3 = r10
            goto L_0x009d
        L_0x009c:
            r6 = 0
        L_0x009d:
            if (r13 == 0) goto L_0x00a2
            r12.setColorFill(r13)
        L_0x00a2:
            int r13 = r4.length
            if (r5 >= r13) goto L_0x00c3
            byte r13 = r4[r5]
            if (r13 != 0) goto L_0x00ac
            float r13 = r11.f504x
            goto L_0x00b2
        L_0x00ac:
            float r13 = r11.f504x
            float r7 = r11.f503n
            float r13 = r13 * r7
        L_0x00b2:
            if (r2 == 0) goto L_0x00bd
            float r7 = r11.inkSpreading
            float r7 = r13 - r7
            float r9 = r11.barHeight
            r12.rectangle((float) r1, (float) r3, (float) r7, (float) r9)
        L_0x00bd:
            r2 = r2 ^ 1
            float r1 = r1 + r13
            int r5 = r5 + 1
            goto L_0x00a2
        L_0x00c3:
            r12.fill()
            com.itextpdf.text.pdf.BaseFont r13 = r11.font
            if (r13 == 0) goto L_0x00e2
            if (r14 == 0) goto L_0x00cf
            r12.setColorFill(r14)
        L_0x00cf:
            r12.beginText()
            com.itextpdf.text.pdf.BaseFont r13 = r11.font
            float r14 = r11.size
            r12.setFontAndSize(r13, r14)
            r12.setTextMatrix(r8, r6)
            r12.showText((java.lang.String) r0)
            r12.endText()
        L_0x00e2:
            com.itextpdf.text.Rectangle r12 = r11.getBarcodeSize()
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.BarcodeCodabar.placeBarcode(com.itextpdf.text.pdf.PdfContentByte, com.itextpdf.text.BaseColor, com.itextpdf.text.BaseColor):com.itextpdf.text.Rectangle");
    }

    public Image createAwtImage(Color color, Color color2) {
        int rgb = color.getRGB();
        int rgb2 = color2.getRGB();
        Canvas canvas = new Canvas();
        String str = this.code;
        if (this.generateChecksum && this.checksumText) {
            str = calculateChecksum(this.code);
        }
        if (!this.startStopText) {
            str.substring(1, str.length() - 1);
        }
        byte[] barsCodabar = getBarsCodabar(this.generateChecksum ? calculateChecksum(this.code) : this.code);
        int i = 0;
        for (byte b : barsCodabar) {
            i += b;
        }
        int length = (barsCodabar.length - i) + (i * ((int) this.f503n));
        int i2 = (int) this.barHeight;
        int i3 = length * i2;
        int[] iArr = new int[i3];
        boolean z = true;
        int i4 = 0;
        for (int i5 = 0; i5 < barsCodabar.length; i5++) {
            int i6 = barsCodabar[i5] == 0 ? 1 : (int) this.f503n;
            int i7 = z ? rgb : rgb2;
            z = !z;
            int i8 = 0;
            while (i8 < i6) {
                iArr[i4] = i7;
                i8++;
                i4++;
            }
        }
        for (int i9 = length; i9 < i3; i9 += length) {
            System.arraycopy(iArr, 0, iArr, i9, length);
        }
        return canvas.createImage(new MemoryImageSource(length, i2, iArr, 0, length));
    }
}
