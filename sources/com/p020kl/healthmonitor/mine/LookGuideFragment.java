package com.p020kl.healthmonitor.mine;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.utils.StringUtils;
import com.p020kl.healthmonitor.C1624R;
import com.p020kl.healthmonitor.adapter.GuideAdapter;
import com.p020kl.healthmonitor.guide.BaseGuideFragment;
import com.p020kl.healthmonitor.guide.ecg.EcgGuideFragment;
import com.p020kl.healthmonitor.guide.measure.MeasureGuideFragment;
import com.p020kl.healthmonitor.guide.p022bg.BgGuideFragment;
import com.p020kl.healthmonitor.guide.p023bp.BpGuideFragment;
import com.p020kl.healthmonitor.guide.p024bt.BtGuideFragment;
import com.p020kl.healthmonitor.guide.spo2.Spo2GuideFragment;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.kl.healthmonitor.mine.LookGuideFragment */
public class LookGuideFragment extends BaseFragmentWhiteToolbar {
    private GuideAdapter guideAdapter;
    /* access modifiers changed from: private */
    public BaseGuideFragment guideFragment;
    private List<String> guides = new ArrayList();
    @BindView(2131296901)
    RecyclerView rvGuideList;

    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_look_guide);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return StringUtils.getString(C1624R.string.operation_guide);
    }

    public void onBindView(Bundle bundle, View view) {
        initData();
        GuideAdapter guideAdapter2 = new GuideAdapter(C1624R.layout.guide_item, this.guides);
        this.guideAdapter = guideAdapter2;
        this.rvGuideList.setAdapter(guideAdapter2);
        this.rvGuideList.setLayoutManager(new LinearLayoutManager(requireContext()));
        this.guideAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (i == 0) {
                    BaseGuideFragment unused = LookGuideFragment.this.guideFragment = new EcgGuideFragment();
                    LookGuideFragment.this.guideFragment.show(LookGuideFragment.this.getChildFragmentManager(), "ecg_guide");
                } else if (i == 1) {
                    BaseGuideFragment unused2 = LookGuideFragment.this.guideFragment = new BpGuideFragment();
                    LookGuideFragment.this.guideFragment.show(LookGuideFragment.this.getChildFragmentManager(), "bp_guide");
                } else if (i == 2) {
                    BaseGuideFragment unused3 = LookGuideFragment.this.guideFragment = new Spo2GuideFragment();
                    LookGuideFragment.this.guideFragment.show(LookGuideFragment.this.getChildFragmentManager(), "spo2_guide");
                } else if (i == 3) {
                    BaseGuideFragment unused4 = LookGuideFragment.this.guideFragment = new BgGuideFragment();
                    LookGuideFragment.this.guideFragment.show(LookGuideFragment.this.getChildFragmentManager(), "bg_guide");
                } else if (i == 4) {
                    BaseGuideFragment unused5 = LookGuideFragment.this.guideFragment = new BtGuideFragment();
                    LookGuideFragment.this.guideFragment.show(LookGuideFragment.this.getChildFragmentManager(), "bt_guide");
                } else if (i == 5) {
                    BaseGuideFragment unused6 = LookGuideFragment.this.guideFragment = new MeasureGuideFragment();
                    LookGuideFragment.this.guideFragment.show(LookGuideFragment.this.getChildFragmentManager(), "measure_guide");
                }
            }
        });
    }

    private void initData() {
        this.guides.add(getString(C1624R.string.ecg));
        this.guides.add(getString(C1624R.string.blood_pressure));
        this.guides.add(getString(C1624R.string.blood_oxygen));
        this.guides.add(getString(C1624R.string.blood_glucose));
        this.guides.add(getString(C1624R.string.body_temperature));
        this.guides.add(getString(C1624R.string.bluetooth));
    }
}
