package p028io.jsonwebtoken.impl.crypto;

import com.itextpdf.text.pdf.ByteBuffer;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.HashMap;
import java.util.Map;
import kotlin.UByte;
import p028io.jsonwebtoken.JwtException;
import p028io.jsonwebtoken.SignatureAlgorithm;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.lang.Strings;

/* renamed from: io.jsonwebtoken.impl.crypto.EllipticCurveProvider */
public abstract class EllipticCurveProvider extends SignatureProvider {
    private static final Map<SignatureAlgorithm, String> EC_CURVE_NAMES = createEcCurveNames();

    private static Map<SignatureAlgorithm, String> createEcCurveNames() {
        HashMap hashMap = new HashMap();
        hashMap.put(SignatureAlgorithm.ES256, "secp256r1");
        hashMap.put(SignatureAlgorithm.ES384, "secp384r1");
        hashMap.put(SignatureAlgorithm.ES512, "secp521r1");
        return hashMap;
    }

    protected EllipticCurveProvider(SignatureAlgorithm signatureAlgorithm, Key key) {
        super(signatureAlgorithm, key);
        Assert.isTrue(signatureAlgorithm.isEllipticCurve(), "SignatureAlgorithm must be an Elliptic Curve algorithm.");
    }

    public static KeyPair generateKeyPair() {
        return generateKeyPair(SignatureAlgorithm.ES512);
    }

    public static KeyPair generateKeyPair(SignatureAlgorithm signatureAlgorithm) {
        return generateKeyPair(signatureAlgorithm, DEFAULT_SECURE_RANDOM);
    }

    public static KeyPair generateKeyPair(SignatureAlgorithm signatureAlgorithm, SecureRandom secureRandom) {
        return generateKeyPair("EC", (String) null, signatureAlgorithm, secureRandom);
    }

    public static KeyPair generateKeyPair(String str, String str2, SignatureAlgorithm signatureAlgorithm, SecureRandom secureRandom) {
        KeyPairGenerator keyPairGenerator;
        Assert.notNull(signatureAlgorithm, "SignatureAlgorithm argument cannot be null.");
        Assert.isTrue(signatureAlgorithm.isEllipticCurve(), "SignatureAlgorithm argument must represent an Elliptic Curve algorithm.");
        try {
            if (Strings.hasText(str2)) {
                keyPairGenerator = KeyPairGenerator.getInstance(str, str2);
            } else {
                keyPairGenerator = KeyPairGenerator.getInstance(str);
            }
            keyPairGenerator.initialize(new ECGenParameterSpec(EC_CURVE_NAMES.get(signatureAlgorithm)), secureRandom);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to generate Elliptic Curve KeyPair: " + e.getMessage(), e);
        }
    }

    /* renamed from: io.jsonwebtoken.impl.crypto.EllipticCurveProvider$1 */
    static /* synthetic */ class C24251 {
        static final /* synthetic */ int[] $SwitchMap$io$jsonwebtoken$SignatureAlgorithm;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                io.jsonwebtoken.SignatureAlgorithm[] r0 = p028io.jsonwebtoken.SignatureAlgorithm.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$jsonwebtoken$SignatureAlgorithm = r0
                io.jsonwebtoken.SignatureAlgorithm r1 = p028io.jsonwebtoken.SignatureAlgorithm.ES256     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$io$jsonwebtoken$SignatureAlgorithm     // Catch:{ NoSuchFieldError -> 0x001d }
                io.jsonwebtoken.SignatureAlgorithm r1 = p028io.jsonwebtoken.SignatureAlgorithm.ES384     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$io$jsonwebtoken$SignatureAlgorithm     // Catch:{ NoSuchFieldError -> 0x0028 }
                io.jsonwebtoken.SignatureAlgorithm r1 = p028io.jsonwebtoken.SignatureAlgorithm.ES512     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p028io.jsonwebtoken.impl.crypto.EllipticCurveProvider.C24251.<clinit>():void");
        }
    }

    public static int getSignatureByteArrayLength(SignatureAlgorithm signatureAlgorithm) throws JwtException {
        int i = C24251.$SwitchMap$io$jsonwebtoken$SignatureAlgorithm[signatureAlgorithm.ordinal()];
        if (i == 1) {
            return 64;
        }
        if (i == 2) {
            return 96;
        }
        if (i == 3) {
            return 132;
        }
        throw new JwtException("Unsupported Algorithm: " + signatureAlgorithm.name());
    }

    public static byte[] transcodeSignatureToConcat(byte[] bArr, int i) throws JwtException {
        int i2;
        if (bArr.length < 8 || bArr[0] != 48) {
            throw new JwtException("Invalid ECDSA signature format");
        }
        if (bArr[1] > 0) {
            i2 = 2;
        } else if (bArr[1] == -127) {
            i2 = 3;
        } else {
            throw new JwtException("Invalid ECDSA signature format");
        }
        byte b = bArr[i2 + 1];
        int i3 = b;
        while (i3 > 0 && bArr[((i2 + 2) + b) - i3] == 0) {
            i3--;
        }
        int i4 = i2 + 2 + b;
        byte b2 = bArr[i4 + 1];
        int i5 = b2;
        while (i5 > 0 && bArr[((i4 + 2) + b2) - i5] == 0) {
            i5--;
        }
        int max = Math.max(Math.max(i3, i5), i / 2);
        int i6 = i2 - 1;
        if ((bArr[i6] & UByte.MAX_VALUE) == bArr.length - i2 && (bArr[i6] & UByte.MAX_VALUE) == b + 2 + 2 + b2 && bArr[i2] == 2 && bArr[i4] == 2) {
            int i7 = max * 2;
            byte[] bArr2 = new byte[i7];
            System.arraycopy(bArr, i4 - i3, bArr2, max - i3, i3);
            System.arraycopy(bArr, ((i4 + 2) + b2) - i5, bArr2, i7 - i5, i5);
            return bArr2;
        }
        throw new JwtException("Invalid ECDSA signature format");
    }

    public static byte[] transcodeSignatureToDER(byte[] bArr) throws JwtException {
        byte[] bArr2;
        int length = bArr.length / 2;
        int i = length;
        while (i > 0 && bArr[length - i] == 0) {
            i--;
        }
        int i2 = length - i;
        int i3 = bArr[i2] < 0 ? i + 1 : i;
        int i4 = length;
        while (i4 > 0 && bArr[(length * 2) - i4] == 0) {
            i4--;
        }
        int i5 = (length * 2) - i4;
        int i6 = bArr[i5] < 0 ? i4 + 1 : i4;
        int i7 = i3 + 2 + 2 + i6;
        if (i7 <= 255) {
            int i8 = 1;
            if (i7 < 128) {
                bArr2 = new byte[(i3 + 4 + 2 + i6)];
            } else {
                bArr2 = new byte[(i3 + 5 + 2 + i6)];
                bArr2[1] = -127;
                i8 = 2;
            }
            bArr2[0] = ByteBuffer.ZERO;
            int i9 = i8 + 1;
            bArr2[i8] = (byte) i7;
            int i10 = i9 + 1;
            bArr2[i9] = 2;
            bArr2[i10] = (byte) i3;
            int i11 = i10 + 1 + i3;
            System.arraycopy(bArr, i2, bArr2, i11 - i, i);
            int i12 = i11 + 1;
            bArr2[i11] = 2;
            bArr2[i12] = (byte) i6;
            System.arraycopy(bArr, i5, bArr2, ((i12 + 1) + i6) - i4, i4);
            return bArr2;
        }
        throw new JwtException("Invalid ECDSA signature format");
    }
}
