package com.itextpdf.text.p018io;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.LinkedList;

/* renamed from: com.itextpdf.text.io.PagedChannelRandomAccessSource */
class PagedChannelRandomAccessSource extends GroupedRandomAccessSource implements RandomAccessSource {
    public static final int DEFAULT_MAX_OPEN_BUFFERS = 16;
    public static final int DEFAULT_TOTAL_BUFSIZE = 67108864;
    private final int bufferSize;
    private final FileChannel channel;
    private final MRU<RandomAccessSource> mru;

    public PagedChannelRandomAccessSource(FileChannel fileChannel) throws IOException {
        this(fileChannel, DEFAULT_TOTAL_BUFSIZE, 16);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PagedChannelRandomAccessSource(java.nio.channels.FileChannel r2, int r3, int r4) throws java.io.IOException {
        /*
            r1 = this;
            int r3 = r3 / r4
            com.itextpdf.text.io.RandomAccessSource[] r0 = buildSources(r2, r3)
            r1.<init>(r0)
            r1.channel = r2
            r1.bufferSize = r3
            com.itextpdf.text.io.PagedChannelRandomAccessSource$MRU r2 = new com.itextpdf.text.io.PagedChannelRandomAccessSource$MRU
            r2.<init>(r4)
            r1.mru = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.p018io.PagedChannelRandomAccessSource.<init>(java.nio.channels.FileChannel, int, int):void");
    }

    private static RandomAccessSource[] buildSources(FileChannel fileChannel, int i) throws IOException {
        long size = fileChannel.size();
        if (size > 0) {
            long j = (long) i;
            int i2 = ((int) (size / j)) + (size % j == 0 ? 0 : 1);
            MappedChannelRandomAccessSource[] mappedChannelRandomAccessSourceArr = new MappedChannelRandomAccessSource[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                long j2 = ((long) i3) * j;
                mappedChannelRandomAccessSourceArr[i3] = new MappedChannelRandomAccessSource(fileChannel, j2, Math.min(size - j2, j));
            }
            return mappedChannelRandomAccessSourceArr;
        }
        throw new IOException("File size must be greater than zero");
    }

    /* access modifiers changed from: protected */
    public int getStartingSourceIndex(long j) {
        return (int) (j / ((long) this.bufferSize));
    }

    /* access modifiers changed from: protected */
    public void sourceReleased(RandomAccessSource randomAccessSource) throws IOException {
        RandomAccessSource enqueue = this.mru.enqueue(randomAccessSource);
        if (enqueue != null) {
            enqueue.close();
        }
    }

    /* access modifiers changed from: protected */
    public void sourceInUse(RandomAccessSource randomAccessSource) throws IOException {
        ((MappedChannelRandomAccessSource) randomAccessSource).open();
    }

    public void close() throws IOException {
        super.close();
        this.channel.close();
    }

    /* renamed from: com.itextpdf.text.io.PagedChannelRandomAccessSource$MRU */
    private static class MRU<E> {
        private final int limit;
        private LinkedList<E> queue = new LinkedList<>();

        public MRU(int i) {
            this.limit = i;
        }

        public E enqueue(E e) {
            if (this.queue.size() > 0 && this.queue.getFirst() == e) {
                return null;
            }
            Iterator it = this.queue.iterator();
            while (it.hasNext()) {
                if (e == it.next()) {
                    it.remove();
                    this.queue.addFirst(e);
                    return null;
                }
            }
            this.queue.addFirst(e);
            if (this.queue.size() > this.limit) {
                return this.queue.removeLast();
            }
            return null;
        }
    }
}
