package androidx.test.espresso.base;

import androidx.test.espresso.FailureHandler;
import androidx.test.espresso.base.BaseLayerModule;
import javax.inject.Provider;

public final class BaseLayerModule_FailureHandlerHolder_Factory implements Provider<BaseLayerModule.FailureHandlerHolder> {
    private final Provider<FailureHandler> defaultHandlerProvider;

    public BaseLayerModule_FailureHandlerHolder_Factory(Provider<FailureHandler> provider) {
        this.defaultHandlerProvider = provider;
    }

    public static BaseLayerModule_FailureHandlerHolder_Factory create(Provider<FailureHandler> provider) {
        return new BaseLayerModule_FailureHandlerHolder_Factory(provider);
    }

    public static BaseLayerModule.FailureHandlerHolder newInstance(FailureHandler failureHandler) {
        return new BaseLayerModule.FailureHandlerHolder(failureHandler);
    }

    public BaseLayerModule.FailureHandlerHolder get() {
        return newInstance(this.defaultHandlerProvider.get());
    }
}
