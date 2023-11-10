package com.p020kl.healthmonitor.guide.spo2;

import com.p020kl.healthmonitor.guide.BaseGuideFragment;

/* renamed from: com.kl.healthmonitor.guide.spo2.Spo2GuideFragment */
public class Spo2GuideFragment extends BaseGuideFragment {
    /* access modifiers changed from: protected */
    public void initPage() {
        this.page1 = new Spo2PageOne();
        this.page2 = new Spo2PageTwo();
        this.fragments.add(this.page1);
        this.fragments.add(this.page2);
    }

    /* access modifiers changed from: protected */
    public void handlePage2Next() {
        dismiss();
    }
}
