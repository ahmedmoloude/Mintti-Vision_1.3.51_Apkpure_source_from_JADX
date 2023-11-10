package com.tencent.bugly.proguard;

import java.util.ArrayList;

/* renamed from: com.tencent.bugly.proguard.bp */
/* compiled from: BUGLY */
public final class C2328bp extends C2346m implements Cloneable {

    /* renamed from: b */
    static ArrayList<C2327bo> f1801b;

    /* renamed from: a */
    public ArrayList<C2327bo> f1802a = null;

    /* renamed from: a */
    public final void mo28090a(StringBuilder sb, int i) {
    }

    /* renamed from: a */
    public final void mo28089a(C2345l lVar) {
        lVar.mo28133a(this.f1802a, 0);
    }

    /* renamed from: a */
    public final void mo28088a(C2343k kVar) {
        if (f1801b == null) {
            f1801b = new ArrayList<>();
            f1801b.add(new C2327bo());
        }
        this.f1802a = (ArrayList) kVar.mo28119a(f1801b, 0, true);
    }
}
