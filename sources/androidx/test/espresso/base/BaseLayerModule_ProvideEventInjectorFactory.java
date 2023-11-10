package androidx.test.espresso.base;

import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import javax.inject.Provider;

public final class BaseLayerModule_ProvideEventInjectorFactory implements Provider<EventInjector> {
    private final BaseLayerModule module;

    public BaseLayerModule_ProvideEventInjectorFactory(BaseLayerModule baseLayerModule) {
        this.module = baseLayerModule;
    }

    public static BaseLayerModule_ProvideEventInjectorFactory create(BaseLayerModule baseLayerModule) {
        return new BaseLayerModule_ProvideEventInjectorFactory(baseLayerModule);
    }

    public static EventInjector provideEventInjector(BaseLayerModule baseLayerModule) {
        return (EventInjector) Preconditions.checkNotNullFromProvides(baseLayerModule.provideEventInjector());
    }

    public EventInjector get() {
        return provideEventInjector(this.module);
    }
}
