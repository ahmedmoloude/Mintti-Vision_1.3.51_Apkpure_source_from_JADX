package com.p020kl.commonbase.bean.rothmanindex;

import p028io.jsonwebtoken.Header;

/* renamed from: com.kl.commonbase.bean.rothmanindex.HeaderBean */
public class HeaderBean {
    private String alg = "HS256";
    private String typ = Header.JWT_TYPE;

    public String getAlg() {
        return this.alg;
    }

    public void setAlg(String str) {
        this.alg = str;
    }

    public String getTyp() {
        return this.typ;
    }

    public void setTyp(String str) {
        this.typ = str;
    }
}
