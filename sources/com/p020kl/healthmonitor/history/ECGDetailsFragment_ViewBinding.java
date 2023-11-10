package com.p020kl.healthmonitor.history;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.p020kl.commonbase.views.ecg.EcgWaveView;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.MeasureItemLevelView;

/* renamed from: com.kl.healthmonitor.history.ECGDetailsFragment_ViewBinding */
public class ECGDetailsFragment_ViewBinding implements Unbinder {
    private ECGDetailsFragment target;

    public ECGDetailsFragment_ViewBinding(ECGDetailsFragment eCGDetailsFragment, View view) {
        this.target = eCGDetailsFragment;
        eCGDetailsFragment.ecgWaveView = (EcgWaveView) Utils.findRequiredViewAsType(view, C1624R.C1628id.ecg_wave_view, "field 'ecgWaveView'", EcgWaveView.class);
        eCGDetailsFragment.mlvEcgPaperSpeed = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_paper_speed, "field 'mlvEcgPaperSpeed'", MeasureItemLevelView.class);
        eCGDetailsFragment.mlvEcgGain = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_gain, "field 'mlvEcgGain'", MeasureItemLevelView.class);
        eCGDetailsFragment.mlvCreateTime = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.miv_ecg_create_time, "field 'mlvCreateTime'", MeasureItemLevelView.class);
        eCGDetailsFragment.mlvDuration = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.miv_ecg_duration, "field 'mlvDuration'", MeasureItemLevelView.class);
        eCGDetailsFragment.mlvRRMax = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_rrimax, "field 'mlvRRMax'", MeasureItemLevelView.class);
        eCGDetailsFragment.mlvRRMin = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_rrimin, "field 'mlvRRMin'", MeasureItemLevelView.class);
        eCGDetailsFragment.mlvAvgHr = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_heart_rate, "field 'mlvAvgHr'", MeasureItemLevelView.class);
        eCGDetailsFragment.mlvHrv = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_hrv, "field 'mlvHrv'", MeasureItemLevelView.class);
        eCGDetailsFragment.mlvBr = (MeasureItemLevelView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mlv_ecg_br, "field 'mlvBr'", MeasureItemLevelView.class);
    }

    public void unbind() {
        ECGDetailsFragment eCGDetailsFragment = this.target;
        if (eCGDetailsFragment != null) {
            this.target = null;
            eCGDetailsFragment.ecgWaveView = null;
            eCGDetailsFragment.mlvEcgPaperSpeed = null;
            eCGDetailsFragment.mlvEcgGain = null;
            eCGDetailsFragment.mlvCreateTime = null;
            eCGDetailsFragment.mlvDuration = null;
            eCGDetailsFragment.mlvRRMax = null;
            eCGDetailsFragment.mlvRRMin = null;
            eCGDetailsFragment.mlvAvgHr = null;
            eCGDetailsFragment.mlvHrv = null;
            eCGDetailsFragment.mlvBr = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
