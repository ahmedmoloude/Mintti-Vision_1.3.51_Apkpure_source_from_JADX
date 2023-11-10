package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.w */
/* compiled from: BUGLY */
public final class C2365w {

    /* renamed from: a */
    public static boolean f1963a = false;

    /* renamed from: b */
    private static C2365w f1964b;

    /* renamed from: c */
    private static C2367x f1965c;

    private C2365w(Context context, List<C2348o> list) {
        f1965c = new C2367x(context, list);
    }

    /* renamed from: a */
    public static synchronized C2365w m1034a(Context context, List<C2348o> list) {
        C2365w wVar;
        synchronized (C2365w.class) {
            if (f1964b == null) {
                f1964b = new C2365w(context, list);
            }
            wVar = f1964b;
        }
        return wVar;
    }

    /* renamed from: a */
    public static synchronized C2365w m1033a() {
        C2365w wVar;
        synchronized (C2365w.class) {
            wVar = f1964b;
        }
        return wVar;
    }

    /* renamed from: a */
    public final Cursor mo28165a(String str, String[] strArr, String str2) {
        return mo28166a(str, strArr, str2, (String) null, (String) null);
    }

    /* renamed from: a */
    public final Cursor mo28166a(String str, String[] strArr, String str2, String str3, String str4) {
        return m1032a(false, str, strArr, str2, (String[]) null, (String) null, (String) null, str3, str4, (C2364v) null);
    }

    /* renamed from: a */
    public final int mo28163a(String str, String str2) {
        return m1029a(str, str2, (String[]) null, (C2364v) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0039, code lost:
        if (r2 != null) goto L_0x003b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003b, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0054, code lost:
        if (r2 != null) goto L_0x003b;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized long mo28164a(java.lang.String r10, android.content.ContentValues r11, com.tencent.bugly.proguard.C2364v r12) {
        /*
            r9 = this;
            monitor-enter(r9)
            r0 = -1
            r2 = 0
            com.tencent.bugly.proguard.x r3 = f1965c     // Catch:{ all -> 0x0041 }
            android.database.sqlite.SQLiteDatabase r2 = r3.getWritableDatabase()     // Catch:{ all -> 0x0041 }
            if (r2 == 0) goto L_0x0030
            if (r11 == 0) goto L_0x0030
            java.lang.String r3 = "_id"
            long r3 = r2.replace(r10, r3, r11)     // Catch:{ all -> 0x0041 }
            r5 = 0
            r11 = 0
            r7 = 1
            int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 < 0) goto L_0x0026
            java.lang.String r5 = "[Database] insert %s success."
            java.lang.Object[] r6 = new java.lang.Object[r7]     // Catch:{ all -> 0x0041 }
            r6[r11] = r10     // Catch:{ all -> 0x0041 }
            com.tencent.bugly.proguard.C2265al.m609c(r5, r6)     // Catch:{ all -> 0x0041 }
            goto L_0x002f
        L_0x0026:
            java.lang.String r5 = "[Database] replace %s error."
            java.lang.Object[] r6 = new java.lang.Object[r7]     // Catch:{ all -> 0x0041 }
            r6[r11] = r10     // Catch:{ all -> 0x0041 }
            com.tencent.bugly.proguard.C2265al.m610d(r5, r6)     // Catch:{ all -> 0x0041 }
        L_0x002f:
            r0 = r3
        L_0x0030:
            if (r12 == 0) goto L_0x0035
            java.lang.Long.valueOf(r0)     // Catch:{ all -> 0x003f }
        L_0x0035:
            boolean r10 = f1963a     // Catch:{ all -> 0x003f }
            if (r10 == 0) goto L_0x0057
            if (r2 == 0) goto L_0x0057
        L_0x003b:
            r2.close()     // Catch:{ all -> 0x003f }
            goto L_0x0057
        L_0x003f:
            r10 = move-exception
            goto L_0x0069
        L_0x0041:
            r10 = move-exception
            boolean r11 = com.tencent.bugly.proguard.C2265al.m605a(r10)     // Catch:{ all -> 0x0059 }
            if (r11 != 0) goto L_0x004b
            r10.printStackTrace()     // Catch:{ all -> 0x0059 }
        L_0x004b:
            if (r12 == 0) goto L_0x0050
            java.lang.Long.valueOf(r0)     // Catch:{ all -> 0x003f }
        L_0x0050:
            boolean r10 = f1963a     // Catch:{ all -> 0x003f }
            if (r10 == 0) goto L_0x0057
            if (r2 == 0) goto L_0x0057
            goto L_0x003b
        L_0x0057:
            monitor-exit(r9)
            return r0
        L_0x0059:
            r10 = move-exception
            if (r12 == 0) goto L_0x005f
            java.lang.Long.valueOf(r0)     // Catch:{ all -> 0x003f }
        L_0x005f:
            boolean r11 = f1963a     // Catch:{ all -> 0x003f }
            if (r11 == 0) goto L_0x0068
            if (r2 == 0) goto L_0x0068
            r2.close()     // Catch:{ all -> 0x003f }
        L_0x0068:
            throw r10     // Catch:{ all -> 0x003f }
        L_0x0069:
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2365w.mo28164a(java.lang.String, android.content.ContentValues, com.tencent.bugly.proguard.v):long");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002f, code lost:
        throw r0;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized android.database.Cursor m1032a(boolean r13, java.lang.String r14, java.lang.String[] r15, java.lang.String r16, java.lang.String[] r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, com.tencent.bugly.proguard.C2364v r22) {
        /*
            r12 = this;
            monitor-enter(r12)
            r1 = 0
            com.tencent.bugly.proguard.x r0 = f1965c     // Catch:{ all -> 0x001e }
            android.database.sqlite.SQLiteDatabase r2 = r0.getWritableDatabase()     // Catch:{ all -> 0x001e }
            if (r2 == 0) goto L_0x0028
            r3 = r13
            r4 = r14
            r5 = r15
            r6 = r16
            r7 = r17
            r8 = r18
            r9 = r19
            r10 = r20
            r11 = r21
            android.database.Cursor r1 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ all -> 0x001e }
            goto L_0x0028
        L_0x001e:
            r0 = move-exception
            boolean r2 = com.tencent.bugly.proguard.C2265al.m605a(r0)     // Catch:{ all -> 0x002a }
            if (r2 != 0) goto L_0x0028
            r0.printStackTrace()     // Catch:{ all -> 0x002a }
        L_0x0028:
            monitor-exit(r12)
            return r1
        L_0x002a:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x002c }
        L_0x002c:
            r0 = move-exception
            r1 = r0
            monitor-exit(r12)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2365w.m1032a(boolean, java.lang.String, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.tencent.bugly.proguard.v):android.database.Cursor");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        if (r1 != null) goto L_0x001a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0033, code lost:
        if (r1 != null) goto L_0x001a;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int m1029a(java.lang.String r4, java.lang.String r5, java.lang.String[] r6, com.tencent.bugly.proguard.C2364v r7) {
        /*
            r3 = this;
            monitor-enter(r3)
            r0 = 0
            r1 = 0
            com.tencent.bugly.proguard.x r2 = f1965c     // Catch:{ all -> 0x0020 }
            android.database.sqlite.SQLiteDatabase r1 = r2.getWritableDatabase()     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x000f
            int r0 = r1.delete(r4, r5, r6)     // Catch:{ all -> 0x0020 }
        L_0x000f:
            if (r7 == 0) goto L_0x0014
            java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x001e }
        L_0x0014:
            boolean r4 = f1963a     // Catch:{ all -> 0x001e }
            if (r4 == 0) goto L_0x0036
            if (r1 == 0) goto L_0x0036
        L_0x001a:
            r1.close()     // Catch:{ all -> 0x001e }
            goto L_0x0036
        L_0x001e:
            r4 = move-exception
            goto L_0x0048
        L_0x0020:
            r4 = move-exception
            boolean r5 = com.tencent.bugly.proguard.C2265al.m605a(r4)     // Catch:{ all -> 0x0038 }
            if (r5 != 0) goto L_0x002a
            r4.printStackTrace()     // Catch:{ all -> 0x0038 }
        L_0x002a:
            if (r7 == 0) goto L_0x002f
            java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x001e }
        L_0x002f:
            boolean r4 = f1963a     // Catch:{ all -> 0x001e }
            if (r4 == 0) goto L_0x0036
            if (r1 == 0) goto L_0x0036
            goto L_0x001a
        L_0x0036:
            monitor-exit(r3)
            return r0
        L_0x0038:
            r4 = move-exception
            if (r7 == 0) goto L_0x003e
            java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x001e }
        L_0x003e:
            boolean r5 = f1963a     // Catch:{ all -> 0x001e }
            if (r5 == 0) goto L_0x0047
            if (r1 == 0) goto L_0x0047
            r1.close()     // Catch:{ all -> 0x001e }
        L_0x0047:
            throw r4     // Catch:{ all -> 0x001e }
        L_0x0048:
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2365w.m1029a(java.lang.String, java.lang.String, java.lang.String[], com.tencent.bugly.proguard.v):int");
    }

    /* renamed from: a */
    public final boolean mo28170a(int i, String str, byte[] bArr, boolean z) {
        if (z) {
            return m1038a(i, str, bArr, (C2364v) null);
        }
        C2366a aVar = new C2366a();
        aVar.mo28173a(i, str, bArr);
        C2263ak.m595a().mo28017a(aVar);
        return true;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public boolean m1038a(int i, String str, byte[] bArr, C2364v vVar) {
        try {
            C2368y yVar = new C2368y();
            yVar.f1988a = (long) i;
            yVar.f1993f = str;
            yVar.f1992e = System.currentTimeMillis();
            yVar.f1994g = bArr;
            boolean b = m1042b(yVar);
            if (vVar == null) {
                return b;
            }
            Boolean.valueOf(b);
            return b;
        } catch (Throwable th) {
            if (vVar != null) {
                Boolean bool = Boolean.FALSE;
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.Map<java.lang.String, byte[]> mo28168a(int r4, com.tencent.bugly.proguard.C2364v r5) {
        /*
            r3 = this;
            r0 = 0
            java.util.List r4 = r3.m1044c((int) r4)     // Catch:{ all -> 0x002b }
            if (r4 == 0) goto L_0x0035
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x002b }
            r1.<init>()     // Catch:{ all -> 0x002b }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ all -> 0x0028 }
        L_0x0010:
            boolean r0 = r4.hasNext()     // Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x0026
            java.lang.Object r0 = r4.next()     // Catch:{ all -> 0x0028 }
            com.tencent.bugly.proguard.y r0 = (com.tencent.bugly.proguard.C2368y) r0     // Catch:{ all -> 0x0028 }
            byte[] r2 = r0.f1994g     // Catch:{ all -> 0x0028 }
            if (r2 == 0) goto L_0x0010
            java.lang.String r0 = r0.f1993f     // Catch:{ all -> 0x0028 }
            r1.put(r0, r2)     // Catch:{ all -> 0x0028 }
            goto L_0x0010
        L_0x0026:
            r0 = r1
            goto L_0x0035
        L_0x0028:
            r4 = move-exception
            r0 = r1
            goto L_0x002c
        L_0x002b:
            r4 = move-exception
        L_0x002c:
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r4)     // Catch:{ all -> 0x0036 }
            if (r1 != 0) goto L_0x0035
            r4.printStackTrace()     // Catch:{ all -> 0x0036 }
        L_0x0035:
            return r0
        L_0x0036:
            r4 = move-exception
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2365w.mo28168a(int, com.tencent.bugly.proguard.v):java.util.Map");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0037, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0042, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x004d, code lost:
        return false;
     */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:24:0x0043=Splitter:B:24:0x0043, B:37:0x005a=Splitter:B:37:0x005a} */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean mo28171a(com.tencent.bugly.proguard.C2368y r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 0
            r1 = 0
            com.tencent.bugly.proguard.x r2 = f1965c     // Catch:{ all -> 0x0050 }
            android.database.sqlite.SQLiteDatabase r1 = r2.getWritableDatabase()     // Catch:{ all -> 0x0050 }
            if (r1 == 0) goto L_0x0043
            android.content.ContentValues r2 = m1043c((com.tencent.bugly.proguard.C2368y) r9)     // Catch:{ all -> 0x0050 }
            if (r2 == 0) goto L_0x0043
            java.lang.String r3 = "t_lr"
            java.lang.String r4 = "_id"
            long r2 = r1.replace(r3, r4, r2)     // Catch:{ all -> 0x0050 }
            r4 = 0
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 < 0) goto L_0x0038
            java.lang.String r4 = "[Database] insert %s success."
            r5 = 1
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ all -> 0x0050 }
            java.lang.String r7 = "t_lr"
            r6[r0] = r7     // Catch:{ all -> 0x0050 }
            com.tencent.bugly.proguard.C2265al.m609c(r4, r6)     // Catch:{ all -> 0x0050 }
            r9.f1988a = r2     // Catch:{ all -> 0x0050 }
            boolean r9 = f1963a     // Catch:{ all -> 0x004e }
            if (r9 == 0) goto L_0x0036
            if (r1 == 0) goto L_0x0036
            r1.close()     // Catch:{ all -> 0x004e }
        L_0x0036:
            monitor-exit(r8)
            return r5
        L_0x0038:
            boolean r9 = f1963a     // Catch:{ all -> 0x004e }
            if (r9 == 0) goto L_0x0041
            if (r1 == 0) goto L_0x0041
            r1.close()     // Catch:{ all -> 0x004e }
        L_0x0041:
            monitor-exit(r8)
            return r0
        L_0x0043:
            boolean r9 = f1963a     // Catch:{ all -> 0x004e }
            if (r9 == 0) goto L_0x004c
            if (r1 == 0) goto L_0x004c
            r1.close()     // Catch:{ all -> 0x004e }
        L_0x004c:
            monitor-exit(r8)
            return r0
        L_0x004e:
            r9 = move-exception
            goto L_0x0070
        L_0x0050:
            r9 = move-exception
            boolean r2 = com.tencent.bugly.proguard.C2265al.m605a(r9)     // Catch:{ all -> 0x0065 }
            if (r2 != 0) goto L_0x005a
            r9.printStackTrace()     // Catch:{ all -> 0x0065 }
        L_0x005a:
            boolean r9 = f1963a     // Catch:{ all -> 0x004e }
            if (r9 == 0) goto L_0x0063
            if (r1 == 0) goto L_0x0063
            r1.close()     // Catch:{ all -> 0x004e }
        L_0x0063:
            monitor-exit(r8)
            return r0
        L_0x0065:
            r9 = move-exception
            boolean r0 = f1963a     // Catch:{ all -> 0x004e }
            if (r0 == 0) goto L_0x006f
            if (r1 == 0) goto L_0x006f
            r1.close()     // Catch:{ all -> 0x004e }
        L_0x006f:
            throw r9     // Catch:{ all -> 0x004e }
        L_0x0070:
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2365w.mo28171a(com.tencent.bugly.proguard.y):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0037, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0042, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x004d, code lost:
        return false;
     */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:24:0x0043=Splitter:B:24:0x0043, B:37:0x005a=Splitter:B:37:0x005a} */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean m1042b(com.tencent.bugly.proguard.C2368y r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 0
            r1 = 0
            com.tencent.bugly.proguard.x r2 = f1965c     // Catch:{ all -> 0x0050 }
            android.database.sqlite.SQLiteDatabase r1 = r2.getWritableDatabase()     // Catch:{ all -> 0x0050 }
            if (r1 == 0) goto L_0x0043
            android.content.ContentValues r2 = m1045d(r9)     // Catch:{ all -> 0x0050 }
            if (r2 == 0) goto L_0x0043
            java.lang.String r3 = "t_pf"
            java.lang.String r4 = "_id"
            long r2 = r1.replace(r3, r4, r2)     // Catch:{ all -> 0x0050 }
            r4 = 0
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 < 0) goto L_0x0038
            java.lang.String r4 = "[Database] insert %s success."
            r5 = 1
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ all -> 0x0050 }
            java.lang.String r7 = "t_pf"
            r6[r0] = r7     // Catch:{ all -> 0x0050 }
            com.tencent.bugly.proguard.C2265al.m609c(r4, r6)     // Catch:{ all -> 0x0050 }
            r9.f1988a = r2     // Catch:{ all -> 0x0050 }
            boolean r9 = f1963a     // Catch:{ all -> 0x004e }
            if (r9 == 0) goto L_0x0036
            if (r1 == 0) goto L_0x0036
            r1.close()     // Catch:{ all -> 0x004e }
        L_0x0036:
            monitor-exit(r8)
            return r5
        L_0x0038:
            boolean r9 = f1963a     // Catch:{ all -> 0x004e }
            if (r9 == 0) goto L_0x0041
            if (r1 == 0) goto L_0x0041
            r1.close()     // Catch:{ all -> 0x004e }
        L_0x0041:
            monitor-exit(r8)
            return r0
        L_0x0043:
            boolean r9 = f1963a     // Catch:{ all -> 0x004e }
            if (r9 == 0) goto L_0x004c
            if (r1 == 0) goto L_0x004c
            r1.close()     // Catch:{ all -> 0x004e }
        L_0x004c:
            monitor-exit(r8)
            return r0
        L_0x004e:
            r9 = move-exception
            goto L_0x0070
        L_0x0050:
            r9 = move-exception
            boolean r2 = com.tencent.bugly.proguard.C2265al.m605a(r9)     // Catch:{ all -> 0x0065 }
            if (r2 != 0) goto L_0x005a
            r9.printStackTrace()     // Catch:{ all -> 0x0065 }
        L_0x005a:
            boolean r9 = f1963a     // Catch:{ all -> 0x004e }
            if (r9 == 0) goto L_0x0063
            if (r1 == 0) goto L_0x0063
            r1.close()     // Catch:{ all -> 0x004e }
        L_0x0063:
            monitor-exit(r8)
            return r0
        L_0x0065:
            r9 = move-exception
            boolean r0 = f1963a     // Catch:{ all -> 0x004e }
            if (r0 == 0) goto L_0x006f
            if (r1 == 0) goto L_0x006f
            r1.close()     // Catch:{ all -> 0x004e }
        L_0x006f:
            throw r9     // Catch:{ all -> 0x004e }
        L_0x0070:
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2365w.m1042b(com.tencent.bugly.proguard.y):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00a8, code lost:
        return r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00b3 A[Catch:{ all -> 0x00c5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00b8 A[SYNTHETIC, Splitter:B:56:0x00b8] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized java.util.List<com.tencent.bugly.proguard.C2368y> mo28167a(int r12) {
        /*
            r11 = this;
            monitor-enter(r11)
            com.tencent.bugly.proguard.x r0 = f1965c     // Catch:{ all -> 0x00d7 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ all -> 0x00d7 }
            r9 = 0
            if (r0 == 0) goto L_0x00d5
            if (r12 < 0) goto L_0x001c
            java.lang.String r1 = "_tp = "
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x0018 }
            java.lang.String r12 = r1.concat(r12)     // Catch:{ all -> 0x0018 }
            r4 = r12
            goto L_0x001d
        L_0x0018:
            r12 = move-exception
            r1 = r9
            goto L_0x00ad
        L_0x001c:
            r4 = r9
        L_0x001d:
            java.lang.String r2 = "t_lr"
            r3 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r1 = r0
            android.database.Cursor r12 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0018 }
            if (r12 != 0) goto L_0x003b
            if (r12 == 0) goto L_0x0030
            r12.close()     // Catch:{ all -> 0x00d7 }
        L_0x0030:
            boolean r12 = f1963a     // Catch:{ all -> 0x00d7 }
            if (r12 == 0) goto L_0x0039
            if (r0 == 0) goto L_0x0039
            r0.close()     // Catch:{ all -> 0x00d7 }
        L_0x0039:
            monitor-exit(r11)
            return r9
        L_0x003b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a9 }
            r1.<init>()     // Catch:{ all -> 0x00a9 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x00a9 }
            r2.<init>()     // Catch:{ all -> 0x00a9 }
        L_0x0045:
            boolean r3 = r12.moveToNext()     // Catch:{ all -> 0x00a9 }
            r4 = 0
            if (r3 == 0) goto L_0x0071
            com.tencent.bugly.proguard.y r3 = m1035a((android.database.Cursor) r12)     // Catch:{ all -> 0x00a9 }
            if (r3 == 0) goto L_0x0056
            r2.add(r3)     // Catch:{ all -> 0x00a9 }
            goto L_0x0045
        L_0x0056:
            java.lang.String r3 = "_id"
            int r3 = r12.getColumnIndex(r3)     // Catch:{ all -> 0x0069 }
            long r5 = r12.getLong(r3)     // Catch:{ all -> 0x0069 }
            java.lang.String r3 = " or _id = "
            r1.append(r3)     // Catch:{ all -> 0x0069 }
            r1.append(r5)     // Catch:{ all -> 0x0069 }
            goto L_0x0045
        L_0x0069:
            java.lang.String r3 = "[Database] unknown id."
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x00a9 }
            com.tencent.bugly.proguard.C2265al.m610d(r3, r4)     // Catch:{ all -> 0x00a9 }
            goto L_0x0045
        L_0x0071:
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00a9 }
            int r3 = r1.length()     // Catch:{ all -> 0x00a9 }
            if (r3 <= 0) goto L_0x0099
            r3 = 4
            java.lang.String r1 = r1.substring(r3)     // Catch:{ all -> 0x00a9 }
            java.lang.String r3 = "t_lr"
            int r1 = r0.delete(r3, r1, r9)     // Catch:{ all -> 0x00a9 }
            java.lang.String r3 = "[Database] deleted %s illegal data %d"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00a9 }
            java.lang.String r6 = "t_lr"
            r5[r4] = r6     // Catch:{ all -> 0x00a9 }
            r4 = 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x00a9 }
            r5[r4] = r1     // Catch:{ all -> 0x00a9 }
            com.tencent.bugly.proguard.C2265al.m610d(r3, r5)     // Catch:{ all -> 0x00a9 }
        L_0x0099:
            if (r12 == 0) goto L_0x009e
            r12.close()     // Catch:{ all -> 0x00d7 }
        L_0x009e:
            boolean r12 = f1963a     // Catch:{ all -> 0x00d7 }
            if (r12 == 0) goto L_0x00a7
            if (r0 == 0) goto L_0x00a7
            r0.close()     // Catch:{ all -> 0x00d7 }
        L_0x00a7:
            monitor-exit(r11)
            return r2
        L_0x00a9:
            r1 = move-exception
            r10 = r1
            r1 = r12
            r12 = r10
        L_0x00ad:
            boolean r2 = com.tencent.bugly.proguard.C2265al.m605a(r12)     // Catch:{ all -> 0x00c5 }
            if (r2 != 0) goto L_0x00b6
            r12.printStackTrace()     // Catch:{ all -> 0x00c5 }
        L_0x00b6:
            if (r1 == 0) goto L_0x00bb
            r1.close()     // Catch:{ all -> 0x00d7 }
        L_0x00bb:
            boolean r12 = f1963a     // Catch:{ all -> 0x00d7 }
            if (r12 == 0) goto L_0x00d5
            if (r0 == 0) goto L_0x00d5
            r0.close()     // Catch:{ all -> 0x00d7 }
            goto L_0x00d5
        L_0x00c5:
            r12 = move-exception
            if (r1 == 0) goto L_0x00cb
            r1.close()     // Catch:{ all -> 0x00d7 }
        L_0x00cb:
            boolean r1 = f1963a     // Catch:{ all -> 0x00d7 }
            if (r1 == 0) goto L_0x00d4
            if (r0 == 0) goto L_0x00d4
            r0.close()     // Catch:{ all -> 0x00d7 }
        L_0x00d4:
            throw r12     // Catch:{ all -> 0x00d7 }
        L_0x00d5:
            monitor-exit(r11)
            return r9
        L_0x00d7:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2365w.mo28167a(int):java.util.List");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x008b, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void mo28169a(java.util.List<com.tencent.bugly.proguard.C2368y> r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r6 == 0) goto L_0x008a
            int r0 = r6.size()     // Catch:{ all -> 0x0087 }
            if (r0 != 0) goto L_0x000b
            goto L_0x008a
        L_0x000b:
            com.tencent.bugly.proguard.x r0 = f1965c     // Catch:{ all -> 0x0087 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ all -> 0x0087 }
            if (r0 == 0) goto L_0x0085
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0087 }
            r1.<init>()     // Catch:{ all -> 0x0087 }
            java.util.Iterator r6 = r6.iterator()     // Catch:{ all -> 0x0087 }
        L_0x001c:
            boolean r2 = r6.hasNext()     // Catch:{ all -> 0x0087 }
            if (r2 == 0) goto L_0x0033
            java.lang.Object r2 = r6.next()     // Catch:{ all -> 0x0087 }
            com.tencent.bugly.proguard.y r2 = (com.tencent.bugly.proguard.C2368y) r2     // Catch:{ all -> 0x0087 }
            java.lang.String r3 = " or _id = "
            r1.append(r3)     // Catch:{ all -> 0x0087 }
            long r2 = r2.f1988a     // Catch:{ all -> 0x0087 }
            r1.append(r2)     // Catch:{ all -> 0x0087 }
            goto L_0x001c
        L_0x0033:
            java.lang.String r6 = r1.toString()     // Catch:{ all -> 0x0087 }
            int r2 = r6.length()     // Catch:{ all -> 0x0087 }
            if (r2 <= 0) goto L_0x0042
            r2 = 4
            java.lang.String r6 = r6.substring(r2)     // Catch:{ all -> 0x0087 }
        L_0x0042:
            r2 = 0
            r1.setLength(r2)     // Catch:{ all -> 0x0087 }
            java.lang.String r1 = "t_lr"
            r3 = 0
            int r6 = r0.delete(r1, r6, r3)     // Catch:{ all -> 0x0069 }
            java.lang.String r1 = "[Database] deleted %s data %d"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0069 }
            java.lang.String r4 = "t_lr"
            r3[r2] = r4     // Catch:{ all -> 0x0069 }
            r2 = 1
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x0069 }
            r3[r2] = r6     // Catch:{ all -> 0x0069 }
            com.tencent.bugly.proguard.C2265al.m609c(r1, r3)     // Catch:{ all -> 0x0069 }
            boolean r6 = f1963a     // Catch:{ all -> 0x0087 }
            if (r6 == 0) goto L_0x0085
            r0.close()     // Catch:{ all -> 0x0087 }
            monitor-exit(r5)
            return
        L_0x0069:
            r6 = move-exception
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r6)     // Catch:{ all -> 0x007c }
            if (r1 != 0) goto L_0x0073
            r6.printStackTrace()     // Catch:{ all -> 0x007c }
        L_0x0073:
            boolean r6 = f1963a     // Catch:{ all -> 0x0087 }
            if (r6 == 0) goto L_0x0085
            r0.close()     // Catch:{ all -> 0x0087 }
            monitor-exit(r5)
            return
        L_0x007c:
            r6 = move-exception
            boolean r1 = f1963a     // Catch:{ all -> 0x0087 }
            if (r1 == 0) goto L_0x0084
            r0.close()     // Catch:{ all -> 0x0087 }
        L_0x0084:
            throw r6     // Catch:{ all -> 0x0087 }
        L_0x0085:
            monitor-exit(r5)
            return
        L_0x0087:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        L_0x008a:
            monitor-exit(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2365w.mo28169a(java.util.List):void");
    }

    /* renamed from: b */
    public final synchronized void mo28172b(int i) {
        String str;
        SQLiteDatabase writableDatabase = f1965c.getWritableDatabase();
        if (writableDatabase != null) {
            if (i >= 0) {
                try {
                    str = "_tp = ".concat(String.valueOf(i));
                } catch (Throwable th) {
                    if (f1963a && writableDatabase != null) {
                        writableDatabase.close();
                    }
                    throw th;
                }
            } else {
                str = null;
            }
            C2265al.m609c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", str, (String[]) null)));
            if (f1963a && writableDatabase != null) {
                writableDatabase.close();
            }
        }
    }

    /* renamed from: c */
    private static ContentValues m1043c(C2368y yVar) {
        if (yVar == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (yVar.f1988a > 0) {
                contentValues.put("_id", Long.valueOf(yVar.f1988a));
            }
            contentValues.put("_tp", Integer.valueOf(yVar.f1989b));
            contentValues.put("_pc", yVar.f1990c);
            contentValues.put("_th", yVar.f1991d);
            contentValues.put("_tm", Long.valueOf(yVar.f1992e));
            if (yVar.f1994g != null) {
                contentValues.put("_dt", yVar.f1994g);
            }
            return contentValues;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* renamed from: a */
    private static C2368y m1035a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            C2368y yVar = new C2368y();
            yVar.f1988a = cursor.getLong(cursor.getColumnIndex("_id"));
            yVar.f1989b = cursor.getInt(cursor.getColumnIndex("_tp"));
            yVar.f1990c = cursor.getString(cursor.getColumnIndex("_pc"));
            yVar.f1991d = cursor.getString(cursor.getColumnIndex("_th"));
            yVar.f1992e = cursor.getLong(cursor.getColumnIndex("_tm"));
            yVar.f1994g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return yVar;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0032, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a4, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00ae, code lost:
        if (r1 != null) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00b0, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00cb, code lost:
        if (r1 != null) goto L_0x00b0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00bf A[Catch:{ all -> 0x00d0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00c4 A[SYNTHETIC, Splitter:B:60:0x00c4] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00cb  */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.util.List<com.tencent.bugly.proguard.C2368y> m1044c(int r12) {
        /*
            r11 = this;
            monitor-enter(r11)
            r0 = 0
            com.tencent.bugly.proguard.x r1 = f1965c     // Catch:{ all -> 0x00b6 }
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ all -> 0x00b6 }
            if (r1 == 0) goto L_0x00aa
            java.lang.String r2 = "_id = "
            java.lang.String r3 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x00a7 }
            java.lang.String r10 = r2.concat(r3)     // Catch:{ all -> 0x00a7 }
            java.lang.String r3 = "t_pf"
            r4 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r2 = r1
            r5 = r10
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x00a7 }
            if (r2 != 0) goto L_0x0033
            if (r2 == 0) goto L_0x0028
            r2.close()     // Catch:{ all -> 0x00b4 }
        L_0x0028:
            boolean r12 = f1963a     // Catch:{ all -> 0x00b4 }
            if (r12 == 0) goto L_0x0031
            if (r1 == 0) goto L_0x0031
            r1.close()     // Catch:{ all -> 0x00b4 }
        L_0x0031:
            monitor-exit(r11)
            return r0
        L_0x0033:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a5 }
            r3.<init>()     // Catch:{ all -> 0x00a5 }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ all -> 0x00a5 }
            r4.<init>()     // Catch:{ all -> 0x00a5 }
        L_0x003d:
            boolean r5 = r2.moveToNext()     // Catch:{ all -> 0x00a5 }
            r6 = 0
            if (r5 == 0) goto L_0x0069
            com.tencent.bugly.proguard.y r5 = m1041b((android.database.Cursor) r2)     // Catch:{ all -> 0x00a5 }
            if (r5 == 0) goto L_0x004e
            r4.add(r5)     // Catch:{ all -> 0x00a5 }
            goto L_0x003d
        L_0x004e:
            java.lang.String r5 = "_tp"
            int r5 = r2.getColumnIndex(r5)     // Catch:{ all -> 0x0061 }
            java.lang.String r5 = r2.getString(r5)     // Catch:{ all -> 0x0061 }
            java.lang.String r7 = " or _tp = "
            r3.append(r7)     // Catch:{ all -> 0x0061 }
            r3.append(r5)     // Catch:{ all -> 0x0061 }
            goto L_0x003d
        L_0x0061:
            java.lang.String r5 = "[Database] unknown id."
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x00a5 }
            com.tencent.bugly.proguard.C2265al.m610d(r5, r6)     // Catch:{ all -> 0x00a5 }
            goto L_0x003d
        L_0x0069:
            int r5 = r3.length()     // Catch:{ all -> 0x00a5 }
            if (r5 <= 0) goto L_0x0095
            java.lang.String r5 = " and _id = "
            r3.append(r5)     // Catch:{ all -> 0x00a5 }
            r3.append(r12)     // Catch:{ all -> 0x00a5 }
            r12 = 4
            java.lang.String r12 = r10.substring(r12)     // Catch:{ all -> 0x00a5 }
            java.lang.String r3 = "t_pf"
            int r12 = r1.delete(r3, r12, r0)     // Catch:{ all -> 0x00a5 }
            java.lang.String r3 = "[Database] deleted %s illegal data %d."
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00a5 }
            java.lang.String r7 = "t_pf"
            r5[r6] = r7     // Catch:{ all -> 0x00a5 }
            r6 = 1
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ all -> 0x00a5 }
            r5[r6] = r12     // Catch:{ all -> 0x00a5 }
            com.tencent.bugly.proguard.C2265al.m610d(r3, r5)     // Catch:{ all -> 0x00a5 }
        L_0x0095:
            if (r2 == 0) goto L_0x009a
            r2.close()     // Catch:{ all -> 0x00b4 }
        L_0x009a:
            boolean r12 = f1963a     // Catch:{ all -> 0x00b4 }
            if (r12 == 0) goto L_0x00a3
            if (r1 == 0) goto L_0x00a3
            r1.close()     // Catch:{ all -> 0x00b4 }
        L_0x00a3:
            monitor-exit(r11)
            return r4
        L_0x00a5:
            r12 = move-exception
            goto L_0x00b9
        L_0x00a7:
            r12 = move-exception
            r2 = r0
            goto L_0x00b9
        L_0x00aa:
            boolean r12 = f1963a     // Catch:{ all -> 0x00b4 }
            if (r12 == 0) goto L_0x00ce
            if (r1 == 0) goto L_0x00ce
        L_0x00b0:
            r1.close()     // Catch:{ all -> 0x00b4 }
            goto L_0x00ce
        L_0x00b4:
            r12 = move-exception
            goto L_0x00e0
        L_0x00b6:
            r12 = move-exception
            r1 = r0
            r2 = r1
        L_0x00b9:
            boolean r3 = com.tencent.bugly.proguard.C2265al.m605a(r12)     // Catch:{ all -> 0x00d0 }
            if (r3 != 0) goto L_0x00c2
            r12.printStackTrace()     // Catch:{ all -> 0x00d0 }
        L_0x00c2:
            if (r2 == 0) goto L_0x00c7
            r2.close()     // Catch:{ all -> 0x00b4 }
        L_0x00c7:
            boolean r12 = f1963a     // Catch:{ all -> 0x00b4 }
            if (r12 == 0) goto L_0x00ce
            if (r1 == 0) goto L_0x00ce
            goto L_0x00b0
        L_0x00ce:
            monitor-exit(r11)
            return r0
        L_0x00d0:
            r12 = move-exception
            if (r2 == 0) goto L_0x00d6
            r2.close()     // Catch:{ all -> 0x00b4 }
        L_0x00d6:
            boolean r0 = f1963a     // Catch:{ all -> 0x00b4 }
            if (r0 == 0) goto L_0x00df
            if (r1 == 0) goto L_0x00df
            r1.close()     // Catch:{ all -> 0x00b4 }
        L_0x00df:
            throw r12     // Catch:{ all -> 0x00b4 }
        L_0x00e0:
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2365w.m1044c(int):java.util.List");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.String[]] */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x006f A[Catch:{ all -> 0x0081 }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0074 A[SYNTHETIC, Splitter:B:29:0x0074] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean m1037a(int r6, java.lang.String r7, com.tencent.bugly.proguard.C2364v r8) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            r1 = 0
            com.tencent.bugly.proguard.x r2 = f1965c     // Catch:{ all -> 0x0068 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ all -> 0x0068 }
            if (r2 == 0) goto L_0x0057
            boolean r3 = com.tencent.bugly.proguard.C2273ap.m657a((java.lang.String) r7)     // Catch:{ all -> 0x0054 }
            if (r3 == 0) goto L_0x001c
            java.lang.String r7 = "_id = "
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x0054 }
            java.lang.String r6 = r7.concat(r6)     // Catch:{ all -> 0x0054 }
            goto L_0x0037
        L_0x001c:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0054 }
            java.lang.String r4 = "_id = "
            r3.<init>(r4)     // Catch:{ all -> 0x0054 }
            r3.append(r6)     // Catch:{ all -> 0x0054 }
            java.lang.String r6 = " and _tp = \""
            r3.append(r6)     // Catch:{ all -> 0x0054 }
            r3.append(r7)     // Catch:{ all -> 0x0054 }
            java.lang.String r6 = "\""
            r3.append(r6)     // Catch:{ all -> 0x0054 }
            java.lang.String r6 = r3.toString()     // Catch:{ all -> 0x0054 }
        L_0x0037:
            java.lang.String r7 = "t_pf"
            int r6 = r2.delete(r7, r6, r0)     // Catch:{ all -> 0x0054 }
            java.lang.String r7 = "[Database] deleted %s data %d"
            r0 = 2
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0054 }
            java.lang.String r3 = "t_pf"
            r0[r1] = r3     // Catch:{ all -> 0x0054 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x0054 }
            r4 = 1
            r0[r4] = r3     // Catch:{ all -> 0x0054 }
            com.tencent.bugly.proguard.C2265al.m609c(r7, r0)     // Catch:{ all -> 0x0054 }
            if (r6 <= 0) goto L_0x0057
            r1 = 1
            goto L_0x0057
        L_0x0054:
            r6 = move-exception
            r0 = r2
            goto L_0x0069
        L_0x0057:
            if (r8 == 0) goto L_0x005c
            java.lang.Boolean.valueOf(r1)     // Catch:{ all -> 0x0066 }
        L_0x005c:
            boolean r6 = f1963a     // Catch:{ all -> 0x0066 }
            if (r6 == 0) goto L_0x007f
            if (r2 == 0) goto L_0x007f
            r2.close()     // Catch:{ all -> 0x0066 }
            goto L_0x007f
        L_0x0066:
            r6 = move-exception
            goto L_0x0090
        L_0x0068:
            r6 = move-exception
        L_0x0069:
            boolean r7 = com.tencent.bugly.proguard.C2265al.m605a(r6)     // Catch:{ all -> 0x0081 }
            if (r7 != 0) goto L_0x0072
            r6.printStackTrace()     // Catch:{ all -> 0x0081 }
        L_0x0072:
            if (r8 == 0) goto L_0x0076
            java.lang.Boolean r6 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0066 }
        L_0x0076:
            boolean r6 = f1963a     // Catch:{ all -> 0x0066 }
            if (r6 == 0) goto L_0x007f
            if (r0 == 0) goto L_0x007f
            r0.close()     // Catch:{ all -> 0x0066 }
        L_0x007f:
            monitor-exit(r5)
            return r1
        L_0x0081:
            r6 = move-exception
            if (r8 == 0) goto L_0x0086
            java.lang.Boolean r7 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x0066 }
        L_0x0086:
            boolean r7 = f1963a     // Catch:{ all -> 0x0066 }
            if (r7 == 0) goto L_0x008f
            if (r0 == 0) goto L_0x008f
            r0.close()     // Catch:{ all -> 0x0066 }
        L_0x008f:
            throw r6     // Catch:{ all -> 0x0066 }
        L_0x0090:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2365w.m1037a(int, java.lang.String, com.tencent.bugly.proguard.v):boolean");
    }

    /* renamed from: d */
    private static ContentValues m1045d(C2368y yVar) {
        if (yVar != null && !C2273ap.m657a(yVar.f1993f)) {
            try {
                ContentValues contentValues = new ContentValues();
                if (yVar.f1988a > 0) {
                    contentValues.put("_id", Long.valueOf(yVar.f1988a));
                }
                contentValues.put("_tp", yVar.f1993f);
                contentValues.put("_tm", Long.valueOf(yVar.f1992e));
                if (yVar.f1994g != null) {
                    contentValues.put("_dt", yVar.f1994g);
                }
                return contentValues;
            } catch (Throwable th) {
                if (!C2265al.m605a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    /* renamed from: b */
    private static C2368y m1041b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            C2368y yVar = new C2368y();
            yVar.f1988a = cursor.getLong(cursor.getColumnIndex("_id"));
            yVar.f1992e = cursor.getLong(cursor.getColumnIndex("_tm"));
            yVar.f1993f = cursor.getString(cursor.getColumnIndex("_tp"));
            yVar.f1994g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return yVar;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* renamed from: com.tencent.bugly.proguard.w$a */
    /* compiled from: BUGLY */
    class C2366a extends Thread {

        /* renamed from: b */
        private int f1967b = 4;

        /* renamed from: c */
        private C2364v f1968c = null;

        /* renamed from: d */
        private String f1969d;

        /* renamed from: e */
        private ContentValues f1970e;

        /* renamed from: f */
        private boolean f1971f;

        /* renamed from: g */
        private String[] f1972g;

        /* renamed from: h */
        private String f1973h;

        /* renamed from: i */
        private String[] f1974i;

        /* renamed from: j */
        private String f1975j;

        /* renamed from: k */
        private String f1976k;

        /* renamed from: l */
        private String f1977l;

        /* renamed from: m */
        private String f1978m;

        /* renamed from: n */
        private String f1979n;

        /* renamed from: o */
        private String[] f1980o;

        /* renamed from: p */
        private int f1981p;

        /* renamed from: q */
        private String f1982q;

        /* renamed from: r */
        private byte[] f1983r;

        public C2366a() {
        }

        /* renamed from: a */
        public final void mo28173a(int i, String str, byte[] bArr) {
            this.f1981p = i;
            this.f1982q = str;
            this.f1983r = bArr;
        }

        public final void run() {
            switch (this.f1967b) {
                case 1:
                    long unused = C2365w.this.mo28164a(this.f1969d, this.f1970e, this.f1968c);
                    return;
                case 2:
                    int unused2 = C2365w.this.m1029a(this.f1969d, this.f1979n, this.f1980o, this.f1968c);
                    return;
                case 3:
                    Cursor a = C2365w.this.m1032a(this.f1971f, this.f1969d, this.f1972g, this.f1973h, this.f1974i, this.f1975j, this.f1976k, this.f1977l, this.f1978m, this.f1968c);
                    if (a != null) {
                        a.close();
                        return;
                    }
                    return;
                case 4:
                    boolean unused3 = C2365w.this.m1038a(this.f1981p, this.f1982q, this.f1983r, this.f1968c);
                    return;
                case 5:
                    Map unused4 = C2365w.this.mo28168a(this.f1981p, this.f1968c);
                    return;
                case 6:
                    boolean unused5 = C2365w.this.m1037a(this.f1981p, this.f1982q, this.f1968c);
                    return;
                default:
                    return;
            }
        }
    }
}
