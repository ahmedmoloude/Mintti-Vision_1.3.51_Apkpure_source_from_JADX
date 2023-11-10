package com.tencent.bugly.proguard;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.tencent.bugly.proguard.ak */
/* compiled from: BUGLY */
public final class C2263ak {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static final AtomicInteger f1562a = new AtomicInteger(1);

    /* renamed from: b */
    private static C2263ak f1563b;

    /* renamed from: c */
    private ScheduledExecutorService f1564c = null;

    protected C2263ak() {
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(3, new ThreadFactory() {
            public final Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("BuglyThread-" + C2263ak.f1562a.getAndIncrement());
                return thread;
            }
        });
        this.f1564c = newScheduledThreadPool;
        if (newScheduledThreadPool == null || newScheduledThreadPool.isShutdown()) {
            C2265al.m610d("[AsyncTaskHandler] ScheduledExecutorService is not valiable!", new Object[0]);
        }
    }

    /* renamed from: a */
    public static synchronized C2263ak m595a() {
        C2263ak akVar;
        synchronized (C2263ak.class) {
            if (f1563b == null) {
                f1563b = new C2263ak();
            }
            akVar = f1563b;
        }
        return akVar;
    }

    /* renamed from: a */
    public final synchronized boolean mo28018a(Runnable runnable, long j) {
        if (!mo28020c()) {
            C2265al.m610d("[AsyncTaskHandler] Async handler was closed, should not post task.", new Object[0]);
            return false;
        }
        if (j <= 0) {
            j = 0;
        }
        C2265al.m609c("[AsyncTaskHandler] Post a delay(time: %dms) task: %s", Long.valueOf(j), runnable.getClass().getName());
        try {
            this.f1564c.schedule(runnable, j, TimeUnit.MILLISECONDS);
            return true;
        } catch (Throwable th) {
            if (C2349p.f1912c) {
                th.printStackTrace();
            }
            return false;
        }
    }

    /* renamed from: a */
    public final synchronized boolean mo28017a(Runnable runnable) {
        if (!mo28020c()) {
            C2265al.m610d("[AsyncTaskHandler] Async handler was closed, should not post task.", new Object[0]);
            return false;
        } else if (runnable == null) {
            C2265al.m610d("[AsyncTaskHandler] Task input is null.", new Object[0]);
            return false;
        } else {
            C2265al.m609c("[AsyncTaskHandler] Post a normal task: %s", runnable.getClass().getName());
            try {
                this.f1564c.execute(runnable);
                return true;
            } catch (Throwable th) {
                if (C2349p.f1912c) {
                    th.printStackTrace();
                }
                return false;
            }
        }
    }

    /* renamed from: b */
    public final synchronized void mo28019b() {
        ScheduledExecutorService scheduledExecutorService = this.f1564c;
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
            C2265al.m609c("[AsyncTaskHandler] Close async handler.", new Object[0]);
            this.f1564c.shutdownNow();
        }
    }

    /* renamed from: c */
    public final synchronized boolean mo28020c() {
        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = this.f1564c;
        return scheduledExecutorService != null && !scheduledExecutorService.isShutdown();
    }
}
