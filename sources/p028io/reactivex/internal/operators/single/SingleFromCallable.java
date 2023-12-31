package p028io.reactivex.internal.operators.single;

import java.util.concurrent.Callable;
import p028io.reactivex.Single;
import p028io.reactivex.SingleObserver;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.disposables.Disposables;
import p028io.reactivex.exceptions.Exceptions;
import p028io.reactivex.internal.functions.ObjectHelper;
import p028io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.single.SingleFromCallable */
public final class SingleFromCallable<T> extends Single<T> {
    final Callable<? extends T> callable;

    public SingleFromCallable(Callable<? extends T> callable2) {
        this.callable = callable2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        Disposable empty = Disposables.empty();
        singleObserver.onSubscribe(empty);
        if (!empty.isDisposed()) {
            try {
                Object requireNonNull = ObjectHelper.requireNonNull(this.callable.call(), "The callable returned a null value");
                if (!empty.isDisposed()) {
                    singleObserver.onSuccess(requireNonNull);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                if (!empty.isDisposed()) {
                    singleObserver.onError(th);
                } else {
                    RxJavaPlugins.onError(th);
                }
            }
        }
    }
}
