package com.itextpdf.text.p018io;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import kotlin.UByte;

/* renamed from: com.itextpdf.text.io.ByteBufferRandomAccessSource */
class ByteBufferRandomAccessSource implements RandomAccessSource {
    private final ByteBuffer byteBuffer;

    public ByteBufferRandomAccessSource(ByteBuffer byteBuffer2) {
        this.byteBuffer = byteBuffer2;
    }

    public int get(long j) throws IOException {
        if (j <= 2147483647L) {
            try {
                if (j >= ((long) this.byteBuffer.limit())) {
                    return -1;
                }
                return this.byteBuffer.get((int) j) & UByte.MAX_VALUE;
            } catch (BufferUnderflowException unused) {
                return -1;
            }
        } else {
            throw new IllegalArgumentException("Position must be less than Integer.MAX_VALUE");
        }
    }

    public int get(long j, byte[] bArr, int i, int i2) throws IOException {
        if (j > 2147483647L) {
            throw new IllegalArgumentException("Position must be less than Integer.MAX_VALUE");
        } else if (j >= ((long) this.byteBuffer.limit())) {
            return -1;
        } else {
            this.byteBuffer.position((int) j);
            int min = Math.min(i2, this.byteBuffer.remaining());
            this.byteBuffer.get(bArr, i, min);
            return min;
        }
    }

    public long length() {
        return (long) this.byteBuffer.limit();
    }

    public void close() throws IOException {
        clean(this.byteBuffer);
    }

    private static boolean clean(final ByteBuffer byteBuffer2) {
        if (byteBuffer2 == null || !byteBuffer2.isDirect()) {
            return false;
        }
        return ((Boolean) AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
            public Boolean run() {
                Boolean bool = Boolean.FALSE;
                try {
                    Method method = byteBuffer2.getClass().getMethod("cleaner", (Class[]) null);
                    method.setAccessible(true);
                    Object invoke = method.invoke(byteBuffer2, (Object[]) null);
                    invoke.getClass().getMethod("clean", (Class[]) null).invoke(invoke, (Object[]) null);
                    return Boolean.TRUE;
                } catch (Exception unused) {
                    return bool;
                }
            }
        })).booleanValue();
    }
}
