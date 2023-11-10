package com.linktop.whealthService.task;

import android.content.Context;
import android.preference.PreferenceManager;
import com.itextpdf.text.pdf.BidiOrder;
import com.linktop.constant.Constants;
import com.linktop.constant.IUserProfile;
import com.linktop.constant.Pair;
import com.linktop.infs.OnEcgResultListener;
import com.linktop.infs.OnSendCodeToDevCallback;
import com.linktop.utils.BleDevLog;
import com.linktop.whealthService.OnBLEService;
import com.linktop.whealthService.util.IBleDev;
import com.neurosky.AlgoSdk.NskAlgoProfile;
import com.neurosky.AlgoSdk.NskAlgoSdk;
import java.util.Arrays;
import java.util.Date;

public final class EcgTask extends HcModuleTask {
    private static final String NSK_ALGO_SDK_LICENCE = "NeuroSky_Release_To_GeneralFreeLicense_Use_Only_Dec  1 2016";
    private static final int OUTPUT_INTERVAL = 30;
    private static final String TAG = "EcgTask";
    private static final int pkgLen = 128;
    private int activeProfile;
    private OnSendCodeToDevCallback callback;
    private int dataEcg = 0;
    private int ecgDataIndex;
    private int ecgStageFlag = 0;
    public int ecgStep = 0;
    private boolean isEcgTest = false;
    private boolean isFingerTouchOnSensor;
    private boolean isModuleExist;
    private final Context mContext;
    /* access modifiers changed from: private */
    public OnEcgResultListener mEcgListener;
    private final NskAlgoSdk mNskAlgoSdk;
    private boolean outputArrayData;
    private int[] outputPkg;
    private boolean outputRawData = false;
    private int pkgIndex = 0;

    public EcgTask(Context context, IBleDev iBleDev) {
        super(iBleDev);
        this.mContext = context;
        NskAlgoSdk nskAlgoSdk = new NskAlgoSdk();
        this.mNskAlgoSdk = nskAlgoSdk;
        nskAlgoSdk.setOnStateChangeListener($$Lambda$EcgTask$0XY65P8qWoxJs5laINhzjblAyPM.INSTANCE);
    }

    private boolean createNewProfile(IUserProfile iUserProfile) {
        NskAlgoProfile nskAlgoProfile = new NskAlgoProfile();
        nskAlgoProfile.name = iUserProfile.getUsername();
        nskAlgoProfile.dob = new Date(iUserProfile.getBirthday());
        nskAlgoProfile.height = iUserProfile.getHeight();
        nskAlgoProfile.weight = iUserProfile.getWeight();
        nskAlgoProfile.gender = iUserProfile.getGender() == 0;
        return NskAlgoSdk.NskAlgoProfileUpdate(nskAlgoProfile);
    }

    private void setupSDK(int i) {
        if (!this.mNskAlgoSdk.setBaudRate(6, 3)) {
            BleDevLog.m141b(TAG, "Failed to set the sampling rate: 3");
            return;
        }
        this.mNskAlgoSdk.setOnSignalQualityListener(new NskAlgoSdk.OnSignalQualityListener() {
            public void onOverallSignalQuality(int i) {
            }

            public void onSignalQuality(int i) {
                if (EcgTask.this.mEcgListener != null) {
                    EcgTask.this.mEcgListener.onSignalQuality(i);
                }
            }
        });
        String str = "SDK ver: " + NskAlgoSdk.NskAlgoSdkVersion();
        if ((i & 8388608) != 0) {
            NskAlgoSdk.NskAlgoSetECGConfigAfib(3.5f);
            str = str + "\nAfib ver: " + NskAlgoSdk.NskAlgoAlgoVersion(8388608);
        }
        if ((i & 524288) != 0) {
            NskAlgoSdk.NskAlgoSetECGConfigHeartage(30);
            str = str + "\nHeartage ver: " + NskAlgoSdk.NskAlgoAlgoVersion(524288);
        }
        if ((i & 65536) != 0) {
            str = str + "\nHeartrate ver: " + NskAlgoSdk.NskAlgoAlgoVersion(65536);
        }
        if ((i & 1048576) != 0) {
            NskAlgoSdk.NskAlgoSetECGConfigHRV(30);
            str = str + "\nHRV ver: " + NskAlgoSdk.NskAlgoAlgoVersion(1048576);
        }
        int i2 = i & 33554432;
        if (i2 != 0) {
            NskAlgoSdk.NskAlgoSetECGConfigHRVFD(30, 30);
            str = str + "\nHRVFD ver: " + NskAlgoSdk.NskAlgoAlgoVersion(33554432);
            this.mNskAlgoSdk.setOnECGHRVFDAlgoIndexListener(new NskAlgoSdk.OnECGHRVFDAlgoIndexListener() {
                public void onECGHRVFDAlgoIndexListener(float f, float f2, float f3, float f4) {
                    BleDevLog.m141b(EcgTask.TAG, "FD hf:" + f + ", lf:" + f2 + ", lfhfRatio:" + f3 + ", hflfRatio:" + f4);
                }
            });
        }
        if (i2 != 0) {
            NskAlgoSdk.NskAlgoSetECGConfigHRVTD(30, 30);
            str = str + "\nHRVTD ver: " + NskAlgoSdk.NskAlgoAlgoVersion(33554432);
            this.mNskAlgoSdk.setOnECGHRVTDAlgoIndexListener(new NskAlgoSdk.OnECGHRVTDAlgoIndexListener() {
                public void onECGHRVTDAlgoIndexListener(float f, float f2, float f3, float f4, float f5) {
                    BleDevLog.m141b(EcgTask.TAG, "TD nn50:" + f + ", sdnn:" + f2 + ", pnn50:" + f3 + ", rrTranIndex:" + f4 + ", rmssd:" + f5);
                }
            });
        }
        if ((i & 262144) != 0) {
            str = str + "\nMood ver: " + NskAlgoSdk.NskAlgoAlgoVersion(262144);
        }
        if ((i & 4194304) != 0) {
            str = str + "\nResp ver: " + NskAlgoSdk.NskAlgoAlgoVersion(4194304);
        }
        if ((i & 2097152) != 0) {
            str = str + "\nSmooth ver: " + NskAlgoSdk.NskAlgoAlgoVersion(2097152);
        }
        if ((i & 131072) != 0) {
            NskAlgoSdk.NskAlgoSetECGConfigStress(30, 30);
            str = str + "\nStress ver: " + NskAlgoSdk.NskAlgoAlgoVersion(131072);
        }
        BleDevLog.m140a(TAG, str);
        this.mNskAlgoSdk.setOnECGAlgoIndexListener(new NskAlgoSdk.OnECGAlgoIndexListener() {
            public final void onECGAlgoIndex(int i, int i2) {
                EcgTask.this.lambda$setupSDK$1$EcgTask(i, i2);
            }
        });
    }

    private void setupUserProfile() {
        IUserProfile userProfile = getUserProfile();
        if (userProfile != null) {
            this.activeProfile = Integer.MIN_VALUE;
            NskAlgoProfile[] NskAlgoProfiles = NskAlgoSdk.NskAlgoProfiles();
            if (NskAlgoProfiles == null || NskAlgoProfiles.length == 0) {
                BleDevLog.m140a(TAG, createNewProfile(userProfile) ? "success to create new profile" : "fail to create new profile");
                NskAlgoProfiles = NskAlgoSdk.NskAlgoProfiles();
            }
            int length = NskAlgoProfiles.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                NskAlgoProfile nskAlgoProfile = NskAlgoProfiles[i];
                if (nskAlgoProfile.name.equals(userProfile.getUsername())) {
                    this.activeProfile = nskAlgoProfile.userId;
                    break;
                }
                i++;
            }
            BleDevLog.m140a(TAG, "setup activeProfile:" + this.activeProfile);
            NskAlgoSdk.NskAlgoProfileActive(this.activeProfile);
            String string = PreferenceManager.getDefaultSharedPreferences(this.mContext).getString("ecg_baseline", (String) null);
            if (string != null) {
                String[] split = string.substring(1, string.length() - 1).split(",");
                byte[] bArr = new byte[split.length];
                for (int i2 = 0; i2 < split.length; i2++) {
                    bArr[i2] = Byte.parseByte(split[i2]);
                }
                NskAlgoSdk.NskAlgoProfileSetBaseline(this.activeProfile, 65536, bArr);
            }
        }
    }

    public void checkModuleExist(OnSendCodeToDevCallback onSendCodeToDevCallback) {
        this.callback = onSendCodeToDevCallback;
        this.mCommunicate.mo27588a((byte) 5, new byte[]{0});
    }

    public void dealData(byte[] bArr) {
        IBleDev iBleDev;
        boolean z = false;
        byte b = bArr[0];
        if (b == 0) {
            if (bArr[1] == 1) {
                z = true;
            }
            setModuleExist(z);
            iBleDev = this.mIBleDev;
            if (!(iBleDev instanceof OnBLEService)) {
                OnSendCodeToDevCallback onSendCodeToDevCallback = this.callback;
                if (onSendCodeToDevCallback != null) {
                    onSendCodeToDevCallback.onReceived();
                    return;
                }
                return;
            }
        } else if (b == 4) {
            iBleDev = this.mIBleDev;
            if (!(iBleDev instanceof OnBLEService)) {
                return;
            }
        } else {
            return;
        }
        ((OnBLEService) iBleDev).mo27452d();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        if (r3 == 18) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0023, code lost:
        if (r3 == 2) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0015, code lost:
        if (r3 == 170) goto L_0x0066;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dealEcgVal(byte[] r10) {
        /*
            r9 = this;
            int r0 = r10.length
            r1 = 0
            r2 = 0
        L_0x0003:
            if (r2 >= r0) goto L_0x00f9
            byte r3 = r10[r2]
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r4 = r9.ecgStageFlag
            r5 = 170(0xaa, float:2.38E-43)
            if (r4 != 0) goto L_0x0012
            if (r3 != r5) goto L_0x00f5
            goto L_0x0066
        L_0x0012:
            r6 = 1
            if (r4 != r6) goto L_0x0018
            if (r3 != r5) goto L_0x00f3
            goto L_0x0066
        L_0x0018:
            r5 = 2
            if (r4 != r5) goto L_0x0020
            r5 = 18
            if (r3 != r5) goto L_0x00f3
            goto L_0x0066
        L_0x0020:
            r7 = 3
            if (r4 != r7) goto L_0x0026
            if (r3 != r5) goto L_0x00f3
            goto L_0x0066
        L_0x0026:
            r5 = 4
            r7 = 200(0xc8, float:2.8E-43)
            if (r4 != r5) goto L_0x005f
            if (r3 != 0) goto L_0x003e
            boolean r3 = r9.isFingerTouchOnSensor
            if (r3 == 0) goto L_0x003b
            boolean r3 = r9.outputRawData
            if (r3 != 0) goto L_0x003b
            com.neurosky.AlgoSdk.NskAlgoSdk.NskAlgoPause()
            com.neurosky.AlgoSdk.NskAlgoSdk.NskAlgoStop()
        L_0x003b:
            r9.isFingerTouchOnSensor = r1
            goto L_0x004f
        L_0x003e:
            if (r3 != r7) goto L_0x004f
            r9.pkgIndex = r1
            boolean r3 = r9.isFingerTouchOnSensor
            if (r3 != 0) goto L_0x004d
            boolean r3 = r9.outputRawData
            if (r3 != 0) goto L_0x004d
            com.neurosky.AlgoSdk.NskAlgoSdk.NskAlgoStart(r1)
        L_0x004d:
            r9.isFingerTouchOnSensor = r6
        L_0x004f:
            com.linktop.infs.OnEcgResultListener r3 = r9.mEcgListener
            if (r3 == 0) goto L_0x0058
            boolean r4 = r9.isFingerTouchOnSensor
            r3.onFingerDetection(r4)
        L_0x0058:
            int r3 = r9.ecgStageFlag
            int r3 = r3 + r6
        L_0x005b:
            r9.ecgStageFlag = r3
            goto L_0x00f5
        L_0x005f:
            r5 = 5
            if (r4 < r5) goto L_0x006c
            r5 = 21
            if (r4 > r5) goto L_0x006c
        L_0x0066:
            int r4 = r4 + 1
            r9.ecgStageFlag = r4
            goto L_0x00f5
        L_0x006c:
            r5 = 22
            if (r4 < r5) goto L_0x00f3
            r5 = 1045(0x415, float:1.464E-42)
            if (r4 > r5) goto L_0x00f3
            int r4 = r4 % 2
            if (r4 != 0) goto L_0x007d
            int r3 = r3 << 8
            r9.dataEcg = r3
            goto L_0x00e8
        L_0x007d:
            int r4 = r9.dataEcg
            int r4 = r4 + r3
            r9.dataEcg = r4
            r3 = 32768(0x8000, float:4.5918E-41)
            if (r4 < r3) goto L_0x008c
            r3 = 65536(0x10000, float:9.18355E-41)
            int r4 = r4 - r3
            r9.dataEcg = r4
        L_0x008c:
            boolean r3 = r9.outputRawData
            if (r3 == 0) goto L_0x00c8
            boolean r3 = r9.isFingerTouchOnSensor
            if (r3 == 0) goto L_0x00e3
            boolean r3 = r9.outputArrayData
            if (r3 == 0) goto L_0x00ba
            int r3 = r9.pkgIndex
            r4 = 128(0x80, float:1.794E-43)
            int r3 = r3 % r4
            if (r3 != 0) goto L_0x00a5
            r9.pkgIndex = r1
            int[] r3 = new int[r4]
            r9.outputPkg = r3
        L_0x00a5:
            int[] r3 = r9.outputPkg
            int r7 = r9.pkgIndex
            int r8 = r9.dataEcg
            r3[r7] = r8
            int r7 = r7 + r6
            r9.pkgIndex = r7
            com.linktop.infs.OnEcgResultListener r8 = r9.mEcgListener
            if (r8 == 0) goto L_0x00e3
            if (r7 != r4) goto L_0x00e3
            r8.onDrawWave(r3)
            goto L_0x00e3
        L_0x00ba:
            com.linktop.infs.OnEcgResultListener r3 = r9.mEcgListener
            if (r3 == 0) goto L_0x00e3
            int r4 = r9.dataEcg
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3.onDrawWave(r4)
            goto L_0x00e3
        L_0x00c8:
            int r3 = r9.ecgDataIndex
            if (r3 == 0) goto L_0x00d0
            int r3 = r3 % 256
            if (r3 != 0) goto L_0x00d8
        L_0x00d0:
            short[] r3 = new short[r6]
            r3[r1] = r7
            r4 = 7
            com.neurosky.AlgoSdk.NskAlgoSdk.NskAlgoDataStream(r4, r3, r6)
        L_0x00d8:
            short[] r3 = new short[r6]
            int r4 = r9.dataEcg
            short r4 = (short) r4
            r3[r1] = r4
            r4 = 6
            com.neurosky.AlgoSdk.NskAlgoSdk.NskAlgoDataStream(r4, r3, r6)
        L_0x00e3:
            int r3 = r9.ecgDataIndex
            int r3 = r3 + r6
            r9.ecgDataIndex = r3
        L_0x00e8:
            int r3 = r9.ecgStageFlag
            if (r3 != r5) goto L_0x00ef
            r3 = 0
            goto L_0x005b
        L_0x00ef:
            int r3 = r3 + 1
            goto L_0x005b
        L_0x00f3:
            r9.ecgStageFlag = r1
        L_0x00f5:
            int r2 = r2 + 1
            goto L_0x0003
        L_0x00f9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.linktop.whealthService.task.EcgTask.dealEcgVal(byte[]):void");
    }

    public void init(int... iArr) {
        if (iArr == null || iArr.length == 0) {
            iArr = new int[]{524288, 65536, 1048576, 262144, 4194304, 2097152, 131072};
        }
        int i = 0;
        for (int i2 : iArr) {
            i |= i2;
        }
        int NskAlgoInit = NskAlgoSdk.NskAlgoInit(i, "", NSK_ALGO_SDK_LICENCE);
        if (NskAlgoInit == 0) {
            BleDevLog.m141b(TAG, "ECG algo has been initialized successfully.");
            setupSDK(i);
            return;
        }
        BleDevLog.m141b(TAG, "Failed to initialize ECG algo, code = " + NskAlgoInit);
    }

    public boolean isModuleExist() {
        return this.isModuleExist;
    }

    public /* synthetic */ void lambda$setupSDK$1$EcgTask(int i, int i2) {
        OnEcgResultListener onEcgResultListener;
        int i3 = 65536;
        switch (i) {
            case 1:
                onEcgResultListener = this.mEcgListener;
                if (onEcgResultListener == null) {
                    return;
                }
                break;
            case 2:
                onEcgResultListener = this.mEcgListener;
                if (onEcgResultListener != null && i2 > 0) {
                    i3 = Constants.ECG_KEY_ROBUST_HR;
                    break;
                } else {
                    return;
                }
                break;
            case 3:
                onEcgResultListener = this.mEcgListener;
                if (onEcgResultListener != null) {
                    i3 = 262144;
                    break;
                } else {
                    return;
                }
            case 4:
                onEcgResultListener = this.mEcgListener;
                if (onEcgResultListener != null) {
                    i3 = Constants.ECG_KEY_R2R;
                    break;
                } else {
                    return;
                }
            case 5:
                onEcgResultListener = this.mEcgListener;
                if (onEcgResultListener != null) {
                    i3 = 1048576;
                    break;
                } else {
                    return;
                }
            case 6:
                onEcgResultListener = this.mEcgListener;
                if (onEcgResultListener != null) {
                    i3 = 524288;
                    break;
                } else {
                    return;
                }
            case 9:
                if (this.outputArrayData) {
                    if (this.pkgIndex % 128 == 0) {
                        this.pkgIndex = 0;
                        this.outputPkg = new int[128];
                    }
                    int[] iArr = this.outputPkg;
                    int i4 = this.pkgIndex;
                    iArr[i4] = i2;
                    int i5 = i4 + 1;
                    this.pkgIndex = i5;
                    OnEcgResultListener onEcgResultListener2 = this.mEcgListener;
                    if (onEcgResultListener2 != null && i5 == 128) {
                        onEcgResultListener2.onDrawWave(iArr);
                        return;
                    }
                    return;
                }
                OnEcgResultListener onEcgResultListener3 = this.mEcgListener;
                if (onEcgResultListener3 != null) {
                    onEcgResultListener3.onDrawWave(Integer.valueOf(i2));
                    return;
                }
                return;
            case 10:
                onEcgResultListener = this.mEcgListener;
                if (onEcgResultListener != null) {
                    i3 = 131072;
                    break;
                } else {
                    return;
                }
            case 11:
                onEcgResultListener = this.mEcgListener;
                if (onEcgResultListener != null) {
                    i3 = Constants.ECG_KEY_HEART_BEAT;
                    break;
                } else {
                    return;
                }
            case 12:
                onEcgResultListener = this.mEcgListener;
                if (onEcgResultListener != null && i2 > 0) {
                    i3 = 4194304;
                    break;
                } else {
                    return;
                }
            case 13:
                String arrays = Arrays.toString(NskAlgoSdk.NskAlgoProfileGetBaseline(this.activeProfile, 65536));
                BleDevLog.m141b(TAG, "Update baseline:" + arrays);
                PreferenceManager.getDefaultSharedPreferences(this.mContext).edit().putString("ecgbaseline", arrays).apply();
                return;
            default:
                return;
        }
        onEcgResultListener.onECGValues(i3, i2);
    }

    public boolean queryEcgTestEnable() {
        return this.isEcgTest;
    }

    public void setModuleExist(boolean z) {
        this.isModuleExist = z;
        BleDevLog.m141b(TAG, "setModuleExist ? " + z);
    }

    public void setOnEcgResultListener(OnEcgResultListener onEcgResultListener) {
        this.mEcgListener = onEcgResultListener;
    }

    public void setParam() {
        this.mCommunicate.mo27588a((byte) 5, new byte[]{4, -86, -86, 4, BidiOrder.f518B, 0, -5, 7, 55});
    }

    public void start(Pair... pairArr) {
        super.start(pairArr);
        this.outputArrayData = false;
        this.outputRawData = false;
        try {
            for (Pair pair : pairArr) {
                int intValue = ((Integer) pair.first).intValue();
                if (intValue == 501) {
                    this.outputArrayData = ((Boolean) pair.second).booleanValue();
                } else if (intValue == 502) {
                    this.outputRawData = ((Boolean) pair.second).booleanValue();
                }
            }
        } catch (Exception unused) {
        }
        this.isEcgTest = true;
        this.ecgStageFlag = 0;
        this.ecgStep = 1;
        setupUserProfile();
        this.mCommunicate.mo27588a((byte) 5, new byte[]{1});
    }

    public void stop() {
        super.stop();
        this.isEcgTest = false;
        if (this.isFingerTouchOnSensor) {
            NskAlgoSdk.NskAlgoPause();
            NskAlgoSdk.NskAlgoStop();
            this.isFingerTouchOnSensor = false;
        }
        this.mCommunicate.mo27588a((byte) 5, new byte[]{2});
        this.ecgStep = 0;
        this.ecgStageFlag = 0;
    }

    public void unInit() {
        NskAlgoSdk.NskAlgoUninit();
    }
}
