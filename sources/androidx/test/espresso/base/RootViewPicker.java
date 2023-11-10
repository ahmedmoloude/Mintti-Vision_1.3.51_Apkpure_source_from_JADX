package androidx.test.espresso.base;

import android.app.Activity;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import androidx.test.espresso.EspressoException;
import androidx.test.espresso.NoActivityResumedException;
import androidx.test.espresso.NoMatchingRootException;
import androidx.test.espresso.Root;
import androidx.test.espresso.UiController;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.ImmutableList;
import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import androidx.test.espresso.core.internal.deps.guava.collect.UnmodifiableIterator;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.internal.platform.p012os.ControlledLooper;
import androidx.test.internal.util.LogUtil;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.Stage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Provider;
import org.hamcrest.Matcher;

public final class RootViewPicker implements Provider<View> {
    private static final ImmutableList<Integer> CREATED_WAIT_TIMES = ImmutableList.m71of(10, 50, 150, 250);
    private static final ImmutableList<Integer> RESUMED_WAIT_TIMES = ImmutableList.m72of(10, 50, 100, 500, 2000, 30000);
    /* access modifiers changed from: private */
    public static final String TAG = "RootViewPicker";
    private final ActivityLifecycleMonitor activityLifecycleMonitor;
    private final ControlledLooper controlledLooper;
    private final AtomicReference<Boolean> needsActivity;
    private final RootResultFetcher rootResultFetcher;
    private final UiController uiController;

    /* renamed from: androidx.test.espresso.base.RootViewPicker$1 */
    static /* synthetic */ class C06941 {

        /* renamed from: $SwitchMap$androidx$test$espresso$base$RootViewPicker$RootResults$State */
        static final /* synthetic */ int[] f197xebad5cb2;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                androidx.test.espresso.base.RootViewPicker$RootResults$State[] r0 = androidx.test.espresso.base.RootViewPicker.RootResults.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f197xebad5cb2 = r0
                androidx.test.espresso.base.RootViewPicker$RootResults$State r1 = androidx.test.espresso.base.RootViewPicker.RootResults.State.ROOTS_PICKED     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f197xebad5cb2     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.test.espresso.base.RootViewPicker$RootResults$State r1 = androidx.test.espresso.base.RootViewPicker.RootResults.State.NO_ROOTS_PRESENT     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f197xebad5cb2     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.test.espresso.base.RootViewPicker$RootResults$State r1 = androidx.test.espresso.base.RootViewPicker.RootResults.State.NO_ROOTS_PICKED     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.base.RootViewPicker.C06941.<clinit>():void");
        }
    }

    private static abstract class BackOff {
        private final List<Integer> backoffTimes;
        private int numberOfAttempts = 0;
        private final TimeUnit timeUnit;

        public BackOff(List<Integer> list, TimeUnit timeUnit2) {
            this.backoffTimes = list;
            this.timeUnit = timeUnit2;
        }

        /* access modifiers changed from: protected */
        public final long getBackoffForAttempt() {
            if (this.numberOfAttempts >= this.backoffTimes.size()) {
                List<Integer> list = this.backoffTimes;
                return (long) list.get(list.size() - 1).intValue();
            }
            int intValue = this.backoffTimes.get(this.numberOfAttempts).intValue();
            this.numberOfAttempts++;
            return this.timeUnit.toMillis((long) intValue);
        }

        /* access modifiers changed from: protected */
        public abstract long getNextBackoffInMillis();
    }

    private static final class NoActiveRootsBackoff extends BackOff {
        private static final ImmutableList<Integer> NO_ACTIVE_ROOTS_BACKOFF = ImmutableList.m74of(10, 10, 20, 30, 50, 80, 130, 210, 340);

        public NoActiveRootsBackoff() {
            super(NO_ACTIVE_ROOTS_BACKOFF, TimeUnit.MILLISECONDS);
        }

        public long getNextBackoffInMillis() {
            long backoffForAttempt = getBackoffForAttempt();
            LogUtil.logDebugWithProcess(RootViewPicker.TAG, "No active roots available - waiting: %sms for one to appear.", Long.valueOf(backoffForAttempt));
            return backoffForAttempt;
        }
    }

    private static final class NoMatchingRootBackoff extends BackOff {
        private static final ImmutableList<Integer> NO_MATCHING_ROOT_BACKOFF = ImmutableList.m72of(10, 20, 200, 400, 1000, 2000);

        public NoMatchingRootBackoff() {
            super(NO_MATCHING_ROOT_BACKOFF, TimeUnit.MILLISECONDS);
        }

        public long getNextBackoffInMillis() {
            long backoffForAttempt = getBackoffForAttempt();
            Log.d(RootViewPicker.TAG, String.format(Locale.ROOT, "No matching root available - waiting: %sms for one to appear.", new Object[]{Long.valueOf(backoffForAttempt)}));
            return backoffForAttempt;
        }
    }

    private static final class RootReadyBackoff extends BackOff {
        private static final ImmutableList<Integer> ROOT_READY_BACKOFF = ImmutableList.m73of(10, 25, 50, 100, 200, 400, 800, 1000);

        public RootReadyBackoff() {
            super(ROOT_READY_BACKOFF, TimeUnit.MILLISECONDS);
        }

        public long getNextBackoffInMillis() {
            long backoffForAttempt = getBackoffForAttempt();
            Log.d(RootViewPicker.TAG, String.format(Locale.ROOT, "Root not ready - waiting: %sms for one to appear.", new Object[]{Long.valueOf(backoffForAttempt)}));
            return backoffForAttempt;
        }
    }

    static class RootResultFetcher {
        private final ActiveRootLister activeRootLister;
        private final Matcher<Root> selector;

        public RootResultFetcher(ActiveRootLister activeRootLister2, AtomicReference<Matcher<Root>> atomicReference) {
            this.activeRootLister = activeRootLister2;
            this.selector = atomicReference.get();
        }

        public RootResults fetch() {
            List<Root> listActiveRoots = this.activeRootLister.listActiveRoots();
            ArrayList newArrayList = Lists.newArrayList();
            for (Root next : listActiveRoots) {
                if (this.selector.matches(next)) {
                    newArrayList.add(next);
                }
            }
            return new RootResults(listActiveRoots, newArrayList, this.selector, (C06941) null);
        }
    }

    RootViewPicker(UiController uiController2, RootResultFetcher rootResultFetcher2, ActivityLifecycleMonitor activityLifecycleMonitor2, AtomicReference<Boolean> atomicReference, ControlledLooper controlledLooper2) {
        this.uiController = uiController2;
        this.rootResultFetcher = rootResultFetcher2;
        this.activityLifecycleMonitor = activityLifecycleMonitor2;
        this.needsActivity = atomicReference;
        this.controlledLooper = controlledLooper2;
    }

    private List<Activity> getAllActiveActivities() {
        ArrayList newArrayList = Lists.newArrayList();
        Iterator it = EnumSet.range(Stage.PRE_ON_CREATE, Stage.RESTARTED).iterator();
        while (it.hasNext()) {
            newArrayList.addAll(this.activityLifecycleMonitor.getActivitiesInStage((Stage) it.next()));
        }
        return newArrayList;
    }

    private Root pickARoot() {
        long currentTimeMillis = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(60);
        RootResults fetch = this.rootResultFetcher.fetch();
        NoActiveRootsBackoff noActiveRootsBackoff = new NoActiveRootsBackoff();
        NoMatchingRootBackoff noMatchingRootBackoff = new NoMatchingRootBackoff();
        while (System.currentTimeMillis() <= currentTimeMillis) {
            int i = C06941.f197xebad5cb2[fetch.getState().ordinal()];
            if (i == 1) {
                return fetch.getPickedRoot();
            }
            if (i == 2) {
                this.uiController.loopMainThreadForAtLeast(noActiveRootsBackoff.getNextBackoffInMillis());
            } else if (i == 3) {
                this.uiController.loopMainThreadForAtLeast(noMatchingRootBackoff.getNextBackoffInMillis());
            }
            fetch = this.rootResultFetcher.fetch();
        }
        if (RootResults.State.ROOTS_PICKED == fetch.getState()) {
            return fetch.getPickedRoot();
        }
        throw NoMatchingRootException.create(fetch.rootSelector, fetch.allRoots);
    }

    private View pickRootView() {
        return waitForRootToBeReady(pickARoot()).getDecorView();
    }

    private void waitForAtLeastOneActivityToBeResumed() {
        Collection<Activity> activitiesInStage = this.activityLifecycleMonitor.getActivitiesInStage(Stage.RESUMED);
        if (activitiesInStage.isEmpty()) {
            this.uiController.loopMainThreadUntilIdle();
            activitiesInStage = this.activityLifecycleMonitor.getActivitiesInStage(Stage.RESUMED);
        }
        if (activitiesInStage.isEmpty()) {
            List<Activity> allActiveActivities = getAllActiveActivities();
            if (allActiveActivities.isEmpty()) {
                UnmodifiableIterator<Integer> it = CREATED_WAIT_TIMES.iterator();
                while (it.hasNext()) {
                    long intValue = (long) it.next().intValue();
                    String str = TAG;
                    StringBuilder sb = new StringBuilder(72);
                    sb.append("No activities found - waiting: ");
                    sb.append(intValue);
                    sb.append("ms for one to appear.");
                    Log.w(str, sb.toString());
                    this.uiController.loopMainThreadForAtLeast(intValue);
                    allActiveActivities = getAllActiveActivities();
                    if (!allActiveActivities.isEmpty()) {
                        break;
                    }
                }
            }
            if (!allActiveActivities.isEmpty()) {
                UnmodifiableIterator<Integer> it2 = RESUMED_WAIT_TIMES.iterator();
                while (it2.hasNext()) {
                    long intValue2 = (long) it2.next().intValue();
                    String str2 = TAG;
                    StringBuilder sb2 = new StringBuilder(82);
                    sb2.append("No activity currently resumed - waiting: ");
                    sb2.append(intValue2);
                    sb2.append("ms for one to appear.");
                    Log.w(str2, sb2.toString());
                    this.uiController.loopMainThreadForAtLeast(intValue2);
                    if (!this.activityLifecycleMonitor.getActivitiesInStage(Stage.RESUMED).isEmpty()) {
                        return;
                    }
                }
                throw new NoActivityResumedException("No activities in stage RESUMED. Did you forget to launch the activity. (test.getActivity() or similar)?");
            }
            throw new NoActivityResumedException("No activities found. Did you forget to launch the activity by calling getActivity() or startActivitySync or similar?");
        }
    }

    private Root waitForRootToBeReady(Root root) {
        long currentTimeMillis = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10);
        RootReadyBackoff rootReadyBackoff = new RootReadyBackoff();
        while (System.currentTimeMillis() <= currentTimeMillis) {
            if (root.isReady()) {
                return root;
            }
            this.controlledLooper.simulateWindowFocus(root.getDecorView());
            this.uiController.loopMainThreadForAtLeast(rootReadyBackoff.getNextBackoffInMillis());
        }
        throw new RootViewWithoutFocusException(String.format(Locale.ROOT, "Waited for the root of the view hierarchy to have window focus and not request layout for 10 seconds. If you specified a non default root matcher, it may be picking a root that never takes focus. Root:\n%s", new Object[]{root}), (C06941) null);
    }

    public View get() {
        Preconditions.checkState(Looper.getMainLooper().equals(Looper.myLooper()), "must be called on main thread.");
        if (this.needsActivity.get().booleanValue()) {
            waitForAtLeastOneActivityToBeResumed();
        }
        return pickRootView();
    }

    private static final class RootViewWithoutFocusException extends RuntimeException implements EspressoException {
        private RootViewWithoutFocusException(String str) {
            super(str);
        }

        /* synthetic */ RootViewWithoutFocusException(String str, C06941 r2) {
            this(str);
        }
    }

    private static class RootResults {
        /* access modifiers changed from: private */
        public final List<Root> allRoots;
        private final List<Root> pickedRoots;
        /* access modifiers changed from: private */
        public final Matcher<Root> rootSelector;

        enum State {
            NO_ROOTS_PRESENT,
            NO_ROOTS_PICKED,
            ROOTS_PICKED
        }

        private RootResults(List<Root> list, List<Root> list2, Matcher<Root> matcher) {
            this.allRoots = list;
            this.pickedRoots = list2;
            this.rootSelector = matcher;
        }

        private Root getRootFromMultipleRoots() {
            Root root = this.pickedRoots.get(0);
            if (this.pickedRoots.size() > 0) {
                for (Root next : this.pickedRoots) {
                    if (RootMatchers.isDialog().matches(next)) {
                        return next;
                    }
                    if (isTopmostRoot(root, next)) {
                        root = next;
                    }
                }
            }
            return root;
        }

        private static boolean isTopmostRoot(Root root, Root root2) {
            return root2.getWindowLayoutParams().get().type > root.getWindowLayoutParams().get().type;
        }

        public Root getPickedRoot() {
            if (this.pickedRoots.size() <= 1) {
                return this.pickedRoots.get(0);
            }
            LogUtil.logDebugWithProcess(RootViewPicker.TAG, "Multiple root windows detected: %s", this.pickedRoots);
            return getRootFromMultipleRoots();
        }

        public State getState() {
            if (this.allRoots.isEmpty()) {
                return State.NO_ROOTS_PRESENT;
            }
            if (this.pickedRoots.isEmpty()) {
                return State.NO_ROOTS_PICKED;
            }
            if (this.pickedRoots.size() > 0) {
                return State.ROOTS_PICKED;
            }
            return State.NO_ROOTS_PICKED;
        }

        /* synthetic */ RootResults(List list, List list2, Matcher matcher, C06941 r4) {
            this(list, list2, matcher);
        }
    }
}
