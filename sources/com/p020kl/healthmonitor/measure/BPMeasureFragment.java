package com.p020kl.healthmonitor.measure;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.mintti.visionsdk.ble.BleManager;
import com.mintti.visionsdk.ble.bean.MeasureType;
import com.mintti.visionsdk.ble.callback.IBleWriteResponse;
import com.mintti.visionsdk.ble.callback.IBpResultListener;
import com.mintti.visionsdk.ble.callback.IRawBpDataCallback;
import com.p020kl.commonbase.bean.BPEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.BPTableManager;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UploadResult;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.LocaleUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.soundplay.SoundType;
import com.p020kl.commonbase.utils.soundplay.SoundUtil;
import com.p020kl.commonbase.views.CommonSelectDialog;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.guide.p023bp.BpGuideFragment;
import com.p020kl.healthmonitor.views.CustomResultProgressBar;
import com.p020kl.healthmonitor.views.MeasureItemView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import p028io.reactivex.functions.Consumer;

/* renamed from: com.kl.healthmonitor.measure.BPMeasureFragment */
public class BPMeasureFragment extends BaseMeasureFragment implements IBpResultListener, IRawBpDataCallback, Handler.Callback {
    public static final String BUNDLE_DIASTOLIC = "diastolic_result";
    public static final String BUNDLE_HEART_RATE = "heart_rate_result";
    public static final String BUNDLE_SYSTOLIC = "systolic_result";
    public static final int MSG_BP_ERROR = 3;
    public static final int MSG_BP_LEAD_ERROR = 2;
    public static final int MSG_BP_RESULT = 1;
    public static final int MSG_BP_RESULT_ERROR = 4;
    public static final int MSG_DECOMPRESSION = 6;
    public static final int MSG_PRESSURE = 7;
    public static final int MSG_PRESSURIZATION = 5;
    private Animator animator1;
    private Animator animator2;
    private Animator animator3;
    @BindView(3066)
    Button btMeasure;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private boolean isMeasureEnd = false;
    @BindView(3336)
    ImageView ivImg1;
    @BindView(3337)
    ImageView ivImg2;
    @BindView(3338)
    ImageView ivImg3;
    private final Handler mMainHandler = new Handler(Looper.getMainLooper(), this);
    private MessageDialog messageDialog;
    @BindView(3441)
    MeasureItemView mivDPItem;
    @BindView(3442)
    MeasureItemView mivHRItem;
    @BindView(3443)
    MeasureItemView mivSPItem;
    @BindView(3558)
    CustomResultProgressBar resultProgressBarDp;
    @BindView(3559)
    CustomResultProgressBar resultProgressBarSp;
    @BindView(3747)
    TextView tvItemDp;
    @BindView(3748)
    TextView tvItemSp;
    @BindView(3780)
    TextView tvMeasureProject;
    @BindView(3781)
    TextView tvMeasureResult;
    @BindView(3785)
    TextView tvMeasureValueTop;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public void onDecompressionData(short s) {
    }

    public void onPressurizationData(short s) {
    }

    private synchronized void setMeasureEnd(boolean z) {
        this.isMeasureEnd = z;
    }

    public static BPMeasureFragment newInstance() {
        Bundle bundle = new Bundle();
        BPMeasureFragment bPMeasureFragment = new BPMeasureFragment();
        bPMeasureFragment.setArguments(bundle);
        return bPMeasureFragment;
    }

    public static BPMeasureFragment newInstance(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(BaseMeasureFragment.IS_ROTHMAN_INDEX_MODE, z);
        BPMeasureFragment bPMeasureFragment = new BPMeasureFragment();
        bPMeasureFragment.setArguments(bundle);
        return bPMeasureFragment;
    }

    /* access modifiers changed from: protected */
    public void showGuideFragment() {
        if (SpManager.getBpGuideIsShow()) {
            this.guideFragment = new BpGuideFragment();
            this.guideFragment.show(getChildFragmentManager(), "bp_guide");
            SpManager.setBpGuideIsShow(false);
        }
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_bp_measure);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return StringUtils.getString(C1624R.string.blood_pressure);
    }

    public void onBindView(Bundle bundle, View view) {
        super.onBindView(bundle, view);
        this.isRothmanIndexMode = getArguments().getBoolean(BaseMeasureFragment.IS_ROTHMAN_INDEX_MODE, false);
        if (LocaleUtils.isZh(getContext())) {
            this.tvItemSp.setTextSize(15.0f);
            this.tvItemDp.setTextSize(15.0f);
        } else {
            this.tvItemSp.setTextSize(10.0f);
            this.tvItemDp.setTextSize(10.0f);
        }
        BleManager.getInstance().setBpResultListener(this);
        BleManager.getInstance().setRawBpDataCallback(this);
        this.tvMeasureProject.setVisibility(8);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.tvMeasureResult.getLayoutParams();
        layoutParams.topMargin = 30;
        this.tvMeasureResult.setLayoutParams(layoutParams);
        this.mivHRItem.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public void startMeasure() {
        super.startMeasure();
        if (this.mHealthMonitorService != null) {
            this.mHealthMonitorService.startMeasure(MeasureType.TYPE_BP, new IBleWriteResponse() {
                public void onWriteFailed() {
                }

                public void onWriteSuccess() {
                }
            });
        }
        this.tvMeasureResult.setText(C1624R.string.testing);
    }

    /* access modifiers changed from: protected */
    public void stopMeasure() {
        super.stopMeasure();
        if (this.mHealthMonitorService != null) {
            this.mHealthMonitorService.stopMeasure(MeasureType.TYPE_BP, new IBleWriteResponse() {
                public void onWriteFailed() {
                }

                public void onWriteSuccess() {
                }
            });
        }
    }

    public void onBpResult(int i, int i2, int i3) {
        setMeasureEnd(true);
        this.mMainHandler.removeMessages(5);
        this.mMainHandler.removeMessages(6);
        Bundle bundle = new Bundle();
        Message obtainMessage = this.mMainHandler.obtainMessage(1);
        bundle.putInt(BUNDLE_SYSTOLIC, i);
        bundle.putInt(BUNDLE_DIASTOLIC, i2);
        bundle.putInt(BUNDLE_HEART_RATE, i3);
        obtainMessage.setData(bundle);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    private void handleBpResult(int i, int i2, int i3) {
        if (this.isViewBind) {
            this.mivSPItem.setMeasureValue(String.valueOf(i));
            this.mivDPItem.setMeasureValue(String.valueOf(i2));
            this.tvMeasureValueTop.setText("0");
            this.mivHRItem.setMeasureValue(String.valueOf(i3));
            this.btMeasure.setText(C1624R.string.start_measure);
            float f = ((float) i) / 200.0f;
            float f2 = 1.0f;
            if (f > 1.0f) {
                f = 1.0f;
            }
            this.resultProgressBarSp.setProgressWeight(f);
            float f3 = ((float) i2) / 130.0f;
            if (f3 <= 1.0f) {
                f2 = f3;
            }
            this.resultProgressBarDp.setProgressWeight(f2);
            stopAnimation();
            if (i > 140 || i2 > 90) {
                this.tvMeasureResult.setText(C1624R.string.hypertension);
                playWarm();
            } else if (i < 90 || i2 < 60) {
                this.tvMeasureResult.setText(C1624R.string.hypotension);
                playWarm();
            } else {
                this.tvMeasureResult.setText(C1624R.string.normal);
            }
            SoundUtil.getInstance(this.mActivity).play(new String[]{String.valueOf(i), String.valueOf(i2)}, SoundType.f848BP);
            addBpToDb(i, i2, i3);
            if (this.isRothmanIndexMode) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BUNDLE_SYSTOLIC_PRESSURE, i);
                bundle.putInt(Constants.BUNDLE_DIASTOLIC_PRESSURE, i2);
                setFragmentResult(-1, bundle);
                pop();
            }
        }
    }

    public void onLeadError() {
        Log.e("BPMeasureFragment", "onLeadError: ");
        setMeasureEnd(true);
        this.mMainHandler.sendEmptyMessage(2);
    }

    private void handleLeadError() {
        if (this.isViewBind) {
            stopAnimation();
            reset();
            this.btMeasure.setText(C1624R.string.start_measure);
            MessageDialog messageDialog2 = this.messageDialog;
            if (messageDialog2 != null && messageDialog2.isShow()) {
                this.messageDialog.dismiss();
            }
            this.messageDialog = MessageDialog.show((CharSequence) getString(C1624R.string.selector_hint), (CharSequence) getString(C1624R.string.leak_and_check), (CharSequence) getString(C1624R.string.selector_confirm));
        }
    }

    public void onBpError() {
        setMeasureEnd(true);
        Log.e("BPMeasureFragment", "onBpError: ");
        this.mMainHandler.sendEmptyMessage(3);
    }

    private void handleBpError() {
        if (this.isViewBind) {
            stopAnimation();
            reset();
            this.btMeasure.setText(C1624R.string.start_measure);
            MessageDialog messageDialog2 = this.messageDialog;
            if (messageDialog2 != null && messageDialog2.isShow()) {
                this.messageDialog.dismiss();
            }
            this.messageDialog = MessageDialog.show((CharSequence) getString(C1624R.string.selector_hint), (CharSequence) getString(C1624R.string.measurement_void), (CharSequence) getString(C1624R.string.selector_confirm));
        }
    }

    private void handleBpResultError() {
        if (this.isViewBind) {
            MessageDialog messageDialog2 = this.messageDialog;
            if (messageDialog2 != null && messageDialog2.isShow()) {
                this.messageDialog.dismiss();
            }
            this.messageDialog = MessageDialog.show((CharSequence) getString(C1624R.string.selector_hint), (CharSequence) getString(C1624R.string.measurement_void), (CharSequence) getString(C1624R.string.selector_confirm));
        }
    }

    private void addBpToDb(final int i, final int i2, final int i3) {
        this.executorService.submit(new Runnable() {
            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                final BPEntity bPEntity = new BPEntity();
                bPEntity.setUserId(SpManager.getMemberId());
                bPEntity.setSystolicPressure(i);
                bPEntity.setDiastolicPressure(i2);
                bPEntity.setHeartRate(i3);
                bPEntity.setCreateTime(currentTimeMillis);
                bPEntity.setModifyTime(currentTimeMillis);
                bPEntity.setYear(DateUtils.getYear(currentTimeMillis));
                bPEntity.setMonth(DateUtils.getMonth(currentTimeMillis));
                bPEntity.setDay(DateUtils.getDay(currentTimeMillis));
                BPTableManager.insertEntity(bPEntity);
                EventBusUtil.sendStickyEvent(new Event(bPEntity));
                RestClient.uploadMeasureData(bPEntity).subscribe(new Consumer<ResponseResult<UploadResult>>() {
                    public void accept(ResponseResult<UploadResult> responseResult) throws Exception {
                        if (responseResult.getStatus() == 200) {
                            bPEntity.setDocId(responseResult.getData().getDocId());
                            BPTableManager.updateEntity(bPEntity);
                        }
                    }
                }, new Consumer<Throwable>() {
                    public void accept(Throwable th) throws Exception {
                    }
                });
            }
        });
    }

    @OnClick({3066})
    public void onClick() {
        if (this.mHealthMonitorService == null || !this.mHealthMonitorService.isConnected()) {
            showHint(StringUtils.getString(C1624R.string.connect_firist));
        } else if (this.btMeasure.getText().equals(StringUtils.getString(C1624R.string.start_measure))) {
            reset();
            startAnimation();
            startMeasure();
            SoundUtil.getInstance(requireContext()).stop();
            this.btMeasure.setText(C1624R.string.stop_measure);
        } else {
            stopAnimation();
            stopMeasure();
            this.btMeasure.setText(C1624R.string.start_measure);
            this.tvMeasureResult.setText(C1624R.string.waiting_for_test);
        }
    }

    private void reset() {
        this.mivDPItem.setMeasureValue("0");
        this.mivSPItem.setMeasureValue("0");
        this.mivHRItem.setMeasureValue("0");
        this.tvMeasureValueTop.setText("0");
        this.tvMeasureResult.setText(C1624R.string.normal);
        this.resultProgressBarSp.clearProgress();
        this.resultProgressBarDp.clearProgress();
        this.tvMeasureResult.setText(C1624R.string.waiting_for_test);
        setMeasureEnd(false);
    }

    private void startAnimation() {
        this.animator1 = AnimatorInflater.loadAnimator(getContext(), C1624R.animator.rotation_animal_anticlockwise);
        this.animator2 = AnimatorInflater.loadAnimator(getContext(), C1624R.animator.rotation_animal_clockwise);
        this.animator3 = AnimatorInflater.loadAnimator(getContext(), C1624R.animator.rotation_animal_clockwise);
        this.animator1.setTarget(this.ivImg1);
        this.animator2.setTarget(this.ivImg2);
        this.animator3.setTarget(this.ivImg3);
        this.animator1.start();
        this.animator2.start();
        this.animator3.start();
    }

    private void stopAnimation() {
        this.animator1.cancel();
        this.animator2.cancel();
        this.animator3.cancel();
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.mHealthMonitorService.isMeasuring()) {
            stopMeasure();
        }
        SoundUtil.getInstance(requireContext()).stop();
    }

    /* access modifiers changed from: protected */
    public void backClick() {
        onBackClicked();
    }

    private void onBackClicked() {
        if (this.mHealthMonitorService.isMeasuring() || !this.btMeasure.getText().equals(getString(C1624R.string.start_measure))) {
            new CommonSelectDialog.Builder(getActivity()).setWidth((int) getResources().getDimension(C1624R.dimen.dp_251)).setHeight((int) getResources().getDimension(C1624R.dimen.dp_101)).setTitle(getString(C1624R.string.end_measure)).setOnClickListener(new CommonSelectDialog.OnClickListener() {
                public void onClick(CommonSelectDialog commonSelectDialog, boolean z) {
                    if (z) {
                        BPMeasureFragment.this.tvMeasureResult.setText(C1624R.string.waiting_for_test);
                        BPMeasureFragment.this.stopMeasure();
                        BPMeasureFragment.this.pop();
                    }
                    commonSelectDialog.dismiss();
                }
            }).build().show();
        } else {
            pop();
        }
    }

    public void onPressure(short s) {
        if (!this.isMeasureEnd) {
            Message obtainMessage = this.mMainHandler.obtainMessage(7);
            obtainMessage.arg1 = s;
            this.mMainHandler.sendMessage(obtainMessage);
        }
    }

    public boolean handleMessage(Message message) {
        TextView textView;
        if (!this.isViewBind) {
            return false;
        }
        int i = message.what;
        if (i == 1) {
            Bundle data = message.getData();
            handleBpResult(data.getInt(BUNDLE_SYSTOLIC), data.getInt(BUNDLE_DIASTOLIC), data.getInt(BUNDLE_HEART_RATE));
            return true;
        } else if (i == 2) {
            handleLeadError();
            return true;
        } else if (i == 3) {
            handleBpError();
            return true;
        } else if (i == 4) {
            handleBpResultError();
            return true;
        } else if (i != 7) {
            return false;
        } else {
            if (!this.isMeasureEnd && (textView = this.tvMeasureValueTop) != null) {
                textView.setText(String.valueOf(message.arg1));
            }
            return true;
        }
    }
}
