package com.mintti.visionsdk.ble.callback;

import com.mintti.visionsdk.ble.BleDevice;

public interface IBleScanCallback {
    void onScanFailed(int i);

    void onScanResult(BleDevice bleDevice);
}
