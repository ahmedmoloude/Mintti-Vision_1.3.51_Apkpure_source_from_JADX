package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.proguard.C2253ag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import p036no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

/* renamed from: com.tencent.bugly.proguard.at */
/* compiled from: BUGLY */
public final class C2293at {

    /* renamed from: C */
    private static C2293at f1631C = null;

    /* renamed from: a */
    public static int f1632a = 0;

    /* renamed from: b */
    public static boolean f1633b = false;

    /* renamed from: d */
    public static int f1634d = 2;

    /* renamed from: e */
    public static boolean f1635e = false;

    /* renamed from: f */
    public static int f1636f = 20480;

    /* renamed from: g */
    public static int f1637g = 3000;

    /* renamed from: h */
    public static int f1638h = 20480;

    /* renamed from: i */
    public static long f1639i = 604800000;

    /* renamed from: j */
    public static String f1640j = null;

    /* renamed from: k */
    public static boolean f1641k = false;

    /* renamed from: l */
    public static String f1642l = null;

    /* renamed from: m */
    public static int f1643m = 5000;

    /* renamed from: n */
    public static boolean f1644n = true;

    /* renamed from: o */
    public static boolean f1645o = false;

    /* renamed from: p */
    public static String f1646p;

    /* renamed from: q */
    public static String f1647q;

    /* renamed from: A */
    public int f1648A = 31;

    /* renamed from: B */
    public boolean f1649B = false;

    /* renamed from: c */
    public final Context f1650c;

    /* renamed from: r */
    public final C2277as f1651r;

    /* renamed from: s */
    public final C2301av f1652s;

    /* renamed from: t */
    public final NativeCrashHandler f1653t;

    /* renamed from: u */
    public final C2248ac f1654u;

    /* renamed from: v */
    public final C2263ak f1655v;

    /* renamed from: w */
    public final C2304ay f1656w;

    /* renamed from: x */
    public BuglyStrategy.C2217a f1657x;

    /* renamed from: y */
    public C2302aw f1658y;

    /* renamed from: z */
    public Boolean f1659z;

    private C2293at(Context context, C2263ak akVar, boolean z, BuglyStrategy.C2217a aVar) {
        f1632a = 1004;
        Context a = C2273ap.m635a(context);
        this.f1650c = a;
        C2248ac a2 = C2248ac.m533a();
        this.f1654u = a2;
        this.f1655v = akVar;
        this.f1657x = aVar;
        this.f1658y = null;
        C2277as asVar = new C2277as(a, C2259ai.m569a(), C2365w.m1033a(), a2, aVar);
        this.f1651r = asVar;
        C2231aa a3 = C2231aa.m457a(a);
        this.f1652s = new C2301av(a, asVar, a2, a3);
        NativeCrashHandler instance = NativeCrashHandler.getInstance(a, a3, asVar, a2, akVar, z, (String) null);
        this.f1653t = instance;
        a3.f1433N = instance;
        if (C2304ay.f1700f == null) {
            C2304ay.f1700f = new C2304ay(a, a2, a3, akVar, asVar);
        }
        this.f1656w = C2304ay.f1700f;
    }

    /* renamed from: a */
    public static synchronized C2293at m739a(Context context, boolean z, BuglyStrategy.C2217a aVar) {
        C2293at atVar;
        synchronized (C2293at.class) {
            if (f1631C == null) {
                f1631C = new C2293at(context, C2263ak.m595a(), z, aVar);
            }
            atVar = f1631C;
        }
        return atVar;
    }

    /* renamed from: a */
    public static synchronized C2293at m738a() {
        C2293at atVar;
        synchronized (C2293at.class) {
            atVar = f1631C;
        }
        return atVar;
    }

    /* renamed from: d */
    public final void mo28044d() {
        this.f1653t.setUserOpened(false);
    }

    /* renamed from: e */
    public final void mo28045e() {
        this.f1653t.setUserOpened(true);
    }

    /* renamed from: f */
    public final void mo28046f() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public final void run() {
                NativeCrashHandler.getInstance().unBlockSigquit(true);
            }
        });
        this.f1656w.mo28070b(true);
    }

    /* renamed from: g */
    public final void mo28047g() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public final void run() {
                NativeCrashHandler.getInstance().unBlockSigquit(false);
            }
        });
        this.f1656w.mo28070b(false);
    }

    /* renamed from: a */
    public final synchronized void mo28041a(boolean z, boolean z2, boolean z3) {
        this.f1653t.testNativeCrash(z, z2, z3);
    }

    /* renamed from: i */
    public final boolean mo28049i() {
        return this.f1656w.f1701a.get();
    }

    /* renamed from: a */
    public final void mo28040a(CrashDetailBean crashDetailBean) {
        this.f1651r.mo28036b(crashDetailBean);
    }

    /* renamed from: a */
    public final void mo28039a(long j) {
        C2263ak.m595a().mo28018a(new Thread() {
            public final void run() {
                ArrayList arrayList;
                if (!C2273ap.m668b(C2293at.this.f1650c, "local_crash_lock")) {
                    C2265al.m609c("Failed to lock file for uploading local crash.", new Object[0]);
                    return;
                }
                C2253ag a = C2253ag.C2255a.f1515a;
                List<C2253ag.C2256b> a2 = C2253ag.m559a();
                if (a2 == null || a2.isEmpty()) {
                    C2265al.m609c("sla local data is null", new Object[0]);
                } else {
                    C2265al.m609c("sla load local data list size:%s", Integer.valueOf(a2.size()));
                    Iterator<C2253ag.C2256b> it = a2.iterator();
                    ArrayList arrayList2 = new ArrayList();
                    while (it.hasNext()) {
                        C2253ag.C2256b next = it.next();
                        if (next.f1517b < C2273ap.m661b() - 604800000) {
                            C2265al.m609c("sla local data is expired:%s", next.f1518c);
                            arrayList2.add(next);
                            it.remove();
                        }
                    }
                    C2253ag.m562d(arrayList2);
                    a.mo28001b(a2);
                }
                List<CrashDetailBean> a3 = C2277as.m691a();
                if (a3 == null || a3.size() <= 0) {
                    C2265al.m609c("no crash need to be uploaded at this start", new Object[0]);
                } else {
                    C2265al.m609c("Size of crash list: %s", Integer.valueOf(a3.size()));
                    int size = a3.size();
                    if (((long) size) > 20) {
                        ArrayList arrayList3 = new ArrayList();
                        Collections.sort(a3);
                        for (int i = 0; ((long) i) < 20; i++) {
                            arrayList3.add(a3.get((size - 1) - i));
                        }
                        arrayList = arrayList3;
                    } else {
                        arrayList = a3;
                    }
                    C2293at.this.f1651r.mo28034a(arrayList, 0, false, false, false);
                }
                C2273ap.m673c(C2293at.this.f1650c, "local_crash_lock");
            }
        }, j);
    }

    /* renamed from: j */
    public final boolean mo28050j() {
        return (this.f1648A & 16) > 0;
    }

    /* renamed from: k */
    public final boolean mo28051k() {
        return (this.f1648A & 8) > 0;
    }

    /* renamed from: b */
    public final synchronized void mo28042b() {
        this.f1652s.mo28058a();
        mo28045e();
        mo28046f();
    }

    /* renamed from: c */
    public final synchronized void mo28043c() {
        this.f1652s.mo28061b();
        mo28044d();
        mo28047g();
    }

    /* renamed from: h */
    public final synchronized void mo28048h() {
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (i < 30) {
                try {
                    C2265al.m604a("try main sleep for make a test anr! try:%d/30 , kill it if you don't want to wait!", Integer.valueOf(i2));
                    C2273ap.m665b((long) BootloaderScanner.TIMEOUT);
                    i = i2;
                } catch (Throwable th) {
                    if (!C2265al.m605a(th)) {
                        th.printStackTrace();
                    }
                    return;
                }
            }
        }
    }
}
