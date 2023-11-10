package androidx.test.espresso.base;

import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import androidx.test.internal.platform.p012os.ControlledLooper;
import javax.inject.Provider;

public final class BaseLayerModule_ProvideControlledLooperFactory implements Provider<ControlledLooper> {
    private final BaseLayerModule module;

    public BaseLayerModule_ProvideControlledLooperFactory(BaseLayerModule baseLayerModule) {
        this.module = baseLayerModule;
    }

    public static BaseLayerModule_ProvideControlledLooperFactory create(BaseLayerModule baseLayerModule) {
        return new BaseLayerModule_ProvideControlledLooperFactory(baseLayerModule);
    }

    public static ControlledLooper provideControlledLooper(BaseLayerModule baseLayerModule) {
        return (ControlledLooper) Preconditions.checkNotNullFromProvides(baseLayerModule.provideControlledLooper());
    }

    public ControlledLooper get() {
        return provideControlledLooper(this.module);
    }
}
