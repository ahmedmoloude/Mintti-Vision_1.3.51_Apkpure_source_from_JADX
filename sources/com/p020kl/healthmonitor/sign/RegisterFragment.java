package com.p020kl.healthmonitor.sign;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import butterknife.BindView;
import butterknife.OnClick;
import com.itextpdf.text.html.HtmlTags;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.bean.UserInfoEntity;
import com.p020kl.commonbase.constants.Constants;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.UserInfoTableManager;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.constants.NetConstants;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.entity.UserForSignup;
import com.p020kl.commonbase.net.utils.NetworkUtils;
import com.p020kl.commonbase.net.utils.VCodeParamUtil;
import com.p020kl.commonbase.utils.AppUtils;
import com.p020kl.commonbase.utils.JsonUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.utils.TimerUtils;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.commonbase.views.CommonSelectDialog;
import com.p020kl.commonbase.views.countrypicker.Country;
import com.p020kl.commonbase.views.countrypicker.CountryPickerFragment;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.MainActivity;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.healthmonitor.sign.RegisterFragment */
public class RegisterFragment extends BaseFragmentWhiteToolbar {
    @BindView(3123)
    AppCompatCheckBox checkBoxAgreement;
    private String code;
    /* access modifiers changed from: private */
    public Disposable disposableSend;
    /* access modifiers changed from: private */
    public Disposable disposableSign;
    /* access modifiers changed from: private */
    public Disposable disposableTime;
    @BindView(3226)
    EditText mEtCode;
    @BindView(3236)
    EditText mEtPassword;
    @BindView(3235)
    EditText mEtPhone;
    @BindView(3073)
    Button mRegister;
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
            Disposable unused = RegisterFragment.this.disposableSend = disposable;
        }

        public void onNext(ResponseResult<Object> responseResult) {
            int status = responseResult.getStatus();
            String access$100 = RegisterFragment.this.TAG;
            Log.e(access$100, "onNext: state=" + status);
            if (status == 200) {
                RegisterFragment.this.cuntDown();
                ToastUtil.showToast((Context) RegisterFragment.this.getActivity(), StringUtils.getString(C1624R.string.send_successfully));
            } else if (status == 212) {
                RegisterFragment.this.setAlphaAndEnable(1.0f, true);
                RegisterFragment.this.showHint(StringUtils.getString(C1624R.string.err_phone_numble));
            } else if (status == 204) {
                RegisterFragment.this.setAlphaAndEnable(1.0f, true);
                RegisterFragment.this.showHint(StringUtils.getString(C1624R.string.account_exists));
            } else if (status == 218) {
                RegisterFragment.this.setAlphaAndEnable(1.0f, true);
                RegisterFragment.this.showHint(StringUtils.getString(C1624R.string.send_frequently));
            } else if (status == 221) {
                RegisterFragment.this.setAlphaAndEnable(1.0f, true);
                RegisterFragment.this.showHint(StringUtils.getString(C1624R.string.please_enter_correct));
            } else {
                RegisterFragment.this.setAlphaAndEnable(1.0f, true);
                RegisterFragment.this.showHint(StringUtils.getString(C1624R.string.abnormal_login));
            }
        }

        public void onError(Throwable th) {
            RegisterFragment.this.mSendCode.setEnabled(true);
            RegisterFragment.this.showHint(StringUtils.getString(C1624R.string.network_err));
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

    public static RegisterFragment newInstance() {
        Bundle bundle = new Bundle();
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setArguments(bundle);
        return registerFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_register);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.get_back);
    }

    @OnClick({3076, 3073, 2131297144, 3390, 3797})
    public void onClick(View view) {
        switch (view.getId()) {
            case C1624R.C1628id.bt_register:
                if (this.checkBoxAgreement.isChecked()) {
                    judInformation();
                    return;
                } else {
                    showAgreementDialog();
                    return;
                }
            case C1624R.C1628id.bt_send_code:
                this.phoneOrEmlNumble = this.mEtPhone.getText().toString().trim();
                this.phoneId = this.mTvPhoneId.getText().toString().trim().substring(1);
                if (!NetworkUtils.isNetworkConnected()) {
                    showHint(StringUtils.getString(C1624R.string.no_netword));
                    return;
                } else if (TextUtils.isEmpty(this.phoneOrEmlNumble)) {
                    showHint(StringUtils.getString(C1624R.string.login_phone));
                    return;
                } else if (StringUtils.isPhone(this.phoneOrEmlNumble) || StringUtils.isEmail(this.phoneOrEmlNumble)) {
                    sendVcode();
                    return;
                } else {
                    showHint(StringUtils.getString(C1624R.string.err_phone_numble));
                    return;
                }
            case C1624R.C1628id.ll_country_select:
                startForResult(new CountryPickerFragment(), 10);
                return;
            case C1624R.C1628id.tv_privacy_policy:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(AppUtils.isZh(requireContext()) ? Constants.PRIVACY_POLICY_ZH_URL : Constants.PRIVACY_POLICY_EN_URL)));
                return;
            case C1624R.C1628id.tv_user_aggrement:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(AppUtils.isZh(requireContext()) ? Constants.USER_AGREEMENT_ZH_URL : Constants.USER_AGREEMENT_EN_URL)));
                return;
            default:
                return;
        }
    }

    private void showAgreementDialog() {
        MessageDialog messageDialog = new MessageDialog((int) C1624R.string.selector_hint, (int) C1624R.string.dialog_read_and_agree_user_agreement);
        messageDialog.setOkButton((int) C1624R.string.picker_ok);
        messageDialog.show();
    }

    /* access modifiers changed from: private */
    public void setAlphaAndEnable(float f, boolean z) {
        this.mSendCode.setAlpha(f);
        this.mSendCode.setEnabled(z);
    }

    /* access modifiers changed from: private */
    public void cuntDown() {
        TimerUtils.countDown(59).subscribe(new Observer<Long>() {
            public void onError(Throwable th) {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = RegisterFragment.this.disposableTime = disposable;
            }

            public void onNext(Long l) {
                Button button = RegisterFragment.this.mSendCode;
                button.setText(l + HtmlTags.f490S);
                if (l.longValue() == 0) {
                    RegisterFragment.this.mSendCode.setText(StringUtils.getString(C1624R.string.send_code));
                }
            }

            public void onComplete() {
                RegisterFragment.this.mSendCode.setAlpha(1.0f);
                RegisterFragment.this.mSendCode.setEnabled(true);
            }
        });
    }

    private void sendVcode() {
        setAlphaAndEnable(0.5f, false);
        if (StringUtils.isPhone(this.phoneOrEmlNumble)) {
            RestClient.getVCodeByPhone(VCodeParamUtil.getPhoneVCodeParam(this.phoneOrEmlNumble, this.phoneId, NetConstants.SIGN_TYPE_SIGNUP)).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this.observer);
        } else if (StringUtils.isEmail(this.phoneOrEmlNumble)) {
            RestClient.getVCodeByEmail(VCodeParamUtil.getEmailVCodeParam(this.phoneOrEmlNumble, NetConstants.SIGN_TYPE_SIGNUP)).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this.observer);
        }
    }

    private void judInformation() {
        this.mRegister.setEnabled(false);
        this.phoneOrEmlNumble = this.mEtPhone.getText().toString().trim();
        this.code = this.mEtCode.getText().toString().trim();
        this.password = this.mEtPassword.getText().toString().trim();
        if (this.phoneOrEmlNumble.equals("") || this.password.equals("")) {
            this.mRegister.setEnabled(true);
            showHint(StringUtils.getString(C1624R.string.empty_nameandpassword_dialog));
        } else if (!StringUtils.checkPassword(this.password)) {
            this.mRegister.setEnabled(true);
            showHint(getString(C1624R.string.ps_not_checked));
        } else if (!StringUtils.isPhone(this.phoneOrEmlNumble) && !StringUtils.isEmail(this.phoneOrEmlNumble)) {
            this.mRegister.setEnabled(true);
            showHint(Integer.valueOf(C1624R.string.nameandpasword_dialog_unmatch));
        } else if (!this.code.equals("")) {
            onHintCollectInfo();
        } else {
            this.mRegister.setEnabled(true);
            showHint(StringUtils.getString(C1624R.string.enter_code_dialog));
        }
    }

    private void onHintCollectInfo() {
        new CommonSelectDialog.Builder(getActivity()).setWidth((int) getResources().getDimension(C1624R.dimen.dp_250)).setHeight((int) getResources().getDimension(C1624R.dimen.dp_100)).setTitle(getString(C1624R.string.hint_collect_info)).setOnClickListener(new CommonSelectDialog.OnClickListener() {
            public void onClick(CommonSelectDialog commonSelectDialog, boolean z) {
                if (z) {
                    RegisterFragment.this.showProgressDialog(StringUtils.getString(C1624R.string.registering), false);
                    RegisterFragment.this.signUp();
                } else {
                    RegisterFragment.this.mRegister.setEnabled(true);
                }
                commonSelectDialog.dismiss();
            }
        }).build().show();
    }

    /* access modifiers changed from: private */
    public void signUp() {
        UserForSignup userForSignup = new UserForSignup();
        userForSignup.setAccount(this.phoneOrEmlNumble);
        if (StringUtils.isPhone(this.phoneOrEmlNumble)) {
            userForSignup.setAccountType(NetConstants.SIGN_ACCOUNT_TYPE_PHONE);
            userForSignup.setCountryId(this.phoneId);
        } else {
            userForSignup.setAccountType("email");
        }
        userForSignup.setVcode(this.code);
        userForSignup.setPassword(this.password);
        RestClient.signup(userForSignup).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseResult<UserInfoEntity>>() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = RegisterFragment.this.disposableSign = disposable;
            }

            public void onNext(ResponseResult<UserInfoEntity> responseResult) {
                int status = responseResult.getStatus();
                if (status == 200) {
                    RegisterFragment.this.disProgressDialog();
                    ToastUtil.showToast(RegisterFragment.this.getContext(), StringUtils.getString(C1624R.string.registered_successfully));
                    UserInfoEntity data = responseResult.getData();
                    UserInfoTableManager.inserOrReplace(data);
                    SpManager.setToken(data.getToken());
                    SpManager.setUserId(data.getId());
                    RegisterFragment.this.startActivity(new Intent(RegisterFragment.this.getActivity(), MainActivity.class));
                    RegisterFragment.this._mActivity.finish();
                } else if (status == 204) {
                    RegisterFragment.this.mRegister.setEnabled(true);
                    RegisterFragment.this.disProgressDialog();
                    RegisterFragment.this.showHint(StringUtils.getString(C1624R.string.account_exists));
                } else if (status == 230 || status == 231) {
                    RegisterFragment.this.mRegister.setEnabled(true);
                    RegisterFragment.this.disProgressDialog();
                    RegisterFragment.this.showHint(StringUtils.getString(C1624R.string.vcode_err));
                } else if (status == 203) {
                    RegisterFragment.this.mRegister.setEnabled(true);
                    RegisterFragment.this.disProgressDialog();
                    RegisterFragment.this.showHint(StringUtils.getString(C1624R.string.please_send_vcode));
                } else {
                    RegisterFragment.this.mRegister.setEnabled(true);
                    RegisterFragment.this.disProgressDialog();
                    RegisterFragment.this.showHint(StringUtils.getString(C1624R.string.abnormal_login));
                }
            }

            public void onError(Throwable th) {
                RegisterFragment.this.mRegister.setEnabled(true);
                RegisterFragment.this.disProgressDialog();
                RegisterFragment.this.showHint(StringUtils.getString(C1624R.string.abnormal_login));
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
