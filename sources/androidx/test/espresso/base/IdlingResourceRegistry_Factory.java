package androidx.test.espresso.base;

import android.os.Looper;
import javax.inject.Provider;

public final class IdlingResourceRegistry_Factory implements Provider<IdlingResourceRegistry> {
    private final Provider<Looper> looperProvider;

    public IdlingResourceRegistry_Factory(Provider<Looper> provider) {
        this.looperProvider = provider;
    }

    public static IdlingResourceRegistry_Factory create(Provider<Looper> provider) {
        return new IdlingResourceRegistry_Factory(provider);
    }

    public static IdlingResourceRegistry newInstance(Looper looper) {
        return new IdlingResourceRegistry(looper);
    }

    public IdlingResourceRegistry get() {
        return newInstance(this.looperProvider.get());
    }
}
