package com.mintti.visionsdk.ble;

public enum DeviceType {
    TYPE_VISION(0),
    TYPE_MINTTI_VISION(1);
    
    private int deviceType;

    private DeviceType(int i) {
        this.deviceType = i;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(int i) {
        this.deviceType = i;
    }
}
