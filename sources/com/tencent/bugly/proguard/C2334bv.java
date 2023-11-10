package com.tencent.bugly.proguard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.bv */
/* compiled from: BUGLY */
public final class C2334bv extends C2346m implements Cloneable {

    /* renamed from: f */
    static ArrayList<C2333bu> f1865f;

    /* renamed from: g */
    static Map<String, String> f1866g;

    /* renamed from: a */
    public byte f1867a = 0;

    /* renamed from: b */
    public String f1868b = "";

    /* renamed from: c */
    public String f1869c = "";

    /* renamed from: d */
    public ArrayList<C2333bu> f1870d = null;

    /* renamed from: e */
    public Map<String, String> f1871e = null;

    /* renamed from: a */
    public final void mo28090a(StringBuilder sb, int i) {
    }

    /* renamed from: a */
    public final void mo28089a(C2345l lVar) {
        lVar.mo28127a(this.f1867a, 0);
        String str = this.f1868b;
        if (str != null) {
            lVar.mo28132a(str, 1);
        }
        String str2 = this.f1869c;
        if (str2 != null) {
            lVar.mo28132a(str2, 2);
        }
        ArrayList<C2333bu> arrayList = this.f1870d;
        if (arrayList != null) {
            lVar.mo28133a(arrayList, 3);
        }
        Map<String, String> map = this.f1871e;
        if (map != null) {
            lVar.mo28134a(map, 4);
        }
    }

    /* renamed from: a */
    public final void mo28088a(C2343k kVar) {
        this.f1867a = kVar.mo28114a(this.f1867a, 0, true);
        this.f1868b = kVar.mo28124b(1, false);
        this.f1869c = kVar.mo28124b(2, false);
        if (f1865f == null) {
            f1865f = new ArrayList<>();
            f1865f.add(new C2333bu());
        }
        this.f1870d = (ArrayList) kVar.mo28119a(f1865f, 3, false);
        if (f1866g == null) {
            HashMap hashMap = new HashMap();
            f1866g = hashMap;
            hashMap.put("", "");
        }
        this.f1871e = (Map) kVar.mo28119a(f1866g, 4, false);
    }
}
