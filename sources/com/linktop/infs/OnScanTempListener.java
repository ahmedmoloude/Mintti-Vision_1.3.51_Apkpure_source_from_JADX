package com.linktop.infs;

public interface OnScanTempListener {
    void onNoTemp();

    void onScanTempResult(String str, double d, int i);
}
