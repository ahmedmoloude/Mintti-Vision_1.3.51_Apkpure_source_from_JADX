package org.greenrobot.greendao.p039rx;

import java.util.concurrent.Callable;
import rx.Observable;
import rx.Scheduler;

/* renamed from: org.greenrobot.greendao.rx.RxBase */
class RxBase {
    protected final Scheduler scheduler;

    RxBase() {
        this.scheduler = null;
    }

    RxBase(Scheduler scheduler2) {
        this.scheduler = scheduler2;
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    /* access modifiers changed from: protected */
    public <R> Observable<R> wrap(Callable<R> callable) {
        return wrap(RxUtils.fromCallable(callable));
    }

    /* access modifiers changed from: protected */
    public <R> Observable<R> wrap(Observable<R> observable) {
        Scheduler scheduler2 = this.scheduler;
        return scheduler2 != null ? observable.subscribeOn(scheduler2) : observable;
    }
}
