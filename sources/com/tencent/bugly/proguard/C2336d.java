package com.tencent.bugly.proguard;

import java.util.HashMap;
import java.util.Set;

/* renamed from: com.tencent.bugly.proguard.d */
/* compiled from: BUGLY */
public class C2336d extends C2335c {

    /* renamed from: e */
    protected HashMap<String, byte[]> f1877e = null;

    /* renamed from: f */
    C2343k f1878f = new C2343k();

    /* renamed from: g */
    private HashMap<String, Object> f1879g = new HashMap<>();

    /* renamed from: a */
    public final /* bridge */ /* synthetic */ void mo28094a(String str) {
        super.mo28094a(str);
    }

    /* renamed from: b */
    public void mo28099b() {
        this.f1877e = new HashMap<>();
    }

    /* renamed from: a */
    public <T> void mo28095a(String str, T t) {
        if (this.f1877e == null) {
            super.mo28095a(str, t);
        } else if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        } else if (t == null) {
            throw new IllegalArgumentException("put value can not is null");
        } else if (!(t instanceof Set)) {
            C2345l lVar = new C2345l();
            lVar.mo28126a(this.f1874c);
            lVar.mo28131a((Object) t, 0);
            this.f1877e.put(str, C2347n.m965a(lVar.f1905a));
        } else {
            throw new IllegalArgumentException("can not support Set");
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: byte[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <T> T mo28098b(java.lang.String r6, T r7) throws com.tencent.bugly.proguard.C2311b {
        /*
            r5 = this;
            java.util.HashMap<java.lang.String, byte[]> r0 = r5.f1877e
            r1 = 1
            r2 = 0
            r3 = 0
            if (r0 == 0) goto L_0x0044
            boolean r0 = r0.containsKey(r6)
            if (r0 != 0) goto L_0x000e
            return r2
        L_0x000e:
            java.util.HashMap<java.lang.String, java.lang.Object> r0 = r5.f1879g
            boolean r0 = r0.containsKey(r6)
            if (r0 == 0) goto L_0x001d
            java.util.HashMap<java.lang.String, java.lang.Object> r7 = r5.f1879g
            java.lang.Object r6 = r7.get(r6)
            return r6
        L_0x001d:
            java.util.HashMap<java.lang.String, byte[]> r0 = r5.f1877e
            java.lang.Object r0 = r0.get(r6)
            byte[] r0 = (byte[]) r0
            com.tencent.bugly.proguard.k r2 = r5.f1878f     // Catch:{ Exception -> 0x003d }
            r2.mo28122a((byte[]) r0)     // Catch:{ Exception -> 0x003d }
            com.tencent.bugly.proguard.k r0 = r5.f1878f     // Catch:{ Exception -> 0x003d }
            java.lang.String r2 = r5.f1874c     // Catch:{ Exception -> 0x003d }
            r0.mo28116a((java.lang.String) r2)     // Catch:{ Exception -> 0x003d }
            com.tencent.bugly.proguard.k r0 = r5.f1878f     // Catch:{ Exception -> 0x003d }
            java.lang.Object r7 = r0.mo28119a(r7, (int) r3, (boolean) r1)     // Catch:{ Exception -> 0x003d }
            if (r7 == 0) goto L_0x003c
            r5.m865c(r6, r7)     // Catch:{ Exception -> 0x003d }
        L_0x003c:
            return r7
        L_0x003d:
            r6 = move-exception
            com.tencent.bugly.proguard.b r7 = new com.tencent.bugly.proguard.b
            r7.<init>(r6)
            throw r7
        L_0x0044:
            java.util.HashMap r0 = r5.f1872a
            boolean r0 = r0.containsKey(r6)
            if (r0 != 0) goto L_0x004d
            return r2
        L_0x004d:
            java.util.HashMap<java.lang.String, java.lang.Object> r0 = r5.f1879g
            boolean r0 = r0.containsKey(r6)
            if (r0 == 0) goto L_0x005c
            java.util.HashMap<java.lang.String, java.lang.Object> r7 = r5.f1879g
            java.lang.Object r6 = r7.get(r6)
            return r6
        L_0x005c:
            java.util.HashMap r0 = r5.f1872a
            java.lang.Object r0 = r0.get(r6)
            java.util.HashMap r0 = (java.util.HashMap) r0
            byte[] r2 = new byte[r3]
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto L_0x0084
            java.lang.Object r0 = r0.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            r0.getKey()
            java.lang.Object r0 = r0.getValue()
            r2 = r0
            byte[] r2 = (byte[]) r2
        L_0x0084:
            com.tencent.bugly.proguard.k r0 = r5.f1878f     // Catch:{ Exception -> 0x009a }
            r0.mo28122a((byte[]) r2)     // Catch:{ Exception -> 0x009a }
            com.tencent.bugly.proguard.k r0 = r5.f1878f     // Catch:{ Exception -> 0x009a }
            java.lang.String r2 = r5.f1874c     // Catch:{ Exception -> 0x009a }
            r0.mo28116a((java.lang.String) r2)     // Catch:{ Exception -> 0x009a }
            com.tencent.bugly.proguard.k r0 = r5.f1878f     // Catch:{ Exception -> 0x009a }
            java.lang.Object r7 = r0.mo28119a(r7, (int) r3, (boolean) r1)     // Catch:{ Exception -> 0x009a }
            r5.m865c(r6, r7)     // Catch:{ Exception -> 0x009a }
            return r7
        L_0x009a:
            r6 = move-exception
            com.tencent.bugly.proguard.b r7 = new com.tencent.bugly.proguard.b
            r7.<init>(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2336d.mo28098b(java.lang.String, java.lang.Object):java.lang.Object");
    }

    /* renamed from: c */
    private void m865c(String str, Object obj) {
        this.f1879g.put(str, obj);
    }

    /* renamed from: a */
    public byte[] mo28097a() {
        if (this.f1877e == null) {
            return super.mo28097a();
        }
        C2345l lVar = new C2345l(0);
        lVar.mo28126a(this.f1874c);
        lVar.mo28134a(this.f1877e, 0);
        return C2347n.m965a(lVar.f1905a);
    }

    /* renamed from: a */
    public void mo28096a(byte[] bArr) {
        try {
            super.mo28096a(bArr);
        } catch (Exception unused) {
            this.f1878f.mo28122a(bArr);
            this.f1878f.mo28116a(this.f1874c);
            HashMap hashMap = new HashMap(1);
            hashMap.put("", new byte[0]);
            this.f1877e = this.f1878f.mo28120a(hashMap, 0, false);
        }
    }
}
