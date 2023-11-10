package com.linktop.infs;

public interface OnBleConnectListener {
    void onBLENoSupported();

    void onBleState(int i);

    void onOpenBLE();

    void onUpdateDialogBleList();
}
