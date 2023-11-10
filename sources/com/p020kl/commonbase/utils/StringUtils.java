package com.p020kl.commonbase.utils;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BidiOrder;
import com.itextpdf.text.pdf.PdfWriter;
import com.p020kl.commonbase.base.BaseApplication;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/* renamed from: com.kl.commonbase.utils.StringUtils */
public class StringUtils {
    public static String formatUUID(String str) {
        String substring = str.substring(0, 8);
        String substring2 = str.substring(8, 12);
        String substring3 = str.substring(12, 16);
        String substring4 = str.substring(16, 20);
        String substring5 = str.substring(20);
        return substring + "-" + substring2 + "-" + substring3 + "-" + substring4 + "-" + substring5;
    }

    public static String getString(int i) {
        return BaseApplication.getInstance().getResources().getString(i);
    }

    public static <E> String formatString(int i, E e) {
        return formatString(getString(i), e);
    }

    public static <E> String formatString(int i, E e, E e2) {
        return formatString(getString(i), e, e2);
    }

    public static <E> String formatString(String str, E e) {
        return String.format(str, new Object[]{e});
    }

    public static <E> String formatString(String str, E e, E e2) {
        return String.format(str, new Object[]{e, e2});
    }

    public static String removeEscape(String str) {
        return str.replace("\\", "");
    }

    public static boolean checkPassword(String str) {
        return str.length() >= 6 && str.length() <= 16;
    }

    public static boolean isNumber(String str) {
        return isMatches("[0-9]*", str);
    }

    public static boolean isEmail(String str) {
        return isMatches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", str);
    }

    public static boolean isPhone(String str) {
        return PhoneNumberUtils.isGlobalPhoneNumber(str);
    }

    public static boolean isChinaPhone(String str) {
        return str != null && str.matches("^[1][3-9]+\\d{9}");
    }

    public static boolean isMatches(String str, String str2) {
        return str2 != null && str2.length() > 0 && Pattern.matches(str, str2);
    }

    public static String getStringMD5(String str) {
        if (str == null) {
            return "";
        }
        char[] cArr = {'0', '1', PdfWriter.VERSION_1_2, PdfWriter.VERSION_1_3, PdfWriter.VERSION_1_4, PdfWriter.VERSION_1_5, PdfWriter.VERSION_1_6, PdfWriter.VERSION_1_7, '8', '9', 'a', 'b', Barcode128.CODE_AB_TO_C, Barcode128.CODE_AC_TO_B, Barcode128.CODE_BC_TO_A, Barcode128.FNC1_INDEX};
        try {
            byte[] bytes = str.getBytes();
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bytes);
            char[] cArr2 = new char[(r1 * 2)];
            int i = 0;
            for (byte b : instance.digest()) {
                int i2 = i + 1;
                cArr2[i] = cArr[(b >>> 4) & 15];
                i = i2 + 1;
                cArr2[i2] = cArr[b & BidiOrder.f518B];
            }
            return new String(cArr2);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public static int parseFirmwareVersionCode(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        String substring = str.substring(str.indexOf("-") + 2);
        if (TextUtils.isEmpty(substring)) {
            return 0;
        }
        String[] split = substring.split("\\.");
        if (split.length == 3) {
            return (Integer.parseInt(split[1]) * 10) + Integer.parseInt(split[2]);
        }
        return 0;
    }
}
