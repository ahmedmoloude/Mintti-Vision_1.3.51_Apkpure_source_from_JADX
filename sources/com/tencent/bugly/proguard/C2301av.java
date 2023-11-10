package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import java.lang.Thread;

/* renamed from: com.tencent.bugly.proguard.av */
/* compiled from: BUGLY */
public final class C2301av implements Thread.UncaughtExceptionHandler {

    /* renamed from: h */
    private static String f1683h;

    /* renamed from: i */
    private static final Object f1684i = new Object();

    /* renamed from: a */
    protected final Context f1685a;

    /* renamed from: b */
    protected final C2277as f1686b;

    /* renamed from: c */
    protected final C2248ac f1687c;

    /* renamed from: d */
    protected final C2231aa f1688d;

    /* renamed from: e */
    protected Thread.UncaughtExceptionHandler f1689e;

    /* renamed from: f */
    protected Thread.UncaughtExceptionHandler f1690f;

    /* renamed from: g */
    protected boolean f1691g = false;

    /* renamed from: j */
    private int f1692j;

    public C2301av(Context context, C2277as asVar, C2248ac acVar, C2231aa aaVar) {
        this.f1685a = context;
        this.f1686b = asVar;
        this.f1687c = acVar;
        this.f1688d = aaVar;
    }

    /* renamed from: a */
    public final synchronized void mo28058a() {
        if (this.f1692j >= 10) {
            C2265al.m604a("java crash handler over %d, no need set.", 10);
            return;
        }
        this.f1691g = true;
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler != null) {
            if (!getClass().getName().equals(defaultUncaughtExceptionHandler.getClass().getName())) {
                if ("com.android.internal.os.RuntimeInit$UncaughtHandler".equals(defaultUncaughtExceptionHandler.getClass().getName())) {
                    C2265al.m604a("backup system java handler: %s", defaultUncaughtExceptionHandler.toString());
                    this.f1690f = defaultUncaughtExceptionHandler;
                    this.f1689e = defaultUncaughtExceptionHandler;
                } else {
                    C2265al.m604a("backup java handler: %s", defaultUncaughtExceptionHandler.toString());
                    this.f1689e = defaultUncaughtExceptionHandler;
                }
            } else {
                return;
            }
        }
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.f1692j++;
        C2265al.m604a("registered java monitor: %s", toString());
    }

    /* renamed from: b */
    public final synchronized void mo28061b() {
        this.f1691g = false;
        C2265al.m604a("close java monitor!", new Object[0]);
        if ("bugly".equals(Thread.getDefaultUncaughtExceptionHandler().getClass().getName())) {
            C2265al.m604a("Java monitor to unregister: %s", toString());
            Thread.setDefaultUncaughtExceptionHandler(this.f1689e);
            this.f1692j--;
        }
    }

    /* renamed from: c */
    private static void m766c() {
        C2265al.m611e("current process die", new Object[0]);
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0112 A[Catch:{ all -> 0x0148 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0114 A[Catch:{ all -> 0x0148 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0117 A[Catch:{ all -> 0x0148 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0127 A[Catch:{ all -> 0x0148 }] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.tencent.bugly.crashreport.crash.CrashDetailBean m765b(java.lang.Thread r6, java.lang.Throwable r7, boolean r8, java.lang.String r9, byte[] r10, boolean r11) {
        /*
            r5 = this;
            r0 = 0
            if (r7 != 0) goto L_0x000c
            java.lang.Object[] r6 = new java.lang.Object[r0]
            java.lang.String r7 = "We can do nothing with a null throwable."
            com.tencent.bugly.proguard.C2265al.m610d(r7, r6)
            r6 = 0
            return r6
        L_0x000c:
            com.tencent.bugly.crashreport.crash.CrashDetailBean r1 = new com.tencent.bugly.crashreport.crash.CrashDetailBean
            r1.<init>()
            long r2 = java.lang.System.currentTimeMillis()
            r1.f1380r = r2
            long r2 = com.tencent.bugly.proguard.C2232ab.m511j()
            r1.f1338C = r2
            long r2 = com.tencent.bugly.proguard.C2232ab.m506f()
            r1.f1339D = r2
            long r2 = com.tencent.bugly.proguard.C2232ab.m513l()
            r1.f1340E = r2
            com.tencent.bugly.proguard.aa r2 = r5.f1688d
            long r2 = r2.mo27977k()
            r1.f1341F = r2
            com.tencent.bugly.proguard.aa r2 = r5.f1688d
            long r2 = r2.mo27976j()
            r1.f1342G = r2
            com.tencent.bugly.proguard.aa r2 = r5.f1688d
            long r2 = r2.mo27978l()
            r1.f1343H = r2
            if (r8 != 0) goto L_0x0050
            int r2 = com.tencent.bugly.proguard.C2231aa.m451B()
            r3 = 31
            if (r2 < r3) goto L_0x0050
            r2 = 0
            r1.f1344I = r2
            goto L_0x0058
        L_0x0050:
            android.content.Context r2 = r5.f1685a
            long r2 = com.tencent.bugly.proguard.C2232ab.m498b(r2)
            r1.f1344I = r2
        L_0x0058:
            long r2 = com.tencent.bugly.proguard.C2232ab.m508g()
            r1.f1345J = r2
            long r2 = com.tencent.bugly.proguard.C2232ab.m509h()
            r1.f1346K = r2
            byte[] r2 = com.tencent.bugly.proguard.C2269ao.m624a()
            r1.f1387y = r2
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]
            byte[] r4 = r1.f1387y
            if (r4 != 0) goto L_0x0073
            r4 = 0
            goto L_0x0076
        L_0x0073:
            byte[] r4 = r1.f1387y
            int r4 = r4.length
        L_0x0076:
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r0] = r4
            java.lang.String r4 = "user log size:%d"
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r4, (java.lang.Object[]) r3)
            if (r8 == 0) goto L_0x0085
            r3 = 0
            goto L_0x0086
        L_0x0085:
            r3 = 2
        L_0x0086:
            r1.f1364b = r3
            com.tencent.bugly.proguard.aa r3 = r5.f1688d
            java.lang.String r3 = r3.mo27972g()
            r1.f1367e = r3
            com.tencent.bugly.proguard.aa r3 = r5.f1688d
            java.lang.String r3 = r3.f1483o
            r1.f1368f = r3
            com.tencent.bugly.proguard.aa r3 = r5.f1688d
            java.lang.String r3 = r3.mo27982q()
            r1.f1369g = r3
            com.tencent.bugly.proguard.aa r3 = r5.f1688d
            java.lang.String r3 = r3.mo27970f()
            r1.f1375m = r3
            int r3 = com.tencent.bugly.proguard.C2293at.f1638h
            java.util.Map r11 = com.tencent.bugly.proguard.C2273ap.m652a((boolean) r11, (int) r3)
            r1.f1388z = r11
            com.tencent.bugly.proguard.aa r11 = r5.f1688d
            java.lang.String r11 = r11.f1472d
            r1.f1336A = r11
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r3 = r6.getName()
            r11.append(r3)
            java.lang.String r3 = "("
            r11.append(r3)
            long r3 = r6.getId()
            r11.append(r3)
            java.lang.String r6 = ")"
            r11.append(r6)
            java.lang.String r6 = r11.toString()
            r1.f1337B = r6
            com.tencent.bugly.proguard.aa r6 = r5.f1688d
            java.lang.String r6 = r6.mo27984s()
            r1.f1347L = r6
            com.tencent.bugly.proguard.aa r6 = r5.f1688d
            java.util.Map r6 = r6.mo27981p()
            r1.f1370h = r6
            com.tencent.bugly.proguard.aa r6 = r5.f1688d
            java.util.Map r6 = r6.mo27957A()
            r1.f1371i = r6
            com.tencent.bugly.proguard.aa r6 = r5.f1688d
            long r3 = r6.f1445a
            r1.f1352Q = r3
            com.tencent.bugly.proguard.aa r6 = r5.f1688d
            boolean r6 = r6.mo27961a()
            r1.f1353R = r6
            m762a(r1, r7, r8)
            if (r8 != 0) goto L_0x0129
            if (r9 == 0) goto L_0x010c
            int r6 = r9.length()     // Catch:{ all -> 0x0148 }
            if (r6 <= 0) goto L_0x010c
            r6 = 1
            goto L_0x010d
        L_0x010c:
            r6 = 0
        L_0x010d:
            if (r10 == 0) goto L_0x0114
            int r7 = r10.length     // Catch:{ all -> 0x0148 }
            if (r7 <= 0) goto L_0x0114
            r7 = 1
            goto L_0x0115
        L_0x0114:
            r7 = 0
        L_0x0115:
            if (r6 == 0) goto L_0x0125
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ all -> 0x0148 }
            r6.<init>(r2)     // Catch:{ all -> 0x0148 }
            r1.f1354S = r6     // Catch:{ all -> 0x0148 }
            java.util.Map<java.lang.String, java.lang.String> r6 = r1.f1354S     // Catch:{ all -> 0x0148 }
            java.lang.String r8 = "UserData"
            r6.put(r8, r9)     // Catch:{ all -> 0x0148 }
        L_0x0125:
            if (r7 == 0) goto L_0x0129
            r1.f1360Y = r10     // Catch:{ all -> 0x0148 }
        L_0x0129:
            com.tencent.bugly.proguard.aa r6 = r5.f1688d     // Catch:{ all -> 0x0148 }
            int r6 = r6.mo27991z()     // Catch:{ all -> 0x0148 }
            r1.f1356U = r6     // Catch:{ all -> 0x0148 }
            com.tencent.bugly.proguard.aa r6 = r5.f1688d     // Catch:{ all -> 0x0148 }
            int r6 = r6.f1492x     // Catch:{ all -> 0x0148 }
            r1.f1357V = r6     // Catch:{ all -> 0x0148 }
            com.tencent.bugly.proguard.aa r6 = r5.f1688d     // Catch:{ all -> 0x0148 }
            java.util.Map r6 = r6.mo27985t()     // Catch:{ all -> 0x0148 }
            r1.f1358W = r6     // Catch:{ all -> 0x0148 }
            com.tencent.bugly.proguard.aa r6 = r5.f1688d     // Catch:{ all -> 0x0148 }
            java.util.Map r6 = r6.mo27990y()     // Catch:{ all -> 0x0148 }
            r1.f1359X = r6     // Catch:{ all -> 0x0148 }
            goto L_0x0156
        L_0x0148:
            r6 = move-exception
            java.lang.Object[] r7 = new java.lang.Object[r2]
            java.lang.String r6 = r6.toString()
            r7[r0] = r6
            java.lang.String r6 = "handle crash error %s"
            com.tencent.bugly.proguard.C2265al.m611e(r6, r7)
        L_0x0156:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2301av.m765b(java.lang.Thread, java.lang.Throwable, boolean, java.lang.String, byte[], boolean):com.tencent.bugly.crashreport.crash.CrashDetailBean");
    }

    /* renamed from: a */
    private static void m762a(CrashDetailBean crashDetailBean, Throwable th, boolean z) {
        String str;
        String name = th.getClass().getName();
        String a = m760a(th);
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(th.getStackTrace().length);
        objArr[1] = Boolean.valueOf(th.getCause() != null);
        C2265al.m611e("stack frame :%d, has cause %b", objArr);
        String str2 = "";
        String stackTraceElement = th.getStackTrace().length > 0 ? th.getStackTrace()[0].toString() : str2;
        Throwable th2 = th;
        while (th2 != null && th2.getCause() != null) {
            th2 = th2.getCause();
        }
        if (th2 == null || th2 == th) {
            crashDetailBean.f1376n = name;
            if (C2293at.m738a().mo28049i() && z) {
                C2265al.m611e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
                str2 = " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]";
            }
            crashDetailBean.f1377o = a + str2;
            crashDetailBean.f1378p = stackTraceElement;
            str = m761a(th, C2293at.f1638h);
            crashDetailBean.f1379q = str;
        } else {
            crashDetailBean.f1376n = th2.getClass().getName();
            crashDetailBean.f1377o = m760a(th2);
            if (th2.getStackTrace().length > 0) {
                crashDetailBean.f1378p = th2.getStackTrace()[0].toString();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(":");
            sb.append(a);
            sb.append("\n");
            sb.append(stackTraceElement);
            sb.append("\n......");
            sb.append("\nCaused by:\n");
            sb.append(crashDetailBean.f1376n);
            sb.append(":");
            sb.append(crashDetailBean.f1377o);
            sb.append("\n");
            str = m761a(th2, C2293at.f1638h);
            sb.append(str);
            crashDetailBean.f1379q = sb.toString();
        }
        crashDetailBean.f1383u = C2273ap.m671c(crashDetailBean.f1379q.getBytes());
        crashDetailBean.f1388z.put(crashDetailBean.f1337B, str);
    }

    /* renamed from: a */
    private static boolean m764a(Thread thread) {
        synchronized (f1684i) {
            if (f1683h != null) {
                if (thread.getName().equals(f1683h)) {
                    return true;
                }
            }
            f1683h = thread.getName();
            return false;
        }
    }

    /* renamed from: a */
    public final void mo28060a(Thread thread, Throwable th, boolean z, String str, byte[] bArr, boolean z2) {
        String str2;
        Thread thread2 = thread;
        Throwable th2 = th;
        boolean z3 = z;
        if (z3) {
            C2265al.m611e("Java Crash Happen cause by %s(%d)", thread.getName(), Long.valueOf(thread.getId()));
            if (m764a(thread)) {
                C2265al.m604a("this class has handled this exception", new Object[0]);
                if (this.f1690f != null) {
                    C2265al.m604a("call system handler", new Object[0]);
                    this.f1690f.uncaughtException(thread2, th2);
                } else {
                    m766c();
                }
            }
        } else {
            C2265al.m611e("Java Catch Happen", new Object[0]);
        }
        try {
            if (!this.f1691g) {
                C2265al.m609c("Java crash handler is disable. Just return.", new Object[0]);
                if (z3) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f1689e;
                    if (uncaughtExceptionHandler != null && m763a(uncaughtExceptionHandler)) {
                        C2265al.m611e("sys default last handle start!", new Object[0]);
                        this.f1689e.uncaughtException(thread2, th2);
                        C2265al.m611e("sys default last handle end!", new Object[0]);
                    } else if (this.f1690f != null) {
                        C2265al.m611e("system handle start!", new Object[0]);
                        this.f1690f.uncaughtException(thread2, th2);
                        C2265al.m611e("system handle end!", new Object[0]);
                    } else {
                        C2265al.m611e("crashreport last handle start!", new Object[0]);
                        m766c();
                        C2265al.m611e("crashreport last handle end!", new Object[0]);
                    }
                }
            } else {
                if (!this.f1687c.mo27995b()) {
                    C2265al.m610d("no remote but still store!", new Object[0]);
                }
                String str3 = "JAVA_CRASH";
                if (!this.f1687c.mo27996c().f1318f) {
                    if (this.f1687c.mo27995b()) {
                        C2265al.m611e("crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
                        if (z3) {
                            str2 = str3;
                        } else {
                            str2 = "JAVA_CATCH";
                        }
                        C2277as.m696a(str2, C2273ap.m640a(), this.f1688d.f1472d, thread.getName(), C2273ap.m646a(th), (CrashDetailBean) null);
                        if (z3) {
                            Thread.UncaughtExceptionHandler uncaughtExceptionHandler2 = this.f1689e;
                            if (uncaughtExceptionHandler2 != null && m763a(uncaughtExceptionHandler2)) {
                                C2265al.m611e("sys default last handle start!", new Object[0]);
                                this.f1689e.uncaughtException(thread2, th2);
                                C2265al.m611e("sys default last handle end!", new Object[0]);
                                return;
                            } else if (this.f1690f != null) {
                                C2265al.m611e("system handle start!", new Object[0]);
                                this.f1690f.uncaughtException(thread2, th2);
                                C2265al.m611e("system handle end!", new Object[0]);
                                return;
                            } else {
                                C2265al.m611e("crashreport last handle start!", new Object[0]);
                                m766c();
                                C2265al.m611e("crashreport last handle end!", new Object[0]);
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                }
                CrashDetailBean b = m765b(thread, th, z, str, bArr, z2);
                if (b == null) {
                    C2265al.m611e("pkg crash datas fail!", new Object[0]);
                    if (z3) {
                        Thread.UncaughtExceptionHandler uncaughtExceptionHandler3 = this.f1689e;
                        if (uncaughtExceptionHandler3 != null && m763a(uncaughtExceptionHandler3)) {
                            C2265al.m611e("sys default last handle start!", new Object[0]);
                            this.f1689e.uncaughtException(thread2, th2);
                            C2265al.m611e("sys default last handle end!", new Object[0]);
                        } else if (this.f1690f != null) {
                            C2265al.m611e("system handle start!", new Object[0]);
                            this.f1690f.uncaughtException(thread2, th2);
                            C2265al.m611e("system handle end!", new Object[0]);
                        } else {
                            C2265al.m611e("crashreport last handle start!", new Object[0]);
                            m766c();
                            C2265al.m611e("crashreport last handle end!", new Object[0]);
                        }
                    }
                } else {
                    if (!z3) {
                        str3 = "JAVA_CATCH";
                    }
                    C2277as.m696a(str3, C2273ap.m640a(), this.f1688d.f1472d, thread.getName(), C2273ap.m646a(th), b);
                    if (!this.f1686b.mo28035a(b, z3)) {
                        this.f1686b.mo28037b(b, z3);
                    }
                    if (z3) {
                        this.f1686b.mo28033a(b);
                    }
                    if (z3) {
                        Thread.UncaughtExceptionHandler uncaughtExceptionHandler4 = this.f1689e;
                        if (uncaughtExceptionHandler4 != null && m763a(uncaughtExceptionHandler4)) {
                            C2265al.m611e("sys default last handle start!", new Object[0]);
                            this.f1689e.uncaughtException(thread2, th2);
                            C2265al.m611e("sys default last handle end!", new Object[0]);
                        } else if (this.f1690f != null) {
                            C2265al.m611e("system handle start!", new Object[0]);
                            this.f1690f.uncaughtException(thread2, th2);
                            C2265al.m611e("system handle end!", new Object[0]);
                        } else {
                            C2265al.m611e("crashreport last handle start!", new Object[0]);
                            m766c();
                            C2265al.m611e("crashreport last handle end!", new Object[0]);
                        }
                    }
                }
            }
        } catch (Throwable th3) {
            if (z3) {
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler5 = this.f1689e;
                if (uncaughtExceptionHandler5 != null && m763a(uncaughtExceptionHandler5)) {
                    C2265al.m611e("sys default last handle start!", new Object[0]);
                    this.f1689e.uncaughtException(thread2, th2);
                    C2265al.m611e("sys default last handle end!", new Object[0]);
                } else if (this.f1690f != null) {
                    C2265al.m611e("system handle start!", new Object[0]);
                    this.f1690f.uncaughtException(thread2, th2);
                    C2265al.m611e("system handle end!", new Object[0]);
                } else {
                    C2265al.m611e("crashreport last handle start!", new Object[0]);
                    m766c();
                    C2265al.m611e("crashreport last handle end!", new Object[0]);
                }
            }
            throw th3;
        }
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        synchronized (f1684i) {
            mo28060a(thread, th, true, (String) null, (byte[]) null, this.f1688d.f1436Q);
        }
    }

    /* renamed from: a */
    private static boolean m763a(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        if (uncaughtExceptionHandler == null) {
            return true;
        }
        String name = uncaughtExceptionHandler.getClass().getName();
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            if (name.equals(className) && "uncaughtException".equals(methodName)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002b, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void mo28059a(com.tencent.bugly.crashreport.common.strategy.StrategyBean r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 == 0) goto L_0x002a
            boolean r0 = r5.f1318f     // Catch:{ all -> 0x0027 }
            boolean r1 = r4.f1691g     // Catch:{ all -> 0x0027 }
            if (r0 == r1) goto L_0x002a
            java.lang.String r0 = "java changed to %b"
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0027 }
            r2 = 0
            boolean r3 = r5.f1318f     // Catch:{ all -> 0x0027 }
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch:{ all -> 0x0027 }
            r1[r2] = r3     // Catch:{ all -> 0x0027 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r0, (java.lang.Object[]) r1)     // Catch:{ all -> 0x0027 }
            boolean r5 = r5.f1318f     // Catch:{ all -> 0x0027 }
            if (r5 == 0) goto L_0x0023
            r4.mo28058a()     // Catch:{ all -> 0x0027 }
            monitor-exit(r4)
            return
        L_0x0023:
            r4.mo28061b()     // Catch:{ all -> 0x0027 }
            goto L_0x002a
        L_0x0027:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        L_0x002a:
            monitor-exit(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2301av.mo28059a(com.tencent.bugly.crashreport.common.strategy.StrategyBean):void");
    }

    /* renamed from: a */
    private static String m761a(Throwable th, int i) {
        if (th == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            if (th.getStackTrace() != null) {
                StackTraceElement[] stackTrace = th.getStackTrace();
                int length = stackTrace.length;
                int i2 = 0;
                while (i2 < length) {
                    StackTraceElement stackTraceElement = stackTrace[i2];
                    if (i <= 0 || sb.length() < i) {
                        sb.append(stackTraceElement.toString());
                        sb.append("\n");
                        i2++;
                    } else {
                        sb.append("\n[Stack over limit size :" + i + " , has been cutted !]");
                        return sb.toString();
                    }
                }
            }
        } catch (Throwable th2) {
            C2265al.m611e("gen stack error %s", th2.toString());
        }
        return sb.toString();
    }

    /* renamed from: a */
    private static String m760a(Throwable th) {
        String message = th.getMessage();
        if (message == null) {
            return "";
        }
        if (message.length() <= 1000) {
            return message;
        }
        return message.substring(0, 1000) + "\n[Message over limit size:1000, has been cutted!]";
    }
}
