package org.greenrobot.greendao;

import android.util.Log;

public class DaoLog {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    private static final String TAG = "greenDAO";
    public static final int VERBOSE = 2;
    public static final int WARN = 5;

    public static boolean isLoggable(int i) {
        return Log.isLoggable(TAG, i);
    }

    public static String getStackTraceString(Throwable th) {
        return Log.getStackTraceString(th);
    }

    public static int println(int i, String str) {
        return Log.println(i, TAG, str);
    }

    /* renamed from: v */
    public static int m1119v(String str) {
        return Log.v(TAG, str);
    }

    /* renamed from: v */
    public static int m1120v(String str, Throwable th) {
        return Log.v(TAG, str, th);
    }

    /* renamed from: d */
    public static int m1113d(String str) {
        return Log.d(TAG, str);
    }

    /* renamed from: d */
    public static int m1114d(String str, Throwable th) {
        return Log.d(TAG, str, th);
    }

    /* renamed from: i */
    public static int m1117i(String str) {
        return Log.i(TAG, str);
    }

    /* renamed from: i */
    public static int m1118i(String str, Throwable th) {
        return Log.i(TAG, str, th);
    }

    /* renamed from: w */
    public static int m1121w(String str) {
        return Log.w(TAG, str);
    }

    /* renamed from: w */
    public static int m1122w(String str, Throwable th) {
        return Log.w(TAG, str, th);
    }

    /* renamed from: w */
    public static int m1123w(Throwable th) {
        return Log.w(TAG, th);
    }

    /* renamed from: e */
    public static int m1115e(String str) {
        return Log.w(TAG, str);
    }

    /* renamed from: e */
    public static int m1116e(String str, Throwable th) {
        return Log.e(TAG, str, th);
    }
}
