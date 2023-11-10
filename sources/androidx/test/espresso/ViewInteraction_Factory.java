package androidx.test.espresso;

import android.view.View;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListeningExecutorService;
import androidx.test.espresso.remote.RemoteInteraction;
import androidx.test.internal.platform.p012os.ControlledLooper;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Provider;
import org.hamcrest.Matcher;

public final class ViewInteraction_Factory implements Provider<ViewInteraction> {
    private final Provider<ControlledLooper> controlledLooperProvider;
    private final Provider<FailureHandler> failureHandlerProvider;
    private final Provider<Executor> mainThreadExecutorProvider;
    private final Provider<AtomicReference<Boolean>> needsActivityProvider;
    private final Provider<ListeningExecutorService> remoteExecutorProvider;
    private final Provider<RemoteInteraction> remoteInteractionProvider;
    private final Provider<AtomicReference<Matcher<Root>>> rootMatcherRefProvider;
    private final Provider<UiController> uiControllerProvider;
    private final Provider<ViewFinder> viewFinderProvider;
    private final Provider<Matcher<View>> viewMatcherProvider;

    public ViewInteraction_Factory(Provider<UiController> provider, Provider<ViewFinder> provider2, Provider<Executor> provider3, Provider<FailureHandler> provider4, Provider<Matcher<View>> provider5, Provider<AtomicReference<Matcher<Root>>> provider6, Provider<AtomicReference<Boolean>> provider7, Provider<RemoteInteraction> provider8, Provider<ListeningExecutorService> provider9, Provider<ControlledLooper> provider10) {
        this.uiControllerProvider = provider;
        this.viewFinderProvider = provider2;
        this.mainThreadExecutorProvider = provider3;
        this.failureHandlerProvider = provider4;
        this.viewMatcherProvider = provider5;
        this.rootMatcherRefProvider = provider6;
        this.needsActivityProvider = provider7;
        this.remoteInteractionProvider = provider8;
        this.remoteExecutorProvider = provider9;
        this.controlledLooperProvider = provider10;
    }

    public static ViewInteraction_Factory create(Provider<UiController> provider, Provider<ViewFinder> provider2, Provider<Executor> provider3, Provider<FailureHandler> provider4, Provider<Matcher<View>> provider5, Provider<AtomicReference<Matcher<Root>>> provider6, Provider<AtomicReference<Boolean>> provider7, Provider<RemoteInteraction> provider8, Provider<ListeningExecutorService> provider9, Provider<ControlledLooper> provider10) {
        return new ViewInteraction_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public static ViewInteraction newInstance(UiController uiController, ViewFinder viewFinder, Executor executor, FailureHandler failureHandler, Matcher<View> matcher, AtomicReference<Matcher<Root>> atomicReference, AtomicReference<Boolean> atomicReference2, RemoteInteraction remoteInteraction, ListeningExecutorService listeningExecutorService, ControlledLooper controlledLooper) {
        return new ViewInteraction(uiController, viewFinder, executor, failureHandler, matcher, atomicReference, atomicReference2, remoteInteraction, listeningExecutorService, controlledLooper);
    }

    public ViewInteraction get() {
        return newInstance(this.uiControllerProvider.get(), this.viewFinderProvider.get(), this.mainThreadExecutorProvider.get(), this.failureHandlerProvider.get(), this.viewMatcherProvider.get(), this.rootMatcherRefProvider.get(), this.needsActivityProvider.get(), this.remoteInteractionProvider.get(), this.remoteExecutorProvider.get(), this.controlledLooperProvider.get());
    }
}
