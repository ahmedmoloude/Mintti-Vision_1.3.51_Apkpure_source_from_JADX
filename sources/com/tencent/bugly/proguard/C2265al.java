package com.tencent.bugly.proguard;

import android.util.Log;
import java.util.Locale;

/* renamed from: com.tencent.bugly.proguard.al */
/* compiled from: BUGLY */
public final class C2265al {

    /* renamed from: a */
    public static String f1566a = "CrashReportInfo";

    /* renamed from: b */
    public static String f1567b = "CrashReport";

    /* renamed from: c */
    public static boolean f1568c = false;

    /* renamed from: a */
    private static boolean m601a(int i, String str, Object... objArr) {
        if (!f1568c) {
            return false;
        }
        if (str == null) {
            str = "null";
        } else if (!(objArr == null || objArr.length == 0)) {
            str = String.format(Locale.US, str, objArr);
        }
        if (i == 0) {
            Log.i(f1567b, str);
            return true;
        } else if (i == 1) {
            Log.d(f1567b, str);
            return true;
        } else if (i == 2) {
            Log.w(f1567b, str);
            return true;
        } else if (i == 3) {
            Log.e(f1567b, str);
            return true;
        } else if (i != 5) {
            return false;
        } else {
            Log.i(f1566a, str);
            return true;
        }
    }

    /* renamed from: a */
    private static boolean m602a(int i, Throwable th) {
        if (!f1568c) {
            return false;
        }
        return m601a(i, C2273ap.m646a(th), new Object[0]);
    }

    /* renamed from: a */
    public static boolean m604a(String str, Object... objArr) {
        return m601a(0, str, objArr);
    }

    /* renamed from: a */
    public static boolean m603a(Class cls, String str, Object... objArr) {
        return m601a(0, String.format(Locale.US, "[%s] %s", new Object[]{cls.getSimpleName(), str}), objArr);
    }

    /* renamed from: b */
    public static boolean m607b(String str, Object... objArr) {
        return m601a(5, str, objArr);
    }

    /* renamed from: c */
    public static boolean m609c(String str, Object... objArr) {
        return m601a(1, str, objArr);
    }

    /* renamed from: b */
    public static boolean m606b(Class cls, String str, Object... objArr) {
        return m601a(1, String.format(Locale.US, "[%s] %s", new Object[]{cls.getSimpleName(), str}), objArr);
    }

    /* renamed from: d */
    public static boolean m610d(String str, Object... objArr) {
        return m601a(2, str, objArr);
    }

    /* renamed from: a */
    public static boolean m605a(Throwable th) {
        return m602a(2, th);
    }

    /* renamed from: e */
    public static boolean m611e(String str, Object... objArr) {
        return m601a(3, str, objArr);
    }

    /* renamed from: b */
    public static boolean m608b(Throwable th) {
        return m602a(3, th);
    }
}
