package com.p020kl.commonbase.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.kl.commonbase.utils.ClassUtils */
public class ClassUtils {
    public static Map<String, Object> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                hashMap.put(field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
