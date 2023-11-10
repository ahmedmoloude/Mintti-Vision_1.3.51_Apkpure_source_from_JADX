package com.p020kl.commonbase.bean;

/* renamed from: com.kl.commonbase.bean.MemberEntity */
public class MemberEntity {
    private String faceUrl;

    /* renamed from: id */
    private String f830id;
    private String name;
    private int status;
    private String uid;

    public String getId() {
        return this.f830id;
    }

    public void setId(String str) {
        this.f830id = str;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getFaceUrl() {
        return this.faceUrl;
    }

    public void setFaceUrl(String str) {
        this.faceUrl = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }
}
