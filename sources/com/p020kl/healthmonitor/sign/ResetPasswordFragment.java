package com.p020kl.healthmonitor.sign;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.itextpdf.text.html.HtmlTags;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.constants.NetConstants;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.utils.VCodeParamUtil;
import com.p020kl.commonbase.utils.JsonUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.TimerUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.views.countrypicker.Country;
import com.p020kl.commonbase.views.countrypicker.CountryPickerFragment;
import com.p020kl.healthmonitor.C1624R;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.healthmonitor.sign.ResetPasswordFragment */
public class ResetPasswordFragment extends BaseFragmentWhiteToolbar {
    private String code;
    /* access modifiers changed from: private */
    public Disposable disposableSend;
    /* access modifiers changed from: private */
    public Disposable disposableSign;
    /* access modifiers changed from: private */
    public Disposable disposableTime;
    @BindView(3065)
    Button mAffirm;
    @BindView(3226)
    EditText mEtCode;
    @BindView(3233)
    EditText mEtPassword;
    @BindView(3235)
    EditText mEtPhone;
    @BindView(3076)
    Button mSendCode;
    @BindView(3737)
    TextView mTvCountry;
    @BindView(3796)
    TextView mTvPhoneId;
    private Observer<ResponseResult<Object>> observer = new Observer<ResponseResult<Object>>() {
        public void onComplete() {
        }

        public void onSubscribe(Disposable disposable) {
            Disposable unused = ResetPasswordFragment.this.disposableSend = disposable;
        }

        public void onNext(ResponseResult<Object> responseResult) {
            int status = responseResult.getStatus();
            if (status == 200) {
                ResetPasswordFragment.this.cuntDown();
                ToastUtil.showToast((Context) ResetPasswordFragment.this.getActivity(), StringUtils.getString(C1624R.string.send_successfully));
            } else if (status == 212) {
                ResetPasswordFragment.this.setAlphaAndEnable(1.0f, true);
                ResetPasswordFragment.this.showHint(StringUtils.getString(C1624R.string.err_phone_numble));
            } else if (status == 204) {
                ResetPasswordFragment.this.setAlphaAndEnable(1.0f, true);
                ResetPasswordFragment.this.showHint(StringUtils.getString(C1624R.string.account_exists));
            } else if (status == 220) {
                ResetPasswordFragment.this.setAlphaAndEnable(1.0f, true);
                ResetPasswordFragment.this.showHint(StringUtils.getString(C1624R.string.user_not_exist));
            } else if (status == 218) {
                ResetPasswordFragment.this.setAlphaAndEnable(1.0f, true);
                ResetPasswordFragment.this.showHint(StringUtils.getString(C1624R.string.send_frequently));
            } else if (status == 221) {
                ResetPasswordFragment.this.setAlphaAndEnable(1.0f, true);
                ResetPasswordFragment.this.showHint(StringUtils.getString(C1624R.string.please_enter_correct));
            } else {
                ResetPasswordFragment.this.setAlphaAndEnable(1.0f, true);
                ResetPasswordFragment.this.showHint(StringUtils.getString(C1624R.string.abnormal_login));
            }
        }

        public void onError(Throwable th) {
            ResetPasswordFragment.this.setAlphaAndEnable(1.0f, true);
            ResetPasswordFragment.this.showHint(StringUtils.getString(C1624R.string.network_err));
        }
    };
    private String password;
    private String phoneId;
    private String phoneOrEmlNumble;
    private String realityCode;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public void onBindView(Bundle bundle, View view) {
    }

    public static ResetPasswordFragment newInstance() {
        Bundle bundle = new Bundle();
        ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
        resetPasswordFragment.setArguments(bundle);
        return resetPasswordFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_tetrieve_password);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.get_back);
    }

    /* access modifiers changed from: private */
    public void setAlphaAndEnable(float f, boolean z) {
        this.mSendCode.setAlpha(f);
        this.mSendCode.setEnabled(z);
    }

    @OnClick({3076, 3065, 3390})
    public void onClick(View view) {
        int id = view.getId();
        if (id == C1624R.C1628id.bt_affirm) {
            judInformation();
        } else if (id == C1624R.C1628id.bt_send_code) {
            this.phoneOrEmlNumble = this.mEtPhone.getText().toString().trim();
            this.phoneId = this.mTvPhoneId.getText().toString().trim().substring(1);
            if (TextUtils.isEmpty(this.phoneOrEmlNumble)) {
                showHint(StringUtils.getString(C1624R.string.login_phone));
            } else if (StringUtils.isPhone(this.phoneOrEmlNumble) || StringUtils.isEmail(this.phoneOrEmlNumble)) {
                sendVcode();
            } else {
                showHint(StringUtils.getString(C1624R.string.err_phone_numble));
            }
        } else if (id == C1624R.C1628id.ll_country_select) {
            startForResult(new CountryPickerFragment(), 10);
        }
    }

    /* access modifiers changed from: private */
    public void cuntDown() {
        TimerUtils.countDown(59).subscribe(new Observer<Long>() {
            public void onError(Throwable th) {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = ResetPasswordFragment.this.disposableTime = disposable;
            }

            public void onNext(Long l) {
                Button button = ResetPasswordFragment.this.mSendCode;
                button.setText(l + HtmlTags.f490S);
                if (l.longValue() == 0) {
                    ResetPasswordFragment.this.mSendCode.setText(StringUtils.getString(C1624R.string.send_code));
                }
            }

            public void onComplete() {
                ResetPasswordFragment.this.mSendCode.setAlpha(1.0f);
                ResetPasswordFragment.this.mSendCode.setEnabled(true);
            }
        });
    }

    private void sendVcode() {
        setAlphaAndEnable(0.5f, false);
        if (StringUtils.isPhone(this.phoneOrEmlNumble)) {
            RestClient.getVCodeByPhone(VCodeParamUtil.getPhoneVCodeParam(this.phoneOrEmlNumble, this.phoneId, NetConstants.SIGN_TYPE_RESET)).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this.observer);
        } else if (StringUtils.isEmail(this.phoneOrEmlNumble)) {
            RestClient.getVCodeByEmail(VCodeParamUtil.getEmailVCodeParam(this.phoneOrEmlNumble, NetConstants.SIGN_TYPE_RESET)).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this.observer);
        }
    }

    private void judInformation() {
        this.mAffirm.setEnabled(false);
        this.phoneOrEmlNumble = this.mEtPhone.getText().toString().trim();
        this.code = this.mEtCode.getText().toString().trim();
        this.password = this.mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(this.phoneOrEmlNumble) || TextUtils.isEmpty(this.password)) {
            this.mAffirm.setEnabled(true);
            showHint(StringUtils.getString(C1624R.string.empty_nameandpassword_dialog));
        } else if (!StringUtils.checkPassword(this.password)) {
            this.mAffirm.setEnabled(true);
            showHint(getString(C1624R.string.ps_not_checked));
        } else if (!StringUtils.isPhone(this.phoneOrEmlNumble) && !StringUtils.isEmail(this.phoneOrEmlNumble)) {
            this.mAffirm.setEnabled(true);
            showHint(Integer.valueOf(C1624R.string.nameandpasword_dialog_unmatch));
        } else if (!this.code.equals("")) {
            showProgressDialog(StringUtils.getString(C1624R.string.retrieving), false);
            resertPassword();
        } else {
            this.mAffirm.setEnabled(true);
            showHint(StringUtils.getString(C1624R.string.enter_code_dialog));
        }
    }

    private void resertPassword() {
        RestClient.resetPassword(this.phoneOrEmlNumble, this.code, this.password).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseResult<Object>>() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = ResetPasswordFragment.this.disposableSign = disposable;
            }

            public void onNext(ResponseResult<Object> responseResult) {
                int status = responseResult.getStatus();
                if (status == 200) {
                    ResetPasswordFragment.this.disProgressDialog();
                    ToastUtil.showToast(ResetPasswordFragment.this.getContext(), StringUtils.getString(C1624R.string.modify_successfully));
                    ResetPasswordFragment.this.pop();
                } else if (status == 204) {
                    ResetPasswordFragment.this.mAffirm.setEnabled(true);
                    ResetPasswordFragment.this.disProgressDialog();
                    ResetPasswordFragment.this.showHint(StringUtils.getString(C1624R.string.account_exists));
                } else if (status == 230 || status == 231) {
                    ResetPasswordFragment.this.mAffirm.setEnabled(true);
                    ResetPasswordFragment.this.disProgressDialog();
                    ResetPasswordFragment.this.showHint(StringUtils.getString(C1624R.string.vcode_err));
                } else if (status == 203) {
                    ResetPasswordFragment.this.mAffirm.setEnabled(true);
                    ResetPasswordFragment.this.disProgressDialog();
                    ResetPasswordFragment.this.showHint(StringUtils.getString(C1624R.string.please_send_vcode));
                } else {
                    ResetPasswordFragment.this.mAffirm.setEnabled(true);
                    ResetPasswordFragment.this.disProgressDialog();
                    ResetPasswordFragment.this.showHint(StringUtils.getString(C1624R.string.abnormal_login));
                }
            }

            public void onError(Throwable th) {
                ResetPasswordFragment.this.mAffirm.setEnabled(true);
                ResetPasswordFragment.this.disProgressDialog();
                ResetPasswordFragment.this.showHint(StringUtils.getString(C1624R.string.abnormal_login));
            }
        });
    }

    public void onFragmentResult(int i, int i2, Bundle bundle) {
        super.onFragmentResult(i, i2, bundle);
        if (i == 10 && i2 == -1) {
            Country country = (Country) JsonUtils.formJsonString(bundle.getString(CountryPickerFragment.KEY_COUNTRY), Country.class);
            this.mTvCountry.setText(country.name);
            TextView textView = this.mTvPhoneId;
            textView.setText("+" + country.code);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.disposableTime;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposableTime.dispose();
        }
        Disposable disposable2 = this.disposableSend;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.disposableSend.dispose();
        }
        Disposable disposable3 = this.disposableSign;
        if (disposable3 != null && !disposable3.isDisposed()) {
            this.disposableSign.dispose();
        }
    }
}
