package p028io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicReference;
import p028io.reactivex.Observable;
import p028io.reactivex.ObservableSource;
import p028io.reactivex.Observer;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.exceptions.Exceptions;
import p028io.reactivex.functions.Function;
import p028io.reactivex.internal.disposables.DisposableHelper;
import p028io.reactivex.internal.disposables.EmptyDisposable;
import p028io.reactivex.internal.functions.ObjectHelper;
import p028io.reactivex.subjects.PublishSubject;

/* renamed from: io.reactivex.internal.operators.observable.ObservablePublishSelector */
public final class ObservablePublishSelector<T, R> extends AbstractObservableWithUpstream<T, R> {
    final Function<? super Observable<T>, ? extends ObservableSource<R>> selector;

    public ObservablePublishSelector(ObservableSource<T> observableSource, Function<? super Observable<T>, ? extends ObservableSource<R>> function) {
        super(observableSource);
        this.selector = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super R> observer) {
        PublishSubject create = PublishSubject.create();
        try {
            ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.selector.apply(create), "The selector returned a null ObservableSource");
            TargetObserver targetObserver = new TargetObserver(observer);
            observableSource.subscribe(targetObserver);
            this.source.subscribe(new SourceObserver(create, targetObserver));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, (Observer<?>) observer);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservablePublishSelector$SourceObserver */
    static final class SourceObserver<T, R> implements Observer<T> {
        final PublishSubject<T> subject;
        final AtomicReference<Disposable> target;

        SourceObserver(PublishSubject<T> publishSubject, AtomicReference<Disposable> atomicReference) {
            this.subject = publishSubject;
            this.target = atomicReference;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this.target, disposable);
        }

        public void onNext(T t) {
            this.subject.onNext(t);
        }

        public void onError(Throwable th) {
            this.subject.onError(th);
        }

        public void onComplete() {
            this.subject.onComplete();
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservablePublishSelector$TargetObserver */
    static final class TargetObserver<T, R> extends AtomicReference<Disposable> implements Observer<R>, Disposable {
        private static final long serialVersionUID = 854110278590336484L;
        final Observer<? super R> downstream;
        Disposable upstream;

        TargetObserver(Observer<? super R> observer) {
            this.downstream = observer;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.upstream, disposable)) {
                this.upstream = disposable;
                this.downstream.onSubscribe(this);
            }
        }

        public void onNext(R r) {
            this.downstream.onNext(r);
        }

        public void onError(Throwable th) {
            DisposableHelper.dispose(this);
            this.downstream.onError(th);
        }

        public void onComplete() {
            DisposableHelper.dispose(this);
            this.downstream.onComplete();
        }

        public void dispose() {
            this.upstream.dispose();
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }
    }
}
