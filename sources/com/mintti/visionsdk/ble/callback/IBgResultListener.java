package com.mintti.visionsdk.ble.callback;

import com.mintti.visionsdk.ble.bean.BgEvent;

public interface IBgResultListener {
    void onBgEvent(BgEvent bgEvent);

    void onBgResult(double d);
}
