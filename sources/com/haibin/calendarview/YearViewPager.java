package com.haibin.calendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.haibin.calendarview.YearRecyclerView;

public final class YearViewPager extends ViewPager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    /* access modifiers changed from: private */
    public boolean isUpdateYearView;
    /* access modifiers changed from: private */
    public CalendarViewDelegate mDelegate;
    /* access modifiers changed from: private */
    public YearRecyclerView.OnMonthSelectedListener mListener;
    /* access modifiers changed from: private */
    public int mYearCount;

    public YearViewPager(Context context) {
        this(context, (AttributeSet) null);
    }

    public YearViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: package-private */
    public void setup(CalendarViewDelegate calendarViewDelegate) {
        this.mDelegate = calendarViewDelegate;
        this.mYearCount = (calendarViewDelegate.getMaxYear() - this.mDelegate.getMinYear()) + 1;
        setAdapter(new PagerAdapter() {
            public boolean isViewFromObject(View view, Object obj) {
                return view == obj;
            }

            public int getCount() {
                return YearViewPager.this.mYearCount;
            }

            public int getItemPosition(Object obj) {
                if (YearViewPager.this.isUpdateYearView) {
                    return -2;
                }
                return super.getItemPosition(obj);
            }

            public Object instantiateItem(ViewGroup viewGroup, int i) {
                YearRecyclerView yearRecyclerView = new YearRecyclerView(YearViewPager.this.getContext());
                viewGroup.addView(yearRecyclerView);
                yearRecyclerView.setup(YearViewPager.this.mDelegate);
                yearRecyclerView.setOnMonthSelectedListener(YearViewPager.this.mListener);
                yearRecyclerView.init(i + YearViewPager.this.mDelegate.getMinYear());
                return yearRecyclerView;
            }

            public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
                viewGroup.removeView((View) obj);
            }
        });
        setCurrentItem(this.mDelegate.getCurrentDay().getYear() - this.mDelegate.getMinYear());
    }

    public void setCurrentItem(int i) {
        setCurrentItem(i, false);
    }

    public void setCurrentItem(int i, boolean z) {
        if (Math.abs(getCurrentItem() - i) > 1) {
            super.setCurrentItem(i, false);
        } else {
            super.setCurrentItem(i, false);
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyDataSetChanged() {
        this.mYearCount = (this.mDelegate.getMaxYear() - this.mDelegate.getMinYear()) + 1;
        if (getAdapter() != null) {
            getAdapter().notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: package-private */
    public void scrollToYear(int i, boolean z) {
        setCurrentItem(i - this.mDelegate.getMinYear(), z);
    }

    /* access modifiers changed from: package-private */
    public final void updateRange() {
        this.isUpdateYearView = true;
        notifyDataSetChanged();
        this.isUpdateYearView = false;
    }

    /* access modifiers changed from: package-private */
    public final void update() {
        for (int i = 0; i < getChildCount(); i++) {
            ((YearRecyclerView) getChildAt(i)).notifyAdapterDataSetChanged();
        }
    }

    /* access modifiers changed from: package-private */
    public final void updateWeekStart() {
        for (int i = 0; i < getChildCount(); i++) {
            YearRecyclerView yearRecyclerView = (YearRecyclerView) getChildAt(i);
            yearRecyclerView.updateWeekStart();
            yearRecyclerView.notifyAdapterDataSetChanged();
        }
    }

    /* access modifiers changed from: package-private */
    public final void updateStyle() {
        for (int i = 0; i < getChildCount(); i++) {
            ((YearRecyclerView) getChildAt(i)).updateStyle();
        }
    }

    /* access modifiers changed from: package-private */
    public final void setOnMonthSelectedListener(YearRecyclerView.OnMonthSelectedListener onMonthSelectedListener) {
        this.mListener = onMonthSelectedListener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(getHeight(getContext(), this), 1073741824));
    }

    private static int getHeight(Context context, View view) {
        int height = ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getHeight();
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        view.getLocationOnScreen(iArr);
        return height - iArr[1];
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mDelegate.isYearViewScrollable() && super.onTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mDelegate.isYearViewScrollable() && super.onInterceptTouchEvent(motionEvent);
    }
}
