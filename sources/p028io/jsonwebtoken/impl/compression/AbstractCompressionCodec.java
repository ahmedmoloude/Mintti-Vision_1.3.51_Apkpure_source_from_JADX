package p028io.jsonwebtoken.impl.compression;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import p028io.jsonwebtoken.CompressionCodec;
import p028io.jsonwebtoken.CompressionException;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.lang.Objects;

/* renamed from: io.jsonwebtoken.impl.compression.AbstractCompressionCodec */
public abstract class AbstractCompressionCodec implements CompressionCodec {

    /* renamed from: io.jsonwebtoken.impl.compression.AbstractCompressionCodec$StreamWrapper */
    interface StreamWrapper {
        OutputStream wrap(OutputStream outputStream) throws IOException;
    }

    /* access modifiers changed from: protected */
    public abstract byte[] doCompress(byte[] bArr) throws IOException;

    /* access modifiers changed from: protected */
    public abstract byte[] doDecompress(byte[] bArr) throws IOException;

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public byte[] readAndClose(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[512];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        try {
            int read = inputStream.read(bArr);
            while (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
                read = inputStream.read(bArr);
            }
            Objects.nullSafeClose(inputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th) {
            Objects.nullSafeClose(inputStream);
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public byte[] writeAndClose(byte[] bArr, StreamWrapper streamWrapper) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        OutputStream wrap = streamWrapper.wrap(byteArrayOutputStream);
        try {
            wrap.write(bArr);
            wrap.flush();
            Objects.nullSafeClose(wrap);
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th) {
            Objects.nullSafeClose(wrap);
            throw th;
        }
    }

    public final byte[] compress(byte[] bArr) {
        Assert.notNull(bArr, "payload cannot be null.");
        try {
            return doCompress(bArr);
        } catch (IOException e) {
            throw new CompressionException("Unable to compress payload.", e);
        }
    }

    public final byte[] decompress(byte[] bArr) {
        Assert.notNull(bArr, "compressed bytes cannot be null.");
        try {
            return doDecompress(bArr);
        } catch (IOException e) {
            throw new CompressionException("Unable to decompress bytes.", e);
        }
    }
}
