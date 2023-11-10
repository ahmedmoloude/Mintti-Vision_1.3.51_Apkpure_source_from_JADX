package com.p020kl.healthmonitor.measure.rothmanindex;

import android.view.View;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.RothmanIndexTrendsFragment_ViewBinding */
public class RothmanIndexTrendsFragment_ViewBinding implements Unbinder {
    private RothmanIndexTrendsFragment target;

    public RothmanIndexTrendsFragment_ViewBinding(RothmanIndexTrendsFragment rothmanIndexTrendsFragment, View view) {
        this.target = rothmanIndexTrendsFragment;
        rothmanIndexTrendsFragment.cardView = (CardView) Utils.findRequiredViewAsType(view, C1624R.C1628id.cv_card, "field 'cardView'", CardView.class);
        rothmanIndexTrendsFragment.recyclerView = (RecyclerView) Utils.findRequiredViewAsType(view, C1624R.C1628id.rv_rothman_index, "field 'recyclerView'", RecyclerView.class);
        rothmanIndexTrendsFragment.lineChartLayout = (LineChartLayout) Utils.findRequiredViewAsType(view, C1624R.C1628id.line_chart_view, "field 'lineChartLayout'", LineChartLayout.class);
    }

    public void unbind() {
        RothmanIndexTrendsFragment rothmanIndexTrendsFragment = this.target;
        if (rothmanIndexTrendsFragment != null) {
            this.target = null;
            rothmanIndexTrendsFragment.cardView = null;
            rothmanIndexTrendsFragment.recyclerView = null;
            rothmanIndexTrendsFragment.lineChartLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
