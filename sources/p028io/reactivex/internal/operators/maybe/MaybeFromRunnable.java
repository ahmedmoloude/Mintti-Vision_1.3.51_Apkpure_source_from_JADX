package p028io.reactivex.internal.operators.maybe;

import java.util.concurrent.Callable;
import p028io.reactivex.Maybe;
import p028io.reactivex.MaybeObserver;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.disposables.Disposables;
import p028io.reactivex.exceptions.Exceptions;
import p028io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeFromRunnable */
public final class MaybeFromRunnable<T> extends Maybe<T> implements Callable<T> {
    final Runnable runnable;

    public MaybeFromRunnable(Runnable runnable2) {
        this.runnable = runnable2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        Disposable empty = Disposables.empty();
        maybeObserver.onSubscribe(empty);
        if (!empty.isDisposed()) {
            try {
                this.runnable.run();
                if (!empty.isDisposed()) {
                    maybeObserver.onComplete();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                if (!empty.isDisposed()) {
                    maybeObserver.onError(th);
                } else {
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }

    public T call() throws Exception {
        this.runnable.run();
        return null;
    }
}
