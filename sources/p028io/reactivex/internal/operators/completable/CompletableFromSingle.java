package p028io.reactivex.internal.operators.completable;

import p028io.reactivex.Completable;
import p028io.reactivex.CompletableObserver;
import p028io.reactivex.SingleObserver;
import p028io.reactivex.SingleSource;
import p028io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.completable.CompletableFromSingle */
public final class CompletableFromSingle<T> extends Completable {
    final SingleSource<T> single;

    public CompletableFromSingle(SingleSource<T> singleSource) {
        this.single = singleSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.single.subscribe(new CompletableFromSingleObserver(completableObserver));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableFromSingle$CompletableFromSingleObserver */
    static final class CompletableFromSingleObserver<T> implements SingleObserver<T> {

        /* renamed from: co */
        final CompletableObserver f2034co;

        CompletableFromSingleObserver(CompletableObserver completableObserver) {
            this.f2034co = completableObserver;
        }

        public void onError(Throwable th) {
            this.f2034co.onError(th);
        }

        public void onSubscribe(Disposable disposable) {
            this.f2034co.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            this.f2034co.onComplete();
        }
    }
}
