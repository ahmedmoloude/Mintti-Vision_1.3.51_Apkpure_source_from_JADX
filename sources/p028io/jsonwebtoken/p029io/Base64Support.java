package p028io.jsonwebtoken.p029io;

import p028io.jsonwebtoken.lang.Assert;

/* renamed from: io.jsonwebtoken.io.Base64Support */
class Base64Support {
    protected final Base64 base64;

    Base64Support(Base64 base642) {
        Assert.notNull(base642, "Base64 argument cannot be null");
        this.base64 = base642;
    }
}
