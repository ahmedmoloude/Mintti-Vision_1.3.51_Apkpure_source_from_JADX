package androidx.test.espresso.base;

import android.view.View;
import javax.inject.Provider;
import org.hamcrest.Matcher;

public final class ViewFinderImpl_Factory implements Provider<ViewFinderImpl> {
    private final Provider<View> rootViewProvider;
    private final Provider<Matcher<View>> viewMatcherProvider;

    public ViewFinderImpl_Factory(Provider<Matcher<View>> provider, Provider<View> provider2) {
        this.viewMatcherProvider = provider;
        this.rootViewProvider = provider2;
    }

    public static ViewFinderImpl_Factory create(Provider<Matcher<View>> provider, Provider<View> provider2) {
        return new ViewFinderImpl_Factory(provider, provider2);
    }

    public static ViewFinderImpl newInstance(Matcher<View> matcher, Provider<View> provider) {
        return new ViewFinderImpl(matcher, provider);
    }

    public ViewFinderImpl get() {
        return newInstance(this.viewMatcherProvider.get(), this.rootViewProvider);
    }
}
