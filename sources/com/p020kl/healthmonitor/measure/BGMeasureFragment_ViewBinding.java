package com.p020kl.healthmonitor.measure;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatSpinner;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.commonbase.views.PickerScrollView;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.CircleBarView;
import com.p020kl.healthmonitor.views.CustomResultProgressBar;

/* renamed from: com.kl.healthmonitor.measure.BGMeasureFragment_ViewBinding */
public class BGMeasureFragment_ViewBinding implements Unbinder {
    private BGMeasureFragment target;
    private View viewc0b;

    public BGMeasureFragment_ViewBinding(final BGMeasureFragment bGMeasureFragment, View view) {
        this.target = bGMeasureFragment;
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.btn_bg_measure, "field 'btnMeasure' and method 'onBgMeasureClicked'");
        bGMeasureFragment.btnMeasure = (Button) Utils.castView(findRequiredView, C1624R.C1628id.btn_bg_measure, "field 'btnMeasure'", Button.class);
        this.viewc0b = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                bGMeasureFragment.onBgMeasureClicked();
            }
        });
        bGMeasureFragment.llFirstPage = (LinearLayout) Utils.findRequiredViewAsType(view, C1624R.C1628id.ll_first_page, "field 'llFirstPage'", LinearLayout.class);
        bGMeasureFragment.circleBarView = (CircleBarView) Utils.findRequiredViewAsType(view, C1624R.C1628id.circle_bar_view, "field 'circleBarView'", CircleBarView.class);
        bGMeasureFragment.tvMeasureTime = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_measure_time, "field 'tvMeasureTime'", TextView.class);
        bGMeasureFragment.tvBgValue = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_bg_value, "field 'tvBgValue'", TextView.class);
        bGMeasureFragment.tvBgUnit = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_bg_unit, "field 'tvBgUnit'", TextView.class);
        bGMeasureFragment.tvResult = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_bg_result, "field 'tvResult'", TextView.class);
        bGMeasureFragment.resultProgressBar = (CustomResultProgressBar) Utils.findRequiredViewAsType(view, C1624R.C1628id.result_progressbar_bg, "field 'resultProgressBar'", CustomResultProgressBar.class);
        bGMeasureFragment.llSelectMeasureTime = (LinearLayout) Utils.findRequiredViewAsType(view, C1624R.C1628id.ll_select_measure_time, "field 'llSelectMeasureTime'", LinearLayout.class);
        bGMeasureFragment.rgMeasureTime = (RadioGroup) Utils.findRequiredViewAsType(view, C1624R.C1628id.rg_measure_time, "field 'rgMeasureTime'", RadioGroup.class);
        bGMeasureFragment.llSelectStrip = (LinearLayout) Utils.findRequiredViewAsType(view, C1624R.C1628id.ll_strip_code, "field 'llSelectStrip'", LinearLayout.class);
        bGMeasureFragment.psvStripCode = (PickerScrollView) Utils.findRequiredViewAsType(view, C1624R.C1628id.picker_view_code, "field 'psvStripCode'", PickerScrollView.class);
        bGMeasureFragment.tvStripCode = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_strip_code, "field 'tvStripCode'", TextView.class);
        bGMeasureFragment.llMeasuring = (LinearLayout) Utils.findRequiredViewAsType(view, C1624R.C1628id.ll_measuring, "field 'llMeasuring'", LinearLayout.class);
        bGMeasureFragment.ivMeasure = (ImageView) Utils.findRequiredViewAsType(view, C1624R.C1628id.iv_bg_measuring, "field 'ivMeasure'", ImageView.class);
        bGMeasureFragment.tvMeasureHint = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_bg_measuring_hint, "field 'tvMeasureHint'", TextView.class);
        bGMeasureFragment.tvBgCountDown = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_bg_result_count_down, "field 'tvBgCountDown'", TextView.class);
        bGMeasureFragment.spinTestPaperManufacturer = (AppCompatSpinner) Utils.findRequiredViewAsType(view, C1624R.C1628id.spin_test_paper_manufacturer, "field 'spinTestPaperManufacturer'", AppCompatSpinner.class);
    }

    public void unbind() {
        BGMeasureFragment bGMeasureFragment = this.target;
        if (bGMeasureFragment != null) {
            this.target = null;
            bGMeasureFragment.btnMeasure = null;
            bGMeasureFragment.llFirstPage = null;
            bGMeasureFragment.circleBarView = null;
            bGMeasureFragment.tvMeasureTime = null;
            bGMeasureFragment.tvBgValue = null;
            bGMeasureFragment.tvBgUnit = null;
            bGMeasureFragment.tvResult = null;
            bGMeasureFragment.resultProgressBar = null;
            bGMeasureFragment.llSelectMeasureTime = null;
            bGMeasureFragment.rgMeasureTime = null;
            bGMeasureFragment.llSelectStrip = null;
            bGMeasureFragment.psvStripCode = null;
            bGMeasureFragment.tvStripCode = null;
            bGMeasureFragment.llMeasuring = null;
            bGMeasureFragment.ivMeasure = null;
            bGMeasureFragment.tvMeasureHint = null;
            bGMeasureFragment.tvBgCountDown = null;
            bGMeasureFragment.spinTestPaperManufacturer = null;
            this.viewc0b.setOnClickListener((View.OnClickListener) null);
            this.viewc0b = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
