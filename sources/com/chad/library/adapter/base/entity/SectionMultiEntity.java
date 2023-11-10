package com.chad.library.adapter.base.entity;

import java.io.Serializable;

public abstract class SectionMultiEntity<T> implements Serializable, MultiItemEntity {
    public String header;
    public boolean isHeader;

    /* renamed from: t */
    public T f319t;

    public SectionMultiEntity(boolean z, String str) {
        this.isHeader = z;
        this.header = str;
        this.f319t = null;
    }

    public SectionMultiEntity(T t) {
        this.isHeader = false;
        this.header = null;
        this.f319t = t;
    }
}
