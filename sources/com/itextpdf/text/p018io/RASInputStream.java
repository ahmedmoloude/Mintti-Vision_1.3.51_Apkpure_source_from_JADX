package com.itextpdf.text.p018io;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.itextpdf.text.io.RASInputStream */
public class RASInputStream extends InputStream {
    private long position = 0;
    private final RandomAccessSource source;

    public RASInputStream(RandomAccessSource randomAccessSource) {
        this.source = randomAccessSource;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.source.get(this.position, bArr, i, i2);
        this.position += (long) i3;
        return i3;
    }

    public int read() throws IOException {
        RandomAccessSource randomAccessSource = this.source;
        long j = this.position;
        this.position = 1 + j;
        return randomAccessSource.get(j);
    }
}
