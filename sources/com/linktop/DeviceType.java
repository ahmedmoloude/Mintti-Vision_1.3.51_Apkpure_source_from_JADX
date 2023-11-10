package com.linktop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface DeviceType {
    public static final String HealthMonitor = "HealthMonitor";
    public static final String Thermometer = "Thermometer";
}
