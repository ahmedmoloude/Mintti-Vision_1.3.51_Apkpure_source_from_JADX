package p028io.reactivex.internal.operators.single;

import p028io.reactivex.Single;
import p028io.reactivex.SingleObserver;
import p028io.reactivex.SingleSource;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.exceptions.Exceptions;
import p028io.reactivex.functions.Action;
import p028io.reactivex.internal.disposables.DisposableHelper;
import p028io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.single.SingleDoAfterTerminate */
public final class SingleDoAfterTerminate<T> extends Single<T> {
    final Action onAfterTerminate;
    final SingleSource<T> source;

    public SingleDoAfterTerminate(SingleSource<T> singleSource, Action action) {
        this.source = singleSource;
        this.onAfterTerminate = action;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe(new DoAfterTerminateObserver(singleObserver, this.onAfterTerminate));
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleDoAfterTerminate$DoAfterTerminateObserver */
    static final class DoAfterTerminateObserver<T> implements SingleObserver<T>, Disposable {
        final SingleObserver<? super T> downstream;
        final Action onAfterTerminate;
        Disposable upstream;

        DoAfterTerminateObserver(SingleObserver<? super T> singleObserver, Action action) {
            this.downstream = singleObserver;
            this.onAfterTerminate = action;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.downstream.onSuccess(t);
            onAfterTerminate();
        }

        public void onError(Throwable th) {
            this.downstream.onError(th);
            onAfterTerminate();
        }

        public void dispose() {
            this.upstream.dispose();
        }

        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        private void onAfterTerminate() {
            try {
                this.onAfterTerminate.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }
}
