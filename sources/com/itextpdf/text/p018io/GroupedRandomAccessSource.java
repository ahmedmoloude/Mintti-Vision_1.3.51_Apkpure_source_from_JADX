package com.itextpdf.text.p018io;

import java.io.IOException;

/* renamed from: com.itextpdf.text.io.GroupedRandomAccessSource */
class GroupedRandomAccessSource implements RandomAccessSource {
    private SourceEntry currentSourceEntry;
    private final long size;
    private final SourceEntry[] sources;

    /* access modifiers changed from: protected */
    public void sourceInUse(RandomAccessSource randomAccessSource) throws IOException {
    }

    /* access modifiers changed from: protected */
    public void sourceReleased(RandomAccessSource randomAccessSource) throws IOException {
    }

    public GroupedRandomAccessSource(RandomAccessSource[] randomAccessSourceArr) throws IOException {
        this.sources = new SourceEntry[randomAccessSourceArr.length];
        long j = 0;
        for (int i = 0; i < randomAccessSourceArr.length; i++) {
            this.sources[i] = new SourceEntry(i, randomAccessSourceArr[i], j);
            j += randomAccessSourceArr[i].length();
        }
        this.size = j;
        SourceEntry sourceEntry = this.sources[randomAccessSourceArr.length - 1];
        this.currentSourceEntry = sourceEntry;
        sourceInUse(sourceEntry.source);
    }

    /* access modifiers changed from: protected */
    public int getStartingSourceIndex(long j) {
        if (j >= this.currentSourceEntry.firstByte) {
            return this.currentSourceEntry.index;
        }
        return 0;
    }

    private SourceEntry getSourceEntryForOffset(long j) throws IOException {
        if (j >= this.size) {
            return null;
        }
        if (j >= this.currentSourceEntry.firstByte && j <= this.currentSourceEntry.lastByte) {
            return this.currentSourceEntry;
        }
        sourceReleased(this.currentSourceEntry.source);
        int startingSourceIndex = getStartingSourceIndex(j);
        while (true) {
            SourceEntry[] sourceEntryArr = this.sources;
            if (startingSourceIndex >= sourceEntryArr.length) {
                return null;
            }
            if (j < sourceEntryArr[startingSourceIndex].firstByte || j > this.sources[startingSourceIndex].lastByte) {
                startingSourceIndex++;
            } else {
                SourceEntry sourceEntry = this.sources[startingSourceIndex];
                this.currentSourceEntry = sourceEntry;
                sourceInUse(sourceEntry.source);
                return this.currentSourceEntry;
            }
        }
    }

    public int get(long j) throws IOException {
        SourceEntry sourceEntryForOffset = getSourceEntryForOffset(j);
        if (sourceEntryForOffset == null) {
            return -1;
        }
        return sourceEntryForOffset.source.get(sourceEntryForOffset.offsetN(j));
    }

    public int get(long j, byte[] bArr, int i, int i2) throws IOException {
        SourceEntry sourceEntryForOffset = getSourceEntryForOffset(j);
        if (sourceEntryForOffset == null) {
            return -1;
        }
        int i3 = i2;
        long offsetN = sourceEntryForOffset.offsetN(j);
        while (i3 > 0 && sourceEntryForOffset != null && offsetN <= sourceEntryForOffset.source.length()) {
            int i4 = sourceEntryForOffset.source.get(offsetN, bArr, i, i3);
            if (i4 == -1) {
                break;
            }
            i += i4;
            j += (long) i4;
            i3 -= i4;
            offsetN = 0;
            sourceEntryForOffset = getSourceEntryForOffset(j);
        }
        if (i3 == i2) {
            return -1;
        }
        return i2 - i3;
    }

    public long length() {
        return this.size;
    }

    public void close() throws IOException {
        for (SourceEntry sourceEntry : this.sources) {
            sourceEntry.source.close();
        }
    }

    /* renamed from: com.itextpdf.text.io.GroupedRandomAccessSource$SourceEntry */
    private static class SourceEntry {
        final long firstByte;
        final int index;
        final long lastByte;
        final RandomAccessSource source;

        public SourceEntry(int i, RandomAccessSource randomAccessSource, long j) {
            this.index = i;
            this.source = randomAccessSource;
            this.firstByte = j;
            this.lastByte = (j + randomAccessSource.length()) - 1;
        }

        public long offsetN(long j) {
            return j - this.firstByte;
        }
    }
}
