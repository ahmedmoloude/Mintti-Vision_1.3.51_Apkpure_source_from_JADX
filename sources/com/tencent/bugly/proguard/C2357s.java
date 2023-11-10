package com.tencent.bugly.proguard;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.C2351r;

/* renamed from: com.tencent.bugly.proguard.s */
/* compiled from: BUGLY */
public class C2357s {

    /* renamed from: a */
    public static boolean f1930a = false;

    /* renamed from: b */
    public static C2351r f1931b = null;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public static int f1932c = 10;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public static long f1933d = 300000;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public static long f1934e = 30000;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public static long f1935f = 0;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public static int f1936g = 0;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public static long f1937h = 0;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public static long f1938i = 0;
    /* access modifiers changed from: private */

    /* renamed from: j */
    public static long f1939j = 0;

    /* renamed from: k */
    private static Application.ActivityLifecycleCallbacks f1940k = null;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public static Class<?> f1941l = null;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public static boolean f1942m = true;

    /* renamed from: g */
    static /* synthetic */ int m1007g() {
        int i = f1936g;
        f1936g = i + 1;
        return i;
    }

    /* JADX WARNING: type inference failed for: r12v11, types: [android.content.Context] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x005b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x005c  */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m1003c(android.content.Context r12, com.tencent.bugly.BuglyStrategy r13) {
        /*
            r0 = 1
            r1 = 0
            if (r13 == 0) goto L_0x000d
            boolean r2 = r13.recordUserInfoOnceADay()
            boolean r13 = r13.isEnableUserInfo()
            goto L_0x000f
        L_0x000d:
            r13 = 1
            r2 = 0
        L_0x000f:
            if (r2 == 0) goto L_0x005d
            com.tencent.bugly.proguard.aa r13 = com.tencent.bugly.proguard.C2231aa.m457a((android.content.Context) r12)
            java.lang.String r2 = r13.f1472d
            java.util.List r2 = com.tencent.bugly.proguard.C2351r.m973a((java.lang.String) r2)
            if (r2 == 0) goto L_0x0058
            r3 = 0
        L_0x001e:
            int r4 = r2.size()
            if (r3 >= r4) goto L_0x0058
            java.lang.Object r4 = r2.get(r3)
            com.tencent.bugly.crashreport.biz.UserInfoBean r4 = (com.tencent.bugly.crashreport.biz.UserInfoBean) r4
            java.lang.String r5 = r4.f1304n
            java.lang.String r6 = r13.f1483o
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0055
            int r5 = r4.f1292b
            if (r5 != r0) goto L_0x0055
            long r5 = com.tencent.bugly.proguard.C2273ap.m661b()
            r7 = 0
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 <= 0) goto L_0x0058
            long r9 = r4.f1295e
            int r11 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r11 < 0) goto L_0x0055
            long r2 = r4.f1296f
            int r13 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r13 > 0) goto L_0x0053
            com.tencent.bugly.proguard.r r13 = f1931b
            r13.mo28145b()
        L_0x0053:
            r13 = 0
            goto L_0x0059
        L_0x0055:
            int r3 = r3 + 1
            goto L_0x001e
        L_0x0058:
            r13 = 1
        L_0x0059:
            if (r13 != 0) goto L_0x005c
            return
        L_0x005c:
            r13 = 0
        L_0x005d:
            com.tencent.bugly.proguard.aa r2 = com.tencent.bugly.proguard.C2231aa.m459b()
            if (r2 == 0) goto L_0x006c
            boolean r3 = com.tencent.bugly.proguard.C2369z.m1061a()
            if (r3 == 0) goto L_0x006c
            r2.mo27958a((int) r1, (boolean) r0)
        L_0x006c:
            if (r13 == 0) goto L_0x00a1
            r13 = 0
            int r2 = android.os.Build.VERSION.SDK_INT
            r3 = 14
            if (r2 < r3) goto L_0x00a1
            android.content.Context r2 = r12.getApplicationContext()
            boolean r2 = r2 instanceof android.app.Application
            if (r2 == 0) goto L_0x0084
            android.content.Context r12 = r12.getApplicationContext()
            r13 = r12
            android.app.Application r13 = (android.app.Application) r13
        L_0x0084:
            if (r13 == 0) goto L_0x00a1
            android.app.Application$ActivityLifecycleCallbacks r12 = f1940k     // Catch:{ Exception -> 0x0097 }
            if (r12 != 0) goto L_0x0091
            com.tencent.bugly.proguard.s$a r12 = new com.tencent.bugly.proguard.s$a     // Catch:{ Exception -> 0x0097 }
            r12.<init>()     // Catch:{ Exception -> 0x0097 }
            f1940k = r12     // Catch:{ Exception -> 0x0097 }
        L_0x0091:
            android.app.Application$ActivityLifecycleCallbacks r12 = f1940k     // Catch:{ Exception -> 0x0097 }
            r13.registerActivityLifecycleCallbacks(r12)     // Catch:{ Exception -> 0x0097 }
            goto L_0x00a1
        L_0x0097:
            r12 = move-exception
            boolean r13 = com.tencent.bugly.proguard.C2265al.m605a(r12)
            if (r13 != 0) goto L_0x00a1
            r12.printStackTrace()
        L_0x00a1:
            boolean r12 = f1942m
            if (r12 == 0) goto L_0x00c4
            long r12 = java.lang.System.currentTimeMillis()
            f1938i = r12
            com.tencent.bugly.proguard.r r12 = f1931b
            r12.mo28143a((int) r0, (boolean) r1)
            java.lang.Object[] r12 = new java.lang.Object[r1]
            java.lang.String r13 = "[session] launch app, new start"
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r13, (java.lang.Object[]) r12)
            com.tencent.bugly.proguard.r r12 = f1931b
            r12.mo28142a()
            com.tencent.bugly.proguard.r r12 = f1931b
            r0 = 21600000(0x1499700, double:1.0671818E-316)
            r12.mo28144a((long) r0)
        L_0x00c4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2357s.m1003c(android.content.Context, com.tencent.bugly.BuglyStrategy):void");
    }

    /* renamed from: a */
    public static void m996a(final Context context, final BuglyStrategy buglyStrategy) {
        long j;
        if (!f1930a) {
            f1942m = C2231aa.m457a(context).f1474f;
            f1931b = new C2351r(context, f1942m);
            f1930a = true;
            if (buglyStrategy != null) {
                f1941l = buglyStrategy.getUserInfoActivity();
                j = buglyStrategy.getAppReportDelay();
            } else {
                j = 0;
            }
            if (j <= 0) {
                m1003c(context, buglyStrategy);
            } else {
                C2263ak.m595a().mo28018a(new Runnable() {
                    public final void run() {
                        C2357s.m1003c(context, buglyStrategy);
                    }
                }, j);
            }
        }
    }

    /* renamed from: a */
    public static void m994a(long j) {
        if (j < 0) {
            j = C2248ac.m533a().mo27996c().f1328p;
        }
        f1935f = j;
    }

    /* renamed from: a */
    public static void m997a(StrategyBean strategyBean, boolean z) {
        C2351r rVar = f1931b;
        if (rVar != null && !z) {
            rVar.mo28145b();
        }
        if (strategyBean != null) {
            if (strategyBean.f1328p > 0) {
                f1934e = strategyBean.f1328p;
            }
            if (strategyBean.f1333u > 0) {
                f1932c = strategyBean.f1333u;
            }
            if (strategyBean.f1334v > 0) {
                f1933d = strategyBean.f1334v;
            }
        }
    }

    /* renamed from: a */
    public static void m993a() {
        C2351r rVar = f1931b;
        if (rVar != null) {
            rVar.mo28143a(2, false);
        }
    }

    /* JADX WARNING: type inference failed for: r3v4, types: [android.content.Context] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m995a(android.content.Context r3) {
        /*
            boolean r0 = f1930a
            if (r0 == 0) goto L_0x0034
            if (r3 != 0) goto L_0x0007
            goto L_0x0034
        L_0x0007:
            r0 = 0
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 14
            if (r1 < r2) goto L_0x0031
            android.content.Context r1 = r3.getApplicationContext()
            boolean r1 = r1 instanceof android.app.Application
            if (r1 == 0) goto L_0x001d
            android.content.Context r3 = r3.getApplicationContext()
            r0 = r3
            android.app.Application r0 = (android.app.Application) r0
        L_0x001d:
            if (r0 == 0) goto L_0x0031
            android.app.Application$ActivityLifecycleCallbacks r3 = f1940k     // Catch:{ Exception -> 0x0027 }
            if (r3 == 0) goto L_0x0031
            r0.unregisterActivityLifecycleCallbacks(r3)     // Catch:{ Exception -> 0x0027 }
            goto L_0x0031
        L_0x0027:
            r3 = move-exception
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r3)
            if (r0 != 0) goto L_0x0031
            r3.printStackTrace()
        L_0x0031:
            r3 = 0
            f1930a = r3
        L_0x0034:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2357s.m995a(android.content.Context):void");
    }

    /* renamed from: com.tencent.bugly.proguard.s$a */
    /* compiled from: BUGLY */
    static class C2359a implements Application.ActivityLifecycleCallbacks {
        public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        C2359a() {
        }

        public final void onActivityStopped(Activity activity) {
            C2265al.m609c(">>> %s onStop <<<", activity.getClass().getName());
            C2231aa.m459b().mo27958a(activity.hashCode(), false);
        }

        public final void onActivityStarted(Activity activity) {
            C2265al.m609c(">>> %s onStart <<<", activity.getClass().getName());
            C2231aa.m459b().mo27958a(activity.hashCode(), true);
        }

        public final void onActivityResumed(Activity activity) {
            String name = activity.getClass().getName();
            if (C2357s.f1941l == null || C2357s.f1941l.getName().equals(name)) {
                C2265al.m609c(">>> %s onResumed <<<", name);
                C2231aa b = C2231aa.m459b();
                if (b != null) {
                    b.f1431L.add(C2357s.m992a(name, "onResumed"));
                    b.f1493y = name;
                    b.f1494z = System.currentTimeMillis();
                    b.f1422C = b.f1494z - C2357s.f1938i;
                    long d = b.f1494z - C2357s.f1937h;
                    if (d > (C2357s.f1935f > 0 ? C2357s.f1935f : C2357s.f1934e)) {
                        b.mo27964c();
                        C2357s.m1007g();
                        C2265al.m604a("[session] launch app one times (app in background %d seconds and over %d seconds)", Long.valueOf(d / 1000), Long.valueOf(C2357s.f1934e / 1000));
                        if (C2357s.f1936g % C2357s.f1932c == 0) {
                            C2357s.f1931b.mo28143a(4, C2357s.f1942m);
                            return;
                        }
                        C2357s.f1931b.mo28143a(4, false);
                        long currentTimeMillis = System.currentTimeMillis();
                        if (currentTimeMillis - C2357s.f1939j > C2357s.f1933d) {
                            long unused = C2357s.f1939j = currentTimeMillis;
                            C2265al.m604a("add a timer to upload hot start user info", new Object[0]);
                            if (C2357s.f1942m) {
                                C2263ak.m595a().mo28018a(new C2351r.C2354a((UserInfoBean) null, true), C2357s.f1933d);
                            }
                        }
                    }
                }
            }
        }

        public final void onActivityPaused(Activity activity) {
            String name = activity.getClass().getName();
            if (C2357s.f1941l == null || C2357s.f1941l.getName().equals(name)) {
                C2265al.m609c(">>> %s onPaused <<<", name);
                C2231aa b = C2231aa.m459b();
                if (b != null) {
                    b.f1431L.add(C2357s.m992a(name, "onPaused"));
                    b.f1420A = System.currentTimeMillis();
                    b.f1421B = b.f1420A - b.f1494z;
                    long unused = C2357s.f1937h = b.f1420A;
                    if (b.f1421B < 0) {
                        b.f1421B = 0;
                    }
                    b.f1493y = "background";
                }
            }
        }

        public final void onActivityDestroyed(Activity activity) {
            String name = activity.getClass().getName();
            if (C2357s.f1941l == null || C2357s.f1941l.getName().equals(name)) {
                C2265al.m609c(">>> %s onDestroyed <<<", name);
                C2231aa b = C2231aa.m459b();
                if (b != null) {
                    b.f1431L.add(C2357s.m992a(name, "onDestroyed"));
                }
            }
        }

        public final void onActivityCreated(Activity activity, Bundle bundle) {
            String name = activity.getClass().getName();
            if (C2357s.f1941l == null || C2357s.f1941l.getName().equals(name)) {
                C2265al.m609c(">>> %s onCreated <<<", name);
                C2231aa b = C2231aa.m459b();
                if (b != null) {
                    b.f1431L.add(C2357s.m992a(name, "onCreated"));
                }
            }
        }
    }

    /* renamed from: a */
    static /* synthetic */ String m992a(String str, String str2) {
        return C2273ap.m640a() + "  " + str + "  " + str2 + "\n";
    }
}
