package androidx.test.espresso.base;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.test.espresso.FailureHandler;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.base.IdlingResourceRegistry;
import androidx.test.espresso.core.internal.deps.guava.base.Optional;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListeningExecutorService;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.MoreExecutors;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder;
import androidx.test.espresso.internal.inject.TargetContext;
import androidx.test.internal.platform.ServiceLoaderWrapper;
import androidx.test.internal.platform.p012os.ControlledLooper;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class BaseLayerModule {

    public static class FailureHandlerHolder {
        private final AtomicReference<FailureHandler> holder;

        public FailureHandlerHolder(@Default FailureHandler failureHandler) {
            this.holder = new AtomicReference<>(failureHandler);
        }

        public FailureHandler get() {
            return this.holder.get();
        }

        public void update(FailureHandler failureHandler) {
            this.holder.set(failureHandler);
        }
    }

    public ActiveRootLister provideActiveRootLister(RootsOracle rootsOracle) {
        return rootsOracle;
    }

    @CompatAsyncTask
    public IdleNotifier<Runnable> provideCompatAsyncTaskMonitor(ThreadPoolExecutorExtractor threadPoolExecutorExtractor) {
        Optional<ThreadPoolExecutor> compatAsyncTaskThreadPool = threadPoolExecutorExtractor.getCompatAsyncTaskThreadPool();
        if (compatAsyncTaskThreadPool.isPresent()) {
            return new AsyncTaskPoolMonitor(compatAsyncTaskThreadPool.get()).asIdleNotifier();
        }
        return new NoopRunnableIdleNotifier();
    }

    public ControlledLooper provideControlledLooper() {
        return (ControlledLooper) ServiceLoaderWrapper.loadSingleService(ControlledLooper.class, BaseLayerModule$$Lambda$0.$instance);
    }

    /* access modifiers changed from: package-private */
    public DefaultFailureHandler provideDefaultFailureHander(@TargetContext Context context) {
        return new DefaultFailureHandler(context);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: androidx.test.espresso.base.WindowManagerEventInjectionStrategy} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: androidx.test.espresso.base.InputManagerEventInjectionStrategy} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: androidx.test.espresso.base.WindowManagerEventInjectionStrategy} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: androidx.test.espresso.base.WindowManagerEventInjectionStrategy} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.test.espresso.base.EventInjector provideEventInjector() {
        /*
            r4 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 16
            if (r0 < r1) goto L_0x000f
            androidx.test.espresso.base.InputManagerEventInjectionStrategy r0 = new androidx.test.espresso.base.InputManagerEventInjectionStrategy
            r0.<init>()
            r0.initialize()
            goto L_0x001a
        L_0x000f:
            r1 = 7
            if (r0 < r1) goto L_0x0020
            androidx.test.espresso.base.WindowManagerEventInjectionStrategy r0 = new androidx.test.espresso.base.WindowManagerEventInjectionStrategy
            r0.<init>()
            r0.initialize()
        L_0x001a:
            androidx.test.espresso.base.EventInjector r1 = new androidx.test.espresso.base.EventInjector
            r1.<init>(r0)
            return r1
        L_0x0020:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 68
            r2.<init>(r3)
            java.lang.String r3 = "API Level 6 and below is not supported. You are running: "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.base.BaseLayerModule.provideEventInjector():androidx.test.espresso.base.EventInjector");
    }

    /* access modifiers changed from: package-private */
    @Default
    public FailureHandler provideFailureHander(DefaultFailureHandler defaultFailureHandler) {
        return defaultFailureHandler;
    }

    /* access modifiers changed from: package-private */
    public FailureHandler provideFailureHandler(FailureHandlerHolder failureHandlerHolder) {
        return failureHandlerHolder.get();
    }

    public ActivityLifecycleMonitor provideLifecycleMonitor() {
        return ActivityLifecycleMonitorRegistry.getInstance();
    }

    public Looper provideMainLooper() {
        return Looper.getMainLooper();
    }

    @MainThread
    public Executor provideMainThreadExecutor(Looper looper) {
        final Handler handler = new Handler(looper);
        return new Executor(this) {
            public void execute(Runnable runnable) {
                handler.post(runnable);
            }
        };
    }

    public ListeningExecutorService provideRemoteExecutor() {
        return MoreExecutors.listeningDecorator(new ThreadPoolExecutor(0, 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(), new ThreadFactoryBuilder().setNameFormat("Espresso Remote #%d").build()));
    }

    @SdkAsyncTask
    public IdleNotifier<Runnable> provideSdkAsyncTaskMonitor(ThreadPoolExecutorExtractor threadPoolExecutorExtractor) {
        return new AsyncTaskPoolMonitor(threadPoolExecutorExtractor.getAsyncTaskThreadPool()).asIdleNotifier();
    }

    @TargetContext
    public Context provideTargetContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    public IdleNotifier<IdlingResourceRegistry.IdleNotificationCallback> provideDynamicNotifer(IdlingResourceRegistry idlingResourceRegistry) {
        idlingResourceRegistry.sync(IdlingRegistry.getInstance().getResources(), IdlingRegistry.getInstance().getLoopers());
        return idlingResourceRegistry.asIdleNotifier();
    }
}
