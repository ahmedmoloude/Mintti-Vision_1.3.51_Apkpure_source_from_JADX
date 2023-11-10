package com.linktop.whealthService.util;

import kotlin.UByte;

public class ParseByte {

    /* renamed from: a */
    private String f1233a = null;

    /* renamed from: b */
    private int f1234b = 0;

    /* renamed from: c */
    private byte f1235c = 0;

    public ParseByte(byte[] bArr) {
        int i;
        int i2 = 0;
        while (i2 < bArr.length - 1) {
            byte b = bArr[i2];
            int i3 = i2 + 1;
            if (bArr[i3] == 9) {
                int i4 = b - 1;
                byte[] bArr2 = new byte[i4];
                System.arraycopy(bArr, i2 + 2, bArr2, 0, i4);
                this.f1233a = new String(bArr2);
                i = i2 + b;
            } else {
                i = i2 + b;
                if (bArr[i3] == -1) {
                    int i5 = ((bArr[i] & UByte.MAX_VALUE) << 8) + (bArr[i - 1] & UByte.MAX_VALUE);
                    this.f1234b = i5;
                    if (i5 > 32768) {
                        this.f1234b = (i5 - 65535) - 1;
                    }
                    this.f1235c = bArr[i - 2];
                }
            }
            i2 = i + 1;
        }
    }

    /* renamed from: a */
    public byte mo27592a() {
        return this.f1235c;
    }

    /* renamed from: b */
    public String mo27593b() {
        return this.f1233a;
    }

    /* renamed from: c */
    public int mo27594c() {
        return this.f1234b;
    }
}
