package com.p020kl.commonbase.utils;

import com.p020kl.commonbase.bean.CustomDate;
import com.p020kl.commonbase.constants.Constants;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/* renamed from: com.kl.commonbase.utils.DateUtils */
public class DateUtils {

    /* renamed from: com.kl.commonbase.utils.DateUtils$DateType */
    public enum DateType {
        YEAR,
        MONTH,
        DAY
    }

    public static String getCreateDate() {
        return getISO8601Time(System.currentTimeMillis());
    }

    public static int getYear(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return instance.get(1);
    }

    public static int getMonth(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return instance.get(2) + 1;
    }

    public static int getWeek(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return instance.get(7) - 1;
    }

    public static int getDay(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return instance.get(5);
    }

    public static CustomDate getCustomDateToday() {
        return new CustomDate(getDate(DateType.YEAR), getDate(DateType.MONTH), getDate(DateType.DAY));
    }

    public static CustomDate getCustomDateThisMonth() {
        return new CustomDate(getDate(DateType.YEAR), getDate(DateType.MONTH));
    }

    public static List<CustomDate> getThisWeek() {
        ArrayList arrayList = new ArrayList();
        long mondayOfThisWeek = getMondayOfThisWeek();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(mondayOfThisWeek);
        arrayList.add(new CustomDate(instance.get(1), instance.get(2) + 1, instance.get(5)));
        for (int i = 1; i < 7; i++) {
            instance.add(5, 1);
            arrayList.add(new CustomDate(instance.get(1), instance.get(2) + 1, instance.get(5)));
        }
        return arrayList;
    }

    public static CustomDate getCustomDateHistoryDay(long j) {
        return new CustomDate(getHistroyDate(DateType.YEAR, j), getHistroyDate(DateType.MONTH, j), getHistroyDate(DateType.DAY, j));
    }

    public static CustomDate getCustomDateHistoryMonth(long j) {
        return new CustomDate(getHistroyDate(DateType.YEAR, j), getHistroyDate(DateType.MONTH, j));
    }

    public static List<CustomDate> getHistoryWeek(long j) {
        ArrayList arrayList = new ArrayList();
        long mondayOfHistoryWeek = getMondayOfHistoryWeek(convertStringToDate(j));
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(mondayOfHistoryWeek);
        arrayList.add(new CustomDate(instance.get(1), instance.get(2) + 1, instance.get(5)));
        for (int i = 1; i < 7; i++) {
            instance.add(5, 1);
            arrayList.add(new CustomDate(instance.get(1), instance.get(2) + 1, instance.get(5)));
        }
        return arrayList;
    }

    /* renamed from: com.kl.commonbase.utils.DateUtils$1 */
    static /* synthetic */ class C16051 {
        static final /* synthetic */ int[] $SwitchMap$com$kl$commonbase$utils$DateUtils$DateType;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.kl.commonbase.utils.DateUtils$DateType[] r0 = com.p020kl.commonbase.utils.DateUtils.DateType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$kl$commonbase$utils$DateUtils$DateType = r0
                com.kl.commonbase.utils.DateUtils$DateType r1 = com.p020kl.commonbase.utils.DateUtils.DateType.YEAR     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$kl$commonbase$utils$DateUtils$DateType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.kl.commonbase.utils.DateUtils$DateType r1 = com.p020kl.commonbase.utils.DateUtils.DateType.MONTH     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$kl$commonbase$utils$DateUtils$DateType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.kl.commonbase.utils.DateUtils$DateType r1 = com.p020kl.commonbase.utils.DateUtils.DateType.DAY     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.p020kl.commonbase.utils.DateUtils.C16051.<clinit>():void");
        }
    }

    public static int getDate(DateType dateType) {
        long currentTimeMillis = System.currentTimeMillis();
        int i = C16051.$SwitchMap$com$kl$commonbase$utils$DateUtils$DateType[dateType.ordinal()];
        if (i == 1) {
            return getYear(currentTimeMillis);
        }
        if (i == 2) {
            return getMonth(currentTimeMillis);
        }
        if (i != 3) {
            return 0;
        }
        return getDay(currentTimeMillis);
    }

    public static int getHistroyDate(DateType dateType, long j) {
        int i = C16051.$SwitchMap$com$kl$commonbase$utils$DateUtils$DateType[dateType.ordinal()];
        if (i == 1) {
            return getYear(j);
        }
        if (i == 2) {
            return getMonth(j);
        }
        if (i != 3) {
            return 0;
        }
        return getDay(j);
    }

    public static long getMondayOfThisWeek() {
        Calendar instance = Calendar.getInstance();
        int i = 7;
        int i2 = instance.get(7) - 1;
        if (i2 != 0) {
            i = i2;
        }
        instance.set(11, 0);
        instance.set(13, 0);
        instance.set(12, 0);
        instance.set(14, 0);
        instance.add(5, (-i) + 1);
        return instance.getTimeInMillis();
    }

    public static long getSundayOfThisWeek() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(7) - 1;
        if (i == 0) {
            i = 7;
        }
        instance.set(11, 23);
        instance.set(13, 59);
        instance.set(12, 59);
        instance.set(14, 999);
        instance.add(5, (-i) + 7);
        return instance.getTimeInMillis();
    }

    public static long getMondayOfHistoryWeek(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int i = 7;
        int i2 = instance.get(7) - 1;
        if (i2 != 0) {
            i = i2;
        }
        instance.set(11, 0);
        instance.set(13, 0);
        instance.set(12, 0);
        instance.set(14, 0);
        instance.add(5, (-i) + 1);
        return instance.getTimeInMillis();
    }

    public static long getSundayOfHistoryWeek(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int i = instance.get(7) - 1;
        if (i == 0) {
            i = 7;
        }
        instance.set(11, 23);
        instance.set(13, 59);
        instance.set(12, 59);
        instance.set(14, 999);
        instance.add(5, (-i) + 7);
        return instance.getTimeInMillis();
    }

    public static final Date convertStringToDate(long j) {
        return new Date(j);
    }

    public static String getFormarBirthday(String str) {
        return getFormatDate(str, Constants.TIME_FORMAT_BIRTHDAY);
    }

    public static String toFormatIsoBirthday(String str) {
        return getFormatDate(formatTimeToLong(str, Constants.TIME_FORMAT_BIRTHDAY), Constants.TIME_FORMAT_UTC);
    }

    public static String getFormatHourMin(long j) {
        return new SimpleDateFormat("HH:mm").format(new Date(j));
    }

    public static String getISO8601Time(long j) {
        Date date = new Date(j);
        TimeZone timeZone = TimeZone.getDefault();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.TIME_FORMAT_UTC);
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(date);
    }

    public static long isoToTime(String str) {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.TIME_FORMAT_UTC);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        return date.getTime();
    }

    public static long formatTimeToLong(String str, String str2) {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        return date.getTime();
    }

    public static String getFormatDate(long j, String str) {
        return new SimpleDateFormat(str).format(new Date(j));
    }

    public static String getFormatDate(String str, String str2) {
        return new SimpleDateFormat(str2).format(new Date(isoToTime(str)));
    }

    public static void main(String[] strArr) {
        PrintStream printStream = System.out;
        printStream.println("今天是星期 ：" + getWeek(System.currentTimeMillis()));
        PrintStream printStream2 = System.out;
        printStream2.println("本周开始日期 ： " + getISO8601Time(getMondayOfThisWeek()));
        PrintStream printStream3 = System.out;
        printStream3.println("本周结束日期 ： " + getISO8601Time(getSundayOfThisWeek()));
        PrintStream printStream4 = System.out;
        printStream4.println("today = " + JsonUtils.toJsonString(getCustomDateToday()));
        PrintStream printStream5 = System.out;
        printStream5.println("thisMonth = " + JsonUtils.toJsonString(getCustomDateThisMonth()));
    }
}
