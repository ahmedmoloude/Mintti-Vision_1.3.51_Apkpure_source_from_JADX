package com.p020kl.commonbase.net.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.constants.NetType;
import com.p020kl.commonbase.net.NetworkListener;

/* renamed from: com.kl.commonbase.net.utils.NetworkUtils */
public class NetworkUtils {
    public static boolean isNetworkConnected(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null) {
            return false;
        }
        return activeNetworkInfo.isAvailable();
    }

    public static boolean isNetworkConnected() {
        return isNetworkConnected(BaseApplication.getInstance());
    }

    public static boolean isNetworkAvailable() {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) NetworkListener.getInstance().getContext().getSystemService("connectivity");
        if (!(connectivityManager == null || (allNetworkInfo = connectivityManager.getAllNetworkInfo()) == null)) {
            for (NetworkInfo state : allNetworkInfo) {
                if (state.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static NetType getNetType() {
        ConnectivityManager connectivityManager = (ConnectivityManager) NetworkListener.getInstance().getContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            return NetType.NONE;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return NetType.NONE;
        }
        int type = activeNetworkInfo.getType();
        if (type == 0) {
            return NetType.MOBILE;
        }
        if (type == 1) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }
}
