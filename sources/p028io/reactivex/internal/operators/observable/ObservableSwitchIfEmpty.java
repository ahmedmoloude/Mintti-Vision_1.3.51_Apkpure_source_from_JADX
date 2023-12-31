package p028io.reactivex.internal.operators.observable;

import p028io.reactivex.ObservableSource;
import p028io.reactivex.Observer;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.internal.disposables.SequentialDisposable;

/* renamed from: io.reactivex.internal.operators.observable.ObservableSwitchIfEmpty */
public final class ObservableSwitchIfEmpty<T> extends AbstractObservableWithUpstream<T, T> {
    final ObservableSource<? extends T> other;

    public ObservableSwitchIfEmpty(ObservableSource<T> observableSource, ObservableSource<? extends T> observableSource2) {
        super(observableSource);
        this.other = observableSource2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        SwitchIfEmptyObserver switchIfEmptyObserver = new SwitchIfEmptyObserver(observer, this.other);
        observer.onSubscribe(switchIfEmptyObserver.arbiter);
        this.source.subscribe(switchIfEmptyObserver);
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableSwitchIfEmpty$SwitchIfEmptyObserver */
    static final class SwitchIfEmptyObserver<T> implements Observer<T> {
        final SequentialDisposable arbiter = new SequentialDisposable();
        final Observer<? super T> downstream;
        boolean empty = true;
        final ObservableSource<? extends T> other;

        SwitchIfEmptyObserver(Observer<? super T> observer, ObservableSource<? extends T> observableSource) {
            this.downstream = observer;
            this.other = observableSource;
        }

        public void onSubscribe(Disposable disposable) {
            this.arbiter.update(disposable);
        }

        public void onNext(T t) {
            if (this.empty) {
                this.empty = false;
            }
            this.downstream.onNext(t);
        }

        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        public void onComplete() {
            if (this.empty) {
                this.empty = false;
                this.other.subscribe(this);
                return;
            }
            this.downstream.onComplete();
        }
    }
}
