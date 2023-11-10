package com.p020kl.commonbase.bean;

/* renamed from: com.kl.commonbase.bean.NoteEntity */
public class NoteEntity {
    private int age;
    private String comment;
    private int gender;
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
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

    public String getComment() {
        return this.comment;
    }

    public void setComment(String str) {
        this.comment = str;
    }
}
