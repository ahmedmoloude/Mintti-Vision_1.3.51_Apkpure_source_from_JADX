package p028io.reactivex.observers;

import java.util.concurrent.atomic.AtomicReference;
import p028io.reactivex.CompletableObserver;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.internal.disposables.DisposableHelper;
import p028io.reactivex.internal.util.EndConsumerHelper;

/* renamed from: io.reactivex.observers.DisposableCompletableObserver */
public abstract class DisposableCompletableObserver implements CompletableObserver, Disposable {
    final AtomicReference<Disposable> upstream = new AtomicReference<>();

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    public final void onSubscribe(Disposable disposable) {
        if (EndConsumerHelper.setOnce(this.upstream, disposable, getClass())) {
            onStart();
        }
    }

    public final boolean isDisposed() {
        return this.upstream.get() == DisposableHelper.DISPOSED;
    }

    public final void dispose() {
        DisposableHelper.dispose(this.upstream);
    }
}
