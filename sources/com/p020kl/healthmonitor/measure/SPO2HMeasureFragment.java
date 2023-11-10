package com.p020kl.healthmonitor.measure;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.mintti.visionsdk.ble.BleManager;
import com.mintti.visionsdk.ble.bean.MeasureType;
import com.mintti.visionsdk.ble.callback.IBleWriteResponse;
import com.mintti.visionsdk.ble.callback.ISpo2ResultListener;
import com.p020kl.commonbase.bean.Spo2Entity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.Spo2hTableManager;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UploadResult;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.guide.spo2.Spo2GuideFragment;
import com.p020kl.healthmonitor.views.CustomResultProgressBar;
import com.p020kl.healthmonitor.views.MeasureItemView;
import com.p020kl.healthmonitor.views.WaveView;
import com.p020kl.healthmonitor.wave.PPGDrawWave;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import p028io.reactivex.functions.Consumer;

/* renamed from: com.kl.healthmonitor.measure.SPO2HMeasureFragment */
public class SPO2HMeasureFragment extends BaseMeasureFragment implements ISpo2ResultListener {
    private static final String BUNDLE_HR = "hr";
    private static final String BUNDLE_SPO2 = "spo2";
    private static final long DELAY_COUNT_DOWN = 1000;
    private static final int MSG_COUNT_DOWN = 2;
    private static final int MSG_SPO2_RESULT = 1;
    /* access modifiers changed from: private */
    public int abnormalCount = 0;
    @BindView(3077)
    Button btMeasure;
    /* access modifiers changed from: private */
    public int heartRate = 0;
    private final ArrayList<Integer> hrResultList = new ArrayList<>();
    private boolean isStop = false;
    private final ExecutorService mExcutor = Executors.newSingleThreadExecutor();
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (SPO2HMeasureFragment.this.isViewBind && message.what == 1) {
                Bundle data = message.getData();
                int i = data.getInt("hr");
                double d = data.getDouble("spo2");
                if (d < 70.0d || d > 100.0d) {
                    SPO2HMeasureFragment.access$108(SPO2HMeasureFragment.this);
                    if (SPO2HMeasureFragment.this.abnormalCount >= 2) {
                        ToastUtil.showToastUserLayout(SPO2HMeasureFragment.this.requireContext(), SPO2HMeasureFragment.this.getString(C1624R.string.spo_abnormal_result));
                    }
                    d = 0.0d;
                }
                if (i < 30 || i > 200) {
                    SPO2HMeasureFragment.access$108(SPO2HMeasureFragment.this);
                    if (SPO2HMeasureFragment.this.abnormalCount >= 2) {
                        ToastUtil.showToastUserLayout(SPO2HMeasureFragment.this.requireContext(), SPO2HMeasureFragment.this.getString(C1624R.string.spo_abnormal_result));
                    }
                    i = 0;
                }
                if (i != 0) {
                    int unused = SPO2HMeasureFragment.this.abnormalCount = 0;
                    int unused2 = SPO2HMeasureFragment.this.heartRate = i;
                    SPO2HMeasureFragment.this.mivHeartRate.setMeasureValue(String.valueOf(i));
                }
                if (d >= 95.0d) {
                    SPO2HMeasureFragment.this.tvIsNormal.setText(C1624R.string.normal);
                } else {
                    SPO2HMeasureFragment.this.tvIsNormal.setText(C1624R.string.hypoxia);
                }
                if (d != 0.0d) {
                    int unused3 = SPO2HMeasureFragment.this.abnormalCount = 0;
                    double unused4 = SPO2HMeasureFragment.this.spo2 = d;
                }
                SPO2HMeasureFragment.this.mivBloodOxygen.setMeasureValue(String.valueOf(d));
                int unused5 = SPO2HMeasureFragment.this.prevHeartRate = i;
            }
        }
    };
    @BindView(3446)
    MeasureItemView mivBloodOxygen;
    @BindView(3447)
    MeasureItemView mivHeartRate;
    /* access modifiers changed from: private */
    public PPGDrawWave oxWave;
    @BindView(3523)
    WaveView oxWaveView;
    /* access modifiers changed from: private */
    public int prevHeartRate = 0;
    @BindView(3556)
    CustomResultProgressBar resultProgressBar;
    /* access modifiers changed from: private */
    public double spo2 = 0.0d;
    private int spo2hMeasureTime = 0;
    private final ArrayList<Double> spo2hResultList = new ArrayList<>();
    @BindView(3770)
    TextView tvIsNormal;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    static /* synthetic */ int access$108(SPO2HMeasureFragment sPO2HMeasureFragment) {
        int i = sPO2HMeasureFragment.abnormalCount;
        sPO2HMeasureFragment.abnormalCount = i + 1;
        return i;
    }

    public static SPO2HMeasureFragment newInstance() {
        Bundle bundle = new Bundle();
        SPO2HMeasureFragment sPO2HMeasureFragment = new SPO2HMeasureFragment();
        sPO2HMeasureFragment.setArguments(bundle);
        return sPO2HMeasureFragment;
    }

    public static SPO2HMeasureFragment newInstance(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(BaseMeasureFragment.IS_ROTHMAN_INDEX_MODE, z);
        SPO2HMeasureFragment sPO2HMeasureFragment = new SPO2HMeasureFragment();
        sPO2HMeasureFragment.setArguments(bundle);
        return sPO2HMeasureFragment;
    }

    /* access modifiers changed from: protected */
    public void showGuideFragment() {
        if (SpManager.getSpo2GuideIsShow()) {
            this.guideFragment = new Spo2GuideFragment();
            this.guideFragment.show(getChildFragmentManager(), "spo2_guide");
            SpManager.setSpo2GuideIsShow(false);
        }
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_spo2h_measure);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return StringUtils.getString(C1624R.string.blood_oxygen);
    }

    public void onBindView(Bundle bundle, View view) {
        super.onBindView(bundle, view);
        this.isRothmanIndexMode = getArguments().getBoolean(BaseMeasureFragment.IS_ROTHMAN_INDEX_MODE, false);
        PPGDrawWave pPGDrawWave = new PPGDrawWave();
        this.oxWave = pPGDrawWave;
        this.oxWaveView.setDrawWave(pPGDrawWave);
        BleManager.getInstance().setSpo2ResultListener(this);
    }

    /* access modifiers changed from: protected */
    public void startMeasure() {
        super.startMeasure();
        reset();
        if (!this.mHealthMonitorService.isMeasuring()) {
            this.mHealthMonitorService.startMeasure(MeasureType.TYPE_SPO2, new IBleWriteResponse() {
                public void onWriteFailed() {
                }

                public void onWriteSuccess() {
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void stopMeasure() {
        super.stopMeasure();
        this.spo2hMeasureTime = 0;
        this.isStop = true;
        this.mHandler.removeMessages(2);
        this.mHealthMonitorService.stopMeasure(MeasureType.TYPE_SPO2, new IBleWriteResponse() {
            public void onWriteFailed() {
            }

            public void onWriteSuccess() {
            }
        });
        handleSpo2Result(this.heartRate, this.spo2);
        this.prevHeartRate = 0;
        this.oxWave.clear();
    }

    private void reset() {
        this.heartRate = 0;
        this.spo2 = 0.0d;
        this.isStop = false;
        this.mivBloodOxygen.setMeasureValue("0");
        this.mivHeartRate.setMeasureValue("0");
        this.tvIsNormal.setText(C1624R.string.testing);
        this.resultProgressBar.clearProgress();
        this.spo2hResultList.clear();
        this.hrResultList.clear();
    }

    @OnClick({3077})
    public void onClick() {
        if (this.mHealthMonitorService == null || !this.mHealthMonitorService.isConnected()) {
            showHint(StringUtils.getString(C1624R.string.connect_firist));
        } else if (!this.btMeasure.isSelected()) {
            this.btMeasure.setSelected(true);
            this.btMeasure.setText(C1624R.string.stop_measure);
            startMeasure();
        } else {
            this.btMeasure.setSelected(false);
            this.btMeasure.setText(C1624R.string.start_measure);
            stopMeasure();
        }
    }

    private void handleSpo2Result(final int i, final double d) {
        if (this.isViewBind) {
            requireActivity().runOnUiThread(new Runnable() {
                public void run() {
                    double d;
                    SPO2HMeasureFragment.this.mivBloodOxygen.setMeasureValue(String.valueOf(d));
                    SPO2HMeasureFragment.this.mivHeartRate.setMeasureValue(String.valueOf(i));
                    double d2 = d - 90.0d;
                    if (d2 >= 0.0d) {
                        d = ((d2 / 10.0d) * 0.6660000085830688d) + 0.3330000042915344d;
                    } else {
                        d = Math.abs(d2) <= 5.0d ? 0.3330000042915344d - ((Math.abs(d2) / 5.0d) * 0.3330000042915344d) : 0.0d;
                    }
                    double d3 = d;
                    if (d3 >= 95.0d) {
                        SPO2HMeasureFragment.this.tvIsNormal.setText(C1624R.string.normal);
                    } else if (d3 <= 0.0d) {
                        SPO2HMeasureFragment.this.tvIsNormal.setText(SPO2HMeasureFragment.this.getString(C1624R.string.waiting_for_test));
                    } else {
                        SPO2HMeasureFragment.this.tvIsNormal.setText(C1624R.string.hypoxia);
                        ToastUtil.showToastUserLayout(SPO2HMeasureFragment.this.requireContext(), SPO2HMeasureFragment.this.getString(C1624R.string.measure_err));
                        SPO2HMeasureFragment.this.playWarm();
                    }
                    SPO2HMeasureFragment.this.resultProgressBar.setProgressWeight((float) d);
                    SPO2HMeasureFragment.this.oxWave.clear();
                    SPO2HMeasureFragment.this.btMeasure.setSelected(false);
                    SPO2HMeasureFragment.this.btMeasure.setText(C1624R.string.start_measure);
                }
            });
            if (d != 0.0d) {
                insertSpo2hToDb(d, i);
                if (this.isRothmanIndexMode) {
                    Bundle bundle = new Bundle();
                    bundle.putDouble(Constants.BUNDLE_SPO2, d);
                    bundle.putInt(Constants.BUNDLE_HR, i);
                    setFragmentResult(-1, bundle);
                    pop();
                }
            }
        }
    }

    private void insertSpo2hToDb(final double d, final int i) {
        this.mExcutor.submit(new Runnable() {
            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                final Spo2Entity spo2Entity = new Spo2Entity();
                spo2Entity.setUserId(SpManager.getMemberId());
                spo2Entity.setSpo2(d);
                spo2Entity.setHeartRate(i);
                spo2Entity.setCreateTime(currentTimeMillis);
                spo2Entity.setModifyTime(currentTimeMillis);
                spo2Entity.setYear(DateUtils.getYear(currentTimeMillis));
                spo2Entity.setMonth(DateUtils.getMonth(currentTimeMillis));
                spo2Entity.setDay(DateUtils.getDay(currentTimeMillis));
                Spo2hTableManager.insertEntity(spo2Entity);
                RestClient.uploadMeasureData(spo2Entity).subscribe(new Consumer<ResponseResult<UploadResult>>() {
                    public void accept(ResponseResult<UploadResult> responseResult) throws Exception {
                        if (responseResult.getStatus() == 200) {
                            spo2Entity.setDocId(responseResult.getData().getDocId());
                            Spo2hTableManager.updateEntity(spo2Entity);
                        }
                    }
                }, new Consumer<Throwable>() {
                    public void accept(Throwable th) throws Exception {
                    }
                });
                EventBusUtil.sendStickyEvent(new Event(spo2Entity));
            }
        });
    }

    public void onWaveData(int i) {
        if (this.oxWave != null && !this.isStop && this.isViewBind) {
            this.oxWave.addData(Integer.valueOf(i));
        }
    }

    public void onSpo2ResultData(int i, double d) {
        String str = this.TAG;
        Log.e(str, "onBoResultData: hr=" + i + " spo2=" + d);
        if (!this.isStop) {
            String str2 = this.TAG;
            Log.e(str2, "onBoResultData1: hr=" + i + " spo2=" + d);
            Message obtainMessage = this.mHandler.obtainMessage(1);
            Bundle bundle = new Bundle();
            bundle.putInt("hr", i);
            bundle.putDouble("spo2", d);
            obtainMessage.setData(bundle);
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public void onSpo2End() {
        this.isStop = true;
        handleSpo2Result(this.heartRate, this.spo2);
    }

    public void onPause() {
        super.onPause();
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.mHealthMonitorService != null) {
            this.mHealthMonitorService.stopMeasure(MeasureType.TYPE_SPO2, (IBleWriteResponse) null);
        }
    }
}
