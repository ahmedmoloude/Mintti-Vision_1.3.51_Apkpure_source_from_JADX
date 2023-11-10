package com.p020kl.healthmonitor.mine;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.mine.MyDeviceFragment_ViewBinding */
public class MyDeviceFragment_ViewBinding implements Unbinder {
    private MyDeviceFragment target;
    private View viewc11;
    private View viewc1f;

    public MyDeviceFragment_ViewBinding(final MyDeviceFragment myDeviceFragment, View view) {
        this.target = myDeviceFragment;
        myDeviceFragment.tvFirmVersion = (TextView) Utils.findRequiredViewAsType(view, C1624R.C1628id.tv_firm_version, "field 'tvFirmVersion'", TextView.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.btn_unbind, "field 'btnUnBind' and method 'onUnbindClicked'");
        myDeviceFragment.btnUnBind = (Button) Utils.castView(findRequiredView, C1624R.C1628id.btn_unbind, "field 'btnUnBind'", Button.class);
        this.viewc1f = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                myDeviceFragment.onUnbindClicked();
            }
        });
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.btn_firm_update, "field 'btnUpgrade' and method 'onUpgradeClick'");
        myDeviceFragment.btnUpgrade = (Button) Utils.castView(findRequiredView2, C1624R.C1628id.btn_firm_update, "field 'btnUpgrade'", Button.class);
        this.viewc11 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                myDeviceFragment.onUpgradeClick();
            }
        });
    }

    public void unbind() {
        MyDeviceFragment myDeviceFragment = this.target;
        if (myDeviceFragment != null) {
            this.target = null;
            myDeviceFragment.tvFirmVersion = null;
            myDeviceFragment.btnUnBind = null;
            myDeviceFragment.btnUpgrade = null;
            this.viewc1f.setOnClickListener((View.OnClickListener) null);
            this.viewc1f = null;
            this.viewc11.setOnClickListener((View.OnClickListener) null);
            this.viewc11 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
