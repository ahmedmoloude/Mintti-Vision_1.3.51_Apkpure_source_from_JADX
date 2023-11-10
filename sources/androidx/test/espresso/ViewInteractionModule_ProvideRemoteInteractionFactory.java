package androidx.test.espresso;

import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import androidx.test.espresso.remote.RemoteInteraction;
import javax.inject.Provider;

public final class ViewInteractionModule_ProvideRemoteInteractionFactory implements Provider<RemoteInteraction> {
    private final ViewInteractionModule module;

    public ViewInteractionModule_ProvideRemoteInteractionFactory(ViewInteractionModule viewInteractionModule) {
        this.module = viewInteractionModule;
    }

    public static ViewInteractionModule_ProvideRemoteInteractionFactory create(ViewInteractionModule viewInteractionModule) {
        return new ViewInteractionModule_ProvideRemoteInteractionFactory(viewInteractionModule);
    }

    public static RemoteInteraction provideRemoteInteraction(ViewInteractionModule viewInteractionModule) {
        return (RemoteInteraction) Preconditions.checkNotNullFromProvides(viewInteractionModule.provideRemoteInteraction());
    }

    public RemoteInteraction get() {
        return provideRemoteInteraction(this.module);
    }
}
