package androidx.test.espresso.core.internal.deps.guava.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public interface ListeningExecutorService extends ExecutorService {
    <T> ListenableFuture<T> submit(Callable<T> callable);
}
