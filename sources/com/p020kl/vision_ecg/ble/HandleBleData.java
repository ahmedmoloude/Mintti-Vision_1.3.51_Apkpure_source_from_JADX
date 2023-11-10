package com.p020kl.vision_ecg.ble;

import android.util.Log;
import com.itextpdf.text.pdf.BidiOrder;
import kotlin.UByte;

/* renamed from: com.kl.vision_ecg.ble.HandleBleData */
public class HandleBleData {
    private static final int blePkgLength = 19;
    private static int deviceType = -1;
    /* access modifiers changed from: private */
    public static int ecgIndex;
    /* access modifiers changed from: private */
    public static final EncryptDecode encryptDecode = new EncryptDecode();
    public static HandleBleDataNew handleBleDataOne = new HandleBleDataNew(new HandleDataCallback() {
        public void onBleData(int i, byte[] bArr, byte[] bArr2) {
            int i2 = 0;
            if (i == 0) {
                short[] access$000 = HandleBleData.byte2ShortArray(bArr);
                int length = access$000.length;
                while (i2 < length) {
                    HandleBleData.iBleOperateCallback.bleData(26, access$000[i2]);
                    i2++;
                }
            } else if (i == 11) {
                HandleBleData.iBleOperateCallback.bleData(13, -1);
                short[] access$0002 = HandleBleData.byte2ShortArray(bArr);
                int length2 = access$0002.length;
                while (i2 < length2) {
                    short s = access$0002[i2];
                    HandleBleData.iBleOperateCallback.bleData(26, 2048);
                    i2++;
                }
            } else if (i == 13) {
                HandleBleData.iBleOperateCallback.bleData(12, (short) bArr[0]);
            } else if (i == 14) {
                float[] fArr = new float[(bArr.length / 2)];
                int i3 = 0;
                int i4 = 0;
                while (i3 < bArr.length) {
                    fArr[i4] = (float) (((((double) ((float) ((bArr[i3 + 1] << 8) + bArr[i3]))) * 0.06d) * 9.8d) / 1000.0d);
                    fArr[i4 + 1] = (float) (((((double) (-((float) ((bArr[i3 + 3] << 8) + bArr[i3 + 2])))) * 0.06d) * 9.8d) / 1000.0d);
                    fArr[i4 + 2] = (float) (((((double) ((float) ((bArr[i3 + 5] << 8) + bArr[i3 + 4]))) * 0.06d) * 9.8d) / 1000.0d);
                    i3 += 6;
                    i4 += 3;
                }
                for (int i5 = 0; i5 < bArr.length / 2; i5 += 3) {
                    float[] fArr2 = new float[3];
                    System.arraycopy(fArr, i5, fArr2, 0, 3);
                    HandleBleData.iBleOperateCallback.bleData(27, fArr2);
                }
            } else if (i == 15) {
                HandleBleData.handlePose(bArr[0]);
            }
        }
    });
    /* access modifiers changed from: private */
    public static IBleOperateCallback iBleOperateCallback = null;
    private static IUpdateCallBack iUpdateCallBack = null;
    private static byteBuf packageBuf;

    /* renamed from: com.kl.vision_ecg.ble.HandleBleData$byteBuf */
    public static class byteBuf {
        public int accCharLength;
        public int accSampleRate;
        public int blePkgNumInPkg;
        public int ecgDataKind;
        public int ecgLeadNumber;
        public int ecgSampleRate;
        public byte[] packageBuffer;
        public int packageCMD;
        public int packageIndex;
        public int packageLength;
        public byte[] pkgNoHeadCrcBuf;
        public int pointer;
        public byte[] tempBuf;
        public int[] unzipDataBuffer;

        private byteBuf() {
            this.blePkgNumInPkg = 0;
            this.packageCMD = 0;
            this.packageLength = 0;
            this.packageIndex = 0;
            this.pointer = 0;
            this.ecgDataKind = 0;
            this.ecgSampleRate = 500;
            this.ecgLeadNumber = 0;
            this.accSampleRate = 20;
            this.accCharLength = 2;
        }

        public static float toAccG(byte[] bArr) {
            int i = 0;
            for (int i2 = 0; i2 < bArr.length; i2++) {
                i += (bArr[i2] & UByte.MAX_VALUE) << (i2 * 8);
            }
            if (i >= 32768) {
                int i3 = i - 1;
                int i4 = 0;
                for (int i5 = 0; i5 < 16; i5++) {
                    int i6 = 1;
                    if ((((1 << i5) & i3) >> i5) == 1) {
                        i6 = 0;
                    }
                    i4 += i6 << i5;
                }
                i = i4 * -1;
            }
            return ((((float) i) * 2.0f) / 32768.0f) * 9.8f;
        }

        public void AnalyzeData(byte[] bArr, IBleOperateCallback iBleOperateCallback, IUpdateCallBack iUpdateCallBack) {
            short s;
            int i = this.packageCMD;
            int i2 = 4;
            int i3 = 0;
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        if ((bArr[0] & UByte.MAX_VALUE) == 130 && (bArr[1] & UByte.MAX_VALUE) == 7) {
                            iUpdateCallBack.updateVersionMsg(15, ((char) (bArr[2] & UByte.MAX_VALUE)) + "" + ((char) (bArr[3] & UByte.MAX_VALUE)) + "" + ((char) (bArr[4] & UByte.MAX_VALUE)) + "." + ((char) (bArr[5] & UByte.MAX_VALUE)) + "" + ((char) (bArr[6] & UByte.MAX_VALUE)) + "." + ((char) (bArr[7] & UByte.MAX_VALUE)) + "" + ((char) (bArr[8] & UByte.MAX_VALUE)) + "");
                            iUpdateCallBack.updateMsg(16, (short) (bArr[9] & UByte.MAX_VALUE));
                            return;
                        }
                        if ((bArr[0] & UByte.MAX_VALUE) == 131) {
                            if ((bArr[1] & UByte.MAX_VALUE) != 2) {
                                return;
                            }
                            if ((bArr[2] & UByte.MAX_VALUE) == 0 || (bArr[2] & UByte.MAX_VALUE) == 1) {
                                s = 13;
                            } else if ((bArr[2] & UByte.MAX_VALUE) == 2) {
                                Log.i("HandleBleData", "electrode is ok");
                                return;
                            } else {
                                return;
                            }
                        } else if ((bArr[0] & UByte.MAX_VALUE) == 133) {
                            if ((bArr[1] & UByte.MAX_VALUE) == 2) {
                                iBleOperateCallback.bleData(12, (short) (bArr[2] & UByte.MAX_VALUE));
                                return;
                            }
                            return;
                        } else if ((bArr[0] & UByte.MAX_VALUE) != 128 || (bArr[1] & UByte.MAX_VALUE) != 2) {
                            if ((bArr[0] & UByte.MAX_VALUE) == 135) {
                                if ((bArr[1] & UByte.MAX_VALUE) == 1) {
                                    s = 28;
                                } else if ((bArr[1] & UByte.MAX_VALUE) == 2) {
                                    short[] sArr = new short[6];
                                    while (i3 < 6) {
                                        sArr[i3] = (short) (bArr[i3 + 2] & UByte.MAX_VALUE);
                                        i3++;
                                    }
                                    iBleOperateCallback.bleData(29, sArr);
                                    return;
                                } else {
                                    return;
                                }
                            } else if ((bArr[0] & UByte.MAX_VALUE) == 136 && (bArr[1] & UByte.MAX_VALUE) == 1) {
                                s = 30;
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                        iBleOperateCallback.bleData(s, -1);
                    }
                } else if ((bArr[0] & UByte.MAX_VALUE) == 129) {
                    if ((bArr[1] & UByte.MAX_VALUE) == 1) {
                        iUpdateCallBack.updateMsg(25, 0);
                    } else if ((bArr[1] & UByte.MAX_VALUE) == 2) {
                        Log.e("heartbook", "You can not go to update firmware");
                    }
                } else if ((bArr[0] & UByte.MAX_VALUE) == 130) {
                    if ((bArr[1] & UByte.MAX_VALUE) == 1) {
                        iUpdateCallBack.updateMsg(25, 2);
                    } else if ((bArr[1] & UByte.MAX_VALUE) == 2) {
                        iUpdateCallBack.updateMsg(25, 1);
                    }
                } else if ((bArr[0] & UByte.MAX_VALUE) == 131) {
                    iUpdateCallBack.updateMsg(25, 3);
                } else if ((bArr[0] & UByte.MAX_VALUE) != 132) {
                } else {
                    if ((bArr[1] & UByte.MAX_VALUE) == 1) {
                        iUpdateCallBack.updateMsg(25, 4);
                    } else if ((bArr[1] & UByte.MAX_VALUE) == 2) {
                        iUpdateCallBack.updateMsg(25, 5);
                    }
                }
            } else if ((bArr[0] & UByte.MAX_VALUE) == 1) {
                this.ecgDataKind = bArr[1] & UByte.MAX_VALUE;
                this.ecgLeadNumber = bArr[3] & UByte.MAX_VALUE;
                byte[] bArr2 = new byte[(bArr.length - 4)];
                while (i2 < bArr.length) {
                    bArr2[i3] = bArr[i2];
                    i3++;
                    i2++;
                }
                HandleEcgData(bArr2, iBleOperateCallback);
            } else if ((bArr[0] & UByte.MAX_VALUE) != 2) {
            } else {
                if ((bArr[1] & UByte.MAX_VALUE) == 129) {
                    int length = bArr.length - 2;
                    byte[] bArr3 = new byte[length];
                    int i4 = 0;
                    for (int i5 = 2; i5 < bArr.length; i5++) {
                        bArr3[i4] = bArr[i5];
                        i4++;
                    }
                    if (length % 15 == 0) {
                        for (int i6 = 0; i6 < length; i6 += 15) {
                            byte b = bArr3[i6] & UByte.MAX_VALUE;
                            byte b2 = bArr3[i6 + 1];
                            byte b3 = bArr3[i6 + 2] & UByte.MAX_VALUE;
                            byte b4 = bArr3[i6 + 3];
                            byte b5 = bArr3[i6 + 4];
                            byte b6 = bArr3[i6 + 5];
                            byte b7 = bArr3[i6 + 6];
                            byte b8 = bArr3[i6 + 7];
                            byte b9 = bArr3[i6 + 8];
                            byte b10 = bArr3[i6 + 9];
                            byte b11 = bArr3[i6 + 10];
                            if (b == 0) {
                                if (b3 == 0) {
                                    iBleOperateCallback.bleData(14, 0);
                                } else if (b3 == 2) {
                                    iBleOperateCallback.bleData(14, 4);
                                } else if (b3 == 5) {
                                    iBleOperateCallback.bleData(14, 5);
                                }
                            } else if (b == 1) {
                                iBleOperateCallback.bleData(14, 1);
                            } else if (b == 2) {
                                iBleOperateCallback.bleData(14, 2);
                            } else if (b == 3) {
                                iBleOperateCallback.bleData(14, 3);
                            }
                        }
                    }
                } else if ((bArr[1] & UByte.MAX_VALUE) == 1) {
                    this.accSampleRate = bArr[2] & UByte.MAX_VALUE;
                    this.accCharLength = bArr[3] & UByte.MAX_VALUE;
                    byte[] bArr4 = new byte[(bArr.length - 4)];
                    while (i2 < bArr.length) {
                        bArr4[i3] = bArr[i2];
                        i3++;
                        i2++;
                    }
                    HandleAccData(bArr4, iBleOperateCallback);
                }
            }
        }

        public void HandleAccData(byte[] bArr, IBleOperateCallback iBleOperateCallback) {
            float[] fArr = new float[3];
            int i = 0;
            while (i < bArr.length) {
                if (i != bArr.length) {
                    fArr[0] = toAccG(new byte[]{bArr[i + 1], bArr[i]});
                    int i2 = i + 2;
                    fArr[1] = toAccG(new byte[]{bArr[i2 + 1], bArr[i2]});
                    int i3 = i2 + 2;
                    int i4 = i3 + 1;
                    fArr[2] = toAccG(new byte[]{bArr[i4], bArr[i3]});
                    iBleOperateCallback.bleData(27, fArr);
                    i = i4;
                }
                i++;
            }
        }

        public void HandleEcgData(byte[] bArr, IBleOperateCallback iBleOperateCallback) {
            int i = this.ecgDataKind;
            if (i == 1) {
                HandleBleData.getHeartRate(bArr, iBleOperateCallback);
                return;
            }
            int i2 = 0;
            if (i == 2) {
                this.unzipDataBuffer = new int[((bArr.length / 3) * 2)];
                int i3 = 0;
                int i4 = 0;
                while (i3 < bArr.length) {
                    int i5 = i3 + 1;
                    int i6 = ((bArr[i3] & UByte.MAX_VALUE) << 4) | ((bArr[i5] & UByte.MAX_VALUE) >> 4);
                    int[] iArr = this.unzipDataBuffer;
                    int i7 = i4 + 1;
                    iArr[i4] = i6;
                    iArr[i7] = (((bArr[i5] & UByte.MAX_VALUE) & BidiOrder.f518B) << 8) | (bArr[i3 + 2] & UByte.MAX_VALUE);
                    i3 += 3;
                    i4 = i7 + 1;
                }
                while (i2 < this.unzipDataBuffer.length) {
                    if (i2 % 2 == 0) {
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    iBleOperateCallback.bleData(26, (short) this.unzipDataBuffer[i2]);
                    i2++;
                }
            } else if (i == 3) {
                byte b = bArr[0] & UByte.MAX_VALUE;
                if (!(HandleBleData.ecgIndex + 1 == b || (HandleBleData.ecgIndex == 127 && b == 1))) {
                    int access$600 = (b - HandleBleData.ecgIndex) - 1;
                    if (HandleBleData.ecgIndex >= b) {
                        access$600 = ((127 - HandleBleData.ecgIndex) + b) - 1;
                    }
                    int i8 = access$600 * 72;
                    for (int i9 = 0; i9 < i8; i9++) {
                        iBleOperateCallback.bleData(26, 0);
                    }
                }
                int length = bArr.length - 1;
                byte[] bArr2 = new byte[length];
                int i10 = 0;
                for (int i11 = 1; i11 < bArr.length; i11++) {
                    bArr2[i10] = bArr[i11];
                    i10++;
                }
                this.unzipDataBuffer = new int[((length / 3) * 2)];
                int i12 = 0;
                int i13 = 0;
                while (i12 < length) {
                    int i14 = i12 + 1;
                    int i15 = ((bArr2[i12] & UByte.MAX_VALUE) << 4) | ((bArr2[i14] & UByte.MAX_VALUE) >> 4);
                    int[] iArr2 = this.unzipDataBuffer;
                    int i16 = i13 + 1;
                    iArr2[i13] = i15;
                    iArr2[i16] = (((bArr2[i14] & UByte.MAX_VALUE) & BidiOrder.f518B) << 8) | (bArr2[i12 + 2] & UByte.MAX_VALUE);
                    i12 += 3;
                    i13 = i16 + 1;
                }
                while (true) {
                    int[] iArr3 = this.unzipDataBuffer;
                    if (i2 < iArr3.length) {
                        iBleOperateCallback.bleData(26, (short) iArr3[i2]);
                        i2++;
                    } else {
                        int unused = HandleBleData.ecgIndex = b;
                        return;
                    }
                }
            }
        }

        public void addData(byte[] bArr) {
            int length = bArr.length;
            int i = this.pointer;
            if (i == this.blePkgNumInPkg - 1) {
                length = (this.packageLength - (i * 19)) + 1;
            }
            int i2 = i * 19;
            int i3 = 0;
            for (int i4 = 1; i4 < length; i4++) {
                this.packageBuffer[i2 + i3] = bArr[i4];
                i3++;
            }
            this.pointer++;
        }

        public void getValidData() {
            int i = 5;
            this.pkgNoHeadCrcBuf = new byte[((this.tempBuf.length - 5) - 2)];
            int i2 = 0;
            while (true) {
                byte[] bArr = this.tempBuf;
                if (i < bArr.length - 2) {
                    this.pkgNoHeadCrcBuf[i2] = bArr[i];
                    i2++;
                    i++;
                } else {
                    return;
                }
            }
        }

        public void init(int i, int i2, int i3, int i4) {
            this.packageIndex = i;
            this.packageLength = i2;
            this.packageCMD = i3;
            this.blePkgNumInPkg = i4;
            this.packageBuffer = new byte[i2];
            int i5 = 0;
            while (true) {
                byte[] bArr = this.packageBuffer;
                if (i5 < bArr.length) {
                    bArr[i5] = 0;
                    i5++;
                } else {
                    this.pointer = 0;
                    return;
                }
            }
        }

        public void removeZero() {
            int length = this.packageBuffer.length;
            int i = 0;
            for (int i2 = 0; i2 < length - 1; i2++) {
                byte[] bArr = this.packageBuffer;
                if ((bArr[i2] & UByte.MAX_VALUE) == 250 && (bArr[i2 + 1] & UByte.MAX_VALUE) == 0) {
                    i++;
                }
            }
            this.tempBuf = new byte[(length - i)];
            int i3 = 1;
            for (int i4 = 1; i4 < length; i4++) {
                byte[] bArr2 = this.packageBuffer;
                if ((bArr2[i4 - 1] & UByte.MAX_VALUE) != 250 || (bArr2[i4] & UByte.MAX_VALUE) != 0) {
                    this.tempBuf[i3] = bArr2[i4];
                    i3++;
                }
            }
            this.tempBuf[0] = this.packageBuffer[0];
        }

        public boolean verifyCRC() {
            getValidData();
            EncryptDecode access$400 = HandleBleData.encryptDecode;
            byte[] bArr = this.pkgNoHeadCrcBuf;
            byte[] crc16_get = access$400.crc16_get(bArr, bArr.length);
            byte[] bArr2 = this.tempBuf;
            return bArr2[bArr2.length - 1] == crc16_get[0] && bArr2[bArr2.length + -2] == crc16_get[1];
        }
    }

    public static void HandleData(byte[] bArr, IBleOperateCallback iBleOperateCallback2, IUpdateCallBack iUpdateCallBack2) {
        byte[] Decrypts;
        if (bArr != null) {
            boolean z = false;
            if (deviceType == -1) {
                if ((bArr[0] & UByte.MAX_VALUE) == 250 && (bArr[1] & UByte.MAX_VALUE) == 250) {
                    deviceType = 1;
                } else {
                    byte[] Decrypts2 = encryptDecode.Decrypts((byte[]) bArr.clone());
                    if ((Decrypts2[0] & UByte.MAX_VALUE) == 0 && (Decrypts2[1] & UByte.MAX_VALUE) == 250 && (Decrypts2[2] & UByte.MAX_VALUE) == 250) {
                        deviceType = 0;
                    }
                }
            }
            int i = deviceType;
            if (i == 1) {
                boolean z2 = iBleOperateCallback == null;
                if (iUpdateCallBack == null) {
                    z = true;
                }
                if (z2 || z) {
                    iBleOperateCallback = iBleOperateCallback2;
                    iUpdateCallBack = iUpdateCallBack2;
                }
                handleBleDataOne.HandleData((byte[]) bArr.clone());
            } else if (i == 0 && (Decrypts = encryptDecode.Decrypts(bArr)) != null) {
                HandleData_version2((byte[]) Decrypts.clone(), iBleOperateCallback2, iUpdateCallBack2);
            }
        }
    }

    public static void HandleData_version2(byte[] bArr, IBleOperateCallback iBleOperateCallback2, IUpdateCallBack iUpdateCallBack2) {
        if (bArr != null) {
            if ((bArr[0] & UByte.MAX_VALUE) == 0 && (bArr[1] & UByte.MAX_VALUE) == 250 && (bArr[2] & UByte.MAX_VALUE) == 250) {
                byte b = bArr[3] & UByte.MAX_VALUE;
                byte b2 = bArr[4] & UByte.MAX_VALUE;
                byte b3 = bArr[5] & UByte.MAX_VALUE;
                int i = b2 % 19 == 0 ? b2 / 19 : 1 + (b2 / 19);
                byteBuf bytebuf = new byteBuf();
                packageBuf = bytebuf;
                bytebuf.init(b, b2, b3, i);
            }
            byteBuf bytebuf2 = packageBuf;
            if (bytebuf2 != null) {
                bytebuf2.addData(bArr);
                byteBuf bytebuf3 = packageBuf;
                if (bytebuf3.pointer == bytebuf3.blePkgNumInPkg) {
                    bytebuf3.removeZero();
                    if (packageBuf.verifyCRC()) {
                        byteBuf bytebuf4 = packageBuf;
                        bytebuf4.AnalyzeData(bytebuf4.pkgNoHeadCrcBuf, iBleOperateCallback2, iUpdateCallBack2);
                    }
                    packageBuf = null;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static short[] byte2ShortArray(byte[] bArr) {
        int length = bArr.length / 2;
        short[] sArr = new short[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            sArr[i] = (short) (((bArr[i2 + 1] << 8) & 65280) | (bArr[i2] & UByte.MAX_VALUE));
        }
        return sArr;
    }

    /* access modifiers changed from: private */
    public static void getHeartRate(byte[] bArr, IBleOperateCallback iBleOperateCallback2) {
        for (int i = 0; i < bArr.length; i = i + 1 + 1) {
            if (i != bArr.length) {
                iBleOperateCallback2.bleData(26, (short) (((bArr[i] & UByte.MAX_VALUE) * 256) + (bArr[i + 1] & UByte.MAX_VALUE)));
            }
        }
    }

    /* access modifiers changed from: private */
    public static void handlePose(int i) {
        int i2;
        switch (i) {
            case 1:
                i2 = 0;
                break;
            case 3:
            case 4:
                i2 = 1;
                break;
            case 5:
            case 6:
                i2 = 2;
                break;
            case 7:
                i2 = 4;
                break;
            case 8:
                i2 = 3;
                break;
            default:
                i2 = -1;
                break;
        }
        IBleOperateCallback iBleOperateCallback2 = iBleOperateCallback;
        if (iBleOperateCallback2 != null) {
            iBleOperateCallback2.bleData(14, (short) i2);
        }
    }

    public static void reset() {
        packageBuf = null;
        deviceType = -1;
    }
}
