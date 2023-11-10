package com.p020kl.healthmonitor.sign;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.sign.LoginFragment_ViewBinding */
public class LoginFragment_ViewBinding implements Unbinder {
    private LoginFragment target;
    private View viewbfe;
    private View vieweac;
    private View viewec2;

    public LoginFragment_ViewBinding(final LoginFragment loginFragment, View view) {
        this.target = loginFragment;
        loginFragment.mEtPhoneOrEmail = (EditText) Utils.findRequiredViewAsType(view, C1624R.C1628id.et_login_phone, "field 'mEtPhoneOrEmail'", EditText.class);
        loginFragment.mEtPassword = (EditText) Utils.findRequiredViewAsType(view, C1624R.C1628id.et_login_password, "field 'mEtPassword'", EditText.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.bt_login, "field 'mBtLogin' and method 'onLogin'");
        loginFragment.mBtLogin = (Button) Utils.castView(findRequiredView, C1624R.C1628id.bt_login, "field 'mBtLogin'", Button.class);
        this.viewbfe = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                loginFragment.onLogin(view);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.tv_login_sign, "field 'mTvLoginSign' and method 'onLogin'");
        loginFragment.mTvLoginSign = (TextView) Utils.castView(findRequiredView2, C1624R.C1628id.tv_login_sign, "field 'mTvLoginSign'", TextView.class);
        this.viewec2 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                loginFragment.onLogin(view);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, C1624R.C1628id.tv_foget_password, "field 'mTvForget' and method 'onLogin'");
        loginFragment.mTvForget = (TextView) Utils.castView(findRequiredView3, C1624R.C1628id.tv_foget_password, "field 'mTvForget'", TextView.class);
        this.vieweac = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                loginFragment.onLogin(view);
            }
        });
    }

    public void unbind() {
        LoginFragment loginFragment = this.target;
        if (loginFragment != null) {
            this.target = null;
            loginFragment.mEtPhoneOrEmail = null;
            loginFragment.mEtPassword = null;
            loginFragment.mBtLogin = null;
            loginFragment.mTvLoginSign = null;
            loginFragment.mTvForget = null;
            this.viewbfe.setOnClickListener((View.OnClickListener) null);
            this.viewbfe = null;
            this.viewec2.setOnClickListener((View.OnClickListener) null);
            this.viewec2 = null;
            this.vieweac.setOnClickListener((View.OnClickListener) null);
            this.vieweac = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
