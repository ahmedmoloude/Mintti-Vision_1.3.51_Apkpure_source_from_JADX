package com.linktop.constant;

public class Pair extends android.util.Pair<Integer, Object> {
    public Pair(Integer num, Object obj) {
        super(num, obj);
    }

    public static Pair create(Integer num, Object obj) {
        return new Pair(num, obj);
    }
}
