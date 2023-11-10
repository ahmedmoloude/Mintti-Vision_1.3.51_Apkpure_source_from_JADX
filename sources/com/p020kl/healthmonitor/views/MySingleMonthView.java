package com.p020kl.healthmonitor.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

/* renamed from: com.kl.healthmonitor.views.MySingleMonthView */
public class MySingleMonthView extends MonthView {
    private Paint mDisablePaint = new Paint();

    /* renamed from: mH */
    private int f902mH;
    private int mRadius;
    private Paint mRingPaint = new Paint();
    private int mRingRadius;

    public MySingleMonthView(Context context) {
        super(context);
        this.mRingPaint.setAntiAlias(true);
        this.mRingPaint.setColor(Color.parseColor("#44bcb1"));
        this.mRingPaint.setStyle(Paint.Style.STROKE);
        this.mRingPaint.setStrokeWidth((float) dipToPx(context, 1.0f));
        this.mDisablePaint.setColor(-6316129);
        this.mDisablePaint.setAntiAlias(true);
        this.mDisablePaint.setStrokeWidth((float) dipToPx(context, 2.0f));
        this.mDisablePaint.setFakeBoldText(true);
        this.f902mH = dipToPx(context, 18.0f);
    }

    /* access modifiers changed from: protected */
    public void onPreviewHook() {
        this.mRadius = dipToPx(getContext(), 15.5f);
        this.mRingRadius = dipToPx(getContext(), 17.5f);
        this.mSelectTextPaint.setTextSize((float) dipToPx(getContext(), 17.0f));
    }

    /* access modifiers changed from: protected */
    public boolean onDrawSelected(Canvas canvas, Calendar calendar, int i, int i2, boolean z) {
        float f = (float) (i + (this.mItemWidth / 2));
        float f2 = (float) (i2 + (this.mItemHeight / 2));
        canvas.drawCircle(f, f2, (float) this.mRadius, this.mSelectedPaint);
        canvas.drawCircle(f, f2, (float) this.mRingRadius, this.mRingPaint);
        return !z;
    }

    /* access modifiers changed from: protected */
    public void onDrawScheme(Canvas canvas, Calendar calendar, int i, int i2) {
        float f = (float) (i + (this.mItemWidth / 2));
        float f2 = (float) (i2 + (this.mItemHeight / 2));
        canvas.drawCircle(f, f2, (float) this.mRadius, this.mSchemePaint);
        if (calendar.isCurrentDay()) {
            canvas.drawCircle(f, f2, (float) this.mRadius, this.mSelectedPaint);
        }
    }

    /* access modifiers changed from: protected */
    public void onDrawText(Canvas canvas, Calendar calendar, int i, int i2, boolean z, boolean z2) {
        Paint paint;
        Paint paint2;
        float dipToPx = (this.mTextBaseLine + ((float) i2)) - ((float) dipToPx(getContext(), 1.0f));
        int i3 = (this.mItemWidth / 2) + i;
        boolean isInRange = isInRange(calendar);
        if (z2) {
            canvas.drawText(String.valueOf(calendar.getDay()), (float) i3, dipToPx, this.mSelectTextPaint);
        } else if (z) {
            String valueOf = String.valueOf(calendar.getDay());
            float f = (float) i3;
            if (calendar.isCurrentDay()) {
                paint2 = this.mCurDayTextPaint;
            } else {
                paint2 = calendar.isCurrentMonth() ? this.mSchemeTextPaint : this.mOtherMonthTextPaint;
            }
            canvas.drawText(valueOf, f, dipToPx, paint2);
        } else {
            if (calendar.isCurrentDay()) {
                canvas.drawCircle((float) i3, (float) ((this.mItemHeight / 2) + i2), (float) this.mRadius, this.mSelectedPaint);
            }
            String valueOf2 = String.valueOf(calendar.getDay());
            float f2 = (float) i3;
            if (calendar.isCurrentDay()) {
                paint = this.mCurDayTextPaint;
            } else {
                paint = (!calendar.isCurrentMonth() || !isInRange) ? this.mOtherMonthTextPaint : this.mCurMonthTextPaint;
            }
            canvas.drawText(valueOf2, f2, dipToPx, paint);
        }
        if (onCalendarIntercept(calendar)) {
            int i4 = this.f902mH;
            canvas.drawLine((float) (i + i4), (float) (i4 + i2), (float) ((i + this.mItemWidth) - this.f902mH), (float) ((i2 + this.mItemHeight) - this.f902mH), this.mDisablePaint);
        }
    }

    private static int dipToPx(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
