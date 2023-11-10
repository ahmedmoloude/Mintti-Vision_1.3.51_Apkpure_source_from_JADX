package com.orhanobut.logger;

public interface Printer {
    void addAdapter(LogAdapter logAdapter);

    void clearLogAdapters();

    /* renamed from: d */
    void mo27696d(Object obj);

    /* renamed from: d */
    void mo27697d(String str, Object... objArr);

    /* renamed from: e */
    void mo27698e(String str, Object... objArr);

    /* renamed from: e */
    void mo27699e(Throwable th, String str, Object... objArr);

    /* renamed from: i */
    void mo27700i(String str, Object... objArr);

    void json(String str);

    void log(int i, String str, String str2, Throwable th);

    /* renamed from: t */
    Printer mo27703t(String str);

    /* renamed from: v */
    void mo27704v(String str, Object... objArr);

    /* renamed from: w */
    void mo27705w(String str, Object... objArr);

    void wtf(String str, Object... objArr);

    void xml(String str);
}
