package p028io.jsonwebtoken.impl;

import p028io.jsonwebtoken.Header;
import p028io.jsonwebtoken.Jwt;

/* renamed from: io.jsonwebtoken.impl.DefaultJwt */
public class DefaultJwt<B> implements Jwt<Header, B> {
    private final B body;
    private final Header header;

    public DefaultJwt(Header header2, B b) {
        this.header = header2;
        this.body = b;
    }

    public Header getHeader() {
        return this.header;
    }

    public B getBody() {
        return this.body;
    }

    public String toString() {
        return "header=" + this.header + ",body=" + this.body;
    }
}
