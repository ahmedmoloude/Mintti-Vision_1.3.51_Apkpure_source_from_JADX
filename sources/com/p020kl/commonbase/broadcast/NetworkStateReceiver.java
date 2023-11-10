package com.p020kl.commonbase.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.p020kl.commonbase.callback.NetChangeListener;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.constants.NetType;
import com.p020kl.commonbase.net.utils.NetworkUtils;

/* renamed from: com.kl.commonbase.broadcast.NetworkStateReceiver */
public class NetworkStateReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkStateReceiver";
    private NetChangeListener listener;
    private NetType netType = NetType.NONE;

    public void setListener(NetChangeListener netChangeListener) {
        this.listener = netChangeListener;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            Log.e(TAG, "onReceive: 异常");
        } else if (intent.getAction().equals(Constants.ANDROID_NET_CHANGE_ACTION)) {
            Log.d(TAG, "onReceive: 网络发生变化");
            this.netType = NetworkUtils.getNetType();
            if (NetworkUtils.isNetworkAvailable()) {
                Log.d(TAG, "onReceive: 网络连接成功");
                NetChangeListener netChangeListener = this.listener;
                if (netChangeListener != null) {
                    netChangeListener.onConnect(this.netType);
                    return;
                }
                return;
            }
            Log.e(TAG, "onReceive: 网络连接失败");
            NetChangeListener netChangeListener2 = this.listener;
            if (netChangeListener2 != null) {
                netChangeListener2.onDisConnect();
            }
        }
    }
}
