package p028io.jsonwebtoken.impl.crypto;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.ECKey;
import p028io.jsonwebtoken.JwtException;
import p028io.jsonwebtoken.SignatureAlgorithm;
import p028io.jsonwebtoken.security.SignatureException;

/* renamed from: io.jsonwebtoken.impl.crypto.EllipticCurveSigner */
public class EllipticCurveSigner extends EllipticCurveProvider implements Signer {
    public EllipticCurveSigner(SignatureAlgorithm signatureAlgorithm, Key key) {
        super(signatureAlgorithm, key);
        if (!(key instanceof PrivateKey) || !(key instanceof ECKey)) {
            throw new IllegalArgumentException("Elliptic Curve signatures must be computed using an EC PrivateKey.  The specified key of type " + key.getClass().getName() + " is not an EC PrivateKey.");
        }
    }

    public byte[] sign(byte[] bArr) {
        try {
            return doSign(bArr);
        } catch (InvalidKeyException e) {
            throw new SignatureException("Invalid Elliptic Curve PrivateKey. " + e.getMessage(), e);
        } catch (java.security.SignatureException e2) {
            throw new SignatureException("Unable to calculate signature using Elliptic Curve PrivateKey. " + e2.getMessage(), e2);
        } catch (JwtException e3) {
            throw new SignatureException("Unable to convert signature to JOSE format. " + e3.getMessage(), e3);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] doSign(byte[] bArr) throws InvalidKeyException, java.security.SignatureException, JwtException {
        Signature createSignatureInstance = createSignatureInstance();
        createSignatureInstance.initSign((PrivateKey) this.key);
        createSignatureInstance.update(bArr);
        return transcodeSignatureToConcat(createSignatureInstance.sign(), getSignatureByteArrayLength(this.alg));
    }
}
