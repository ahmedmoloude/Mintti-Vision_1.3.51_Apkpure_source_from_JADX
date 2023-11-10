package androidx.test.espresso.base;

import android.os.Looper;
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class BaseLayerModule_ProvideMainThreadExecutorFactory implements Provider<Executor> {
    private final Provider<Looper> mainLooperProvider;
    private final BaseLayerModule module;

    public BaseLayerModule_ProvideMainThreadExecutorFactory(BaseLayerModule baseLayerModule, Provider<Looper> provider) {
        this.module = baseLayerModule;
        this.mainLooperProvider = provider;
    }

    public static BaseLayerModule_ProvideMainThreadExecutorFactory create(BaseLayerModule baseLayerModule, Provider<Looper> provider) {
        return new BaseLayerModule_ProvideMainThreadExecutorFactory(baseLayerModule, provider);
    }

    public static Executor provideMainThreadExecutor(BaseLayerModule baseLayerModule, Looper looper) {
        return (Executor) Preconditions.checkNotNullFromProvides(baseLayerModule.provideMainThreadExecutor(looper));
    }

    public Executor get() {
        return provideMainThreadExecutor(this.module, this.mainLooperProvider.get());
    }
}
