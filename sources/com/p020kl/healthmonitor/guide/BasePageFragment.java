package com.p020kl.healthmonitor.guide;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.p020kl.commonbase.base.BaseFragment;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.guide.BasePageFragment */
public abstract class BasePageFragment extends BaseFragment {
    protected LinearLayout llNext;
    protected OnNextClickListener onNextClickListener;

    public void setOnNextClickListener(OnNextClickListener onNextClickListener2) {
        this.onNextClickListener = onNextClickListener2;
    }

    public void onBindView(Bundle bundle, View view) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(C1624R.C1628id.ll_next);
        this.llNext = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (BasePageFragment.this.onNextClickListener != null) {
                    BasePageFragment.this.onNextClickListener.onNextClick();
                }
            }
        });
    }
}
