package androidx.test.espresso.base;

import android.os.Looper;
import javax.inject.Provider;

public final class RootsOracle_Factory implements Provider<RootsOracle> {
    private final Provider<Looper> mainLooperProvider;

    public RootsOracle_Factory(Provider<Looper> provider) {
        this.mainLooperProvider = provider;
    }

    public static RootsOracle_Factory create(Provider<Looper> provider) {
        return new RootsOracle_Factory(provider);
    }

    public static RootsOracle newInstance(Looper looper) {
        return new RootsOracle(looper);
    }

    public RootsOracle get() {
        return newInstance(this.mainLooperProvider.get());
    }
}
