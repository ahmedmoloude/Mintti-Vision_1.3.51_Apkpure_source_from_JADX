package com.mintti.visionsdk.ble.callback;

public interface IEcgResultListener {
    void onDrawWave(int i);

    void onEcgDuration(int i, boolean z);

    void onEcgResult(int i, int i2, int i3);

    void onHeartRate(int i);

    void onRespiratoryRate(int i);
}
