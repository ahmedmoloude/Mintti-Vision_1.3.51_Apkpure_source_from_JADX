package androidx.test.espresso;

import android.view.View;
import androidx.test.espresso.base.RootViewPicker;
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ViewInteractionModule_ProvideRootViewFactory implements Provider<View> {
    private final ViewInteractionModule module;
    private final Provider<RootViewPicker> rootViewPickerProvider;

    public ViewInteractionModule_ProvideRootViewFactory(ViewInteractionModule viewInteractionModule, Provider<RootViewPicker> provider) {
        this.module = viewInteractionModule;
        this.rootViewPickerProvider = provider;
    }

    public static ViewInteractionModule_ProvideRootViewFactory create(ViewInteractionModule viewInteractionModule, Provider<RootViewPicker> provider) {
        return new ViewInteractionModule_ProvideRootViewFactory(viewInteractionModule, provider);
    }

    public static View provideRootView(ViewInteractionModule viewInteractionModule, RootViewPicker rootViewPicker) {
        return (View) Preconditions.checkNotNullFromProvides(viewInteractionModule.provideRootView(rootViewPicker));
    }

    public View get() {
        return provideRootView(this.module, this.rootViewPickerProvider.get());
    }
}
