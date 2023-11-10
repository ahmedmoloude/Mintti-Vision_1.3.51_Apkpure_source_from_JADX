package androidx.test.espresso.base;

import androidx.test.espresso.UiController;
import androidx.test.espresso.base.RootViewPicker;
import androidx.test.internal.platform.p012os.ControlledLooper;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Provider;

public final class RootViewPicker_Factory implements Provider<RootViewPicker> {
    private final Provider<ActivityLifecycleMonitor> activityLifecycleMonitorProvider;
    private final Provider<ControlledLooper> controlledLooperProvider;
    private final Provider<AtomicReference<Boolean>> needsActivityProvider;
    private final Provider<RootViewPicker.RootResultFetcher> rootResultFetcherProvider;
    private final Provider<UiController> uiControllerProvider;

    public RootViewPicker_Factory(Provider<UiController> provider, Provider<RootViewPicker.RootResultFetcher> provider2, Provider<ActivityLifecycleMonitor> provider3, Provider<AtomicReference<Boolean>> provider4, Provider<ControlledLooper> provider5) {
        this.uiControllerProvider = provider;
        this.rootResultFetcherProvider = provider2;
        this.activityLifecycleMonitorProvider = provider3;
        this.needsActivityProvider = provider4;
        this.controlledLooperProvider = provider5;
    }

    public static RootViewPicker_Factory create(Provider<UiController> provider, Provider<RootViewPicker.RootResultFetcher> provider2, Provider<ActivityLifecycleMonitor> provider3, Provider<AtomicReference<Boolean>> provider4, Provider<ControlledLooper> provider5) {
        return new RootViewPicker_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static RootViewPicker newInstance(UiController uiController, Object obj, ActivityLifecycleMonitor activityLifecycleMonitor, AtomicReference<Boolean> atomicReference, ControlledLooper controlledLooper) {
        return new RootViewPicker(uiController, (RootViewPicker.RootResultFetcher) obj, activityLifecycleMonitor, atomicReference, controlledLooper);
    }

    public RootViewPicker get() {
        return newInstance(this.uiControllerProvider.get(), this.rootResultFetcherProvider.get(), this.activityLifecycleMonitorProvider.get(), this.needsActivityProvider.get(), this.controlledLooperProvider.get());
    }
}
