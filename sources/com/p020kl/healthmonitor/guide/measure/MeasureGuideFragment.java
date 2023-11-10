package com.p020kl.healthmonitor.guide.measure;

import com.p020kl.healthmonitor.guide.BaseGuideFragment;

/* renamed from: com.kl.healthmonitor.guide.measure.MeasureGuideFragment */
public class MeasureGuideFragment extends BaseGuideFragment {
    /* access modifiers changed from: protected */
    public void initPage() {
        this.page1 = new MeasurePageOne();
        this.page2 = new MeasurePageTwo();
        this.page3 = new MeasurePageThree();
        this.fragments.add(this.page1);
        this.fragments.add(this.page2);
        this.fragments.add(this.page3);
    }

    /* access modifiers changed from: protected */
    public void handlePage3Next() {
        dismiss();
    }
}
