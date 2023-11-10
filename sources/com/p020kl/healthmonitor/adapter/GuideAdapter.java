package com.p020kl.healthmonitor.adapter;

import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p020kl.healthmonitor.C1624R;
import java.util.List;

/* renamed from: com.kl.healthmonitor.adapter.GuideAdapter */
public class GuideAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public GuideAdapter(int i, List<String> list) {
        super(i, list);
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder baseViewHolder, String str) {
        ((TextView) baseViewHolder.getView(C1624R.C1628id.tv_guide_text)).setText(str);
    }
}
