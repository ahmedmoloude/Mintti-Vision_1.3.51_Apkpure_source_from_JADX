package com.haibin.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import com.haibin.calendarview.Calendar;
import java.util.List;

public abstract class YearView extends View {
    protected Paint mCurDayLunarTextPaint;
    protected Paint mCurDayTextPaint;
    protected Paint mCurMonthLunarTextPaint;
    protected Paint mCurMonthTextPaint;
    CalendarViewDelegate mDelegate;
    protected int mItemHeight;
    protected int mItemWidth;
    List<Calendar> mItems;
    protected int mLineCount;
    protected int mMonth;
    protected float mMonthTextBaseLine;
    protected Paint mMonthTextPaint;
    protected int mNextDiff;
    protected Paint mOtherMonthLunarTextPaint;
    protected Paint mOtherMonthTextPaint;
    protected Paint mSchemeLunarTextPaint;
    protected Paint mSchemePaint;
    protected Paint mSchemeTextPaint;
    protected Paint mSelectTextPaint;
    protected Paint mSelectedLunarTextPaint;
    protected Paint mSelectedPaint;
    protected float mTextBaseLine;
    protected int mWeekStart;
    protected float mWeekTextBaseLine;
    protected Paint mWeekTextPaint;
    protected int mYear;

    /* access modifiers changed from: protected */
    public abstract void onDrawMonth(Canvas canvas, int i, int i2, int i3, int i4, int i5, int i6);

    /* access modifiers changed from: protected */
    public abstract void onDrawScheme(Canvas canvas, Calendar calendar, int i, int i2);

    /* access modifiers changed from: protected */
    public abstract boolean onDrawSelected(Canvas canvas, Calendar calendar, int i, int i2, boolean z);

    /* access modifiers changed from: protected */
    public abstract void onDrawText(Canvas canvas, Calendar calendar, int i, int i2, boolean z, boolean z2);

    /* access modifiers changed from: protected */
    public abstract void onDrawWeek(Canvas canvas, int i, int i2, int i3, int i4, int i5);

    /* access modifiers changed from: protected */
    public void onPreviewHook() {
    }

    public YearView(Context context) {
        this(context, (AttributeSet) null);
    }

    public YearView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurMonthTextPaint = new Paint();
        this.mOtherMonthTextPaint = new Paint();
        this.mCurMonthLunarTextPaint = new Paint();
        this.mSelectedLunarTextPaint = new Paint();
        this.mOtherMonthLunarTextPaint = new Paint();
        this.mSchemeLunarTextPaint = new Paint();
        this.mSchemePaint = new Paint();
        this.mSelectedPaint = new Paint();
        this.mSchemeTextPaint = new Paint();
        this.mSelectTextPaint = new Paint();
        this.mCurDayTextPaint = new Paint();
        this.mCurDayLunarTextPaint = new Paint();
        this.mMonthTextPaint = new Paint();
        this.mWeekTextPaint = new Paint();
        initPaint();
    }

    private void initPaint() {
        this.mCurMonthTextPaint.setAntiAlias(true);
        this.mCurMonthTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mCurMonthTextPaint.setColor(-15658735);
        this.mCurMonthTextPaint.setFakeBoldText(true);
        this.mOtherMonthTextPaint.setAntiAlias(true);
        this.mOtherMonthTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mOtherMonthTextPaint.setColor(-1973791);
        this.mOtherMonthTextPaint.setFakeBoldText(true);
        this.mCurMonthLunarTextPaint.setAntiAlias(true);
        this.mCurMonthLunarTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mSelectedLunarTextPaint.setAntiAlias(true);
        this.mSelectedLunarTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mOtherMonthLunarTextPaint.setAntiAlias(true);
        this.mOtherMonthLunarTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mMonthTextPaint.setAntiAlias(true);
        this.mMonthTextPaint.setFakeBoldText(true);
        this.mWeekTextPaint.setAntiAlias(true);
        this.mWeekTextPaint.setFakeBoldText(true);
        this.mWeekTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mSchemeLunarTextPaint.setAntiAlias(true);
        this.mSchemeLunarTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mSchemeTextPaint.setAntiAlias(true);
        this.mSchemeTextPaint.setStyle(Paint.Style.FILL);
        this.mSchemeTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mSchemeTextPaint.setColor(-1223853);
        this.mSchemeTextPaint.setFakeBoldText(true);
        this.mSelectTextPaint.setAntiAlias(true);
        this.mSelectTextPaint.setStyle(Paint.Style.FILL);
        this.mSelectTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mSelectTextPaint.setColor(-1223853);
        this.mSelectTextPaint.setFakeBoldText(true);
        this.mSchemePaint.setAntiAlias(true);
        this.mSchemePaint.setStyle(Paint.Style.FILL);
        this.mSchemePaint.setStrokeWidth(2.0f);
        this.mSchemePaint.setColor(-1052689);
        this.mCurDayTextPaint.setAntiAlias(true);
        this.mCurDayTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mCurDayTextPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.mCurDayTextPaint.setFakeBoldText(true);
        this.mCurDayLunarTextPaint.setAntiAlias(true);
        this.mCurDayLunarTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mCurDayLunarTextPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.mCurDayLunarTextPaint.setFakeBoldText(true);
        this.mSelectedPaint.setAntiAlias(true);
        this.mSelectedPaint.setStyle(Paint.Style.FILL);
        this.mSelectedPaint.setStrokeWidth(2.0f);
    }

    /* access modifiers changed from: package-private */
    public final void setup(CalendarViewDelegate calendarViewDelegate) {
        this.mDelegate = calendarViewDelegate;
        updateStyle();
    }

    /* access modifiers changed from: package-private */
    public final void updateStyle() {
        CalendarViewDelegate calendarViewDelegate = this.mDelegate;
        if (calendarViewDelegate != null) {
            this.mCurMonthTextPaint.setTextSize((float) calendarViewDelegate.getYearViewDayTextSize());
            this.mSchemeTextPaint.setTextSize((float) this.mDelegate.getYearViewDayTextSize());
            this.mOtherMonthTextPaint.setTextSize((float) this.mDelegate.getYearViewDayTextSize());
            this.mCurDayTextPaint.setTextSize((float) this.mDelegate.getYearViewDayTextSize());
            this.mSelectTextPaint.setTextSize((float) this.mDelegate.getYearViewDayTextSize());
            this.mSchemeTextPaint.setColor(this.mDelegate.getYearViewSchemeTextColor());
            this.mCurMonthTextPaint.setColor(this.mDelegate.getYearViewDayTextColor());
            this.mOtherMonthTextPaint.setColor(this.mDelegate.getYearViewDayTextColor());
            this.mCurDayTextPaint.setColor(this.mDelegate.getYearViewCurDayTextColor());
            this.mSelectTextPaint.setColor(this.mDelegate.getYearViewSelectTextColor());
            this.mMonthTextPaint.setTextSize((float) this.mDelegate.getYearViewMonthTextSize());
            this.mMonthTextPaint.setColor(this.mDelegate.getYearViewMonthTextColor());
            this.mWeekTextPaint.setColor(this.mDelegate.getYearViewWeekTextColor());
            this.mWeekTextPaint.setTextSize((float) this.mDelegate.getYearViewWeekTextSize());
        }
    }

    /* access modifiers changed from: package-private */
    public final void init(int i, int i2) {
        this.mYear = i;
        this.mMonth = i2;
        this.mNextDiff = CalendarUtil.getMonthEndDiff(i, i2, this.mDelegate.getWeekStart());
        CalendarUtil.getMonthViewStartDiff(this.mYear, this.mMonth, this.mDelegate.getWeekStart());
        this.mItems = CalendarUtil.initCalendarForMonthView(this.mYear, this.mMonth, this.mDelegate.getCurrentDay(), this.mDelegate.getWeekStart());
        this.mLineCount = 6;
        addSchemesFromMap();
    }

    /* access modifiers changed from: package-private */
    public final void measureSize(int i, int i2) {
        Rect rect = new Rect();
        this.mCurMonthTextPaint.getTextBounds("1", 0, 1, rect);
        int height = (rect.height() * 12) + getMonthViewTop();
        if (i2 < height) {
            i2 = height;
        }
        getLayoutParams().width = i;
        getLayoutParams().height = i2;
        this.mItemHeight = (i2 - getMonthViewTop()) / 6;
        Paint.FontMetrics fontMetrics = this.mCurMonthTextPaint.getFontMetrics();
        this.mTextBaseLine = (((float) (this.mItemHeight / 2)) - fontMetrics.descent) + ((fontMetrics.bottom - fontMetrics.top) / 2.0f);
        Paint.FontMetrics fontMetrics2 = this.mMonthTextPaint.getFontMetrics();
        this.mMonthTextBaseLine = (((float) (this.mDelegate.getYearViewMonthHeight() / 2)) - fontMetrics2.descent) + ((fontMetrics2.bottom - fontMetrics2.top) / 2.0f);
        Paint.FontMetrics fontMetrics3 = this.mWeekTextPaint.getFontMetrics();
        this.mWeekTextBaseLine = (((float) (this.mDelegate.getYearViewWeekHeight() / 2)) - fontMetrics3.descent) + ((fontMetrics3.bottom - fontMetrics3.top) / 2.0f);
        invalidate();
    }

    private void addSchemesFromMap() {
        if (this.mDelegate.mSchemeDatesMap != null && this.mDelegate.mSchemeDatesMap.size() != 0) {
            for (Calendar next : this.mItems) {
                if (this.mDelegate.mSchemeDatesMap.containsKey(next.toString())) {
                    Calendar calendar = this.mDelegate.mSchemeDatesMap.get(next.toString());
                    next.setScheme(TextUtils.isEmpty(calendar.getScheme()) ? this.mDelegate.getSchemeText() : calendar.getScheme());
                    next.setSchemeColor(calendar.getSchemeColor());
                    next.setSchemes(calendar.getSchemes());
                } else {
                    next.setScheme("");
                    next.setSchemeColor(0);
                    next.setSchemes((List<Calendar.Scheme>) null);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.mItemWidth = (getWidth() - (this.mDelegate.getYearViewPadding() * 2)) / 7;
        onPreviewHook();
        onDrawMonth(canvas);
        onDrawWeek(canvas);
        onDrawMonthView(canvas);
    }

    private void onDrawMonth(Canvas canvas) {
        onDrawMonth(canvas, this.mYear, this.mMonth, this.mDelegate.getYearViewPadding(), this.mDelegate.getYearViewMonthMarginTop(), getWidth() - (this.mDelegate.getYearViewPadding() * 2), this.mDelegate.getYearViewMonthHeight() + this.mDelegate.getYearViewMonthMarginTop());
    }

    private int getMonthViewTop() {
        return this.mDelegate.getYearViewMonthMarginTop() + this.mDelegate.getYearViewMonthHeight() + this.mDelegate.getYearViewMonthMarginBottom() + this.mDelegate.getYearViewWeekHeight();
    }

    private void onDrawWeek(Canvas canvas) {
        if (this.mDelegate.getYearViewWeekHeight() > 0) {
            int weekStart = this.mDelegate.getWeekStart();
            if (weekStart > 0) {
                weekStart--;
            }
            int width = (getWidth() - (this.mDelegate.getYearViewPadding() * 2)) / 7;
            for (int i = 0; i < 7; i++) {
                onDrawWeek(canvas, r0, this.mDelegate.getYearViewPadding() + (i * width), this.mDelegate.getYearViewMonthHeight() + this.mDelegate.getYearViewMonthMarginTop() + this.mDelegate.getYearViewMonthMarginBottom(), width, this.mDelegate.getYearViewWeekHeight());
                int i2 = i2 + 1;
                if (i2 >= 7) {
                    i2 = 0;
                }
            }
        }
    }

    private void onDrawMonthView(Canvas canvas) {
        int i = 0;
        int i2 = 0;
        while (i2 < this.mLineCount) {
            int i3 = i;
            int i4 = 0;
            while (i4 < 7) {
                Calendar calendar = this.mItems.get(i3);
                if (i3 <= this.mItems.size() - this.mNextDiff) {
                    if (calendar.isCurrentMonth()) {
                        draw(canvas, calendar, i2, i4, i3);
                    }
                    i3++;
                    i4++;
                } else {
                    return;
                }
            }
            i2++;
            i = i3;
        }
    }

    private void draw(Canvas canvas, Calendar calendar, int i, int i2, int i3) {
        int yearViewPadding = (i2 * this.mItemWidth) + this.mDelegate.getYearViewPadding();
        int monthViewTop = (i * this.mItemHeight) + getMonthViewTop();
        boolean equals = calendar.equals(this.mDelegate.mSelectedCalendar);
        boolean hasScheme = calendar.hasScheme();
        if (hasScheme) {
            boolean z = false;
            if (equals) {
                z = onDrawSelected(canvas, calendar, yearViewPadding, monthViewTop, true);
            }
            if (z || !equals) {
                this.mSchemePaint.setColor(calendar.getSchemeColor() != 0 ? calendar.getSchemeColor() : this.mDelegate.getSchemeThemeColor());
                onDrawScheme(canvas, calendar, yearViewPadding, monthViewTop);
            }
        } else if (equals) {
            onDrawSelected(canvas, calendar, yearViewPadding, monthViewTop, false);
        }
        onDrawText(canvas, calendar, yearViewPadding, monthViewTop, hasScheme, equals);
    }
}
