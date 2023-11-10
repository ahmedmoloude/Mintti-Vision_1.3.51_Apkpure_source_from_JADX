package com.p020kl.commonbase.callback;

import com.p020kl.commonbase.constants.NetType;

/* renamed from: com.kl.commonbase.callback.NetChangeListener */
public interface NetChangeListener {
    void onConnect(NetType netType);

    void onDisConnect();
}
