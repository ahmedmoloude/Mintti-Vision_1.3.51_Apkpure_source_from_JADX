package com.itextpdf.text.p018io;

import java.io.IOException;
import java.util.Objects;
import kotlin.UByte;

/* renamed from: com.itextpdf.text.io.ArrayRandomAccessSource */
class ArrayRandomAccessSource implements RandomAccessSource {
    private byte[] array;

    public ArrayRandomAccessSource(byte[] bArr) {
        Objects.requireNonNull(bArr);
        this.array = bArr;
    }

    public int get(long j) {
        byte[] bArr = this.array;
        if (j >= ((long) bArr.length)) {
            return -1;
        }
        return bArr[(int) j] & UByte.MAX_VALUE;
    }

    public int get(long j, byte[] bArr, int i, int i2) {
        byte[] bArr2 = this.array;
        if (bArr2 == null) {
            throw new IllegalStateException("Already closed");
        } else if (j >= ((long) bArr2.length)) {
            return -1;
        } else {
            if (((long) i2) + j > ((long) bArr2.length)) {
                i2 = (int) (((long) bArr2.length) - j);
            }
            System.arraycopy(bArr2, (int) j, bArr, i, i2);
            return i2;
        }
    }

    public long length() {
        return (long) this.array.length;
    }

    public void close() throws IOException {
        this.array = null;
    }
}
