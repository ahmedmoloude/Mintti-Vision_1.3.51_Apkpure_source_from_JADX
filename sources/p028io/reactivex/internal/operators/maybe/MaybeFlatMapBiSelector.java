package p028io.reactivex.internal.operators.maybe;

import java.util.concurrent.atomic.AtomicReference;
import p028io.reactivex.MaybeObserver;
import p028io.reactivex.MaybeSource;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.exceptions.Exceptions;
import p028io.reactivex.functions.BiFunction;
import p028io.reactivex.functions.Function;
import p028io.reactivex.internal.disposables.DisposableHelper;
import p028io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeFlatMapBiSelector */
public final class MaybeFlatMapBiSelector<T, U, R> extends AbstractMaybeWithUpstream<T, R> {
    final Function<? super T, ? extends MaybeSource<? extends U>> mapper;
    final BiFunction<? super T, ? super U, ? extends R> resultSelector;

    public MaybeFlatMapBiSelector(MaybeSource<T> maybeSource, Function<? super T, ? extends MaybeSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        super(maybeSource);
        this.mapper = function;
        this.resultSelector = biFunction;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        this.source.subscribe(new FlatMapBiMainObserver(maybeObserver, this.mapper, this.resultSelector));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeFlatMapBiSelector$FlatMapBiMainObserver */
    static final class FlatMapBiMainObserver<T, U, R> implements MaybeObserver<T>, Disposable {
        final InnerObserver<T, U, R> inner;
        final Function<? super T, ? extends MaybeSource<? extends U>> mapper;

        FlatMapBiMainObserver(MaybeObserver<? super R> maybeObserver, Function<? super T, ? extends MaybeSource<? extends U>> function, BiFunction<? super T, ? super U, ? extends R> biFunction) {
            this.inner = new InnerObserver<>(maybeObserver, biFunction);
            this.mapper = function;
        }

        public void dispose() {
            DisposableHelper.dispose(this.inner);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.inner.get());
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this.inner, disposable)) {
                this.inner.downstream.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            try {
                MaybeSource maybeSource = (MaybeSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null MaybeSource");
                if (DisposableHelper.replace(this.inner, (Disposable) null)) {
                    this.inner.value = t;
                    maybeSource.subscribe(this.inner);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.inner.downstream.onError(th);
            }
        }

        public void onError(Throwable th) {
            this.inner.downstream.onError(th);
        }

        public void onComplete() {
            this.inner.downstream.onComplete();
        }

        /* renamed from: io.reactivex.internal.operators.maybe.MaybeFlatMapBiSelector$FlatMapBiMainObserver$InnerObserver */
        static final class InnerObserver<T, U, R> extends AtomicReference<Disposable> implements MaybeObserver<U> {
            private static final long serialVersionUID = -2897979525538174559L;
            final MaybeObserver<? super R> downstream;
            final BiFunction<? super T, ? super U, ? extends R> resultSelector;
            T value;

            InnerObserver(MaybeObserver<? super R> maybeObserver, BiFunction<? super T, ? super U, ? extends R> biFunction) {
                this.downstream = maybeObserver;
                this.resultSelector = biFunction;
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            public void onSuccess(U u) {
                T t = this.value;
                this.value = null;
                try {
                    this.downstream.onSuccess(ObjectHelper.requireNonNull(this.resultSelector.apply(t, u), "The resultSelector returned a null value"));
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                }
            }

            public void onError(Throwable th) {
                this.downstream.onError(th);
            }

            public void onComplete() {
                this.downstream.onComplete();
            }
        }
    }
}
