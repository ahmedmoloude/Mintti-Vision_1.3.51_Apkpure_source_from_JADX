package com.chad.library.adapter.base;

import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;
import java.util.List;

public abstract class BaseSectionQuickAdapter<T extends SectionEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    protected static final int SECTION_HEADER_VIEW = 1092;
    protected int mSectionHeadResId;

    /* access modifiers changed from: protected */
    public abstract void convertHead(K k, T t);

    public BaseSectionQuickAdapter(int i, int i2, List<T> list) {
        super(i, list);
        this.mSectionHeadResId = i2;
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int i) {
        return ((SectionEntity) this.mData.get(i)).isHeader ? 1092 : 0;
    }

    /* access modifiers changed from: protected */
    public K onCreateDefViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1092) {
            return createBaseViewHolder(getItemView(this.mSectionHeadResId, viewGroup));
        }
        return super.onCreateDefViewHolder(viewGroup, i);
    }

    /* access modifiers changed from: protected */
    public boolean isFixedViewType(int i) {
        return super.isFixedViewType(i) || i == 1092;
    }

    public void onBindViewHolder(K k, int i) {
        if (k.getItemViewType() != 1092) {
            super.onBindViewHolder(k, i);
            return;
        }
        setFullSpan(k);
        convertHead(k, (SectionEntity) getItem(i - getHeaderLayoutCount()));
    }
}
