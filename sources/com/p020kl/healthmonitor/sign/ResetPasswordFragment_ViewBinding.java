package com.p020kl.healthmonitor.sign;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.sign.ResetPasswordFragment_ViewBinding */
public class ResetPasswordFragment_ViewBinding implements Unbinder {
    private ResetPasswordFragment target;
    private View viewbf9;
    private View viewc04;
    private View viewd3e;

    public ResetPasswordFragment_ViewBinding(final ResetPasswordFragment resetPasswordFragment, View view) {
        this.target = resetPasswordFragment;
        resetPasswordFragment.mTvCountry = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_country, "field 'mTvCountry'", TextView.class);
        resetPasswordFragment.mTvPhoneId = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_phone_id, "field 'mTvPhoneId'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.bt_send_code, "field 'mSendCode' and method 'onClick'");
        resetPasswordFragment.mSendCode = (Button) Utils.castView(findRequiredView, C1624R.C1628id.bt_send_code, "field 'mSendCode'", Button.class);
        this.viewc04 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                resetPasswordFragment.onClick(view);
            }
        });
        resetPasswordFragment.mEtPhone = (EditText) Utils.findRequiredViewAsType(view, C1624R.C1628id.et_phone, "field 'mEtPhone'", EditText.class);
        resetPasswordFragment.mEtCode = (EditText) Utils.findRequiredViewAsType(view, C1624R.C1628id.et_code, "field 'mEtCode'", EditText.class);
        resetPasswordFragment.mEtPassword = (EditText) Utils.findRequiredViewAsType(view, C1624R.C1628id.et_new_password, "field 'mEtPassword'", EditText.class);
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.bt_affirm, "field 'mAffirm' and method 'onClick'");
        resetPasswordFragment.mAffirm = (Button) Utils.castView(findRequiredView2, C1624R.C1628id.bt_affirm, "field 'mAffirm'", Button.class);
        this.viewbf9 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                resetPasswordFragment.onClick(view);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, C1624R.C1628id.ll_country_select, "method 'onClick'");
        this.viewd3e = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                resetPasswordFragment.onClick(view);
            }
        });
    }

    public void unbind() {
        ResetPasswordFragment resetPasswordFragment = this.target;
        if (resetPasswordFragment != null) {
            this.target = null;
            resetPasswordFragment.mTvCountry = null;
            resetPasswordFragment.mTvPhoneId = null;
            resetPasswordFragment.mSendCode = null;
            resetPasswordFragment.mEtPhone = null;
            resetPasswordFragment.mEtCode = null;
            resetPasswordFragment.mEtPassword = null;
            resetPasswordFragment.mAffirm = null;
            this.viewc04.setOnClickListener((View.OnClickListener) null);
            this.viewc04 = null;
            this.viewbf9.setOnClickListener((View.OnClickListener) null);
            this.viewbf9 = null;
            this.viewd3e.setOnClickListener((View.OnClickListener) null);
            this.viewd3e = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
