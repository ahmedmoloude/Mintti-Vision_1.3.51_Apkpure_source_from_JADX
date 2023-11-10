package com.tencent.bugly;

import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.C2293at;
import com.tencent.bugly.proguard.C2348o;

/* compiled from: BUGLY */
public class CrashModule extends C2348o {
    public static final int MODULE_ID = 1004;

    /* renamed from: c */
    private static int f1283c;

    /* renamed from: e */
    private static CrashModule f1284e = new CrashModule();

    /* renamed from: a */
    private long f1285a;

    /* renamed from: b */
    private BuglyStrategy.C2217a f1286b;

    /* renamed from: d */
    private boolean f1287d = false;

    public static CrashModule getInstance() {
        f1284e.f1909id = 1004;
        return f1284e;
    }

    public synchronized boolean hasInitialized() {
        return this.f1287d;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00e5, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00ba  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void init(android.content.Context r5, boolean r6, com.tencent.bugly.BuglyStrategy r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 == 0) goto L_0x00e4
            boolean r0 = r4.f1287d     // Catch:{ all -> 0x00e1 }
            if (r0 == 0) goto L_0x0009
            goto L_0x00e4
        L_0x0009:
            java.lang.String r0 = "Initializing crash module."
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r0, (java.lang.Object[]) r2)     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.proguard.u r0 = com.tencent.bugly.proguard.C2361u.m1013a()     // Catch:{ all -> 0x00e1 }
            int r2 = f1283c     // Catch:{ all -> 0x00e1 }
            r3 = 1
            int r2 = r2 + r3
            f1283c = r2     // Catch:{ all -> 0x00e1 }
            r0.mo28159a((int) r2)     // Catch:{ all -> 0x00e1 }
            r4.f1287d = r3     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.crashreport.CrashReport.setContext(r5)     // Catch:{ all -> 0x00e1 }
            r4.m407a(r5, r7)     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.BuglyStrategy$a r0 = r4.f1286b     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.proguard.at r6 = com.tencent.bugly.proguard.C2293at.m739a((android.content.Context) r5, (boolean) r6, (com.tencent.bugly.BuglyStrategy.C2217a) r0)     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.proguard.av r0 = r6.f1652s     // Catch:{ all -> 0x00e1 }
            r0.mo28058a()     // Catch:{ all -> 0x00e1 }
            if (r7 == 0) goto L_0x0061
            int r0 = r7.getCallBackType()     // Catch:{ all -> 0x00e1 }
            r6.f1648A = r0     // Catch:{ all -> 0x00e1 }
            boolean r0 = r7.getCloseErrorCallback()     // Catch:{ all -> 0x00e1 }
            r6.f1649B = r0     // Catch:{ all -> 0x00e1 }
            boolean r0 = r7.isUploadSpotCrash()     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.proguard.C2293at.f1644n = r0     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.proguard.aa r0 = com.tencent.bugly.proguard.C2231aa.m457a((android.content.Context) r5)     // Catch:{ all -> 0x00e1 }
            boolean r2 = r7.isEnableRecordAnrMainStack()     // Catch:{ all -> 0x00e1 }
            r0.f1438S = r2     // Catch:{ all -> 0x00e1 }
            boolean r0 = r7.isEnableCatchAnrTrace()     // Catch:{ all -> 0x00e1 }
            if (r0 != 0) goto L_0x005b
            com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler r0 = r6.f1653t     // Catch:{ all -> 0x00e1 }
            r0.disableCatchAnrTrace()     // Catch:{ all -> 0x00e1 }
            goto L_0x0066
        L_0x005b:
            com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler r0 = r6.f1653t     // Catch:{ all -> 0x00e1 }
            r0.enableCatchAnrTrace()     // Catch:{ all -> 0x00e1 }
            goto L_0x0066
        L_0x0061:
            com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler r0 = r6.f1653t     // Catch:{ all -> 0x00e1 }
            r0.enableCatchAnrTrace()     // Catch:{ all -> 0x00e1 }
        L_0x0066:
            com.tencent.bugly.proguard.aa r0 = com.tencent.bugly.proguard.C2231aa.m459b()     // Catch:{ all -> 0x00e1 }
            java.lang.String r0 = r0.f1472d     // Catch:{ all -> 0x00e1 }
            android.content.Context r2 = r6.f1650c     // Catch:{ all -> 0x00e1 }
            java.lang.String r2 = com.tencent.bugly.proguard.C2369z.m1059a((android.content.Context) r2)     // Catch:{ all -> 0x00e1 }
            boolean r0 = r0.equals(r2)     // Catch:{ all -> 0x00e1 }
            if (r0 == 0) goto L_0x007d
            com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler r0 = r6.f1653t     // Catch:{ all -> 0x00e1 }
            r0.removeEmptyNativeRecordFiles()     // Catch:{ all -> 0x00e1 }
        L_0x007d:
            if (r7 == 0) goto L_0x0091
            boolean r0 = r7.isEnableNativeCrashMonitor()     // Catch:{ all -> 0x00e1 }
            if (r0 == 0) goto L_0x0086
            goto L_0x0091
        L_0x0086:
            java.lang.String r0 = "[crash] Closed native crash monitor!"
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r0, (java.lang.Object[]) r2)     // Catch:{ all -> 0x00e1 }
            r6.mo28044d()     // Catch:{ all -> 0x00e1 }
            goto L_0x0094
        L_0x0091:
            r6.mo28045e()     // Catch:{ all -> 0x00e1 }
        L_0x0094:
            if (r7 == 0) goto L_0x00a8
            boolean r0 = r7.isEnableANRCrashMonitor()     // Catch:{ all -> 0x00e1 }
            if (r0 == 0) goto L_0x009d
            goto L_0x00a8
        L_0x009d:
            java.lang.String r0 = "[crash] Closed ANR monitor!"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r0, (java.lang.Object[]) r1)     // Catch:{ all -> 0x00e1 }
            r6.mo28047g()     // Catch:{ all -> 0x00e1 }
            goto L_0x00ab
        L_0x00a8:
            r6.mo28046f()     // Catch:{ all -> 0x00e1 }
        L_0x00ab:
            if (r7 == 0) goto L_0x00b3
            boolean r0 = r7.isMerged()     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.proguard.C2293at.f1635e = r0     // Catch:{ all -> 0x00e1 }
        L_0x00b3:
            if (r7 == 0) goto L_0x00ba
            long r0 = r7.getAppReportDelay()     // Catch:{ all -> 0x00e1 }
            goto L_0x00bc
        L_0x00ba:
            r0 = 0
        L_0x00bc:
            r6.mo28039a((long) r0)     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler r6 = r6.f1653t     // Catch:{ all -> 0x00e1 }
            r6.checkUploadRecordCrash()     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.proguard.C2298au.m756a((android.content.Context) r5)     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.proguard.aq r6 = com.tencent.bugly.proguard.C2274aq.m679a()     // Catch:{ all -> 0x00e1 }
            java.lang.String r7 = "android.net.conn.CONNECTIVITY_CHANGE"
            r6.mo28028a((java.lang.String) r7)     // Catch:{ all -> 0x00e1 }
            r6.mo28027a((android.content.Context) r5)     // Catch:{ all -> 0x00e1 }
            com.tencent.bugly.proguard.u r5 = com.tencent.bugly.proguard.C2361u.m1013a()     // Catch:{ all -> 0x00e1 }
            int r6 = f1283c     // Catch:{ all -> 0x00e1 }
            int r6 = r6 - r3
            f1283c = r6     // Catch:{ all -> 0x00e1 }
            r5.mo28159a((int) r6)     // Catch:{ all -> 0x00e1 }
            monitor-exit(r4)
            return
        L_0x00e1:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        L_0x00e4:
            monitor-exit(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.CrashModule.init(android.content.Context, boolean, com.tencent.bugly.BuglyStrategy):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0051, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m407a(android.content.Context r7, com.tencent.bugly.BuglyStrategy r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            if (r8 != 0) goto L_0x0005
            monitor-exit(r6)
            return
        L_0x0005:
            java.lang.String r0 = r8.getLibBuglySOFilePath()     // Catch:{ all -> 0x0052 }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0052 }
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0020
            com.tencent.bugly.proguard.aa r7 = com.tencent.bugly.proguard.C2231aa.m457a((android.content.Context) r7)     // Catch:{ all -> 0x0052 }
            r7.f1488t = r0     // Catch:{ all -> 0x0052 }
            java.lang.String r7 = "setted libBugly.so file path :%s"
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x0052 }
            r1[r3] = r0     // Catch:{ all -> 0x0052 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r7, (java.lang.Object[]) r1)     // Catch:{ all -> 0x0052 }
        L_0x0020:
            com.tencent.bugly.BuglyStrategy$a r7 = r8.getCrashHandleCallback()     // Catch:{ all -> 0x0052 }
            if (r7 == 0) goto L_0x0033
            com.tencent.bugly.BuglyStrategy$a r7 = r8.getCrashHandleCallback()     // Catch:{ all -> 0x0052 }
            r6.f1286b = r7     // Catch:{ all -> 0x0052 }
            java.lang.String r7 = "setted CrashHanldeCallback"
            java.lang.Object[] r0 = new java.lang.Object[r3]     // Catch:{ all -> 0x0052 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r7, (java.lang.Object[]) r0)     // Catch:{ all -> 0x0052 }
        L_0x0033:
            long r0 = r8.getAppReportDelay()     // Catch:{ all -> 0x0052 }
            r4 = 0
            int r7 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r7 <= 0) goto L_0x0050
            long r7 = r8.getAppReportDelay()     // Catch:{ all -> 0x0052 }
            r6.f1285a = r7     // Catch:{ all -> 0x0052 }
            java.lang.String r0 = "setted delay: %d"
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x0052 }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0052 }
            r1[r3] = r7     // Catch:{ all -> 0x0052 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r0, (java.lang.Object[]) r1)     // Catch:{ all -> 0x0052 }
        L_0x0050:
            monitor-exit(r6)
            return
        L_0x0052:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.CrashModule.m407a(android.content.Context, com.tencent.bugly.BuglyStrategy):void");
    }

    public void onServerStrategyChanged(StrategyBean strategyBean) {
        C2293at a;
        if (strategyBean != null && (a = C2293at.m738a()) != null) {
            a.f1652s.mo28059a(strategyBean);
            a.f1653t.onStrategyChanged(strategyBean);
            a.f1656w.mo28069b();
        }
    }

    public String[] getTables() {
        return new String[]{"t_cr"};
    }
}
