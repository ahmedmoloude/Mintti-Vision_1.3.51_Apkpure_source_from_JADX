package com.p020kl.healthmonitor.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.BindView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mintti.visionsdk.ble.BleManager;
import com.p020kl.commonbase.base.BaseFragment;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.measure.MeasureFragment;

/* renamed from: com.kl.healthmonitor.navigation.NavigationFragment */
public class NavigationFragment extends BaseFragment implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final long WAIT_TIME = 2000;
    private long TOUCH_TIME = 0;
    private BaseFragment[] fragments = new BaseFragment[3];
    @BindView(3049)
    BottomNavigationView mBottomNavigationView;
    private int mCurPosition;
    @BindView(3259)
    FrameLayout mFlContainer;

    public static NavigationFragment newInstance() {
        Bundle bundle = new Bundle();
        NavigationFragment navigationFragment = new NavigationFragment();
        navigationFragment.setArguments(bundle);
        return navigationFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_navigation);
    }

    public void onBindView(Bundle bundle, View view) {
        this.mBottomNavigationView.setItemIconTintList((ColorStateList) null);
        this.mBottomNavigationView.setSelectedItemId(C1624R.C1628id.item_bottom_2);
        this.mBottomNavigationView.setItemBackground((Drawable) null);
        this.mCurPosition = 1;
        this.mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (((BaseFragment) findChildFragment(HistoryFragment.class)) == null) {
            this.fragments[0] = new HistoryFragment();
            this.fragments[1] = new MeasureFragment();
            this.fragments[2] = new MineFragment();
            loadMultipleRootFragment(C1624R.C1628id.fl_content_container, 1, this.fragments);
            return;
        }
        this.fragments[0] = (BaseFragment) findChildFragment(HistoryFragment.class);
        this.fragments[1] = (BaseFragment) findChildFragment(MeasureFragment.class);
        this.fragments[2] = (BaseFragment) findChildFragment(MineFragment.class);
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case C1624R.C1628id.item_bottom_1:
                BaseFragment[] baseFragmentArr = this.fragments;
                showHideFragment(baseFragmentArr[0], baseFragmentArr[this.mCurPosition]);
                this.mCurPosition = 0;
                break;
            case C1624R.C1628id.item_bottom_2:
                BaseFragment[] baseFragmentArr2 = this.fragments;
                showHideFragment(baseFragmentArr2[1], baseFragmentArr2[this.mCurPosition]);
                this.mCurPosition = 1;
                break;
            case C1624R.C1628id.item_bottom_3:
                BaseFragment[] baseFragmentArr3 = this.fragments;
                showHideFragment(baseFragmentArr3[2], baseFragmentArr3[this.mCurPosition]);
                this.mCurPosition = 2;
                break;
        }
        return true;
    }

    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - this.TOUCH_TIME < WAIT_TIME) {
            this._mActivity.finish();
            BleManager.getInstance().release();
            System.exit(0);
            return true;
        }
        this.TOUCH_TIME = System.currentTimeMillis();
        ToastUtil.showToast((Context) this._mActivity, (int) C1624R.string.press_again_exit);
        return true;
    }
}
