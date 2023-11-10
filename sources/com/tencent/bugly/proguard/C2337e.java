package com.tencent.bugly.proguard;

import java.nio.ByteBuffer;
import java.util.HashMap;

/* renamed from: com.tencent.bugly.proguard.e */
/* compiled from: BUGLY */
public final class C2337e extends C2336d {

    /* renamed from: h */
    static HashMap<String, byte[]> f1880h;

    /* renamed from: i */
    static HashMap<String, HashMap<String, byte[]>> f1881i;

    /* renamed from: g */
    protected C2339g f1882g;

    /* renamed from: j */
    private int f1883j = 0;

    public C2337e() {
        C2339g gVar = new C2339g();
        this.f1882g = gVar;
        gVar.f1889a = 2;
    }

    /* renamed from: a */
    public final <T> void mo28095a(String str, T t) {
        if (!str.startsWith(".")) {
            super.mo28095a(str, t);
            return;
        }
        throw new IllegalArgumentException("put name can not startwith . , now is ".concat(String.valueOf(str)));
    }

    /* renamed from: b */
    public final void mo28099b() {
        super.mo28099b();
        this.f1882g.f1889a = 3;
    }

    /* renamed from: a */
    public final byte[] mo28097a() {
        if (this.f1882g.f1889a != 2) {
            if (this.f1882g.f1893e == null) {
                this.f1882g.f1893e = "";
            }
            if (this.f1882g.f1894f == null) {
                this.f1882g.f1894f = "";
            }
        } else if (this.f1882g.f1893e.equals("")) {
            throw new IllegalArgumentException("servantName can not is null");
        } else if (this.f1882g.f1894f.equals("")) {
            throw new IllegalArgumentException("funcName can not is null");
        }
        C2345l lVar = new C2345l(0);
        lVar.mo28126a(this.f1874c);
        if (this.f1882g.f1889a == 2) {
            lVar.mo28134a(this.f1872a, 0);
        } else {
            lVar.mo28134a(this.f1877e, 0);
        }
        this.f1882g.f1895g = C2347n.m965a(lVar.f1905a);
        C2345l lVar2 = new C2345l(0);
        lVar2.mo28126a(this.f1874c);
        this.f1882g.mo28089a(lVar2);
        byte[] a = C2347n.m965a(lVar2.f1905a);
        int length = a.length + 4;
        ByteBuffer allocate = ByteBuffer.allocate(length);
        allocate.putInt(length).put(a).flip();
        return allocate.array();
    }

    /* renamed from: a */
    public final void mo28096a(byte[] bArr) {
        if (bArr.length >= 4) {
            try {
                C2343k kVar = new C2343k(bArr, (byte) 0);
                kVar.mo28116a(this.f1874c);
                this.f1882g.mo28088a(kVar);
                if (this.f1882g.f1889a == 3) {
                    C2343k kVar2 = new C2343k(this.f1882g.f1895g);
                    kVar2.mo28116a(this.f1874c);
                    if (f1880h == null) {
                        HashMap<String, byte[]> hashMap = new HashMap<>();
                        f1880h = hashMap;
                        hashMap.put("", new byte[0]);
                    }
                    this.f1877e = kVar2.mo28120a(f1880h, 0, false);
                    return;
                }
                C2343k kVar3 = new C2343k(this.f1882g.f1895g);
                kVar3.mo28116a(this.f1874c);
                if (f1881i == null) {
                    f1881i = new HashMap<>();
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put("", new byte[0]);
                    f1881i.put("", hashMap2);
                }
                this.f1872a = kVar3.mo28120a(f1881i, 0, false);
                this.f1873b = new HashMap();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("decode package must include size head");
        }
    }

    /* renamed from: b */
    public final void mo28100b(String str) {
        this.f1882g.f1893e = str;
    }

    /* renamed from: c */
    public final void mo28102c(String str) {
        this.f1882g.f1894f = str;
    }

    /* renamed from: c */
    public final void mo28101c() {
        this.f1882g.f1892d = 1;
    }
}
