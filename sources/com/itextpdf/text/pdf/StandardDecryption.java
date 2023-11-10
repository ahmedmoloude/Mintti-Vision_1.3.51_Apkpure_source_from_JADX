package com.itextpdf.text.pdf;

import com.itextpdf.text.pdf.crypto.AESCipher;
import com.itextpdf.text.pdf.crypto.ARCFOUREncryption;

public class StandardDecryption {
    private static final int AES_128 = 4;
    private static final int AES_256 = 5;
    private boolean aes;
    protected ARCFOUREncryption arcfour;
    protected AESCipher cipher;
    private boolean initiated;

    /* renamed from: iv */
    private byte[] f692iv = new byte[16];
    private int ivptr;
    private byte[] key;

    public StandardDecryption(byte[] bArr, int i, int i2, int i3) {
        boolean z = i3 == 4 || i3 == 5;
        this.aes = z;
        if (z) {
            byte[] bArr2 = new byte[i2];
            this.key = bArr2;
            System.arraycopy(bArr, i, bArr2, 0, i2);
            return;
        }
        ARCFOUREncryption aRCFOUREncryption = new ARCFOUREncryption();
        this.arcfour = aRCFOUREncryption;
        aRCFOUREncryption.prepareARCFOURKey(bArr, i, i2);
    }

    public byte[] update(byte[] bArr, int i, int i2) {
        if (!this.aes) {
            byte[] bArr2 = new byte[i2];
            this.arcfour.encryptARCFOUR(bArr, i, i2, bArr2, 0);
            return bArr2;
        } else if (this.initiated) {
            return this.cipher.update(bArr, i, i2);
        } else {
            int min = Math.min(this.f692iv.length - this.ivptr, i2);
            System.arraycopy(bArr, i, this.f692iv, this.ivptr, min);
            int i3 = i + min;
            int i4 = i2 - min;
            int i5 = this.ivptr + min;
            this.ivptr = i5;
            byte[] bArr3 = this.f692iv;
            if (i5 != bArr3.length) {
                return null;
            }
            AESCipher aESCipher = new AESCipher(false, this.key, bArr3);
            this.cipher = aESCipher;
            this.initiated = true;
            if (i4 > 0) {
                return aESCipher.update(bArr, i3, i4);
            }
            return null;
        }
    }

    public byte[] finish() {
        if (this.aes) {
            return this.cipher.doFinal();
        }
        return null;
    }
}
