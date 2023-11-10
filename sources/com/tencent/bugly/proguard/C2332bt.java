package com.tencent.bugly.proguard;

import com.itextpdf.text.Annotation;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.bt */
/* compiled from: BUGLY */
public final class C2332bt extends C2346m implements Cloneable {

    /* renamed from: m */
    static C2331bs f1841m = new C2331bs();

    /* renamed from: n */
    static Map<String, String> f1842n = null;

    /* renamed from: o */
    static final /* synthetic */ boolean f1843o = true;

    /* renamed from: a */
    public boolean f1844a = true;

    /* renamed from: b */
    public boolean f1845b = true;

    /* renamed from: c */
    public boolean f1846c = true;

    /* renamed from: d */
    public String f1847d = "";

    /* renamed from: e */
    public String f1848e = "";

    /* renamed from: f */
    public C2331bs f1849f = null;

    /* renamed from: g */
    public Map<String, String> f1850g = null;

    /* renamed from: h */
    public long f1851h = 0;

    /* renamed from: i */
    public String f1852i = "";

    /* renamed from: j */
    public String f1853j = "";

    /* renamed from: k */
    public int f1854k = 0;

    /* renamed from: l */
    public int f1855l = 0;

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        C2332bt btVar = (C2332bt) obj;
        if (!C2347n.m964a(this.f1844a, btVar.f1844a) || !C2347n.m964a(this.f1845b, btVar.f1845b) || !C2347n.m964a(this.f1846c, btVar.f1846c) || !C2347n.m963a((Object) this.f1847d, (Object) btVar.f1847d) || !C2347n.m963a((Object) this.f1848e, (Object) btVar.f1848e) || !C2347n.m963a((Object) this.f1849f, (Object) btVar.f1849f) || !C2347n.m963a((Object) this.f1850g, (Object) btVar.f1850g) || !C2347n.m962a(this.f1851h, btVar.f1851h) || !C2347n.m963a((Object) this.f1852i, (Object) btVar.f1852i) || !C2347n.m963a((Object) this.f1853j, (Object) btVar.f1853j) || !C2347n.m961a(this.f1854k, btVar.f1854k) || !C2347n.m961a(this.f1855l, btVar.f1855l)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            if (f1843o) {
                return null;
            }
            throw new AssertionError();
        }
    }

    /* renamed from: a */
    public final void mo28089a(C2345l lVar) {
        lVar.mo28136a(this.f1844a, 0);
        lVar.mo28136a(this.f1845b, 1);
        lVar.mo28136a(this.f1846c, 2);
        String str = this.f1847d;
        if (str != null) {
            lVar.mo28132a(str, 3);
        }
        String str2 = this.f1848e;
        if (str2 != null) {
            lVar.mo28132a(str2, 4);
        }
        C2331bs bsVar = this.f1849f;
        if (bsVar != null) {
            lVar.mo28130a((C2346m) bsVar, 5);
        }
        Map<String, String> map = this.f1850g;
        if (map != null) {
            lVar.mo28134a(map, 6);
        }
        lVar.mo28129a(this.f1851h, 7);
        String str3 = this.f1852i;
        if (str3 != null) {
            lVar.mo28132a(str3, 8);
        }
        String str4 = this.f1853j;
        if (str4 != null) {
            lVar.mo28132a(str4, 9);
        }
        lVar.mo28128a(this.f1854k, 10);
        lVar.mo28128a(this.f1855l, 11);
    }

    static {
        HashMap hashMap = new HashMap();
        f1842n = hashMap;
        hashMap.put("", "");
    }

    /* renamed from: a */
    public final void mo28088a(C2343k kVar) {
        this.f1844a = kVar.mo28123a(0, true);
        this.f1845b = kVar.mo28123a(1, true);
        this.f1846c = kVar.mo28123a(2, true);
        this.f1847d = kVar.mo28124b(3, false);
        this.f1848e = kVar.mo28124b(4, false);
        this.f1849f = (C2331bs) kVar.mo28118a((C2346m) f1841m, 5, false);
        this.f1850g = (Map) kVar.mo28119a(f1842n, 6, false);
        this.f1851h = kVar.mo28117a(this.f1851h, 7, false);
        this.f1852i = kVar.mo28124b(8, false);
        this.f1853j = kVar.mo28124b(9, false);
        this.f1854k = kVar.mo28115a(this.f1854k, 10, false);
        this.f1855l = kVar.mo28115a(this.f1855l, 11, false);
    }

    /* renamed from: a */
    public final void mo28090a(StringBuilder sb, int i) {
        C2341i iVar = new C2341i(sb, i);
        iVar.mo28112a(this.f1844a, "enable");
        iVar.mo28112a(this.f1845b, "enableUserInfo");
        iVar.mo28112a(this.f1846c, "enableQuery");
        iVar.mo28109a(this.f1847d, Annotation.URL);
        iVar.mo28109a(this.f1848e, "expUrl");
        iVar.mo28108a((C2346m) this.f1849f, "security");
        iVar.mo28110a(this.f1850g, "valueMap");
        iVar.mo28107a(this.f1851h, "strategylastUpdateTime");
        iVar.mo28109a(this.f1852i, "httpsUrl");
        iVar.mo28109a(this.f1853j, "httpsExpUrl");
        iVar.mo28106a(this.f1854k, "eventRecordCount");
        iVar.mo28106a(this.f1855l, "eventTimeInterval");
    }
}
