package p028io.jsonwebtoken;

/* renamed from: io.jsonwebtoken.CompressionCodecResolver */
public interface CompressionCodecResolver {
    CompressionCodec resolveCompressionCodec(Header header) throws CompressionException;
}
