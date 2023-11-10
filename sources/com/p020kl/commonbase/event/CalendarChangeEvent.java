package com.p020kl.commonbase.event;

import com.haibin.calendarview.Calendar;

/* renamed from: com.kl.commonbase.event.CalendarChangeEvent */
public class CalendarChangeEvent extends Event<Calendar> {
    private static final long serialVersionUID = 3935242058315550753L;
    private String measureType;

    public String getMeasureType() {
        return this.measureType;
    }

    public void setMeasureType(String str) {
        this.measureType = str;
    }

    public CalendarChangeEvent(Calendar calendar, String str) {
        super(calendar);
        this.measureType = str;
    }
}
