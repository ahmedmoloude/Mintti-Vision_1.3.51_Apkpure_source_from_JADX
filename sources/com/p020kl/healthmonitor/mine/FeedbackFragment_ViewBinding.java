package com.p020kl.healthmonitor.mine;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.mine.FeedbackFragment_ViewBinding */
public class FeedbackFragment_ViewBinding implements Unbinder {
    private FeedbackFragment target;
    private View viewc1d;
    private View viewd03;

    public FeedbackFragment_ViewBinding(final FeedbackFragment feedbackFragment, View view) {
        this.target = feedbackFragment;
        feedbackFragment.etSuggestion = (EditText) Utils.findRequiredViewAsType(view, C1624R.C1628id.et_suggest, "field 'etSuggestion'", EditText.class);
        feedbackFragment.llImgContainer = (LinearLayout) Utils.findRequiredViewAsType(view, C1624R.C1628id.ll_container_img, "field 'llImgContainer'", LinearLayout.class);
        View findRequiredView = Utils.findRequiredView(view, C1624R.C1628id.iv_add_image, "field 'ivAddImage' and method 'onAddImage'");
        feedbackFragment.ivAddImage = (ImageView) Utils.castView(findRequiredView, C1624R.C1628id.iv_add_image, "field 'ivAddImage'", ImageView.class);
        this.viewd03 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                feedbackFragment.onAddImage();
            }
        });
        feedbackFragment.etContactWay = (EditText) Utils.findRequiredViewAsType(view, C1624R.C1628id.et_contact_way, "field 'etContactWay'", EditText.class);
        View findRequiredView2 = Utils.findRequiredView(view, C1624R.C1628id.btn_submit, "field 'btnSubmit' and method 'onBtnSubmitClicked'");
        feedbackFragment.btnSubmit = (Button) Utils.castView(findRequiredView2, C1624R.C1628id.btn_submit, "field 'btnSubmit'", Button.class);
        this.viewc1d = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                feedbackFragment.onBtnSubmitClicked();
            }
        });
    }

    public void unbind() {
        FeedbackFragment feedbackFragment = this.target;
        if (feedbackFragment != null) {
            this.target = null;
            feedbackFragment.etSuggestion = null;
            feedbackFragment.llImgContainer = null;
            feedbackFragment.ivAddImage = null;
            feedbackFragment.etContactWay = null;
            feedbackFragment.btnSubmit = null;
            this.viewd03.setOnClickListener((View.OnClickListener) null);
            this.viewd03 = null;
            this.viewc1d.setOnClickListener((View.OnClickListener) null);
            this.viewc1d = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
