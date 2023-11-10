package p028io.jsonwebtoken;

/* renamed from: io.jsonwebtoken.CompressionCodec */
public interface CompressionCodec {
    byte[] compress(byte[] bArr) throws CompressionException;

    byte[] decompress(byte[] bArr) throws CompressionException;

    String getAlgorithmName();
}
