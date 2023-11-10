package p028io.jsonwebtoken.p029io;

import p028io.jsonwebtoken.lang.Assert;

/* renamed from: io.jsonwebtoken.io.Base64Decoder */
class Base64Decoder extends Base64Support implements Decoder<String, byte[]> {
    Base64Decoder() {
        super(Base64.DEFAULT);
    }

    Base64Decoder(Base64 base64) {
        super(base64);
    }

    public byte[] decode(String str) throws DecodingException {
        Assert.notNull(str, "String argument cannot be null");
        return this.base64.decodeFast(str.toCharArray());
    }
}
