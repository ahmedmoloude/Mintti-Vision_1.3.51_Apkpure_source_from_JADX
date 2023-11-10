package com.p020kl.commonbase.event;

/* renamed from: com.kl.commonbase.event.BatteryEvent */
public class BatteryEvent extends Event {
    private static final long serialVersionUID = 4461617145937736175L;
    private int batteryValue;
    private boolean isBatteryFull;
    private boolean isCharging;

    public BatteryEvent() {
    }

    public BatteryEvent(boolean z, int i, boolean z2) {
        this.isCharging = z;
        this.batteryValue = i;
        this.isBatteryFull = z2;
    }

    public boolean isCharging() {
        return this.isCharging;
    }

    public void setCharging(boolean z) {
        this.isCharging = z;
    }

    public int getBatteryValue() {
        return this.batteryValue;
    }

    public void setBatteryValue(int i) {
        this.batteryValue = i;
    }

    public boolean isBatteryFull() {
        return this.isBatteryFull;
    }

    public void setBatteryFull(boolean z) {
        this.isBatteryFull = z;
    }
}
