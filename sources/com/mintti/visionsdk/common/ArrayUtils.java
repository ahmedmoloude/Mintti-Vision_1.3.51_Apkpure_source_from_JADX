package com.mintti.visionsdk.common;

import com.itextpdf.text.pdf.BidiOrder;
import kotlin.UByte;

public class ArrayUtils {
    public static int bytes2Int(byte[] bArr) {
        return ((bArr[3] & UByte.MAX_VALUE) << 24) | (bArr[0] & UByte.MAX_VALUE) | ((bArr[1] & UByte.MAX_VALUE) << 8) | ((bArr[2] & UByte.MAX_VALUE) << BidiOrder.f527S);
    }

    public static short[] bytes2ShortArray(byte[] bArr) {
        int length = bArr.length / 2;
        short[] sArr = new short[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            sArr[i] = (short) (((bArr[i2 + 1] << 8) & 65280) | (bArr[i2] & UByte.MAX_VALUE));
        }
        return sArr;
    }

    public static int[] bytes2ints(byte[] bArr) {
        int[] iArr = new int[(bArr.length / 4)];
        for (int i = 0; i < bArr.length; i += 4) {
            iArr[i / 4] = (bArr[i] & UByte.MAX_VALUE) | ((bArr[i + 1] & UByte.MAX_VALUE) << 8) | ((bArr[i + 2] & UByte.MAX_VALUE) << BidiOrder.f527S) | ((bArr[i + 3] & UByte.MAX_VALUE) << 24);
        }
        return iArr;
    }

    public static short bytes2short(byte[] bArr) {
        return (short) (((bArr[1] << 8) & 65280) | (bArr[0] & UByte.MAX_VALUE));
    }

    public static String bytesToHex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
            if (hexString.length() < 2) {
                stringBuffer.append(0);
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString();
    }

    public static byte hexToByte(String str) {
        return (byte) Integer.parseInt(str, 16);
    }

    public static byte[] hexToByteArray(String str) {
        byte[] bArr;
        int length = str.length();
        if (length % 2 == 1) {
            length++;
            bArr = new byte[(length / 2)];
            str = "0" + str;
        } else {
            bArr = new byte[(length / 2)];
        }
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i + 2;
            bArr[i2] = hexToByte(str.substring(i, i3));
            i2++;
            i = i3;
        }
        return bArr;
    }

    public static byte[] int2ByteArray(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    public static byte[] ints2ByteArray(int[] iArr) {
        byte[] bArr = new byte[(iArr.length * 4)];
        for (int i = 0; i < iArr.length; i++) {
            int i2 = i * 4;
            bArr[i2] = (byte) (iArr[i] & 255);
            bArr[i2 + 1] = (byte) ((iArr[i] >> 8) & 255);
            bArr[i2 + 2] = (byte) ((iArr[i] >> 16) & 255);
            bArr[i2 + 3] = (byte) ((iArr[i] >> 24) & 255);
        }
        return bArr;
    }

    public static byte[] short2bytes(short s, boolean z) {
        byte[] bArr = new byte[2];
        if (z) {
            bArr[0] = (byte) (s & 255);
            bArr[1] = (byte) ((s >> 8) & 255);
            return bArr;
        }
        bArr[1] = (byte) (s & 255);
        bArr[0] = (byte) ((s >> 8) & 255);
        return bArr;
    }

    public static byte[] shorts2ByteArray(short[] sArr) {
        byte[] bArr = new byte[(sArr.length * 2)];
        for (int i = 0; i < sArr.length; i++) {
            int i2 = i * 2;
            bArr[i2] = (byte) (sArr[i] & 255);
            bArr[i2 + 1] = (byte) ((sArr[i] >> 8) & 255);
        }
        return bArr;
    }
}
