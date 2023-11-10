package com.mintti.visionsdk.ble.callback;

public interface IBleNotifyResponse {
    void onCharacteristicChanged(byte[] bArr);

    void onNotifyFailed();

    void onNotifySuccess();
}
