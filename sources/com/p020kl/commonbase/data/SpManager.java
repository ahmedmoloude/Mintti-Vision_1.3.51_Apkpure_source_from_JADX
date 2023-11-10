package com.p020kl.commonbase.data;

import android.text.TextUtils;
import com.p020kl.commonbase.bean.rothmanindex.TrendBean;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.utils.JsonUtils;
import com.p020kl.commonbase.utils.SharedPreUtils;

/* renamed from: com.kl.commonbase.data.SpManager */
public class SpManager {
    public static void setIsReadDisclaimer(boolean z) {
        SharedPreUtils.setSp(Constants.SP_IS_READ_DISCLAIMER, z);
    }

    public static boolean getIsReadDisclaimer() {
        return SharedPreUtils.getSp(Constants.SP_IS_READ_DISCLAIMER, false);
    }

    public static void setDeviceAddress(String str) {
        SharedPreUtils.setSp(Constants.SP_DEVICE_MAC, str);
    }

    public static String getDeviceAddress() {
        return SharedPreUtils.getSp(Constants.SP_DEVICE_MAC, "");
    }

    public static void setDeviceName(String str) {
        SharedPreUtils.setSp(Constants.SP_DEVICE_NAME, str);
    }

    public static String getDeviceName() {
        return SharedPreUtils.getSp(Constants.SP_DEVICE_NAME, "");
    }

    public static void setFirmwareVersion(String str) {
        SharedPreUtils.setSp(Constants.SP_DEVIVE_FIRM_VERSION, str);
    }

    public static String getFirmwareVersion() {
        return SharedPreUtils.getSp(Constants.SP_DEVIVE_FIRM_VERSION, "");
    }

    public static void setTemperaTrueUnit(int i) {
        SharedPreUtils.setSp(Constants.SP_TEMP_UNIT, i);
    }

    public static int getTemperaTrueUnit() {
        return SharedPreUtils.getSp(Constants.SP_TEMP_UNIT, 0);
    }

    public static void setIsWarn(boolean z) {
        SharedPreUtils.setSp(Constants.SP_IS_WARN, z);
    }

    public static boolean getIsWarn() {
        return SharedPreUtils.getSp(Constants.SP_IS_WARN, true);
    }

    public static void setPaperSpeed(int i) {
        SharedPreUtils.setSp(Constants.SP_PAPER_SPEED, i);
    }

    public static int getPaperSpeed() {
        return SharedPreUtils.getSp(Constants.SP_PAPER_SPEED, 0);
    }

    public static void setGain(float f) {
        SharedPreUtils.setSp(Constants.SP_GAIN, f);
    }

    public static float getGain() {
        return SharedPreUtils.getSp(Constants.SP_GAIN, 1.0f);
    }

    public static void setBgUnit(int i) {
        SharedPreUtils.setSp(Constants.SP_BG_UNIT, i);
    }

    public static int getBgUnit() {
        return SharedPreUtils.getSp(Constants.SP_BG_UNIT, 0);
    }

    public static void setToken(String str) {
        SharedPreUtils.setSp(Constants.SP_TOKEN, str);
    }

    public static String getToken() {
        return SharedPreUtils.getSp(Constants.SP_TOKEN, "");
    }

    public static void setUserId(String str) {
        SharedPreUtils.setSp(Constants.SP_USER_ID, str);
    }

    public static String getUserId() {
        return SharedPreUtils.getSp(Constants.SP_USER_ID, "");
    }

    public static void setMemberId(String str) {
        SharedPreUtils.setSp(Constants.SP_MEMBER_ID, str);
    }

    public static String getMemberId() {
        return SharedPreUtils.getSp(Constants.SP_MEMBER_ID, "");
    }

    public static void setMemberName(String str) {
        SharedPreUtils.setSp(Constants.SP_MEMBER_NAME, str);
    }

    public static String getMemberName() {
        return SharedPreUtils.getSp(Constants.SP_MEMBER_NAME, "");
    }

    public static void setMemberInfo(String[] strArr) {
        SharedPreUtils.setSp(Constants.SP_MEMBER_INFO, strArr);
    }

    public static String[] getMemberInfo() {
        return SharedPreUtils.getSp(Constants.SP_MEMBER_INFO, Constants.SP_MEMBER_DEFAULT);
    }

    public static void setMemberFace(String str) {
        SharedPreUtils.setSp(Constants.SP_MEMBER_URL, str);
    }

    public static String getMemberFace() {
        return SharedPreUtils.getSp(Constants.SP_MEMBER_URL, "http://vision.mintti.cn/mintti-vision/face/default/defaultMaleFace.png");
    }

    public static void setLastAccount(String str) {
        SharedPreUtils.setSp(Constants.SP_LAST_ACCOUNT, str);
    }

    public static String getLastAccount() {
        return SharedPreUtils.getSp(Constants.SP_LAST_ACCOUNT, "");
    }

    public static void deleteAll() {
        SharedPreUtils.clear();
    }

    public static void setRothmanIndex(TrendBean trendBean) {
        SharedPreUtils.setSp(Constants.SP_ROTHMAN_INDEX, JsonUtils.toJsonString(trendBean));
    }

    public static TrendBean getRothmanIndex() {
        String sp = SharedPreUtils.getSp(Constants.SP_ROTHMAN_INDEX, "");
        if (!TextUtils.isEmpty(sp)) {
            return (TrendBean) JsonUtils.formJsonString(sp, TrendBean.class);
        }
        return null;
    }

    public static void setEcgGuideIsShow(boolean z) {
        SharedPreUtils.setSp(Constants.ECG_GUIDE_IS_SHOW, z);
    }

    public static boolean getEcgGuideIsShow() {
        return SharedPreUtils.getSp(Constants.ECG_GUIDE_IS_SHOW, true);
    }

    public static void setBpGuideIsShow(boolean z) {
        SharedPreUtils.setSp(Constants.BP_GUIDE_IS_SHOW, z);
    }

    public static boolean getBpGuideIsShow() {
        return SharedPreUtils.getSp(Constants.BP_GUIDE_IS_SHOW, true);
    }

    public static void setSpo2GuideIsShow(boolean z) {
        SharedPreUtils.setSp(Constants.SPO2_GUIDE_IS_SHOW, z);
    }

    public static boolean getSpo2GuideIsShow() {
        return SharedPreUtils.getSp(Constants.SPO2_GUIDE_IS_SHOW, true);
    }

    public static void setBgGuideIsShow(boolean z) {
        SharedPreUtils.setSp(Constants.BG_GUIDE_IS_SHOW, z);
    }

    public static boolean getBgGuideIsShow() {
        return SharedPreUtils.getSp(Constants.BG_GUIDE_IS_SHOW, true);
    }

    public static void setBtGuideIsShow(boolean z) {
        SharedPreUtils.setSp(Constants.BT_GUIDE_IS_SHOW, z);
    }

    public static boolean getBtGuideIsShow() {
        return SharedPreUtils.getSp(Constants.BT_GUIDE_IS_SHOW, true);
    }

    public static void setConnectGuideIsShow(boolean z) {
        SharedPreUtils.setSp(Constants.CONNECT_GUIDE_IS_SHOW, z);
    }

    public static boolean getConnectGuideIsShow() {
        return SharedPreUtils.getSp(Constants.CONNECT_GUIDE_IS_SHOW, true);
    }
}
