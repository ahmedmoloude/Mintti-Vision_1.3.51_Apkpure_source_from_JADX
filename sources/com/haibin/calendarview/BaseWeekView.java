package com.haibin.calendarview;

import android.content.Context;
import android.view.View;
import java.util.Calendar;

public abstract class BaseWeekView extends BaseView {
    /* access modifiers changed from: protected */
    public void onDestroy() {
    }

    /* access modifiers changed from: protected */
    public void onLoopStart(int i) {
    }

    /* access modifiers changed from: protected */
    public void onPreviewHook() {
    }

    public BaseWeekView(Context context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public final void setup(Calendar calendar) {
        this.mItems = CalendarUtil.initCalendarForWeekView(calendar, this.mDelegate, this.mDelegate.getWeekStart());
        addSchemesFromMap();
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public final void setSelectedCalendar(Calendar calendar) {
        if (this.mDelegate.getSelectMode() != 1 || calendar.equals(this.mDelegate.mSelectedCalendar)) {
            this.mCurrentItem = this.mItems.indexOf(calendar);
        }
    }

    /* access modifiers changed from: package-private */
    public final void performClickCalendar(Calendar calendar, boolean z) {
        if (this.mParentLayout != null && this.mDelegate.mInnerListener != null && this.mItems != null && this.mItems.size() != 0) {
            int weekViewIndexFromCalendar = CalendarUtil.getWeekViewIndexFromCalendar(calendar, this.mDelegate.getWeekStart());
            if (this.mItems.contains(this.mDelegate.getCurrentDay())) {
                weekViewIndexFromCalendar = CalendarUtil.getWeekViewIndexFromCalendar(this.mDelegate.getCurrentDay(), this.mDelegate.getWeekStart());
            }
            Calendar calendar2 = (Calendar) this.mItems.get(weekViewIndexFromCalendar);
            if (this.mDelegate.getSelectMode() != 0) {
                if (this.mItems.contains(this.mDelegate.mSelectedCalendar)) {
                    calendar2 = this.mDelegate.mSelectedCalendar;
                } else {
                    this.mCurrentItem = -1;
                }
            }
            if (!isInRange(calendar2)) {
                weekViewIndexFromCalendar = getEdgeIndex(isMinRangeEdge(calendar2));
                calendar2 = (Calendar) this.mItems.get(weekViewIndexFromCalendar);
            }
            calendar2.setCurrentDay(calendar2.equals(this.mDelegate.getCurrentDay()));
            this.mDelegate.mInnerListener.onWeekDateSelected(calendar2, false);
            this.mParentLayout.updateSelectWeek(CalendarUtil.getWeekFromDayInMonth(calendar2, this.mDelegate.getWeekStart()));
            if (this.mDelegate.mCalendarSelectListener != null && z && this.mDelegate.getSelectMode() == 0) {
                this.mDelegate.mCalendarSelectListener.onCalendarSelect(calendar2, false);
            }
            this.mParentLayout.updateContentViewTranslateY();
            if (this.mDelegate.getSelectMode() == 0) {
                this.mCurrentItem = weekViewIndexFromCalendar;
            }
            if (!(this.mDelegate.isShowYearSelectedLayout || this.mDelegate.mIndexCalendar == null || calendar.getYear() == this.mDelegate.mIndexCalendar.getYear() || this.mDelegate.mYearChangeListener == null)) {
                this.mDelegate.mYearChangeListener.onYearChange(this.mDelegate.mIndexCalendar.getYear());
            }
            this.mDelegate.mIndexCalendar = calendar2;
            invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean isMinRangeEdge(Calendar calendar) {
        Calendar instance = Calendar.getInstance();
        instance.set(this.mDelegate.getMinYear(), this.mDelegate.getMinYearMonth() - 1, this.mDelegate.getMinYearDay());
        long timeInMillis = instance.getTimeInMillis();
        instance.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay());
        if (instance.getTimeInMillis() < timeInMillis) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public final int getEdgeIndex(boolean z) {
        for (int i = 0; i < this.mItems.size(); i++) {
            boolean isInRange = isInRange((Calendar) this.mItems.get(i));
            if (z && isInRange) {
                return i;
            }
            if (!z && !isInRange) {
                return i - 1;
            }
        }
        if (z) {
            return 6;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public Calendar getIndex() {
        int calendarPadding = ((int) (this.f355mX - ((float) this.mDelegate.getCalendarPadding()))) / this.mItemWidth;
        if (calendarPadding >= 7) {
            calendarPadding = 6;
        }
        int i = ((((int) this.f356mY) / this.mItemHeight) * 7) + calendarPadding;
        if (i < 0 || i >= this.mItems.size()) {
            return null;
        }
        return (Calendar) this.mItems.get(i);
    }

    /* access modifiers changed from: package-private */
    public final void updateShowMode() {
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public final void updateWeekStart() {
        Calendar firstCalendarStartWithMinCalendar = CalendarUtil.getFirstCalendarStartWithMinCalendar(this.mDelegate.getMinYear(), this.mDelegate.getMinYearMonth(), this.mDelegate.getMinYearDay(), ((Integer) getTag()).intValue() + 1, this.mDelegate.getWeekStart());
        setSelectedCalendar(this.mDelegate.mSelectedCalendar);
        setup(firstCalendarStartWithMinCalendar);
    }

    /* access modifiers changed from: package-private */
    public final void updateSingleSelect() {
        if (!this.mItems.contains(this.mDelegate.mSelectedCalendar)) {
            this.mCurrentItem = -1;
            invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateCurrentDate() {
        if (this.mItems != null) {
            if (this.mItems.contains(this.mDelegate.getCurrentDay())) {
                for (Calendar currentDay : this.mItems) {
                    currentDay.setCurrentDay(false);
                }
                ((Calendar) this.mItems.get(this.mItems.indexOf(this.mDelegate.getCurrentDay()))).setCurrentDay(true);
            }
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(this.mItemHeight, 1073741824));
    }
}
