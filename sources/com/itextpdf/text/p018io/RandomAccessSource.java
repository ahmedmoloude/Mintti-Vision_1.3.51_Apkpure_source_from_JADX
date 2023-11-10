package com.itextpdf.text.p018io;

import java.io.IOException;

/* renamed from: com.itextpdf.text.io.RandomAccessSource */
public interface RandomAccessSource {
    void close() throws IOException;

    int get(long j) throws IOException;

    int get(long j, byte[] bArr, int i, int i2) throws IOException;

    long length();
}
