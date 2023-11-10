package p028io.jsonwebtoken.impl;

import java.nio.charset.Charset;
import p028io.jsonwebtoken.lang.Assert;

@Deprecated
/* renamed from: io.jsonwebtoken.impl.AbstractTextCodec */
public abstract class AbstractTextCodec implements TextCodec {
    protected static final Charset US_ASCII = Charset.forName("US-ASCII");
    protected static final Charset UTF8 = Charset.forName("UTF-8");

    public String encode(String str) {
        Assert.hasText(str, "String argument to encode cannot be null or empty.");
        return encode(str.getBytes(UTF8));
    }

    public String decodeToString(String str) {
        return new String(decode(str), UTF8);
    }
}
