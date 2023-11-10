package androidx.test.espresso.action;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import java.util.Collection;
import java.util.concurrent.TimeoutException;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public final class CloseKeyboardAction implements ViewAction {
    private static final int NUM_RETRIES = 3;
    private static final String TAG = "CloseKeyboardAction";

    private static class CloseKeyboardIdlingResult extends ResultReceiver implements IdlingResource {
        private final Handler handler;
        /* access modifiers changed from: private */
        public boolean idle;
        /* access modifiers changed from: private */
        public boolean receivedResult;
        /* access modifiers changed from: private */
        public IdlingResource.ResourceCallback resourceCallback;
        /* access modifiers changed from: private */
        public int result;
        /* access modifiers changed from: private */
        public boolean timedOut;

        private CloseKeyboardIdlingResult(Handler handler2) {
            super(handler2);
            this.receivedResult = false;
            this.result = -1;
            this.timedOut = false;
            this.idle = false;
            this.handler = handler2;
        }

        private void notifyEspresso(long j) {
            Preconditions.checkState(this.receivedResult);
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    boolean unused = CloseKeyboardIdlingResult.this.idle = true;
                    if (CloseKeyboardIdlingResult.this.resourceCallback != null) {
                        CloseKeyboardIdlingResult.this.resourceCallback.onTransitionToIdle();
                    }
                }
            }, j);
        }

        /* access modifiers changed from: private */
        public void scheduleTimeout(long j) {
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    if (!CloseKeyboardIdlingResult.this.receivedResult) {
                        boolean unused = CloseKeyboardIdlingResult.this.timedOut = true;
                        if (CloseKeyboardIdlingResult.this.resourceCallback != null) {
                            CloseKeyboardIdlingResult.this.resourceCallback.onTransitionToIdle();
                        }
                    }
                }
            }, j);
        }

        public String getName() {
            return "CloseKeyboardIdlingResource";
        }

        public boolean isIdleNow() {
            return this.idle || this.timedOut;
        }

        /* access modifiers changed from: protected */
        public void onReceiveResult(int i, Bundle bundle) {
            this.result = i;
            this.receivedResult = true;
            notifyEspresso(300);
        }

        public void registerIdleTransitionCallback(IdlingResource.ResourceCallback resourceCallback2) {
            this.resourceCallback = resourceCallback2;
        }
    }

    private static Activity getRootActivity(UiController uiController) {
        Collection<Activity> activitiesInStage = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
        if (activitiesInStage.isEmpty()) {
            uiController.loopMainThreadUntilIdle();
            activitiesInStage = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
        }
        boolean z = true;
        if (activitiesInStage.size() != 1) {
            z = false;
        }
        Preconditions.checkState(z, "More than one activity is in RESUMED stage. There may have been an error during the activity creation/startup process, please check your logs.");
        return (Activity) Iterables.getOnlyElement(activitiesInStage);
    }

    public Matcher<View> getConstraints() {
        return Matchers.any(View.class);
    }

    public String getDescription() {
        return "close keyboard";
    }

    private void tryToCloseKeyboard(View view, UiController uiController) throws TimeoutException {
        InputMethodManager inputMethodManager = (InputMethodManager) getRootActivity(uiController).getSystemService("input_method");
        CloseKeyboardIdlingResult closeKeyboardIdlingResult = new CloseKeyboardIdlingResult(new Handler(Looper.getMainLooper()));
        IdlingRegistry.getInstance().register(closeKeyboardIdlingResult);
        try {
            if (!inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0, closeKeyboardIdlingResult)) {
                Log.w(TAG, "Attempting to close soft keyboard, while it is not shown.");
                return;
            }
            closeKeyboardIdlingResult.scheduleTimeout(2000);
            uiController.loopMainThreadUntilIdle();
            if (!closeKeyboardIdlingResult.timedOut) {
                IdlingRegistry.getInstance().unregister(closeKeyboardIdlingResult);
                if (closeKeyboardIdlingResult.result != 1 && closeKeyboardIdlingResult.result != 3) {
                    int access$300 = closeKeyboardIdlingResult.result;
                    StringBuilder sb = new StringBuilder(105);
                    sb.append("Attempt to close the soft keyboard did not result in soft keyboard to be hidden. resultCode = ");
                    sb.append(access$300);
                    String sb2 = sb.toString();
                    Log.e(TAG, sb2);
                    throw new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(new RuntimeException(sb2)).build();
                }
                return;
            }
            throw new TimeoutException("Wait on operation result timed out.");
        } finally {
            IdlingRegistry.getInstance().unregister(closeKeyboardIdlingResult);
        }
    }

    public void perform(UiController uiController, View view) {
        int i = 0;
        while (i < 3) {
            try {
                tryToCloseKeyboard(view, uiController);
                return;
            } catch (TimeoutException e) {
                Log.w(TAG, "Caught timeout exception. Retrying.");
                if (i != 2) {
                    i++;
                } else {
                    throw new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(e).build();
                }
            }
        }
    }
}
