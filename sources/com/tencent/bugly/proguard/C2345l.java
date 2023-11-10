package com.tencent.bugly.proguard;

import com.itextpdf.text.pdf.BidiOrder;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.l */
/* compiled from: BUGLY */
public final class C2345l {

    /* renamed from: a */
    public ByteBuffer f1905a;

    /* renamed from: b */
    protected String f1906b;

    public C2345l(int i) {
        this.f1906b = "GBK";
        this.f1905a = ByteBuffer.allocate(i);
    }

    public C2345l() {
        this(128);
    }

    /* renamed from: a */
    private void m937a(int i) {
        if (this.f1905a.remaining() < i) {
            ByteBuffer allocate = ByteBuffer.allocate((this.f1905a.capacity() + i) * 2);
            allocate.put(this.f1905a.array(), 0, this.f1905a.position());
            this.f1905a = allocate;
        }
    }

    /* renamed from: b */
    private void m945b(byte b, int i) {
        if (i < 15) {
            this.f1905a.put((byte) (b | (i << 4)));
        } else if (i < 256) {
            this.f1905a.put((byte) (b | 240));
            this.f1905a.put((byte) i);
        } else {
            throw new C2342j("tag is too large: ".concat(String.valueOf(i)));
        }
    }

    /* renamed from: a */
    public final void mo28136a(boolean z, int i) {
        mo28127a(z ? (byte) 1 : 0, i);
    }

    /* renamed from: a */
    public final void mo28127a(byte b, int i) {
        m937a(3);
        if (b == 0) {
            m945b(BidiOrder.f520CS, i);
            return;
        }
        m945b((byte) 0, i);
        this.f1905a.put(b);
    }

    /* renamed from: a */
    public final void mo28135a(short s, int i) {
        m937a(4);
        if (s < -128 || s > 127) {
            m945b((byte) 1, i);
            this.f1905a.putShort(s);
            return;
        }
        mo28127a((byte) s, i);
    }

    /* renamed from: a */
    public final void mo28128a(int i, int i2) {
        m937a(6);
        if (i < -32768 || i > 32767) {
            m945b((byte) 2, i2);
            this.f1905a.putInt(i);
            return;
        }
        mo28135a((short) i, i2);
    }

    /* renamed from: a */
    public final void mo28129a(long j, int i) {
        m937a(10);
        if (j < -2147483648L || j > 2147483647L) {
            m945b((byte) 3, i);
            this.f1905a.putLong(j);
            return;
        }
        mo28128a((int) j, i);
    }

    /* renamed from: a */
    private void m936a(float f, int i) {
        m937a(6);
        m945b((byte) 4, i);
        this.f1905a.putFloat(f);
    }

    /* renamed from: a */
    private void m935a(double d, int i) {
        m937a(10);
        m945b((byte) 5, i);
        this.f1905a.putDouble(d);
    }

    /* renamed from: a */
    public final void mo28132a(String str, int i) {
        byte[] bArr;
        try {
            bArr = str.getBytes(this.f1906b);
        } catch (UnsupportedEncodingException unused) {
            bArr = str.getBytes();
        }
        m937a(bArr.length + 10);
        if (bArr.length > 255) {
            m945b((byte) 7, i);
            this.f1905a.putInt(bArr.length);
            this.f1905a.put(bArr);
            return;
        }
        m945b((byte) 6, i);
        this.f1905a.put((byte) bArr.length);
        this.f1905a.put(bArr);
    }

    /* renamed from: a */
    public final <K, V> void mo28134a(Map<K, V> map, int i) {
        int i2;
        m937a(8);
        m945b((byte) 8, i);
        if (map == null) {
            i2 = 0;
        } else {
            i2 = map.size();
        }
        mo28128a(i2, 0);
        if (map != null) {
            for (Map.Entry next : map.entrySet()) {
                mo28131a(next.getKey(), 0);
                mo28131a(next.getValue(), 1);
            }
        }
    }

    /* renamed from: a */
    private void m944a(boolean[] zArr, int i) {
        m937a(8);
        m945b((byte) 9, i);
        mo28128a(zArr.length, 0);
        for (boolean a : zArr) {
            mo28136a(a, 0);
        }
    }

    /* renamed from: a */
    public final void mo28137a(byte[] bArr, int i) {
        m937a(bArr.length + 8);
        m945b(BidiOrder.NSM, i);
        m945b((byte) 0, 0);
        mo28128a(bArr.length, 0);
        this.f1905a.put(bArr);
    }

    /* renamed from: a */
    private void m943a(short[] sArr, int i) {
        m937a(8);
        m945b((byte) 9, i);
        mo28128a(sArr.length, 0);
        for (short a : sArr) {
            mo28135a(a, 0);
        }
    }

    /* renamed from: a */
    private void m940a(int[] iArr, int i) {
        m937a(8);
        m945b((byte) 9, i);
        mo28128a(iArr.length, 0);
        for (int a : iArr) {
            mo28128a(a, 0);
        }
    }

    /* renamed from: a */
    private void m941a(long[] jArr, int i) {
        m937a(8);
        m945b((byte) 9, i);
        mo28128a(jArr.length, 0);
        for (long a : jArr) {
            mo28129a(a, 0);
        }
    }

    /* renamed from: a */
    private void m939a(float[] fArr, int i) {
        m937a(8);
        m945b((byte) 9, i);
        mo28128a(fArr.length, 0);
        for (float a : fArr) {
            m936a(a, 0);
        }
    }

    /* renamed from: a */
    private void m938a(double[] dArr, int i) {
        m937a(8);
        m945b((byte) 9, i);
        mo28128a(dArr.length, 0);
        for (double a : dArr) {
            m935a(a, 0);
        }
    }

    /* renamed from: a */
    private void m942a(Object[] objArr, int i) {
        m937a(8);
        m945b((byte) 9, i);
        mo28128a(objArr.length, 0);
        for (Object a : objArr) {
            mo28131a(a, 0);
        }
    }

    /* renamed from: a */
    public final <T> void mo28133a(Collection<T> collection, int i) {
        int i2;
        m937a(8);
        m945b((byte) 9, i);
        if (collection == null) {
            i2 = 0;
        } else {
            i2 = collection.size();
        }
        mo28128a(i2, 0);
        if (collection != null) {
            for (T a : collection) {
                mo28131a((Object) a, 0);
            }
        }
    }

    /* renamed from: a */
    public final void mo28130a(C2346m mVar, int i) {
        m937a(2);
        m945b((byte) 10, i);
        mVar.mo28089a(this);
        m937a(2);
        m945b(BidiOrder.f517AN, 0);
    }

    /* renamed from: a */
    public final void mo28131a(Object obj, int i) {
        if (obj instanceof Byte) {
            mo28127a(((Byte) obj).byteValue(), i);
        } else if (obj instanceof Boolean) {
            mo28136a(((Boolean) obj).booleanValue(), i);
        } else if (obj instanceof Short) {
            mo28135a(((Short) obj).shortValue(), i);
        } else if (obj instanceof Integer) {
            mo28128a(((Integer) obj).intValue(), i);
        } else if (obj instanceof Long) {
            mo28129a(((Long) obj).longValue(), i);
        } else if (obj instanceof Float) {
            m936a(((Float) obj).floatValue(), i);
        } else if (obj instanceof Double) {
            m935a(((Double) obj).doubleValue(), i);
        } else if (obj instanceof String) {
            mo28132a((String) obj, i);
        } else if (obj instanceof Map) {
            mo28134a((Map) obj, i);
        } else if (obj instanceof List) {
            mo28133a((List) obj, i);
        } else if (obj instanceof C2346m) {
            mo28130a((C2346m) obj, i);
        } else if (obj instanceof byte[]) {
            mo28137a((byte[]) obj, i);
        } else if (obj instanceof boolean[]) {
            m944a((boolean[]) obj, i);
        } else if (obj instanceof short[]) {
            m943a((short[]) obj, i);
        } else if (obj instanceof int[]) {
            m940a((int[]) obj, i);
        } else if (obj instanceof long[]) {
            m941a((long[]) obj, i);
        } else if (obj instanceof float[]) {
            m939a((float[]) obj, i);
        } else if (obj instanceof double[]) {
            m938a((double[]) obj, i);
        } else if (obj.getClass().isArray()) {
            m942a((Object[]) obj, i);
        } else if (obj instanceof Collection) {
            mo28133a((Collection) obj, i);
        } else {
            throw new C2342j("write object error: unsupport type. " + obj.getClass());
        }
    }

    /* renamed from: a */
    public final int mo28126a(String str) {
        this.f1906b = str;
        return 0;
    }
}
