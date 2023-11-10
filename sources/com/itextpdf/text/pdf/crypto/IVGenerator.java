package com.itextpdf.text.pdf.crypto;

public final class IVGenerator {
    private static ARCFOUREncryption arcfour = new ARCFOUREncryption();

    static {
        long currentTimeMillis = System.currentTimeMillis();
        long freeMemory = Runtime.getRuntime().freeMemory();
        arcfour.prepareARCFOURKey((currentTimeMillis + "+" + freeMemory).getBytes());
    }

    private IVGenerator() {
    }

    public static byte[] getIV() {
        return getIV(16);
    }

    public static byte[] getIV(int i) {
        byte[] bArr = new byte[i];
        synchronized (arcfour) {
            arcfour.encryptARCFOUR(bArr);
        }
        return bArr;
    }
}
