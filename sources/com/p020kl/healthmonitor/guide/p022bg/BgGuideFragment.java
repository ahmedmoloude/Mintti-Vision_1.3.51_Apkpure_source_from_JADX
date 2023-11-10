package com.p020kl.healthmonitor.guide.p022bg;

import com.p020kl.healthmonitor.guide.BaseGuideFragment;

/* renamed from: com.kl.healthmonitor.guide.bg.BgGuideFragment */
public class BgGuideFragment extends BaseGuideFragment {
    /* access modifiers changed from: protected */
    public void initPage() {
        this.page1 = new BgPageOne();
        this.page2 = new BgPageTwo();
        this.page3 = new BgPageThree();
        this.page4 = new BgPageFour();
        this.page5 = new BgPageFive();
        this.page6 = new BgPageSix();
        this.page7 = new BgPageSeven();
        this.page8 = new BgPageEight();
        this.fragments.add(this.page1);
        this.fragments.add(this.page2);
        this.fragments.add(this.page3);
        this.fragments.add(this.page4);
        this.fragments.add(this.page5);
        this.fragments.add(this.page6);
        this.fragments.add(this.page7);
        this.fragments.add(this.page8);
    }

    /* access modifiers changed from: protected */
    public void handlePage8Next() {
        dismiss();
    }
}
