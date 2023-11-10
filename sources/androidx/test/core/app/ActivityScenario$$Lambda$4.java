package androidx.test.core.app;

import androidx.test.core.app.ActivityScenario;

/* compiled from: ActivityScenario */
final /* synthetic */ class ActivityScenario$$Lambda$4 implements Runnable {
    private final ActivityScenario arg$1;
    private final ActivityScenario.ActivityAction arg$2;

    ActivityScenario$$Lambda$4(ActivityScenario activityScenario, ActivityScenario.ActivityAction activityAction) {
        this.arg$1 = activityScenario;
        this.arg$2 = activityAction;
    }

    public void run() {
        this.arg$1.lambda$onActivity$2$ActivityScenario(this.arg$2);
    }
}
