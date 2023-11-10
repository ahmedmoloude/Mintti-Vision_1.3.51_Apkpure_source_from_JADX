package com.p020kl.commonbase.bean;

import com.google.gson.annotations.SerializedName;

/* renamed from: com.kl.commonbase.bean.UserInfoEntity */
public class UserInfoEntity extends BaseEntity {
    private static final long serialVersionUID = 3696019652373836978L;
    private String account;
    private String accountType;
    private int age;
    private String birthday;
    private String countryId;
    @SerializedName("face")
    private String faceUrl;
    private int gender;
    private int height;

    /* renamed from: id */
    private String f831id;
    private long modifyTime;
    @SerializedName("nickname")
    private String nickName;
    private String token;
    private int weight;

    public UserInfoEntity(String str, String str2, String str3, String str4, String str5, int i, int i2, String str6, String str7, int i3, int i4, String str8, long j) {
        this.f831id = str;
        this.account = str2;
        this.accountType = str3;
        this.countryId = str4;
        this.nickName = str5;
        this.age = i;
        this.gender = i2;
        this.token = str6;
        this.faceUrl = str7;
        this.height = i3;
        this.weight = i4;
        this.birthday = str8;
        this.modifyTime = j;
    }

    public UserInfoEntity() {
    }

    public String getId() {
        return this.f831id;
    }

    public void setId(String str) {
        this.f831id = str;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String str) {
        this.account = str;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String str) {
        this.accountType = str;
    }

    public String getCountryId() {
        return this.countryId;
    }

    public void setCountryId(String str) {
        this.countryId = str;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String str) {
        this.nickName = str;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int i) {
        this.age = i;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int i) {
        this.gender = i;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public String getFaceUrl() {
        return this.faceUrl;
    }

    public void setFaceUrl(String str) {
        this.faceUrl = str;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int i) {
        this.weight = i;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String str) {
        this.birthday = str;
    }

    public long getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(long j) {
        this.modifyTime = j;
    }
}
