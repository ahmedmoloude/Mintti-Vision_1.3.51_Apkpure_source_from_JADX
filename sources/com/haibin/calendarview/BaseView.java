package com.haibin.calendarview;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import com.haibin.calendarview.Calendar;
import java.util.List;

public abstract class BaseView extends View implements View.OnClickListener, View.OnLongClickListener {
    static final int TEXT_SIZE = 14;
    boolean isClick;
    protected Paint mCurDayLunarTextPaint;
    protected Paint mCurDayTextPaint;
    protected Paint mCurMonthLunarTextPaint;
    protected Paint mCurMonthTextPaint;
    int mCurrentItem;
    CalendarViewDelegate mDelegate;
    protected int mItemHeight;
    protected int mItemWidth;
    List<Calendar> mItems;
    protected Paint mOtherMonthLunarTextPaint;
    protected Paint mOtherMonthTextPaint;
    CalendarLayout mParentLayout;
    protected Paint mSchemeLunarTextPaint;
    protected Paint mSchemePaint;
    protected Paint mSchemeTextPaint;
    protected Paint mSelectTextPaint;
    protected Paint mSelectedLunarTextPaint;
    protected Paint mSelectedPaint;
    protected float mTextBaseLine;

    /* renamed from: mX */
    float f355mX;

    /* renamed from: mY */
    float f356mY;

    /* access modifiers changed from: protected */
    public void initPaint() {
    }

    /* access modifiers changed from: protected */
    public abstract void onDestroy();

    /* access modifiers changed from: protected */
    public void onPreviewHook() {
    }

    /* access modifiers changed from: package-private */
    public abstract void updateCurrentDate();

    public BaseView(Context context) {
        this(context, (AttributeSet) null);
    }

    public BaseView(Context context, AttributeSet attributeSet) {
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
        this.isClick = true;
        this.mCurrentItem = -1;
        initPaint(context);
    }

    private void initPaint(Context context) {
        this.mCurMonthTextPaint.setAntiAlias(true);
        this.mCurMonthTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mCurMonthTextPaint.setColor(-15658735);
        this.mCurMonthTextPaint.setFakeBoldText(true);
        this.mCurMonthTextPaint.setTextSize((float) CalendarUtil.dipToPx(context, 14.0f));
        this.mOtherMonthTextPaint.setAntiAlias(true);
        this.mOtherMonthTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mOtherMonthTextPaint.setColor(-1973791);
        this.mOtherMonthTextPaint.setFakeBoldText(true);
        this.mOtherMonthTextPaint.setTextSize((float) CalendarUtil.dipToPx(context, 14.0f));
        this.mCurMonthLunarTextPaint.setAntiAlias(true);
        this.mCurMonthLunarTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mSelectedLunarTextPaint.setAntiAlias(true);
        this.mSelectedLunarTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mOtherMonthLunarTextPaint.setAntiAlias(true);
        this.mOtherMonthLunarTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mSchemeLunarTextPaint.setAntiAlias(true);
        this.mSchemeLunarTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mSchemeTextPaint.setAntiAlias(true);
        this.mSchemeTextPaint.setStyle(Paint.Style.FILL);
        this.mSchemeTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mSchemeTextPaint.setColor(-1223853);
        this.mSchemeTextPaint.setFakeBoldText(true);
        this.mSchemeTextPaint.setTextSize((float) CalendarUtil.dipToPx(context, 14.0f));
        this.mSelectTextPaint.setAntiAlias(true);
        this.mSelectTextPaint.setStyle(Paint.Style.FILL);
        this.mSelectTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mSelectTextPaint.setColor(-1223853);
        this.mSelectTextPaint.setFakeBoldText(true);
        this.mSelectTextPaint.setTextSize((float) CalendarUtil.dipToPx(context, 14.0f));
        this.mSchemePaint.setAntiAlias(true);
        this.mSchemePaint.setStyle(Paint.Style.FILL);
        this.mSchemePaint.setStrokeWidth(2.0f);
        this.mSchemePaint.setColor(-1052689);
        this.mCurDayTextPaint.setAntiAlias(true);
        this.mCurDayTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mCurDayTextPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.mCurDayTextPaint.setFakeBoldText(true);
        this.mCurDayTextPaint.setTextSize((float) CalendarUtil.dipToPx(context, 14.0f));
        this.mCurDayLunarTextPaint.setAntiAlias(true);
        this.mCurDayLunarTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mCurDayLunarTextPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.mCurDayLunarTextPaint.setFakeBoldText(true);
        this.mCurDayLunarTextPaint.setTextSize((float) CalendarUtil.dipToPx(context, 14.0f));
        this.mSelectedPaint.setAntiAlias(true);
        this.mSelectedPaint.setStyle(Paint.Style.FILL);
        this.mSelectedPaint.setStrokeWidth(2.0f);
        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    /* access modifiers changed from: package-private */
    public final void setup(CalendarViewDelegate calendarViewDelegate) {
        this.mDelegate = calendarViewDelegate;
        updateStyle();
        updateItemHeight();
        initPaint();
    }

    /* access modifiers changed from: package-private */
    public final void updateStyle() {
        CalendarViewDelegate calendarViewDelegate = this.mDelegate;
        if (calendarViewDelegate != null) {
            this.mCurDayTextPaint.setColor(calendarViewDelegate.getCurDayTextColor());
            this.mCurDayLunarTextPaint.setColor(this.mDelegate.getCurDayLunarTextColor());
            this.mCurMonthTextPaint.setColor(this.mDelegate.getCurrentMonthTextColor());
            this.mOtherMonthTextPaint.setColor(this.mDelegate.getOtherMonthTextColor());
            this.mCurMonthLunarTextPaint.setColor(this.mDelegate.getCurrentMonthLunarTextColor());
            this.mSelectedLunarTextPaint.setColor(this.mDelegate.getSelectedLunarTextColor());
            this.mSelectTextPaint.setColor(this.mDelegate.getSelectedTextColor());
            this.mOtherMonthLunarTextPaint.setColor(this.mDelegate.getOtherMonthLunarTextColor());
            this.mSchemeLunarTextPaint.setColor(this.mDelegate.getSchemeLunarTextColor());
            this.mSchemePaint.setColor(this.mDelegate.getSchemeThemeColor());
            this.mSchemeTextPaint.setColor(this.mDelegate.getSchemeTextColor());
            this.mCurMonthTextPaint.setTextSize((float) this.mDelegate.getDayTextSize());
            this.mOtherMonthTextPaint.setTextSize((float) this.mDelegate.getDayTextSize());
            this.mCurDayTextPaint.setTextSize((float) this.mDelegate.getDayTextSize());
            this.mSchemeTextPaint.setTextSize((float) this.mDelegate.getDayTextSize());
            this.mSelectTextPaint.setTextSize((float) this.mDelegate.getDayTextSize());
            this.mCurMonthLunarTextPaint.setTextSize((float) this.mDelegate.getLunarTextSize());
            this.mSelectedLunarTextPaint.setTextSize((float) this.mDelegate.getLunarTextSize());
            this.mCurDayLunarTextPaint.setTextSize((float) this.mDelegate.getLunarTextSize());
            this.mOtherMonthLunarTextPaint.setTextSize((float) this.mDelegate.getLunarTextSize());
            this.mSchemeLunarTextPaint.setTextSize((float) this.mDelegate.getLunarTextSize());
            this.mSelectedPaint.setStyle(Paint.Style.FILL);
            this.mSelectedPaint.setColor(this.mDelegate.getSelectedThemeColor());
        }
    }

    /* access modifiers changed from: package-private */
    public void updateItemHeight() {
        this.mItemHeight = this.mDelegate.getCalendarItemHeight();
        Paint.FontMetrics fontMetrics = this.mCurMonthTextPaint.getFontMetrics();
        this.mTextBaseLine = (((float) (this.mItemHeight / 2)) - fontMetrics.descent) + ((fontMetrics.bottom - fontMetrics.top) / 2.0f);
    }

    /* access modifiers changed from: package-private */
    public final void removeSchemes() {
        for (Calendar next : this.mItems) {
            next.setScheme("");
            next.setSchemeColor(0);
            next.setSchemes((List<Calendar.Scheme>) null);
        }
    }

    /* access modifiers changed from: package-private */
    public final void addSchemesFromMap() {
        if (this.mDelegate.mSchemeDatesMap != null && this.mDelegate.mSchemeDatesMap.size() != 0) {
            for (Calendar next : this.mItems) {
                if (this.mDelegate.mSchemeDatesMap.containsKey(next.toString())) {
                    Calendar calendar = this.mDelegate.mSchemeDatesMap.get(next.toString());
                    if (calendar != null) {
                        next.setScheme(TextUtils.isEmpty(calendar.getScheme()) ? this.mDelegate.getSchemeText() : calendar.getScheme());
                        next.setSchemeColor(calendar.getSchemeColor());
                        next.setSchemes(calendar.getSchemes());
                    }
                } else {
                    next.setScheme("");
                    next.setSchemeColor(0);
                    next.setSchemes((List<Calendar.Scheme>) null);
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (motionEvent.getPointerCount() > 1) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.f355mX = motionEvent.getX();
            this.f356mY = motionEvent.getY();
            this.isClick = true;
        } else if (action == 1) {
            this.f355mX = motionEvent.getX();
            this.f356mY = motionEvent.getY();
        } else if (action == 2 && this.isClick) {
            if (Math.abs(motionEvent.getY() - this.f356mY) <= 50.0f) {
                z = true;
            }
            this.isClick = z;
        }
        return super.onTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public boolean isSelected(Calendar calendar) {
        List<Calendar> list = this.mItems;
        return list != null && list.indexOf(calendar) == this.mCurrentItem;
    }

    /* access modifiers changed from: package-private */
    public final void update() {
        if (this.mDelegate.mSchemeDatesMap == null || this.mDelegate.mSchemeDatesMap.size() == 0) {
            removeSchemes();
            invalidate();
            return;
        }
        addSchemesFromMap();
        invalidate();
    }

    /* access modifiers changed from: protected */
    public final boolean onCalendarIntercept(Calendar calendar) {
        return this.mDelegate.mCalendarInterceptListener != null && this.mDelegate.mCalendarInterceptListener.onCalendarIntercept(calendar);
    }

    /* access modifiers changed from: protected */
    public final boolean isInRange(Calendar calendar) {
        CalendarViewDelegate calendarViewDelegate = this.mDelegate;
        return calendarViewDelegate != null && CalendarUtil.isCalendarInRange(calendar, calendarViewDelegate);
    }
}
