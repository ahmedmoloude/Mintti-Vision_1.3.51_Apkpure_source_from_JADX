package p028io.jsonwebtoken.p029io;

import com.itextpdf.text.pdf.BidiOrder;
import java.util.Arrays;
import kotlin.UByte;

/* renamed from: io.jsonwebtoken.io.Base64 */
final class Base64 {
    private static final char[] BASE64URL_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray();
    private static final int[] BASE64URL_IALPHABET;
    private static final char[] BASE64_ALPHABET;
    private static final int[] BASE64_IALPHABET;
    static final Base64 DEFAULT = new Base64(false);
    private static final int IALPHABET_MAX_INDEX;
    static final Base64 URL_SAFE = new Base64(true);
    private final char[] ALPHABET;
    private final int[] IALPHABET;
    private final boolean urlsafe;

    static {
        char[] charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        BASE64_ALPHABET = charArray;
        int[] iArr = new int[256];
        BASE64_IALPHABET = iArr;
        int[] iArr2 = new int[256];
        BASE64URL_IALPHABET = iArr2;
        IALPHABET_MAX_INDEX = iArr.length - 1;
        Arrays.fill(iArr, -1);
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        int length = charArray.length;
        for (int i = 0; i < length; i++) {
            BASE64_IALPHABET[BASE64_ALPHABET[i]] = i;
            BASE64URL_IALPHABET[BASE64URL_ALPHABET[i]] = i;
        }
        BASE64_IALPHABET[61] = 0;
        BASE64URL_IALPHABET[61] = 0;
    }

    private Base64(boolean z) {
        this.urlsafe = z;
        this.ALPHABET = z ? BASE64URL_ALPHABET : BASE64_ALPHABET;
        this.IALPHABET = z ? BASE64URL_IALPHABET : BASE64_IALPHABET;
    }

    private String getName() {
        return this.urlsafe ? "base64url" : "base64";
    }

    private char[] encodeToChar(byte[] bArr, boolean z) {
        byte[] bArr2 = bArr;
        int length = bArr2 != null ? bArr2.length : 0;
        if (length == 0) {
            return new char[0];
        }
        int i = (length / 3) * 3;
        int i2 = length - i;
        int i3 = length - 1;
        int i4 = ((i3 / 3) + 1) << 2;
        int i5 = i4 + (z ? ((i4 - 1) / 76) << 1 : 0);
        char[] cArr = new char[(this.urlsafe ? i5 - (i2 == 2 ? 1 : i2 == 1 ? 2 : 0) : i5)];
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i6 < i) {
            int i9 = i6 + 1;
            int i10 = i9 + 1;
            byte b = ((bArr2[i6] & UByte.MAX_VALUE) << BidiOrder.f527S) | ((bArr2[i9] & UByte.MAX_VALUE) << 8);
            int i11 = i10 + 1;
            byte b2 = b | (bArr2[i10] & UByte.MAX_VALUE);
            int i12 = i7 + 1;
            char[] cArr2 = this.ALPHABET;
            cArr[i7] = cArr2[(b2 >>> 18) & 63];
            int i13 = i12 + 1;
            cArr[i12] = cArr2[(b2 >>> BidiOrder.f520CS) & 63];
            int i14 = i13 + 1;
            cArr[i13] = cArr2[(b2 >>> 6) & 63];
            i7 = i14 + 1;
            cArr[i14] = cArr2[b2 & 63];
            if (z && (i8 = i8 + 1) == 19 && i7 < i5 - 2) {
                int i15 = i7 + 1;
                cArr[i7] = 13;
                cArr[i15] = 10;
                i7 = i15 + 1;
                i8 = 0;
            }
            i6 = i11;
        }
        if (i2 > 0) {
            int i16 = ((bArr2[i] & UByte.MAX_VALUE) << 10) | (i2 == 2 ? (bArr2[i3] & UByte.MAX_VALUE) << 2 : 0);
            char[] cArr3 = this.ALPHABET;
            cArr[i5 - 4] = cArr3[i16 >> 12];
            cArr[i5 - 3] = cArr3[(i16 >>> 6) & 63];
            if (i2 == 2) {
                cArr[i5 - 2] = cArr3[i16 & 63];
            } else if (!this.urlsafe) {
                cArr[i5 - 2] = '=';
            }
            if (!this.urlsafe) {
                cArr[i5 - 1] = '=';
            }
        }
        return cArr;
    }

    private int ctoi(char c) {
        int i = c > IALPHABET_MAX_INDEX ? -1 : this.IALPHABET[c];
        if (i >= 0) {
            return i;
        }
        throw new DecodingException("Illegal " + getName() + " character: '" + c + "'");
    }

    /* access modifiers changed from: package-private */
    public final byte[] decodeFast(char[] cArr) throws DecodingException {
        int i;
        int i2;
        int i3 = 0;
        int length = cArr != null ? cArr.length : 0;
        if (length == 0) {
            return new byte[0];
        }
        int i4 = length - 1;
        int i5 = 0;
        while (i < i4 && this.IALPHABET[cArr[i]] < 0) {
            i5 = i + 1;
        }
        while (i4 > 0 && this.IALPHABET[cArr[i4]] < 0) {
            i4--;
        }
        int i6 = cArr[i4] == '=' ? cArr[i4 + -1] == '=' ? 2 : 1 : 0;
        int i7 = (i4 - i) + 1;
        if (length > 76) {
            i2 = (cArr[76] == 13 ? i7 / 78 : 0) << 1;
        } else {
            i2 = 0;
        }
        int i8 = (((i7 - i2) * 6) >> 3) - i6;
        byte[] bArr = new byte[i8];
        int i9 = (i8 / 3) * 3;
        int i10 = 0;
        int i11 = 0;
        while (i10 < i9) {
            int i12 = i + 1;
            int i13 = i12 + 1;
            int ctoi = (ctoi(cArr[i]) << 18) | (ctoi(cArr[i12]) << 12);
            int i14 = i13 + 1;
            int ctoi2 = ctoi | (ctoi(cArr[i13]) << 6);
            int i15 = i14 + 1;
            int ctoi3 = ctoi2 | ctoi(cArr[i14]);
            int i16 = i10 + 1;
            bArr[i10] = (byte) (ctoi3 >> 16);
            int i17 = i16 + 1;
            bArr[i16] = (byte) (ctoi3 >> 8);
            int i18 = i17 + 1;
            bArr[i17] = (byte) ctoi3;
            if (i2 <= 0 || (i11 = i11 + 1) != 19) {
                i = i15;
            } else {
                i = i15 + 2;
                i11 = 0;
            }
            i10 = i18;
        }
        if (i10 < i8) {
            int i19 = 0;
            while (i <= i4 - i6) {
                i3 |= ctoi(cArr[i]) << (18 - (i19 * 6));
                i19++;
                i++;
            }
            int i20 = 16;
            while (i10 < i8) {
                bArr[i10] = (byte) (i3 >> i20);
                i20 -= 8;
                i10++;
            }
        }
        return bArr;
    }

    /* access modifiers changed from: package-private */
    public final String encodeToString(byte[] bArr, boolean z) {
        return new String(encodeToChar(bArr, z));
    }
}
