package com.tencent.bugly.proguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

/* renamed from: com.tencent.bugly.proguard.aq */
/* compiled from: BUGLY */
public final class C2274aq extends BroadcastReceiver {
    /* access modifiers changed from: private */

    /* renamed from: d */
    public static C2274aq f1601d;
    /* access modifiers changed from: private */

    /* renamed from: a */
    public IntentFilter f1602a = new IntentFilter();
    /* access modifiers changed from: private */

    /* renamed from: b */
    public Context f1603b;

    /* renamed from: c */
    private String f1604c;

    /* renamed from: e */
    private boolean f1605e = true;

    /* renamed from: a */
    public static synchronized C2274aq m679a() {
        C2274aq aqVar;
        synchronized (C2274aq.class) {
            if (f1601d == null) {
                f1601d = new C2274aq();
            }
            aqVar = f1601d;
        }
        return aqVar;
    }

    /* renamed from: a */
    public final synchronized void mo28028a(String str) {
        if (!this.f1602a.hasAction(str)) {
            this.f1602a.addAction(str);
        }
        C2265al.m609c("add action %s", str);
    }

    /* renamed from: a */
    public final synchronized void mo28027a(Context context) {
        this.f1603b = context;
        C2273ap.m656a((Runnable) new Runnable() {
            public final void run() {
                try {
                    C2265al.m603a((Class) C2274aq.f1601d.getClass(), "Register broadcast receiver of Bugly.", new Object[0]);
                    synchronized (this) {
                        C2274aq.this.f1603b.registerReceiver(C2274aq.f1601d, C2274aq.this.f1602a, "com.tencent.bugly.BuglyBroadcastReceiver.permission", (Handler) null);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    /* renamed from: b */
    public final synchronized void mo28029b(Context context) {
        try {
            C2265al.m603a((Class) getClass(), "Unregister broadcast receiver of Bugly.", new Object[0]);
            context.unregisterReceiver(this);
            this.f1603b = context;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
        }
    }

    public final void onReceive(Context context, Intent intent) {
        try {
            m680a(context, intent);
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a4, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00b2, code lost:
        return false;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean m680a(android.content.Context r8, android.content.Intent r9) {
        /*
            r7 = this;
            monitor-enter(r7)
            r0 = 0
            if (r8 == 0) goto L_0x00b1
            if (r9 == 0) goto L_0x00b1
            java.lang.String r9 = r9.getAction()     // Catch:{ all -> 0x00ae }
            java.lang.String r1 = "android.net.conn.CONNECTIVITY_CHANGE"
            boolean r9 = r9.equals(r1)     // Catch:{ all -> 0x00ae }
            if (r9 != 0) goto L_0x0014
            goto L_0x00b1
        L_0x0014:
            boolean r9 = r7.f1605e     // Catch:{ all -> 0x00ae }
            r1 = 1
            if (r9 == 0) goto L_0x001d
            r7.f1605e = r0     // Catch:{ all -> 0x00ae }
            monitor-exit(r7)
            return r1
        L_0x001d:
            android.content.Context r9 = r7.f1603b     // Catch:{ all -> 0x00ae }
            java.lang.String r9 = com.tencent.bugly.proguard.C2232ab.m501c(r9)     // Catch:{ all -> 0x00ae }
            java.lang.String r2 = "is Connect BC "
            java.lang.String r3 = java.lang.String.valueOf(r9)     // Catch:{ all -> 0x00ae }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ all -> 0x00ae }
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ all -> 0x00ae }
            com.tencent.bugly.proguard.C2265al.m609c(r2, r3)     // Catch:{ all -> 0x00ae }
            java.lang.String r2 = "network %s changed to %s"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00ae }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ae }
            r4.<init>()     // Catch:{ all -> 0x00ae }
            java.lang.String r5 = r7.f1604c     // Catch:{ all -> 0x00ae }
            r4.append(r5)     // Catch:{ all -> 0x00ae }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00ae }
            r3[r0] = r4     // Catch:{ all -> 0x00ae }
            java.lang.String r4 = java.lang.String.valueOf(r9)     // Catch:{ all -> 0x00ae }
            r3[r1] = r4     // Catch:{ all -> 0x00ae }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r2, (java.lang.Object[]) r3)     // Catch:{ all -> 0x00ae }
            if (r9 != 0) goto L_0x0057
            r8 = 0
            r7.f1604c = r8     // Catch:{ all -> 0x00ae }
            monitor-exit(r7)
            return r1
        L_0x0057:
            java.lang.String r2 = r7.f1604c     // Catch:{ all -> 0x00ae }
            r7.f1604c = r9     // Catch:{ all -> 0x00ae }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00ae }
            com.tencent.bugly.proguard.ac r5 = com.tencent.bugly.proguard.C2248ac.m533a()     // Catch:{ all -> 0x00ae }
            com.tencent.bugly.proguard.ai r6 = com.tencent.bugly.proguard.C2259ai.m569a()     // Catch:{ all -> 0x00ae }
            com.tencent.bugly.proguard.aa r8 = com.tencent.bugly.proguard.C2231aa.m457a((android.content.Context) r8)     // Catch:{ all -> 0x00ae }
            if (r5 == 0) goto L_0x00a5
            if (r6 == 0) goto L_0x00a5
            if (r8 != 0) goto L_0x0072
            goto L_0x00a5
        L_0x0072:
            boolean r8 = r9.equals(r2)     // Catch:{ all -> 0x00ae }
            if (r8 != 0) goto L_0x00a3
            int r8 = com.tencent.bugly.proguard.C2293at.f1632a     // Catch:{ all -> 0x00ae }
            long r8 = r6.mo28005a((int) r8)     // Catch:{ all -> 0x00ae }
            long r3 = r3 - r8
            r8 = 30000(0x7530, double:1.4822E-319)
            int r2 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
            if (r2 <= 0) goto L_0x00a3
            java.lang.String r8 = "try to upload crash on network changed."
            java.lang.Object[] r9 = new java.lang.Object[r0]     // Catch:{ all -> 0x00ae }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r8, (java.lang.Object[]) r9)     // Catch:{ all -> 0x00ae }
            com.tencent.bugly.proguard.at r8 = com.tencent.bugly.proguard.C2293at.m738a()     // Catch:{ all -> 0x00ae }
            if (r8 == 0) goto L_0x0097
            r2 = 0
            r8.mo28039a((long) r2)     // Catch:{ all -> 0x00ae }
        L_0x0097:
            java.lang.String r8 = "try to upload userinfo on network changed."
            java.lang.Object[] r9 = new java.lang.Object[r0]     // Catch:{ all -> 0x00ae }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r8, (java.lang.Object[]) r9)     // Catch:{ all -> 0x00ae }
            com.tencent.bugly.proguard.r r8 = com.tencent.bugly.proguard.C2357s.f1931b     // Catch:{ all -> 0x00ae }
            r8.mo28145b()     // Catch:{ all -> 0x00ae }
        L_0x00a3:
            monitor-exit(r7)
            return r1
        L_0x00a5:
            java.lang.String r8 = "not inited BC not work"
            java.lang.Object[] r9 = new java.lang.Object[r0]     // Catch:{ all -> 0x00ae }
            com.tencent.bugly.proguard.C2265al.m610d(r8, r9)     // Catch:{ all -> 0x00ae }
            monitor-exit(r7)
            return r1
        L_0x00ae:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        L_0x00b1:
            monitor-exit(r7)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2274aq.m680a(android.content.Context, android.content.Intent):boolean");
    }
}
