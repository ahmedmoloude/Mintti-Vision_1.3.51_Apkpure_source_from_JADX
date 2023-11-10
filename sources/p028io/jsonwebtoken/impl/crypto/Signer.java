package p028io.jsonwebtoken.impl.crypto;

import p028io.jsonwebtoken.security.SignatureException;

/* renamed from: io.jsonwebtoken.impl.crypto.Signer */
public interface Signer {
    byte[] sign(byte[] bArr) throws SignatureException;
}
