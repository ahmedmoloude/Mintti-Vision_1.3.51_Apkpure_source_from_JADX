package p028io.jsonwebtoken.impl.crypto;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import p028io.jsonwebtoken.SignatureAlgorithm;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.security.SignatureException;

/* renamed from: io.jsonwebtoken.impl.crypto.MacSigner */
public class MacSigner extends MacProvider implements Signer {
    public MacSigner(SignatureAlgorithm signatureAlgorithm, byte[] bArr) {
        this(signatureAlgorithm, (Key) new SecretKeySpec(bArr, signatureAlgorithm.getJcaName()));
    }

    public MacSigner(SignatureAlgorithm signatureAlgorithm, Key key) {
        super(signatureAlgorithm, key);
        Assert.isTrue(signatureAlgorithm.isHmac(), "The MacSigner only supports HMAC signature algorithms.");
        if (!(key instanceof SecretKey)) {
            throw new IllegalArgumentException("MAC signatures must be computed and verified using a SecretKey.  The specified key of type " + key.getClass().getName() + " is not a SecretKey.");
        }
    }

    public byte[] sign(byte[] bArr) {
        return getMacInstance().doFinal(bArr);
    }

    /* access modifiers changed from: protected */
    public Mac getMacInstance() throws SignatureException {
        try {
            return doGetMacInstance();
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureException("Unable to obtain JCA MAC algorithm '" + this.alg.getJcaName() + "': " + e.getMessage(), e);
        } catch (InvalidKeyException e2) {
            throw new SignatureException("The specified signing key is not a valid " + this.alg.name() + " key: " + e2.getMessage(), e2);
        }
    }

    /* access modifiers changed from: protected */
    public Mac doGetMacInstance() throws NoSuchAlgorithmException, InvalidKeyException {
        Mac instance = Mac.getInstance(this.alg.getJcaName());
        instance.init(this.key);
        return instance;
    }
}
