package com.p020kl.commonbase.bean;

/* renamed from: com.kl.commonbase.bean.CustomDate */
public class CustomDate {
    private int day;
    private int month;
    private int year;

    public CustomDate(int i, int i2) {
        this.year = i;
        this.month = i2;
        this.day = 0;
    }

    public CustomDate(int i, int i2, int i3) {
        this.year = i;
        this.month = i2;
        this.day = i3;
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
}
