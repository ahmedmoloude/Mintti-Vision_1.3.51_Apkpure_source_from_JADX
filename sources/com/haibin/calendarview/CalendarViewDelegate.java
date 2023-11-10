package com.haibin.calendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.core.internal.view.SupportMenu;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class CalendarViewDelegate {
    static final int FIRST_DAY_OF_MONTH = 0;
    static final int LAST_MONTH_VIEW_SELECT_DAY = 1;
    static final int LAST_MONTH_VIEW_SELECT_DAY_IGNORE_CURRENT = 2;
    private static final int MAX_YEAR = 2099;
    static final int MIN_YEAR = 1900;
    static final int MODE_ALL_MONTH = 0;
    static final int MODE_FIT_MONTH = 2;
    static final int MODE_ONLY_CURRENT_MONTH = 1;
    static final int SELECT_MODE_DEFAULT = 0;
    static final int SELECT_MODE_MULTI = 3;
    static final int SELECT_MODE_RANGE = 2;
    static final int SELECT_MODE_SINGLE = 1;
    static final int WEEK_START_WITH_MON = 2;
    static final int WEEK_START_WITH_SAT = 7;
    static final int WEEK_START_WITH_SUN = 1;
    private boolean isFullScreenCalendar;
    boolean isShowYearSelectedLayout;
    CalendarView.OnCalendarInterceptListener mCalendarInterceptListener;
    private int mCalendarItemHeight;
    CalendarView.OnCalendarLongClickListener mCalendarLongClickListener;
    CalendarView.OnCalendarMultiSelectListener mCalendarMultiSelectListener;
    private int mCalendarPadding;
    CalendarView.OnCalendarRangeSelectListener mCalendarRangeSelectListener;
    CalendarView.OnCalendarSelectListener mCalendarSelectListener;
    private int mCurDayLunarTextColor;
    private int mCurDayTextColor;
    private int mCurMonthLunarTextColor;
    private Calendar mCurrentDate;
    private int mCurrentMonthTextColor;
    int mCurrentMonthViewItem;
    private int mDayTextSize;
    private int mDefaultCalendarSelectDay;
    Calendar mIndexCalendar;
    CalendarView.OnInnerDateSelectedListener mInnerListener;
    private int mLunarTextSize;
    private int mMaxMultiSelectSize;
    private int mMaxSelectRange;
    private int mMaxYear;
    private int mMaxYearDay;
    private int mMaxYearMonth;
    private int mMinSelectRange;
    private int mMinYear;
    private int mMinYearDay;
    private int mMinYearMonth;
    CalendarView.OnMonthChangeListener mMonthChangeListener;
    private Class<?> mMonthViewClass;
    private String mMonthViewClassPath;
    private boolean mMonthViewScrollable;
    private int mMonthViewShowMode;
    private int mOtherMonthLunarTextColor;
    private int mOtherMonthTextColor;
    Map<String, Calendar> mSchemeDatesMap;
    private int mSchemeLunarTextColor;
    private String mSchemeText;
    private int mSchemeTextColor;
    private int mSchemeThemeColor;
    private int mSelectMode;
    Calendar mSelectedCalendar;
    Map<String, Calendar> mSelectedCalendars = new HashMap();
    Calendar mSelectedEndRangeCalendar;
    private int mSelectedLunarTextColor;
    Calendar mSelectedStartRangeCalendar;
    private int mSelectedTextColor;
    private int mSelectedThemeColor;
    CalendarView.OnViewChangeListener mViewChangeListener;
    private int mWeekBackground;
    private Class<?> mWeekBarClass;
    private String mWeekBarClassPath;
    private int mWeekBarHeight;
    CalendarView.OnWeekChangeListener mWeekChangeListener;
    private int mWeekLineBackground;
    private int mWeekLineMargin;
    private int mWeekStart;
    private int mWeekTextColor;
    private int mWeekTextSize;
    private Class<?> mWeekViewClass;
    private String mWeekViewClassPath;
    private boolean mWeekViewScrollable;
    CalendarView.OnYearChangeListener mYearChangeListener;
    private int mYearViewBackground;
    CalendarView.OnYearViewChangeListener mYearViewChangeListener;
    private Class<?> mYearViewClass;
    private String mYearViewClassPath;
    private int mYearViewCurDayTextColor;
    private int mYearViewDayTextColor;
    private int mYearViewDayTextSize;
    private int mYearViewMonthHeight;
    private int mYearViewMonthMarginBottom;
    private int mYearViewMonthMarginTop;
    private int mYearViewMonthTextColor;
    private int mYearViewMonthTextSize;
    private int mYearViewPadding;
    private int mYearViewSchemeTextColor;
    private boolean mYearViewScrollable;
    private int mYearViewSelectTextColor;
    private int mYearViewWeekHeight;
    private int mYearViewWeekTextColor;
    private int mYearViewWeekTextSize;
    private boolean preventLongPressedSelected;

    CalendarViewDelegate(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1484R.styleable.CalendarView);
        LunarCalendar.init(context);
        this.mCalendarPadding = (int) obtainStyledAttributes.getDimension(C1484R.styleable.CalendarView_calendar_padding, 0.0f);
        this.mSchemeTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_scheme_text_color, -1);
        this.mSchemeLunarTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_scheme_lunar_text_color, -1973791);
        this.mSchemeThemeColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_scheme_theme_color, 1355796431);
        this.mMonthViewClassPath = obtainStyledAttributes.getString(C1484R.styleable.CalendarView_month_view);
        this.mYearViewClassPath = obtainStyledAttributes.getString(C1484R.styleable.CalendarView_year_view);
        this.mWeekViewClassPath = obtainStyledAttributes.getString(C1484R.styleable.CalendarView_week_view);
        this.mWeekBarClassPath = obtainStyledAttributes.getString(C1484R.styleable.CalendarView_week_bar_view);
        this.mWeekTextSize = obtainStyledAttributes.getDimensionPixelSize(C1484R.styleable.CalendarView_week_text_size, CalendarUtil.dipToPx(context, 12.0f));
        this.mWeekBarHeight = (int) obtainStyledAttributes.getDimension(C1484R.styleable.CalendarView_week_bar_height, (float) CalendarUtil.dipToPx(context, 40.0f));
        this.mWeekLineMargin = (int) obtainStyledAttributes.getDimension(C1484R.styleable.CalendarView_week_line_margin, (float) CalendarUtil.dipToPx(context, 0.0f));
        String string = obtainStyledAttributes.getString(C1484R.styleable.CalendarView_scheme_text);
        this.mSchemeText = string;
        if (TextUtils.isEmpty(string)) {
            this.mSchemeText = "C";
        }
        this.mMonthViewScrollable = obtainStyledAttributes.getBoolean(C1484R.styleable.CalendarView_month_view_scrollable, true);
        this.mWeekViewScrollable = obtainStyledAttributes.getBoolean(C1484R.styleable.CalendarView_week_view_scrollable, true);
        this.mYearViewScrollable = obtainStyledAttributes.getBoolean(C1484R.styleable.CalendarView_year_view_scrollable, true);
        this.mDefaultCalendarSelectDay = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_month_view_auto_select_day, 0);
        this.mMonthViewShowMode = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_month_view_show_mode, 0);
        this.mWeekStart = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_week_start_with, 1);
        this.mSelectMode = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_select_mode, 0);
        this.mMaxMultiSelectSize = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_max_multi_select_size, Integer.MAX_VALUE);
        this.mMinSelectRange = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_min_select_range, -1);
        int i = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_max_select_range, -1);
        this.mMaxSelectRange = i;
        setSelectRange(this.mMinSelectRange, i);
        this.mWeekBackground = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_week_background, -1);
        this.mWeekLineBackground = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_week_line_background, 0);
        this.mYearViewBackground = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_year_view_background, -1);
        this.mWeekTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_week_text_color, -13421773);
        this.mCurDayTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_current_day_text_color, SupportMenu.CATEGORY_MASK);
        this.mCurDayLunarTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_current_day_lunar_text_color, SupportMenu.CATEGORY_MASK);
        this.mSelectedThemeColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_selected_theme_color, 1355796431);
        this.mSelectedTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_selected_text_color, -15658735);
        this.mSelectedLunarTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_selected_lunar_text_color, -15658735);
        this.mCurrentMonthTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_current_month_text_color, -15658735);
        this.mOtherMonthTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_other_month_text_color, -1973791);
        this.mCurMonthLunarTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_current_month_lunar_text_color, -1973791);
        this.mOtherMonthLunarTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_other_month_lunar_text_color, -1973791);
        this.mMinYear = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_min_year, 1971);
        this.mMaxYear = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_max_year, 2055);
        this.mMinYearMonth = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_min_year_month, 1);
        this.mMaxYearMonth = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_max_year_month, 12);
        this.mMinYearDay = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_min_year_day, 1);
        this.mMaxYearDay = obtainStyledAttributes.getInt(C1484R.styleable.CalendarView_max_year_day, -1);
        this.mDayTextSize = obtainStyledAttributes.getDimensionPixelSize(C1484R.styleable.CalendarView_day_text_size, CalendarUtil.dipToPx(context, 16.0f));
        this.mLunarTextSize = obtainStyledAttributes.getDimensionPixelSize(C1484R.styleable.CalendarView_lunar_text_size, CalendarUtil.dipToPx(context, 10.0f));
        this.mCalendarItemHeight = (int) obtainStyledAttributes.getDimension(C1484R.styleable.CalendarView_calendar_height, (float) CalendarUtil.dipToPx(context, 56.0f));
        this.isFullScreenCalendar = obtainStyledAttributes.getBoolean(C1484R.styleable.CalendarView_calendar_match_parent, false);
        this.mYearViewMonthTextSize = obtainStyledAttributes.getDimensionPixelSize(C1484R.styleable.CalendarView_year_view_month_text_size, CalendarUtil.dipToPx(context, 18.0f));
        this.mYearViewDayTextSize = obtainStyledAttributes.getDimensionPixelSize(C1484R.styleable.CalendarView_year_view_day_text_size, CalendarUtil.dipToPx(context, 7.0f));
        this.mYearViewMonthTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_year_view_month_text_color, -15658735);
        this.mYearViewDayTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_year_view_day_text_color, -15658735);
        this.mYearViewSchemeTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_year_view_scheme_color, this.mSchemeThemeColor);
        this.mYearViewWeekTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_year_view_week_text_color, -13421773);
        this.mYearViewCurDayTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_year_view_current_day_text_color, this.mCurDayTextColor);
        this.mYearViewSelectTextColor = obtainStyledAttributes.getColor(C1484R.styleable.CalendarView_year_view_select_text_color, -13421773);
        this.mYearViewWeekTextSize = obtainStyledAttributes.getDimensionPixelSize(C1484R.styleable.CalendarView_year_view_week_text_size, CalendarUtil.dipToPx(context, 8.0f));
        this.mYearViewMonthHeight = obtainStyledAttributes.getDimensionPixelSize(C1484R.styleable.CalendarView_year_view_month_height, CalendarUtil.dipToPx(context, 32.0f));
        this.mYearViewWeekHeight = obtainStyledAttributes.getDimensionPixelSize(C1484R.styleable.CalendarView_year_view_week_height, CalendarUtil.dipToPx(context, 0.0f));
        this.mYearViewPadding = (int) obtainStyledAttributes.getDimension(C1484R.styleable.CalendarView_year_view_padding, (float) CalendarUtil.dipToPx(context, 6.0f));
        this.mYearViewMonthMarginTop = (int) obtainStyledAttributes.getDimension(C1484R.styleable.CalendarView_year_view_month_margin_top, (float) CalendarUtil.dipToPx(context, 4.0f));
        this.mYearViewMonthMarginBottom = (int) obtainStyledAttributes.getDimension(C1484R.styleable.CalendarView_year_view_month_margin_bottom, (float) CalendarUtil.dipToPx(context, 4.0f));
        if (this.mMinYear <= 1900) {
            this.mMinYear = 1900;
        }
        if (this.mMaxYear >= 2099) {
            this.mMaxYear = 2099;
        }
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        Class cls;
        Class cls2;
        Class cls3;
        Class cls4;
        this.mCurrentDate = new Calendar();
        Date date = new Date();
        this.mCurrentDate.setYear(CalendarUtil.getDate("yyyy", date));
        this.mCurrentDate.setMonth(CalendarUtil.getDate("MM", date));
        this.mCurrentDate.setDay(CalendarUtil.getDate("dd", date));
        this.mCurrentDate.setCurrentDay(true);
        LunarCalendar.setupLunarCalendar(this.mCurrentDate);
        setRange(this.mMinYear, this.mMinYearMonth, this.mMaxYear, this.mMaxYearMonth);
        try {
            if (TextUtils.isEmpty(this.mWeekBarClassPath)) {
                cls4 = WeekBar.class;
                this.mWeekBarClass = cls4;
            } else {
                cls4 = Class.forName(this.mWeekBarClassPath);
            }
            this.mWeekBarClass = cls4;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (TextUtils.isEmpty(this.mYearViewClassPath)) {
                cls3 = DefaultYearView.class;
                this.mYearViewClass = cls3;
            } else {
                cls3 = Class.forName(this.mYearViewClassPath);
            }
            this.mYearViewClass = cls3;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (TextUtils.isEmpty(this.mMonthViewClassPath)) {
                cls2 = DefaultMonthView.class;
            } else {
                cls2 = Class.forName(this.mMonthViewClassPath);
            }
            this.mMonthViewClass = cls2;
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            if (TextUtils.isEmpty(this.mWeekViewClassPath)) {
                cls = DefaultWeekView.class;
            } else {
                cls = Class.forName(this.mWeekViewClassPath);
            }
            this.mWeekViewClass = cls;
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    private void setRange(int i, int i2, int i3, int i4) {
        this.mMinYear = i;
        this.mMinYearMonth = i2;
        this.mMaxYear = i3;
        this.mMaxYearMonth = i4;
        if (i3 < this.mCurrentDate.getYear()) {
            this.mMaxYear = this.mCurrentDate.getYear();
        }
        if (this.mMaxYearDay == -1) {
            this.mMaxYearDay = CalendarUtil.getMonthDaysCount(this.mMaxYear, this.mMaxYearMonth);
        }
        this.mCurrentMonthViewItem = (((this.mCurrentDate.getYear() - this.mMinYear) * 12) + this.mCurrentDate.getMonth()) - this.mMinYearMonth;
    }

    /* access modifiers changed from: package-private */
    public void setRange(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mMinYear = i;
        this.mMinYearMonth = i2;
        this.mMinYearDay = i3;
        this.mMaxYear = i4;
        this.mMaxYearMonth = i5;
        this.mMaxYearDay = i6;
        if (i6 == -1) {
            this.mMaxYearDay = CalendarUtil.getMonthDaysCount(i4, i5);
        }
        this.mCurrentMonthViewItem = (((this.mCurrentDate.getYear() - this.mMinYear) * 12) + this.mCurrentDate.getMonth()) - this.mMinYearMonth;
    }

    /* access modifiers changed from: package-private */
    public String getSchemeText() {
        return this.mSchemeText;
    }

    /* access modifiers changed from: package-private */
    public int getCurDayTextColor() {
        return this.mCurDayTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getCurDayLunarTextColor() {
        return this.mCurDayLunarTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getWeekTextColor() {
        return this.mWeekTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getSchemeTextColor() {
        return this.mSchemeTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getSchemeLunarTextColor() {
        return this.mSchemeLunarTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getOtherMonthTextColor() {
        return this.mOtherMonthTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getCurrentMonthTextColor() {
        return this.mCurrentMonthTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getSelectedTextColor() {
        return this.mSelectedTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getSelectedLunarTextColor() {
        return this.mSelectedLunarTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getCurrentMonthLunarTextColor() {
        return this.mCurMonthLunarTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getOtherMonthLunarTextColor() {
        return this.mOtherMonthLunarTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getSchemeThemeColor() {
        return this.mSchemeThemeColor;
    }

    /* access modifiers changed from: package-private */
    public int getSelectedThemeColor() {
        return this.mSelectedThemeColor;
    }

    /* access modifiers changed from: package-private */
    public int getWeekBackground() {
        return this.mWeekBackground;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewBackground() {
        return this.mYearViewBackground;
    }

    /* access modifiers changed from: package-private */
    public int getWeekLineBackground() {
        return this.mWeekLineBackground;
    }

    /* access modifiers changed from: package-private */
    public int getWeekLineMargin() {
        return this.mWeekLineMargin;
    }

    /* access modifiers changed from: package-private */
    public Class<?> getMonthViewClass() {
        return this.mMonthViewClass;
    }

    /* access modifiers changed from: package-private */
    public Class<?> getWeekViewClass() {
        return this.mWeekViewClass;
    }

    /* access modifiers changed from: package-private */
    public Class<?> getWeekBarClass() {
        return this.mWeekBarClass;
    }

    /* access modifiers changed from: package-private */
    public Class<?> getYearViewClass() {
        return this.mYearViewClass;
    }

    /* access modifiers changed from: package-private */
    public String getYearViewClassPath() {
        return this.mYearViewClassPath;
    }

    /* access modifiers changed from: package-private */
    public int getWeekBarHeight() {
        return this.mWeekBarHeight;
    }

    /* access modifiers changed from: package-private */
    public int getMinYear() {
        return this.mMinYear;
    }

    /* access modifiers changed from: package-private */
    public int getMaxYear() {
        return this.mMaxYear;
    }

    /* access modifiers changed from: package-private */
    public int getDayTextSize() {
        return this.mDayTextSize;
    }

    /* access modifiers changed from: package-private */
    public int getLunarTextSize() {
        return this.mLunarTextSize;
    }

    /* access modifiers changed from: package-private */
    public int getCalendarItemHeight() {
        return this.mCalendarItemHeight;
    }

    /* access modifiers changed from: package-private */
    public void setCalendarItemHeight(int i) {
        this.mCalendarItemHeight = i;
    }

    /* access modifiers changed from: package-private */
    public int getMinYearMonth() {
        return this.mMinYearMonth;
    }

    /* access modifiers changed from: package-private */
    public int getMaxYearMonth() {
        return this.mMaxYearMonth;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewMonthTextSize() {
        return this.mYearViewMonthTextSize;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewMonthTextColor() {
        return this.mYearViewMonthTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewWeekTextSize() {
        return this.mYearViewWeekTextSize;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewWeekTextColor() {
        return this.mYearViewWeekTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewSelectTextColor() {
        return this.mYearViewSelectTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewCurDayTextColor() {
        return this.mYearViewCurDayTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewPadding() {
        return this.mYearViewPadding;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewMonthMarginTop() {
        return this.mYearViewMonthMarginTop;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewMonthMarginBottom() {
        return this.mYearViewMonthMarginBottom;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewWeekHeight() {
        return this.mYearViewWeekHeight;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewMonthHeight() {
        return this.mYearViewMonthHeight;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewDayTextColor() {
        return this.mYearViewDayTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewDayTextSize() {
        return this.mYearViewDayTextSize;
    }

    /* access modifiers changed from: package-private */
    public int getYearViewSchemeTextColor() {
        return this.mYearViewSchemeTextColor;
    }

    /* access modifiers changed from: package-private */
    public int getMonthViewShowMode() {
        return this.mMonthViewShowMode;
    }

    /* access modifiers changed from: package-private */
    public void setMonthViewShowMode(int i) {
        this.mMonthViewShowMode = i;
    }

    /* access modifiers changed from: package-private */
    public void setTextColor(int i, int i2, int i3, int i4, int i5) {
        this.mCurDayTextColor = i;
        this.mOtherMonthTextColor = i3;
        this.mCurrentMonthTextColor = i2;
        this.mCurMonthLunarTextColor = i4;
        this.mOtherMonthLunarTextColor = i5;
    }

    /* access modifiers changed from: package-private */
    public void setSchemeColor(int i, int i2, int i3) {
        this.mSchemeThemeColor = i;
        this.mSchemeTextColor = i2;
        this.mSchemeLunarTextColor = i3;
    }

    /* access modifiers changed from: package-private */
    public void setYearViewTextColor(int i, int i2, int i3) {
        this.mYearViewMonthTextColor = i;
        this.mYearViewDayTextColor = i2;
        this.mYearViewSchemeTextColor = i3;
    }

    /* access modifiers changed from: package-private */
    public void setSelectColor(int i, int i2, int i3) {
        this.mSelectedThemeColor = i;
        this.mSelectedTextColor = i2;
        this.mSelectedLunarTextColor = i3;
    }

    /* access modifiers changed from: package-private */
    public void setThemeColor(int i, int i2) {
        this.mSelectedThemeColor = i;
        this.mSchemeThemeColor = i2;
    }

    /* access modifiers changed from: package-private */
    public boolean isMonthViewScrollable() {
        return this.mMonthViewScrollable;
    }

    /* access modifiers changed from: package-private */
    public boolean isWeekViewScrollable() {
        return this.mWeekViewScrollable;
    }

    /* access modifiers changed from: package-private */
    public boolean isYearViewScrollable() {
        return this.mYearViewScrollable;
    }

    /* access modifiers changed from: package-private */
    public void setMonthViewScrollable(boolean z) {
        this.mMonthViewScrollable = z;
    }

    /* access modifiers changed from: package-private */
    public void setWeekViewScrollable(boolean z) {
        this.mWeekViewScrollable = z;
    }

    /* access modifiers changed from: package-private */
    public void setYearViewScrollable(boolean z) {
        this.mYearViewScrollable = z;
    }

    /* access modifiers changed from: package-private */
    public int getWeekStart() {
        return this.mWeekStart;
    }

    /* access modifiers changed from: package-private */
    public void setWeekStart(int i) {
        this.mWeekStart = i;
    }

    /* access modifiers changed from: package-private */
    public void setDefaultCalendarSelectDay(int i) {
        this.mDefaultCalendarSelectDay = i;
    }

    /* access modifiers changed from: package-private */
    public int getDefaultCalendarSelectDay() {
        return this.mDefaultCalendarSelectDay;
    }

    /* access modifiers changed from: package-private */
    public int getWeekTextSize() {
        return this.mWeekTextSize;
    }

    /* access modifiers changed from: package-private */
    public int getSelectMode() {
        return this.mSelectMode;
    }

    /* access modifiers changed from: package-private */
    public void setSelectMode(int i) {
        this.mSelectMode = i;
    }

    /* access modifiers changed from: package-private */
    public int getMinSelectRange() {
        return this.mMinSelectRange;
    }

    /* access modifiers changed from: package-private */
    public int getMaxSelectRange() {
        return this.mMaxSelectRange;
    }

    /* access modifiers changed from: package-private */
    public int getMaxMultiSelectSize() {
        return this.mMaxMultiSelectSize;
    }

    /* access modifiers changed from: package-private */
    public void setMaxMultiSelectSize(int i) {
        this.mMaxMultiSelectSize = i;
    }

    /* access modifiers changed from: package-private */
    public final void setSelectRange(int i, int i2) {
        if (i <= i2 || i2 <= 0) {
            if (i <= 0) {
                this.mMinSelectRange = -1;
            } else {
                this.mMinSelectRange = i;
            }
            if (i2 <= 0) {
                this.mMaxSelectRange = -1;
            } else {
                this.mMaxSelectRange = i2;
            }
        } else {
            this.mMaxSelectRange = i;
            this.mMinSelectRange = i;
        }
    }

    /* access modifiers changed from: package-private */
    public Calendar getCurrentDay() {
        return this.mCurrentDate;
    }

    /* access modifiers changed from: package-private */
    public void updateCurrentDay() {
        Date date = new Date();
        this.mCurrentDate.setYear(CalendarUtil.getDate("yyyy", date));
        this.mCurrentDate.setMonth(CalendarUtil.getDate("MM", date));
        this.mCurrentDate.setDay(CalendarUtil.getDate("dd", date));
        LunarCalendar.setupLunarCalendar(this.mCurrentDate);
    }

    /* access modifiers changed from: package-private */
    public int getCalendarPadding() {
        return this.mCalendarPadding;
    }

    /* access modifiers changed from: package-private */
    public void setPreventLongPressedSelected(boolean z) {
        this.preventLongPressedSelected = z;
    }

    /* access modifiers changed from: package-private */
    public void setMonthViewClass(Class<?> cls) {
        this.mMonthViewClass = cls;
    }

    /* access modifiers changed from: package-private */
    public void setWeekBarClass(Class<?> cls) {
        this.mWeekBarClass = cls;
    }

    /* access modifiers changed from: package-private */
    public void setWeekViewClass(Class<?> cls) {
        this.mWeekViewClass = cls;
    }

    /* access modifiers changed from: package-private */
    public boolean isPreventLongPressedSelected() {
        return this.preventLongPressedSelected;
    }

    /* access modifiers changed from: package-private */
    public void clearSelectedScheme() {
        this.mSelectedCalendar.clearScheme();
    }

    /* access modifiers changed from: package-private */
    public int getMinYearDay() {
        return this.mMinYearDay;
    }

    /* access modifiers changed from: package-private */
    public int getMaxYearDay() {
        return this.mMaxYearDay;
    }

    /* access modifiers changed from: package-private */
    public boolean isFullScreenCalendar() {
        return this.isFullScreenCalendar;
    }

    /* access modifiers changed from: package-private */
    public final void updateSelectCalendarScheme() {
        Map<String, Calendar> map = this.mSchemeDatesMap;
        if (map == null || map.size() <= 0) {
            clearSelectedScheme();
            return;
        }
        String calendar = this.mSelectedCalendar.toString();
        if (this.mSchemeDatesMap.containsKey(calendar)) {
            this.mSelectedCalendar.mergeScheme(this.mSchemeDatesMap.get(calendar), getSchemeText());
        }
    }

    /* access modifiers changed from: package-private */
    public final void updateCalendarScheme(Calendar calendar) {
        Map<String, Calendar> map;
        if (calendar != null && (map = this.mSchemeDatesMap) != null && map.size() != 0) {
            String calendar2 = calendar.toString();
            if (this.mSchemeDatesMap.containsKey(calendar2)) {
                calendar.mergeScheme(this.mSchemeDatesMap.get(calendar2), getSchemeText());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Calendar createCurrentDate() {
        Calendar calendar = new Calendar();
        calendar.setYear(this.mCurrentDate.getYear());
        calendar.setWeek(this.mCurrentDate.getWeek());
        calendar.setMonth(this.mCurrentDate.getMonth());
        calendar.setDay(this.mCurrentDate.getDay());
        calendar.setCurrentDay(true);
        LunarCalendar.setupLunarCalendar(calendar);
        return calendar;
    }

    /* access modifiers changed from: package-private */
    public final Calendar getMinRangeCalendar() {
        Calendar calendar = new Calendar();
        calendar.setYear(this.mMinYear);
        calendar.setMonth(this.mMinYearMonth);
        calendar.setDay(this.mMinYearDay);
        calendar.setCurrentDay(calendar.equals(this.mCurrentDate));
        LunarCalendar.setupLunarCalendar(calendar);
        return calendar;
    }

    /* access modifiers changed from: package-private */
    public final Calendar getMaxRangeCalendar() {
        Calendar calendar = new Calendar();
        calendar.setYear(this.mMaxYear);
        calendar.setMonth(this.mMaxYearMonth);
        calendar.setDay(this.mMaxYearDay);
        calendar.setCurrentDay(calendar.equals(this.mCurrentDate));
        LunarCalendar.setupLunarCalendar(calendar);
        return calendar;
    }

    /* access modifiers changed from: package-private */
    public final void addSchemesFromMap(List<Calendar> list) {
        Map<String, Calendar> map = this.mSchemeDatesMap;
        if (map != null && map.size() != 0) {
            for (Calendar next : list) {
                if (this.mSchemeDatesMap.containsKey(next.toString())) {
                    Calendar calendar = this.mSchemeDatesMap.get(next.toString());
                    if (calendar != null) {
                        next.setScheme(TextUtils.isEmpty(calendar.getScheme()) ? getSchemeText() : calendar.getScheme());
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

    /* access modifiers changed from: package-private */
    public final void addSchemes(Map<String, Calendar> map) {
        if (map != null && map.size() != 0) {
            if (this.mSchemeDatesMap == null) {
                this.mSchemeDatesMap = new HashMap();
            }
            for (String next : map.keySet()) {
                this.mSchemeDatesMap.remove(next);
                Calendar calendar = map.get(next);
                if (calendar != null) {
                    this.mSchemeDatesMap.put(next, calendar);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void clearSelectRange() {
        this.mSelectedStartRangeCalendar = null;
        this.mSelectedEndRangeCalendar = null;
    }

    /* access modifiers changed from: package-private */
    public final List<Calendar> getSelectCalendarRange() {
        if (this.mSelectMode != 2) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (!(this.mSelectedStartRangeCalendar == null || this.mSelectedEndRangeCalendar == null)) {
            java.util.Calendar instance = java.util.Calendar.getInstance();
            instance.set(this.mSelectedStartRangeCalendar.getYear(), this.mSelectedStartRangeCalendar.getMonth() - 1, this.mSelectedStartRangeCalendar.getDay());
            instance.set(this.mSelectedEndRangeCalendar.getYear(), this.mSelectedEndRangeCalendar.getMonth() - 1, this.mSelectedEndRangeCalendar.getDay());
            long timeInMillis = instance.getTimeInMillis();
            for (long timeInMillis2 = instance.getTimeInMillis(); timeInMillis2 <= timeInMillis; timeInMillis2 += 86400000) {
                instance.setTimeInMillis(timeInMillis2);
                Calendar calendar = new Calendar();
                calendar.setYear(instance.get(1));
                calendar.setMonth(instance.get(2) + 1);
                calendar.setDay(instance.get(5));
                LunarCalendar.setupLunarCalendar(calendar);
                updateCalendarScheme(calendar);
                CalendarView.OnCalendarInterceptListener onCalendarInterceptListener = this.mCalendarInterceptListener;
                if (onCalendarInterceptListener == null || !onCalendarInterceptListener.onCalendarIntercept(calendar)) {
                    arrayList.add(calendar);
                }
            }
            addSchemesFromMap(arrayList);
        }
        return arrayList;
    }
}
