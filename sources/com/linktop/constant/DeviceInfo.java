package com.linktop.constant;

public class DeviceInfo {
    private String aKey;
    private String deviceId;
    private String deviceKey;
    private String deviceType;
    private String factory;
    private String prodMonth;
    private String prodYear;
    private String reserved;

    public DeviceInfo() {
    }

    public DeviceInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.prodYear = str;
        this.prodMonth = str2;
        this.deviceId = str3;
        this.deviceKey = str4;
        this.factory = str5;
        this.reserved = str6;
        this.deviceType = str7;
        this.aKey = str8;
    }

    public String getAKey() {
        return this.aKey;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public String getFactory() {
        return this.factory;
    }

    public String getProdMonth() {
        return this.prodMonth;
    }

    public String getProdYear() {
        return this.prodYear;
    }

    public String getReserved() {
        return this.reserved;
    }

    public void setAKey(String str) {
        this.aKey = str;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        setProdYear(deviceInfo.getProdYear());
        setProdMonth(deviceInfo.getProdMonth());
        setDeviceId(deviceInfo.getDeviceId());
        setDeviceKey(deviceInfo.getDeviceKey());
        setFactory(deviceInfo.getFactory());
        setReserved(deviceInfo.getReserved());
        setDeviceType(deviceInfo.getDeviceType());
        setAKey(deviceInfo.getAKey());
    }

    public void setDeviceKey(String str) {
        this.deviceKey = str;
    }

    public void setDeviceType(String str) {
        this.deviceType = str;
    }

    public void setFactory(String str) {
        this.factory = str;
    }

    public void setProdMonth(String str) {
        this.prodMonth = str;
    }

    public void setProdYear(String str) {
        this.prodYear = str;
    }

    public void setReserved(String str) {
        this.reserved = str;
    }

    public String toString() {
        return "DeviceInfo{prodYear='" + this.prodYear + '\'' + ", prodMonth='" + this.prodMonth + '\'' + ", deviceId='" + this.deviceId + '\'' + ", deviceKey='" + this.deviceKey + '\'' + ", factory='" + this.factory + '\'' + ", reserved='" + this.reserved + '\'' + ", deviceType='" + this.deviceType + '\'' + ", aKey='" + this.aKey + '\'' + '}';
    }
}
