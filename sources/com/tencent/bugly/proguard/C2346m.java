package com.tencent.bugly.proguard;

import java.io.Serializable;

/* renamed from: com.tencent.bugly.proguard.m */
/* compiled from: BUGLY */
public abstract class C2346m implements Serializable {
    /* renamed from: a */
    public abstract void mo28088a(C2343k kVar);

    /* renamed from: a */
    public abstract void mo28089a(C2345l lVar);

    /* renamed from: a */
    public abstract void mo28090a(StringBuilder sb, int i);

    public String toString() {
        StringBuilder sb = new StringBuilder();
        mo28090a(sb, 0);
        return sb.toString();
    }
}
