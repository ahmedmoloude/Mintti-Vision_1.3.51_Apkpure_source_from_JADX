package com.neurosky.AlgoSdk;

public final class NskAlgoType {
    public static final int NSK_ALGO_TYPE_ECG_AFIB = 8388608;
    public static final int NSK_ALGO_TYPE_ECG_HEARTAGE = 524288;
    public static final int NSK_ALGO_TYPE_ECG_HEARTRATE = 65536;
    public static final int NSK_ALGO_TYPE_ECG_HRV = 1048576;
    public static final int NSK_ALGO_TYPE_ECG_HRVFD = 33554432;
    public static final int NSK_ALGO_TYPE_ECG_HRVTD = 16777216;
    public static final int NSK_ALGO_TYPE_ECG_MOOD = 262144;
    public static final int NSK_ALGO_TYPE_ECG_RESPIRATORY = 4194304;
    public static final int NSK_ALGO_TYPE_ECG_SMOOTH = 2097152;
    public static final int NSK_ALGO_TYPE_ECG_STRESS = 131072;
    private int value;

    public NskAlgoType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        int i = this.value;
        return i != 65536 ? i != 131072 ? i != 262144 ? i != 524288 ? i != 1048576 ? i != 2097152 ? i != 4194304 ? i != 8388608 ? i != 16777216 ? i != 33554432 ? "UNKNOWN" : "HRVFD" : "HRVTD" : "AFIB" : "Respiratory" : "Smooth Filter" : "HRV" : "HeartAge" : "Mood" : "Stress" : "HeartRate";
    }
}
