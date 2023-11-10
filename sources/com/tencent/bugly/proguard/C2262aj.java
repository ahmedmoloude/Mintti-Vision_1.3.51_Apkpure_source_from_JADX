package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import android.util.Pair;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* renamed from: com.tencent.bugly.proguard.aj */
/* compiled from: BUGLY */
public final class C2262aj implements Runnable {

    /* renamed from: a */
    protected int f1543a;

    /* renamed from: b */
    protected long f1544b;

    /* renamed from: c */
    protected long f1545c;

    /* renamed from: d */
    private int f1546d;

    /* renamed from: e */
    private int f1547e;

    /* renamed from: f */
    private final Context f1548f;

    /* renamed from: g */
    private final int f1549g;

    /* renamed from: h */
    private final byte[] f1550h;

    /* renamed from: i */
    private final C2231aa f1551i;

    /* renamed from: j */
    private final C2248ac f1552j;

    /* renamed from: k */
    private final C2252af f1553k;

    /* renamed from: l */
    private final C2259ai f1554l;

    /* renamed from: m */
    private final int f1555m;

    /* renamed from: n */
    private final C2258ah f1556n;

    /* renamed from: o */
    private final C2258ah f1557o;

    /* renamed from: p */
    private String f1558p;

    /* renamed from: q */
    private final String f1559q;

    /* renamed from: r */
    private final Map<String, String> f1560r;

    /* renamed from: s */
    private boolean f1561s;

    public C2262aj(Context context, int i, int i2, byte[] bArr, String str, String str2, C2258ah ahVar, boolean z) {
        this(context, i, i2, bArr, str, str2, ahVar, 2, 30000, z);
    }

    public C2262aj(Context context, int i, int i2, byte[] bArr, String str, String str2, C2258ah ahVar, int i3, int i4, boolean z) {
        this.f1546d = 2;
        this.f1547e = 30000;
        this.f1558p = null;
        this.f1543a = 0;
        this.f1544b = 0;
        this.f1545c = 0;
        this.f1561s = false;
        this.f1548f = context;
        this.f1551i = C2231aa.m457a(context);
        this.f1550h = bArr;
        this.f1552j = C2248ac.m533a();
        if (C2252af.f1508a == null) {
            C2252af.f1508a = new C2252af(context);
        }
        this.f1553k = C2252af.f1508a;
        C2259ai a = C2259ai.m569a();
        this.f1554l = a;
        this.f1555m = i;
        this.f1558p = str;
        this.f1559q = str2;
        this.f1556n = ahVar;
        this.f1557o = a.f1527a;
        this.f1549g = i2;
        if (i3 > 0) {
            this.f1546d = i3;
        }
        if (i4 > 0) {
            this.f1547e = i4;
        }
        this.f1561s = z;
        this.f1560r = null;
    }

    /* renamed from: a */
    private static void m589a(String str) {
        C2265al.m611e("[Upload] Failed to upload(%d): %s", 1, str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0020  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m590a(boolean r5, int r6, java.lang.String r7) {
        /*
            r4 = this;
            int r0 = r4.f1549g
            r1 = 630(0x276, float:8.83E-43)
            if (r0 == r1) goto L_0x001a
            r1 = 640(0x280, float:8.97E-43)
            if (r0 == r1) goto L_0x0017
            r1 = 830(0x33e, float:1.163E-42)
            if (r0 == r1) goto L_0x001a
            r1 = 840(0x348, float:1.177E-42)
            if (r0 == r1) goto L_0x0017
            java.lang.String r0 = java.lang.String.valueOf(r0)
            goto L_0x001c
        L_0x0017:
            java.lang.String r0 = "userinfo"
            goto L_0x001c
        L_0x001a:
            java.lang.String r0 = "crash"
        L_0x001c:
            r1 = 1
            r2 = 0
            if (r5 == 0) goto L_0x002a
            java.lang.Object[] r6 = new java.lang.Object[r1]
            r6[r2] = r0
            java.lang.String r0 = "[Upload] Success: %s"
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r0, (java.lang.Object[]) r6)
            goto L_0x003d
        L_0x002a:
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r3[r2] = r6
            r3[r1] = r0
            r6 = 2
            r3[r6] = r7
            java.lang.String r6 = "[Upload] Failed to upload(%d) %s: %s"
            com.tencent.bugly.proguard.C2265al.m611e(r6, r3)
        L_0x003d:
            long r0 = r4.f1544b
            long r2 = r4.f1545c
            long r0 = r0 + r2
            r2 = 0
            int r6 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r6 <= 0) goto L_0x005d
            com.tencent.bugly.proguard.ai r6 = r4.f1554l
            boolean r0 = r4.f1561s
            long r0 = r6.mo28006a((boolean) r0)
            long r2 = r4.f1544b
            long r0 = r0 + r2
            long r2 = r4.f1545c
            long r0 = r0 + r2
            com.tencent.bugly.proguard.ai r6 = r4.f1554l
            boolean r2 = r4.f1561s
            r6.mo28010a((long) r0, (boolean) r2)
        L_0x005d:
            com.tencent.bugly.proguard.ah r6 = r4.f1556n
            if (r6 == 0) goto L_0x0064
            r6.mo28004a(r5, r7)
        L_0x0064:
            com.tencent.bugly.proguard.ah r6 = r4.f1557o
            if (r6 == 0) goto L_0x006b
            r6.mo28004a(r5, r7)
        L_0x006b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2262aj.m590a(boolean, int, java.lang.String):void");
    }

    /* renamed from: a */
    private static boolean m591a(C2330br brVar, C2231aa aaVar, C2248ac acVar) {
        if (brVar == null) {
            C2265al.m610d("resp == null!", new Object[0]);
            return false;
        } else if (brVar.f1831a != 0) {
            C2265al.m611e("resp result error %d", Byte.valueOf(brVar.f1831a));
            return false;
        } else {
            try {
                if (!C2273ap.m657a(brVar.f1837g) && !C2231aa.m459b().mo27975i().equals(brVar.f1837g)) {
                    C2365w.m1033a().mo28170a(C2248ac.f1498a, "device", brVar.f1837g.getBytes("UTF-8"), true);
                    aaVar.mo27967d(brVar.f1837g);
                }
            } catch (Throwable th) {
                C2265al.m605a(th);
            }
            aaVar.f1481m = brVar.f1835e;
            if (brVar.f1832b == 510) {
                if (brVar.f1833c == null) {
                    C2265al.m611e("[Upload] Strategy data is null. Response cmd: %d", Integer.valueOf(brVar.f1832b));
                    return false;
                }
                C2332bt btVar = (C2332bt) C2251ae.m550a(brVar.f1833c, C2332bt.class);
                if (btVar == null) {
                    C2265al.m611e("[Upload] Failed to decode strategy from server. Response cmd: %d", Integer.valueOf(brVar.f1832b));
                    return false;
                }
                acVar.mo27994a(btVar);
            }
            return true;
        }
    }

    public final void run() {
        String str;
        boolean z;
        Pair pair;
        C2248ac acVar;
        try {
            this.f1543a = 0;
            this.f1544b = 0;
            this.f1545c = 0;
            if (C2232ab.m501c(this.f1548f) == null) {
                str = "network is not available";
            } else {
                byte[] bArr = this.f1550h;
                if (bArr != null) {
                    if (bArr.length != 0) {
                        if (!(this.f1548f == null || this.f1551i == null || (acVar = this.f1552j) == null)) {
                            if (this.f1553k != null) {
                                str = acVar.mo27996c() == null ? "illegal local strategy" : null;
                            }
                        }
                        str = "illegal access error";
                    }
                }
                str = "request package is empty!";
            }
            if (str != null) {
                m590a(false, 0, str);
                return;
            }
            byte[] a = C2273ap.m660a(this.f1550h);
            if (a == null) {
                m590a(false, 0, "failed to zip request body");
                return;
            }
            HashMap hashMap = new HashMap(10);
            hashMap.put("tls", "1");
            hashMap.put("prodId", this.f1551i.mo27968e());
            hashMap.put("bundleId", this.f1551i.f1471c);
            hashMap.put("appVer", this.f1551i.f1483o);
            Map<String, String> map = this.f1560r;
            if (map != null) {
                hashMap.putAll(map);
            }
            hashMap.put("cmd", Integer.toString(this.f1549g));
            hashMap.put("platformId", Byte.toString((byte) 1));
            hashMap.put("sdkVer", this.f1551i.f1476h);
            hashMap.put("strategylastUpdateTime", Long.toString(this.f1552j.mo27996c().f1327o));
            this.f1554l.mo28007a(this.f1555m, System.currentTimeMillis());
            String str2 = this.f1558p;
            this.f1552j.mo27996c();
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = i + 1;
                if (i < this.f1546d) {
                    if (i3 > 1) {
                        C2265al.m610d("[Upload] Failed to upload last time, wait and try(%d) again.", Integer.valueOf(i3));
                        C2273ap.m665b((long) this.f1547e);
                        if (i3 == this.f1546d) {
                            C2265al.m610d("[Upload] Use the back-up url at the last time: %s", this.f1559q);
                            str2 = this.f1559q;
                        }
                    }
                    C2265al.m609c("[Upload] Send %d bytes", Integer.valueOf(a.length));
                    str2 = m592b(str2);
                    C2265al.m609c("[Upload] Upload to %s with cmd %d (pid=%d | tid=%d).", str2, Integer.valueOf(this.f1549g), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                    byte[] a2 = this.f1553k.mo27998a(str2, a, this, (Map<String, String>) hashMap);
                    Map<String, String> map2 = this.f1553k.f1510c;
                    Pair<Boolean, Boolean> a3 = m588a(a2, map2);
                    if (!((Boolean) a3.first).booleanValue()) {
                        z = ((Boolean) a3.second).booleanValue();
                    } else {
                        Pair<Boolean, Boolean> a4 = m587a(map2);
                        if (!((Boolean) a4.first).booleanValue()) {
                            z = ((Boolean) a4.second).booleanValue();
                        } else {
                            byte[] b = C2273ap.m670b(a2);
                            if (b != null) {
                                a2 = b;
                            }
                            C2330br a5 = C2251ae.m548a(a2);
                            if (a5 == null) {
                                m590a(false, 1, "failed to decode response package");
                                Boolean bool = Boolean.FALSE;
                                pair = new Pair(bool, bool);
                            } else {
                                Object[] objArr = new Object[2];
                                objArr[0] = Integer.valueOf(a5.f1832b);
                                objArr[1] = Integer.valueOf(a5.f1833c == null ? 0 : a5.f1833c.length);
                                C2265al.m609c("[Upload] Response cmd is: %d, length of sBuffer is: %d", objArr);
                                if (!m591a(a5, this.f1551i, this.f1552j)) {
                                    m590a(false, 2, "failed to process response package");
                                    Boolean bool2 = Boolean.FALSE;
                                    pair = new Pair(bool2, bool2);
                                } else {
                                    m590a(true, 2, "successfully uploaded");
                                    Boolean bool3 = Boolean.TRUE;
                                    pair = new Pair(bool3, bool3);
                                }
                            }
                            z = !((Boolean) pair.first).booleanValue() ? ((Boolean) pair.second).booleanValue() : false;
                        }
                    }
                    if (z) {
                        i = i3;
                        i2 = 1;
                    } else {
                        return;
                    }
                } else {
                    m590a(false, i2, "failed after many attempts");
                    return;
                }
            }
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
        }
    }

    /* renamed from: a */
    private Pair<Boolean, Boolean> m588a(byte[] bArr, Map<String, String> map) {
        if (bArr == null) {
            m589a("Failed to upload for no response!");
            return new Pair<>(Boolean.FALSE, Boolean.TRUE);
        }
        C2265al.m609c("[Upload] Received %d bytes", Integer.valueOf(bArr.length));
        if (bArr.length == 0) {
            m590a(false, 1, "response data from server is empty");
            if (map != null) {
                for (Map.Entry next : map.entrySet()) {
                    C2265al.m609c("[Upload] HTTP headers from server: key = %s, value = %s", next.getKey(), next.getValue());
                }
            }
            Boolean bool = Boolean.FALSE;
            return new Pair<>(bool, bool);
        }
        Boolean bool2 = Boolean.TRUE;
        return new Pair<>(bool2, bool2);
    }

    /* renamed from: a */
    public final void mo28014a(long j) {
        this.f1543a++;
        this.f1544b += j;
    }

    /* renamed from: b */
    public final void mo28015b(long j) {
        this.f1545c += j;
    }

    /* renamed from: b */
    private static String m592b(String str) {
        if (C2273ap.m657a(str)) {
            return str;
        }
        try {
            return String.format("%s?aid=%s", new Object[]{str, UUID.randomUUID().toString()});
        } catch (Throwable th) {
            C2265al.m605a(th);
            return str;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00ba  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.util.Pair<java.lang.Boolean, java.lang.Boolean> m587a(java.util.Map<java.lang.String, java.lang.String> r8) {
        /*
            r7 = this;
            java.lang.String r0 = "status"
            r1 = 1
            r2 = 0
            if (r8 == 0) goto L_0x0050
            int r3 = r8.size()
            if (r3 != 0) goto L_0x000d
            goto L_0x0050
        L_0x000d:
            boolean r3 = r8.containsKey(r0)
            java.lang.String r4 = "[Upload] Headers does not contain %s"
            if (r3 != 0) goto L_0x001d
            java.lang.Object[] r3 = new java.lang.Object[r1]
            r3[r2] = r0
            com.tencent.bugly.proguard.C2265al.m610d(r4, r3)
            goto L_0x0057
        L_0x001d:
            java.lang.String r3 = "Bugly-Version"
            boolean r5 = r8.containsKey(r3)
            if (r5 != 0) goto L_0x002d
            java.lang.Object[] r5 = new java.lang.Object[r1]
            r5[r2] = r3
            com.tencent.bugly.proguard.C2265al.m610d(r4, r5)
            goto L_0x0057
        L_0x002d:
            java.lang.Object r3 = r8.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "bugly"
            boolean r4 = r3.contains(r4)
            if (r4 != 0) goto L_0x0045
            java.lang.Object[] r4 = new java.lang.Object[r1]
            r4[r2] = r3
            java.lang.String r3 = "[Upload] Bugly version is not valid: %s"
            com.tencent.bugly.proguard.C2265al.m610d(r3, r4)
            goto L_0x0057
        L_0x0045:
            java.lang.Object[] r4 = new java.lang.Object[r1]
            r4[r2] = r3
            java.lang.String r3 = "[Upload] Bugly version from headers is: %s"
            com.tencent.bugly.proguard.C2265al.m609c(r3, r4)
            r3 = 1
            goto L_0x0058
        L_0x0050:
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.String r4 = "[Upload] Headers is empty."
            com.tencent.bugly.proguard.C2265al.m610d(r4, r3)
        L_0x0057:
            r3 = 0
        L_0x0058:
            r4 = 2
            if (r3 != 0) goto L_0x00ba
            java.lang.Object[] r0 = new java.lang.Object[r4]
            int r3 = android.os.Process.myPid()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0[r2] = r3
            int r3 = android.os.Process.myTid()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0[r1] = r3
            java.lang.String r3 = "[Upload] Headers from server is not valid, just try again (pid=%d | tid=%d)."
            com.tencent.bugly.proguard.C2265al.m609c(r3, r0)
            java.lang.String r0 = "[Upload] Failed to upload for no status header."
            m589a((java.lang.String) r0)
            if (r8 == 0) goto L_0x00ab
            java.util.Set r8 = r8.entrySet()
            java.util.Iterator r8 = r8.iterator()
        L_0x0085:
            boolean r3 = r8.hasNext()
            if (r3 == 0) goto L_0x00ab
            java.lang.Object r3 = r8.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object[] r5 = new java.lang.Object[r4]
            java.lang.Object r6 = r3.getKey()
            r5[r2] = r6
            java.lang.Object r3 = r3.getValue()
            r5[r1] = r3
            java.lang.String r3 = "[key]: %s, [value]: %s"
            java.lang.String r3 = java.lang.String.format(r3, r5)
            java.lang.Object[] r5 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.C2265al.m609c(r3, r5)
            goto L_0x0085
        L_0x00ab:
            java.lang.Object[] r8 = new java.lang.Object[r2]
            com.tencent.bugly.proguard.C2265al.m609c(r0, r8)
            android.util.Pair r8 = new android.util.Pair
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r8.<init>(r0, r1)
            return r8
        L_0x00ba:
            r3 = -1
            java.lang.Object r8 = r8.get(r0)     // Catch:{ all -> 0x0106 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ all -> 0x0106 }
            int r3 = java.lang.Integer.parseInt(r8)     // Catch:{ all -> 0x0106 }
            java.lang.String r8 = "[Upload] Status from server is %d (pid=%d | tid=%d)."
            r0 = 3
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0106 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0106 }
            r0[r2] = r5     // Catch:{ all -> 0x0106 }
            int r5 = android.os.Process.myPid()     // Catch:{ all -> 0x0106 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0106 }
            r0[r1] = r5     // Catch:{ all -> 0x0106 }
            int r5 = android.os.Process.myTid()     // Catch:{ all -> 0x0106 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0106 }
            r0[r4] = r5     // Catch:{ all -> 0x0106 }
            com.tencent.bugly.proguard.C2265al.m609c(r8, r0)     // Catch:{ all -> 0x0106 }
            if (r3 == 0) goto L_0x00fe
            java.lang.String r8 = java.lang.String.valueOf(r3)
            java.lang.String r0 = "status of server is "
            java.lang.String r8 = r0.concat(r8)
            r7.m590a((boolean) r2, (int) r1, (java.lang.String) r8)
            android.util.Pair r8 = new android.util.Pair
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            r8.<init>(r0, r0)
            return r8
        L_0x00fe:
            android.util.Pair r8 = new android.util.Pair
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            r8.<init>(r0, r0)
            return r8
        L_0x0106:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r0 = "[Upload] Failed to upload for format of status header is invalid: "
            r8.<init>(r0)
            java.lang.String r0 = java.lang.Integer.toString(r3)
            r8.append(r0)
            java.lang.String r8 = r8.toString()
            m589a((java.lang.String) r8)
            android.util.Pair r8 = new android.util.Pair
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r8.<init>(r0, r1)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2262aj.m587a(java.util.Map):android.util.Pair");
    }
}
