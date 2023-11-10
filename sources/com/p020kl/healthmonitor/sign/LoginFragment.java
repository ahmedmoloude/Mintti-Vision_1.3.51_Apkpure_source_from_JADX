package com.p020kl.healthmonitor.sign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.bean.UserInfoEntity;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.data.p021db.manager.UserInfoTableManager;
import com.p020kl.commonbase.net.RestClient;
import com.p020kl.commonbase.net.entity.ResponseResult;
import com.p020kl.commonbase.net.utils.NetworkUtils;
import com.p020kl.commonbase.utils.JsonUtils;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.commonbase.views.countrypicker.Country;
import com.p020kl.commonbase.views.countrypicker.CountryPickerFragment;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.MainActivity;
import p028io.reactivex.Observer;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.healthmonitor.sign.LoginFragment */
public class LoginFragment extends BaseFragmentWhiteToolbar {
    /* access modifiers changed from: private */
    public Disposable disposable;
    @BindView(3070)
    Button mBtLogin;
    @BindView(3229)
    EditText mEtPassword;
    @BindView(3230)
    EditText mEtPhoneOrEmail;
    @BindView(3756)
    TextView mTvForget;
    @BindView(3778)
    TextView mTvLoginSign;

    public static LoginFragment newInstance() {
        Bundle bundle = new Bundle();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_login);
    }

    public void onBindView(Bundle bundle, View view) {
        this.mEtPhoneOrEmail.setText(SpManager.getLastAccount());
    }

    @OnClick({3778, 3070, 3756})
    public void onLogin(View view) {
        int id = view.getId();
        if (id == C1624R.C1628id.bt_login) {
            judInformation();
        } else if (id == C1624R.C1628id.tv_foget_password) {
            start(ResetPasswordFragment.newInstance());
        } else if (id == C1624R.C1628id.tv_login_sign) {
            start(RegisterFragment.newInstance());
        }
    }

    private void judInformation() {
        this.mBtLogin.setEnabled(false);
        String trim = this.mEtPhoneOrEmail.getText().toString().trim();
        String trim2 = this.mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(trim) || TextUtils.isEmpty(trim2)) {
            this.mBtLogin.setEnabled(true);
            showHint(StringUtils.getString(C1624R.string.empty_nameandpassword_dialog));
        } else if (!StringUtils.isPhone(trim) && !StringUtils.isEmail(trim)) {
            this.mBtLogin.setEnabled(true);
            showHint(StringUtils.getString(C1624R.string.username_dialog_err));
        } else if (StringUtils.checkPassword(trim2)) {
            showProgressDialog(StringUtils.getString(C1624R.string.logging), false);
            signIn(trim, trim2);
        } else {
            this.mBtLogin.setEnabled(true);
            showHint(Integer.valueOf(C1624R.string.nameandpasword_dialog_unmatch));
        }
    }

    private void signIn(String str, String str2) {
        if (!NetworkUtils.isNetworkConnected()) {
            showHint(StringUtils.getString(C1624R.string.net_err));
            this.mBtLogin.setEnabled(true);
            disProgressDialog();
            return;
        }
        RestClient.signin(str, str2).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseResult<UserInfoEntity>>() {
            public void onComplete() {
            }

            public void onSubscribe(Disposable disposable) {
                Disposable unused = LoginFragment.this.disposable = disposable;
            }

            public void onNext(ResponseResult<UserInfoEntity> responseResult) {
                int status = responseResult.getStatus();
                if (status == 200) {
                    LoginFragment.this.disProgressDialog();
                    UserInfoEntity data = responseResult.getData();
                    UserInfoTableManager.inserOrReplace(data);
                    Log.d("token_sign", data.getToken());
                    SpManager.setToken(data.getToken());
                    SpManager.setUserId(data.getId());
                    LoginFragment.this.startActivity(new Intent(LoginFragment.this.getActivity(), MainActivity.class));
                    LoginFragment.this._mActivity.finish();
                } else if (status == 205) {
                    LoginFragment.this.mBtLogin.setEnabled(true);
                    LoginFragment.this.disProgressDialog();
                    LoginFragment.this.showHint(StringUtils.getString(C1624R.string.nameandpasword_dialog_unmatch));
                } else if (status == 220) {
                    LoginFragment.this.mBtLogin.setEnabled(true);
                    LoginFragment.this.disProgressDialog();
                    LoginFragment.this.showHint(StringUtils.getString(C1624R.string.user_not_exist));
                } else {
                    LoginFragment.this.mBtLogin.setEnabled(true);
                    LoginFragment.this.disProgressDialog();
                    LoginFragment.this.showHint(StringUtils.getString(C1624R.string.net_err));
                }
            }

            public void onError(Throwable th) {
                LoginFragment.this.mBtLogin.setEnabled(true);
                LoginFragment.this.disProgressDialog();
                Log.d("log_err", th.getMessage());
                LoginFragment.this.showHint(StringUtils.getString(C1624R.string.abnormal_login));
            }
        });
    }

    public void onFragmentResult(int i, int i2, Bundle bundle) {
        super.onFragmentResult(i, i2, bundle);
        if (i == 10 && i2 == -1) {
            Country country = (Country) JsonUtils.formJsonString(bundle.getString(CountryPickerFragment.KEY_COUNTRY), Country.class);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        disProgressDialog();
        Disposable disposable2 = this.disposable;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.disposable.dispose();
        }
    }
}
