package p028io.reactivex;

/* renamed from: io.reactivex.MaybeOnSubscribe */
public interface MaybeOnSubscribe<T> {
    void subscribe(MaybeEmitter<T> maybeEmitter) throws Exception;
}
