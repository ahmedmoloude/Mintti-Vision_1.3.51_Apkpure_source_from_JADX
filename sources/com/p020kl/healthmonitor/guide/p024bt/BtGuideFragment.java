package com.p020kl.healthmonitor.guide.p024bt;

import com.p020kl.healthmonitor.guide.BaseGuideFragment;

/* renamed from: com.kl.healthmonitor.guide.bt.BtGuideFragment */
public class BtGuideFragment extends BaseGuideFragment {
    /* access modifiers changed from: protected */
    public void initPage() {
        this.page1 = new BtPageOne();
        this.page2 = new BtPageTwo();
        this.fragments.add(this.page1);
        this.fragments.add(this.page2);
    }

    /* access modifiers changed from: protected */
    public void handlePage2Next() {
        dismiss();
    }
}
