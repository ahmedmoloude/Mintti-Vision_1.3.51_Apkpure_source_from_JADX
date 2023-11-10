package com.p020kl.healthmonitor.base.dfu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.p020kl.healthmonitor.MainActivity;

/* renamed from: com.kl.healthmonitor.base.dfu.NotificationActivity */
public class NotificationActivity extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isTaskRoot()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(268435456);
            intent.putExtras(getIntent().getExtras());
            startActivity(intent);
        }
        finish();
    }
}
