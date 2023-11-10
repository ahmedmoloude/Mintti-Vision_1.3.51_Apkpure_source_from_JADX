package p028io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import p028io.reactivex.Flowable;
import p028io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableSerialized */
public final class FlowableSerialized<T> extends AbstractFlowableWithUpstream<T, T> {
    public FlowableSerialized(Flowable<T> flowable) {
        super(flowable);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new SerializedSubscriber(subscriber));
    }
}
