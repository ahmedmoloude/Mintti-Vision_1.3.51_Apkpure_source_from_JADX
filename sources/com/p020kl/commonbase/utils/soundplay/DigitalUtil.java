package com.p020kl.commonbase.utils.soundplay;

import android.util.Log;

/* renamed from: com.kl.commonbase.utils.soundplay.DigitalUtil */
public class DigitalUtil {
    public static int getDigit(int i) {
        return String.valueOf(i).length();
    }

    public static int getOneDigit(int i) {
        String valueOf = String.valueOf(i);
        int length = valueOf.length() - 1;
        if (Character.isDigit(valueOf.charAt(length))) {
            return Integer.parseInt(String.valueOf(valueOf.charAt(length)));
        }
        return valueOf.charAt(length);
    }

    public static int getTwoDigit(int i) {
        String valueOf = String.valueOf(i);
        int length = valueOf.length();
        StringBuilder sb = new StringBuilder();
        sb.append("数值");
        int i2 = length - 2;
        sb.append(valueOf.charAt(i2));
        Log.d("sound_two", sb.toString());
        if (Character.isDigit(valueOf.charAt(i2))) {
            return Integer.parseInt(String.valueOf(valueOf.charAt(i2)));
        }
        return valueOf.charAt(i2);
    }

    public static int getThreeDigit(int i) {
        String valueOf = String.valueOf(i);
        int length = valueOf.length();
        StringBuilder sb = new StringBuilder();
        sb.append("数值");
        int i2 = length - 3;
        sb.append(valueOf.charAt(i2));
        Log.d("sound_two", sb.toString());
        if (Character.isDigit(valueOf.charAt(i2))) {
            return Integer.parseInt(String.valueOf(valueOf.charAt(i2)));
        }
        return valueOf.charAt(i2);
    }
}
