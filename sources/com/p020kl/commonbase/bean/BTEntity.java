package com.p020kl.commonbase.bean;

import com.google.gson.annotations.SerializedName;
import com.p020kl.commonbase.constants.DataType;

/* renamed from: com.kl.commonbase.bean.BTEntity */
public class BTEntity extends BaseMeasureEntity {
    private static final long serialVersionUID = -3689090190784586667L;
    private Long _id;
    private long createTime;
    private int day;
    @SerializedName("id")
    private String docId;
    private String macAddress;
    private long modifyTime;
    private int month;
    private String note;
    private double temperature;
    @SerializedName("uid")
    private String userId;
    private int year;

    public BTEntity(Long l, String str, double d, long j, long j2, String str2, String str3, int i, int i2, int i3, String str4) {
        this._id = l;
        this.userId = str;
        this.temperature = d;
        this.createTime = j;
        this.modifyTime = j2;
        this.note = str2;
        this.docId = str3;
        this.year = i;
        this.month = i2;
        this.day = i3;
        this.macAddress = str4;
    }

    public BTEntity() {
    }

    public String getType() {
        return DataType.TEMPERATURE.name();
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

    public double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double d) {
        this.temperature = d;
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
