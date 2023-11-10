package com.p020kl.healthmonitor.measure;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.Group;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.commonbase.views.FunctionButton;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.measure.MeasureFragment_ViewBinding */
public class MeasureFragment_ViewBinding implements Unbinder {
    private MeasureFragment target;
    private View view7f0900e0;
    private View view7f090354;
    private View viewcae;
    private View viewcaf;
    private View viewcb0;
    private View viewcb1;
    private View viewcb2;
    private View viewcb3;
    private View viewe98;

    public MeasureFragment_ViewBinding(final MeasureFragment measureFragment, View view) {
        this.target = measureFragment;
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.fbt_ecg, "field 'fbtEcg' and method 'onFbtClicked'");
        measureFragment.fbtEcg = (FunctionButton) Utils.castView(findRequiredView, C1624R.C1628id.fbt_ecg, "field 'fbtEcg'", FunctionButton.class);
        this.viewcb1 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                measureFragment.onFbtClicked(view);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.fbt_temp, "field 'fbtTemperature' and method 'onFbtClicked'");
        measureFragment.fbtTemperature = (FunctionButton) Utils.castView(findRequiredView2, C1624R.C1628id.fbt_temp, "field 'fbtTemperature'", FunctionButton.class);
        this.viewcb3 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                measureFragment.onFbtClicked(view);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, C1624R.C1628id.tv_connect, "field 'tvConnect' and method 'onConnectClicked'");
        measureFragment.tvConnect = (TextView) Utils.castView(findRequiredView3, C1624R.C1628id.tv_connect, "field 'tvConnect'", TextView.class);
        this.viewe98 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                measureFragment.onConnectClicked();
            }
        });
        measureFragment.tvLoadingDot = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_loading_dot, "field 'tvLoadingDot'", TextView.class);
        measureFragment.ivBattery = (ImageView) Utils.findRequiredViewAsType(view, C1624R.C1628id.iv_battery, "field 'ivBattery'", ImageView.class);
        measureFragment.tvBattery = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_battery, "field 'tvBattery'", TextView.class);
        measureFragment.groupBattery = (Group) Utils.findRequiredViewAsType(view, C1624R.C1628id.group_battery, "field 'groupBattery'", Group.class);
        View findRequiredView4 = Utils.findRequiredView(view, C1624R.C1628id.create_pdf, "method 'onCreatePdf'");
        this.view7f0900e0 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                measureFragment.onCreatePdf();
            }
        });
        View findRequiredView5 = Utils.findRequiredView(view, C1624R.C1628id.tv_medical, "method 'onMedicalClick'");
        this.view7f090354 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                measureFragment.onMedicalClick();
            }
        });
        View findRequiredView6 = Utils.findRequiredView(view, C1624R.C1628id.fbt_blood_pressure, "method 'onFbtClicked'");
        this.viewcb0 = findRequiredView6;
        findRequiredView6.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                measureFragment.onFbtClicked(view);
            }
        });
        View findRequiredView7 = Utils.findRequiredView(view, C1624R.C1628id.fbt_blood_oxygen, "method 'onFbtClicked'");
        this.viewcaf = findRequiredView7;
        findRequiredView7.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                measureFragment.onFbtClicked(view);
            }
        });
        View findRequiredView8 = Utils.findRequiredView(view, C1624R.C1628id.fbt_blood_glucose, "method 'onFbtClicked'");
        this.viewcae = findRequiredView8;
        findRequiredView8.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                measureFragment.onFbtClicked(view);
            }
        });
        View findRequiredView9 = Utils.findRequiredView(view, C1624R.C1628id.fbt_rothman, "method 'onFbtClicked'");
        this.viewcb2 = findRequiredView9;
        findRequiredView9.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                measureFragment.onFbtClicked(view);
            }
        });
    }

    public void unbind() {
        MeasureFragment measureFragment = this.target;
        if (measureFragment != null) {
            this.target = null;
            measureFragment.fbtEcg = null;
            measureFragment.fbtTemperature = null;
            measureFragment.tvConnect = null;
            measureFragment.tvLoadingDot = null;
            measureFragment.ivBattery = null;
            measureFragment.tvBattery = null;
            measureFragment.groupBattery = null;
            this.viewcb1.setOnClickListener((View.OnClickListener) null);
            this.viewcb1 = null;
            this.viewcb3.setOnClickListener((View.OnClickListener) null);
            this.viewcb3 = null;
            this.viewe98.setOnClickListener((View.OnClickListener) null);
            this.viewe98 = null;
            this.view7f0900e0.setOnClickListener((View.OnClickListener) null);
            this.view7f0900e0 = null;
            this.view7f090354.setOnClickListener((View.OnClickListener) null);
            this.view7f090354 = null;
            this.viewcb0.setOnClickListener((View.OnClickListener) null);
            this.viewcb0 = null;
            this.viewcaf.setOnClickListener((View.OnClickListener) null);
            this.viewcaf = null;
            this.viewcae.setOnClickListener((View.OnClickListener) null);
            this.viewcae = null;
            this.viewcb2.setOnClickListener((View.OnClickListener) null);
            this.viewcb2 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
