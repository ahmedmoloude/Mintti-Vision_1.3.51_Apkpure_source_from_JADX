package com.itextpdf.xmp.impl;

import com.itextpdf.xmp.XMPDateTime;
import com.itextpdf.xmp.XMPException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class ISO8601Converter {
    private ISO8601Converter() {
    }

    public static XMPDateTime parse(String str) throws XMPException {
        return parse(str, new XMPDateTimeImpl());
    }

    /* JADX WARNING: Removed duplicated region for block: B:128:0x0222 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x0223  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.xmp.XMPDateTime parse(java.lang.String r12, com.itextpdf.xmp.XMPDateTime r13) throws com.itextpdf.xmp.XMPException {
        /*
            if (r12 == 0) goto L_0x022b
            int r0 = r12.length()
            if (r0 != 0) goto L_0x0009
            return r13
        L_0x0009:
            com.itextpdf.xmp.impl.ParseState r0 = new com.itextpdf.xmp.impl.ParseState
            r0.<init>(r12)
            r12 = 0
            char r1 = r0.mo24234ch(r12)
            r2 = 45
            if (r1 != r2) goto L_0x001a
            r0.skip()
        L_0x001a:
            r1 = 9999(0x270f, float:1.4012E-41)
            java.lang.String r3 = "Invalid year in date string"
            int r1 = r0.gatherInt(r3, r1)
            boolean r3 = r0.hasNext()
            r4 = 5
            if (r3 == 0) goto L_0x0038
            char r3 = r0.mo24233ch()
            if (r3 != r2) goto L_0x0030
            goto L_0x0038
        L_0x0030:
            com.itextpdf.xmp.XMPException r12 = new com.itextpdf.xmp.XMPException
            java.lang.String r13 = "Invalid date string, after year"
            r12.<init>(r13, r4)
            throw r12
        L_0x0038:
            char r3 = r0.mo24234ch(r12)
            if (r3 != r2) goto L_0x003f
            int r1 = -r1
        L_0x003f:
            r13.setYear(r1)
            boolean r1 = r0.hasNext()
            if (r1 != 0) goto L_0x0049
            return r13
        L_0x0049:
            r0.skip()
            r1 = 12
            java.lang.String r3 = "Invalid month in date string"
            int r1 = r0.gatherInt(r3, r1)
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0069
            char r3 = r0.mo24233ch()
            if (r3 != r2) goto L_0x0061
            goto L_0x0069
        L_0x0061:
            com.itextpdf.xmp.XMPException r12 = new com.itextpdf.xmp.XMPException
            java.lang.String r13 = "Invalid date string, after month"
            r12.<init>(r13, r4)
            throw r12
        L_0x0069:
            r13.setMonth(r1)
            boolean r1 = r0.hasNext()
            if (r1 != 0) goto L_0x0073
            return r13
        L_0x0073:
            r0.skip()
            r1 = 31
            java.lang.String r3 = "Invalid day in date string"
            int r1 = r0.gatherInt(r3, r1)
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0095
            char r3 = r0.mo24233ch()
            r5 = 84
            if (r3 != r5) goto L_0x008d
            goto L_0x0095
        L_0x008d:
            com.itextpdf.xmp.XMPException r12 = new com.itextpdf.xmp.XMPException
            java.lang.String r13 = "Invalid date string, after day"
            r12.<init>(r13, r4)
            throw r12
        L_0x0095:
            r13.setDay(r1)
            boolean r1 = r0.hasNext()
            if (r1 != 0) goto L_0x009f
            return r13
        L_0x009f:
            r0.skip()
            java.lang.String r1 = "Invalid hour in date string"
            r3 = 23
            int r1 = r0.gatherInt(r1, r3)
            r13.setHour(r1)
            boolean r1 = r0.hasNext()
            if (r1 != 0) goto L_0x00b4
            return r13
        L_0x00b4:
            char r1 = r0.mo24233ch()
            r5 = 59
            r6 = 58
            r7 = 43
            r8 = 90
            if (r1 != r6) goto L_0x00f5
            r0.skip()
            java.lang.String r1 = "Invalid minute in date string"
            int r1 = r0.gatherInt(r1, r5)
            boolean r9 = r0.hasNext()
            if (r9 == 0) goto L_0x00f2
            char r9 = r0.mo24233ch()
            if (r9 == r6) goto L_0x00f2
            char r9 = r0.mo24233ch()
            if (r9 == r8) goto L_0x00f2
            char r9 = r0.mo24233ch()
            if (r9 == r7) goto L_0x00f2
            char r9 = r0.mo24233ch()
            if (r9 != r2) goto L_0x00ea
            goto L_0x00f2
        L_0x00ea:
            com.itextpdf.xmp.XMPException r12 = new com.itextpdf.xmp.XMPException
            java.lang.String r13 = "Invalid date string, after minute"
            r12.<init>(r13, r4)
            throw r12
        L_0x00f2:
            r13.setMinute(r1)
        L_0x00f5:
            boolean r1 = r0.hasNext()
            if (r1 != 0) goto L_0x00fc
            return r13
        L_0x00fc:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x018d
            char r1 = r0.mo24233ch()
            if (r1 != r6) goto L_0x018d
            r0.skip()
            java.lang.String r1 = "Invalid whole seconds in date string"
            int r1 = r0.gatherInt(r1, r5)
            boolean r9 = r0.hasNext()
            r10 = 46
            if (r9 == 0) goto L_0x013a
            char r9 = r0.mo24233ch()
            if (r9 == r10) goto L_0x013a
            char r9 = r0.mo24233ch()
            if (r9 == r8) goto L_0x013a
            char r9 = r0.mo24233ch()
            if (r9 == r7) goto L_0x013a
            char r9 = r0.mo24233ch()
            if (r9 != r2) goto L_0x0132
            goto L_0x013a
        L_0x0132:
            com.itextpdf.xmp.XMPException r12 = new com.itextpdf.xmp.XMPException
            java.lang.String r13 = "Invalid date string, after whole seconds"
            r12.<init>(r13, r4)
            throw r12
        L_0x013a:
            r13.setSecond(r1)
            char r1 = r0.mo24233ch()
            if (r1 != r10) goto L_0x01a8
            r0.skip()
            int r1 = r0.pos()
            r9 = 999999999(0x3b9ac9ff, float:0.004723787)
            java.lang.String r10 = "Invalid fractional seconds in date string"
            int r9 = r0.gatherInt(r10, r9)
            boolean r10 = r0.hasNext()
            if (r10 == 0) goto L_0x0174
            char r10 = r0.mo24233ch()
            if (r10 == r8) goto L_0x0174
            char r10 = r0.mo24233ch()
            if (r10 == r7) goto L_0x0174
            char r10 = r0.mo24233ch()
            if (r10 != r2) goto L_0x016c
            goto L_0x0174
        L_0x016c:
            com.itextpdf.xmp.XMPException r12 = new com.itextpdf.xmp.XMPException
            java.lang.String r13 = "Invalid date string, after fractional second"
            r12.<init>(r13, r4)
            throw r12
        L_0x0174:
            int r10 = r0.pos()
            int r10 = r10 - r1
        L_0x0179:
            r1 = 9
            if (r10 <= r1) goto L_0x0182
            int r9 = r9 / 10
            int r10 = r10 + -1
            goto L_0x0179
        L_0x0182:
            if (r10 >= r1) goto L_0x0189
            int r9 = r9 * 10
            int r10 = r10 + 1
            goto L_0x0182
        L_0x0189:
            r13.setNanoSecond(r9)
            goto L_0x01a8
        L_0x018d:
            char r1 = r0.mo24233ch()
            if (r1 == r8) goto L_0x01a8
            char r1 = r0.mo24233ch()
            if (r1 == r7) goto L_0x01a8
            char r1 = r0.mo24233ch()
            if (r1 != r2) goto L_0x01a0
            goto L_0x01a8
        L_0x01a0:
            com.itextpdf.xmp.XMPException r12 = new com.itextpdf.xmp.XMPException
            java.lang.String r13 = "Invalid date string, after time"
            r12.<init>(r13, r4)
            throw r12
        L_0x01a8:
            boolean r1 = r0.hasNext()
            if (r1 != 0) goto L_0x01af
            return r13
        L_0x01af:
            char r1 = r0.mo24233ch()
            if (r1 != r8) goto L_0x01b9
            r0.skip()
            goto L_0x0205
        L_0x01b9:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0205
            char r1 = r0.mo24233ch()
            if (r1 != r7) goto L_0x01c7
            r1 = 1
            goto L_0x01ce
        L_0x01c7:
            char r1 = r0.mo24233ch()
            if (r1 != r2) goto L_0x01fd
            r1 = -1
        L_0x01ce:
            r0.skip()
            java.lang.String r2 = "Invalid time zone hour in date string"
            int r2 = r0.gatherInt(r2, r3)
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x01f9
            char r12 = r0.mo24233ch()
            if (r12 != r6) goto L_0x01f1
            r0.skip()
            java.lang.String r12 = "Invalid time zone minute in date string"
            int r12 = r0.gatherInt(r12, r5)
            r11 = r1
            r1 = r12
            r12 = r2
            r2 = r11
            goto L_0x0207
        L_0x01f1:
            com.itextpdf.xmp.XMPException r12 = new com.itextpdf.xmp.XMPException
            java.lang.String r13 = "Invalid date string, after time zone hour"
            r12.<init>(r13, r4)
            throw r12
        L_0x01f9:
            r12 = r2
            r2 = r1
            r1 = 0
            goto L_0x0207
        L_0x01fd:
            com.itextpdf.xmp.XMPException r12 = new com.itextpdf.xmp.XMPException
            java.lang.String r13 = "Time zone must begin with 'Z', '+', or '-'"
            r12.<init>(r13, r4)
            throw r12
        L_0x0205:
            r1 = 0
            r2 = 0
        L_0x0207:
            int r12 = r12 * 3600
            int r12 = r12 * 1000
            int r1 = r1 * 60
            int r1 = r1 * 1000
            int r12 = r12 + r1
            int r12 = r12 * r2
            java.util.SimpleTimeZone r1 = new java.util.SimpleTimeZone
            java.lang.String r2 = ""
            r1.<init>(r12, r2)
            r13.setTimeZone(r1)
            boolean r12 = r0.hasNext()
            if (r12 != 0) goto L_0x0223
            return r13
        L_0x0223:
            com.itextpdf.xmp.XMPException r12 = new com.itextpdf.xmp.XMPException
            java.lang.String r13 = "Invalid date string, extra chars at end"
            r12.<init>(r13, r4)
            throw r12
        L_0x022b:
            com.itextpdf.xmp.XMPException r12 = new com.itextpdf.xmp.XMPException
            r13 = 4
            java.lang.String r0 = "Parameter must not be null"
            r12.<init>(r0, r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.xmp.impl.ISO8601Converter.parse(java.lang.String, com.itextpdf.xmp.XMPDateTime):com.itextpdf.xmp.XMPDateTime");
    }

    public static String render(XMPDateTime xMPDateTime) {
        StringBuffer stringBuffer = new StringBuffer();
        if (xMPDateTime.hasDate()) {
            DecimalFormat decimalFormat = new DecimalFormat("0000", new DecimalFormatSymbols(Locale.ENGLISH));
            stringBuffer.append(decimalFormat.format((long) xMPDateTime.getYear()));
            if (xMPDateTime.getMonth() == 0) {
                return stringBuffer.toString();
            }
            decimalFormat.applyPattern("'-'00");
            stringBuffer.append(decimalFormat.format((long) xMPDateTime.getMonth()));
            if (xMPDateTime.getDay() == 0) {
                return stringBuffer.toString();
            }
            stringBuffer.append(decimalFormat.format((long) xMPDateTime.getDay()));
            if (xMPDateTime.hasTime()) {
                stringBuffer.append('T');
                decimalFormat.applyPattern("00");
                stringBuffer.append(decimalFormat.format((long) xMPDateTime.getHour()));
                stringBuffer.append(':');
                stringBuffer.append(decimalFormat.format((long) xMPDateTime.getMinute()));
                if (!(xMPDateTime.getSecond() == 0 && xMPDateTime.getNanoSecond() == 0)) {
                    decimalFormat.applyPattern(":00.#########");
                    stringBuffer.append(decimalFormat.format(((double) xMPDateTime.getSecond()) + (((double) xMPDateTime.getNanoSecond()) / 1.0E9d)));
                }
                if (xMPDateTime.hasTimeZone()) {
                    int offset = xMPDateTime.getTimeZone().getOffset(xMPDateTime.getCalendar().getTimeInMillis());
                    if (offset == 0) {
                        stringBuffer.append('Z');
                    } else {
                        int i = offset / 3600000;
                        int abs = Math.abs((offset % 3600000) / 60000);
                        decimalFormat.applyPattern("+00;-00");
                        stringBuffer.append(decimalFormat.format((long) i));
                        decimalFormat.applyPattern(":00");
                        stringBuffer.append(decimalFormat.format((long) abs));
                    }
                }
            }
        }
        return stringBuffer.toString();
    }
}
