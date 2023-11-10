package com.p020kl.healthmonitor.measure;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.OnClick;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.linktop.constant.TestPaper;
import com.mintti.visionsdk.ble.BleManager;
import com.mintti.visionsdk.ble.DeviceType;
import com.mintti.visionsdk.ble.bean.BgEvent;
import com.mintti.visionsdk.ble.bean.MeasureType;
import com.mintti.visionsdk.ble.callback.IBgResultListener;
import com.mintti.visionsdk.ble.callback.IBleWriteResponse;
import com.mintti.visionsdk.common.LogUtils;
import com.p020kl.commonbase.bean.BGEntity;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.BGTableManager;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UploadResult;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.TimerUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.utils.UnitUtil;
import com.p020kl.commonbase.utils.soundplay.SoundType;
import com.p020kl.commonbase.utils.soundplay.SoundUtil;
import com.p020kl.commonbase.views.PickerScrollView;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.guide.p022bg.BgGuideFragment;
import com.p020kl.healthmonitor.views.CircleBarView;
import com.p020kl.healthmonitor.views.CustomResultProgressBar;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import p028io.reactivex.Observer;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.functions.Consumer;

/* renamed from: com.kl.healthmonitor.measure.BGMeasureFragment */
public class BGMeasureFragment extends BaseMeasureFragment implements RadioGroup.OnCheckedChangeListener, IBgResultListener {
    private static final int INIT_BG = 5;
    private static final int STEP_CALIBRATION = 0;
    private static final int STEP_PAGER_IN = 4;
    private static final int STEP_SELECT_TIME = 2;
    private static final int STEP_START = 1;
    private static final int STEP_STRIP_CODE = 3;
    private MessageDialog bgInitFailedDialog;
    private MessageDialog bgTimeoutDialog;
    @BindView(3083)
    Button btnMeasure;
    @BindView(3132)
    CircleBarView circleBarView;
    /* access modifiers changed from: private */
    public Disposable countDownDisposable = null;
    /* access modifiers changed from: private */
    public int curStep;
    private DeviceType deviceType = null;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    /* access modifiers changed from: private */
    public final MyHandler handler = new MyHandler(this);
    /* access modifiers changed from: private */
    public int initTime;
    @BindView(3334)
    ImageView ivMeasure;
    @BindView(3392)
    LinearLayout llFirstPage;
    @BindView(3393)
    LinearLayout llMeasuring;
    @BindView(3400)
    LinearLayout llSelectMeasureTime;
    @BindView(3402)
    LinearLayout llSelectStrip;
    protected String mManufacturer;
    protected String[] mTestPaperCodes;
    /* access modifiers changed from: private */
    public int measureTime = 0;
    @BindView(3538)
    PickerScrollView psvStripCode;
    @BindView(3557)
    CustomResultProgressBar resultProgressBar;
    /* access modifiers changed from: private */
    public int retryInitCount = 0;
    @BindView(3561)
    RadioGroup rgMeasureTime;
    @BindView(3636)
    AppCompatSpinner spinTestPaperManufacturer;
    /* access modifiers changed from: private */
    public String stripCode = TestPaper.Code.C20;
    @BindView(3727)
    TextView tvBgCountDown;
    @BindView(3728)
    TextView tvBgUnit;
    @BindView(3729)
    TextView tvBgValue;
    @BindView(3725)
    TextView tvMeasureHint;
    @BindView(3782)
    TextView tvMeasureTime;
    @BindView(3726)
    TextView tvResult;
    @BindView(3811)
    TextView tvStripCode;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    static /* synthetic */ int access$008(BGMeasureFragment bGMeasureFragment) {
        int i = bGMeasureFragment.initTime;
        bGMeasureFragment.initTime = i + 1;
        return i;
    }

    static /* synthetic */ int access$208(BGMeasureFragment bGMeasureFragment) {
        int i = bGMeasureFragment.retryInitCount;
        bGMeasureFragment.retryInitCount = i + 1;
        return i;
    }

    /* renamed from: com.kl.healthmonitor.measure.BGMeasureFragment$MyHandler */
    private class MyHandler extends Handler {
        private WeakReference<BGMeasureFragment> mFragment;

        public MyHandler(BGMeasureFragment bGMeasureFragment) {
            this.mFragment = new WeakReference<>(bGMeasureFragment);
        }

        public void handleMessage(Message message) {
            BGMeasureFragment bGMeasureFragment = (BGMeasureFragment) this.mFragment.get();
            if (bGMeasureFragment != null && message.what == 5) {
                BGMeasureFragment.access$008(bGMeasureFragment);
                if (bGMeasureFragment.initTime > 10) {
                    BGMeasureFragment.this.removeInitCuntDown();
                    BGMeasureFragment.access$208(BGMeasureFragment.this);
                    if (BGMeasureFragment.this.retryInitCount < 3) {
                        bGMeasureFragment.handleMinttiBg();
                        return;
                    }
                    int unused = BGMeasureFragment.this.retryInitCount = 0;
                    BGMeasureFragment.this.onBgEvent(BgEvent.BG_EVENT_CALIBRATION_FAILED);
                    return;
                }
                bGMeasureFragment.handler.sendEmptyMessageDelayed(5, 1000);
            }
        }
    }

    public static BGMeasureFragment newInstance() {
        Bundle bundle = new Bundle();
        BGMeasureFragment bGMeasureFragment = new BGMeasureFragment();
        bGMeasureFragment.setArguments(bundle);
        return bGMeasureFragment;
    }

    /* access modifiers changed from: protected */
    public void showGuideFragment() {
        if (SpManager.getBgGuideIsShow()) {
            this.guideFragment = new BgGuideFragment();
            this.guideFragment.show(getChildFragmentManager(), "bg_guide");
            SpManager.setBgGuideIsShow(false);
        }
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_bg_measure);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.blood_glucose);
    }

    public void onBindView(Bundle bundle, View view) {
        super.onBindView(bundle, view);
        this.curStep = 1;
        if (this.mHealthMonitorService != null) {
            this.deviceType = this.mHealthMonitorService.getDeviceType();
            BleManager.getInstance().setBgResultListener(this);
        }
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        super.initView(view);
        this.rgMeasureTime.setOnCheckedChangeListener(this);
        if (SpManager.getBgUnit() != 0) {
            this.tvBgUnit.setText("mg/dl");
            this.resultProgressBar.setLeftText(UnitUtil.getBgValue(3.9d) + "");
            this.resultProgressBar.setRightText(UnitUtil.getBgValue(6.1d) + "");
        }
        String[] stringArray = getResources().getStringArray(C1624R.array.strip_code_list);
        ArrayList arrayList = new ArrayList();
        for (String add : stringArray) {
            arrayList.add(add);
        }
        this.circleBarView.setMaxProgress(12.0f);
        resetView();
        if (this.mHealthMonitorService.getDeviceType() == DeviceType.TYPE_VISION) {
            initTestPaper();
        }
    }

    private void initTestPaper() {
        final String[] testPaperManufacturer = BleManager.getInstance().getTestPaperManufacturer();
        this.spinTestPaperManufacturer.setAdapter((SpinnerAdapter) new ArrayAdapter(getContext(), 17367049, getManufacturerList(testPaperManufacturer)));
        this.spinTestPaperManufacturer.setSelection(TestPaper.Manufacturer.indexOf(TestPaper.Manufacturer.YI_CHENG));
        this.spinTestPaperManufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                BGMeasureFragment.this.mManufacturer = testPaperManufacturer[i];
                BGMeasureFragment.this.mTestPaperCodes = BleManager.getInstance().getTestPaperCodesByManufacturer(BGMeasureFragment.this.mManufacturer);
                ArrayList arrayList = new ArrayList();
                for (String add : BGMeasureFragment.this.mTestPaperCodes) {
                    arrayList.add(add);
                }
                BGMeasureFragment.this.psvStripCode.setData(arrayList);
                if (TestPaper.Manufacturer.YI_CHENG.equals(BGMeasureFragment.this.mManufacturer)) {
                    BGMeasureFragment.this.psvStripCode.setSelected(TestPaper.Code.C20);
                } else if (TestPaper.Manufacturer.BENE_CHECK.equals(BGMeasureFragment.this.mManufacturer)) {
                    BGMeasureFragment.this.psvStripCode.setSelected(TestPaper.Code._9CC3);
                } else {
                    BGMeasureFragment.this.psvStripCode.setSelected(TestPaper.Code.CHECK3);
                }
            }
        });
        this.psvStripCode.setOnSelectListener(new PickerScrollView.onSelectListener() {
            public void onSelect(String str) {
                BGMeasureFragment.this.tvStripCode.setText(str);
                String unused = BGMeasureFragment.this.stripCode = str;
                BleManager.getInstance().setTestPaper(BGMeasureFragment.this.mManufacturer, BGMeasureFragment.this.stripCode);
                LogUtils.m378d("BGMeasureFragment", "psvStripCode onSelect = " + BGMeasureFragment.this.mManufacturer + " " + BGMeasureFragment.this.stripCode);
            }
        });
        this.mTestPaperCodes = BleManager.getInstance().getTestPaperCodesByManufacturer(TestPaper.Manufacturer.YI_CHENG);
        this.mManufacturer = TestPaper.Manufacturer.YI_CHENG;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            String[] strArr = this.mTestPaperCodes;
            if (i < strArr.length) {
                arrayList.add(strArr[i]);
                i++;
            } else {
                this.psvStripCode.setData(arrayList);
                this.psvStripCode.setSelected(TestPaper.Code.C20);
                BleManager.getInstance().setTestPaper(this.mManufacturer, this.stripCode);
                return;
            }
        }
    }

    private List<String> getManufacturerList(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            str.hashCode();
            char c = 65535;
            switch (str.hashCode()) {
                case -1585252925:
                    if (str.equals(TestPaper.Manufacturer.BENE_CHECK)) {
                        c = 0;
                        break;
                    }
                    break;
                case 71647:
                    if (str.equals(TestPaper.Manufacturer.HMD)) {
                        c = 1;
                        break;
                    }
                    break;
                case 1974747242:
                    if (str.equals(TestPaper.Manufacturer.YI_CHENG)) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    arrayList.add(getString(C1624R.string.manufacturer_bene_check));
                    break;
                case 1:
                    arrayList.add(getString(C1624R.string.manufacturer_hmd));
                    break;
                case 2:
                    arrayList.add(getString(C1624R.string.manufacturer_yi_cheng));
                    break;
            }
        }
        return arrayList;
    }

    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case C1624R.C1628id.rb_after:
                this.measureTime = 1;
                return;
            case C1624R.C1628id.rb_before:
                this.measureTime = 0;
                return;
            default:
                return;
        }
    }

    @OnClick({3083})
    public void onBgMeasureClicked() {
        if (this.mHealthMonitorService == null || !this.mHealthMonitorService.isConnected()) {
            showHint(StringUtils.getString(C1624R.string.connect_firist));
        } else if (this.deviceType == DeviceType.TYPE_VISION) {
            LogUtils.m379e("BGMeasureFragment", "onBgMeasureClicked:" + this.curStep);
            handleLinkTopBg();
        } else if (this.deviceType != DeviceType.TYPE_MINTTI_VISION) {
        } else {
            if (this.btnMeasure.getText().equals(getString(C1624R.string.cancel))) {
                this.curStep = 1;
                cancelResultCountDown();
                stopMeasure();
                this.tvResult.setText(C1624R.string.waiting_for_test);
                resetView();
                this.llMeasuring.setVisibility(8);
                this.llFirstPage.setVisibility(0);
                return;
            }
            handleMinttiBg();
        }
    }

    /* access modifiers changed from: protected */
    public void stopMeasure() {
        super.stopMeasure();
        if (this.mHealthMonitorService != null && this.mHealthMonitorService.isConnected()) {
            this.mHealthMonitorService.stopMeasure(MeasureType.TYPE_BG, new IBleWriteResponse() {
                public void onWriteFailed() {
                }

                public void onWriteSuccess() {
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void handleMinttiBg() {
        int i = this.curStep;
        if (i == 0) {
            this.handler.sendEmptyMessageDelayed(5, 1000);
            this.llSelectMeasureTime.setVisibility(8);
            this.llMeasuring.setVisibility(0);
            this.mHealthMonitorService.startMeasure(MeasureType.TYPE_BG, (IBleWriteResponse) null);
            this.btnMeasure.setText(C1624R.string.cancel);
            this.ivMeasure.setImageResource(C1624R.C1627drawable.img_test_strip);
            this.tvMeasureHint.setText(C1624R.string.wait_bg_calibration);
        } else if (i == 1) {
            removeInitCuntDown();
            this.curStep = 0;
            this.llFirstPage.setVisibility(8);
            this.llSelectMeasureTime.setVisibility(0);
            this.btnMeasure.setText(C1624R.string.next_step);
            this.tvBgValue.setText("0");
            this.circleBarView.setProgressNum(0, 0.0f, C1624R.C1626color.colorPrimary);
        }
    }

    /* access modifiers changed from: private */
    public void removeInitCuntDown() {
        this.initTime = 0;
        this.handler.removeMessages(5);
    }

    private void handleLinkTopBg() {
        LogUtils.m378d("BGMeasureFragment", "handleLinkTopBg = " + this.curStep);
        int i = this.curStep;
        if (i == 1) {
            this.curStep = 2;
            this.llFirstPage.setVisibility(8);
            this.llSelectMeasureTime.setVisibility(0);
            this.btnMeasure.setText(C1624R.string.next_step);
            this.tvBgValue.setText("0");
            this.circleBarView.setProgressNum(0, 0.0f, C1624R.C1626color.colorPrimary);
        } else if (i == 2) {
            this.curStep = 3;
            this.llSelectMeasureTime.setVisibility(8);
            this.llSelectStrip.setVisibility(0);
        } else if (i == 3) {
            this.curStep = 4;
            this.llSelectStrip.setVisibility(8);
            this.llMeasuring.setVisibility(0);
        } else if (i == 4) {
            if (this.btnMeasure.getText().equals(StringUtils.getString(C1624R.string.cancel))) {
                this.curStep = 1;
                stopMeasure();
                resetView();
                this.llMeasuring.setVisibility(8);
                this.llFirstPage.setVisibility(0);
                cancelResultCountDown();
                return;
            }
            startMeasure();
        }
    }

    /* access modifiers changed from: protected */
    public void startMeasure() {
        super.startMeasure();
        if (this.mHealthMonitorService != null) {
            this.mHealthMonitorService.startMeasure(MeasureType.TYPE_BG, new IBleWriteResponse() {
                public void onWriteFailed() {
                }

                public void onWriteSuccess() {
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void resetView() {
        this.btnMeasure.setText(C1624R.string.start_measure);
        this.ivMeasure.setImageResource(C1624R.C1627drawable.img_test_strip);
        this.tvMeasureHint.setText(C1624R.string.insert_strip);
        CustomResultProgressBar customResultProgressBar = this.resultProgressBar;
        customResultProgressBar.setLeftText(UnitUtil.getBgValue(3.9d) + "");
        CustomResultProgressBar customResultProgressBar2 = this.resultProgressBar;
        customResultProgressBar2.setRightText(UnitUtil.getBgValue(6.1d) + "");
        this.resultProgressBar.clearProgress();
        this.tvResult.setText(getString(C1624R.string.waiting_for_test));
        this.tvMeasureTime.setText("- -");
        this.tvBgCountDown.setVisibility(4);
    }

    private void showResult(double d) {
        double d2 = d;
        Log.e("BGMeasureFragment", "showResult: " + d2);
        double d3 = 12.0d;
        if (this.measureTime == 0) {
            this.tvMeasureTime.setText(C1624R.string.before_fasting);
            if (d2 >= 3.9d && d2 <= 6.1d) {
                this.tvResult.setText(C1624R.string.normal);
                this.resultProgressBar.setProgressWeight((float) ((((d2 - 3.9d) / 2.1999999999999997d) * 0.3330000042915344d) + 0.3330000042915344d));
                this.circleBarView.setProgressNum(500, (float) d2, C1624R.C1626color.colorPrimary);
            } else if (d2 < 3.9d) {
                this.tvResult.setText(C1624R.string.low);
                this.resultProgressBar.setProgressWeight((float) ((d2 / 3.9d) * 0.3330000042915344d));
                this.circleBarView.setProgressNum(500, (float) d2, C1624R.C1626color.yellowf8d253);
                playWarm();
            } else if (d2 > 6.1d) {
                this.tvResult.setText(C1624R.string.high);
                CustomResultProgressBar customResultProgressBar = this.resultProgressBar;
                int i = (d2 > 12.0d ? 1 : (d2 == 12.0d ? 0 : -1));
                if (i <= 0) {
                    d3 = d2;
                }
                customResultProgressBar.setProgressWeight((float) ((((d3 - 6.1d) / 5.9d) * 0.3330000042915344d) + 0.6660000085830688d));
                if (i > 0) {
                    this.circleBarView.setProgressNum(500, 12.0f, C1624R.C1626color.reded4b49);
                } else {
                    this.circleBarView.setProgressNum(500, (float) d2, C1624R.C1626color.reded4b49);
                }
                playWarm();
            }
        } else {
            CustomResultProgressBar customResultProgressBar2 = this.resultProgressBar;
            customResultProgressBar2.setLeftText(UnitUtil.getBgValue(3.9d) + "");
            CustomResultProgressBar customResultProgressBar3 = this.resultProgressBar;
            customResultProgressBar3.setRightText(UnitUtil.getBgValue(7.8d) + "");
            this.tvMeasureTime.setText(C1624R.string.two_hours_after_a_meal);
            if (d2 >= 3.9d && d2 <= 7.8d) {
                this.tvResult.setText(C1624R.string.normal);
                this.resultProgressBar.setProgressWeight((float) ((((d2 - 3.9d) / 3.9d) * 0.3330000042915344d) + 0.3330000042915344d));
                this.circleBarView.setProgressNum(500, (float) d2, C1624R.C1626color.colorPrimary);
            } else if (d2 < 3.9d) {
                this.tvResult.setText(C1624R.string.low);
                this.resultProgressBar.setProgressWeight((float) ((d2 / 3.9d) * 0.3330000042915344d));
                this.circleBarView.setProgressNum(500, (float) d2, C1624R.C1626color.yellowf8d253);
                playWarm();
            } else if (d2 > 7.8d) {
                this.tvResult.setText(C1624R.string.high);
                CustomResultProgressBar customResultProgressBar4 = this.resultProgressBar;
                double d4 = 12.0d;
                int i2 = (d2 > 12.0d ? 1 : (d2 == 12.0d ? 0 : -1));
                if (i2 <= 0) {
                    d4 = d2;
                }
                customResultProgressBar4.setProgressWeight((float) ((((d4 - 7.8d) / 4.2d) * 0.3330000042915344d) + 0.6669999957084656d));
                if (i2 > 0) {
                    this.circleBarView.setProgressNum(500, 12.0f, C1624R.C1626color.reded4b49);
                } else {
                    this.circleBarView.setProgressNum(500, (float) d2, C1624R.C1626color.reded4b49);
                }
                playWarm();
            }
        }
        this.tvBgValue.setText(String.valueOf(UnitUtil.getBgValue(d)));
        SoundUtil.getInstance(this.mActivity).play(new String[]{String.valueOf(UnitUtil.getBgValue(d)), String.valueOf(this.measureTime)}, SoundType.f847BG);
    }

    private void insertBgToDb(final double d) {
        if (d > 0.0d) {
            this.executorService.submit(new Runnable() {
                public void run() {
                    long currentTimeMillis = System.currentTimeMillis();
                    final BGEntity bGEntity = new BGEntity();
                    bGEntity.setUserId(SpManager.getMemberId());
                    bGEntity.setBloodGlucose(d);
                    bGEntity.setCreateTime(currentTimeMillis);
                    bGEntity.setMeasureTime(BGMeasureFragment.this.measureTime);
                    bGEntity.setModifyTime(currentTimeMillis);
                    bGEntity.setYear(DateUtils.getYear(currentTimeMillis));
                    bGEntity.setMonth(DateUtils.getMonth(currentTimeMillis));
                    bGEntity.setDay(DateUtils.getDay(currentTimeMillis));
                    bGEntity.setCorrectionCode(BGMeasureFragment.this.stripCode);
                    BGTableManager.insertEntity(bGEntity);
                    EventBusUtil.sendStickyEvent(new Event(bGEntity));
                    RestClient.uploadMeasureData(bGEntity).subscribe(new Consumer<ResponseResult<UploadResult>>() {
                        public void accept(ResponseResult<UploadResult> responseResult) throws Exception {
                            if (responseResult.getStatus() == 200) {
                                bGEntity.setDocId(responseResult.getData().getDocId());
                                BGTableManager.updateEntity(bGEntity);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        public void accept(Throwable th) throws Exception {
                            Log.d("bgdata", "血糖数据上传失败");
                        }
                    });
                }
            });
        }
    }

    public void onBgEvent(BgEvent bgEvent) {
        if (this.isViewBind) {
            requireActivity().runOnUiThread(new Runnable(bgEvent) {
                public final /* synthetic */ BgEvent f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    BGMeasureFragment.this.lambda$onBgEvent$1$BGMeasureFragment(this.f$1);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onBgEvent$1$BGMeasureFragment(BgEvent bgEvent) {
        LogUtils.m378d("BGMeasureFragment", "onBgEvent:" + bgEvent.name());
        cancelResultCountDown();
        removeInitCuntDown();
        this.tvBgCountDown.setVisibility(4);
        int i = C17078.$SwitchMap$com$mintti$visionsdk$ble$bean$BgEvent[bgEvent.ordinal()];
        if (i == 1) {
            Log.e("BGMeasureFragment", "calibrationBgFailed: ");
            this.retryInitCount = 0;
            showBgInitFailedDialog();
        } else if (i == 2) {
            this.retryInitCount = 0;
            this.ivMeasure.setImageResource(C1624R.C1627drawable.img_test_strip);
            this.tvMeasureHint.setText(C1624R.string.insert_strip);
        } else if (i == 3) {
            this.ivMeasure.setImageResource(C1624R.C1627drawable.img_bg_hand);
            this.tvMeasureHint.setText(C1624R.string.wait_blood_sample);
            this.btnMeasure.setText(C1624R.string.cancel);
        } else if (i == 4) {
            this.tvMeasureHint.setText(C1624R.string.blood_collected);
            startResultCountDown();
            this.tvBgCountDown.setVisibility(0);
        } else if (i == 6) {
            this.ivMeasure.setImageResource(C1624R.C1627drawable.img_strip_unusable);
            this.tvMeasureHint.setText(C1624R.string.strip_not_measured);
        }
    }

    /* renamed from: com.kl.healthmonitor.measure.BGMeasureFragment$8 */
    static /* synthetic */ class C17078 {
        static final /* synthetic */ int[] $SwitchMap$com$mintti$visionsdk$ble$bean$BgEvent;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|14) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.mintti.visionsdk.ble.bean.BgEvent[] r0 = com.mintti.visionsdk.ble.bean.BgEvent.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$mintti$visionsdk$ble$bean$BgEvent = r0
                com.mintti.visionsdk.ble.bean.BgEvent r1 = com.mintti.visionsdk.ble.bean.BgEvent.BG_EVENT_CALIBRATION_FAILED     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$mintti$visionsdk$ble$bean$BgEvent     // Catch:{ NoSuchFieldError -> 0x001d }
                com.mintti.visionsdk.ble.bean.BgEvent r1 = com.mintti.visionsdk.ble.bean.BgEvent.BG_EVENT_WAIT_PAGER_INSERT     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$mintti$visionsdk$ble$bean$BgEvent     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.mintti.visionsdk.ble.bean.BgEvent r1 = com.mintti.visionsdk.ble.bean.BgEvent.BG_EVENT_WAIT_DRIP_BLOOD     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$mintti$visionsdk$ble$bean$BgEvent     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.mintti.visionsdk.ble.bean.BgEvent r1 = com.mintti.visionsdk.ble.bean.BgEvent.BG_EVENT_BLOOD_SAMPLE_DETECTING     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$mintti$visionsdk$ble$bean$BgEvent     // Catch:{ NoSuchFieldError -> 0x003e }
                com.mintti.visionsdk.ble.bean.BgEvent r1 = com.mintti.visionsdk.ble.bean.BgEvent.BG_EVENT_MEASURE_END     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$mintti$visionsdk$ble$bean$BgEvent     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.mintti.visionsdk.ble.bean.BgEvent r1 = com.mintti.visionsdk.ble.bean.BgEvent.BG_EVENT_PAPER_USED     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.p020kl.healthmonitor.measure.BGMeasureFragment.C17078.<clinit>():void");
        }
    }

    public void onBgResult(double d) {
        cancelResultCountDown();
        if (this.isViewBind) {
            requireActivity().runOnUiThread(new Runnable(d) {
                public final /* synthetic */ double f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    BGMeasureFragment.this.lambda$onBgResult$2$BGMeasureFragment(this.f$1);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onBgResult$2$BGMeasureFragment(double d) {
        this.curStep = 1;
        resetView();
        this.llMeasuring.setVisibility(8);
        this.llFirstPage.setVisibility(0);
        showResult(d);
        insertBgToDb(d);
    }

    private void startResultCountDown() {
        Disposable disposable = this.countDownDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.countDownDisposable.dispose();
        }
        TimerUtils.countDown(16).subscribe(new Observer<Long>() {
            public void onError(Throwable th) {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = BGMeasureFragment.this.countDownDisposable = disposable;
            }

            public void onNext(Long l) {
                TextView textView = BGMeasureFragment.this.tvBgCountDown;
                long j = 0;
                if (l.longValue() - 1 >= 0) {
                    j = l.longValue() - 1;
                }
                textView.setText(String.valueOf(j));
            }

            public void onComplete() {
                BGMeasureFragment.this.showBgTimeoutDialog();
            }
        });
    }

    private void cancelResultCountDown() {
        Disposable disposable = this.countDownDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.countDownDisposable.dispose();
            this.countDownDisposable = null;
        }
    }

    /* access modifiers changed from: private */
    public void showBgTimeoutDialog() {
        if (this.bgTimeoutDialog == null) {
            MessageDialog show = MessageDialog.show((int) C1624R.string.bg_calculate_timeout, (int) C1624R.string.change_test_paper);
            this.bgTimeoutDialog = show;
            show.setCancelable(false);
            this.bgTimeoutDialog.setOkButton((int) C1624R.string.picker_ok, (OnDialogButtonClickListener<MessageDialog>) new OnDialogButtonClickListener<MessageDialog>() {
                public boolean onClick(MessageDialog messageDialog, View view) {
                    int unused = BGMeasureFragment.this.curStep = 1;
                    BGMeasureFragment.this.stopMeasure();
                    BGMeasureFragment.this.resetView();
                    BGMeasureFragment.this.llMeasuring.setVisibility(8);
                    BGMeasureFragment.this.llFirstPage.setVisibility(0);
                    return false;
                }
            });
        }
        this.bgTimeoutDialog.show();
    }

    private void showBgInitFailedDialog() {
        FragmentActivity requireActivity = requireActivity();
        ToastUtil.showToastUserLayout(requireActivity, getString(C1624R.string.bg_init_failed) + getString(C1624R.string.pull_out_test_paper_and_retry));
        this.curStep = 1;
        stopMeasure();
        resetView();
        this.llMeasuring.setVisibility(8);
        this.llFirstPage.setVisibility(0);
    }

    public void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacksAndMessages((Object) null);
        MessageDialog messageDialog = this.bgTimeoutDialog;
        if (messageDialog != null) {
            messageDialog.dismiss();
        }
        MessageDialog messageDialog2 = this.bgInitFailedDialog;
        if (messageDialog2 != null) {
            messageDialog2.dismiss();
        }
        cancelResultCountDown();
    }
}
