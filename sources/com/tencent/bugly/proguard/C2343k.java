package com.tencent.bugly.proguard;

import com.itextpdf.text.pdf.BidiOrder;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.k */
/* compiled from: BUGLY */
public final class C2343k {

    /* renamed from: a */
    protected String f1901a = "GBK";

    /* renamed from: b */
    private ByteBuffer f1902b;

    /* renamed from: com.tencent.bugly.proguard.k$a */
    /* compiled from: BUGLY */
    public static class C2344a {

        /* renamed from: a */
        public byte f1903a;

        /* renamed from: b */
        public int f1904b;
    }

    public C2343k() {
    }

    public C2343k(byte[] bArr) {
        this.f1902b = ByteBuffer.wrap(bArr);
    }

    public C2343k(byte[] bArr, byte b) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        this.f1902b = wrap;
        wrap.position(4);
    }

    /* renamed from: a */
    public final void mo28122a(byte[] bArr) {
        ByteBuffer byteBuffer = this.f1902b;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
        this.f1902b = ByteBuffer.wrap(bArr);
    }

    /* renamed from: a */
    private static int m906a(C2344a aVar, ByteBuffer byteBuffer) {
        byte b = byteBuffer.get();
        aVar.f1903a = (byte) (b & BidiOrder.f518B);
        aVar.f1904b = (b & 240) >> 4;
        if (aVar.f1904b != 15) {
            return 1;
        }
        aVar.f1904b = byteBuffer.get();
        return 2;
    }

    /* renamed from: a */
    private void m912a(C2344a aVar) {
        m906a(aVar, this.f1902b);
    }

    /* renamed from: a */
    private void m911a(int i) {
        ByteBuffer byteBuffer = this.f1902b;
        byteBuffer.position(byteBuffer.position() + i);
    }

    /* renamed from: b */
    private boolean m915b(int i) {
        try {
            C2344a aVar = new C2344a();
            while (true) {
                int a = m906a(aVar, this.f1902b.duplicate());
                if (i <= aVar.f1904b) {
                    break;
                } else if (aVar.f1903a == 11) {
                    break;
                } else {
                    m911a(a);
                    m910a(aVar.f1903a);
                }
            }
            if (i == aVar.f1904b) {
                return true;
            }
            return false;
        } catch (C2340h | BufferUnderflowException unused) {
        }
    }

    /* renamed from: a */
    private void m909a() {
        C2344a aVar = new C2344a();
        do {
            m912a(aVar);
            m910a(aVar.f1903a);
        } while (aVar.f1903a != 11);
    }

    /* renamed from: b */
    private void m914b() {
        C2344a aVar = new C2344a();
        m912a(aVar);
        m910a(aVar.f1903a);
    }

    /* renamed from: a */
    private void m910a(byte b) {
        int i = 0;
        switch (b) {
            case 0:
                m911a(1);
                return;
            case 1:
                m911a(2);
                return;
            case 2:
                m911a(4);
                return;
            case 3:
                m911a(8);
                return;
            case 4:
                m911a(4);
                return;
            case 5:
                m911a(8);
                return;
            case 6:
                int i2 = this.f1902b.get();
                if (i2 < 0) {
                    i2 += 256;
                }
                m911a(i2);
                return;
            case 7:
                m911a(this.f1902b.getInt());
                return;
            case 8:
                int a = mo28115a(0, 0, true);
                while (i < a * 2) {
                    m914b();
                    i++;
                }
                return;
            case 9:
                int a2 = mo28115a(0, 0, true);
                while (i < a2) {
                    m914b();
                    i++;
                }
                return;
            case 10:
                m909a();
                return;
            case 11:
            case 12:
                return;
            case 13:
                C2344a aVar = new C2344a();
                m912a(aVar);
                if (aVar.f1903a == 0) {
                    m911a(mo28115a(0, 0, true));
                    return;
                }
                throw new C2340h("skipField with invalid type, type value: " + b + ", " + aVar.f1903a);
            default:
                throw new C2340h("invalid type.");
        }
    }

    /* renamed from: a */
    public final boolean mo28123a(int i, boolean z) {
        return mo28114a((byte) 0, i, z) != 0;
    }

    /* renamed from: a */
    public final byte mo28114a(byte b, int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            byte b2 = aVar.f1903a;
            if (b2 == 0) {
                return this.f1902b.get();
            }
            if (b2 == 12) {
                return 0;
            }
            throw new C2340h("type mismatch.");
        } else if (!z) {
            return b;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: a */
    public final short mo28121a(short s, int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            byte b = aVar.f1903a;
            if (b == 0) {
                return (short) this.f1902b.get();
            }
            if (b == 1) {
                return this.f1902b.getShort();
            }
            if (b == 12) {
                return 0;
            }
            throw new C2340h("type mismatch.");
        } else if (!z) {
            return s;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: a */
    public final int mo28115a(int i, int i2, boolean z) {
        if (m915b(i2)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            byte b = aVar.f1903a;
            if (b == 0) {
                return this.f1902b.get();
            }
            if (b == 1) {
                return this.f1902b.getShort();
            }
            if (b == 2) {
                return this.f1902b.getInt();
            }
            if (b == 12) {
                return 0;
            }
            throw new C2340h("type mismatch.");
        } else if (!z) {
            return i;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: a */
    public final long mo28117a(long j, int i, boolean z) {
        int i2;
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            byte b = aVar.f1903a;
            if (b == 0) {
                i2 = this.f1902b.get();
            } else if (b == 1) {
                i2 = this.f1902b.getShort();
            } else if (b == 2) {
                i2 = this.f1902b.getInt();
            } else if (b == 3) {
                return this.f1902b.getLong();
            } else {
                if (b == 12) {
                    return 0;
                }
                throw new C2340h("type mismatch.");
            }
            return (long) i2;
        } else if (!z) {
            return j;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: a */
    private float m905a(float f, int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            byte b = aVar.f1903a;
            if (b == 4) {
                return this.f1902b.getFloat();
            }
            if (b == 12) {
                return 0.0f;
            }
            throw new C2340h("type mismatch.");
        } else if (!z) {
            return f;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: a */
    private double m904a(double d, int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            byte b = aVar.f1903a;
            if (b == 4) {
                return (double) this.f1902b.getFloat();
            }
            if (b == 5) {
                return this.f1902b.getDouble();
            }
            if (b == 12) {
                return 0.0d;
            }
            throw new C2340h("type mismatch.");
        } else if (!z) {
            return d;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: b */
    public final String mo28124b(int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            byte b = aVar.f1903a;
            if (b == 6) {
                int i2 = this.f1902b.get();
                if (i2 < 0) {
                    i2 += 256;
                }
                byte[] bArr = new byte[i2];
                this.f1902b.get(bArr);
                try {
                    return new String(bArr, this.f1901a);
                } catch (UnsupportedEncodingException unused) {
                    return new String(bArr);
                }
            } else if (b == 7) {
                int i3 = this.f1902b.getInt();
                if (i3 > 104857600 || i3 < 0) {
                    throw new C2340h("String too long: ".concat(String.valueOf(i3)));
                }
                byte[] bArr2 = new byte[i3];
                this.f1902b.get(bArr2);
                try {
                    return new String(bArr2, this.f1901a);
                } catch (UnsupportedEncodingException unused2) {
                    return new String(bArr2);
                }
            } else {
                throw new C2340h("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: a */
    public final <K, V> HashMap<K, V> mo28120a(Map<K, V> map, int i, boolean z) {
        return (HashMap) m908a(new HashMap(), map, i, z);
    }

    /* renamed from: a */
    private <K, V> Map<K, V> m908a(Map<K, V> map, Map<K, V> map2, int i, boolean z) {
        if (map2 == null || map2.isEmpty()) {
            return new HashMap();
        }
        Map.Entry next = map2.entrySet().iterator().next();
        Object key = next.getKey();
        Object value = next.getValue();
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            if (aVar.f1903a == 8) {
                int a = mo28115a(0, 0, true);
                if (a >= 0) {
                    for (int i2 = 0; i2 < a; i2++) {
                        map.put(mo28119a(key, 0, true), mo28119a(value, 1, true));
                    }
                } else {
                    throw new C2340h("size invalid: ".concat(String.valueOf(a)));
                }
            } else {
                throw new C2340h("type mismatch.");
            }
        } else if (z) {
            throw new C2340h("require field not exist.");
        }
        return map;
    }

    /* renamed from: d */
    private boolean[] m917d(int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            if (aVar.f1903a == 9) {
                int a = mo28115a(0, 0, true);
                if (a >= 0) {
                    boolean[] zArr = new boolean[a];
                    for (int i2 = 0; i2 < a; i2++) {
                        zArr[i2] = mo28123a(0, true);
                    }
                    return zArr;
                }
                throw new C2340h("size invalid: ".concat(String.valueOf(a)));
            }
            throw new C2340h("type mismatch.");
        } else if (!z) {
            return null;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: c */
    public final byte[] mo28125c(int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            byte b = aVar.f1903a;
            if (b == 9) {
                int a = mo28115a(0, 0, true);
                if (a >= 0) {
                    byte[] bArr = new byte[a];
                    for (int i2 = 0; i2 < a; i2++) {
                        bArr[i2] = mo28114a(bArr[0], 0, true);
                    }
                    return bArr;
                }
                throw new C2340h("size invalid: ".concat(String.valueOf(a)));
            } else if (b == 13) {
                C2344a aVar2 = new C2344a();
                m912a(aVar2);
                if (aVar2.f1903a == 0) {
                    int a2 = mo28115a(0, 0, true);
                    if (a2 >= 0) {
                        byte[] bArr2 = new byte[a2];
                        this.f1902b.get(bArr2);
                        return bArr2;
                    }
                    throw new C2340h("invalid size, tag: " + i + ", type: " + aVar.f1903a + ", " + aVar2.f1903a + ", size: " + a2);
                }
                throw new C2340h("type mismatch, tag: " + i + ", type: " + aVar.f1903a + ", " + aVar2.f1903a);
            } else {
                throw new C2340h("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: e */
    private short[] m918e(int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            if (aVar.f1903a == 9) {
                int a = mo28115a(0, 0, true);
                if (a >= 0) {
                    short[] sArr = new short[a];
                    for (int i2 = 0; i2 < a; i2++) {
                        sArr[i2] = mo28121a(sArr[0], 0, true);
                    }
                    return sArr;
                }
                throw new C2340h("size invalid: ".concat(String.valueOf(a)));
            }
            throw new C2340h("type mismatch.");
        } else if (!z) {
            return null;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: f */
    private int[] m919f(int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            if (aVar.f1903a == 9) {
                int a = mo28115a(0, 0, true);
                if (a >= 0) {
                    int[] iArr = new int[a];
                    for (int i2 = 0; i2 < a; i2++) {
                        iArr[i2] = mo28115a(iArr[0], 0, true);
                    }
                    return iArr;
                }
                throw new C2340h("size invalid: ".concat(String.valueOf(a)));
            }
            throw new C2340h("type mismatch.");
        } else if (!z) {
            return null;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: g */
    private long[] m920g(int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            if (aVar.f1903a == 9) {
                int a = mo28115a(0, 0, true);
                if (a >= 0) {
                    long[] jArr = new long[a];
                    for (int i2 = 0; i2 < a; i2++) {
                        jArr[i2] = mo28117a(jArr[0], 0, true);
                    }
                    return jArr;
                }
                throw new C2340h("size invalid: ".concat(String.valueOf(a)));
            }
            throw new C2340h("type mismatch.");
        } else if (!z) {
            return null;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: h */
    private float[] m921h(int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            if (aVar.f1903a == 9) {
                int a = mo28115a(0, 0, true);
                if (a >= 0) {
                    float[] fArr = new float[a];
                    for (int i2 = 0; i2 < a; i2++) {
                        fArr[i2] = m905a(fArr[0], 0, true);
                    }
                    return fArr;
                }
                throw new C2340h("size invalid: ".concat(String.valueOf(a)));
            }
            throw new C2340h("type mismatch.");
        } else if (!z) {
            return null;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: i */
    private double[] m922i(int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            if (aVar.f1903a == 9) {
                int a = mo28115a(0, 0, true);
                if (a >= 0) {
                    double[] dArr = new double[a];
                    for (int i2 = 0; i2 < a; i2++) {
                        dArr[i2] = m904a(dArr[0], 0, true);
                    }
                    return dArr;
                }
                throw new C2340h("size invalid: ".concat(String.valueOf(a)));
            }
            throw new C2340h("type mismatch.");
        } else if (!z) {
            return null;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: a */
    private <T> T[] m913a(T[] tArr, int i, boolean z) {
        if (tArr != null && tArr.length != 0) {
            return m916b(tArr[0], i, z);
        }
        throw new C2340h("unable to get type of key and value.");
    }

    /* renamed from: a */
    private <T> List<T> m907a(List<T> list, int i, boolean z) {
        if (list == null || list.isEmpty()) {
            return new ArrayList();
        }
        Object[] b = m916b(list.get(0), i, z);
        if (b == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object add : b) {
            arrayList.add(add);
        }
        return arrayList;
    }

    /* renamed from: b */
    private <T> T[] m916b(T t, int i, boolean z) {
        if (m915b(i)) {
            C2344a aVar = new C2344a();
            m912a(aVar);
            if (aVar.f1903a == 9) {
                int a = mo28115a(0, 0, true);
                if (a >= 0) {
                    T[] tArr = (Object[]) Array.newInstance(t.getClass(), a);
                    for (int i2 = 0; i2 < a; i2++) {
                        tArr[i2] = mo28119a(t, 0, true);
                    }
                    return tArr;
                }
                throw new C2340h("size invalid: ".concat(String.valueOf(a)));
            }
            throw new C2340h("type mismatch.");
        } else if (!z) {
            return null;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: a */
    public final C2346m mo28118a(C2346m mVar, int i, boolean z) {
        if (m915b(i)) {
            try {
                C2346m mVar2 = (C2346m) mVar.getClass().newInstance();
                C2344a aVar = new C2344a();
                m912a(aVar);
                if (aVar.f1903a == 10) {
                    mVar2.mo28088a(this);
                    m909a();
                    return mVar2;
                }
                throw new C2340h("type mismatch.");
            } catch (Exception e) {
                throw new C2340h(e.getMessage());
            }
        } else if (!z) {
            return null;
        } else {
            throw new C2340h("require field not exist.");
        }
    }

    /* renamed from: a */
    public final <T> Object mo28119a(T t, int i, boolean z) {
        if (t instanceof Byte) {
            return Byte.valueOf(mo28114a((byte) 0, i, z));
        }
        if (t instanceof Boolean) {
            return Boolean.valueOf(mo28123a(i, z));
        }
        if (t instanceof Short) {
            return Short.valueOf(mo28121a(0, i, z));
        }
        if (t instanceof Integer) {
            return Integer.valueOf(mo28115a(0, i, z));
        }
        if (t instanceof Long) {
            return Long.valueOf(mo28117a(0, i, z));
        }
        if (t instanceof Float) {
            return Float.valueOf(m905a(0.0f, i, z));
        }
        if (t instanceof Double) {
            return Double.valueOf(m904a(0.0d, i, z));
        }
        if (t instanceof String) {
            return String.valueOf(mo28124b(i, z));
        }
        if (t instanceof Map) {
            return mo28120a((Map) t, i, z);
        }
        if (t instanceof List) {
            return m907a((List) t, i, z);
        }
        if (t instanceof C2346m) {
            return mo28118a((C2346m) t, i, z);
        }
        if (!t.getClass().isArray()) {
            throw new C2340h("read object error: unsupport type.");
        } else if ((t instanceof byte[]) || (t instanceof Byte[])) {
            return mo28125c(i, z);
        } else {
            if (t instanceof boolean[]) {
                return m917d(i, z);
            }
            if (t instanceof short[]) {
                return m918e(i, z);
            }
            if (t instanceof int[]) {
                return m919f(i, z);
            }
            if (t instanceof long[]) {
                return m920g(i, z);
            }
            if (t instanceof float[]) {
                return m921h(i, z);
            }
            if (t instanceof double[]) {
                return m922i(i, z);
            }
            return m913a((T[]) (Object[]) t, i, z);
        }
    }

    /* renamed from: a */
    public final int mo28116a(String str) {
        this.f1901a = str;
        return 0;
    }
}
