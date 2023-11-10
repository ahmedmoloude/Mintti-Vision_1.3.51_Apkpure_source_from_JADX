package androidx.test.runner.lifecycle;

import java.util.concurrent.atomic.AtomicReference;

public final class ActivityLifecycleMonitorRegistry {
    private static final AtomicReference<ActivityLifecycleMonitor> lifecycleMonitor = new AtomicReference<>((Object) null);

    private ActivityLifecycleMonitorRegistry() {
    }

    public static ActivityLifecycleMonitor getInstance() {
        ActivityLifecycleMonitor activityLifecycleMonitor = lifecycleMonitor.get();
        if (activityLifecycleMonitor != null) {
            return activityLifecycleMonitor;
        }
        throw new IllegalStateException("No lifecycle monitor registered! Are you running under an Instrumentation which registers lifecycle monitors?");
    }

    public static void registerInstance(ActivityLifecycleMonitor activityLifecycleMonitor) {
        lifecycleMonitor.set(activityLifecycleMonitor);
    }
}
