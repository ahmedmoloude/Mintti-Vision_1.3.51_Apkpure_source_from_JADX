package com.haibin.calendarview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {
    Context mContext;
    LayoutInflater mInflater;
    private List<T> mItems = new ArrayList();
    private OnClickListener onClickListener;
    /* access modifiers changed from: private */
    public OnItemClickListener onItemClickListener;

    interface OnItemClickListener {
        void onItemClick(int i, long j);
    }

    /* access modifiers changed from: package-private */
    public abstract void onBindViewHolder(RecyclerView.ViewHolder viewHolder, T t, int i);

    /* access modifiers changed from: package-private */
    public abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup viewGroup, int i);

    BaseRecyclerAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.onClickListener = new OnClickListener() {
            public void onClick(int i, long j) {
                if (BaseRecyclerAdapter.this.onItemClickListener != null) {
                    BaseRecyclerAdapter.this.onItemClickListener.onItemClick(i, j);
                }
            }
        };
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder onCreateDefaultViewHolder = onCreateDefaultViewHolder(viewGroup, i);
        if (onCreateDefaultViewHolder != null) {
            onCreateDefaultViewHolder.itemView.setTag(onCreateDefaultViewHolder);
            onCreateDefaultViewHolder.itemView.setOnClickListener(this.onClickListener);
        }
        return onCreateDefaultViewHolder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        onBindViewHolder(viewHolder, this.mItems.get(i), i);
    }

    public int getItemCount() {
        return this.mItems.size();
    }

    /* access modifiers changed from: package-private */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    /* access modifiers changed from: package-private */
    public void addAll(List<T> list) {
        if (list != null && list.size() > 0) {
            this.mItems.addAll(list);
            notifyItemRangeInserted(this.mItems.size(), list.size());
        }
    }

    /* access modifiers changed from: package-private */
    public final void addItem(T t) {
        if (t != null) {
            this.mItems.add(t);
            notifyItemChanged(this.mItems.size());
        }
    }

    /* access modifiers changed from: package-private */
    public final List<T> getItems() {
        return this.mItems;
    }

    /* access modifiers changed from: package-private */
    public final T getItem(int i) {
        if (i < 0 || i >= this.mItems.size()) {
            return null;
        }
        return this.mItems.get(i);
    }

    static abstract class OnClickListener implements View.OnClickListener {
        public abstract void onClick(int i, long j);

        OnClickListener() {
        }

        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            onClick(viewHolder.getAdapterPosition(), viewHolder.getItemId());
        }
    }
}
