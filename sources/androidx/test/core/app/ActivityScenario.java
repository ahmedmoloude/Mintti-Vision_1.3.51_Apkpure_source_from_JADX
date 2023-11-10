package androidx.test.core.app;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.test.internal.platform.ServiceLoaderWrapper;
import androidx.test.internal.platform.app.ActivityInvoker;
import androidx.test.internal.platform.p012os.ControlledLooper;
import androidx.test.internal.util.Checks;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleCallback;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import java.io.Closeable;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public final class ActivityScenario<A extends Activity> implements AutoCloseable, Closeable {
    private static final Map<Stage, Lifecycle.State> STEADY_STATES;
    /* access modifiers changed from: private */
    public static final String TAG = "ActivityScenario";
    private static final long TIMEOUT_MILLISECONDS = 45000;
    private final ActivityInvoker activityInvoker;
    private final ActivityLifecycleCallback activityLifecycleObserver;
    private final ControlledLooper controlledLooper;
    /* access modifiers changed from: private */
    public A currentActivity;
    /* access modifiers changed from: private */
    public Stage currentActivityStage;
    /* access modifiers changed from: private */
    public final ReentrantLock lock;
    /* access modifiers changed from: private */
    public final Intent startActivityIntent;
    /* access modifiers changed from: private */
    public final Condition stateChangedCondition;

    public interface ActivityAction<A extends Activity> {
        void perform(A a);
    }

    static {
        EnumMap enumMap = new EnumMap(Stage.class);
        STEADY_STATES = enumMap;
        enumMap.put(Stage.RESUMED, Lifecycle.State.RESUMED);
        enumMap.put(Stage.PAUSED, Lifecycle.State.STARTED);
        enumMap.put(Stage.STOPPED, Lifecycle.State.CREATED);
        enumMap.put(Stage.DESTROYED, Lifecycle.State.DESTROYED);
    }

    static final /* synthetic */ ActivityInvoker lambda$new$0$ActivityScenario() {
        return new InstrumentationActivityInvoker();
    }

    private ActivityScenario(Intent intent) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.stateChangedCondition = reentrantLock.newCondition();
        this.activityInvoker = (ActivityInvoker) ServiceLoaderWrapper.loadSingleService(ActivityInvoker.class, ActivityScenario$$Lambda$0.$instance);
        this.controlledLooper = (ControlledLooper) ServiceLoaderWrapper.loadSingleService(ControlledLooper.class, ActivityScenario$$Lambda$1.$instance);
        this.currentActivityStage = Stage.PRE_ON_CREATE;
        this.activityLifecycleObserver = new ActivityLifecycleCallback() {
            public void onActivityLifecycleChanged(Activity activity, Stage stage) {
                if (!ActivityScenario.activityMatchesIntent(ActivityScenario.this.startActivityIntent, activity)) {
                    Log.v(ActivityScenario.TAG, String.format("Activity lifecycle changed event received but ignored because the intent does not match. startActivityIntent=%s, activity.getIntent()=%s, activity=%s", new Object[]{ActivityScenario.this.startActivityIntent, activity.getIntent(), activity}));
                    return;
                }
                ActivityScenario.this.lock.lock();
                int i = C06232.$SwitchMap$androidx$test$runner$lifecycle$Stage[ActivityScenario.this.currentActivityStage.ordinal()];
                if (i == 1 || i == 2) {
                    try {
                        if (stage != Stage.CREATED) {
                            Log.v(ActivityScenario.TAG, String.format("Activity lifecycle changed event received but ignored because the reported transition was not ON_CREATE while the last known transition was %s", new Object[]{ActivityScenario.this.currentActivityStage}));
                            return;
                        }
                    } finally {
                        ActivityScenario.this.lock.unlock();
                    }
                } else if (ActivityScenario.this.currentActivity != activity) {
                    Log.v(ActivityScenario.TAG, String.format("Activity lifecycle changed event received but ignored because the activity instance does not match. currentActivity=%s, receivedActivity=%s", new Object[]{ActivityScenario.this.currentActivity, activity}));
                    ActivityScenario.this.lock.unlock();
                    return;
                }
                Stage unused = ActivityScenario.this.currentActivityStage = stage;
                ActivityScenario activityScenario = ActivityScenario.this;
                if (stage == Stage.DESTROYED) {
                    activity = null;
                }
                Activity unused2 = activityScenario.currentActivity = activity;
                Log.v(ActivityScenario.TAG, String.format("Update currentActivityStage to %s, currentActivity=%s", new Object[]{ActivityScenario.this.currentActivityStage, ActivityScenario.this.currentActivity}));
                ActivityScenario.this.stateChangedCondition.signal();
                ActivityScenario.this.lock.unlock();
            }
        };
        this.startActivityIntent = (Intent) Checks.checkNotNull(intent);
    }

    private ActivityScenario(Class<A> cls) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.stateChangedCondition = reentrantLock.newCondition();
        ActivityInvoker activityInvoker2 = (ActivityInvoker) ServiceLoaderWrapper.loadSingleService(ActivityInvoker.class, ActivityScenario$$Lambda$2.$instance);
        this.activityInvoker = activityInvoker2;
        this.controlledLooper = (ControlledLooper) ServiceLoaderWrapper.loadSingleService(ControlledLooper.class, ActivityScenario$$Lambda$3.$instance);
        this.currentActivityStage = Stage.PRE_ON_CREATE;
        this.activityLifecycleObserver = new ActivityLifecycleCallback() {
            public void onActivityLifecycleChanged(Activity activity, Stage stage) {
                if (!ActivityScenario.activityMatchesIntent(ActivityScenario.this.startActivityIntent, activity)) {
                    Log.v(ActivityScenario.TAG, String.format("Activity lifecycle changed event received but ignored because the intent does not match. startActivityIntent=%s, activity.getIntent()=%s, activity=%s", new Object[]{ActivityScenario.this.startActivityIntent, activity.getIntent(), activity}));
                    return;
                }
                ActivityScenario.this.lock.lock();
                int i = C06232.$SwitchMap$androidx$test$runner$lifecycle$Stage[ActivityScenario.this.currentActivityStage.ordinal()];
                if (i == 1 || i == 2) {
                    try {
                        if (stage != Stage.CREATED) {
                            Log.v(ActivityScenario.TAG, String.format("Activity lifecycle changed event received but ignored because the reported transition was not ON_CREATE while the last known transition was %s", new Object[]{ActivityScenario.this.currentActivityStage}));
                            return;
                        }
                    } finally {
                        ActivityScenario.this.lock.unlock();
                    }
                } else if (ActivityScenario.this.currentActivity != activity) {
                    Log.v(ActivityScenario.TAG, String.format("Activity lifecycle changed event received but ignored because the activity instance does not match. currentActivity=%s, receivedActivity=%s", new Object[]{ActivityScenario.this.currentActivity, activity}));
                    ActivityScenario.this.lock.unlock();
                    return;
                }
                Stage unused = ActivityScenario.this.currentActivityStage = stage;
                ActivityScenario activityScenario = ActivityScenario.this;
                if (stage == Stage.DESTROYED) {
                    activity = null;
                }
                Activity unused2 = activityScenario.currentActivity = activity;
                Log.v(ActivityScenario.TAG, String.format("Update currentActivityStage to %s, currentActivity=%s", new Object[]{ActivityScenario.this.currentActivityStage, ActivityScenario.this.currentActivity}));
                ActivityScenario.this.stateChangedCondition.signal();
                ActivityScenario.this.lock.unlock();
            }
        };
        this.startActivityIntent = (Intent) Checks.checkNotNull(activityInvoker2.getIntentForActivity((Class) Checks.checkNotNull(cls)));
    }

    public static <A extends Activity> ActivityScenario<A> launch(Class<A> cls) {
        ActivityScenario<A> activityScenario = new ActivityScenario<>((Class) Checks.checkNotNull(cls));
        activityScenario.launchInternal((Bundle) null);
        return activityScenario;
    }

    public static <A extends Activity> ActivityScenario<A> launch(Class<A> cls, Bundle bundle) {
        ActivityScenario<A> activityScenario = new ActivityScenario<>((Class) Checks.checkNotNull(cls));
        activityScenario.launchInternal(bundle);
        return activityScenario;
    }

    public static <A extends Activity> ActivityScenario<A> launch(Intent intent) {
        ActivityScenario<A> activityScenario = new ActivityScenario<>((Intent) Checks.checkNotNull(intent));
        activityScenario.launchInternal((Bundle) null);
        return activityScenario;
    }

    public static <A extends Activity> ActivityScenario<A> launch(Intent intent, Bundle bundle) {
        ActivityScenario<A> activityScenario = new ActivityScenario<>((Intent) Checks.checkNotNull(intent));
        activityScenario.launchInternal(bundle);
        return activityScenario;
    }

    private void launchInternal(Bundle bundle) {
        Checks.checkState(Settings.System.getInt(InstrumentationRegistry.getInstrumentation().getTargetContext().getContentResolver(), "always_finish_activities", 0) == 0, "\"Don't keep activities\" developer options must be disabled for ActivityScenario");
        Checks.checkNotMainThread();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(this.activityLifecycleObserver);
        if (bundle == null) {
            this.activityInvoker.startActivity(this.startActivityIntent);
        } else {
            this.activityInvoker.startActivity(this.startActivityIntent, bundle);
        }
        waitForActivityToBecomeAnyOf((Lifecycle.State[]) STEADY_STATES.values().toArray(new Lifecycle.State[0]));
    }

    public void close() {
        moveToState(Lifecycle.State.DESTROYED);
        ActivityLifecycleMonitorRegistry.getInstance().removeLifecycleCallback(this.activityLifecycleObserver);
    }

    private void waitForActivityToBecomeAnyOf(Lifecycle.State... stateArr) {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        HashSet hashSet = new HashSet(Arrays.asList(stateArr));
        this.lock.lock();
        try {
            if (hashSet.contains(STEADY_STATES.get(this.currentActivityStage))) {
                this.lock.unlock();
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            long j = TIMEOUT_MILLISECONDS + currentTimeMillis;
            while (currentTimeMillis < j && !hashSet.contains(STEADY_STATES.get(this.currentActivityStage))) {
                this.stateChangedCondition.await(j - currentTimeMillis, TimeUnit.MILLISECONDS);
                currentTimeMillis = System.currentTimeMillis();
            }
            if (hashSet.contains(STEADY_STATES.get(this.currentActivityStage))) {
                this.lock.unlock();
            } else {
                throw new AssertionError(String.format("Activity never becomes requested state \"%s\" (last lifecycle transition = \"%s\")", new Object[]{hashSet, this.currentActivityStage}));
            }
        } catch (InterruptedException e) {
            throw new AssertionError(String.format("Activity never becomes requested state \"%s\" (last lifecycle transition = \"%s\")", new Object[]{hashSet, this.currentActivityStage}), e);
        } catch (Throwable th) {
            this.lock.unlock();
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public static boolean activityMatchesIntent(Intent intent, Activity activity) {
        Intent intent2 = activity.getIntent();
        if (!equals(intent.getAction(), intent2.getAction()) || !equals(intent.getData(), intent2.getData()) || !equals(intent.getType(), intent2.getType()) || !equals(intent.getPackage(), intent2.getPackage())) {
            return false;
        }
        if ((intent.getComponent() != null && !equals(intent.getComponent(), intent2.getComponent())) || !equals(intent.getCategories(), intent2.getCategories())) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 29 || equals(intent.getIdentifier(), intent2.getIdentifier())) {
            return true;
        }
        return false;
    }

    private static boolean equals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    private static class ActivityState<A extends Activity> {
        final A activity;
        final Stage stage;
        final Lifecycle.State state;

        ActivityState(A a, Lifecycle.State state2, Stage stage2) {
            this.activity = a;
            this.state = state2;
            this.stage = stage2;
        }
    }

    private ActivityState<A> getCurrentActivityState() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        this.lock.lock();
        try {
            return new ActivityState<>(this.currentActivity, STEADY_STATES.get(this.currentActivityStage), this.currentActivityStage);
        } finally {
            this.lock.unlock();
        }
    }

    public ActivityScenario<A> moveToState(Lifecycle.State state) {
        Checks.checkNotMainThread();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        ActivityState currentActivityState = getCurrentActivityState();
        Checks.checkNotNull(currentActivityState.state, String.format("Current state was null unexpectedly. Last stage = %s", new Object[]{currentActivityState.stage}));
        if (currentActivityState.state == state) {
            return this;
        }
        Checks.checkState((currentActivityState.state == Lifecycle.State.DESTROYED || currentActivityState.activity == null) ? false : true, String.format("Cannot move to state \"%s\" since the Activity has been destroyed already", new Object[]{state}));
        int i = C06232.$SwitchMap$android$arch$lifecycle$Lifecycle$State[state.ordinal()];
        if (i == 1) {
            this.activityInvoker.stopActivity(currentActivityState.activity);
        } else if (i == 2) {
            moveToState(Lifecycle.State.RESUMED);
            this.activityInvoker.pauseActivity(currentActivityState.activity);
        } else if (i == 3) {
            this.activityInvoker.resumeActivity(currentActivityState.activity);
        } else if (i == 4) {
            this.activityInvoker.finishActivity(currentActivityState.activity);
        } else {
            throw new IllegalArgumentException(String.format("A requested state \"%s\" is not supported", new Object[]{state}));
        }
        waitForActivityToBecomeAnyOf(state);
        return this;
    }

    /* renamed from: androidx.test.core.app.ActivityScenario$2 */
    static /* synthetic */ class C06232 {
        static final /* synthetic */ int[] $SwitchMap$android$arch$lifecycle$Lifecycle$State;
        static final /* synthetic */ int[] $SwitchMap$androidx$test$runner$lifecycle$Stage;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|(2:1|2)|3|5|6|7|8|(2:9|10)|11|13|14|15|16|18) */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0044 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0028 */
        static {
            /*
                androidx.lifecycle.Lifecycle$State[] r0 = androidx.lifecycle.Lifecycle.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$android$arch$lifecycle$Lifecycle$State = r0
                r1 = 1
                androidx.lifecycle.Lifecycle$State r2 = androidx.lifecycle.Lifecycle.State.CREATED     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                r0 = 2
                int[] r2 = $SwitchMap$android$arch$lifecycle$Lifecycle$State     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.lifecycle.Lifecycle$State r3 = androidx.lifecycle.Lifecycle.State.STARTED     // Catch:{ NoSuchFieldError -> 0x001d }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r2 = $SwitchMap$android$arch$lifecycle$Lifecycle$State     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.lifecycle.Lifecycle$State r3 = androidx.lifecycle.Lifecycle.State.RESUMED     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r4 = 3
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r2 = $SwitchMap$android$arch$lifecycle$Lifecycle$State     // Catch:{ NoSuchFieldError -> 0x0033 }
                androidx.lifecycle.Lifecycle$State r3 = androidx.lifecycle.Lifecycle.State.DESTROYED     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r4 = 4
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                androidx.test.runner.lifecycle.Stage[] r2 = androidx.test.runner.lifecycle.Stage.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                $SwitchMap$androidx$test$runner$lifecycle$Stage = r2
                androidx.test.runner.lifecycle.Stage r3 = androidx.test.runner.lifecycle.Stage.PRE_ON_CREATE     // Catch:{ NoSuchFieldError -> 0x0044 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0044 }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x0044 }
            L_0x0044:
                int[] r1 = $SwitchMap$androidx$test$runner$lifecycle$Stage     // Catch:{ NoSuchFieldError -> 0x004e }
                androidx.test.runner.lifecycle.Stage r2 = androidx.test.runner.lifecycle.Stage.DESTROYED     // Catch:{ NoSuchFieldError -> 0x004e }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x004e }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x004e }
            L_0x004e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.core.app.ActivityScenario.C06232.<clinit>():void");
        }
    }

    public ActivityScenario<A> recreate() {
        ActivityState currentActivityState;
        Checks.checkNotMainThread();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        ActivityState currentActivityState2 = getCurrentActivityState();
        Checks.checkNotNull(currentActivityState2.activity);
        Checks.checkNotNull(currentActivityState2.state);
        moveToState(Lifecycle.State.RESUMED);
        this.activityInvoker.recreateActivity(currentActivityState2.activity);
        long currentTimeMillis = System.currentTimeMillis() + TIMEOUT_MILLISECONDS;
        do {
            waitForActivityToBecomeAnyOf(Lifecycle.State.RESUMED);
            long currentTimeMillis2 = System.currentTimeMillis();
            currentActivityState = getCurrentActivityState();
            if (currentTimeMillis2 >= currentTimeMillis || currentActivityState.activity != currentActivityState2.activity) {
            }
            waitForActivityToBecomeAnyOf(Lifecycle.State.RESUMED);
            long currentTimeMillis22 = System.currentTimeMillis();
            currentActivityState = getCurrentActivityState();
            break;
        } while (currentActivityState.activity != currentActivityState2.activity);
        if (currentActivityState.activity != currentActivityState2.activity) {
            moveToState(currentActivityState2.state);
            return this;
        }
        throw new IllegalStateException("Requested a re-creation of Activity but didn't happen");
    }

    public ActivityScenario<A> onActivity(ActivityAction<A> activityAction) {
        ActivityScenario$$Lambda$4 activityScenario$$Lambda$4 = new ActivityScenario$$Lambda$4(this, activityAction);
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.controlledLooper.drainMainThreadUntilIdle();
            activityScenario$$Lambda$4.run();
        } else {
            InstrumentationRegistry.getInstrumentation().waitForIdleSync();
            InstrumentationRegistry.getInstrumentation().runOnMainSync(activityScenario$$Lambda$4);
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onActivity$2$ActivityScenario(ActivityAction activityAction) {
        Checks.checkMainThread();
        this.lock.lock();
        try {
            Checks.checkNotNull(this.currentActivity, "Cannot run onActivity since Activity has been destroyed already");
            activityAction.perform(this.currentActivity);
        } finally {
            this.lock.unlock();
        }
    }

    public Instrumentation.ActivityResult getResult() {
        return this.activityInvoker.getActivityResult();
    }

    public Lifecycle.State getState() {
        ActivityState currentActivityState = getCurrentActivityState();
        return (Lifecycle.State) Checks.checkNotNull(currentActivityState.state, "Could not get current state of activity %s due to the transition is incomplete. Current stage = %s", currentActivityState.activity, currentActivityState.stage);
    }
}
