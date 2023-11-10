package com.p020kl.healthmonitor.guide.p023bp;

import com.p020kl.healthmonitor.guide.BaseGuideFragment;

/* renamed from: com.kl.healthmonitor.guide.bp.BpGuideFragment */
public class BpGuideFragment extends BaseGuideFragment {
    /* access modifiers changed from: protected */
    public void initPage() {
        this.page1 = new BpPageOne();
        this.page2 = new BpPageTwo();
        this.page3 = new BpPageThree();
        this.page4 = new BpPageFour();
        this.fragments.add(this.page1);
        this.fragments.add(this.page2);
        this.fragments.add(this.page3);
        this.fragments.add(this.page4);
    }

    /* access modifiers changed from: protected */
    public void handlePage4Next() {
        dismiss();
    }
}
