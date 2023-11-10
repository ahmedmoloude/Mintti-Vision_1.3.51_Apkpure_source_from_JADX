package com.p020kl.commonbase.base;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import com.p020kl.commonbase.C1544R;

/* renamed from: com.kl.commonbase.base.ProxyActivity */
public abstract class ProxyActivity extends BaseActivity {
    public abstract BaseFragment setRootFragment();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initContainer(bundle);
    }

    private void initContainer(Bundle bundle) {
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(C1544R.C1548id.fragment_container);
        setContentView((View) frameLayout);
        if (bundle == null && findFragment(setRootFragment().getClass()) == null) {
            loadRootFragment(C1544R.C1548id.fragment_container, setRootFragment());
        }
    }
}
