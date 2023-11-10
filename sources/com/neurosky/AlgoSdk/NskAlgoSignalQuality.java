package com.neurosky.AlgoSdk;

public final class NskAlgoSignalQuality {
    public static final int NSK_ALGO_SQ_GOOD = 0;
    public static final int NSK_ALGO_SQ_MEDIUM = 1;
    public static final int NSK_ALGO_SQ_NOT_DETECTED = 3;
    public static final int NSK_ALGO_SQ_POOR = 2;
    private int value;

    public NskAlgoSignalQuality(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        int i = this.value;
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? "UNKNOWN" : "NOT DETECTED" : "POOR" : "MEDIUM" : "GOOD";
    }
}
