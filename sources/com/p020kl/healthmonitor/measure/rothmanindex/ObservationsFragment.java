package com.p020kl.healthmonitor.measure.rothmanindex;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.p020kl.commonbase.base.BaseApplication;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.bean.rothmanindex.AssessmentBean;
import com.p020kl.commonbase.bean.rothmanindex.AuthenticatedSession;
import com.p020kl.commonbase.bean.rothmanindex.ObservationsBean;
import com.p020kl.commonbase.bean.rothmanindex.VitalSignsBean;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.utils.DateUtils;
import com.p020kl.commonbase.utils.JWTUtils;
import com.p020kl.commonbase.utils.JsonUtils;
import com.p020kl.commonbase.utils.LoggerUtil;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.TemperatureUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.measure.BPMeasureFragment;
import com.p020kl.healthmonitor.measure.BTMeasureFragment;
import com.p020kl.healthmonitor.measure.ECGMeasureFragment;
import com.p020kl.healthmonitor.measure.SPO2HMeasureFragment;
import java.io.IOException;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.ObservationsFragment */
public class ObservationsFragment extends BaseFragmentWhiteToolbar {
    public static final int REQURST_BP_MEASURE = 0;
    public static final int REQURST_BT_MEASURE = 2;
    public static final int REQURST_RESPIRATORY_MEASURE = 3;
    public static final int REQURST_SPO2_MEASURE = 1;
    private AssessmentBean assessmentBean;

    /* renamed from: bt */
    private double f900bt = 0.0d;
    /* access modifiers changed from: private */
    public String consumerToken;
    private int diastolic = 0;

    /* renamed from: hr */
    private int f901hr = 0;
    private int respiratoryRate = 0;
    private double spo2 = 0.0d;
    private int systolic = 0;
    @BindView(3734)
    TextView tvBpValue;
    @BindView(3814)
    TextView tvBtValue;
    @BindView(3803)
    TextView tvRespiratory;
    @BindView(3810)
    TextView tvSpo2HrValue;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public static ObservationsFragment newInstance(AssessmentBean assessmentBean2) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("AssessmentBean", assessmentBean2);
        ObservationsFragment observationsFragment = new ObservationsFragment();
        observationsFragment.setArguments(bundle);
        return observationsFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_observations);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.rothman_index);
    }

    public void onBindView(Bundle bundle, View view) {
        this.assessmentBean = (AssessmentBean) getArguments().getSerializable("AssessmentBean");
        if (SpManager.getTemperaTrueUnit() == 0) {
            this.tvBtValue.setText(" ℃");
        } else {
            this.tvBtValue.setText(" ℉");
        }
    }

    @OnClick({3066, 3078, 3079, 3074, 3072})
    public void onButtonClicked(View view) {
        switch (view.getId()) {
            case C1624R.C1628id.bt_bp_measure:
                startForResult(BPMeasureFragment.newInstance(true), 0);
                return;
            case C1624R.C1628id.bt_observations_submit:
                if (checkAllDataNull()) {
                    showHint(Integer.valueOf(C1624R.string.data_cannot_be_empty));
                    return;
                } else {
                    getConsumerToken();
                    return;
                }
            case C1624R.C1628id.bt_respiratory_measure:
                startForResult(ECGMeasureFragment.newInstance(true), 3);
                return;
            case C1624R.C1628id.bt_spo_measure:
                startForResult(SPO2HMeasureFragment.newInstance(true), 1);
                return;
            case C1624R.C1628id.bt_temperature_measure:
                startForResult(BTMeasureFragment.newInstance(true), 2);
                return;
            default:
                return;
        }
    }

    private boolean checkAllDataNull() {
        return this.systolic == 0 && this.diastolic == 0 && this.spo2 == 0.0d && this.f901hr == 0 && this.f900bt == 0.0d && this.respiratoryRate == 0;
    }

    /* access modifiers changed from: private */
    public void observationsSubmit() {
        RestClient.observations(this.consumerToken, getObservations()).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Response<Object>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(Response<Object> response) {
                if (response.isSuccessful()) {
                    ToastUtil.showToast((Context) ObservationsFragment.this.getActivity(), (int) C1624R.string.upload_successful);
                    ObservationsFragment.this.popTo(SelfAssessmentFragment.class, true);
                }
            }

            public void onError(Throwable th) {
                Log.e("rendsFragment", "onError: " + th.toString());
                ObservationsFragment.this.disProgressDialog();
            }

            public void onComplete() {
                ObservationsFragment.this.disProgressDialog();
            }
        });
    }

    private void getConsumerToken() {
        showProgressDialog(StringUtils.getString(C1624R.string.data_submission), false);
        RestClient.getSessionsToken(Constants.CLIENT_ID, JWTUtils.getJwt(BaseApplication.getInstance().getRothmanIndexUuid())).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Response<AuthenticatedSession>>() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(Response<AuthenticatedSession> response) {
                Log.e("ObservationsFragment", "onNext: " + response.toString());
                if (response.isSuccessful()) {
                    String unused = ObservationsFragment.this.consumerToken = response.body().getAccess_token();
                    ObservationsFragment.this.observationsSubmit();
                    return;
                }
                try {
                    ObservationsFragment.this.showHint(response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void onError(Throwable th) {
                ObservationsFragment.this.disProgressDialog();
                if (th.getMessage().equals("Canceled")) {
                    ObservationsFragment observationsFragment = ObservationsFragment.this;
                    observationsFragment.showHint(observationsFragment.getString(C1624R.string.timeout));
                    return;
                }
                ObservationsFragment.this.showHint(th.getMessage());
            }
        });
    }

    private ObservationsBean getObservations() {
        String createDate = DateUtils.getCreateDate();
        String rothmanIndexUuid = BaseApplication.getInstance().getRothmanIndexUuid();
        ObservationsBean observationsBean = new ObservationsBean();
        observationsBean.setObservedBy(rothmanIndexUuid);
        observationsBean.setObservedId(rothmanIndexUuid);
        observationsBean.setAssessment(this.assessmentBean);
        VitalSignsBean vitalSignsBean = new VitalSignsBean();
        VitalSignsBean.BloodPressureBean bloodPressureBean = new VitalSignsBean.BloodPressureBean();
        bloodPressureBean.setDateMeasured(createDate);
        bloodPressureBean.setMeasuredBy(rothmanIndexUuid);
        bloodPressureBean.setDiastolic(this.diastolic);
        bloodPressureBean.setSystolic(this.systolic);
        vitalSignsBean.setBloodPressure(bloodPressureBean);
        VitalSignsBean.OximetryBean oximetryBean = new VitalSignsBean.OximetryBean();
        oximetryBean.setMeasurement((int) this.spo2);
        oximetryBean.setDateMeasured(createDate);
        oximetryBean.setMeasuredBy(rothmanIndexUuid);
        vitalSignsBean.setOximetry(oximetryBean);
        VitalSignsBean.PulseBean pulseBean = new VitalSignsBean.PulseBean();
        pulseBean.setDateMeasured(createDate);
        pulseBean.setMeasuredBy(rothmanIndexUuid);
        pulseBean.setMeasurement(this.f901hr);
        vitalSignsBean.setPulse(pulseBean);
        VitalSignsBean.RespirationBean respirationBean = new VitalSignsBean.RespirationBean();
        respirationBean.setDateMeasured(createDate);
        respirationBean.setMeasuredBy(rothmanIndexUuid);
        respirationBean.setMeasurement(this.respiratoryRate);
        vitalSignsBean.setRespiration(respirationBean);
        VitalSignsBean.TemperatureBean temperatureBean = new VitalSignsBean.TemperatureBean();
        temperatureBean.setDateMeasured(createDate);
        temperatureBean.setMeasuredBy(rothmanIndexUuid);
        temperatureBean.setMeasurement(this.f900bt);
        vitalSignsBean.setTemperature(temperatureBean);
        observationsBean.setVitalSigns(vitalSignsBean);
        LoggerUtil.json(JsonUtils.toJsonString(observationsBean));
        return observationsBean;
    }

    public void onFragmentResult(int i, int i2, Bundle bundle) {
        super.onFragmentResult(i, i2, bundle);
        if (i == 0 && i2 == -1) {
            this.systolic = bundle.getInt(Constants.BUNDLE_SYSTOLIC_PRESSURE);
            int i3 = bundle.getInt(Constants.BUNDLE_DIASTOLIC_PRESSURE);
            this.diastolic = i3;
            setBP(this.systolic, i3);
        } else if (i == 1 && i2 == -1) {
            this.spo2 = bundle.getDouble(Constants.BUNDLE_SPO2);
            int i4 = bundle.getInt(Constants.BUNDLE_HR);
            this.f901hr = i4;
            setSpo2(this.spo2, i4);
        } else if (i == 2 && i2 == -1) {
            double d = bundle.getDouble(Constants.BUNDLE_BT);
            this.f900bt = d;
            setBt(d);
        } else if (i == 3 && i2 == -1) {
            int i5 = bundle.getInt(Constants.BUNDLE_RESPIRATORY_RATE);
            this.respiratoryRate = i5;
            setRespiratoryRate(i5);
        }
    }

    private void setBP(int i, int i2) {
        TextView textView = this.tvBpValue;
        textView.setText(i + "/" + i2 + " mmHg");
    }

    private void setSpo2(double d, int i) {
        TextView textView = this.tvSpo2HrValue;
        textView.setText(d + "% / " + i + " bpm");
    }

    private void setBt(double d) {
        String str = SpManager.getTemperaTrueUnit() == 0 ? " ℃" : " ℉";
        TextView textView = this.tvBtValue;
        textView.setText(TemperatureUtils.tempeTypeConversionDouble(d) + str);
    }

    private void setRespiratoryRate(int i) {
        TextView textView = this.tvRespiratory;
        textView.setText(i + " bpm");
    }
}
