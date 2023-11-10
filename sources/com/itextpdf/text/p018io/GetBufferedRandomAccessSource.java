package com.itextpdf.text.p018io;

import android.support.p005v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import kotlin.UByte;

/* renamed from: com.itextpdf.text.io.GetBufferedRandomAccessSource */
public class GetBufferedRandomAccessSource implements RandomAccessSource {
    private final byte[] getBuffer;
    private long getBufferEnd = -1;
    private long getBufferStart = -1;
    private final RandomAccessSource source;

    public GetBufferedRandomAccessSource(RandomAccessSource randomAccessSource) {
        this.source = randomAccessSource;
        this.getBuffer = new byte[((int) Math.min(Math.max(randomAccessSource.length() / 4, 1), PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM))];
        this.getBufferStart = -1;
        this.getBufferEnd = -1;
    }

    public int get(long j) throws IOException {
        if (j < this.getBufferStart || j > this.getBufferEnd) {
            RandomAccessSource randomAccessSource = this.source;
            byte[] bArr = this.getBuffer;
            int i = randomAccessSource.get(j, bArr, 0, bArr.length);
            if (i == -1) {
                return -1;
            }
            this.getBufferStart = j;
            this.getBufferEnd = (((long) i) + j) - 1;
        }
        return this.getBuffer[(int) (j - this.getBufferStart)] & UByte.MAX_VALUE;
    }

    public int get(long j, byte[] bArr, int i, int i2) throws IOException {
        return this.source.get(j, bArr, i, i2);
    }

    public long length() {
        return this.source.length();
    }

    public void close() throws IOException {
        this.source.close();
        this.getBufferStart = -1;
        this.getBufferEnd = -1;
    }
}
