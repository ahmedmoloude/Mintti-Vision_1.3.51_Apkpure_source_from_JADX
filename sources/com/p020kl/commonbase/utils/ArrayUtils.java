package com.p020kl.commonbase.utils;

import androidx.core.view.ViewCompat;
import kotlin.UByte;

/* renamed from: com.kl.commonbase.utils.ArrayUtils */
public class ArrayUtils {
    public static byte[] int2bytes(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((65280 & i) >> 8), (byte) ((16711680 & i) >> 16), (byte) ((i & ViewCompat.MEASURED_STATE_MASK) >> 24)};
    }

    public static void int2bytes(int i, byte[] bArr, int i2) {
        bArr[i2 + 0] = (byte) (i & 255);
        bArr[i2 + 1] = (byte) ((65280 & i) >> 8);
        bArr[i2 + 2] = (byte) ((16711680 & i) >> 16);
        bArr[i2 + 3] = (byte) ((i & ViewCompat.MEASURED_STATE_MASK) >> 24);
    }

    public static int bytes2int(byte[] bArr, int i) {
        return (bArr[i + 0] & UByte.MAX_VALUE) | ((((((bArr[i + 3] & UByte.MAX_VALUE) << 8) | (bArr[i + 2] & UByte.MAX_VALUE)) << 8) | (bArr[i + 1] & UByte.MAX_VALUE)) << 8);
    }

    public static int bytes2int(byte[] bArr) {
        return (bArr[0] & UByte.MAX_VALUE) | ((((((bArr[3] & UByte.MAX_VALUE) << 8) | (bArr[2] & UByte.MAX_VALUE)) << 8) | (bArr[1] & UByte.MAX_VALUE)) << 8);
    }
}
