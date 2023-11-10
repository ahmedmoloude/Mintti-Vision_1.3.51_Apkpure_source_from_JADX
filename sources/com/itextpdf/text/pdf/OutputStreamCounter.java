package com.itextpdf.text.pdf;

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamCounter extends OutputStream {
    protected long counter = 0;
    protected OutputStream out;

    public OutputStreamCounter(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void close() throws IOException {
        this.out.close();
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public void write(byte[] bArr) throws IOException {
        this.counter += (long) bArr.length;
        this.out.write(bArr);
    }

    public void write(int i) throws IOException {
        this.counter++;
        this.out.write(i);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.counter += (long) i2;
        this.out.write(bArr, i, i2);
    }

    public long getCounter() {
        return this.counter;
    }

    public void resetCounter() {
        this.counter = 0;
    }
}
