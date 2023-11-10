package androidx.test.espresso.base;

import androidx.test.espresso.Root;
import androidx.test.espresso.base.RootViewPicker;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Provider;
import org.hamcrest.Matcher;

public final class RootViewPicker_RootResultFetcher_Factory implements Provider<RootViewPicker.RootResultFetcher> {
    private final Provider<ActiveRootLister> activeRootListerProvider;
    private final Provider<AtomicReference<Matcher<Root>>> rootMatcherRefProvider;

    public RootViewPicker_RootResultFetcher_Factory(Provider<ActiveRootLister> provider, Provider<AtomicReference<Matcher<Root>>> provider2) {
        this.activeRootListerProvider = provider;
        this.rootMatcherRefProvider = provider2;
    }

    public static RootViewPicker_RootResultFetcher_Factory create(Provider<ActiveRootLister> provider, Provider<AtomicReference<Matcher<Root>>> provider2) {
        return new RootViewPicker_RootResultFetcher_Factory(provider, provider2);
    }

    public static RootViewPicker.RootResultFetcher newInstance(ActiveRootLister activeRootLister, AtomicReference<Matcher<Root>> atomicReference) {
        return new RootViewPicker.RootResultFetcher(activeRootLister, atomicReference);
    }

    public RootViewPicker.RootResultFetcher get() {
        return newInstance(this.activeRootListerProvider.get(), this.rootMatcherRefProvider.get());
    }
}
