package com.p020kl.commonbase.net.entity;

import com.p020kl.commonbase.utils.JsonUtils;

/* renamed from: com.kl.commonbase.net.entity.MeasureRequest */
public class MeasureRequest<T> {
    private String jsonData;
    private String type;

    public MeasureRequest(T t, String str) {
        this.jsonData = JsonUtils.toJsonString(t);
        this.type = str;
    }

    public String getJsonData() {
        return this.jsonData;
    }

    public void setJsonData(String str) {
        this.jsonData = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }
}
