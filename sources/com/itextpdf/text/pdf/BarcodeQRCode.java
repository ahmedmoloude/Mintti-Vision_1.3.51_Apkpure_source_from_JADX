package com.itextpdf.text.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.codec.CCITTG4Encoder;
import com.itextpdf.text.pdf.qrcode.ByteMatrix;
import com.itextpdf.text.pdf.qrcode.EncodeHintType;
import com.itextpdf.text.pdf.qrcode.QRCodeWriter;
import com.itextpdf.text.pdf.qrcode.WriterException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.MemoryImageSource;
import java.util.Map;

public class BarcodeQRCode {

    /* renamed from: bm */
    ByteMatrix f515bm;

    public BarcodeQRCode(String str, int i, int i2, Map<EncodeHintType, Object> map) {
        try {
            this.f515bm = new QRCodeWriter().encode(str, i, i2, map);
        } catch (WriterException e) {
            throw new ExceptionConverter(e);
        }
    }

    private byte[] getBitMatrix() {
        int width = this.f515bm.getWidth();
        int height = this.f515bm.getHeight();
        int i = (width + 7) / 8;
        byte[] bArr = new byte[(i * height)];
        byte[][] array = this.f515bm.getArray();
        for (int i2 = 0; i2 < height; i2++) {
            byte[] bArr2 = array[i2];
            for (int i3 = 0; i3 < width; i3++) {
                if (bArr2[i3] != 0) {
                    int i4 = (i * i2) + (i3 / 8);
                    bArr[i4] = (byte) (bArr[i4] | ((byte) (128 >> (i3 % 8))));
                }
            }
        }
        return bArr;
    }

    public Image getImage() throws BadElementException {
        return Image.getInstance(this.f515bm.getWidth(), this.f515bm.getHeight(), false, 256, 1, CCITTG4Encoder.compress(getBitMatrix(), this.f515bm.getWidth(), this.f515bm.getHeight()), (int[]) null);
    }

    public java.awt.Image createAwtImage(Color color, Color color2) {
        int rgb = color.getRGB();
        int rgb2 = color2.getRGB();
        Canvas canvas = new Canvas();
        int width = this.f515bm.getWidth();
        int height = this.f515bm.getHeight();
        int[] iArr = new int[(width * height)];
        byte[][] array = this.f515bm.getArray();
        for (int i = 0; i < height; i++) {
            byte[] bArr = array[i];
            for (int i2 = 0; i2 < width; i2++) {
                iArr[(i * width) + i2] = bArr[i2] == 0 ? rgb : rgb2;
            }
        }
        return canvas.createImage(new MemoryImageSource(width, height, iArr, 0, width));
    }

    public void placeBarcode(PdfContentByte pdfContentByte, BaseColor baseColor, float f) {
        int width = this.f515bm.getWidth();
        int height = this.f515bm.getHeight();
        byte[][] array = this.f515bm.getArray();
        pdfContentByte.setColorFill(baseColor);
        for (int i = 0; i < height; i++) {
            byte[] bArr = array[i];
            for (int i2 = 0; i2 < width; i2++) {
                if (bArr[i2] == 0) {
                    pdfContentByte.rectangle(((float) i2) * f, ((float) ((height - i) - 1)) * f, f, f);
                }
            }
        }
        pdfContentByte.fill();
    }

    public Rectangle getBarcodeSize() {
        return new Rectangle(0.0f, 0.0f, (float) this.f515bm.getWidth(), (float) this.f515bm.getHeight());
    }
}
