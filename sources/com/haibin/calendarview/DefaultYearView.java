package com.haibin.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class DefaultYearView extends YearView {
    private int mTextPadding;

    /* access modifiers changed from: protected */
    public void onDrawScheme(Canvas canvas, Calendar calendar, int i, int i2) {
    }

    /* access modifiers changed from: protected */
    public boolean onDrawSelected(Canvas canvas, Calendar calendar, int i, int i2, boolean z) {
        return false;
    }

    public DefaultYearView(Context context) {
        super(context);
        this.mTextPadding = CalendarUtil.dipToPx(context, 3.0f);
    }

    /* access modifiers changed from: protected */
    public void onDrawMonth(Canvas canvas, int i, int i2, int i3, int i4, int i5, int i6) {
        canvas.drawText(getContext().getResources().getStringArray(C1484R.array.month_string_array)[i2 - 1], (float) ((i3 + (this.mItemWidth / 2)) - this.mTextPadding), ((float) i4) + this.mMonthTextBaseLine, this.mMonthTextPaint);
    }

    /* access modifiers changed from: protected */
    public void onDrawWeek(Canvas canvas, int i, int i2, int i3, int i4, int i5) {
        canvas.drawText(getContext().getResources().getStringArray(C1484R.array.year_view_week_string_array)[i], (float) (i2 + (i4 / 2)), ((float) i3) + this.mWeekTextBaseLine, this.mWeekTextPaint);
    }

    /* access modifiers changed from: protected */
    public void onDrawText(Canvas canvas, Calendar calendar, int i, int i2, boolean z, boolean z2) {
        Paint paint;
        Paint paint2;
        float f = this.mTextBaseLine + ((float) i2);
        int i3 = i + (this.mItemWidth / 2);
        if (z2) {
            canvas.drawText(String.valueOf(calendar.getDay()), (float) i3, f, z ? this.mSchemeTextPaint : this.mSelectTextPaint);
        } else if (z) {
            String valueOf = String.valueOf(calendar.getDay());
            float f2 = (float) i3;
            if (calendar.isCurrentDay()) {
                paint2 = this.mCurDayTextPaint;
            } else {
                paint2 = calendar.isCurrentMonth() ? this.mSchemeTextPaint : this.mOtherMonthTextPaint;
            }
            canvas.drawText(valueOf, f2, f, paint2);
        } else {
            String valueOf2 = String.valueOf(calendar.getDay());
            float f3 = (float) i3;
            if (calendar.isCurrentDay()) {
                paint = this.mCurDayTextPaint;
            } else {
                paint = calendar.isCurrentMonth() ? this.mCurMonthTextPaint : this.mOtherMonthTextPaint;
            }
            canvas.drawText(valueOf2, f3, f, paint);
        }
    }
}
