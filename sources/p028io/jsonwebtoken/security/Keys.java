package p028io.jsonwebtoken.security;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import p028io.jsonwebtoken.SignatureAlgorithm;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.lang.Classes;

/* renamed from: io.jsonwebtoken.security.Keys */
public final class Keys {

    /* renamed from: EC */
    private static final String f2009EC = "io.jsonwebtoken.impl.crypto.EllipticCurveProvider";
    private static final String MAC = "io.jsonwebtoken.impl.crypto.MacProvider";
    private static final List<SignatureAlgorithm> PREFERRED_HMAC_ALGS = Collections.unmodifiableList(Arrays.asList(new SignatureAlgorithm[]{SignatureAlgorithm.HS512, SignatureAlgorithm.HS384, SignatureAlgorithm.HS256}));
    private static final String RSA = "io.jsonwebtoken.impl.crypto.RsaProvider";
    private static final Class[] SIG_ARG_TYPES = {SignatureAlgorithm.class};

    private Keys() {
    }

    public static SecretKey hmacShaKeyFor(byte[] bArr) throws WeakKeyException {
        if (bArr != null) {
            int length = bArr.length * 8;
            for (SignatureAlgorithm next : PREFERRED_HMAC_ALGS) {
                if (length >= next.getMinKeyLength()) {
                    return new SecretKeySpec(bArr, next.getJcaName());
                }
            }
            throw new WeakKeyException("The specified key byte array is " + length + " bits which " + "is not secure enough for any JWT HMAC-SHA algorithm.  The JWT " + "JWA Specification (RFC 7518, Section 3.2) states that keys used with HMAC-SHA algorithms MUST have a " + "size >= 256 bits (the key size must be greater than or equal to the hash " + "output size).  Consider using the " + Keys.class.getName() + "#secretKeyFor(SignatureAlgorithm) method " + "to create a key guaranteed to be secure enough for your preferred HMAC-SHA algorithm.  See " + "https://tools.ietf.org/html/rfc7518#section-3.2 for more information.");
        }
        throw new InvalidKeyException("SecretKey byte array cannot be null.");
    }

    public static SecretKey secretKeyFor(SignatureAlgorithm signatureAlgorithm) throws IllegalArgumentException {
        Assert.notNull(signatureAlgorithm, "SignatureAlgorithm cannot be null.");
        int i = C24361.$SwitchMap$io$jsonwebtoken$SignatureAlgorithm[signatureAlgorithm.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            return (SecretKey) Classes.invokeStatic(MAC, "generateKey", SIG_ARG_TYPES, signatureAlgorithm);
        }
        throw new IllegalArgumentException("The " + signatureAlgorithm.name() + " algorithm does not support shared secret keys.");
    }

    public static KeyPair keyPairFor(SignatureAlgorithm signatureAlgorithm) throws IllegalArgumentException {
        Assert.notNull(signatureAlgorithm, "SignatureAlgorithm cannot be null.");
        switch (signatureAlgorithm) {
            case RS256:
            case PS256:
            case RS384:
            case PS384:
            case RS512:
            case PS512:
                return (KeyPair) Classes.invokeStatic(RSA, "generateKeyPair", SIG_ARG_TYPES, signatureAlgorithm);
            case ES256:
            case ES384:
            case ES512:
                return (KeyPair) Classes.invokeStatic(f2009EC, "generateKeyPair", SIG_ARG_TYPES, signatureAlgorithm);
            default:
                throw new IllegalArgumentException("The " + signatureAlgorithm.name() + " algorithm does not support Key Pairs.");
        }
    }
}
