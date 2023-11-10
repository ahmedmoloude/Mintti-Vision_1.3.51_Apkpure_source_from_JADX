package com.itextpdf.xmp.impl;

import com.itextpdf.xmp.XMPDateTime;
import com.itextpdf.xmp.XMPException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.time.DurationKt;

public class XMPDateTimeImpl implements XMPDateTime {
    private int day = 0;
    private boolean hasDate = false;
    private boolean hasTime = false;
    private boolean hasTimeZone = false;
    private int hour = 0;
    private int minute = 0;
    private int month = 0;
    private int nanoSeconds;
    private int second = 0;
    private TimeZone timeZone = null;
    private int year = 0;

    public XMPDateTimeImpl() {
    }

    public XMPDateTimeImpl(Calendar calendar) {
        Date time = calendar.getTime();
        TimeZone timeZone2 = calendar.getTimeZone();
        GregorianCalendar gregorianCalendar = (GregorianCalendar) Calendar.getInstance(Locale.US);
        gregorianCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
        gregorianCalendar.setTimeZone(timeZone2);
        gregorianCalendar.setTime(time);
        this.year = gregorianCalendar.get(1);
        this.month = gregorianCalendar.get(2) + 1;
        this.day = gregorianCalendar.get(5);
        this.hour = gregorianCalendar.get(11);
        this.minute = gregorianCalendar.get(12);
        this.second = gregorianCalendar.get(13);
        this.nanoSeconds = gregorianCalendar.get(14) * DurationKt.NANOS_IN_MILLIS;
        this.timeZone = gregorianCalendar.getTimeZone();
        this.hasTimeZone = true;
        this.hasTime = true;
        this.hasDate = true;
    }

    public XMPDateTimeImpl(Date date, TimeZone timeZone2) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone2);
        gregorianCalendar.setTime(date);
        this.year = gregorianCalendar.get(1);
        this.month = gregorianCalendar.get(2) + 1;
        this.day = gregorianCalendar.get(5);
        this.hour = gregorianCalendar.get(11);
        this.minute = gregorianCalendar.get(12);
        this.second = gregorianCalendar.get(13);
        this.nanoSeconds = gregorianCalendar.get(14) * DurationKt.NANOS_IN_MILLIS;
        this.timeZone = timeZone2;
        this.hasTimeZone = true;
        this.hasTime = true;
        this.hasDate = true;
    }

    public XMPDateTimeImpl(String str) throws XMPException {
        ISO8601Converter.parse(str, this);
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int i) {
        this.year = Math.min(Math.abs(i), 9999);
        this.hasDate = true;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int i) {
        if (i < 1) {
            this.month = 1;
        } else if (i > 12) {
            this.month = 12;
        } else {
            this.month = i;
        }
        this.hasDate = true;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int i) {
        if (i < 1) {
            this.day = 1;
        } else if (i > 31) {
            this.day = 31;
        } else {
            this.day = i;
        }
        this.hasDate = true;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int i) {
        this.hour = Math.min(Math.abs(i), 23);
        this.hasTime = true;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int i) {
        this.minute = Math.min(Math.abs(i), 59);
        this.hasTime = true;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int i) {
        this.second = Math.min(Math.abs(i), 59);
        this.hasTime = true;
    }

    public int getNanoSecond() {
        return this.nanoSeconds;
    }

    public void setNanoSecond(int i) {
        this.nanoSeconds = i;
        this.hasTime = true;
    }

    public int compareTo(Object obj) {
        float signum;
        XMPDateTime xMPDateTime = (XMPDateTime) obj;
        long timeInMillis = getCalendar().getTimeInMillis() - xMPDateTime.getCalendar().getTimeInMillis();
        if (timeInMillis != 0) {
            signum = Math.signum((float) timeInMillis);
        } else {
            signum = Math.signum((float) ((long) (this.nanoSeconds - xMPDateTime.getNanoSecond())));
        }
        return (int) signum;
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(TimeZone timeZone2) {
        this.timeZone = timeZone2;
        this.hasTime = true;
        this.hasTimeZone = true;
    }

    public boolean hasDate() {
        return this.hasDate;
    }

    public boolean hasTime() {
        return this.hasTime;
    }

    public boolean hasTimeZone() {
        return this.hasTimeZone;
    }

    public Calendar getCalendar() {
        GregorianCalendar gregorianCalendar = (GregorianCalendar) Calendar.getInstance(Locale.US);
        gregorianCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
        if (this.hasTimeZone) {
            gregorianCalendar.setTimeZone(this.timeZone);
        }
        gregorianCalendar.set(1, this.year);
        gregorianCalendar.set(2, this.month - 1);
        gregorianCalendar.set(5, this.day);
        gregorianCalendar.set(11, this.hour);
        gregorianCalendar.set(12, this.minute);
        gregorianCalendar.set(13, this.second);
        gregorianCalendar.set(14, this.nanoSeconds / DurationKt.NANOS_IN_MILLIS);
        return gregorianCalendar;
    }

    public String getISO8601String() {
        return ISO8601Converter.render(this);
    }

    public String toString() {
        return getISO8601String();
    }
}
