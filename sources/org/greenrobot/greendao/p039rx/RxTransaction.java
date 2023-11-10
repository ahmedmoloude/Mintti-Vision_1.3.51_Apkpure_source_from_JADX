package org.greenrobot.greendao.p039rx;

import java.util.concurrent.Callable;
import org.greenrobot.greendao.AbstractDaoSession;
import rx.Observable;
import rx.Scheduler;

/* renamed from: org.greenrobot.greendao.rx.RxTransaction */
public class RxTransaction extends RxBase {
    /* access modifiers changed from: private */
    public final AbstractDaoSession daoSession;

    public /* bridge */ /* synthetic */ Scheduler getScheduler() {
        return super.getScheduler();
    }

    public RxTransaction(AbstractDaoSession abstractDaoSession) {
        this.daoSession = abstractDaoSession;
    }

    public RxTransaction(AbstractDaoSession abstractDaoSession, Scheduler scheduler) {
        super(scheduler);
        this.daoSession = abstractDaoSession;
    }

    public Observable<Void> run(final Runnable runnable) {
        return wrap(new Callable<Void>() {
            public Void call() throws Exception {
                RxTransaction.this.daoSession.runInTx(runnable);
                return null;
            }
        });
    }

    public <T> Observable<T> call(final Callable<T> callable) {
        return wrap(new Callable<T>() {
            public T call() throws Exception {
                return RxTransaction.this.daoSession.callInTx(callable);
            }
        });
    }

    public AbstractDaoSession getDaoSession() {
        return this.daoSession;
    }
}
