package androidx.test.espresso.core.internal.deps.guava.util.concurrent;

import androidx.test.espresso.core.internal.deps.guava.util.concurrent.AbstractFuture;

public final class SettableFuture<V> extends AbstractFuture.TrustedFuture<V> {
    private SettableFuture() {
    }

    public static <V> SettableFuture<V> create() {
        return new SettableFuture<>();
    }

    public boolean set(V v) {
        return super.set(v);
    }

    public boolean setException(Throwable th) {
        return super.setException(th);
    }
}
