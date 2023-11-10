package androidx.test.espresso.base;

import android.content.Context;
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import javax.inject.Provider;

public final class BaseLayerModule_ProvideTargetContextFactory implements Provider<Context> {
    private final BaseLayerModule module;

    public BaseLayerModule_ProvideTargetContextFactory(BaseLayerModule baseLayerModule) {
        this.module = baseLayerModule;
    }

    public static BaseLayerModule_ProvideTargetContextFactory create(BaseLayerModule baseLayerModule) {
        return new BaseLayerModule_ProvideTargetContextFactory(baseLayerModule);
    }

    public static Context provideTargetContext(BaseLayerModule baseLayerModule) {
        return (Context) Preconditions.checkNotNullFromProvides(baseLayerModule.provideTargetContext());
    }

    public Context get() {
        return provideTargetContext(this.module);
    }
}
