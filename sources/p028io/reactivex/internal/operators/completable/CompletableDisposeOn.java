package p028io.reactivex.internal.operators.completable;

import p028io.reactivex.Completable;
import p028io.reactivex.CompletableObserver;
import p028io.reactivex.CompletableSource;
import p028io.reactivex.Scheduler;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.internal.disposables.DisposableHelper;
import p028io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletableDisposeOn */
public final class CompletableDisposeOn extends Completable {
    final Scheduler scheduler;
    final CompletableSource source;

    public CompletableDisposeOn(CompletableSource completableSource, Scheduler scheduler2) {
        this.source = completableSource;
        this.scheduler = scheduler2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new DisposeOnObserver(completableObserver, this.scheduler));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableDisposeOn$DisposeOnObserver */
    static final class DisposeOnObserver implements CompletableObserver, Disposable, Runnable {
        volatile boolean disposed;
        final CompletableObserver downstream;
        final Scheduler scheduler;
        Disposable upstream;

        DisposeOnObserver(CompletableObserver completableObserver, Scheduler scheduler2) {
            this.downstream = completableObserver;
            this.scheduler = scheduler2;
        }

        public void onComplete() {
            if (!this.disposed) {
                this.downstream.onComplete();
            }
        }

        public void onError(Throwable th) {
            if (this.disposed) {
                RxJavaPlugins.onError(th);
            } else {
                this.downstream.onError(th);
            }
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        public void dispose() {
            this.disposed = true;
            this.scheduler.scheduleDirect(this);
        }

        public boolean isDisposed() {
            return this.disposed;
        }

        public void run() {
            this.upstream.dispose();
            this.upstream = DisposableHelper.DISPOSED;
        }
    }
}
