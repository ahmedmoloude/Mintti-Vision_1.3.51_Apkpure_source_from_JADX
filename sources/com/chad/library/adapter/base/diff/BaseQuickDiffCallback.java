package com.chad.library.adapter.base.diff;

import androidx.recyclerview.widget.DiffUtil;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseQuickDiffCallback<T> extends DiffUtil.Callback {
    private List<T> newList;
    private List<T> oldList;

    /* access modifiers changed from: protected */
    public abstract boolean areContentsTheSame(T t, T t2);

    /* access modifiers changed from: protected */
    public abstract boolean areItemsTheSame(T t, T t2);

    /* access modifiers changed from: protected */
    public Object getChangePayload(T t, T t2) {
        return null;
    }

    public BaseQuickDiffCallback(List<T> list) {
        this.newList = list == null ? new ArrayList<>() : list;
    }

    public List<T> getNewList() {
        return this.newList;
    }

    public List<T> getOldList() {
        return this.oldList;
    }

    public void setOldList(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.oldList = list;
    }

    public int getOldListSize() {
        return this.oldList.size();
    }

    public int getNewListSize() {
        return this.newList.size();
    }

    public boolean areItemsTheSame(int i, int i2) {
        return areItemsTheSame(this.oldList.get(i), this.newList.get(i2));
    }

    public boolean areContentsTheSame(int i, int i2) {
        return areContentsTheSame(this.oldList.get(i), this.newList.get(i2));
    }

    public Object getChangePayload(int i, int i2) {
        return getChangePayload(this.oldList.get(i), this.newList.get(i2));
    }
}
