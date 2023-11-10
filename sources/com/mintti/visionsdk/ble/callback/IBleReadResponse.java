package com.mintti.visionsdk.ble.callback;

public interface IBleReadResponse {
    void onReadFailed();

    void onReadSuccess(byte[] bArr);
}
