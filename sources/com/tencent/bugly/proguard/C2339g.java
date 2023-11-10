package com.tencent.bugly.proguard;

import androidx.core.app.NotificationCompat;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.g */
/* compiled from: BUGLY */
public final class C2339g extends C2346m {

    /* renamed from: k */
    static byte[] f1886k = null;

    /* renamed from: l */
    static Map<String, String> f1887l = null;

    /* renamed from: m */
    static final /* synthetic */ boolean f1888m = true;

    /* renamed from: a */
    public short f1889a = 0;

    /* renamed from: b */
    public byte f1890b = 0;

    /* renamed from: c */
    public int f1891c = 0;

    /* renamed from: d */
    public int f1892d = 0;

    /* renamed from: e */
    public String f1893e = null;

    /* renamed from: f */
    public String f1894f = null;

    /* renamed from: g */
    public byte[] f1895g;

    /* renamed from: h */
    public int f1896h = 0;

    /* renamed from: i */
    public Map<String, String> f1897i;

    /* renamed from: j */
    public Map<String, String> f1898j;

    public final boolean equals(Object obj) {
        C2339g gVar = (C2339g) obj;
        return C2347n.m961a(1, (int) gVar.f1889a) && C2347n.m961a(1, (int) gVar.f1890b) && C2347n.m961a(1, gVar.f1891c) && C2347n.m961a(1, gVar.f1892d) && C2347n.m963a((Object) 1, (Object) gVar.f1893e) && C2347n.m963a((Object) 1, (Object) gVar.f1894f) && C2347n.m963a((Object) 1, (Object) gVar.f1895g) && C2347n.m961a(1, gVar.f1896h) && C2347n.m963a((Object) 1, (Object) gVar.f1897i) && C2347n.m963a((Object) 1, (Object) gVar.f1898j);
    }

    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            if (f1888m) {
                return null;
            }
            throw new AssertionError();
        }
    }

    /* renamed from: a */
    public final void mo28089a(C2345l lVar) {
        lVar.mo28135a(this.f1889a, 1);
        lVar.mo28127a(this.f1890b, 2);
        lVar.mo28128a(this.f1891c, 3);
        lVar.mo28128a(this.f1892d, 4);
        lVar.mo28132a(this.f1893e, 5);
        lVar.mo28132a(this.f1894f, 6);
        lVar.mo28137a(this.f1895g, 7);
        lVar.mo28128a(this.f1896h, 8);
        lVar.mo28134a(this.f1897i, 9);
        lVar.mo28134a(this.f1898j, 10);
    }

    /* renamed from: a */
    public final void mo28088a(C2343k kVar) {
        try {
            this.f1889a = kVar.mo28121a(this.f1889a, 1, true);
            this.f1890b = kVar.mo28114a(this.f1890b, 2, true);
            this.f1891c = kVar.mo28115a(this.f1891c, 3, true);
            this.f1892d = kVar.mo28115a(this.f1892d, 4, true);
            this.f1893e = kVar.mo28124b(5, true);
            this.f1894f = kVar.mo28124b(6, true);
            if (f1886k == null) {
                f1886k = new byte[]{0};
            }
            this.f1895g = kVar.mo28125c(7, true);
            this.f1896h = kVar.mo28115a(this.f1896h, 8, true);
            if (f1887l == null) {
                HashMap hashMap = new HashMap();
                f1887l = hashMap;
                hashMap.put("", "");
            }
            this.f1897i = (Map) kVar.mo28119a(f1887l, 9, true);
            if (f1887l == null) {
                HashMap hashMap2 = new HashMap();
                f1887l = hashMap2;
                hashMap2.put("", "");
            }
            this.f1898j = (Map) kVar.mo28119a(f1887l, 10, true);
        } catch (Exception e) {
            e.printStackTrace();
            PrintStream printStream = System.out;
            printStream.println("RequestPacket decode error " + C2338f.m879a(this.f1895g));
            throw new RuntimeException(e);
        }
    }

    /* renamed from: a */
    public final void mo28090a(StringBuilder sb, int i) {
        C2341i iVar = new C2341i(sb, i);
        iVar.mo28111a(this.f1889a, "iVersion");
        iVar.mo28105a(this.f1890b, "cPacketType");
        iVar.mo28106a(this.f1891c, "iMessageType");
        iVar.mo28106a(this.f1892d, "iRequestId");
        iVar.mo28109a(this.f1893e, "sServantName");
        iVar.mo28109a(this.f1894f, "sFuncName");
        iVar.mo28113a(this.f1895g, "sBuffer");
        iVar.mo28106a(this.f1896h, "iTimeout");
        iVar.mo28110a(this.f1897i, "context");
        iVar.mo28110a(this.f1898j, NotificationCompat.CATEGORY_STATUS);
    }
}
