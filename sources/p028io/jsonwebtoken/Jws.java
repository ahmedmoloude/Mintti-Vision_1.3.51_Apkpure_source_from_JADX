package p028io.jsonwebtoken;

/* renamed from: io.jsonwebtoken.Jws */
public interface Jws<B> extends Jwt<JwsHeader, B> {
    String getSignature();
}
