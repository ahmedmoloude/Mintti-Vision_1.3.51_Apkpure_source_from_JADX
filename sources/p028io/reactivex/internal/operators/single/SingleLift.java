package p028io.reactivex.internal.operators.single;

import p028io.reactivex.Single;
import p028io.reactivex.SingleObserver;
import p028io.reactivex.SingleOperator;
import p028io.reactivex.SingleSource;
import p028io.reactivex.exceptions.Exceptions;
import p028io.reactivex.internal.disposables.EmptyDisposable;
import p028io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleLift */
public final class SingleLift<T, R> extends Single<R> {
    final SingleOperator<? extends R, ? super T> onLift;
    final SingleSource<T> source;

    public SingleLift(SingleSource<T> singleSource, SingleOperator<? extends R, ? super T> singleOperator) {
        this.source = singleSource;
        this.onLift = singleOperator;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        try {
            this.source.subscribe((SingleObserver) ObjectHelper.requireNonNull(this.onLift.apply(singleObserver), "The onLift returned a null SingleObserver"));
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, (SingleObserver<?>) singleObserver);
        }
    }
}
