package com.haibin.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public abstract class MultiWeekView extends BaseWeekView {
    /* access modifiers changed from: protected */
    public abstract void onDrawScheme(Canvas canvas, Calendar calendar, int i, boolean z);

    /* access modifiers changed from: protected */
    public abstract boolean onDrawSelected(Canvas canvas, Calendar calendar, int i, boolean z, boolean z2, boolean z3);

    /* access modifiers changed from: protected */
    public abstract void onDrawText(Canvas canvas, Calendar calendar, int i, boolean z, boolean z2);

    public boolean onLongClick(View view) {
        return false;
    }

    public MultiWeekView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mItems.size() != 0) {
            this.mItemWidth = (getWidth() - (this.mDelegate.getCalendarPadding() * 2)) / 7;
            onPreviewHook();
            for (int i = 0; i < 7; i++) {
                int calendarPadding = (this.mItemWidth * i) + this.mDelegate.getCalendarPadding();
                onLoopStart(calendarPadding);
                Calendar calendar = (Calendar) this.mItems.get(i);
                boolean isCalendarSelected = isCalendarSelected(calendar);
                boolean isSelectPreCalendar = isSelectPreCalendar(calendar);
                boolean isSelectNextCalendar = isSelectNextCalendar(calendar);
                boolean hasScheme = calendar.hasScheme();
                if (hasScheme) {
                    if ((isCalendarSelected ? onDrawSelected(canvas, calendar, calendarPadding, true, isSelectPreCalendar, isSelectNextCalendar) : false) || !isCalendarSelected) {
                        this.mSchemePaint.setColor(calendar.getSchemeColor() != 0 ? calendar.getSchemeColor() : this.mDelegate.getSchemeThemeColor());
                        onDrawScheme(canvas, calendar, calendarPadding, isCalendarSelected);
                    }
                } else if (isCalendarSelected) {
                    onDrawSelected(canvas, calendar, calendarPadding, false, isSelectPreCalendar, isSelectNextCalendar);
                }
                onDrawText(canvas, calendar, calendarPadding, hasScheme, isCalendarSelected);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isCalendarSelected(Calendar calendar) {
        return !onCalendarIntercept(calendar) && this.mDelegate.mSelectedCalendars.containsKey(calendar.toString());
    }

    public void onClick(View view) {
        Calendar index;
        if (!this.isClick || (index = getIndex()) == null) {
            return;
        }
        if (onCalendarIntercept(index)) {
            this.mDelegate.mCalendarInterceptListener.onCalendarInterceptClick(index, true);
        } else if (isInRange(index)) {
            String calendar = index.toString();
            if (this.mDelegate.mSelectedCalendars.containsKey(calendar)) {
                this.mDelegate.mSelectedCalendars.remove(calendar);
            } else if (this.mDelegate.mSelectedCalendars.size() < this.mDelegate.getMaxMultiSelectSize()) {
                this.mDelegate.mSelectedCalendars.put(calendar, index);
            } else if (this.mDelegate.mCalendarMultiSelectListener != null) {
                this.mDelegate.mCalendarMultiSelectListener.onMultiSelectOutOfSize(index, this.mDelegate.getMaxMultiSelectSize());
                return;
            } else {
                return;
            }
            this.mCurrentItem = this.mItems.indexOf(index);
            if (this.mDelegate.mInnerListener != null) {
                this.mDelegate.mInnerListener.onWeekDateSelected(index, true);
            }
            if (this.mParentLayout != null) {
                this.mParentLayout.updateSelectWeek(CalendarUtil.getWeekFromDayInMonth(index, this.mDelegate.getWeekStart()));
            }
            if (this.mDelegate.mCalendarMultiSelectListener != null) {
                this.mDelegate.mCalendarMultiSelectListener.onCalendarMultiSelect(index, this.mDelegate.mSelectedCalendars.size(), this.mDelegate.getMaxMultiSelectSize());
            }
            invalidate();
        } else if (this.mDelegate.mCalendarMultiSelectListener != null) {
            this.mDelegate.mCalendarMultiSelectListener.onCalendarMultiSelectOutOfRange(index);
        }
    }

    /* access modifiers changed from: protected */
    public final boolean isSelectPreCalendar(Calendar calendar) {
        Calendar preCalendar = CalendarUtil.getPreCalendar(calendar);
        this.mDelegate.updateCalendarScheme(preCalendar);
        return isCalendarSelected(preCalendar);
    }

    /* access modifiers changed from: protected */
    public final boolean isSelectNextCalendar(Calendar calendar) {
        Calendar nextCalendar = CalendarUtil.getNextCalendar(calendar);
        this.mDelegate.updateCalendarScheme(nextCalendar);
        return isCalendarSelected(nextCalendar);
    }
}
