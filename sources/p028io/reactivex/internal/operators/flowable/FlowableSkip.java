package p028io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p028io.reactivex.Flowable;
import p028io.reactivex.FlowableSubscriber;
import p028io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableSkip */
public final class FlowableSkip<T> extends AbstractFlowableWithUpstream<T, T> {

    /* renamed from: n */
    final long f2058n;

    public FlowableSkip(Flowable<T> flowable, long j) {
        super(flowable);
        this.f2058n = j;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.source.subscribe(new SkipSubscriber(subscriber, this.f2058n));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableSkip$SkipSubscriber */
    static final class SkipSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> downstream;
        long remaining;
        Subscription upstream;

        SkipSubscriber(Subscriber<? super T> subscriber, long j) {
            this.downstream = subscriber;
            this.remaining = j;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                long j = this.remaining;
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(j);
            }
        }

        public void onNext(T t) {
            long j = this.remaining;
            if (j != 0) {
                this.remaining = j - 1;
            } else {
                this.downstream.onNext(t);
            }
        }

        public void onError(Throwable th) {
            this.downstream.onError(th);
        }

        public void onComplete() {
            this.downstream.onComplete();
        }

        public void request(long j) {
            this.upstream.request(j);
        }

        public void cancel() {
            this.upstream.cancel();
        }
    }
}
