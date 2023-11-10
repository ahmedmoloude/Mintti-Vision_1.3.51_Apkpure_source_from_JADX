package com.itextpdf.text.pdf.qrcode;

import kotlin.UByte;

public final class ByteArray {
    private static final int INITIAL_SIZE = 32;
    private byte[] bytes;
    private int size;

    public ByteArray() {
        this.bytes = null;
        this.size = 0;
    }

    public ByteArray(int i) {
        this.bytes = new byte[i];
        this.size = i;
    }

    public ByteArray(byte[] bArr) {
        this.bytes = bArr;
        this.size = bArr.length;
    }

    /* renamed from: at */
    public int mo23880at(int i) {
        return this.bytes[i] & UByte.MAX_VALUE;
    }

    public void set(int i, int i2) {
        this.bytes[i] = (byte) i2;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void appendByte(int i) {
        int i2 = this.size;
        if (i2 == 0 || i2 >= this.bytes.length) {
            reserve(Math.max(32, i2 << 1));
        }
        byte[] bArr = this.bytes;
        int i3 = this.size;
        bArr[i3] = (byte) i;
        this.size = i3 + 1;
    }

    public void reserve(int i) {
        byte[] bArr = this.bytes;
        if (bArr == null || bArr.length < i) {
            byte[] bArr2 = new byte[i];
            if (bArr != null) {
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            }
            this.bytes = bArr2;
        }
    }

    public void set(byte[] bArr, int i, int i2) {
        this.bytes = new byte[i2];
        this.size = i2;
        for (int i3 = 0; i3 < i2; i3++) {
            this.bytes[i3] = bArr[i + i3];
        }
    }
}
