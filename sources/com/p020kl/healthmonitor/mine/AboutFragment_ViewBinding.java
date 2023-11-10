package com.p020kl.healthmonitor.mine;

import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.mine.AboutFragment_ViewBinding */
public class AboutFragment_ViewBinding implements Unbinder {
    private AboutFragment target;
    private View viewc40;
    private View viewc43;

    public AboutFragment_ViewBinding(final AboutFragment aboutFragment, View view) {
        this.target = aboutFragment;
        aboutFragment.tvSoftVersion = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_soft_version, "field 'tvSoftVersion'", TextView.class);
        aboutFragment.tvUpdateInfo = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_update_info, "field 'tvUpdateInfo'", TextView.class);
        aboutFragment.clCheckUpdate = (ConstraintLayout) Utils.findRequiredViewAsType(view, C1624R.C1628id.cl_check_update, "field 'clCheckUpdate'", ConstraintLayout.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.cl_disclaimer, "method 'onPrivacyPolicyClicked'");
        this.viewc40 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                aboutFragment.onPrivacyPolicyClicked();
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.cl_user_agreement, "method 'onUserAgreementClicked'");
        this.viewc43 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                aboutFragment.onUserAgreementClicked();
            }
        });
    }

    public void unbind() {
        AboutFragment aboutFragment = this.target;
        if (aboutFragment != null) {
            this.target = null;
            aboutFragment.tvSoftVersion = null;
            aboutFragment.tvUpdateInfo = null;
            aboutFragment.clCheckUpdate = null;
            this.viewc40.setOnClickListener((View.OnClickListener) null);
            this.viewc40 = null;
            this.viewc43.setOnClickListener((View.OnClickListener) null);
            this.viewc43 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
