package com.p020kl.healthmonitor.history;

import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.NormalDataView;

/* renamed from: com.kl.healthmonitor.history.BaseListHistoryFragment_ViewBinding */
public class BaseListHistoryFragment_ViewBinding implements Unbinder {
    private BaseListHistoryFragment target;
    private View viewbd1;
    private View viewbd2;

    public BaseListHistoryFragment_ViewBinding(final BaseListHistoryFragment baseListHistoryFragment, View view) {
        this.target = baseListHistoryFragment;
        baseListHistoryFragment.rvHistoryList = (RecyclerView) Utils.findRequiredViewAsType(view, C1624R.C1628id.rv_bt_list, "field 'rvHistoryList'", RecyclerView.class);
        baseListHistoryFragment.normalData = (NormalDataView) Utils.findRequiredViewAsType(view, C1624R.C1628id.ndv_normal_data, "field 'normalData'", NormalDataView.class);
        baseListHistoryFragment.llBottomBar = (LinearLayout) Utils.findRequiredViewAsType(view, C1624R.C1628id.ll_bottom_bar, "field 'llBottomBar'", LinearLayout.class);
        baseListHistoryFragment.vBottom = Utils.findRequiredView(view, C1624R.C1628id.v_bottom, "field 'vBottom'");
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.all_select, "method 'onClick'");
        this.viewbd1 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                baseListHistoryFragment.onClick(view);
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.all_share, "method 'onClick'");
        this.viewbd2 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                baseListHistoryFragment.onClick(view);
            }
        });
    }

    public void unbind() {
        BaseListHistoryFragment baseListHistoryFragment = this.target;
        if (baseListHistoryFragment != null) {
            this.target = null;
            baseListHistoryFragment.rvHistoryList = null;
            baseListHistoryFragment.normalData = null;
            baseListHistoryFragment.llBottomBar = null;
            baseListHistoryFragment.vBottom = null;
            this.viewbd1.setOnClickListener((View.OnClickListener) null);
            this.viewbd1 = null;
            this.viewbd2.setOnClickListener((View.OnClickListener) null);
            this.viewbd2 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
