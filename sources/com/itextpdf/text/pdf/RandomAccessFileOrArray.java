package com.itextpdf.text.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.p018io.IndependentRandomAccessSource;
import com.itextpdf.text.p018io.RandomAccessSource;
import com.itextpdf.text.p018io.RandomAccessSourceFactory;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import kotlin.UByte;

public class RandomAccessFileOrArray implements DataInput {
    private byte back;
    private final RandomAccessSource byteSource;
    private long byteSourcePosition;
    private boolean isBack;

    @Deprecated
    public RandomAccessFileOrArray(String str) throws IOException {
        this(new RandomAccessSourceFactory().setForceRead(false).setUsePlainRandomAccess(Document.plainRandomAccess).createBestSource(str));
    }

    @Deprecated
    public RandomAccessFileOrArray(RandomAccessFileOrArray randomAccessFileOrArray) {
        this((RandomAccessSource) new IndependentRandomAccessSource(randomAccessFileOrArray.byteSource));
    }

    public RandomAccessFileOrArray createView() {
        return new RandomAccessFileOrArray((RandomAccessSource) new IndependentRandomAccessSource(this.byteSource));
    }

    public RandomAccessSource createSourceView() {
        return new IndependentRandomAccessSource(this.byteSource);
    }

    public RandomAccessFileOrArray(RandomAccessSource randomAccessSource) {
        this.isBack = false;
        this.byteSource = randomAccessSource;
    }

    @Deprecated
    public RandomAccessFileOrArray(String str, boolean z, boolean z2) throws IOException {
        this(new RandomAccessSourceFactory().setForceRead(z).setUsePlainRandomAccess(z2).createBestSource(str));
    }

    @Deprecated
    public RandomAccessFileOrArray(URL url) throws IOException {
        this(new RandomAccessSourceFactory().createSource(url));
    }

    @Deprecated
    public RandomAccessFileOrArray(InputStream inputStream) throws IOException {
        this(new RandomAccessSourceFactory().createSource(inputStream));
    }

    @Deprecated
    public RandomAccessFileOrArray(byte[] bArr) {
        this(new RandomAccessSourceFactory().createSource(bArr));
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public RandomAccessSource getByteSource() {
        return this.byteSource;
    }

    public void pushBack(byte b) {
        this.back = b;
        this.isBack = true;
    }

    public int read() throws IOException {
        if (this.isBack) {
            this.isBack = false;
            return this.back & UByte.MAX_VALUE;
        }
        RandomAccessSource randomAccessSource = this.byteSource;
        long j = this.byteSourcePosition;
        this.byteSourcePosition = 1 + j;
        return randomAccessSource.get(j);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        int i4;
        int i5;
        int i6 = 0;
        if (i2 == 0) {
            return 0;
        }
        if (!this.isBack || i2 <= 0) {
            i4 = i;
            i3 = i2;
        } else {
            this.isBack = false;
            bArr[i] = this.back;
            i3 = i2 - 1;
            i4 = i + 1;
            i6 = 1;
        }
        if (i3 > 0 && (i5 = this.byteSource.get(this.byteSourcePosition, bArr, i4, i3)) > 0) {
            i6 += i5;
            this.byteSourcePosition += (long) i5;
        }
        if (i6 == 0) {
            return -1;
        }
        return i6;
    }

    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr) throws IOException {
        readFully(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        do {
            int read = read(bArr, i + i3, i2 - i3);
            if (read >= 0) {
                i3 += read;
            } else {
                throw new EOFException();
            }
        } while (i3 < i2);
    }

    public long skip(long j) throws IOException {
        if (j <= 0) {
            return 0;
        }
        int i = 0;
        if (this.isBack) {
            this.isBack = false;
            if (j == 1) {
                return 1;
            }
            j--;
            i = 1;
        }
        long filePointer = getFilePointer();
        long length = length();
        long j2 = j + filePointer;
        if (j2 <= length) {
            length = j2;
        }
        seek(length);
        return (length - filePointer) + ((long) i);
    }

    public int skipBytes(int i) throws IOException {
        return (int) skip((long) i);
    }

    @Deprecated
    public void reOpen() throws IOException {
        seek(0);
    }

    public void close() throws IOException {
        this.isBack = false;
        this.byteSource.close();
    }

    public long length() throws IOException {
        return this.byteSource.length();
    }

    public void seek(long j) throws IOException {
        this.byteSourcePosition = j;
        this.isBack = false;
    }

    public long getFilePointer() throws IOException {
        return this.byteSourcePosition - (this.isBack ? 1 : 0);
    }

    public boolean readBoolean() throws IOException {
        int read = read();
        if (read >= 0) {
            return read != 0;
        }
        throw new EOFException();
    }

    public byte readByte() throws IOException {
        int read = read();
        if (read >= 0) {
            return (byte) read;
        }
        throw new EOFException();
    }

    public int readUnsignedByte() throws IOException {
        int read = read();
        if (read >= 0) {
            return read;
        }
        throw new EOFException();
    }

    public short readShort() throws IOException {
        int read = read();
        int read2 = read();
        if ((read | read2) >= 0) {
            return (short) ((read << 8) + read2);
        }
        throw new EOFException();
    }

    public final short readShortLE() throws IOException {
        int read = read();
        int read2 = read();
        if ((read | read2) >= 0) {
            return (short) ((read2 << 8) + (read << 0));
        }
        throw new EOFException();
    }

    public int readUnsignedShort() throws IOException {
        int read = read();
        int read2 = read();
        if ((read | read2) >= 0) {
            return (read << 8) + read2;
        }
        throw new EOFException();
    }

    public final int readUnsignedShortLE() throws IOException {
        int read = read();
        int read2 = read();
        if ((read | read2) >= 0) {
            return (read2 << 8) + (read << 0);
        }
        throw new EOFException();
    }

    public char readChar() throws IOException {
        int read = read();
        int read2 = read();
        if ((read | read2) >= 0) {
            return (char) ((read << 8) + read2);
        }
        throw new EOFException();
    }

    public final char readCharLE() throws IOException {
        int read = read();
        int read2 = read();
        if ((read | read2) >= 0) {
            return (char) ((read2 << 8) + (read << 0));
        }
        throw new EOFException();
    }

    public int readInt() throws IOException {
        int read = read();
        int read2 = read();
        int read3 = read();
        int read4 = read();
        if ((read | read2 | read3 | read4) >= 0) {
            return (read << 24) + (read2 << 16) + (read3 << 8) + read4;
        }
        throw new EOFException();
    }

    public final int readIntLE() throws IOException {
        int read = read();
        int read2 = read();
        int read3 = read();
        int read4 = read();
        if ((read | read2 | read3 | read4) >= 0) {
            return (read4 << 24) + (read3 << 16) + (read2 << 8) + (read << 0);
        }
        throw new EOFException();
    }

    public final long readUnsignedInt() throws IOException {
        long read = (long) read();
        long read2 = (long) read();
        long read3 = (long) read();
        long read4 = (long) read();
        if ((read | read2 | read3 | read4) >= 0) {
            return (read << 24) + (read2 << 16) + (read3 << 8) + (read4 << 0);
        }
        throw new EOFException();
    }

    public final long readUnsignedIntLE() throws IOException {
        long read = (long) read();
        long read2 = (long) read();
        long read3 = (long) read();
        long read4 = (long) read();
        if ((read | read2 | read3 | read4) >= 0) {
            return (read4 << 24) + (read3 << 16) + (read2 << 8) + (read << 0);
        }
        throw new EOFException();
    }

    public long readLong() throws IOException {
        return (((long) readInt()) << 32) + (((long) readInt()) & 4294967295L);
    }

    public final long readLongLE() throws IOException {
        return (((long) readIntLE()) << 32) + (((long) readIntLE()) & 4294967295L);
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public final float readFloatLE() throws IOException {
        return Float.intBitsToFloat(readIntLE());
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public final double readDoubleLE() throws IOException {
        return Double.longBitsToDouble(readLongLE());
    }

    public String readLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        int i = -1;
        while (!z) {
            i = read();
            if (!(i == -1 || i == 10)) {
                if (i != 13) {
                    sb.append((char) i);
                } else {
                    long filePointer = getFilePointer();
                    if (read() != 10) {
                        seek(filePointer);
                    }
                }
            }
            z = true;
        }
        if (i == -1 && sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    public String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }

    public String readString(int i, String str) throws IOException {
        byte[] bArr = new byte[i];
        readFully(bArr);
        try {
            return new String(bArr, str);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }
}
