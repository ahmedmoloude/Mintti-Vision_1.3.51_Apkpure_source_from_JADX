package com.neurosky.AlgoSdk;

public final class NskAlgoSampleRate {
    public static final int NSK_ALGO_SAMPLE_RATE_256 = 1;
    public static final int NSK_ALGO_SAMPLE_RATE_300 = 2;
    public static final int NSK_ALGO_SAMPLE_RATE_512 = 3;
    public static final int NSK_ALGO_SAMPLE_RATE_600 = 4;
    private int value;

    public NskAlgoSampleRate(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        int i = this.value;
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? "" : "600" : "512" : "300" : "256";
    }
}
