package p028io.jsonwebtoken;

import p028io.jsonwebtoken.lang.Classes;

/* renamed from: io.jsonwebtoken.CompressionCodecs */
public final class CompressionCodecs {
    public static final CompressionCodec DEFLATE = ((CompressionCodec) Classes.newInstance("io.jsonwebtoken.impl.compression.DeflateCompressionCodec"));
    public static final CompressionCodec GZIP = ((CompressionCodec) Classes.newInstance("io.jsonwebtoken.impl.compression.GzipCompressionCodec"));

    private CompressionCodecs() {
    }
}
