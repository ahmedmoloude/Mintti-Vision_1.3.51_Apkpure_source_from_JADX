package com.neurosky.AlgoSdk;

import com.p020kl.commonbase.constants.Constants;

public final class NskAlgoDataType {
    public static final int NSK_ALGO_DATA_TYPE_BULK_ECG = 8;
    public static final int NSK_ALGO_DATA_TYPE_ECG = 6;
    public static final int NSK_ALGO_DATA_TYPE_ECG_PQ = 7;
    public static final int NSK_ALGO_DATA_TYPE_MAX = 9;
    private int value;

    public NskAlgoDataType(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        int i = this.value;
        return i != 6 ? i != 7 ? i != 8 ? "" : "Bulk ECG" : "ECG PQ" : Constants.ECG;
    }
}
