package com.itextpdf.awt.geom.misc;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
    private static ResourceBundle bundle;

    public static String getString(String str) {
        ResourceBundle resourceBundle = bundle;
        if (resourceBundle == null) {
            return str;
        }
        try {
            return resourceBundle.getString(str);
        } catch (MissingResourceException unused) {
            return "Missing message: " + str;
        }
    }

    public static String getString(String str, Object obj) {
        return getString(str, new Object[]{obj});
    }

    public static String getString(String str, int i) {
        return getString(str, new Object[]{Integer.toString(i)});
    }

    public static String getString(String str, char c) {
        return getString(str, new Object[]{String.valueOf(c)});
    }

    public static String getString(String str, Object obj, Object obj2) {
        return getString(str, new Object[]{obj, obj2});
    }

    public static String getString(String str, Object[] objArr) {
        ResourceBundle resourceBundle = bundle;
        if (resourceBundle != null) {
            try {
                str = resourceBundle.getString(str);
            } catch (MissingResourceException unused) {
            }
        }
        return format(str, objArr);
    }

    public static String format(String str, Object[] objArr) {
        int i;
        StringBuilder sb = new StringBuilder(str.length() + (objArr.length * 20));
        int length = objArr.length;
        String[] strArr = new String[length];
        int i2 = 0;
        for (int i3 = 0; i3 < objArr.length; i3++) {
            if (objArr[i3] == null) {
                strArr[i3] = "<null>";
            } else {
                strArr[i3] = objArr[i3].toString();
            }
        }
        while (true) {
            int indexOf = str.indexOf(123, i2);
            if (indexOf < 0) {
                break;
            }
            if (indexOf != 0) {
                int i4 = indexOf - 1;
                if (str.charAt(i4) == '\\') {
                    if (indexOf != 1) {
                        sb.append(str.substring(i2, i4));
                    }
                    sb.append('{');
                    i = indexOf + 1;
                    i2 = i;
                }
            }
            if (indexOf > str.length() - 3) {
                sb.append(str.substring(i2, str.length()));
                i2 = str.length();
            } else {
                int i5 = indexOf + 1;
                byte digit = (byte) Character.digit(str.charAt(i5), 10);
                if (digit < 0 || str.charAt(indexOf + 2) != '}') {
                    sb.append(str.substring(i2, i5));
                    i2 = i5;
                } else {
                    sb.append(str.substring(i2, indexOf));
                    if (digit >= length) {
                        sb.append("<missing argument>");
                    } else {
                        sb.append(strArr[digit]);
                    }
                    i = indexOf + 3;
                    i2 = i;
                }
            }
        }
        if (i2 < str.length()) {
            sb.append(str.substring(i2, str.length()));
        }
        return sb.toString();
    }
}
