package com.p020kl.commonbase.bean;

import com.google.gson.annotations.SerializedName;
import com.p020kl.commonbase.constants.DataType;

/* renamed from: com.kl.commonbase.bean.Spo2Entity */
public class Spo2Entity extends BaseMeasureEntity {
    private static final long serialVersionUID = 4309782108665234407L;
    private Long _id;
    private long createTime;
    private int day;
    @SerializedName("id")
    private String docId;
    private int heartRate;
    private String macAddress;
    private long modifyTime;
    private int month;
    private String note;
    private double spo2;
    @SerializedName("uid")
    private String userId;
    private int year;

    public Spo2Entity(Long l, String str, int i, double d, long j, long j2, String str2, String str3, int i2, int i3, int i4, String str4) {
        this._id = l;
        this.userId = str;
        this.heartRate = i;
        this.spo2 = d;
        this.createTime = j;
        this.modifyTime = j2;
        this.note = str2;
        this.docId = str3;
        this.year = i2;
        this.month = i3;
        this.day = i4;
        this.macAddress = str4;
    }

    public Spo2Entity() {
    }

    public String getType() {
        return DataType.SPO2.name();
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long l) {
        this._id = l;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public int getHeartRate() {
        return this.heartRate;
    }

    public void setHeartRate(int i) {
        this.heartRate = i;
    }

    public double getSpo2() {
        return this.spo2;
    }

    public void setSpo2(double d) {
        this.spo2 = d;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long j) {
        this.createTime = j;
    }

    public long getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(long j) {
        this.modifyTime = j;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String str) {
        this.note = str;
    }

    public String getDocId() {
        return this.docId;
    }

    public void setDocId(String str) {
        this.docId = str;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int i) {
        this.year = i;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int i) {
        this.month = i;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int i) {
        this.day = i;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public void setMacAddress(String str) {
        this.macAddress = str;
    }
}
