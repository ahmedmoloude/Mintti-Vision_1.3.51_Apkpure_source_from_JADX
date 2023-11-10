package p028io.jsonwebtoken;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import p028io.jsonwebtoken.p029io.Decoder;
import p028io.jsonwebtoken.p029io.Deserializer;
import p028io.jsonwebtoken.security.SignatureException;

/* renamed from: io.jsonwebtoken.JwtParser */
public interface JwtParser {
    public static final char SEPARATOR_CHAR = '.';

    @Deprecated
    JwtParser base64UrlDecodeWith(Decoder<String, byte[]> decoder);

    @Deprecated
    JwtParser deserializeJsonWith(Deserializer<Map<String, ?>> deserializer);

    boolean isSigned(String str);

    Jwt parse(String str) throws ExpiredJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;

    <T> T parse(String str, JwtHandler<T> jwtHandler) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;

    Jws<Claims> parseClaimsJws(String str) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;

    Jwt<Header, Claims> parseClaimsJwt(String str) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;

    Jws<String> parsePlaintextJws(String str) throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;

    Jwt<Header, String> parsePlaintextJwt(String str) throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;

    @Deprecated
    JwtParser require(String str, Object obj);

    @Deprecated
    JwtParser requireAudience(String str);

    @Deprecated
    JwtParser requireExpiration(Date date);

    @Deprecated
    JwtParser requireId(String str);

    @Deprecated
    JwtParser requireIssuedAt(Date date);

    @Deprecated
    JwtParser requireIssuer(String str);

    @Deprecated
    JwtParser requireNotBefore(Date date);

    @Deprecated
    JwtParser requireSubject(String str);

    @Deprecated
    JwtParser setAllowedClockSkewSeconds(long j);

    @Deprecated
    JwtParser setClock(Clock clock);

    @Deprecated
    JwtParser setCompressionCodecResolver(CompressionCodecResolver compressionCodecResolver);

    @Deprecated
    JwtParser setSigningKey(String str);

    @Deprecated
    JwtParser setSigningKey(Key key);

    @Deprecated
    JwtParser setSigningKey(byte[] bArr);

    @Deprecated
    JwtParser setSigningKeyResolver(SigningKeyResolver signingKeyResolver);
}
