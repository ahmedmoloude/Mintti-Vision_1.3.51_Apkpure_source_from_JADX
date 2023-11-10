package p028io.jsonwebtoken.impl.crypto;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import p028io.jsonwebtoken.SignatureAlgorithm;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.security.SignatureException;

/* renamed from: io.jsonwebtoken.impl.crypto.RsaSignatureValidator */
public class RsaSignatureValidator extends RsaProvider implements SignatureValidator {
    private final RsaSigner SIGNER;

    public RsaSignatureValidator(SignatureAlgorithm signatureAlgorithm, Key key) {
        super(signatureAlgorithm, key);
        boolean z = key instanceof RSAPrivateKey;
        Assert.isTrue(z || (key instanceof RSAPublicKey), "RSA Signature validation requires either a RSAPublicKey or RSAPrivateKey instance.");
        this.SIGNER = z ? new RsaSigner(signatureAlgorithm, key) : null;
    }

    public boolean isValid(byte[] bArr, byte[] bArr2) {
        if (this.key instanceof PublicKey) {
            try {
                return doVerify(createSignatureInstance(), (PublicKey) this.key, bArr, bArr2);
            } catch (Exception e) {
                throw new SignatureException("Unable to verify RSA signature using configured PublicKey. " + e.getMessage(), e);
            }
        } else {
            Assert.notNull(this.SIGNER, "RSA Signer instance cannot be null.  This is a bug.  Please report it.");
            return MessageDigest.isEqual(this.SIGNER.sign(bArr), bArr2);
        }
    }

    /* access modifiers changed from: protected */
    public boolean doVerify(Signature signature, PublicKey publicKey, byte[] bArr, byte[] bArr2) throws InvalidKeyException, java.security.SignatureException {
        signature.initVerify(publicKey);
        signature.update(bArr);
        return signature.verify(bArr2);
    }
}
