package com.tencent.bugly.crashreport;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.tencent.bugly.proguard.C2269ao;
import com.tencent.bugly.proguard.C2349p;

/* compiled from: BUGLY */
public class BuglyLog {
    /* renamed from: v */
    public static void m412v(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (C2349p.f1912c) {
            Log.v(str, str2);
        }
        C2269ao.m622a(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, str, str2);
    }

    /* renamed from: d */
    public static void m408d(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (C2349p.f1912c) {
            Log.d(str, str2);
        }
        C2269ao.m622a("D", str, str2);
    }

    /* renamed from: i */
    public static void m411i(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (C2349p.f1912c) {
            Log.i(str, str2);
        }
        C2269ao.m622a("I", str, str2);
    }

    /* renamed from: w */
    public static void m413w(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (C2349p.f1912c) {
            Log.w(str, str2);
        }
        C2269ao.m622a(ExifInterface.LONGITUDE_WEST, str, str2);
    }

    /* renamed from: e */
    public static void m409e(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (C2349p.f1912c) {
            Log.e(str, str2);
        }
        C2269ao.m622a(ExifInterface.LONGITUDE_EAST, str, str2);
    }

    /* renamed from: e */
    public static void m410e(String str, String str2, Throwable th) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (C2349p.f1912c) {
            Log.e(str, str2, th);
        }
        C2269ao.m623a(ExifInterface.LONGITUDE_EAST, str, th);
    }

    public static void setCache(int i) {
        C2269ao.m620a(i);
    }
}
