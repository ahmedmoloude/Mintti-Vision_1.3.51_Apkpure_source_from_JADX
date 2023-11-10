package com.linktop.constant;

public interface IUserProfile {
    public static final int FEMALE = 0;
    public static final int MALE = 1;

    long getBirthday();

    int getGender();

    int getHeight();

    String getUsername();

    int getWeight();
}
