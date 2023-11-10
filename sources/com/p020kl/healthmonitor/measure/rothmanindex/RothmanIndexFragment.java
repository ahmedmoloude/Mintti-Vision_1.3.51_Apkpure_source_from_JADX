package com.p020kl.healthmonitor.measure.rothmanindex;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import butterknife.OnClick;
import com.p020kl.commonbase.base.BaseFragmentWhiteToolbar;
import com.p020kl.commonbase.service.HealthMonitorService;
import com.p020kl.commonbase.utils.ToastUtil;
import com.p020kl.healthmonitor.C1624R;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.RothmanIndexFragment */
public class RothmanIndexFragment extends BaseFragmentWhiteToolbar {
    /* access modifiers changed from: protected */
    public boolean isShowBack() {
        return true;
    }

    public void onBindView(Bundle bundle, View view) {
    }

    public static RothmanIndexFragment newInstance() {
        Bundle bundle = new Bundle();
        RothmanIndexFragment rothmanIndexFragment = new RothmanIndexFragment();
        rothmanIndexFragment.setArguments(bundle);
        return rothmanIndexFragment;
    }

    public Object setLayout() {
        return Integer.valueOf(C1624R.layout.fragment_rothman_index);
    }

    /* access modifiers changed from: protected */
    public Object setToolbarTitle() {
        return Integer.valueOf(C1624R.string.rothman_index);
    }

    @OnClick({3081, 3071})
    public void onClicked(View view) {
        int id = view.getId();
        if (id != C1624R.C1628id.bt_observations) {
            if (id == C1624R.C1628id.bt_trends) {
                start(RothmanIndexTrendsFragment.newInstance());
            }
        } else if (!HealthMonitorService.getInstance().isConnected()) {
            ToastUtil.showToast((Context) getActivity(), (int) C1624R.string.connect_firist);
        } else {
            start(SelfAssessmentFragment.newInstance());
        }
    }
}
