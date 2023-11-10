package com.p020kl.commonbase.net;

import android.content.Context;
import android.content.IntentFilter;
import com.p020kl.commonbase.bean.SingletonTemplate;
import com.p020kl.commonbase.broadcast.NetworkStateReceiver;
import com.p020kl.commonbase.callback.NetChangeListener;
import com.p020kl.commonbase.constants.Constants;

/* renamed from: com.kl.commonbase.net.NetworkListener */
public class NetworkListener {
    private static final SingletonTemplate<NetworkListener> INSTANCE = new SingletonTemplate<NetworkListener>() {
        /* access modifiers changed from: protected */
        public NetworkListener create() {
            return new NetworkListener();
        }
    };
    private Context context;
    private NetworkStateReceiver receiver;

    private NetworkListener() {
        this.receiver = new NetworkStateReceiver();
    }

    public static NetworkListener getInstance() {
        return INSTANCE.get();
    }

    public Context getContext() {
        return this.context;
    }

    public void setListener(NetChangeListener netChangeListener) {
        this.receiver.setListener(netChangeListener);
    }

    public void init(Context context2) {
        this.context = context2;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        context2.registerReceiver(this.receiver, intentFilter);
    }

    public void release() {
        this.context.unregisterReceiver(this.receiver);
    }
}
