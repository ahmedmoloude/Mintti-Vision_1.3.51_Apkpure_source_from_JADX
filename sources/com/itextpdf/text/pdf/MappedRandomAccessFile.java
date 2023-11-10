package com.itextpdf.text.pdf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import kotlin.UByte;

public class MappedRandomAccessFile {
    private static final int BUFSIZE = 1073741824;
    private FileChannel channel = null;
    private MappedByteBuffer[] mappedBuffers;
    private long pos;
    private long size;

    public MappedRandomAccessFile(String str, String str2) throws FileNotFoundException, IOException {
        if (str2.equals("rw")) {
            init(new RandomAccessFile(str, str2).getChannel(), FileChannel.MapMode.READ_WRITE);
        } else {
            init(new FileInputStream(str).getChannel(), FileChannel.MapMode.READ_ONLY);
        }
    }

    private void init(FileChannel fileChannel, FileChannel.MapMode mapMode) throws IOException {
        this.channel = fileChannel;
        long size2 = fileChannel.size();
        this.size = size2;
        this.pos = 0;
        int i = ((int) (size2 / 1073741824)) + (size2 % 1073741824 == 0 ? 0 : 1);
        this.mappedBuffers = new MappedByteBuffer[i];
        long j = 0;
        int i2 = 0;
        while (true) {
            try {
                long j2 = this.size;
                if (j >= j2) {
                    break;
                }
                this.mappedBuffers[i2] = fileChannel.map(mapMode, j, Math.min(j2 - j, 1073741824));
                this.mappedBuffers[i2].load();
                i2++;
                j += 1073741824;
            } catch (IOException e) {
                close();
                throw e;
            } catch (RuntimeException e2) {
                close();
                throw e2;
            }
        }
        if (i2 != i) {
            throw new Error("Should never happen - " + i2 + " != " + i);
        }
    }

    public FileChannel getChannel() {
        return this.channel;
    }

    public int read() {
        try {
            long j = this.pos;
            int i = (int) (j / 1073741824);
            int i2 = (int) (j % 1073741824);
            MappedByteBuffer[] mappedByteBufferArr = this.mappedBuffers;
            if (i >= mappedByteBufferArr.length || i2 >= mappedByteBufferArr[i].limit()) {
                return -1;
            }
            byte b = this.mappedBuffers[i].get(i2);
            this.pos++;
            return b & UByte.MAX_VALUE;
        } catch (BufferUnderflowException unused) {
            return -1;
        }
    }

    public int read(byte[] bArr, int i, int i2) {
        long j = this.pos;
        int i3 = (int) (j / 1073741824);
        int i4 = (int) (j % 1073741824);
        int i5 = 0;
        while (i5 < i2) {
            MappedByteBuffer[] mappedByteBufferArr = this.mappedBuffers;
            if (i3 >= mappedByteBufferArr.length) {
                break;
            }
            MappedByteBuffer mappedByteBuffer = mappedByteBufferArr[i3];
            if (i4 > mappedByteBuffer.limit()) {
                break;
            }
            mappedByteBuffer.position(i4);
            int min = Math.min(i2 - i5, mappedByteBuffer.remaining());
            mappedByteBuffer.get(bArr, i, min);
            i += min;
            this.pos += (long) min;
            i5 += min;
            i3++;
            i4 = 0;
        }
        if (i5 == 0) {
            return -1;
        }
        return i5;
    }

    public long getFilePointer() {
        return this.pos;
    }

    public void seek(long j) {
        this.pos = j;
    }

    public long length() {
        return this.size;
    }

    public void close() throws IOException {
        int i = 0;
        while (true) {
            MappedByteBuffer[] mappedByteBufferArr = this.mappedBuffers;
            if (i >= mappedByteBufferArr.length) {
                break;
            }
            if (mappedByteBufferArr[i] != null) {
                clean(mappedByteBufferArr[i]);
                this.mappedBuffers[i] = null;
            }
            i++;
        }
        FileChannel fileChannel = this.channel;
        if (fileChannel != null) {
            fileChannel.close();
        }
        this.channel = null;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        close();
        super.finalize();
    }

    public static boolean clean(final ByteBuffer byteBuffer) {
        if (byteBuffer == null || !byteBuffer.isDirect()) {
            return false;
        }
        return ((Boolean) AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
            public Boolean run() {
                Boolean bool = Boolean.FALSE;
                try {
                    Method method = byteBuffer.getClass().getMethod("cleaner", (Class[]) null);
                    method.setAccessible(true);
                    Object invoke = method.invoke(byteBuffer, (Object[]) null);
                    invoke.getClass().getMethod("clean", (Class[]) null).invoke(invoke, (Object[]) null);
                    return Boolean.TRUE;
                } catch (Exception unused) {
                    return bool;
                }
            }
        })).booleanValue();
    }
}
