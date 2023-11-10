package com.p020kl.healthmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.p020kl.commonbase.base.BaseActivity;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.utils.TimerUtils;
import com.p020kl.healthmonitor.sign.SignActivity;
import com.p020kl.healthmonitor.views.DisclaimerDialog;
import java.util.concurrent.TimeUnit;
import p028io.reactivex.functions.Consumer;

/* renamed from: com.kl.healthmonitor.SplashActivity */
public class SplashActivity extends BaseActivity {
    private DisclaimerDialog disclaimerDialog;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1624R.layout.activity_splash);
        DisclaimerDialog disclaimerDialog2 = new DisclaimerDialog(this);
        this.disclaimerDialog = disclaimerDialog2;
        disclaimerDialog2.setCancelClickListener(new DisclaimerDialog.ICancelClickListener() {
            public void onCancelClick() {
                SplashActivity.this.startCountDown();
            }
        });
        if (!SpManager.getIsReadDisclaimer()) {
            this.disclaimerDialog.show();
        } else {
            startCountDown();
        }
    }

    /* access modifiers changed from: private */
    public void startCountDown() {
        TimerUtils.delayed(1000, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            public void accept(Long l) throws Exception {
                if (TextUtils.isEmpty(SpManager.getUserId())) {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, SignActivity.class));
                } else {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                SplashActivity.this.finish();
            }
        });
    }
}
