package com.p020kl.commonbase.base;

import androidx.fragment.app.Fragment;
import com.gyf.immersionbar.ImmersionBar;

/* renamed from: com.kl.commonbase.base.BaseFragmentWhiteToolbar */
public abstract class BaseFragmentWhiteToolbar extends BaseFragment {
    public void initImmersionBar() {
        ImmersionBar.with((Fragment) this).reset().fitsSystemWindows(true).statusBarDarkFont(true).init();
    }
}
