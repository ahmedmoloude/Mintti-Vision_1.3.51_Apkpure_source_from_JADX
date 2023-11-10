package com.itextpdf.text.p018io;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: com.itextpdf.text.io.StreamUtil */
public final class StreamUtil {
    private StreamUtil() {
    }

    public static byte[] inputStreamToArray(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[8192];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int read = inputStream.read(bArr);
            if (read < 1) {
                byteArrayOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    public static void CopyBytes(RandomAccessSource randomAccessSource, long j, long j2, OutputStream outputStream) throws IOException {
        if (j2 > 0) {
            byte[] bArr = new byte[8192];
            while (j2 > 0) {
                long j3 = (long) randomAccessSource.get(j, bArr, 0, (int) Math.min((long) 8192, j2));
                if (j3 > 0) {
                    outputStream.write(bArr, 0, (int) j3);
                    j += j3;
                    j2 -= j3;
                } else {
                    throw new EOFException();
                }
            }
        }
    }

    public static InputStream getResourceStream(String str) {
        return getResourceStream(str, (ClassLoader) null);
    }

    public static InputStream getResourceStream(String str, ClassLoader classLoader) {
        if (str.startsWith("/")) {
            str = str.substring(1);
        }
        InputStream inputStream = null;
        if (classLoader != null && (inputStream = classLoader.getResourceAsStream(str)) != null) {
            return inputStream;
        }
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null) {
                inputStream = contextClassLoader.getResourceAsStream(str);
            }
        } catch (Throwable unused) {
        }
        if (inputStream == null) {
            inputStream = StreamUtil.class.getResourceAsStream("/" + str);
        }
        return inputStream == null ? ClassLoader.getSystemResourceAsStream(str) : inputStream;
    }
}
