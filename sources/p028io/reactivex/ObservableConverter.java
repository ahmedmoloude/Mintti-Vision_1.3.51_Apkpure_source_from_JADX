package p028io.reactivex;

/* renamed from: io.reactivex.ObservableConverter */
public interface ObservableConverter<T, R> {
    R apply(Observable<T> observable);
}
