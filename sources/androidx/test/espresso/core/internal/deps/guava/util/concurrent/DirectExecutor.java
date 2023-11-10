package androidx.test.espresso.core.internal.deps.guava.util.concurrent;

import java.util.concurrent.Executor;

enum DirectExecutor implements Executor {
    INSTANCE;

    public void execute(Runnable runnable) {
        runnable.run();
    }

    public String toString() {
        return "MoreExecutors.directExecutor()";
    }
}
