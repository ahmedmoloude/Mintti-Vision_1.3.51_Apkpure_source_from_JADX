package androidx.test.espresso.base;

import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import javax.inject.Provider;

public final class BaseLayerModule_ProvideCompatAsyncTaskMonitorFactory implements Provider<IdleNotifier<Runnable>> {
    private final Provider<ThreadPoolExecutorExtractor> extractorProvider;
    private final BaseLayerModule module;

    public BaseLayerModule_ProvideCompatAsyncTaskMonitorFactory(BaseLayerModule baseLayerModule, Provider<ThreadPoolExecutorExtractor> provider) {
        this.module = baseLayerModule;
        this.extractorProvider = provider;
    }

    public static BaseLayerModule_ProvideCompatAsyncTaskMonitorFactory create(BaseLayerModule baseLayerModule, Provider<ThreadPoolExecutorExtractor> provider) {
        return new BaseLayerModule_ProvideCompatAsyncTaskMonitorFactory(baseLayerModule, provider);
    }

    public static IdleNotifier<Runnable> provideCompatAsyncTaskMonitor(BaseLayerModule baseLayerModule, Object obj) {
        return (IdleNotifier) Preconditions.checkNotNullFromProvides(baseLayerModule.provideCompatAsyncTaskMonitor((ThreadPoolExecutorExtractor) obj));
    }

    public IdleNotifier<Runnable> get() {
        return provideCompatAsyncTaskMonitor(this.module, this.extractorProvider.get());
    }
}
