package com.chad.library.adapter.base.entity;

import java.io.Serializable;

public abstract class SectionEntity<T> implements Serializable {
    public String header;
    public boolean isHeader;

    /* renamed from: t */
    public T f318t;

    public SectionEntity(boolean z, String str) {
        this.isHeader = z;
        this.header = str;
        this.f318t = null;
    }

    public SectionEntity(T t) {
        this.isHeader = false;
        this.header = null;
        this.f318t = t;
    }
}
