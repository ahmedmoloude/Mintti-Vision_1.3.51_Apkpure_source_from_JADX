package androidx.test.espresso.base;

import androidx.test.espresso.FailureHandler;
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import javax.inject.Provider;

public final class BaseLayerModule_ProvideFailureHanderFactory implements Provider<FailureHandler> {
    private final Provider<DefaultFailureHandler> implProvider;
    private final BaseLayerModule module;

    public BaseLayerModule_ProvideFailureHanderFactory(BaseLayerModule baseLayerModule, Provider<DefaultFailureHandler> provider) {
        this.module = baseLayerModule;
        this.implProvider = provider;
    }

    public static BaseLayerModule_ProvideFailureHanderFactory create(BaseLayerModule baseLayerModule, Provider<DefaultFailureHandler> provider) {
        return new BaseLayerModule_ProvideFailureHanderFactory(baseLayerModule, provider);
    }

    public static FailureHandler provideFailureHander(BaseLayerModule baseLayerModule, DefaultFailureHandler defaultFailureHandler) {
        return (FailureHandler) Preconditions.checkNotNullFromProvides(baseLayerModule.provideFailureHander(defaultFailureHandler));
    }

    public FailureHandler get() {
        return provideFailureHander(this.module, this.implProvider.get());
    }
}
