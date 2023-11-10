package com.trello.rxlifecycle2;

import com.trello.rxlifecycle2.internal.Preconditions;
import javax.annotation.ParametersAreNonnullByDefault;
import org.reactivestreams.Publisher;
import p028io.reactivex.BackpressureStrategy;
import p028io.reactivex.Completable;
import p028io.reactivex.CompletableSource;
import p028io.reactivex.CompletableTransformer;
import p028io.reactivex.Flowable;
import p028io.reactivex.FlowableTransformer;
import p028io.reactivex.Maybe;
import p028io.reactivex.MaybeSource;
import p028io.reactivex.MaybeTransformer;
import p028io.reactivex.Observable;
import p028io.reactivex.ObservableSource;
import p028io.reactivex.ObservableTransformer;
import p028io.reactivex.Single;
import p028io.reactivex.SingleSource;
import p028io.reactivex.SingleTransformer;

@ParametersAreNonnullByDefault
public final class LifecycleTransformer<T> implements ObservableTransformer<T, T>, FlowableTransformer<T, T>, SingleTransformer<T, T>, MaybeTransformer<T, T>, CompletableTransformer {
    final Observable<?> observable;

    LifecycleTransformer(Observable<?> observable2) {
        Preconditions.checkNotNull(observable2, "observable == null");
        this.observable = observable2;
    }

    public ObservableSource<T> apply(Observable<T> observable2) {
        return observable2.takeUntil((ObservableSource<U>) this.observable);
    }

    public Publisher<T> apply(Flowable<T> flowable) {
        return flowable.takeUntil((Publisher<U>) this.observable.toFlowable(BackpressureStrategy.LATEST));
    }

    public SingleSource<T> apply(Single<T> single) {
        return single.takeUntil((SingleSource<? extends E>) this.observable.firstOrError());
    }

    public MaybeSource<T> apply(Maybe<T> maybe) {
        return maybe.takeUntil((MaybeSource<U>) this.observable.firstElement());
    }

    public CompletableSource apply(Completable completable) {
        return Completable.ambArray(completable, this.observable.flatMapCompletable(Functions.CANCEL_COMPLETABLE));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.observable.equals(((LifecycleTransformer) obj).observable);
    }

    public int hashCode() {
        return this.observable.hashCode();
    }

    public String toString() {
        return "LifecycleTransformer{observable=" + this.observable + '}';
    }
}
