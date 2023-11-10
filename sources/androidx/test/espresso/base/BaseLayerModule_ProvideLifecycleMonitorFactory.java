package androidx.test.espresso.base;

import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import javax.inject.Provider;

public final class BaseLayerModule_ProvideLifecycleMonitorFactory implements Provider<ActivityLifecycleMonitor> {
    private final BaseLayerModule module;

    public BaseLayerModule_ProvideLifecycleMonitorFactory(BaseLayerModule baseLayerModule) {
        this.module = baseLayerModule;
    }

    public static BaseLayerModule_ProvideLifecycleMonitorFactory create(BaseLayerModule baseLayerModule) {
        return new BaseLayerModule_ProvideLifecycleMonitorFactory(baseLayerModule);
    }

    public static ActivityLifecycleMonitor provideLifecycleMonitor(BaseLayerModule baseLayerModule) {
        return (ActivityLifecycleMonitor) Preconditions.checkNotNullFromProvides(baseLayerModule.provideLifecycleMonitor());
    }

    public ActivityLifecycleMonitor get() {
        return provideLifecycleMonitor(this.module);
    }
}
