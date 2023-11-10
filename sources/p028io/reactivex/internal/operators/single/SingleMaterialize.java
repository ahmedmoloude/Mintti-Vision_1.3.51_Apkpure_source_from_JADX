package p028io.reactivex.internal.operators.single;

import p028io.reactivex.Notification;
import p028io.reactivex.Single;
import p028io.reactivex.SingleObserver;
import p028io.reactivex.internal.operators.mixed.MaterializeSingleObserver;

/* renamed from: io.reactivex.internal.operators.single.SingleMaterialize */
public final class SingleMaterialize<T> extends Single<Notification<T>> {
    final Single<T> source;

    public SingleMaterialize(Single<T> single) {
        this.source = single;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Notification<T>> singleObserver) {
        this.source.subscribe(new MaterializeSingleObserver(singleObserver));
    }
}
