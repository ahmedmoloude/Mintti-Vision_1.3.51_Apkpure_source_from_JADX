package com.p020kl.healthmonitor.measure;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.views.CustomResultProgressBar;
import com.p020kl.healthmonitor.views.MeasureTopItemView;

/* renamed from: com.kl.healthmonitor.measure.BTMeasureFragment_ViewBinding */
public class BTMeasureFragment_ViewBinding implements Unbinder {
    private BTMeasureFragment target;
    private View viewc0e;

    public BTMeasureFragment_ViewBinding(final BTMeasureFragment bTMeasureFragment, View view) {
        this.target = bTMeasureFragment;
        bTMeasureFragment.topItemView = (MeasureTopItemView) Utils.findRequiredViewAsType(view, C1624R.C1628id.mtiv_bt_topvalue, "field 'topItemView'", MeasureTopItemView.class);
        bTMeasureFragment.progressBar = (CustomResultProgressBar) Utils.findRequiredViewAsType(view, C1624R.C1628id.crpb_bt_progress, "field 'progressBar'", CustomResultProgressBar.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.btn_bt_measure, "field 'btMeasure' and method 'onClick'");
        bTMeasureFragment.btMeasure = (Button) Utils.castView(findRequiredView, C1624R.C1628id.btn_bt_measure, "field 'btMeasure'", Button.class);
        this.viewc0e = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                bTMeasureFragment.onClick();
            }
        });
        bTMeasureFragment.ivImg1 = (ImageView) Utils.findRequiredViewAsType(view, C1624R.C1628id.iv_bt_img1, "field 'ivImg1'", ImageView.class);
        bTMeasureFragment.ivImg2 = (ImageView) Utils.findRequiredViewAsType(view, C1624R.C1628id.iv_bt_img2, "field 'ivImg2'", ImageView.class);
        bTMeasureFragment.ivImg3 = (ImageView) Utils.findRequiredViewAsType(view, C1624R.C1628id.iv_bt_img3, "field 'ivImg3'", ImageView.class);
    }

    public void unbind() {
        BTMeasureFragment bTMeasureFragment = this.target;
        if (bTMeasureFragment != null) {
            this.target = null;
            bTMeasureFragment.topItemView = null;
            bTMeasureFragment.progressBar = null;
            bTMeasureFragment.btMeasure = null;
            bTMeasureFragment.ivImg1 = null;
            bTMeasureFragment.ivImg2 = null;
            bTMeasureFragment.ivImg3 = null;
            this.viewc0e.setOnClickListener((View.OnClickListener) null);
            this.viewc0e = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
