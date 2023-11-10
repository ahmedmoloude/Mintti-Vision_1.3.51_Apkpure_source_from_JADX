package com.p020kl.healthmonitor.mine;

import android.view.View;
import android.widget.Switch;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.UserItemView;

/* renamed from: com.kl.healthmonitor.mine.SettingFragment_ViewBinding */
public class SettingFragment_ViewBinding implements Unbinder {
    private SettingFragment target;
    private View view7f090386;
    private View viewf03;
    private View viewf04;
    private View viewf05;

    public SettingFragment_ViewBinding(final SettingFragment settingFragment, View view) {
        this.target = settingFragment;
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.uiv_set_temperature, "field 'uivSetTemp' and method 'onClick'");
        settingFragment.uivSetTemp = (UserItemView) Utils.castView(findRequiredView, C1624R.C1628id.uiv_set_temperature, "field 'uivSetTemp'", UserItemView.class);
        this.viewf04 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                settingFragment.onClick(view);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.uiv_set_time, "field 'uivSetTime' and method 'onClick'");
        settingFragment.uivSetTime = (UserItemView) Utils.castView(findRequiredView2, C1624R.C1628id.uiv_set_time, "field 'uivSetTime'", UserItemView.class);
        this.viewf05 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                settingFragment.onClick(view);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, C1624R.C1628id.uiv_set_gain, "field 'uivSetGain' and method 'onClick'");
        settingFragment.uivSetGain = (UserItemView) Utils.castView(findRequiredView3, C1624R.C1628id.uiv_set_gain, "field 'uivSetGain'", UserItemView.class);
        this.viewf03 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                settingFragment.onClick(view);
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, C1624R.C1628id.uiv_bg_unit, "field 'uivBgUnit' and method 'onClick'");
        settingFragment.uivBgUnit = (UserItemView) Utils.castView(findRequiredView4, C1624R.C1628id.uiv_bg_unit, "field 'uivBgUnit'", UserItemView.class);
        this.view7f090386 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                settingFragment.onClick(view);
            }
        });
        settingFragment.sbWarn = (Switch) Utils.findRequiredViewAsType(view, C1624R.C1628id.sb_warn, "field 'sbWarn'", Switch.class);
    }

    public void unbind() {
        SettingFragment settingFragment = this.target;
        if (settingFragment != null) {
            this.target = null;
            settingFragment.uivSetTemp = null;
            settingFragment.uivSetTime = null;
            settingFragment.uivSetGain = null;
            settingFragment.uivBgUnit = null;
            settingFragment.sbWarn = null;
            this.viewf04.setOnClickListener((View.OnClickListener) null);
            this.viewf04 = null;
            this.viewf05.setOnClickListener((View.OnClickListener) null);
            this.viewf05 = null;
            this.viewf03.setOnClickListener((View.OnClickListener) null);
            this.viewf03 = null;
            this.view7f090386.setOnClickListener((View.OnClickListener) null);
            this.view7f090386 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
