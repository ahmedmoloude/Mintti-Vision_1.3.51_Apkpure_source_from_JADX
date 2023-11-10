package androidx.test.espresso.base;

import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import javax.inject.Provider;

public final class BaseLayerModule_ProvideSdkAsyncTaskMonitorFactory implements Provider<IdleNotifier<Runnable>> {
    private final Provider<ThreadPoolExecutorExtractor> extractorProvider;
    private final BaseLayerModule module;

    public BaseLayerModule_ProvideSdkAsyncTaskMonitorFactory(BaseLayerModule baseLayerModule, Provider<ThreadPoolExecutorExtractor> provider) {
        this.module = baseLayerModule;
        this.extractorProvider = provider;
    }

    public static BaseLayerModule_ProvideSdkAsyncTaskMonitorFactory create(BaseLayerModule baseLayerModule, Provider<ThreadPoolExecutorExtractor> provider) {
        return new BaseLayerModule_ProvideSdkAsyncTaskMonitorFactory(baseLayerModule, provider);
    }

    public static IdleNotifier<Runnable> provideSdkAsyncTaskMonitor(BaseLayerModule baseLayerModule, Object obj) {
        return (IdleNotifier) Preconditions.checkNotNullFromProvides(baseLayerModule.provideSdkAsyncTaskMonitor((ThreadPoolExecutorExtractor) obj));
    }

    public IdleNotifier<Runnable> get() {
        return provideSdkAsyncTaskMonitor(this.module, this.extractorProvider.get());
    }
}
