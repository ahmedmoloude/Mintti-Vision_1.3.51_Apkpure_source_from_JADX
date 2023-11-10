package com.p020kl.commonbase.bean;

/* renamed from: com.kl.commonbase.bean.BaseMeasureEntity */
public abstract class BaseMeasureEntity extends BaseEntity {
    private static final long serialVersionUID = 5034599959585120289L;

    public abstract long getCreateTime();

    public int getMeasureTime() {
        return 0;
    }

    public abstract String getType();

    public void setDocId(String str) {
    }
}
