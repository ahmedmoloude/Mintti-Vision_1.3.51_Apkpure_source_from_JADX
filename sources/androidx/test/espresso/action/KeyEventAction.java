package androidx.test.espresso.action;

import android.app.Activity;
import android.view.View;
import androidx.test.espresso.UiController;
import org.hamcrest.Matcher;

public final class KeyEventAction extends KeyEventActionBase {
    public KeyEventAction(EspressoKey espressoKey) {
        super(espressoKey);
    }

    public /* bridge */ /* synthetic */ Matcher getConstraints() {
        return super.getConstraints();
    }

    public /* bridge */ /* synthetic */ String getDescription() {
        return super.getDescription();
    }

    public void perform(UiController uiController, View view) {
        Activity currentActivity = getCurrentActivity();
        super.perform(uiController, view);
        if (this.espressoKey.getKeyCode() == 4) {
            waitForStageChangeInitialActivity(uiController, currentActivity);
            waitForPendingForegroundActivities(uiController, true);
        }
    }
}
