package androidx.test.espresso.action;

import android.view.View;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.util.HumanReadables;
import java.util.Locale;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public final class RepeatActionUntilViewState implements ViewAction {
    private final ViewAction mAction;
    private final Matcher<View> mDesiredStateMatcher;
    private final int mMaxAttempts;

    protected RepeatActionUntilViewState(ViewAction viewAction, Matcher<View> matcher, int i) {
        Preconditions.checkNotNull(viewAction);
        Preconditions.checkNotNull(matcher);
        Preconditions.checkState(i <= 1 ? false : true, "maxAttempts should be greater than 1");
        this.mAction = viewAction;
        this.mDesiredStateMatcher = matcher;
        this.mMaxAttempts = i;
    }

    public Matcher<View> getConstraints() {
        return this.mAction.getConstraints();
    }

    public String getDescription() {
        StringDescription stringDescription = new StringDescription();
        this.mDesiredStateMatcher.describeTo(stringDescription);
        return String.format(Locale.ROOT, "%s until: %s", new Object[]{this.mAction.getDescription(), stringDescription});
    }

    public void perform(UiController uiController, View view) {
        int i = 1;
        while (!this.mDesiredStateMatcher.matches(view) && i <= this.mMaxAttempts) {
            this.mAction.perform(uiController, view);
            uiController.loopMainThreadUntilIdle();
            i++;
        }
        if (i > this.mMaxAttempts) {
            throw new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(new RuntimeException(String.format(Locale.ROOT, "Failed to achieve view state after %d attempts", new Object[]{Integer.valueOf(this.mMaxAttempts)}))).build();
        }
    }
}
