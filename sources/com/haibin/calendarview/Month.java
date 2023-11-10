package com.haibin.calendarview;

import java.io.Serializable;

final class Month implements Serializable {
    private int count;
    private int diff;
    private int month;
    private int year;

    Month() {
    }

    /* access modifiers changed from: package-private */
    public int getDiff() {
        return this.diff;
    }

    /* access modifiers changed from: package-private */
    public void setDiff(int i) {
        this.diff = i;
    }

    /* access modifiers changed from: package-private */
    public int getCount() {
        return this.count;
    }

    /* access modifiers changed from: package-private */
    public void setCount(int i) {
        this.count = i;
    }

    /* access modifiers changed from: package-private */
    public int getMonth() {
        return this.month;
    }

    /* access modifiers changed from: package-private */
    public void setMonth(int i) {
        this.month = i;
    }

    /* access modifiers changed from: package-private */
    public int getYear() {
        return this.year;
    }

    /* access modifiers changed from: package-private */
    public void setYear(int i) {
        this.year = i;
    }
}
