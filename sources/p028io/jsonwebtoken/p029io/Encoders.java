package p028io.jsonwebtoken.p029io;

/* renamed from: io.jsonwebtoken.io.Encoders */
public final class Encoders {
    public static final Encoder<byte[], String> BASE64 = new ExceptionPropagatingEncoder(new Base64Encoder());
    public static final Encoder<byte[], String> BASE64URL = new ExceptionPropagatingEncoder(new Base64UrlEncoder());

    private Encoders() {
    }
}
