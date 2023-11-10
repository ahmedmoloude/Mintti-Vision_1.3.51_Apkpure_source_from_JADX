package com.p020kl.commonbase.event;

import java.io.Serializable;

/* renamed from: com.kl.commonbase.event.Event */
public class Event<T> implements Serializable {
    private static final long serialVersionUID = -7333055006958652436L;
    private T data;

    public Event() {
    }

    public Event(T t) {
        this.data = t;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T t) {
        this.data = t;
    }
}
