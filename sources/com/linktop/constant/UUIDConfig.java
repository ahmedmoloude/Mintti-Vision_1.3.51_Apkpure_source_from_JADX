package com.linktop.constant;

import android.os.ParcelUuid;
import java.util.UUID;

public final class UUIDConfig {
    public static final String CCC = "00002902-0000-1000-8000-00805f9b34fb";
    public static final String DEV_INFO_FIRMWARE_REV_UUID = "00002A26-0000-1000-8000-00805f9b34fb";
    public static final String DEV_INFO_HARDWARE_PCB_UUID = "00002A27-0000-1000-8000-00805f9b34fb";
    public static final String DEV_INFO_SER_UUID = "0000180A-0000-1000-8000-00805f9b34fb";
    public static final String DEV_INFO_SOFTWARE_REV_UUID = "00002A28-0000-1000-8000-00805f9b34fb";
    public static final String HEART_RATE_MEASUREMENT_CHARA = "0000fff4-0000-1000-8000-00805f9b34fb";
    public static final String HEART_RATE_WRITE_CHARA = "0000fff1-0000-1000-8000-00805f9b34fb";
    public static final String HRP_SERVICE = "0000ff27-0000-1000-8000-00805f9b34fb";
    public static final String THERM_CONNECT_CONFIRM = "0000fff5-0000-1000-8000-00805f9b34fb";
    public static final String THERM_QR_CODE = "00002A24-0000-1000-8000-00805f9b34fb";
    public static final String THERM_QR_CODE_SERVICE = "0000180A-0000-1000-8000-00805f9b34fb";
    public static final String THERM_SERVICE = "0000fff0-0000-1000-8000-00805f9b34fb";

    public static synchronized ParcelUuid getParcelUuid(String str) {
        ParcelUuid fromString;
        synchronized (UUIDConfig.class) {
            fromString = ParcelUuid.fromString(str);
        }
        return fromString;
    }

    public static synchronized UUID getUUID(String str) {
        UUID fromString;
        synchronized (UUIDConfig.class) {
            fromString = UUID.fromString(str);
        }
        return fromString;
    }
}
