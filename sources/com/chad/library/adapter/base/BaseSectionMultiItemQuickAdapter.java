package com.chad.library.adapter.base;

import android.util.SparseIntArray;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.SectionMultiEntity;
import java.util.List;

public abstract class BaseSectionMultiItemQuickAdapter<T extends SectionMultiEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private static final int DEFAULT_VIEW_TYPE = -255;
    protected static final int SECTION_HEADER_VIEW = 1092;
    public static final int TYPE_NOT_FOUND = -404;
    private SparseIntArray layouts;
    protected int mSectionHeadResId;

    /* access modifiers changed from: protected */
    public abstract void convertHead(K k, T t);

    public BaseSectionMultiItemQuickAdapter(int i, List<T> list) {
        super(list);
        this.mSectionHeadResId = i;
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int i) {
        SectionMultiEntity sectionMultiEntity = (SectionMultiEntity) this.mData.get(i);
        if (sectionMultiEntity == null) {
            return DEFAULT_VIEW_TYPE;
        }
        if (sectionMultiEntity.isHeader) {
            return 1092;
        }
        return sectionMultiEntity.getItemType();
    }

    /* access modifiers changed from: protected */
    public void setDefaultViewTypeLayout(int i) {
        addItemType(DEFAULT_VIEW_TYPE, i);
    }

    /* access modifiers changed from: protected */
    public K onCreateDefViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1092) {
            return createBaseViewHolder(getItemView(this.mSectionHeadResId, viewGroup));
        }
        return createBaseViewHolder(viewGroup, getLayoutId(i));
    }

    private int getLayoutId(int i) {
        return this.layouts.get(i, -404);
    }

    /* access modifiers changed from: protected */
    public void addItemType(int i, int i2) {
        if (this.layouts == null) {
            this.layouts = new SparseIntArray();
        }
        this.layouts.put(i, i2);
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
        convertHead(k, (SectionMultiEntity) getItem(i - getHeaderLayoutCount()));
    }

    public void remove(int i) {
        if (this.mData != null && i >= 0 && i < this.mData.size()) {
            SectionMultiEntity sectionMultiEntity = (SectionMultiEntity) this.mData.get(i);
            if (sectionMultiEntity instanceof IExpandable) {
                removeAllChild((IExpandable) sectionMultiEntity, i);
            }
            removeDataFromParent(sectionMultiEntity);
            super.remove(i);
        }
    }

    /* access modifiers changed from: protected */
    public void removeAllChild(IExpandable iExpandable, int i) {
        List subItems;
        if (iExpandable.isExpanded() && (subItems = iExpandable.getSubItems()) != null && subItems.size() != 0) {
            int size = subItems.size();
            for (int i2 = 0; i2 < size; i2++) {
                remove(i + 1);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void removeDataFromParent(T t) {
        int parentPosition = getParentPosition(t);
        if (parentPosition >= 0) {
            ((IExpandable) this.mData.get(parentPosition)).getSubItems().remove(t);
        }
    }
}
