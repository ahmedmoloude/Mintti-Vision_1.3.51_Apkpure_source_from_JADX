package com.p020kl.healthmonitor.navigation;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;
import p026de.hdodenhof.circleimageview.CircleImageView;

/* renamed from: com.kl.healthmonitor.navigation.MineFragment_ViewBinding */
public class MineFragment_ViewBinding implements Unbinder {
    private MineFragment target;
    private View view7f090385;
    private View view7f090387;
    private View viewc1b;
    private View viewc3e;
    private View viewecb;
    private View viewefe;
    private View vieweff;
    private View viewf00;
    private View viewf01;
    private View viewf02;

    public MineFragment_ViewBinding(final MineFragment mineFragment, View view) {
        this.target = mineFragment;
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.civ_my_img, "field 'mImgFace' and method 'click'");
        mineFragment.mImgFace = (CircleImageView) Utils.castView(findRequiredView, C1624R.C1628id.civ_my_img, "field 'mImgFace'", CircleImageView.class);
        this.viewc3e = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                mineFragment.click(view);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.tv_my_username, "field 'tvUserName' and method 'click'");
        mineFragment.tvUserName = (TextView) Utils.castView(findRequiredView2, C1624R.C1628id.tv_my_username, "field 'tvUserName'", TextView.class);
        this.viewecb = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                mineFragment.click(view);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, C1624R.C1628id.uiv_problem_feedback, "method 'click'");
        this.viewf00 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                mineFragment.click(view);
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, C1624R.C1628id.uiv_problem_faq, "method 'click'");
        this.vieweff = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                mineFragment.click(view);
            }
        });
        View findRequiredView5 = Utils.findRequiredView(view, C1624R.C1628id.uiv_problem_about, "method 'click'");
        this.viewefe = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                mineFragment.click(view);
            }
        });
        View findRequiredView6 = Utils.findRequiredView(view, C1624R.C1628id.uiv_problem_mydevice, "method 'click'");
        this.viewf01 = findRequiredView6;
        findRequiredView6.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                mineFragment.click(view);
            }
        });
        View findRequiredView7 = Utils.findRequiredView(view, C1624R.C1628id.uiv_guide, "method 'click'");
        this.view7f090387 = findRequiredView7;
        findRequiredView7.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                mineFragment.click(view);
            }
        });
        View findRequiredView8 = Utils.findRequiredView(view, C1624R.C1628id.uiv_add_member, "method 'click'");
        this.view7f090385 = findRequiredView8;
        findRequiredView8.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                mineFragment.click(view);
            }
        });
        View findRequiredView9 = Utils.findRequiredView(view, C1624R.C1628id.uiv_problem_setting, "method 'click'");
        this.viewf02 = findRequiredView9;
        findRequiredView9.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                mineFragment.click(view);
            }
        });
        View findRequiredView10 = Utils.findRequiredView(view, C1624R.C1628id.btn_sign_out, "method 'click'");
        this.viewc1b = findRequiredView10;
        findRequiredView10.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                mineFragment.click(view);
            }
        });
    }

    public void unbind() {
        MineFragment mineFragment = this.target;
        if (mineFragment != null) {
            this.target = null;
            mineFragment.mImgFace = null;
            mineFragment.tvUserName = null;
            this.viewc3e.setOnClickListener((View.OnClickListener) null);
            this.viewc3e = null;
            this.viewecb.setOnClickListener((View.OnClickListener) null);
            this.viewecb = null;
            this.viewf00.setOnClickListener((View.OnClickListener) null);
            this.viewf00 = null;
            this.vieweff.setOnClickListener((View.OnClickListener) null);
            this.vieweff = null;
            this.viewefe.setOnClickListener((View.OnClickListener) null);
            this.viewefe = null;
            this.viewf01.setOnClickListener((View.OnClickListener) null);
            this.viewf01 = null;
            this.view7f090387.setOnClickListener((View.OnClickListener) null);
            this.view7f090387 = null;
            this.view7f090385.setOnClickListener((View.OnClickListener) null);
            this.view7f090385 = null;
            this.viewf02.setOnClickListener((View.OnClickListener) null);
            this.viewf02 = null;
            this.viewc1b.setOnClickListener((View.OnClickListener) null);
            this.viewc1b = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
