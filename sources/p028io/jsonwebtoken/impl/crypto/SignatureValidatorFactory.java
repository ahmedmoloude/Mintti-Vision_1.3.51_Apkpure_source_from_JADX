package p028io.jsonwebtoken.impl.crypto;

import java.security.Key;
import p028io.jsonwebtoken.SignatureAlgorithm;

/* renamed from: io.jsonwebtoken.impl.crypto.SignatureValidatorFactory */
public interface SignatureValidatorFactory {
    SignatureValidator createSignatureValidator(SignatureAlgorithm signatureAlgorithm, Key key);
}
