package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.content.Context;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.bd */
/* compiled from: BUGLY */
public final class C2315bd implements NativeExceptionHandler {

    /* renamed from: a */
    private final Context f1739a;

    /* renamed from: b */
    private final C2277as f1740b;

    /* renamed from: c */
    private final C2231aa f1741c;

    /* renamed from: d */
    private final C2248ac f1742d;

    public C2315bd(Context context, C2231aa aaVar, C2277as asVar, C2248ac acVar) {
        this.f1739a = context;
        this.f1740b = asVar;
        this.f1741c = aaVar;
        this.f1742d = acVar;
    }

    public final CrashDetailBean packageCrashDatas(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, byte[] bArr, Map<String, String> map, boolean z, boolean z2) {
        int i;
        String str12;
        int indexOf;
        String str13 = str;
        String str14 = str8;
        byte[] bArr2 = bArr;
        boolean i2 = C2293at.m738a().mo28049i();
        if (i2) {
            C2265al.m611e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.f1364b = 1;
        crashDetailBean.f1367e = this.f1741c.mo27972g();
        crashDetailBean.f1368f = this.f1741c.f1483o;
        crashDetailBean.f1369g = this.f1741c.mo27982q();
        crashDetailBean.f1375m = this.f1741c.mo27970f();
        crashDetailBean.f1376n = str3;
        String str15 = "";
        crashDetailBean.f1377o = i2 ? " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]" : str15;
        crashDetailBean.f1378p = str4;
        if (str5 != null) {
            str15 = str5;
        }
        crashDetailBean.f1379q = str15;
        crashDetailBean.f1380r = j;
        crashDetailBean.f1383u = C2273ap.m671c(crashDetailBean.f1379q.getBytes());
        crashDetailBean.f1336A = str13;
        crashDetailBean.f1337B = str2;
        crashDetailBean.f1347L = this.f1741c.mo27984s();
        crashDetailBean.f1370h = this.f1741c.mo27981p();
        crashDetailBean.f1371i = this.f1741c.mo27957A();
        crashDetailBean.f1384v = str14;
        String dumpFilePath = NativeCrashHandler.getInstance() != null ? NativeCrashHandler.getDumpFilePath() : null;
        String a = C2316be.m805a(dumpFilePath, str14);
        if (!C2273ap.m657a(a)) {
            crashDetailBean.f1361Z = a;
        }
        crashDetailBean.f1363aa = C2316be.m809b(dumpFilePath);
        crashDetailBean.f1385w = C2316be.m804a(str9, C2293at.f1636f, C2293at.f1640j, C2293at.f1645o);
        crashDetailBean.f1386x = C2316be.m804a(str10, C2293at.f1636f, (String) null, true);
        crashDetailBean.f1349N = str7;
        crashDetailBean.f1350O = str6;
        crashDetailBean.f1351P = str11;
        crashDetailBean.f1341F = this.f1741c.mo27977k();
        crashDetailBean.f1342G = this.f1741c.mo27976j();
        crashDetailBean.f1343H = this.f1741c.mo27978l();
        crashDetailBean.f1344I = C2232ab.m498b(this.f1739a);
        crashDetailBean.f1345J = C2232ab.m508g();
        crashDetailBean.f1346K = C2232ab.m509h();
        if (z) {
            crashDetailBean.f1338C = C2232ab.m511j();
            crashDetailBean.f1339D = C2232ab.m506f();
            crashDetailBean.f1340E = C2232ab.m513l();
            crashDetailBean.f1387y = C2269ao.m624a();
            crashDetailBean.f1352Q = this.f1741c.f1445a;
            crashDetailBean.f1353R = this.f1741c.mo27961a();
            crashDetailBean.f1388z = C2273ap.m652a(this.f1741c.f1436Q, C2293at.f1638h);
            int indexOf2 = crashDetailBean.f1379q.indexOf("java:\n");
            if (indexOf2 > 0 && (i = indexOf2 + 6) < crashDetailBean.f1379q.length()) {
                String str16 = crashDetailBean.f1379q;
                String substring = str16.substring(i, str16.length() - 1);
                if (substring.length() > 0 && crashDetailBean.f1388z.containsKey(crashDetailBean.f1337B) && (indexOf = str12.indexOf(substring)) > 0) {
                    String substring2 = (str12 = crashDetailBean.f1388z.get(crashDetailBean.f1337B)).substring(indexOf);
                    crashDetailBean.f1388z.put(crashDetailBean.f1337B, substring2);
                    crashDetailBean.f1379q = crashDetailBean.f1379q.substring(0, i);
                    crashDetailBean.f1379q += substring2;
                }
            }
            if (str13 == null) {
                crashDetailBean.f1336A = this.f1741c.f1472d;
            }
            crashDetailBean.f1356U = this.f1741c.mo27991z();
            crashDetailBean.f1357V = this.f1741c.f1492x;
            crashDetailBean.f1358W = this.f1741c.mo27985t();
            crashDetailBean.f1359X = this.f1741c.mo27990y();
        } else {
            crashDetailBean.f1338C = -1;
            crashDetailBean.f1339D = -1;
            crashDetailBean.f1340E = -1;
            if (crashDetailBean.f1385w == null) {
                crashDetailBean.f1385w = "This crash occurred at last process! Log is miss, when get an terrible ABRT Native Exception etc.";
            }
            crashDetailBean.f1352Q = -1;
            crashDetailBean.f1356U = -1;
            crashDetailBean.f1357V = -1;
            crashDetailBean.f1358W = map;
            crashDetailBean.f1359X = this.f1741c.mo27990y();
            crashDetailBean.f1388z = null;
            if (str13 == null) {
                crashDetailBean.f1336A = "unknown(record)";
            }
            if (bArr2 != null) {
                crashDetailBean.f1387y = bArr2;
            }
        }
        return crashDetailBean;
    }

    public final boolean getAndUpdateAnrState() {
        if (C2304ay.m775a() == null) {
            return false;
        }
        C2304ay a = C2304ay.m775a();
        if (a.f1701a.get()) {
            C2265al.m609c("anr is processing, return", new Object[0]);
            return false;
        }
        ActivityManager activityManager = a.f1702b;
        if (!(!C2369z.m1062a(activityManager) && C2310az.m793a(activityManager, 0) != null)) {
            C2265al.m609c("proc is not in anr, wait next check", new Object[0]);
            return false;
        } else if (a.mo28067a(System.currentTimeMillis())) {
            return false;
        } else {
            return a.mo28068a(true);
        }
    }

    public final void handleNativeException(int i, int i2, long j, long j2, String str, String str2, String str3, String str4, int i3, String str5, int i4, int i5, int i6, String str6, String str7) {
        int i7 = i;
        C2265al.m604a("Native Crash Happen v1", new Object[0]);
        handleNativeException2(i, i2, j, j2, str, str2, str3, str4, i3, str5, i4, i5, i6, str6, str7, (String[]) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x009c A[Catch:{ all -> 0x0227 }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00ba A[Catch:{ all -> 0x0227 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00f2 A[Catch:{ all -> 0x0227 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0105 A[SYNTHETIC, Splitter:B:28:0x0105] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0148 A[Catch:{ all -> 0x0227 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x014d A[Catch:{ all -> 0x0227 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0153 A[Catch:{ all -> 0x0227 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x01a6 A[Catch:{ all -> 0x0223 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01af A[Catch:{ all -> 0x0223 }] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x022f  */
    /* JADX WARNING: Removed duplicated region for block: B:79:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleNativeException2(int r28, int r29, long r30, long r32, java.lang.String r34, java.lang.String r35, java.lang.String r36, java.lang.String r37, int r38, java.lang.String r39, int r40, int r41, int r42, java.lang.String r43, java.lang.String r44, java.lang.String[] r45) {
        /*
            r27 = this;
            r14 = r27
            r0 = r29
            r13 = r35
            r1 = r40
            r12 = 0
            java.lang.Object[] r2 = new java.lang.Object[r12]
            java.lang.String r3 = "Native Crash Happen v2"
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r3, (java.lang.Object[]) r2)
            java.lang.String r2 = ")"
            java.lang.String r3 = "("
            if (r38 <= 0) goto L_0x0031
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0227 }
            r4.<init>()     // Catch:{ all -> 0x0227 }
            r5 = r34
            r4.append(r5)     // Catch:{ all -> 0x0227 }
            r4.append(r3)     // Catch:{ all -> 0x0227 }
            r6 = r39
            r4.append(r6)     // Catch:{ all -> 0x0227 }
            r4.append(r2)     // Catch:{ all -> 0x0227 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0227 }
            r11 = r4
            goto L_0x0036
        L_0x0031:
            r5 = r34
            r6 = r39
            r11 = r5
        L_0x0036:
            java.lang.String r10 = com.tencent.bugly.proguard.C2316be.m803a((java.lang.String) r36)     // Catch:{ all -> 0x0227 }
            java.util.Map r4 = m798a(r45)     // Catch:{ all -> 0x0227 }
            java.lang.String r5 = "HasPendingException"
            java.lang.Object r5 = r4.get(r5)     // Catch:{ all -> 0x0227 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x0227 }
            r9 = 1
            if (r5 == 0) goto L_0x005b
            java.lang.String r7 = "true"
            boolean r5 = r5.equals(r7)     // Catch:{ all -> 0x0227 }
            if (r5 == 0) goto L_0x005b
            java.lang.String r5 = "Native crash happened with a Java pending exception."
            java.lang.Object[] r7 = new java.lang.Object[r12]     // Catch:{ all -> 0x0227 }
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r5, (java.lang.Object[]) r7)     // Catch:{ all -> 0x0227 }
            r18 = 1
            goto L_0x005d
        L_0x005b:
            r18 = 0
        L_0x005d:
            com.tencent.bugly.proguard.aa r5 = r14.f1741c     // Catch:{ all -> 0x0227 }
            java.lang.String r7 = "ExceptionProcessName"
            java.lang.Object r7 = r4.get(r7)     // Catch:{ all -> 0x0227 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ all -> 0x0227 }
            if (r7 == 0) goto L_0x007a
            int r8 = r7.length()     // Catch:{ all -> 0x0227 }
            if (r8 != 0) goto L_0x0070
            goto L_0x007a
        L_0x0070:
            java.lang.String r5 = "Name of crash process: %s"
            java.lang.Object[] r8 = new java.lang.Object[r9]     // Catch:{ all -> 0x0227 }
            r8[r12] = r7     // Catch:{ all -> 0x0227 }
            com.tencent.bugly.proguard.C2265al.m609c(r5, r8)     // Catch:{ all -> 0x0227 }
            goto L_0x007c
        L_0x007a:
            java.lang.String r7 = r5.f1472d     // Catch:{ all -> 0x0227 }
        L_0x007c:
            r19 = r7
            java.lang.String r5 = "ExceptionThreadName"
            java.lang.Object r5 = r4.get(r5)     // Catch:{ all -> 0x0227 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x0227 }
            java.lang.String r7 = "crash thread name:%s tid:%s"
            r8 = 2
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x0227 }
            r8[r12] = r5     // Catch:{ all -> 0x0227 }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r29)     // Catch:{ all -> 0x0227 }
            r8[r9] = r15     // Catch:{ all -> 0x0227 }
            com.tencent.bugly.proguard.C2265al.m609c(r7, r8)     // Catch:{ all -> 0x0227 }
            boolean r7 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x0227 }
            if (r7 == 0) goto L_0x00ba
            java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0227 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0227 }
            r7.<init>()     // Catch:{ all -> 0x0227 }
            java.lang.String r5 = r5.getName()     // Catch:{ all -> 0x0227 }
            r7.append(r5)     // Catch:{ all -> 0x0227 }
            r7.append(r3)     // Catch:{ all -> 0x0227 }
            r7.append(r0)     // Catch:{ all -> 0x0227 }
            r7.append(r2)     // Catch:{ all -> 0x0227 }
            java.lang.String r0 = r7.toString()     // Catch:{ all -> 0x0227 }
            goto L_0x00cf
        L_0x00ba:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0227 }
            r7.<init>()     // Catch:{ all -> 0x0227 }
            r7.append(r5)     // Catch:{ all -> 0x0227 }
            r7.append(r3)     // Catch:{ all -> 0x0227 }
            r7.append(r0)     // Catch:{ all -> 0x0227 }
            r7.append(r2)     // Catch:{ all -> 0x0227 }
            java.lang.String r0 = r7.toString()     // Catch:{ all -> 0x0227 }
        L_0x00cf:
            r7 = 1000(0x3e8, double:4.94E-321)
            long r15 = r30 * r7
            long r7 = r32 / r7
            long r7 = r7 + r15
            java.lang.String r5 = "SysLogPath"
            java.lang.Object r5 = r4.get(r5)     // Catch:{ all -> 0x0227 }
            r20 = r5
            java.lang.String r20 = (java.lang.String) r20     // Catch:{ all -> 0x0227 }
            java.lang.String r5 = "JniLogPath"
            java.lang.Object r4 = r4.get(r5)     // Catch:{ all -> 0x0227 }
            r21 = r4
            java.lang.String r21 = (java.lang.String) r21     // Catch:{ all -> 0x0227 }
            com.tencent.bugly.proguard.ac r4 = r14.f1742d     // Catch:{ all -> 0x0227 }
            boolean r4 = r4.mo27995b()     // Catch:{ all -> 0x0227 }
            if (r4 != 0) goto L_0x00f9
            java.lang.String r4 = "no remote but still store!"
            java.lang.Object[] r5 = new java.lang.Object[r12]     // Catch:{ all -> 0x0227 }
            com.tencent.bugly.proguard.C2265al.m610d(r4, r5)     // Catch:{ all -> 0x0227 }
        L_0x00f9:
            com.tencent.bugly.proguard.ac r4 = r14.f1742d     // Catch:{ all -> 0x0227 }
            com.tencent.bugly.crashreport.common.strategy.StrategyBean r4 = r4.mo27996c()     // Catch:{ all -> 0x0227 }
            boolean r4 = r4.f1318f     // Catch:{ all -> 0x0227 }
            java.lang.String r5 = "\n"
            if (r4 != 0) goto L_0x0146
            com.tencent.bugly.proguard.ac r4 = r14.f1742d     // Catch:{ all -> 0x0227 }
            boolean r4 = r4.mo27995b()     // Catch:{ all -> 0x0227 }
            if (r4 == 0) goto L_0x0146
            java.lang.String r1 = "crash report was closed by remote , will not upload to Bugly , print local for helpful!"
            java.lang.Object[] r2 = new java.lang.Object[r12]     // Catch:{ all -> 0x0227 }
            com.tencent.bugly.proguard.C2265al.m611e(r1, r2)     // Catch:{ all -> 0x0227 }
            java.lang.String r1 = "NATIVE_CRASH"
            java.lang.String r2 = com.tencent.bugly.proguard.C2273ap.m640a()     // Catch:{ all -> 0x0227 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0227 }
            r3.<init>()     // Catch:{ all -> 0x0227 }
            r3.append(r11)     // Catch:{ all -> 0x0227 }
            r3.append(r5)     // Catch:{ all -> 0x0227 }
            r3.append(r13)     // Catch:{ all -> 0x0227 }
            r3.append(r5)     // Catch:{ all -> 0x0227 }
            r3.append(r10)     // Catch:{ all -> 0x0227 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0227 }
            r4 = 0
            r28 = r1
            r29 = r2
            r30 = r19
            r31 = r0
            r32 = r3
            r33 = r4
            com.tencent.bugly.proguard.C2277as.m696a(r28, r29, r30, r31, r32, r33)     // Catch:{ all -> 0x0227 }
            com.tencent.bugly.proguard.C2273ap.m667b((java.lang.String) r37)     // Catch:{ all -> 0x0227 }
            return
        L_0x0146:
            if (r38 <= 0) goto L_0x014d
            java.lang.String r4 = "KERNEL"
            r22 = r4
            goto L_0x014f
        L_0x014d:
            r22 = r6
        L_0x014f:
            java.lang.String r4 = "UNKNOWN"
            if (r38 > 0) goto L_0x017b
            if (r1 <= 0) goto L_0x0159
            java.lang.String r4 = com.tencent.bugly.proguard.C2369z.m1058a((int) r40)     // Catch:{ all -> 0x0227 }
        L_0x0159:
            java.lang.String r6 = java.lang.String.valueOf(r40)     // Catch:{ all -> 0x0227 }
            boolean r6 = r4.equals(r6)     // Catch:{ all -> 0x0227 }
            if (r6 != 0) goto L_0x017b
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0227 }
            r6.<init>()     // Catch:{ all -> 0x0227 }
            r6.append(r4)     // Catch:{ all -> 0x0227 }
            r6.append(r3)     // Catch:{ all -> 0x0227 }
            r6.append(r1)     // Catch:{ all -> 0x0227 }
            r6.append(r2)     // Catch:{ all -> 0x0227 }
            java.lang.String r1 = r6.toString()     // Catch:{ all -> 0x0227 }
            r23 = r1
            goto L_0x017d
        L_0x017b:
            r23 = r4
        L_0x017d:
            r15 = 0
            r16 = 0
            r17 = 1
            r1 = r27
            r2 = r19
            r3 = r0
            r6 = r5
            r4 = r7
            r8 = r6
            r6 = r11
            r7 = r35
            r24 = r8
            r8 = r10
            r9 = r22
            r25 = r10
            r10 = r23
            r26 = r11
            r11 = r37
            r12 = r20
            r13 = r21
            r14 = r44
            com.tencent.bugly.crashreport.crash.CrashDetailBean r1 = r1.packageCrashDatas(r2, r3, r4, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)     // Catch:{ all -> 0x0223 }
            if (r1 != 0) goto L_0x01af
            java.lang.String r0 = "pkg crash datas fail!"
            r2 = 0
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x0223 }
            com.tencent.bugly.proguard.C2265al.m611e(r0, r1)     // Catch:{ all -> 0x0223 }
            return
        L_0x01af:
            r2 = 0
            java.lang.String r3 = "NATIVE_CRASH"
            java.lang.String r4 = com.tencent.bugly.proguard.C2273ap.m640a()     // Catch:{ all -> 0x0223 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0223 }
            r5.<init>()     // Catch:{ all -> 0x0223 }
            r6 = r26
            r5.append(r6)     // Catch:{ all -> 0x0223 }
            r6 = r24
            r5.append(r6)     // Catch:{ all -> 0x0223 }
            r7 = r35
            r5.append(r7)     // Catch:{ all -> 0x0223 }
            r5.append(r6)     // Catch:{ all -> 0x0223 }
            r6 = r25
            r5.append(r6)     // Catch:{ all -> 0x0223 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0223 }
            r28 = r3
            r29 = r4
            r30 = r19
            r31 = r0
            r32 = r5
            r33 = r1
            com.tencent.bugly.proguard.C2277as.m696a(r28, r29, r30, r31, r32, r33)     // Catch:{ all -> 0x0223 }
            r3 = r27
            com.tencent.bugly.proguard.as r0 = r3.f1740b     // Catch:{ all -> 0x0221 }
            if (r0 != 0) goto L_0x01f3
            java.lang.String r0 = "crashHandler is null. Won't upload native crash."
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x0221 }
            com.tencent.bugly.proguard.C2265al.m610d(r0, r1)     // Catch:{ all -> 0x0221 }
            return
        L_0x01f3:
            r4 = 1
            boolean r0 = r0.mo28035a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r1, (boolean) r4)     // Catch:{ all -> 0x0221 }
            if (r0 != 0) goto L_0x01fc
            r12 = 1
            goto L_0x01fd
        L_0x01fc:
            r12 = 0
        L_0x01fd:
            r0 = 0
            com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler r2 = com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.getInstance()     // Catch:{ all -> 0x0221 }
            if (r2 == 0) goto L_0x0208
            java.lang.String r0 = com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.getDumpFilePath()     // Catch:{ all -> 0x0221 }
        L_0x0208:
            com.tencent.bugly.proguard.C2316be.m807a((boolean) r4, (java.lang.String) r0)     // Catch:{ all -> 0x0221 }
            if (r12 == 0) goto L_0x0212
            com.tencent.bugly.proguard.as r0 = r3.f1740b     // Catch:{ all -> 0x0221 }
            r0.mo28037b((com.tencent.bugly.crashreport.crash.CrashDetailBean) r1, (boolean) r4)     // Catch:{ all -> 0x0221 }
        L_0x0212:
            com.tencent.bugly.proguard.as r0 = r3.f1740b     // Catch:{ all -> 0x0221 }
            r0.mo28033a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r1)     // Catch:{ all -> 0x0221 }
            com.tencent.bugly.proguard.at r0 = com.tencent.bugly.proguard.C2293at.m738a()     // Catch:{ all -> 0x0221 }
            com.tencent.bugly.proguard.av r0 = r0.f1652s     // Catch:{ all -> 0x0221 }
            r0.mo28061b()     // Catch:{ all -> 0x0221 }
            return
        L_0x0221:
            r0 = move-exception
            goto L_0x0229
        L_0x0223:
            r0 = move-exception
            r3 = r27
            goto L_0x0229
        L_0x0227:
            r0 = move-exception
            r3 = r14
        L_0x0229:
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)
            if (r1 != 0) goto L_0x0232
            r0.printStackTrace()
        L_0x0232:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2315bd.handleNativeException2(int, int, long, long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, int, int, int, java.lang.String, java.lang.String, java.lang.String[]):void");
    }

    /* renamed from: a */
    private static Map<String, String> m798a(String[] strArr) {
        HashMap hashMap = new HashMap(strArr == null ? 1 : strArr.length);
        if (strArr != null) {
            for (int i = 0; i < strArr.length; i++) {
                String str = strArr[i];
                if (str != null) {
                    C2265al.m604a("Extra message[%d]: %s", Integer.valueOf(i), str);
                    String[] split = str.split("=");
                    if (split.length == 2) {
                        hashMap.put(split[0], split[1]);
                    } else {
                        C2265al.m610d("bad extraMsg %s", str);
                    }
                }
            }
        } else {
            C2265al.m609c("not found extraMsg", new Object[0]);
        }
        return hashMap;
    }
}
