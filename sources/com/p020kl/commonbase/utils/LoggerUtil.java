package com.p020kl.commonbase.utils;

import com.orhanobut.logger.Logger;

/* renamed from: com.kl.commonbase.utils.LoggerUtil */
public class LoggerUtil {
    public static final int DEBUG = 2;
    public static final int ERROR = 5;
    public static final int INFO = 3;
    public static final int NOTHING = 6;
    public static final int VERBOSE = 1;
    public static final int WARN = 4;
    public static int level = 1;

    /* renamed from: v */
    public static void m116v(String str) {
        if (level <= 1 && str != null) {
            Logger.m389v(str, new Object[0]);
        }
    }

    /* renamed from: d */
    public static void m112d(Object obj) {
        if (level <= 2 && obj != null) {
            Logger.m383d(obj);
        }
    }

    /* renamed from: d */
    public static void m113d(String str, Object obj) {
        if (level <= 2 && obj != null) {
            Logger.m388t(str).mo27696d(obj);
        }
    }

    /* renamed from: i */
    public static void m115i(String str) {
        if (level <= 3 && str != null) {
            Logger.m387i(str, new Object[0]);
        }
    }

    /* renamed from: w */
    public static void m117w(String str) {
        if (level <= 4 && str != null) {
            Logger.m390w(str, new Object[0]);
        }
    }

    /* renamed from: e */
    public static void m114e(String str) {
        int i = level;
    }

    public static void json(String str) {
        if (level <= 5 && str != null) {
            Logger.json(str);
        }
    }
}
