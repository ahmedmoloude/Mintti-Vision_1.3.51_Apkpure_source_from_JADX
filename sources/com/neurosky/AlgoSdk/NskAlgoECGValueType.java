package com.neurosky.AlgoSdk;

public final class NskAlgoECGValueType {
    public static final int NSK_ALGO_ECG_TYPE_UNKNOWN = 255;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_AFIB = 7;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_BASELINE_UPDATED = 13;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_HEARTAGE = 6;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_HEARTBEAT = 11;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_HR = 1;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_HRV = 5;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_HRV_FREQDOMAIN = 15;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_HRV_TIMEDOMAIN = 14;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_MOOD = 3;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_R2R = 4;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_RDETECTED = 8;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_RESPIRATORY_RATE = 12;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_ROBUST_HR = 2;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_SMOOTH = 9;
    public static final int NSK_ALGO_ECG_VALUE_TYPE_STRESS = 10;
    private int value;

    public NskAlgoECGValueType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        switch (this.value) {
            case 1:
                return "Heart Rate";
            case 2:
                return "Robust Heart Rate";
            case 3:
                return "Mood";
            case 4:
                return "R2R";
            case 5:
                return "HRV";
            case 6:
                return "Heart Age";
            case 7:
                return "AFIB";
            case 8:
                return "R Detected";
            case 9:
                return "Smooth Value";
            case 10:
                return "Stress";
            case 11:
                return "Heartbeat";
            case 12:
                return "Respiratory Rate";
            case 13:
                return "Baseline updated notification";
            case 14:
                return "HRV Time Domain Analysis";
            case 15:
                return "HRV Frequency Domain Analysis";
            default:
                return "";
        }
    }
}
