package p028io.reactivex.internal.operators.maybe;

import p028io.reactivex.Maybe;
import p028io.reactivex.MaybeObserver;
import p028io.reactivex.internal.disposables.EmptyDisposable;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeNever */
public final class MaybeNever extends Maybe<Object> {
    public static final MaybeNever INSTANCE = new MaybeNever();

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super Object> maybeObserver) {
        maybeObserver.onSubscribe(EmptyDisposable.NEVER);
    }
}
