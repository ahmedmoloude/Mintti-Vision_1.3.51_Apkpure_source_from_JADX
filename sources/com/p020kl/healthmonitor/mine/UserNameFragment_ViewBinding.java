package com.p020kl.healthmonitor.mine;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.mine.UserNameFragment_ViewBinding */
public class UserNameFragment_ViewBinding implements Unbinder {
    private UserNameFragment target;
    private View viewc03;

    public UserNameFragment_ViewBinding(final UserNameFragment userNameFragment, View view) {
        this.target = userNameFragment;
        userNameFragment.btSave = (Button) Utils.findRequiredViewAsType(view, C1624R.C1628id.bt_save, "field 'btSave'", Button.class);
        userNameFragment.etUserName = (EditText) Utils.findRequiredViewAsType(view, C1624R.C1628id.et_username, "field 'etUserName'", EditText.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.bt_save, "method 'onSaveClicked'");
        this.viewc03 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                userNameFragment.onSaveClicked();
            }
        });
    }

    public void unbind() {
        UserNameFragment userNameFragment = this.target;
        if (userNameFragment != null) {
            this.target = null;
            userNameFragment.btSave = null;
            userNameFragment.etUserName = null;
            this.viewc03.setOnClickListener((View.OnClickListener) null);
            this.viewc03 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
