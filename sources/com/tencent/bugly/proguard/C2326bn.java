package com.tencent.bugly.proguard;

/* renamed from: com.tencent.bugly.proguard.bn */
/* compiled from: BUGLY */
public final class C2326bn extends C2346m implements Cloneable {

    /* renamed from: d */
    static byte[] f1768d;

    /* renamed from: a */
    public byte f1769a = 0;

    /* renamed from: b */
    public String f1770b = "";

    /* renamed from: c */
    public byte[] f1771c = null;

    /* renamed from: a */
    public final void mo28090a(StringBuilder sb, int i) {
    }

    public C2326bn() {
    }

    public C2326bn(byte b, String str, byte[] bArr) {
        this.f1769a = b;
        this.f1770b = str;
        this.f1771c = bArr;
    }

    /* renamed from: a */
    public final void mo28089a(C2345l lVar) {
        lVar.mo28127a(this.f1769a, 0);
        lVar.mo28132a(this.f1770b, 1);
        byte[] bArr = this.f1771c;
        if (bArr != null) {
            lVar.mo28137a(bArr, 2);
        }
    }

    /* renamed from: a */
    public final void mo28088a(C2343k kVar) {
        this.f1769a = kVar.mo28114a(this.f1769a, 0, true);
        this.f1770b = kVar.mo28124b(1, true);
        if (f1768d == null) {
            byte[] bArr = new byte[1];
            f1768d = bArr;
            bArr[0] = 0;
        }
        this.f1771c = kVar.mo28125c(2, false);
    }
}
