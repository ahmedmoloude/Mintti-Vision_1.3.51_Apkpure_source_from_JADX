package com.linktop.whealthService;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface MeasureType {

    /* renamed from: BG */
    public static final int f982BG = 3;

    /* renamed from: BP */
    public static final int f983BP = 1;

    /* renamed from: BT */
    public static final int f984BT = 2;
    public static final int CHOL = 9;
    public static final int ECG = 5;
    public static final int HRV = 7;
    public static final int SPO2 = 4;

    /* renamed from: UA */
    public static final int f985UA = 8;
}
