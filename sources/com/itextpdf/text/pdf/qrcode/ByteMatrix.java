package com.itextpdf.text.pdf.qrcode;

import java.lang.reflect.Array;

public final class ByteMatrix {
    private final byte[][] bytes;
    private final int height;
    private final int width;

    public ByteMatrix(int i, int i2) {
        int[] iArr = new int[2];
        iArr[1] = i;
        iArr[0] = i2;
        this.bytes = (byte[][]) Array.newInstance(byte.class, iArr);
        this.width = i;
        this.height = i2;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public byte get(int i, int i2) {
        return this.bytes[i2][i];
    }

    public byte[][] getArray() {
        return this.bytes;
    }

    public void set(int i, int i2, byte b) {
        this.bytes[i2][i] = b;
    }

    public void set(int i, int i2, int i3) {
        this.bytes[i2][i] = (byte) i3;
    }

    public void clear(byte b) {
        for (int i = 0; i < this.height; i++) {
            for (int i2 = 0; i2 < this.width; i2++) {
                this.bytes[i][i2] = b;
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer((this.width * 2 * this.height) + 2);
        for (int i = 0; i < this.height; i++) {
            for (int i2 = 0; i2 < this.width; i2++) {
                byte b = this.bytes[i][i2];
                if (b == 0) {
                    stringBuffer.append(" 0");
                } else if (b != 1) {
                    stringBuffer.append("  ");
                } else {
                    stringBuffer.append(" 1");
                }
            }
            stringBuffer.append(10);
        }
        return stringBuffer.toString();
    }
}
