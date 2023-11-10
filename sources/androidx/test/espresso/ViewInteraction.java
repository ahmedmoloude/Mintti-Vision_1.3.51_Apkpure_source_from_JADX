package androidx.test.espresso;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.action.ScrollToAction;
import androidx.test.espresso.base.InterruptableUiController;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.ImmutableMap;
import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListenableFuture;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListenableFutureTask;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListeningExecutorService;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.remote.Bindable;
import androidx.test.espresso.remote.IInteractionExecutionStatus;
import androidx.test.espresso.remote.RemoteInteraction;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.internal.platform.p012os.ControlledLooper;
import androidx.test.internal.util.LogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.StringDescription;

public final class ViewInteraction {
    /* access modifiers changed from: private */
    public static final String TAG = "ViewInteraction";
    private final ControlledLooper controlledLooper;
    private volatile FailureHandler failureHandler;
    private boolean hasRootMatcher = false;
    private final Executor mainThreadExecutor;
    private final AtomicReference<Boolean> needsActivity;
    private final ListeningExecutorService remoteExecutor;
    private final RemoteInteraction remoteInteraction;
    private final AtomicReference<Matcher<Root>> rootMatcherRef;
    /* access modifiers changed from: private */
    public final InterruptableUiController uiController;
    /* access modifiers changed from: private */
    public final ViewFinder viewFinder;
    /* access modifiers changed from: private */
    public final Matcher<View> viewMatcher;

    private static final class SingleExecutionViewAction implements ViewAction, Bindable {
        private IInteractionExecutionStatus actionExecutionStatus;
        final ViewAction viewAction;
        final Matcher<View> viewMatcher;

        private SingleExecutionViewAction(ViewAction viewAction2, Matcher<View> matcher) {
            this.actionExecutionStatus = new IInteractionExecutionStatus.Stub(this) {
                AtomicBoolean run = new AtomicBoolean(true);

                public boolean canExecute() throws RemoteException {
                    return this.run.getAndSet(false);
                }
            };
            this.viewAction = viewAction2;
            this.viewMatcher = matcher;
        }

        public Matcher<View> getConstraints() {
            return this.viewAction.getConstraints();
        }

        public String getDescription() {
            return this.viewAction.getDescription();
        }

        public IBinder getIBinder() {
            return this.actionExecutionStatus.asBinder();
        }

        public String getId() {
            return RemoteInteraction.BUNDLE_EXECUTION_STATUS;
        }

        /* access modifiers changed from: package-private */
        public ViewAction getInnerViewAction() {
            return this.viewAction;
        }

        public void perform(UiController uiController, View view) {
            try {
                if (!this.actionExecutionStatus.canExecute()) {
                    String access$500 = ViewInteraction.TAG;
                    String valueOf = String.valueOf(this.viewAction);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 63);
                    sb.append("Attempted to execute a Single Execution Action more then once: ");
                    sb.append(valueOf);
                    LogUtil.logDebugWithProcess(access$500, sb.toString(), new Object[0]);
                    return;
                }
                this.viewAction.perform(uiController, view);
            } catch (RemoteException e) {
                throw new PerformException.Builder().withActionDescription(this.viewAction.getDescription()).withViewDescription(this.viewMatcher.toString()).withCause(new RuntimeException("Unable to query interaction execution status", e.getCause())).build();
            }
        }

        public void setIBinder(IBinder iBinder) {
            this.actionExecutionStatus = IInteractionExecutionStatus.Stub.asInterface(iBinder);
        }
    }

    private static final class SingleExecutionViewAssertion implements ViewAssertion, Bindable {
        private IInteractionExecutionStatus assertionExecutionStatus;
        final ViewAssertion viewAssertion;

        private SingleExecutionViewAssertion(ViewAssertion viewAssertion2) {
            this.assertionExecutionStatus = new IInteractionExecutionStatus.Stub(this) {
                AtomicBoolean run = new AtomicBoolean(true);

                public boolean canExecute() throws RemoteException {
                    return this.run.getAndSet(false);
                }
            };
            this.viewAssertion = viewAssertion2;
        }

        public void check(View view, NoMatchingViewException noMatchingViewException) {
            try {
                if (!this.assertionExecutionStatus.canExecute()) {
                    String access$500 = ViewInteraction.TAG;
                    String valueOf = String.valueOf(this.viewAssertion);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 66);
                    sb.append("Attempted to execute a Single Execution Assertion more then once: ");
                    sb.append(valueOf);
                    LogUtil.logDebugWithProcess(access$500, sb.toString(), new Object[0]);
                    return;
                }
                this.viewAssertion.check(view, noMatchingViewException);
            } catch (RemoteException e) {
                throw new RuntimeException("Unable to query interaction execution status", e.getCause());
            }
        }

        public IBinder getIBinder() {
            return this.assertionExecutionStatus.asBinder();
        }

        public String getId() {
            return RemoteInteraction.BUNDLE_EXECUTION_STATUS;
        }

        public void setIBinder(IBinder iBinder) {
            this.assertionExecutionStatus = IInteractionExecutionStatus.Stub.asInterface(iBinder);
        }
    }

    /* JADX WARNING: type inference failed for: r6v0, types: [org.hamcrest.Matcher<android.view.View>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    ViewInteraction(androidx.test.espresso.UiController r2, androidx.test.espresso.ViewFinder r3, @androidx.test.espresso.base.MainThread java.util.concurrent.Executor r4, androidx.test.espresso.FailureHandler r5, org.hamcrest.Matcher<android.view.View> r6, java.util.concurrent.atomic.AtomicReference<org.hamcrest.Matcher<androidx.test.espresso.Root>> r7, java.util.concurrent.atomic.AtomicReference<java.lang.Boolean> r8, androidx.test.espresso.remote.RemoteInteraction r9, androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListeningExecutorService r10, androidx.test.internal.platform.p012os.ControlledLooper r11) {
        /*
            r1 = this;
            r1.<init>()
            r0 = 0
            r1.hasRootMatcher = r0
            java.lang.Object r3 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r3)
            androidx.test.espresso.ViewFinder r3 = (androidx.test.espresso.ViewFinder) r3
            r1.viewFinder = r3
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            androidx.test.espresso.base.InterruptableUiController r2 = (androidx.test.espresso.base.InterruptableUiController) r2
            r1.uiController = r2
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r5)
            androidx.test.espresso.FailureHandler r2 = (androidx.test.espresso.FailureHandler) r2
            r1.failureHandler = r2
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r4)
            java.util.concurrent.Executor r2 = (java.util.concurrent.Executor) r2
            r1.mainThreadExecutor = r2
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r6)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1.viewMatcher = r2
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r7)
            java.util.concurrent.atomic.AtomicReference r2 = (java.util.concurrent.atomic.AtomicReference) r2
            r1.rootMatcherRef = r2
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r8)
            java.util.concurrent.atomic.AtomicReference r2 = (java.util.concurrent.atomic.AtomicReference) r2
            r1.needsActivity = r2
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r9)
            androidx.test.espresso.remote.RemoteInteraction r2 = (androidx.test.espresso.remote.RemoteInteraction) r2
            r1.remoteInteraction = r2
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r10)
            androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListeningExecutorService r2 = (androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListeningExecutorService) r2
            r1.remoteExecutor = r2
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r11)
            androidx.test.internal.platform.os.ControlledLooper r2 = (androidx.test.internal.platform.p012os.ControlledLooper) r2
            r1.controlledLooper = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.ViewInteraction.<init>(androidx.test.espresso.UiController, androidx.test.espresso.ViewFinder, java.util.concurrent.Executor, androidx.test.espresso.FailureHandler, org.hamcrest.Matcher, java.util.concurrent.atomic.AtomicReference, java.util.concurrent.atomic.AtomicReference, androidx.test.espresso.remote.RemoteInteraction, androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListeningExecutorService, androidx.test.internal.platform.os.ControlledLooper):void");
    }

    private void desugaredPerform(final SingleExecutionViewAction singleExecutionViewAction) {
        C06401 r0 = new Callable<Void>() {
            public Void call() {
                ViewInteraction.this.doPerform(singleExecutionViewAction);
                return null;
            }
        };
        ViewAction innerViewAction = singleExecutionViewAction.getInnerViewAction();
        ArrayList arrayList = new ArrayList();
        arrayList.add(postAsynchronouslyOnUiThread(r0));
        if (!this.remoteInteraction.isRemoteProcess()) {
            arrayList.add(this.remoteExecutor.submit(this.remoteInteraction.createRemotePerformCallable(this.rootMatcherRef.get(), this.viewMatcher, getIBindersFromViewActions(singleExecutionViewAction, innerViewAction), innerViewAction)));
        }
        waitForAndHandleInteractionResults(arrayList);
    }

    /* access modifiers changed from: private */
    public void doPerform(SingleExecutionViewAction singleExecutionViewAction) {
        Preconditions.checkNotNull(singleExecutionViewAction);
        Matcher matcher = (Matcher) Preconditions.checkNotNull(singleExecutionViewAction.getConstraints());
        this.uiController.loopMainThreadUntilIdle();
        View view = this.viewFinder.getView();
        Log.i(TAG, String.format(Locale.ROOT, "Performing '%s' action on view %s", new Object[]{singleExecutionViewAction.getDescription(), this.viewMatcher}));
        if (!matcher.matches(view)) {
            StringDescription stringDescription = new StringDescription(new StringBuilder("Action will not be performed because the target view does not match one or more of the following constraints:\n"));
            matcher.describeTo(stringDescription);
            stringDescription.appendText("\nTarget view: ").appendValue(HumanReadables.describe(view));
            if ((singleExecutionViewAction.getInnerViewAction() instanceof ScrollToAction) && ViewMatchers.isDescendantOfA(ViewMatchers.isAssignableFrom(AdapterView.class)).matches(view)) {
                stringDescription.appendText("\nFurther Info: ScrollToAction on a view inside an AdapterView will not work. Use Espresso.onData to load the view.");
            }
            throw new PerformException.Builder().withActionDescription(singleExecutionViewAction.getDescription()).withViewDescription(this.viewMatcher.toString()).withCause(new RuntimeException(stringDescription.toString())).build();
        }
        singleExecutionViewAction.perform(this.uiController, view);
    }

    private static List<Bindable> getBindables(Object... objArr) {
        ArrayList newArrayListWithCapacity = Lists.newArrayListWithCapacity(objArr.length);
        for (Bindable bindable : objArr) {
            if (bindable instanceof Bindable) {
                newArrayListWithCapacity.add(bindable);
            }
        }
        return newArrayListWithCapacity;
    }

    private static Map<String, IBinder> getIBindersFromBindables(List<Bindable> list) {
        HashMap hashMap = new HashMap();
        for (Bindable next : list) {
            hashMap.put((String) Preconditions.checkNotNull(next.getId(), "Bindable id cannot be null!"), (IBinder) Preconditions.checkNotNull(next.getIBinder(), "Bindable binder cannot be null!"));
        }
        return ImmutableMap.copyOf(hashMap);
    }

    private static Map<String, IBinder> getIBindersFromViewActions(ViewAction... viewActionArr) {
        return getIBindersFromBindables(getBindables((Object[]) viewActionArr));
    }

    private static Map<String, IBinder> getIBindersFromViewAssertions(ViewAssertion... viewAssertionArr) {
        return getIBindersFromBindables(getBindables((Object[]) viewAssertionArr));
    }

    private ListenableFuture<Void> postAsynchronouslyOnUiThread(Callable<Void> callable) {
        ListenableFutureTask<Void> create = ListenableFutureTask.create(callable);
        this.mainThreadExecutor.execute(create);
        return create;
    }

    private void waitForAndHandleInteractionResults(List<ListenableFuture<Void>> list) {
        try {
            this.controlledLooper.drainMainThreadUntilIdle();
            InteractionResultsHandler.gatherAnyResult(list);
        } catch (RuntimeException e) {
            this.failureHandler.handle(e, this.viewMatcher);
        } catch (Error e2) {
            this.failureHandler.handle(e2, this.viewMatcher);
        } catch (Throwable th) {
            this.uiController.interruptEspressoTasks();
            throw th;
        }
        this.uiController.interruptEspressoTasks();
    }

    public ViewInteraction check(final ViewAssertion viewAssertion) {
        Preconditions.checkNotNull(viewAssertion);
        final SingleExecutionViewAssertion singleExecutionViewAssertion = new SingleExecutionViewAssertion(viewAssertion);
        C06412 r1 = new Callable<Void>() {
            public Void call() {
                NoMatchingViewException noMatchingViewException;
                View view;
                ViewInteraction.this.uiController.loopMainThreadUntilIdle();
                try {
                    view = ViewInteraction.this.viewFinder.getView();
                    noMatchingViewException = null;
                } catch (NoMatchingViewException e) {
                    noMatchingViewException = e;
                    view = null;
                }
                Log.i(ViewInteraction.TAG, String.format(Locale.ROOT, "Checking '%s' assertion on view %s", new Object[]{viewAssertion, ViewInteraction.this.viewMatcher}));
                singleExecutionViewAssertion.check(view, noMatchingViewException);
                return null;
            }
        };
        ArrayList arrayList = new ArrayList();
        arrayList.add(postAsynchronouslyOnUiThread(r1));
        if (!this.remoteInteraction.isRemoteProcess()) {
            arrayList.add(this.remoteExecutor.submit(this.remoteInteraction.createRemoteCheckCallable(this.rootMatcherRef.get(), this.viewMatcher, getIBindersFromViewAssertions(singleExecutionViewAssertion, viewAssertion), viewAssertion)));
        }
        waitForAndHandleInteractionResults(arrayList);
        return this;
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<androidx.test.espresso.Root>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.test.espresso.ViewInteraction inRoot(org.hamcrest.Matcher<androidx.test.espresso.Root> r2) {
        /*
            r1 = this;
            r0 = 1
            r1.hasRootMatcher = r0
            java.util.concurrent.atomic.AtomicReference<org.hamcrest.Matcher<androidx.test.espresso.Root>> r0 = r1.rootMatcherRef
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r0.set(r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.ViewInteraction.inRoot(org.hamcrest.Matcher):androidx.test.espresso.ViewInteraction");
    }

    public ViewInteraction noActivity() {
        if (!this.hasRootMatcher) {
            this.rootMatcherRef.set(Matchers.anyOf(RootMatchers.DEFAULT, Matchers.allOf(RootMatchers.hasWindowLayoutParams(), RootMatchers.isSystemAlertWindow())));
        }
        this.needsActivity.set(false);
        return this;
    }

    public ViewInteraction perform(ViewAction... viewActionArr) {
        Preconditions.checkNotNull(viewActionArr);
        for (ViewAction singleExecutionViewAction : viewActionArr) {
            desugaredPerform(new SingleExecutionViewAction(singleExecutionViewAction, this.viewMatcher));
        }
        return this;
    }

    public ViewInteraction withFailureHandler(FailureHandler failureHandler2) {
        this.failureHandler = (FailureHandler) Preconditions.checkNotNull(failureHandler2);
        return this;
    }
}
