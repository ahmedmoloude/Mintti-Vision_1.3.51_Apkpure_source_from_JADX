package p028io.reactivex.internal.operators.observable;

import java.util.ArrayDeque;
import p028io.reactivex.ObservableSource;
import p028io.reactivex.Observer;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableSkipLast */
public final class ObservableSkipLast<T> extends AbstractObservableWithUpstream<T, T> {
    final int skip;

    public ObservableSkipLast(ObservableSource<T> observableSource, int i) {
        super(observableSource);
        this.skip = i;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new SkipLastObserver(observer, this.skip));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableSkipLast$SkipLastObserver */
    static final class SkipLastObserver<T> extends ArrayDeque<T> implements Observer<T>, Disposable {
        private static final long serialVersionUID = -3807491841935125653L;
        final Observer<? super T> downstream;
        final int skip;
        Disposable upstream;

        SkipLastObserver(Observer<? super T> observer, int i) {
            super(i);
            this.downstream = observer;
            this.skip = i;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        public void dispose() {
            this.upstream.dispose();
        }

        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        public void onNext(T t) {
            if (this.skip == size()) {
                this.downstream.onNext(poll());
            }
            offer(t);
        }

        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        public void onComplete() {
            this.downstream.onComplete();
        }
    }
}
