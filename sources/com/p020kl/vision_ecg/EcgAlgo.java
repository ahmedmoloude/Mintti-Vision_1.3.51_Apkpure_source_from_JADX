package com.p020kl.vision_ecg;

/* renamed from: com.kl.vision_ecg.EcgAlgo */
public class EcgAlgo {
    private final int AF_CESSAT = 4;
    private final int AF_ONSET = 3;
    private final int BRAD_ONSET = 32;
    private final int HEARTRATE_ERR = 2;
    private final int HEARTRATE_LARGE_INFERENCE = 3;
    private final int HEARTRATE_NOT_REFRESH = 0;
    private final int HEARTRATE_REFRESH = 1;
    private final int NORMAL_RHYTHM_ONSET = 2;
    private final int RESP_SIG_REFRESH = 1;
    private final int TACH_ONSET = 22;
    private final int TYPE_REFRESH = 1;
    private final int VALID_ALL = 32;
    private final int VALID_ANY = 64;
    private final int VALID_P = 16;
    private final int VALID_PR = 4;
    private final int VALID_QRS = 2;
    private final int VALID_QT = 8;
    private final int VALID_RR = 1;
    private ISmctAlgoCallback mISmctAlgoCallback;

    static {
        System.loadLibrary("vision_ecg");
    }

    public EcgAlgo(int i) {
        if (initECGParam(i) == 0) {
            throw new IllegalArgumentException("The sample rate is not reasonable.");
        }
    }

    public void addECGData(int i) {
        addNativeECGData(i);
        if (this.mISmctAlgoCallback != null) {
            if (getHeartRateFlag() == 1) {
                this.mISmctAlgoCallback.algoData(0, getHeartRate());
            }
            int respFlag = getRespFlag();
            if (respFlag == 11 || respFlag == 12 || respFlag == 13) {
                this.mISmctAlgoCallback.algoData(1, getRespRate());
            }
            if (getFlatRespSigFlag() == 1) {
                this.mISmctAlgoCallback.algoData(100, getRespSig());
            }
            if (getTypeFlag() == 1) {
                this.mISmctAlgoCallback.algoData(101, getBeatType());
                this.mISmctAlgoCallback.algoData(102, getBeatTypeDelay());
            }
            int intAndDurFlag = getIntAndDurFlag();
            if ((intAndDurFlag & 2) == 0) {
                this.mISmctAlgoCallback.algoData(8, getIntAndDurDelay(), getInsQRS());
            }
            if ((intAndDurFlag & 1) == 0) {
                this.mISmctAlgoCallback.algoData(7, getIntAndDurDelay(), getInsRR());
            }
            if ((intAndDurFlag & 4) == 0) {
                this.mISmctAlgoCallback.algoData(11, getIntAndDurDelay(), getInsPR());
            }
            if ((intAndDurFlag & 8) == 0) {
                this.mISmctAlgoCallback.algoData(9, getIntAndDurDelay(), getInsQT());
            }
            if ((intAndDurFlag & 16) == 0) {
                this.mISmctAlgoCallback.algoData(10, getIntAndDurDelay(), getInsP());
            }
            int afFlag = getAfFlag();
            if (afFlag == 3) {
                this.mISmctAlgoCallback.algoData(103, 1);
            } else if (afFlag == 4) {
                this.mISmctAlgoCallback.algoData(103, 0);
            }
            int rhythmFlag = getRhythmFlag();
            if (rhythmFlag == 32) {
                this.mISmctAlgoCallback.algoData(104, 2);
            } else if (rhythmFlag == 22) {
                this.mISmctAlgoCallback.algoData(104, 1);
            } else if (rhythmFlag == 2) {
                this.mISmctAlgoCallback.algoData(104, 0);
            }
        }
    }

    public native void addNativeECGData(int i);

    public native void algoStart();

    public native void calHrv();

    public native int getAPBNum();

    public native int getAfFlag();

    public native char getBeatType();

    public native int getBeatTypeDelay();

    public native int getBradNum();

    public native int getFlatRespSigFlag();

    public native int getHF();

    public native int getHeartRate();

    public native int getHeartRateFlag();

    public native int getInsP();

    public native int getInsPR();

    public native int getInsQRS();

    public native int getInsQT();

    public native int getInsRR();

    public native int getIntAndDurDelay();

    public native int getIntAndDurFlag();

    public native int getLF();

    public native int getLFHFRatio();

    public native int getLongStopNum();

    public native int getMaxRR();

    public native int getMeanRR();

    public native int getMinRR();

    public native int getNNX();

    public native int getPNNX();

    public native int getRMSSD();

    public native int getRespFlag();

    public native int getRespRate();

    public native int getRespSig();

    public native int getRespSigSps();

    public native int getRhythmFlag();

    public native int getSDANN();

    public native int getSDNN();

    public native int getSDNNindex();

    public native int getTP();

    public native int getTachNum();

    public native int getTypeFlag();

    public native int getVLF();

    public native int getVPBNum();

    public native int initECGParam(int i);

    public void setISmctAlgoCallback(ISmctAlgoCallback iSmctAlgoCallback) {
        this.mISmctAlgoCallback = iSmctAlgoCallback;
    }
}
