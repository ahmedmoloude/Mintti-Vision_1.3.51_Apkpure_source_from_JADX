package p028io.reactivex.internal.fuseable;

import p028io.reactivex.FlowableSubscriber;

/* renamed from: io.reactivex.internal.fuseable.ConditionalSubscriber */
public interface ConditionalSubscriber<T> extends FlowableSubscriber<T> {
    boolean tryOnNext(T t);
}
