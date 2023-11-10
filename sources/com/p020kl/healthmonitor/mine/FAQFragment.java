package com.p020kl.healthmonitor.mine;

import android.os.Bundle;
import android.view.View;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.mine.FAQFragment */
public class FAQFragment extends BaseFragmentWhiteToolbar {
    public void onBindView(Bundle bundle, View view) {
    }

    public static FAQFragment newInstance() {
        Bundle bundle = new Bundle();
        FAQFragment fAQFragment = new FAQFragment();
        fAQFragment.setArguments(bundle);
        return fAQFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_faq);
    }
}
