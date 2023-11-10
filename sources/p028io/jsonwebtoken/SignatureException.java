package p028io.jsonwebtoken;

import p028io.jsonwebtoken.security.SecurityException;

@Deprecated
/* renamed from: io.jsonwebtoken.SignatureException */
public class SignatureException extends SecurityException {
    public SignatureException(String str) {
        super(str);
    }

    public SignatureException(String str, Throwable th) {
        super(str, th);
    }
}
