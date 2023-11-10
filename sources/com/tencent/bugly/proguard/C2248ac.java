package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Parcelable;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.util.List;

/* renamed from: com.tencent.bugly.proguard.ac */
/* compiled from: BUGLY */
public final class C2248ac {

    /* renamed from: a */
    public static int f1498a = 1000;

    /* renamed from: b */
    public static long f1499b = 259200000;

    /* renamed from: d */
    private static C2248ac f1500d;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public static String f1501i;

    /* renamed from: c */
    public final C2263ak f1502c;

    /* renamed from: e */
    private final List<C2348o> f1503e;

    /* renamed from: f */
    private final StrategyBean f1504f;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public StrategyBean f1505g = null;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public Context f1506h;

    private C2248ac(Context context, List<C2348o> list) {
        this.f1506h = context;
        if (C2231aa.m457a(context) != null) {
            String str = C2231aa.m457a(context).f1427H;
            if ("oversea".equals(str)) {
                StrategyBean.f1313a = "https://astat.bugly.qcloud.com/rqd/async";
                StrategyBean.f1314b = "https://astat.bugly.qcloud.com/rqd/async";
            } else if ("na_https".equals(str)) {
                StrategyBean.f1313a = "https://astat.bugly.cros.wr.pvp.net/:8180/rqd/async";
                StrategyBean.f1314b = "https://astat.bugly.cros.wr.pvp.net/:8180/rqd/async";
            }
        }
        this.f1504f = new StrategyBean();
        this.f1503e = list;
        this.f1502c = C2263ak.m595a();
    }

    /* renamed from: a */
    public static synchronized C2248ac m534a(Context context, List<C2348o> list) {
        C2248ac acVar;
        synchronized (C2248ac.class) {
            if (f1500d == null) {
                f1500d = new C2248ac(context, list);
            }
            acVar = f1500d;
        }
        return acVar;
    }

    /* renamed from: a */
    public static synchronized C2248ac m533a() {
        C2248ac acVar;
        synchronized (C2248ac.class) {
            acVar = f1500d;
        }
        return acVar;
    }

    /* renamed from: b */
    public final synchronized boolean mo27995b() {
        return this.f1505g != null;
    }

    /* renamed from: c */
    public final StrategyBean mo27996c() {
        StrategyBean strategyBean = this.f1505g;
        if (strategyBean != null) {
            if (!C2273ap.m674c(strategyBean.f1329q)) {
                this.f1505g.f1329q = StrategyBean.f1313a;
            }
            if (!C2273ap.m674c(this.f1505g.f1330r)) {
                this.f1505g.f1330r = StrategyBean.f1314b;
            }
            return this.f1505g;
        }
        if (!C2273ap.m657a(f1501i) && C2273ap.m674c(f1501i)) {
            this.f1504f.f1329q = f1501i;
            this.f1504f.f1330r = f1501i;
        }
        return this.f1504f;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo27993a(StrategyBean strategyBean, boolean z) {
        C2265al.m609c("[Strategy] Notify %s", C2357s.class.getName());
        C2357s.m997a(strategyBean, z);
        for (C2348o next : this.f1503e) {
            try {
                C2265al.m609c("[Strategy] Notify %s", next.getClass().getName());
                next.onServerStrategyChanged(strategyBean);
            } catch (Throwable th) {
                if (!C2265al.m605a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    /* renamed from: a */
    public static void m535a(String str) {
        if (C2273ap.m657a(str) || !C2273ap.m674c(str)) {
            C2265al.m610d("URL user set is invalid.", new Object[0]);
        } else {
            f1501i = str;
        }
    }

    /* renamed from: a */
    public final void mo27994a(C2332bt btVar) {
        if (btVar != null) {
            if (this.f1505g == null || btVar.f1851h != this.f1505g.f1327o) {
                StrategyBean strategyBean = new StrategyBean();
                strategyBean.f1318f = btVar.f1844a;
                strategyBean.f1320h = btVar.f1846c;
                strategyBean.f1319g = btVar.f1845b;
                if (C2273ap.m657a(f1501i) || !C2273ap.m674c(f1501i)) {
                    if (C2273ap.m674c(btVar.f1847d)) {
                        C2265al.m609c("[Strategy] Upload url changes to %s", btVar.f1847d);
                        strategyBean.f1329q = btVar.f1847d;
                    }
                    if (C2273ap.m674c(btVar.f1848e)) {
                        C2265al.m609c("[Strategy] Exception upload url changes to %s", btVar.f1848e);
                        strategyBean.f1330r = btVar.f1848e;
                    }
                }
                if (btVar.f1849f != null && !C2273ap.m657a(btVar.f1849f.f1839a)) {
                    strategyBean.f1331s = btVar.f1849f.f1839a;
                }
                if (btVar.f1851h != 0) {
                    strategyBean.f1327o = btVar.f1851h;
                }
                if (!(btVar == null || btVar.f1850g == null || btVar.f1850g.size() <= 0)) {
                    strategyBean.f1332t = btVar.f1850g;
                    String str = btVar.f1850g.get("B11");
                    strategyBean.f1321i = str != null && str.equals("1");
                    String str2 = btVar.f1850g.get("B3");
                    if (str2 != null) {
                        strategyBean.f1335w = Long.parseLong(str2);
                    }
                    strategyBean.f1328p = (long) btVar.f1855l;
                    strategyBean.f1334v = (long) btVar.f1855l;
                    String str3 = btVar.f1850g.get("B27");
                    if (str3 != null && str3.length() > 0) {
                        try {
                            int parseInt = Integer.parseInt(str3);
                            if (parseInt > 0) {
                                strategyBean.f1333u = parseInt;
                            }
                        } catch (Exception e) {
                            if (!C2265al.m605a(e)) {
                                e.printStackTrace();
                            }
                        }
                    }
                    String str4 = btVar.f1850g.get("B25");
                    strategyBean.f1323k = str4 != null && str4.equals("1");
                }
                C2265al.m604a("[Strategy] enableCrashReport:%b, enableQuery:%b, enableUserInfo:%b, enableAnr:%b, enableBlock:%b, enableSession:%b, enableSessionTimer:%b, sessionOverTime:%d, enableCocos:%b, strategyLastUpdateTime:%d", Boolean.valueOf(strategyBean.f1318f), Boolean.valueOf(strategyBean.f1320h), Boolean.valueOf(strategyBean.f1319g), Boolean.valueOf(strategyBean.f1321i), Boolean.valueOf(strategyBean.f1322j), Boolean.valueOf(strategyBean.f1325m), Boolean.valueOf(strategyBean.f1326n), Long.valueOf(strategyBean.f1328p), Boolean.valueOf(strategyBean.f1323k), Long.valueOf(strategyBean.f1327o));
                this.f1505g = strategyBean;
                if (!C2273ap.m674c(btVar.f1847d)) {
                    C2265al.m609c("[Strategy] download url is null", new Object[0]);
                    this.f1505g.f1329q = "";
                }
                if (!C2273ap.m674c(btVar.f1848e)) {
                    C2265al.m609c("[Strategy] download crashurl is null", new Object[0]);
                    this.f1505g.f1330r = "";
                }
                C2365w.m1033a().mo28172b(2);
                C2368y yVar = new C2368y();
                yVar.f1989b = 2;
                yVar.f1988a = strategyBean.f1316d;
                yVar.f1992e = strategyBean.f1317e;
                yVar.f1994g = C2273ap.m658a((Parcelable) strategyBean);
                C2365w.m1033a().mo28171a(yVar);
                mo27993a(strategyBean, true);
            }
        }
    }

    /* renamed from: d */
    public static StrategyBean m537d() {
        List<C2368y> a = C2365w.m1033a().mo28167a(2);
        if (a == null || a.size() <= 0) {
            return null;
        }
        C2368y yVar = a.get(0);
        if (yVar.f1994g != null) {
            return (StrategyBean) C2273ap.m639a(yVar.f1994g, StrategyBean.CREATOR);
        }
        return null;
    }
}
