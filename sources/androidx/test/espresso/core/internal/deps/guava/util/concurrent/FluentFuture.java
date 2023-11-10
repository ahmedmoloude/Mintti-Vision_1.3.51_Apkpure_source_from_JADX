package androidx.test.espresso.core.internal.deps.guava.util.concurrent;

import androidx.test.espresso.core.internal.deps.guava.util.concurrent.AbstractFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class FluentFuture<V> extends GwtFluentFutureCatchingSpecialization<V> {

    static abstract class TrustedFuture<V> extends FluentFuture<V> implements AbstractFuture.Trusted<V> {
        TrustedFuture() {
        }

        public final void addListener(Runnable runnable, Executor executor) {
            FluentFuture.super.addListener(runnable, executor);
        }

        public final boolean cancel(boolean z) {
            return FluentFuture.super.cancel(z);
        }

        public final V get() throws InterruptedException, ExecutionException {
            return FluentFuture.super.get();
        }

        public final boolean isCancelled() {
            return FluentFuture.super.isCancelled();
        }

        public final boolean isDone() {
            return FluentFuture.super.isDone();
        }

        public final V get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            return FluentFuture.super.get(j, timeUnit);
        }
    }

    FluentFuture() {
    }
}
