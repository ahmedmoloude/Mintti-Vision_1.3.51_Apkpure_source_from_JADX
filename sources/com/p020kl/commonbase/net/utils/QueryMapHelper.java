package com.p020kl.commonbase.net.utils;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.kl.commonbase.net.utils.QueryMapHelper */
public class QueryMapHelper {
    public static Map<String, Integer> getDayMap(int i, int i2, int i3) {
        HashMap hashMap = new HashMap();
        hashMap.put("year", Integer.valueOf(i));
        hashMap.put("month", Integer.valueOf(i2));
        hashMap.put("day", Integer.valueOf(i3));
        return hashMap;
    }

    public static Map<String, Integer> getMonthMap(int i, int i2) {
        HashMap hashMap = new HashMap();
        hashMap.put("year", Integer.valueOf(i));
        hashMap.put("month", Integer.valueOf(i2));
        return hashMap;
    }
}
