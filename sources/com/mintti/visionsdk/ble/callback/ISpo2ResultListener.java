package com.mintti.visionsdk.ble.callback;

public interface ISpo2ResultListener {
    void onSpo2End();

    void onSpo2ResultData(int i, double d);

    void onWaveData(int i);
}
