package com.itextpdf.text.pdf.crypto;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

public class AESCipherCBCnoPad {
    private BlockCipher cbc = new CBCBlockCipher(new AESFastEngine());

    public AESCipherCBCnoPad(boolean z, byte[] bArr) {
        this.cbc.init(z, new KeyParameter(bArr));
    }

    public byte[] processBlock(byte[] bArr, int i, int i2) {
        if (i2 % this.cbc.getBlockSize() == 0) {
            byte[] bArr2 = new byte[i2];
            int i3 = 0;
            while (i2 > 0) {
                this.cbc.processBlock(bArr, i, bArr2, i3);
                i2 -= this.cbc.getBlockSize();
                i3 += this.cbc.getBlockSize();
                i += this.cbc.getBlockSize();
            }
            return bArr2;
        }
        throw new IllegalArgumentException("Not multiple of block: " + i2);
    }
}
