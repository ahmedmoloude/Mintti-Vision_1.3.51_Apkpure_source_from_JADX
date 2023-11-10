package com.p020kl.commonbase.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.p020kl.commonbase.constants.DataType;

/* renamed from: com.kl.commonbase.bean.ECGEntity */
public class ECGEntity extends BaseMeasureEntity {
    private static final long serialVersionUID = -7146496077930517766L;
    private Long _id;
    private int avgHr;

    /* renamed from: br */
    private int f828br;
    private long createTime;
    private int day;
    @SerializedName("id")
    private String docId;
    private long duration;
    private String fileMd5;
    @Expose(deserialize = false, serialize = false)
    private String filePath;
    @Expose(deserialize = false, serialize = false)
    private String fileUrl;
    private int hrv;
    private boolean isChecked;
    private String macAddress;
    private long modifyTime;
    private int month;
    private int mood;
    private String note;
    @SerializedName("rrimax")
    private int rriMax;
    @SerializedName("rrimin")
    private int rriMin;
    private int sampleRate;
    @SerializedName("uid")
    private String userId;
    private int year;

    public ECGEntity(Long l, String str, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, long j2, String str2, String str3, long j3, String str4, String str5, int i8, int i9, int i10, String str6, String str7) {
        this._id = l;
        this.userId = str;
        this.rriMax = i;
        this.rriMin = i2;
        this.avgHr = i3;
        this.hrv = i4;
        this.mood = i5;
        this.f828br = i6;
        this.sampleRate = i7;
        this.duration = j;
        this.createTime = j2;
        this.filePath = str2;
        this.fileUrl = str3;
        this.modifyTime = j3;
        this.note = str4;
        this.docId = str5;
        this.year = i8;
        this.month = i9;
        this.day = i10;
        this.fileMd5 = str6;
        this.macAddress = str7;
    }

    public ECGEntity() {
    }

    public String getType() {
        return DataType.ECG.name();
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long j) {
        this.createTime = j;
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

    public int getRriMax() {
        return this.rriMax;
    }

    public void setRriMax(int i) {
        this.rriMax = i;
    }

    public int getRriMin() {
        return this.rriMin;
    }

    public void setRriMin(int i) {
        this.rriMin = i;
    }

    public int getAvgHr() {
        return this.avgHr;
    }

    public void setAvgHr(int i) {
        this.avgHr = i;
    }

    public int getHrv() {
        return this.hrv;
    }

    public void setHrv(int i) {
        this.hrv = i;
    }

    public int getMood() {
        return this.mood;
    }

    public void setMood(int i) {
        this.mood = i;
    }

    public int getBr() {
        return this.f828br;
    }

    public void setBr(int i) {
        this.f828br = i;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public void setSampleRate(int i) {
        this.sampleRate = i;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public void setFileUrl(String str) {
        this.fileUrl = str;
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

    public String getFileMd5() {
        return this.fileMd5;
    }

    public void setFileMd5(String str) {
        this.fileMd5 = str;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public void setMacAddress(String str) {
        this.macAddress = str;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }
}
