package com.p020kl.vision_ecg;

import java.util.UUID;

/* renamed from: com.kl.vision_ecg.SmctConstant */
public class SmctConstant {
    public static final UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final short KEY_ACC_DATA = 27;
    public static final short KEY_ALGO_ARRHYTHMIA = 3;
    public static final short KEY_ALGO_Af = 103;
    public static final short KEY_ALGO_BEAT_TYPE = 101;
    public static final short KEY_ALGO_BEAT_TYPE_DELAY = 102;
    public static final short KEY_ALGO_HEART_RATE = 0;
    public static final short KEY_ALGO_HF = 24;
    public static final short KEY_ALGO_HRV = 2;
    public static final short KEY_ALGO_LF = 23;
    public static final short KEY_ALGO_LF_HF = 26;
    public static final short KEY_ALGO_MAXRR = 14;
    public static final short KEY_ALGO_MEANRR = 16;
    public static final short KEY_ALGO_MINRR = 15;
    public static final short KEY_ALGO_MISSED_BEAT = 6;
    public static final short KEY_ALGO_NNX = 19;
    public static final short KEY_ALGO_PNNX = 20;
    public static final short KEY_ALGO_PREMATURE_BEAT = 5;
    public static final short KEY_ALGO_PR_INTERVAL = 11;
    public static final short KEY_ALGO_P_DURATION = 10;
    public static final short KEY_ALGO_P_TYPE = 13;
    public static final short KEY_ALGO_QRS_DURATION = 8;
    public static final short KEY_ALGO_QT_INTERVAL = 9;
    public static final short KEY_ALGO_RESP_RATE = 1;
    public static final short KEY_ALGO_RESP_SIG = 100;
    public static final short KEY_ALGO_RESP_SIG_SPS = 1001;
    public static final short KEY_ALGO_RHYTHM = 104;
    public static final short KEY_ALGO_RMSSD = 18;
    public static final short KEY_ALGO_RR_INTERVAL = 7;
    public static final short KEY_ALGO_SDNN = 17;
    public static final short KEY_ALGO_STRESS_LEVEL = 28;
    public static final short KEY_ALGO_STRESS_SCORE = 27;
    public static final short KEY_ALGO_TP = 25;
    public static final short KEY_ALGO_T_TYPE = 12;
    public static final short KEY_ALGO_ULF = 21;
    public static final short KEY_ALGO_VLF = 22;
    public static final short KEY_BLE_CONNECT_STATE = 10;
    public static final short KEY_BODY_POSE = 14;
    public static final short KEY_DEVICE_BLE_AREA = 18;
    public static final short KEY_DEVICE_BLE_VERSION = 17;
    public static final short KEY_DEVICE_ELECTRODE_DROP = 13;
    public static final short KEY_DEVICE_FIRMWARE_AREA = 16;
    public static final short KEY_DEVICE_FIRMWARE_VERSION = 15;
    public static final short KEY_DEVICE_POWER_LEVEL = 12;
    public static final short KEY_DEVICE_REQUEST_TIMESTAMP_ON_PHONE = 28;
    public static final short KEY_DEVICE_RETURN_TIME_FOR_VERIFY = 29;
    public static final short KEY_DEVICE_UPDATE_BLE_PROCESS = 20;
    public static final short KEY_DEVICE_UPDATE_BLE_RESULT = 24;
    public static final short KEY_DEVICE_UPDATE_FIRMWARE_PROCESS = 19;
    public static final short KEY_DEVICE_UPDATE_FIRMWARE_RESULT = 21;
    public static final short KEY_DEVICE_UPDATE_MSG = 25;
    public static final short KEY_ECG_DATA = 26;
    public static final short KEY_TYPE_BLE = 23;
    public static final short KEY_TYPE_FIRMWARE = 22;
    public static final short KEY_USER_KICK_DEVICE_THAN_NOTIFY_APP = 30;
    public static final String UUID_KEY_DATA_FFE2 = "0000ffe2-0000-1000-8000-00805f9b34fb";
    public static final short VALUE_ALGO_AF_CESSAT = 0;
    public static final short VALUE_ALGO_AF_ONSET = 1;
    public static final short VALUE_ALGO_BRAD_ONSET = 2;
    public static final short VALUE_ALGO_NORMAL_RHYTHM_ONSET = 0;
    public static final short VALUE_ALGO_TACH_ONSET = 1;
    public static final short VALUE_BLE_CONNECTED = 0;
    public static final short VALUE_BLE_DATA_AVAILABLE = 3;
    public static final short VALUE_BLE_DISCONNECTED = 1;
    public static final short VALUE_BLE_SERVICE_DISCOVERED = 2;
    public static final short VALUE_HEAVY_STRESS = 3;
    public static final short VALUE_LIGHT_STRESS = 1;
    public static final short VALUE_MODERATE_STRESS = 2;
    public static final short VALUE_POSE_FALL_DOWN = 3;
    public static final short VALUE_POSE_LIE_LOW = 2;
    public static final short VALUE_POSE_RUN = 5;
    public static final short VALUE_POSE_SIDE_DECUBITUS = 1;
    public static final short VALUE_POSE_STAND = 0;
    public static final short VALUE_POSE_STOP = 0;
    public static final short VALUE_POSE_WALK = 4;
    public static final short VALUE_P_TYPE_CAN_NOT_DETECT = 0;
    public static final short VALUE_P_TYPE_DOWNWARD = 2;
    public static final short VALUE_P_TYPE_DOWNWARD_UPWARD = 4;
    public static final short VALUE_P_TYPE_UPWARD = 1;
    public static final short VALUE_P_TYPE_UPWARD_DOWNWARD = 3;
    public static final short VALUE_RELAXATION = 0;
    public static final short VALUE_STRESS_INITIALIZING = 4;
    public static final short VALUE_T_TYPE_CAN_NOT_DETECT = 0;
    public static final short VALUE_T_TYPE_DOWNWARD = 2;
    public static final short VALUE_T_TYPE_DOWNWARD_UPWARD = 4;
    public static final short VALUE_T_TYPE_UPWARD = 1;
    public static final short VALUE_T_TYPE_UPWARD_DOWNWARD = 3;
    public static final short VALUE_UPDATE_FIRMWARE_IS_READY_FOR_UPDATE = 0;
    public static final short VALUE_UPDATE_MD5_CHECK_FAILED = 5;
    public static final short VALUE_UPDATE_MD5_CHECK_SUCCESS = 4;
    public static final short VALUE_UPDATE_SEND_DATA_FAILED = 1;
    public static final short VALUE_UPDATE_SEND_DATA_SUCCESS = 2;
    public static final short VALUE_UPDATE_SEND_LAST_DATA_SUCCESS = 3;
}