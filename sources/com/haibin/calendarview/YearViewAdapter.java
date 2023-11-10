package com.haibin.calendarview;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

final class YearViewAdapter extends BaseRecyclerAdapter<Month> {
    private CalendarViewDelegate mDelegate;
    private int mItemHeight;
    private int mItemWidth;

    YearViewAdapter(Context context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public final void setup(CalendarViewDelegate calendarViewDelegate) {
        this.mDelegate = calendarViewDelegate;
    }

    /* access modifiers changed from: package-private */
    public final void setYearViewSize(int i, int i2) {
        this.mItemWidth = i;
        this.mItemHeight = i2;
    }

    /* access modifiers changed from: package-private */
    public RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup viewGroup, int i) {
        YearView yearView;
        if (TextUtils.isEmpty(this.mDelegate.getYearViewClassPath())) {
            yearView = new DefaultYearView(this.mContext);
        } else {
            try {
                yearView = (YearView) this.mDelegate.getYearViewClass().getConstructor(new Class[]{Context.class}).newInstance(new Object[]{this.mContext});
            } catch (Exception e) {
                e.printStackTrace();
                yearView = new DefaultYearView(this.mContext);
            }
        }
        yearView.setLayoutParams(new RecyclerView.LayoutParams(-1, -1));
        return new YearViewHolder(yearView, this.mDelegate);
    }

    /* access modifiers changed from: package-private */
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Month month, int i) {
        YearView yearView = ((YearViewHolder) viewHolder).mYearView;
        yearView.init(month.getYear(), month.getMonth());
        yearView.measureSize(this.mItemWidth, this.mItemHeight);
    }

    private static class YearViewHolder extends RecyclerView.ViewHolder {
        YearView mYearView;

        YearViewHolder(View view, CalendarViewDelegate calendarViewDelegate) {
            super(view);
            YearView yearView = (YearView) view;
            this.mYearView = yearView;
            yearView.setup(calendarViewDelegate);
        }
    }
}
