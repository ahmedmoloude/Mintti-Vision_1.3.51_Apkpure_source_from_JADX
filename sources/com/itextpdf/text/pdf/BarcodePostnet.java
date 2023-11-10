package com.itextpdf.text.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class BarcodePostnet extends Barcode {
    private static final byte[][] BARS = {new byte[]{1, 1, 0, 0, 0}, new byte[]{0, 0, 0, 1, 1}, new byte[]{0, 0, 1, 0, 1}, new byte[]{0, 0, 1, 1, 0}, new byte[]{0, 1, 0, 0, 1}, new byte[]{0, 1, 0, 1, 0}, new byte[]{0, 1, 1, 0, 0}, new byte[]{1, 0, 0, 0, 1}, new byte[]{1, 0, 0, 1, 0}, new byte[]{1, 0, 1, 0, 0}};

    public BarcodePostnet() {
        this.f503n = 3.2727273f;
        this.f504x = 1.4399999f;
        this.barHeight = 9.0f;
        this.size = 3.6000001f;
        this.codeType = 7;
    }

    public static byte[] getBarsPostnet(String str) {
        int i = 0;
        for (int length = str.length() - 1; length >= 0; length--) {
            i += str.charAt(length) - '0';
        }
        String str2 = str + ((char) (((10 - (i % 10)) % 10) + 48));
        int length2 = (str2.length() * 5) + 2;
        byte[] bArr = new byte[length2];
        bArr[0] = 1;
        bArr[length2 - 1] = 1;
        for (int i2 = 0; i2 < str2.length(); i2++) {
            System.arraycopy(BARS[str2.charAt(i2) - '0'], 0, bArr, (i2 * 5) + 1, 5);
        }
        return bArr;
    }

    public Rectangle getBarcodeSize() {
        return new Rectangle((((float) (((this.code.length() + 1) * 5) + 1)) * this.f503n) + this.f504x, this.barHeight);
    }

    public Rectangle placeBarcode(PdfContentByte pdfContentByte, BaseColor baseColor, BaseColor baseColor2) {
        if (baseColor != null) {
            pdfContentByte.setColorFill(baseColor);
        }
        byte[] barsPostnet = getBarsPostnet(this.code);
        byte b = 1;
        if (this.codeType == 8) {
            barsPostnet[0] = 0;
            barsPostnet[barsPostnet.length - 1] = 0;
            b = 0;
        }
        float f = 0.0f;
        for (int i = 0; i < barsPostnet.length; i++) {
            pdfContentByte.rectangle(f, 0.0f, this.f504x - this.inkSpreading, barsPostnet[i] == b ? this.barHeight : this.size);
            f += this.f503n;
        }
        pdfContentByte.fill();
        return getBarcodeSize();
    }

    public Image createAwtImage(Color color, Color color2) {
        byte b;
        int rgb = color.getRGB();
        int rgb2 = color2.getRGB();
        Canvas canvas = new Canvas();
        int i = (int) this.f504x;
        if (i <= 0) {
            i = 1;
        }
        int i2 = (int) this.f503n;
        if (i2 <= i) {
            i2 = i + 1;
        }
        int i3 = (int) this.size;
        if (i3 <= 0) {
            i3 = 1;
        }
        int i4 = (int) this.barHeight;
        if (i4 <= i3) {
            i4 = i3 + 1;
        }
        int i5 = i4;
        int length = ((((this.code.length() + 1) * 5) + 1) * i2) + i;
        int i6 = length * i5;
        int[] iArr = new int[i6];
        byte[] barsPostnet = getBarsPostnet(this.code);
        int i7 = 0;
        if (this.codeType == 8) {
            barsPostnet[0] = 0;
            barsPostnet[barsPostnet.length - 1] = 0;
            b = 0;
        } else {
            b = 1;
        }
        int i8 = 0;
        int i9 = 0;
        while (i8 < barsPostnet.length) {
            boolean z = barsPostnet[i8] == b;
            while (i7 < i2) {
                iArr[i9 + i7] = (!z || i7 >= i) ? rgb2 : rgb;
                i7++;
            }
            i9 += i2;
            i8++;
            i7 = 0;
        }
        int i10 = (i5 - i3) * length;
        for (int i11 = length; i11 < i10; i11 += length) {
            System.arraycopy(iArr, 0, iArr, i11, length);
        }
        int i12 = i10;
        for (int i13 = 0; i13 < barsPostnet.length; i13++) {
            int i14 = 0;
            while (i14 < i2) {
                iArr[i12 + i14] = i14 < i ? rgb : rgb2;
                i14++;
            }
            i12 += i2;
        }
        for (int i15 = i10 + length; i15 < i6; i15 += length) {
            System.arraycopy(iArr, i10, iArr, i15, length);
        }
        return canvas.createImage(new MemoryImageSource(length, i5, iArr, 0, length));
    }
}
