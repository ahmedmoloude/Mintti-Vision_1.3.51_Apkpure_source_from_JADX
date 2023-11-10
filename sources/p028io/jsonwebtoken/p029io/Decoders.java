package p028io.jsonwebtoken.p029io;

/* renamed from: io.jsonwebtoken.io.Decoders */
public final class Decoders {
    public static final Decoder<String, byte[]> BASE64 = new ExceptionPropagatingDecoder(new Base64Decoder());
    public static final Decoder<String, byte[]> BASE64URL = new ExceptionPropagatingDecoder(new Base64UrlDecoder());

    private Decoders() {
    }
}
