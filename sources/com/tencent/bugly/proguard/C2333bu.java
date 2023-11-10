package com.tencent.bugly.proguard;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.bu */
/* compiled from: BUGLY */
public final class C2333bu extends C2346m {

    /* renamed from: i */
    static Map<String, String> f1856i;

    /* renamed from: a */
    public long f1857a = 0;

    /* renamed from: b */
    public byte f1858b = 0;

    /* renamed from: c */
    public String f1859c = "";

    /* renamed from: d */
    public String f1860d = "";

    /* renamed from: e */
    public String f1861e = "";

    /* renamed from: f */
    public Map<String, String> f1862f = null;

    /* renamed from: g */
    public String f1863g = "";

    /* renamed from: h */
    public boolean f1864h = true;

    /* renamed from: a */
    public final void mo28089a(C2345l lVar) {
        lVar.mo28129a(this.f1857a, 0);
        lVar.mo28127a(this.f1858b, 1);
        String str = this.f1859c;
        if (str != null) {
            lVar.mo28132a(str, 2);
        }
        String str2 = this.f1860d;
        if (str2 != null) {
            lVar.mo28132a(str2, 3);
        }
        String str3 = this.f1861e;
        if (str3 != null) {
            lVar.mo28132a(str3, 4);
        }
        Map<String, String> map = this.f1862f;
        if (map != null) {
            lVar.mo28134a(map, 5);
        }
        String str4 = this.f1863g;
        if (str4 != null) {
            lVar.mo28132a(str4, 6);
        }
        lVar.mo28136a(this.f1864h, 7);
    }

    static {
        HashMap hashMap = new HashMap();
        f1856i = hashMap;
        hashMap.put("", "");
    }

    /* renamed from: a */
    public final void mo28088a(C2343k kVar) {
        this.f1857a = kVar.mo28117a(this.f1857a, 0, true);
        this.f1858b = kVar.mo28114a(this.f1858b, 1, true);
        this.f1859c = kVar.mo28124b(2, false);
        this.f1860d = kVar.mo28124b(3, false);
        this.f1861e = kVar.mo28124b(4, false);
        this.f1862f = (Map) kVar.mo28119a(f1856i, 5, false);
        this.f1863g = kVar.mo28124b(6, false);
        this.f1864h = kVar.mo28123a(7, false);
    }
}
