package androidx.test.espresso.action;

import android.view.View;
import android.widget.EditText;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.remote.annotation.RemoteMsgConstructor;
import androidx.test.espresso.remote.annotation.RemoteMsgField;
import java.util.Locale;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public final class ReplaceTextAction implements ViewAction {
    @RemoteMsgField(order = 0)
    final String stringToBeSet;

    @RemoteMsgConstructor
    public ReplaceTextAction(String str) {
        Preconditions.checkNotNull(str);
        this.stringToBeSet = str;
    }

    public Matcher<View> getConstraints() {
        return Matchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.isAssignableFrom(EditText.class));
    }

    public String getDescription() {
        return String.format(Locale.ROOT, "replace text(%s)", new Object[]{this.stringToBeSet});
    }

    public void perform(UiController uiController, View view) {
        ((EditText) view).setText(this.stringToBeSet);
    }
}
