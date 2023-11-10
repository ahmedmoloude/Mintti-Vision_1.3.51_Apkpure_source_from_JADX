package com.itextpdf.text.pdf.codec;

import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.BidiOrder;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class JBIG2SegmentReader {
    public static final int END_OF_FILE = 51;
    public static final int END_OF_PAGE = 49;
    public static final int END_OF_STRIPE = 50;
    public static final int EXTENSION = 62;
    public static final int IMMEDIATE_GENERIC_REFINEMENT_REGION = 42;
    public static final int IMMEDIATE_GENERIC_REGION = 38;
    public static final int IMMEDIATE_HALFTONE_REGION = 22;
    public static final int IMMEDIATE_LOSSLESS_GENERIC_REFINEMENT_REGION = 43;
    public static final int IMMEDIATE_LOSSLESS_GENERIC_REGION = 39;
    public static final int IMMEDIATE_LOSSLESS_HALFTONE_REGION = 23;
    public static final int IMMEDIATE_LOSSLESS_TEXT_REGION = 7;
    public static final int IMMEDIATE_TEXT_REGION = 6;
    public static final int INTERMEDIATE_GENERIC_REFINEMENT_REGION = 40;
    public static final int INTERMEDIATE_GENERIC_REGION = 36;
    public static final int INTERMEDIATE_HALFTONE_REGION = 20;
    public static final int INTERMEDIATE_TEXT_REGION = 4;
    public static final int PAGE_INFORMATION = 48;
    public static final int PATTERN_DICTIONARY = 16;
    public static final int PROFILES = 52;
    public static final int SYMBOL_DICTIONARY = 0;
    public static final int TABLES = 53;
    private final SortedSet<JBIG2Segment> globals = new TreeSet();
    private int number_of_pages = -1;
    private boolean number_of_pages_known;
    private final SortedMap<Integer, JBIG2Page> pages = new TreeMap();

    /* renamed from: ra */
    private RandomAccessFileOrArray f708ra;
    private boolean read = false;
    private final SortedMap<Integer, JBIG2Segment> segments = new TreeMap();
    private boolean sequential;

    public static class JBIG2Segment implements Comparable<JBIG2Segment> {
        public int countOfReferredToSegments = -1;
        public byte[] data = null;
        public long dataLength = -1;
        public boolean deferredNonRetain = false;
        public byte[] headerData = null;
        public int page = -1;
        public int page_association_offset = -1;
        public boolean page_association_size = false;
        public int[] referredToSegmentNumbers = null;
        public final int segmentNumber;
        public boolean[] segmentRetentionFlags = null;
        public int type = -1;

        public JBIG2Segment(int i) {
            this.segmentNumber = i;
        }

        public int compareTo(JBIG2Segment jBIG2Segment) {
            return this.segmentNumber - jBIG2Segment.segmentNumber;
        }
    }

    public static class JBIG2Page {
        public final int page;
        public int pageBitmapHeight = -1;
        public int pageBitmapWidth = -1;
        private final SortedMap<Integer, JBIG2Segment> segs = new TreeMap();

        /* renamed from: sr */
        private final JBIG2SegmentReader f709sr;

        public JBIG2Page(int i, JBIG2SegmentReader jBIG2SegmentReader) {
            this.page = i;
            this.f709sr = jBIG2SegmentReader;
        }

        public byte[] getData(boolean z) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (Integer num : this.segs.keySet()) {
                JBIG2Segment jBIG2Segment = (JBIG2Segment) this.segs.get(num);
                if (!z || !(jBIG2Segment.type == 51 || jBIG2Segment.type == 49)) {
                    if (z) {
                        byte[] copyByteArray = JBIG2SegmentReader.copyByteArray(jBIG2Segment.headerData);
                        if (jBIG2Segment.page_association_size) {
                            copyByteArray[jBIG2Segment.page_association_offset] = 0;
                            copyByteArray[jBIG2Segment.page_association_offset + 1] = 0;
                            copyByteArray[jBIG2Segment.page_association_offset + 2] = 0;
                            copyByteArray[jBIG2Segment.page_association_offset + 3] = 1;
                        } else {
                            copyByteArray[jBIG2Segment.page_association_offset] = 1;
                        }
                        byteArrayOutputStream.write(copyByteArray);
                    } else {
                        byteArrayOutputStream.write(jBIG2Segment.headerData);
                    }
                    byteArrayOutputStream.write(jBIG2Segment.data);
                }
            }
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        }

        public void addSegment(JBIG2Segment jBIG2Segment) {
            this.segs.put(Integer.valueOf(jBIG2Segment.segmentNumber), jBIG2Segment);
        }
    }

    public JBIG2SegmentReader(RandomAccessFileOrArray randomAccessFileOrArray) throws IOException {
        this.f708ra = randomAccessFileOrArray;
    }

    public static byte[] copyByteArray(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    public void read() throws IOException {
        JBIG2Segment readHeader;
        if (!this.read) {
            this.read = true;
            readFileHeader();
            if (this.sequential) {
                do {
                    JBIG2Segment readHeader2 = readHeader();
                    readSegment(readHeader2);
                    this.segments.put(Integer.valueOf(readHeader2.segmentNumber), readHeader2);
                } while (this.f708ra.getFilePointer() < this.f708ra.length());
                return;
            }
            do {
                readHeader = readHeader();
                this.segments.put(Integer.valueOf(readHeader.segmentNumber), readHeader);
            } while (readHeader.type != 51);
            for (Integer num : this.segments.keySet()) {
                readSegment((JBIG2Segment) this.segments.get(num));
            }
            return;
        }
        throw new IllegalStateException(MessageLocalization.getComposedMessage("already.attempted.a.read.on.this.jbig2.file", new Object[0]));
    }

    /* access modifiers changed from: package-private */
    public void readSegment(JBIG2Segment jBIG2Segment) throws IOException {
        int filePointer = (int) this.f708ra.getFilePointer();
        if (jBIG2Segment.dataLength != 4294967295L) {
            byte[] bArr = new byte[((int) jBIG2Segment.dataLength)];
            this.f708ra.read(bArr);
            jBIG2Segment.data = bArr;
            if (jBIG2Segment.type == 48) {
                int filePointer2 = (int) this.f708ra.getFilePointer();
                this.f708ra.seek((long) filePointer);
                int readInt = this.f708ra.readInt();
                int readInt2 = this.f708ra.readInt();
                this.f708ra.seek((long) filePointer2);
                JBIG2Page jBIG2Page = (JBIG2Page) this.pages.get(Integer.valueOf(jBIG2Segment.page));
                if (jBIG2Page != null) {
                    jBIG2Page.pageBitmapWidth = readInt;
                    jBIG2Page.pageBitmapHeight = readInt2;
                    return;
                }
                throw new IllegalStateException(MessageLocalization.getComposedMessage("referring.to.widht.height.of.page.we.havent.seen.yet.1", jBIG2Segment.page));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public JBIG2Segment readHeader() throws IOException {
        int i;
        int filePointer = (int) this.f708ra.getFilePointer();
        int readInt = this.f708ra.readInt();
        JBIG2Segment jBIG2Segment = new JBIG2Segment(readInt);
        int read2 = this.f708ra.read();
        jBIG2Segment.deferredNonRetain = (read2 & 128) == 128;
        boolean z = (read2 & 64) == 64;
        jBIG2Segment.type = read2 & 63;
        int read3 = this.f708ra.read();
        int i2 = (read3 & 224) >> 5;
        boolean[] zArr = null;
        if (i2 == 7) {
            RandomAccessFileOrArray randomAccessFileOrArray = this.f708ra;
            randomAccessFileOrArray.seek(randomAccessFileOrArray.getFilePointer() - 1);
            int readInt2 = this.f708ra.readInt() & 536870911;
            boolean[] zArr2 = new boolean[(readInt2 + 1)];
            int i3 = 0;
            int i4 = 0;
            do {
                int i5 = i3 % 8;
                if (i5 == 0) {
                    i4 = this.f708ra.read();
                }
                zArr2[i3] = (((1 << i5) & i4) >> i5) == 1;
                i3++;
            } while (i3 <= readInt2);
            i2 = readInt2;
            zArr = zArr2;
        } else if (i2 <= 4) {
            zArr = new boolean[(i2 + 1)];
            int i6 = read3 & 31;
            for (int i7 = 0; i7 <= i2; i7++) {
                zArr[i7] = (((1 << i7) & i6) >> i7) == 1;
            }
        } else if (i2 == 5 || i2 == 6) {
            throw new IllegalStateException(MessageLocalization.getComposedMessage("count.of.referred.to.segments.had.bad.value.in.header.for.segment.1.starting.at.2", String.valueOf(readInt), String.valueOf(filePointer)));
        }
        jBIG2Segment.segmentRetentionFlags = zArr;
        jBIG2Segment.countOfReferredToSegments = i2;
        int[] iArr = new int[(i2 + 1)];
        for (int i8 = 1; i8 <= i2; i8++) {
            if (readInt <= 256) {
                iArr[i8] = this.f708ra.read();
            } else if (readInt <= 65536) {
                iArr[i8] = this.f708ra.readUnsignedShort();
            } else {
                iArr[i8] = (int) this.f708ra.readUnsignedInt();
            }
        }
        jBIG2Segment.referredToSegmentNumbers = iArr;
        int filePointer2 = ((int) this.f708ra.getFilePointer()) - filePointer;
        if (z) {
            i = this.f708ra.readInt();
        } else {
            i = this.f708ra.read();
        }
        if (i >= 0) {
            jBIG2Segment.page = i;
            jBIG2Segment.page_association_size = z;
            jBIG2Segment.page_association_offset = filePointer2;
            if (i > 0 && !this.pages.containsKey(Integer.valueOf(i))) {
                this.pages.put(Integer.valueOf(i), new JBIG2Page(i, this));
            }
            if (i > 0) {
                ((JBIG2Page) this.pages.get(Integer.valueOf(i))).addSegment(jBIG2Segment);
            } else {
                this.globals.add(jBIG2Segment);
            }
            jBIG2Segment.dataLength = this.f708ra.readUnsignedInt();
            int filePointer3 = (int) this.f708ra.getFilePointer();
            this.f708ra.seek((long) filePointer);
            byte[] bArr = new byte[(filePointer3 - filePointer)];
            this.f708ra.read(bArr);
            jBIG2Segment.headerData = bArr;
            return jBIG2Segment;
        }
        throw new IllegalStateException(MessageLocalization.getComposedMessage("page.1.invalid.for.segment.2.starting.at.3", String.valueOf(i), String.valueOf(readInt), String.valueOf(filePointer)));
    }

    /* access modifiers changed from: package-private */
    public void readFileHeader() throws IOException {
        this.f708ra.seek(0);
        byte[] bArr = new byte[8];
        this.f708ra.read(bArr);
        byte[] bArr2 = {-105, 74, 66, 50, BidiOrder.NSM, 10, 26, 10};
        int i = 0;
        while (i < 8) {
            if (bArr[i] == bArr2[i]) {
                i++;
            } else {
                throw new IllegalStateException(MessageLocalization.getComposedMessage("file.header.idstring.not.good.at.byte.1", i));
            }
        }
        int read2 = this.f708ra.read();
        boolean z = true;
        this.sequential = (read2 & 1) == 1;
        if ((read2 & 2) != 0) {
            z = false;
        }
        this.number_of_pages_known = z;
        if ((read2 & 252) != 0) {
            throw new IllegalStateException(MessageLocalization.getComposedMessage("file.header.flags.bits.2.7.not.0", new Object[0]));
        } else if (z) {
            this.number_of_pages = this.f708ra.readInt();
        }
    }

    public int numberOfPages() {
        return this.pages.size();
    }

    public int getPageHeight(int i) {
        return ((JBIG2Page) this.pages.get(Integer.valueOf(i))).pageBitmapHeight;
    }

    public int getPageWidth(int i) {
        return ((JBIG2Page) this.pages.get(Integer.valueOf(i))).pageBitmapWidth;
    }

    public JBIG2Page getPage(int i) {
        return (JBIG2Page) this.pages.get(Integer.valueOf(i));
    }

    public byte[] getGlobal(boolean z) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            for (JBIG2Segment jBIG2Segment : this.globals) {
                if (z) {
                    if (jBIG2Segment.type != 51) {
                        if (jBIG2Segment.type == 49) {
                        }
                    }
                }
                byteArrayOutputStream.write(jBIG2Segment.headerData);
                byteArrayOutputStream.write(jBIG2Segment.data);
            }
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (byteArrayOutputStream.size() <= 0) {
            return null;
        }
        return byteArrayOutputStream.toByteArray();
    }

    public String toString() {
        if (!this.read) {
            return "Jbig2SegmentReader in indeterminate state.";
        }
        return "Jbig2SegmentReader: number of pages: " + numberOfPages();
    }
}
