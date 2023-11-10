package p028io.reactivex.internal.operators.observable;

import p028io.reactivex.Observable;
import p028io.reactivex.ObservableSource;
import p028io.reactivex.internal.fuseable.HasUpstreamObservableSource;

/* renamed from: io.reactivex.internal.operators.observable.AbstractObservableWithUpstream */
abstract class AbstractObservableWithUpstream<T, U> extends Observable<U> implements HasUpstreamObservableSource<T> {
    protected final ObservableSource<T> source;

    AbstractObservableWithUpstream(ObservableSource<T> observableSource) {
        this.source = observableSource;
    }

    public final ObservableSource<T> source() {
        return this.source;
    }
}
