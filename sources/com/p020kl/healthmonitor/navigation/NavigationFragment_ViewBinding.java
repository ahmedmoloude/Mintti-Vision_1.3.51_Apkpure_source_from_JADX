package com.p020kl.healthmonitor.navigation;

import android.view.View;
import android.widget.FrameLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.navigation.NavigationFragment_ViewBinding */
public class NavigationFragment_ViewBinding implements Unbinder {
    private NavigationFragment target;

    public NavigationFragment_ViewBinding(NavigationFragment navigationFragment, View view) {
        this.target = navigationFragment;
        navigationFragment.mBottomNavigationView = (BottomNavigationView) Utils.findRequiredViewAsType(view, C1624R.C1628id.bottom_nav_view, "field 'mBottomNavigationView'", BottomNavigationView.class);
        navigationFragment.mFlContainer = (FrameLayout) Utils.findRequiredViewAsType(view, C1624R.C1628id.fl_content_container, "field 'mFlContainer'", FrameLayout.class);
    }

    public void unbind() {
        NavigationFragment navigationFragment = this.target;
        if (navigationFragment != null) {
            this.target = null;
            navigationFragment.mBottomNavigationView = null;
            navigationFragment.mFlContainer = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
