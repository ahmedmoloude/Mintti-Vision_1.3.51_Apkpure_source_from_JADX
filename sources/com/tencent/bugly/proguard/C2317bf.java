package com.tencent.bugly.proguard;

import android.os.Handler;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import p036no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

/* renamed from: com.tencent.bugly.proguard.bf */
/* compiled from: BUGLY */
public final class C2317bf implements Runnable {

    /* renamed from: a */
    final Handler f1744a;

    /* renamed from: b */
    long f1745b;

    /* renamed from: c */
    boolean f1746c;

    /* renamed from: d */
    long f1747d;

    /* renamed from: e */
    private final String f1748e;

    /* renamed from: f */
    private final List<C2312ba> f1749f = new LinkedList();

    /* renamed from: g */
    private final long f1750g;

    C2317bf(Handler handler, String str) {
        this.f1744a = handler;
        this.f1748e = str;
        this.f1745b = BootloaderScanner.TIMEOUT;
        this.f1750g = BootloaderScanner.TIMEOUT;
        this.f1746c = true;
    }

    /* renamed from: a */
    public final boolean mo28076a() {
        return !this.f1746c && SystemClock.uptimeMillis() >= this.f1747d + this.f1745b;
    }

    /* renamed from: e */
    private Thread m814e() {
        return this.f1744a.getLooper().getThread();
    }

    public final void run() {
        this.f1746c = true;
        this.f1745b = this.f1750g;
    }

    /* renamed from: b */
    public final long mo28077b() {
        return SystemClock.uptimeMillis() - this.f1747d;
    }

    /* renamed from: c */
    public final List<C2312ba> mo28078c() {
        ArrayList arrayList;
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this.f1749f) {
            arrayList = new ArrayList(this.f1749f.size());
            for (int i = 0; i < this.f1749f.size(); i++) {
                C2312ba baVar = this.f1749f.get(i);
                if (!baVar.f1725e && currentTimeMillis - baVar.f1722b < 200000) {
                    arrayList.add(baVar);
                    baVar.f1725e = true;
                }
            }
        }
        return arrayList;
    }

    /* renamed from: d */
    public final void mo28079d() {
        StringBuilder sb = new StringBuilder(1024);
        long nanoTime = System.nanoTime();
        try {
            StackTraceElement[] stackTrace = m814e().getStackTrace();
            if (stackTrace.length == 0) {
                sb.append("Thread does not have stack trace.\n");
            } else {
                for (StackTraceElement append : stackTrace) {
                    sb.append(append);
                    sb.append("\n");
                }
            }
        } catch (SecurityException e) {
            sb.append("getStackTrace() encountered:\n");
            sb.append(e.getMessage());
            sb.append("\n");
            C2265al.m605a(e);
        }
        long nanoTime2 = System.nanoTime();
        C2312ba baVar = new C2312ba(sb.toString(), System.currentTimeMillis());
        baVar.f1724d = nanoTime2 - nanoTime;
        String name = m814e().getName();
        if (name == null) {
            name = "";
        }
        baVar.f1721a = name;
        synchronized (this.f1749f) {
            while (this.f1749f.size() >= 32) {
                this.f1749f.remove(0);
            }
            this.f1749f.add(baVar);
        }
    }
}
