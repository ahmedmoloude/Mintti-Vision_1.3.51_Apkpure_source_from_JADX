package com.chad.library.adapter.base;

import android.util.SparseIntArray;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.List;

public abstract class BaseMultiItemQuickAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private static final int DEFAULT_VIEW_TYPE = -255;
    public static final int TYPE_NOT_FOUND = -404;
    private SparseIntArray layouts;

    public BaseMultiItemQuickAdapter(List<T> list) {
        super(list);
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int i) {
        MultiItemEntity multiItemEntity = (MultiItemEntity) this.mData.get(i);
        return multiItemEntity != null ? multiItemEntity.getItemType() : DEFAULT_VIEW_TYPE;
    }

    /* access modifiers changed from: protected */
    public void setDefaultViewTypeLayout(int i) {
        addItemType(DEFAULT_VIEW_TYPE, i);
    }

    /* access modifiers changed from: protected */
    public K onCreateDefViewHolder(ViewGroup viewGroup, int i) {
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

    public void remove(int i) {
        if (this.mData != null && i >= 0 && i < this.mData.size()) {
            MultiItemEntity multiItemEntity = (MultiItemEntity) this.mData.get(i);
            if (multiItemEntity instanceof IExpandable) {
                removeAllChild((IExpandable) multiItemEntity, i);
            }
            removeDataFromParent(multiItemEntity);
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
        T t2;
        int parentPosition = getParentPosition(t);
        if (parentPosition >= 0 && (t2 = (IExpandable) this.mData.get(parentPosition)) != t) {
            t2.getSubItems().remove(t);
        }
    }

    public int getParentPositionInAll(int i) {
        List data = getData();
        MultiItemEntity multiItemEntity = (MultiItemEntity) getItem(i);
        if (isExpandable(multiItemEntity)) {
            IExpandable iExpandable = (IExpandable) multiItemEntity;
            for (int i2 = i - 1; i2 >= 0; i2--) {
                MultiItemEntity multiItemEntity2 = (MultiItemEntity) data.get(i2);
                if (isExpandable(multiItemEntity2) && iExpandable.getLevel() > ((IExpandable) multiItemEntity2).getLevel()) {
                    return i2;
                }
            }
            return -1;
        }
        for (int i3 = i - 1; i3 >= 0; i3--) {
            if (isExpandable((MultiItemEntity) data.get(i3))) {
                return i3;
            }
        }
        return -1;
    }

    public boolean isExpandable(MultiItemEntity multiItemEntity) {
        return multiItemEntity != null && (multiItemEntity instanceof IExpandable);
    }
}
