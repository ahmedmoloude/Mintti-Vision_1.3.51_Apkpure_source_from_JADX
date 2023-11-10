package com.tencent.bugly.proguard;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.i */
/* compiled from: BUGLY */
public final class C2341i {

    /* renamed from: a */
    private StringBuilder f1899a;

    /* renamed from: b */
    private int f1900b = 0;

    /* renamed from: a */
    private void m894a(String str) {
        for (int i = 0; i < this.f1900b; i++) {
            this.f1899a.append(9);
        }
        if (str != null) {
            StringBuilder sb = this.f1899a;
            sb.append(str);
            sb.append(": ");
        }
    }

    public C2341i(StringBuilder sb, int i) {
        this.f1899a = sb;
        this.f1900b = i;
    }

    /* renamed from: a */
    public final C2341i mo28112a(boolean z, String str) {
        m894a(str);
        StringBuilder sb = this.f1899a;
        sb.append(z ? 'T' : 'F');
        sb.append(10);
        return this;
    }

    /* renamed from: a */
    public final C2341i mo28105a(byte b, String str) {
        m894a(str);
        StringBuilder sb = this.f1899a;
        sb.append(b);
        sb.append(10);
        return this;
    }

    /* renamed from: a */
    private C2341i m883a(char c, String str) {
        m894a(str);
        StringBuilder sb = this.f1899a;
        sb.append(c);
        sb.append(10);
        return this;
    }

    /* renamed from: a */
    public final C2341i mo28111a(short s, String str) {
        m894a(str);
        StringBuilder sb = this.f1899a;
        sb.append(s);
        sb.append(10);
        return this;
    }

    /* renamed from: a */
    public final C2341i mo28106a(int i, String str) {
        m894a(str);
        StringBuilder sb = this.f1899a;
        sb.append(i);
        sb.append(10);
        return this;
    }

    /* renamed from: a */
    public final C2341i mo28107a(long j, String str) {
        m894a(str);
        StringBuilder sb = this.f1899a;
        sb.append(j);
        sb.append(10);
        return this;
    }

    /* renamed from: a */
    private C2341i m885a(float f, String str) {
        m894a(str);
        StringBuilder sb = this.f1899a;
        sb.append(f);
        sb.append(10);
        return this;
    }

    /* renamed from: a */
    private C2341i m884a(double d, String str) {
        m894a(str);
        StringBuilder sb = this.f1899a;
        sb.append(d);
        sb.append(10);
        return this;
    }

    /* renamed from: a */
    public final C2341i mo28109a(String str, String str2) {
        m894a(str2);
        if (str == null) {
            this.f1899a.append("null\n");
        } else {
            StringBuilder sb = this.f1899a;
            sb.append(str);
            sb.append(10);
        }
        return this;
    }

    /* renamed from: a */
    public final C2341i mo28113a(byte[] bArr, String str) {
        m894a(str);
        if (bArr == null) {
            this.f1899a.append("null\n");
            return this;
        } else if (bArr.length == 0) {
            StringBuilder sb = this.f1899a;
            sb.append(bArr.length);
            sb.append(", []\n");
            return this;
        } else {
            StringBuilder sb2 = this.f1899a;
            sb2.append(bArr.length);
            sb2.append(", [\n");
            C2341i iVar = new C2341i(this.f1899a, this.f1900b + 1);
            for (byte a : bArr) {
                iVar.mo28105a(a, (String) null);
            }
            m883a(']', (String) null);
            return this;
        }
    }

    /* renamed from: a */
    private C2341i m893a(short[] sArr, String str) {
        m894a(str);
        if (sArr == null) {
            this.f1899a.append("null\n");
            return this;
        } else if (sArr.length == 0) {
            StringBuilder sb = this.f1899a;
            sb.append(sArr.length);
            sb.append(", []\n");
            return this;
        } else {
            StringBuilder sb2 = this.f1899a;
            sb2.append(sArr.length);
            sb2.append(", [\n");
            C2341i iVar = new C2341i(this.f1899a, this.f1900b + 1);
            for (short a : sArr) {
                iVar.mo28111a(a, (String) null);
            }
            m883a(']', (String) null);
            return this;
        }
    }

    /* renamed from: a */
    private C2341i m890a(int[] iArr, String str) {
        m894a(str);
        if (iArr == null) {
            this.f1899a.append("null\n");
            return this;
        } else if (iArr.length == 0) {
            StringBuilder sb = this.f1899a;
            sb.append(iArr.length);
            sb.append(", []\n");
            return this;
        } else {
            StringBuilder sb2 = this.f1899a;
            sb2.append(iArr.length);
            sb2.append(", [\n");
            C2341i iVar = new C2341i(this.f1899a, this.f1900b + 1);
            for (int a : iArr) {
                iVar.mo28106a(a, (String) null);
            }
            m883a(']', (String) null);
            return this;
        }
    }

    /* renamed from: a */
    private C2341i m891a(long[] jArr, String str) {
        m894a(str);
        if (jArr == null) {
            this.f1899a.append("null\n");
            return this;
        } else if (jArr.length == 0) {
            StringBuilder sb = this.f1899a;
            sb.append(jArr.length);
            sb.append(", []\n");
            return this;
        } else {
            StringBuilder sb2 = this.f1899a;
            sb2.append(jArr.length);
            sb2.append(", [\n");
            C2341i iVar = new C2341i(this.f1899a, this.f1900b + 1);
            for (long a : jArr) {
                iVar.mo28107a(a, (String) null);
            }
            m883a(']', (String) null);
            return this;
        }
    }

    /* renamed from: a */
    private C2341i m889a(float[] fArr, String str) {
        m894a(str);
        if (fArr == null) {
            this.f1899a.append("null\n");
            return this;
        } else if (fArr.length == 0) {
            StringBuilder sb = this.f1899a;
            sb.append(fArr.length);
            sb.append(", []\n");
            return this;
        } else {
            StringBuilder sb2 = this.f1899a;
            sb2.append(fArr.length);
            sb2.append(", [\n");
            C2341i iVar = new C2341i(this.f1899a, this.f1900b + 1);
            for (float a : fArr) {
                iVar.m885a(a, (String) null);
            }
            m883a(']', (String) null);
            return this;
        }
    }

    /* renamed from: a */
    private C2341i m888a(double[] dArr, String str) {
        m894a(str);
        if (dArr == null) {
            this.f1899a.append("null\n");
            return this;
        } else if (dArr.length == 0) {
            StringBuilder sb = this.f1899a;
            sb.append(dArr.length);
            sb.append(", []\n");
            return this;
        } else {
            StringBuilder sb2 = this.f1899a;
            sb2.append(dArr.length);
            sb2.append(", [\n");
            C2341i iVar = new C2341i(this.f1899a, this.f1900b + 1);
            for (double a : dArr) {
                iVar.m884a(a, (String) null);
            }
            m883a(']', (String) null);
            return this;
        }
    }

    /* renamed from: a */
    public final <K, V> C2341i mo28110a(Map<K, V> map, String str) {
        m894a(str);
        if (map == null) {
            this.f1899a.append("null\n");
            return this;
        } else if (map.isEmpty()) {
            StringBuilder sb = this.f1899a;
            sb.append(map.size());
            sb.append(", {}\n");
            return this;
        } else {
            StringBuilder sb2 = this.f1899a;
            sb2.append(map.size());
            sb2.append(", {\n");
            C2341i iVar = new C2341i(this.f1899a, this.f1900b + 1);
            C2341i iVar2 = new C2341i(this.f1899a, this.f1900b + 2);
            for (Map.Entry next : map.entrySet()) {
                iVar.m883a('(', (String) null);
                iVar2.m886a(next.getKey(), (String) null);
                iVar2.m886a(next.getValue(), (String) null);
                iVar.m883a(')', (String) null);
            }
            m883a('}', (String) null);
            return this;
        }
    }

    /* renamed from: a */
    private <T> C2341i m892a(T[] tArr, String str) {
        m894a(str);
        if (tArr == null) {
            this.f1899a.append("null\n");
            return this;
        } else if (tArr.length == 0) {
            StringBuilder sb = this.f1899a;
            sb.append(tArr.length);
            sb.append(", []\n");
            return this;
        } else {
            StringBuilder sb2 = this.f1899a;
            sb2.append(tArr.length);
            sb2.append(", [\n");
            C2341i iVar = new C2341i(this.f1899a, this.f1900b + 1);
            for (T a : tArr) {
                iVar.m886a(a, (String) null);
            }
            m883a(']', (String) null);
            return this;
        }
    }

    /* renamed from: a */
    private <T> C2341i m887a(Collection<T> collection, String str) {
        if (collection != null) {
            return m892a((T[]) collection.toArray(), str);
        }
        m894a(str);
        this.f1899a.append("null\t");
        return this;
    }

    /* renamed from: a */
    private <T> C2341i m886a(T t, String str) {
        if (t == null) {
            this.f1899a.append("null\n");
        } else if (t instanceof Byte) {
            mo28105a(((Byte) t).byteValue(), str);
        } else if (t instanceof Boolean) {
            mo28112a(((Boolean) t).booleanValue(), str);
        } else if (t instanceof Short) {
            mo28111a(((Short) t).shortValue(), str);
        } else if (t instanceof Integer) {
            mo28106a(((Integer) t).intValue(), str);
        } else if (t instanceof Long) {
            mo28107a(((Long) t).longValue(), str);
        } else if (t instanceof Float) {
            m885a(((Float) t).floatValue(), str);
        } else if (t instanceof Double) {
            m884a(((Double) t).doubleValue(), str);
        } else if (t instanceof String) {
            mo28109a((String) t, str);
        } else if (t instanceof Map) {
            mo28110a((Map) t, str);
        } else if (t instanceof List) {
            m887a((List) t, str);
        } else if (t instanceof C2346m) {
            mo28108a((C2346m) t, str);
        } else if (t instanceof byte[]) {
            mo28113a((byte[]) t, str);
        } else if (t instanceof boolean[]) {
            m886a((boolean[]) t, str);
        } else if (t instanceof short[]) {
            m893a((short[]) t, str);
        } else if (t instanceof int[]) {
            m890a((int[]) t, str);
        } else if (t instanceof long[]) {
            m891a((long[]) t, str);
        } else if (t instanceof float[]) {
            m889a((float[]) t, str);
        } else if (t instanceof double[]) {
            m888a((double[]) t, str);
        } else if (t.getClass().isArray()) {
            m892a((T[]) (Object[]) t, str);
        } else {
            throw new C2342j("write object error: unsupport type.");
        }
        return this;
    }

    /* renamed from: a */
    public final C2341i mo28108a(C2346m mVar, String str) {
        m883a('{', str);
        if (mVar == null) {
            StringBuilder sb = this.f1899a;
            sb.append(9);
            sb.append("null");
        } else {
            mVar.mo28090a(this.f1899a, this.f1900b + 1);
        }
        m883a('}', (String) null);
        return this;
    }
}
