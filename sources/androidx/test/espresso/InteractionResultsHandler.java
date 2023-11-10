package androidx.test.espresso;

import androidx.test.espresso.core.internal.deps.guava.base.MoreObjects;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListenableFuture;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.MoreExecutors;
import androidx.test.espresso.remote.NoRemoteEspressoInstanceException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

final class InteractionResultsHandler {
    private static final int LOCAL_OR_REMOTE_ERROR_PRIORITY = Integer.MAX_VALUE;
    private static final String TAG = "InteractionResultsHandl";

    private static class ExecutionResult<T> {
        private final Throwable failure;
        private final boolean priority;
        private final T result;
        private final boolean success;

        private ExecutionResult(T t, boolean z, Throwable th, boolean z2) {
            this.result = t;
            this.success = z;
            this.failure = th;
            this.priority = z2;
        }

        public static <T> ExecutionResult<T> error(Throwable th) {
            return error(th, false);
        }

        public static <T> ExecutionResult<T> success(T t) {
            return new ExecutionResult<>(t, true, (Throwable) null, true);
        }

        public Throwable getFailure() {
            Preconditions.checkState(!this.success);
            return this.failure;
        }

        public T getResult() {
            Preconditions.checkState(this.success);
            return this.result;
        }

        public boolean isPriority() {
            return this.priority;
        }

        public boolean isSuccess() {
            return this.success;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).omitNullValues().add("priority", this.priority).add("success", this.success).add("result", (Object) this.result).add("failure", (Object) this.failure).toString();
        }

        public static <T> ExecutionResult<T> error(Throwable th, boolean z) {
            return new ExecutionResult<>((Object) null, false, th, z);
        }
    }

    private InteractionResultsHandler() {
    }

    /* access modifiers changed from: private */
    public static <T> ExecutionResult<T> adaptResult(Future<T> future) {
        try {
            Preconditions.checkState(future.isDone());
            return ExecutionResult.success(future.get());
        } catch (ExecutionException e) {
            return ExecutionResult.error(e, getPriority(e) == Integer.MAX_VALUE);
        } catch (InterruptedException e2) {
            return ExecutionResult.error(e2);
        } catch (RuntimeException e3) {
            return ExecutionResult.error(e3);
        } catch (Error e4) {
            return ExecutionResult.error(e4);
        }
    }

    private static <T> T finalResult(ExecutionResult<T> executionResult) {
        if (executionResult.isSuccess()) {
            return executionResult.getResult();
        }
        Throwable failure = executionResult.getFailure();
        if (failure instanceof ExecutionException) {
            Throwable cause = failure.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unknown error during interactions", executionResult.getFailure());
            }
        } else if (failure instanceof InterruptedException) {
            throw new IllegalStateException("Interrupted while interacting remotely", failure);
        } else {
            throw new RuntimeException("Error interacting remotely", failure);
        }
    }

    static <T> T gatherAnyResult(List<ListenableFuture<T>> list) {
        return gatherAnyResult(list, MoreExecutors.directExecutor());
    }

    static <T> T gatherAnyResult(List<ListenableFuture<T>> list, Executor executor) {
        Preconditions.checkNotNull(list);
        Preconditions.checkState(!list.isEmpty());
        int size = list.size();
        final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(size);
        for (final ListenableFuture next : list) {
            next.addListener(new Runnable() {
                public void run() {
                    if (!ListenableFuture.this.isCancelled()) {
                        linkedBlockingQueue.offer(InteractionResultsHandler.adaptResult(ListenableFuture.this));
                    }
                }
            }, executor);
        }
        ExecutionResult executionResult = null;
        while (true) {
            if (size == 0) {
                break;
            }
            if (executionResult != null) {
                try {
                    if (executionResult.isPriority()) {
                        break;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupted while interacting", e);
                } catch (Throwable th) {
                    for (ListenableFuture<T> cancel : list) {
                        cancel.cancel(true);
                    }
                    throw th;
                }
            }
            size--;
            executionResult = pickResult(executionResult, (ExecutionResult) linkedBlockingQueue.take());
        }
        T finalResult = finalResult(executionResult);
        for (ListenableFuture<T> cancel2 : list) {
            cancel2.cancel(true);
        }
        return finalResult;
    }

    private static int getPriority(Throwable th) {
        if (th == null) {
            return Integer.MIN_VALUE;
        }
        if (!(th instanceof ExecutionException)) {
            return -2147483647;
        }
        if (th.getCause() instanceof NoRemoteEspressoInstanceException) {
            return 0;
        }
        return th.getCause() instanceof NoActivityResumedException ? 1 : Integer.MAX_VALUE;
    }

    private static <T> ExecutionResult<T> pickResult(ExecutionResult<T> executionResult, ExecutionResult<T> executionResult2) {
        if (executionResult2 == null) {
            return executionResult;
        }
        if (executionResult == null) {
            return executionResult2;
        }
        if (executionResult.isSuccess()) {
            return executionResult;
        }
        return (!executionResult2.isSuccess() && getPriority(executionResult.getFailure()) > getPriority(executionResult2.getFailure())) ? executionResult : executionResult2;
    }
}
