package com.p020kl.healthmonitor.bean;

/* renamed from: com.kl.healthmonitor.bean.HistoryItemBean */
public class HistoryItemBean {
    private long createTime;
    private int day;
    private boolean isChecked;
    private String listData;
    private String listType;
    private String measureResult;
    private String measureTime;
    private String measureValue;
    private int month;
    private int year;

    public HistoryItemBean(String str, long j, String str2, String str3, String str4, String str5, int i, int i2, int i3) {
        this.measureTime = str;
        this.measureValue = str2;
        this.measureResult = str3;
        this.listData = str4;
        this.listType = str5;
        this.year = i;
        this.month = i2;
        this.day = i3;
        this.createTime = j;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long j) {
        this.createTime = j;
    }

    public String getMeasureTime() {
        return this.measureTime;
    }

    public void setMeasureTime(String str) {
        this.measureTime = str;
    }

    public String getMeasureValue() {
        return this.measureValue;
    }

    public void setMeasureValue(String str) {
        this.measureValue = str;
    }

    public String getMeasureResult() {
        return this.measureResult;
    }

    public void setMeasureResult(String str) {
        this.measureResult = str;
    }

    public String getListData() {
        return this.listData;
    }

    public void setListData(String str) {
        this.listData = str;
    }

    public String getListType() {
        return this.listType;
    }

    public void setListType(String str) {
        this.listType = str;
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

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }
}
