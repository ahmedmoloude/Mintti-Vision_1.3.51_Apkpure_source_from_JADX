package p028io.reactivex.internal.subscribers;

import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;
import p028io.reactivex.FlowableSubscriber;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.exceptions.CompositeException;
import p028io.reactivex.exceptions.Exceptions;
import p028io.reactivex.functions.Action;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.internal.functions.Functions;
import p028io.reactivex.internal.subscriptions.SubscriptionHelper;
import p028io.reactivex.observers.LambdaConsumerIntrospection;
import p028io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.subscribers.BoundedSubscriber */
public final class BoundedSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription, Disposable, LambdaConsumerIntrospection {
    private static final long serialVersionUID = -7251123623727029452L;
    final int bufferSize;
    int consumed;
    final int limit;
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;
    final Consumer<? super Subscription> onSubscribe;

    public BoundedSubscriber(Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action, Consumer<? super Subscription> consumer3, int i) {
        this.onNext = consumer;
        this.onError = consumer2;
        this.onComplete = action;
        this.onSubscribe = consumer3;
        this.bufferSize = i;
        this.limit = i - (i >> 2);
    }

    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.setOnce(this, subscription)) {
            try {
                this.onSubscribe.accept(this);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                subscription.cancel();
                onError(th);
            }
        }
    }

    public void onNext(T t) {
        if (!isDisposed()) {
            try {
                this.onNext.accept(t);
                int i = this.consumed + 1;
                if (i == this.limit) {
                    this.consumed = 0;
                    ((Subscription) get()).request((long) this.limit);
                    return;
                }
                this.consumed = i;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                ((Subscription) get()).cancel();
                onError(th);
            }
        }
    }

    public void onError(Throwable th) {
        if (get() != SubscriptionHelper.CANCELLED) {
            lazySet(SubscriptionHelper.CANCELLED);
            try {
                this.onError.accept(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                RxJavaPlugins.onError(new CompositeException(th, th2));
            }
        } else {
            RxJavaPlugins.onError(th);
        }
    }

    public void onComplete() {
        if (get() != SubscriptionHelper.CANCELLED) {
            lazySet(SubscriptionHelper.CANCELLED);
            try {
                this.onComplete.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }

    public void dispose() {
        cancel();
    }

    public boolean isDisposed() {
        return get() == SubscriptionHelper.CANCELLED;
    }

    public void request(long j) {
        ((Subscription) get()).request(j);
    }

    public void cancel() {
        SubscriptionHelper.cancel(this);
    }

    public boolean hasCustomOnError() {
        return this.onError != Functions.ON_ERROR_MISSING;
    }
}
