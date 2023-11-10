package com.p020kl.healthmonitor.measure;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.CustomResultProgressBar;
import com.p020kl.healthmonitor.views.MeasureItemView;

/* renamed from: com.kl.healthmonitor.measure.BPMeasureFragment_ViewBinding */
public class BPMeasureFragment_ViewBinding implements Unbinder {
    private BPMeasureFragment target;
    private View viewbfa;

    public BPMeasureFragment_ViewBinding(final BPMeasureFragment bPMeasureFragment, View view) {
        this.target = bPMeasureFragment;
        bPMeasureFragment.tvMeasureProject = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_measure_project, "field 'tvMeasureProject'", TextView.class);
        bPMeasureFragment.tvMeasureValueTop = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_measure_value_top, "field 'tvMeasureValueTop'", TextView.class);
        bPMeasureFragment.tvMeasureResult = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_measure_result, "field 'tvMeasureResult'", TextView.class);
        bPMeasureFragment.mivSPItem = (MeasureItemView) Utils.findRequiredViewAsType(view, C1624R.C1628id.miv_bp_sp, "field 'mivSPItem'", MeasureItemView.class);
        bPMeasureFragment.mivDPItem = (MeasureItemView) Utils.findRequiredViewAsType(view, C1624R.C1628id.miv_bp_dp, "field 'mivDPItem'", MeasureItemView.class);
        bPMeasureFragment.mivHRItem = (MeasureItemView) Utils.findRequiredViewAsType(view, C1624R.C1628id.miv_bp_hr, "field 'mivHRItem'", MeasureItemView.class);
        bPMeasureFragment.ivImg1 = (ImageView) Utils.findRequiredViewAsType(view, C1624R.C1628id.iv_bp_img1, "field 'ivImg1'", ImageView.class);
        bPMeasureFragment.ivImg2 = (ImageView) Utils.findRequiredViewAsType(view, C1624R.C1628id.iv_bp_img2, "field 'ivImg2'", ImageView.class);
        bPMeasureFragment.ivImg3 = (ImageView) Utils.findRequiredViewAsType(view, C1624R.C1628id.iv_bp_img3, "field 'ivImg3'", ImageView.class);
        bPMeasureFragment.tvItemSp = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_dp_itemtop, "field 'tvItemSp'", TextView.class);
        bPMeasureFragment.tvItemDp = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_dp_itemblw, "field 'tvItemDp'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.bt_bp_measure, "field 'btMeasure' and method 'onClick'");
        bPMeasureFragment.btMeasure = (Button) Utils.castView(findRequiredView, C1624R.C1628id.bt_bp_measure, "field 'btMeasure'", Button.class);
        this.viewbfa = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                bPMeasureFragment.onClick();
            }
        });
        bPMeasureFragment.resultProgressBarSp = (CustomResultProgressBar) Utils.findRequiredViewAsType(view, C1624R.C1628id.result_progressbar_sp, "field 'resultProgressBarSp'", CustomResultProgressBar.class);
        bPMeasureFragment.resultProgressBarDp = (CustomResultProgressBar) Utils.findRequiredViewAsType(view, C1624R.C1628id.result_progressbar_dp, "field 'resultProgressBarDp'", CustomResultProgressBar.class);
    }

    public void unbind() {
        BPMeasureFragment bPMeasureFragment = this.target;
        if (bPMeasureFragment != null) {
            this.target = null;
            bPMeasureFragment.tvMeasureProject = null;
            bPMeasureFragment.tvMeasureValueTop = null;
            bPMeasureFragment.tvMeasureResult = null;
            bPMeasureFragment.mivSPItem = null;
            bPMeasureFragment.mivDPItem = null;
            bPMeasureFragment.mivHRItem = null;
            bPMeasureFragment.ivImg1 = null;
            bPMeasureFragment.ivImg2 = null;
            bPMeasureFragment.ivImg3 = null;
            bPMeasureFragment.tvItemSp = null;
            bPMeasureFragment.tvItemDp = null;
            bPMeasureFragment.btMeasure = null;
            bPMeasureFragment.resultProgressBarSp = null;
            bPMeasureFragment.resultProgressBarDp = null;
            this.viewbfa.setOnClickListener((View.OnClickListener) null);
            this.viewbfa = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
