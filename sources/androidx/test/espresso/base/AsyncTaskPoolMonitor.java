package androidx.test.espresso.base;

import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

class AsyncTaskPoolMonitor {
    /* access modifiers changed from: private */
    public final AtomicInteger activeBarrierChecks = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public final AtomicReference<IdleMonitor> monitor = new AtomicReference<>((Object) null);
    /* access modifiers changed from: private */
    public final ThreadPoolExecutor pool;

    private static class BarrierRestarter {
        private final CyclicBarrier barrier;
        private final AtomicInteger barrierGeneration;

        BarrierRestarter(CyclicBarrier cyclicBarrier, AtomicInteger atomicInteger) {
            this.barrier = cyclicBarrier;
            this.barrierGeneration = atomicInteger;
        }

        /* access modifiers changed from: package-private */
        public synchronized void restart(int i) {
            if (this.barrierGeneration.compareAndSet(i, i + 1)) {
                this.barrier.reset();
            }
        }
    }

    private class IdleMonitor {
        /* access modifiers changed from: private */
        public final CyclicBarrier barrier;
        /* access modifiers changed from: private */
        public final AtomicInteger barrierGeneration;
        private final Runnable onIdle;
        /* access modifiers changed from: private */
        public volatile boolean poisoned;

        private IdleMonitor(final Runnable runnable) {
            this.barrierGeneration = new AtomicInteger(0);
            this.onIdle = (Runnable) Preconditions.checkNotNull(runnable);
            this.barrier = new CyclicBarrier(AsyncTaskPoolMonitor.this.pool.getCorePoolSize(), new Runnable(AsyncTaskPoolMonitor.this) {
                public void run() {
                    if (AsyncTaskPoolMonitor.this.pool.getQueue().isEmpty()) {
                        AsyncTaskPoolMonitor.this.monitor.compareAndSet(IdleMonitor.this, (Object) null);
                        runnable.run();
                        return;
                    }
                    IdleMonitor.this.monitorForIdle();
                }
            });
        }

        /* access modifiers changed from: private */
        public void monitorForIdle() {
            if (!this.poisoned) {
                if (AsyncTaskPoolMonitor.this.isIdleNow()) {
                    AsyncTaskPoolMonitor.this.monitor.compareAndSet(this, (Object) null);
                    this.onIdle.run();
                    return;
                }
                int corePoolSize = AsyncTaskPoolMonitor.this.pool.getCorePoolSize();
                final BarrierRestarter barrierRestarter = new BarrierRestarter(this.barrier, this.barrierGeneration);
                for (int i = 0; i < corePoolSize; i++) {
                    AsyncTaskPoolMonitor.this.pool.execute(new Runnable() {
                        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0026, code lost:
                            androidx.test.espresso.base.AsyncTaskPoolMonitor.access$600(r2.this$1.this$0).decrementAndGet();
                         */
                        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0031, code lost:
                            return;
                         */
                        /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0034 */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public void run() {
                            /*
                                r2 = this;
                            L_0x0000:
                                androidx.test.espresso.base.AsyncTaskPoolMonitor$IdleMonitor r0 = androidx.test.espresso.base.AsyncTaskPoolMonitor.IdleMonitor.this
                                boolean r0 = r0.poisoned
                                if (r0 != 0) goto L_0x0057
                                androidx.test.espresso.base.AsyncTaskPoolMonitor$IdleMonitor r0 = androidx.test.espresso.base.AsyncTaskPoolMonitor.IdleMonitor.this
                                androidx.test.espresso.base.AsyncTaskPoolMonitor r0 = androidx.test.espresso.base.AsyncTaskPoolMonitor.this
                                java.util.concurrent.atomic.AtomicInteger r0 = r0.activeBarrierChecks
                                r0.incrementAndGet()
                                androidx.test.espresso.base.AsyncTaskPoolMonitor$IdleMonitor r0 = androidx.test.espresso.base.AsyncTaskPoolMonitor.IdleMonitor.this
                                java.util.concurrent.atomic.AtomicInteger r0 = r0.barrierGeneration
                                int r0 = r0.get()
                                androidx.test.espresso.base.AsyncTaskPoolMonitor$IdleMonitor r1 = androidx.test.espresso.base.AsyncTaskPoolMonitor.IdleMonitor.this     // Catch:{ InterruptedException -> 0x003a, BrokenBarrierException -> 0x0034 }
                                java.util.concurrent.CyclicBarrier r1 = r1.barrier     // Catch:{ InterruptedException -> 0x003a, BrokenBarrierException -> 0x0034 }
                                r1.await()     // Catch:{ InterruptedException -> 0x003a, BrokenBarrierException -> 0x0034 }
                                androidx.test.espresso.base.AsyncTaskPoolMonitor$IdleMonitor r0 = androidx.test.espresso.base.AsyncTaskPoolMonitor.IdleMonitor.this
                                androidx.test.espresso.base.AsyncTaskPoolMonitor r0 = androidx.test.espresso.base.AsyncTaskPoolMonitor.this
                                java.util.concurrent.atomic.AtomicInteger r0 = r0.activeBarrierChecks
                                r0.decrementAndGet()
                                return
                            L_0x0032:
                                r0 = move-exception
                                goto L_0x004b
                            L_0x0034:
                                androidx.test.espresso.base.AsyncTaskPoolMonitor$BarrierRestarter r1 = r1     // Catch:{ all -> 0x0032 }
                                r1.restart(r0)     // Catch:{ all -> 0x0032 }
                                goto L_0x003f
                            L_0x003a:
                                androidx.test.espresso.base.AsyncTaskPoolMonitor$BarrierRestarter r1 = r1     // Catch:{ all -> 0x0032 }
                                r1.restart(r0)     // Catch:{ all -> 0x0032 }
                            L_0x003f:
                                androidx.test.espresso.base.AsyncTaskPoolMonitor$IdleMonitor r0 = androidx.test.espresso.base.AsyncTaskPoolMonitor.IdleMonitor.this
                                androidx.test.espresso.base.AsyncTaskPoolMonitor r0 = androidx.test.espresso.base.AsyncTaskPoolMonitor.this
                                java.util.concurrent.atomic.AtomicInteger r0 = r0.activeBarrierChecks
                                r0.decrementAndGet()
                                goto L_0x0000
                            L_0x004b:
                                androidx.test.espresso.base.AsyncTaskPoolMonitor$IdleMonitor r1 = androidx.test.espresso.base.AsyncTaskPoolMonitor.IdleMonitor.this
                                androidx.test.espresso.base.AsyncTaskPoolMonitor r1 = androidx.test.espresso.base.AsyncTaskPoolMonitor.this
                                java.util.concurrent.atomic.AtomicInteger r1 = r1.activeBarrierChecks
                                r1.decrementAndGet()
                                throw r0
                            L_0x0057:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.base.AsyncTaskPoolMonitor.IdleMonitor.C06822.run():void");
                        }
                    });
                }
            }
        }

        /* access modifiers changed from: private */
        public void poison() {
            this.poisoned = true;
            this.barrier.reset();
        }
    }

    AsyncTaskPoolMonitor(ThreadPoolExecutor threadPoolExecutor) {
        this.pool = (ThreadPoolExecutor) Preconditions.checkNotNull(threadPoolExecutor);
    }

    /* access modifiers changed from: package-private */
    public IdleNotifier<Runnable> asIdleNotifier() {
        return new IdleNotifier<Runnable>() {
            public void cancelCallback() {
                AsyncTaskPoolMonitor.this.cancelIdleMonitor();
            }

            public boolean isIdleNow() {
                return AsyncTaskPoolMonitor.this.isIdleNow();
            }

            public void registerNotificationCallback(Runnable runnable) {
                AsyncTaskPoolMonitor.this.notifyWhenIdle(runnable);
            }
        };
    }

    /* access modifiers changed from: package-private */
    public void cancelIdleMonitor() {
        IdleMonitor andSet = this.monitor.getAndSet((Object) null);
        if (andSet != null) {
            andSet.poison();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isIdleNow() {
        if (!this.pool.getQueue().isEmpty()) {
            return false;
        }
        int activeCount = this.pool.getActiveCount();
        if (activeCount != 0 && this.monitor.get() == null) {
            activeCount -= this.activeBarrierChecks.get();
        }
        if (activeCount == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void notifyWhenIdle(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        IdleMonitor idleMonitor = new IdleMonitor(runnable);
        Preconditions.checkState(this.monitor.compareAndSet((Object) null, idleMonitor), "cannot monitor for idle recursively!");
        idleMonitor.monitorForIdle();
    }
}
