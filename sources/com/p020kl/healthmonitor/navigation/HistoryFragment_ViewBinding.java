package com.p020kl.healthmonitor.navigation;

import android.view.View;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.androidkun.xtablayout.XTabLayout;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.navigation.HistoryFragment_ViewBinding */
public class HistoryFragment_ViewBinding implements Unbinder {
    private HistoryFragment target;

    public HistoryFragment_ViewBinding(HistoryFragment historyFragment, View view) {
        this.target = historyFragment;
        historyFragment.tlTab = (XTabLayout) Utils.findRequiredViewAsType(view, C1624R.C1628id.tl_tab, "field 'tlTab'", XTabLayout.class);
        historyFragment.vpRecord = (ViewPager) Utils.findRequiredViewAsType(view, C1624R.C1628id.vp_record, "field 'vpRecord'", ViewPager.class);
    }

    public void unbind() {
        HistoryFragment historyFragment = this.target;
        if (historyFragment != null) {
            this.target = null;
            historyFragment.tlTab = null;
            historyFragment.vpRecord = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
