package com.p020kl.commonbase.net.entity;

import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.p020kl.commonbase.net.constants.NetConstants;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.kl.commonbase.net.entity.PhoneVerifyCodeParam */
public class PhoneVerifyCodeParam {
    private String countryId;
    public String phone;
    private String signKey;
    private String time;
    private String type;

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public String getCountryId() {
        return this.countryId;
    }

    public void setCountryId(String str) {
        this.countryId = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public String getSignKey() {
        return this.signKey;
    }

    public void setSignKey(String str) {
        this.signKey = str;
    }

    public Map<String, String> getVCodeMap() {
        HashMap hashMap = new HashMap();
        hashMap.put(NetConstants.SIGN_ACCOUNT_TYPE_PHONE, this.phone);
        hashMap.put("countryId", this.countryId);
        hashMap.put(DublinCoreProperties.TYPE, this.type);
        hashMap.put("time", this.time);
        hashMap.put("signKey", this.signKey);
        return hashMap;
    }
}
