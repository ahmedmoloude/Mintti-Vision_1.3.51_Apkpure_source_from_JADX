package com.haibin.calendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.haibin.calendarview.BaseRecyclerAdapter;
import java.util.Calendar;

public final class YearRecyclerView extends RecyclerView {
    /* access modifiers changed from: private */
    public YearViewAdapter mAdapter;
    /* access modifiers changed from: private */
    public CalendarViewDelegate mDelegate;
    /* access modifiers changed from: private */
    public OnMonthSelectedListener mListener;

    interface OnMonthSelectedListener {
        void onMonthSelected(int i, int i2);
    }

    public YearRecyclerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public YearRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAdapter = new YearViewAdapter(context);
        setLayoutManager(new GridLayoutManager(context, 3));
        setAdapter(this.mAdapter);
        this.mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            public void onItemClick(int i, long j) {
                Month month;
                if (YearRecyclerView.this.mListener != null && YearRecyclerView.this.mDelegate != null && (month = (Month) YearRecyclerView.this.mAdapter.getItem(i)) != null && CalendarUtil.isMonthInRange(month.getYear(), month.getMonth(), YearRecyclerView.this.mDelegate.getMinYear(), YearRecyclerView.this.mDelegate.getMinYearMonth(), YearRecyclerView.this.mDelegate.getMaxYear(), YearRecyclerView.this.mDelegate.getMaxYearMonth())) {
                    YearRecyclerView.this.mListener.onMonthSelected(month.getYear(), month.getMonth());
                    if (YearRecyclerView.this.mDelegate.mYearViewChangeListener != null) {
                        YearRecyclerView.this.mDelegate.mYearViewChangeListener.onYearViewChange(true);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public final void setup(CalendarViewDelegate calendarViewDelegate) {
        this.mDelegate = calendarViewDelegate;
        this.mAdapter.setup(calendarViewDelegate);
    }

    /* access modifiers changed from: package-private */
    public final void init(int i) {
        Calendar instance = Calendar.getInstance();
        for (int i2 = 1; i2 <= 12; i2++) {
            instance.set(i, i2 - 1, 1);
            int monthDaysCount = CalendarUtil.getMonthDaysCount(i, i2);
            Month month = new Month();
            month.setDiff(CalendarUtil.getMonthViewStartDiff(i, i2, this.mDelegate.getWeekStart()));
            month.setCount(monthDaysCount);
            month.setMonth(i2);
            month.setYear(i);
            this.mAdapter.addItem(month);
        }
    }

    /* access modifiers changed from: package-private */
    public final void updateWeekStart() {
        for (Month month : this.mAdapter.getItems()) {
            month.setDiff(CalendarUtil.getMonthViewStartDiff(month.getYear(), month.getMonth(), this.mDelegate.getWeekStart()));
        }
    }

    /* access modifiers changed from: package-private */
    public final void updateStyle() {
        for (int i = 0; i < getChildCount(); i++) {
            YearView yearView = (YearView) getChildAt(i);
            yearView.updateStyle();
            yearView.invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public final void setOnMonthSelectedListener(OnMonthSelectedListener onMonthSelectedListener) {
        this.mListener = onMonthSelectedListener;
    }

    /* access modifiers changed from: package-private */
    public void notifyAdapterDataSetChanged() {
        if (getAdapter() != null) {
            getAdapter().notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i2);
        this.mAdapter.setYearViewSize(View.MeasureSpec.getSize(i) / 3, size / 4);
    }
}
