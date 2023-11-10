package p028io.jsonwebtoken.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import p028io.jsonwebtoken.Claims;
import p028io.jsonwebtoken.Clock;
import p028io.jsonwebtoken.CompressionCodecResolver;
import p028io.jsonwebtoken.JwtParser;
import p028io.jsonwebtoken.JwtParserBuilder;
import p028io.jsonwebtoken.SigningKeyResolver;
import p028io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import p028io.jsonwebtoken.impl.lang.Services;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.p029io.Decoder;
import p028io.jsonwebtoken.p029io.Decoders;
import p028io.jsonwebtoken.p029io.Deserializer;

/* renamed from: io.jsonwebtoken.impl.DefaultJwtParserBuilder */
public class DefaultJwtParserBuilder implements JwtParserBuilder {
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private long allowedClockSkewMillis = 0;
    private Decoder<String, byte[]> base64UrlDecoder = Decoders.BASE64URL;
    private Clock clock = DefaultClock.INSTANCE;
    private CompressionCodecResolver compressionCodecResolver = new DefaultCompressionCodecResolver();
    private Deserializer<Map<String, ?>> deserializer;
    private Claims expectedClaims = new DefaultClaims();
    private Key key;
    private byte[] keyBytes;
    private SigningKeyResolver signingKeyResolver;

    public JwtParserBuilder deserializeJsonWith(Deserializer<Map<String, ?>> deserializer2) {
        Assert.notNull(deserializer2, "deserializer cannot be null.");
        this.deserializer = deserializer2;
        return this;
    }

    public JwtParserBuilder base64UrlDecodeWith(Decoder<String, byte[]> decoder) {
        Assert.notNull(decoder, "base64UrlDecoder cannot be null.");
        this.base64UrlDecoder = decoder;
        return this;
    }

    public JwtParserBuilder requireIssuedAt(Date date) {
        this.expectedClaims.setIssuedAt(date);
        return this;
    }

    public JwtParserBuilder requireIssuer(String str) {
        this.expectedClaims.setIssuer(str);
        return this;
    }

    public JwtParserBuilder requireAudience(String str) {
        this.expectedClaims.setAudience(str);
        return this;
    }

    public JwtParserBuilder requireSubject(String str) {
        this.expectedClaims.setSubject(str);
        return this;
    }

    public JwtParserBuilder requireId(String str) {
        this.expectedClaims.setId(str);
        return this;
    }

    public JwtParserBuilder requireExpiration(Date date) {
        this.expectedClaims.setExpiration(date);
        return this;
    }

    public JwtParserBuilder requireNotBefore(Date date) {
        this.expectedClaims.setNotBefore(date);
        return this;
    }

    public JwtParserBuilder require(String str, Object obj) {
        Assert.hasText(str, "claim name cannot be null or empty.");
        Assert.notNull(obj, "The value cannot be null for claim name: " + str);
        this.expectedClaims.put(str, obj);
        return this;
    }

    public JwtParserBuilder setClock(Clock clock2) {
        Assert.notNull(clock2, "Clock instance cannot be null.");
        this.clock = clock2;
        return this;
    }

    public JwtParserBuilder setAllowedClockSkewSeconds(long j) {
        this.allowedClockSkewMillis = Math.max(0, j * 1000);
        return this;
    }

    public JwtParserBuilder setSigningKey(byte[] bArr) {
        Assert.notEmpty(bArr, "signing key cannot be null or empty.");
        this.keyBytes = bArr;
        return this;
    }

    public JwtParserBuilder setSigningKey(String str) {
        Assert.hasText(str, "signing key cannot be null or empty.");
        this.keyBytes = Decoders.BASE64.decode(str);
        return this;
    }

    public JwtParserBuilder setSigningKey(Key key2) {
        Assert.notNull(key2, "signing key cannot be null.");
        this.key = key2;
        return this;
    }

    public JwtParserBuilder setSigningKeyResolver(SigningKeyResolver signingKeyResolver2) {
        Assert.notNull(signingKeyResolver2, "SigningKeyResolver cannot be null.");
        this.signingKeyResolver = signingKeyResolver2;
        return this;
    }

    public JwtParserBuilder setCompressionCodecResolver(CompressionCodecResolver compressionCodecResolver2) {
        Assert.notNull(compressionCodecResolver2, "compressionCodecResolver cannot be null.");
        this.compressionCodecResolver = compressionCodecResolver2;
        return this;
    }

    public JwtParser build() {
        if (this.deserializer == null) {
            this.deserializer = (Deserializer) Services.loadFirst(Deserializer.class);
        }
        return new ImmutableJwtParser(new DefaultJwtParser(this.signingKeyResolver, this.key, this.keyBytes, this.clock, this.allowedClockSkewMillis, this.expectedClaims, this.base64UrlDecoder, this.deserializer, this.compressionCodecResolver));
    }
}
