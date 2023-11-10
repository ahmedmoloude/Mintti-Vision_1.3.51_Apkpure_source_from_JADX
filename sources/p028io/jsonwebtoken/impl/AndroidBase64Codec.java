package p028io.jsonwebtoken.impl;

import p028io.jsonwebtoken.p029io.Decoders;
import p028io.jsonwebtoken.p029io.Encoders;

@Deprecated
/* renamed from: io.jsonwebtoken.impl.AndroidBase64Codec */
public class AndroidBase64Codec extends AbstractTextCodec {
    public String encode(byte[] bArr) {
        return Encoders.BASE64.encode(bArr);
    }

    public byte[] decode(String str) {
        return Decoders.BASE64.decode(str);
    }
}
