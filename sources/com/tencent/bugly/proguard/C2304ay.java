package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.FileObserver;
import android.text.TextUtils;
import com.p020kl.commonbase.constants.Constants;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.anr.TraceFileHelper;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.tencent.bugly.proguard.ay */
/* compiled from: BUGLY */
public final class C2304ay {

    /* renamed from: f */
    public static C2304ay f1700f;

    /* renamed from: a */
    public final AtomicBoolean f1701a = new AtomicBoolean(false);

    /* renamed from: b */
    public final ActivityManager f1702b;

    /* renamed from: c */
    final C2231aa f1703c;

    /* renamed from: d */
    final C2263ak f1704d;

    /* renamed from: e */
    String f1705e;

    /* renamed from: g */
    private final Context f1706g;

    /* renamed from: h */
    private final C2248ac f1707h;

    /* renamed from: i */
    private final C2277as f1708i;

    /* renamed from: j */
    private final Object f1709j = new Object();

    /* renamed from: k */
    private FileObserver f1710k;

    /* renamed from: l */
    private boolean f1711l = true;

    /* renamed from: m */
    private C2318bg f1712m;

    /* renamed from: n */
    private int f1713n;

    /* renamed from: o */
    private long f1714o = 0;

    /* renamed from: a */
    public static synchronized C2304ay m775a() {
        C2304ay ayVar;
        synchronized (C2304ay.class) {
            ayVar = f1700f;
        }
        return ayVar;
    }

    public C2304ay(Context context, C2248ac acVar, C2231aa aaVar, C2263ak akVar, C2277as asVar) {
        Context a = C2273ap.m635a(context);
        this.f1706g = a;
        this.f1702b = (ActivityManager) a.getSystemService("activity");
        if (C2273ap.m657a(NativeCrashHandler.getDumpFilePath())) {
            this.f1705e = context.getDir("bugly", 0).getAbsolutePath();
        } else {
            this.f1705e = NativeCrashHandler.getDumpFilePath();
        }
        this.f1703c = aaVar;
        this.f1704d = akVar;
        this.f1707h = acVar;
        this.f1708i = asVar;
    }

    /* renamed from: a */
    private CrashDetailBean m774a(C2303ax axVar) {
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        try {
            crashDetailBean.f1338C = C2232ab.m511j();
            crashDetailBean.f1339D = C2232ab.m506f();
            crashDetailBean.f1340E = C2232ab.m513l();
            crashDetailBean.f1341F = this.f1703c.mo27977k();
            crashDetailBean.f1342G = this.f1703c.mo27976j();
            crashDetailBean.f1343H = this.f1703c.mo27978l();
            crashDetailBean.f1344I = C2232ab.m498b(this.f1706g);
            crashDetailBean.f1345J = C2232ab.m508g();
            crashDetailBean.f1346K = C2232ab.m509h();
            crashDetailBean.f1364b = 3;
            crashDetailBean.f1367e = this.f1703c.mo27972g();
            crashDetailBean.f1368f = this.f1703c.f1483o;
            crashDetailBean.f1369g = this.f1703c.mo27982q();
            crashDetailBean.f1375m = this.f1703c.mo27970f();
            crashDetailBean.f1376n = "ANR_EXCEPTION";
            crashDetailBean.f1377o = axVar.f1698f;
            crashDetailBean.f1379q = axVar.f1699g;
            crashDetailBean.f1355T = new HashMap();
            crashDetailBean.f1355T.put("BUGLY_CR_01", axVar.f1697e);
            int i = -1;
            if (crashDetailBean.f1379q != null) {
                i = crashDetailBean.f1379q.indexOf("\n");
            }
            crashDetailBean.f1378p = i > 0 ? crashDetailBean.f1379q.substring(0, i) : "GET_FAIL";
            crashDetailBean.f1380r = axVar.f1695c;
            if (crashDetailBean.f1379q != null) {
                crashDetailBean.f1383u = C2273ap.m671c(crashDetailBean.f1379q.getBytes());
            }
            crashDetailBean.f1388z = axVar.f1694b;
            crashDetailBean.f1336A = axVar.f1693a;
            crashDetailBean.f1337B = "main(1)";
            crashDetailBean.f1347L = this.f1703c.mo27984s();
            crashDetailBean.f1370h = this.f1703c.mo27981p();
            crashDetailBean.f1371i = this.f1703c.mo27957A();
            crashDetailBean.f1384v = axVar.f1696d;
            crashDetailBean.f1351P = this.f1703c.f1489u;
            crashDetailBean.f1352Q = this.f1703c.f1445a;
            crashDetailBean.f1353R = this.f1703c.mo27961a();
            crashDetailBean.f1356U = this.f1703c.mo27991z();
            crashDetailBean.f1357V = this.f1703c.f1492x;
            crashDetailBean.f1358W = this.f1703c.mo27985t();
            crashDetailBean.f1359X = this.f1703c.mo27990y();
            crashDetailBean.f1387y = C2269ao.m624a();
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
        }
        return crashDetailBean;
    }

    /* renamed from: a */
    private static boolean m779a(String str, String str2, String str3) {
        TraceFileHelper.C2227a readTargetDumpInfo = TraceFileHelper.readTargetDumpInfo(str3, str, true);
        if (readTargetDumpInfo == null || readTargetDumpInfo.f1397d == null || readTargetDumpInfo.f1397d.isEmpty()) {
            C2265al.m611e("not found trace dump for %s", str3);
            return false;
        }
        StringBuilder sb = new StringBuilder(1024);
        String[] strArr = readTargetDumpInfo.f1397d.get("main");
        if (strArr != null && strArr.length >= 3) {
            sb.append("\"main\" tid=");
            sb.append(strArr[2]);
            sb.append(" :\n");
            sb.append(strArr[0]);
            sb.append("\n");
            sb.append(strArr[1]);
            sb.append("\n\n");
        }
        for (Map.Entry next : readTargetDumpInfo.f1397d.entrySet()) {
            if (!((String) next.getKey()).equals("main") && next.getValue() != null && ((String[]) next.getValue()).length >= 3) {
                sb.append("\"");
                sb.append((String) next.getKey());
                sb.append("\" tid=");
                sb.append(((String[]) next.getValue())[2]);
                sb.append(" :\n");
                sb.append(((String[]) next.getValue())[0]);
                sb.append("\n");
                sb.append(((String[]) next.getValue())[1]);
                sb.append("\n\n");
            }
        }
        return C2266am.m617a(str2, sb.toString(), sb.length() * 2);
    }

    /* renamed from: a */
    private static String m776a(List<C2312ba> list, long j) {
        if (list == null || list.isEmpty()) {
            return "main thread stack not enable";
        }
        StringBuilder sb = new StringBuilder(4096);
        sb.append("\n>>>>> 以下为anr过程中主线程堆栈记录，可根据堆栈出现次数推测在该堆栈阻塞的时间，出现次数越多对anr贡献越大，越可能是造成anr的原因 >>>>>\n");
        sb.append("\n>>>>> Thread Stack Traces Records Start >>>>>\n");
        for (int i = 0; i < list.size(); i++) {
            C2312ba baVar = list.get(i);
            sb.append("Thread name:");
            sb.append(baVar.f1721a);
            sb.append("\n");
            long j2 = baVar.f1722b - j;
            String str = j2 <= 0 ? "before " : "after ";
            sb.append("Got ");
            sb.append(str);
            sb.append("anr:");
            sb.append(Math.abs(j2));
            sb.append("ms\n");
            sb.append(baVar.f1723c);
            sb.append("\n");
            if (sb.length() * 2 >= 101376) {
                break;
            }
        }
        sb.append("\n<<<<< Thread Stack Traces Records End <<<<<\n");
        return sb.toString();
    }

    /* renamed from: a */
    public final boolean mo28068a(boolean z) {
        boolean compareAndSet = this.f1701a.compareAndSet(!z, z);
        C2265al.m609c("tryChangeAnrState to %s, success:%s", Boolean.valueOf(z), Boolean.valueOf(compareAndSet));
        return compareAndSet;
    }

    /* renamed from: c */
    private synchronized void m780c() {
        if (m784e()) {
            C2265al.m610d("start when started!", new Object[0]);
            return;
        }
        C23051 r0 = new FileObserver("/data/anr/") {
            public final void onEvent(int i, String str) {
                if (str != null) {
                    final String concat = "/data/anr/".concat(String.valueOf(str));
                    C2265al.m610d("watching file %s", concat);
                    if (!concat.contains("trace")) {
                        C2265al.m610d("not anr file %s", concat);
                        return;
                    }
                    C2304ay.this.f1704d.mo28017a(new Runnable() {
                        public final void run() {
                            C2304ay ayVar = C2304ay.this;
                            String str = concat;
                            if (ayVar.mo28068a(true)) {
                                try {
                                    C2265al.m609c("read trace first dump for create time!", new Object[0]);
                                    TraceFileHelper.C2227a readFirstDumpInfo = TraceFileHelper.readFirstDumpInfo(str, false);
                                    long j = readFirstDumpInfo != null ? readFirstDumpInfo.f1396c : -1;
                                    if (j == -1) {
                                        C2265al.m610d("trace dump fail could not get time!", new Object[0]);
                                        j = System.currentTimeMillis();
                                    }
                                    if (!ayVar.mo28067a(j)) {
                                        ayVar.mo28066a(j, str);
                                    }
                                } catch (Throwable th) {
                                    if (!C2265al.m605a(th)) {
                                        th.printStackTrace();
                                    }
                                    C2265al.m611e("handle anr error %s", th.getClass().toString());
                                }
                            }
                        }
                    });
                }
            }
        };
        this.f1710k = r0;
        try {
            r0.startWatching();
            C2265al.m604a("start anr monitor!", new Object[0]);
            this.f1704d.mo28017a(new Runnable() {
                public final void run() {
                    C2304ay.m777a(C2304ay.this);
                }
            });
        } catch (Throwable th) {
            this.f1710k = null;
            C2265al.m610d("start anr monitor failed!", new Object[0]);
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
        }
    }

    /* renamed from: d */
    private synchronized void m782d() {
        if (!m784e()) {
            C2265al.m610d("close when closed!", new Object[0]);
            return;
        }
        try {
            this.f1710k.stopWatching();
            this.f1710k = null;
            C2265al.m610d("close anr monitor!", new Object[0]);
        } catch (Throwable th) {
            C2265al.m610d("stop anr monitor failed!", new Object[0]);
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
        }
    }

    /* renamed from: e */
    private synchronized boolean m784e() {
        return this.f1710k != null;
    }

    /* renamed from: c */
    private synchronized void m781c(boolean z) {
        if (Build.VERSION.SDK_INT <= 19) {
            if (z) {
                m780c();
            } else {
                m782d();
            }
        } else if (z) {
            m786g();
        } else {
            m787h();
        }
    }

    /* renamed from: f */
    private synchronized boolean m785f() {
        return this.f1711l;
    }

    /* renamed from: d */
    private synchronized void m783d(boolean z) {
        if (this.f1711l != z) {
            C2265al.m604a("user change anr %b", Boolean.valueOf(z));
            this.f1711l = z;
        }
    }

    /* renamed from: b */
    public final void mo28070b(boolean z) {
        m783d(z);
        boolean f = m785f();
        C2248ac a = C2248ac.m533a();
        if (a != null) {
            f = f && a.mo27996c().f1318f;
        }
        if (f != m784e()) {
            C2265al.m604a("anr changed to %b", Boolean.valueOf(f));
            m781c(f);
        }
    }

    /* renamed from: b */
    public final synchronized void mo28069b() {
        C2265al.m610d("customer decides whether to open or close.", new Object[0]);
    }

    /* access modifiers changed from: package-private */
    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
        	at java.base/java.util.Objects.checkIndex(Objects.java:372)
        	at java.base/java.util.ArrayList.get(ArrayList.java:458)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01b8 A[Catch:{ all -> 0x0213 }] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01c0 A[Catch:{ all -> 0x0213 }] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01e2 A[Catch:{ all -> 0x0213 }] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x01e4 A[Catch:{ all -> 0x0213 }] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01eb A[Catch:{ all -> 0x0213 }] */
    /* renamed from: a */
    public final void mo28066a(long r19, java.lang.String r21) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            r0 = r21
            r4 = 0
            java.lang.String r5 = "anr time:%s"
            r6 = 1
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch:{ all -> 0x0213 }
            java.lang.Long r8 = java.lang.Long.valueOf(r19)     // Catch:{ all -> 0x0213 }
            r7[r4] = r8     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m609c(r5, r7)     // Catch:{ all -> 0x0213 }
            java.lang.Object r5 = r1.f1709j     // Catch:{ all -> 0x0213 }
            monitor-enter(r5)     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.bg r7 = r1.f1712m     // Catch:{ all -> 0x0210 }
            if (r7 == 0) goto L_0x0028
            java.lang.String r7 = "Disable record main stack trace."
            java.lang.Object[] r8 = new java.lang.Object[r4]     // Catch:{ all -> 0x0210 }
            com.tencent.bugly.proguard.C2265al.m609c(r7, r8)     // Catch:{ all -> 0x0210 }
            com.tencent.bugly.proguard.bg r7 = r1.f1712m     // Catch:{ all -> 0x0210 }
            r7.mo28083c()     // Catch:{ all -> 0x0210 }
        L_0x0028:
            monitor-exit(r5)     // Catch:{ all -> 0x0210 }
            android.os.Looper r5 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x0213 }
            java.lang.Thread r5 = r5.getThread()     // Catch:{ all -> 0x0213 }
            java.lang.String r5 = com.tencent.bugly.proguard.C2273ap.m645a((java.lang.Thread) r5)     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.aa r7 = r1.f1703c     // Catch:{ all -> 0x0213 }
            boolean r7 = r7.f1437R     // Catch:{ all -> 0x0213 }
            int r8 = com.tencent.bugly.proguard.C2293at.f1638h     // Catch:{ all -> 0x0213 }
            java.util.Map r7 = com.tencent.bugly.proguard.C2273ap.m652a((boolean) r7, (int) r8)     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.aa r8 = r1.f1703c     // Catch:{ all -> 0x0213 }
            boolean r8 = r8.mo27961a()     // Catch:{ all -> 0x0213 }
            r9 = 0
            if (r8 == 0) goto L_0x0074
            android.content.Context r8 = r1.f1706g     // Catch:{ all -> 0x0213 }
            boolean r11 = com.tencent.bugly.proguard.C2232ab.m505e(r8)     // Catch:{ all -> 0x0213 }
            if (r11 != 0) goto L_0x005a
            boolean r8 = com.tencent.bugly.proguard.C2232ab.m507f(r8)     // Catch:{ all -> 0x0213 }
            if (r8 == 0) goto L_0x0058
            goto L_0x005a
        L_0x0058:
            r8 = 0
            goto L_0x005b
        L_0x005a:
            r8 = 1
        L_0x005b:
            java.lang.String r11 = "isAnrCrashDevice:%s"
            java.lang.Object[] r12 = new java.lang.Object[r6]     // Catch:{ all -> 0x0213 }
            java.lang.Boolean r13 = java.lang.Boolean.valueOf(r8)     // Catch:{ all -> 0x0213 }
            r12[r4] = r13     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m609c(r11, r12)     // Catch:{ all -> 0x0213 }
            if (r8 == 0) goto L_0x006b
            goto L_0x0074
        L_0x006b:
            android.app.ActivityManager r8 = r1.f1702b     // Catch:{ all -> 0x0213 }
            r11 = 21000(0x5208, double:1.03754E-319)
            android.app.ActivityManager$ProcessErrorStateInfo r8 = com.tencent.bugly.proguard.C2310az.m793a(r8, r11)     // Catch:{ all -> 0x0213 }
            goto L_0x007a
        L_0x0074:
            android.app.ActivityManager r8 = r1.f1702b     // Catch:{ all -> 0x0213 }
            android.app.ActivityManager$ProcessErrorStateInfo r8 = com.tencent.bugly.proguard.C2310az.m793a(r8, r9)     // Catch:{ all -> 0x0213 }
        L_0x007a:
            if (r8 != 0) goto L_0x0087
            java.lang.String r0 = "proc state is invisible or not my proc!"
            java.lang.Object[] r2 = new java.lang.Object[r4]     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m609c(r0, r2)     // Catch:{ all -> 0x0213 }
            r1.mo28068a((boolean) r4)
            return
        L_0x0087:
            com.tencent.bugly.proguard.ax r11 = new com.tencent.bugly.proguard.ax     // Catch:{ all -> 0x0213 }
            r11.<init>()     // Catch:{ all -> 0x0213 }
            r11.f1695c = r2     // Catch:{ all -> 0x0213 }
            if (r8 == 0) goto L_0x0093
            java.lang.String r12 = r8.processName     // Catch:{ all -> 0x0213 }
            goto L_0x009b
        L_0x0093:
            int r12 = android.os.Process.myPid()     // Catch:{ all -> 0x0213 }
            java.lang.String r12 = com.tencent.bugly.proguard.C2369z.m1058a((int) r12)     // Catch:{ all -> 0x0213 }
        L_0x009b:
            r11.f1693a = r12     // Catch:{ all -> 0x0213 }
            if (r8 == 0) goto L_0x00a2
            java.lang.String r12 = r8.shortMsg     // Catch:{ all -> 0x0213 }
            goto L_0x00a4
        L_0x00a2:
            java.lang.String r12 = ""
        L_0x00a4:
            r11.f1698f = r12     // Catch:{ all -> 0x0213 }
            if (r8 == 0) goto L_0x00ab
            java.lang.String r8 = r8.longMsg     // Catch:{ all -> 0x0213 }
            goto L_0x00ad
        L_0x00ab:
            java.lang.String r8 = ""
        L_0x00ad:
            r11.f1697e = r8     // Catch:{ all -> 0x0213 }
            r11.f1694b = r7     // Catch:{ all -> 0x0213 }
            r11.f1699g = r5     // Catch:{ all -> 0x0213 }
            java.lang.String r5 = r11.f1699g     // Catch:{ all -> 0x0213 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x0213 }
            if (r5 == 0) goto L_0x00bf
            java.lang.String r5 = "main stack is null , some error may be encountered."
            r11.f1699g = r5     // Catch:{ all -> 0x0213 }
        L_0x00bf:
            java.lang.String r5 = "anr time:%d\ntrace file:%s\nproc:%s\nmain stack:%s\nshort msg:%s\nlong msg:%s\n threads:%d"
            r7 = 7
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x0213 }
            long r12 = r11.f1695c     // Catch:{ all -> 0x0213 }
            java.lang.Long r8 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x0213 }
            r7[r4] = r8     // Catch:{ all -> 0x0213 }
            java.lang.String r8 = r11.f1696d     // Catch:{ all -> 0x0213 }
            r7[r6] = r8     // Catch:{ all -> 0x0213 }
            r8 = 2
            java.lang.String r12 = r11.f1693a     // Catch:{ all -> 0x0213 }
            r7[r8] = r12     // Catch:{ all -> 0x0213 }
            r8 = 3
            java.lang.String r12 = r11.f1699g     // Catch:{ all -> 0x0213 }
            r7[r8] = r12     // Catch:{ all -> 0x0213 }
            r8 = 4
            java.lang.String r12 = r11.f1698f     // Catch:{ all -> 0x0213 }
            r7[r8] = r12     // Catch:{ all -> 0x0213 }
            r8 = 5
            java.lang.String r12 = r11.f1697e     // Catch:{ all -> 0x0213 }
            r7[r8] = r12     // Catch:{ all -> 0x0213 }
            r8 = 6
            java.util.Map<java.lang.String, java.lang.String> r12 = r11.f1694b     // Catch:{ all -> 0x0213 }
            if (r12 != 0) goto L_0x00eb
            r12 = 0
            goto L_0x00f1
        L_0x00eb:
            java.util.Map<java.lang.String, java.lang.String> r12 = r11.f1694b     // Catch:{ all -> 0x0213 }
            int r12 = r12.size()     // Catch:{ all -> 0x0213 }
        L_0x00f1:
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ all -> 0x0213 }
            r7[r8] = r12     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m609c(r5, r7)     // Catch:{ all -> 0x0213 }
            java.lang.String r5 = "found visible anr , start to upload!"
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r5, (java.lang.Object[]) r7)     // Catch:{ all -> 0x0213 }
            java.lang.String r5 = "trace file:%s"
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch:{ all -> 0x0213 }
            r7[r4] = r0     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m609c(r5, r7)     // Catch:{ all -> 0x0213 }
            boolean r5 = android.text.TextUtils.isEmpty(r21)     // Catch:{ all -> 0x0213 }
            if (r5 != 0) goto L_0x01a0
            java.io.File r5 = new java.io.File     // Catch:{ all -> 0x0213 }
            r5.<init>(r0)     // Catch:{ all -> 0x0213 }
            boolean r5 = r5.exists()     // Catch:{ all -> 0x0213 }
            if (r5 != 0) goto L_0x011d
            goto L_0x01a0
        L_0x011d:
            java.io.File r5 = new java.io.File     // Catch:{ all -> 0x0213 }
            java.lang.String r7 = r1.f1705e     // Catch:{ all -> 0x0213 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0213 }
            java.lang.String r12 = "bugly_trace_"
            r8.<init>(r12)     // Catch:{ all -> 0x0213 }
            r8.append(r2)     // Catch:{ all -> 0x0213 }
            java.lang.String r12 = ".txt"
            r8.append(r12)     // Catch:{ all -> 0x0213 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0213 }
            r5.<init>(r7, r8)     // Catch:{ all -> 0x0213 }
            java.lang.String r7 = "trace file exists"
            java.lang.Object[] r8 = new java.lang.Object[r4]     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m609c(r7, r8)     // Catch:{ all -> 0x0213 }
            java.lang.String r7 = "/data/anr/"
            boolean r7 = r0.startsWith(r7)     // Catch:{ all -> 0x0213 }
            if (r7 == 0) goto L_0x015e
            java.lang.String r7 = r5.getAbsolutePath()     // Catch:{ all -> 0x0213 }
            java.lang.String r8 = r11.f1693a     // Catch:{ all -> 0x0213 }
            boolean r0 = m779a((java.lang.String) r0, (java.lang.String) r7, (java.lang.String) r8)     // Catch:{ all -> 0x0213 }
            java.lang.String r7 = "backup trace isOK:%s"
            java.lang.Object[] r8 = new java.lang.Object[r6]     // Catch:{ all -> 0x0213 }
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ all -> 0x0213 }
            r8[r4] = r0     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r7, (java.lang.Object[]) r8)     // Catch:{ all -> 0x0213 }
            goto L_0x0174
        L_0x015e:
            java.io.File r7 = new java.io.File     // Catch:{ all -> 0x0213 }
            r7.<init>(r0)     // Catch:{ all -> 0x0213 }
            boolean r0 = r7.renameTo(r5)     // Catch:{ all -> 0x0213 }
            java.lang.String r7 = "trace file rename :%s"
            java.lang.Object[] r8 = new java.lang.Object[r6]     // Catch:{ all -> 0x0213 }
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ all -> 0x0213 }
            r8[r4] = r0     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r7, (java.lang.Object[]) r8)     // Catch:{ all -> 0x0213 }
        L_0x0174:
            r0 = 0
            java.lang.Object r7 = r1.f1709j     // Catch:{ all -> 0x0213 }
            monitor-enter(r7)     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.bg r8 = r1.f1712m     // Catch:{ all -> 0x019d }
            if (r8 == 0) goto L_0x0182
            com.tencent.bugly.proguard.bf r0 = r8.f1751a     // Catch:{ all -> 0x019d }
            java.util.List r0 = r0.mo28078c()     // Catch:{ all -> 0x019d }
        L_0x0182:
            monitor-exit(r7)     // Catch:{ all -> 0x019d }
            if (r0 == 0) goto L_0x0196
            java.lang.String r0 = m776a((java.util.List<com.tencent.bugly.proguard.C2312ba>) r0, (long) r2)     // Catch:{ all -> 0x0213 }
            java.lang.String r7 = "save main stack trace"
            java.lang.Object[] r8 = new java.lang.Object[r4]     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m609c(r7, r8)     // Catch:{ all -> 0x0213 }
            r7 = 2147483647(0x7fffffff, double:1.060997895E-314)
            com.tencent.bugly.proguard.C2266am.m615a((java.io.File) r5, (java.lang.String) r0, (long) r7, (boolean) r6)     // Catch:{ all -> 0x0213 }
        L_0x0196:
            java.lang.String r0 = r5.getAbsolutePath()     // Catch:{ all -> 0x0213 }
            r11.f1696d = r0     // Catch:{ all -> 0x0213 }
            goto L_0x01a7
        L_0x019d:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x019d }
            throw r0     // Catch:{ all -> 0x0213 }
        L_0x01a0:
            java.lang.String r0 = "trace file is null or not exists, just ignore"
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m609c(r0, r5)     // Catch:{ all -> 0x0213 }
        L_0x01a7:
            com.tencent.bugly.crashreport.crash.CrashDetailBean r0 = r1.m774a((com.tencent.bugly.proguard.C2303ax) r11)     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.at r5 = com.tencent.bugly.proguard.C2293at.m738a()     // Catch:{ all -> 0x0213 }
            r5.mo28040a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r0)     // Catch:{ all -> 0x0213 }
            long r7 = r0.f1362a     // Catch:{ all -> 0x0213 }
            int r5 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r5 < 0) goto L_0x01c0
            java.lang.String r5 = "backup anr record success!"
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r5, (java.lang.Object[]) r7)     // Catch:{ all -> 0x0213 }
            goto L_0x01c7
        L_0x01c0:
            java.lang.String r5 = "backup anr record fail!"
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.C2265al.m610d(r5, r7)     // Catch:{ all -> 0x0213 }
        L_0x01c7:
            java.lang.String r12 = "ANR"
            java.lang.String r13 = com.tencent.bugly.proguard.C2273ap.m642a((long) r19)     // Catch:{ all -> 0x0213 }
            java.lang.String r14 = r11.f1693a     // Catch:{ all -> 0x0213 }
            java.lang.String r15 = "main"
            java.lang.String r2 = r11.f1699g     // Catch:{ all -> 0x0213 }
            r16 = r2
            r17 = r0
            com.tencent.bugly.proguard.C2277as.m696a(r12, r13, r14, r15, r16, r17)     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.as r2 = r1.f1708i     // Catch:{ all -> 0x0213 }
            boolean r3 = com.tencent.bugly.proguard.C2232ab.m516o()     // Catch:{ all -> 0x0213 }
            if (r3 != 0) goto L_0x01e4
            r3 = 1
            goto L_0x01e5
        L_0x01e4:
            r3 = 0
        L_0x01e5:
            boolean r2 = r2.mo28035a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r0, (boolean) r3)     // Catch:{ all -> 0x0213 }
            if (r2 != 0) goto L_0x01f0
            com.tencent.bugly.proguard.as r2 = r1.f1708i     // Catch:{ all -> 0x0213 }
            r2.mo28037b((com.tencent.bugly.crashreport.crash.CrashDetailBean) r0, (boolean) r6)     // Catch:{ all -> 0x0213 }
        L_0x01f0:
            com.tencent.bugly.proguard.as r2 = r1.f1708i     // Catch:{ all -> 0x0213 }
            r2.mo28033a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r0)     // Catch:{ all -> 0x0213 }
            java.lang.Object r2 = r1.f1709j     // Catch:{ all -> 0x0213 }
            monitor-enter(r2)     // Catch:{ all -> 0x0213 }
            com.tencent.bugly.proguard.bg r0 = r1.f1712m     // Catch:{ all -> 0x020d }
            if (r0 == 0) goto L_0x0208
            java.lang.String r0 = "Finish anr process."
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch:{ all -> 0x020d }
            com.tencent.bugly.proguard.C2265al.m609c(r0, r3)     // Catch:{ all -> 0x020d }
            com.tencent.bugly.proguard.bg r0 = r1.f1712m     // Catch:{ all -> 0x020d }
            r0.mo28084d()     // Catch:{ all -> 0x020d }
        L_0x0208:
            monitor-exit(r2)     // Catch:{ all -> 0x020d }
            r1.mo28068a((boolean) r4)
            return
        L_0x020d:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x020d }
            throw r0     // Catch:{ all -> 0x0213 }
        L_0x0210:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0210 }
            throw r0     // Catch:{ all -> 0x0213 }
        L_0x0213:
            r0 = move-exception
            com.tencent.bugly.proguard.C2265al.m608b(r0)     // Catch:{ all -> 0x021b }
            r1.mo28068a((boolean) r4)
            return
        L_0x021b:
            r0 = move-exception
            r2 = r0
            r1.mo28068a((boolean) r4)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2304ay.mo28066a(long, java.lang.String):void");
    }

    /* renamed from: g */
    private synchronized void m786g() {
        if (m784e()) {
            C2265al.m610d("start when started!", new Object[0]);
        } else if (!TextUtils.isEmpty(this.f1705e)) {
            synchronized (this.f1709j) {
                C2318bg bgVar = this.f1712m;
                if (bgVar == null || !bgVar.isAlive()) {
                    C2318bg bgVar2 = new C2318bg();
                    this.f1712m = bgVar2;
                    boolean z = this.f1703c.f1438S;
                    bgVar2.f1752b = z;
                    C2265al.m609c("set record stack trace enable:".concat(String.valueOf(z)), new Object[0]);
                    C2318bg bgVar3 = this.f1712m;
                    StringBuilder sb = new StringBuilder("Bugly-ThreadMonitor");
                    int i = this.f1713n;
                    this.f1713n = i + 1;
                    sb.append(i);
                    bgVar3.setName(sb.toString());
                    this.f1712m.mo28082b();
                }
            }
            C23083 r0 = new FileObserver(this.f1705e) {
                public final void onEvent(int i, String str) {
                    if (str != null) {
                        boolean z = true;
                        C2265al.m610d("observe file, dir:%s fileName:%s", C2304ay.this.f1705e, str);
                        if (!str.startsWith("manual_bugly_trace_") || !str.endsWith(Constants.SHARE_TEXT_NAME)) {
                            z = false;
                        }
                        if (!z) {
                            C2265al.m609c("not manual trace file, ignore.", new Object[0]);
                        } else if (!C2304ay.this.f1701a.get()) {
                            C2265al.m609c("proc is not in anr, just ignore", new Object[0]);
                        } else if (C2304ay.this.f1703c.mo27961a()) {
                            C2265al.m609c("Found foreground anr, resend sigquit immediately.", new Object[0]);
                            NativeCrashHandler.getInstance().resendSigquit();
                            long a = C2266am.m612a(str, "manual_bugly_trace_", Constants.SHARE_TEXT_NAME);
                            C2304ay ayVar = C2304ay.this;
                            ayVar.mo28066a(a, C2304ay.this.f1705e + "/" + str);
                            C2265al.m609c("Finish handling one anr.", new Object[0]);
                        } else {
                            C2265al.m609c("Found background anr, resend sigquit later.", new Object[0]);
                            long a2 = C2266am.m612a(str, "manual_bugly_trace_", Constants.SHARE_TEXT_NAME);
                            C2304ay ayVar2 = C2304ay.this;
                            ayVar2.mo28066a(a2, C2304ay.this.f1705e + "/" + str);
                            C2265al.m609c("Finish handling one anr, now resend sigquit.", new Object[0]);
                            NativeCrashHandler.getInstance().resendSigquit();
                        }
                    }
                }
            };
            this.f1710k = r0;
            try {
                r0.startWatching();
                C2265al.m604a("startWatchingPrivateAnrDir! dumFilePath is %s", this.f1705e);
                this.f1704d.mo28017a(new Runnable() {
                    public final void run() {
                        C2304ay.m777a(C2304ay.this);
                    }
                });
            } catch (Throwable th) {
                this.f1710k = null;
                C2265al.m610d("startWatchingPrivateAnrDir failed!", new Object[0]);
                if (!C2265al.m605a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    /* renamed from: a */
    public final boolean mo28067a(long j) {
        if (Math.abs(j - this.f1714o) < 10000) {
            C2265al.m610d("should not process ANR too Fre in %dms", 10000);
            return true;
        }
        this.f1714o = j;
        return false;
    }

    /* renamed from: h */
    private synchronized void m787h() {
        if (!m784e()) {
            C2265al.m610d("close when closed!", new Object[0]);
            return;
        }
        synchronized (this.f1709j) {
            C2318bg bgVar = this.f1712m;
            if (bgVar != null) {
                bgVar.mo28081a();
                this.f1712m = null;
            }
        }
        C2265al.m604a("stopWatchingPrivateAnrDir", new Object[0]);
        try {
            this.f1710k.stopWatching();
            this.f1710k = null;
            C2265al.m610d("close anr monitor!", new Object[0]);
        } catch (Throwable th) {
            C2265al.m610d("stop anr monitor failed!", new Object[0]);
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
        }
    }

    /* renamed from: a */
    static /* synthetic */ void m777a(C2304ay ayVar) {
        long currentTimeMillis = (C2293at.f1639i + System.currentTimeMillis()) - C2273ap.m661b();
        C2266am.m614a(ayVar.f1705e, "bugly_trace_", Constants.SHARE_TEXT_NAME, currentTimeMillis);
        C2266am.m614a(ayVar.f1705e, "manual_bugly_trace_", Constants.SHARE_TEXT_NAME, currentTimeMillis);
        C2266am.m614a(ayVar.f1705e, "main_stack_record_", Constants.SHARE_TEXT_NAME, currentTimeMillis);
        C2266am.m614a(ayVar.f1705e, "main_stack_record_", ".txt.merged", currentTimeMillis);
    }
}
