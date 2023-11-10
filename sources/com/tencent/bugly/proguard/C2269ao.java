package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;

/* renamed from: com.tencent.bugly.proguard.ao */
/* compiled from: BUGLY */
public final class C2269ao {

    /* renamed from: a */
    public static boolean f1572a = true;

    /* renamed from: b */
    public static boolean f1573b = true;

    /* renamed from: c */
    private static SimpleDateFormat f1574c = null;

    /* renamed from: d */
    private static int f1575d = 30720;

    /* renamed from: e */
    private static StringBuilder f1576e = null;

    /* renamed from: f */
    private static StringBuilder f1577f = null;

    /* renamed from: g */
    private static boolean f1578g = false;

    /* renamed from: h */
    private static C2272a f1579h = null;

    /* renamed from: i */
    private static String f1580i = null;

    /* renamed from: j */
    private static String f1581j = null;

    /* renamed from: k */
    private static Context f1582k = null;

    /* renamed from: l */
    private static String f1583l = null;

    /* renamed from: m */
    private static boolean f1584m = false;

    /* renamed from: n */
    private static boolean f1585n = false;

    /* renamed from: o */
    private static ExecutorService f1586o;

    /* renamed from: p */
    private static int f1587p;

    /* renamed from: q */
    private static final Object f1588q = new Object();

    static {
        try {
            f1574c = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
            C2265al.m608b(th.getCause());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public static boolean m629d(String str, String str2, String str3) {
        try {
            C2231aa b = C2231aa.m459b();
            if (b == null || b.f1433N == null) {
                return false;
            }
            return b.f1433N.appendLogToNative(str, str2, str3);
        } catch (Throwable th) {
            if (C2265al.m605a(th)) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    /* renamed from: b */
    private static String m625b() {
        try {
            C2231aa b = C2231aa.m459b();
            if (b == null || b.f1433N == null) {
                return null;
            }
            return b.f1433N.getLogFromNative();
        } catch (Throwable th) {
            if (C2265al.m605a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0071, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void m621a(android.content.Context r3) {
        /*
            java.lang.Class<com.tencent.bugly.proguard.ao> r0 = com.tencent.bugly.proguard.C2269ao.class
            monitor-enter(r0)
            boolean r1 = f1584m     // Catch:{ all -> 0x0072 }
            if (r1 != 0) goto L_0x0070
            if (r3 == 0) goto L_0x0070
            boolean r1 = f1573b     // Catch:{ all -> 0x0072 }
            if (r1 != 0) goto L_0x000e
            goto L_0x0070
        L_0x000e:
            java.util.concurrent.ExecutorService r1 = java.util.concurrent.Executors.newSingleThreadExecutor()     // Catch:{ all -> 0x006b }
            f1586o = r1     // Catch:{ all -> 0x006b }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x006b }
            r2 = 0
            r1.<init>(r2)     // Catch:{ all -> 0x006b }
            f1577f = r1     // Catch:{ all -> 0x006b }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x006b }
            r1.<init>(r2)     // Catch:{ all -> 0x006b }
            f1576e = r1     // Catch:{ all -> 0x006b }
            f1582k = r3     // Catch:{ all -> 0x006b }
            com.tencent.bugly.proguard.aa r3 = com.tencent.bugly.proguard.C2231aa.m457a((android.content.Context) r3)     // Catch:{ all -> 0x006b }
            java.lang.String r1 = r3.f1472d     // Catch:{ all -> 0x006b }
            f1580i = r1     // Catch:{ all -> 0x006b }
            r3.getClass()     // Catch:{ all -> 0x006b }
            java.lang.String r3 = ""
            f1581j = r3     // Catch:{ all -> 0x006b }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x006b }
            r3.<init>()     // Catch:{ all -> 0x006b }
            android.content.Context r1 = f1582k     // Catch:{ all -> 0x006b }
            java.io.File r1 = r1.getFilesDir()     // Catch:{ all -> 0x006b }
            java.lang.String r1 = r1.getPath()     // Catch:{ all -> 0x006b }
            r3.append(r1)     // Catch:{ all -> 0x006b }
            java.lang.String r1 = "/buglylog_"
            r3.append(r1)     // Catch:{ all -> 0x006b }
            java.lang.String r1 = f1580i     // Catch:{ all -> 0x006b }
            r3.append(r1)     // Catch:{ all -> 0x006b }
            java.lang.String r1 = "_"
            r3.append(r1)     // Catch:{ all -> 0x006b }
            java.lang.String r1 = f1581j     // Catch:{ all -> 0x006b }
            r3.append(r1)     // Catch:{ all -> 0x006b }
            java.lang.String r1 = ".txt"
            r3.append(r1)     // Catch:{ all -> 0x006b }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x006b }
            f1583l = r3     // Catch:{ all -> 0x006b }
            int r3 = android.os.Process.myPid()     // Catch:{ all -> 0x006b }
            f1587p = r3     // Catch:{ all -> 0x006b }
        L_0x006b:
            r3 = 1
            f1584m = r3     // Catch:{ all -> 0x0072 }
            monitor-exit(r0)
            return
        L_0x0070:
            monitor-exit(r0)
            return
        L_0x0072:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2269ao.m621a(android.content.Context):void");
    }

    /* renamed from: a */
    public static void m620a(int i) {
        synchronized (f1588q) {
            f1575d = i;
            if (i < 0) {
                f1575d = 0;
            } else if (i > 30720) {
                f1575d = 30720;
            }
        }
    }

    /* renamed from: a */
    public static void m623a(String str, String str2, Throwable th) {
        if (th != null) {
            String message = th.getMessage();
            if (message == null) {
                message = "";
            }
            m622a(str, str2, message + 10 + C2273ap.m663b(th));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x002f, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void m622a(final java.lang.String r3, final java.lang.String r4, final java.lang.String r5) {
        /*
            java.lang.Class<com.tencent.bugly.proguard.ao> r0 = com.tencent.bugly.proguard.C2269ao.class
            monitor-enter(r0)
            boolean r1 = f1584m     // Catch:{ all -> 0x0030 }
            if (r1 == 0) goto L_0x002e
            boolean r1 = f1573b     // Catch:{ all -> 0x0030 }
            if (r1 != 0) goto L_0x000c
            goto L_0x002e
        L_0x000c:
            boolean r1 = f1585n     // Catch:{ Exception -> 0x0028 }
            if (r1 == 0) goto L_0x001c
            java.util.concurrent.ExecutorService r1 = f1586o     // Catch:{ Exception -> 0x0028 }
            com.tencent.bugly.proguard.ao$1 r2 = new com.tencent.bugly.proguard.ao$1     // Catch:{ Exception -> 0x0028 }
            r2.<init>(r3, r4, r5)     // Catch:{ Exception -> 0x0028 }
            r1.execute(r2)     // Catch:{ Exception -> 0x0028 }
            monitor-exit(r0)
            return
        L_0x001c:
            java.util.concurrent.ExecutorService r1 = f1586o     // Catch:{ Exception -> 0x0028 }
            com.tencent.bugly.proguard.ao$2 r2 = new com.tencent.bugly.proguard.ao$2     // Catch:{ Exception -> 0x0028 }
            r2.<init>(r3, r4, r5)     // Catch:{ Exception -> 0x0028 }
            r1.execute(r2)     // Catch:{ Exception -> 0x0028 }
            monitor-exit(r0)
            return
        L_0x0028:
            r3 = move-exception
            com.tencent.bugly.proguard.C2265al.m608b(r3)     // Catch:{ all -> 0x0030 }
            monitor-exit(r0)
            return
        L_0x002e:
            monitor-exit(r0)
            return
        L_0x0030:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2269ao.m622a(java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public static synchronized void m630e(String str, String str2, String str3) {
        synchronized (C2269ao.class) {
            if (f1572a) {
                m631f(str, str2, str3);
            } else {
                m632g(str, str2, str3);
            }
        }
    }

    /* renamed from: f */
    private static synchronized void m631f(String str, String str2, String str3) {
        synchronized (C2269ao.class) {
            String a = m619a(str, str2, str3, (long) Process.myTid());
            synchronized (f1588q) {
                try {
                    f1577f.append(a);
                    if (f1577f.length() >= f1575d) {
                        StringBuilder sb = f1577f;
                        f1577f = sb.delete(0, sb.indexOf("\u0001\r\n") + 1);
                    }
                } catch (Throwable th) {
                    if (!C2265al.m608b(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:19|20|21|(1:23)(2:24|(1:28))|29|(1:31)|32|33|34|35) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x0070 */
    /* renamed from: g */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized void m632g(java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
            java.lang.Class<com.tencent.bugly.proguard.ao> r0 = com.tencent.bugly.proguard.C2269ao.class
            monitor-enter(r0)
            int r1 = android.os.Process.myTid()     // Catch:{ all -> 0x0076 }
            long r1 = (long) r1     // Catch:{ all -> 0x0076 }
            java.lang.String r5 = m619a(r5, r6, r7, r1)     // Catch:{ all -> 0x0076 }
            java.lang.Object r6 = f1588q     // Catch:{ all -> 0x0076 }
            monitor-enter(r6)     // Catch:{ all -> 0x0076 }
            java.lang.StringBuilder r7 = f1577f     // Catch:{ all -> 0x0070 }
            r7.append(r5)     // Catch:{ all -> 0x0070 }
            java.lang.StringBuilder r5 = f1577f     // Catch:{ all -> 0x0070 }
            int r5 = r5.length()     // Catch:{ all -> 0x0070 }
            int r7 = f1575d     // Catch:{ all -> 0x0070 }
            if (r5 > r7) goto L_0x0021
            monitor-exit(r6)     // Catch:{ all -> 0x0073 }
            monitor-exit(r0)
            return
        L_0x0021:
            boolean r5 = f1578g     // Catch:{ all -> 0x0070 }
            if (r5 == 0) goto L_0x0028
            monitor-exit(r6)     // Catch:{ all -> 0x0073 }
            monitor-exit(r0)
            return
        L_0x0028:
            r5 = 1
            f1578g = r5     // Catch:{ all -> 0x0070 }
            com.tencent.bugly.proguard.ao$a r5 = f1579h     // Catch:{ all -> 0x0070 }
            if (r5 != 0) goto L_0x0039
            com.tencent.bugly.proguard.ao$a r5 = new com.tencent.bugly.proguard.ao$a     // Catch:{ all -> 0x0070 }
            java.lang.String r7 = f1583l     // Catch:{ all -> 0x0070 }
            r5.<init>(r7)     // Catch:{ all -> 0x0070 }
            f1579h = r5     // Catch:{ all -> 0x0070 }
            goto L_0x005a
        L_0x0039:
            java.io.File r5 = r5.f1596b     // Catch:{ all -> 0x0070 }
            if (r5 == 0) goto L_0x0055
            com.tencent.bugly.proguard.ao$a r5 = f1579h     // Catch:{ all -> 0x0070 }
            java.io.File r5 = r5.f1596b     // Catch:{ all -> 0x0070 }
            long r1 = r5.length()     // Catch:{ all -> 0x0070 }
            java.lang.StringBuilder r5 = f1577f     // Catch:{ all -> 0x0070 }
            int r5 = r5.length()     // Catch:{ all -> 0x0070 }
            long r3 = (long) r5     // Catch:{ all -> 0x0070 }
            long r1 = r1 + r3
            com.tencent.bugly.proguard.ao$a r5 = f1579h     // Catch:{ all -> 0x0070 }
            long r3 = r5.f1597c     // Catch:{ all -> 0x0070 }
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 <= 0) goto L_0x005a
        L_0x0055:
            com.tencent.bugly.proguard.ao$a r5 = f1579h     // Catch:{ all -> 0x0070 }
            r5.mo28025a()     // Catch:{ all -> 0x0070 }
        L_0x005a:
            com.tencent.bugly.proguard.ao$a r5 = f1579h     // Catch:{ all -> 0x0070 }
            java.lang.StringBuilder r7 = f1577f     // Catch:{ all -> 0x0070 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0070 }
            boolean r5 = r5.mo28026a(r7)     // Catch:{ all -> 0x0070 }
            if (r5 == 0) goto L_0x0070
            java.lang.StringBuilder r5 = f1577f     // Catch:{ all -> 0x0070 }
            r7 = 0
            r5.setLength(r7)     // Catch:{ all -> 0x0070 }
            f1578g = r7     // Catch:{ all -> 0x0070 }
        L_0x0070:
            monitor-exit(r6)     // Catch:{ all -> 0x0073 }
            monitor-exit(r0)
            return
        L_0x0073:
            r5 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0073 }
            throw r5     // Catch:{ all -> 0x0076 }
        L_0x0076:
            r5 = move-exception
            monitor-exit(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2269ao.m632g(java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* renamed from: a */
    private static String m619a(String str, String str2, String str3, long j) {
        String str4;
        f1576e.setLength(0);
        if (str3.length() > 30720) {
            str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = f1574c;
        if (simpleDateFormat != null) {
            str4 = simpleDateFormat.format(date);
        } else {
            str4 = date.toString();
        }
        StringBuilder sb = f1576e;
        sb.append(str4);
        sb.append(" ");
        sb.append(f1587p);
        sb.append(" ");
        sb.append(j);
        sb.append(" ");
        sb.append(str);
        sb.append(" ");
        sb.append(str2);
        sb.append(": ");
        sb.append(str3);
        sb.append("\u0001\r\n");
        return f1576e.toString();
    }

    /* renamed from: a */
    public static byte[] m624a() {
        if (!f1572a) {
            return m628c();
        }
        if (!f1573b) {
            return null;
        }
        return C2273ap.m659a(f1577f.toString(), "BuglyLog.txt");
    }

    /* renamed from: c */
    private static byte[] m628c() {
        if (!f1573b) {
            return null;
        }
        if (f1585n) {
            C2265al.m604a("[LogUtil] Get user log from native.", new Object[0]);
            String b = m625b();
            if (b != null) {
                C2265al.m604a("[LogUtil] Got user log from native: %d bytes", Integer.valueOf(b.length()));
                return C2273ap.m659a(b, "BuglyNativeLog.txt");
            }
        }
        StringBuilder sb = new StringBuilder();
        synchronized (f1588q) {
            C2272a aVar = f1579h;
            if (aVar != null && aVar.f1595a && f1579h.f1596b != null && f1579h.f1596b.length() > 0) {
                sb.append(C2273ap.m644a(f1579h.f1596b, 30720, true));
            }
            StringBuilder sb2 = f1577f;
            if (sb2 != null && sb2.length() > 0) {
                sb.append(f1577f.toString());
            }
        }
        return C2273ap.m659a(sb.toString(), "BuglyLog.txt");
    }

    /* renamed from: com.tencent.bugly.proguard.ao$a */
    /* compiled from: BUGLY */
    public static class C2272a {

        /* renamed from: a */
        boolean f1595a;

        /* renamed from: b */
        File f1596b;

        /* renamed from: c */
        long f1597c = 30720;

        /* renamed from: d */
        private String f1598d;

        /* renamed from: e */
        private long f1599e;

        public C2272a(String str) {
            if (str != null && !str.equals("")) {
                this.f1598d = str;
                this.f1595a = mo28025a();
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public final boolean mo28025a() {
            try {
                File file = new File(this.f1598d);
                this.f1596b = file;
                if (file.exists() && !this.f1596b.delete()) {
                    this.f1595a = false;
                    return false;
                } else if (this.f1596b.createNewFile()) {
                    return true;
                } else {
                    this.f1595a = false;
                    return false;
                }
            } catch (Throwable th) {
                C2265al.m605a(th);
                this.f1595a = false;
                return false;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:19:0x0036 A[SYNTHETIC, Splitter:B:19:0x0036] */
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean mo28026a(java.lang.String r10) {
            /*
                r9 = this;
                boolean r0 = r9.f1595a
                r1 = 0
                if (r0 != 0) goto L_0x0006
                return r1
            L_0x0006:
                r0 = 0
                java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ all -> 0x002e }
                java.io.File r3 = r9.f1596b     // Catch:{ all -> 0x002e }
                r4 = 1
                r2.<init>(r3, r4)     // Catch:{ all -> 0x002e }
                java.lang.String r0 = "UTF-8"
                byte[] r10 = r10.getBytes(r0)     // Catch:{ all -> 0x002b }
                r2.write(r10)     // Catch:{ all -> 0x002b }
                r2.flush()     // Catch:{ all -> 0x002b }
                r2.close()     // Catch:{ all -> 0x002b }
                long r5 = r9.f1599e     // Catch:{ all -> 0x002b }
                int r10 = r10.length     // Catch:{ all -> 0x002b }
                long r7 = (long) r10     // Catch:{ all -> 0x002b }
                long r5 = r5 + r7
                r9.f1599e = r5     // Catch:{ all -> 0x002b }
                r9.f1595a = r4     // Catch:{ all -> 0x002b }
                r2.close()     // Catch:{ IOException -> 0x002a }
            L_0x002a:
                return r4
            L_0x002b:
                r10 = move-exception
                r0 = r2
                goto L_0x002f
            L_0x002e:
                r10 = move-exception
            L_0x002f:
                com.tencent.bugly.proguard.C2265al.m605a(r10)     // Catch:{ all -> 0x003a }
                r9.f1595a = r1     // Catch:{ all -> 0x003a }
                if (r0 == 0) goto L_0x0039
                r0.close()     // Catch:{ IOException -> 0x0039 }
            L_0x0039:
                return r1
            L_0x003a:
                r10 = move-exception
                if (r0 == 0) goto L_0x0040
                r0.close()     // Catch:{ IOException -> 0x0040 }
            L_0x0040:
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2269ao.C2272a.mo28026a(java.lang.String):boolean");
        }
    }
}
