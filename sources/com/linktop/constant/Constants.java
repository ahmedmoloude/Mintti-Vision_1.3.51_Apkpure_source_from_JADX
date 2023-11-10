package com.linktop.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {
    public static final int CONFIG_BP_RUN_ORIG_DATA = 102;
    public static final int CONFIG_BP_SAVE_ORIG_DATA = 101;
    public static final int CONFIG_BT_USE_TEST2 = 201;
    public static final int CONFIG_ECG_OUTPUT_ARRAY_DATA = 501;
    public static final int CONFIG_ECG_OUTPUT_RAW_DATA = 502;
    public static final int CONFIG_HRV_RESTING_HR = 702;
    public static final int CONFIG_HRV_SAMPLING_RATE = 701;
    public static final int CONFIG_SPO2_FILE_PATH = 402;
    public static final int CONFIG_SPO2_USE_75mA = 401;
    public static final int ECG_KEY_HEART_AGE = 524288;
    public static final int ECG_KEY_HEART_BEAT = 524289;
    public static final int ECG_KEY_HEART_RATE = 65536;
    public static final int ECG_KEY_HRV = 1048576;
    public static final int ECG_KEY_HRV_FD = 33554432;
    public static final int ECG_KEY_HRV_TD = 16777216;
    public static final int ECG_KEY_MOOD = 262144;
    public static final int ECG_KEY_R2R = 262145;
    public static final int ECG_KEY_RESPIRATORY_RATE = 4194304;
    public static final int ECG_KEY_ROBUST_HR = 4194305;
    public static final int ECG_KEY_SMOOTH = 2097152;
    public static final int ECG_KEY_STRESS = 131072;
    public static final int ECG_SQ_GOOD = 0;
    public static final int ECG_SQ_MEDIUM = 1;
    public static final int ECG_SQ_NOT_DETECTED = 3;
    public static final int ECG_SQ_POOR = 2;
    public static final int ECG_STRESS_LEVEL_HIGH = 4;
    public static final int ECG_STRESS_LEVEL_INVALID = 0;
    public static final int ECG_STRESS_LEVEL_LOW = 2;
    public static final int ECG_STRESS_LEVEL_MEDIUM = 3;
    public static final int ECG_STRESS_LEVEL_NO = 1;
    public static final int ECG_STRESS_LEVEL_VERY_HIGH = 5;
    public static final int SAMPLING_RATE_125_HZ = 125;
    public static final int SAMPLING_RATE_250_HZ = 250;
    public static final int SAMPLING_RATE_500_HZ = 500;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ECGAlgoKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ECGKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ECGSignalQuality {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ECGStressLevel {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PPGSamplingRate {
    }
}
