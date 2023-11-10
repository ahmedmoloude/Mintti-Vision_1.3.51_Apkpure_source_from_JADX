package com.linktop.utils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import kotlin.UByte;

public class Translate {
    public static double TempC2F(double d) {
        return new BigDecimal(((d * 9.0d) / 5.0d) + 32.0d).setScale(1, 4).doubleValue();
    }

    /* renamed from: a */
    public static int m144a(byte b) {
        return b & UByte.MAX_VALUE;
    }

    /* renamed from: a */
    public static String m145a(byte[] bArr) {
        return new String(bArr, StandardCharsets.UTF_8);
    }
}
