package androidx.test.espresso;

import androidx.test.espresso.base.ViewFinderImpl;
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ViewInteractionModule_ProvideViewFinderFactory implements Provider<ViewFinder> {
    private final Provider<ViewFinderImpl> implProvider;
    private final ViewInteractionModule module;

    public ViewInteractionModule_ProvideViewFinderFactory(ViewInteractionModule viewInteractionModule, Provider<ViewFinderImpl> provider) {
        this.module = viewInteractionModule;
        this.implProvider = provider;
    }

    public static ViewInteractionModule_ProvideViewFinderFactory create(ViewInteractionModule viewInteractionModule, Provider<ViewFinderImpl> provider) {
        return new ViewInteractionModule_ProvideViewFinderFactory(viewInteractionModule, provider);
    }

    public static ViewFinder provideViewFinder(ViewInteractionModule viewInteractionModule, ViewFinderImpl viewFinderImpl) {
        return (ViewFinder) Preconditions.checkNotNullFromProvides(viewInteractionModule.provideViewFinder(viewFinderImpl));
    }

    public ViewFinder get() {
        return provideViewFinder(this.module, this.implProvider.get());
    }
}
