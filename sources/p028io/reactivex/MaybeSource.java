package p028io.reactivex;

/* renamed from: io.reactivex.MaybeSource */
public interface MaybeSource<T> {
    void subscribe(MaybeObserver<? super T> maybeObserver);
}
