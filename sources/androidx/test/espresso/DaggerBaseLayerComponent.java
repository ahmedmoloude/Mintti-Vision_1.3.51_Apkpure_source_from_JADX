package androidx.test.espresso;

import android.content.Context;
import android.os.Looper;
import android.view.View;
import androidx.test.espresso.base.ActiveRootLister;
import androidx.test.espresso.base.BaseLayerModule;
import androidx.test.espresso.base.BaseLayerModule_FailureHandlerHolder_Factory;
import androidx.test.espresso.base.BaseLayerModule_ProvideActiveRootListerFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideCompatAsyncTaskMonitorFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideControlledLooperFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideDefaultFailureHanderFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideDynamicNotiferFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideEventInjectorFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideFailureHanderFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideFailureHandlerFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideLifecycleMonitorFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideMainLooperFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideMainThreadExecutorFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideRemoteExecutorFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideSdkAsyncTaskMonitorFactory;
import androidx.test.espresso.base.BaseLayerModule_ProvideTargetContextFactory;
import androidx.test.espresso.base.DefaultFailureHandler;
import androidx.test.espresso.base.IdlingResourceRegistry;
import androidx.test.espresso.base.IdlingResourceRegistry_Factory;
import androidx.test.espresso.base.RootViewPicker;
import androidx.test.espresso.base.RootViewPicker_Factory;
import androidx.test.espresso.base.RootViewPicker_RootResultFetcher_Factory;
import androidx.test.espresso.base.RootsOracle_Factory;
import androidx.test.espresso.base.ThreadPoolExecutorExtractor_Factory;
import androidx.test.espresso.base.UiControllerImpl_Factory;
import androidx.test.espresso.base.UiControllerModule;
import androidx.test.espresso.base.UiControllerModule_ProvideUiControllerFactory;
import androidx.test.espresso.base.ViewFinderImpl;
import androidx.test.espresso.base.ViewFinderImpl_Factory;
import androidx.test.espresso.core.internal.deps.dagger.internal.DoubleCheck;
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListeningExecutorService;
import androidx.test.internal.platform.p012os.ControlledLooper;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Provider;
import org.hamcrest.Matcher;

public final class DaggerBaseLayerComponent implements BaseLayerComponent {
    private final BaseLayerModule baseLayerModule;
    private Provider<BaseLayerModule.FailureHandlerHolder> failureHandlerHolderProvider;
    private Provider<IdlingResourceRegistry> idlingResourceRegistryProvider;
    /* access modifiers changed from: private */
    public Provider<ActiveRootLister> provideActiveRootListerProvider;
    private Provider provideCompatAsyncTaskMonitorProvider;
    /* access modifiers changed from: private */
    public Provider<ControlledLooper> provideControlledLooperProvider;
    private Provider<DefaultFailureHandler> provideDefaultFailureHanderProvider;
    private Provider provideDynamicNotiferProvider;
    private Provider provideEventInjectorProvider;
    private Provider<FailureHandler> provideFailureHanderProvider;
    /* access modifiers changed from: private */
    public Provider<ActivityLifecycleMonitor> provideLifecycleMonitorProvider;
    private Provider<Looper> provideMainLooperProvider;
    /* access modifiers changed from: private */
    public Provider<Executor> provideMainThreadExecutorProvider;
    /* access modifiers changed from: private */
    public Provider<ListeningExecutorService> provideRemoteExecutorProvider;
    private Provider provideSdkAsyncTaskMonitorProvider;
    private Provider<Context> provideTargetContextProvider;
    /* access modifiers changed from: private */
    public Provider<UiController> provideUiControllerProvider;
    private Provider rootsOracleProvider;
    private Provider threadPoolExecutorExtractorProvider;
    private Provider uiControllerImplProvider;

    public static final class Builder {
        private BaseLayerModule baseLayerModule;
        private UiControllerModule uiControllerModule;

        private Builder() {
        }

        public Builder baseLayerModule(BaseLayerModule baseLayerModule2) {
            this.baseLayerModule = (BaseLayerModule) Preconditions.checkNotNull(baseLayerModule2);
            return this;
        }

        public BaseLayerComponent build() {
            if (this.baseLayerModule == null) {
                this.baseLayerModule = new BaseLayerModule();
            }
            if (this.uiControllerModule == null) {
                this.uiControllerModule = new UiControllerModule();
            }
            return new DaggerBaseLayerComponent(this.baseLayerModule, this.uiControllerModule);
        }

        public Builder uiControllerModule(UiControllerModule uiControllerModule2) {
            this.uiControllerModule = (UiControllerModule) Preconditions.checkNotNull(uiControllerModule2);
            return this;
        }
    }

    private final class ViewInteractionComponentImpl implements ViewInteractionComponent {
        private Provider<AtomicReference<Boolean>> provideNeedsActivityProvider;
        private Provider<AtomicReference<Matcher<Root>>> provideRootMatcherProvider;
        private Provider<View> provideRootViewProvider;
        private Provider rootResultFetcherProvider;
        private Provider<RootViewPicker> rootViewPickerProvider;
        private final ViewInteractionModule viewInteractionModule;

        private ViewInteractionComponentImpl(ViewInteractionModule viewInteractionModule2) {
            this.viewInteractionModule = viewInteractionModule2;
            initialize(viewInteractionModule2);
        }

        private void initialize(ViewInteractionModule viewInteractionModule2) {
            this.provideRootMatcherProvider = ViewInteractionModule_ProvideRootMatcherFactory.create(viewInteractionModule2);
            this.rootResultFetcherProvider = RootViewPicker_RootResultFetcher_Factory.create(DaggerBaseLayerComponent.this.provideActiveRootListerProvider, this.provideRootMatcherProvider);
            this.provideNeedsActivityProvider = ViewInteractionModule_ProvideNeedsActivityFactory.create(viewInteractionModule2);
            Provider<RootViewPicker> provider = DoubleCheck.provider(RootViewPicker_Factory.create(DaggerBaseLayerComponent.this.provideUiControllerProvider, this.rootResultFetcherProvider, DaggerBaseLayerComponent.this.provideLifecycleMonitorProvider, this.provideNeedsActivityProvider, DaggerBaseLayerComponent.this.provideControlledLooperProvider));
            this.rootViewPickerProvider = provider;
            this.provideRootViewProvider = ViewInteractionModule_ProvideRootViewFactory.create(viewInteractionModule2, provider);
        }

        private ViewFinder viewFinder() {
            return ViewInteractionModule_ProvideViewFinderFactory.provideViewFinder(this.viewInteractionModule, viewFinderImpl());
        }

        private ViewFinderImpl viewFinderImpl() {
            return ViewFinderImpl_Factory.newInstance(ViewInteractionModule_ProvideViewMatcherFactory.provideViewMatcher(this.viewInteractionModule), this.provideRootViewProvider);
        }

        public ViewInteraction viewInteraction() {
            return new ViewInteraction((UiController) DaggerBaseLayerComponent.this.provideUiControllerProvider.get(), viewFinder(), (Executor) DaggerBaseLayerComponent.this.provideMainThreadExecutorProvider.get(), DaggerBaseLayerComponent.this.failureHandler(), ViewInteractionModule_ProvideViewMatcherFactory.provideViewMatcher(this.viewInteractionModule), ViewInteractionModule_ProvideRootMatcherFactory.provideRootMatcher(this.viewInteractionModule), ViewInteractionModule_ProvideNeedsActivityFactory.provideNeedsActivity(this.viewInteractionModule), ViewInteractionModule_ProvideRemoteInteractionFactory.provideRemoteInteraction(this.viewInteractionModule), (ListeningExecutorService) DaggerBaseLayerComponent.this.provideRemoteExecutorProvider.get(), (ControlledLooper) DaggerBaseLayerComponent.this.provideControlledLooperProvider.get());
        }
    }

    private DaggerBaseLayerComponent(BaseLayerModule baseLayerModule2, UiControllerModule uiControllerModule) {
        this.baseLayerModule = baseLayerModule2;
        initialize(baseLayerModule2, uiControllerModule);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static BaseLayerComponent create() {
        return new Builder().build();
    }

    private void initialize(BaseLayerModule baseLayerModule2, UiControllerModule uiControllerModule) {
        BaseLayerModule_ProvideTargetContextFactory create = BaseLayerModule_ProvideTargetContextFactory.create(baseLayerModule2);
        this.provideTargetContextProvider = create;
        BaseLayerModule_ProvideDefaultFailureHanderFactory create2 = BaseLayerModule_ProvideDefaultFailureHanderFactory.create(baseLayerModule2, create);
        this.provideDefaultFailureHanderProvider = create2;
        BaseLayerModule_ProvideFailureHanderFactory create3 = BaseLayerModule_ProvideFailureHanderFactory.create(baseLayerModule2, create2);
        this.provideFailureHanderProvider = create3;
        this.failureHandlerHolderProvider = DoubleCheck.provider(BaseLayerModule_FailureHandlerHolder_Factory.create(create3));
        Provider<Looper> provider = DoubleCheck.provider(BaseLayerModule_ProvideMainLooperFactory.create(baseLayerModule2));
        this.provideMainLooperProvider = provider;
        this.idlingResourceRegistryProvider = DoubleCheck.provider(IdlingResourceRegistry_Factory.create(provider));
        this.provideEventInjectorProvider = DoubleCheck.provider(BaseLayerModule_ProvideEventInjectorFactory.create(baseLayerModule2));
        Provider provider2 = DoubleCheck.provider(ThreadPoolExecutorExtractor_Factory.create(this.provideMainLooperProvider));
        this.threadPoolExecutorExtractorProvider = provider2;
        this.provideSdkAsyncTaskMonitorProvider = DoubleCheck.provider(BaseLayerModule_ProvideSdkAsyncTaskMonitorFactory.create(baseLayerModule2, provider2));
        this.provideCompatAsyncTaskMonitorProvider = DoubleCheck.provider(BaseLayerModule_ProvideCompatAsyncTaskMonitorFactory.create(baseLayerModule2, this.threadPoolExecutorExtractorProvider));
        BaseLayerModule_ProvideDynamicNotiferFactory create4 = BaseLayerModule_ProvideDynamicNotiferFactory.create(baseLayerModule2, this.idlingResourceRegistryProvider);
        this.provideDynamicNotiferProvider = create4;
        Provider provider3 = DoubleCheck.provider(UiControllerImpl_Factory.create(this.provideEventInjectorProvider, this.provideSdkAsyncTaskMonitorProvider, this.provideCompatAsyncTaskMonitorProvider, create4, this.provideMainLooperProvider, this.idlingResourceRegistryProvider));
        this.uiControllerImplProvider = provider3;
        this.provideUiControllerProvider = DoubleCheck.provider(UiControllerModule_ProvideUiControllerFactory.create(uiControllerModule, provider3));
        this.provideMainThreadExecutorProvider = DoubleCheck.provider(BaseLayerModule_ProvideMainThreadExecutorFactory.create(baseLayerModule2, this.provideMainLooperProvider));
        this.provideControlledLooperProvider = DoubleCheck.provider(BaseLayerModule_ProvideControlledLooperFactory.create(baseLayerModule2));
        RootsOracle_Factory create5 = RootsOracle_Factory.create(this.provideMainLooperProvider);
        this.rootsOracleProvider = create5;
        this.provideActiveRootListerProvider = BaseLayerModule_ProvideActiveRootListerFactory.create(baseLayerModule2, create5);
        this.provideLifecycleMonitorProvider = BaseLayerModule_ProvideLifecycleMonitorFactory.create(baseLayerModule2);
        this.provideRemoteExecutorProvider = DoubleCheck.provider(BaseLayerModule_ProvideRemoteExecutorFactory.create(baseLayerModule2));
    }

    private Object rootsOracle() {
        return RootsOracle_Factory.newInstance(this.provideMainLooperProvider.get());
    }

    public ActiveRootLister activeRootLister() {
        return BaseLayerModule_ProvideActiveRootListerFactory.provideActiveRootLister(this.baseLayerModule, rootsOracle());
    }

    public ControlledLooper controlledLooper() {
        return this.provideControlledLooperProvider.get();
    }

    public FailureHandler failureHandler() {
        return BaseLayerModule_ProvideFailureHandlerFactory.provideFailureHandler(this.baseLayerModule, this.failureHandlerHolderProvider.get());
    }

    public BaseLayerModule.FailureHandlerHolder failureHolder() {
        return this.failureHandlerHolderProvider.get();
    }

    public IdlingResourceRegistry idlingResourceRegistry() {
        return this.idlingResourceRegistryProvider.get();
    }

    public Executor mainThreadExecutor() {
        return this.provideMainThreadExecutorProvider.get();
    }

    public ViewInteractionComponent plus(ViewInteractionModule viewInteractionModule) {
        Preconditions.checkNotNull(viewInteractionModule);
        return new ViewInteractionComponentImpl(viewInteractionModule);
    }

    public UiController uiController() {
        return this.provideUiControllerProvider.get();
    }
}
