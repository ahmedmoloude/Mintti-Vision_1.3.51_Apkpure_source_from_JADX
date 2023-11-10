package com.p020kl.healthmonitor.measure.rothmanindex;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.AxisData */
public class AxisData {
    private int value1;
    private String value2;

    public AxisData() {
    }

    public AxisData(int i, String str) {
        this.value1 = i;
        this.value2 = str;
    }

    public int getValue1() {
        return this.value1;
    }

    public void setValue1(int i) {
        this.value1 = i;
    }

    public String getValue2() {
        return this.value2;
    }

    public void setValue2(String str) {
        this.value2 = str;
    }
}
