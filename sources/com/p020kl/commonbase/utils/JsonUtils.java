package com.p020kl.commonbase.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/* renamed from: com.kl.commonbase.utils.JsonUtils */
public class JsonUtils {
    private static Gson getGson() {
        return new GsonBuilder().create();
    }

    public static <T> T formJsonString(String str, Class<T> cls) {
        return getGson().fromJson(str, cls);
    }

    public static String toJsonString(Object obj) {
        return getGson().toJson(obj);
    }
}
