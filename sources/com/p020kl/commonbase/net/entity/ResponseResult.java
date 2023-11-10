package com.p020kl.commonbase.net.entity;

import com.p020kl.commonbase.utils.JsonUtils;

/* renamed from: com.kl.commonbase.net.entity.ResponseResult */
public class ResponseResult<T> {
    protected T data;
    protected String msg;
    protected int status;

    public String toString() {
        return JsonUtils.toJsonString(this);
    }

    public ResponseResult() {
    }

    public ResponseResult(int i, String str, T t) {
        this.status = i;
        this.msg = str;
        this.data = t;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T t) {
        this.data = t;
    }
}
