package com.tencent.bugly.proguard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.bo */
/* compiled from: BUGLY */
public final class C2327bo extends C2346m {

    /* renamed from: A */
    static ArrayList<C2326bn> f1772A = new ArrayList<>();

    /* renamed from: B */
    static Map<String, String> f1773B;

    /* renamed from: C */
    static Map<String, String> f1774C;

    /* renamed from: v */
    static Map<String, String> f1775v;

    /* renamed from: w */
    static C2325bm f1776w = new C2325bm();

    /* renamed from: x */
    static C2324bl f1777x = new C2324bl();

    /* renamed from: y */
    static ArrayList<C2324bl> f1778y = new ArrayList<>();

    /* renamed from: z */
    static ArrayList<C2324bl> f1779z = new ArrayList<>();

    /* renamed from: a */
    public String f1780a = "";

    /* renamed from: b */
    public long f1781b = 0;

    /* renamed from: c */
    public String f1782c = "";

    /* renamed from: d */
    public String f1783d = "";

    /* renamed from: e */
    public String f1784e = "";

    /* renamed from: f */
    public String f1785f = "";

    /* renamed from: g */
    public String f1786g = "";

    /* renamed from: h */
    public Map<String, String> f1787h = null;

    /* renamed from: i */
    public String f1788i = "";

    /* renamed from: j */
    public C2325bm f1789j = null;

    /* renamed from: k */
    public int f1790k = 0;

    /* renamed from: l */
    public String f1791l = "";

    /* renamed from: m */
    public String f1792m = "";

    /* renamed from: n */
    public C2324bl f1793n = null;

    /* renamed from: o */
    public ArrayList<C2324bl> f1794o = null;

    /* renamed from: p */
    public ArrayList<C2324bl> f1795p = null;

    /* renamed from: q */
    public ArrayList<C2326bn> f1796q = null;

    /* renamed from: r */
    public Map<String, String> f1797r = null;

    /* renamed from: s */
    public Map<String, String> f1798s = null;

    /* renamed from: t */
    public String f1799t = "";

    /* renamed from: u */
    public boolean f1800u = true;

    /* renamed from: a */
    public final void mo28089a(C2345l lVar) {
        lVar.mo28132a(this.f1780a, 0);
        lVar.mo28129a(this.f1781b, 1);
        lVar.mo28132a(this.f1782c, 2);
        String str = this.f1783d;
        if (str != null) {
            lVar.mo28132a(str, 3);
        }
        String str2 = this.f1784e;
        if (str2 != null) {
            lVar.mo28132a(str2, 4);
        }
        String str3 = this.f1785f;
        if (str3 != null) {
            lVar.mo28132a(str3, 5);
        }
        String str4 = this.f1786g;
        if (str4 != null) {
            lVar.mo28132a(str4, 6);
        }
        Map<String, String> map = this.f1787h;
        if (map != null) {
            lVar.mo28134a(map, 7);
        }
        String str5 = this.f1788i;
        if (str5 != null) {
            lVar.mo28132a(str5, 8);
        }
        C2325bm bmVar = this.f1789j;
        if (bmVar != null) {
            lVar.mo28130a((C2346m) bmVar, 9);
        }
        lVar.mo28128a(this.f1790k, 10);
        String str6 = this.f1791l;
        if (str6 != null) {
            lVar.mo28132a(str6, 11);
        }
        String str7 = this.f1792m;
        if (str7 != null) {
            lVar.mo28132a(str7, 12);
        }
        C2324bl blVar = this.f1793n;
        if (blVar != null) {
            lVar.mo28130a((C2346m) blVar, 13);
        }
        ArrayList<C2324bl> arrayList = this.f1794o;
        if (arrayList != null) {
            lVar.mo28133a(arrayList, 14);
        }
        ArrayList<C2324bl> arrayList2 = this.f1795p;
        if (arrayList2 != null) {
            lVar.mo28133a(arrayList2, 15);
        }
        ArrayList<C2326bn> arrayList3 = this.f1796q;
        if (arrayList3 != null) {
            lVar.mo28133a(arrayList3, 16);
        }
        Map<String, String> map2 = this.f1797r;
        if (map2 != null) {
            lVar.mo28134a(map2, 17);
        }
        Map<String, String> map3 = this.f1798s;
        if (map3 != null) {
            lVar.mo28134a(map3, 18);
        }
        String str8 = this.f1799t;
        if (str8 != null) {
            lVar.mo28132a(str8, 19);
        }
        lVar.mo28136a(this.f1800u, 20);
    }

    static {
        HashMap hashMap = new HashMap();
        f1775v = hashMap;
        hashMap.put("", "");
        f1778y.add(new C2324bl());
        f1779z.add(new C2324bl());
        f1772A.add(new C2326bn());
        HashMap hashMap2 = new HashMap();
        f1773B = hashMap2;
        hashMap2.put("", "");
        HashMap hashMap3 = new HashMap();
        f1774C = hashMap3;
        hashMap3.put("", "");
    }

    /* renamed from: a */
    public final void mo28088a(C2343k kVar) {
        this.f1780a = kVar.mo28124b(0, true);
        this.f1781b = kVar.mo28117a(this.f1781b, 1, true);
        this.f1782c = kVar.mo28124b(2, true);
        this.f1783d = kVar.mo28124b(3, false);
        this.f1784e = kVar.mo28124b(4, false);
        this.f1785f = kVar.mo28124b(5, false);
        this.f1786g = kVar.mo28124b(6, false);
        this.f1787h = (Map) kVar.mo28119a(f1775v, 7, false);
        this.f1788i = kVar.mo28124b(8, false);
        this.f1789j = (C2325bm) kVar.mo28118a((C2346m) f1776w, 9, false);
        this.f1790k = kVar.mo28115a(this.f1790k, 10, false);
        this.f1791l = kVar.mo28124b(11, false);
        this.f1792m = kVar.mo28124b(12, false);
        this.f1793n = (C2324bl) kVar.mo28118a((C2346m) f1777x, 13, false);
        this.f1794o = (ArrayList) kVar.mo28119a(f1778y, 14, false);
        this.f1795p = (ArrayList) kVar.mo28119a(f1779z, 15, false);
        this.f1796q = (ArrayList) kVar.mo28119a(f1772A, 16, false);
        this.f1797r = (Map) kVar.mo28119a(f1773B, 17, false);
        this.f1798s = (Map) kVar.mo28119a(f1774C, 18, false);
        this.f1799t = kVar.mo28124b(19, false);
        this.f1800u = kVar.mo28123a(20, false);
    }
}
