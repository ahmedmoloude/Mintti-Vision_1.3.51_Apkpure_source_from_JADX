package p028io.jsonwebtoken;

import p028io.jsonwebtoken.Header;

/* renamed from: io.jsonwebtoken.Jwt */
public interface Jwt<H extends Header, B> {
    B getBody();

    H getHeader();
}
