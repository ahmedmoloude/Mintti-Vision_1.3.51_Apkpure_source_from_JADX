package com.p020kl.commonbase.bean;

import com.google.gson.annotations.SerializedName;
import com.p020kl.commonbase.constants.DataType;
import java.text.DecimalFormat;

/* renamed from: com.kl.commonbase.bean.BGEntity */
public class BGEntity extends BaseMeasureEntity {
    private static final long serialVersionUID = -4805915031703557765L;
    private Long _id;
    private double bloodGlucose;
    private String correctionCode;
    private long createTime;
    private int day;
    @SerializedName("id")
    private String docId;
    private String macAddress;
    private int measureTime;
    private long modifyTime;
    private int month;
    private String note;
    @SerializedName("uid")
    private String userId;
    private int year;

    public BGEntity(Long l, String str, double d, String str2, int i, long j, long j2, String str3, String str4, int i2, int i3, int i4, String str5) {
        this._id = l;
        this.userId = str;
        this.bloodGlucose = d;
        this.correctionCode = str2;
        this.measureTime = i;
        this.createTime = j;
        this.modifyTime = j2;
        this.note = str3;
        this.docId = str4;
        this.year = i2;
        this.month = i3;
        this.day = i4;
        this.macAddress = str5;
    }

    public BGEntity() {
    }

    public String getType() {
        return DataType.BG.name();
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

    public double getBloodGlucose() {
        return Double.parseDouble(new DecimalFormat("#.#").format(this.bloodGlucose));
    }

    public void setBloodGlucose(double d) {
        this.bloodGlucose = d;
    }

    public String getCorrectionCode() {
        return this.correctionCode;
    }

    public void setCorrectionCode(String str) {
        this.correctionCode = str;
    }

    public int getMeasureTime() {
        return this.measureTime;
    }

    public void setMeasureTime(int i) {
        this.measureTime = i;
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
