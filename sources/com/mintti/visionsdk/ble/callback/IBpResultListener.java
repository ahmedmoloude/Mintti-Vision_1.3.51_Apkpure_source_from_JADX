package com.mintti.visionsdk.ble.callback;

public interface IBpResultListener {
    void onBpError();

    void onBpResult(int i, int i2, int i3);

    void onLeadError();
}
