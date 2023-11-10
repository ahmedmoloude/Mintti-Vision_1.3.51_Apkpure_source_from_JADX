package com.neurosky.AlgoSdk;

public final class NskAlgoState {
    public static final int NSK_ALGO_REASON_BASELINE_EXPIRED = 5;
    public static final int NSK_ALGO_REASON_BY_USER = 4;
    public static final int NSK_ALGO_REASON_CB_CHANGED = 3;
    public static final int NSK_ALGO_REASON_CONFIG_CHANGED = 1;
    public static final int NSK_ALGO_REASON_MASK = 255;
    public static final int NSK_ALGO_REASON_NO_BASELINE = 6;
    public static final int NSK_ALGO_REASON_SIGNAL_QUALITY = 7;
    public static final int NSK_ALGO_REASON_USER_PROFILE_CHANGED = 2;
    public static final int NSK_ALGO_STATE_COLLECTING_BASELINE_DATA = 768;
    public static final int NSK_ALGO_STATE_INITED = 256;
    public static final int NSK_ALGO_STATE_MASK = 65280;
    public static final int NSK_ALGO_STATE_PAUSE = 1280;
    public static final int NSK_ALGO_STATE_RUNNING = 512;
    public static final int NSK_ALGO_STATE_STOP = 1024;
    public static final int NSK_ALGO_STATE_UNINTIED = 1536;
    private int value;

    public NskAlgoState(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        int i = this.value;
        int i2 = 65280 & i;
        if (i2 == 256) {
            return "Inited";
        }
        if (i2 == 768) {
            return "Collecting Baseline";
        }
        if (i2 == 1280) {
            return "Pause";
        }
        if (i2 == 512) {
            return "Running";
        }
        if (i2 == 1024) {
            return "Stop";
        }
        if (i2 == 1536) {
            return "Uninited";
        }
        int i3 = i & 255;
        return i3 == 5 ? "Baseline Expired" : i3 == 4 ? "By User" : i3 == 3 ? "CB Changed" : i3 == 1 ? "Config Changed" : i3 == 6 ? "No Baseline" : i3 == 7 ? "Signal Quality" : i3 == 2 ? "User Profile Changed" : "UNKNOWN";
    }
}
