package androidx.test.espresso;

import android.view.View;
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import javax.inject.Provider;
import org.hamcrest.Matcher;

public final class ViewInteractionModule_ProvideViewMatcherFactory implements Provider<Matcher<View>> {
    private final ViewInteractionModule module;

    public ViewInteractionModule_ProvideViewMatcherFactory(ViewInteractionModule viewInteractionModule) {
        this.module = viewInteractionModule;
    }

    public static ViewInteractionModule_ProvideViewMatcherFactory create(ViewInteractionModule viewInteractionModule) {
        return new ViewInteractionModule_ProvideViewMatcherFactory(viewInteractionModule);
    }

    public static Matcher<View> provideViewMatcher(ViewInteractionModule viewInteractionModule) {
        return (Matcher) Preconditions.checkNotNullFromProvides(viewInteractionModule.provideViewMatcher());
    }

    public Matcher<View> get() {
        return provideViewMatcher(this.module);
    }
}
