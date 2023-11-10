package androidx.test.espresso.action;

import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import androidx.test.espresso.InjectEventSecurityException;
import androidx.test.espresso.NoActivityResumedException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.ActivityLifecycles;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import java.util.Locale;
import org.hamcrest.Matcher;

class KeyEventActionBase implements ViewAction {
    public static final int BACK_ACTIVITY_TRANSITION_MILLIS_DELAY = 150;
    public static final int CLEAR_TRANSITIONING_ACTIVITIES_ATTEMPTS = 4;
    public static final int CLEAR_TRANSITIONING_ACTIVITIES_MILLIS_DELAY = 150;
    private static final String TAG = "KeyEventActionBase";
    final EspressoKey espressoKey;

    KeyEventActionBase(EspressoKey espressoKey2) {
        this.espressoKey = (EspressoKey) Preconditions.checkNotNull(espressoKey2);
    }

    static Activity getCurrentActivity() {
        return (Activity) Iterables.getOnlyElement(ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED));
    }

    private static boolean isActivityResumed(Activity activity) {
        return ActivityLifecycleMonitorRegistry.getInstance().getLifecycleStageOf(activity) == Stage.RESUMED;
    }

    static void waitForPendingForegroundActivities(UiController uiController, boolean z) {
        ActivityLifecycleMonitor instance = ActivityLifecycleMonitorRegistry.getInstance();
        boolean z2 = false;
        for (int i = 0; i < 4; i++) {
            uiController.loopMainThreadUntilIdle();
            z2 = ActivityLifecycles.hasTransitioningActivities(instance);
            if (!z2) {
                break;
            }
            uiController.loopMainThreadForAtLeast(150);
        }
        if (!ActivityLifecycles.hasForegroundActivities(instance)) {
            if (!z) {
                Log.w(TAG, "Pressed back and hopped to a different process or potentially killed the app");
            } else {
                throw new NoActivityResumedException("Pressed back and killed the app");
            }
        }
        if (z2) {
            Log.w(TAG, "Back was pressed and left the application in an inconsistent state even after 600ms.");
        }
    }

    static void waitForStageChangeInitialActivity(UiController uiController, Activity activity) {
        if (isActivityResumed(activity)) {
            uiController.loopMainThreadForAtLeast(150);
            if (isActivityResumed(activity)) {
                Log.i(TAG, "Back was pressed but there was no Activity stage transition in 150ms. Pressing back may trigger an activity stage transition if the activity is finished as a result. However, the activity may handle the back behavior in any number of other ways internally as well, such as popping the fragment back stack, dismissing a dialog, otherwise manually transacting fragments, etc.");
            }
        }
    }

    public Matcher<View> getConstraints() {
        return ViewMatchers.isDisplayed();
    }

    public String getDescription() {
        return String.format(Locale.ROOT, "send %s key event", new Object[]{this.espressoKey});
    }

    public void perform(UiController uiController, View view) {
        try {
            if (!sendKeyEvent(uiController)) {
                String valueOf = String.valueOf(this.espressoKey);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 36);
                sb.append("Failed to inject espressoKey event: ");
                sb.append(valueOf);
                Log.e(TAG, sb.toString());
                PerformException.Builder withViewDescription = new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view));
                String valueOf2 = String.valueOf(this.espressoKey);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 35);
                sb2.append("Failed to inject espressoKey event ");
                sb2.append(valueOf2);
                throw withViewDescription.withCause(new RuntimeException(sb2.toString())).build();
            }
        } catch (InjectEventSecurityException e) {
            String valueOf3 = String.valueOf(this.espressoKey);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 36);
            sb3.append("Failed to inject espressoKey event: ");
            sb3.append(valueOf3);
            Log.e(TAG, sb3.toString());
            throw new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(e).build();
        }
    }

    private boolean sendKeyEvent(UiController uiController) throws InjectEventSecurityException {
        UiController uiController2 = uiController;
        long uptimeMillis = SystemClock.uptimeMillis();
        boolean z = false;
        boolean z2 = false;
        int i = 0;
        while (!z2 && i < 4) {
            z2 = uiController2.injectKeyEvent(new KeyEvent(uptimeMillis, uptimeMillis, 0, this.espressoKey.getKeyCode(), 0, this.espressoKey.getMetaState()));
            i++;
        }
        if (!z2) {
            return false;
        }
        long uptimeMillis2 = SystemClock.uptimeMillis();
        int i2 = 0;
        while (!z && i2 < 4) {
            z = uiController2.injectKeyEvent(new KeyEvent(uptimeMillis2, uptimeMillis2, 1, this.espressoKey.getKeyCode(), 0, this.espressoKey.getMetaState()));
            i2++;
        }
        return z;
    }
}
