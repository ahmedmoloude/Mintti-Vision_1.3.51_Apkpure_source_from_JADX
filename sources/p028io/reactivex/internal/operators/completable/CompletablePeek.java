package p028io.reactivex.internal.operators.completable;

import p028io.reactivex.Completable;
import p028io.reactivex.CompletableObserver;
import p028io.reactivex.CompletableSource;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.exceptions.CompositeException;
import p028io.reactivex.exceptions.Exceptions;
import p028io.reactivex.functions.Action;
import p028io.reactivex.functions.Consumer;
import p028io.reactivex.internal.disposables.DisposableHelper;
import p028io.reactivex.internal.disposables.EmptyDisposable;
import p028io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletablePeek */
public final class CompletablePeek extends Completable {
    final Action onAfterTerminate;
    final Action onComplete;
    final Action onDispose;
    final Consumer<? super Throwable> onError;
    final Consumer<? super Disposable> onSubscribe;
    final Action onTerminate;
    final CompletableSource source;

    public CompletablePeek(CompletableSource completableSource, Consumer<? super Disposable> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2, Action action3, Action action4) {
        this.source = completableSource;
        this.onSubscribe = consumer;
        this.onError = consumer2;
        this.onComplete = action;
        this.onTerminate = action2;
        this.onAfterTerminate = action3;
        this.onDispose = action4;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new CompletableObserverImplementation(completableObserver));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletablePeek$CompletableObserverImplementation */
    final class CompletableObserverImplementation implements CompletableObserver, Disposable {
        final CompletableObserver downstream;
        Disposable upstream;

        CompletableObserverImplementation(CompletableObserver completableObserver) {
            this.downstream = completableObserver;
        }

        public void onSubscribe(Disposable disposable) {
            try {
                CompletablePeek.this.onSubscribe.accept(disposable);
                if (DisposableHelper.validate(this.upstream, disposable)) {
                    this.upstream = disposable;
                    this.downstream.onSubscribe(this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                disposable.dispose();
                this.upstream = DisposableHelper.DISPOSED;
                EmptyDisposable.error(th, this.downstream);
            }
        }

        public void onError(Throwable th) {
            if (this.upstream == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
                return;
            }
            try {
                CompletablePeek.this.onError.accept(th);
                CompletablePeek.this.onTerminate.run();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.downstream.onError(th);
            doAfter();
        }

        public void onComplete() {
            if (this.upstream != DisposableHelper.DISPOSED) {
                try {
                    CompletablePeek.this.onComplete.run();
                    CompletablePeek.this.onTerminate.run();
                    this.downstream.onComplete();
                    doAfter();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.downstream.onError(th);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void doAfter() {
            try {
                CompletablePeek.this.onAfterTerminate.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }

        public void dispose() {
            try {
                CompletablePeek.this.onDispose.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.upstream.dispose();
        }

        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }
    }
}
