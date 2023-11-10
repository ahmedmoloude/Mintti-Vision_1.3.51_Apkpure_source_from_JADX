package com.itextpdf.text.factories;

import com.itextpdf.text.error_messages.MessageLocalization;

public class RomanAlphabetFactory {
    public static final String getString(int i) {
        int i2 = 0;
        int i3 = 1;
        if (i >= 1) {
            int i4 = i - 1;
            int i5 = 26;
            while (true) {
                int i6 = i5 + i2;
                if (i4 < i6) {
                    break;
                }
                i3++;
                i5 *= 26;
                i2 = i6;
            }
            int i7 = i4 - i2;
            char[] cArr = new char[i3];
            while (i3 > 0) {
                i3--;
                cArr[i3] = (char) ((i7 % 26) + 97);
                i7 /= 26;
            }
            return new String(cArr);
        }
        throw new NumberFormatException(MessageLocalization.getComposedMessage("you.can.t.translate.a.negative.number.into.an.alphabetical.value", new Object[0]));
    }

    public static final String getLowerCaseString(int i) {
        return getString(i);
    }

    public static final String getUpperCaseString(int i) {
        return getString(i).toUpperCase();
    }

    public static final String getString(int i, boolean z) {
        if (z) {
            return getLowerCaseString(i);
        }
        return getUpperCaseString(i);
    }
}
