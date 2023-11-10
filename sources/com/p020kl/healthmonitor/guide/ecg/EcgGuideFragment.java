package com.p020kl.healthmonitor.guide.ecg;

import com.p020kl.healthmonitor.guide.BaseGuideFragment;

/* renamed from: com.kl.healthmonitor.guide.ecg.EcgGuideFragment */
public class EcgGuideFragment extends BaseGuideFragment {
    /* access modifiers changed from: protected */
    public void initPage() {
        this.page1 = new EcgPageOne();
        this.page2 = new EcgPageTwo();
        this.fragments.add(this.page1);
        this.fragments.add(this.page2);
    }

    /* access modifiers changed from: protected */
    public void handlePage2Next() {
        dismiss();
    }
}
