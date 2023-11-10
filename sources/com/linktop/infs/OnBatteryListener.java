package com.linktop.infs;

public interface OnBatteryListener {
    void onBatteryCharging();

    void onBatteryFull();

    void onBatteryQuery(int i);
}
