package com.p020kl.commonbase.bean.rothmanindex;

/* renamed from: com.kl.commonbase.bean.rothmanindex.AuthenticatedSession */
public class AuthenticatedSession {
    private String access_token;
    private int expires_in;
    private String token_type;

    public String getAccess_token() {
        return this.access_token;
    }

    public void setAccess_token(String str) {
        this.access_token = str;
    }

    public String getToken_type() {
        return this.token_type;
    }

    public void setToken_type(String str) {
        this.token_type = str;
    }

    public int getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(int i) {
        this.expires_in = i;
    }

    public String toString() {
        return "AuthenticatedSession{access_token='" + this.access_token + '\'' + ", token_type='" + this.token_type + '\'' + ", expires_in=" + this.expires_in + '}';
    }
}
