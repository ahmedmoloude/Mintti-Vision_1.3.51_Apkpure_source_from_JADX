package com.p020kl.healthmonitor.measure;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.mintti.visionsdk.ble.BleManager;
import com.mintti.visionsdk.ble.bean.MeasureType;
import com.mintti.visionsdk.ble.callback.IBleWriteResponse;
import com.mintti.visionsdk.ble.callback.IBtResultListener;
import com.p020kl.commonbase.bean.BTEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.BTTableManager;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UploadResult;
import com.p020kl.commonbase.utils.AnimalUtil;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.TemperatureUtils;
import com.p020kl.commonbase.utils.soundplay.SoundType;
import com.p020kl.commonbase.utils.soundplay.SoundUtil;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.guide.p024bt.BtGuideFragment;
import com.p020kl.healthmonitor.views.CustomResultProgressBar;
import com.p020kl.healthmonitor.views.MeasureTopItemView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import p028io.reactivex.Observable;
import p028io.reactivex.ObservableEmitter;
import p028io.reactivex.ObservableOnSubscribe;
import p028io.reactivex.functions.Consumer;

/* renamed from: com.kl.healthmonitor.measure.BTMeasureFragment */
public class BTMeasureFragment extends BaseMeasureFragment {
    private static final String BUNDLE_BT_RESULT = "bundle_bt_result";
    private static final int MSG_BT_RESULT = 1;
    private static final int MSG_GET_BT_RESULT_TIMEOUT = 2;
    @BindView(3086)
    Button btMeasure;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public final Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1 && BTMeasureFragment.this.btMeasure != null) {
                double d = message.getData().getDouble(BTMeasureFragment.BUNDLE_BT_RESULT);
                if (d < 32.0d || d > 42.0d) {
                    BTMeasureFragment.this.btMeasure.setEnabled(true);
                    AnimalUtil.stopRotateAnimal(BTMeasureFragment.this.requireActivity());
                    MessageDialog.show((int) C1624R.string.selector_hint, (int) C1624R.string.abnormal_bt, (int) C1624R.string.picker_ok);
                    BTMeasureFragment.this.topItemView.setMeasureResult(StringUtils.getString(C1624R.string.waiting_for_test));
                    return;
                }
                BTMeasureFragment.this.handleBtResult(d);
            } else if (message.what == 2) {
                BTMeasureFragment.this.btMeasure.setEnabled(true);
                AnimalUtil.stopRotateAnimal(BTMeasureFragment.this.requireActivity());
                MessageDialog.show((int) C1624R.string.selector_hint, (int) C1624R.string.abnormal_bt, (int) C1624R.string.picker_ok);
                BTMeasureFragment.this.topItemView.setMeasureResult(StringUtils.getString(C1624R.string.waiting_for_test));
            }
        }
    };
    @BindView(2131296661)
    ImageView ivImg1;
    @BindView(2131296662)
    ImageView ivImg2;
    @BindView(2131296663)
    ImageView ivImg3;
    private final IBtResultListener minttiVisionBtResultListener = new IBtResultListener() {
        public void onBtResult(double d) {
            Message obtainMessage = BTMeasureFragment.this.handler.obtainMessage(1);
            Bundle bundle = new Bundle();
            bundle.putDouble(BTMeasureFragment.BUNDLE_BT_RESULT, ((double) Math.round(d * 10.0d)) / 10.0d);
            obtainMessage.setData(bundle);
            BTMeasureFragment.this.handler.sendMessage(obtainMessage);
        }
    };
    @BindView(3159)
    CustomResultProgressBar progressBar;
    @BindView(3471)
    MeasureTopItemView topItemView;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public static BTMeasureFragment newInstance() {
        Bundle bundle = new Bundle();
        BTMeasureFragment bTMeasureFragment = new BTMeasureFragment();
        bTMeasureFragment.setArguments(bundle);
        return bTMeasureFragment;
    }

    public static BTMeasureFragment newInstance(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(BaseMeasureFragment.IS_ROTHMAN_INDEX_MODE, z);
        BTMeasureFragment bTMeasureFragment = new BTMeasureFragment();
        bTMeasureFragment.setArguments(bundle);
        return bTMeasureFragment;
    }

    /* access modifiers changed from: protected */
    public void showGuideFragment() {
        if (SpManager.getBtGuideIsShow()) {
            this.guideFragment = new BtGuideFragment();
            this.guideFragment.show(getChildFragmentManager(), "bt_guide");
            SpManager.setBtGuideIsShow(false);
        }
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_bt_measure);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return StringUtils.getString(C1624R.string.body_temperature);
    }

    public void onBindView(Bundle bundle, View view) {
        super.onBindView(bundle, view);
        this.isRothmanIndexMode = getArguments().getBoolean(BaseMeasureFragment.IS_ROTHMAN_INDEX_MODE, false);
        if (SpManager.getTemperaTrueUnit() == 0) {
            this.topItemView.setMeasureUnit(StringUtils.getString(C1624R.string.centigrade_unit));
        } else {
            this.topItemView.setMeasureUnit(StringUtils.getString(C1624R.string.fahrenheit_unit));
        }
        this.progressBar.setLeftText(TemperatureUtils.tempeTypeConversionStrD(36.0d));
        this.progressBar.setRightText(TemperatureUtils.tempeTypeConversionStrD(37.1d));
        BleManager.getInstance().setBtResultListener(this.minttiVisionBtResultListener);
    }

    public void startMeasure() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            public void subscribe(ObservableEmitter<Object> observableEmitter) throws Exception {
            }
        });
        super.startMeasure();
        if (this.mHealthMonitorService != null) {
            this.mHealthMonitorService.startMeasure(MeasureType.TYPE_BT, new IBleWriteResponse() {
                public void onWriteFailed() {
                }

                public void onWriteSuccess() {
                }
            });
        }
        this.topItemView.setMeasureResult(StringUtils.getString(C1624R.string.testing));
    }

    private void resetMiv() {
        this.topItemView.setMeasureValueText("0");
        this.topItemView.setMeasureResult(StringUtils.getString(C1624R.string.normal));
        this.progressBar.clearProgress();
    }

    @OnClick({3086})
    public void onClick() {
        if (this.mHealthMonitorService == null || !this.mHealthMonitorService.isConnected()) {
            showHint(StringUtils.getString(C1624R.string.connect_firist));
            return;
        }
        resetMiv();
        startMeasure();
        this.btMeasure.setEnabled(false);
        AnimalUtil.showRotateAnimal(getContext(), this.ivImg1, C1624R.animator.rotation_animal_anticlockwise);
        AnimalUtil.showRotateAnimal(getContext(), this.ivImg2, C1624R.animator.rotation_animal_clockwise);
        AnimalUtil.showRotateAnimal(getContext(), this.ivImg3, C1624R.animator.rotation_animal_clockwise);
        SoundUtil.getInstance(requireContext()).stop();
    }

    /* access modifiers changed from: private */
    public void handleBtResult(double d) {
        if (this.isViewBind) {
            this.btMeasure.setEnabled(true);
            AnimalUtil.stopRotateAnimal(requireActivity());
            this.topItemView.setMeasureValueText(String.valueOf(TemperatureUtils.tempeTypeConversionDouble(d)));
            if (d > 37.1d && d <= 38.0d) {
                this.topItemView.setMeasureResult(StringUtils.getString(C1624R.string.low_fever));
                this.progressBar.setProgressWeight(0.77f);
                playWarm();
            } else if (d > 38.0d && d <= 39.0d) {
                this.topItemView.setMeasureResult(StringUtils.getString(C1624R.string.middle_fever));
                this.progressBar.setProgressWeight(0.88f);
                playWarm();
            } else if (d > 39.0d) {
                this.topItemView.setMeasureResult(StringUtils.getString(C1624R.string.high_fever));
                this.progressBar.setProgressWeight(0.99f);
                playWarm();
            } else if (d >= 36.0d && d <= 37.1d) {
                this.topItemView.setMeasureResult(StringUtils.getString(C1624R.string.normal));
                this.progressBar.setProgressWeight((float) (((d - 36.0d) * 0.33d) + 0.33d));
            } else if (d > 35.0d && d < 36.0d) {
                this.topItemView.setMeasureResult(StringUtils.getString(C1624R.string.flat));
                this.progressBar.setProgressWeight(0.28f);
            } else if (d == 0.0d) {
                this.topItemView.setMeasureResult(StringUtils.getString(C1624R.string.abnormal));
            } else {
                this.topItemView.setMeasureResult(StringUtils.getString(C1624R.string.too_low));
                this.progressBar.setProgressWeight(0.01f);
            }
            SoundUtil.getInstance(this.mActivity).play(new String[]{String.valueOf(TemperatureUtils.tempeTypeConversionDouble(d))}, SoundType.f849BT);
            addBtToDb(d);
            if (this.isRothmanIndexMode) {
                Bundle bundle = new Bundle();
                bundle.putDouble(Constants.BUNDLE_BT, d);
                setFragmentResult(-1, bundle);
                pop();
            }
        }
    }

    private void addBtToDb(final double d) {
        this.executorService.submit(new Runnable() {
            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                final BTEntity bTEntity = new BTEntity();
                bTEntity.setUserId(SpManager.getMemberId());
                bTEntity.setTemperature(d);
                bTEntity.setCreateTime(currentTimeMillis);
                bTEntity.setModifyTime(currentTimeMillis);
                bTEntity.setYear(DateUtils.getYear(currentTimeMillis));
                bTEntity.setMonth(DateUtils.getMonth(currentTimeMillis));
                bTEntity.setDay(DateUtils.getDay(currentTimeMillis));
                BTTableManager.insertEntity(bTEntity);
                RestClient.uploadMeasureData(bTEntity).subscribe(new Consumer<ResponseResult<UploadResult>>() {
                    public void accept(ResponseResult<UploadResult> responseResult) throws Exception {
                        if (responseResult.getStatus() == 200) {
                            Log.d("uploadData", "上传数据成功");
                            bTEntity.setDocId(responseResult.getData().getDocId());
                            BTTableManager.updateEntity(bTEntity);
                        }
                    }
                }, new Consumer<Throwable>() {
                    public void accept(Throwable th) throws Exception {
                        Log.d("uploadData", "上传数据失败");
                    }
                });
                EventBusUtil.sendStickyEvent(new Event(bTEntity));
            }
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d("BTMeasureFragment", "onDestroyView");
        SoundUtil.getInstance(requireContext()).stop();
    }

    public void onDestroy() {
        super.onDestroy();
        AnimalUtil.stopRotateAnimal(requireActivity());
    }
}
