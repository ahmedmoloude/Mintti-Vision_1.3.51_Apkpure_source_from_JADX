package androidx.test.espresso.base;

import android.os.Looper;
import androidx.test.espresso.base.IdlingResourceRegistry;
import javax.inject.Provider;

public final class UiControllerImpl_Factory implements Provider<UiControllerImpl> {
    private final Provider<IdleNotifier<Runnable>> asyncIdleProvider;
    private final Provider<IdleNotifier<Runnable>> compatIdleProvider;
    private final Provider<IdleNotifier<IdlingResourceRegistry.IdleNotificationCallback>> dynamicIdleProvider;
    private final Provider<EventInjector> eventInjectorProvider;
    private final Provider<IdlingResourceRegistry> idlingResourceRegistryProvider;
    private final Provider<Looper> mainLooperProvider;

    public UiControllerImpl_Factory(Provider<EventInjector> provider, Provider<IdleNotifier<Runnable>> provider2, Provider<IdleNotifier<Runnable>> provider3, Provider<IdleNotifier<IdlingResourceRegistry.IdleNotificationCallback>> provider4, Provider<Looper> provider5, Provider<IdlingResourceRegistry> provider6) {
        this.eventInjectorProvider = provider;
        this.asyncIdleProvider = provider2;
        this.compatIdleProvider = provider3;
        this.dynamicIdleProvider = provider4;
        this.mainLooperProvider = provider5;
        this.idlingResourceRegistryProvider = provider6;
    }

    public static UiControllerImpl_Factory create(Provider<EventInjector> provider, Provider<IdleNotifier<Runnable>> provider2, Provider<IdleNotifier<Runnable>> provider3, Provider<IdleNotifier<IdlingResourceRegistry.IdleNotificationCallback>> provider4, Provider<Looper> provider5, Provider<IdlingResourceRegistry> provider6) {
        return new UiControllerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static UiControllerImpl newInstance(Object obj, Object obj2, Object obj3, Provider<IdleNotifier<IdlingResourceRegistry.IdleNotificationCallback>> provider, Looper looper, IdlingResourceRegistry idlingResourceRegistry) {
        return new UiControllerImpl((EventInjector) obj, (IdleNotifier) obj2, (IdleNotifier) obj3, provider, looper, idlingResourceRegistry);
    }

    public UiControllerImpl get() {
        return newInstance(this.eventInjectorProvider.get(), this.asyncIdleProvider.get(), this.compatIdleProvider.get(), this.dynamicIdleProvider, this.mainLooperProvider.get(), this.idlingResourceRegistryProvider.get());
    }
}
