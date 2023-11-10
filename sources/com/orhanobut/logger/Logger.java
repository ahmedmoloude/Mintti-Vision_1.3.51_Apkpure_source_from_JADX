package com.orhanobut.logger;

public final class Logger {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static Printer printer = new LoggerPrinter();

    private Logger() {
    }

    public static void printer(Printer printer2) {
        printer = (Printer) Utils.checkNotNull(printer2);
    }

    public static void addLogAdapter(LogAdapter logAdapter) {
        printer.addAdapter((LogAdapter) Utils.checkNotNull(logAdapter));
    }

    public static void clearLogAdapters() {
        printer.clearLogAdapters();
    }

    /* renamed from: t */
    public static Printer m388t(String str) {
        return printer.mo27703t(str);
    }

    public static void log(int i, String str, String str2, Throwable th) {
        printer.log(i, str, str2, th);
    }

    /* renamed from: d */
    public static void m384d(String str, Object... objArr) {
        printer.mo27697d(str, objArr);
    }

    /* renamed from: d */
    public static void m383d(Object obj) {
        printer.mo27696d(obj);
    }

    /* renamed from: e */
    public static void m385e(String str, Object... objArr) {
        printer.mo27699e((Throwable) null, str, objArr);
    }

    /* renamed from: e */
    public static void m386e(Throwable th, String str, Object... objArr) {
        printer.mo27699e(th, str, objArr);
    }

    /* renamed from: i */
    public static void m387i(String str, Object... objArr) {
        printer.mo27700i(str, objArr);
    }

    /* renamed from: v */
    public static void m389v(String str, Object... objArr) {
        printer.mo27704v(str, objArr);
    }

    /* renamed from: w */
    public static void m390w(String str, Object... objArr) {
        printer.mo27705w(str, objArr);
    }

    public static void wtf(String str, Object... objArr) {
        printer.wtf(str, objArr);
    }

    public static void json(String str) {
        printer.json(str);
    }

    public static void xml(String str) {
        printer.xml(str);
    }
}
