package p028io.reactivex.internal.operators.single;

import p028io.reactivex.Single;
import p028io.reactivex.SingleObserver;
import p028io.reactivex.SingleSource;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleDetach */
public final class SingleDetach<T> extends Single<T> {
    final SingleSource<T> source;

    public SingleDetach(SingleSource<T> singleSource) {
        this.source = singleSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe(new DetachSingleObserver(singleObserver));
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleDetach$DetachSingleObserver */
    static final class DetachSingleObserver<T> implements SingleObserver<T>, Disposable {
        SingleObserver<? super T> downstream;
        Disposable upstream;

        DetachSingleObserver(SingleObserver<? super T> singleObserver) {
            this.downstream = singleObserver;
        }

        public void dispose() {
            this.downstream = null;
            this.upstream.dispose();
            this.upstream = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.upstream = DisposableHelper.DISPOSED;
            SingleObserver<? super T> singleObserver = this.downstream;
            if (singleObserver != null) {
                this.downstream = null;
                singleObserver.onSuccess(t);
            }
        }

        public void onError(Throwable th) {
            this.upstream = DisposableHelper.DISPOSED;
            SingleObserver<? super T> singleObserver = this.downstream;
            if (singleObserver != null) {
                this.downstream = null;
                singleObserver.onError(th);
            }
        }
    }
}
