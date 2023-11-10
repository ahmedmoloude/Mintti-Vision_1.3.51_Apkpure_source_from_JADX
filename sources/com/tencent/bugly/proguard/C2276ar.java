package com.tencent.bugly.proguard;

/* renamed from: com.tencent.bugly.proguard.ar */
/* compiled from: BUGLY */
public final class C2276ar implements Comparable<C2276ar> {

    /* renamed from: a */
    public long f1608a = -1;

    /* renamed from: b */
    public long f1609b = -1;

    /* renamed from: c */
    public String f1610c = null;

    /* renamed from: d */
    public boolean f1611d = false;

    /* renamed from: e */
    public boolean f1612e = false;

    /* renamed from: f */
    public int f1613f = 0;

    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        int i;
        C2276ar arVar = (C2276ar) obj;
        if (arVar == null || this.f1609b - arVar.f1609b > 0) {
            return 1;
        }
        return i < 0 ? -1 : 0;
    }
}
