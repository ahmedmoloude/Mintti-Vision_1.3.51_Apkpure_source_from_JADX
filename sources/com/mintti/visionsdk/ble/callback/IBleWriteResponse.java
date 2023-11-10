package com.mintti.visionsdk.ble.callback;

public interface IBleWriteResponse {
    void onWriteFailed();

    void onWriteSuccess();
}
