package p028io.jsonwebtoken.impl.crypto;

import java.security.Key;
import p028io.jsonwebtoken.SignatureAlgorithm;

/* renamed from: io.jsonwebtoken.impl.crypto.SignerFactory */
public interface SignerFactory {
    Signer createSigner(SignatureAlgorithm signatureAlgorithm, Key key);
}
