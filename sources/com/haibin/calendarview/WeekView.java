package com.haibin.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public abstract class WeekView extends BaseWeekView {
    /* access modifiers changed from: protected */
    public abstract void onDrawScheme(Canvas canvas, Calendar calendar, int i);

    /* access modifiers changed from: protected */
    public abstract boolean onDrawSelected(Canvas canvas, Calendar calendar, int i, boolean z);

    /* access modifiers changed from: protected */
    public abstract void onDrawText(Canvas canvas, Calendar calendar, int i, boolean z, boolean z2);

    public WeekView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mItems.size() != 0) {
            this.mItemWidth = (getWidth() - (this.mDelegate.getCalendarPadding() * 2)) / 7;
            onPreviewHook();
            int i = 0;
            while (i < this.mItems.size()) {
                int calendarPadding = (this.mItemWidth * i) + this.mDelegate.getCalendarPadding();
                onLoopStart(calendarPadding);
                Calendar calendar = (Calendar) this.mItems.get(i);
                boolean z = i == this.mCurrentItem;
                boolean hasScheme = calendar.hasScheme();
                if (hasScheme) {
                    if ((z ? onDrawSelected(canvas, calendar, calendarPadding, true) : false) || !z) {
                        this.mSchemePaint.setColor(calendar.getSchemeColor() != 0 ? calendar.getSchemeColor() : this.mDelegate.getSchemeThemeColor());
                        onDrawScheme(canvas, calendar, calendarPadding);
                    }
                } else if (z) {
                    onDrawSelected(canvas, calendar, calendarPadding, false);
                }
                onDrawText(canvas, calendar, calendarPadding, hasScheme, z);
                i++;
            }
        }
    }

    public void onClick(View view) {
        Calendar index;
        if (!this.isClick || (index = getIndex()) == null) {
            return;
        }
        if (onCalendarIntercept(index)) {
            this.mDelegate.mCalendarInterceptListener.onCalendarInterceptClick(index, true);
        } else if (isInRange(index)) {
            this.mCurrentItem = this.mItems.indexOf(index);
            if (this.mDelegate.mInnerListener != null) {
                this.mDelegate.mInnerListener.onWeekDateSelected(index, true);
            }
            if (this.mParentLayout != null) {
                this.mParentLayout.updateSelectWeek(CalendarUtil.getWeekFromDayInMonth(index, this.mDelegate.getWeekStart()));
            }
            if (this.mDelegate.mCalendarSelectListener != null) {
                this.mDelegate.mCalendarSelectListener.onCalendarSelect(index, true);
            }
            invalidate();
        } else if (this.mDelegate.mCalendarSelectListener != null) {
            this.mDelegate.mCalendarSelectListener.onCalendarOutOfRange(index);
        }
    }

    public boolean onLongClick(View view) {
        Calendar index;
        if (this.mDelegate.mCalendarLongClickListener == null || !this.isClick || (index = getIndex()) == null) {
            return false;
        }
        if (onCalendarIntercept(index)) {
            this.mDelegate.mCalendarInterceptListener.onCalendarInterceptClick(index, true);
            return true;
        } else if (!isInRange(index)) {
            if (this.mDelegate.mCalendarLongClickListener != null) {
                this.mDelegate.mCalendarLongClickListener.onCalendarLongClickOutOfRange(index);
            }
            return true;
        } else if (this.mDelegate.isPreventLongPressedSelected()) {
            if (this.mDelegate.mCalendarLongClickListener != null) {
                this.mDelegate.mCalendarLongClickListener.onCalendarLongClick(index);
            }
            return true;
        } else {
            this.mCurrentItem = this.mItems.indexOf(index);
            this.mDelegate.mIndexCalendar = this.mDelegate.mSelectedCalendar;
            if (this.mDelegate.mInnerListener != null) {
                this.mDelegate.mInnerListener.onWeekDateSelected(index, true);
            }
            if (this.mParentLayout != null) {
                this.mParentLayout.updateSelectWeek(CalendarUtil.getWeekFromDayInMonth(index, this.mDelegate.getWeekStart()));
            }
            if (this.mDelegate.mCalendarSelectListener != null) {
                this.mDelegate.mCalendarSelectListener.onCalendarSelect(index, true);
            }
            if (this.mDelegate.mCalendarLongClickListener != null) {
                this.mDelegate.mCalendarLongClickListener.onCalendarLongClick(index);
            }
            invalidate();
            return true;
        }
    }
}
