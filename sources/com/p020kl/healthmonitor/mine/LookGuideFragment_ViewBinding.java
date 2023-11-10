package com.p020kl.healthmonitor.mine;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.mine.LookGuideFragment_ViewBinding */
public class LookGuideFragment_ViewBinding implements Unbinder {
    private LookGuideFragment target;

    public LookGuideFragment_ViewBinding(LookGuideFragment lookGuideFragment, View view) {
        this.target = lookGuideFragment;
        lookGuideFragment.rvGuideList = (RecyclerView) Utils.findRequiredViewAsType(view, C1624R.C1628id.rv_guide_list, "field 'rvGuideList'", RecyclerView.class);
    }

    public void unbind() {
        LookGuideFragment lookGuideFragment = this.target;
        if (lookGuideFragment != null) {
            this.target = null;
            lookGuideFragment.rvGuideList = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
