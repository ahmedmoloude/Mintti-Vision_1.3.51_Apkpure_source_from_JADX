package com.p020kl.healthmonitor.measure.rothmanindex;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.ObservationsFragment_ViewBinding */
public class ObservationsFragment_ViewBinding implements Unbinder {
    private ObservationsFragment target;
    private View viewbfa;
    private View viewc00;
    private View viewc02;
    private View viewc06;
    private View viewc07;

    public ObservationsFragment_ViewBinding(final ObservationsFragment observationsFragment, View view) {
        this.target = observationsFragment;
        observationsFragment.tvBpValue = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_bp_value, "field 'tvBpValue'", TextView.class);
        observationsFragment.tvSpo2HrValue = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_spo_value, "field 'tvSpo2HrValue'", TextView.class);
        observationsFragment.tvBtValue = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_temperature_value, "field 'tvBtValue'", TextView.class);
        observationsFragment.tvRespiratory = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_respiratory_value, "field 'tvRespiratory'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.bt_bp_measure, "method 'onButtonClicked'");
        this.viewbfa = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                observationsFragment.onButtonClicked(view);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.bt_spo_measure, "method 'onButtonClicked'");
        this.viewc06 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                observationsFragment.onButtonClicked(view);
            }
        });
        View findRequiredView3 = Utils.findRequiredView(view, C1624R.C1628id.bt_temperature_measure, "method 'onButtonClicked'");
        this.viewc07 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                observationsFragment.onButtonClicked(view);
            }
        });
        View findRequiredView4 = Utils.findRequiredView(view, C1624R.C1628id.bt_respiratory_measure, "method 'onButtonClicked'");
        this.viewc02 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                observationsFragment.onButtonClicked(view);
            }
        });
        View findRequiredView5 = Utils.findRequiredView(view, C1624R.C1628id.bt_observations_submit, "method 'onButtonClicked'");
        this.viewc00 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                observationsFragment.onButtonClicked(view);
            }
        });
    }

    public void unbind() {
        ObservationsFragment observationsFragment = this.target;
        if (observationsFragment != null) {
            this.target = null;
            observationsFragment.tvBpValue = null;
            observationsFragment.tvSpo2HrValue = null;
            observationsFragment.tvBtValue = null;
            observationsFragment.tvRespiratory = null;
            this.viewbfa.setOnClickListener((View.OnClickListener) null);
            this.viewbfa = null;
            this.viewc06.setOnClickListener((View.OnClickListener) null);
            this.viewc06 = null;
            this.viewc07.setOnClickListener((View.OnClickListener) null);
            this.viewc07 = null;
            this.viewc02.setOnClickListener((View.OnClickListener) null);
            this.viewc02 = null;
            this.viewc00.setOnClickListener((View.OnClickListener) null);
            this.viewc00 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
