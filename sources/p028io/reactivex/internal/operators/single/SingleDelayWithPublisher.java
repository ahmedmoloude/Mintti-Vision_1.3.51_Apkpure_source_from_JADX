package p028io.reactivex.internal.operators.single;

import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p028io.reactivex.FlowableSubscriber;
import p028io.reactivex.Single;
import p028io.reactivex.SingleObserver;
import p028io.reactivex.SingleSource;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.internal.disposables.DisposableHelper;
import p028io.reactivex.internal.observers.ResumeSingleObserver;
import p028io.reactivex.internal.subscriptions.SubscriptionHelper;
import p028io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.single.SingleDelayWithPublisher */
public final class SingleDelayWithPublisher<T, U> extends Single<T> {
    final Publisher<U> other;
    final SingleSource<T> source;

    public SingleDelayWithPublisher(SingleSource<T> singleSource, Publisher<U> publisher) {
        this.source = singleSource;
        this.other = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.other.subscribe(new OtherSubscriber(singleObserver, this.source));
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleDelayWithPublisher$OtherSubscriber */
    static final class OtherSubscriber<T, U> extends AtomicReference<Disposable> implements FlowableSubscriber<U>, Disposable {
        private static final long serialVersionUID = -8565274649390031272L;
        boolean done;
        final SingleObserver<? super T> downstream;
        final SingleSource<T> source;
        Subscription upstream;

        OtherSubscriber(SingleObserver<? super T> singleObserver, SingleSource<T> singleSource) {
            this.downstream = singleObserver;
            this.source = singleSource;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.downstream.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(U u) {
            this.upstream.cancel();
            onComplete();
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.downstream.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.source.subscribe(new ResumeSingleObserver(this, this.downstream));
            }
        }

        public void dispose() {
            this.upstream.cancel();
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }
    }
}
