package p028io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import p028io.reactivex.Flowable;
import p028io.reactivex.internal.fuseable.ConditionalSubscriber;
import p028io.reactivex.internal.subscriptions.BasicQueueSubscription;
import p028io.reactivex.internal.subscriptions.SubscriptionHelper;
import p028io.reactivex.internal.util.BackpressureHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableRange */
public final class FlowableRange extends Flowable<Integer> {
    final int end;
    final int start;

    public FlowableRange(int i, int i2) {
        this.start = i;
        this.end = i + i2;
    }

    public void subscribeActual(Subscriber<? super Integer> subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            subscriber.onSubscribe(new RangeConditionalSubscription((ConditionalSubscriber) subscriber, this.start, this.end));
        } else {
            subscriber.onSubscribe(new RangeSubscription(subscriber, this.start, this.end));
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableRange$BaseRangeSubscription */
    static abstract class BaseRangeSubscription extends BasicQueueSubscription<Integer> {
        private static final long serialVersionUID = -2252972430506210021L;
        volatile boolean cancelled;
        final int end;
        int index;

        /* access modifiers changed from: package-private */
        public abstract void fastPath();

        public final int requestFusion(int i) {
            return i & 1;
        }

        /* access modifiers changed from: package-private */
        public abstract void slowPath(long j);

        BaseRangeSubscription(int i, int i2) {
            this.index = i;
            this.end = i2;
        }

        public final Integer poll() {
            int i = this.index;
            if (i == this.end) {
                return null;
            }
            this.index = i + 1;
            return Integer.valueOf(i);
        }

        public final boolean isEmpty() {
            return this.index == this.end;
        }

        public final void clear() {
            this.index = this.end;
        }

        public final void request(long j) {
            if (SubscriptionHelper.validate(j) && BackpressureHelper.add(this, j) == 0) {
                if (j == Long.MAX_VALUE) {
                    fastPath();
                } else {
                    slowPath(j);
                }
            }
        }

        public final void cancel() {
            this.cancelled = true;
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableRange$RangeSubscription */
    static final class RangeSubscription extends BaseRangeSubscription {
        private static final long serialVersionUID = 2587302975077663557L;
        final Subscriber<? super Integer> downstream;

        RangeSubscription(Subscriber<? super Integer> subscriber, int i, int i2) {
            super(i, i2);
            this.downstream = subscriber;
        }

        /* access modifiers changed from: package-private */
        public void fastPath() {
            int i = this.end;
            Subscriber<? super Integer> subscriber = this.downstream;
            int i2 = this.index;
            while (i2 != i) {
                if (!this.cancelled) {
                    subscriber.onNext(Integer.valueOf(i2));
                    i2++;
                } else {
                    return;
                }
            }
            if (!this.cancelled) {
                subscriber.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        public void slowPath(long j) {
            int i = this.end;
            int i2 = this.index;
            Subscriber<? super Integer> subscriber = this.downstream;
            do {
                long j2 = 0;
                while (true) {
                    if (j2 == j || i2 == i) {
                        if (i2 != i) {
                            j = get();
                            if (j2 == j) {
                                this.index = i2;
                                j = addAndGet(-j2);
                            }
                        } else if (!this.cancelled) {
                            subscriber.onComplete();
                            return;
                        } else {
                            return;
                        }
                    } else if (!this.cancelled) {
                        subscriber.onNext(Integer.valueOf(i2));
                        j2++;
                        i2++;
                    } else {
                        return;
                    }
                }
            } while (j != 0);
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableRange$RangeConditionalSubscription */
    static final class RangeConditionalSubscription extends BaseRangeSubscription {
        private static final long serialVersionUID = 2587302975077663557L;
        final ConditionalSubscriber<? super Integer> downstream;

        RangeConditionalSubscription(ConditionalSubscriber<? super Integer> conditionalSubscriber, int i, int i2) {
            super(i, i2);
            this.downstream = conditionalSubscriber;
        }

        /* access modifiers changed from: package-private */
        public void fastPath() {
            int i = this.end;
            ConditionalSubscriber<? super Integer> conditionalSubscriber = this.downstream;
            int i2 = this.index;
            while (i2 != i) {
                if (!this.cancelled) {
                    conditionalSubscriber.tryOnNext(Integer.valueOf(i2));
                    i2++;
                } else {
                    return;
                }
            }
            if (!this.cancelled) {
                conditionalSubscriber.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        public void slowPath(long j) {
            int i = this.end;
            int i2 = this.index;
            ConditionalSubscriber<? super Integer> conditionalSubscriber = this.downstream;
            do {
                long j2 = 0;
                while (true) {
                    if (j2 == j || i2 == i) {
                        if (i2 != i) {
                            j = get();
                            if (j2 == j) {
                                this.index = i2;
                                j = addAndGet(-j2);
                            }
                        } else if (!this.cancelled) {
                            conditionalSubscriber.onComplete();
                            return;
                        } else {
                            return;
                        }
                    } else if (!this.cancelled) {
                        if (conditionalSubscriber.tryOnNext(Integer.valueOf(i2))) {
                            j2++;
                        }
                        i2++;
                    } else {
                        return;
                    }
                }
            } while (j != 0);
        }
    }
}
