package com.linktop.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface WareType {
    public static final int VER_FIRMWARE = 0;
    public static final int VER_HARDWARE = 1;
    public static final int VER_SOFTWARE = 2;
}
