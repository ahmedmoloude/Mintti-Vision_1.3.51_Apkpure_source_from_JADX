package p028io.jsonwebtoken.p029io;

import p028io.jsonwebtoken.lang.Assert;

/* renamed from: io.jsonwebtoken.io.Base64Encoder */
class Base64Encoder extends Base64Support implements Encoder<byte[], String> {
    Base64Encoder() {
        super(Base64.DEFAULT);
    }

    Base64Encoder(Base64 base64) {
        super(base64);
    }

    public String encode(byte[] bArr) throws EncodingException {
        Assert.notNull(bArr, "byte array argument cannot be null");
        return this.base64.encodeToString(bArr, false);
    }
}
