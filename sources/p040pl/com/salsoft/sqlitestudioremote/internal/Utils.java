package p040pl.com.salsoft.sqlitestudioremote.internal;

import com.itextpdf.text.pdf.BidiOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kotlin.UByte;
import kotlin.text.Typography;
import p028io.jsonwebtoken.JwtParser;

/* renamed from: pl.com.salsoft.sqlitestudioremote.internal.Utils */
public class Utils {
    public static String LOG_TAG = "SQLiteStudioRemote";
    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String toBlobString(byte[] bArr) {
        return "X'" + bytesToHex(bArr) + "'";
    }

    public static String bytesToHex(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i] & UByte.MAX_VALUE;
            int i2 = i * 2;
            char[] cArr2 = hexArray;
            cArr[i2] = cArr2[b >>> 4];
            cArr[i2 + 1] = cArr2[b & BidiOrder.f518B];
        }
        return new String(cArr);
    }

    public static String createRegexFromGlob(String str) {
        String str2 = "^";
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '*') {
                str2 = str2 + ".*";
            } else if (charAt == '.') {
                str2 = str2 + "\\.";
            } else if (charAt == '?') {
                str2 = str2 + JwtParser.SEPARATOR_CHAR;
            } else if (charAt != '\\') {
                str2 = str2 + charAt;
            } else {
                str2 = str2 + "\\\\";
            }
        }
        return str2 + Typography.dollar;
    }

    public static byte[] md5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            return instance.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
