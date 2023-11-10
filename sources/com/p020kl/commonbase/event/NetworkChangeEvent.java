package com.p020kl.commonbase.event;

import com.p020kl.commonbase.constants.NetType;

/* renamed from: com.kl.commonbase.event.NetworkChangeEvent */
public class NetworkChangeEvent extends Event<NetType> {
    public NetworkChangeEvent(NetType netType) {
        super(netType);
    }
}
