package com.linktop.infs;

public interface OnSpO2ResultListener {
    public static final int FINGER_NO_TOUCH = 0;
    public static final int FINGER_TOUCH = 1;

    void onFingerDetection(int i);

    void onSpO2End();

    void onSpO2Result(int i, int i2);

    void onSpO2Wave(int i);
}
