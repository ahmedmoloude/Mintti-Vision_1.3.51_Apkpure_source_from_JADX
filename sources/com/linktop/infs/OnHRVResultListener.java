package com.linktop.infs;

public interface OnHRVResultListener {
    public static final int KEY_FATIGUE_INDEX = 605;
    public static final int KEY_HBI_COUNT = 606;
    public static final int KEY_LF_HF = 607;
    public static final int KEY_MEAN_HR = 601;
    public static final int KEY_PSI = 604;
    public static final int KEY_RMSSD = 603;
    public static final int KEY_SDNN = 602;

    void onHRVResult(int i, Object obj);

    void onSignalData(int i, int i2);
}
