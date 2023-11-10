package com.tencent.bugly.proguard;

import java.util.ArrayList;

/* renamed from: com.tencent.bugly.proguard.bm */
/* compiled from: BUGLY */
public final class C2325bm extends C2346m implements Cloneable {

    /* renamed from: c */
    static ArrayList<String> f1765c;

    /* renamed from: a */
    public String f1766a = "";

    /* renamed from: b */
    public ArrayList<String> f1767b = null;

    /* renamed from: a */
    public final void mo28090a(StringBuilder sb, int i) {
    }

    /* renamed from: a */
    public final void mo28089a(C2345l lVar) {
        lVar.mo28132a(this.f1766a, 0);
        ArrayList<String> arrayList = this.f1767b;
        if (arrayList != null) {
            lVar.mo28133a(arrayList, 1);
        }
    }

    /* renamed from: a */
    public final void mo28088a(C2343k kVar) {
        this.f1766a = kVar.mo28124b(0, true);
        if (f1765c == null) {
            ArrayList<String> arrayList = new ArrayList<>();
            f1765c = arrayList;
            arrayList.add("");
        }
        this.f1767b = (ArrayList) kVar.mo28119a(f1765c, 1, false);
    }
}
