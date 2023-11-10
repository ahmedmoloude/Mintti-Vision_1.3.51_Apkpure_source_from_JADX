package com.tencent.bugly.proguard;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.u */
/* compiled from: BUGLY */
public final class C2361u {

    /* renamed from: a */
    public static final long f1952a = System.currentTimeMillis();

    /* renamed from: b */
    private static C2361u f1953b;

    /* renamed from: c */
    private Context f1954c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public String f1955d = C2231aa.m459b().f1472d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public Map<Integer, Map<String, C2360t>> f1956e = new HashMap();
    /* access modifiers changed from: private */

    /* renamed from: f */
    public SharedPreferences f1957f;

    private C2361u(Context context) {
        this.f1954c = context;
        this.f1957f = context.getSharedPreferences("crashrecord", 0);
    }

    /* renamed from: a */
    public static synchronized C2361u m1014a(Context context) {
        C2361u uVar;
        synchronized (C2361u.class) {
            if (f1953b == null) {
                f1953b = new C2361u(context);
            }
            uVar = f1953b;
        }
        return uVar;
    }

    /* renamed from: a */
    public static synchronized C2361u m1013a() {
        C2361u uVar;
        synchronized (C2361u.class) {
            uVar = f1953b;
        }
        return uVar;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0077, code lost:
        return true;
     */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean m1024c(int r13) {
        /*
            r12 = this;
            monitor-enter(r12)
            r0 = 0
            java.util.List r1 = r12.m1025d(r13)     // Catch:{ Exception -> 0x0082 }
            if (r1 != 0) goto L_0x000a
            monitor-exit(r12)
            return r0
        L_0x000a:
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0082 }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ Exception -> 0x0082 }
            r4.<init>()     // Catch:{ Exception -> 0x0082 }
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Exception -> 0x0082 }
            r5.<init>()     // Catch:{ Exception -> 0x0082 }
            java.util.Iterator r6 = r1.iterator()     // Catch:{ Exception -> 0x0082 }
        L_0x001c:
            boolean r7 = r6.hasNext()     // Catch:{ Exception -> 0x0082 }
            r8 = 86400000(0x5265c00, double:4.2687272E-316)
            if (r7 == 0) goto L_0x004b
            java.lang.Object r7 = r6.next()     // Catch:{ Exception -> 0x0082 }
            com.tencent.bugly.proguard.t r7 = (com.tencent.bugly.proguard.C2360t) r7     // Catch:{ Exception -> 0x0082 }
            java.lang.String r10 = r7.f1946b     // Catch:{ Exception -> 0x0082 }
            if (r10 == 0) goto L_0x0040
            java.lang.String r10 = r7.f1946b     // Catch:{ Exception -> 0x0082 }
            java.lang.String r11 = r12.f1955d     // Catch:{ Exception -> 0x0082 }
            boolean r10 = r10.equalsIgnoreCase(r11)     // Catch:{ Exception -> 0x0082 }
            if (r10 == 0) goto L_0x0040
            int r10 = r7.f1948d     // Catch:{ Exception -> 0x0082 }
            if (r10 <= 0) goto L_0x0040
            r4.add(r7)     // Catch:{ Exception -> 0x0082 }
        L_0x0040:
            long r10 = r7.f1947c     // Catch:{ Exception -> 0x0082 }
            long r10 = r10 + r8
            int r8 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r8 >= 0) goto L_0x001c
            r5.add(r7)     // Catch:{ Exception -> 0x0082 }
            goto L_0x001c
        L_0x004b:
            java.util.Collections.sort(r4)     // Catch:{ Exception -> 0x0082 }
            int r6 = r4.size()     // Catch:{ Exception -> 0x0082 }
            r7 = 2
            if (r6 < r7) goto L_0x0078
            int r5 = r4.size()     // Catch:{ Exception -> 0x0082 }
            r6 = 1
            if (r5 <= 0) goto L_0x0076
            int r5 = r4.size()     // Catch:{ Exception -> 0x0082 }
            int r5 = r5 - r6
            java.lang.Object r4 = r4.get(r5)     // Catch:{ Exception -> 0x0082 }
            com.tencent.bugly.proguard.t r4 = (com.tencent.bugly.proguard.C2360t) r4     // Catch:{ Exception -> 0x0082 }
            long r4 = r4.f1947c     // Catch:{ Exception -> 0x0082 }
            long r4 = r4 + r8
            int r7 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r7 >= 0) goto L_0x0076
            r1.clear()     // Catch:{ Exception -> 0x0082 }
            r12.m1017a((int) r13, r1)     // Catch:{ Exception -> 0x0082 }
            monitor-exit(r12)
            return r0
        L_0x0076:
            monitor-exit(r12)
            return r6
        L_0x0078:
            r1.removeAll(r5)     // Catch:{ Exception -> 0x0082 }
            r12.m1017a((int) r13, r1)     // Catch:{ Exception -> 0x0082 }
            monitor-exit(r12)
            return r0
        L_0x0080:
            r13 = move-exception
            goto L_0x008b
        L_0x0082:
            java.lang.String r13 = "isFrequentCrash failed"
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x0080 }
            com.tencent.bugly.proguard.C2265al.m611e(r13, r1)     // Catch:{ all -> 0x0080 }
            monitor-exit(r12)
            return r0
        L_0x008b:
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2361u.m1024c(int):boolean");
    }

    /* renamed from: a */
    public final void mo28159a(final int i) {
        C2263ak.m595a().mo28017a(new Runnable() {

            /* renamed from: a */
            final /* synthetic */ int f1958a = 1004;

            public final void run() {
                C2360t tVar;
                try {
                    if (!TextUtils.isEmpty(C2361u.this.f1955d)) {
                        List<C2360t> a = C2361u.this.m1025d(this.f1958a);
                        if (a == null) {
                            a = new ArrayList<>();
                        }
                        if (C2361u.this.f1956e.get(Integer.valueOf(this.f1958a)) == null) {
                            C2361u.this.f1956e.put(Integer.valueOf(this.f1958a), new HashMap());
                        }
                        if (((Map) C2361u.this.f1956e.get(Integer.valueOf(this.f1958a))).get(C2361u.this.f1955d) == null) {
                            tVar = new C2360t();
                            tVar.f1945a = (long) this.f1958a;
                            tVar.f1951g = C2361u.f1952a;
                            tVar.f1946b = C2361u.this.f1955d;
                            tVar.f1950f = C2231aa.m459b().f1483o;
                            tVar.f1949e = C2231aa.m459b().f1476h;
                            tVar.f1947c = System.currentTimeMillis();
                            tVar.f1948d = i;
                            ((Map) C2361u.this.f1956e.get(Integer.valueOf(this.f1958a))).put(C2361u.this.f1955d, tVar);
                        } else {
                            tVar = (C2360t) ((Map) C2361u.this.f1956e.get(Integer.valueOf(this.f1958a))).get(C2361u.this.f1955d);
                            tVar.f1948d = i;
                        }
                        ArrayList arrayList = new ArrayList();
                        boolean z = false;
                        for (C2360t tVar2 : a) {
                            if (C2361u.m1019a(tVar2, tVar)) {
                                z = true;
                                tVar2.f1948d = tVar.f1948d;
                            }
                            if (C2361u.m1021b(tVar2, tVar)) {
                                arrayList.add(tVar2);
                            }
                        }
                        a.removeAll(arrayList);
                        if (!z) {
                            a.add(tVar);
                        }
                        C2361u.this.m1017a(this.f1958a, a);
                    }
                } catch (Exception unused) {
                    C2265al.m611e("saveCrashRecord failed", new Object[0]);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
        if (r6 != null) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x004a, code lost:
        if (r6 != null) goto L_0x003e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0050 A[SYNTHETIC, Splitter:B:35:0x0050] */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized <T extends java.util.List<?>> T m1025d(int r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0056 }
            android.content.Context r3 = r5.f1954c     // Catch:{ Exception -> 0x0056 }
            java.lang.String r4 = "crashrecord"
            java.io.File r3 = r3.getDir(r4, r1)     // Catch:{ Exception -> 0x0056 }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ Exception -> 0x0056 }
            r2.<init>(r3, r6)     // Catch:{ Exception -> 0x0056 }
            boolean r6 = r2.exists()     // Catch:{ Exception -> 0x0056 }
            if (r6 != 0) goto L_0x001c
            monitor-exit(r5)
            return r0
        L_0x001c:
            java.io.ObjectInputStream r6 = new java.io.ObjectInputStream     // Catch:{ IOException -> 0x0042, ClassNotFoundException -> 0x0034, all -> 0x0031 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0042, ClassNotFoundException -> 0x0034, all -> 0x0031 }
            r3.<init>(r2)     // Catch:{ IOException -> 0x0042, ClassNotFoundException -> 0x0034, all -> 0x0031 }
            r6.<init>(r3)     // Catch:{ IOException -> 0x0042, ClassNotFoundException -> 0x0034, all -> 0x0031 }
            java.lang.Object r2 = r6.readObject()     // Catch:{ IOException -> 0x0043, ClassNotFoundException -> 0x0035 }
            java.util.List r2 = (java.util.List) r2     // Catch:{ IOException -> 0x0043, ClassNotFoundException -> 0x0035 }
            r6.close()     // Catch:{ Exception -> 0x0056 }
            monitor-exit(r5)
            return r2
        L_0x0031:
            r2 = move-exception
            r6 = r0
            goto L_0x004e
        L_0x0034:
            r6 = r0
        L_0x0035:
            java.lang.String r2 = "get object error"
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ all -> 0x004d }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r2, (java.lang.Object[]) r3)     // Catch:{ all -> 0x004d }
            if (r6 == 0) goto L_0x005d
        L_0x003e:
            r6.close()     // Catch:{ Exception -> 0x0056 }
            goto L_0x005d
        L_0x0042:
            r6 = r0
        L_0x0043:
            java.lang.String r2 = "open record file error"
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ all -> 0x004d }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r2, (java.lang.Object[]) r3)     // Catch:{ all -> 0x004d }
            if (r6 == 0) goto L_0x005d
            goto L_0x003e
        L_0x004d:
            r2 = move-exception
        L_0x004e:
            if (r6 == 0) goto L_0x0053
            r6.close()     // Catch:{ Exception -> 0x0056 }
        L_0x0053:
            throw r2     // Catch:{ Exception -> 0x0056 }
        L_0x0054:
            r6 = move-exception
            goto L_0x005f
        L_0x0056:
            java.lang.String r6 = "readCrashRecord error"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0054 }
            com.tencent.bugly.proguard.C2265al.m611e(r6, r1)     // Catch:{ all -> 0x0054 }
        L_0x005d:
            monitor-exit(r5)
            return r0
        L_0x005f:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2361u.m1025d(int):java.util.List");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003f A[SYNTHETIC, Splitter:B:24:0x003f] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0044 A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0049 A[SYNTHETIC, Splitter:B:32:0x0049] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized <T extends java.util.List<?>> void m1017a(int r5, T r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r6 != 0) goto L_0x0005
            monitor-exit(r4)
            return
        L_0x0005:
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x004f }
            android.content.Context r2 = r4.f1954c     // Catch:{ Exception -> 0x004f }
            java.lang.String r3 = "crashrecord"
            java.io.File r2 = r2.getDir(r3, r0)     // Catch:{ Exception -> 0x004f }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Exception -> 0x004f }
            r1.<init>(r2, r5)     // Catch:{ Exception -> 0x004f }
            r5 = 0
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x0030, all -> 0x002c }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0030, all -> 0x002c }
            r3.<init>(r1)     // Catch:{ IOException -> 0x0030, all -> 0x002c }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0030, all -> 0x002c }
            r2.writeObject(r6)     // Catch:{ IOException -> 0x002a }
            r2.close()     // Catch:{ Exception -> 0x004f }
            monitor-exit(r4)
            return
        L_0x002a:
            r5 = move-exception
            goto L_0x0033
        L_0x002c:
            r6 = move-exception
            r2 = r5
            r5 = r6
            goto L_0x0047
        L_0x0030:
            r6 = move-exception
            r2 = r5
            r5 = r6
        L_0x0033:
            r5.printStackTrace()     // Catch:{ all -> 0x0046 }
            java.lang.String r5 = "open record file error"
            java.lang.Object[] r6 = new java.lang.Object[r0]     // Catch:{ all -> 0x0046 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r5, (java.lang.Object[]) r6)     // Catch:{ all -> 0x0046 }
            if (r2 == 0) goto L_0x0044
            r2.close()     // Catch:{ Exception -> 0x004f }
            monitor-exit(r4)
            return
        L_0x0044:
            monitor-exit(r4)
            return
        L_0x0046:
            r5 = move-exception
        L_0x0047:
            if (r2 == 0) goto L_0x004c
            r2.close()     // Catch:{ Exception -> 0x004f }
        L_0x004c:
            throw r5     // Catch:{ Exception -> 0x004f }
        L_0x004d:
            r5 = move-exception
            goto L_0x0058
        L_0x004f:
            java.lang.String r5 = "writeCrashRecord error"
            java.lang.Object[] r6 = new java.lang.Object[r0]     // Catch:{ all -> 0x004d }
            com.tencent.bugly.proguard.C2265al.m611e(r5, r6)     // Catch:{ all -> 0x004d }
            monitor-exit(r4)
            return
        L_0x0058:
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2361u.m1017a(int, java.util.List):void");
    }

    /* renamed from: b */
    public final synchronized boolean mo28160b(final int i) {
        boolean z;
        z = true;
        try {
            SharedPreferences sharedPreferences = this.f1957f;
            z = sharedPreferences.getBoolean(i + "_" + this.f1955d, true);
            C2263ak.m595a().mo28017a(new Runnable() {
                public final void run() {
                    boolean b = C2361u.this.m1024c(i);
                    SharedPreferences.Editor edit = C2361u.this.f1957f.edit();
                    edit.putBoolean(i + "_" + C2361u.this.f1955d, !b).commit();
                }
            });
        } catch (Exception unused) {
            C2265al.m611e("canInit error", new Object[0]);
            return z;
        }
        return z;
    }

    /* renamed from: a */
    static /* synthetic */ boolean m1019a(C2360t tVar, C2360t tVar2) {
        return tVar.f1951g == tVar2.f1951g && tVar.f1946b != null && tVar.f1946b.equalsIgnoreCase(tVar2.f1946b);
    }

    /* renamed from: b */
    static /* synthetic */ boolean m1021b(C2360t tVar, C2360t tVar2) {
        if (tVar.f1949e == null || tVar.f1949e.equalsIgnoreCase(tVar2.f1949e)) {
            return (tVar.f1950f != null && !tVar.f1950f.equalsIgnoreCase(tVar2.f1950f)) || tVar.f1948d <= 0;
        }
        return true;
    }
}
