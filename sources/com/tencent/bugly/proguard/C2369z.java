package com.tencent.bugly.proguard;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.z */
/* compiled from: BUGLY */
public final class C2369z {

    /* renamed from: a */
    public static final String[] f1995a = "@buglyAllChannel@".split(",");

    /* renamed from: b */
    public static final String[] f1996b = "@buglyAllChannelPriority@".split(",");

    /* renamed from: c */
    private static ActivityManager f1997c;

    /* renamed from: a */
    public static String m1059a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return context.getPackageName();
        } catch (Throwable th) {
            if (C2265al.m605a(th)) {
                return "fail";
            }
            th.printStackTrace();
            return "fail";
        }
    }

    /* renamed from: b */
    public static PackageInfo m1063b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(m1059a(context), 0);
        } catch (Throwable th) {
            if (C2265al.m605a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0044 A[Catch:{ all -> 0x0051, all -> 0x0057 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004d A[SYNTHETIC, Splitter:B:24:0x004d] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m1058a(int r7) {
        /*
            r0 = 0
            java.io.FileReader r1 = new java.io.FileReader     // Catch:{ all -> 0x003a }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x003a }
            java.lang.String r3 = "/proc/"
            r2.<init>(r3)     // Catch:{ all -> 0x003a }
            r2.append(r7)     // Catch:{ all -> 0x003a }
            java.lang.String r3 = "/cmdline"
            r2.append(r3)     // Catch:{ all -> 0x003a }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x003a }
            r1.<init>(r2)     // Catch:{ all -> 0x003a }
            r0 = 512(0x200, float:7.175E-43)
            char[] r2 = new char[r0]     // Catch:{ all -> 0x0038 }
            r1.read(r2)     // Catch:{ all -> 0x0038 }
            r3 = 0
            r4 = 0
        L_0x0022:
            if (r4 >= r0) goto L_0x002b
            char r5 = r2[r4]     // Catch:{ all -> 0x0038 }
            if (r5 == 0) goto L_0x002b
            int r4 = r4 + 1
            goto L_0x0022
        L_0x002b:
            java.lang.String r0 = new java.lang.String     // Catch:{ all -> 0x0038 }
            r0.<init>(r2)     // Catch:{ all -> 0x0038 }
            java.lang.String r7 = r0.substring(r3, r4)     // Catch:{ all -> 0x0038 }
            r1.close()     // Catch:{ all -> 0x0037 }
        L_0x0037:
            return r7
        L_0x0038:
            r0 = move-exception
            goto L_0x003e
        L_0x003a:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x003e:
            boolean r2 = com.tencent.bugly.proguard.C2265al.m605a(r0)     // Catch:{ all -> 0x0051 }
            if (r2 != 0) goto L_0x0047
            r0.printStackTrace()     // Catch:{ all -> 0x0051 }
        L_0x0047:
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x0051 }
            if (r1 == 0) goto L_0x0050
            r1.close()     // Catch:{ all -> 0x0050 }
        L_0x0050:
            return r7
        L_0x0051:
            r7 = move-exception
            if (r1 == 0) goto L_0x0057
            r1.close()     // Catch:{ all -> 0x0057 }
        L_0x0057:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2369z.m1058a(int):java.lang.String");
    }

    /* renamed from: c */
    public static String m1064c(Context context) {
        CharSequence applicationLabel;
        if (context == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (!(packageManager == null || applicationInfo == null || (applicationLabel = packageManager.getApplicationLabel(applicationInfo)) == null)) {
                return applicationLabel.toString();
            }
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
        }
        return null;
    }

    /* renamed from: a */
    public static boolean m1061a() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Field declaredField = cls.getDeclaredField("sCurrentActivityThread");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(cls);
            Field declaredField2 = cls.getDeclaredField("mActivities");
            declaredField2.setAccessible(true);
            for (Map.Entry value : ((Map) declaredField2.get(obj)).entrySet()) {
                Field declaredField3 = Class.forName("android.app.ActivityThread$ActivityClientRecord").getDeclaredField("activity");
                declaredField3.setAccessible(true);
                Field declaredField4 = Activity.class.getDeclaredField("mResumed");
                declaredField4.setAccessible(true);
                if (((Boolean) declaredField4.get((Activity) declaredField3.get(value.getValue()))).booleanValue()) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            C2265al.m608b(th);
            return true;
        }
    }

    /* renamed from: a */
    public static boolean m1062a(ActivityManager activityManager) {
        if (activityManager == null) {
            C2265al.m609c("is proc running, ActivityManager is null", new Object[0]);
            return true;
        }
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        if (runningAppProcesses == null) {
            C2265al.m609c("running proc info list is empty, my proc not running.", new Object[0]);
            return false;
        }
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == myPid) {
                C2265al.m609c("my proc is running.", new Object[0]);
                return true;
            }
        }
        C2265al.m609c("proc not in running proc info list, my proc not running.", new Object[0]);
        return false;
    }

    /* renamed from: d */
    public static Map<String, String> m1065d(Context context) {
        if (context == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData == null) {
                return null;
            }
            HashMap hashMap = new HashMap();
            Object obj = applicationInfo.metaData.get("BUGLY_DISABLE");
            if (obj != null) {
                hashMap.put("BUGLY_DISABLE", obj.toString());
            }
            Object obj2 = applicationInfo.metaData.get("BUGLY_APPID");
            if (obj2 != null) {
                hashMap.put("BUGLY_APPID", obj2.toString());
            }
            Object obj3 = applicationInfo.metaData.get("BUGLY_APP_CHANNEL");
            if (obj3 != null) {
                hashMap.put("BUGLY_APP_CHANNEL", obj3.toString());
            }
            Object obj4 = applicationInfo.metaData.get("BUGLY_APP_VERSION");
            if (obj4 != null) {
                hashMap.put("BUGLY_APP_VERSION", obj4.toString());
            }
            Object obj5 = applicationInfo.metaData.get("BUGLY_ENABLE_DEBUG");
            if (obj5 != null) {
                hashMap.put("BUGLY_ENABLE_DEBUG", obj5.toString());
            }
            Object obj6 = applicationInfo.metaData.get("com.tencent.rdm.uuid");
            if (obj6 != null) {
                hashMap.put("com.tencent.rdm.uuid", obj6.toString());
            }
            Object obj7 = applicationInfo.metaData.get("BUGLY_APP_BUILD_NO");
            if (obj7 != null) {
                hashMap.put("BUGLY_APP_BUILD_NO", obj7.toString());
            }
            Object obj8 = applicationInfo.metaData.get("BUGLY_AREA");
            if (obj8 != null) {
                hashMap.put("BUGLY_AREA", obj8.toString());
            }
            return hashMap;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* renamed from: a */
    public static List<String> m1060a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        try {
            String str = map.get("BUGLY_DISABLE");
            if (str != null) {
                if (str.length() != 0) {
                    String[] split = str.split(",");
                    for (int i = 0; i < split.length; i++) {
                        split[i] = split[i].trim();
                    }
                    return Arrays.asList(split);
                }
            }
            return null;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* renamed from: e */
    public static boolean m1066e(Context context) {
        if (context == null) {
            return false;
        }
        if (f1997c == null) {
            f1997c = (ActivityManager) context.getSystemService("activity");
        }
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            f1997c.getMemoryInfo(memoryInfo);
            if (!memoryInfo.lowMemory) {
                return false;
            }
            C2265al.m609c("Memory is low.", new Object[0]);
            return true;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }
}
