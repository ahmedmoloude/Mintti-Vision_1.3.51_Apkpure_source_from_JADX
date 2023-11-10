package com.linktop.whealthService.util;

import com.linktop.utils.Translate;

public class Protocol {
    /* renamed from: a */
    public static int m375a(byte[] bArr, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 = (i2 ^ Translate.m144a(bArr[i3])) & 65535;
        }
        return i2;
    }

    /* renamed from: a */
    public static int m376a(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            i2 = 65535;
        }
        for (int i3 = 0; i3 < i; i3++) {
            int a = ((((i2 << 8) | ((i2 >> 8) & 255)) & 65535) ^ Translate.m144a(bArr[i3])) & 65535;
            int i4 = (a ^ ((a & 255) >> 4)) & 65535;
            int i5 = (i4 ^ ((i4 << 8) << 4)) & 65535;
            i2 = (i5 ^ (((i5 & 255) << 4) << 1)) & 65535;
        }
        return i2;
    }
}
