package com.p020kl.vision_ecg.ble;

import android.util.Log;
import kotlin.UByte;

/* renamed from: com.kl.vision_ecg.ble.HandleBleDataNew */
public class HandleBleDataNew {
    public static final int BLE_STATUS_ACC = 14;
    public static final int BLE_STATUS_CRC_FAILED = 12;
    public static final int BLE_STATUS_LAST_PACKAGE = 10;
    public static final int BLE_STATUS_LEAD_OFF = 11;
    public static final int BLE_STATUS_NORMAL = 0;
    public static final int BLE_STATUS_POSE = 15;
    public static final int BLE_STATUS_POWER = 13;
    public static final int BLE_STATUS_STEPS = 16;
    /* access modifiers changed from: private */
    public static int blePkgLength = 20;
    private static byteBuf packageBuf = null;
    private static final int pkgInfoLength = 10;
    /* access modifiers changed from: private */
    public EncryptDecode encryptDecode = new EncryptDecode();
    private HandleDataCallback handleDataCallback;

    /* renamed from: com.kl.vision_ecg.ble.HandleBleDataNew$byteBuf */
    public class byteBuf {
        public int blePkgNumInPkg;
        public byte[] packetBuffer;
        public byte[] packetCrc;
        public byte[] packetDesc;
        public int packetLength;
        public int packetSerial;
        public byte[] pkgNoHeadCrcBuf;
        public byte[] pkgTransformedBuf;
        public int pointer;
        public byte[] tempBuf;

        private byteBuf() {
            this.blePkgNumInPkg = 0;
            this.packetLength = 0;
            this.packetSerial = 0;
            this.pointer = 0;
        }

        public void addData(byte[] bArr) {
            int length = bArr.length;
            int i = this.pointer;
            if (i == this.blePkgNumInPkg - 1) {
                length = this.packetLength - (i * HandleBleDataNew.blePkgLength);
            }
            int access$100 = this.pointer * HandleBleDataNew.blePkgLength;
            for (int i2 = 0; i2 < length; i2++) {
                this.packetBuffer[access$100 + i2] = bArr[i2];
            }
            this.pointer++;
        }

        public void getValidData() {
            byte[] bArr = this.tempBuf;
            if (bArr.length - 10 > 0) {
                this.pkgNoHeadCrcBuf = new byte[(bArr.length - 10)];
                int i = 0;
                while (true) {
                    byte[] bArr2 = this.pkgNoHeadCrcBuf;
                    if (i < bArr2.length) {
                        bArr2[i] = this.tempBuf[i + 10];
                        i++;
                    } else {
                        return;
                    }
                }
            } else {
                this.pkgNoHeadCrcBuf = null;
            }
        }

        public void init(int i, int i2, byte[] bArr, byte[] bArr2, int i3) {
            this.packetSerial = i;
            this.packetLength = i2;
            this.packetDesc = bArr;
            this.blePkgNumInPkg = i3;
            this.packetCrc = bArr2;
            this.packetBuffer = new byte[i2];
            int i4 = 0;
            while (true) {
                byte[] bArr3 = this.packetBuffer;
                if (i4 < bArr3.length) {
                    bArr3[i4] = 0;
                    i4++;
                } else {
                    this.pointer = 0;
                    return;
                }
            }
        }

        public void removeZero() {
            int length = this.packetBuffer.length - 10;
            int i = 0;
            for (int i2 = 0; i2 < length - 2; i2++) {
                byte[] bArr = this.packetBuffer;
                if ((bArr[i2 + 10] & UByte.MAX_VALUE) == 250 && (bArr[i2 + 1 + 10] & UByte.MAX_VALUE) == 0 && (bArr[i2 + 2 + 10] & UByte.MAX_VALUE) == 250) {
                    i++;
                }
            }
            this.tempBuf = new byte[((length - i) + 10)];
            int i3 = 11;
            int i4 = 1;
            while (i4 < length - 1) {
                byte[] bArr2 = this.packetBuffer;
                if ((bArr2[(i4 - 1) + 10] & UByte.MAX_VALUE) != 250 || (bArr2[i4 + 10] & UByte.MAX_VALUE) != 0 || (bArr2[i4 + 1 + 10] & UByte.MAX_VALUE) != 250) {
                    this.tempBuf[i3] = bArr2[i4 + 10];
                    i3++;
                }
                i4++;
            }
            byte[] bArr3 = this.tempBuf;
            byte[] bArr4 = this.packetBuffer;
            bArr3[10] = bArr4[10];
            if (length > 1) {
                bArr3[i3] = bArr4[i4 + 10];
            }
            for (int i5 = 0; i5 < 10; i5++) {
                this.tempBuf[i5] = this.packetBuffer[i5];
            }
        }

        public boolean verifyCRC() {
            getValidData();
            if (this.pkgNoHeadCrcBuf == null) {
                return true;
            }
            EncryptDecode access$200 = HandleBleDataNew.this.encryptDecode;
            byte[] bArr = this.pkgNoHeadCrcBuf;
            byte[] crc16_get = access$200.crc16_get(bArr, bArr.length);
            byte[] bArr2 = this.packetCrc;
            return bArr2[0] == crc16_get[0] && bArr2[1] == crc16_get[1];
        }
    }

    public HandleBleDataNew(HandleDataCallback handleDataCallback2) {
        this.handleDataCallback = handleDataCallback2;
    }

    public void HandleData(byte[] bArr) {
        HandleDataCallback handleDataCallback2;
        byte[] bArr2 = bArr;
        int length = bArr2.length;
        blePkgLength = length;
        int i = 0;
        if ((bArr2[0] & UByte.MAX_VALUE) == 250 && (bArr2[1] & UByte.MAX_VALUE) == 250) {
            byte b = bArr2[2] & UByte.MAX_VALUE;
            byte b2 = (bArr2[4] << 8) | (bArr2[3] & UByte.MAX_VALUE);
            byte[] bArr3 = {bArr2[5], bArr2[6], bArr2[7]};
            byte[] bArr4 = {bArr2[8], bArr2[9]};
            int i2 = b2 + 10;
            int i3 = i2 % length;
            int i4 = i2 / length;
            if (i3 != 0) {
                i4++;
            }
            int i5 = i4;
            byteBuf bytebuf = new byteBuf();
            packageBuf = bytebuf;
            bytebuf.init(b, i2, bArr3, bArr4, i5);
        }
        byteBuf bytebuf2 = packageBuf;
        if (bytebuf2 != null) {
            bytebuf2.addData(bArr2);
            byteBuf bytebuf3 = packageBuf;
            if (bytebuf3.pointer == bytebuf3.blePkgNumInPkg) {
                bytebuf3.removeZero();
                if (packageBuf.verifyCRC()) {
                    byteBuf bytebuf4 = packageBuf;
                    byte[] bArr5 = bytebuf4.packetDesc;
                    if (bArr5[0] == 2) {
                        i = 11;
                    } else if (bArr5[0] == 4) {
                        i = 13;
                    } else if (bArr5[0] == 1) {
                        i = 14;
                    } else if (bArr5[0] == 3) {
                        i = 15;
                    } else if (bArr5[0] == 5) {
                        i = 16;
                    }
                    byte[] bArr6 = bytebuf4.pkgNoHeadCrcBuf;
                    if (!(bArr6 == null || (handleDataCallback2 = this.handleDataCallback) == null)) {
                        handleDataCallback2.onBleData(i, bArr6, (byte[]) null);
                    }
                    packageBuf = null;
                    return;
                }
                packageBuf = null;
                Log.i("XXG", "---------verify CRC failed------------");
                this.handleDataCallback.onBleData(12, (byte[]) null, (byte[]) null);
            }
        }
    }
}
