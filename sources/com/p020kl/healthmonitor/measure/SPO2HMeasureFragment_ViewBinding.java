package com.p020kl.healthmonitor.measure;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.CustomResultProgressBar;
import com.p020kl.healthmonitor.views.MeasureItemView;
import com.p020kl.healthmonitor.views.WaveView;

/* renamed from: com.kl.healthmonitor.measure.SPO2HMeasureFragment_ViewBinding */
public class SPO2HMeasureFragment_ViewBinding implements Unbinder {
    private SPO2HMeasureFragment target;
    private View viewc05;

    public SPO2HMeasureFragment_ViewBinding(final SPO2HMeasureFragment sPO2HMeasureFragment, View view) {
        this.target = sPO2HMeasureFragment;
        sPO2HMeasureFragment.oxWaveView = (WaveView) Utils.findRequiredViewAsType(view, C1624R.C1628id.ox_wave_view, "field 'oxWaveView'", WaveView.class);
        sPO2HMeasureFragment.tvIsNormal = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_isnormal, "field 'tvIsNormal'", TextView.class);
        sPO2HMeasureFragment.mivBloodOxygen = (MeasureItemView) Utils.findRequiredViewAsType(view, C1624R.C1628id.miv_spo2h_bo, "field 'mivBloodOxygen'", MeasureItemView.class);
        sPO2HMeasureFragment.mivHeartRate = (MeasureItemView) Utils.findRequiredViewAsType(view, C1624R.C1628id.miv_spo2h_hr, "field 'mivHeartRate'", MeasureItemView.class);
        sPO2HMeasureFragment.resultProgressBar = (CustomResultProgressBar) Utils.findRequiredViewAsType(view, C1624R.C1628id.result_progressbar, "field 'resultProgressBar'", CustomResultProgressBar.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.bt_spo2h_measure, "field 'btMeasure' and method 'onClick'");
        sPO2HMeasureFragment.btMeasure = (Button) Utils.castView(findRequiredView, C1624R.C1628id.bt_spo2h_measure, "field 'btMeasure'", Button.class);
        this.viewc05 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                sPO2HMeasureFragment.onClick();
            }
        });
    }

    public void unbind() {
        SPO2HMeasureFragment sPO2HMeasureFragment = this.target;
        if (sPO2HMeasureFragment != null) {
            this.target = null;
            sPO2HMeasureFragment.oxWaveView = null;
            sPO2HMeasureFragment.tvIsNormal = null;
            sPO2HMeasureFragment.mivBloodOxygen = null;
            sPO2HMeasureFragment.mivHeartRate = null;
            sPO2HMeasureFragment.resultProgressBar = null;
            sPO2HMeasureFragment.btMeasure = null;
            this.viewc05.setOnClickListener((View.OnClickListener) null);
            this.viewc05 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
