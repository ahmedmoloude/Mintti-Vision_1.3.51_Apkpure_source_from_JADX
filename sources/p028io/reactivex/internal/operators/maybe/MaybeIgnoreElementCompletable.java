package p028io.reactivex.internal.operators.maybe;

import p028io.reactivex.Completable;
import p028io.reactivex.CompletableObserver;
import p028io.reactivex.Maybe;
import p028io.reactivex.MaybeObserver;
import p028io.reactivex.MaybeSource;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.internal.disposables.DisposableHelper;
import p028io.reactivex.internal.fuseable.FuseToMaybe;
import p028io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeIgnoreElementCompletable */
public final class MaybeIgnoreElementCompletable<T> extends Completable implements FuseToMaybe<T> {
    final MaybeSource<T> source;

    public MaybeIgnoreElementCompletable(MaybeSource<T> maybeSource) {
        this.source = maybeSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new IgnoreMaybeObserver(completableObserver));
    }

    public Maybe<T> fuseToMaybe() {
        return RxJavaPlugins.onAssembly(new MaybeIgnoreElement(this.source));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeIgnoreElementCompletable$IgnoreMaybeObserver */
    static final class IgnoreMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        final CompletableObserver downstream;
        Disposable upstream;

        IgnoreMaybeObserver(CompletableObserver completableObserver) {
            this.downstream = completableObserver;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.upstream = DisposableHelper.DISPOSED;
            this.downstream.onComplete();
        }

        public void onError(Throwable th) {
            this.upstream = DisposableHelper.DISPOSED;
            this.downstream.onError(th);
        }

        public void onComplete() {
            this.upstream = DisposableHelper.DISPOSED;
            this.downstream.onComplete();
        }

        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        public void dispose() {
            this.upstream.dispose();
            this.upstream = DisposableHelper.DISPOSED;
        }
    }
}
