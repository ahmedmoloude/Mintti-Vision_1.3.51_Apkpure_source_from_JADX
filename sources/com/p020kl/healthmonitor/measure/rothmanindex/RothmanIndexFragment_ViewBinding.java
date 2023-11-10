package com.p020kl.healthmonitor.measure.rothmanindex;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.RothmanIndexFragment_ViewBinding */
public class RothmanIndexFragment_ViewBinding implements Unbinder {
    private RothmanIndexFragment target;
    private View viewbff;
    private View viewc09;

    public RothmanIndexFragment_ViewBinding(final RothmanIndexFragment rothmanIndexFragment, View view) {
        this.target = rothmanIndexFragment;
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.bt_trends, "method 'onClicked'");
        this.viewc09 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                rothmanIndexFragment.onClicked(view);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.bt_observations, "method 'onClicked'");
        this.viewbff = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                rothmanIndexFragment.onClicked(view);
            }
        });
    }

    public void unbind() {
        if (this.target != null) {
            this.target = null;
            this.viewc09.setOnClickListener((View.OnClickListener) null);
            this.viewc09 = null;
            this.viewbff.setOnClickListener((View.OnClickListener) null);
            this.viewbff = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
