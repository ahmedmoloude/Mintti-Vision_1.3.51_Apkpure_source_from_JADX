package com.itextpdf.text.pdf;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.pdf.crypto.AESCipher;
import com.itextpdf.text.pdf.crypto.ARCFOUREncryption;
import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamEncryption extends OutputStream {
    private static final int AES_128 = 4;
    private static final int AES_256 = 5;
    private boolean aes;
    protected ARCFOUREncryption arcfour;
    protected AESCipher cipher;
    private boolean finished;
    protected OutputStream out;

    /* renamed from: sb */
    private byte[] f534sb;

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0019 A[Catch:{ Exception -> 0x0038 }] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x002d A[Catch:{ Exception -> 0x0038 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public OutputStreamEncryption(java.io.OutputStream r3, byte[] r4, int r5, int r6, int r7) {
        /*
            r2 = this;
            r2.<init>()
            r0 = 1
            byte[] r1 = new byte[r0]
            r2.f534sb = r1
            r2.out = r3     // Catch:{ Exception -> 0x0038 }
            r3 = 4
            r1 = 0
            if (r7 == r3) goto L_0x0014
            r3 = 5
            if (r7 != r3) goto L_0x0012
            goto L_0x0014
        L_0x0012:
            r3 = 0
            goto L_0x0015
        L_0x0014:
            r3 = 1
        L_0x0015:
            r2.aes = r3     // Catch:{ Exception -> 0x0038 }
            if (r3 == 0) goto L_0x002d
            byte[] r3 = com.itextpdf.text.pdf.crypto.IVGenerator.getIV()     // Catch:{ Exception -> 0x0038 }
            byte[] r7 = new byte[r6]     // Catch:{ Exception -> 0x0038 }
            java.lang.System.arraycopy(r4, r5, r7, r1, r6)     // Catch:{ Exception -> 0x0038 }
            com.itextpdf.text.pdf.crypto.AESCipher r4 = new com.itextpdf.text.pdf.crypto.AESCipher     // Catch:{ Exception -> 0x0038 }
            r4.<init>(r0, r7, r3)     // Catch:{ Exception -> 0x0038 }
            r2.cipher = r4     // Catch:{ Exception -> 0x0038 }
            r2.write((byte[]) r3)     // Catch:{ Exception -> 0x0038 }
            goto L_0x0037
        L_0x002d:
            com.itextpdf.text.pdf.crypto.ARCFOUREncryption r3 = new com.itextpdf.text.pdf.crypto.ARCFOUREncryption     // Catch:{ Exception -> 0x0038 }
            r3.<init>()     // Catch:{ Exception -> 0x0038 }
            r2.arcfour = r3     // Catch:{ Exception -> 0x0038 }
            r3.prepareARCFOURKey(r4, r5, r6)     // Catch:{ Exception -> 0x0038 }
        L_0x0037:
            return
        L_0x0038:
            r3 = move-exception
            com.itextpdf.text.ExceptionConverter r4 = new com.itextpdf.text.ExceptionConverter
            r4.<init>(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.OutputStreamEncryption.<init>(java.io.OutputStream, byte[], int, int, int):void");
    }

    public OutputStreamEncryption(OutputStream outputStream, byte[] bArr, int i) {
        this(outputStream, bArr, 0, bArr.length, i);
    }

    public void close() throws IOException {
        finish();
        this.out.close();
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    public void write(int i) throws IOException {
        byte[] bArr = this.f534sb;
        bArr[0] = (byte) i;
        write(bArr, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (this.aes) {
            byte[] update = this.cipher.update(bArr, i, i2);
            if (update != null && update.length != 0) {
                this.out.write(update, 0, update.length);
                return;
            }
            return;
        }
        int min = Math.min(i2, 4192);
        byte[] bArr2 = new byte[min];
        while (i2 > 0) {
            int min2 = Math.min(i2, min);
            this.arcfour.encryptARCFOUR(bArr, i, min2, bArr2, 0);
            this.out.write(bArr2, 0, min2);
            i2 -= min2;
            i += min2;
        }
    }

    public void finish() throws IOException {
        if (!this.finished) {
            this.finished = true;
            if (this.aes) {
                try {
                    byte[] doFinal = this.cipher.doFinal();
                    this.out.write(doFinal, 0, doFinal.length);
                } catch (Exception e) {
                    throw new ExceptionConverter(e);
                }
            }
        }
    }
}
