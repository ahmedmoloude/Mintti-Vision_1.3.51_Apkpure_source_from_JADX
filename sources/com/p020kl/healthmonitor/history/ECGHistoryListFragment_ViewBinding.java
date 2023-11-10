package com.p020kl.healthmonitor.history;

import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.NormalDataView;

/* renamed from: com.kl.healthmonitor.history.ECGHistoryListFragment_ViewBinding */
public class ECGHistoryListFragment_ViewBinding implements Unbinder {
    private ECGHistoryListFragment target;

    public ECGHistoryListFragment_ViewBinding(ECGHistoryListFragment eCGHistoryListFragment, View view) {
        this.target = eCGHistoryListFragment;
        eCGHistoryListFragment.rvHistory = (RecyclerView) Utils.findRequiredViewAsType(view, C1624R.C1628id.rv_bt_list, "field 'rvHistory'", RecyclerView.class);
        eCGHistoryListFragment.normalData = (NormalDataView) Utils.findRequiredViewAsType(view, C1624R.C1628id.ndv_normal_data, "field 'normalData'", NormalDataView.class);
        eCGHistoryListFragment.llBottomBar = (LinearLayout) Utils.findRequiredViewAsType(view, C1624R.C1628id.ll_bottom_bar, "field 'llBottomBar'", LinearLayout.class);
        eCGHistoryListFragment.vBottom = Utils.findRequiredView(view, C1624R.C1628id.v_bottom, "field 'vBottom'");
    }

    public void unbind() {
        ECGHistoryListFragment eCGHistoryListFragment = this.target;
        if (eCGHistoryListFragment != null) {
            this.target = null;
            eCGHistoryListFragment.rvHistory = null;
            eCGHistoryListFragment.normalData = null;
            eCGHistoryListFragment.llBottomBar = null;
            eCGHistoryListFragment.vBottom = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
