package com.p020kl.commonbase.net.entity;

/* renamed from: com.kl.commonbase.net.entity.EmailVerifyCodeParam */
public class EmailVerifyCodeParam {
    public String email;
    private String signKey;
    private String time;
    private String type;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
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
}
