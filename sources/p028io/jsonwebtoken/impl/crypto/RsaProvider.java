package p028io.jsonwebtoken.impl.crypto;

import com.itextpdf.text.pdf.security.SecurityConstants;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.HashMap;
import java.util.Map;
import p028io.jsonwebtoken.SignatureAlgorithm;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.lang.RuntimeEnvironment;
import p028io.jsonwebtoken.security.SignatureException;

/* renamed from: io.jsonwebtoken.impl.crypto.RsaProvider */
public abstract class RsaProvider extends SignatureProvider {
    private static final Map<SignatureAlgorithm, PSSParameterSpec> PSS_PARAMETER_SPECS = createPssParameterSpecs();

    static {
        RuntimeEnvironment.enableBouncyCastleIfPossible();
    }

    private static Map<SignatureAlgorithm, PSSParameterSpec> createPssParameterSpecs() {
        HashMap hashMap = new HashMap();
        MGF1ParameterSpec mGF1ParameterSpec = MGF1ParameterSpec.SHA256;
        hashMap.put(SignatureAlgorithm.PS256, new PSSParameterSpec(mGF1ParameterSpec.getDigestAlgorithm(), "MGF1", mGF1ParameterSpec, 32, 1));
        MGF1ParameterSpec mGF1ParameterSpec2 = MGF1ParameterSpec.SHA384;
        hashMap.put(SignatureAlgorithm.PS384, new PSSParameterSpec(mGF1ParameterSpec2.getDigestAlgorithm(), "MGF1", mGF1ParameterSpec2, 48, 1));
        MGF1ParameterSpec mGF1ParameterSpec3 = MGF1ParameterSpec.SHA512;
        hashMap.put(SignatureAlgorithm.PS512, new PSSParameterSpec(mGF1ParameterSpec3.getDigestAlgorithm(), "MGF1", mGF1ParameterSpec3, 64, 1));
        return hashMap;
    }

    protected RsaProvider(SignatureAlgorithm signatureAlgorithm, Key key) {
        super(signatureAlgorithm, key);
        Assert.isTrue(signatureAlgorithm.isRsa(), "SignatureAlgorithm must be an RSASSA or RSASSA-PSS algorithm.");
    }

    /* access modifiers changed from: protected */
    public Signature createSignatureInstance() {
        Signature createSignatureInstance = super.createSignatureInstance();
        PSSParameterSpec pSSParameterSpec = PSS_PARAMETER_SPECS.get(this.alg);
        if (pSSParameterSpec != null) {
            setParameter(createSignatureInstance, pSSParameterSpec);
        }
        return createSignatureInstance;
    }

    /* access modifiers changed from: protected */
    public void setParameter(Signature signature, PSSParameterSpec pSSParameterSpec) {
        try {
            doSetParameter(signature, pSSParameterSpec);
        } catch (InvalidAlgorithmParameterException e) {
            throw new SignatureException("Unsupported RSASSA-PSS parameter '" + pSSParameterSpec + "': " + e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public void doSetParameter(Signature signature, PSSParameterSpec pSSParameterSpec) throws InvalidAlgorithmParameterException {
        signature.setParameter(pSSParameterSpec);
    }

    public static KeyPair generateKeyPair() {
        return generateKeyPair(4096);
    }

    public static KeyPair generateKeyPair(int i) {
        return generateKeyPair(i, DEFAULT_SECURE_RANDOM);
    }

    public static KeyPair generateKeyPair(SignatureAlgorithm signatureAlgorithm) {
        Assert.isTrue(signatureAlgorithm.isRsa(), "Only RSA algorithms are supported by this method.");
        int i = C24261.$SwitchMap$io$jsonwebtoken$SignatureAlgorithm[signatureAlgorithm.ordinal()];
        return generateKeyPair((i == 1 || i == 2) ? 2048 : (i == 3 || i == 4) ? 3072 : 4096, DEFAULT_SECURE_RANDOM);
    }

    /* renamed from: io.jsonwebtoken.impl.crypto.RsaProvider$1 */
    static /* synthetic */ class C24261 {
        static final /* synthetic */ int[] $SwitchMap$io$jsonwebtoken$SignatureAlgorithm;

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        static {
            /*
                io.jsonwebtoken.SignatureAlgorithm[] r0 = p028io.jsonwebtoken.SignatureAlgorithm.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$jsonwebtoken$SignatureAlgorithm = r0
                io.jsonwebtoken.SignatureAlgorithm r1 = p028io.jsonwebtoken.SignatureAlgorithm.RS256     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$io$jsonwebtoken$SignatureAlgorithm     // Catch:{ NoSuchFieldError -> 0x001d }
                io.jsonwebtoken.SignatureAlgorithm r1 = p028io.jsonwebtoken.SignatureAlgorithm.PS256     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$io$jsonwebtoken$SignatureAlgorithm     // Catch:{ NoSuchFieldError -> 0x0028 }
                io.jsonwebtoken.SignatureAlgorithm r1 = p028io.jsonwebtoken.SignatureAlgorithm.RS384     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$io$jsonwebtoken$SignatureAlgorithm     // Catch:{ NoSuchFieldError -> 0x0033 }
                io.jsonwebtoken.SignatureAlgorithm r1 = p028io.jsonwebtoken.SignatureAlgorithm.PS384     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p028io.jsonwebtoken.impl.crypto.RsaProvider.C24261.<clinit>():void");
        }
    }

    public static KeyPair generateKeyPair(int i, SecureRandom secureRandom) {
        return generateKeyPair(SecurityConstants.RSA, i, secureRandom);
    }

    protected static KeyPair generateKeyPair(String str, int i, SecureRandom secureRandom) {
        try {
            KeyPairGenerator instance = KeyPairGenerator.getInstance(str);
            instance.initialize(i, secureRandom);
            return instance.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Unable to obtain an RSA KeyPairGenerator: " + e.getMessage(), e);
        }
    }
}
