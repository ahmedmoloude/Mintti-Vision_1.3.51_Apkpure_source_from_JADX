package p028io.jsonwebtoken.security;

import p028io.jsonwebtoken.JwtException;

/* renamed from: io.jsonwebtoken.security.SecurityException */
public class SecurityException extends JwtException {
    public SecurityException(String str) {
        super(str);
    }

    public SecurityException(String str, Throwable th) {
        super(str, th);
    }
}
