package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;
import com.linktop.constant.TestPaper;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.proguard.C2253ag;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* renamed from: com.tencent.bugly.proguard.as */
/* compiled from: BUGLY */
public final class C2277as {

    /* renamed from: a */
    public static int f1614a;

    /* renamed from: h */
    private static final Map<Integer, Pair<String, String>> f1615h = new HashMap<Integer, Pair<String, String>>() {
        {
            put(3, new Pair("203", "103"));
            put(7, new Pair("208", "108"));
            put(0, new Pair("200", "100"));
            put(1, new Pair("201", "101"));
            put(2, new Pair("202", "102"));
            put(4, new Pair("204", "104"));
            put(6, new Pair("206", "106"));
            put(5, new Pair("207", "107"));
        }
    };

    /* renamed from: i */
    private static final ArrayList<C2284a> f1616i = new ArrayList<C2284a>() {
        {
            add(new C2285b((byte) 0));
            add(new C2286c((byte) 0));
            add(new C2287d((byte) 0));
            add(new C2288e((byte) 0));
            add(new C2291h((byte) 0));
            add(new C2292i((byte) 0));
            add(new C2289f((byte) 0));
            add(new C2290g((byte) 0));
        }
    };

    /* renamed from: j */
    private static final Map<Integer, Integer> f1617j = new HashMap<Integer, Integer>() {
        {
            put(3, 4);
            put(7, 7);
            put(2, 1);
            put(0, 0);
            put(1, 2);
            put(4, 3);
            put(5, 5);
            put(6, 6);
        }
    };

    /* renamed from: k */
    private static final Map<Integer, String> f1618k = new HashMap<Integer, String>() {
        {
            put(3, "BuglyAnrCrash");
            put(0, "BuglyJavaCrash");
            put(1, "BuglyNativeCrash");
        }
    };

    /* renamed from: l */
    private static final Map<Integer, String> f1619l = new HashMap<Integer, String>() {
        {
            put(3, "BuglyAnrCrashReport");
            put(0, "BuglyJavaCrashReport");
            put(1, "BuglyNativeCrashReport");
        }
    };

    /* renamed from: b */
    protected final Context f1620b;

    /* renamed from: c */
    protected final C2259ai f1621c;

    /* renamed from: d */
    protected final C2365w f1622d;

    /* renamed from: e */
    protected final C2248ac f1623e;

    /* renamed from: f */
    protected C2302aw f1624f = null;

    /* renamed from: g */
    protected BuglyStrategy.C2217a f1625g;

    public C2277as(Context context, C2259ai aiVar, C2365w wVar, C2248ac acVar, BuglyStrategy.C2217a aVar) {
        f1614a = 1004;
        this.f1620b = context;
        this.f1621c = aiVar;
        this.f1622d = wVar;
        this.f1623e = acVar;
        this.f1625g = aVar;
    }

    /* renamed from: a */
    private static List<C2276ar> m692a(List<C2276ar> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        for (C2276ar next : list) {
            if (next.f1611d && next.f1609b <= currentTimeMillis - 86400000) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    private static CrashDetailBean m687a(List<C2276ar> list, CrashDetailBean crashDetailBean) {
        List<CrashDetailBean> c;
        if (list.isEmpty()) {
            return crashDetailBean;
        }
        CrashDetailBean crashDetailBean2 = null;
        ArrayList arrayList = new ArrayList(10);
        for (C2276ar next : list) {
            if (next.f1612e) {
                arrayList.add(next);
            }
        }
        if (!arrayList.isEmpty() && (c = m717c((List<C2276ar>) arrayList)) != null && !c.isEmpty()) {
            Collections.sort(c);
            crashDetailBean2 = c.get(0);
            m694a(crashDetailBean2, c);
        }
        if (crashDetailBean2 == null) {
            crashDetailBean.f1372j = true;
            crashDetailBean.f1382t = 0;
            crashDetailBean.f1381s = "";
            crashDetailBean2 = crashDetailBean;
        }
        m710b(crashDetailBean2, list);
        if (crashDetailBean2.f1380r != crashDetailBean.f1380r) {
            String str = crashDetailBean2.f1381s;
            StringBuilder sb = new StringBuilder();
            sb.append(crashDetailBean.f1380r);
            if (!str.contains(sb.toString())) {
                crashDetailBean2.f1382t++;
                crashDetailBean2.f1381s += crashDetailBean.f1380r + "\n";
            }
        }
        return crashDetailBean2;
    }

    /* renamed from: a */
    private static void m694a(CrashDetailBean crashDetailBean, List<CrashDetailBean> list) {
        String[] split;
        StringBuilder sb = new StringBuilder(128);
        for (int i = 1; i < list.size(); i++) {
            CrashDetailBean crashDetailBean2 = list.get(i);
            if (!(crashDetailBean2.f1381s == null || (split = crashDetailBean2.f1381s.split("\n")) == null)) {
                for (String str : split) {
                    if (!crashDetailBean.f1381s.contains(str)) {
                        crashDetailBean.f1382t++;
                        sb.append(str);
                        sb.append("\n");
                    }
                }
            }
        }
        crashDetailBean.f1381s += sb.toString();
    }

    /* renamed from: b */
    private static void m710b(CrashDetailBean crashDetailBean, List<C2276ar> list) {
        StringBuilder sb = new StringBuilder(64);
        for (C2276ar next : list) {
            if (!next.f1612e && !next.f1611d) {
                String str = crashDetailBean.f1381s;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(next.f1609b);
                if (!str.contains(sb2.toString())) {
                    crashDetailBean.f1382t++;
                    sb.append(next.f1609b);
                    sb.append("\n");
                }
            }
        }
        crashDetailBean.f1381s += sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00d2 A[Catch:{ all -> 0x014d }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00e0 A[Catch:{ all -> 0x014d }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f9 A[Catch:{ all -> 0x014d }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0101 A[Catch:{ all -> 0x014d }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0136 A[Catch:{ all -> 0x014d }] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x01ae A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01af  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean mo28035a(com.tencent.bugly.crashreport.crash.CrashDetailBean r20, boolean r21) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            java.lang.String r3 = "t_cr"
            r4 = 1
            r5 = 0
            if (r2 != 0) goto L_0x0012
            java.lang.Object[] r0 = new java.lang.Object[r5]
            java.lang.String r2 = "CrashBean is null, won't handle."
            com.tencent.bugly.proguard.C2265al.m610d(r2, r0)
            return r4
        L_0x0012:
            r19.mo28036b((com.tencent.bugly.crashreport.crash.CrashDetailBean) r20)
            r6 = 2
            if (r21 == 0) goto L_0x0168
            if (r2 == 0) goto L_0x0168
            com.tencent.bugly.BuglyStrategy$a r0 = r1.f1625g
            if (r0 != 0) goto L_0x0022
            com.tencent.bugly.proguard.aw r0 = r1.f1624f
            if (r0 == 0) goto L_0x0168
        L_0x0022:
            java.util.ArrayList<com.tencent.bugly.proguard.as$a> r0 = f1616i
            java.util.Iterator r0 = r0.iterator()
        L_0x0028:
            boolean r7 = r0.hasNext()
            if (r7 == 0) goto L_0x0044
            java.lang.Object r7 = r0.next()
            com.tencent.bugly.proguard.as$a r7 = (com.tencent.bugly.proguard.C2277as.C2284a) r7
            int r8 = r7.f1630a
            int r9 = r2.f1364b
            if (r8 != r9) goto L_0x003c
            r8 = 1
            goto L_0x003d
        L_0x003c:
            r8 = 0
        L_0x003d:
            if (r8 == 0) goto L_0x0028
            boolean r0 = r7.mo28038a()
            goto L_0x0045
        L_0x0044:
            r0 = 0
        L_0x0045:
            if (r0 != 0) goto L_0x0050
            java.lang.Object[] r0 = new java.lang.Object[r5]
            java.lang.String r7 = "Should not call back."
            com.tencent.bugly.proguard.C2265al.m609c(r7, r0)
            goto L_0x0168
        L_0x0050:
            java.util.Map<java.lang.Integer, java.lang.Integer> r0 = f1617j     // Catch:{ all -> 0x014d }
            int r7 = r2.f1364b     // Catch:{ all -> 0x014d }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x014d }
            boolean r7 = r0.containsKey(r7)     // Catch:{ all -> 0x014d }
            if (r7 != 0) goto L_0x0075
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x014d }
            java.lang.String r7 = "Cannot get crash type for crashBean type:"
            r0.<init>(r7)     // Catch:{ all -> 0x014d }
            int r7 = r2.f1364b     // Catch:{ all -> 0x014d }
            r0.append(r7)     // Catch:{ all -> 0x014d }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x014d }
            java.lang.Object[] r7 = new java.lang.Object[r5]     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.C2265al.m610d(r0, r7)     // Catch:{ all -> 0x014d }
            goto L_0x0168
        L_0x0075:
            int r7 = r2.f1364b     // Catch:{ all -> 0x014d }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x014d }
            java.lang.Object r0 = r0.get(r7)     // Catch:{ all -> 0x014d }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ all -> 0x014d }
            int r0 = r0.intValue()     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.aw r7 = r1.f1624f     // Catch:{ all -> 0x014d }
            r8 = 0
            if (r7 == 0) goto L_0x00ab
            java.lang.String r7 = "Calling 'onCrashHandleStart' of RQD crash listener."
            java.lang.Object[] r9 = new java.lang.Object[r5]     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.C2265al.m609c(r7, r9)     // Catch:{ all -> 0x014d }
            java.lang.String r7 = "Calling 'getCrashExtraMessage' of RQD crash listener."
            java.lang.Object[] r9 = new java.lang.Object[r5]     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.C2265al.m609c(r7, r9)     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.aw r7 = r1.f1624f     // Catch:{ all -> 0x014d }
            java.lang.String r7 = r7.mo28064b()     // Catch:{ all -> 0x014d }
            if (r7 == 0) goto L_0x00c3
            java.util.HashMap r9 = new java.util.HashMap     // Catch:{ all -> 0x014d }
            r9.<init>(r4)     // Catch:{ all -> 0x014d }
            java.lang.String r10 = "userData"
            r9.put(r10, r7)     // Catch:{ all -> 0x014d }
            goto L_0x00c4
        L_0x00ab:
            com.tencent.bugly.BuglyStrategy$a r7 = r1.f1625g     // Catch:{ all -> 0x014d }
            if (r7 == 0) goto L_0x00c3
            java.lang.String r7 = "Calling 'onCrashHandleStart' of Bugly crash listener."
            java.lang.Object[] r9 = new java.lang.Object[r5]     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.C2265al.m609c(r7, r9)     // Catch:{ all -> 0x014d }
            com.tencent.bugly.BuglyStrategy$a r7 = r1.f1625g     // Catch:{ all -> 0x014d }
            java.lang.String r9 = r2.f1376n     // Catch:{ all -> 0x014d }
            java.lang.String r10 = r2.f1377o     // Catch:{ all -> 0x014d }
            java.lang.String r11 = r2.f1379q     // Catch:{ all -> 0x014d }
            java.util.Map r9 = r7.onCrashHandleStart(r0, r9, r10, r11)     // Catch:{ all -> 0x014d }
            goto L_0x00c4
        L_0x00c3:
            r9 = r8
        L_0x00c4:
            m695a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r2, (java.util.Map<java.lang.String, java.lang.String>) r9)     // Catch:{ all -> 0x014d }
            java.lang.String r7 = "[crash callback] start user's callback:onCrashHandleStart2GetExtraDatas()"
            java.lang.Object[] r9 = new java.lang.Object[r5]     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r7, (java.lang.Object[]) r9)     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.aw r7 = r1.f1624f     // Catch:{ all -> 0x014d }
            if (r7 == 0) goto L_0x00e0
            java.lang.String r0 = "Calling 'getCrashExtraData' of RQD crash listener."
            java.lang.Object[] r7 = new java.lang.Object[r5]     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.C2265al.m609c(r0, r7)     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.aw r0 = r1.f1624f     // Catch:{ all -> 0x014d }
            byte[] r8 = r0.mo28063a()     // Catch:{ all -> 0x014d }
            goto L_0x00f7
        L_0x00e0:
            com.tencent.bugly.BuglyStrategy$a r7 = r1.f1625g     // Catch:{ all -> 0x014d }
            if (r7 == 0) goto L_0x00f7
            java.lang.String r7 = "Calling 'onCrashHandleStart2GetExtraDatas' of Bugly crash listener."
            java.lang.Object[] r8 = new java.lang.Object[r5]     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.C2265al.m609c(r7, r8)     // Catch:{ all -> 0x014d }
            com.tencent.bugly.BuglyStrategy$a r7 = r1.f1625g     // Catch:{ all -> 0x014d }
            java.lang.String r8 = r2.f1376n     // Catch:{ all -> 0x014d }
            java.lang.String r9 = r2.f1377o     // Catch:{ all -> 0x014d }
            java.lang.String r10 = r2.f1379q     // Catch:{ all -> 0x014d }
            byte[] r8 = r7.onCrashHandleStart2GetExtraDatas(r0, r8, r9, r10)     // Catch:{ all -> 0x014d }
        L_0x00f7:
            if (r8 != 0) goto L_0x0101
            java.lang.String r0 = "extra user byte is null. CrashBean won't have userExtraByteDatas."
            java.lang.Object[] r7 = new java.lang.Object[r5]     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.C2265al.m610d(r0, r7)     // Catch:{ all -> 0x014d }
            goto L_0x0132
        L_0x0101:
            int r0 = r8.length     // Catch:{ all -> 0x014d }
            r7 = 100000(0x186a0, float:1.4013E-40)
            if (r0 > r7) goto L_0x010a
            r2.f1360Y = r8     // Catch:{ all -> 0x014d }
            goto L_0x0124
        L_0x010a:
            java.lang.String r0 = "extra bytes size %d is over limit %d will drop over part"
            java.lang.Object[] r9 = new java.lang.Object[r6]     // Catch:{ all -> 0x014d }
            int r10 = r8.length     // Catch:{ all -> 0x014d }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x014d }
            r9[r5] = r10     // Catch:{ all -> 0x014d }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x014d }
            r9[r4] = r10     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.C2265al.m610d(r0, r9)     // Catch:{ all -> 0x014d }
            byte[] r0 = java.util.Arrays.copyOf(r8, r7)     // Catch:{ all -> 0x014d }
            r2.f1360Y = r0     // Catch:{ all -> 0x014d }
        L_0x0124:
            java.lang.String r0 = "add extra bytes %d "
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x014d }
            int r8 = r8.length     // Catch:{ all -> 0x014d }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x014d }
            r7[r5] = r8     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r0, (java.lang.Object[]) r7)     // Catch:{ all -> 0x014d }
        L_0x0132:
            com.tencent.bugly.proguard.aw r0 = r1.f1624f     // Catch:{ all -> 0x014d }
            if (r0 == 0) goto L_0x0168
            java.lang.String r0 = "Calling 'onCrashSaving' of RQD crash listener."
            java.lang.Object[] r7 = new java.lang.Object[r5]     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.C2265al.m609c(r0, r7)     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.aw r0 = r1.f1624f     // Catch:{ all -> 0x014d }
            boolean r0 = r0.mo28065c()     // Catch:{ all -> 0x014d }
            if (r0 != 0) goto L_0x0168
            java.lang.String r0 = "Crash listener 'onCrashSaving' return 'false' thus will not handle this crash."
            java.lang.Object[] r7 = new java.lang.Object[r5]     // Catch:{ all -> 0x014d }
            com.tencent.bugly.proguard.C2265al.m610d(r0, r7)     // Catch:{ all -> 0x014d }
            goto L_0x0168
        L_0x014d:
            r0 = move-exception
            java.lang.Object[] r7 = new java.lang.Object[r4]
            java.lang.Class r8 = r0.getClass()
            java.lang.String r8 = r8.getName()
            r7[r5] = r8
            java.lang.String r8 = "crash handle callback something wrong! %s"
            com.tencent.bugly.proguard.C2265al.m610d(r8, r7)
            boolean r7 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r7 != 0) goto L_0x0168
            r0.printStackTrace()
        L_0x0168:
            boolean r0 = com.tencent.bugly.proguard.C2232ab.m516o()
            if (r0 != 0) goto L_0x0178
            int r0 = com.tencent.bugly.proguard.C2293at.f1636f
            java.lang.String r7 = com.tencent.bugly.proguard.C2293at.f1640j
            java.lang.String r0 = com.tencent.bugly.proguard.C2273ap.m641a((int) r0, (java.lang.String) r7)
            r2.f1385w = r0
        L_0x0178:
            java.lang.String r0 = r2.f1379q
            java.lang.String r7 = com.tencent.bugly.proguard.C2293at.f1646p
            if (r7 == 0) goto L_0x01a2
            java.lang.String r7 = com.tencent.bugly.proguard.C2293at.f1646p
            boolean r7 = r7.isEmpty()
            if (r7 != 0) goto L_0x01a2
            java.lang.Object[] r7 = new java.lang.Object[r4]
            java.lang.String r8 = com.tencent.bugly.proguard.C2293at.f1646p
            r7[r5] = r8
            java.lang.String r8 = "Crash filter for crash stack is: %s"
            com.tencent.bugly.proguard.C2265al.m609c(r8, r7)
            java.lang.String r7 = com.tencent.bugly.proguard.C2293at.f1646p
            boolean r0 = r0.contains(r7)
            if (r0 == 0) goto L_0x01a2
            java.lang.Object[] r0 = new java.lang.Object[r5]
            java.lang.String r7 = "This crash contains the filter string set. It will not be record and upload."
            com.tencent.bugly.proguard.C2265al.m610d(r7, r0)
            r0 = 1
            goto L_0x01a3
        L_0x01a2:
            r0 = 0
        L_0x01a3:
            if (r0 == 0) goto L_0x01a6
            return r4
        L_0x01a6:
            java.lang.String r0 = r2.f1379q
            boolean r0 = m707a((java.lang.String) r0)
            if (r0 == 0) goto L_0x01af
            return r4
        L_0x01af:
            int r0 = r2.f1364b
            if (r0 == r6) goto L_0x01dc
            com.tencent.bugly.proguard.y r0 = new com.tencent.bugly.proguard.y
            r0.<init>()
            r0.f1989b = r4
            java.lang.String r7 = r2.f1336A
            r0.f1990c = r7
            java.lang.String r7 = r2.f1337B
            r0.f1991d = r7
            long r7 = r2.f1380r
            r0.f1992e = r7
            com.tencent.bugly.proguard.w r7 = com.tencent.bugly.proguard.C2365w.m1033a()
            r7.mo28172b((int) r4)
            com.tencent.bugly.proguard.w r7 = com.tencent.bugly.proguard.C2365w.m1033a()
            r7.mo28171a((com.tencent.bugly.proguard.C2368y) r0)
            java.lang.Object[] r0 = new java.lang.Object[r5]
            java.lang.String r7 = "[crash] a crash occur, handling..."
            com.tencent.bugly.proguard.C2265al.m607b(r7, r0)
            goto L_0x01e3
        L_0x01dc:
            java.lang.Object[] r0 = new java.lang.Object[r5]
            java.lang.String r7 = "[crash] a caught exception occur, handling..."
            com.tencent.bugly.proguard.C2265al.m607b(r7, r0)
        L_0x01e3:
            java.util.List r7 = m709b()
            java.util.ArrayList r8 = new java.util.ArrayList
            r0 = 10
            r8.<init>(r0)
            if (r7 == 0) goto L_0x024f
            int r0 = r7.size()
            if (r0 <= 0) goto L_0x024f
            java.util.List r0 = m692a((java.util.List<com.tencent.bugly.proguard.C2276ar>) r7)
            r8.addAll(r0)
            r7.removeAll(r8)
            int r0 = r7.size()
            long r9 = (long) r0
            r11 = 20
            int r0 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r0 <= 0) goto L_0x0248
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r9 = "_id in ("
            r0.append(r9)
            java.lang.String r9 = "SELECT _id FROM t_cr order by _id limit 5"
            r0.append(r9)
            java.lang.String r9 = ")"
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            r0.setLength(r5)
            com.tencent.bugly.proguard.w r0 = com.tencent.bugly.proguard.C2365w.m1033a()     // Catch:{ all -> 0x023e }
            int r0 = r0.mo28163a((java.lang.String) r3, (java.lang.String) r9)     // Catch:{ all -> 0x023e }
            java.lang.String r9 = "deleted first record %s data %d"
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x023e }
            r6[r5] = r3     // Catch:{ all -> 0x023e }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x023e }
            r6[r4] = r0     // Catch:{ all -> 0x023e }
            com.tencent.bugly.proguard.C2265al.m609c(r9, r6)     // Catch:{ all -> 0x023e }
            goto L_0x0248
        L_0x023e:
            r0 = move-exception
            boolean r3 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r3 != 0) goto L_0x0248
            r0.printStackTrace()
        L_0x0248:
            boolean r0 = r1.m715b((com.tencent.bugly.crashreport.crash.CrashDetailBean) r2, (java.util.List<com.tencent.bugly.proguard.C2276ar>) r7, (java.util.List<com.tencent.bugly.proguard.C2276ar>) r8)
            if (r0 == 0) goto L_0x024f
            return r4
        L_0x024f:
            r19.mo28036b((com.tencent.bugly.crashreport.crash.CrashDetailBean) r20)
            java.util.Map<java.lang.Integer, java.lang.String> r0 = f1618k
            int r3 = r2.f1364b
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.Object r0 = r0.get(r3)
            r11 = r0
            java.lang.String r11 = (java.lang.String) r11
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 != 0) goto L_0x027f
            com.tencent.bugly.proguard.ag r0 = com.tencent.bugly.proguard.C2253ag.C2255a.f1515a
            com.tencent.bugly.proguard.ag$c r3 = new com.tencent.bugly.proguard.ag$c
            java.lang.String r10 = r2.f1365c
            long r12 = r2.f1380r
            r14 = 1
            r15 = 0
            r18 = 0
            java.lang.String r17 = "realtime"
            r9 = r3
            r9.<init>(r10, r11, r12, r14, r15, r17, r18)
            r0.mo27999a((com.tencent.bugly.proguard.C2253ag.C2257c) r3)
        L_0x027f:
            m719d((java.util.List<com.tencent.bugly.proguard.C2276ar>) r8)
            java.lang.Object[] r0 = new java.lang.Object[r5]
            java.lang.String r2 = "[crash] save crash success"
            com.tencent.bugly.proguard.C2265al.m607b(r2, r0)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2277as.mo28035a(com.tencent.bugly.crashreport.crash.CrashDetailBean, boolean):boolean");
    }

    /* renamed from: a */
    private static boolean m707a(String str) {
        if (C2293at.f1647q != null && !C2293at.f1647q.isEmpty()) {
            try {
                C2265al.m609c("Crash regular filter for crash stack is: %s", C2293at.f1647q);
                if (Pattern.compile(C2293at.f1647q).matcher(str).find()) {
                    C2265al.m610d("This crash matches the regular filter string set. It will not be record and upload.", new Object[0]);
                    return true;
                }
            } catch (Exception e) {
                C2265al.m605a(e);
                C2265al.m610d("Failed to compile " + C2293at.f1647q, new Object[0]);
            }
        }
        return false;
    }

    /* renamed from: a */
    private static boolean m706a(CrashDetailBean crashDetailBean, List<C2276ar> list, List<C2276ar> list2) {
        boolean z = false;
        for (C2276ar next : list) {
            if (crashDetailBean.f1383u.equals(next.f1610c)) {
                if (next.f1612e) {
                    z = true;
                }
                list2.add(next);
            }
        }
        return z;
    }

    /* renamed from: a */
    public static List<CrashDetailBean> m691a() {
        StrategyBean c = C2248ac.m533a().mo27996c();
        if (c == null) {
            C2265al.m610d("have not synced remote!", new Object[0]);
            return null;
        } else if (!c.f1318f) {
            C2265al.m610d("Crashreport remote closed, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            C2265al.m607b("[init] WARNING! Crashreport closed by server, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            return null;
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            long b = C2273ap.m661b();
            List<C2276ar> b2 = m709b();
            C2265al.m609c("Size of crash list loaded from DB: %s", Integer.valueOf(b2.size()));
            if (b2 == null || b2.size() <= 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            arrayList.addAll(m692a(b2));
            b2.removeAll(arrayList);
            Iterator<C2276ar> it = b2.iterator();
            while (it.hasNext()) {
                C2276ar next = it.next();
                if (next.f1609b < b - C2293at.f1639i) {
                    arrayList2.add(next);
                    it.remove();
                    arrayList.add(next);
                } else if (next.f1611d) {
                    if (next.f1609b >= currentTimeMillis - 86400000) {
                        it.remove();
                    } else if (!next.f1612e) {
                        it.remove();
                        arrayList.add(next);
                    }
                } else if (((long) next.f1613f) >= 3 && next.f1609b < currentTimeMillis - 86400000) {
                    it.remove();
                    arrayList.add(next);
                }
            }
            m714b((List<C2276ar>) arrayList2);
            if (arrayList.size() > 0) {
                m719d((List<C2276ar>) arrayList);
            }
            ArrayList arrayList3 = new ArrayList();
            List<CrashDetailBean> c2 = m717c(b2);
            if (c2 != null && c2.size() > 0) {
                String str = C2231aa.m459b().f1483o;
                Iterator<CrashDetailBean> it2 = c2.iterator();
                while (it2.hasNext()) {
                    CrashDetailBean next2 = it2.next();
                    if (!str.equals(next2.f1368f)) {
                        it2.remove();
                        arrayList3.add(next2);
                    }
                }
            }
            if (arrayList3.size() > 0) {
                m722e((List<CrashDetailBean>) arrayList3);
            }
            return c2;
        }
    }

    /* renamed from: b */
    private static void m714b(List<C2276ar> list) {
        List<CrashDetailBean> c = m717c(list);
        if (c != null && !c.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            for (CrashDetailBean next : c) {
                String str = f1619l.get(Integer.valueOf(next.f1364b));
                if (!TextUtils.isEmpty(str)) {
                    C2265al.m609c("find expired data,crashId:%s eventType:%s", next.f1365c, str);
                    arrayList.add(new C2253ag.C2257c(next.f1365c, str, next.f1380r, false, 0, "expired", (String) null));
                }
            }
            C2253ag.C2255a.f1515a.mo28000a((List<C2253ag.C2257c>) arrayList);
        }
    }

    /* renamed from: a */
    public final void mo28033a(CrashDetailBean crashDetailBean) {
        int i = crashDetailBean.f1364b;
        if (i != 0) {
            if (i != 1) {
                if (i == 3 && !C2293at.m738a().mo28051k()) {
                    return;
                }
            } else if (!C2293at.m738a().mo28050j()) {
                return;
            }
        } else if (!C2293at.m738a().mo28050j()) {
            return;
        }
        if (this.f1624f != null) {
            C2265al.m609c("Calling 'onCrashHandleEnd' of RQD crash listener.", new Object[0]);
        }
    }

    /* renamed from: b */
    public final void mo28037b(CrashDetailBean crashDetailBean, boolean z) {
        if (C2293at.f1644n) {
            C2265al.m604a("try to upload right now", new Object[0]);
            ArrayList arrayList = new ArrayList();
            arrayList.add(crashDetailBean);
            mo28034a((List<CrashDetailBean>) arrayList, 3000, z, crashDetailBean.f1364b == 7, z);
            return;
        }
        C2265al.m604a("do not upload spot crash right now, crash would be uploaded when app next start", new Object[0]);
    }

    /* renamed from: a */
    public final void mo28034a(List<CrashDetailBean> list, long j, boolean z, boolean z2, boolean z3) {
        List<CrashDetailBean> list2 = list;
        if (!C2231aa.m457a(this.f1620b).f1474f) {
            C2265al.m610d("warn: not upload process", new Object[0]);
            return;
        }
        C2259ai aiVar = this.f1621c;
        if (aiVar == null) {
            C2265al.m610d("warn: upload manager is null", new Object[0]);
        } else if (z3 || aiVar.mo28011b(C2293at.f1632a)) {
            StrategyBean c = this.f1623e.mo27996c();
            if (!c.f1318f) {
                C2265al.m610d("remote report is disable!", new Object[0]);
                C2265al.m607b("[crash] server closed bugly in this app. please check your appid if is correct, and re-install it", new Object[0]);
            } else if (list2 == null || list.size() == 0) {
                C2265al.m610d("warn: crashList is null or crashList num is 0", new Object[0]);
            } else {
                try {
                    String str = c.f1330r;
                    String str2 = StrategyBean.f1314b;
                    C2328bp a = m690a(this.f1620b, list2, C2231aa.m459b());
                    if (a == null) {
                        C2265al.m610d("create eupPkg fail!", new Object[0]);
                        return;
                    }
                    byte[] a2 = C2251ae.m551a((C2346m) a);
                    if (a2 == null) {
                        C2265al.m610d("send encode fail!", new Object[0]);
                        return;
                    }
                    C2329bq a3 = C2251ae.m547a(this.f1620b, 830, a2);
                    if (a3 == null) {
                        C2265al.m610d("request package is null.", new Object[0]);
                        return;
                    }
                    final long currentTimeMillis = System.currentTimeMillis();
                    final List<CrashDetailBean> list3 = list;
                    final boolean z4 = z;
                    C22836 r1 = new C2258ah() {
                        /* renamed from: a */
                        public final void mo28004a(boolean z, String str) {
                            C2277as.m703a(list3, z, System.currentTimeMillis() - currentTimeMillis, z4 ? "realtime" : "cache", str);
                            C2277as.m705a(z, (List<CrashDetailBean>) list3);
                        }
                    };
                    if (z) {
                        this.f1621c.mo28008a(f1614a, a3, str, str2, (C2258ah) r1, j, z2);
                    } else {
                        this.f1621c.mo28009a(f1614a, a3, str, str2, r1, false);
                    }
                } catch (Throwable th) {
                    C2265al.m611e("req cr error %s", th.toString());
                    if (!C2265al.m608b(th)) {
                        th.printStackTrace();
                    }
                }
            }
        } else {
            C2265al.m610d("warn: not crashHappen or not should upload", new Object[0]);
        }
    }

    /* renamed from: a */
    public static void m705a(boolean z, List<CrashDetailBean> list) {
        if (list != null && list.size() > 0) {
            C2265al.m609c("up finish update state %b", Boolean.valueOf(z));
            for (CrashDetailBean next : list) {
                C2265al.m609c("pre uid:%s uc:%d re:%b me:%b", next.f1365c, Integer.valueOf(next.f1374l), Boolean.valueOf(next.f1366d), Boolean.valueOf(next.f1372j));
                next.f1374l++;
                next.f1366d = z;
                C2265al.m609c("set uid:%s uc:%d re:%b me:%b", next.f1365c, Integer.valueOf(next.f1374l), Boolean.valueOf(next.f1366d), Boolean.valueOf(next.f1372j));
            }
            for (CrashDetailBean a : list) {
                C2293at.m738a().mo28040a(a);
            }
            C2265al.m609c("update state size %d", Integer.valueOf(list.size()));
        }
        if (!z) {
            C2265al.m607b("[crash] upload fail.", new Object[0]);
        }
    }

    /* renamed from: c */
    private static ContentValues m716c(CrashDetailBean crashDetailBean) {
        if (crashDetailBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (crashDetailBean.f1362a > 0) {
                contentValues.put("_id", Long.valueOf(crashDetailBean.f1362a));
            }
            contentValues.put("_tm", Long.valueOf(crashDetailBean.f1380r));
            contentValues.put("_s1", crashDetailBean.f1383u);
            int i = 1;
            contentValues.put("_up", Integer.valueOf(crashDetailBean.f1366d ? 1 : 0));
            if (!crashDetailBean.f1372j) {
                i = 0;
            }
            contentValues.put("_me", Integer.valueOf(i));
            contentValues.put("_uc", Integer.valueOf(crashDetailBean.f1374l));
            contentValues.put("_dt", C2273ap.m658a((Parcelable) crashDetailBean));
            return contentValues;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* renamed from: a */
    private static CrashDetailBean m686a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            CrashDetailBean crashDetailBean = (CrashDetailBean) C2273ap.m639a(blob, CrashDetailBean.CREATOR);
            if (crashDetailBean != null) {
                crashDetailBean.f1362a = j;
            }
            return crashDetailBean;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* renamed from: b */
    public final void mo28036b(CrashDetailBean crashDetailBean) {
        if (crashDetailBean != null) {
            ContentValues c = m716c(crashDetailBean);
            if (c != null) {
                long a = C2365w.m1033a().mo28164a("t_cr", c, (C2364v) null);
                if (a >= 0) {
                    C2265al.m609c("insert %s success!", "t_cr");
                    crashDetailBean.f1362a = a;
                }
            }
            if (C2293at.f1641k) {
                m720d(crashDetailBean);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x00e5 A[Catch:{ all -> 0x00ee }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ea A[DONT_GENERATE] */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.tencent.bugly.crashreport.crash.CrashDetailBean> m717c(java.util.List<com.tencent.bugly.proguard.C2276ar> r10) {
        /*
            java.lang.String r0 = "t_cr"
            r1 = 0
            if (r10 == 0) goto L_0x00f5
            int r2 = r10.size()
            if (r2 != 0) goto L_0x000d
            goto L_0x00f5
        L_0x000d:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "_id in ("
            r2.append(r3)
            java.util.Iterator r10 = r10.iterator()
        L_0x001b:
            boolean r4 = r10.hasNext()
            java.lang.String r5 = ","
            if (r4 == 0) goto L_0x0032
            java.lang.Object r4 = r10.next()
            com.tencent.bugly.proguard.ar r4 = (com.tencent.bugly.proguard.C2276ar) r4
            long r6 = r4.f1608a
            r2.append(r6)
            r2.append(r5)
            goto L_0x001b
        L_0x0032:
            java.lang.String r10 = r2.toString()
            boolean r10 = r10.contains(r5)
            r4 = 0
            if (r10 == 0) goto L_0x004b
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            int r6 = r2.lastIndexOf(r5)
            java.lang.String r2 = r2.substring(r4, r6)
            r10.<init>(r2)
            r2 = r10
        L_0x004b:
            java.lang.String r10 = ")"
            r2.append(r10)
            java.lang.String r6 = r2.toString()
            r2.setLength(r4)
            com.tencent.bugly.proguard.w r7 = com.tencent.bugly.proguard.C2365w.m1033a()     // Catch:{ all -> 0x00dd }
            android.database.Cursor r6 = r7.mo28165a((java.lang.String) r0, (java.lang.String[]) r1, (java.lang.String) r6)     // Catch:{ all -> 0x00dd }
            if (r6 != 0) goto L_0x0067
            if (r6 == 0) goto L_0x0066
            r6.close()
        L_0x0066:
            return r1
        L_0x0067:
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ all -> 0x00db }
            r7.<init>()     // Catch:{ all -> 0x00db }
            r2.append(r3)     // Catch:{ all -> 0x00db }
            r3 = 0
        L_0x0070:
            boolean r8 = r6.moveToNext()     // Catch:{ all -> 0x00db }
            if (r8 == 0) goto L_0x009b
            com.tencent.bugly.crashreport.crash.CrashDetailBean r8 = m686a((android.database.Cursor) r6)     // Catch:{ all -> 0x00db }
            if (r8 == 0) goto L_0x0080
            r7.add(r8)     // Catch:{ all -> 0x00db }
            goto L_0x0070
        L_0x0080:
            java.lang.String r8 = "_id"
            int r8 = r6.getColumnIndex(r8)     // Catch:{ all -> 0x0093 }
            long r8 = r6.getLong(r8)     // Catch:{ all -> 0x0093 }
            r2.append(r8)     // Catch:{ all -> 0x0093 }
            r2.append(r5)     // Catch:{ all -> 0x0093 }
            int r3 = r3 + 1
            goto L_0x0070
        L_0x0093:
            java.lang.String r8 = "unknown id!"
            java.lang.Object[] r9 = new java.lang.Object[r4]     // Catch:{ all -> 0x00db }
            com.tencent.bugly.proguard.C2265al.m610d(r8, r9)     // Catch:{ all -> 0x00db }
            goto L_0x0070
        L_0x009b:
            java.lang.String r8 = r2.toString()     // Catch:{ all -> 0x00db }
            boolean r8 = r8.contains(r5)     // Catch:{ all -> 0x00db }
            if (r8 == 0) goto L_0x00b3
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00db }
            int r5 = r2.lastIndexOf(r5)     // Catch:{ all -> 0x00db }
            java.lang.String r2 = r2.substring(r4, r5)     // Catch:{ all -> 0x00db }
            r8.<init>(r2)     // Catch:{ all -> 0x00db }
            r2 = r8
        L_0x00b3:
            r2.append(r10)     // Catch:{ all -> 0x00db }
            java.lang.String r10 = r2.toString()     // Catch:{ all -> 0x00db }
            if (r3 <= 0) goto L_0x00d5
            com.tencent.bugly.proguard.w r2 = com.tencent.bugly.proguard.C2365w.m1033a()     // Catch:{ all -> 0x00db }
            int r10 = r2.mo28163a((java.lang.String) r0, (java.lang.String) r10)     // Catch:{ all -> 0x00db }
            java.lang.String r2 = "deleted %s illegal data %d"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00db }
            r3[r4] = r0     // Catch:{ all -> 0x00db }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x00db }
            r0 = 1
            r3[r0] = r10     // Catch:{ all -> 0x00db }
            com.tencent.bugly.proguard.C2265al.m610d(r2, r3)     // Catch:{ all -> 0x00db }
        L_0x00d5:
            if (r6 == 0) goto L_0x00da
            r6.close()
        L_0x00da:
            return r7
        L_0x00db:
            r10 = move-exception
            goto L_0x00df
        L_0x00dd:
            r10 = move-exception
            r6 = r1
        L_0x00df:
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r10)     // Catch:{ all -> 0x00ee }
            if (r0 != 0) goto L_0x00e8
            r10.printStackTrace()     // Catch:{ all -> 0x00ee }
        L_0x00e8:
            if (r6 == 0) goto L_0x00ed
            r6.close()
        L_0x00ed:
            return r1
        L_0x00ee:
            r10 = move-exception
            if (r6 == 0) goto L_0x00f4
            r6.close()
        L_0x00f4:
            throw r10
        L_0x00f5:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2277as.m717c(java.util.List):java.util.List");
    }

    /* renamed from: b */
    private static C2276ar m708b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            C2276ar arVar = new C2276ar();
            arVar.f1608a = cursor.getLong(cursor.getColumnIndex("_id"));
            arVar.f1609b = cursor.getLong(cursor.getColumnIndex("_tm"));
            arVar.f1610c = cursor.getString(cursor.getColumnIndex("_s1"));
            boolean z = false;
            arVar.f1611d = cursor.getInt(cursor.getColumnIndex("_up")) == 1;
            if (cursor.getInt(cursor.getColumnIndex("_me")) == 1) {
                z = true;
            }
            arVar.f1612e = z;
            arVar.f1613f = cursor.getInt(cursor.getColumnIndex("_uc"));
            return arVar;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [java.util.List<com.tencent.bugly.proguard.ar>, java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00bc A[Catch:{ all -> 0x00c5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00c1 A[DONT_GENERATE] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.tencent.bugly.proguard.C2276ar> m709b() {
        /*
            java.lang.String r0 = "t_cr"
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 0
            java.lang.String r3 = "_id"
            java.lang.String r4 = "_tm"
            java.lang.String r5 = "_s1"
            java.lang.String r6 = "_up"
            java.lang.String r7 = "_me"
            java.lang.String r8 = "_uc"
            java.lang.String[] r3 = new java.lang.String[]{r3, r4, r5, r6, r7, r8}     // Catch:{ all -> 0x00b5 }
            com.tencent.bugly.proguard.w r4 = com.tencent.bugly.proguard.C2365w.m1033a()     // Catch:{ all -> 0x00b5 }
            android.database.Cursor r3 = r4.mo28165a((java.lang.String) r0, (java.lang.String[]) r3, (java.lang.String) r2)     // Catch:{ all -> 0x00b5 }
            if (r3 != 0) goto L_0x0028
            if (r3 == 0) goto L_0x0027
            r3.close()
        L_0x0027:
            return r2
        L_0x0028:
            int r2 = r3.getCount()     // Catch:{ all -> 0x00b2 }
            if (r2 > 0) goto L_0x0034
            if (r3 == 0) goto L_0x0033
            r3.close()
        L_0x0033:
            return r1
        L_0x0034:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b2 }
            r2.<init>()     // Catch:{ all -> 0x00b2 }
            java.lang.String r4 = "_id in ("
            r2.append(r4)     // Catch:{ all -> 0x00b2 }
            r4 = 0
            r5 = 0
        L_0x0040:
            boolean r6 = r3.moveToNext()     // Catch:{ all -> 0x00b2 }
            java.lang.String r7 = ","
            if (r6 == 0) goto L_0x006d
            com.tencent.bugly.proguard.ar r6 = m708b((android.database.Cursor) r3)     // Catch:{ all -> 0x00b2 }
            if (r6 == 0) goto L_0x0052
            r1.add(r6)     // Catch:{ all -> 0x00b2 }
            goto L_0x0040
        L_0x0052:
            java.lang.String r6 = "_id"
            int r6 = r3.getColumnIndex(r6)     // Catch:{ all -> 0x0065 }
            long r8 = r3.getLong(r6)     // Catch:{ all -> 0x0065 }
            r2.append(r8)     // Catch:{ all -> 0x0065 }
            r2.append(r7)     // Catch:{ all -> 0x0065 }
            int r5 = r5 + 1
            goto L_0x0040
        L_0x0065:
            java.lang.String r6 = "unknown id!"
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x00b2 }
            com.tencent.bugly.proguard.C2265al.m610d(r6, r7)     // Catch:{ all -> 0x00b2 }
            goto L_0x0040
        L_0x006d:
            java.lang.String r6 = r2.toString()     // Catch:{ all -> 0x00b2 }
            boolean r6 = r6.contains(r7)     // Catch:{ all -> 0x00b2 }
            if (r6 == 0) goto L_0x0085
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b2 }
            int r7 = r2.lastIndexOf(r7)     // Catch:{ all -> 0x00b2 }
            java.lang.String r2 = r2.substring(r4, r7)     // Catch:{ all -> 0x00b2 }
            r6.<init>(r2)     // Catch:{ all -> 0x00b2 }
            r2 = r6
        L_0x0085:
            java.lang.String r6 = ")"
            r2.append(r6)     // Catch:{ all -> 0x00b2 }
            java.lang.String r6 = r2.toString()     // Catch:{ all -> 0x00b2 }
            r2.setLength(r4)     // Catch:{ all -> 0x00b2 }
            if (r5 <= 0) goto L_0x00ac
            com.tencent.bugly.proguard.w r2 = com.tencent.bugly.proguard.C2365w.m1033a()     // Catch:{ all -> 0x00b2 }
            int r2 = r2.mo28163a((java.lang.String) r0, (java.lang.String) r6)     // Catch:{ all -> 0x00b2 }
            java.lang.String r5 = "deleted %s illegal data %d"
            r6 = 2
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x00b2 }
            r6[r4] = r0     // Catch:{ all -> 0x00b2 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x00b2 }
            r2 = 1
            r6[r2] = r0     // Catch:{ all -> 0x00b2 }
            com.tencent.bugly.proguard.C2265al.m610d(r5, r6)     // Catch:{ all -> 0x00b2 }
        L_0x00ac:
            if (r3 == 0) goto L_0x00b1
            r3.close()
        L_0x00b1:
            return r1
        L_0x00b2:
            r0 = move-exception
            r2 = r3
            goto L_0x00b6
        L_0x00b5:
            r0 = move-exception
        L_0x00b6:
            boolean r3 = com.tencent.bugly.proguard.C2265al.m605a(r0)     // Catch:{ all -> 0x00c5 }
            if (r3 != 0) goto L_0x00bf
            r0.printStackTrace()     // Catch:{ all -> 0x00c5 }
        L_0x00bf:
            if (r2 == 0) goto L_0x00c4
            r2.close()
        L_0x00c4:
            return r1
        L_0x00c5:
            r0 = move-exception
            if (r2 == 0) goto L_0x00cb
            r2.close()
        L_0x00cb:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2277as.m709b():java.util.List");
    }

    /* renamed from: d */
    private static void m719d(List<C2276ar> list) {
        if (list != null && list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("_id in (");
            for (C2276ar arVar : list) {
                sb.append(arVar.f1608a);
                sb.append(",");
            }
            StringBuilder sb2 = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
            sb2.append(")");
            String sb3 = sb2.toString();
            sb2.setLength(0);
            try {
                C2265al.m609c("deleted %s data %d", "t_cr", Integer.valueOf(C2365w.m1033a().mo28163a("t_cr", sb3)));
            } catch (Throwable th) {
                if (!C2265al.m605a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    /* renamed from: e */
    private static void m722e(List<CrashDetailBean> list) {
        try {
            if (list.size() != 0) {
                StringBuilder sb = new StringBuilder();
                for (CrashDetailBean crashDetailBean : list) {
                    sb.append(" or _id = ");
                    sb.append(crashDetailBean.f1362a);
                }
                String sb2 = sb.toString();
                if (sb2.length() > 0) {
                    sb2 = sb2.substring(4);
                }
                sb.setLength(0);
                C2265al.m609c("deleted %s data %d", "t_cr", Integer.valueOf(C2365w.m1033a().mo28163a("t_cr", sb2)));
            }
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
        }
    }

    /* renamed from: a */
    private static C2327bo m689a(Context context, CrashDetailBean crashDetailBean, C2231aa aaVar) {
        ArrayList<C2324bl> arrayList = null;
        boolean z = false;
        if (context == null || crashDetailBean == null || aaVar == null) {
            C2265al.m610d("enExp args == null", new Object[0]);
            return null;
        }
        C2327bo boVar = new C2327bo();
        boVar.f1780a = m721e(crashDetailBean);
        boVar.f1781b = crashDetailBean.f1380r;
        boVar.f1782c = crashDetailBean.f1376n;
        boVar.f1783d = crashDetailBean.f1377o;
        boVar.f1784e = crashDetailBean.f1378p;
        boVar.f1786g = crashDetailBean.f1379q;
        boVar.f1787h = crashDetailBean.f1388z;
        boVar.f1788i = crashDetailBean.f1365c;
        boVar.f1789j = null;
        boVar.f1791l = crashDetailBean.f1375m;
        boVar.f1792m = crashDetailBean.f1367e;
        boVar.f1785f = crashDetailBean.f1337B;
        boVar.f1793n = null;
        if (crashDetailBean.f1370h != null && !crashDetailBean.f1370h.isEmpty()) {
            arrayList = new ArrayList<>(crashDetailBean.f1370h.size());
            for (Map.Entry next : crashDetailBean.f1370h.entrySet()) {
                C2324bl blVar = new C2324bl();
                blVar.f1760a = ((PlugInBean) next.getValue()).f1310a;
                blVar.f1762c = ((PlugInBean) next.getValue()).f1312c;
                blVar.f1764e = ((PlugInBean) next.getValue()).f1311b;
                arrayList.add(blVar);
            }
        }
        boVar.f1795p = arrayList;
        C2265al.m609c("libInfo %s", boVar.f1794o);
        ArrayList<C2326bn> arrayList2 = new ArrayList<>(20);
        m697a(arrayList2, crashDetailBean);
        m699a(arrayList2, crashDetailBean.f1385w);
        m712b(arrayList2, crashDetailBean.f1386x);
        m718c(arrayList2, crashDetailBean.f1361Z);
        m700a(arrayList2, crashDetailBean.f1363aa, context);
        m702a(arrayList2, crashDetailBean.f1387y);
        m698a(arrayList2, crashDetailBean, context);
        m711b(arrayList2, crashDetailBean, context);
        m701a(arrayList2, aaVar.f1431L);
        m713b(arrayList2, crashDetailBean.f1360Y);
        boVar.f1796q = arrayList2;
        if (crashDetailBean.f1372j) {
            boVar.f1790k = crashDetailBean.f1382t;
        }
        boVar.f1797r = m693a(crashDetailBean, aaVar);
        boVar.f1798s = new HashMap();
        if (crashDetailBean.f1354S != null && crashDetailBean.f1354S.size() > 0) {
            boVar.f1798s.putAll(crashDetailBean.f1354S);
            C2265al.m604a("setted message size %d", Integer.valueOf(boVar.f1798s.size()));
        }
        Map<String, String> map = boVar.f1798s;
        C2265al.m609c("pss:" + crashDetailBean.f1344I + " vss:" + crashDetailBean.f1345J + " javaHeap:" + crashDetailBean.f1346K, new Object[0]);
        StringBuilder sb = new StringBuilder();
        sb.append(crashDetailBean.f1344I);
        map.put("SDK_UPLOAD_U1", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(crashDetailBean.f1345J);
        map.put("SDK_UPLOAD_U2", sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(crashDetailBean.f1346K);
        map.put("SDK_UPLOAD_U3", sb3.toString());
        Object[] objArr = new Object[12];
        objArr[0] = crashDetailBean.f1376n;
        objArr[1] = crashDetailBean.f1365c;
        objArr[2] = aaVar.mo27966d();
        objArr[3] = Long.valueOf((crashDetailBean.f1380r - crashDetailBean.f1352Q) / 1000);
        objArr[4] = Boolean.valueOf(crashDetailBean.f1373k);
        objArr[5] = Boolean.valueOf(crashDetailBean.f1353R);
        objArr[6] = Boolean.valueOf(crashDetailBean.f1372j);
        if (crashDetailBean.f1364b == 1) {
            z = true;
        }
        objArr[7] = Boolean.valueOf(z);
        objArr[8] = Integer.valueOf(crashDetailBean.f1382t);
        objArr[9] = crashDetailBean.f1381s;
        objArr[10] = Boolean.valueOf(crashDetailBean.f1366d);
        objArr[11] = Integer.valueOf(boVar.f1797r.size());
        C2265al.m609c("%s rid:%s sess:%s ls:%ds isR:%b isF:%b isM:%b isN:%b mc:%d ,%s ,isUp:%b ,vm:%d", objArr);
        return boVar;
    }

    /* renamed from: a */
    private static C2328bp m690a(Context context, List<CrashDetailBean> list, C2231aa aaVar) {
        if (context == null || list == null || list.size() == 0 || aaVar == null) {
            C2265al.m610d("enEXPPkg args == null!", new Object[0]);
            return null;
        }
        C2328bp bpVar = new C2328bp();
        bpVar.f1802a = new ArrayList<>();
        for (CrashDetailBean a : list) {
            bpVar.f1802a.add(m689a(context, a, aaVar));
        }
        return bpVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x008e A[Catch:{ all -> 0x00b0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0093 A[SYNTHETIC, Splitter:B:35:0x0093] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a7 A[DONT_GENERATE] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.tencent.bugly.proguard.C2326bn m688a(java.lang.String r6, android.content.Context r7, java.lang.String r8) {
        /*
            java.lang.String r0 = "del tmp"
            r1 = 0
            r2 = 0
            if (r8 == 0) goto L_0x00d0
            if (r7 != 0) goto L_0x000a
            goto L_0x00d0
        L_0x000a:
            r3 = 1
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r4[r2] = r8
            java.lang.String r5 = "zip %s"
            com.tencent.bugly.proguard.C2265al.m609c(r5, r4)
            java.io.File r4 = new java.io.File
            r4.<init>(r8)
            java.io.File r8 = new java.io.File
            java.io.File r7 = r7.getCacheDir()
            r8.<init>(r7, r6)
            boolean r6 = com.tencent.bugly.proguard.C2273ap.m655a((java.io.File) r4, (java.io.File) r8)
            if (r6 != 0) goto L_0x0030
            java.lang.Object[] r6 = new java.lang.Object[r2]
            java.lang.String r7 = "zip fail!"
            com.tencent.bugly.proguard.C2265al.m610d(r7, r6)
            return r1
        L_0x0030:
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream
            r6.<init>()
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ all -> 0x0086 }
            r7.<init>(r8)     // Catch:{ all -> 0x0086 }
            r4 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x0084 }
        L_0x003e:
            int r5 = r7.read(r4)     // Catch:{ all -> 0x0084 }
            if (r5 <= 0) goto L_0x004b
            r6.write(r4, r2, r5)     // Catch:{ all -> 0x0084 }
            r6.flush()     // Catch:{ all -> 0x0084 }
            goto L_0x003e
        L_0x004b:
            byte[] r6 = r6.toByteArray()     // Catch:{ all -> 0x0084 }
            java.lang.String r4 = "read bytes :%d"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0084 }
            int r5 = r6.length     // Catch:{ all -> 0x0084 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0084 }
            r3[r2] = r5     // Catch:{ all -> 0x0084 }
            com.tencent.bugly.proguard.C2265al.m609c(r4, r3)     // Catch:{ all -> 0x0084 }
            com.tencent.bugly.proguard.bn r3 = new com.tencent.bugly.proguard.bn     // Catch:{ all -> 0x0084 }
            r4 = 2
            java.lang.String r5 = r8.getName()     // Catch:{ all -> 0x0084 }
            r3.<init>(r4, r5, r6)     // Catch:{ all -> 0x0084 }
            r7.close()     // Catch:{ IOException -> 0x006b }
            goto L_0x0075
        L_0x006b:
            r6 = move-exception
            boolean r7 = com.tencent.bugly.proguard.C2265al.m605a(r6)
            if (r7 != 0) goto L_0x0075
            r6.printStackTrace()
        L_0x0075:
            boolean r6 = r8.exists()
            if (r6 == 0) goto L_0x0083
            java.lang.Object[] r6 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.C2265al.m609c(r0, r6)
            r8.delete()
        L_0x0083:
            return r3
        L_0x0084:
            r6 = move-exception
            goto L_0x0088
        L_0x0086:
            r6 = move-exception
            r7 = r1
        L_0x0088:
            boolean r3 = com.tencent.bugly.proguard.C2265al.m605a(r6)     // Catch:{ all -> 0x00b0 }
            if (r3 != 0) goto L_0x0091
            r6.printStackTrace()     // Catch:{ all -> 0x00b0 }
        L_0x0091:
            if (r7 == 0) goto L_0x00a1
            r7.close()     // Catch:{ IOException -> 0x0097 }
            goto L_0x00a1
        L_0x0097:
            r6 = move-exception
            boolean r7 = com.tencent.bugly.proguard.C2265al.m605a(r6)
            if (r7 != 0) goto L_0x00a1
            r6.printStackTrace()
        L_0x00a1:
            boolean r6 = r8.exists()
            if (r6 == 0) goto L_0x00af
            java.lang.Object[] r6 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.C2265al.m609c(r0, r6)
            r8.delete()
        L_0x00af:
            return r1
        L_0x00b0:
            r6 = move-exception
            if (r7 == 0) goto L_0x00c1
            r7.close()     // Catch:{ IOException -> 0x00b7 }
            goto L_0x00c1
        L_0x00b7:
            r7 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r7)
            if (r1 != 0) goto L_0x00c1
            r7.printStackTrace()
        L_0x00c1:
            boolean r7 = r8.exists()
            if (r7 == 0) goto L_0x00cf
            java.lang.Object[] r7 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.C2265al.m609c(r0, r7)
            r8.delete()
        L_0x00cf:
            throw r6
        L_0x00d0:
            java.lang.Object[] r6 = new java.lang.Object[r2]
            java.lang.String r7 = "rqdp{  createZipAttachment sourcePath == null || context == null ,pls check}"
            com.tencent.bugly.proguard.C2265al.m610d(r7, r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2277as.m688a(java.lang.String, android.content.Context, java.lang.String):com.tencent.bugly.proguard.bn");
    }

    /* renamed from: d */
    private boolean m720d(CrashDetailBean crashDetailBean) {
        try {
            C2265al.m609c("save eup logs", new Object[0]);
            C2231aa b = C2231aa.m459b();
            String str = "#--------\npackage:" + b.mo27968e() + "\nversion:" + b.f1483o + "\nsdk:" + b.f1476h + "\nprocess:" + crashDetailBean.f1336A + "\ndate:" + C2273ap.m647a(new Date(crashDetailBean.f1380r)) + "\ntype:" + crashDetailBean.f1376n + "\nmessage:" + crashDetailBean.f1377o + "\nstack:\n" + crashDetailBean.f1379q + "\neupID:" + crashDetailBean.f1365c + "\n";
            String str2 = null;
            if (C2293at.f1642l != null) {
                File file = new File(C2293at.f1642l);
                if (file.isFile()) {
                    file = file.getParentFile();
                }
                str2 = file.getAbsolutePath();
            } else if (Environment.getExternalStorageState().equals("mounted")) {
                str2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Tencent/" + this.f1620b.getPackageName();
            }
            C2266am.m617a(str2 + "/euplog.txt", str, C2293at.f1643m);
            return true;
        } catch (Throwable th) {
            C2265al.m610d("rqdp{  save error} %s", th.toString());
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    /* renamed from: a */
    public static void m696a(String str, String str2, String str3, String str4, String str5, CrashDetailBean crashDetailBean) {
        String str6;
        C2231aa b = C2231aa.m459b();
        if (b != null) {
            C2265al.m611e("#++++++++++Record By Bugly++++++++++#", new Object[0]);
            C2265al.m611e("# You can use Bugly(http:\\\\bugly.qq.com) to get more Crash Detail!", new Object[0]);
            C2265al.m611e("# PKG NAME: %s", b.f1471c);
            C2265al.m611e("# APP VER: %s", b.f1483o);
            C2265al.m611e("# SDK VER: %s", b.f1476h);
            C2265al.m611e("# LAUNCH TIME: %s", C2273ap.m647a(new Date(C2231aa.m459b().f1445a)));
            C2265al.m611e("# CRASH TYPE: %s", str);
            C2265al.m611e("# CRASH TIME: %s", str2);
            C2265al.m611e("# CRASH PROCESS: %s", str3);
            C2265al.m611e("# CRASH FOREGROUND: %s", Boolean.valueOf(b.mo27961a()));
            C2265al.m611e("# CRASH THREAD: %s", str4);
            if (crashDetailBean != null) {
                C2265al.m611e("# REPORT ID: %s", crashDetailBean.f1365c);
                Object[] objArr = new Object[2];
                objArr[0] = b.mo27974h();
                objArr[1] = b.mo27983r().booleanValue() ? "ROOTED" : "UNROOT";
                C2265al.m611e("# CRASH DEVICE: %s %s", objArr);
                C2265al.m611e("# RUNTIME AVAIL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.f1338C), Long.valueOf(crashDetailBean.f1339D), Long.valueOf(crashDetailBean.f1340E));
                C2265al.m611e("# RUNTIME TOTAL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.f1341F), Long.valueOf(crashDetailBean.f1342G), Long.valueOf(crashDetailBean.f1343H));
                if (!C2273ap.m657a(crashDetailBean.f1350O)) {
                    C2265al.m611e("# EXCEPTION FIRED BY %s %s", crashDetailBean.f1350O, crashDetailBean.f1349N);
                } else if (crashDetailBean.f1364b == 3) {
                    Object[] objArr2 = new Object[1];
                    if (crashDetailBean.f1355T == null) {
                        str6 = "null";
                    } else {
                        str6 = crashDetailBean.f1355T.get("BUGLY_CR_01");
                    }
                    objArr2[0] = str6;
                    C2265al.m611e("# EXCEPTION ANR MESSAGE:\n %s", objArr2);
                }
            }
            if (!C2273ap.m657a(str5)) {
                C2265al.m611e("# CRASH STACK: ", new Object[0]);
                C2265al.m611e(str5, new Object[0]);
            }
            C2265al.m611e("#++++++++++++++++++++++++++++++++++++++++++#", new Object[0]);
        }
    }

    /* renamed from: a */
    private static void m695a(CrashDetailBean crashDetailBean, Map<String, String> map) {
        String str;
        if (map == null || map.isEmpty()) {
            C2265al.m610d("extra map is empty. CrashBean won't have userDatas.", new Object[0]);
            return;
        }
        crashDetailBean.f1354S = new LinkedHashMap(map.size());
        for (Map.Entry next : map.entrySet()) {
            if (!C2273ap.m657a((String) next.getKey())) {
                String str2 = (String) next.getKey();
                if (str2.length() > 100) {
                    str2 = str2.substring(0, 100);
                    C2265al.m610d("setted key length is over limit %d substring to %s", 100, str2);
                }
                if (C2273ap.m657a((String) next.getValue()) || ((String) next.getValue()).length() <= 100000) {
                    str = (String) next.getValue();
                } else {
                    str = ((String) next.getValue()).substring(((String) next.getValue()).length() - BuglyStrategy.C2217a.MAX_USERDATA_VALUE_LENGTH);
                    C2265al.m610d("setted %s value length is over limit %d substring", str2, Integer.valueOf(BuglyStrategy.C2217a.MAX_USERDATA_VALUE_LENGTH));
                }
                crashDetailBean.f1354S.put(str2, str);
                C2265al.m604a("add setted key %s value size:%d", str2, Integer.valueOf(str.length()));
            }
        }
    }

    /* renamed from: e */
    private static String m721e(CrashDetailBean crashDetailBean) {
        try {
            Pair pair = f1615h.get(Integer.valueOf(crashDetailBean.f1364b));
            if (pair == null) {
                C2265al.m611e("crash type error! %d", Integer.valueOf(crashDetailBean.f1364b));
                return "";
            } else if (crashDetailBean.f1372j) {
                return (String) pair.first;
            } else {
                return (String) pair.second;
            }
        } catch (Exception e) {
            C2265al.m605a(e);
            return "";
        }
    }

    /* renamed from: a */
    private static void m697a(ArrayList<C2326bn> arrayList, CrashDetailBean crashDetailBean) {
        if (crashDetailBean.f1372j && crashDetailBean.f1381s != null && crashDetailBean.f1381s.length() > 0) {
            try {
                arrayList.add(new C2326bn((byte) 1, "alltimes.txt", crashDetailBean.f1381s.getBytes("utf-8")));
            } catch (Exception e) {
                e.printStackTrace();
                C2265al.m605a(e);
            }
        }
    }

    /* renamed from: a */
    private static void m699a(ArrayList<C2326bn> arrayList, String str) {
        if (str != null) {
            try {
                arrayList.add(new C2326bn((byte) 1, "log.txt", str.getBytes("utf-8")));
            } catch (Exception e) {
                e.printStackTrace();
                C2265al.m605a(e);
            }
        }
    }

    /* renamed from: b */
    private static void m712b(ArrayList<C2326bn> arrayList, String str) {
        if (str != null) {
            try {
                arrayList.add(new C2326bn((byte) 1, "jniLog.txt", str.getBytes("utf-8")));
            } catch (Exception e) {
                e.printStackTrace();
                C2265al.m605a(e);
            }
        }
    }

    /* renamed from: c */
    private static void m718c(ArrayList<C2326bn> arrayList, String str) {
        if (!C2273ap.m657a(str)) {
            try {
                C2326bn bnVar = new C2326bn((byte) 1, "crashInfos.txt", str.getBytes("utf-8"));
                C2265al.m609c("attach crash infos", new Object[0]);
                arrayList.add(bnVar);
            } catch (Exception e) {
                e.printStackTrace();
                C2265al.m605a(e);
            }
        }
    }

    /* renamed from: a */
    private static void m700a(ArrayList<C2326bn> arrayList, String str, Context context) {
        if (str != null) {
            try {
                C2326bn a = m688a("backupRecord.zip", context, str);
                if (a != null) {
                    C2265al.m609c("attach backup record", new Object[0]);
                    arrayList.add(a);
                }
            } catch (Exception e) {
                C2265al.m605a(e);
            }
        }
    }

    /* renamed from: a */
    private static void m702a(ArrayList<C2326bn> arrayList, byte[] bArr) {
        if (bArr != null && bArr.length > 0) {
            try {
                C2326bn bnVar = new C2326bn((byte) 2, "buglylog.zip", bArr);
                C2265al.m609c("attach user log", new Object[0]);
                arrayList.add(bnVar);
            } catch (Exception e) {
                C2265al.m605a(e);
            }
        }
    }

    /* renamed from: a */
    private static void m698a(ArrayList<C2326bn> arrayList, CrashDetailBean crashDetailBean, Context context) {
        C2326bn a;
        if (crashDetailBean.f1364b == 3) {
            C2265al.m609c("crashBean.anrMessages:%s", crashDetailBean.f1355T);
            try {
                if (crashDetailBean.f1355T != null && crashDetailBean.f1355T.containsKey("BUGLY_CR_01")) {
                    if (!TextUtils.isEmpty(crashDetailBean.f1355T.get("BUGLY_CR_01"))) {
                        arrayList.add(new C2326bn((byte) 1, "anrMessage.txt", crashDetailBean.f1355T.get("BUGLY_CR_01").getBytes("utf-8")));
                        C2265al.m609c("attach anr message", new Object[0]);
                    }
                    crashDetailBean.f1355T.remove("BUGLY_CR_01");
                }
                if (crashDetailBean.f1384v != null && (a = m688a("trace.zip", context, crashDetailBean.f1384v)) != null) {
                    C2265al.m609c("attach traces", new Object[0]);
                    arrayList.add(a);
                }
            } catch (Exception e) {
                e.printStackTrace();
                C2265al.m605a(e);
            }
        }
    }

    /* renamed from: b */
    private static void m711b(ArrayList<C2326bn> arrayList, CrashDetailBean crashDetailBean, Context context) {
        if (crashDetailBean.f1364b == 1 && crashDetailBean.f1384v != null) {
            try {
                C2326bn a = m688a("tomb.zip", context, crashDetailBean.f1384v);
                if (a != null) {
                    C2265al.m609c("attach tombs", new Object[0]);
                    arrayList.add(a);
                }
            } catch (Exception e) {
                C2265al.m605a(e);
            }
        }
    }

    /* renamed from: a */
    private static void m701a(ArrayList<C2326bn> arrayList, List<String> list) {
        if (list != null && !list.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String append : list) {
                sb.append(append);
            }
            try {
                arrayList.add(new C2326bn((byte) 1, "martianlog.txt", sb.toString().getBytes("utf-8")));
                C2265al.m609c("attach pageTracingList", new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: b */
    private static void m713b(ArrayList<C2326bn> arrayList, byte[] bArr) {
        if (bArr != null && bArr.length > 0) {
            try {
                arrayList.add(new C2326bn((byte) 1, "userExtraByteData", bArr));
                C2265al.m609c("attach extraData", new Object[0]);
            } catch (Exception e) {
                C2265al.m605a(e);
            }
        }
    }

    /* renamed from: a */
    private static Map<String, String> m693a(CrashDetailBean crashDetailBean, C2231aa aaVar) {
        HashMap hashMap = new HashMap(30);
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(crashDetailBean.f1338C);
            hashMap.put("A9", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(crashDetailBean.f1339D);
            hashMap.put("A11", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(crashDetailBean.f1340E);
            hashMap.put("A10", sb3.toString());
            hashMap.put("A23", crashDetailBean.f1368f);
            StringBuilder sb4 = new StringBuilder();
            aaVar.getClass();
            hashMap.put("A7", sb4.toString());
            hashMap.put("A6", C2231aa.m460n());
            hashMap.put("A5", aaVar.mo27979m());
            hashMap.put("A22", aaVar.mo27972g());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(crashDetailBean.f1342G);
            hashMap.put("A2", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append(crashDetailBean.f1341F);
            hashMap.put("A1", sb6.toString());
            hashMap.put("A24", aaVar.f1479k);
            StringBuilder sb7 = new StringBuilder();
            sb7.append(crashDetailBean.f1343H);
            hashMap.put("A17", sb7.toString());
            hashMap.put("A25", aaVar.mo27972g());
            hashMap.put("A15", aaVar.mo27982q());
            StringBuilder sb8 = new StringBuilder();
            sb8.append(aaVar.mo27983r());
            hashMap.put("A13", sb8.toString());
            hashMap.put("A34", crashDetailBean.f1336A);
            if (aaVar.f1426G != null) {
                hashMap.put("productIdentify", aaVar.f1426G);
            }
            hashMap.put("A26", URLEncoder.encode(crashDetailBean.f1347L, "utf-8"));
            boolean z = true;
            if (crashDetailBean.f1364b == 1) {
                hashMap.put("A27", crashDetailBean.f1350O);
                hashMap.put("A28", crashDetailBean.f1349N);
                StringBuilder sb9 = new StringBuilder();
                sb9.append(crashDetailBean.f1373k);
                hashMap.put("A29", sb9.toString());
            }
            hashMap.put("A30", crashDetailBean.f1351P);
            StringBuilder sb10 = new StringBuilder();
            sb10.append(crashDetailBean.f1352Q);
            hashMap.put("A18", sb10.toString());
            StringBuilder sb11 = new StringBuilder();
            if (crashDetailBean.f1353R) {
                z = false;
            }
            sb11.append(z);
            hashMap.put("A36", sb11.toString());
            StringBuilder sb12 = new StringBuilder();
            sb12.append(aaVar.f1494z);
            hashMap.put("F02", sb12.toString());
            StringBuilder sb13 = new StringBuilder();
            sb13.append(aaVar.f1420A);
            hashMap.put("F03", sb13.toString());
            hashMap.put("F04", aaVar.mo27966d());
            StringBuilder sb14 = new StringBuilder();
            sb14.append(aaVar.f1421B);
            hashMap.put("F05", sb14.toString());
            hashMap.put("F06", aaVar.f1493y);
            hashMap.put("F08", aaVar.f1424E);
            hashMap.put("F09", aaVar.f1425F);
            StringBuilder sb15 = new StringBuilder();
            sb15.append(aaVar.f1422C);
            hashMap.put("F10", sb15.toString());
            m704a((Map<String, String>) hashMap, crashDetailBean);
        } catch (Exception e) {
            e.printStackTrace();
            C2265al.m605a(e);
        }
        return hashMap;
    }

    /* renamed from: a */
    private static void m704a(Map<String, String> map, CrashDetailBean crashDetailBean) {
        if (crashDetailBean.f1356U >= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(crashDetailBean.f1356U);
            map.put(TestPaper.Code.C01, sb.toString());
        }
        if (crashDetailBean.f1357V >= 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(crashDetailBean.f1357V);
            map.put(TestPaper.Code.C02, sb2.toString());
        }
        if (crashDetailBean.f1358W != null && crashDetailBean.f1358W.size() > 0) {
            for (Map.Entry next : crashDetailBean.f1358W.entrySet()) {
                map.put("C03_" + ((String) next.getKey()), next.getValue());
            }
        }
        if (crashDetailBean.f1359X != null && crashDetailBean.f1359X.size() > 0) {
            for (Map.Entry next2 : crashDetailBean.f1359X.entrySet()) {
                map.put("C04_" + ((String) next2.getKey()), next2.getValue());
            }
        }
    }

    /* renamed from: com.tencent.bugly.proguard.as$a */
    /* compiled from: BUGLY */
    static abstract class C2284a {

        /* renamed from: a */
        final int f1630a;

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public abstract boolean mo28038a();

        /* synthetic */ C2284a(int i, byte b) {
            this(i);
        }

        private C2284a(int i) {
            this.f1630a = i;
        }
    }

    /* renamed from: com.tencent.bugly.proguard.as$b */
    /* compiled from: BUGLY */
    static class C2285b extends C2284a {
        /* synthetic */ C2285b(byte b) {
            this();
        }

        private C2285b() {
            super(3, (byte) 0);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public final boolean mo28038a() {
            return C2293at.m738a().mo28051k();
        }
    }

    /* renamed from: com.tencent.bugly.proguard.as$c */
    /* compiled from: BUGLY */
    static class C2286c extends C2284a {
        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public final boolean mo28038a() {
            return true;
        }

        /* synthetic */ C2286c(byte b) {
            this();
        }

        private C2286c() {
            super(7, (byte) 0);
        }
    }

    /* renamed from: com.tencent.bugly.proguard.as$d */
    /* compiled from: BUGLY */
    static class C2287d extends C2284a {
        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public final boolean mo28038a() {
            return true;
        }

        /* synthetic */ C2287d(byte b) {
            this();
        }

        private C2287d() {
            super(2, (byte) 0);
        }
    }

    /* renamed from: com.tencent.bugly.proguard.as$e */
    /* compiled from: BUGLY */
    static class C2288e extends C2284a {
        /* synthetic */ C2288e(byte b) {
            this();
        }

        private C2288e() {
            super(0, (byte) 0);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public final boolean mo28038a() {
            return C2293at.m738a().mo28050j();
        }
    }

    /* renamed from: com.tencent.bugly.proguard.as$h */
    /* compiled from: BUGLY */
    static class C2291h extends C2284a {
        /* synthetic */ C2291h(byte b) {
            this();
        }

        private C2291h() {
            super(1, (byte) 0);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public final boolean mo28038a() {
            return C2293at.m738a().mo28050j();
        }
    }

    /* renamed from: com.tencent.bugly.proguard.as$i */
    /* compiled from: BUGLY */
    static class C2292i extends C2284a {
        /* synthetic */ C2292i(byte b) {
            this();
        }

        private C2292i() {
            super(4, (byte) 0);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public final boolean mo28038a() {
            return (C2293at.m738a().f1648A & 4) > 0;
        }
    }

    /* renamed from: com.tencent.bugly.proguard.as$f */
    /* compiled from: BUGLY */
    static class C2289f extends C2284a {
        /* synthetic */ C2289f(byte b) {
            this();
        }

        private C2289f() {
            super(5, (byte) 0);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public final boolean mo28038a() {
            return (C2293at.m738a().f1648A & 2) > 0;
        }
    }

    /* renamed from: com.tencent.bugly.proguard.as$g */
    /* compiled from: BUGLY */
    static class C2290g extends C2284a {
        /* synthetic */ C2290g(byte b) {
            this();
        }

        private C2290g() {
            super(6, (byte) 0);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public final boolean mo28038a() {
            return (C2293at.m738a().f1648A & 1) > 0;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0036, code lost:
        if (r0.size() >= com.tencent.bugly.proguard.C2293at.f1634d) goto L_0x0038;
     */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean m715b(com.tencent.bugly.crashreport.crash.CrashDetailBean r9, java.util.List<com.tencent.bugly.proguard.C2276ar> r10, java.util.List<com.tencent.bugly.proguard.C2276ar> r11) {
        /*
            r8 = this;
            int r0 = r9.f1364b
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x000b
            if (r0 != r1) goto L_0x0009
            goto L_0x000b
        L_0x0009:
            r3 = 0
            goto L_0x000c
        L_0x000b:
            r3 = 1
        L_0x000c:
            r4 = 3
            if (r0 != r4) goto L_0x0011
            r0 = 1
            goto L_0x0012
        L_0x0011:
            r0 = 0
        L_0x0012:
            boolean r4 = com.tencent.bugly.proguard.C2349p.f1912c
            if (r4 != 0) goto L_0x001f
            if (r0 != 0) goto L_0x001c
            if (r3 != 0) goto L_0x001c
            r0 = 1
            goto L_0x0020
        L_0x001c:
            boolean r0 = com.tencent.bugly.proguard.C2293at.f1635e
            goto L_0x0020
        L_0x001f:
            r0 = 0
        L_0x0020:
            if (r0 != 0) goto L_0x0023
            return r2
        L_0x0023:
            java.util.ArrayList r0 = new java.util.ArrayList
            r3 = 10
            r0.<init>(r3)
            boolean r10 = m706a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r9, (java.util.List<com.tencent.bugly.proguard.C2276ar>) r10, (java.util.List<com.tencent.bugly.proguard.C2276ar>) r0)
            if (r10 != 0) goto L_0x0038
            int r10 = r0.size()     // Catch:{ Exception -> 0x006d }
            int r3 = com.tencent.bugly.proguard.C2293at.f1634d     // Catch:{ Exception -> 0x006d }
            if (r10 < r3) goto L_0x0078
        L_0x0038:
            java.lang.String r10 = "same crash occur too much do merged!"
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x006d }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r10, (java.lang.Object[]) r3)     // Catch:{ Exception -> 0x006d }
            com.tencent.bugly.crashreport.crash.CrashDetailBean r9 = m687a((java.util.List<com.tencent.bugly.proguard.C2276ar>) r0, (com.tencent.bugly.crashreport.crash.CrashDetailBean) r9)     // Catch:{ Exception -> 0x006d }
            java.util.Iterator r10 = r0.iterator()     // Catch:{ Exception -> 0x006d }
        L_0x0047:
            boolean r0 = r10.hasNext()     // Catch:{ Exception -> 0x006d }
            if (r0 == 0) goto L_0x005f
            java.lang.Object r0 = r10.next()     // Catch:{ Exception -> 0x006d }
            com.tencent.bugly.proguard.ar r0 = (com.tencent.bugly.proguard.C2276ar) r0     // Catch:{ Exception -> 0x006d }
            long r3 = r0.f1608a     // Catch:{ Exception -> 0x006d }
            long r5 = r9.f1362a     // Catch:{ Exception -> 0x006d }
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x0047
            r11.add(r0)     // Catch:{ Exception -> 0x006d }
            goto L_0x0047
        L_0x005f:
            r8.mo28036b((com.tencent.bugly.crashreport.crash.CrashDetailBean) r9)     // Catch:{ Exception -> 0x006d }
            m719d((java.util.List<com.tencent.bugly.proguard.C2276ar>) r11)     // Catch:{ Exception -> 0x006d }
            java.lang.String r9 = "[crash] save crash success. For this device crash many times, it will not upload crashes immediately"
            java.lang.Object[] r10 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x006d }
            com.tencent.bugly.proguard.C2265al.m607b(r9, r10)     // Catch:{ Exception -> 0x006d }
            return r1
        L_0x006d:
            r9 = move-exception
            com.tencent.bugly.proguard.C2265al.m605a(r9)
            java.lang.Object[] r9 = new java.lang.Object[r2]
            java.lang.String r10 = "Failed to merge crash."
            com.tencent.bugly.proguard.C2265al.m610d(r10, r9)
        L_0x0078:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2277as.m715b(com.tencent.bugly.crashreport.crash.CrashDetailBean, java.util.List, java.util.List):boolean");
    }

    /* renamed from: a */
    static /* synthetic */ void m703a(List list, boolean z, long j, String str, String str2) {
        if (list != null && !list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                CrashDetailBean crashDetailBean = (CrashDetailBean) it.next();
                String str3 = f1619l.get(Integer.valueOf(crashDetailBean.f1364b));
                if (!TextUtils.isEmpty(str3)) {
                    arrayList.add(new C2253ag.C2257c(crashDetailBean.f1365c, str3, crashDetailBean.f1380r, z, j, str, str2));
                }
            }
            C2253ag.C2255a.f1515a.mo28000a((List<C2253ag.C2257c>) arrayList);
        }
    }
}
