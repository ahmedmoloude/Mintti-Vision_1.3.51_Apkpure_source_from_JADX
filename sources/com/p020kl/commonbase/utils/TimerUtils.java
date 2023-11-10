package com.p020kl.commonbase.utils;

import java.util.concurrent.TimeUnit;
import p028io.reactivex.Observable;
import p028io.reactivex.android.schedulers.AndroidSchedulers;
import p028io.reactivex.functions.Function;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: com.kl.commonbase.utils.TimerUtils */
public class TimerUtils {
    public static Observable<Long> countDown(final int i) {
        return Observable.interval(0, 1, TimeUnit.SECONDS).take((long) (i + 1)).map(new Function<Long, Long>() {
            public Long apply(Long l) throws Exception {
                return Long.valueOf(((long) i) - l.longValue());
            }
        }).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Long> delayed(long j, TimeUnit timeUnit) {
        return Observable.timer(j, timeUnit).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Long> intervalTime(long j, TimeUnit timeUnit) {
        return Observable.interval(j, timeUnit).subscribeOn(Schedulers.m1081io()).observeOn(AndroidSchedulers.mainThread());
    }
}
