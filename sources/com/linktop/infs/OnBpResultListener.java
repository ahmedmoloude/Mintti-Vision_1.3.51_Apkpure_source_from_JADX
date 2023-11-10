package com.linktop.infs;

public interface OnBpResultListener {
    void onBpResult(int i, int i2, int i3);

    void onBpResultError();

    void onLeakError(int i);
}
