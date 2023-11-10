package com.mintti.visionsdk.common;

import android.util.Log;

public class LogUtils {
    public static final int DEBUG = 2;
    public static final int ERROR = 5;
    public static final int INFO = 3;
    public static final int NOTHING = 6;
    public static final int VERBOSE = 1;
    public static final int WARN = 4;
    public static int level = 1;

    /* renamed from: d */
    public static void m378d(String str, String str2) {
        if (level <= 2 && str2 != null) {
            Log.d(str, str2);
        }
    }

    /* renamed from: e */
    public static void m379e(String str, String str2) {
        if (level <= 5 && str2 != null) {
            Log.e(str, str2);
        }
    }

    /* renamed from: i */
    public static void m380i(String str, String str2) {
        if (level <= 3 && str2 != null) {
            Log.i(str, str2);
        }
    }

    /* renamed from: v */
    public static void m381v(String str, String str2) {
        if (level <= 1 && str2 != null) {
            Log.v(str, str2);
        }
    }

    /* renamed from: w */
    public static void m382w(String str, String str2) {
        if (level <= 4 && str2 != null) {
            Log.w(str, str2);
        }
    }
}
