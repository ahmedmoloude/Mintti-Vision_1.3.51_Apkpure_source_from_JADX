package com.linktop.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.linktop.utils.BleDevLog;

public abstract class BleEnableStateReceiver extends BroadcastReceiver {
    /* renamed from: a */
    public IntentFilter mo27405a() {
        return new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
    }

    /* renamed from: b */
    public abstract void mo27406b();

    /* renamed from: c */
    public abstract void mo27407c();

    /* renamed from: d */
    public abstract void mo27408d();

    /* renamed from: e */
    public abstract void mo27409e();

    public void onReceive(Context context, Intent intent) {
        switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1)) {
            case 10:
                BleDevLog.m138a(getClass(), "STATE_OFF");
                mo27406b();
                return;
            case 11:
                BleDevLog.m138a(getClass(), "STATE_TURNING_ON");
                mo27409e();
                return;
            case 12:
                BleDevLog.m138a(getClass(), "STATE_ON");
                mo27407c();
                return;
            case 13:
                BleDevLog.m138a(getClass(), "STATE_TURNING_OFF");
                mo27408d();
                return;
            default:
                return;
        }
    }
}
