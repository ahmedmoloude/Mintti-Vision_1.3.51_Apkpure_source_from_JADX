package p028io.reactivex.internal.subscribers;

/* renamed from: io.reactivex.internal.subscribers.BlockingLastSubscriber */
public final class BlockingLastSubscriber<T> extends BlockingBaseSubscriber<T> {
    public void onNext(T t) {
        this.value = t;
    }

    public void onError(Throwable th) {
        this.value = null;
        this.error = th;
        countDown();
    }
}
