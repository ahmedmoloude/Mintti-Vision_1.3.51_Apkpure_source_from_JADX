package com.linktop.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface UserDataType {
    public static final int AGE = 1;
    public static final int HEIGHT = 2;
    public static final int SEX = 0;
    public static final int WEIGHT = 3;
}
