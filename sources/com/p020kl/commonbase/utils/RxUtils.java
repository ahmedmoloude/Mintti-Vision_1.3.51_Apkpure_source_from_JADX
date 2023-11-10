package com.p020kl.commonbase.utils;

import com.p020kl.commonbase.C1544R;
import com.p020kl.commonbase.net.utils.NetworkUtils;
import p028io.reactivex.Observable;
import p028io.reactivex.ObservableEmitter;
import p028io.reactivex.ObservableOnSubscribe;

/* renamed from: com.kl.commonbase.utils.RxUtils */
public class RxUtils {
    public static Observable checkNetworkConnection() {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            public void subscribe(ObservableEmitter<Object> observableEmitter) throws Exception {
                if (!NetworkUtils.isNetworkConnected()) {
                    observableEmitter.onError(new Throwable(StringUtils.getString(C1544R.string.no_network_connection)));
                }
                observableEmitter.onComplete();
            }
        });
    }
}
