package p028io.reactivex.internal.operators.completable;

import java.util.concurrent.atomic.AtomicReference;
import p028io.reactivex.Completable;
import p028io.reactivex.CompletableObserver;
import p028io.reactivex.CompletableSource;
import p028io.reactivex.disposables.Disposable;
import p028io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.completable.CompletableAndThenCompletable */
public final class CompletableAndThenCompletable extends Completable {
    final CompletableSource next;
    final CompletableSource source;

    public CompletableAndThenCompletable(CompletableSource completableSource, CompletableSource completableSource2) {
        this.source = completableSource;
        this.next = completableSource2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new SourceObserver(completableObserver, this.next));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableAndThenCompletable$SourceObserver */
    static final class SourceObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
        private static final long serialVersionUID = -4101678820158072998L;
        final CompletableObserver actualObserver;
        final CompletableSource next;

        SourceObserver(CompletableObserver completableObserver, CompletableSource completableSource) {
            this.actualObserver = completableObserver;
            this.next = completableSource;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.actualObserver.onSubscribe(this);
            }
        }

        public void onError(Throwable th) {
            this.actualObserver.onError(th);
        }

        public void onComplete() {
            this.next.subscribe(new NextObserver(this, this.actualObserver));
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableAndThenCompletable$NextObserver */
    static final class NextObserver implements CompletableObserver {
        final CompletableObserver downstream;
        final AtomicReference<Disposable> parent;

        public NextObserver(AtomicReference<Disposable> atomicReference, CompletableObserver completableObserver) {
            this.parent = atomicReference;
            this.downstream = completableObserver;
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.replace(this.parent, disposable);
        }

        public void onComplete() {
            this.downstream.onComplete();
        }

        public void onError(Throwable th) {
            this.downstream.onError(th);
        }
    }
}
