package androidx.test.espresso.core.internal.deps.guava.util.concurrent;

import java.util.concurrent.ScheduledFuture;

public interface ListenableScheduledFuture<V> extends ScheduledFuture<V>, ListenableFuture<V> {
}
