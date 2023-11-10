package com.p020kl.healthmonitor.measure;

import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.commonbase.views.ecg.ChartView;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.MeasureItemLevelView;

/* renamed from: com.kl.healthmonitor.measure.ECGMeasureFragment_ViewBinding */
public class ECGMeasureFragment_ViewBinding implements Unbinder {
    private ECGMeasureFragment target;
    private View viewbfd;

    public ECGMeasureFragment_ViewBinding(final ECGMeasureFragment eCGMeasureFragment, View view) {
        this.target = eCGMeasureFragment;
        eCGMeasureFragment.mivPaperSpeed = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_timebase, "field 'mivPaperSpeed'", MeasureItemLevelView.class);
        eCGMeasureFragment.mivGain = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_gain, "field 'mivGain'", MeasureItemLevelView.class);
        eCGMeasureFragment.mivPRImax = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_primax, "field 'mivPRImax'", MeasureItemLevelView.class);
        eCGMeasureFragment.mivHR = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_heart_rate, "field 'mivHR'", MeasureItemLevelView.class);
        eCGMeasureFragment.mivPRImin = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_primin, "field 'mivPRImin'", MeasureItemLevelView.class);
        eCGMeasureFragment.mivHRV = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_hrv, "field 'mivHRV'", MeasureItemLevelView.class);
        eCGMeasureFragment.mivBr = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_br, "field 'mivBr'", MeasureItemLevelView.class);
        eCGMeasureFragment.ecgView = (ChartView) Utils.findRequiredViewAsType(view, C1624R.C1628id.ecg_chart_view, "field 'ecgView'", ChartView.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.bt_ecg_measure, "field 'btnEcgMeasure' and method 'onClick'");
        eCGMeasureFragment.btnEcgMeasure = (Button) Utils.castView(findRequiredView, C1624R.C1628id.bt_ecg_measure, "field 'btnEcgMeasure'", Button.class);
        this.viewbfd = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                eCGMeasureFragment.onClick();
            }
        });
        eCGMeasureFragment.mivDuration = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_duration, "field 'mivDuration'", MeasureItemLevelView.class);
    }

    public void unbind() {
        ECGMeasureFragment eCGMeasureFragment = this.target;
        if (eCGMeasureFragment != null) {
            this.target = null;
            eCGMeasureFragment.mivPaperSpeed = null;
            eCGMeasureFragment.mivGain = null;
            eCGMeasureFragment.mivPRImax = null;
            eCGMeasureFragment.mivHR = null;
            eCGMeasureFragment.mivPRImin = null;
            eCGMeasureFragment.mivHRV = null;
            eCGMeasureFragment.mivBr = null;
            eCGMeasureFragment.ecgView = null;
            eCGMeasureFragment.btnEcgMeasure = null;
            eCGMeasureFragment.mivDuration = null;
            this.viewbfd.setOnClickListener((View.OnClickListener) null);
            this.viewbfd = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
