package com.haibin.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public abstract class MonthView extends BaseMonthView {
    /* access modifiers changed from: protected */
    public abstract void onDrawScheme(Canvas canvas, Calendar calendar, int i, int i2);

    /* access modifiers changed from: protected */
    public abstract boolean onDrawSelected(Canvas canvas, Calendar calendar, int i, int i2, boolean z);

    /* access modifiers changed from: protected */
    public abstract void onDrawText(Canvas canvas, Calendar calendar, int i, int i2, boolean z, boolean z2);

    public MonthView(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mLineCount != 0) {
            this.mItemWidth = (getWidth() - (this.mDelegate.getCalendarPadding() * 2)) / 7;
            onPreviewHook();
            int i = this.mLineCount * 7;
            int i2 = 0;
            int i3 = 0;
            while (i3 < this.mLineCount) {
                int i4 = i2;
                for (int i5 = 0; i5 < 7; i5++) {
                    Calendar calendar = (Calendar) this.mItems.get(i4);
                    if (this.mDelegate.getMonthViewShowMode() == 1) {
                        if (i4 <= this.mItems.size() - this.mNextDiff) {
                            if (!calendar.isCurrentMonth()) {
                                i4++;
                            }
                        } else {
                            return;
                        }
                    } else if (this.mDelegate.getMonthViewShowMode() == 2 && i4 >= i) {
                        return;
                    }
                    draw(canvas, calendar, i3, i5, i4);
                    i4++;
                }
                i3++;
                i2 = i4;
            }
        }
    }

    private void draw(Canvas canvas, Calendar calendar, int i, int i2, int i3) {
        int calendarPadding = (i2 * this.mItemWidth) + this.mDelegate.getCalendarPadding();
        int i4 = i * this.mItemHeight;
        onLoopStart(calendarPadding, i4);
        boolean z = false;
        boolean z2 = i3 == this.mCurrentItem;
        boolean hasScheme = calendar.hasScheme();
        if (hasScheme) {
            if (z2) {
                z = onDrawSelected(canvas, calendar, calendarPadding, i4, true);
            }
            if (z || !z2) {
                this.mSchemePaint.setColor(calendar.getSchemeColor() != 0 ? calendar.getSchemeColor() : this.mDelegate.getSchemeThemeColor());
                onDrawScheme(canvas, calendar, calendarPadding, i4);
            }
        } else if (z2) {
            onDrawSelected(canvas, calendar, calendarPadding, i4, false);
        }
        onDrawText(canvas, calendar, calendarPadding, i4, hasScheme, z2);
    }

    public void onClick(View view) {
        Calendar index;
        if (!this.isClick || (index = getIndex()) == null) {
            return;
        }
        if (this.mDelegate.getMonthViewShowMode() == 1 && !index.isCurrentMonth()) {
            return;
        }
        if (onCalendarIntercept(index)) {
            this.mDelegate.mCalendarInterceptListener.onCalendarInterceptClick(index, true);
        } else if (isInRange(index)) {
            this.mCurrentItem = this.mItems.indexOf(index);
            if (!index.isCurrentMonth() && this.mMonthViewPager != null) {
                int currentItem = this.mMonthViewPager.getCurrentItem();
                this.mMonthViewPager.setCurrentItem(this.mCurrentItem < 7 ? currentItem - 1 : currentItem + 1);
            }
            if (this.mDelegate.mInnerListener != null) {
                this.mDelegate.mInnerListener.onMonthDateSelected(index, true);
            }
            if (this.mParentLayout != null) {
                if (index.isCurrentMonth()) {
                    this.mParentLayout.updateSelectPosition(this.mItems.indexOf(index));
                } else {
                    this.mParentLayout.updateSelectWeek(CalendarUtil.getWeekFromDayInMonth(index, this.mDelegate.getWeekStart()));
                }
            }
            if (this.mDelegate.mCalendarSelectListener != null) {
                this.mDelegate.mCalendarSelectListener.onCalendarSelect(index, true);
            }
        } else if (this.mDelegate.mCalendarSelectListener != null) {
            this.mDelegate.mCalendarSelectListener.onCalendarOutOfRange(index);
        }
    }

    public boolean onLongClick(View view) {
        Calendar index;
        if (this.mDelegate.mCalendarLongClickListener == null || !this.isClick || (index = getIndex()) == null) {
            return false;
        }
        if (this.mDelegate.getMonthViewShowMode() == 1 && !index.isCurrentMonth()) {
            return false;
        }
        if (onCalendarIntercept(index)) {
            this.mDelegate.mCalendarInterceptListener.onCalendarInterceptClick(index, true);
            return false;
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
            if (!index.isCurrentMonth() && this.mMonthViewPager != null) {
                int currentItem = this.mMonthViewPager.getCurrentItem();
                this.mMonthViewPager.setCurrentItem(this.mCurrentItem < 7 ? currentItem - 1 : currentItem + 1);
            }
            if (this.mDelegate.mInnerListener != null) {
                this.mDelegate.mInnerListener.onMonthDateSelected(index, true);
            }
            if (this.mParentLayout != null) {
                if (index.isCurrentMonth()) {
                    this.mParentLayout.updateSelectPosition(this.mItems.indexOf(index));
                } else {
                    this.mParentLayout.updateSelectWeek(CalendarUtil.getWeekFromDayInMonth(index, this.mDelegate.getWeekStart()));
                }
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
