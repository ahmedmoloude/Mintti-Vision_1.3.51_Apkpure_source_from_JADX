package com.p020kl.healthmonitor.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.p020kl.commonbase.base.BaseFragment;
import java.util.List;

/* renamed from: com.kl.healthmonitor.adapter.HistoryFragmentAdapter */
public class HistoryFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;
    private String[] mTitles;

    public HistoryFragmentAdapter(FragmentManager fragmentManager, List<BaseFragment> list, String[] strArr) {
        super(fragmentManager);
        this.fragments = list;
        this.mTitles = strArr;
    }

    public Fragment getItem(int i) {
        return this.fragments.get(i);
    }

    public int getCount() {
        return this.mTitles.length;
    }

    public CharSequence getPageTitle(int i) {
        return this.mTitles[i];
    }
}
