package com.haibin.calendarview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarLayout extends LinearLayout {
    private static final int ACTIVE_POINTER = 1;
    private static final int CALENDAR_SHOW_MODE_BOTH_MONTH_WEEK_VIEW = 0;
    private static final int CALENDAR_SHOW_MODE_ONLY_MONTH_VIEW = 2;
    private static final int CALENDAR_SHOW_MODE_ONLY_WEEK_VIEW = 1;
    private static final int GESTURE_MODE_DEFAULT = 0;
    private static final int GESTURE_MODE_DISABLED = 2;
    private static final int INVALID_POINTER = -1;
    private static final int STATUS_EXPAND = 0;
    private static final int STATUS_SHRINK = 1;
    private float downY;
    /* access modifiers changed from: private */
    public boolean isAnimating = false;
    /* access modifiers changed from: private */
    public boolean isWeekView;
    private int mActivePointerId;
    private int mCalendarShowMode;
    CalendarView mCalendarView;
    ViewGroup mContentView;
    private int mContentViewId;
    /* access modifiers changed from: private */
    public int mContentViewTranslateY;
    private int mDefaultStatus;
    /* access modifiers changed from: private */
    public CalendarViewDelegate mDelegate;
    /* access modifiers changed from: private */
    public int mGestureMode;
    private int mItemHeight;
    private float mLastX;
    private float mLastY;
    private int mMaximumVelocity;
    MonthViewPager mMonthView;
    private VelocityTracker mVelocityTracker;
    /* access modifiers changed from: private */
    public int mViewPagerTranslateY = 0;
    WeekBar mWeekBar;
    WeekViewPager mWeekPager;
    YearViewPager mYearView;

    public interface CalendarScrollView {
        boolean isScrollToTop();
    }

    public CalendarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOrientation(1);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1484R.styleable.CalendarLayout);
        this.mContentViewId = obtainStyledAttributes.getResourceId(C1484R.styleable.CalendarLayout_calendar_content_view_id, 0);
        this.mDefaultStatus = obtainStyledAttributes.getInt(C1484R.styleable.CalendarLayout_default_status, 0);
        this.mCalendarShowMode = obtainStyledAttributes.getInt(C1484R.styleable.CalendarLayout_calendar_show_mode, 0);
        this.mGestureMode = obtainStyledAttributes.getInt(C1484R.styleable.CalendarLayout_gesture_mode, 0);
        obtainStyledAttributes.recycle();
        this.mVelocityTracker = VelocityTracker.obtain();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        viewConfiguration.getScaledTouchSlop();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    /* access modifiers changed from: package-private */
    public final void setup(CalendarViewDelegate calendarViewDelegate) {
        Calendar calendar;
        this.mDelegate = calendarViewDelegate;
        this.mItemHeight = calendarViewDelegate.getCalendarItemHeight();
        if (calendarViewDelegate.mSelectedCalendar.isAvailable()) {
            calendar = calendarViewDelegate.mSelectedCalendar;
        } else {
            calendar = calendarViewDelegate.createCurrentDate();
        }
        initCalendarPosition(calendar);
        updateContentViewTranslateY();
    }

    private void initCalendarPosition(Calendar calendar) {
        updateSelectPosition((CalendarUtil.getMonthViewStartDiff(calendar, this.mDelegate.getWeekStart()) + calendar.getDay()) - 1);
    }

    /* access modifiers changed from: package-private */
    public final void updateSelectPosition(int i) {
        this.mViewPagerTranslateY = (((i + 7) / 7) - 1) * this.mItemHeight;
    }

    /* access modifiers changed from: package-private */
    public final void updateSelectWeek(int i) {
        this.mViewPagerTranslateY = (i - 1) * this.mItemHeight;
    }

    /* access modifiers changed from: package-private */
    public void updateContentViewTranslateY() {
        ViewGroup viewGroup;
        Calendar calendar = this.mDelegate.mIndexCalendar;
        if (this.mDelegate.getMonthViewShowMode() == 0) {
            this.mContentViewTranslateY = this.mItemHeight * 5;
        } else {
            this.mContentViewTranslateY = CalendarUtil.getMonthViewHeight(calendar.getYear(), calendar.getMonth(), this.mItemHeight, this.mDelegate.getWeekStart()) - this.mItemHeight;
        }
        if (this.mWeekPager.getVisibility() == 0 && (viewGroup = this.mContentView) != null) {
            viewGroup.setTranslationY((float) (-this.mContentViewTranslateY));
        }
    }

    /* access modifiers changed from: package-private */
    public final void updateCalendarItemHeight() {
        this.mItemHeight = this.mDelegate.getCalendarItemHeight();
        if (this.mContentView != null) {
            Calendar calendar = this.mDelegate.mIndexCalendar;
            updateSelectWeek(CalendarUtil.getWeekFromDayInMonth(calendar, this.mDelegate.getWeekStart()));
            if (this.mDelegate.getMonthViewShowMode() == 0) {
                this.mContentViewTranslateY = this.mItemHeight * 5;
            } else {
                this.mContentViewTranslateY = CalendarUtil.getMonthViewHeight(calendar.getYear(), calendar.getMonth(), this.mItemHeight, this.mDelegate.getWeekStart()) - this.mItemHeight;
            }
            translationViewPager();
            if (this.mWeekPager.getVisibility() == 0) {
                this.mContentView.setTranslationY((float) (-this.mContentViewTranslateY));
            }
        }
    }

    public void hideCalendarView() {
        CalendarView calendarView = this.mCalendarView;
        if (calendarView != null) {
            calendarView.setVisibility(8);
            if (!isExpand()) {
                expand(0);
            }
            requestLayout();
        }
    }

    public void showCalendarView() {
        this.mCalendarView.setVisibility(0);
        requestLayout();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0047, code lost:
        if (r0 != 6) goto L_0x0166;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r9) {
        /*
            r8 = this;
            int r0 = r8.mGestureMode
            r1 = 2
            r2 = 0
            if (r0 == r1) goto L_0x017a
            int r0 = r8.mCalendarShowMode
            if (r0 == r1) goto L_0x017a
            r3 = 1
            if (r0 != r3) goto L_0x000f
            goto L_0x017a
        L_0x000f:
            com.haibin.calendarview.CalendarViewDelegate r0 = r8.mDelegate
            if (r0 != 0) goto L_0x0014
            return r2
        L_0x0014:
            boolean r0 = r0.isShowYearSelectedLayout
            if (r0 == 0) goto L_0x0019
            return r2
        L_0x0019:
            android.view.ViewGroup r0 = r8.mContentView
            if (r0 == 0) goto L_0x017a
            com.haibin.calendarview.CalendarView r0 = r8.mCalendarView
            if (r0 == 0) goto L_0x017a
            int r0 = r0.getVisibility()
            r4 = 8
            if (r0 != r4) goto L_0x002b
            goto L_0x017a
        L_0x002b:
            int r0 = r9.getAction()
            float r4 = r9.getY()
            android.view.VelocityTracker r5 = r8.mVelocityTracker
            r5.addMovement(r9)
            if (r0 == 0) goto L_0x016b
            r5 = 0
            if (r0 == r3) goto L_0x0110
            r6 = -1
            if (r0 == r1) goto L_0x0073
            r1 = 3
            if (r0 == r1) goto L_0x005f
            r1 = 5
            if (r0 == r1) goto L_0x004b
            r1 = 6
            if (r0 == r1) goto L_0x005f
            goto L_0x0166
        L_0x004b:
            int r0 = r9.getActionIndex()
            int r0 = r9.getPointerId(r0)
            r8.mActivePointerId = r0
            if (r0 != 0) goto L_0x0166
            float r0 = r9.getY(r0)
            r8.mLastY = r0
            goto L_0x0166
        L_0x005f:
            int r0 = r8.mActivePointerId
            int r0 = r8.getPointerIndex(r9, r0)
            int r1 = r8.mActivePointerId
            if (r1 != r6) goto L_0x006b
            goto L_0x0166
        L_0x006b:
            float r0 = r9.getY(r0)
            r8.mLastY = r0
            goto L_0x0166
        L_0x0073:
            int r0 = r8.mActivePointerId
            r8.getPointerIndex(r9, r0)
            int r0 = r8.mActivePointerId
            if (r0 != r6) goto L_0x0080
            r8.mLastY = r4
            r8.mActivePointerId = r3
        L_0x0080:
            float r0 = r8.mLastY
            float r0 = r4 - r0
            int r1 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r1 >= 0) goto L_0x00bd
            android.view.ViewGroup r6 = r8.mContentView
            float r6 = r6.getTranslationY()
            int r7 = r8.mContentViewTranslateY
            int r7 = -r7
            float r7 = (float) r7
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 != 0) goto L_0x00bd
            r8.mLastY = r4
            r9.setAction(r2)
            r8.dispatchTouchEvent(r9)
            com.haibin.calendarview.WeekViewPager r9 = r8.mWeekPager
            r9.setVisibility(r2)
            com.haibin.calendarview.MonthViewPager r9 = r8.mMonthView
            r0 = 4
            r9.setVisibility(r0)
            boolean r9 = r8.isWeekView
            if (r9 != 0) goto L_0x00ba
            com.haibin.calendarview.CalendarViewDelegate r9 = r8.mDelegate
            com.haibin.calendarview.CalendarView$OnViewChangeListener r9 = r9.mViewChangeListener
            if (r9 == 0) goto L_0x00ba
            com.haibin.calendarview.CalendarViewDelegate r9 = r8.mDelegate
            com.haibin.calendarview.CalendarView$OnViewChangeListener r9 = r9.mViewChangeListener
            r9.onViewChange(r2)
        L_0x00ba:
            r8.isWeekView = r3
            return r2
        L_0x00bd:
            r8.hideWeek(r2)
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 <= 0) goto L_0x00de
            android.view.ViewGroup r2 = r8.mContentView
            float r2 = r2.getTranslationY()
            float r2 = r2 + r0
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 < 0) goto L_0x00de
            android.view.ViewGroup r0 = r8.mContentView
            r0.setTranslationY(r5)
            r8.translationViewPager()
            r8.mLastY = r4
            boolean r9 = super.onTouchEvent(r9)
            return r9
        L_0x00de:
            if (r1 >= 0) goto L_0x0100
            android.view.ViewGroup r1 = r8.mContentView
            float r1 = r1.getTranslationY()
            float r1 = r1 + r0
            int r2 = r8.mContentViewTranslateY
            int r3 = -r2
            float r3 = (float) r3
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 > 0) goto L_0x0100
            android.view.ViewGroup r0 = r8.mContentView
            int r1 = -r2
            float r1 = (float) r1
            r0.setTranslationY(r1)
            r8.translationViewPager()
            r8.mLastY = r4
            boolean r9 = super.onTouchEvent(r9)
            return r9
        L_0x0100:
            android.view.ViewGroup r1 = r8.mContentView
            float r2 = r1.getTranslationY()
            float r2 = r2 + r0
            r1.setTranslationY(r2)
            r8.translationViewPager()
            r8.mLastY = r4
            goto L_0x0166
        L_0x0110:
            android.view.VelocityTracker r0 = r8.mVelocityTracker
            r1 = 1000(0x3e8, float:1.401E-42)
            int r2 = r8.mMaximumVelocity
            float r2 = (float) r2
            r0.computeCurrentVelocity(r1, r2)
            float r0 = r0.getYVelocity()
            android.view.ViewGroup r1 = r8.mContentView
            float r1 = r1.getTranslationY()
            int r1 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r1 == 0) goto L_0x0163
            android.view.ViewGroup r1 = r8.mContentView
            float r1 = r1.getTranslationY()
            int r2 = r8.mContentViewTranslateY
            float r2 = (float) r2
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 != 0) goto L_0x0136
            goto L_0x0163
        L_0x0136:
            float r1 = java.lang.Math.abs(r0)
            r2 = 1145569280(0x44480000, float:800.0)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 < 0) goto L_0x0150
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x0148
            r8.shrink()
            goto L_0x014b
        L_0x0148:
            r8.expand()
        L_0x014b:
            boolean r9 = super.onTouchEvent(r9)
            return r9
        L_0x0150:
            float r0 = r9.getY()
            float r1 = r8.downY
            float r0 = r0 - r1
            int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r0 <= 0) goto L_0x015f
            r8.expand()
            goto L_0x0166
        L_0x015f:
            r8.shrink()
            goto L_0x0166
        L_0x0163:
            r8.expand()
        L_0x0166:
            boolean r9 = super.onTouchEvent(r9)
            return r9
        L_0x016b:
            int r0 = r9.getActionIndex()
            int r9 = r9.getPointerId(r0)
            r8.mActivePointerId = r9
            r8.downY = r4
            r8.mLastY = r4
            return r3
        L_0x017a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.haibin.calendarview.CalendarLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        CalendarView calendarView;
        ViewGroup viewGroup;
        if (this.isAnimating) {
            return super.dispatchTouchEvent(motionEvent);
        }
        if (this.mGestureMode == 2) {
            return super.dispatchTouchEvent(motionEvent);
        }
        if (this.mYearView == null || (calendarView = this.mCalendarView) == null || calendarView.getVisibility() == 8 || (viewGroup = this.mContentView) == null || viewGroup.getVisibility() != 0) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int i = this.mCalendarShowMode;
        if (i == 2 || i == 1) {
            return super.dispatchTouchEvent(motionEvent);
        }
        if (this.mYearView.getVisibility() == 0 || this.mDelegate.isShowYearSelectedLayout) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        float y = motionEvent.getY();
        if (action != 2 || y - this.mLastY <= 0.0f || this.mContentView.getTranslationY() != ((float) (-this.mContentViewTranslateY)) || !isScrollTop()) {
            return super.dispatchTouchEvent(motionEvent);
        }
        requestDisallowInterceptTouchEvent(false);
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        CalendarView calendarView;
        ViewGroup viewGroup;
        if (this.isAnimating) {
            return true;
        }
        if (this.mGestureMode == 2) {
            return false;
        }
        if (this.mYearView == null || (calendarView = this.mCalendarView) == null || calendarView.getVisibility() == 8 || (viewGroup = this.mContentView) == null || viewGroup.getVisibility() != 0) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        int i = this.mCalendarShowMode;
        if (i == 2 || i == 1) {
            return false;
        }
        if (this.mYearView.getVisibility() == 0 || this.mDelegate.isShowYearSelectedLayout) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        float y = motionEvent.getY();
        float x = motionEvent.getX();
        if (action == 0) {
            this.mActivePointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
            this.downY = y;
            this.mLastY = y;
            this.mLastX = x;
        } else if (action == 2) {
            float f = y - this.mLastY;
            float f2 = x - this.mLastX;
            int i2 = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
            if (i2 < 0 && this.mContentView.getTranslationY() == ((float) (-this.mContentViewTranslateY))) {
                return false;
            }
            int i3 = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
            if (i3 > 0 && this.mContentView.getTranslationY() == ((float) (-this.mContentViewTranslateY)) && y >= ((float) (this.mDelegate.getCalendarItemHeight() + this.mDelegate.getWeekBarHeight())) && !isScrollTop()) {
                return false;
            }
            if (i3 > 0 && this.mContentView.getTranslationY() == 0.0f && y >= ((float) CalendarUtil.dipToPx(getContext(), 98.0f))) {
                return false;
            }
            if (Math.abs(f) > Math.abs(f2) && ((i3 > 0 && this.mContentView.getTranslationY() <= 0.0f) || (i2 < 0 && this.mContentView.getTranslationY() >= ((float) (-this.mContentViewTranslateY))))) {
                this.mLastY = y;
                return true;
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    private int getPointerIndex(MotionEvent motionEvent, int i) {
        int findPointerIndex = motionEvent.findPointerIndex(i);
        if (findPointerIndex == -1) {
            this.mActivePointerId = -1;
        }
        return findPointerIndex;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (this.mContentView == null || this.mCalendarView == null) {
            super.onMeasure(i, i2);
            return;
        }
        int year = this.mDelegate.mIndexCalendar.getYear();
        int month = this.mDelegate.mIndexCalendar.getMonth();
        int dipToPx = CalendarUtil.dipToPx(getContext(), 1.0f) + this.mDelegate.getWeekBarHeight();
        int monthViewHeight = CalendarUtil.getMonthViewHeight(year, month, this.mDelegate.getCalendarItemHeight(), this.mDelegate.getWeekStart(), this.mDelegate.getMonthViewShowMode()) + dipToPx;
        int size = View.MeasureSpec.getSize(i2);
        if (this.mDelegate.isFullScreenCalendar()) {
            super.onMeasure(i, i2);
            this.mContentView.measure(i, View.MeasureSpec.makeMeasureSpec((size - dipToPx) - this.mDelegate.getCalendarItemHeight(), 1073741824));
            ViewGroup viewGroup = this.mContentView;
            viewGroup.layout(viewGroup.getLeft(), this.mContentView.getTop(), this.mContentView.getRight(), this.mContentView.getBottom());
            return;
        }
        if (monthViewHeight >= size && this.mMonthView.getHeight() > 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(monthViewHeight + dipToPx + this.mDelegate.getWeekBarHeight(), 1073741824);
            size = monthViewHeight;
        } else if (monthViewHeight < size && this.mMonthView.getHeight() > 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
        }
        if (this.mCalendarShowMode == 2 || this.mCalendarView.getVisibility() == 8) {
            monthViewHeight = this.mCalendarView.getVisibility() == 8 ? 0 : this.mCalendarView.getHeight();
        } else if (this.mGestureMode != 2 || this.isAnimating) {
            size -= dipToPx;
            monthViewHeight = this.mItemHeight;
        } else if (!isExpand()) {
            size -= dipToPx;
            monthViewHeight = this.mItemHeight;
        }
        super.onMeasure(i, i2);
        this.mContentView.measure(i, View.MeasureSpec.makeMeasureSpec(size - monthViewHeight, 1073741824));
        ViewGroup viewGroup2 = this.mContentView;
        viewGroup2.layout(viewGroup2.getLeft(), this.mContentView.getTop(), this.mContentView.getRight(), this.mContentView.getBottom());
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mMonthView = (MonthViewPager) findViewById(C1484R.C1488id.vp_month);
        this.mWeekPager = (WeekViewPager) findViewById(C1484R.C1488id.vp_week);
        if (getChildCount() > 0) {
            this.mCalendarView = (CalendarView) getChildAt(0);
        }
        this.mContentView = (ViewGroup) findViewById(this.mContentViewId);
        this.mYearView = (YearViewPager) findViewById(C1484R.C1488id.selectLayout);
    }

    private void translationViewPager() {
        this.mMonthView.setTranslationY(((float) this.mViewPagerTranslateY) * ((this.mContentView.getTranslationY() * 1.0f) / ((float) this.mContentViewTranslateY)));
    }

    public void setModeBothMonthWeekView() {
        this.mCalendarShowMode = 0;
        requestLayout();
    }

    public void setModeOnlyWeekView() {
        this.mCalendarShowMode = 1;
        requestLayout();
    }

    public void setModeOnlyMonthView() {
        this.mCalendarShowMode = 2;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("super", super.onSaveInstanceState());
        bundle.putBoolean("isExpand", isExpand());
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        Parcelable parcelable2 = bundle.getParcelable("super");
        if (bundle.getBoolean("isExpand")) {
            post(new Runnable() {
                public void run() {
                    CalendarLayout.this.expand(0);
                }
            });
        } else {
            post(new Runnable() {
                public void run() {
                    CalendarLayout.this.shrink(0);
                }
            });
        }
        super.onRestoreInstanceState(parcelable2);
    }

    public final boolean isExpand() {
        return this.mMonthView.getVisibility() == 0;
    }

    public boolean expand() {
        return expand(240);
    }

    public boolean expand(int i) {
        if (this.isAnimating || this.mCalendarShowMode == 1 || this.mContentView == null) {
            return false;
        }
        if (this.mMonthView.getVisibility() != 0) {
            this.mWeekPager.setVisibility(8);
            onShowMonthView();
            this.isWeekView = false;
            this.mMonthView.setVisibility(0);
        }
        ViewGroup viewGroup = this.mContentView;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(viewGroup, "translationY", new float[]{viewGroup.getTranslationY(), 0.0f});
        ofFloat.setDuration((long) i);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CalendarLayout.this.mMonthView.setTranslationY(((float) CalendarLayout.this.mViewPagerTranslateY) * ((((Float) valueAnimator.getAnimatedValue()).floatValue() * 1.0f) / ((float) CalendarLayout.this.mContentViewTranslateY)));
                boolean unused = CalendarLayout.this.isAnimating = true;
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                boolean unused = CalendarLayout.this.isAnimating = false;
                if (CalendarLayout.this.mGestureMode == 2) {
                    CalendarLayout.this.requestLayout();
                }
                CalendarLayout.this.hideWeek(true);
                if (CalendarLayout.this.mDelegate.mViewChangeListener != null && CalendarLayout.this.isWeekView) {
                    CalendarLayout.this.mDelegate.mViewChangeListener.onViewChange(true);
                }
                boolean unused2 = CalendarLayout.this.isWeekView = false;
            }
        });
        ofFloat.start();
        return true;
    }

    public boolean shrink() {
        return shrink(240);
    }

    public boolean shrink(int i) {
        ViewGroup viewGroup;
        if (this.mGestureMode == 2) {
            requestLayout();
        }
        if (this.isAnimating || (viewGroup = this.mContentView) == null) {
            return false;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(viewGroup, "translationY", new float[]{viewGroup.getTranslationY(), (float) (-this.mContentViewTranslateY)});
        ofFloat.setDuration((long) i);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                CalendarLayout.this.mMonthView.setTranslationY(((float) CalendarLayout.this.mViewPagerTranslateY) * ((((Float) valueAnimator.getAnimatedValue()).floatValue() * 1.0f) / ((float) CalendarLayout.this.mContentViewTranslateY)));
                boolean unused = CalendarLayout.this.isAnimating = true;
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                boolean unused = CalendarLayout.this.isAnimating = false;
                CalendarLayout.this.showWeek();
                boolean unused2 = CalendarLayout.this.isWeekView = true;
            }
        });
        ofFloat.start();
        return true;
    }

    /* access modifiers changed from: package-private */
    public final void initStatus() {
        if ((this.mDefaultStatus == 1 || this.mCalendarShowMode == 1) && this.mCalendarShowMode != 2) {
            if (this.mContentView == null) {
                this.mWeekPager.setVisibility(0);
                this.mMonthView.setVisibility(8);
                return;
            }
            post(new Runnable() {
                public void run() {
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(CalendarLayout.this.mContentView, "translationY", new float[]{CalendarLayout.this.mContentView.getTranslationY(), (float) (-CalendarLayout.this.mContentViewTranslateY)});
                    ofFloat.setDuration(0);
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            CalendarLayout.this.mMonthView.setTranslationY(((float) CalendarLayout.this.mViewPagerTranslateY) * ((((Float) valueAnimator.getAnimatedValue()).floatValue() * 1.0f) / ((float) CalendarLayout.this.mContentViewTranslateY)));
                            boolean unused = CalendarLayout.this.isAnimating = true;
                        }
                    });
                    ofFloat.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            boolean unused = CalendarLayout.this.isAnimating = false;
                            boolean unused2 = CalendarLayout.this.isWeekView = true;
                            CalendarLayout.this.showWeek();
                            if (CalendarLayout.this.mDelegate != null && CalendarLayout.this.mDelegate.mViewChangeListener != null) {
                                CalendarLayout.this.mDelegate.mViewChangeListener.onViewChange(false);
                            }
                        }
                    });
                    ofFloat.start();
                }
            });
        } else if (this.mDelegate.mViewChangeListener != null) {
            post(new Runnable() {
                public void run() {
                    CalendarLayout.this.mDelegate.mViewChangeListener.onViewChange(true);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void hideWeek(boolean z) {
        if (z) {
            onShowMonthView();
        }
        this.mWeekPager.setVisibility(8);
        this.mMonthView.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void showWeek() {
        onShowWeekView();
        WeekViewPager weekViewPager = this.mWeekPager;
        if (!(weekViewPager == null || weekViewPager.getAdapter() == null)) {
            this.mWeekPager.getAdapter().notifyDataSetChanged();
            this.mWeekPager.setVisibility(0);
        }
        this.mMonthView.setVisibility(4);
    }

    private void onShowWeekView() {
        CalendarViewDelegate calendarViewDelegate;
        if (this.mWeekPager.getVisibility() != 0 && (calendarViewDelegate = this.mDelegate) != null && calendarViewDelegate.mViewChangeListener != null && !this.isWeekView) {
            this.mDelegate.mViewChangeListener.onViewChange(false);
        }
    }

    private void onShowMonthView() {
        CalendarViewDelegate calendarViewDelegate;
        if (this.mMonthView.getVisibility() != 0 && (calendarViewDelegate = this.mDelegate) != null && calendarViewDelegate.mViewChangeListener != null && this.isWeekView) {
            this.mDelegate.mViewChangeListener.onViewChange(true);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isScrollTop() {
        ViewGroup viewGroup = this.mContentView;
        if (viewGroup instanceof CalendarScrollView) {
            return ((CalendarScrollView) viewGroup).isScrollToTop();
        }
        boolean z = true;
        if (viewGroup instanceof RecyclerView) {
            if (((RecyclerView) viewGroup).computeVerticalScrollOffset() == 0) {
                return true;
            }
            return false;
        } else if (viewGroup instanceof AbsListView) {
            AbsListView absListView = (AbsListView) viewGroup;
            if (absListView.getFirstVisiblePosition() != 0) {
                return false;
            }
            if (absListView.getChildAt(0).getTop() != 0) {
                z = false;
            }
            return z;
        } else if (viewGroup.getScrollY() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public final void hideContentView() {
        ViewGroup viewGroup = this.mContentView;
        if (viewGroup != null) {
            viewGroup.animate().translationY((float) (getHeight() - this.mMonthView.getHeight())).setDuration(220).setInterpolator(new LinearInterpolator()).setListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    CalendarLayout.this.mContentView.setVisibility(4);
                    CalendarLayout.this.mContentView.clearAnimation();
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public final void showContentView() {
        ViewGroup viewGroup = this.mContentView;
        if (viewGroup != null) {
            viewGroup.setTranslationY((float) (getHeight() - this.mMonthView.getHeight()));
            this.mContentView.setVisibility(0);
            this.mContentView.animate().translationY(0.0f).setDuration(180).setInterpolator(new LinearInterpolator()).setListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                }
            });
        }
    }

    private int getCalendarViewHeight() {
        int i;
        int i2;
        if (this.mMonthView.getVisibility() == 0) {
            i2 = this.mDelegate.getWeekBarHeight();
            i = this.mMonthView.getHeight();
        } else {
            i2 = this.mDelegate.getWeekBarHeight();
            i = this.mDelegate.getCalendarItemHeight();
        }
        return i2 + i;
    }
}
