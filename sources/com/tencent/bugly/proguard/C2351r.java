package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import p036no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

/* renamed from: com.tencent.bugly.proguard.r */
/* compiled from: BUGLY */
public final class C2351r {

    /* renamed from: e */
    private static boolean f1915e = true;

    /* renamed from: a */
    private Context f1916a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public long f1917b;

    /* renamed from: c */
    private int f1918c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public boolean f1919d = true;

    public C2351r(Context context, boolean z) {
        this.f1916a = context;
        this.f1919d = z;
    }

    /* renamed from: a */
    public final void mo28143a(int i, boolean z) {
        C2248ac a = C2248ac.m533a();
        int i2 = 0;
        if (a == null || a.mo27996c().f1319g || i == 1 || i == 3) {
            if (i == 1 || i == 3) {
                this.f1918c++;
            }
            C2231aa a2 = C2231aa.m457a(this.f1916a);
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.f1292b = i;
            userInfoBean.f1293c = a2.f1472d;
            userInfoBean.f1294d = a2.mo27970f();
            userInfoBean.f1295e = System.currentTimeMillis();
            userInfoBean.f1296f = -1;
            userInfoBean.f1304n = a2.f1483o;
            if (i == 1) {
                i2 = 1;
            }
            userInfoBean.f1305o = i2;
            userInfoBean.f1302l = a2.mo27961a();
            userInfoBean.f1303m = a2.f1493y;
            userInfoBean.f1297g = a2.f1494z;
            userInfoBean.f1298h = a2.f1420A;
            userInfoBean.f1299i = a2.f1421B;
            userInfoBean.f1301k = a2.f1422C;
            userInfoBean.f1308r = a2.mo27985t();
            userInfoBean.f1309s = a2.mo27990y();
            userInfoBean.f1306p = a2.mo27991z();
            userInfoBean.f1307q = a2.f1492x;
            C2263ak.m595a().mo28018a(new C2354a(userInfoBean, z), 0);
            return;
        }
        C2265al.m611e("UserInfo is disable", new Object[0]);
    }

    /* renamed from: a */
    public final void mo28144a(long j) {
        C2263ak.m595a().mo28018a(new C2356c(j), j);
    }

    /* renamed from: a */
    public final void mo28142a() {
        this.f1917b = C2273ap.m661b() + 86400000;
        C2263ak.m595a().mo28018a(new C2355b(), (this.f1917b - System.currentTimeMillis()) + BootloaderScanner.TIMEOUT);
    }

    /* renamed from: com.tencent.bugly.proguard.r$a */
    /* compiled from: BUGLY */
    class C2354a implements Runnable {

        /* renamed from: b */
        private boolean f1925b;

        /* renamed from: c */
        private UserInfoBean f1926c;

        public C2354a(UserInfoBean userInfoBean, boolean z) {
            this.f1926c = userInfoBean;
            this.f1925b = z;
        }

        public final void run() {
            if (C2351r.this.f1919d) {
                try {
                    UserInfoBean userInfoBean = this.f1926c;
                    if (userInfoBean != null) {
                        C2351r.m974a(userInfoBean);
                        C2265al.m609c("[UserInfo] Record user info.", new Object[0]);
                        C2351r.this.m975a(this.f1926c, false);
                    }
                    if (this.f1925b) {
                        C2351r.this.mo28145b();
                    }
                } catch (Throwable th) {
                    if (!C2265al.m605a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    /* renamed from: a */
    private static void m978a(List<UserInfoBean> list, List<UserInfoBean> list2) {
        int size = list.size() - 20;
        if (size > 0) {
            int i = 0;
            while (i < list.size() - 1) {
                int i2 = i + 1;
                for (int i3 = i2; i3 < list.size(); i3++) {
                    if (list.get(i).f1295e > list.get(i3).f1295e) {
                        list.set(i, list.get(i3));
                        list.set(i3, list.get(i));
                    }
                }
                i = i2;
            }
            for (int i4 = 0; i4 < size; i4++) {
                list2.add(list.get(i4));
            }
        }
    }

    /* renamed from: b */
    private static void m985b(List<UserInfoBean> list, List<UserInfoBean> list2) {
        Iterator<UserInfoBean> it = list.iterator();
        while (it.hasNext()) {
            UserInfoBean next = it.next();
            if (next.f1296f != -1) {
                it.remove();
                if (next.f1295e < C2273ap.m661b()) {
                    list2.add(next);
                }
            }
        }
    }

    /* renamed from: a */
    private static int m971a(List<UserInfoBean> list) {
        long currentTimeMillis = System.currentTimeMillis();
        int i = 0;
        for (UserInfoBean next : list) {
            if (next.f1295e > currentTimeMillis - 600000 && (next.f1292b == 1 || next.f1292b == 4 || next.f1292b == 3)) {
                i++;
            }
        }
        return i;
    }

    /* renamed from: a */
    private void m979a(final List<UserInfoBean> list, boolean z) {
        C2231aa b;
        if (!m986b(z)) {
            long currentTimeMillis = System.currentTimeMillis();
            for (UserInfoBean next : list) {
                next.f1296f = currentTimeMillis;
                m975a(next, true);
            }
            C2265al.m610d("uploadCheck failed", new Object[0]);
            return;
        }
        int i = this.f1918c == 1 ? 1 : 2;
        C2334bv bvVar = null;
        if (!(list == null || list.size() == 0 || (b = C2231aa.m459b()) == null)) {
            b.mo27980o();
            C2334bv bvVar2 = new C2334bv();
            bvVar2.f1868b = b.f1472d;
            bvVar2.f1869c = b.mo27972g();
            ArrayList<C2333bu> arrayList = new ArrayList<>();
            for (UserInfoBean a : list) {
                C2333bu a2 = C2251ae.m549a(a);
                if (a2 != null) {
                    arrayList.add(a2);
                }
            }
            bvVar2.f1870d = arrayList;
            bvVar2.f1871e = new HashMap();
            Map<String, String> map = bvVar2.f1871e;
            StringBuilder sb = new StringBuilder();
            b.getClass();
            map.put("A7", sb.toString());
            Map<String, String> map2 = bvVar2.f1871e;
            map2.put("A6", C2231aa.m460n());
            Map<String, String> map3 = bvVar2.f1871e;
            map3.put("A5", b.mo27979m());
            Map<String, String> map4 = bvVar2.f1871e;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(b.mo27977k());
            map4.put("A2", sb2.toString());
            Map<String, String> map5 = bvVar2.f1871e;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(b.mo27977k());
            map5.put("A1", sb3.toString());
            Map<String, String> map6 = bvVar2.f1871e;
            map6.put("A24", b.f1479k);
            Map<String, String> map7 = bvVar2.f1871e;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(b.mo27978l());
            map7.put("A17", sb4.toString());
            Map<String, String> map8 = bvVar2.f1871e;
            map8.put("A15", b.mo27982q());
            Map<String, String> map9 = bvVar2.f1871e;
            StringBuilder sb5 = new StringBuilder();
            sb5.append(b.mo27983r());
            map9.put("A13", sb5.toString());
            Map<String, String> map10 = bvVar2.f1871e;
            map10.put("F08", b.f1424E);
            Map<String, String> map11 = bvVar2.f1871e;
            map11.put("F09", b.f1425F);
            Map<String, String> y = b.mo27990y();
            if (y != null && y.size() > 0) {
                for (Map.Entry next2 : y.entrySet()) {
                    Map<String, String> map12 = bvVar2.f1871e;
                    map12.put("C04_" + ((String) next2.getKey()), next2.getValue());
                }
            }
            if (i == 1) {
                bvVar2.f1867a = 1;
            } else if (i != 2) {
                C2265al.m611e("unknown up type %d ", Integer.valueOf(i));
            } else {
                bvVar2.f1867a = 2;
            }
            bvVar = bvVar2;
        }
        if (bvVar == null) {
            C2265al.m610d("[UserInfo] Failed to create UserInfoPackage.", new Object[0]);
            return;
        }
        byte[] a3 = C2251ae.m551a((C2346m) bvVar);
        if (a3 == null) {
            C2265al.m610d("[UserInfo] Failed to encode data.", new Object[0]);
            return;
        }
        C2329bq a4 = C2251ae.m547a(this.f1916a, 840, a3);
        if (a4 == null) {
            C2265al.m610d("[UserInfo] Request package is null.", new Object[0]);
            return;
        }
        C23521 r9 = new C2258ah() {
            /* renamed from: a */
            public final void mo28004a(boolean z, String str) {
                if (z) {
                    C2265al.m609c("[UserInfo] Successfully uploaded user info.", new Object[0]);
                    long currentTimeMillis = System.currentTimeMillis();
                    for (UserInfoBean userInfoBean : list) {
                        userInfoBean.f1296f = currentTimeMillis;
                        C2351r.this.m975a(userInfoBean, true);
                    }
                }
            }
        };
        C2259ai.m569a().mo28009a(1001, a4, C2248ac.m533a().mo27996c().f1329q, StrategyBean.f1313a, r9, this.f1918c == 1);
    }

    /* renamed from: b */
    public final void mo28145b() {
        C2263ak a = C2263ak.m595a();
        if (a != null) {
            a.mo28017a(new Runnable() {

                /* renamed from: a */
                final /* synthetic */ boolean f1922a = false;

                public final void run() {
                    try {
                        C2351r.this.m980a(this.f1922a);
                    } catch (Throwable th) {
                        C2265al.m605a(th);
                    }
                }
            });
        }
    }

    /* renamed from: com.tencent.bugly.proguard.r$b */
    /* compiled from: BUGLY */
    class C2355b implements Runnable {
        C2355b() {
        }

        public final void run() {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < C2351r.this.f1917b) {
                C2263ak.m595a().mo28018a(new C2355b(), (C2351r.this.f1917b - currentTimeMillis) + BootloaderScanner.TIMEOUT);
                return;
            }
            C2351r.this.mo28143a(3, false);
            C2351r.this.mo28142a();
        }
    }

    /* renamed from: com.tencent.bugly.proguard.r$c */
    /* compiled from: BUGLY */
    class C2356c implements Runnable {

        /* renamed from: b */
        private long f1929b = 21600000;

        public C2356c(long j) {
            this.f1929b = j;
        }

        public final void run() {
            C2351r.this.mo28145b();
            C2351r.this.mo28144a(this.f1929b);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m975a(UserInfoBean userInfoBean, boolean z) {
        List<UserInfoBean> a;
        if (userInfoBean != null) {
            if (z || userInfoBean.f1292b == 1 || (a = m973a(C2231aa.m457a(this.f1916a).f1472d)) == null || a.size() < 20) {
                long a2 = C2365w.m1033a().mo28164a("t_ui", m983b(userInfoBean), (C2364v) null);
                if (a2 >= 0) {
                    C2265al.m609c("[Database] insert %s success with ID: %d", "t_ui", Long.valueOf(a2));
                    userInfoBean.f1291a = a2;
                    return;
                }
                return;
            }
            C2265al.m604a("[UserInfo] There are too many user info in local: %d", Integer.valueOf(a.size()));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x009c A[Catch:{ all -> 0x00a5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00a1 A[DONT_GENERATE] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<com.tencent.bugly.crashreport.biz.UserInfoBean> m973a(java.lang.String r8) {
        /*
            java.lang.String r0 = "t_ui"
            r1 = 0
            boolean r2 = com.tencent.bugly.proguard.C2273ap.m657a((java.lang.String) r8)     // Catch:{ all -> 0x0094 }
            if (r2 == 0) goto L_0x000b
            r8 = r1
            goto L_0x001e
        L_0x000b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0094 }
            java.lang.String r3 = "_pc = '"
            r2.<init>(r3)     // Catch:{ all -> 0x0094 }
            r2.append(r8)     // Catch:{ all -> 0x0094 }
            java.lang.String r8 = "'"
            r2.append(r8)     // Catch:{ all -> 0x0094 }
            java.lang.String r8 = r2.toString()     // Catch:{ all -> 0x0094 }
        L_0x001e:
            com.tencent.bugly.proguard.w r2 = com.tencent.bugly.proguard.C2365w.m1033a()     // Catch:{ all -> 0x0094 }
            android.database.Cursor r8 = r2.mo28165a((java.lang.String) r0, (java.lang.String[]) r1, (java.lang.String) r8)     // Catch:{ all -> 0x0094 }
            if (r8 != 0) goto L_0x002e
            if (r8 == 0) goto L_0x002d
            r8.close()
        L_0x002d:
            return r1
        L_0x002e:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0092 }
            r2.<init>()     // Catch:{ all -> 0x0092 }
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ all -> 0x0092 }
            r3.<init>()     // Catch:{ all -> 0x0092 }
        L_0x0038:
            boolean r4 = r8.moveToNext()     // Catch:{ all -> 0x0092 }
            r5 = 0
            if (r4 == 0) goto L_0x0064
            com.tencent.bugly.crashreport.biz.UserInfoBean r4 = m972a((android.database.Cursor) r8)     // Catch:{ all -> 0x0092 }
            if (r4 == 0) goto L_0x0049
            r3.add(r4)     // Catch:{ all -> 0x0092 }
            goto L_0x0038
        L_0x0049:
            java.lang.String r4 = "_id"
            int r4 = r8.getColumnIndex(r4)     // Catch:{ all -> 0x005c }
            long r6 = r8.getLong(r4)     // Catch:{ all -> 0x005c }
            java.lang.String r4 = " or _id = "
            r2.append(r4)     // Catch:{ all -> 0x005c }
            r2.append(r6)     // Catch:{ all -> 0x005c }
            goto L_0x0038
        L_0x005c:
            java.lang.String r4 = "[Database] unknown id."
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0092 }
            com.tencent.bugly.proguard.C2265al.m610d(r4, r5)     // Catch:{ all -> 0x0092 }
            goto L_0x0038
        L_0x0064:
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0092 }
            int r4 = r2.length()     // Catch:{ all -> 0x0092 }
            if (r4 <= 0) goto L_0x008c
            r4 = 4
            java.lang.String r2 = r2.substring(r4)     // Catch:{ all -> 0x0092 }
            com.tencent.bugly.proguard.w r4 = com.tencent.bugly.proguard.C2365w.m1033a()     // Catch:{ all -> 0x0092 }
            int r2 = r4.mo28163a((java.lang.String) r0, (java.lang.String) r2)     // Catch:{ all -> 0x0092 }
            java.lang.String r4 = "[Database] deleted %s error data %d"
            r6 = 2
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x0092 }
            r6[r5] = r0     // Catch:{ all -> 0x0092 }
            r0 = 1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x0092 }
            r6[r0] = r2     // Catch:{ all -> 0x0092 }
            com.tencent.bugly.proguard.C2265al.m610d(r4, r6)     // Catch:{ all -> 0x0092 }
        L_0x008c:
            if (r8 == 0) goto L_0x0091
            r8.close()
        L_0x0091:
            return r3
        L_0x0092:
            r0 = move-exception
            goto L_0x0096
        L_0x0094:
            r0 = move-exception
            r8 = r1
        L_0x0096:
            boolean r2 = com.tencent.bugly.proguard.C2265al.m605a(r0)     // Catch:{ all -> 0x00a5 }
            if (r2 != 0) goto L_0x009f
            r0.printStackTrace()     // Catch:{ all -> 0x00a5 }
        L_0x009f:
            if (r8 == 0) goto L_0x00a4
            r8.close()
        L_0x00a4:
            return r1
        L_0x00a5:
            r0 = move-exception
            if (r8 == 0) goto L_0x00ab
            r8.close()
        L_0x00ab:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2351r.m973a(java.lang.String):java.util.List");
    }

    /* renamed from: b */
    private static void m984b(List<UserInfoBean> list) {
        if (list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < list.size() && i < 50) {
                sb.append(" or _id = ");
                sb.append(list.get(i).f1291a);
                i++;
            }
            String sb2 = sb.toString();
            if (sb2.length() > 0) {
                sb2 = sb2.substring(4);
            }
            sb.setLength(0);
            try {
                C2265al.m609c("[Database] deleted %s data %d", "t_ui", Integer.valueOf(C2365w.m1033a().mo28163a("t_ui", sb2)));
            } catch (Throwable th) {
                if (!C2265al.m605a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    /* renamed from: b */
    private static ContentValues m983b(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (userInfoBean.f1291a > 0) {
                contentValues.put("_id", Long.valueOf(userInfoBean.f1291a));
            }
            contentValues.put("_tm", Long.valueOf(userInfoBean.f1295e));
            contentValues.put("_ut", Long.valueOf(userInfoBean.f1296f));
            contentValues.put("_tp", Integer.valueOf(userInfoBean.f1292b));
            contentValues.put("_pc", userInfoBean.f1293c);
            contentValues.put("_dt", C2273ap.m658a((Parcelable) userInfoBean));
            return contentValues;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* renamed from: a */
    private static UserInfoBean m972a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            UserInfoBean userInfoBean = (UserInfoBean) C2273ap.m639a(blob, UserInfoBean.CREATOR);
            if (userInfoBean != null) {
                userInfoBean.f1291a = j;
            }
            return userInfoBean;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0029 A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x002b A[SYNTHETIC, Splitter:B:19:0x002b] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006c  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void m980a(boolean r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            boolean r0 = r7.f1919d     // Catch:{ all -> 0x0092 }
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0009
        L_0x0007:
            r0 = 0
            goto L_0x0027
        L_0x0009:
            com.tencent.bugly.proguard.ai r0 = com.tencent.bugly.proguard.C2259ai.m569a()     // Catch:{ all -> 0x0092 }
            if (r0 != 0) goto L_0x0010
            goto L_0x0007
        L_0x0010:
            com.tencent.bugly.proguard.ac r3 = com.tencent.bugly.proguard.C2248ac.m533a()     // Catch:{ all -> 0x0092 }
            if (r3 != 0) goto L_0x0017
            goto L_0x0007
        L_0x0017:
            boolean r3 = r3.mo27995b()     // Catch:{ all -> 0x0092 }
            if (r3 == 0) goto L_0x0026
            r3 = 1001(0x3e9, float:1.403E-42)
            boolean r0 = r0.mo28011b((int) r3)     // Catch:{ all -> 0x0092 }
            if (r0 != 0) goto L_0x0026
            goto L_0x0007
        L_0x0026:
            r0 = 1
        L_0x0027:
            if (r0 != 0) goto L_0x002b
            monitor-exit(r7)
            return
        L_0x002b:
            android.content.Context r0 = r7.f1916a     // Catch:{ all -> 0x0092 }
            com.tencent.bugly.proguard.aa r0 = com.tencent.bugly.proguard.C2231aa.m457a((android.content.Context) r0)     // Catch:{ all -> 0x0092 }
            java.lang.String r0 = r0.f1472d     // Catch:{ all -> 0x0092 }
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ all -> 0x0092 }
            r3.<init>()     // Catch:{ all -> 0x0092 }
            java.util.List r0 = m973a((java.lang.String) r0)     // Catch:{ all -> 0x0092 }
            if (r0 == 0) goto L_0x005b
            m978a((java.util.List<com.tencent.bugly.crashreport.biz.UserInfoBean>) r0, (java.util.List<com.tencent.bugly.crashreport.biz.UserInfoBean>) r3)     // Catch:{ all -> 0x0092 }
            m985b(r0, r3)     // Catch:{ all -> 0x0092 }
            int r4 = m971a((java.util.List<com.tencent.bugly.crashreport.biz.UserInfoBean>) r0)     // Catch:{ all -> 0x0092 }
            r5 = 15
            if (r4 <= r5) goto L_0x0060
            java.lang.String r5 = "[UserInfo] Upload user info too many times in 10 min: %d"
            java.lang.Object[] r6 = new java.lang.Object[r1]     // Catch:{ all -> 0x0092 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x0092 }
            r6[r2] = r4     // Catch:{ all -> 0x0092 }
            com.tencent.bugly.proguard.C2265al.m610d(r5, r6)     // Catch:{ all -> 0x0092 }
            r4 = 0
            goto L_0x0061
        L_0x005b:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0092 }
            r0.<init>()     // Catch:{ all -> 0x0092 }
        L_0x0060:
            r4 = 1
        L_0x0061:
            int r5 = r3.size()     // Catch:{ all -> 0x0092 }
            if (r5 <= 0) goto L_0x006a
            m984b((java.util.List<com.tencent.bugly.crashreport.biz.UserInfoBean>) r3)     // Catch:{ all -> 0x0092 }
        L_0x006a:
            if (r4 == 0) goto L_0x0089
            int r3 = r0.size()     // Catch:{ all -> 0x0092 }
            if (r3 != 0) goto L_0x0073
            goto L_0x0089
        L_0x0073:
            java.lang.String r3 = "[UserInfo] Upload user info(size: %d)"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0092 }
            int r4 = r0.size()     // Catch:{ all -> 0x0092 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x0092 }
            r1[r2] = r4     // Catch:{ all -> 0x0092 }
            com.tencent.bugly.proguard.C2265al.m609c(r3, r1)     // Catch:{ all -> 0x0092 }
            r7.m979a((java.util.List<com.tencent.bugly.crashreport.biz.UserInfoBean>) r0, (boolean) r8)     // Catch:{ all -> 0x0092 }
            monitor-exit(r7)
            return
        L_0x0089:
            java.lang.String r8 = "[UserInfo] There is no user info in local database."
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ all -> 0x0092 }
            com.tencent.bugly.proguard.C2265al.m609c(r8, r0)     // Catch:{ all -> 0x0092 }
            monitor-exit(r7)
            return
        L_0x0092:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2351r.m980a(boolean):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0064 A[Catch:{ all -> 0x006e, all -> 0x0084 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0066 A[Catch:{ all -> 0x006e, all -> 0x0084 }] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean m986b(boolean r15) {
        /*
            r14 = this;
            boolean r0 = f1915e
            r1 = 1
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            java.io.File r0 = new java.io.File
            android.content.Context r2 = r14.f1916a
            java.io.File r2 = r2.getFilesDir()
            java.lang.String r3 = "bugly_last_us_up_tm"
            r0.<init>(r2, r3)
            long r2 = java.lang.System.currentTimeMillis()
            r4 = 1024(0x400, double:5.06E-321)
            r6 = 0
            if (r15 == 0) goto L_0x0024
            java.lang.String r15 = java.lang.String.valueOf(r2)
            com.tencent.bugly.proguard.C2266am.m615a((java.io.File) r0, (java.lang.String) r15, (long) r4, (boolean) r6)
            return r1
        L_0x0024:
            boolean r15 = r0.exists()
            if (r15 != 0) goto L_0x0032
            java.lang.String r15 = java.lang.String.valueOf(r2)
            com.tencent.bugly.proguard.C2266am.m615a((java.io.File) r0, (java.lang.String) r15, (long) r4, (boolean) r6)
            goto L_0x0095
        L_0x0032:
            java.io.BufferedReader r15 = com.tencent.bugly.proguard.C2273ap.m637a((java.io.File) r0)
            if (r15 == 0) goto L_0x0090
            java.lang.String r7 = r15.readLine()     // Catch:{ all -> 0x006e }
            java.lang.String r7 = r7.trim()     // Catch:{ all -> 0x006e }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x006e }
            long r7 = r7.longValue()     // Catch:{ all -> 0x006e }
            int r9 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r9 < 0) goto L_0x0058
            long r9 = r2 - r7
            r11 = 86400000(0x5265c00, double:4.2687272E-316)
            int r13 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r13 <= 0) goto L_0x0056
            goto L_0x0058
        L_0x0056:
            r9 = 1
            goto L_0x0059
        L_0x0058:
            r9 = 0
        L_0x0059:
            if (r9 == 0) goto L_0x0066
            long r7 = r2 - r7
            r9 = 300000(0x493e0, double:1.482197E-318)
            int r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r11 >= 0) goto L_0x0066
            r1 = 0
            goto L_0x0090
        L_0x0066:
            java.lang.String r7 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x006e }
            com.tencent.bugly.proguard.C2266am.m615a((java.io.File) r0, (java.lang.String) r7, (long) r4, (boolean) r6)     // Catch:{ all -> 0x006e }
            goto L_0x0090
        L_0x006e:
            r7 = move-exception
            com.tencent.bugly.proguard.C2265al.m608b(r7)     // Catch:{ all -> 0x0084 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x0084 }
            com.tencent.bugly.proguard.C2266am.m615a((java.io.File) r0, (java.lang.String) r2, (long) r4, (boolean) r6)     // Catch:{ all -> 0x0084 }
            if (r15 == 0) goto L_0x0095
            r15.close()     // Catch:{ Exception -> 0x007f }
            goto L_0x0095
        L_0x007f:
            r15 = move-exception
            com.tencent.bugly.proguard.C2265al.m605a(r15)
            goto L_0x0095
        L_0x0084:
            r0 = move-exception
            if (r15 == 0) goto L_0x008f
            r15.close()     // Catch:{ Exception -> 0x008b }
            goto L_0x008f
        L_0x008b:
            r15 = move-exception
            com.tencent.bugly.proguard.C2265al.m605a(r15)
        L_0x008f:
            throw r0
        L_0x0090:
            if (r15 == 0) goto L_0x0095
            r15.close()     // Catch:{ Exception -> 0x007f }
        L_0x0095:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2351r.m986b(boolean):boolean");
    }

    /* renamed from: a */
    static /* synthetic */ void m974a(UserInfoBean userInfoBean) {
        C2231aa b;
        if (userInfoBean != null && (b = C2231aa.m459b()) != null) {
            userInfoBean.f1300j = b.mo27966d();
        }
    }
}
