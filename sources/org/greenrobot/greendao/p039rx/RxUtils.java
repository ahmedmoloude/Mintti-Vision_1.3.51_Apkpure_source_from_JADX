package org.greenrobot.greendao.p039rx;

import java.util.concurrent.Callable;
import rx.Observable;
import rx.functions.Func0;

/* renamed from: org.greenrobot.greendao.rx.RxUtils */
class RxUtils {
    RxUtils() {
    }

    static <T> Observable<T> fromCallable(final Callable<T> callable) {
        return Observable.defer(new Func0<Observable<T>>() {
            public Observable<T> call() {
                try {
                    return Observable.just(callable.call());
                } catch (Exception e) {
                    return Observable.error(e);
                }
            }
        });
    }
}
