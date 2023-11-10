package com.linktop.whealthService.util;

import com.linktop.whealthService.task.EcgTask;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import kotlin.UByte;

public class Communicate {

    /* renamed from: a */
    private final IBleDev f1229a;

    /* renamed from: b */
    private int f1230b = 0;

    /* renamed from: c */
    private FileOutputStream f1231c;

    /* renamed from: d */
    private boolean f1232d;

    public Communicate(IBleDev iBleDev) {
        this.f1229a = iBleDev;
    }

    /* renamed from: a */
    private boolean m364a(byte[] bArr) {
        int i = (bArr[1] & UByte.MAX_VALUE) + ((bArr[2] & UByte.MAX_VALUE) << 8);
        if (bArr.length >= 9 && i <= 11 && bArr[0] == 2 && bArr[3] == 4) {
            int i2 = i + 6;
            if ((bArr[i2 + 2] & UByte.MAX_VALUE) != 255 || Protocol.m375a(bArr, 5) != (bArr[5] & UByte.MAX_VALUE) || Protocol.m376a(bArr, i2, 0) != ((bArr[i2 + 1] & UByte.MAX_VALUE) << 8) + (255 & bArr[i2])) {
                return false;
            }
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 6, bArr2, 0, i);
            byte b = bArr[4];
            if (b == -123) {
                this.f1229a.getEcgTask().dealData(bArr2);
            } else if (b == 16) {
                this.f1229a.getAckTask().dealData(bArr2);
            } else if (b == 32) {
                this.f1229a.getSysErrorTask().dealData(bArr2);
            } else if (b == -114) {
                this.f1229a.getDeviceTask().dealData(bArr2);
            } else if (b != -113) {
                switch (b) {
                    case -127:
                        try {
                            if (this.f1231c != null) {
                                this.f1231c.write((Arrays.toString(bArr) + "\n").getBytes(StandardCharsets.UTF_8));
                            }
                        } catch (IOException unused) {
                        }
                        this.f1229a.getBpTask().dealData(bArr2);
                        break;
                    case -126:
                        this.f1229a.getBtTask().dealData(bArr2);
                        break;
                    case -125:
                        this.f1229a.getTestPaperTask().dealData(bArr2);
                        break;
                }
            } else {
                this.f1229a.getBatteryTask().dealData(bArr2);
            }
            return true;
        }
        return false;
    }

    /* renamed from: b */
    private boolean m365b(byte[] bArr) {
        int i;
        EcgTask ecgTask = this.f1229a.getEcgTask();
        if (!ecgTask.queryEcgTestEnable() || (i = ecgTask.ecgStep) <= 0) {
            return false;
        }
        if (i == 1) {
            ecgTask.ecgStep = i + 1;
        }
        ecgTask.dealEcgVal(bArr);
        return true;
    }

    /* renamed from: c */
    private synchronized void m366c(byte[] bArr) {
        if (bArr[0] == 2 && bArr[3] == 4 && bArr[4] == -124 && Protocol.m375a(bArr, 5) == (bArr[5] & UByte.MAX_VALUE)) {
            this.f1230b = Protocol.m376a(bArr, bArr.length, 0);
            byte[] bArr2 = new byte[14];
            System.arraycopy(bArr, 6, bArr2, 0, 14);
            this.f1229a.getOxTask().mo27547a(true);
            this.f1229a.getOxTask().dealData(bArr2);
        }
        if ((bArr[16] & UByte.MAX_VALUE) == 0 && (bArr[19] & UByte.MAX_VALUE) == 255) {
            int a = Protocol.m376a(bArr, bArr.length - 3, this.f1230b);
            this.f1230b = 0;
            if (a == ((bArr[18] & UByte.MAX_VALUE) << 8) + (255 & bArr[17])) {
                byte[] bArr3 = new byte[16];
                System.arraycopy(bArr, 0, bArr3, 0, 16);
                this.f1229a.getOxTask().mo27547a(false);
                this.f1229a.getOxTask().dealData(bArr3);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004f, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0051, code lost:
        return false;
     */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean m367d(byte[] r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            int r0 = r7.length     // Catch:{ all -> 0x0054 }
            r1 = 20
            r2 = 0
            if (r1 != r0) goto L_0x0052
            com.linktop.whealthService.util.IBleDev r0 = r6.f1229a     // Catch:{ all -> 0x0054 }
            com.linktop.whealthService.task.OxTask r0 = r0.getOxTask()     // Catch:{ all -> 0x0054 }
            byte r1 = r7[r2]     // Catch:{ all -> 0x0054 }
            r3 = 2
            r4 = 1
            if (r1 != r3) goto L_0x002e
            r1 = 3
            byte r1 = r7[r1]     // Catch:{ all -> 0x0054 }
            r3 = 4
            if (r1 != r3) goto L_0x002e
            byte r1 = r7[r3]     // Catch:{ all -> 0x0054 }
            r3 = -121(0xffffffffffffff87, float:NaN)
            if (r1 != r3) goto L_0x002e
            r1 = 14
            byte[] r3 = new byte[r1]     // Catch:{ all -> 0x0054 }
            r5 = 6
            java.lang.System.arraycopy(r7, r5, r3, r2, r1)     // Catch:{ all -> 0x0054 }
            r0.mo27547a((boolean) r4)     // Catch:{ all -> 0x0054 }
            r0.dealData(r3)     // Catch:{ all -> 0x0054 }
            goto L_0x004e
        L_0x002e:
            boolean r1 = r0.mo27549c()     // Catch:{ all -> 0x0054 }
            if (r1 == 0) goto L_0x0050
            r1 = 18
            byte r1 = r7[r1]     // Catch:{ all -> 0x0054 }
            r3 = -1
            if (r1 != r3) goto L_0x0050
            r1 = 19
            byte r1 = r7[r1]     // Catch:{ all -> 0x0054 }
            if (r1 != 0) goto L_0x0050
            r1 = 16
            byte[] r3 = new byte[r1]     // Catch:{ all -> 0x0054 }
            java.lang.System.arraycopy(r7, r2, r3, r2, r1)     // Catch:{ all -> 0x0054 }
            r0.mo27547a((boolean) r2)     // Catch:{ all -> 0x0054 }
            r0.dealData(r3)     // Catch:{ all -> 0x0054 }
        L_0x004e:
            monitor-exit(r6)
            return r4
        L_0x0050:
            monitor-exit(r6)
            return r2
        L_0x0052:
            monitor-exit(r6)
            return r2
        L_0x0054:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.linktop.whealthService.util.Communicate.m367d(byte[]):boolean");
    }

    /* renamed from: a */
    public void mo27587a() {
        try {
            FileOutputStream fileOutputStream = this.f1231c;
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                this.f1231c.close();
                this.f1231c = null;
            }
        } catch (IOException unused) {
        }
    }

    /* renamed from: a */
    public void mo27588a(byte b, byte[] bArr) {
        if (!this.f1232d) {
            int length = bArr.length;
            int i = length + 1 + 1 + 2 + 1 + 1 + 1 + 2;
            byte[] bArr2 = new byte[i];
            bArr2[0] = 1;
            bArr2[1] = (byte) (length & 255);
            bArr2[2] = (byte) ((length & 65280) >> 8);
            bArr2[3] = 4;
            bArr2[4] = b;
            bArr2[5] = (byte) Protocol.m375a(bArr2, 5);
            System.arraycopy(bArr, 0, bArr2, 6, bArr.length);
            int a = Protocol.m376a(bArr2, (i - 2) - 1, 0);
            bArr2[bArr.length + 5 + 1] = (byte) (a & 255);
            bArr2[bArr.length + 5 + 2] = (byte) ((a & 65280) >> 8);
            bArr2[bArr.length + 5 + 3] = -1;
            this.f1229a.cmdToSend(bArr2);
        }
    }

    /* renamed from: a */
    public void mo27589a(File file) {
        try {
            this.f1231c = new FileOutputStream(file);
        } catch (FileNotFoundException unused) {
        }
    }

    /* renamed from: a */
    public void mo27590a(boolean z) {
        this.f1232d = z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void packageParse(byte[] r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.m367d(r2)     // Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r1)
            return
        L_0x0009:
            boolean r0 = r1.m365b(r2)     // Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x0011
            monitor-exit(r1)
            return
        L_0x0011:
            boolean r0 = r1.m364a((byte[]) r2)     // Catch:{ all -> 0x001c }
            if (r0 != 0) goto L_0x001a
            r1.m366c(r2)     // Catch:{ all -> 0x001c }
        L_0x001a:
            monitor-exit(r1)
            return
        L_0x001c:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.linktop.whealthService.util.Communicate.packageParse(byte[]):void");
    }
}
