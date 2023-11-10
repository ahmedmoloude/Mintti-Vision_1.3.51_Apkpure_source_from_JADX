package com.tencent.bugly.proguard;

import java.nio.ByteBuffer;

/* renamed from: com.tencent.bugly.proguard.n */
/* compiled from: BUGLY */
public final class C2347n {

    /* renamed from: a */
    private static final byte[] f1907a;

    /* renamed from: b */
    private static final byte[] f1908b;

    /* renamed from: a */
    public static boolean m961a(int i, int i2) {
        return i == i2;
    }

    /* renamed from: a */
    public static boolean m962a(long j, long j2) {
        return j == j2;
    }

    /* renamed from: a */
    public static boolean m964a(boolean z, boolean z2) {
        return z == z2;
    }

    /* renamed from: a */
    public static boolean m963a(Object obj, Object obj2) {
        return obj.equals(obj2);
    }

    /* renamed from: a */
    public static byte[] m965a(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        byte[] bArr = new byte[position];
        System.arraycopy(byteBuffer.array(), 0, bArr, 0, position);
        return bArr;
    }

    static {
        byte[] bArr = {com.itextpdf.text.pdf.ByteBuffer.ZERO, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
        byte[] bArr2 = new byte[256];
        byte[] bArr3 = new byte[256];
        for (int i = 0; i < 256; i++) {
            bArr2[i] = bArr[i >>> 4];
            bArr3[i] = bArr[i & 15];
        }
        f1907a = bArr2;
        f1908b = bArr3;
    }
}
