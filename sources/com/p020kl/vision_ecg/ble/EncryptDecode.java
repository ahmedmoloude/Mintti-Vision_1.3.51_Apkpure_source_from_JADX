package com.p020kl.vision_ecg.ble;

import com.itextpdf.text.DocWriter;
import com.itextpdf.text.pdf.BidiOrder;
import com.itextpdf.text.pdf.ByteBuffer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* renamed from: com.kl.vision_ecg.ble.EncryptDecode */
public class EncryptDecode {
    private final byte[] ENCRYPT_KEY_CPT = {67, 80, 84, 66, 84, 80, 84, 80, 66, 67, 80, 84, 84, 80, 84, 80};
    private final byte[] ENCRYPT_KEY_FUCK = {70, 117, 99, 107, DocWriter.SPACE, 121, 111, 117, 33, DocWriter.SPACE, 84, 104, 105, 101, 102, 33};
    private final String KEY_CPT = "CPTBTPTPBCPTTPTP";
    private final String KEY_FUCK = "Fuck you! Thief!";
    private final byte[] TABLE_CRC_H = {0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 1, -64, ByteCompanionObject.MIN_VALUE, 65, 0, -63, -127, 64};
    private final byte[] TABLE_CRC_J = {0, -64, -63, 1, -61, 3, 2, -62, -58, 6, 7, -57, 5, -59, -60, 4, -52, BidiOrder.f520CS, BidiOrder.NSM, -51, BidiOrder.f518B, -49, -50, BidiOrder.f519BN, 10, -54, -53, BidiOrder.f517AN, -55, 9, 8, -56, -40, 24, 25, -39, 27, -37, -38, 26, 30, -34, -33, 31, -35, 29, 28, -36, 20, -44, -43, 21, -41, 23, 22, -42, -46, 18, 19, -45, BidiOrder.f528WS, -47, -48, BidiOrder.f527S, -16, ByteBuffer.ZERO, 49, -15, 51, -13, -14, 50, 54, -10, -9, 55, -11, 53, 52, -12, DocWriter.f438LT, -4, -3, DocWriter.EQUALS, -1, 63, DocWriter.f437GT, -2, -6, 58, 59, -5, 57, -7, -8, 56, 40, -24, -23, 41, -21, 43, 42, -22, -18, 46, DocWriter.FORWARD, -17, 45, -19, -20, 44, -28, 36, 37, -27, 39, -25, -26, 38, DocWriter.QUOTE, -30, -29, 35, -31, 33, DocWriter.SPACE, -32, -96, 96, 97, -95, 99, -93, -94, 98, 102, -90, -89, 103, -91, 101, 100, -92, 108, -84, -83, 109, -81, 111, 110, -82, -86, 106, 107, -85, 105, -87, -88, 104, 120, -72, -71, 121, -69, 123, 122, -70, -66, 126, ByteCompanionObject.MAX_VALUE, -65, 125, -67, -68, 124, -76, 116, 117, -75, 119, -73, -74, 118, 114, -78, -77, 115, -79, 113, 112, -80, 80, -112, -111, 81, -109, 83, 82, -110, -106, 86, 87, -105, 85, -107, -108, 84, -100, 92, 93, -99, 95, -97, -98, 94, 90, -102, -101, 91, -103, 89, 88, -104, -120, 72, 73, -119, 75, -117, -118, 74, 78, -114, -113, 79, -115, 77, 76, -116, 68, -124, -123, 69, -121, 71, 70, -122, -126, 66, 67, -125, 65, -127, ByteCompanionObject.MIN_VALUE, 64};

    public byte[] Decrypt(byte[] bArr, String str) {
        try {
            byte[] bytes = str.getBytes("ASCII");
            SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/NoPadding");
            instance.init(2, secretKeySpec, new IvParameterSpec(bytes));
            byte[] doFinal = instance.doFinal(bArr);
            try {
                new String(doFinal, "utf-8");
                return doFinal;
            } catch (Exception unused) {
                return doFinal;
            }
        } catch (Exception unused2) {
            return null;
        }
    }

    public byte[] Decrypts(byte[] bArr) {
        byte[] bArr2 = new byte[16];
        int i = 4;
        int i2 = 4;
        int i3 = 0;
        while (i2 < bArr.length) {
            bArr2[i3] = bArr[i2];
            i2++;
            i3++;
        }
        byte[] Decrypt = Decrypt(bArr2, "CPTBTPTPBCPTTPTP");
        int i4 = 0;
        while (i < bArr.length) {
            bArr[i] = Decrypt[i4];
            i++;
            i4++;
        }
        for (int i5 = 0; i5 < Decrypt.length; i5++) {
            Decrypt[i5] = bArr[i5];
        }
        byte[] Decrypt2 = Decrypt(Decrypt, "Fuck you! Thief!");
        for (int i6 = 0; i6 < Decrypt2.length; i6++) {
            bArr[i6] = Decrypt2[i6];
        }
        return bArr;
    }

    public byte[] Encrypt(byte[] bArr) {
        byte[] bArr2 = new byte[16];
        int i = 0;
        for (int i2 = 0; i2 < 16; i2++) {
            bArr2[i2] = bArr[i2];
        }
        byte[] Encrypt = Encrypt(bArr2, this.ENCRYPT_KEY_FUCK);
        for (int i3 = 0; i3 < Encrypt.length; i3++) {
            bArr[i3] = Encrypt[i3];
        }
        int i4 = 4;
        int i5 = 4;
        int i6 = 0;
        while (i5 < bArr.length) {
            Encrypt[i6] = bArr[i5];
            i5++;
            i6++;
        }
        byte[] Encrypt2 = Encrypt(Encrypt, this.ENCRYPT_KEY_CPT);
        while (i4 < bArr.length) {
            bArr[i4] = Encrypt2[i];
            i4++;
            i++;
        }
        return bArr;
    }

    public byte[] Encrypt(byte[] bArr, byte[] bArr2) {
        byte[] bArr3;
        byte[] bArr4 = new byte[16];
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(1, secretKeySpec, new IvParameterSpec(bArr2));
            bArr3 = instance.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            bArr3 = null;
        }
        for (int i = 0; i < 16; i++) {
            bArr4[i] = bArr3[i];
        }
        return bArr4;
    }

    public byte[] crc16_get(byte[] bArr, int i) {
        byte[] bArr2 = new byte[2];
        int i2 = 0;
        byte b = UByte.MAX_VALUE;
        byte b2 = UByte.MAX_VALUE;
        while (i2 < i) {
            byte b3 = (b ^ bArr[i2]) & UByte.MAX_VALUE;
            i2++;
            byte b4 = b2 ^ this.TABLE_CRC_H[b3];
            b2 = this.TABLE_CRC_J[b3];
            b = b4;
        }
        bArr2[0] = (byte) b2;
        bArr2[1] = (byte) b;
        return bArr2;
    }

    public int getBinInt(String str) {
        return Integer.parseInt(Pattern.compile("[^0-9]").matcher(str).replaceAll("").trim());
    }

    public byte[] getBytesFromFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1000);
            byte[] bArr = new byte[1000];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    byteArrayOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException unused) {
            return null;
        }
    }

    public byte[] getMd5(byte[] bArr) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            messageDigest = null;
        }
        messageDigest.update(bArr);
        return messageDigest.digest();
    }
}
