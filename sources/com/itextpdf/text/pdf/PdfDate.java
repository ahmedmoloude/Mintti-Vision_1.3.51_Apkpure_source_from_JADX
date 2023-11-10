package com.itextpdf.text.pdf;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

public class PdfDate extends PdfString {
    private static final int[] DATE_SPACE = {1, 4, 0, 2, 2, -1, 5, 2, 0, 11, 2, 0, 12, 2, 0, 13, 2, 0};

    public PdfDate(Calendar calendar) {
        StringBuffer stringBuffer = new StringBuffer("D:");
        stringBuffer.append(setLength(calendar.get(1), 4));
        stringBuffer.append(setLength(calendar.get(2) + 1, 2));
        stringBuffer.append(setLength(calendar.get(5), 2));
        stringBuffer.append(setLength(calendar.get(11), 2));
        stringBuffer.append(setLength(calendar.get(12), 2));
        stringBuffer.append(setLength(calendar.get(13), 2));
        int i = (calendar.get(15) + calendar.get(16)) / 3600000;
        if (i == 0) {
            stringBuffer.append('Z');
        } else if (i < 0) {
            stringBuffer.append('-');
            i = -i;
        } else {
            stringBuffer.append('+');
        }
        if (i != 0) {
            stringBuffer.append(setLength(i, 2));
            stringBuffer.append('\'');
            stringBuffer.append(setLength(Math.abs((calendar.get(15) + calendar.get(16)) / 60000) - (i * 60), 2));
            stringBuffer.append('\'');
        }
        this.value = stringBuffer.toString();
    }

    public PdfDate() {
        this(new GregorianCalendar());
    }

    private String setLength(int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(i);
        while (stringBuffer.length() < i2) {
            stringBuffer.insert(0, "0");
        }
        stringBuffer.setLength(i2);
        return stringBuffer.toString();
    }

    public String getW3CDate() {
        return getW3CDate(this.value);
    }

    public static String getW3CDate(String str) {
        String str2;
        if (str.startsWith("D:")) {
            str = str.substring(2);
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() < 4) {
            return "0000";
        }
        stringBuffer.append(str.substring(0, 4));
        String substring = str.substring(4);
        if (substring.length() < 2) {
            return stringBuffer.toString();
        }
        stringBuffer.append('-');
        stringBuffer.append(substring.substring(0, 2));
        String substring2 = substring.substring(2);
        if (substring2.length() < 2) {
            return stringBuffer.toString();
        }
        stringBuffer.append('-');
        stringBuffer.append(substring2.substring(0, 2));
        String substring3 = substring2.substring(2);
        if (substring3.length() < 2) {
            return stringBuffer.toString();
        }
        stringBuffer.append('T');
        stringBuffer.append(substring3.substring(0, 2));
        String substring4 = substring3.substring(2);
        if (substring4.length() < 2) {
            stringBuffer.append(":00Z");
            return stringBuffer.toString();
        }
        stringBuffer.append(':');
        stringBuffer.append(substring4.substring(0, 2));
        String substring5 = substring4.substring(2);
        if (substring5.length() < 2) {
            stringBuffer.append('Z');
            return stringBuffer.toString();
        }
        stringBuffer.append(':');
        stringBuffer.append(substring5.substring(0, 2));
        String substring6 = substring5.substring(2);
        if (substring6.startsWith("-") || substring6.startsWith("+")) {
            String substring7 = substring6.substring(0, 1);
            String substring8 = substring6.substring(1);
            if (substring8.length() >= 2) {
                String substring9 = substring8.substring(0, 2);
                if (substring8.length() > 2) {
                    String substring10 = substring8.substring(3);
                    if (substring10.length() >= 2) {
                        str2 = substring10.substring(0, 2);
                        stringBuffer.append(substring7);
                        stringBuffer.append(substring9);
                        stringBuffer.append(':');
                        stringBuffer.append(str2);
                        return stringBuffer.toString();
                    }
                }
                str2 = "00";
                stringBuffer.append(substring7);
                stringBuffer.append(substring9);
                stringBuffer.append(':');
                stringBuffer.append(str2);
                return stringBuffer.toString();
            }
        }
        stringBuffer.append('Z');
        return stringBuffer.toString();
    }

    public static Calendar decode(String str) {
        GregorianCalendar gregorianCalendar;
        try {
            if (str.startsWith("D:")) {
                str = str.substring(2);
            }
            int length = str.length();
            int indexOf = str.indexOf(90);
            int i = 0;
            if (indexOf >= 0) {
                gregorianCalendar = new GregorianCalendar(new SimpleTimeZone(0, "ZPDF"));
            } else {
                indexOf = str.indexOf(43);
                int i2 = 1;
                if (indexOf < 0 && (indexOf = str.indexOf(45)) >= 0) {
                    i2 = -1;
                }
                if (indexOf < 0) {
                    indexOf = length;
                    gregorianCalendar = new GregorianCalendar();
                } else {
                    int parseInt = Integer.parseInt(str.substring(indexOf + 1, indexOf + 3)) * 60;
                    if (indexOf + 5 < str.length()) {
                        parseInt += Integer.parseInt(str.substring(indexOf + 4, indexOf + 6));
                    }
                    gregorianCalendar = new GregorianCalendar(new SimpleTimeZone(parseInt * i2 * 60000, "ZPDF"));
                }
            }
            gregorianCalendar.clear();
            int i3 = 0;
            while (true) {
                int[] iArr = DATE_SPACE;
                if (i >= iArr.length) {
                    break;
                } else if (i3 >= indexOf) {
                    break;
                } else {
                    int i4 = i + 1;
                    gregorianCalendar.set(iArr[i], Integer.parseInt(str.substring(i3, iArr[i4] + i3)) + iArr[i + 2]);
                    i3 += iArr[i4];
                    i += 3;
                }
            }
            return gregorianCalendar;
        } catch (Exception unused) {
            return null;
        }
    }
}
