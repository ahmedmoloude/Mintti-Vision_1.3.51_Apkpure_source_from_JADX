package com.haibin.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public final class DefaultMonthView extends MonthView {
    private int mPadding;
    private float mRadio;
    private float mSchemeBaseLine;
    private Paint mSchemeBasicPaint = new Paint();
    private Paint mTextPaint = new Paint();

    public DefaultMonthView(Context context) {
        super(context);
        this.mTextPaint.setTextSize((float) CalendarUtil.dipToPx(context, 8.0f));
        this.mTextPaint.setColor(-1);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setFakeBoldText(true);
        this.mSchemeBasicPaint.setAntiAlias(true);
        this.mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        this.mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        this.mSchemeBasicPaint.setColor(-1223853);
        this.mSchemeBasicPaint.setFakeBoldText(true);
        this.mRadio = (float) CalendarUtil.dipToPx(getContext(), 7.0f);
        this.mPadding = CalendarUtil.dipToPx(getContext(), 4.0f);
        Paint.FontMetrics fontMetrics = this.mSchemeBasicPaint.getFontMetrics();
        this.mSchemeBaseLine = (this.mRadio - fontMetrics.descent) + ((fontMetrics.bottom - fontMetrics.top) / 2.0f) + ((float) CalendarUtil.dipToPx(getContext(), 1.0f));
    }

    /* access modifiers changed from: protected */
    public boolean onDrawSelected(Canvas canvas, Calendar calendar, int i, int i2, boolean z) {
        this.mSelectedPaint.setStyle(Paint.Style.FILL);
        int i3 = this.mPadding;
        canvas.drawRect((float) (i + i3), (float) (i3 + i2), (float) ((i + this.mItemWidth) - this.mPadding), (float) ((i2 + this.mItemHeight) - this.mPadding), this.mSelectedPaint);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDrawScheme(Canvas canvas, Calendar calendar, int i, int i2) {
        this.mSchemeBasicPaint.setColor(calendar.getSchemeColor());
        int i3 = this.mPadding;
        float f = this.mRadio;
        canvas.drawCircle(((float) ((this.mItemWidth + i) - i3)) - (f / 2.0f), ((float) (i3 + i2)) + f, f, this.mSchemeBasicPaint);
        canvas.drawText(calendar.getScheme(), (((float) ((i + this.mItemWidth) - this.mPadding)) - (this.mRadio / 2.0f)) - (getTextWidth(calendar.getScheme()) / 2.0f), ((float) (i2 + this.mPadding)) + this.mSchemeBaseLine, this.mTextPaint);
    }

    private float getTextWidth(String str) {
        return this.mTextPaint.measureText(str);
    }

    /* access modifiers changed from: protected */
    public void onDrawText(Canvas canvas, Calendar calendar, int i, int i2, boolean z, boolean z2) {
        Paint paint;
        Paint paint2;
        Paint paint3;
        int i3 = i + (this.mItemWidth / 2);
        int i4 = i2 - (this.mItemHeight / 6);
        if (z2) {
            float f = (float) i3;
            canvas.drawText(String.valueOf(calendar.getDay()), f, this.mTextBaseLine + ((float) i4), this.mSelectTextPaint);
            canvas.drawText(calendar.getLunar(), f, this.mTextBaseLine + ((float) i2) + ((float) (this.mItemHeight / 10)), this.mSelectedLunarTextPaint);
        } else if (z) {
            String valueOf = String.valueOf(calendar.getDay());
            float f2 = (float) i3;
            float f3 = this.mTextBaseLine + ((float) i4);
            if (calendar.isCurrentDay()) {
                paint3 = this.mCurDayTextPaint;
            } else {
                paint3 = calendar.isCurrentMonth() ? this.mSchemeTextPaint : this.mOtherMonthTextPaint;
            }
            canvas.drawText(valueOf, f2, f3, paint3);
            canvas.drawText(calendar.getLunar(), f2, this.mTextBaseLine + ((float) i2) + ((float) (this.mItemHeight / 10)), calendar.isCurrentDay() ? this.mCurDayLunarTextPaint : this.mSchemeLunarTextPaint);
        } else {
            String valueOf2 = String.valueOf(calendar.getDay());
            float f4 = (float) i3;
            float f5 = this.mTextBaseLine + ((float) i4);
            if (calendar.isCurrentDay()) {
                paint = this.mCurDayTextPaint;
            } else {
                paint = calendar.isCurrentMonth() ? this.mCurMonthTextPaint : this.mOtherMonthTextPaint;
            }
            canvas.drawText(valueOf2, f4, f5, paint);
            String lunar = calendar.getLunar();
            float f6 = this.mTextBaseLine + ((float) i2) + ((float) (this.mItemHeight / 10));
            if (calendar.isCurrentDay()) {
                paint2 = this.mCurDayLunarTextPaint;
            } else {
                paint2 = calendar.isCurrentMonth() ? this.mCurMonthLunarTextPaint : this.mOtherMonthLunarTextPaint;
            }
            canvas.drawText(lunar, f4, f6, paint2);
        }
    }
}
