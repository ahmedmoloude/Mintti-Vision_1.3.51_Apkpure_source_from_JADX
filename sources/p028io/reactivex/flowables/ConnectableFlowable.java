package p028io.reactivex.flowables;

import java.util.concurrent.TimeUnit;
import p028io.reactivex.Flowable;
import p028io.reactivex.Scheduler;
import p028io.reactivex.annotations.BackpressureKind;
import p028io.reactivex.annotations.BackpressureSupport;
import p028io.reactivex.annotations.CheckReturnValue;
import p028io.reactivex.annotations.SchedulerSupport;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.internal.functions.Functions;
import p028io.reactivex.internal.functions.ObjectHelper;
import p028io.reactivex.internal.operators.flowable.FlowableAutoConnect;
import p028io.reactivex.internal.operators.flowable.FlowableRefCount;
import p028io.reactivex.internal.util.ConnectConsumer;
import p028io.reactivex.plugins.RxJavaPlugins;
import p028io.reactivex.schedulers.Schedulers;

/* renamed from: io.reactivex.flowables.ConnectableFlowable */
public abstract class ConnectableFlowable<T> extends Flowable<T> {
    public abstract void connect(Consumer<? super Disposable> consumer);

    public final Disposable connect() {
        ConnectConsumer connectConsumer = new ConnectConsumer();
        connect(connectConsumer);
        return connectConsumer.disposable;
    }

    @CheckReturnValue
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public Flowable<T> refCount() {
        return RxJavaPlugins.onAssembly(new FlowableRefCount(this));
    }

    @CheckReturnValue
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("none")
    public final Flowable<T> refCount(int i) {
        return refCount(i, 0, TimeUnit.NANOSECONDS, Schedulers.trampoline());
    }

    @CheckReturnValue
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> refCount(long j, TimeUnit timeUnit) {
        return refCount(1, j, timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("custom")
    public final Flowable<T> refCount(long j, TimeUnit timeUnit, Scheduler scheduler) {
        return refCount(1, j, timeUnit, scheduler);
    }

    @CheckReturnValue
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("io.reactivex:computation")
    public final Flowable<T> refCount(int i, long j, TimeUnit timeUnit) {
        return refCount(i, j, timeUnit, Schedulers.computation());
    }

    @CheckReturnValue
    @BackpressureSupport(BackpressureKind.PASS_THROUGH)
    @SchedulerSupport("custom")
    public final Flowable<T> refCount(int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
        ObjectHelper.verifyPositive(i, "subscriberCount");
        ObjectHelper.requireNonNull(timeUnit, "unit is null");
        ObjectHelper.requireNonNull(scheduler, "scheduler is null");
        return RxJavaPlugins.onAssembly(new FlowableRefCount(this, i, j, timeUnit, scheduler));
    }

    public Flowable<T> autoConnect() {
        return autoConnect(1);
    }

    public Flowable<T> autoConnect(int i) {
        return autoConnect(i, Functions.emptyConsumer());
    }

    public Flowable<T> autoConnect(int i, Consumer<? super Disposable> consumer) {
        if (i > 0) {
            return RxJavaPlugins.onAssembly(new FlowableAutoConnect(this, i, consumer));
        }
        connect(consumer);
        return RxJavaPlugins.onAssembly(this);
    }
}
