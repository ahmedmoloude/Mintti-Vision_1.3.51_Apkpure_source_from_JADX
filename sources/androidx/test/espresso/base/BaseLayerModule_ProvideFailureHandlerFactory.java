package androidx.test.espresso.base;

import androidx.test.espresso.FailureHandler;
import androidx.test.espresso.base.BaseLayerModule;
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import javax.inject.Provider;

public final class BaseLayerModule_ProvideFailureHandlerFactory implements Provider<FailureHandler> {
    private final Provider<BaseLayerModule.FailureHandlerHolder> holderProvider;
    private final BaseLayerModule module;

    public BaseLayerModule_ProvideFailureHandlerFactory(BaseLayerModule baseLayerModule, Provider<BaseLayerModule.FailureHandlerHolder> provider) {
        this.module = baseLayerModule;
        this.holderProvider = provider;
    }

    public static BaseLayerModule_ProvideFailureHandlerFactory create(BaseLayerModule baseLayerModule, Provider<BaseLayerModule.FailureHandlerHolder> provider) {
        return new BaseLayerModule_ProvideFailureHandlerFactory(baseLayerModule, provider);
    }

    public static FailureHandler provideFailureHandler(BaseLayerModule baseLayerModule, BaseLayerModule.FailureHandlerHolder failureHandlerHolder) {
        return (FailureHandler) Preconditions.checkNotNullFromProvides(baseLayerModule.provideFailureHandler(failureHandlerHolder));
    }

    public FailureHandler get() {
        return provideFailureHandler(this.module, this.holderProvider.get());
    }
}
