package com.p020kl.healthmonitor.measure;

import android.content.Context;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.event.BleStatusEvent;
import com.p020kl.commonbase.service.HealthMonitorService;
import com.p020kl.commonbase.utils.LoggerUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.TimerUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.utils.WarnSoundUtil;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.MainActivity;
import com.p020kl.healthmonitor.guide.BaseGuideFragment;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.functions.Consumer;

/* renamed from: com.kl.healthmonitor.measure.BaseMeasureFragment */
public abstract class BaseMeasureFragment extends BaseFragmentWhiteToolbar implements SoundPool.OnLoadCompleteListener {
    public static final String IS_ROTHMAN_INDEX_MODE = "isRothmanIndexMode";
    Disposable disposable = null;
    protected BaseGuideFragment guideFragment;
    protected boolean isReady;
    protected boolean isRothmanIndexMode = false;
    protected HealthMonitorService mHealthMonitorService;
    private MainActivity mMainActivity;
    protected SoundPool mSoundPool;
    protected int streamID;

    /* access modifiers changed from: protected */
    public boolean isEventBusRegister() {
        return true;
    }

    /* access modifiers changed from: protected */
    public abstract void showGuideFragment();

    public void onAttach(Context context) {
        super.onAttach(context);
        if (SpManager.getIsWarn()) {
            SoundPool singleSoundPool = WarnSoundUtil.getSingleSoundPool();
            this.mSoundPool = singleSoundPool;
            singleSoundPool.setOnLoadCompleteListener(this);
            this.streamID = this.mSoundPool.load(getContext(), C1624R.raw.warn, 1);
        }
        this.mMainActivity = (MainActivity) context;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        showGuideFragment();
    }

    /* access modifiers changed from: protected */
    public void startMeasure() {
        if (this.mHealthMonitorService == null) {
            ToastUtil.showToast(requireContext(), (int) C1624R.string.service_loading);
        }
    }

    /* access modifiers changed from: protected */
    public void stopMeasure() {
        if (this.mHealthMonitorService == null) {
            ToastUtil.showToast(requireContext(), (int) C1624R.string.service_loading);
        }
    }

    public void onBindView(Bundle bundle, View view) {
        if (BaseApplication.isBindService) {
            Log.e("huhu", "onBindView: 已绑定");
            this.mHealthMonitorService = HealthMonitorService.getInstance();
            return;
        }
        Log.e("huhu", "onBindView: wei绑定");
        showProgressDialog(StringUtils.getString(C1624R.string.service_loading), false);
        this.disposable = TimerUtils.intervalTime(100, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            public void accept(Long l) throws Exception {
                if (BaseApplication.isBindService) {
                    LoggerUtil.m112d("服务绑定成功");
                    BaseMeasureFragment.this.disProgressDialog();
                    BaseMeasureFragment.this.mHealthMonitorService = HealthMonitorService.getInstance();
                    if (BaseMeasureFragment.this.disposable != null && !BaseMeasureFragment.this.disposable.isDisposed()) {
                        BaseMeasureFragment.this.disposable.dispose();
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBleStatusEvent(BleStatusEvent bleStatusEvent) {
        if (((Integer) bleStatusEvent.getData()).intValue() == 101) {
            pop();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        SoundPool soundPool = this.mSoundPool;
        if (soundPool != null) {
            soundPool.release();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Disposable disposable2 = this.disposable;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.disposable.dispose();
        }
    }

    /* access modifiers changed from: protected */
    public void playWarm() {
        if (this.isReady) {
            Log.d("bofang", "播放报警声");
            this.mSoundPool.play(this.streamID, 1.0f, 1.0f, 1, 1, 2.0f);
            return;
        }
        Log.d("bofang", "资源未准备好");
    }

    public void onLoadComplete(SoundPool soundPool, int i, int i2) {
        this.isReady = true;
    }
}
