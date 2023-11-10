package com.p020kl.healthmonitor.base;

import android.app.ActivityManager;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.mintti.visionsdk.ble.BleManager;
import com.mintti.visionsdk.ble.callback.IBleInitCallback;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.event.TokenNotExistEvent;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.LoggerUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.sign.SignActivity;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* renamed from: com.kl.healthmonitor.base.AppApplication */
public class AppApplication extends BaseApplication {
    public void onCreate() {
        super.onCreate();
        EventBusUtil.register(this);
        BleManager.getInstance().init(this, new IBleInitCallback() {
            public void onInitFailed() {
            }

            public void onInitSuccess() {
                BaseApplication.isBindService = true;
            }
        });
        Log.e("AppApplication", "onCreate: ");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenNotExistEvent(TokenNotExistEvent tokenNotExistEvent) {
        LoggerUtil.m112d("onTokenNotExistEvent token = " + token);
        if (!TextUtils.isEmpty(token)) {
            isTokenExpire = true;
            ToastUtil.showShortToast(StringUtils.getString(C1624R.string.token_not_exist));
            BleManager.getInstance().disconnect();
            String lastAccount = SpManager.getLastAccount();
            SpManager.deleteAll();
            SpManager.setLastAccount(lastAccount);
            clearToken();
            Intent intent = new Intent(this, SignActivity.class);
            intent.setFlags(268435456);
            startActivity(intent);
            getActivityManager().finishActivity(((ActivityManager) getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getClassName());
        }
    }

    public void onTerminate() {
        super.onTerminate();
        EventBusUtil.unRegister(this);
    }
}
