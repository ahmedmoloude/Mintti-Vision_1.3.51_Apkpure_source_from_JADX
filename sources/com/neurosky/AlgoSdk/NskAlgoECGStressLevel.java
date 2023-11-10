package com.neurosky.AlgoSdk;

public final class NskAlgoECGStressLevel {
    public static final int NSK_ALGO_ECG_STRESS_LEVEL_HIGH = 4;
    public static final int NSK_ALGO_ECG_STRESS_LEVEL_INVALID = 0;
    public static final int NSK_ALGO_ECG_STRESS_LEVEL_LOW = 2;
    public static final int NSK_ALGO_ECG_STRESS_LEVEL_MEDIUM = 3;
    public static final int NSK_ALGO_ECG_STRESS_LEVEL_NO = 1;
    public static final int NSK_ALGO_ECG_STRESS_LEVEL_VERYHIGH = 5;
    private int value;

    public NskAlgoECGStressLevel(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        int i = this.value;
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? "" : "Very High Stress" : "High Stress" : "Medium Stress" : "Low Stress" : "No Stress" : "Invalid";
    }
}
