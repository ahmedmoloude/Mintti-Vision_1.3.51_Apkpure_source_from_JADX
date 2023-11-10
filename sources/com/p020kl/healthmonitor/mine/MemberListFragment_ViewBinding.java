package com.p020kl.healthmonitor.mine;

import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.NormalDataView;

/* renamed from: com.kl.healthmonitor.mine.MemberListFragment_ViewBinding */
public class MemberListFragment_ViewBinding implements Unbinder {
    private MemberListFragment target;
    private View viewbf8;

    public MemberListFragment_ViewBinding(final MemberListFragment memberListFragment, View view) {
        this.target = memberListFragment;
        memberListFragment.rvMember = (RecyclerView) Utils.findRequiredViewAsType(view, C1624R.C1628id.rv_member_list, "field 'rvMember'", RecyclerView.class);
        memberListFragment.normalDataView = (NormalDataView) Utils.findRequiredViewAsType(view, C1624R.C1628id.ndv_normal_data, "field 'normalDataView'", NormalDataView.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.bt_add_member, "field 'addMember' and method 'onClick'");
        memberListFragment.addMember = (Button) Utils.castView(findRequiredView, C1624R.C1628id.bt_add_member, "field 'addMember'", Button.class);
        this.viewbf8 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                memberListFragment.onClick(view);
            }
        });
    }

    public void unbind() {
        MemberListFragment memberListFragment = this.target;
        if (memberListFragment != null) {
            this.target = null;
            memberListFragment.rvMember = null;
            memberListFragment.normalDataView = null;
            memberListFragment.addMember = null;
            this.viewbf8.setOnClickListener((View.OnClickListener) null);
            this.viewbf8 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
