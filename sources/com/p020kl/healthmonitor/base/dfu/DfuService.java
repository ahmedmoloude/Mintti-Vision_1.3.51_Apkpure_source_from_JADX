package com.p020kl.healthmonitor.base.dfu;

import android.app.Activity;
import p036no.nordicsemi.android.dfu.DfuBaseService;

/* renamed from: com.kl.healthmonitor.base.dfu.DfuService */
public class DfuService extends DfuBaseService {
    /* access modifiers changed from: protected */
    public boolean isDebug() {
        return true;
    }

    /* access modifiers changed from: protected */
    public Class<? extends Activity> getNotificationTarget() {
        return NotificationActivity.class;
    }
}
