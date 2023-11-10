package com.p020kl.commonbase.constants;

import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.xml.xmp.PdfSchema;
import com.linktop.DeviceType;
import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.utils.StringUtils;

/* renamed from: com.kl.commonbase.constants.Constants */
public class Constants {
    public static final String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String APK_PACKAGE = "com.kl.healthmonitor";
    public static final String Add_MEMBER = "Add_member";

    /* renamed from: BG */
    public static final String f837BG = "BG";
    public static final String BG_GUIDE_IS_SHOW = "bg_guide_is_show";
    public static final String BG_HOME = "BGHOME";
    public static final String BG_LIST = "BGLIST";
    public static final String BG_TYPE = "bg";
    public static final String BG_UNIT = "mmol/L";
    public static final int BLE_CLOSED = 100;
    public static final int BLE_CONNECTED_DEVICE = 103;
    public static final int BLE_CONNECTING_DEVICE = 102;
    public static final int BLE_NOTIFICATION_DISABLED = 105;
    public static final int BLE_NOTIFICATION_ENABLED = 104;
    public static final int BLE_OPENED_AND_DISCONNECT = 101;
    public static final int BLE_STATUS_CONNECT_FAILED = 107;
    public static final int BLE_STATUS_UNBIND = 106;

    /* renamed from: BP */
    public static final String f838BP = "BP";
    public static final String BPM_UNIT = "BPM";
    public static final String BP_GUIDE_IS_SHOW = "bp_guide_is_show";
    public static final String BP_HOME = "BPHOME";
    public static final String BP_LIST = "BPLIST";
    public static final String BP_TYPE = "bp";
    public static final String BP_UNIT = "mmHg";

    /* renamed from: BT */
    public static final String f839BT = "BT";
    public static final String BT_GUIDE_IS_SHOW = "bt_guide_is_show";
    public static final String BT_HOME = "BTHOME";
    public static final String BT_LIST = "BTLIST";
    public static final String BT_TYPE = "bt";
    public static final String BUNDLE_BT = "bundle_bt";
    public static final String BUNDLE_CONNECT_TYPE = "bundle_connect_type";
    public static final String BUNDLE_DIASTOLIC_PRESSURE = "bundle_diastolicp_ressure";
    public static final String BUNDLE_HR = "bundle_hr";
    public static final String BUNDLE_NICKNAME = "nickname";
    public static final String BUNDLE_RECORD = "bundle_record";
    public static final String BUNDLE_RECORD_STATUS = "bundle_record_status";
    public static final String BUNDLE_RESPIRATORY_RATE = "bundle_respiratory_rate";
    public static final String BUNDLE_SPO2 = "bundle_spo2";
    public static final String BUNDLE_SYSTOLIC_PRESSURE = "bundle_systolic_pressure";
    public static final String BUNDLE_USER_INFO = "bundle_user_info";
    public static final String CLIENT_ID = "2ab8bebd-e65f-4e4f-8ade-3bc8a12989d3";
    public static final String CONNECT_GUIDE_IS_SHOW = "connect_guide_is_show";
    public static final String CONNECT_TYPE_MEASURE = "connect_type_measure";
    public static final String CONNECT_TYPE_UPGRADE = "connect_type_upgrade";
    public static final String DATE_DAY = "day";
    public static final String DATE_MONTH = "month";
    public static final String DATE_YEAR = "year";
    public static final String DAY = "day";
    public static final String ECG = "ECG";
    public static final String ECG_GUIDE_IS_SHOW = "ecg_guide_is_show";
    public static final String ECG_HOME = "ECG_HOME";
    public static final String ECG_LIST = "ECG_LIST";
    public static final String ECG_TYPE = "ecg";
    public static final String FILE_NAME_FACE = "face.png";
    public static final String FILE_NAME_FACE_CROP = "face_crop.png";
    public static final String FILE_NAME_FEED_IMG = "feed.png";
    public static final String FILE_PATH_ECG;
    public static final String FILE_PATH_ROOT;
    public static final String FILE_PATH_ZIP;
    public static final String FILE_SHARE_TEXT;
    public static final boolean IS_DEBUG = false;
    public static final String MONTH = "month";
    public static final String MS_UNIT = "ms";
    public static final int ONE = 1;

    /* renamed from: OS */
    public static final String f840OS = "android";
    public static final String OTHER_MONTH = "other_month";
    public static final String PDF_IMG_ROTE_PATH = BaseApplication.getInstance().getExternalFilesDir(HtmlTags.IMG).getAbsolutePath();
    public static final String PDF_PATH;
    public static final String PDF_ROTE_PATH;
    public static final String PM_UNIT = "PM";
    public static final String PRIVACY_POLICY_EN_URL = "http://www.mintti.cn/privacyPolic/mintti-vision-privacypolicy-en.html";
    public static final String PRIVACY_POLICY_ZH_URL = "http://www.mintti.cn/privacyPolic/mintti-vision-privacypolicy-zh.html";
    public static final int REQUEST_BLUETOOTH_ENABLE = 11;
    public static final int REQUEST_CAMERA_CODE = 13;
    public static final int REQUEST_CHANGE_USER_INFO = 10;
    public static final int REQUEST_CROP_FACE = 16;
    public static final int REQUEST_GPS_ENABLE = 15;
    public static final int REQUEST_IMAGE_CODE = 12;
    public static final int REQUEST_RECORD_DETAILS = 14;
    public static final String SECRET_ID = "a2ffcc30-f511-443f-946c-404bf0e4b712";
    public static final String SHARE_TEXT_NAME = ".txt";
    public static final String SPO2 = "SPO2";
    public static final String SPO2H_HOME = "SPO2HHOME";
    public static final String SPO2H_LIST = "SPO2HLIST";
    public static final String SPO2_GUIDE_IS_SHOW = "spo2_guide_is_show";
    public static final String SPO2_TYPE = "spo2";
    public static final String SPO2_UNIT = "bpm";
    public static final String SP_ACCOUNT = "sp_account";
    public static final String SP_ACCOUNT_TYPE = "sp_account_type";
    public static final String SP_BG_UNIT = "sp_bg_unit";
    public static final String SP_DEVICE_MAC = "sp_device_mac";
    public static final String SP_DEVICE_NAME = "sp_device_name";
    public static final String SP_DEVICE_TYPE = "sp_device_type";
    public static final String SP_DEVIVE_FIRM_VERSION = "sp_device_firm_version";
    public static final String SP_FACE = "sp_face";
    public static final String SP_GAIN = "sp_ecg_gain";
    public static final String SP_INFO_FILE = "HEALTH_MONITOR_INFO_FILE";
    public static final String SP_IS_CHECK_APK_UPDATE = "sp_isCheckUpdate";
    public static final String SP_IS_READ_DISCLAIMER = "sp_is_read_disclaimer";
    public static final String SP_IS_SIGNIN = "sp_is_signin";
    public static final String SP_IS_WARN = "sp_is_warn";
    public static final String SP_LAST_ACCOUNT = "sp_last_account";
    public static final String[] SP_MEMBER_DEFAULT = {"", "http://vision.mintti.cn/mintti-vision/face/default/defaultMaleFace.png", "Member"};
    public static final String SP_MEMBER_ID = "sp_member_id";
    public static final String[] SP_MEMBER_INFO = {SP_MEMBER_ID, SP_MEMBER_URL, SP_MEMBER_NAME};
    public static final String SP_MEMBER_NAME = "sp_member_name";
    public static final String SP_MEMBER_URL = "sp_member_url";
    public static final String SP_PAPER_SPEED = "sp_ecg_paper_speed";
    public static final String SP_PASSWORD = "sp_password";
    public static final String SP_ROTHMAN_INDEX = "sp_rothman_index";
    public static final String SP_TEMP_UNIT = "sp_temp_unit";
    public static final String SP_TOKEN = "sp_token";
    public static final String SP_USER_ID = "sp_user_id";
    public static final String SP_USER_NAME = "sp_username";
    public static final String SWITCH_MEMBER = "switch_member";
    public static final String TIME_FORMAT_BIRTHDAY = "yyyy-MM-dd";
    public static final String TIME_FORMAT_FILE = "yyyy-MM-dd_HH-mm-ss.SSS";
    public static final String TIME_FORMAT_ITEM = StringUtils.getString(C1544R.string.time_format_item);
    public static final String TIME_FORMAT_ITEM_TOP = StringUtils.getString(C1544R.string.time_format_item_top);
    public static final String TIME_FORMAT_TIMEFULL = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String TIME_FORMAT_XITEM = "HH:mm MM-dd ";
    public static final String Time_FORMAT_PDFTIME = "yyyy-MM-dd HH:mm";
    public static final String URL_ROTHMAN_INDEX = "https://healthscore.alivesci.com/analyzer/";
    public static final String URL_SESSIONS = "https://security.alivesci.com/";
    public static final String USER_AGREEMENT_EN_URL = "http://www.mintti.cn/privacyPolic/mintti-vision-user-agreement-en.html";
    public static final String USER_AGREEMENT_ZH_URL = "http://www.mintti.cn/privacyPolic/mintti-vision-user-agreement-zh.html";
    public static final String VARIANT = "release";
    public static final String VARIANT_DEBUG = "debug";
    public static final String VARIANT_RELEASE = "release";
    public static final String VISION_FIRMWARE = "vision";
    public static final String VISION_FIRMWARE_A00 = "vision-A00";
    public static final String VISION_FIRMWARE_A01 = "vision-A01";
    public static final String WEEK = "week";
    public static final String YEAR = "year";

    static {
        String absolutePath = BaseApplication.getInstance().getExternalFilesDir(DeviceType.HealthMonitor).getAbsolutePath();
        FILE_PATH_ROOT = absolutePath;
        String absolutePath2 = BaseApplication.getInstance().getExternalFilesDir(PdfSchema.DEFAULT_XPATH_ID).getAbsolutePath();
        PDF_ROTE_PATH = absolutePath2;
        FILE_PATH_ECG = absolutePath + "/ecg";
        FILE_PATH_ZIP = absolutePath + "/zip";
        FILE_SHARE_TEXT = absolutePath + "/share";
        PDF_PATH = absolutePath2 + "/table.pdf";
    }
}
