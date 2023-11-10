package com.itextpdf.text.pdf.hyphenation;

import java.io.Serializable;

public class CharVector implements Cloneable, Serializable {
    private static final int DEFAULT_BLOCK_SIZE = 2048;
    private static final long serialVersionUID = -4875768298308363544L;
    private char[] array;
    private int blockSize;

    /* renamed from: n */
    private int f735n;

    public CharVector() {
        this(2048);
    }

    public CharVector(int i) {
        if (i > 0) {
            this.blockSize = i;
        } else {
            this.blockSize = 2048;
        }
        this.array = new char[this.blockSize];
        this.f735n = 0;
    }

    public CharVector(char[] cArr) {
        this.blockSize = 2048;
        this.array = cArr;
        this.f735n = cArr.length;
    }

    public CharVector(char[] cArr, int i) {
        if (i > 0) {
            this.blockSize = i;
        } else {
            this.blockSize = 2048;
        }
        this.array = cArr;
        this.f735n = cArr.length;
    }

    public void clear() {
        this.f735n = 0;
    }

    public Object clone() {
        CharVector charVector = new CharVector((char[]) this.array.clone(), this.blockSize);
        charVector.f735n = this.f735n;
        return charVector;
    }

    public char[] getArray() {
        return this.array;
    }

    public int length() {
        return this.f735n;
    }

    public int capacity() {
        return this.array.length;
    }

    public void put(int i, char c) {
        this.array[i] = c;
    }

    public char get(int i) {
        return this.array[i];
    }

    public int alloc(int i) {
        int i2 = this.f735n;
        char[] cArr = this.array;
        int length = cArr.length;
        if (i2 + i >= length) {
            char[] cArr2 = new char[(this.blockSize + length)];
            System.arraycopy(cArr, 0, cArr2, 0, length);
            this.array = cArr2;
        }
        this.f735n += i;
        return i2;
    }

    public void trimToSize() {
        int i = this.f735n;
        char[] cArr = this.array;
        if (i < cArr.length) {
            char[] cArr2 = new char[i];
            System.arraycopy(cArr, 0, cArr2, 0, i);
            this.array = cArr2;
        }
    }
}
