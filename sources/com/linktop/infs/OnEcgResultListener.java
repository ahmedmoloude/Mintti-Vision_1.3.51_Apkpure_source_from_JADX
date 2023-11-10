package com.linktop.infs;

public interface OnEcgResultListener extends IFingerDetector {
    void onDrawWave(Object obj);

    void onECGValues(int i, int i2);

    void onSignalQuality(int i);
}
