package com.tencent.bugly.proguard;

import android.content.Context;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import java.util.LinkedHashMap;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.au */
/* compiled from: BUGLY */
public final class C2298au {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static C2298au f1671a;

    /* renamed from: b */
    private C2248ac f1672b;

    /* renamed from: c */
    private C2231aa f1673c;

    /* renamed from: d */
    private C2277as f1674d;

    /* renamed from: e */
    private Context f1675e;

    private C2298au(Context context) {
        C2293at a = C2293at.m738a();
        if (a != null) {
            this.f1672b = C2248ac.m533a();
            this.f1673c = C2231aa.m457a(context);
            this.f1674d = a.f1651r;
            this.f1675e = context;
            C2263ak.m595a().mo28017a(new Runnable() {
                public final void run() {
                    C2298au.m757a(C2298au.this);
                }
            });
        }
    }

    /* renamed from: a */
    public static C2298au m756a(Context context) {
        if (f1671a == null) {
            f1671a = new C2298au(context);
        }
        return f1671a;
    }

    /* renamed from: a */
    public static void m759a(Thread thread, int i, String str, String str2, String str3, Map<String, String> map) {
        final Thread thread2 = thread;
        final int i2 = i;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final Map<String, String> map2 = map;
        C2263ak.m595a().mo28017a(new Runnable() {
            public final void run() {
                try {
                    if (C2298au.f1671a == null) {
                        C2265al.m611e("[ExtraCrashManager] Extra crash manager has not been initialized.", new Object[0]);
                    } else {
                        C2298au.m758a(C2298au.f1671a, thread2, i2, str4, str5, str6, map2);
                    }
                } catch (Throwable th) {
                    if (!C2265al.m608b(th)) {
                        th.printStackTrace();
                    }
                    C2265al.m611e("[ExtraCrashManager] Crash error %s %s %s", str4, str5, str6);
                }
            }
        });
    }

    /* renamed from: a */
    static /* synthetic */ void m757a(C2298au auVar) {
        C2265al.m609c("[ExtraCrashManager] Trying to notify Bugly agents.", new Object[0]);
        try {
            Class<?> cls = Class.forName("com.tencent.bugly.agent.GameAgent");
            auVar.f1673c.getClass();
            C2273ap.m654a(cls, "sdkPackageName", (Object) "com.tencent.bugly");
            C2265al.m609c("[ExtraCrashManager] Bugly game agent has been notified.", new Object[0]);
        } catch (Throwable unused) {
            C2265al.m604a("[ExtraCrashManager] no game agent", new Object[0]);
        }
    }

    /* renamed from: a */
    static /* synthetic */ void m758a(C2298au auVar, Thread thread, int i, String str, String str2, String str3, Map map) {
        Thread thread2;
        String str4;
        String str5;
        C2298au auVar2 = auVar;
        int i2 = i;
        String str6 = str;
        String str7 = str2;
        String str8 = str3;
        Map map2 = map;
        if (thread == null) {
            thread2 = Thread.currentThread();
        } else {
            thread2 = thread;
        }
        int i3 = 5;
        if (i2 == 4) {
            str4 = "Unity";
        } else if (i2 == 5 || i2 == 6) {
            str4 = "Cocos";
        } else if (i2 != 8) {
            C2265al.m610d("[ExtraCrashManager] Unknown extra crash type: %d", Integer.valueOf(i));
            return;
        } else {
            str4 = "H5";
        }
        C2265al.m611e("[ExtraCrashManager] %s Crash Happen", str4);
        if (!auVar2.f1672b.mo27995b()) {
            C2265al.m610d("[ExtraCrashManager] There is no remote strategy, but still store it.", new Object[0]);
        }
        StrategyBean c = auVar2.f1672b.mo27996c();
        if (!c.f1318f) {
            if (auVar2.f1672b.mo27995b()) {
                C2265al.m611e("[ExtraCrashManager] Crash report was closed by remote. Will not upload to Bugly , print local for helpful!", new Object[0]);
                String a = C2273ap.m640a();
                String str9 = auVar2.f1673c.f1472d;
                String name = thread2.getName();
                C2277as.m696a(str4, a, str9, name, str6 + "\n" + str7 + "\n" + str8, (CrashDetailBean) null);
                C2265al.m611e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                return;
            }
        }
        if (i2 == 5 || i2 == 6) {
            try {
                if (!c.f1323k) {
                    C2265al.m611e("[ExtraCrashManager] %s report is disabled.", str4);
                    return;
                }
            } catch (Throwable th) {
                if (!C2265al.m605a(th)) {
                    th.printStackTrace();
                }
                return;
            } finally {
                C2265al.m611e("[ExtraCrashManager] Successfully handled.", new Object[0]);
            }
        } else if (i2 == 8) {
            if (!c.f1324l) {
                C2265al.m611e("[ExtraCrashManager] %s report is disabled.", str4);
                C2265al.m611e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                return;
            }
        }
        if (i2 != 8) {
            i3 = i2;
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.f1338C = C2232ab.m511j();
        crashDetailBean.f1339D = C2232ab.m506f();
        crashDetailBean.f1340E = C2232ab.m513l();
        crashDetailBean.f1341F = auVar2.f1673c.mo27977k();
        crashDetailBean.f1342G = auVar2.f1673c.mo27976j();
        crashDetailBean.f1343H = auVar2.f1673c.mo27978l();
        crashDetailBean.f1344I = C2232ab.m498b(auVar2.f1675e);
        crashDetailBean.f1345J = C2232ab.m508g();
        crashDetailBean.f1346K = C2232ab.m509h();
        crashDetailBean.f1364b = i3;
        crashDetailBean.f1367e = auVar2.f1673c.mo27972g();
        crashDetailBean.f1368f = auVar2.f1673c.f1483o;
        crashDetailBean.f1369g = auVar2.f1673c.mo27982q();
        crashDetailBean.f1375m = auVar2.f1673c.mo27970f();
        crashDetailBean.f1376n = String.valueOf(str);
        crashDetailBean.f1377o = String.valueOf(str2);
        String str10 = "";
        if (str8 != null) {
            String[] split = str8.split("\n");
            if (split.length > 0) {
                str10 = split[0];
            }
            str5 = str8;
        } else {
            str5 = str10;
        }
        crashDetailBean.f1378p = str10;
        crashDetailBean.f1379q = str5;
        crashDetailBean.f1380r = System.currentTimeMillis();
        crashDetailBean.f1383u = C2273ap.m671c(crashDetailBean.f1379q.getBytes());
        crashDetailBean.f1388z = C2273ap.m652a(auVar2.f1673c.f1436Q, C2293at.f1638h);
        crashDetailBean.f1336A = auVar2.f1673c.f1472d;
        crashDetailBean.f1337B = thread2.getName() + "(" + thread2.getId() + ")";
        crashDetailBean.f1347L = auVar2.f1673c.mo27984s();
        crashDetailBean.f1370h = auVar2.f1673c.mo27981p();
        crashDetailBean.f1352Q = auVar2.f1673c.f1445a;
        crashDetailBean.f1353R = auVar2.f1673c.mo27961a();
        crashDetailBean.f1356U = auVar2.f1673c.mo27991z();
        crashDetailBean.f1357V = auVar2.f1673c.f1492x;
        crashDetailBean.f1358W = auVar2.f1673c.mo27985t();
        crashDetailBean.f1359X = auVar2.f1673c.mo27990y();
        crashDetailBean.f1387y = C2269ao.m624a();
        if (crashDetailBean.f1354S == null) {
            crashDetailBean.f1354S = new LinkedHashMap();
        }
        if (map2 != null) {
            crashDetailBean.f1354S.putAll(map2);
        }
        String a2 = C2273ap.m640a();
        String str11 = auVar2.f1673c.f1472d;
        String name2 = thread2.getName();
        C2277as.m696a(str4, a2, str11, name2, str6 + "\n" + str7 + "\n" + str8, crashDetailBean);
        if (!auVar2.f1674d.mo28035a(crashDetailBean, !C2293at.m738a().f1649B)) {
            auVar2.f1674d.mo28037b(crashDetailBean, false);
        }
        C2265al.m611e("[ExtraCrashManager] Successfully handled.", new Object[0]);
    }
}
