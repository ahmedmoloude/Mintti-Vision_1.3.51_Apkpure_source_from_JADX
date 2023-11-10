package p028io.jsonwebtoken.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import p028io.jsonwebtoken.Claims;
import p028io.jsonwebtoken.CompressionCodec;
import p028io.jsonwebtoken.Header;
import p028io.jsonwebtoken.JwsHeader;
import p028io.jsonwebtoken.JwtBuilder;
import p028io.jsonwebtoken.JwtParser;
import p028io.jsonwebtoken.SignatureAlgorithm;
import p028io.jsonwebtoken.impl.crypto.DefaultJwtSigner;
import p028io.jsonwebtoken.impl.crypto.JwtSigner;
import p028io.jsonwebtoken.impl.lang.LegacyServices;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.lang.Collections;
import p028io.jsonwebtoken.lang.Strings;
import p028io.jsonwebtoken.p029io.Decoders;
import p028io.jsonwebtoken.p029io.Encoder;
import p028io.jsonwebtoken.p029io.Encoders;
import p028io.jsonwebtoken.p029io.SerializationException;
import p028io.jsonwebtoken.p029io.Serializer;
import p028io.jsonwebtoken.security.InvalidKeyException;

/* renamed from: io.jsonwebtoken.impl.DefaultJwtBuilder */
public class DefaultJwtBuilder implements JwtBuilder {
    private SignatureAlgorithm algorithm;
    private Encoder<byte[], String> base64UrlEncoder = Encoders.BASE64URL;
    private Claims claims;
    private CompressionCodec compressionCodec;
    private Header header;
    private Key key;
    private String payload;
    private Serializer<Map<String, ?>> serializer;

    public JwtBuilder serializeToJsonWith(Serializer<Map<String, ?>> serializer2) {
        Assert.notNull(serializer2, "Serializer cannot be null.");
        this.serializer = serializer2;
        return this;
    }

    public JwtBuilder base64UrlEncodeWith(Encoder<byte[], String> encoder) {
        Assert.notNull(encoder, "base64UrlEncoder cannot be null.");
        this.base64UrlEncoder = encoder;
        return this;
    }

    public JwtBuilder setHeader(Header header2) {
        this.header = header2;
        return this;
    }

    public JwtBuilder setHeader(Map<String, Object> map) {
        this.header = new DefaultHeader(map);
        return this;
    }

    public JwtBuilder setHeaderParams(Map<String, Object> map) {
        if (!Collections.isEmpty((Map) map)) {
            Header ensureHeader = ensureHeader();
            for (Map.Entry next : map.entrySet()) {
                ensureHeader.put(next.getKey(), next.getValue());
            }
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public Header ensureHeader() {
        if (this.header == null) {
            this.header = new DefaultHeader();
        }
        return this.header;
    }

    public JwtBuilder setHeaderParam(String str, Object obj) {
        ensureHeader().put(str, obj);
        return this;
    }

    public JwtBuilder signWith(Key key2) throws InvalidKeyException {
        Assert.notNull(key2, "Key argument cannot be null.");
        return signWith(key2, SignatureAlgorithm.forSigningKey(key2));
    }

    public JwtBuilder signWith(Key key2, SignatureAlgorithm signatureAlgorithm) throws InvalidKeyException {
        Assert.notNull(key2, "Key argument cannot be null.");
        Assert.notNull(signatureAlgorithm, "SignatureAlgorithm cannot be null.");
        signatureAlgorithm.assertValidSigningKey(key2);
        this.algorithm = signatureAlgorithm;
        this.key = key2;
        return this;
    }

    public JwtBuilder signWith(SignatureAlgorithm signatureAlgorithm, byte[] bArr) throws InvalidKeyException {
        Assert.notNull(signatureAlgorithm, "SignatureAlgorithm cannot be null.");
        Assert.notEmpty(bArr, "secret key byte array cannot be null or empty.");
        Assert.isTrue(signatureAlgorithm.isHmac(), "Key bytes may only be specified for HMAC signatures.  If using RSA or Elliptic Curve, use the signWith(SignatureAlgorithm, Key) method instead.");
        return signWith((Key) new SecretKeySpec(bArr, signatureAlgorithm.getJcaName()), signatureAlgorithm);
    }

    public JwtBuilder signWith(SignatureAlgorithm signatureAlgorithm, String str) throws InvalidKeyException {
        Assert.hasText(str, "base64-encoded secret key cannot be null or empty.");
        Assert.isTrue(signatureAlgorithm.isHmac(), "Base64-encoded key bytes may only be specified for HMAC signatures.  If using RSA or Elliptic Curve, use the signWith(SignatureAlgorithm, Key) method instead.");
        return signWith(signatureAlgorithm, Decoders.BASE64.decode(str));
    }

    public JwtBuilder signWith(SignatureAlgorithm signatureAlgorithm, Key key2) {
        return signWith(key2, signatureAlgorithm);
    }

    public JwtBuilder compressWith(CompressionCodec compressionCodec2) {
        Assert.notNull(compressionCodec2, "compressionCodec cannot be null");
        this.compressionCodec = compressionCodec2;
        return this;
    }

    public JwtBuilder setPayload(String str) {
        this.payload = str;
        return this;
    }

    /* access modifiers changed from: protected */
    public Claims ensureClaims() {
        if (this.claims == null) {
            this.claims = new DefaultClaims();
        }
        return this.claims;
    }

    public JwtBuilder setClaims(Claims claims2) {
        this.claims = claims2;
        return this;
    }

    public JwtBuilder setClaims(Map<String, ?> map) {
        this.claims = new DefaultClaims(map);
        return this;
    }

    public JwtBuilder addClaims(Map<String, Object> map) {
        ensureClaims().putAll(map);
        return this;
    }

    public JwtBuilder setIssuer(String str) {
        if (Strings.hasText(str)) {
            ensureClaims().setIssuer(str);
        } else {
            Claims claims2 = this.claims;
            if (claims2 != null) {
                claims2.setIssuer(str);
            }
        }
        return this;
    }

    public JwtBuilder setSubject(String str) {
        if (Strings.hasText(str)) {
            ensureClaims().setSubject(str);
        } else {
            Claims claims2 = this.claims;
            if (claims2 != null) {
                claims2.setSubject(str);
            }
        }
        return this;
    }

    public JwtBuilder setAudience(String str) {
        if (Strings.hasText(str)) {
            ensureClaims().setAudience(str);
        } else {
            Claims claims2 = this.claims;
            if (claims2 != null) {
                claims2.setAudience(str);
            }
        }
        return this;
    }

    public JwtBuilder setExpiration(Date date) {
        if (date != null) {
            ensureClaims().setExpiration(date);
        } else {
            Claims claims2 = this.claims;
            if (claims2 != null) {
                claims2.setExpiration(date);
            }
        }
        return this;
    }

    public JwtBuilder setNotBefore(Date date) {
        if (date != null) {
            ensureClaims().setNotBefore(date);
        } else {
            Claims claims2 = this.claims;
            if (claims2 != null) {
                claims2.setNotBefore(date);
            }
        }
        return this;
    }

    public JwtBuilder setIssuedAt(Date date) {
        if (date != null) {
            ensureClaims().setIssuedAt(date);
        } else {
            Claims claims2 = this.claims;
            if (claims2 != null) {
                claims2.setIssuedAt(date);
            }
        }
        return this;
    }

    public JwtBuilder setId(String str) {
        if (Strings.hasText(str)) {
            ensureClaims().setId(str);
        } else {
            Claims claims2 = this.claims;
            if (claims2 != null) {
                claims2.setId(str);
            }
        }
        return this;
    }

    public JwtBuilder claim(String str, Object obj) {
        Assert.hasText(str, "Claim property name cannot be null or empty.");
        Claims claims2 = this.claims;
        if (claims2 == null) {
            if (obj != null) {
                ensureClaims().put(str, obj);
            }
        } else if (obj == null) {
            claims2.remove(str);
        } else {
            claims2.put(str, obj);
        }
        return this;
    }

    public String compact() {
        JwsHeader jwsHeader;
        if (this.serializer == null) {
            this.serializer = (Serializer) LegacyServices.loadFirst(Serializer.class);
        }
        if (this.payload == null && Collections.isEmpty((Map) this.claims)) {
            throw new IllegalStateException("Either 'payload' or 'claims' must be specified.");
        } else if (this.payload == null || Collections.isEmpty((Map) this.claims)) {
            Header ensureHeader = ensureHeader();
            if (ensureHeader instanceof JwsHeader) {
                jwsHeader = (JwsHeader) ensureHeader;
            } else {
                jwsHeader = new DefaultJwsHeader(ensureHeader);
            }
            if (this.key != null) {
                jwsHeader.setAlgorithm(this.algorithm.getValue());
            } else {
                jwsHeader.setAlgorithm(SignatureAlgorithm.NONE.getValue());
            }
            CompressionCodec compressionCodec2 = this.compressionCodec;
            if (compressionCodec2 != null) {
                jwsHeader.setCompressionAlgorithm(compressionCodec2.getAlgorithmName());
            }
            String base64UrlEncode = base64UrlEncode(jwsHeader, "Unable to serialize header to json.");
            try {
                String str = this.payload;
                byte[] bytes = str != null ? str.getBytes(Strings.UTF_8) : toJson(this.claims);
                CompressionCodec compressionCodec3 = this.compressionCodec;
                if (compressionCodec3 != null) {
                    bytes = compressionCodec3.compress(bytes);
                }
                String str2 = base64UrlEncode + JwtParser.SEPARATOR_CHAR + this.base64UrlEncoder.encode(bytes);
                Key key2 = this.key;
                if (key2 != null) {
                    return str2 + JwtParser.SEPARATOR_CHAR + createSigner(this.algorithm, key2).sign(str2);
                }
                return str2 + JwtParser.SEPARATOR_CHAR;
            } catch (SerializationException e) {
                throw new IllegalArgumentException("Unable to serialize claims object to json: " + e.getMessage(), e);
            }
        } else {
            throw new IllegalStateException("Both 'payload' and 'claims' cannot both be specified. Choose either one.");
        }
    }

    /* access modifiers changed from: protected */
    public JwtSigner createSigner(SignatureAlgorithm signatureAlgorithm, Key key2) {
        return new DefaultJwtSigner(signatureAlgorithm, key2, this.base64UrlEncoder);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public String base64UrlEncode(Object obj, String str) {
        Assert.isInstanceOf(Map.class, obj, "object argument must be a map.");
        try {
            return this.base64UrlEncoder.encode(toJson((Map) obj));
        } catch (SerializationException e) {
            throw new IllegalStateException(str, e);
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public byte[] toJson(Object obj) throws SerializationException {
        Assert.isInstanceOf(Map.class, obj, "object argument must be a map.");
        return this.serializer.serialize((Map) obj);
    }
}
