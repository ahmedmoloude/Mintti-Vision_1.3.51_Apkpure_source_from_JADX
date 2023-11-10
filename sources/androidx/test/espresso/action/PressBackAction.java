package androidx.test.espresso.action;

import android.app.Activity;
import android.view.View;
import androidx.test.espresso.UiController;
import androidx.test.espresso.action.EspressoKey;
import org.hamcrest.Matcher;

public final class PressBackAction extends KeyEventActionBase {
    private final boolean conditional;

    public PressBackAction(boolean z) {
        this(z, new EspressoKey.Builder().withKeyCode(4).build());
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
        waitForStageChangeInitialActivity(uiController, currentActivity);
        waitForPendingForegroundActivities(uiController, this.conditional);
    }

    public PressBackAction(boolean z, EspressoKey espressoKey) {
        super(espressoKey);
        this.conditional = z;
    }
}
