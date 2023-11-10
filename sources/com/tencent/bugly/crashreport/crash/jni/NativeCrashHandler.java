package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import android.os.Build;
import com.itextpdf.text.pdf.PdfBoolean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.proguard.C2231aa;
import com.tencent.bugly.proguard.C2248ac;
import com.tencent.bugly.proguard.C2263ak;
import com.tencent.bugly.proguard.C2265al;
import com.tencent.bugly.proguard.C2273ap;
import com.tencent.bugly.proguard.C2277as;
import com.tencent.bugly.proguard.C2293at;
import com.tencent.bugly.proguard.C2315bd;
import com.tencent.bugly.proguard.C2316be;
import com.tencent.bugly.proguard.C2350q;
import java.io.File;

/* compiled from: BUGLY */
public class NativeCrashHandler implements C2350q {

    /* renamed from: a */
    private static NativeCrashHandler f1403a = null;

    /* renamed from: b */
    private static int f1404b = 1;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public static String f1405g = null;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public static boolean f1406n = true;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final Context f1407c;

    /* renamed from: d */
    private final C2231aa f1408d;

    /* renamed from: e */
    private final C2263ak f1409e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public NativeExceptionHandler f1410f;

    /* renamed from: h */
    private final boolean f1411h;

    /* renamed from: i */
    private boolean f1412i = false;

    /* renamed from: j */
    private boolean f1413j = false;

    /* renamed from: k */
    private boolean f1414k = false;

    /* renamed from: l */
    private boolean f1415l = false;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public C2277as f1416m;

    private native String getSoCpuAbi();

    /* access modifiers changed from: protected */
    public native boolean appendNativeLog(String str, String str2, String str3);

    /* access modifiers changed from: protected */
    public native boolean appendWholeNativeLog(String str);

    /* access modifiers changed from: protected */
    public native String getNativeKeyValueList();

    /* access modifiers changed from: protected */
    public native String getNativeLog();

    /* access modifiers changed from: protected */
    public native boolean putNativeKeyValue(String str, String str2);

    /* access modifiers changed from: protected */
    public native String regist(String str, boolean z, int i);

    /* access modifiers changed from: protected */
    public native String removeNativeKeyValue(String str);

    /* access modifiers changed from: protected */
    public native void setNativeInfo(int i, String str);

    /* access modifiers changed from: protected */
    public native void testCrash();

    /* access modifiers changed from: protected */
    public native String unregist();

    private NativeCrashHandler(Context context, C2231aa aaVar, C2277as asVar, C2263ak akVar, boolean z, String str) {
        this.f1407c = C2273ap.m635a(context);
        if (C2273ap.m657a(f1405g)) {
            try {
                if (C2273ap.m657a(str)) {
                    str = context.getDir("bugly", 0).getAbsolutePath();
                }
            } catch (Throwable unused) {
                str = "/data/data/" + C2231aa.m457a(context).f1471c + "/app_bugly";
            }
            f1405g = str;
        }
        this.f1416m = asVar;
        this.f1408d = aaVar;
        this.f1409e = akVar;
        this.f1411h = z;
        this.f1410f = new C2315bd(context, aaVar, asVar, C2248ac.m533a());
    }

    public static synchronized NativeCrashHandler getInstance(Context context, C2231aa aaVar, C2277as asVar, C2248ac acVar, C2263ak akVar, boolean z, String str) {
        NativeCrashHandler nativeCrashHandler;
        synchronized (NativeCrashHandler.class) {
            if (f1403a == null) {
                f1403a = new NativeCrashHandler(context, aaVar, asVar, akVar, z, str);
            }
            nativeCrashHandler = f1403a;
        }
        return nativeCrashHandler;
    }

    public static synchronized NativeCrashHandler getInstance() {
        NativeCrashHandler nativeCrashHandler;
        synchronized (NativeCrashHandler.class) {
            nativeCrashHandler = f1403a;
        }
        return nativeCrashHandler;
    }

    public static synchronized String getDumpFilePath() {
        String str;
        synchronized (NativeCrashHandler.class) {
            str = f1405g;
        }
        return str;
    }

    public static synchronized void setDumpFilePath(String str) {
        synchronized (NativeCrashHandler.class) {
            f1405g = str;
        }
    }

    public static void setShouldHandleInJava(boolean z) {
        f1406n = z;
        NativeCrashHandler nativeCrashHandler = f1403a;
        if (nativeCrashHandler != null) {
            nativeCrashHandler.m440a(999, String.valueOf(z));
        }
    }

    public static boolean isShouldHandleInJava() {
        return f1406n;
    }

    public String getRunningCpuAbi() {
        try {
            return getSoCpuAbi();
        } catch (Throwable unused) {
            C2265al.m610d("get so cpu abi failed，please upgrade bugly so version", new Object[0]);
            return "";
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:7|8|9|(3:11|12|(4:14|(1:18)|19|(1:21)))(2:28|(7:30|31|32|(1:34)(1:35)|36|(1:38)|(4:40|(1:42)|43|(1:45))))|48|49|50|51) */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0074, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0135, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:48:0x0136 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m439a(boolean r11) {
        /*
            r10 = this;
            monitor-enter(r10)
            boolean r0 = r10.f1414k     // Catch:{ all -> 0x013c }
            r1 = 0
            if (r0 == 0) goto L_0x000f
            java.lang.String r11 = "[Native] Native crash report has already registered."
            java.lang.Object[] r0 = new java.lang.Object[r1]     // Catch:{ all -> 0x013c }
            com.tencent.bugly.proguard.C2265al.m610d(r11, r0)     // Catch:{ all -> 0x013c }
            monitor-exit(r10)
            return
        L_0x000f:
            boolean r0 = r10.f1413j     // Catch:{ all -> 0x013c }
            r2 = 1
            if (r0 == 0) goto L_0x007e
            java.lang.String r0 = f1405g     // Catch:{ all -> 0x0075 }
            int r3 = f1404b     // Catch:{ all -> 0x0075 }
            java.lang.String r11 = r10.regist(r0, r11, r3)     // Catch:{ all -> 0x0075 }
            if (r11 == 0) goto L_0x0136
            java.lang.String r0 = "[Native] Native Crash Report enable."
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ all -> 0x0075 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r0, (java.lang.Object[]) r3)     // Catch:{ all -> 0x0075 }
            com.tencent.bugly.proguard.aa r0 = r10.f1408d     // Catch:{ all -> 0x0075 }
            r0.f1489u = r11     // Catch:{ all -> 0x0075 }
            java.lang.String r11 = "-"
            com.tencent.bugly.proguard.aa r0 = r10.f1408d     // Catch:{ all -> 0x0075 }
            java.lang.String r0 = r0.f1489u     // Catch:{ all -> 0x0075 }
            java.lang.String r11 = r11.concat(r0)     // Catch:{ all -> 0x0075 }
            boolean r0 = com.tencent.bugly.proguard.C2293at.f1633b     // Catch:{ all -> 0x0075 }
            if (r0 != 0) goto L_0x0055
            com.tencent.bugly.proguard.aa r0 = r10.f1408d     // Catch:{ all -> 0x0075 }
            java.lang.String r0 = r0.f1476h     // Catch:{ all -> 0x0075 }
            boolean r11 = r0.contains(r11)     // Catch:{ all -> 0x0075 }
            if (r11 != 0) goto L_0x0055
            com.tencent.bugly.proguard.aa r11 = r10.f1408d     // Catch:{ all -> 0x0075 }
            java.lang.String r0 = r11.f1476h     // Catch:{ all -> 0x0075 }
            java.lang.String r3 = "-"
            java.lang.String r0 = r0.concat(r3)     // Catch:{ all -> 0x0075 }
            com.tencent.bugly.proguard.aa r3 = r10.f1408d     // Catch:{ all -> 0x0075 }
            java.lang.String r3 = r3.f1489u     // Catch:{ all -> 0x0075 }
            java.lang.String r0 = r0.concat(r3)     // Catch:{ all -> 0x0075 }
            r11.f1476h = r0     // Catch:{ all -> 0x0075 }
        L_0x0055:
            java.lang.String r11 = "comInfo.sdkVersion %s"
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ all -> 0x0075 }
            com.tencent.bugly.proguard.aa r3 = r10.f1408d     // Catch:{ all -> 0x0075 }
            java.lang.String r3 = r3.f1476h     // Catch:{ all -> 0x0075 }
            r0[r1] = r3     // Catch:{ all -> 0x0075 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r11, (java.lang.Object[]) r0)     // Catch:{ all -> 0x0075 }
            r10.f1414k = r2     // Catch:{ all -> 0x0075 }
            java.lang.String r11 = r10.getRunningCpuAbi()     // Catch:{ all -> 0x0075 }
            boolean r0 = android.text.TextUtils.isEmpty(r11)     // Catch:{ all -> 0x0075 }
            if (r0 != 0) goto L_0x0073
            com.tencent.bugly.proguard.aa r0 = r10.f1408d     // Catch:{ all -> 0x0075 }
            r0.mo27969e(r11)     // Catch:{ all -> 0x0075 }
        L_0x0073:
            monitor-exit(r10)
            return
        L_0x0075:
            java.lang.String r11 = "[Native] Failed to load Bugly SO file."
            java.lang.Object[] r0 = new java.lang.Object[r1]     // Catch:{ all -> 0x013c }
            com.tencent.bugly.proguard.C2265al.m609c(r11, r0)     // Catch:{ all -> 0x013c }
            goto L_0x0136
        L_0x007e:
            boolean r0 = r10.f1412i     // Catch:{ all -> 0x013c }
            if (r0 == 0) goto L_0x0136
            java.lang.String r0 = "com.tencent.feedback.eup.jni.NativeExceptionUpload"
            java.lang.String r3 = "registNativeExceptionHandler2"
            r4 = 4
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch:{ all -> 0x0136 }
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r5[r1] = r6     // Catch:{ all -> 0x0136 }
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r5[r2] = r6     // Catch:{ all -> 0x0136 }
            java.lang.Class r6 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0136 }
            r7 = 2
            r5[r7] = r6     // Catch:{ all -> 0x0136 }
            java.lang.Class r6 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0136 }
            r8 = 3
            r5[r8] = r6     // Catch:{ all -> 0x0136 }
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0136 }
            java.lang.String r6 = f1405g     // Catch:{ all -> 0x0136 }
            r4[r1] = r6     // Catch:{ all -> 0x0136 }
            java.lang.String r6 = com.tencent.bugly.proguard.C2232ab.m502d()     // Catch:{ all -> 0x0136 }
            r4[r2] = r6     // Catch:{ all -> 0x0136 }
            r6 = 5
            if (r11 == 0) goto L_0x00ac
            r9 = 1
            goto L_0x00ad
        L_0x00ac:
            r9 = 5
        L_0x00ad:
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x0136 }
            r4[r7] = r9     // Catch:{ all -> 0x0136 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x0136 }
            r4[r8] = r9     // Catch:{ all -> 0x0136 }
            java.lang.Object r0 = com.tencent.bugly.proguard.C2273ap.m638a(r0, r3, r5, r4)     // Catch:{ all -> 0x0136 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0136 }
            if (r0 != 0) goto L_0x00f2
            java.lang.String r0 = "com.tencent.feedback.eup.jni.NativeExceptionUpload"
            java.lang.String r3 = "registNativeExceptionHandler"
            java.lang.Class[] r4 = new java.lang.Class[r8]     // Catch:{ all -> 0x0136 }
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r4[r1] = r5     // Catch:{ all -> 0x0136 }
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r4[r2] = r5     // Catch:{ all -> 0x0136 }
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0136 }
            r4[r7] = r5     // Catch:{ all -> 0x0136 }
            java.lang.Object[] r5 = new java.lang.Object[r8]     // Catch:{ all -> 0x0136 }
            java.lang.String r8 = f1405g     // Catch:{ all -> 0x0136 }
            r5[r1] = r8     // Catch:{ all -> 0x0136 }
            java.lang.String r8 = com.tencent.bugly.proguard.C2232ab.m502d()     // Catch:{ all -> 0x0136 }
            r5[r2] = r8     // Catch:{ all -> 0x0136 }
            com.tencent.bugly.proguard.C2231aa.m459b()     // Catch:{ all -> 0x0136 }
            int r8 = com.tencent.bugly.proguard.C2231aa.m451B()     // Catch:{ all -> 0x0136 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0136 }
            r5[r7] = r8     // Catch:{ all -> 0x0136 }
            java.lang.Object r0 = com.tencent.bugly.proguard.C2273ap.m638a(r0, r3, r4, r5)     // Catch:{ all -> 0x0136 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0136 }
        L_0x00f2:
            if (r0 == 0) goto L_0x0136
            r10.f1414k = r2     // Catch:{ all -> 0x0136 }
            com.tencent.bugly.proguard.aa r3 = r10.f1408d     // Catch:{ all -> 0x0136 }
            r3.f1489u = r0     // Catch:{ all -> 0x0136 }
            java.lang.String r0 = "com.tencent.feedback.eup.jni.NativeExceptionUpload"
            java.lang.String r3 = "enableHandler"
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch:{ all -> 0x0136 }
            java.lang.Class r5 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x0136 }
            r4[r1] = r5     // Catch:{ all -> 0x0136 }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ all -> 0x0136 }
            java.lang.Boolean r7 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x0136 }
            r5[r1] = r7     // Catch:{ all -> 0x0136 }
            com.tencent.bugly.proguard.C2273ap.m638a(r0, r3, r4, r5)     // Catch:{ all -> 0x0136 }
            if (r11 == 0) goto L_0x0110
            r6 = 1
        L_0x0110:
            java.lang.String r11 = "com.tencent.feedback.eup.jni.NativeExceptionUpload"
            java.lang.String r0 = "setLogMode"
            java.lang.Class[] r3 = new java.lang.Class[r2]     // Catch:{ all -> 0x0136 }
            java.lang.Class r4 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0136 }
            r3[r1] = r4     // Catch:{ all -> 0x0136 }
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0136 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x0136 }
            r2[r1] = r4     // Catch:{ all -> 0x0136 }
            com.tencent.bugly.proguard.C2273ap.m638a(r11, r0, r3, r2)     // Catch:{ all -> 0x0136 }
            java.lang.String r11 = r10.getRunningCpuAbi()     // Catch:{ all -> 0x0136 }
            boolean r0 = android.text.TextUtils.isEmpty(r11)     // Catch:{ all -> 0x0136 }
            if (r0 != 0) goto L_0x0134
            com.tencent.bugly.proguard.aa r0 = r10.f1408d     // Catch:{ all -> 0x0136 }
            r0.mo27969e(r11)     // Catch:{ all -> 0x0136 }
        L_0x0134:
            monitor-exit(r10)
            return
        L_0x0136:
            r10.f1413j = r1     // Catch:{ all -> 0x013c }
            r10.f1412i = r1     // Catch:{ all -> 0x013c }
            monitor-exit(r10)
            return
        L_0x013c:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.m439a(boolean):void");
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:31:0x0057=Splitter:B:31:0x0057, B:35:0x008c=Splitter:B:35:0x008c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void startNativeMonitor() {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.f1413j     // Catch:{ all -> 0x0093 }
            if (r0 != 0) goto L_0x008c
            boolean r0 = r4.f1412i     // Catch:{ all -> 0x0093 }
            if (r0 == 0) goto L_0x000b
            goto L_0x008c
        L_0x000b:
            com.tencent.bugly.proguard.aa r0 = r4.f1408d     // Catch:{ all -> 0x0093 }
            java.lang.String r0 = r0.f1488t     // Catch:{ all -> 0x0093 }
            boolean r0 = com.tencent.bugly.proguard.C2273ap.m657a((java.lang.String) r0)     // Catch:{ all -> 0x0093 }
            r1 = 0
            if (r0 != 0) goto L_0x0018
            r0 = 1
            goto L_0x0019
        L_0x0018:
            r0 = 0
        L_0x0019:
            boolean r2 = com.tencent.bugly.proguard.C2293at.f1633b     // Catch:{ all -> 0x0093 }
            if (r2 == 0) goto L_0x0038
            java.lang.String r2 = "Bugly_Native"
            java.lang.String r3 = "NativeRQD"
            if (r0 == 0) goto L_0x0027
            com.tencent.bugly.proguard.aa r2 = r4.f1408d     // Catch:{ all -> 0x0093 }
            java.lang.String r2 = r2.f1488t     // Catch:{ all -> 0x0093 }
        L_0x0027:
            boolean r2 = m442a((java.lang.String) r2, (boolean) r0)     // Catch:{ all -> 0x0093 }
            r4.f1413j = r2     // Catch:{ all -> 0x0093 }
            if (r2 != 0) goto L_0x004d
            if (r0 != 0) goto L_0x004d
            boolean r0 = m442a((java.lang.String) r3, (boolean) r1)     // Catch:{ all -> 0x0093 }
            r4.f1412i = r0     // Catch:{ all -> 0x0093 }
            goto L_0x004d
        L_0x0038:
            java.lang.String r1 = "Bugly_Native"
            com.tencent.bugly.proguard.aa r2 = r4.f1408d     // Catch:{ all -> 0x0093 }
            java.lang.String r2 = r2.f1488t     // Catch:{ all -> 0x0093 }
            if (r0 != 0) goto L_0x0046
            com.tencent.bugly.proguard.aa r2 = r4.f1408d     // Catch:{ all -> 0x0093 }
            r2.getClass()     // Catch:{ all -> 0x0093 }
            goto L_0x0047
        L_0x0046:
            r1 = r2
        L_0x0047:
            boolean r0 = m442a((java.lang.String) r1, (boolean) r0)     // Catch:{ all -> 0x0093 }
            r4.f1413j = r0     // Catch:{ all -> 0x0093 }
        L_0x004d:
            boolean r0 = r4.f1413j     // Catch:{ all -> 0x0093 }
            if (r0 != 0) goto L_0x0057
            boolean r0 = r4.f1412i     // Catch:{ all -> 0x0093 }
            if (r0 != 0) goto L_0x0057
            monitor-exit(r4)
            return
        L_0x0057:
            boolean r0 = r4.f1411h     // Catch:{ all -> 0x0093 }
            r4.m439a((boolean) r0)     // Catch:{ all -> 0x0093 }
            com.tencent.bugly.proguard.aa r0 = r4.f1408d     // Catch:{ all -> 0x0093 }
            java.lang.String r0 = r0.f1483o     // Catch:{ all -> 0x0093 }
            r4.setNativeAppVersion(r0)     // Catch:{ all -> 0x0093 }
            com.tencent.bugly.proguard.aa r0 = r4.f1408d     // Catch:{ all -> 0x0093 }
            java.lang.String r0 = r0.f1487s     // Catch:{ all -> 0x0093 }
            r4.setNativeAppChannel(r0)     // Catch:{ all -> 0x0093 }
            com.tencent.bugly.proguard.aa r0 = r4.f1408d     // Catch:{ all -> 0x0093 }
            java.lang.String r0 = r0.f1471c     // Catch:{ all -> 0x0093 }
            r4.setNativeAppPackage(r0)     // Catch:{ all -> 0x0093 }
            com.tencent.bugly.proguard.aa r0 = r4.f1408d     // Catch:{ all -> 0x0093 }
            java.lang.String r0 = r0.mo27970f()     // Catch:{ all -> 0x0093 }
            r4.setNativeUserId(r0)     // Catch:{ all -> 0x0093 }
            com.tencent.bugly.proguard.aa r0 = r4.f1408d     // Catch:{ all -> 0x0093 }
            boolean r0 = r0.mo27961a()     // Catch:{ all -> 0x0093 }
            r4.setNativeIsAppForeground(r0)     // Catch:{ all -> 0x0093 }
            com.tencent.bugly.proguard.aa r0 = r4.f1408d     // Catch:{ all -> 0x0093 }
            long r0 = r0.f1445a     // Catch:{ all -> 0x0093 }
            r4.setNativeLaunchTime(r0)     // Catch:{ all -> 0x0093 }
            monitor-exit(r4)
            return
        L_0x008c:
            boolean r0 = r4.f1411h     // Catch:{ all -> 0x0093 }
            r4.m439a((boolean) r0)     // Catch:{ all -> 0x0093 }
            monitor-exit(r4)
            return
        L_0x0093:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.startNativeMonitor():void");
    }

    public void checkUploadRecordCrash() {
        this.f1409e.mo28017a(new Runnable() {
            public final void run() {
                if (!C2273ap.m668b(NativeCrashHandler.this.f1407c, "native_record_lock")) {
                    C2265al.m604a("[Native] Failed to lock file for handling native crash record.", new Object[0]);
                    return;
                }
                if (!NativeCrashHandler.f1406n) {
                    boolean unused = NativeCrashHandler.this.m440a(999, PdfBoolean.FALSE);
                }
                CrashDetailBean a = C2316be.m799a(NativeCrashHandler.this.f1407c, NativeCrashHandler.f1405g, NativeCrashHandler.this.f1410f);
                if (a != null) {
                    C2265al.m604a("[Native] Get crash from native record.", new Object[0]);
                    if (!NativeCrashHandler.this.f1416m.mo28035a(a, true)) {
                        NativeCrashHandler.this.f1416m.mo28037b(a, false);
                    }
                    C2316be.m807a(false, NativeCrashHandler.f1405g);
                }
                NativeCrashHandler.m438a();
                C2273ap.m673c(NativeCrashHandler.this.f1407c, "native_record_lock");
            }
        });
    }

    /* renamed from: a */
    private static boolean m442a(String str, boolean z) {
        boolean z2;
        try {
            C2265al.m604a("[Native] Trying to load so: %s", str);
            if (z) {
                System.load(str);
            } else {
                System.loadLibrary(str);
            }
            try {
                C2265al.m604a("[Native] Successfully loaded SO: %s", str);
                return true;
            } catch (Throwable th) {
                th = th;
                z2 = true;
            }
        } catch (Throwable th2) {
            th = th2;
            z2 = false;
            C2265al.m610d(th.getMessage(), new Object[0]);
            C2265al.m610d("[Native] Failed to load so: %s", str);
            return z2;
        }
    }

    /* renamed from: d */
    private synchronized void m449d() {
        if (!this.f1414k) {
            C2265al.m610d("[Native] Native crash report has already unregistered.", new Object[0]);
            return;
        }
        try {
            if (unregist() != null) {
                C2265al.m604a("[Native] Successfully closed native crash report.", new Object[0]);
                this.f1414k = false;
                return;
            }
        } catch (Throwable unused) {
            C2265al.m609c("[Native] Failed to close native crash report.", new Object[0]);
        }
        try {
            C2273ap.m638a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", new Class[]{Boolean.TYPE}, new Object[]{Boolean.FALSE});
            this.f1414k = false;
            C2265al.m604a("[Native] Successfully closed native crash report.", new Object[0]);
            return;
        } catch (Throwable unused2) {
            C2265al.m609c("[Native] Failed to close native crash report.", new Object[0]);
            this.f1413j = false;
            this.f1412i = false;
            return;
        }
    }

    public void testNativeCrash() {
        if (!this.f1413j) {
            C2265al.m610d("[Native] Bugly SO file has not been load.", new Object[0]);
        } else {
            testCrash();
        }
    }

    public void testNativeCrash(boolean z, boolean z2, boolean z3) {
        m440a(16, String.valueOf(z));
        m440a(17, String.valueOf(z2));
        m440a(18, String.valueOf(z3));
        testNativeCrash();
    }

    public void dumpAnrNativeStack() {
        m440a(19, "1");
    }

    public void resendSigquit() {
        m440a(20, "");
    }

    public void unBlockSigquit(boolean z) {
        if (z) {
            m440a(21, PdfBoolean.TRUE);
        } else {
            m440a(21, PdfBoolean.FALSE);
        }
    }

    public NativeExceptionHandler getNativeExceptionHandler() {
        return this.f1410f;
    }

    /* renamed from: a */
    protected static void m438a() {
        long b = C2273ap.m661b() - C2293at.f1639i;
        long b2 = C2273ap.m661b() + 86400000;
        File file = new File(f1405g);
        if (file.exists() && file.isDirectory()) {
            try {
                File[] listFiles = file.listFiles();
                if (listFiles == null) {
                    return;
                }
                if (listFiles.length != 0) {
                    int i = 0;
                    int i2 = 0;
                    for (File file2 : listFiles) {
                        long lastModified = file2.lastModified();
                        if (lastModified < b || lastModified >= b2) {
                            C2265al.m604a("[Native] Delete record file: %s", file2.getAbsolutePath());
                            i++;
                            if (file2.delete()) {
                                i2++;
                            }
                        }
                    }
                    C2265al.m609c("[Native] Number of record files overdue: %d, has deleted: %d", Integer.valueOf(i), Integer.valueOf(i2));
                }
            } catch (Throwable th) {
                C2265al.m605a(th);
            }
        }
    }

    public void removeEmptyNativeRecordFiles() {
        C2316be.m812c(f1405g);
    }

    /* renamed from: b */
    private synchronized void m444b(boolean z) {
        if (z) {
            startNativeMonitor();
        } else {
            m449d();
        }
    }

    public synchronized boolean isUserOpened() {
        return this.f1415l;
    }

    /* renamed from: c */
    private synchronized void m448c(boolean z) {
        if (this.f1415l != z) {
            C2265al.m604a("user change native %b", Boolean.valueOf(z));
            this.f1415l = z;
        }
    }

    public synchronized void setUserOpened(boolean z) {
        m448c(z);
        boolean isUserOpened = isUserOpened();
        C2248ac a = C2248ac.m533a();
        if (a != null) {
            isUserOpened = isUserOpened && a.mo27996c().f1318f;
        }
        if (isUserOpened != this.f1414k) {
            C2265al.m604a("native changed to %b", Boolean.valueOf(isUserOpened));
            m444b(isUserOpened);
        }
    }

    public synchronized void onStrategyChanged(StrategyBean strategyBean) {
        if (strategyBean != null) {
            if (strategyBean.f1318f != this.f1414k) {
                C2265al.m610d("server native changed to %b", Boolean.valueOf(strategyBean.f1318f));
            }
        }
        boolean z = C2248ac.m533a().mo27996c().f1318f && this.f1415l;
        if (z != this.f1414k) {
            C2265al.m604a("native changed to %b", Boolean.valueOf(z));
            m444b(z);
        }
    }

    public boolean appendLogToNative(String str, String str2, String str3) {
        if (!((!this.f1412i && !this.f1413j) || str == null || str2 == null || str3 == null)) {
            try {
                if (this.f1413j) {
                    return appendNativeLog(str, str2, str3);
                }
                Boolean bool = (Boolean) C2273ap.m638a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "appendNativeLog", new Class[]{String.class, String.class, String.class}, new Object[]{str, str2, str3});
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            } catch (UnsatisfiedLinkError unused) {
            } catch (Throwable th) {
                if (!C2265al.m605a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return false;
    }

    public String getLogFromNative() {
        if (!this.f1412i && !this.f1413j) {
            return null;
        }
        try {
            if (this.f1413j) {
                return getNativeLog();
            }
            return (String) C2273ap.m638a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "getNativeLog", (Class<?>[]) null, (Object[]) null);
        } catch (UnsatisfiedLinkError unused) {
            return null;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public boolean putKeyValueToNative(String str, String str2) {
        if (!((!this.f1412i && !this.f1413j) || str == null || str2 == null)) {
            try {
                if (this.f1413j) {
                    return putNativeKeyValue(str, str2);
                }
                Boolean bool = (Boolean) C2273ap.m638a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "putNativeKeyValue", new Class[]{String.class, String.class}, new Object[]{str, str2});
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            } catch (UnsatisfiedLinkError unused) {
            } catch (Throwable th) {
                if (!C2265al.m605a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public boolean m440a(int i, String str) {
        if (!this.f1413j) {
            return false;
        }
        try {
            setNativeInfo(i, str);
            return true;
        } catch (UnsatisfiedLinkError unused) {
            return false;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    public boolean filterSigabrtSysLog() {
        return m440a(998, PdfBoolean.TRUE);
    }

    public boolean setNativeAppVersion(String str) {
        return m440a(10, str);
    }

    public boolean setNativeAppChannel(String str) {
        return m440a(12, str);
    }

    public boolean setNativeAppPackage(String str) {
        return m440a(13, str);
    }

    public boolean setNativeUserId(String str) {
        return m440a(11, str);
    }

    public boolean setNativeIsAppForeground(boolean z) {
        return m440a(14, z ? PdfBoolean.TRUE : PdfBoolean.FALSE);
    }

    public boolean setNativeLaunchTime(long j) {
        try {
            return m440a(15, String.valueOf(j));
        } catch (NumberFormatException e) {
            if (C2265al.m605a(e)) {
                return false;
            }
            e.printStackTrace();
            return false;
        }
    }

    public void enableCatchAnrTrace() {
        if (Build.VERSION.SDK_INT > 19) {
            f1404b |= 2;
        }
    }

    public void disableCatchAnrTrace() {
        if (Build.VERSION.SDK_INT > 19) {
            f1404b = 1;
        }
    }

    public boolean isEnableCatchAnrTrace() {
        return (f1404b & 2) == 2;
    }
}
