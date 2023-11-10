package com.itextpdf.text.pdf;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class Barcode39 extends Barcode {
    private static final byte[][] BARS = {new byte[]{0, 0, 0, 1, 1, 0, 1, 0, 0}, new byte[]{1, 0, 0, 1, 0, 0, 0, 0, 1}, new byte[]{0, 0, 1, 1, 0, 0, 0, 0, 1}, new byte[]{1, 0, 1, 1, 0, 0, 0, 0, 0}, new byte[]{0, 0, 0, 1, 1, 0, 0, 0, 1}, new byte[]{1, 0, 0, 1, 1, 0, 0, 0, 0}, new byte[]{0, 0, 1, 1, 1, 0, 0, 0, 0}, new byte[]{0, 0, 0, 1, 0, 0, 1, 0, 1}, new byte[]{1, 0, 0, 1, 0, 0, 1, 0, 0}, new byte[]{0, 0, 1, 1, 0, 0, 1, 0, 0}, new byte[]{1, 0, 0, 0, 0, 1, 0, 0, 1}, new byte[]{0, 0, 1, 0, 0, 1, 0, 0, 1}, new byte[]{1, 0, 1, 0, 0, 1, 0, 0, 0}, new byte[]{0, 0, 0, 0, 1, 1, 0, 0, 1}, new byte[]{1, 0, 0, 0, 1, 1, 0, 0, 0}, new byte[]{0, 0, 1, 0, 1, 1, 0, 0, 0}, new byte[]{0, 0, 0, 0, 0, 1, 1, 0, 1}, new byte[]{1, 0, 0, 0, 0, 1, 1, 0, 0}, new byte[]{0, 0, 1, 0, 0, 1, 1, 0, 0}, new byte[]{0, 0, 0, 0, 1, 1, 1, 0, 0}, new byte[]{1, 0, 0, 0, 0, 0, 0, 1, 1}, new byte[]{0, 0, 1, 0, 0, 0, 0, 1, 1}, new byte[]{1, 0, 1, 0, 0, 0, 0, 1, 0}, new byte[]{0, 0, 0, 0, 1, 0, 0, 1, 1}, new byte[]{1, 0, 0, 0, 1, 0, 0, 1, 0}, new byte[]{0, 0, 1, 0, 1, 0, 0, 1, 0}, new byte[]{0, 0, 0, 0, 0, 0, 1, 1, 1}, new byte[]{1, 0, 0, 0, 0, 0, 1, 1, 0}, new byte[]{0, 0, 1, 0, 0, 0, 1, 1, 0}, new byte[]{0, 0, 0, 0, 1, 0, 1, 1, 0}, new byte[]{1, 1, 0, 0, 0, 0, 0, 0, 1}, new byte[]{0, 1, 1, 0, 0, 0, 0, 0, 1}, new byte[]{1, 1, 1, 0, 0, 0, 0, 0, 0}, new byte[]{0, 1, 0, 0, 1, 0, 0, 0, 1}, new byte[]{1, 1, 0, 0, 1, 0, 0, 0, 0}, new byte[]{0, 1, 1, 0, 1, 0, 0, 0, 0}, new byte[]{0, 1, 0, 0, 0, 0, 1, 0, 1}, new byte[]{1, 1, 0, 0, 0, 0, 1, 0, 0}, new byte[]{0, 1, 1, 0, 0, 0, 1, 0, 0}, new byte[]{0, 1, 0, 1, 0, 1, 0, 0, 0}, new byte[]{0, 1, 0, 1, 0, 0, 0, 1, 0}, new byte[]{0, 1, 0, 0, 0, 1, 0, 1, 0}, new byte[]{0, 0, 0, 1, 0, 1, 0, 1, 0}, new byte[]{0, 1, 0, 0, 1, 0, 1, 0, 0}};
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%*";
    private static final String EXTENDED = "%U$A$B$C$D$E$F$G$H$I$J$K$L$M$N$O$P$Q$R$S$T$U$V$W$X$Y$Z%A%B%C%D%E  /A/B/C/D/E/F/G/H/I/J/K/L - ./O 0 1 2 3 4 5 6 7 8 9/Z%F%G%H%I%J%V A B C D E F G H I J K L M N O P Q R S T U V W X Y Z%K%L%M%N%O%W+A+B+C+D+E+F+G+H+I+J+K+L+M+N+O+P+Q+R+S+T+U+V+W+X+Y+Z%P%Q%R%S%T";

    public Barcode39() {
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
            this.startStopText = true;
            this.extended = false;
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public static byte[] getBarsCode39(String str) {
        String str2 = "*" + str + "*";
        byte[] bArr = new byte[((str2.length() * 10) - 1)];
        int i = 0;
        while (i < str2.length()) {
            int indexOf = CHARS.indexOf(str2.charAt(i));
            if (indexOf >= 0) {
                System.arraycopy(BARS[indexOf], 0, bArr, i * 10, 9);
                i++;
            } else {
                throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.character.1.is.illegal.in.code.39", (int) str2.charAt(i)));
            }
        }
        return bArr;
    }

    public static String getCode39Ex(String str) {
        StringBuilder sb = new StringBuilder("");
        int i = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt <= 127) {
                int i2 = charAt * 2;
                char charAt2 = EXTENDED.charAt(i2);
                char charAt3 = EXTENDED.charAt(i2 + 1);
                if (charAt2 != ' ') {
                    sb.append(charAt2);
                }
                sb.append(charAt3);
                i++;
            } else {
                throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.character.1.is.illegal.in.code.39.extended", (int) charAt));
            }
        }
        return sb.toString();
    }

    static char getChecksum(String str) {
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            int indexOf = CHARS.indexOf(str.charAt(i));
            if (indexOf >= 0) {
                i2 += indexOf;
                i++;
            } else {
                throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.character.1.is.illegal.in.code.39", (int) str.charAt(i)));
            }
        }
        return CHARS.charAt(i2 % 43);
    }

    public Rectangle getBarcodeSize() {
        float f;
        float f2;
        String str = this.code;
        if (this.extended) {
            str = getCode39Ex(this.code);
        }
        float f3 = 0.0f;
        if (this.font != null) {
            if (this.baseline > 0.0f) {
                f2 = this.baseline - this.font.getFontDescriptor(3, this.size);
            } else {
                f2 = (-this.baseline) + this.size;
            }
            float f4 = f2;
            String str2 = this.code;
            if (this.generateChecksum && this.checksumText) {
                str2 = str2 + getChecksum(str);
            }
            if (this.startStopText) {
                str2 = "*" + str2 + "*";
            }
            BaseFont baseFont = this.font;
            if (this.altText != null) {
                str2 = this.altText;
            }
            float f5 = f4;
            f3 = baseFont.getWidthPoint(str2, this.size);
            f = f5;
        } else {
            f = 0.0f;
        }
        int length = str.length() + 2;
        if (this.generateChecksum) {
            length++;
        }
        return new Rectangle(Math.max((((float) length) * ((this.f504x * 6.0f) + (this.f504x * 3.0f * this.f503n))) + (((float) (length - 1)) * this.f504x), f3), this.barHeight + f);
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00d4  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0100  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.Rectangle placeBarcode(com.itextpdf.text.pdf.PdfContentByte r12, com.itextpdf.text.BaseColor r13, com.itextpdf.text.BaseColor r14) {
        /*
            r11 = this;
            java.lang.String r0 = r11.code
            java.lang.String r1 = r11.code
            boolean r2 = r11.extended
            if (r2 == 0) goto L_0x000e
            java.lang.String r1 = r11.code
            java.lang.String r1 = getCode39Ex(r1)
        L_0x000e:
            com.itextpdf.text.pdf.BaseFont r2 = r11.font
            r3 = 0
            if (r2 == 0) goto L_0x0055
            boolean r2 = r11.generateChecksum
            if (r2 == 0) goto L_0x002e
            boolean r2 = r11.checksumText
            if (r2 == 0) goto L_0x002e
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            char r0 = getChecksum(r1)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
        L_0x002e:
            boolean r2 = r11.startStopText
            if (r2 == 0) goto L_0x0046
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "*"
            r2.append(r4)
            r2.append(r0)
            r2.append(r4)
            java.lang.String r0 = r2.toString()
        L_0x0046:
            com.itextpdf.text.pdf.BaseFont r2 = r11.font
            java.lang.String r4 = r11.altText
            if (r4 == 0) goto L_0x004e
            java.lang.String r0 = r11.altText
        L_0x004e:
            float r4 = r11.size
            float r2 = r2.getWidthPoint((java.lang.String) r0, (float) r4)
            goto L_0x0056
        L_0x0055:
            r2 = 0
        L_0x0056:
            boolean r4 = r11.generateChecksum
            if (r4 == 0) goto L_0x006d
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r1)
            char r1 = getChecksum(r1)
            r4.append(r1)
            java.lang.String r1 = r4.toString()
        L_0x006d:
            int r4 = r1.length()
            r5 = 2
            int r4 = r4 + r5
            float r6 = (float) r4
            r7 = 1086324736(0x40c00000, float:6.0)
            float r8 = r11.f504x
            float r8 = r8 * r7
            r7 = 1077936128(0x40400000, float:3.0)
            float r9 = r11.f504x
            float r9 = r9 * r7
            float r7 = r11.f503n
            float r9 = r9 * r7
            float r8 = r8 + r9
            float r6 = r6 * r8
            r7 = 1
            int r4 = r4 - r7
            float r4 = (float) r4
            float r8 = r11.f504x
            float r4 = r4 * r8
            float r6 = r6 + r4
            int r4 = r11.textAlignment
            if (r4 == 0) goto L_0x00aa
            if (r4 == r5) goto L_0x00a1
            r4 = 1073741824(0x40000000, float:2.0)
            int r5 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x009e
            float r2 = r2 - r6
            float r2 = r2 / r4
            goto L_0x00ab
        L_0x009e:
            float r6 = r6 - r2
            float r6 = r6 / r4
            goto L_0x00a8
        L_0x00a1:
            int r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x00a7
            float r2 = r2 - r6
            goto L_0x00ab
        L_0x00a7:
            float r6 = r6 - r2
        L_0x00a8:
            r2 = 0
            goto L_0x00ac
        L_0x00aa:
            r2 = 0
        L_0x00ab:
            r6 = 0
        L_0x00ac:
            com.itextpdf.text.pdf.BaseFont r4 = r11.font
            if (r4 == 0) goto L_0x00cd
            float r4 = r11.baseline
            int r4 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1))
            if (r4 > 0) goto L_0x00bc
            float r4 = r11.barHeight
            float r5 = r11.baseline
            float r4 = r4 - r5
            goto L_0x00ce
        L_0x00bc:
            com.itextpdf.text.pdf.BaseFont r3 = r11.font
            r4 = 3
            float r5 = r11.size
            float r3 = r3.getFontDescriptor(r4, r5)
            float r3 = -r3
            float r4 = r11.baseline
            float r4 = r4 + r3
            r10 = r4
            r4 = r3
            r3 = r10
            goto L_0x00ce
        L_0x00cd:
            r4 = 0
        L_0x00ce:
            byte[] r1 = getBarsCode39(r1)
            if (r13 == 0) goto L_0x00d7
            r12.setColorFill(r13)
        L_0x00d7:
            r13 = 0
        L_0x00d8:
            int r5 = r1.length
            if (r13 >= r5) goto L_0x00f9
            byte r5 = r1[r13]
            if (r5 != 0) goto L_0x00e2
            float r5 = r11.f504x
            goto L_0x00e8
        L_0x00e2:
            float r5 = r11.f504x
            float r8 = r11.f503n
            float r5 = r5 * r8
        L_0x00e8:
            if (r7 == 0) goto L_0x00f3
            float r8 = r11.inkSpreading
            float r8 = r5 - r8
            float r9 = r11.barHeight
            r12.rectangle((float) r2, (float) r3, (float) r8, (float) r9)
        L_0x00f3:
            r7 = r7 ^ 1
            float r2 = r2 + r5
            int r13 = r13 + 1
            goto L_0x00d8
        L_0x00f9:
            r12.fill()
            com.itextpdf.text.pdf.BaseFont r13 = r11.font
            if (r13 == 0) goto L_0x0118
            if (r14 == 0) goto L_0x0105
            r12.setColorFill(r14)
        L_0x0105:
            r12.beginText()
            com.itextpdf.text.pdf.BaseFont r13 = r11.font
            float r14 = r11.size
            r12.setFontAndSize(r13, r14)
            r12.setTextMatrix(r6, r4)
            r12.showText((java.lang.String) r0)
            r12.endText()
        L_0x0118:
            com.itextpdf.text.Rectangle r12 = r11.getBarcodeSize()
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.Barcode39.placeBarcode(com.itextpdf.text.pdf.PdfContentByte, com.itextpdf.text.BaseColor, com.itextpdf.text.BaseColor):com.itextpdf.text.Rectangle");
    }

    public Image createAwtImage(Color color, Color color2) {
        int rgb = color.getRGB();
        int rgb2 = color2.getRGB();
        Canvas canvas = new Canvas();
        String str = this.code;
        if (this.extended) {
            str = getCode39Ex(this.code);
        }
        if (this.generateChecksum) {
            str = str + getChecksum(str);
        }
        int length = str.length() + 2;
        int i = (int) this.f503n;
        int i2 = (((i * 3) + 6) * length) + (length - 1);
        byte[] barsCode39 = getBarsCode39(str);
        int i3 = (int) this.barHeight;
        int i4 = i2 * i3;
        int[] iArr = new int[i4];
        boolean z = true;
        int i5 = 0;
        for (int i6 = 0; i6 < barsCode39.length; i6++) {
            int i7 = barsCode39[i6] == 0 ? 1 : i;
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
