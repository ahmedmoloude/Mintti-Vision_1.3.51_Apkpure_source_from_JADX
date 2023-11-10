package com.linktop.infs;

import com.linktop.constant.DeviceInfo;

public interface OnDeviceInfoListener {
    void onDeviceInfo(DeviceInfo deviceInfo);

    void onReadDeviceInfoFailed();
}
