package com.p020kl.healthmonitor.measure;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.p005v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import com.kongzue.dialogx.dialogs.PopTip;
import com.kongzue.dialogx.style.MaterialStyle;
import com.mintti.visionsdk.ble.BleManager;
import com.mintti.visionsdk.ble.bean.MeasureType;
import com.mintti.visionsdk.ble.callback.IBleWriteResponse;
import com.mintti.visionsdk.ble.callback.IEcgFingerDetectionListener;
import com.mintti.visionsdk.ble.callback.IEcgResultListener;
import com.mintti.visionsdk.common.LogUtils;
import com.p020kl.commonbase.bean.ECGEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.ECGTableManager;
import com.p020kl.commonbase.event.Event;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UploadResult;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.EventBusUtil;
import com.p020kl.commonbase.utils.FileUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.views.ecg.ChartView;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.bean.EcgBufferData;
import com.p020kl.healthmonitor.guide.ecg.EcgGuideFragment;
import com.p020kl.healthmonitor.views.MeasureItemLevelView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import p028io.reactivex.functions.Consumer;

/* renamed from: com.kl.healthmonitor.measure.ECGMeasureFragment */
public class ECGMeasureFragment extends BaseMeasureFragment implements IEcgResultListener, IEcgFingerDetectionListener {
    private static final String BUNDLE_MINTTI_DURATION = "ecg_duration";
    private static final String BUNDLE_MINTTI_IS_END = "isEnd";
    private static final long DELAY_COUNT_DOWN = 1000;
    private static final int MSG_COUNT_DOWN = 2;
    private static final int MSG_LINK_TOP_ECG_VALUE = 3;
    private static final int MSG_MINTTI_ECG_DURATION = 4;
    /* access modifiers changed from: private */
    public int avgHr;
    @BindView(3069)
    Button btnEcgMeasure;
    /* access modifiers changed from: private */
    public long createDate;
    private long curClickTime = 0;
    private EcgBufferData ecgBufferData;
    /* access modifiers changed from: private */
    public File ecgFile;
    private FileOutputStream ecgFos;
    /* access modifiers changed from: private */
    public int ecgMeasureTime = 0;
    @BindView(3215)
    ChartView ecgView;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private EcgGuideFragment guideFragment;
    /* access modifiers changed from: private */
    public int hrv;
    private boolean isEcgMeasuring = false;
    private boolean isEcgModuleExist;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (!ECGMeasureFragment.this.isViewBind) {
                return;
            }
            if (message.what == 2) {
                ECGMeasureFragment.access$108(ECGMeasureFragment.this);
                int i = 60;
                if (ECGMeasureFragment.this.respiratoryRate != 0) {
                    i = 40;
                }
                ECGMeasureFragment.this.mivDuration.setResultValue(String.valueOf(ECGMeasureFragment.this.ecgMeasureTime));
                if (ECGMeasureFragment.this.ecgMeasureTime == i) {
                    ECGMeasureFragment.this.onClick();
                } else {
                    ECGMeasureFragment.this.mHandler.sendEmptyMessageDelayed(2, ECGMeasureFragment.DELAY_COUNT_DOWN);
                }
            } else if (message.what == 4) {
                Bundle data = message.getData();
                ECGMeasureFragment.this.handleMinttiEcgDuration(data.getInt(ECGMeasureFragment.BUNDLE_MINTTI_DURATION), data.getBoolean(ECGMeasureFragment.BUNDLE_MINTTI_IS_END));
            }
        }
    };
    /* access modifiers changed from: private */
    public int maxRRI = 0;
    /* access modifiers changed from: private */
    public long measureDuration;
    /* access modifiers changed from: private */
    public int minRRI = 0;
    @BindView(3448)
    MeasureItemLevelView mivBr;
    @BindView(3449)
    MeasureItemLevelView mivDuration;
    @BindView(3450)
    MeasureItemLevelView mivGain;
    @BindView(3451)
    MeasureItemLevelView mivHR;
    @BindView(3452)
    MeasureItemLevelView mivHRV;
    @BindView(3454)
    MeasureItemLevelView mivPRImax;
    @BindView(3455)
    MeasureItemLevelView mivPRImin;
    @BindView(3459)
    MeasureItemLevelView mivPaperSpeed;
    /* access modifiers changed from: private */
    public int mood;
    private long preClickTime = 0;
    /* access modifiers changed from: private */
    public int respiratoryRate;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    static /* synthetic */ int access$108(ECGMeasureFragment eCGMeasureFragment) {
        int i = eCGMeasureFragment.ecgMeasureTime;
        eCGMeasureFragment.ecgMeasureTime = i + 1;
        return i;
    }

    public synchronized void setEcgMeasuring(boolean z) {
        this.isEcgMeasuring = z;
    }

    public static ECGMeasureFragment newInstance() {
        Bundle bundle = new Bundle();
        ECGMeasureFragment eCGMeasureFragment = new ECGMeasureFragment();
        eCGMeasureFragment.setArguments(bundle);
        return eCGMeasureFragment;
    }

    public static ECGMeasureFragment newInstance(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(BaseMeasureFragment.IS_ROTHMAN_INDEX_MODE, z);
        ECGMeasureFragment eCGMeasureFragment = new ECGMeasureFragment();
        eCGMeasureFragment.setArguments(bundle);
        return eCGMeasureFragment;
    }

    /* access modifiers changed from: protected */
    public void showGuideFragment() {
        if (SpManager.getEcgGuideIsShow()) {
            EcgGuideFragment ecgGuideFragment = new EcgGuideFragment();
            this.guideFragment = ecgGuideFragment;
            ecgGuideFragment.show(getChildFragmentManager(), "ecg_guide");
            SpManager.setEcgGuideIsShow(false);
        }
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_ecg_measure);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return StringUtils.getString(C1624R.string.ecg);
    }

    public void onLazyInitView(Bundle bundle) {
        super.onLazyInitView(bundle);
        BleManager.getInstance().setEcgResultListener(this);
        BleManager.getInstance().setEcgFingerDetectionListener(this);
        this.ecgView.setSampleRate(BleManager.getInstance().getSampleRate());
        int paperSpeed = SpManager.getPaperSpeed();
        float gain = SpManager.getGain();
        this.ecgView.setGain(gain);
        this.ecgView.setPagerSpeed(paperSpeed + 1);
        if (paperSpeed == 0) {
            this.mivPaperSpeed.setResultValue(String.valueOf(25));
        } else {
            this.mivPaperSpeed.setResultValue(String.valueOf(50));
        }
        this.mivGain.setResultValue(new DecimalFormat("0").format((double) (gain * 10.0f)));
    }

    public void onBindView(Bundle bundle, View view) {
        super.onBindView(bundle, view);
        this.isRothmanIndexMode = getArguments().getBoolean(BaseMeasureFragment.IS_ROTHMAN_INDEX_MODE, false);
    }

    public void startMeasure() {
        super.startMeasure();
        setEcgMeasuring(true);
        resetMiv();
        boolean isEcgModuleExist2 = this.mHealthMonitorService.isEcgModuleExist();
        this.isEcgModuleExist = isEcgModuleExist2;
        if (isEcgModuleExist2) {
            createEcgFile();
            this.mHealthMonitorService.startMeasure(MeasureType.TYPE_ECG, new IBleWriteResponse() {
                public void onWriteFailed() {
                }

                public void onWriteSuccess() {
                }
            });
            this.btnEcgMeasure.setText(C1624R.string.stop_measure);
            return;
        }
        this.btnEcgMeasure.setText(C1624R.string.start_measure);
        ToastUtil.showLongToast(StringUtils.getString(C1624R.string.ecg_is_exist));
    }

    private void createEcgFile() {
        try {
            this.ecgBufferData = new EcgBufferData();
            long currentTimeMillis = System.currentTimeMillis();
            this.createDate = currentTimeMillis;
            this.ecgFile = FileUtils.getEcgFile(currentTimeMillis);
            this.ecgFos = new FileOutputStream(this.ecgFile);
            this.measureDuration = 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void closeFile(boolean z) {
        File file;
        FileOutputStream fileOutputStream = this.ecgFos;
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
                this.ecgFos = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (z && (file = this.ecgFile) != null && file.exists()) {
            this.ecgFile.delete();
        }
    }

    public void stopMeasure() {
        super.stopMeasure();
        setEcgMeasuring(false);
        this.mHealthMonitorService.stopMeasure(MeasureType.TYPE_ECG, new IBleWriteResponse() {
            public void onWriteFailed() {
            }

            public void onWriteSuccess() {
            }
        });
        this.ecgView.clearDatas();
        closeFile(false);
    }

    private void addEcgToDb() {
        this.executorService.submit(new Runnable() {
            public void run() {
                final ECGEntity eCGEntity = new ECGEntity();
                eCGEntity.setUserId(SpManager.getMemberId());
                eCGEntity.setCreateTime(ECGMeasureFragment.this.createDate);
                eCGEntity.setModifyTime(ECGMeasureFragment.this.createDate);
                eCGEntity.setRriMin(ECGMeasureFragment.this.minRRI);
                eCGEntity.setRriMax(ECGMeasureFragment.this.maxRRI);
                eCGEntity.setAvgHr(ECGMeasureFragment.this.avgHr);
                eCGEntity.setHrv(ECGMeasureFragment.this.hrv);
                eCGEntity.setMood(ECGMeasureFragment.this.mood);
                eCGEntity.setBr(ECGMeasureFragment.this.respiratoryRate);
                eCGEntity.setDuration(ECGMeasureFragment.this.measureDuration);
                eCGEntity.setFilePath(ECGMeasureFragment.this.ecgFile.getAbsolutePath());
                eCGEntity.setYear(DateUtils.getYear(ECGMeasureFragment.this.createDate));
                eCGEntity.setMonth(DateUtils.getMonth(ECGMeasureFragment.this.createDate));
                eCGEntity.setDay(DateUtils.getDay(ECGMeasureFragment.this.createDate));
                eCGEntity.setFileMd5(FileUtils.getFileMd5(ECGMeasureFragment.this.ecgFile));
                eCGEntity.setSampleRate(BleManager.getInstance().getSampleRate());
                ECGTableManager.insertEcgEntity(eCGEntity);
                Log.e(ECGMeasureFragment.this.TAG, "addEcgToDb: insert db");
                EventBusUtil.sendStickyEvent(new Event(eCGEntity));
                RestClient.uploadEcgFile(eCGEntity, ECGMeasureFragment.this.ecgFile).subscribe(new Consumer<ResponseResult<UploadResult>>() {
                    public void accept(ResponseResult<UploadResult> responseResult) throws Exception {
                        if (responseResult.getStatus() == 200) {
                            Log.d("ecgUploap", "上传ecg数据成功 " + ECGMeasureFragment.this.ecgFile.length());
                            UploadResult data = responseResult.getData();
                            eCGEntity.setDocId(data.getDocId());
                            eCGEntity.setFileUrl(data.getFileUrl());
                            ECGTableManager.updateEcgEntity(eCGEntity);
                            return;
                        }
                        Log.d("ecgUploap", "上传ecg数据失败");
                    }
                }, new Consumer<Throwable>() {
                    public void accept(Throwable th) throws Exception {
                        Log.d("ecgUploap", "上传ecg数据失败" + th.getMessage());
                    }
                });
            }
        });
    }

    private void onEcgDuration(long j) {
        closeFile(false);
        this.measureDuration = j;
        if (this.isViewBind) {
            requireActivity().runOnUiThread(new Runnable() {
                public void run() {
                    ECGMeasureFragment.this.btnEcgMeasure.setText(C1624R.string.start_measure);
                    ECGMeasureFragment.this.ecgView.clearDatas();
                }
            });
            addEcgToDb();
        }
    }

    private void addBufferDataAndWrite(int i) {
        try {
            EcgBufferData ecgBufferData2 = this.ecgBufferData;
            if (ecgBufferData2 != null && ecgBufferData2.getIndex() >= 512) {
                FileOutputStream fileOutputStream = this.ecgFos;
                if (fileOutputStream != null) {
                    fileOutputStream.write(this.ecgBufferData.getDatas());
                }
                this.ecgBufferData = null;
            }
            if (this.ecgBufferData == null) {
                this.ecgBufferData = new EcgBufferData();
            }
            this.ecgBufferData.addData(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick({3069})
    public void onClick() {
        long currentTimeMillis = System.currentTimeMillis();
        this.curClickTime = currentTimeMillis;
        if (currentTimeMillis - this.preClickTime <= 400) {
            ToastUtil.showToast((Context) this.mActivity, (int) C1624R.string.click_too_fast);
        } else if (!this.mHealthMonitorService.isConnected()) {
            showHint(StringUtils.getString(C1624R.string.connect_firist));
            return;
        } else if (this.btnEcgMeasure.getText().equals(StringUtils.getString(C1624R.string.start_measure))) {
            startMeasure();
        } else {
            stopMeasure();
            this.btnEcgMeasure.setText(C1624R.string.start_measure);
        }
        this.preClickTime = this.curClickTime;
    }

    private void resetMiv() {
        this.minRRI = 0;
        this.maxRRI = 0;
        this.avgHr = 0;
        this.hrv = 0;
        this.mood = 0;
        this.respiratoryRate = 0;
        this.mivPRImax.setResultValue("0");
        this.mivPRImin.setResultValue("0");
        this.mivHR.setResultValue("0");
        this.mivHRV.setResultValue("0");
        this.mivBr.setResultValue("0");
        this.mivDuration.setResultValue("0");
        this.measureDuration = 0;
        this.ecgMeasureTime = 0;
    }

    public void onDrawWave(int i) {
        if (this.isEcgMeasuring && this.ecgView != null && this.isViewBind) {
            this.ecgView.addPoint(i);
            addBufferDataAndWrite(i);
        }
    }

    public void onHeartRate(int i) {
        if (this.isEcgMeasuring && this.isViewBind) {
            this.avgHr = i;
            requireActivity().runOnUiThread(new Runnable(i) {
                public final /* synthetic */ int f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    ECGMeasureFragment.this.lambda$onHeartRate$3$ECGMeasureFragment(this.f$1);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onHeartRate$3$ECGMeasureFragment(int i) {
        this.mivHR.setResultValue(String.valueOf(i));
    }

    public void onRespiratoryRate(int i) {
        if (this.isEcgMeasuring && this.isViewBind) {
            if (i < 0 || i > 120) {
                i = 0;
            }
            if (i != 0) {
                this.respiratoryRate = i;
                requireActivity().runOnUiThread(new Runnable() {
                    public final void run() {
                        ECGMeasureFragment.this.lambda$onRespiratoryRate$4$ECGMeasureFragment();
                    }
                });
                if (this.ecgMeasureTime > 40) {
                    onClick();
                }
            }
        }
    }

    public /* synthetic */ void lambda$onRespiratoryRate$4$ECGMeasureFragment() {
        this.mivBr.setResultValue(String.valueOf(this.respiratoryRate));
    }

    public void onEcgResult(int i, int i2, int i3) {
        Log.e("EcgMeasureFragment", "onEcgResult: rrMax = " + i + " rrMin = " + i2 + " hrv = " + i3);
        if (this.isViewBind) {
            this.maxRRI = i;
            this.minRRI = i2;
            if (i < i2 || i > 2000 || i2 < 300) {
                this.maxRRI = 0;
                this.minRRI = 0;
                this.hrv = 0;
            }
            if (i3 < 0) {
                i3 = 0;
            }
            this.hrv = i3;
            requireActivity().runOnUiThread(new Runnable() {
                public final void run() {
                    ECGMeasureFragment.this.lambda$onEcgResult$5$ECGMeasureFragment();
                }
            });
        }
    }

    public /* synthetic */ void lambda$onEcgResult$5$ECGMeasureFragment() {
        if (this.isViewBind) {
            this.mivPRImax.setResultValue(String.valueOf(this.maxRRI));
            this.mivPRImin.setResultValue(String.valueOf(this.minRRI));
            this.mivHRV.setResultValue(String.valueOf(this.hrv));
        }
    }

    public void onEcgDuration(int i, boolean z) {
        LogUtils.m379e("ECGMeasureFragment", "onEcgDuration = " + i);
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_MINTTI_DURATION, i);
        bundle.putBoolean(BUNDLE_MINTTI_IS_END, z);
        Message obtainMessage = this.mHandler.obtainMessage(4);
        obtainMessage.setData(bundle);
        this.mHandler.sendMessage(obtainMessage);
    }

    public void onFingerDetection(boolean z) {
        if (this.isEcgMeasuring && !z) {
            PopTip popTip = new PopTip((CharSequence) getString(C1624R.string.finger_detection_fail));
            popTip.setStyle(MaterialStyle.style());
            popTip.show();
        }
    }

    /* access modifiers changed from: private */
    public void handleMinttiEcgDuration(int i, boolean z) {
        if (this.isViewBind) {
            this.mivDuration.setResultValue(String.valueOf(i));
            int i2 = 60;
            if (this.respiratoryRate != 0) {
                i2 = 40;
            }
            if (i == i2 && !z) {
                onClick();
            }
            if (i > 40 && this.respiratoryRate != 0 && !z) {
                onClick();
            }
            if (z) {
                closeFile(false);
                this.measureDuration = (long) i;
                this.btnEcgMeasure.setText(C1624R.string.start_measure);
                this.ecgView.clearDatas();
                File file = this.ecgFile;
                if (file == null || file.length() <= PlaybackStateCompat.ACTION_PREPARE) {
                    closeFile(true);
                    ToastUtil.showToastUserLayout(this.mActivity, getString(C1624R.string.data_too_short_no_save));
                } else {
                    addEcgToDb();
                }
                if (this.isRothmanIndexMode) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.BUNDLE_RESPIRATORY_RATE, this.respiratoryRate);
                    setFragmentResult(-1, bundle);
                    pop();
                }
            }
        }
    }

    public void onPause() {
        super.onPause();
    }

    public void onDestroyView() {
        super.onDestroyView();
        setEcgMeasuring(false);
        this.mHealthMonitorService.stopMeasure(MeasureType.TYPE_ECG, (IBleWriteResponse) null);
        this.mHandler.removeMessages(2);
        this.mHandler.removeCallbacksAndMessages((Object) null);
    }
}
