package p028io.reactivex.internal.operators.maybe;

import java.util.concurrent.atomic.AtomicReference;
import p028io.reactivex.CompletableObserver;
import p028io.reactivex.CompletableSource;
import p028io.reactivex.Maybe;
import p028io.reactivex.MaybeObserver;
import p028io.reactivex.MaybeSource;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeDelayWithCompletable */
public final class MaybeDelayWithCompletable<T> extends Maybe<T> {
    final CompletableSource other;
    final MaybeSource<T> source;

    public MaybeDelayWithCompletable(MaybeSource<T> maybeSource, CompletableSource completableSource) {
        this.source = maybeSource;
        this.other = completableSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.other.subscribe(new OtherObserver(maybeObserver, this.source));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeDelayWithCompletable$OtherObserver */
    static final class OtherObserver<T> extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
        private static final long serialVersionUID = 703409937383992161L;
        final MaybeObserver<? super T> downstream;
        final MaybeSource<T> source;

        OtherObserver(MaybeObserver<? super T> maybeObserver, MaybeSource<T> maybeSource) {
            this.downstream = maybeObserver;
            this.source = maybeSource;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.downstream.onSubscribe(this);
            }
        }

        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        public void onComplete() {
            this.source.subscribe(new DelayWithMainObserver(this, this.downstream));
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeDelayWithCompletable$DelayWithMainObserver */
    static final class DelayWithMainObserver<T> implements MaybeObserver<T> {
        final MaybeObserver<? super T> downstream;
        final AtomicReference<Disposable> parent;

        DelayWithMainObserver(AtomicReference<Disposable> atomicReference, MaybeObserver<? super T> maybeObserver) {
            this.parent = atomicReference;
            this.downstream = maybeObserver;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this.parent, disposable);
        }

        public void onSuccess(T t) {
            this.downstream.onSuccess(t);
        }

        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        public void onComplete() {
            this.downstream.onComplete();
        }
    }
}
