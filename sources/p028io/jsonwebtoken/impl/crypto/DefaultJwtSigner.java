package p028io.jsonwebtoken.impl.crypto;

import java.nio.charset.Charset;
import java.security.Key;
import p028io.jsonwebtoken.SignatureAlgorithm;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.p029io.Encoder;
import p028io.jsonwebtoken.p029io.Encoders;

/* renamed from: io.jsonwebtoken.impl.crypto.DefaultJwtSigner */
public class DefaultJwtSigner implements JwtSigner {
    private static final Charset US_ASCII = Charset.forName("US-ASCII");
    private final Encoder<byte[], String> base64UrlEncoder;
    private final Signer signer;

    @Deprecated
    public DefaultJwtSigner(SignatureAlgorithm signatureAlgorithm, Key key) {
        this(DefaultSignerFactory.INSTANCE, signatureAlgorithm, key, Encoders.BASE64URL);
    }

    public DefaultJwtSigner(SignatureAlgorithm signatureAlgorithm, Key key, Encoder<byte[], String> encoder) {
        this(DefaultSignerFactory.INSTANCE, signatureAlgorithm, key, encoder);
    }

    @Deprecated
    public DefaultJwtSigner(SignerFactory signerFactory, SignatureAlgorithm signatureAlgorithm, Key key) {
        this(signerFactory, signatureAlgorithm, key, Encoders.BASE64URL);
    }

    public DefaultJwtSigner(SignerFactory signerFactory, SignatureAlgorithm signatureAlgorithm, Key key, Encoder<byte[], String> encoder) {
        Assert.notNull(signerFactory, "SignerFactory argument cannot be null.");
        Assert.notNull(encoder, "Base64Url Encoder cannot be null.");
        this.base64UrlEncoder = encoder;
        this.signer = signerFactory.createSigner(signatureAlgorithm, key);
    }

    public String sign(String str) {
        return this.base64UrlEncoder.encode(this.signer.sign(str.getBytes(US_ASCII)));
    }
}
