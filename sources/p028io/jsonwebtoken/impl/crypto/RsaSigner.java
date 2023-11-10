package p028io.jsonwebtoken.impl.crypto;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAKey;
import p028io.jsonwebtoken.SignatureAlgorithm;
import p028io.jsonwebtoken.security.SignatureException;

/* renamed from: io.jsonwebtoken.impl.crypto.RsaSigner */
public class RsaSigner extends RsaProvider implements Signer {
    public RsaSigner(SignatureAlgorithm signatureAlgorithm, Key key) {
        super(signatureAlgorithm, key);
        if (!(key instanceof PrivateKey) || !(key instanceof RSAKey)) {
            throw new IllegalArgumentException("RSA signatures must be computed using an RSA PrivateKey.  The specified key of type " + key.getClass().getName() + " is not an RSA PrivateKey.");
        }
    }

    public byte[] sign(byte[] bArr) {
        try {
            return doSign(bArr);
        } catch (InvalidKeyException e) {
            throw new SignatureException("Invalid RSA PrivateKey. " + e.getMessage(), e);
        } catch (java.security.SignatureException e2) {
            throw new SignatureException("Unable to calculate signature using RSA PrivateKey. " + e2.getMessage(), e2);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] doSign(byte[] bArr) throws InvalidKeyException, java.security.SignatureException {
        Signature createSignatureInstance = createSignatureInstance();
        createSignatureInstance.initSign((PrivateKey) this.key);
        createSignatureInstance.update(bArr);
        return createSignatureInstance.sign();
    }
}
