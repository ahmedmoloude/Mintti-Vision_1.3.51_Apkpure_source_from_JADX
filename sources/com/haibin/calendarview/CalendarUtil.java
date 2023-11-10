package com.haibin.calendarview;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

final class CalendarUtil {
    private static final long ONE_DAY = 86400000;

    static boolean isMonthInRange(int i, int i2, int i3, int i4, int i5, int i6) {
        return i >= i3 && i <= i5 && (i != i3 || i2 >= i4) && (i != i5 || i2 <= i6);
    }

    CalendarUtil() {
    }

    static int getDate(String str, Date date) {
        return Integer.parseInt(new SimpleDateFormat(str).format(date));
    }

    static boolean isWeekend(Calendar calendar) {
        int weekFormCalendar = getWeekFormCalendar(calendar);
        return weekFormCalendar == 0 || weekFormCalendar == 6;
    }

    static int getMonthDaysCount(int i, int i2) {
        int i3 = (i2 == 1 || i2 == 3 || i2 == 5 || i2 == 7 || i2 == 8 || i2 == 10 || i2 == 12) ? 31 : 0;
        if (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) {
            i3 = 30;
        }
        if (i2 == 2) {
            return isLeapYear(i) ? 29 : 28;
        }
        return i3;
    }

    static boolean isLeapYear(int i) {
        return (i % 4 == 0 && i % 100 != 0) || i % 400 == 0;
    }

    static int getMonthViewLineCount(int i, int i2, int i3, int i4) {
        if (i4 == 0) {
            return 6;
        }
        return ((getMonthViewStartDiff(i, i2, i3) + getMonthDaysCount(i, i2)) + getMonthEndDiff(i, i2, i3)) / 7;
    }

    static int getMonthViewHeight(int i, int i2, int i3, int i4) {
        Calendar.getInstance().set(i, i2 - 1, 1, 12, 0, 0);
        int monthViewStartDiff = getMonthViewStartDiff(i, i2, i4);
        int monthDaysCount = getMonthDaysCount(i, i2);
        return (((monthViewStartDiff + monthDaysCount) + getMonthEndDiff(i, i2, monthDaysCount, i4)) / 7) * i3;
    }

    static int getMonthViewHeight(int i, int i2, int i3, int i4, int i5) {
        return i5 == 0 ? i3 * 6 : getMonthViewHeight(i, i2, i3, i4);
    }

    static int getWeekFromDayInMonth(Calendar calendar, int i) {
        Calendar.getInstance().set(calendar.getYear(), calendar.getMonth() - 1, 1, 12, 0, 0);
        return (((calendar.getDay() + getMonthViewStartDiff(calendar, i)) - 1) / 7) + 1;
    }

    static Calendar getPreCalendar(Calendar calendar) {
        Calendar instance = Calendar.getInstance();
        instance.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay(), 12, 0, 0);
        instance.setTimeInMillis(instance.getTimeInMillis() - ONE_DAY);
        Calendar calendar2 = new Calendar();
        calendar2.setYear(instance.get(1));
        calendar2.setMonth(instance.get(2) + 1);
        calendar2.setDay(instance.get(5));
        return calendar2;
    }

    static Calendar getNextCalendar(Calendar calendar) {
        Calendar instance = Calendar.getInstance();
        instance.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay(), 12, 0, 0);
        instance.setTimeInMillis(instance.getTimeInMillis() + ONE_DAY);
        Calendar calendar2 = new Calendar();
        calendar2.setYear(instance.get(1));
        calendar2.setMonth(instance.get(2) + 1);
        calendar2.setDay(instance.get(5));
        return calendar2;
    }

    static int getMonthViewStartDiff(Calendar calendar, int i) {
        Calendar instance = Calendar.getInstance();
        instance.set(calendar.getYear(), calendar.getMonth() - 1, 1, 12, 0, 0);
        int i2 = instance.get(7);
        if (i == 1) {
            return i2 - 1;
        }
        if (i == 2) {
            if (i2 == 1) {
                return 6;
            }
            return i2 - i;
        } else if (i2 == 7) {
            return 0;
        } else {
            return i2;
        }
    }

    static int getMonthViewStartDiff(int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(i, i2 - 1, 1, 12, 0, 0);
        int i4 = instance.get(7);
        if (i3 == 1) {
            return i4 - 1;
        }
        if (i3 == 2) {
            if (i4 == 1) {
                return 6;
            }
            return i4 - i3;
        } else if (i4 == 7) {
            return 0;
        } else {
            return i4;
        }
    }

    static int getMonthEndDiff(int i, int i2, int i3) {
        return getMonthEndDiff(i, i2, getMonthDaysCount(i, i2), i3);
    }

    private static int getMonthEndDiff(int i, int i2, int i3, int i4) {
        Calendar instance = Calendar.getInstance();
        instance.set(i, i2 - 1, i3);
        int i5 = instance.get(7);
        if (i4 == 1) {
            return 7 - i5;
        }
        if (i4 == 2) {
            if (i5 == 1) {
                return 0;
            }
            return (7 - i5) + 1;
        } else if (i5 == 7) {
            return 6;
        } else {
            return (7 - i5) - 1;
        }
    }

    static int getWeekFormCalendar(Calendar calendar) {
        Calendar instance = Calendar.getInstance();
        instance.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay());
        return instance.get(7) - 1;
    }

    static int getWeekViewIndexFromCalendar(Calendar calendar, int i) {
        return getWeekViewStartDiff(calendar.getYear(), calendar.getMonth(), calendar.getDay(), i);
    }

    static boolean isCalendarInRange(Calendar calendar, int i, int i2, int i3, int i4, int i5, int i6) {
        Calendar instance = Calendar.getInstance();
        instance.set(i, i2 - 1, i3);
        long timeInMillis = instance.getTimeInMillis();
        instance.set(i4, i5 - 1, i6);
        long timeInMillis2 = instance.getTimeInMillis();
        instance.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay());
        long timeInMillis3 = instance.getTimeInMillis();
        if (timeInMillis3 < timeInMillis || timeInMillis3 > timeInMillis2) {
            return false;
        }
        return true;
    }

    static int getWeekCountBetweenBothCalendar(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        Calendar instance = Calendar.getInstance();
        instance.set(i, i2 - 1, i3);
        long timeInMillis = instance.getTimeInMillis();
        int weekViewStartDiff = getWeekViewStartDiff(i, i2, i3, i7);
        instance.set(i4, i5 - 1, i6);
        return ((weekViewStartDiff + getWeekViewEndDiff(i4, i5, i6, i7)) + (((int) ((instance.getTimeInMillis() - timeInMillis) / ONE_DAY)) + 1)) / 7;
    }

    static int getWeekFromCalendarStartWithMinCalendar(Calendar calendar, int i, int i2, int i3, int i4) {
        Calendar instance = Calendar.getInstance();
        instance.set(i, i2 - 1, i3);
        long timeInMillis = instance.getTimeInMillis();
        int weekViewStartDiff = getWeekViewStartDiff(i, i2, i3, i4);
        instance.set(calendar.getYear(), calendar.getMonth() - 1, getWeekViewStartDiff(calendar.getYear(), calendar.getMonth(), calendar.getDay(), i4) == 0 ? calendar.getDay() + 1 : calendar.getDay());
        return ((weekViewStartDiff + ((int) ((instance.getTimeInMillis() - timeInMillis) / ONE_DAY))) / 7) + 1;
    }

    static Calendar getFirstCalendarStartWithMinCalendar(int i, int i2, int i3, int i4, int i5) {
        Calendar instance = Calendar.getInstance();
        instance.set(i, i2 - 1, i3, 12, 0);
        long timeInMillis = (((long) ((i4 - 1) * 7)) * ONE_DAY) + instance.getTimeInMillis();
        instance.setTimeInMillis(timeInMillis);
        instance.setTimeInMillis(timeInMillis - (((long) getWeekViewStartDiff(instance.get(1), instance.get(2) + 1, instance.get(5), i5)) * ONE_DAY));
        Calendar calendar = new Calendar();
        calendar.setYear(instance.get(1));
        calendar.setMonth(instance.get(2) + 1);
        calendar.setDay(instance.get(5));
        return calendar;
    }

    static boolean isCalendarInRange(Calendar calendar, CalendarViewDelegate calendarViewDelegate) {
        return isCalendarInRange(calendar, calendarViewDelegate.getMinYear(), calendarViewDelegate.getMinYearMonth(), calendarViewDelegate.getMinYearDay(), calendarViewDelegate.getMaxYear(), calendarViewDelegate.getMaxYearMonth(), calendarViewDelegate.getMaxYearDay());
    }

    static int differ(Calendar calendar, Calendar calendar2) {
        if (calendar == null) {
            return Integer.MIN_VALUE;
        }
        if (calendar2 == null) {
            return Integer.MAX_VALUE;
        }
        Calendar instance = Calendar.getInstance();
        Calendar calendar3 = instance;
        calendar3.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay(), 12, 0, 0);
        long timeInMillis = instance.getTimeInMillis();
        calendar3.set(calendar2.getYear(), calendar2.getMonth() - 1, calendar2.getDay(), 12, 0, 0);
        return (int) ((timeInMillis - instance.getTimeInMillis()) / ONE_DAY);
    }

    static int compareTo(int i, int i2, int i3, int i4, int i5, int i6) {
        Calendar calendar = new Calendar();
        calendar.setYear(i);
        calendar.setMonth(i2);
        calendar.setDay(i3);
        Calendar calendar2 = new Calendar();
        calendar2.setYear(i4);
        calendar2.setMonth(i5);
        calendar2.setDay(i6);
        return calendar.compareTo(calendar2);
    }

    static List<Calendar> initCalendarForMonthView(int i, int i2, Calendar calendar, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11 = i2 - 1;
        Calendar.getInstance().set(i, i11, 1);
        int monthViewStartDiff = getMonthViewStartDiff(i, i2, i3);
        int monthDaysCount = getMonthDaysCount(i, i2);
        ArrayList arrayList = new ArrayList();
        int i12 = 12;
        if (i2 == 1) {
            i7 = i - 1;
            int i13 = i2 + 1;
            if (monthViewStartDiff == 0) {
                i10 = 0;
            } else {
                i10 = getMonthDaysCount(i7, 12);
            }
            i4 = i10;
            i5 = i13;
            i6 = i;
        } else if (i2 == 12) {
            int i14 = i + 1;
            if (monthViewStartDiff == 0) {
                i9 = 0;
            } else {
                i9 = getMonthDaysCount(i, i11);
            }
            i6 = i14;
            i4 = i9;
            i5 = 1;
            i12 = i11;
            i7 = i;
        } else {
            int i15 = i2 + 1;
            if (monthViewStartDiff == 0) {
                i8 = 0;
            } else {
                i8 = getMonthDaysCount(i, i11);
            }
            i12 = i11;
            i4 = i8;
            i7 = i;
            i5 = i15;
            i6 = i7;
        }
        int i16 = 1;
        for (int i17 = 0; i17 < 42; i17++) {
            Calendar calendar2 = new Calendar();
            if (i17 < monthViewStartDiff) {
                calendar2.setYear(i7);
                calendar2.setMonth(i12);
                calendar2.setDay((i4 - monthViewStartDiff) + i17 + 1);
            } else if (i17 >= monthDaysCount + monthViewStartDiff) {
                calendar2.setYear(i6);
                calendar2.setMonth(i5);
                calendar2.setDay(i16);
                i16++;
            } else {
                calendar2.setYear(i);
                calendar2.setMonth(i2);
                calendar2.setCurrentMonth(true);
                calendar2.setDay((i17 - monthViewStartDiff) + 1);
            }
            if (calendar2.equals(calendar)) {
                calendar2.setCurrentDay(true);
            }
            LunarCalendar.setupLunarCalendar(calendar2);
            arrayList.add(calendar2);
        }
        return arrayList;
    }

    static List<Calendar> getWeekCalendars(Calendar calendar, CalendarViewDelegate calendarViewDelegate) {
        long timeInMillis = calendar.getTimeInMillis();
        Calendar instance = Calendar.getInstance();
        instance.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay(), 12, 0);
        int i = instance.get(7);
        if (calendarViewDelegate.getWeekStart() == 1) {
            i--;
        } else if (calendarViewDelegate.getWeekStart() == 2) {
            i = i == 1 ? 6 : i - calendarViewDelegate.getWeekStart();
        } else if (i == 7) {
            i = 0;
        }
        Calendar instance2 = Calendar.getInstance();
        instance2.setTimeInMillis(timeInMillis - (((long) i) * ONE_DAY));
        Calendar calendar2 = new Calendar();
        calendar2.setYear(instance2.get(1));
        calendar2.setMonth(instance2.get(2) + 1);
        calendar2.setDay(instance2.get(5));
        return initCalendarForWeekView(calendar2, calendarViewDelegate, calendarViewDelegate.getWeekStart());
    }

    static List<Calendar> initCalendarForWeekView(Calendar calendar, CalendarViewDelegate calendarViewDelegate, int i) {
        Calendar instance = Calendar.getInstance();
        instance.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay(), 12, 0);
        long timeInMillis = instance.getTimeInMillis();
        ArrayList arrayList = new ArrayList();
        instance.setTimeInMillis(timeInMillis);
        Calendar calendar2 = new Calendar();
        calendar2.setYear(calendar.getYear());
        calendar2.setMonth(calendar.getMonth());
        calendar2.setDay(calendar.getDay());
        if (calendar2.equals(calendarViewDelegate.getCurrentDay())) {
            calendar2.setCurrentDay(true);
        }
        LunarCalendar.setupLunarCalendar(calendar2);
        calendar2.setCurrentMonth(true);
        arrayList.add(calendar2);
        for (int i2 = 1; i2 <= 6; i2++) {
            instance.setTimeInMillis((((long) i2) * ONE_DAY) + timeInMillis);
            Calendar calendar3 = new Calendar();
            calendar3.setYear(instance.get(1));
            calendar3.setMonth(instance.get(2) + 1);
            calendar3.setDay(instance.get(5));
            if (calendar3.equals(calendarViewDelegate.getCurrentDay())) {
                calendar3.setCurrentDay(true);
            }
            LunarCalendar.setupLunarCalendar(calendar3);
            calendar3.setCurrentMonth(true);
            arrayList.add(calendar3);
        }
        return arrayList;
    }

    private static int getWeekViewStartDiff(int i, int i2, int i3, int i4) {
        Calendar instance = Calendar.getInstance();
        instance.set(i, i2 - 1, i3, 12, 0);
        int i5 = instance.get(7);
        if (i4 == 1) {
            return i5 - 1;
        }
        if (i4 == 2) {
            if (i5 == 1) {
                return 6;
            }
            return i5 - i4;
        } else if (i5 == 7) {
            return 0;
        } else {
            return i5;
        }
    }

    public static int getWeekViewEndDiff(int i, int i2, int i3, int i4) {
        Calendar instance = Calendar.getInstance();
        instance.set(i, i2 - 1, i3, 12, 0);
        int i5 = instance.get(7);
        if (i4 == 1) {
            return 7 - i5;
        }
        if (i4 == 2) {
            if (i5 == 1) {
                return 0;
            }
            return (7 - i5) + 1;
        } else if (i5 == 7) {
            return 6;
        } else {
            return (7 - i5) - 1;
        }
    }

    static Calendar getFirstCalendarFromMonthViewPager(int i, CalendarViewDelegate calendarViewDelegate) {
        Calendar calendar = new Calendar();
        boolean z = true;
        calendar.setYear((((calendarViewDelegate.getMinYearMonth() + i) - 1) / 12) + calendarViewDelegate.getMinYear());
        calendar.setMonth((((i + calendarViewDelegate.getMinYearMonth()) - 1) % 12) + 1);
        if (calendarViewDelegate.getDefaultCalendarSelectDay() != 0) {
            int monthDaysCount = getMonthDaysCount(calendar.getYear(), calendar.getMonth());
            Calendar calendar2 = calendarViewDelegate.mIndexCalendar;
            if (calendar2 == null || calendar2.getDay() == 0) {
                monthDaysCount = 1;
            } else if (monthDaysCount >= calendar2.getDay()) {
                monthDaysCount = calendar2.getDay();
            }
            calendar.setDay(monthDaysCount);
        } else {
            calendar.setDay(1);
        }
        if (!isCalendarInRange(calendar, calendarViewDelegate)) {
            if (isMinRangeEdge(calendar, calendarViewDelegate)) {
                calendar = calendarViewDelegate.getMinRangeCalendar();
            } else {
                calendar = calendarViewDelegate.getMaxRangeCalendar();
            }
        }
        if (!(calendar.getYear() == calendarViewDelegate.getCurrentDay().getYear() && calendar.getMonth() == calendarViewDelegate.getCurrentDay().getMonth())) {
            z = false;
        }
        calendar.setCurrentMonth(z);
        calendar.setCurrentDay(calendar.equals(calendarViewDelegate.getCurrentDay()));
        LunarCalendar.setupLunarCalendar(calendar);
        return calendar;
    }

    static Calendar getRangeEdgeCalendar(Calendar calendar, CalendarViewDelegate calendarViewDelegate) {
        if (isCalendarInRange(calendarViewDelegate.getCurrentDay(), calendarViewDelegate) && calendarViewDelegate.getDefaultCalendarSelectDay() != 2) {
            return calendarViewDelegate.createCurrentDate();
        }
        if (isCalendarInRange(calendar, calendarViewDelegate)) {
            return calendar;
        }
        if (calendarViewDelegate.getMinRangeCalendar().isSameMonth(calendar)) {
            return calendarViewDelegate.getMinRangeCalendar();
        }
        return calendarViewDelegate.getMaxRangeCalendar();
    }

    private static boolean isMinRangeEdge(Calendar calendar, CalendarViewDelegate calendarViewDelegate) {
        Calendar instance = Calendar.getInstance();
        Calendar calendar2 = instance;
        calendar2.set(calendarViewDelegate.getMinYear(), calendarViewDelegate.getMinYearMonth() - 1, calendarViewDelegate.getMinYearDay(), 12, 0);
        long timeInMillis = instance.getTimeInMillis();
        calendar2.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay(), 12, 0);
        if (instance.getTimeInMillis() < timeInMillis) {
            return true;
        }
        return false;
    }

    static int dipToPx(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
