package com.p020kl.commonbase.net.utils;

import com.p020kl.commonbase.net.entity.EmailVerifyCodeParam;
import com.p020kl.commonbase.net.entity.PhoneVerifyCodeParam;
import com.p020kl.commonbase.utils.StringUtils;

/* renamed from: com.kl.commonbase.net.utils.VCodeParamUtil */
public class VCodeParamUtil {
    public static EmailVerifyCodeParam getEmailVCodeParam(String str, String str2) {
        EmailVerifyCodeParam emailVerifyCodeParam = new EmailVerifyCodeParam();
        emailVerifyCodeParam.setEmail(str);
        emailVerifyCodeParam.setType(str2);
        emailVerifyCodeParam.setTime(String.valueOf((int) (System.currentTimeMillis() / 1000)));
        emailVerifyCodeParam.setSignKey(StringUtils.getStringMD5(emailVerifyCodeParam.getEmail() + emailVerifyCodeParam.getTime() + emailVerifyCodeParam.getType() + "mintti@#!vision"));
        return emailVerifyCodeParam;
    }

    public static PhoneVerifyCodeParam getPhoneVCodeParam(String str, String str2, String str3) {
        PhoneVerifyCodeParam phoneVerifyCodeParam = new PhoneVerifyCodeParam();
        phoneVerifyCodeParam.setPhone(str);
        phoneVerifyCodeParam.setCountryId(str2);
        phoneVerifyCodeParam.setType(str3);
        int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        phoneVerifyCodeParam.setTime(currentTimeMillis + "");
        phoneVerifyCodeParam.setSignKey(StringUtils.getStringMD5(phoneVerifyCodeParam.getPhone() + phoneVerifyCodeParam.getTime() + phoneVerifyCodeParam.getType() + "mintti@#!vision"));
        return phoneVerifyCodeParam;
    }
}
