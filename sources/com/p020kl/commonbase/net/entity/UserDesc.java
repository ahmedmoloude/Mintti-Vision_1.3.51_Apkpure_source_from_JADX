package com.p020kl.commonbase.net.entity;

/* renamed from: com.kl.commonbase.net.entity.UserDesc */
public class UserDesc {
    private int age;
    private String birthday;
    private long createTime;
    private String face;
    private int gender;
    private int height;

    /* renamed from: id */
    private String f846id;
    private long modifyTime;
    private String nickname;
    private int weight;

    public String getId() {
        return this.f846id;
    }

    public void setId(String str) {
        this.f846id = str;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String str) {
        this.nickname = str;
    }

    public String getFace() {
        return this.face;
    }

    public void setFace(String str) {
        this.face = str;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int i) {
        this.gender = i;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int i) {
        this.age = i;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String str) {
        this.birthday = str;
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

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long j) {
        this.createTime = j;
    }

    public long getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(long j) {
        this.modifyTime = j;
    }
}
