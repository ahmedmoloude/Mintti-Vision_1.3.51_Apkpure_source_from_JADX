package com.itextpdf.text.pdf.qrcode;

public final class BitMatrix {
    public final int[] bits;
    public final int height;
    public final int rowSize;
    public final int width;

    public BitMatrix(int i) {
        this(i, i);
    }

    public BitMatrix(int i, int i2) {
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException("Both dimensions must be greater than 0");
        }
        this.width = i;
        this.height = i2;
        int i3 = i >> 5;
        i3 = (i & 31) != 0 ? i3 + 1 : i3;
        this.rowSize = i3;
        this.bits = new int[(i3 * i2)];
    }

    public boolean get(int i, int i2) {
        return ((this.bits[(i2 * this.rowSize) + (i >> 5)] >>> (i & 31)) & 1) != 0;
    }

    public void set(int i, int i2) {
        int i3 = (i2 * this.rowSize) + (i >> 5);
        int[] iArr = this.bits;
        iArr[i3] = (1 << (i & 31)) | iArr[i3];
    }

    public void flip(int i, int i2) {
        int i3 = (i2 * this.rowSize) + (i >> 5);
        int[] iArr = this.bits;
        iArr[i3] = (1 << (i & 31)) ^ iArr[i3];
    }

    public void clear() {
        int length = this.bits.length;
        for (int i = 0; i < length; i++) {
            this.bits[i] = 0;
        }
    }

    public void setRegion(int i, int i2, int i3, int i4) {
        if (i2 < 0 || i < 0) {
            throw new IllegalArgumentException("Left and top must be nonnegative");
        } else if (i4 < 1 || i3 < 1) {
            throw new IllegalArgumentException("Height and width must be at least 1");
        } else {
            int i5 = i3 + i;
            int i6 = i4 + i2;
            if (i6 > this.height || i5 > this.width) {
                throw new IllegalArgumentException("The region must fit inside the matrix");
            }
            while (i2 < i6) {
                int i7 = this.rowSize * i2;
                for (int i8 = i; i8 < i5; i8++) {
                    int[] iArr = this.bits;
                    int i9 = (i8 >> 5) + i7;
                    iArr[i9] = iArr[i9] | (1 << (i8 & 31));
                }
                i2++;
            }
        }
    }

    public BitArray getRow(int i, BitArray bitArray) {
        if (bitArray == null || bitArray.getSize() < this.width) {
            bitArray = new BitArray(this.width);
        }
        int i2 = i * this.rowSize;
        for (int i3 = 0; i3 < this.rowSize; i3++) {
            bitArray.setBulk(i3 << 5, this.bits[i2 + i3]);
        }
        return bitArray;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getDimension() {
        int i = this.width;
        if (i == this.height) {
            return i;
        }
        throw new RuntimeException("Can't call getDimension() on a non-square matrix");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.height * (this.width + 1));
        for (int i = 0; i < this.height; i++) {
            for (int i2 = 0; i2 < this.width; i2++) {
                stringBuffer.append(get(i2, i) ? "X " : "  ");
            }
            stringBuffer.append(10);
        }
        return stringBuffer.toString();
    }
}
