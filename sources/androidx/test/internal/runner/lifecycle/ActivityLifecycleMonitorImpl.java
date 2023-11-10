package androidx.test.internal.runner.lifecycle;

import android.app.Activity;
import android.os.Looper;
import android.util.Log;
import androidx.test.internal.util.Checks;
import androidx.test.runner.lifecycle.ActivityLifecycleCallback;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.Stage;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class ActivityLifecycleMonitorImpl implements ActivityLifecycleMonitor {
    private static final String TAG = "LifecycleMonitor";
    private List<ActivityStatus> activityStatuses;
    private final List<WeakReference<ActivityLifecycleCallback>> callbacks;
    private final boolean declawThreadCheck;

    public ActivityLifecycleMonitorImpl() {
        this(false);
    }

    public ActivityLifecycleMonitorImpl(boolean z) {
        this.callbacks = new ArrayList();
        this.activityStatuses = new ArrayList();
        this.declawThreadCheck = z;
    }

    public void addLifecycleCallback(ActivityLifecycleCallback activityLifecycleCallback) {
        Checks.checkNotNull(activityLifecycleCallback);
        synchronized (this.callbacks) {
            boolean z = true;
            Iterator<WeakReference<ActivityLifecycleCallback>> it = this.callbacks.iterator();
            while (it.hasNext()) {
                ActivityLifecycleCallback activityLifecycleCallback2 = (ActivityLifecycleCallback) it.next().get();
                if (activityLifecycleCallback2 == null) {
                    it.remove();
                } else if (activityLifecycleCallback2 == activityLifecycleCallback) {
                    z = false;
                }
            }
            if (z) {
                this.callbacks.add(new WeakReference(activityLifecycleCallback));
            }
        }
    }

    public void removeLifecycleCallback(ActivityLifecycleCallback activityLifecycleCallback) {
        Checks.checkNotNull(activityLifecycleCallback);
        synchronized (this.callbacks) {
            Iterator<WeakReference<ActivityLifecycleCallback>> it = this.callbacks.iterator();
            while (it.hasNext()) {
                ActivityLifecycleCallback activityLifecycleCallback2 = (ActivityLifecycleCallback) it.next().get();
                if (activityLifecycleCallback2 == null) {
                    it.remove();
                } else if (activityLifecycleCallback2 == activityLifecycleCallback) {
                    it.remove();
                }
            }
        }
    }

    public Stage getLifecycleStageOf(Activity activity) {
        checkMainThread();
        Checks.checkNotNull(activity);
        Iterator<ActivityStatus> it = this.activityStatuses.iterator();
        while (it.hasNext()) {
            ActivityStatus next = it.next();
            Activity activity2 = (Activity) next.activityRef.get();
            if (activity2 == null) {
                it.remove();
            } else if (activity == activity2) {
                return next.lifecycleStage;
            }
        }
        String valueOf = String.valueOf(activity);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
        sb.append("Unknown activity: ");
        sb.append(valueOf);
        throw new IllegalArgumentException(sb.toString());
    }

    public Collection<Activity> getActivitiesInStage(Stage stage) {
        checkMainThread();
        Checks.checkNotNull(stage);
        ArrayList arrayList = new ArrayList();
        Iterator<ActivityStatus> it = this.activityStatuses.iterator();
        while (it.hasNext()) {
            ActivityStatus next = it.next();
            Activity activity = (Activity) next.activityRef.get();
            if (activity == null) {
                it.remove();
            } else if (stage == next.lifecycleStage) {
                arrayList.add(activity);
            }
        }
        return arrayList;
    }

    public void signalLifecycleChange(Stage stage, Activity activity) {
        String valueOf = String.valueOf(activity);
        String valueOf2 = String.valueOf(stage);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 30 + String.valueOf(valueOf2).length());
        sb.append("Lifecycle status change: ");
        sb.append(valueOf);
        sb.append(" in: ");
        sb.append(valueOf2);
        Log.d(TAG, sb.toString());
        Iterator<ActivityStatus> it = this.activityStatuses.iterator();
        boolean z = true;
        while (it.hasNext()) {
            ActivityStatus next = it.next();
            Activity activity2 = (Activity) next.activityRef.get();
            if (activity2 == null) {
                it.remove();
            } else if (activity == activity2) {
                Stage unused = next.lifecycleStage = stage;
                z = false;
            }
        }
        if (z) {
            this.activityStatuses.add(new ActivityStatus(activity, stage));
        }
        synchronized (this.callbacks) {
            Iterator<WeakReference<ActivityLifecycleCallback>> it2 = this.callbacks.iterator();
            while (it2.hasNext()) {
                ActivityLifecycleCallback activityLifecycleCallback = (ActivityLifecycleCallback) it2.next().get();
                if (activityLifecycleCallback == null) {
                    it2.remove();
                } else {
                    try {
                        activityLifecycleCallback.onActivityLifecycleChanged(activity, stage);
                    } catch (RuntimeException e) {
                        Log.e(TAG, String.format("Callback threw exception! (callback: %s activity: %s stage: %s)", new Object[]{activityLifecycleCallback, activity, stage}), e);
                    }
                }
            }
        }
    }

    private void checkMainThread() {
        if (!this.declawThreadCheck && !Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
            throw new IllegalStateException("Querying activity state off main thread is not allowed.");
        }
    }

    private static class ActivityStatus {
        /* access modifiers changed from: private */
        public final WeakReference<Activity> activityRef;
        /* access modifiers changed from: private */
        public Stage lifecycleStage;

        ActivityStatus(Activity activity, Stage stage) {
            this.activityRef = new WeakReference<>((Activity) Checks.checkNotNull(activity));
            this.lifecycleStage = (Stage) Checks.checkNotNull(stage);
        }
    }
}
