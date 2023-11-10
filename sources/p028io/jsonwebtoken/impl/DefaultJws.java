package p028io.jsonwebtoken.impl;

import p028io.jsonwebtoken.Jws;
import p028io.jsonwebtoken.JwsHeader;

/* renamed from: io.jsonwebtoken.impl.DefaultJws */
public class DefaultJws<B> implements Jws<B> {
    private final B body;
    private final JwsHeader header;
    private final String signature;

    public DefaultJws(JwsHeader jwsHeader, B b, String str) {
        this.header = jwsHeader;
        this.body = b;
        this.signature = str;
    }

    public JwsHeader getHeader() {
        return this.header;
    }

    public B getBody() {
        return this.body;
    }

    public String getSignature() {
        return this.signature;
    }

    public String toString() {
        return "header=" + this.header + ",body=" + this.body + ",signature=" + this.signature;
    }
}
