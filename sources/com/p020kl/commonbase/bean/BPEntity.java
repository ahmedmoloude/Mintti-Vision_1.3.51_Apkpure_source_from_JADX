package com.p020kl.commonbase.bean;

import com.google.gson.annotations.SerializedName;
import com.p020kl.commonbase.constants.DataType;

/* renamed from: com.kl.commonbase.bean.BPEntity */
public class BPEntity extends BaseMeasureEntity {
    private static final long serialVersionUID = -6570988175196799547L;
    private Long _id;
    private long createTime;
    private int day;
    private int diastolicPressure;
    @SerializedName("id")
    private String docId;
    private int heartRate;
    private String macAddress;
    @SerializedName("updateTime")
    private long modifyTime;
    private int month;
    private String note;
    private int systolicPressure;
    @SerializedName("uid")
    private String userId;
    private int year;

    public BPEntity(Long l, String str, int i, int i2, int i3, long j, long j2, String str2, String str3, int i4, int i5, int i6, String str4) {
        this._id = l;
        this.userId = str;
        this.systolicPressure = i;
        this.diastolicPressure = i2;
        this.heartRate = i3;
        this.createTime = j;
        this.modifyTime = j2;
        this.note = str2;
        this.docId = str3;
        this.year = i4;
        this.month = i5;
        this.day = i6;
        this.macAddress = str4;
    }

    public BPEntity() {
    }

    public String getType() {
        return DataType.BP.name();
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

    public int getSystolicPressure() {
        return this.systolicPressure;
    }

    public void setSystolicPressure(int i) {
        this.systolicPressure = i;
    }

    public int getDiastolicPressure() {
        return this.diastolicPressure;
    }

    public void setDiastolicPressure(int i) {
        this.diastolicPressure = i;
    }

    public int getHeartRate() {
        return this.heartRate;
    }

    public void setHeartRate(int i) {
        this.heartRate = i;
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
