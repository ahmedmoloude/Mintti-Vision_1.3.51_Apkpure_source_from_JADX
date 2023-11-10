package com.p020kl.healthmonitor.sign;

import com.p020kl.commonbase.base.BaseFragment;
import com.p020kl.commonbase.base.ProxyActivity;

/* renamed from: com.kl.healthmonitor.sign.SignActivity */
public class SignActivity extends ProxyActivity {
    public BaseFragment setRootFragment() {
        return LoginFragment.newInstance();
    }
}
