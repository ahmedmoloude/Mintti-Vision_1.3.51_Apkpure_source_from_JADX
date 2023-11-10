package androidx.test.runner;

import android.util.Log;
import androidx.test.internal.runner.RunnerArgs;
import androidx.test.internal.runner.tracker.UsageTracker;
import androidx.test.internal.runner.tracker.UsageTrackerRegistry;
import androidx.test.internal.util.Checks;

public class UsageTrackerFacilitator implements UsageTracker {
    private static final String TAG = "UsageTrackerFacilitator";
    private final boolean shouldTrackUsage;

    public UsageTrackerFacilitator(RunnerArgs runnerArgs) {
        Checks.checkNotNull(runnerArgs, "runnerArgs cannot be null!");
        boolean z = true;
        if (runnerArgs.orchestratorService != null) {
            this.shouldTrackUsage = (runnerArgs.disableAnalytics || !runnerArgs.listTestsForOrchestrator) ? false : z;
        } else {
            this.shouldTrackUsage = !runnerArgs.disableAnalytics;
        }
    }

    public UsageTrackerFacilitator(boolean z) {
        this.shouldTrackUsage = z;
    }

    public boolean shouldTrackUsage() {
        return this.shouldTrackUsage;
    }

    public void registerUsageTracker(UsageTracker usageTracker) {
        if (usageTracker == null || !shouldTrackUsage()) {
            Log.i(TAG, "Usage tracking disabled");
            UsageTrackerRegistry.registerInstance(new UsageTracker.NoOpUsageTracker());
            return;
        }
        Log.i(TAG, "Usage tracking enabled");
        UsageTrackerRegistry.registerInstance(usageTracker);
    }

    public void trackUsage(String str, String str2) {
        if (shouldTrackUsage()) {
            UsageTrackerRegistry.getInstance().trackUsage(str, str2);
        }
    }

    public void sendUsages() {
        if (shouldTrackUsage()) {
            UsageTrackerRegistry.getInstance().sendUsages();
        }
    }
}
