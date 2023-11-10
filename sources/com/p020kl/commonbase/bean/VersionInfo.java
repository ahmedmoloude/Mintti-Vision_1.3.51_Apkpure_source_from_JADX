package com.p020kl.commonbase.bean;

import com.google.gson.annotations.SerializedName;

/* renamed from: com.kl.commonbase.bean.VersionInfo */
public class VersionInfo {
    @SerializedName("createTime")
    private long createTime;
    @SerializedName("downloadUrl")
    private String downloadUrl;
    @SerializedName("fileMd5")
    private String fileMd5;
    @SerializedName("id")

    /* renamed from: id */
    private int f832id;
    @SerializedName("os")

    /* renamed from: os */
    private String f833os;
    @SerializedName("updateInstall")
    private int updateInstall;
    @SerializedName(alternate = {"upadteLog"}, value = "updateLog")
    private String updateLog;
    @SerializedName("variant")
    private String variant;
    @SerializedName("versionCode")
    private int versionCode;
    @SerializedName("versionName")
    private String versionName;

    public int getId() {
        return this.f832id;
    }

    public void setId(int i) {
        this.f832id = i;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int i) {
        this.versionCode = i;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String str) {
        this.versionName = str;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String str) {
        this.downloadUrl = str;
    }

    public String getVariant() {
        return this.variant;
    }

    public void setVariant(String str) {
        this.variant = str;
    }

    public int getUpdateInstall() {
        return this.updateInstall;
    }

    public void setUpdateInstall(int i) {
        this.updateInstall = i;
    }

    public String getFileMd5() {
        return this.fileMd5;
    }

    public void setFileMd5(String str) {
        this.fileMd5 = str;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long j) {
        this.createTime = j;
    }

    public String getOs() {
        return this.f833os;
    }

    public void setOs(String str) {
        this.f833os = str;
    }

    public String getUpdateLog() {
        return this.updateLog;
    }

    public void setUpdateLog(String str) {
        this.updateLog = str;
    }
}
