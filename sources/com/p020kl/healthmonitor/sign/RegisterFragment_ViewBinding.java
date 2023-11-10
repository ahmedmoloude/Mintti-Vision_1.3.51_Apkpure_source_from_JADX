package com.p020kl.healthmonitor.sign;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.sign.RegisterFragment_ViewBinding */
public class RegisterFragment_ViewBinding implements Unbinder {
    private RegisterFragment target;
    private View view7f090378;
    private View viewc01;
    private View viewc04;
    private View viewd3e;
    private View viewed5;

    public RegisterFragment_ViewBinding(final RegisterFragment registerFragment, View view) {
        this.target = registerFragment;
        registerFragment.mTvCountry = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_country, "field 'mTvCountry'", TextView.class);
        registerFragment.mTvPhoneId = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_phone_id, "field 'mTvPhoneId'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.bt_send_code, "field 'mSendCode' and method 'onClick'");
        registerFragment.mSendCode = (Button) Utils.castView(findRequiredView, C1624R.C1628id.bt_send_code, "field 'mSendCode'", Button.class);
        this.viewc04 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                registerFragment.onClick(view);
            }
        });
        registerFragment.mEtPhone = (EditText) Utils.findRequiredViewAsType(view, C1624R.C1628id.et_phone, "field 'mEtPhone'", EditText.class);
        registerFragment.mEtCode = (EditText) Utils.findRequiredViewAsType(view, C1624R.C1628id.et_code, "field 'mEtCode'", EditText.class);
        registerFragment.mEtPassword = (EditText) Utils.findRequiredViewAsType(view, C1624R.C1628id.et_register_password, "field 'mEtPassword'", EditText.class);
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.bt_register, "field 'mRegister' and method 'onClick'");
        registerFragment.mRegister = (Button) Utils.castView(findRequiredView2, C1624R.C1628id.bt_register, "field 'mRegister'", Button.class);
        this.viewc01 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                registerFragment.onClick(view);
            }
        });
        registerFragment.checkBoxAgreement = (AppCompatCheckBox) Utils.findRequiredViewAsType(view, C1624R.C1628id.checkbox_agreement, "field 'checkBoxAgreement'", AppCompatCheckBox.class);
        View findRequiredView3 = Utils.findRequiredView(view, C1624R.C1628id.tv_user_aggrement, "method 'onClick'");
        this.view7f090378 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                registerFragment.onClick(view);
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, C1624R.C1628id.ll_country_select, "method 'onClick'");
        this.viewd3e = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                registerFragment.onClick(view);
            }
        });
        View findRequiredView5 = Utils.findRequiredView(view, C1624R.C1628id.tv_privacy_policy, "method 'onClick'");
        this.viewed5 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                registerFragment.onClick(view);
            }
        });
    }

    public void unbind() {
        RegisterFragment registerFragment = this.target;
        if (registerFragment != null) {
            this.target = null;
            registerFragment.mTvCountry = null;
            registerFragment.mTvPhoneId = null;
            registerFragment.mSendCode = null;
            registerFragment.mEtPhone = null;
            registerFragment.mEtCode = null;
            registerFragment.mEtPassword = null;
            registerFragment.mRegister = null;
            registerFragment.checkBoxAgreement = null;
            this.viewc04.setOnClickListener((View.OnClickListener) null);
            this.viewc04 = null;
            this.viewc01.setOnClickListener((View.OnClickListener) null);
            this.viewc01 = null;
            this.view7f090378.setOnClickListener((View.OnClickListener) null);
            this.view7f090378 = null;
            this.viewd3e.setOnClickListener((View.OnClickListener) null);
            this.viewd3e = null;
            this.viewed5.setOnClickListener((View.OnClickListener) null);
            this.viewed5 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
