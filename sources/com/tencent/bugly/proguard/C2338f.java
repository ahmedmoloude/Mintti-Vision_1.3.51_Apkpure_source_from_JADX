package com.tencent.bugly.proguard;

import com.itextpdf.text.pdf.BidiOrder;
import com.itextpdf.text.pdf.PdfWriter;

/* renamed from: com.tencent.bugly.proguard.f */
/* compiled from: BUGLY */
public final class C2338f {

    /* renamed from: a */
    public static final byte[] f1884a = new byte[0];

    /* renamed from: b */
    private static final char[] f1885b = {'0', '1', PdfWriter.VERSION_1_2, PdfWriter.VERSION_1_3, PdfWriter.VERSION_1_4, PdfWriter.VERSION_1_5, PdfWriter.VERSION_1_6, PdfWriter.VERSION_1_7, '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: a */
    public static String m879a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            int i2 = i * 2;
            char[] cArr2 = f1885b;
            cArr[i2 + 1] = cArr2[b & BidiOrder.f518B];
            cArr[i2 + 0] = cArr2[((byte) (b >>> 4)) & BidiOrder.f518B];
        }
        return new String(cArr);
    }
}
