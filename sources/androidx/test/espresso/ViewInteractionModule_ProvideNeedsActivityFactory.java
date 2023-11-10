package androidx.test.espresso;

import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Provider;

public final class ViewInteractionModule_ProvideNeedsActivityFactory implements Provider<AtomicReference<Boolean>> {
    private final ViewInteractionModule module;

    public ViewInteractionModule_ProvideNeedsActivityFactory(ViewInteractionModule viewInteractionModule) {
        this.module = viewInteractionModule;
    }

    public static ViewInteractionModule_ProvideNeedsActivityFactory create(ViewInteractionModule viewInteractionModule) {
        return new ViewInteractionModule_ProvideNeedsActivityFactory(viewInteractionModule);
    }

    public static AtomicReference<Boolean> provideNeedsActivity(ViewInteractionModule viewInteractionModule) {
        return (AtomicReference) Preconditions.checkNotNullFromProvides(viewInteractionModule.provideNeedsActivity());
    }

    public AtomicReference<Boolean> get() {
        return provideNeedsActivity(this.module);
    }
}
