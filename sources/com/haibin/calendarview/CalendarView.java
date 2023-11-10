package com.haibin.calendarview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import androidx.viewpager.widget.ViewPager;
import com.haibin.calendarview.YearRecyclerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarView extends FrameLayout {
    /* access modifiers changed from: private */
    public final CalendarViewDelegate mDelegate;
    /* access modifiers changed from: private */
    public MonthViewPager mMonthPager;
    CalendarLayout mParentLayout;
    /* access modifiers changed from: private */
    public WeekBar mWeekBar;
    private View mWeekLine;
    /* access modifiers changed from: private */
    public WeekViewPager mWeekPager;
    /* access modifiers changed from: private */
    public YearViewPager mYearViewPager;

    public interface OnCalendarInterceptListener {
        boolean onCalendarIntercept(Calendar calendar);

        void onCalendarInterceptClick(Calendar calendar, boolean z);
    }

    public interface OnCalendarLongClickListener {
        void onCalendarLongClick(Calendar calendar);

        void onCalendarLongClickOutOfRange(Calendar calendar);
    }

    public interface OnCalendarMultiSelectListener {
        void onCalendarMultiSelect(Calendar calendar, int i, int i2);

        void onCalendarMultiSelectOutOfRange(Calendar calendar);

        void onMultiSelectOutOfSize(Calendar calendar, int i);
    }

    public interface OnCalendarRangeSelectListener {
        void onCalendarRangeSelect(Calendar calendar, boolean z);

        void onCalendarSelectOutOfRange(Calendar calendar);

        void onSelectOutOfRange(Calendar calendar, boolean z);
    }

    public interface OnCalendarSelectListener {
        void onCalendarOutOfRange(Calendar calendar);

        void onCalendarSelect(Calendar calendar, boolean z);
    }

    interface OnInnerDateSelectedListener {
        void onMonthDateSelected(Calendar calendar, boolean z);

        void onWeekDateSelected(Calendar calendar, boolean z);
    }

    public interface OnMonthChangeListener {
        void onMonthChange(int i, int i2);
    }

    public interface OnViewChangeListener {
        void onViewChange(boolean z);
    }

    public interface OnWeekChangeListener {
        void onWeekChange(List<Calendar> list);
    }

    public interface OnYearChangeListener {
        void onYearChange(int i);
    }

    public interface OnYearViewChangeListener {
        void onYearViewChange(boolean z);
    }

    public CalendarView(Context context) {
        this(context, (AttributeSet) null);
    }

    public CalendarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDelegate = new CalendarViewDelegate(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(C1484R.layout.cv_layout_calendar_view, this, true);
        FrameLayout frameLayout = (FrameLayout) findViewById(C1484R.C1488id.frameContent);
        WeekViewPager weekViewPager = (WeekViewPager) findViewById(C1484R.C1488id.vp_week);
        this.mWeekPager = weekViewPager;
        weekViewPager.setup(this.mDelegate);
        try {
            this.mWeekBar = (WeekBar) this.mDelegate.getWeekBarClass().getConstructor(new Class[]{Context.class}).newInstance(new Object[]{getContext()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        frameLayout.addView(this.mWeekBar, 2);
        this.mWeekBar.setup(this.mDelegate);
        this.mWeekBar.onWeekStartChange(this.mDelegate.getWeekStart());
        View findViewById = findViewById(C1484R.C1488id.line);
        this.mWeekLine = findViewById;
        findViewById.setBackgroundColor(this.mDelegate.getWeekLineBackground());
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mWeekLine.getLayoutParams();
        layoutParams.setMargins(this.mDelegate.getWeekLineMargin(), this.mDelegate.getWeekBarHeight(), this.mDelegate.getWeekLineMargin(), 0);
        this.mWeekLine.setLayoutParams(layoutParams);
        MonthViewPager monthViewPager = (MonthViewPager) findViewById(C1484R.C1488id.vp_month);
        this.mMonthPager = monthViewPager;
        monthViewPager.mWeekPager = this.mWeekPager;
        this.mMonthPager.mWeekBar = this.mWeekBar;
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mMonthPager.getLayoutParams();
        layoutParams2.setMargins(0, this.mDelegate.getWeekBarHeight() + CalendarUtil.dipToPx(context, 1.0f), 0, 0);
        this.mWeekPager.setLayoutParams(layoutParams2);
        YearViewPager yearViewPager = (YearViewPager) findViewById(C1484R.C1488id.selectLayout);
        this.mYearViewPager = yearViewPager;
        yearViewPager.setBackgroundColor(this.mDelegate.getYearViewBackground());
        this.mYearViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                if (CalendarView.this.mWeekPager.getVisibility() != 0 && CalendarView.this.mDelegate.mYearChangeListener != null) {
                    CalendarView.this.mDelegate.mYearChangeListener.onYearChange(i + CalendarView.this.mDelegate.getMinYear());
                }
            }
        });
        this.mDelegate.mInnerListener = new OnInnerDateSelectedListener() {
            public void onMonthDateSelected(Calendar calendar, boolean z) {
                if (calendar.getYear() != CalendarView.this.mDelegate.getCurrentDay().getYear() || calendar.getMonth() != CalendarView.this.mDelegate.getCurrentDay().getMonth() || CalendarView.this.mMonthPager.getCurrentItem() == CalendarView.this.mDelegate.mCurrentMonthViewItem) {
                    CalendarView.this.mDelegate.mIndexCalendar = calendar;
                    if (CalendarView.this.mDelegate.getSelectMode() == 0 || z) {
                        CalendarView.this.mDelegate.mSelectedCalendar = calendar;
                    }
                    CalendarView.this.mWeekPager.updateSelected(CalendarView.this.mDelegate.mIndexCalendar, false);
                    CalendarView.this.mMonthPager.updateSelected();
                    if (CalendarView.this.mWeekBar == null) {
                        return;
                    }
                    if (CalendarView.this.mDelegate.getSelectMode() == 0 || z) {
                        CalendarView.this.mWeekBar.onDateSelected(calendar, CalendarView.this.mDelegate.getWeekStart(), z);
                    }
                }
            }

            public void onWeekDateSelected(Calendar calendar, boolean z) {
                CalendarView.this.mDelegate.mIndexCalendar = calendar;
                if (CalendarView.this.mDelegate.getSelectMode() == 0 || z || CalendarView.this.mDelegate.mIndexCalendar.equals(CalendarView.this.mDelegate.mSelectedCalendar)) {
                    CalendarView.this.mDelegate.mSelectedCalendar = calendar;
                }
                int year = (((calendar.getYear() - CalendarView.this.mDelegate.getMinYear()) * 12) + CalendarView.this.mDelegate.mIndexCalendar.getMonth()) - CalendarView.this.mDelegate.getMinYearMonth();
                CalendarView.this.mWeekPager.updateSingleSelect();
                CalendarView.this.mMonthPager.setCurrentItem(year, false);
                CalendarView.this.mMonthPager.updateSelected();
                if (CalendarView.this.mWeekBar == null) {
                    return;
                }
                if (CalendarView.this.mDelegate.getSelectMode() == 0 || z || CalendarView.this.mDelegate.mIndexCalendar.equals(CalendarView.this.mDelegate.mSelectedCalendar)) {
                    CalendarView.this.mWeekBar.onDateSelected(calendar, CalendarView.this.mDelegate.getWeekStart(), z);
                }
            }
        };
        if (this.mDelegate.getSelectMode() != 0) {
            this.mDelegate.mSelectedCalendar = new Calendar();
        } else if (isInRange(this.mDelegate.getCurrentDay())) {
            CalendarViewDelegate calendarViewDelegate = this.mDelegate;
            calendarViewDelegate.mSelectedCalendar = calendarViewDelegate.createCurrentDate();
        } else {
            CalendarViewDelegate calendarViewDelegate2 = this.mDelegate;
            calendarViewDelegate2.mSelectedCalendar = calendarViewDelegate2.getMinRangeCalendar();
        }
        CalendarViewDelegate calendarViewDelegate3 = this.mDelegate;
        calendarViewDelegate3.mIndexCalendar = calendarViewDelegate3.mSelectedCalendar;
        this.mWeekBar.onDateSelected(this.mDelegate.mSelectedCalendar, this.mDelegate.getWeekStart(), false);
        this.mMonthPager.setup(this.mDelegate);
        this.mMonthPager.setCurrentItem(this.mDelegate.mCurrentMonthViewItem);
        this.mYearViewPager.setOnMonthSelectedListener(new YearRecyclerView.OnMonthSelectedListener() {
            public void onMonthSelected(int i, int i2) {
                CalendarView.this.closeSelectLayout((((i - CalendarView.this.mDelegate.getMinYear()) * 12) + i2) - CalendarView.this.mDelegate.getMinYearMonth());
                CalendarView.this.mDelegate.isShowYearSelectedLayout = false;
            }
        });
        this.mYearViewPager.setup(this.mDelegate);
        this.mWeekPager.updateSelected(this.mDelegate.createCurrentDate(), false);
    }

    public void setRange(int i, int i2, int i3, int i4, int i5, int i6) {
        if (CalendarUtil.compareTo(i, i2, i3, i4, i5, i6) <= 0) {
            this.mDelegate.setRange(i, i2, i3, i4, i5, i6);
            this.mWeekPager.notifyDataSetChanged();
            this.mYearViewPager.notifyDataSetChanged();
            this.mMonthPager.notifyDataSetChanged();
            if (!isInRange(this.mDelegate.mSelectedCalendar)) {
                CalendarViewDelegate calendarViewDelegate = this.mDelegate;
                calendarViewDelegate.mSelectedCalendar = calendarViewDelegate.getMinRangeCalendar();
                this.mDelegate.updateSelectCalendarScheme();
                CalendarViewDelegate calendarViewDelegate2 = this.mDelegate;
                calendarViewDelegate2.mIndexCalendar = calendarViewDelegate2.mSelectedCalendar;
            }
            this.mWeekPager.updateRange();
            this.mMonthPager.updateRange();
            this.mYearViewPager.updateRange();
        }
    }

    public int getCurDay() {
        return this.mDelegate.getCurrentDay().getDay();
    }

    public int getCurMonth() {
        return this.mDelegate.getCurrentDay().getMonth();
    }

    public int getCurYear() {
        return this.mDelegate.getCurrentDay().getYear();
    }

    public void showYearSelectLayout(int i) {
        showSelectLayout(i);
    }

    private void showSelectLayout(final int i) {
        CalendarLayout calendarLayout = this.mParentLayout;
        if (!(calendarLayout == null || calendarLayout.mContentView == null || this.mParentLayout.isExpand())) {
            this.mParentLayout.expand();
        }
        this.mWeekPager.setVisibility(8);
        this.mDelegate.isShowYearSelectedLayout = true;
        CalendarLayout calendarLayout2 = this.mParentLayout;
        if (calendarLayout2 != null) {
            calendarLayout2.hideContentView();
        }
        this.mWeekBar.animate().translationY((float) (-this.mWeekBar.getHeight())).setInterpolator(new LinearInterpolator()).setDuration(260).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                CalendarView.this.mWeekBar.setVisibility(8);
                CalendarView.this.mYearViewPager.setVisibility(0);
                CalendarView.this.mYearViewPager.scrollToYear(i, false);
                if (CalendarView.this.mParentLayout != null && CalendarView.this.mParentLayout.mContentView != null) {
                    CalendarView.this.mParentLayout.expand();
                }
            }
        });
        this.mMonthPager.animate().scaleX(0.0f).scaleY(0.0f).setDuration(260).setInterpolator(new LinearInterpolator()).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (CalendarView.this.mDelegate.mYearViewChangeListener != null) {
                    CalendarView.this.mDelegate.mYearViewChangeListener.onYearViewChange(false);
                }
            }
        });
    }

    public boolean isYearSelectLayoutVisible() {
        return this.mYearViewPager.getVisibility() == 0;
    }

    public void closeYearSelectLayout() {
        if (this.mYearViewPager.getVisibility() != 8) {
            closeSelectLayout((((this.mDelegate.mSelectedCalendar.getYear() - this.mDelegate.getMinYear()) * 12) + this.mDelegate.mSelectedCalendar.getMonth()) - this.mDelegate.getMinYearMonth());
            this.mDelegate.isShowYearSelectedLayout = false;
        }
    }

    /* access modifiers changed from: private */
    public void closeSelectLayout(int i) {
        this.mYearViewPager.setVisibility(8);
        this.mWeekBar.setVisibility(0);
        if (i != this.mMonthPager.getCurrentItem()) {
            this.mMonthPager.setCurrentItem(i, false);
        } else if (!(this.mDelegate.mCalendarSelectListener == null || this.mDelegate.getSelectMode() == 1)) {
            this.mDelegate.mCalendarSelectListener.onCalendarSelect(this.mDelegate.mSelectedCalendar, false);
        }
        this.mWeekBar.animate().translationY(0.0f).setInterpolator(new LinearInterpolator()).setDuration(280).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                CalendarView.this.mWeekBar.setVisibility(0);
            }
        });
        this.mMonthPager.animate().scaleX(1.0f).scaleY(1.0f).setDuration(180).setInterpolator(new LinearInterpolator()).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (CalendarView.this.mDelegate.mYearViewChangeListener != null) {
                    CalendarView.this.mDelegate.mYearViewChangeListener.onYearViewChange(true);
                }
                if (CalendarView.this.mParentLayout != null) {
                    CalendarView.this.mParentLayout.showContentView();
                    if (CalendarView.this.mParentLayout.isExpand()) {
                        CalendarView.this.mMonthPager.setVisibility(0);
                    } else {
                        CalendarView.this.mWeekPager.setVisibility(0);
                        CalendarView.this.mParentLayout.shrink();
                    }
                } else {
                    CalendarView.this.mMonthPager.setVisibility(0);
                }
                CalendarView.this.mMonthPager.clearAnimation();
            }
        });
    }

    public void scrollToCurrent() {
        scrollToCurrent(false);
    }

    public void scrollToCurrent(boolean z) {
        if (isInRange(this.mDelegate.getCurrentDay())) {
            Calendar createCurrentDate = this.mDelegate.createCurrentDate();
            if (this.mDelegate.mCalendarInterceptListener == null || !this.mDelegate.mCalendarInterceptListener.onCalendarIntercept(createCurrentDate)) {
                CalendarViewDelegate calendarViewDelegate = this.mDelegate;
                calendarViewDelegate.mSelectedCalendar = calendarViewDelegate.createCurrentDate();
                CalendarViewDelegate calendarViewDelegate2 = this.mDelegate;
                calendarViewDelegate2.mIndexCalendar = calendarViewDelegate2.mSelectedCalendar;
                this.mDelegate.updateSelectCalendarScheme();
                this.mWeekBar.onDateSelected(this.mDelegate.mSelectedCalendar, this.mDelegate.getWeekStart(), false);
                if (this.mMonthPager.getVisibility() == 0) {
                    this.mMonthPager.scrollToCurrent(z);
                    this.mWeekPager.updateSelected(this.mDelegate.mIndexCalendar, false);
                } else {
                    this.mWeekPager.scrollToCurrent(z);
                }
                this.mYearViewPager.scrollToYear(this.mDelegate.getCurrentDay().getYear(), z);
                return;
            }
            this.mDelegate.mCalendarInterceptListener.onCalendarInterceptClick(createCurrentDate, false);
        }
    }

    public void scrollToNext() {
        scrollToNext(false);
    }

    public void scrollToNext(boolean z) {
        if (isYearSelectLayoutVisible()) {
            YearViewPager yearViewPager = this.mYearViewPager;
            yearViewPager.setCurrentItem(yearViewPager.getCurrentItem() + 1, z);
        } else if (this.mWeekPager.getVisibility() == 0) {
            WeekViewPager weekViewPager = this.mWeekPager;
            weekViewPager.setCurrentItem(weekViewPager.getCurrentItem() + 1, z);
        } else {
            MonthViewPager monthViewPager = this.mMonthPager;
            monthViewPager.setCurrentItem(monthViewPager.getCurrentItem() + 1, z);
        }
    }

    public void scrollToPre() {
        scrollToPre(false);
    }

    public void scrollToPre(boolean z) {
        if (isYearSelectLayoutVisible()) {
            YearViewPager yearViewPager = this.mYearViewPager;
            yearViewPager.setCurrentItem(yearViewPager.getCurrentItem() - 1, z);
        } else if (this.mWeekPager.getVisibility() == 0) {
            WeekViewPager weekViewPager = this.mWeekPager;
            weekViewPager.setCurrentItem(weekViewPager.getCurrentItem() - 1, z);
        } else {
            MonthViewPager monthViewPager = this.mMonthPager;
            monthViewPager.setCurrentItem(monthViewPager.getCurrentItem() - 1, z);
        }
    }

    public void scrollToSelectCalendar() {
        if (this.mDelegate.mSelectedCalendar.isAvailable()) {
            scrollToCalendar(this.mDelegate.mSelectedCalendar.getYear(), this.mDelegate.mSelectedCalendar.getMonth(), this.mDelegate.mSelectedCalendar.getDay(), false, true);
        }
    }

    public void scrollToCalendar(int i, int i2, int i3) {
        scrollToCalendar(i, i2, i3, false, true);
    }

    public void scrollToCalendar(int i, int i2, int i3, boolean z) {
        scrollToCalendar(i, i2, i3, z, true);
    }

    public void scrollToCalendar(int i, int i2, int i3, boolean z, boolean z2) {
        Calendar calendar = new Calendar();
        calendar.setYear(i);
        calendar.setMonth(i2);
        calendar.setDay(i3);
        if (!calendar.isAvailable() || !isInRange(calendar)) {
            return;
        }
        if (this.mDelegate.mCalendarInterceptListener != null && this.mDelegate.mCalendarInterceptListener.onCalendarIntercept(calendar)) {
            this.mDelegate.mCalendarInterceptListener.onCalendarInterceptClick(calendar, false);
        } else if (this.mWeekPager.getVisibility() == 0) {
            this.mWeekPager.scrollToCalendar(i, i2, i3, z, z2);
        } else {
            this.mMonthPager.scrollToCalendar(i, i2, i3, z, z2);
        }
    }

    public void scrollToYear(int i) {
        scrollToYear(i, false);
    }

    public void scrollToYear(int i, boolean z) {
        if (this.mYearViewPager.getVisibility() == 0) {
            this.mYearViewPager.scrollToYear(i, z);
        }
    }

    public final void setMonthViewScrollable(boolean z) {
        this.mDelegate.setMonthViewScrollable(z);
    }

    public final void setWeekViewScrollable(boolean z) {
        this.mDelegate.setWeekViewScrollable(z);
    }

    public final void setYearViewScrollable(boolean z) {
        this.mDelegate.setYearViewScrollable(z);
    }

    public final void setDefaultMonthViewSelectDay() {
        this.mDelegate.setDefaultCalendarSelectDay(0);
    }

    public final void setLastMonthViewSelectDay() {
        this.mDelegate.setDefaultCalendarSelectDay(1);
    }

    public final void setLastMonthViewSelectDayIgnoreCurrent() {
        this.mDelegate.setDefaultCalendarSelectDay(2);
    }

    public final void clearSelectRange() {
        this.mDelegate.clearSelectRange();
        this.mMonthPager.clearSelectRange();
        this.mWeekPager.clearSelectRange();
    }

    public final void clearSingleSelect() {
        this.mDelegate.mSelectedCalendar = new Calendar();
        this.mMonthPager.clearSingleSelect();
        this.mWeekPager.clearSingleSelect();
    }

    public final void clearMultiSelect() {
        this.mDelegate.mSelectedCalendars.clear();
        this.mMonthPager.clearMultiSelect();
        this.mWeekPager.clearMultiSelect();
    }

    public final void putMultiSelect(Calendar... calendarArr) {
        if (calendarArr != null && calendarArr.length != 0) {
            for (Calendar calendar : calendarArr) {
                if (calendar != null && !this.mDelegate.mSelectedCalendars.containsKey(calendar.toString())) {
                    this.mDelegate.mSelectedCalendars.put(calendar.toString(), calendar);
                }
            }
            update();
        }
    }

    public final void removeMultiSelect(Calendar... calendarArr) {
        if (calendarArr != null && calendarArr.length != 0) {
            for (Calendar calendar : calendarArr) {
                if (calendar != null && this.mDelegate.mSelectedCalendars.containsKey(calendar.toString())) {
                    this.mDelegate.mSelectedCalendars.remove(calendar.toString());
                }
            }
            update();
        }
    }

    public final List<Calendar> getMultiSelectCalendars() {
        ArrayList arrayList = new ArrayList();
        if (this.mDelegate.mSelectedCalendars.size() == 0) {
            return arrayList;
        }
        arrayList.addAll(this.mDelegate.mSelectedCalendars.values());
        Collections.sort(arrayList);
        return arrayList;
    }

    public final List<Calendar> getSelectCalendarRange() {
        return this.mDelegate.getSelectCalendarRange();
    }

    public final void setCalendarItemHeight(int i) {
        if (this.mDelegate.getCalendarItemHeight() != i) {
            this.mDelegate.setCalendarItemHeight(i);
            this.mMonthPager.updateItemHeight();
            this.mWeekPager.updateItemHeight();
            CalendarLayout calendarLayout = this.mParentLayout;
            if (calendarLayout != null) {
                calendarLayout.updateCalendarItemHeight();
            }
        }
    }

    public final void setMonthView(Class<?> cls) {
        if (cls != null && !this.mDelegate.getMonthViewClass().equals(cls)) {
            this.mDelegate.setMonthViewClass(cls);
            this.mMonthPager.updateMonthViewClass();
        }
    }

    public final void setWeekView(Class<?> cls) {
        if (cls != null && !this.mDelegate.getWeekBarClass().equals(cls)) {
            this.mDelegate.setWeekViewClass(cls);
            this.mWeekPager.updateWeekViewClass();
        }
    }

    public final void setWeekBar(Class<?> cls) {
        if (cls != null && !this.mDelegate.getWeekBarClass().equals(cls)) {
            this.mDelegate.setWeekBarClass(cls);
            FrameLayout frameLayout = (FrameLayout) findViewById(C1484R.C1488id.frameContent);
            frameLayout.removeView(this.mWeekBar);
            try {
                this.mWeekBar = (WeekBar) cls.getConstructor(new Class[]{Context.class}).newInstance(new Object[]{getContext()});
            } catch (Exception e) {
                e.printStackTrace();
            }
            frameLayout.addView(this.mWeekBar, 2);
            this.mWeekBar.setup(this.mDelegate);
            this.mWeekBar.onWeekStartChange(this.mDelegate.getWeekStart());
            this.mMonthPager.mWeekBar = this.mWeekBar;
            this.mWeekBar.onDateSelected(this.mDelegate.mSelectedCalendar, this.mDelegate.getWeekStart(), false);
        }
    }

    public final void setOnCalendarInterceptListener(OnCalendarInterceptListener onCalendarInterceptListener) {
        if (onCalendarInterceptListener == null) {
            this.mDelegate.mCalendarInterceptListener = null;
        }
        if (onCalendarInterceptListener != null && this.mDelegate.getSelectMode() != 0) {
            this.mDelegate.mCalendarInterceptListener = onCalendarInterceptListener;
            if (onCalendarInterceptListener.onCalendarIntercept(this.mDelegate.mSelectedCalendar)) {
                this.mDelegate.mSelectedCalendar = new Calendar();
            }
        }
    }

    public void setOnYearChangeListener(OnYearChangeListener onYearChangeListener) {
        this.mDelegate.mYearChangeListener = onYearChangeListener;
    }

    public void setOnMonthChangeListener(OnMonthChangeListener onMonthChangeListener) {
        this.mDelegate.mMonthChangeListener = onMonthChangeListener;
    }

    public void setOnWeekChangeListener(OnWeekChangeListener onWeekChangeListener) {
        this.mDelegate.mWeekChangeListener = onWeekChangeListener;
    }

    public void setOnCalendarSelectListener(OnCalendarSelectListener onCalendarSelectListener) {
        this.mDelegate.mCalendarSelectListener = onCalendarSelectListener;
        if (this.mDelegate.mCalendarSelectListener != null && this.mDelegate.getSelectMode() == 0 && isInRange(this.mDelegate.mSelectedCalendar)) {
            this.mDelegate.updateSelectCalendarScheme();
        }
    }

    public final void setOnCalendarRangeSelectListener(OnCalendarRangeSelectListener onCalendarRangeSelectListener) {
        this.mDelegate.mCalendarRangeSelectListener = onCalendarRangeSelectListener;
    }

    public final void setOnCalendarMultiSelectListener(OnCalendarMultiSelectListener onCalendarMultiSelectListener) {
        this.mDelegate.mCalendarMultiSelectListener = onCalendarMultiSelectListener;
    }

    public final void setSelectRange(int i, int i2) {
        if (i <= i2) {
            this.mDelegate.setSelectRange(i, i2);
        }
    }

    public final void setSelectStartCalendar(int i, int i2, int i3) {
        if (this.mDelegate.getSelectMode() == 2) {
            Calendar calendar = new Calendar();
            calendar.setYear(i);
            calendar.setMonth(i2);
            calendar.setDay(i3);
            setSelectStartCalendar(calendar);
        }
    }

    public final void setSelectStartCalendar(Calendar calendar) {
        if (this.mDelegate.getSelectMode() != 2 || calendar == null) {
            return;
        }
        if (!isInRange(calendar)) {
            if (this.mDelegate.mCalendarRangeSelectListener != null) {
                this.mDelegate.mCalendarRangeSelectListener.onSelectOutOfRange(calendar, true);
            }
        } else if (!onCalendarIntercept(calendar)) {
            this.mDelegate.mSelectedEndRangeCalendar = null;
            this.mDelegate.mSelectedStartRangeCalendar = calendar;
            scrollToCalendar(calendar.getYear(), calendar.getMonth(), calendar.getDay());
        } else if (this.mDelegate.mCalendarInterceptListener != null) {
            this.mDelegate.mCalendarInterceptListener.onCalendarInterceptClick(calendar, false);
        }
    }

    public final void setSelectEndCalendar(int i, int i2, int i3) {
        if (this.mDelegate.getSelectMode() == 2 && this.mDelegate.mSelectedStartRangeCalendar != null) {
            Calendar calendar = new Calendar();
            calendar.setYear(i);
            calendar.setMonth(i2);
            calendar.setDay(i3);
            setSelectEndCalendar(calendar);
        }
    }

    public final void setSelectEndCalendar(Calendar calendar) {
        if (this.mDelegate.getSelectMode() == 2 && this.mDelegate.mSelectedStartRangeCalendar != null) {
            setSelectCalendarRange(this.mDelegate.mSelectedStartRangeCalendar, calendar);
        }
    }

    public final void setSelectCalendarRange(int i, int i2, int i3, int i4, int i5, int i6) {
        if (this.mDelegate.getSelectMode() == 2) {
            Calendar calendar = new Calendar();
            calendar.setYear(i);
            calendar.setMonth(i2);
            calendar.setDay(i3);
            Calendar calendar2 = new Calendar();
            calendar2.setYear(i4);
            calendar2.setMonth(i5);
            calendar2.setDay(i6);
            setSelectCalendarRange(calendar, calendar2);
        }
    }

    public final void setSelectCalendarRange(Calendar calendar, Calendar calendar2) {
        if (this.mDelegate.getSelectMode() == 2 && calendar != null && calendar2 != null) {
            if (onCalendarIntercept(calendar)) {
                if (this.mDelegate.mCalendarInterceptListener != null) {
                    this.mDelegate.mCalendarInterceptListener.onCalendarInterceptClick(calendar, false);
                }
            } else if (!onCalendarIntercept(calendar2)) {
                int differ = calendar2.differ(calendar);
                if (differ < 0 || !isInRange(calendar) || !isInRange(calendar2)) {
                    return;
                }
                if (this.mDelegate.getMinSelectRange() == -1 || this.mDelegate.getMinSelectRange() <= differ + 1) {
                    if (this.mDelegate.getMaxSelectRange() == -1 || this.mDelegate.getMaxSelectRange() >= differ + 1) {
                        if (this.mDelegate.getMinSelectRange() == -1 && differ == 0) {
                            this.mDelegate.mSelectedStartRangeCalendar = calendar;
                            this.mDelegate.mSelectedEndRangeCalendar = null;
                            if (this.mDelegate.mCalendarRangeSelectListener != null) {
                                this.mDelegate.mCalendarRangeSelectListener.onCalendarRangeSelect(calendar, false);
                            }
                            scrollToCalendar(calendar.getYear(), calendar.getMonth(), calendar.getDay());
                            return;
                        }
                        this.mDelegate.mSelectedStartRangeCalendar = calendar;
                        this.mDelegate.mSelectedEndRangeCalendar = calendar2;
                        if (this.mDelegate.mCalendarRangeSelectListener != null) {
                            this.mDelegate.mCalendarRangeSelectListener.onCalendarRangeSelect(calendar, false);
                            this.mDelegate.mCalendarRangeSelectListener.onCalendarRangeSelect(calendar2, true);
                        }
                        scrollToCalendar(calendar.getYear(), calendar.getMonth(), calendar.getDay());
                    } else if (this.mDelegate.mCalendarRangeSelectListener != null) {
                        this.mDelegate.mCalendarRangeSelectListener.onSelectOutOfRange(calendar2, false);
                    }
                } else if (this.mDelegate.mCalendarRangeSelectListener != null) {
                    this.mDelegate.mCalendarRangeSelectListener.onSelectOutOfRange(calendar2, true);
                }
            } else if (this.mDelegate.mCalendarInterceptListener != null) {
                this.mDelegate.mCalendarInterceptListener.onCalendarInterceptClick(calendar2, false);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final boolean onCalendarIntercept(Calendar calendar) {
        return this.mDelegate.mCalendarInterceptListener != null && this.mDelegate.mCalendarInterceptListener.onCalendarIntercept(calendar);
    }

    public final int getMaxMultiSelectSize() {
        return this.mDelegate.getMaxMultiSelectSize();
    }

    public final void setMaxMultiSelectSize(int i) {
        this.mDelegate.setMaxMultiSelectSize(i);
    }

    public final int getMinSelectRange() {
        return this.mDelegate.getMinSelectRange();
    }

    public final int getMaxSelectRange() {
        return this.mDelegate.getMaxSelectRange();
    }

    public void setOnCalendarLongClickListener(OnCalendarLongClickListener onCalendarLongClickListener) {
        this.mDelegate.mCalendarLongClickListener = onCalendarLongClickListener;
    }

    public void setOnCalendarLongClickListener(OnCalendarLongClickListener onCalendarLongClickListener, boolean z) {
        this.mDelegate.mCalendarLongClickListener = onCalendarLongClickListener;
        this.mDelegate.setPreventLongPressedSelected(z);
    }

    public void setOnViewChangeListener(OnViewChangeListener onViewChangeListener) {
        this.mDelegate.mViewChangeListener = onViewChangeListener;
    }

    public void setOnYearViewChangeListener(OnYearViewChangeListener onYearViewChangeListener) {
        this.mDelegate.mYearViewChangeListener = onYearViewChangeListener;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        if (this.mDelegate == null) {
            return super.onSaveInstanceState();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("super", super.onSaveInstanceState());
        bundle.putSerializable("selected_calendar", this.mDelegate.mSelectedCalendar);
        bundle.putSerializable("index_calendar", this.mDelegate.mIndexCalendar);
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        Parcelable parcelable2 = bundle.getParcelable("super");
        this.mDelegate.mSelectedCalendar = (Calendar) bundle.getSerializable("selected_calendar");
        this.mDelegate.mIndexCalendar = (Calendar) bundle.getSerializable("index_calendar");
        if (this.mDelegate.mCalendarSelectListener != null) {
            this.mDelegate.mCalendarSelectListener.onCalendarSelect(this.mDelegate.mSelectedCalendar, false);
        }
        if (this.mDelegate.mIndexCalendar != null) {
            scrollToCalendar(this.mDelegate.mIndexCalendar.getYear(), this.mDelegate.mIndexCalendar.getMonth(), this.mDelegate.mIndexCalendar.getDay());
        }
        update();
        super.onRestoreInstanceState(parcelable2);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getParent() != null && (getParent() instanceof CalendarLayout)) {
            CalendarLayout calendarLayout = (CalendarLayout) getParent();
            this.mParentLayout = calendarLayout;
            this.mMonthPager.mParentLayout = calendarLayout;
            this.mWeekPager.mParentLayout = this.mParentLayout;
            this.mParentLayout.mWeekBar = this.mWeekBar;
            this.mParentLayout.setup(this.mDelegate);
            this.mParentLayout.initStatus();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i2);
        CalendarViewDelegate calendarViewDelegate = this.mDelegate;
        if (calendarViewDelegate == null || !calendarViewDelegate.isFullScreenCalendar()) {
            super.onMeasure(i, i2);
            return;
        }
        setCalendarItemHeight((size - this.mDelegate.getWeekBarHeight()) / 6);
        super.onMeasure(i, i2);
    }

    public final void setSchemeDate(Map<String, Calendar> map) {
        this.mDelegate.mSchemeDatesMap = map;
        this.mDelegate.updateSelectCalendarScheme();
        this.mYearViewPager.update();
        this.mMonthPager.updateScheme();
        this.mWeekPager.updateScheme();
    }

    public final void clearSchemeDate() {
        this.mDelegate.mSchemeDatesMap = null;
        this.mDelegate.clearSelectedScheme();
        this.mYearViewPager.update();
        this.mMonthPager.updateScheme();
        this.mWeekPager.updateScheme();
    }

    public final void addSchemeDate(Calendar calendar) {
        if (calendar != null && calendar.isAvailable()) {
            if (this.mDelegate.mSchemeDatesMap == null) {
                this.mDelegate.mSchemeDatesMap = new HashMap();
            }
            this.mDelegate.mSchemeDatesMap.remove(calendar.toString());
            this.mDelegate.mSchemeDatesMap.put(calendar.toString(), calendar);
            this.mDelegate.updateSelectCalendarScheme();
            this.mYearViewPager.update();
            this.mMonthPager.updateScheme();
            this.mWeekPager.updateScheme();
        }
    }

    public final void addSchemeDate(Map<String, Calendar> map) {
        if (this.mDelegate != null && map != null && map.size() != 0) {
            if (this.mDelegate.mSchemeDatesMap == null) {
                this.mDelegate.mSchemeDatesMap = new HashMap();
            }
            this.mDelegate.addSchemes(map);
            this.mDelegate.updateSelectCalendarScheme();
            this.mYearViewPager.update();
            this.mMonthPager.updateScheme();
            this.mWeekPager.updateScheme();
        }
    }

    public final void removeSchemeDate(Calendar calendar) {
        if (calendar != null && this.mDelegate.mSchemeDatesMap != null && this.mDelegate.mSchemeDatesMap.size() != 0) {
            this.mDelegate.mSchemeDatesMap.remove(calendar.toString());
            if (this.mDelegate.mSelectedCalendar.equals(calendar)) {
                this.mDelegate.clearSelectedScheme();
            }
            this.mYearViewPager.update();
            this.mMonthPager.updateScheme();
            this.mWeekPager.updateScheme();
        }
    }

    public void setBackground(int i, int i2, int i3) {
        this.mWeekBar.setBackgroundColor(i2);
        this.mYearViewPager.setBackgroundColor(i);
        this.mWeekLine.setBackgroundColor(i3);
    }

    public void setTextColor(int i, int i2, int i3, int i4, int i5) {
        CalendarViewDelegate calendarViewDelegate = this.mDelegate;
        if (calendarViewDelegate != null && this.mMonthPager != null && this.mWeekPager != null) {
            calendarViewDelegate.setTextColor(i, i2, i3, i4, i5);
            this.mMonthPager.updateStyle();
            this.mWeekPager.updateStyle();
        }
    }

    public void setSelectedColor(int i, int i2, int i3) {
        CalendarViewDelegate calendarViewDelegate = this.mDelegate;
        if (calendarViewDelegate != null && this.mMonthPager != null && this.mWeekPager != null) {
            calendarViewDelegate.setSelectColor(i, i2, i3);
            this.mMonthPager.updateStyle();
            this.mWeekPager.updateStyle();
        }
    }

    public void setThemeColor(int i, int i2) {
        CalendarViewDelegate calendarViewDelegate = this.mDelegate;
        if (calendarViewDelegate != null && this.mMonthPager != null && this.mWeekPager != null) {
            calendarViewDelegate.setThemeColor(i, i2);
            this.mMonthPager.updateStyle();
            this.mWeekPager.updateStyle();
        }
    }

    public void setSchemeColor(int i, int i2, int i3) {
        CalendarViewDelegate calendarViewDelegate = this.mDelegate;
        if (calendarViewDelegate != null && this.mMonthPager != null && this.mWeekPager != null) {
            calendarViewDelegate.setSchemeColor(i, i2, i3);
            this.mMonthPager.updateStyle();
            this.mWeekPager.updateStyle();
        }
    }

    public void setYearViewTextColor(int i, int i2, int i3) {
        CalendarViewDelegate calendarViewDelegate = this.mDelegate;
        if (calendarViewDelegate != null && this.mYearViewPager != null) {
            calendarViewDelegate.setYearViewTextColor(i, i2, i3);
            this.mYearViewPager.updateStyle();
        }
    }

    public void setWeeColor(int i, int i2) {
        WeekBar weekBar = this.mWeekBar;
        if (weekBar != null) {
            weekBar.setBackgroundColor(i);
            this.mWeekBar.setTextColor(i2);
        }
    }

    public final void setSelectDefaultMode() {
        if (this.mDelegate.getSelectMode() != 0) {
            CalendarViewDelegate calendarViewDelegate = this.mDelegate;
            calendarViewDelegate.mSelectedCalendar = calendarViewDelegate.mIndexCalendar;
            this.mDelegate.setSelectMode(0);
            this.mWeekBar.onDateSelected(this.mDelegate.mSelectedCalendar, this.mDelegate.getWeekStart(), false);
            this.mMonthPager.updateDefaultSelect();
            this.mWeekPager.updateDefaultSelect();
        }
    }

    public void setSelectRangeMode() {
        if (this.mDelegate.getSelectMode() != 2) {
            this.mDelegate.setSelectMode(2);
            clearSelectRange();
        }
    }

    public void setSelectMultiMode() {
        if (this.mDelegate.getSelectMode() != 3) {
            this.mDelegate.setSelectMode(3);
            clearMultiSelect();
        }
    }

    public void setSelectSingleMode() {
        if (this.mDelegate.getSelectMode() != 1) {
            this.mDelegate.setSelectMode(1);
            this.mWeekPager.updateSelected();
            this.mMonthPager.updateSelected();
        }
    }

    public void setWeekStarWithSun() {
        setWeekStart(1);
    }

    public void setWeekStarWithMon() {
        setWeekStart(2);
    }

    public void setWeekStarWithSat() {
        setWeekStart(7);
    }

    private void setWeekStart(int i) {
        if ((i == 1 || i == 2 || i == 7) && i != this.mDelegate.getWeekStart()) {
            this.mDelegate.setWeekStart(i);
            this.mWeekBar.onWeekStartChange(i);
            this.mWeekBar.onDateSelected(this.mDelegate.mSelectedCalendar, i, false);
            this.mWeekPager.updateWeekStart();
            this.mMonthPager.updateWeekStart();
            this.mYearViewPager.updateWeekStart();
        }
    }

    public boolean isSingleSelectMode() {
        return this.mDelegate.getSelectMode() == 1;
    }

    public void setAllMode() {
        setShowMode(0);
    }

    public void setOnlyCurrentMode() {
        setShowMode(1);
    }

    public void setFixMode() {
        setShowMode(2);
    }

    private void setShowMode(int i) {
        if ((i == 0 || i == 1 || i == 2) && this.mDelegate.getMonthViewShowMode() != i) {
            this.mDelegate.setMonthViewShowMode(i);
            this.mWeekPager.updateShowMode();
            this.mMonthPager.updateShowMode();
            this.mWeekPager.notifyDataSetChanged();
        }
    }

    public final void update() {
        this.mWeekBar.onWeekStartChange(this.mDelegate.getWeekStart());
        this.mYearViewPager.update();
        this.mMonthPager.updateScheme();
        this.mWeekPager.updateScheme();
    }

    public void updateWeekBar() {
        this.mWeekBar.onWeekStartChange(this.mDelegate.getWeekStart());
    }

    public final void updateCurrentDate() {
        if (this.mDelegate != null && this.mMonthPager != null && this.mWeekPager != null && getCurDay() != Calendar.getInstance().get(5)) {
            this.mDelegate.updateCurrentDay();
            this.mMonthPager.updateCurrentDate();
            this.mWeekPager.updateCurrentDate();
        }
    }

    public List<Calendar> getCurrentWeekCalendars() {
        return this.mWeekPager.getCurrentWeekCalendars();
    }

    public List<Calendar> getCurrentMonthCalendars() {
        return this.mMonthPager.getCurrentMonthCalendars();
    }

    public Calendar getSelectedCalendar() {
        return this.mDelegate.mSelectedCalendar;
    }

    public Calendar getMinRangeCalendar() {
        return this.mDelegate.getMinRangeCalendar();
    }

    public Calendar getMaxRangeCalendar() {
        return this.mDelegate.getMaxRangeCalendar();
    }

    public MonthViewPager getMonthViewPager() {
        return this.mMonthPager;
    }

    public WeekViewPager getWeekViewPager() {
        return this.mWeekPager;
    }

    /* access modifiers changed from: protected */
    public final boolean isInRange(Calendar calendar) {
        CalendarViewDelegate calendarViewDelegate = this.mDelegate;
        return calendarViewDelegate != null && CalendarUtil.isCalendarInRange(calendar, calendarViewDelegate);
    }
}
