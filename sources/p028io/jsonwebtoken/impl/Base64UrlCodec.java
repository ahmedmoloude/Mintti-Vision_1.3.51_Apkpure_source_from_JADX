package p028io.jsonwebtoken.impl;

import p028io.jsonwebtoken.p029io.Decoders;
import p028io.jsonwebtoken.p029io.Encoders;

@Deprecated
/* renamed from: io.jsonwebtoken.impl.Base64UrlCodec */
public class Base64UrlCodec extends AbstractTextCodec {
    public String encode(byte[] bArr) {
        return Encoders.BASE64URL.encode(bArr);
    }

    public byte[] decode(String str) {
        return Decoders.BASE64URL.decode(str);
    }
}
