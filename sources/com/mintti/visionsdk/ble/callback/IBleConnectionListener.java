package com.mintti.visionsdk.ble.callback;

public interface IBleConnectionListener {
    void onConnectFailed(String str);

    void onConnectSuccess(String str);

    void onDisconnected(String str, boolean z);
}
