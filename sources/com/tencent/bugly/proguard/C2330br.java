package com.tencent.bugly.proguard;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.br */
/* compiled from: BUGLY */
public final class C2330br extends C2346m {

    /* renamed from: i */
    static byte[] f1829i;

    /* renamed from: j */
    static Map<String, String> f1830j;

    /* renamed from: a */
    public byte f1831a = 0;

    /* renamed from: b */
    public int f1832b = 0;

    /* renamed from: c */
    public byte[] f1833c = null;

    /* renamed from: d */
    public String f1834d = "";

    /* renamed from: e */
    public long f1835e = 0;

    /* renamed from: f */
    public String f1836f = "";

    /* renamed from: g */
    public String f1837g = "";

    /* renamed from: h */
    public Map<String, String> f1838h = null;

    /* renamed from: a */
    public final void mo28089a(C2345l lVar) {
        lVar.mo28127a(this.f1831a, 0);
        lVar.mo28128a(this.f1832b, 1);
        byte[] bArr = this.f1833c;
        if (bArr != null) {
            lVar.mo28137a(bArr, 2);
        }
        String str = this.f1834d;
        if (str != null) {
            lVar.mo28132a(str, 3);
        }
        lVar.mo28129a(this.f1835e, 4);
        String str2 = this.f1836f;
        if (str2 != null) {
            lVar.mo28132a(str2, 5);
        }
        String str3 = this.f1837g;
        if (str3 != null) {
            lVar.mo28132a(str3, 6);
        }
        Map<String, String> map = this.f1838h;
        if (map != null) {
            lVar.mo28134a(map, 7);
        }
    }

    static {
        byte[] bArr = new byte[1];
        f1829i = bArr;
        bArr[0] = 0;
        HashMap hashMap = new HashMap();
        f1830j = hashMap;
        hashMap.put("", "");
    }

    /* renamed from: a */
    public final void mo28088a(C2343k kVar) {
        this.f1831a = kVar.mo28114a(this.f1831a, 0, true);
        this.f1832b = kVar.mo28115a(this.f1832b, 1, true);
        this.f1833c = kVar.mo28125c(2, false);
        this.f1834d = kVar.mo28124b(3, false);
        this.f1835e = kVar.mo28117a(this.f1835e, 4, false);
        this.f1836f = kVar.mo28124b(5, false);
        this.f1837g = kVar.mo28124b(6, false);
        this.f1838h = (Map) kVar.mo28119a(f1830j, 7, false);
    }
}
