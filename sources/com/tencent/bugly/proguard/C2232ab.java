package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.support.p005v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.ab */
/* compiled from: BUGLY */
public final class C2232ab {

    /* renamed from: a */
    private static final ArrayList<C2235a> f1495a = new ArrayList<C2235a>() {
        {
            add(new C2246l((byte) 0));
            add(new C2240f((byte) 0));
            add(new C2241g((byte) 0));
            add(new C2247m((byte) 0));
            add(new C2242h((byte) 0));
            add(new C2243i((byte) 0));
            add(new C2245k((byte) 0));
            add(new C2239e((byte) 0));
            add(new C2244j((byte) 0));
            add(new C2236b((byte) 0));
            add(new C2238d((byte) 0));
            add(new C2237c((byte) 0));
        }
    };

    /* renamed from: b */
    private static final Map<Integer, String> f1496b = new HashMap<Integer, String>() {
        {
            put(1, "GPRS");
            put(2, "EDGE");
            put(3, "UMTS");
            put(8, "HSDPA");
            put(9, "HSUPA");
            put(10, "HSPA");
            put(4, "CDMA");
            put(5, "EVDO_0");
            put(6, "EVDO_A");
            put(7, "1xRTT");
            put(11, "iDen");
            put(12, "EVDO_B");
            put(13, "LTE");
            put(14, "eHRPD");
            put(15, "HSPA+");
        }
    };

    /* renamed from: c */
    private static final String[] f1497c = {"/su", "/su/bin/su", "/sbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/data/local/su", "/system/xbin/su", "/system/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/system/bin/cufsdosck", "/system/xbin/cufsdosck", "/system/bin/cufsmgr", "/system/xbin/cufsmgr", "/system/bin/cufaevdd", "/system/xbin/cufaevdd", "/system/bin/conbb", "/system/xbin/conbb"};

    /* renamed from: m */
    public static String m514m() {
        return "";
    }

    /* renamed from: a */
    public static String m496a() {
        try {
            return Build.MODEL;
        } catch (Throwable th) {
            if (C2265al.m605a(th)) {
                return "fail";
            }
            th.printStackTrace();
            return "fail";
        }
    }

    /* renamed from: b */
    public static String m499b() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Throwable th) {
            if (C2265al.m605a(th)) {
                return "fail";
            }
            th.printStackTrace();
            return "fail";
        }
    }

    /* renamed from: c */
    public static int m500c() {
        try {
            return Build.VERSION.SDK_INT;
        } catch (Throwable th) {
            if (C2265al.m605a(th)) {
                return -1;
            }
            th.printStackTrace();
            return -1;
        }
    }

    /* renamed from: p */
    private static boolean m517p() {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            if (C2265al.m605a(th)) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    /* renamed from: d */
    public static String m502d() {
        try {
            return String.valueOf(System.getProperty("os.arch"));
        } catch (Throwable th) {
            if (C2265al.m605a(th)) {
                return "fail";
            }
            th.printStackTrace();
            return "fail";
        }
    }

    /* renamed from: a */
    public static String m497a(Context context) {
        if (!(context == null || context.getApplicationInfo() == null)) {
            String str = context.getApplicationInfo().nativeLibraryDir;
            if (TextUtils.isEmpty(str)) {
                return "fail";
            }
            if (str.endsWith("arm")) {
                return "armeabi-v7a";
            }
            if (str.endsWith("arm64")) {
                return "arm64-v8a";
            }
            if (str.endsWith("x86")) {
                return "x86";
            }
            if (str.endsWith("x86_64")) {
                return "x86_64";
            }
        }
        return "fail";
    }

    /* renamed from: e */
    public static long m504e() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return -1;
        }
    }

    /* renamed from: f */
    public static long m506f() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return -1;
        }
    }

    /* renamed from: b */
    public static long m498b(Context context) {
        long pss;
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                return 0;
            }
            pss = (long) activityManager.getProcessMemoryInfo(new int[]{Process.myPid()})[0].getTotalPss();
            return pss * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        } catch (Throwable unused) {
            pss = Debug.getPss();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0043 A[SYNTHETIC, Splitter:B:20:0x0043] */
    /* renamed from: g */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long m508g() {
        /*
            r0 = 0
            r2 = 0
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ all -> 0x003a }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ all -> 0x003a }
            java.lang.String r5 = "/proc/self/status"
            r4.<init>(r5)     // Catch:{ all -> 0x003a }
            r3.<init>(r4)     // Catch:{ all -> 0x003a }
            java.lang.String r2 = r3.readLine()     // Catch:{ all -> 0x0038 }
        L_0x0013:
            if (r2 == 0) goto L_0x002f
            java.lang.String r4 = "VmSize"
            boolean r4 = r2.startsWith(r4)     // Catch:{ all -> 0x0038 }
            if (r4 == 0) goto L_0x002a
            java.lang.String r4 = "[^\\d]"
            java.lang.String r5 = ""
            java.lang.String r2 = r2.replaceAll(r4, r5)     // Catch:{ all -> 0x0038 }
            long r0 = java.lang.Long.parseLong(r2)     // Catch:{ all -> 0x0038 }
            goto L_0x002f
        L_0x002a:
            java.lang.String r2 = r3.readLine()     // Catch:{ all -> 0x0038 }
            goto L_0x0013
        L_0x002f:
            r3.close()     // Catch:{ all -> 0x0033 }
            goto L_0x0046
        L_0x0033:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x0046
        L_0x0038:
            r2 = move-exception
            goto L_0x003e
        L_0x003a:
            r3 = move-exception
            r6 = r3
            r3 = r2
            r2 = r6
        L_0x003e:
            com.tencent.bugly.proguard.C2265al.m605a(r2)     // Catch:{ all -> 0x004b }
            if (r3 == 0) goto L_0x0046
            r3.close()     // Catch:{ all -> 0x0033 }
        L_0x0046:
            r2 = 1024(0x400, double:5.06E-321)
            long r0 = r0 * r2
            return r0
        L_0x004b:
            r0 = move-exception
            if (r3 == 0) goto L_0x0056
            r3.close()     // Catch:{ all -> 0x0052 }
            goto L_0x0056
        L_0x0052:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0056:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2232ab.m508g():long");
    }

    /* renamed from: h */
    public static long m509h() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: java.io.BufferedReader} */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0084 A[Catch:{ all -> 0x00aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0089 A[SYNTHETIC, Splitter:B:47:0x0089] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0099 A[SYNTHETIC, Splitter:B:54:0x0099] */
    /* JADX WARNING: Removed duplicated region for block: B:80:? A[RETURN, SYNTHETIC] */
    /* renamed from: i */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long m510i() {
        /*
            java.lang.String r0 = "/proc/meminfo"
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ all -> 0x007a }
            r2.<init>(r0)     // Catch:{ all -> 0x007a }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ all -> 0x0075 }
            r3 = 2048(0x800, float:2.87E-42)
            r0.<init>(r2, r3)     // Catch:{ all -> 0x0075 }
            java.lang.String r1 = r0.readLine()     // Catch:{ all -> 0x0073 }
            if (r1 != 0) goto L_0x0034
            r0.close()     // Catch:{ IOException -> 0x0019 }
            goto L_0x0023
        L_0x0019:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x0023
            r0.printStackTrace()
        L_0x0023:
            r2.close()     // Catch:{ IOException -> 0x0027 }
            goto L_0x0031
        L_0x0027:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x0031
            r0.printStackTrace()
        L_0x0031:
            r0 = -1
            return r0
        L_0x0034:
            java.lang.String r3 = ":\\s+"
            r4 = 2
            java.lang.String[] r1 = r1.split(r3, r4)     // Catch:{ all -> 0x0073 }
            r3 = 1
            r1 = r1[r3]     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = r1.toLowerCase()     // Catch:{ all -> 0x0073 }
            java.lang.String r3 = "kb"
            java.lang.String r4 = ""
            java.lang.String r1 = r1.replace(r3, r4)     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = r1.trim()     // Catch:{ all -> 0x0073 }
            long r3 = java.lang.Long.parseLong(r1)     // Catch:{ all -> 0x0073 }
            r5 = 1024(0x400, double:5.06E-321)
            long r3 = r3 * r5
            r0.close()     // Catch:{ IOException -> 0x005a }
            goto L_0x0064
        L_0x005a:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x0064
            r0.printStackTrace()
        L_0x0064:
            r2.close()     // Catch:{ IOException -> 0x0068 }
            goto L_0x0072
        L_0x0068:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x0072
            r0.printStackTrace()
        L_0x0072:
            return r3
        L_0x0073:
            r1 = move-exception
            goto L_0x007e
        L_0x0075:
            r0 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x007e
        L_0x007a:
            r0 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
        L_0x007e:
            boolean r3 = com.tencent.bugly.proguard.C2265al.m605a(r1)     // Catch:{ all -> 0x00aa }
            if (r3 != 0) goto L_0x0087
            r1.printStackTrace()     // Catch:{ all -> 0x00aa }
        L_0x0087:
            if (r0 == 0) goto L_0x0097
            r0.close()     // Catch:{ IOException -> 0x008d }
            goto L_0x0097
        L_0x008d:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x0097
            r0.printStackTrace()
        L_0x0097:
            if (r2 == 0) goto L_0x00a7
            r2.close()     // Catch:{ IOException -> 0x009d }
            goto L_0x00a7
        L_0x009d:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x00a7
            r0.printStackTrace()
        L_0x00a7:
            r0 = -2
            return r0
        L_0x00aa:
            r1 = move-exception
            if (r0 == 0) goto L_0x00bb
            r0.close()     // Catch:{ IOException -> 0x00b1 }
            goto L_0x00bb
        L_0x00b1:
            r0 = move-exception
            boolean r3 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r3 != 0) goto L_0x00bb
            r0.printStackTrace()
        L_0x00bb:
            if (r2 == 0) goto L_0x00cb
            r2.close()     // Catch:{ IOException -> 0x00c1 }
            goto L_0x00cb
        L_0x00c1:
            r0 = move-exception
            boolean r2 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r2 != 0) goto L_0x00cb
            r0.printStackTrace()
        L_0x00cb:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2232ab.m510i():long");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: java.io.FileReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: java.io.FileReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: java.io.FileReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: java.io.FileReader} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:114:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0101 A[Catch:{ all -> 0x0127 }] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0106 A[SYNTHETIC, Splitter:B:84:0x0106] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0116 A[SYNTHETIC, Splitter:B:91:0x0116] */
    /* renamed from: j */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long m511j() {
        /*
            java.lang.String r0 = ""
            java.lang.String r1 = "kb"
            java.lang.String r2 = ":\\s+"
            java.lang.String r3 = "/proc/meminfo"
            r4 = 0
            java.io.FileReader r5 = new java.io.FileReader     // Catch:{ all -> 0x00f9 }
            r5.<init>(r3)     // Catch:{ all -> 0x00f9 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ all -> 0x00f7 }
            r6 = 2048(0x800, float:2.87E-42)
            r3.<init>(r5, r6)     // Catch:{ all -> 0x00f7 }
            r3.readLine()     // Catch:{ all -> 0x00f4 }
            java.lang.String r4 = r3.readLine()     // Catch:{ all -> 0x00f4 }
            r6 = -1
            if (r4 != 0) goto L_0x003d
            r3.close()     // Catch:{ IOException -> 0x0024 }
            goto L_0x002e
        L_0x0024:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x002e
            r0.printStackTrace()
        L_0x002e:
            r5.close()     // Catch:{ IOException -> 0x0032 }
            goto L_0x003c
        L_0x0032:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x003c
            r0.printStackTrace()
        L_0x003c:
            return r6
        L_0x003d:
            r8 = 2
            java.lang.String[] r4 = r4.split(r2, r8)     // Catch:{ all -> 0x00f4 }
            r9 = 1
            r4 = r4[r9]     // Catch:{ all -> 0x00f4 }
            java.lang.String r4 = r4.toLowerCase()     // Catch:{ all -> 0x00f4 }
            java.lang.String r4 = r4.replace(r1, r0)     // Catch:{ all -> 0x00f4 }
            java.lang.String r4 = r4.trim()     // Catch:{ all -> 0x00f4 }
            r10 = 0
            long r12 = java.lang.Long.parseLong(r4)     // Catch:{ all -> 0x00f4 }
            r14 = 1024(0x400, double:5.06E-321)
            long r12 = r12 * r14
            long r12 = r12 + r10
            java.lang.String r4 = r3.readLine()     // Catch:{ all -> 0x00f4 }
            if (r4 != 0) goto L_0x007f
            r3.close()     // Catch:{ IOException -> 0x0066 }
            goto L_0x0070
        L_0x0066:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x0070
            r0.printStackTrace()
        L_0x0070:
            r5.close()     // Catch:{ IOException -> 0x0074 }
            goto L_0x007e
        L_0x0074:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x007e
            r0.printStackTrace()
        L_0x007e:
            return r6
        L_0x007f:
            java.lang.String[] r4 = r4.split(r2, r8)     // Catch:{ all -> 0x00f4 }
            r4 = r4[r9]     // Catch:{ all -> 0x00f4 }
            java.lang.String r4 = r4.toLowerCase()     // Catch:{ all -> 0x00f4 }
            java.lang.String r4 = r4.replace(r1, r0)     // Catch:{ all -> 0x00f4 }
            java.lang.String r4 = r4.trim()     // Catch:{ all -> 0x00f4 }
            long r10 = java.lang.Long.parseLong(r4)     // Catch:{ all -> 0x00f4 }
            java.lang.Long.signum(r10)
            long r10 = r10 * r14
            long r12 = r12 + r10
            java.lang.String r4 = r3.readLine()     // Catch:{ all -> 0x00f4 }
            if (r4 != 0) goto L_0x00be
            r3.close()     // Catch:{ IOException -> 0x00a5 }
            goto L_0x00af
        L_0x00a5:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x00af
            r0.printStackTrace()
        L_0x00af:
            r5.close()     // Catch:{ IOException -> 0x00b3 }
            goto L_0x00bd
        L_0x00b3:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x00bd
            r0.printStackTrace()
        L_0x00bd:
            return r6
        L_0x00be:
            java.lang.String[] r2 = r4.split(r2, r8)     // Catch:{ all -> 0x00f4 }
            r2 = r2[r9]     // Catch:{ all -> 0x00f4 }
            java.lang.String r2 = r2.toLowerCase()     // Catch:{ all -> 0x00f4 }
            java.lang.String r0 = r2.replace(r1, r0)     // Catch:{ all -> 0x00f4 }
            java.lang.String r0 = r0.trim()     // Catch:{ all -> 0x00f4 }
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ all -> 0x00f4 }
            long r0 = r0 * r14
            long r12 = r12 + r0
            r3.close()     // Catch:{ IOException -> 0x00db }
            goto L_0x00e5
        L_0x00db:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x00e5
            r0.printStackTrace()
        L_0x00e5:
            r5.close()     // Catch:{ IOException -> 0x00e9 }
            goto L_0x00f3
        L_0x00e9:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x00f3
            r0.printStackTrace()
        L_0x00f3:
            return r12
        L_0x00f4:
            r0 = move-exception
            r4 = r3
            goto L_0x00fb
        L_0x00f7:
            r0 = move-exception
            goto L_0x00fb
        L_0x00f9:
            r0 = move-exception
            r5 = r4
        L_0x00fb:
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)     // Catch:{ all -> 0x0127 }
            if (r1 != 0) goto L_0x0104
            r0.printStackTrace()     // Catch:{ all -> 0x0127 }
        L_0x0104:
            if (r4 == 0) goto L_0x0114
            r4.close()     // Catch:{ IOException -> 0x010a }
            goto L_0x0114
        L_0x010a:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x0114
            r0.printStackTrace()
        L_0x0114:
            if (r5 == 0) goto L_0x0124
            r5.close()     // Catch:{ IOException -> 0x011a }
            goto L_0x0124
        L_0x011a:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x0124
            r0.printStackTrace()
        L_0x0124:
            r0 = -2
            return r0
        L_0x0127:
            r0 = move-exception
            if (r4 == 0) goto L_0x0138
            r4.close()     // Catch:{ IOException -> 0x012e }
            goto L_0x0138
        L_0x012e:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.C2265al.m605a(r1)
            if (r2 != 0) goto L_0x0138
            r1.printStackTrace()
        L_0x0138:
            if (r5 == 0) goto L_0x0148
            r5.close()     // Catch:{ IOException -> 0x013e }
            goto L_0x0148
        L_0x013e:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.C2265al.m605a(r1)
            if (r2 != 0) goto L_0x0148
            r1.printStackTrace()
        L_0x0148:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2232ab.m511j():long");
    }

    /* renamed from: k */
    public static long m512k() {
        if (!m517p()) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (C2265al.m605a(th)) {
                return -2;
            }
            th.printStackTrace();
            return -2;
        }
    }

    /* renamed from: l */
    public static long m513l() {
        if (!m517p()) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (C2265al.m605a(th)) {
                return -2;
            }
            th.printStackTrace();
            return -2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x005e  */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m501c(android.content.Context r4) {
        /*
            java.lang.String r0 = "unknown"
            java.lang.String r1 = "connectivity"
            java.lang.Object r1 = r4.getSystemService(r1)     // Catch:{ Exception -> 0x0057 }
            android.net.ConnectivityManager r1 = (android.net.ConnectivityManager) r1     // Catch:{ Exception -> 0x0057 }
            android.net.NetworkInfo r1 = r1.getActiveNetworkInfo()     // Catch:{ Exception -> 0x0057 }
            if (r1 != 0) goto L_0x0012
            r4 = 0
            return r4
        L_0x0012:
            int r2 = r1.getType()     // Catch:{ Exception -> 0x0057 }
            r3 = 1
            if (r2 != r3) goto L_0x001c
            java.lang.String r4 = "WIFI"
            goto L_0x0062
        L_0x001c:
            int r1 = r1.getType()     // Catch:{ Exception -> 0x0057 }
            if (r1 != 0) goto L_0x0061
            java.lang.String r1 = "phone"
            java.lang.Object r4 = r4.getSystemService(r1)     // Catch:{ Exception -> 0x0057 }
            android.telephony.TelephonyManager r4 = (android.telephony.TelephonyManager) r4     // Catch:{ Exception -> 0x0057 }
            if (r4 == 0) goto L_0x0061
            int r4 = r4.getNetworkType()     // Catch:{ Exception -> 0x0057 }
            java.util.Map<java.lang.Integer, java.lang.String> r1 = f1496b     // Catch:{ Exception -> 0x0057 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x0057 }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ Exception -> 0x0057 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0057 }
            if (r1 != 0) goto L_0x0055
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0052 }
            java.lang.String r2 = "MOBILE("
            r0.<init>(r2)     // Catch:{ Exception -> 0x0052 }
            r0.append(r4)     // Catch:{ Exception -> 0x0052 }
            java.lang.String r4 = ")"
            r0.append(r4)     // Catch:{ Exception -> 0x0052 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0052 }
            goto L_0x0061
        L_0x0052:
            r4 = move-exception
            r0 = r1
            goto L_0x0058
        L_0x0055:
            r0 = r1
            goto L_0x0061
        L_0x0057:
            r4 = move-exception
        L_0x0058:
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r4)
            if (r1 != 0) goto L_0x0061
            r4.printStackTrace()
        L_0x0061:
            r4 = r0
        L_0x0062:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2232ab.m501c(android.content.Context):java.lang.String");
    }

    /* renamed from: d */
    public static String m503d(Context context) {
        Iterator<C2235a> it = f1495a.iterator();
        while (it.hasNext()) {
            String a = it.next().mo27992a(context);
            if (!TextUtils.isEmpty(a)) {
                return a;
            }
        }
        return null;
    }

    /* renamed from: e */
    public static boolean m505e(Context context) {
        if (TextUtils.isEmpty(new C2243i((byte) 0).mo27992a(context))) {
            return false;
        }
        return true;
    }

    /* renamed from: f */
    public static boolean m507f(Context context) {
        if (TextUtils.isEmpty(new C2245k((byte) 0).mo27992a(context))) {
            return false;
        }
        return true;
    }

    /* renamed from: n */
    public static boolean m515n() {
        boolean z;
        String[] strArr = f1497c;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            } else if (new File(strArr[i]).exists()) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        return (Build.TAGS != null && Build.TAGS.contains("test-keys")) || z;
    }

    /* renamed from: o */
    public static boolean m516o() {
        float maxMemory = (float) ((((double) Runtime.getRuntime().maxMemory()) * 1.0d) / 1048576.0d);
        float f = (float) ((((double) Runtime.getRuntime().totalMemory()) * 1.0d) / 1048576.0d);
        float f2 = maxMemory - f;
        C2265al.m609c("maxMemory : %f", Float.valueOf(maxMemory));
        C2265al.m609c("totalMemory : %f", Float.valueOf(f));
        C2265al.m609c("freeMemory : %f", Float.valueOf(f2));
        if (f2 < 10.0f) {
            return true;
        }
        return false;
    }

    /* renamed from: com.tencent.bugly.proguard.ab$a */
    /* compiled from: BUGLY */
    static abstract class C2235a {
        /* renamed from: a */
        public abstract String mo27992a(Context context);

        private C2235a() {
        }

        /* synthetic */ C2235a(byte b) {
            this();
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ab$l */
    /* compiled from: BUGLY */
    static class C2246l extends C2235a {
        private C2246l() {
            super((byte) 0);
        }

        /* synthetic */ C2246l(byte b) {
            this();
        }

        /* renamed from: a */
        public final String mo27992a(Context context) {
            String a = C2273ap.m643a(context, "ro.miui.ui.version.name");
            if (C2273ap.m657a(a) || a.equals("fail")) {
                return null;
            }
            return "XiaoMi/MIUI/".concat(String.valueOf(a));
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ab$f */
    /* compiled from: BUGLY */
    static class C2240f extends C2235a {
        private C2240f() {
            super((byte) 0);
        }

        /* synthetic */ C2240f(byte b) {
            this();
        }

        /* renamed from: a */
        public final String mo27992a(Context context) {
            String a = C2273ap.m643a(context, "ro.build.version.emui");
            if (C2273ap.m657a(a) || a.equals("fail")) {
                return null;
            }
            return "HuaWei/EMOTION/".concat(String.valueOf(a));
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ab$g */
    /* compiled from: BUGLY */
    static class C2241g extends C2235a {
        private C2241g() {
            super((byte) 0);
        }

        /* synthetic */ C2241g(byte b) {
            this();
        }

        /* renamed from: a */
        public final String mo27992a(Context context) {
            String a = C2273ap.m643a(context, "ro.lenovo.series");
            if (C2273ap.m657a(a) || a.equals("fail")) {
                return null;
            }
            return "Lenovo/VIBE/".concat(String.valueOf(C2273ap.m643a(context, "ro.build.version.incremental")));
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ab$m */
    /* compiled from: BUGLY */
    static class C2247m extends C2235a {
        private C2247m() {
            super((byte) 0);
        }

        /* synthetic */ C2247m(byte b) {
            this();
        }

        /* renamed from: a */
        public final String mo27992a(Context context) {
            String a = C2273ap.m643a(context, "ro.build.nubia.rom.name");
            if (C2273ap.m657a(a) || a.equals("fail")) {
                return null;
            }
            return "Zte/NUBIA/" + a + "_" + C2273ap.m643a(context, "ro.build.nubia.rom.code");
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ab$h */
    /* compiled from: BUGLY */
    static class C2242h extends C2235a {
        private C2242h() {
            super((byte) 0);
        }

        /* synthetic */ C2242h(byte b) {
            this();
        }

        /* renamed from: a */
        public final String mo27992a(Context context) {
            String a = C2273ap.m643a(context, "ro.meizu.product.model");
            if (C2273ap.m657a(a) || a.equals("fail")) {
                return null;
            }
            return "Meizu/FLYME/" + C2273ap.m643a(context, "ro.build.display.id");
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ab$i */
    /* compiled from: BUGLY */
    static class C2243i extends C2235a {
        private C2243i() {
            super((byte) 0);
        }

        /* synthetic */ C2243i(byte b) {
            this();
        }

        /* renamed from: a */
        public final String mo27992a(Context context) {
            String a = C2273ap.m643a(context, "ro.build.version.opporom");
            if (C2273ap.m657a(a) || a.equals("fail")) {
                return null;
            }
            return "Oppo/COLOROS/".concat(String.valueOf(a));
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ab$k */
    /* compiled from: BUGLY */
    static class C2245k extends C2235a {
        private C2245k() {
            super((byte) 0);
        }

        /* synthetic */ C2245k(byte b) {
            this();
        }

        /* renamed from: a */
        public final String mo27992a(Context context) {
            String a = C2273ap.m643a(context, "ro.vivo.os.build.display.id");
            if (C2273ap.m657a(a) || a.equals("fail")) {
                return null;
            }
            return "vivo/FUNTOUCH/".concat(String.valueOf(a));
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ab$e */
    /* compiled from: BUGLY */
    static class C2239e extends C2235a {
        private C2239e() {
            super((byte) 0);
        }

        /* synthetic */ C2239e(byte b) {
            this();
        }

        /* renamed from: a */
        public final String mo27992a(Context context) {
            String a = C2273ap.m643a(context, "ro.aa.romver");
            if (C2273ap.m657a(a) || a.equals("fail")) {
                return null;
            }
            return "htc/" + a + "/" + C2273ap.m643a(context, "ro.build.description");
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ab$j */
    /* compiled from: BUGLY */
    static class C2244j extends C2235a {
        private C2244j() {
            super((byte) 0);
        }

        /* synthetic */ C2244j(byte b) {
            this();
        }

        /* renamed from: a */
        public final String mo27992a(Context context) {
            String a = C2273ap.m643a(context, "ro.lewa.version");
            if (C2273ap.m657a(a) || a.equals("fail")) {
                return null;
            }
            return "tcl/" + a + "/" + C2273ap.m643a(context, "ro.build.display.id");
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ab$b */
    /* compiled from: BUGLY */
    static class C2236b extends C2235a {
        private C2236b() {
            super((byte) 0);
        }

        /* synthetic */ C2236b(byte b) {
            this();
        }

        /* renamed from: a */
        public final String mo27992a(Context context) {
            String a = C2273ap.m643a(context, "ro.gn.gnromvernumber");
            if (C2273ap.m657a(a) || a.equals("fail")) {
                return null;
            }
            return "amigo/" + a + "/" + C2273ap.m643a(context, "ro.build.display.id");
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ab$d */
    /* compiled from: BUGLY */
    static class C2238d extends C2235a {
        private C2238d() {
            super((byte) 0);
        }

        /* synthetic */ C2238d(byte b) {
            this();
        }

        /* renamed from: a */
        public final String mo27992a(Context context) {
            String a = C2273ap.m643a(context, "ro.build.tyd.kbstyle_version");
            if (C2273ap.m657a(a) || a.equals("fail")) {
                return null;
            }
            return "dido/".concat(String.valueOf(a));
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ab$c */
    /* compiled from: BUGLY */
    static class C2237c extends C2235a {
        private C2237c() {
            super((byte) 0);
        }

        /* synthetic */ C2237c(byte b) {
            this();
        }

        /* renamed from: a */
        public final String mo27992a(Context context) {
            return C2273ap.m643a(context, "ro.build.fingerprint") + "/" + C2273ap.m643a(context, "ro.build.rom.id");
        }
    }
}
