package com.itextpdf.text.p018io;

import java.io.IOException;
import java.nio.channels.FileChannel;

/* renamed from: com.itextpdf.text.io.FileChannelRandomAccessSource */
public class FileChannelRandomAccessSource implements RandomAccessSource {
    private final FileChannel channel;
    private final MappedChannelRandomAccessSource source;

    public FileChannelRandomAccessSource(FileChannel fileChannel) throws IOException {
        this.channel = fileChannel;
        if (fileChannel.size() != 0) {
            MappedChannelRandomAccessSource mappedChannelRandomAccessSource = new MappedChannelRandomAccessSource(fileChannel, 0, fileChannel.size());
            this.source = mappedChannelRandomAccessSource;
            mappedChannelRandomAccessSource.open();
            return;
        }
        throw new IOException("File size is 0 bytes");
    }

    public void close() throws IOException {
        this.source.close();
        this.channel.close();
    }

    public int get(long j) throws IOException {
        return this.source.get(j);
    }

    public int get(long j, byte[] bArr, int i, int i2) throws IOException {
        return this.source.get(j, bArr, i, i2);
    }

    public long length() {
        return this.source.length();
    }
}
