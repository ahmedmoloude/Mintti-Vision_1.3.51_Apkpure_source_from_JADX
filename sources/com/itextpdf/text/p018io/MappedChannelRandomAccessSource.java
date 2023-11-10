package com.itextpdf.text.p018io;

import java.io.IOException;
import java.nio.channels.FileChannel;

/* renamed from: com.itextpdf.text.io.MappedChannelRandomAccessSource */
class MappedChannelRandomAccessSource implements RandomAccessSource {
    private final FileChannel channel;
    private final long length;
    private final long offset;
    private ByteBufferRandomAccessSource source;

    public MappedChannelRandomAccessSource(FileChannel fileChannel, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException(j + " is negative");
        } else if (j2 > 0) {
            this.channel = fileChannel;
            this.offset = j;
            this.length = j2;
            this.source = null;
        } else {
            throw new IllegalArgumentException(j2 + " is zero or negative");
        }
    }

    /* access modifiers changed from: package-private */
    public void open() throws IOException {
        if (this.source == null) {
            if (this.channel.isOpen()) {
                try {
                    this.source = new ByteBufferRandomAccessSource(this.channel.map(FileChannel.MapMode.READ_ONLY, this.offset, this.length));
                } catch (IOException e) {
                    if (exceptionIsMapFailureException(e)) {
                        throw new MapFailedException(e);
                    }
                    throw e;
                }
            } else {
                throw new IllegalStateException("Channel is closed");
            }
        }
    }

    private static boolean exceptionIsMapFailureException(IOException iOException) {
        return iOException.getMessage() != null && iOException.getMessage().indexOf("Map failed") >= 0;
    }

    public int get(long j) throws IOException {
        ByteBufferRandomAccessSource byteBufferRandomAccessSource = this.source;
        if (byteBufferRandomAccessSource != null) {
            return byteBufferRandomAccessSource.get(j);
        }
        throw new IOException("RandomAccessSource not opened");
    }

    public int get(long j, byte[] bArr, int i, int i2) throws IOException {
        ByteBufferRandomAccessSource byteBufferRandomAccessSource = this.source;
        if (byteBufferRandomAccessSource != null) {
            return byteBufferRandomAccessSource.get(j, bArr, i, i2);
        }
        throw new IOException("RandomAccessSource not opened");
    }

    public long length() {
        return this.length;
    }

    public void close() throws IOException {
        ByteBufferRandomAccessSource byteBufferRandomAccessSource = this.source;
        if (byteBufferRandomAccessSource != null) {
            byteBufferRandomAccessSource.close();
            this.source = null;
        }
    }

    public String toString() {
        return getClass().getName() + " (" + this.offset + ", " + this.length + ")";
    }
}
