package p028io.jsonwebtoken.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import p028io.jsonwebtoken.ClaimJwtException;
import p028io.jsonwebtoken.Claims;
import p028io.jsonwebtoken.Clock;
import p028io.jsonwebtoken.CompressionCodec;
import p028io.jsonwebtoken.CompressionCodecResolver;
import p028io.jsonwebtoken.ExpiredJwtException;
import p028io.jsonwebtoken.Header;
import p028io.jsonwebtoken.IncorrectClaimException;
import p028io.jsonwebtoken.InvalidClaimException;
import p028io.jsonwebtoken.Jws;
import p028io.jsonwebtoken.JwsHeader;
import p028io.jsonwebtoken.Jwt;
import p028io.jsonwebtoken.JwtHandler;
import p028io.jsonwebtoken.JwtHandlerAdapter;
import p028io.jsonwebtoken.JwtParser;
import p028io.jsonwebtoken.MalformedJwtException;
import p028io.jsonwebtoken.MissingClaimException;
import p028io.jsonwebtoken.PrematureJwtException;
import p028io.jsonwebtoken.SignatureAlgorithm;
import p028io.jsonwebtoken.SigningKeyResolver;
import p028io.jsonwebtoken.UnsupportedJwtException;
import p028io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import p028io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import p028io.jsonwebtoken.impl.crypto.JwtSignatureValidator;
import p028io.jsonwebtoken.impl.lang.LegacyServices;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.lang.DateFormats;
import p028io.jsonwebtoken.lang.Objects;
import p028io.jsonwebtoken.lang.Strings;
import p028io.jsonwebtoken.p029io.Decoder;
import p028io.jsonwebtoken.p029io.Decoders;
import p028io.jsonwebtoken.p029io.DeserializationException;
import p028io.jsonwebtoken.p029io.Deserializer;
import p028io.jsonwebtoken.security.InvalidKeyException;
import p028io.jsonwebtoken.security.SignatureException;
import p028io.jsonwebtoken.security.WeakKeyException;

/* renamed from: io.jsonwebtoken.impl.DefaultJwtParser */
public class DefaultJwtParser implements JwtParser {
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

    @Deprecated
    public DefaultJwtParser() {
    }

    DefaultJwtParser(SigningKeyResolver signingKeyResolver2, Key key2, byte[] bArr, Clock clock2, long j, Claims claims, Decoder<String, byte[]> decoder, Deserializer<Map<String, ?>> deserializer2, CompressionCodecResolver compressionCodecResolver2) {
        this.signingKeyResolver = signingKeyResolver2;
        this.key = key2;
        this.keyBytes = bArr;
        this.clock = clock2;
        this.allowedClockSkewMillis = j;
        this.expectedClaims = claims;
        this.base64UrlDecoder = decoder;
        this.deserializer = deserializer2;
        this.compressionCodecResolver = compressionCodecResolver2;
    }

    public JwtParser deserializeJsonWith(Deserializer<Map<String, ?>> deserializer2) {
        Assert.notNull(deserializer2, "deserializer cannot be null.");
        this.deserializer = deserializer2;
        return this;
    }

    public JwtParser base64UrlDecodeWith(Decoder<String, byte[]> decoder) {
        Assert.notNull(decoder, "base64UrlDecoder cannot be null.");
        this.base64UrlDecoder = decoder;
        return this;
    }

    public JwtParser requireIssuedAt(Date date) {
        this.expectedClaims.setIssuedAt(date);
        return this;
    }

    public JwtParser requireIssuer(String str) {
        this.expectedClaims.setIssuer(str);
        return this;
    }

    public JwtParser requireAudience(String str) {
        this.expectedClaims.setAudience(str);
        return this;
    }

    public JwtParser requireSubject(String str) {
        this.expectedClaims.setSubject(str);
        return this;
    }

    public JwtParser requireId(String str) {
        this.expectedClaims.setId(str);
        return this;
    }

    public JwtParser requireExpiration(Date date) {
        this.expectedClaims.setExpiration(date);
        return this;
    }

    public JwtParser requireNotBefore(Date date) {
        this.expectedClaims.setNotBefore(date);
        return this;
    }

    public JwtParser require(String str, Object obj) {
        Assert.hasText(str, "claim name cannot be null or empty.");
        Assert.notNull(obj, "The value cannot be null for claim name: " + str);
        this.expectedClaims.put(str, obj);
        return this;
    }

    public JwtParser setClock(Clock clock2) {
        Assert.notNull(clock2, "Clock instance cannot be null.");
        this.clock = clock2;
        return this;
    }

    public JwtParser setAllowedClockSkewSeconds(long j) {
        this.allowedClockSkewMillis = Math.max(0, j * 1000);
        return this;
    }

    public JwtParser setSigningKey(byte[] bArr) {
        Assert.notEmpty(bArr, "signing key cannot be null or empty.");
        this.keyBytes = bArr;
        return this;
    }

    public JwtParser setSigningKey(String str) {
        Assert.hasText(str, "signing key cannot be null or empty.");
        this.keyBytes = Decoders.BASE64.decode(str);
        return this;
    }

    public JwtParser setSigningKey(Key key2) {
        Assert.notNull(key2, "signing key cannot be null.");
        this.key = key2;
        return this;
    }

    public JwtParser setSigningKeyResolver(SigningKeyResolver signingKeyResolver2) {
        Assert.notNull(signingKeyResolver2, "SigningKeyResolver cannot be null.");
        this.signingKeyResolver = signingKeyResolver2;
        return this;
    }

    public JwtParser setCompressionCodecResolver(CompressionCodecResolver compressionCodecResolver2) {
        Assert.notNull(compressionCodecResolver2, "compressionCodecResolver cannot be null.");
        this.compressionCodecResolver = compressionCodecResolver2;
        return this;
    }

    public boolean isSigned(String str) {
        if (str == null) {
            return false;
        }
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (i2 != 2) {
                if (charAt == '.') {
                    i2++;
                }
                i++;
            } else if (Character.isWhitespace(charAt) || charAt == '.') {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public Jwt parse(String str) throws ExpiredJwtException, MalformedJwtException, SignatureException {
        boolean z;
        String str2;
        JwsHeader jwsHeader;
        CompressionCodec compressionCodec;
        String str3;
        DefaultClaims defaultClaims;
        SigningKeyResolver signingKeyResolver2;
        Key key2;
        String str4 = str;
        if (this.deserializer == null) {
            this.deserializer = (Deserializer) LegacyServices.loadFirst(Deserializer.class);
        }
        Assert.hasText(str4, "JWT String argument cannot be null or empty.");
        StringBuilder sb = new StringBuilder(128);
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        SignatureAlgorithm signatureAlgorithm = null;
        String str5 = null;
        String str6 = null;
        int i = 0;
        int i2 = 0;
        while (true) {
            z = true;
            if (i >= length) {
                break;
            }
            char c = charArray[i];
            if (c == '.') {
                CharSequence clean = Strings.clean((CharSequence) sb);
                String charSequence = clean != null ? clean.toString() : null;
                if (i2 == 0) {
                    str6 = charSequence;
                } else if (i2 == 1) {
                    str5 = charSequence;
                }
                i2++;
                sb.setLength(0);
            } else {
                sb.append(c);
            }
            i++;
        }
        if (i2 == 2) {
            if (sb.length() > 0) {
                str2 = sb.toString();
            } else {
                str2 = null;
            }
            if (str5 != null) {
                if (str6 != null) {
                    Map<String, ?> readValue = readValue(new String(this.base64UrlDecoder.decode(str6), Strings.UTF_8));
                    if (str2 != null) {
                        jwsHeader = new DefaultJwsHeader(readValue);
                    } else {
                        jwsHeader = new DefaultHeader(readValue);
                    }
                    compressionCodec = this.compressionCodecResolver.resolveCompressionCodec(jwsHeader);
                } else {
                    compressionCodec = null;
                    jwsHeader = null;
                }
                byte[] decode = this.base64UrlDecoder.decode(str5);
                if (compressionCodec != null) {
                    decode = compressionCodec.decompress(decode);
                }
                String str7 = new String(decode, Strings.UTF_8);
                DefaultClaims defaultClaims2 = (str7.charAt(0) == '{' && str7.charAt(str7.length() - 1) == '}') ? new DefaultClaims(readValue(str7)) : null;
                if (str2 != null) {
                    JwsHeader jwsHeader2 = jwsHeader;
                    if (jwsHeader != null) {
                        String algorithm = jwsHeader2.getAlgorithm();
                        if (Strings.hasText(algorithm)) {
                            signatureAlgorithm = SignatureAlgorithm.forName(algorithm);
                        }
                    }
                    if (signatureAlgorithm == null || signatureAlgorithm == SignatureAlgorithm.NONE) {
                        throw new MalformedJwtException("JWT string has a digest/signature, but the header does not reference a valid signature algorithm.");
                    }
                    Key key3 = this.key;
                    if (key3 != null && this.keyBytes != null) {
                        throw new IllegalStateException("A key object and key bytes cannot both be specified. Choose either.");
                    } else if ((key3 == null && this.keyBytes == null) || this.signingKeyResolver == null) {
                        if (key3 == null) {
                            byte[] bArr = this.keyBytes;
                            if (Objects.isEmpty(bArr) && (signingKeyResolver2 = this.signingKeyResolver) != null) {
                                if (defaultClaims2 != null) {
                                    key2 = signingKeyResolver2.resolveSigningKey(jwsHeader2, (Claims) defaultClaims2);
                                } else {
                                    key2 = signingKeyResolver2.resolveSigningKey(jwsHeader2, str7);
                                }
                                key3 = key2;
                            }
                            if (!Objects.isEmpty(bArr)) {
                                Assert.isTrue(signatureAlgorithm.isHmac(), "Key bytes can only be specified for HMAC signatures. Please specify a PublicKey or PrivateKey instance.");
                                key3 = new SecretKeySpec(bArr, signatureAlgorithm.getJcaName());
                            }
                        }
                        Assert.notNull(key3, "A signing key must be specified if the specified JWT is digitally signed.");
                        String str8 = str6 + JwtParser.SEPARATOR_CHAR + str5;
                        try {
                            signatureAlgorithm.assertValidVerificationKey(key3);
                            if (!createSignatureValidator(signatureAlgorithm, key3).isValid(str8, str2)) {
                                throw new SignatureException("JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.");
                            }
                        } catch (WeakKeyException e) {
                            throw e;
                        } catch (InvalidKeyException | IllegalArgumentException e2) {
                            String value = signatureAlgorithm.getValue();
                            throw new UnsupportedJwtException("The parsed JWT indicates it was signed with the " + value + " signature " + "algorithm, but the specified signing key of type " + key3.getClass().getName() + " may not be used to validate " + value + " signatures.  Because the specified " + "signing key reflects a specific and expected algorithm, and the JWT does not reflect " + "this algorithm, it is likely that the JWT was not expected and therefore should not be " + "trusted.  Another possibility is that the parser was configured with the incorrect " + "signing key, but this cannot be assumed for security reasons.", e2);
                        }
                    } else {
                        throw new IllegalStateException("A signing key resolver and " + (key3 != null ? "a key object" : "key bytes") + " cannot both be specified. Choose either.");
                    }
                }
                if (this.allowedClockSkewMillis <= 0) {
                    z = false;
                }
                if (defaultClaims2 != null) {
                    Date now = this.clock.now();
                    long time = now.getTime();
                    Date expiration = defaultClaims2.getExpiration();
                    if (expiration != null) {
                        DefaultClaims defaultClaims3 = defaultClaims2;
                        long j = time - this.allowedClockSkewMillis;
                        if (!(z ? new Date(j) : now).after(expiration)) {
                            defaultClaims = defaultClaims3;
                        } else {
                            throw new ExpiredJwtException(jwsHeader, defaultClaims3, "JWT expired at " + DateFormats.formatIso8601(expiration, false) + ". Current time: " + DateFormats.formatIso8601(now, false) + ", a difference of " + (j - expiration.getTime()) + " milliseconds.  Allowed clock skew: " + this.allowedClockSkewMillis + " milliseconds.");
                        }
                    } else {
                        defaultClaims = defaultClaims2;
                    }
                    Date notBefore = defaultClaims.getNotBefore();
                    str3 = str2;
                    if (notBefore != null) {
                        JwsHeader jwsHeader3 = jwsHeader;
                        long j2 = time + this.allowedClockSkewMillis;
                        if (!(z ? new Date(j2) : now).before(notBefore)) {
                            jwsHeader = jwsHeader3;
                        } else {
                            throw new PrematureJwtException(jwsHeader3, defaultClaims, "JWT must not be accepted before " + DateFormats.formatIso8601(notBefore, false) + ". Current time: " + DateFormats.formatIso8601(now, false) + ", a difference of " + (notBefore.getTime() - j2) + " milliseconds.  Allowed clock skew: " + this.allowedClockSkewMillis + " milliseconds.");
                        }
                    }
                    validateExpectedClaims(jwsHeader, defaultClaims);
                } else {
                    defaultClaims = defaultClaims2;
                    str3 = str2;
                }
                if (defaultClaims != null) {
                    str7 = defaultClaims;
                }
                if (str3 != null) {
                    return new DefaultJws(jwsHeader, str7, str3);
                }
                return new DefaultJwt(jwsHeader, str7);
            }
            throw new MalformedJwtException("JWT string '" + str4 + "' is missing a body/payload.");
        }
        throw new MalformedJwtException("JWT strings must contain exactly 2 period characters. Found: " + i2);
    }

    private static Object normalize(Object obj) {
        return obj instanceof Integer ? Long.valueOf(((Integer) obj).longValue()) : obj;
    }

    private void validateExpectedClaims(Header header, Claims claims) {
        for (String str : this.expectedClaims.keySet()) {
            Object normalize = normalize(this.expectedClaims.get(str));
            Object normalize2 = normalize(claims.get(str));
            if (normalize instanceof Date) {
                try {
                    normalize2 = claims.get(str, Date.class);
                } catch (Exception unused) {
                    throw new IncorrectClaimException(header, claims, "JWT Claim '" + str + "' was expected to be a Date, but its value " + "cannot be converted to a Date using current heuristics.  Value: " + normalize2);
                }
            }
            InvalidClaimException invalidClaimException = null;
            if (normalize2 == null) {
                invalidClaimException = new MissingClaimException(header, claims, String.format(ClaimJwtException.MISSING_EXPECTED_CLAIM_MESSAGE_TEMPLATE, new Object[]{str, normalize}));
                continue;
            } else if (!normalize.equals(normalize2)) {
                invalidClaimException = new IncorrectClaimException(header, claims, String.format(ClaimJwtException.INCORRECT_EXPECTED_CLAIM_MESSAGE_TEMPLATE, new Object[]{str, normalize, normalize2}));
                continue;
            } else {
                continue;
            }
            if (invalidClaimException != null) {
                invalidClaimException.setClaimName(str);
                invalidClaimException.setClaimValue(normalize);
                throw invalidClaimException;
            }
        }
    }

    /* access modifiers changed from: protected */
    public JwtSignatureValidator createSignatureValidator(SignatureAlgorithm signatureAlgorithm, Key key2) {
        return new DefaultJwtSignatureValidator(signatureAlgorithm, key2, this.base64UrlDecoder);
    }

    public <T> T parse(String str, JwtHandler<T> jwtHandler) throws ExpiredJwtException, MalformedJwtException, SignatureException {
        Assert.notNull(jwtHandler, "JwtHandler argument cannot be null.");
        Assert.hasText(str, "JWT String argument cannot be null or empty.");
        Jwt parse = parse(str);
        if (parse instanceof Jws) {
            Jws jws = (Jws) parse;
            if (jws.getBody() instanceof Claims) {
                return jwtHandler.onClaimsJws(jws);
            }
            return jwtHandler.onPlaintextJws(jws);
        } else if (parse.getBody() instanceof Claims) {
            return jwtHandler.onClaimsJwt(parse);
        } else {
            return jwtHandler.onPlaintextJwt(parse);
        }
    }

    public Jwt<Header, String> parsePlaintextJwt(String str) {
        return (Jwt) parse(str, new JwtHandlerAdapter<Jwt<Header, String>>() {
            public Jwt<Header, String> onPlaintextJwt(Jwt<Header, String> jwt) {
                return jwt;
            }
        });
    }

    public Jwt<Header, Claims> parseClaimsJwt(String str) {
        try {
            return (Jwt) parse(str, new JwtHandlerAdapter<Jwt<Header, Claims>>() {
                public Jwt<Header, Claims> onClaimsJwt(Jwt<Header, Claims> jwt) {
                    return jwt;
                }
            });
        } catch (IllegalArgumentException e) {
            throw new UnsupportedJwtException("Signed JWSs are not supported.", e);
        }
    }

    public Jws<String> parsePlaintextJws(String str) {
        try {
            return (Jws) parse(str, new JwtHandlerAdapter<Jws<String>>() {
                public Jws<String> onPlaintextJws(Jws<String> jws) {
                    return jws;
                }
            });
        } catch (IllegalArgumentException e) {
            throw new UnsupportedJwtException("Signed JWSs are not supported.", e);
        }
    }

    public Jws<Claims> parseClaimsJws(String str) {
        return (Jws) parse(str, new JwtHandlerAdapter<Jws<Claims>>() {
            public Jws<Claims> onClaimsJws(Jws<Claims> jws) {
                return jws;
            }
        });
    }

    /* access modifiers changed from: protected */
    public Map<String, ?> readValue(String str) {
        try {
            return this.deserializer.deserialize(str.getBytes(Strings.UTF_8));
        } catch (DeserializationException e) {
            throw new MalformedJwtException("Unable to read JSON value: " + str, e);
        }
    }
}
