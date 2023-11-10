package p028io.jsonwebtoken.impl.compression;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import p028io.jsonwebtoken.CompressionCodec;
import p028io.jsonwebtoken.impl.compression.AbstractCompressionCodec;

/* renamed from: io.jsonwebtoken.impl.compression.GzipCompressionCodec */
public class GzipCompressionCodec extends AbstractCompressionCodec implements CompressionCodec {
    private static final String GZIP = "GZIP";
    private static final AbstractCompressionCodec.StreamWrapper WRAPPER = new AbstractCompressionCodec.StreamWrapper() {
        public OutputStream wrap(OutputStream outputStream) throws IOException {
            return new GZIPOutputStream(outputStream);
        }
    };

    public String getAlgorithmName() {
        return GZIP;
    }

    /* access modifiers changed from: protected */
    public byte[] doCompress(byte[] bArr) throws IOException {
        return writeAndClose(bArr, WRAPPER);
    }

    /* access modifiers changed from: protected */
    public byte[] doDecompress(byte[] bArr) throws IOException {
        return readAndClose(new GZIPInputStream(new ByteArrayInputStream(bArr)));
    }
}
