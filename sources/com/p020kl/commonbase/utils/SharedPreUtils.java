package com.p020kl.commonbase.utils;

import android.content.SharedPreferences;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.constants.Constants;

/* renamed from: com.kl.commonbase.utils.SharedPreUtils */
public class SharedPreUtils {
    public static void setSp(String str, long j) {
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.putLong(str, j);
        edit.apply();
    }

    public static void setSp(String str, float f) {
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.putFloat(str, f);
        edit.apply();
    }

    public static void setSp(String str, int i) {
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public static void setSp(String str, boolean z) {
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public static void setSp(String str, String str2) {
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static void setSp(String[] strArr, String[] strArr2) {
        SharedPreferences.Editor edit = getPreferences().edit();
        for (int i = 0; i < strArr.length; i++) {
            edit.putString(strArr[i], strArr2[i]);
        }
        edit.apply();
    }

    public static boolean getSp(String str, boolean z) {
        return getPreferences().getBoolean(str, z);
    }

    public static String getSp(String str, String str2) {
        return getPreferences().getString(str, str2);
    }

    public static int getSp(String str, int i) {
        return getPreferences().getInt(str, i);
    }

    public static long getSp(String str, long j) {
        return getPreferences().getLong(str, j);
    }

    public static float getSp(String str, float f) {
        return getPreferences().getFloat(str, f);
    }

    public static String[] getSp(String[] strArr, String[] strArr2) {
        int length = strArr.length;
        String[] strArr3 = new String[length];
        for (int i = 0; i < length; i++) {
            strArr3[i] = getPreferences().getString(strArr[i], strArr2[i]);
        }
        return strArr3;
    }

    public static void clear() {
        getPreferences().edit().clear().apply();
    }

    public static SharedPreferences getPreferences() {
        return BaseApplication.getInstance().getSharedPreferences(Constants.SP_INFO_FILE, 4);
    }
}
