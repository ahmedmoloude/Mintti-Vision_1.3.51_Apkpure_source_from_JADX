package com.p020kl.healthmonitor.mine;

import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.mine.AddMemberFragment_ViewBinding */
public class AddMemberFragment_ViewBinding implements Unbinder {
    private AddMemberFragment target;
    private View viewbf7;

    public AddMemberFragment_ViewBinding(final AddMemberFragment addMemberFragment, View view) {
        this.target = addMemberFragment;
        addMemberFragment.edName = (EditText) Utils.findRequiredViewAsType(view, C1624R.C1628id.et_member_name, "field 'edName'", EditText.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.bt_add, "method 'onClick'");
        this.viewbf7 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                addMemberFragment.onClick(view);
            }
        });
    }

    public void unbind() {
        AddMemberFragment addMemberFragment = this.target;
        if (addMemberFragment != null) {
            this.target = null;
            addMemberFragment.edName = null;
            this.viewbf7.setOnClickListener((View.OnClickListener) null);
            this.viewbf7 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
