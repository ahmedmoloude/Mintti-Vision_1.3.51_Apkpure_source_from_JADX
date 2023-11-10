package com.p020kl.commonbase.bean;

/* renamed from: com.kl.commonbase.bean.SingletonTemplate */
public abstract class SingletonTemplate<T> {
    private T mInstance;

    /* access modifiers changed from: protected */
    public abstract T create();

    public final T get() {
        T t;
        synchronized (this) {
            if (this.mInstance == null) {
                this.mInstance = create();
            }
            t = this.mInstance;
        }
        return t;
    }
}
