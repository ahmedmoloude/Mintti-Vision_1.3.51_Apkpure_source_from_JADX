package com.haibin.calendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.List;

public final class WeekViewPager extends ViewPager {
    /* access modifiers changed from: private */
    public boolean isUpdateWeekView;
    /* access modifiers changed from: private */
    public boolean isUsingScrollToCalendar;
    /* access modifiers changed from: private */
    public CalendarViewDelegate mDelegate;
    CalendarLayout mParentLayout;
    /* access modifiers changed from: private */
    public int mWeekCount;

    public WeekViewPager(Context context) {
        this(context, (AttributeSet) null);
    }

    public WeekViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isUsingScrollToCalendar = false;
    }

    /* access modifiers changed from: package-private */
    public void setup(CalendarViewDelegate calendarViewDelegate) {
        this.mDelegate = calendarViewDelegate;
        init();
    }

    private void init() {
        this.mWeekCount = CalendarUtil.getWeekCountBetweenBothCalendar(this.mDelegate.getMinYear(), this.mDelegate.getMinYearMonth(), this.mDelegate.getMinYearDay(), this.mDelegate.getMaxYear(), this.mDelegate.getMaxYearMonth(), this.mDelegate.getMaxYearDay(), this.mDelegate.getWeekStart());
        setAdapter(new WeekViewPagerAdapter());
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                if (WeekViewPager.this.getVisibility() != 0) {
                    boolean unused = WeekViewPager.this.isUsingScrollToCalendar = false;
                } else if (WeekViewPager.this.isUsingScrollToCalendar) {
                    boolean unused2 = WeekViewPager.this.isUsingScrollToCalendar = false;
                } else {
                    BaseWeekView baseWeekView = (BaseWeekView) WeekViewPager.this.findViewWithTag(Integer.valueOf(i));
                    if (baseWeekView != null) {
                        baseWeekView.performClickCalendar(WeekViewPager.this.mDelegate.getSelectMode() != 0 ? WeekViewPager.this.mDelegate.mIndexCalendar : WeekViewPager.this.mDelegate.mSelectedCalendar, !WeekViewPager.this.isUsingScrollToCalendar);
                        if (WeekViewPager.this.mDelegate.mWeekChangeListener != null) {
                            WeekViewPager.this.mDelegate.mWeekChangeListener.onWeekChange(WeekViewPager.this.getCurrentWeekCalendars());
                        }
                    }
                    boolean unused3 = WeekViewPager.this.isUsingScrollToCalendar = false;
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public List<Calendar> getCurrentWeekCalendars() {
        List<Calendar> weekCalendars = CalendarUtil.getWeekCalendars(this.mDelegate.mIndexCalendar, this.mDelegate);
        this.mDelegate.addSchemesFromMap(weekCalendars);
        return weekCalendars;
    }

    /* access modifiers changed from: package-private */
    public void notifyDataSetChanged() {
        this.mWeekCount = CalendarUtil.getWeekCountBetweenBothCalendar(this.mDelegate.getMinYear(), this.mDelegate.getMinYearMonth(), this.mDelegate.getMinYearDay(), this.mDelegate.getMaxYear(), this.mDelegate.getMaxYearMonth(), this.mDelegate.getMaxYearDay(), this.mDelegate.getWeekStart());
        notifyAdapterDataSetChanged();
    }

    /* access modifiers changed from: package-private */
    public void updateWeekViewClass() {
        this.isUpdateWeekView = true;
        notifyAdapterDataSetChanged();
        this.isUpdateWeekView = false;
    }

    /* access modifiers changed from: package-private */
    public void updateRange() {
        this.isUpdateWeekView = true;
        notifyDataSetChanged();
        this.isUpdateWeekView = false;
        if (getVisibility() == 0) {
            this.isUsingScrollToCalendar = true;
            Calendar calendar = this.mDelegate.mSelectedCalendar;
            updateSelected(calendar, false);
            if (this.mDelegate.mInnerListener != null) {
                this.mDelegate.mInnerListener.onWeekDateSelected(calendar, false);
            }
            if (this.mDelegate.mCalendarSelectListener != null) {
                this.mDelegate.mCalendarSelectListener.onCalendarSelect(calendar, false);
            }
            this.mParentLayout.updateSelectWeek(CalendarUtil.getWeekFromDayInMonth(calendar, this.mDelegate.getWeekStart()));
        }
    }

    /* access modifiers changed from: package-private */
    public void scrollToCalendar(int i, int i2, int i3, boolean z, boolean z2) {
        this.isUsingScrollToCalendar = true;
        Calendar calendar = new Calendar();
        calendar.setYear(i);
        calendar.setMonth(i2);
        calendar.setDay(i3);
        calendar.setCurrentDay(calendar.equals(this.mDelegate.getCurrentDay()));
        LunarCalendar.setupLunarCalendar(calendar);
        this.mDelegate.mIndexCalendar = calendar;
        this.mDelegate.mSelectedCalendar = calendar;
        this.mDelegate.updateSelectCalendarScheme();
        updateSelected(calendar, z);
        if (this.mDelegate.mInnerListener != null) {
            this.mDelegate.mInnerListener.onWeekDateSelected(calendar, false);
        }
        if (this.mDelegate.mCalendarSelectListener != null && z2) {
            this.mDelegate.mCalendarSelectListener.onCalendarSelect(calendar, false);
        }
        this.mParentLayout.updateSelectWeek(CalendarUtil.getWeekFromDayInMonth(calendar, this.mDelegate.getWeekStart()));
    }

    /* access modifiers changed from: package-private */
    public void scrollToCurrent(boolean z) {
        this.isUsingScrollToCalendar = true;
        int weekFromCalendarStartWithMinCalendar = CalendarUtil.getWeekFromCalendarStartWithMinCalendar(this.mDelegate.getCurrentDay(), this.mDelegate.getMinYear(), this.mDelegate.getMinYearMonth(), this.mDelegate.getMinYearDay(), this.mDelegate.getWeekStart()) - 1;
        if (getCurrentItem() == weekFromCalendarStartWithMinCalendar) {
            this.isUsingScrollToCalendar = false;
        }
        setCurrentItem(weekFromCalendarStartWithMinCalendar, z);
        BaseWeekView baseWeekView = (BaseWeekView) findViewWithTag(Integer.valueOf(weekFromCalendarStartWithMinCalendar));
        if (baseWeekView != null) {
            baseWeekView.performClickCalendar(this.mDelegate.getCurrentDay(), false);
            baseWeekView.setSelectedCalendar(this.mDelegate.getCurrentDay());
            baseWeekView.invalidate();
        }
        if (this.mDelegate.mCalendarSelectListener != null && getVisibility() == 0) {
            this.mDelegate.mCalendarSelectListener.onCalendarSelect(this.mDelegate.mSelectedCalendar, false);
        }
        if (getVisibility() == 0) {
            this.mDelegate.mInnerListener.onWeekDateSelected(this.mDelegate.getCurrentDay(), false);
        }
        this.mParentLayout.updateSelectWeek(CalendarUtil.getWeekFromDayInMonth(this.mDelegate.getCurrentDay(), this.mDelegate.getWeekStart()));
    }

    /* access modifiers changed from: package-private */
    public void updateSelected(Calendar calendar, boolean z) {
        boolean z2 = true;
        int weekFromCalendarStartWithMinCalendar = CalendarUtil.getWeekFromCalendarStartWithMinCalendar(calendar, this.mDelegate.getMinYear(), this.mDelegate.getMinYearMonth(), this.mDelegate.getMinYearDay(), this.mDelegate.getWeekStart()) - 1;
        if (getCurrentItem() == weekFromCalendarStartWithMinCalendar) {
            z2 = false;
        }
        this.isUsingScrollToCalendar = z2;
        setCurrentItem(weekFromCalendarStartWithMinCalendar, z);
        BaseWeekView baseWeekView = (BaseWeekView) findViewWithTag(Integer.valueOf(weekFromCalendarStartWithMinCalendar));
        if (baseWeekView != null) {
            baseWeekView.setSelectedCalendar(calendar);
            baseWeekView.invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateSingleSelect() {
        if (this.mDelegate.getSelectMode() != 0) {
            for (int i = 0; i < getChildCount(); i++) {
                ((BaseWeekView) getChildAt(i)).updateSingleSelect();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void updateDefaultSelect() {
        BaseWeekView baseWeekView = (BaseWeekView) findViewWithTag(Integer.valueOf(getCurrentItem()));
        if (baseWeekView != null) {
            baseWeekView.setSelectedCalendar(this.mDelegate.mSelectedCalendar);
            baseWeekView.invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateSelected() {
        for (int i = 0; i < getChildCount(); i++) {
            BaseWeekView baseWeekView = (BaseWeekView) getChildAt(i);
            baseWeekView.setSelectedCalendar(this.mDelegate.mSelectedCalendar);
            baseWeekView.invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public final void updateStyle() {
        for (int i = 0; i < getChildCount(); i++) {
            BaseWeekView baseWeekView = (BaseWeekView) getChildAt(i);
            baseWeekView.updateStyle();
            baseWeekView.invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateScheme() {
        for (int i = 0; i < getChildCount(); i++) {
            ((BaseWeekView) getChildAt(i)).update();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateCurrentDate() {
        for (int i = 0; i < getChildCount(); i++) {
            ((BaseWeekView) getChildAt(i)).updateCurrentDate();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateShowMode() {
        for (int i = 0; i < getChildCount(); i++) {
            ((BaseWeekView) getChildAt(i)).updateShowMode();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateWeekStart() {
        if (getAdapter() != null) {
            int count = getAdapter().getCount();
            int weekCountBetweenBothCalendar = CalendarUtil.getWeekCountBetweenBothCalendar(this.mDelegate.getMinYear(), this.mDelegate.getMinYearMonth(), this.mDelegate.getMinYearDay(), this.mDelegate.getMaxYear(), this.mDelegate.getMaxYearMonth(), this.mDelegate.getMaxYearDay(), this.mDelegate.getWeekStart());
            this.mWeekCount = weekCountBetweenBothCalendar;
            if (count != weekCountBetweenBothCalendar) {
                this.isUpdateWeekView = true;
                getAdapter().notifyDataSetChanged();
            }
            for (int i = 0; i < getChildCount(); i++) {
                ((BaseWeekView) getChildAt(i)).updateWeekStart();
            }
            this.isUpdateWeekView = false;
            updateSelected(this.mDelegate.mSelectedCalendar, false);
        }
    }

    /* access modifiers changed from: package-private */
    public final void updateItemHeight() {
        for (int i = 0; i < getChildCount(); i++) {
            BaseWeekView baseWeekView = (BaseWeekView) getChildAt(i);
            baseWeekView.updateItemHeight();
            baseWeekView.requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    public final void clearSelectRange() {
        for (int i = 0; i < getChildCount(); i++) {
            ((BaseWeekView) getChildAt(i)).invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public final void clearSingleSelect() {
        for (int i = 0; i < getChildCount(); i++) {
            BaseWeekView baseWeekView = (BaseWeekView) getChildAt(i);
            baseWeekView.mCurrentItem = -1;
            baseWeekView.invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public final void clearMultiSelect() {
        for (int i = 0; i < getChildCount(); i++) {
            BaseWeekView baseWeekView = (BaseWeekView) getChildAt(i);
            baseWeekView.mCurrentItem = -1;
            baseWeekView.invalidate();
        }
    }

    private void notifyAdapterDataSetChanged() {
        if (getAdapter() != null) {
            getAdapter().notifyDataSetChanged();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mDelegate.isWeekViewScrollable() && super.onTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mDelegate.isWeekViewScrollable() && super.onInterceptTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(this.mDelegate.getCalendarItemHeight(), 1073741824));
    }

    private class WeekViewPagerAdapter extends PagerAdapter {
        private WeekViewPagerAdapter() {
        }

        public int getCount() {
            return WeekViewPager.this.mWeekCount;
        }

        public int getItemPosition(Object obj) {
            if (WeekViewPager.this.isUpdateWeekView) {
                return -2;
            }
            return super.getItemPosition(obj);
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view.equals(obj);
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            Calendar firstCalendarStartWithMinCalendar = CalendarUtil.getFirstCalendarStartWithMinCalendar(WeekViewPager.this.mDelegate.getMinYear(), WeekViewPager.this.mDelegate.getMinYearMonth(), WeekViewPager.this.mDelegate.getMinYearDay(), i + 1, WeekViewPager.this.mDelegate.getWeekStart());
            try {
                BaseWeekView baseWeekView = (BaseWeekView) WeekViewPager.this.mDelegate.getWeekViewClass().getConstructor(new Class[]{Context.class}).newInstance(new Object[]{WeekViewPager.this.getContext()});
                baseWeekView.mParentLayout = WeekViewPager.this.mParentLayout;
                baseWeekView.setup(WeekViewPager.this.mDelegate);
                baseWeekView.setup(firstCalendarStartWithMinCalendar);
                baseWeekView.setTag(Integer.valueOf(i));
                baseWeekView.setSelectedCalendar(WeekViewPager.this.mDelegate.mSelectedCalendar);
                viewGroup.addView(baseWeekView);
                return baseWeekView;
            } catch (Exception e) {
                e.printStackTrace();
                return new DefaultWeekView(WeekViewPager.this.getContext());
            }
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            BaseWeekView baseWeekView = (BaseWeekView) obj;
            baseWeekView.onDestroy();
            viewGroup.removeView(baseWeekView);
        }
    }
}
