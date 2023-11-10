package p028io.jsonwebtoken.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import p028io.jsonwebtoken.Claims;
import p028io.jsonwebtoken.Clock;
import p028io.jsonwebtoken.CompressionCodecResolver;
import p028io.jsonwebtoken.ExpiredJwtException;
import p028io.jsonwebtoken.Header;
import p028io.jsonwebtoken.Jws;
import p028io.jsonwebtoken.Jwt;
import p028io.jsonwebtoken.JwtHandler;
import p028io.jsonwebtoken.JwtParser;
import p028io.jsonwebtoken.MalformedJwtException;
import p028io.jsonwebtoken.SigningKeyResolver;
import p028io.jsonwebtoken.UnsupportedJwtException;
import p028io.jsonwebtoken.p029io.Decoder;
import p028io.jsonwebtoken.p029io.Deserializer;
import p028io.jsonwebtoken.security.SignatureException;

/* renamed from: io.jsonwebtoken.impl.ImmutableJwtParser */
class ImmutableJwtParser implements JwtParser {
    private final JwtParser jwtParser;

    ImmutableJwtParser(JwtParser jwtParser2) {
        this.jwtParser = jwtParser2;
    }

    private IllegalStateException doNotMutate() {
        return new IllegalStateException("Cannot mutate a JwtParser created from JwtParserBuilder.build(), the mutable methods in JwtParser will be removed before version 1.0");
    }

    public JwtParser requireId(String str) {
        throw doNotMutate();
    }

    public JwtParser requireSubject(String str) {
        throw doNotMutate();
    }

    public JwtParser requireAudience(String str) {
        throw doNotMutate();
    }

    public JwtParser requireIssuer(String str) {
        throw doNotMutate();
    }

    public JwtParser requireIssuedAt(Date date) {
        throw doNotMutate();
    }

    public JwtParser requireExpiration(Date date) {
        throw doNotMutate();
    }

    public JwtParser requireNotBefore(Date date) {
        throw doNotMutate();
    }

    public JwtParser require(String str, Object obj) {
        throw doNotMutate();
    }

    public JwtParser setClock(Clock clock) {
        throw doNotMutate();
    }

    public JwtParser setAllowedClockSkewSeconds(long j) {
        throw doNotMutate();
    }

    public JwtParser setSigningKey(byte[] bArr) {
        throw doNotMutate();
    }

    public JwtParser setSigningKey(String str) {
        throw doNotMutate();
    }

    public JwtParser setSigningKey(Key key) {
        throw doNotMutate();
    }

    public JwtParser setSigningKeyResolver(SigningKeyResolver signingKeyResolver) {
        throw doNotMutate();
    }

    public JwtParser setCompressionCodecResolver(CompressionCodecResolver compressionCodecResolver) {
        throw doNotMutate();
    }

    public JwtParser base64UrlDecodeWith(Decoder<String, byte[]> decoder) {
        throw doNotMutate();
    }

    public JwtParser deserializeJsonWith(Deserializer<Map<String, ?>> deserializer) {
        throw doNotMutate();
    }

    public boolean isSigned(String str) {
        return this.jwtParser.isSigned(str);
    }

    public Jwt parse(String str) throws ExpiredJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return this.jwtParser.parse(str);
    }

    public <T> T parse(String str, JwtHandler<T> jwtHandler) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return this.jwtParser.parse(str, jwtHandler);
    }

    public Jwt<Header, String> parsePlaintextJwt(String str) throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return this.jwtParser.parsePlaintextJwt(str);
    }

    public Jwt<Header, Claims> parseClaimsJwt(String str) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return this.jwtParser.parseClaimsJwt(str);
    }

    public Jws<String> parsePlaintextJws(String str) throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return this.jwtParser.parsePlaintextJws(str);
    }

    public Jws<Claims> parseClaimsJws(String str) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return this.jwtParser.parseClaimsJws(str);
    }
}
