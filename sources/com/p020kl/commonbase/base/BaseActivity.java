package com.p020kl.commonbase.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.gyf.immersionbar.ImmersionBar;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.utils.EventBusUtil;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p035me.yokeyword.fragmentation.SupportActivity;

/* renamed from: com.kl.commonbase.base.BaseActivity */
public class BaseActivity extends SupportActivity {
    protected String TAG = getClass().getSimpleName();
    private Unbinder unbinder = null;

    /* access modifiers changed from: protected */
    public boolean isEventBusRegister() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Event event) {
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        BaseApplication.getInstance().getActivityManager().addActivity(this);
        this.unbinder = ButterKnife.bind((Activity) this);
        if (isEventBusRegister() && !EventBusUtil.isRegister(this)) {
            EventBusUtil.register(this);
        }
        ImmersionBar.with((Activity) this).init();
    }

    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        Unbinder unbinder2 = this.unbinder;
        if (unbinder2 != null) {
            unbinder2.unbind();
        }
        if (isEventBusRegister()) {
            EventBusUtil.unRegister(this);
        }
        BaseApplication.getInstance().getActivityManager().removeActivity(this);
    }

    public Resources getResources() {
        Configuration configuration;
        Resources resources = super.getResources();
        if (!(resources == null || (configuration = resources.getConfiguration()) == null || configuration.fontScale == 1.0f)) {
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }
}
