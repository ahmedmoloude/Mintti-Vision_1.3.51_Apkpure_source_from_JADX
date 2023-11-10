package com.tencent.bugly.proguard;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import p036no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

/* renamed from: com.tencent.bugly.proguard.bg */
/* compiled from: BUGLY */
public final class C2318bg extends Thread {

    /* renamed from: a */
    public C2317bf f1751a;

    /* renamed from: b */
    public boolean f1752b = true;

    /* renamed from: c */
    private boolean f1753c = false;

    /* renamed from: d */
    private boolean f1754d = true;

    /* renamed from: e */
    private boolean f1755e = false;

    /* renamed from: f */
    private int f1756f = 1;

    /* renamed from: g */
    private C2319a f1757g;

    /* renamed from: com.tencent.bugly.proguard.bg$a */
    /* compiled from: BUGLY */
    public interface C2319a {
    }

    /* renamed from: a */
    public final boolean mo28081a() {
        this.f1753c = true;
        if (!isAlive()) {
            return false;
        }
        try {
            interrupt();
        } catch (Exception e) {
            C2265al.m608b(e);
        }
        C2265al.m610d("MainHandlerChecker is reset to null.", new Object[0]);
        this.f1751a = null;
        return true;
    }

    public final void run() {
        long currentTimeMillis = System.currentTimeMillis();
        while (!this.f1753c) {
            try {
                C2317bf bfVar = this.f1751a;
                boolean z = false;
                if (bfVar == null) {
                    C2265al.m609c("Main handler checker is null. Stop thread monitor.", new Object[0]);
                    return;
                }
                if (bfVar.f1746c) {
                    bfVar.f1746c = false;
                    bfVar.f1747d = SystemClock.uptimeMillis();
                    bfVar.f1744a.post(bfVar);
                }
                m819a(bfVar);
                boolean z2 = true;
                if (this.f1752b) {
                    if (this.f1754d) {
                        long b = bfVar.mo28077b();
                        if (b > 1510) {
                            if (b < 199990) {
                                if (b <= 5010) {
                                    this.f1756f = 1;
                                    C2265al.m609c("timeSinceMsgSent in [2s, 5s], record stack", new Object[0]);
                                    z = true;
                                } else {
                                    int i = this.f1756f + 1;
                                    this.f1756f = i;
                                    if ((i & (i - 1)) != 0) {
                                        z2 = false;
                                    }
                                    if (z2) {
                                        C2265al.m609c("timeSinceMsgSent in (5s, 200s), should record stack:true", new Object[0]);
                                    }
                                    z = z2;
                                }
                            }
                        }
                    }
                }
                if (z) {
                    bfVar.mo28079d();
                }
                if (this.f1757g != null && this.f1754d) {
                    bfVar.mo28076a();
                    bfVar.mo28077b();
                }
                C2273ap.m665b(500 - ((System.currentTimeMillis() - currentTimeMillis) % 500));
            } catch (Exception e) {
                C2265al.m608b(e);
            } catch (OutOfMemoryError e2) {
                C2265al.m608b(e2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001f, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m819a(com.tencent.bugly.proguard.C2317bf r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.f1754d     // Catch:{ all -> 0x0020 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            boolean r0 = r2.f1755e     // Catch:{ all -> 0x0020 }
            if (r0 == 0) goto L_0x001e
            boolean r3 = r3.mo28076a()     // Catch:{ all -> 0x0020 }
            if (r3 != 0) goto L_0x001e
            java.lang.String r3 = "Restart getting main stack trace."
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x0020 }
            com.tencent.bugly.proguard.C2265al.m609c(r3, r1)     // Catch:{ all -> 0x0020 }
            r3 = 1
            r2.f1754d = r3     // Catch:{ all -> 0x0020 }
            r2.f1755e = r0     // Catch:{ all -> 0x0020 }
        L_0x001e:
            monitor-exit(r2)
            return
        L_0x0020:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2318bg.m819a(com.tencent.bugly.proguard.bf):void");
    }

    /* renamed from: c */
    public final synchronized void mo28083c() {
        this.f1754d = false;
        C2265al.m609c("Record stack trace is disabled.", new Object[0]);
    }

    /* renamed from: d */
    public final synchronized void mo28084d() {
        this.f1755e = true;
    }

    /* renamed from: b */
    public final boolean mo28082b() {
        Handler handler = new Handler(Looper.getMainLooper());
        C2317bf bfVar = this.f1751a;
        if (bfVar != null) {
            bfVar.f1745b = BootloaderScanner.TIMEOUT;
        } else {
            this.f1751a = new C2317bf(handler, handler.getLooper().getThread().getName());
        }
        if (isAlive()) {
            return false;
        }
        try {
            start();
            return true;
        } catch (Exception e) {
            C2265al.m608b(e);
            return false;
        }
    }
}
