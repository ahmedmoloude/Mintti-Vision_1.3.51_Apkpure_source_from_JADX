package com.p020kl.healthmonitor.navigation;

import android.os.Bundle;
import android.view.View;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import com.androidkun.xtablayout.XTabLayout;
import com.p020kl.commonbase.base.BaseFragment;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.adapter.HistoryFragmentAdapter;
import com.p020kl.healthmonitor.history.BGMHistoryFragment;
import com.p020kl.healthmonitor.history.BPHistoryFragment;
import com.p020kl.healthmonitor.history.BTMHistoryFragment;
import com.p020kl.healthmonitor.history.ECGHistoryFragment;
import com.p020kl.healthmonitor.history.SPO2HHistoryFragment;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.navigation.HistoryFragment */
public class HistoryFragment extends BaseFragmentWhiteToolbar {
    private static OnBGMonthChangeListener bgMonthChangeListener;
    private static OnBPMonthChangeListener bpMonthChangeListener;
    private static OnBTMonthChangeListener btMonthChangeListener;
    private static OnECGMonthChangeListener ecgMonthChangeListener;
    private static OnSPO2MonthChangeListener spo2MonthChangeListener;
    private List<BaseFragment> fragmentList = new ArrayList();
    /* access modifiers changed from: private */
    public int index = 0;
    @BindView(3708)
    XTabLayout tlTab;
    @BindView(3867)
    ViewPager vpRecord;

    /* renamed from: com.kl.healthmonitor.navigation.HistoryFragment$OnBGMonthChangeListener */
    public interface OnBGMonthChangeListener {
        void onBGMonthChange();
    }

    /* renamed from: com.kl.healthmonitor.navigation.HistoryFragment$OnBPMonthChangeListener */
    public interface OnBPMonthChangeListener {
        void onBPMonthChange();
    }

    /* renamed from: com.kl.healthmonitor.navigation.HistoryFragment$OnBTMonthChangeListener */
    public interface OnBTMonthChangeListener {
        void onBTMonthChange();
    }

    /* renamed from: com.kl.healthmonitor.navigation.HistoryFragment$OnECGMonthChangeListener */
    public interface OnECGMonthChangeListener {
        void onECGMonthChange();
    }

    /* renamed from: com.kl.healthmonitor.navigation.HistoryFragment$OnSPO2MonthChangeListener */
    public interface OnSPO2MonthChangeListener {
        void onSPO2MonthChange();
    }

    /* access modifiers changed from: protected */
    public boolean isEventBusRegister() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isShowRightImg() {
        return false;
    }

    public void onBindView(Bundle bundle, View view) {
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_history);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return StringUtils.getString(C1624R.string.history);
    }

    /* access modifiers changed from: protected */
    public String getRigthText() {
        return StringUtils.getString(C1624R.string.share);
    }

    /* access modifiers changed from: protected */
    public void onRightClicked() {
        onClick();
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        super.initView(view);
        this.fragmentList.add(ECGHistoryFragment.newInstance());
        this.fragmentList.add(BPHistoryFragment.newInstance());
        this.fragmentList.add(SPO2HHistoryFragment.newInstance());
        this.fragmentList.add(BGMHistoryFragment.newInstance());
        this.fragmentList.add(BTMHistoryFragment.newInstance());
        showRightText();
        this.vpRecord.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                int unused = HistoryFragment.this.index = i;
            }
        });
        this.vpRecord.setAdapter(new HistoryFragmentAdapter(getChildFragmentManager(), this.fragmentList, getResources().getStringArray(C1624R.array.history_item)));
        this.tlTab.setupWithViewPager(this.vpRecord);
        this.vpRecord.setOffscreenPageLimit(5);
    }

    public void onLazyInitView(Bundle bundle) {
        super.onLazyInitView(bundle);
    }

    public void onClick() {
        OnBTMonthChangeListener onBTMonthChangeListener;
        int i = this.index;
        if (i == 0) {
            OnECGMonthChangeListener onECGMonthChangeListener = ecgMonthChangeListener;
            if (onECGMonthChangeListener != null) {
                onECGMonthChangeListener.onECGMonthChange();
            }
        } else if (i == 1) {
            OnBPMonthChangeListener onBPMonthChangeListener = bpMonthChangeListener;
            if (onBPMonthChangeListener != null) {
                onBPMonthChangeListener.onBPMonthChange();
            }
        } else if (i == 2) {
            OnSPO2MonthChangeListener onSPO2MonthChangeListener = spo2MonthChangeListener;
            if (onSPO2MonthChangeListener != null) {
                onSPO2MonthChangeListener.onSPO2MonthChange();
            }
        } else if (i == 3) {
            OnBGMonthChangeListener onBGMonthChangeListener = bgMonthChangeListener;
            if (onBGMonthChangeListener != null) {
                onBGMonthChangeListener.onBGMonthChange();
            }
        } else if (i == 4 && (onBTMonthChangeListener = btMonthChangeListener) != null) {
            onBTMonthChangeListener.onBTMonthChange();
        }
    }

    public static void setOnBTMonthChangeListener(OnBTMonthChangeListener onBTMonthChangeListener) {
        btMonthChangeListener = onBTMonthChangeListener;
    }

    public static void setOnSPO2MonthChangeListener(OnSPO2MonthChangeListener onSPO2MonthChangeListener) {
        spo2MonthChangeListener = onSPO2MonthChangeListener;
    }

    public static void setOnBPMonthChangeListener(OnBPMonthChangeListener onBPMonthChangeListener) {
        bpMonthChangeListener = onBPMonthChangeListener;
    }

    public static void setOnBGMonthChangeListener(OnBGMonthChangeListener onBGMonthChangeListener) {
        bgMonthChangeListener = onBGMonthChangeListener;
    }

    public static void setOnECGMonthChangeListener(OnECGMonthChangeListener onECGMonthChangeListener) {
        ecgMonthChangeListener = onECGMonthChangeListener;
    }
}
