package p028io.jsonwebtoken;

/* renamed from: io.jsonwebtoken.InvalidClaimException */
public class InvalidClaimException extends ClaimJwtException {
    private String claimName;
    private Object claimValue;

    protected InvalidClaimException(Header header, Claims claims, String str) {
        super(header, claims, str);
    }

    protected InvalidClaimException(Header header, Claims claims, String str, Throwable th) {
        super(header, claims, str, th);
    }

    public String getClaimName() {
        return this.claimName;
    }

    public void setClaimName(String str) {
        this.claimName = str;
    }

    public Object getClaimValue() {
        return this.claimValue;
    }

    public void setClaimValue(Object obj) {
        this.claimValue = obj;
    }
}
