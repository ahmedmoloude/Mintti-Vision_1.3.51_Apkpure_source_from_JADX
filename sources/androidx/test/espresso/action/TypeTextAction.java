package androidx.test.espresso.action;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import androidx.test.espresso.InjectEventSecurityException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.remote.annotation.RemoteMsgConstructor;
import androidx.test.espresso.remote.annotation.RemoteMsgField;
import androidx.test.espresso.util.HumanReadables;
import java.util.Locale;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public final class TypeTextAction implements ViewAction {
    private static final String TAG = "TypeTextAction";
    final GeneralClickAction clickAction;
    @RemoteMsgField(order = 0)
    final String stringToBeTyped;
    @RemoteMsgField(order = 1)
    final boolean tapToFocus;

    public TypeTextAction(String str) {
        this(str, true, defaultClickAction());
    }

    private static GeneralClickAction defaultClickAction() {
        return new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER, Press.FINGER, 0, 1);
    }

    public Matcher<View> getConstraints() {
        Matcher<View> allOf = Matchers.allOf((Matcher<? super T>[]) new Matcher[]{ViewMatchers.isDisplayed()});
        if (!this.tapToFocus) {
            allOf = Matchers.allOf(allOf, ViewMatchers.hasFocus());
        }
        if (Build.VERSION.SDK_INT < 11) {
            return Matchers.allOf(allOf, ViewMatchers.supportsInputMethods());
        }
        return Matchers.allOf(allOf, Matchers.anyOf(ViewMatchers.supportsInputMethods(), ViewMatchers.isAssignableFrom(SearchView.class)));
    }

    public String getDescription() {
        return String.format(Locale.ROOT, "type text(%s)", new Object[]{this.stringToBeTyped});
    }

    public void perform(UiController uiController, View view) {
        if (this.stringToBeTyped.length() == 0) {
            Log.w(TAG, "Supplied string is empty resulting in no-op (nothing is typed).");
            return;
        }
        if (this.tapToFocus) {
            GeneralClickAction generalClickAction = this.clickAction;
            if (generalClickAction == null) {
                defaultClickAction().perform(uiController, view);
            } else {
                generalClickAction.perform(uiController, view);
            }
            uiController.loopMainThreadUntilIdle();
        }
        try {
            if (!uiController.injectString(this.stringToBeTyped)) {
                String str = TAG;
                String valueOf = String.valueOf(this.stringToBeTyped);
                Log.e(str, valueOf.length() != 0 ? "Failed to type text: ".concat(valueOf) : new String("Failed to type text: "));
                PerformException.Builder withViewDescription = new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view));
                String valueOf2 = String.valueOf(this.stringToBeTyped);
                throw withViewDescription.withCause(new RuntimeException(valueOf2.length() != 0 ? "Failed to type text: ".concat(valueOf2) : new String("Failed to type text: "))).build();
            }
        } catch (InjectEventSecurityException e) {
            String str2 = TAG;
            String valueOf3 = String.valueOf(this.stringToBeTyped);
            Log.e(str2, valueOf3.length() != 0 ? "Failed to type text: ".concat(valueOf3) : new String("Failed to type text: "));
            throw new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(e).build();
        }
    }

    @RemoteMsgConstructor
    public TypeTextAction(String str, boolean z) {
        this(str, z, (GeneralClickAction) null);
    }

    public TypeTextAction(String str, boolean z, GeneralClickAction generalClickAction) {
        Preconditions.checkNotNull(str);
        this.stringToBeTyped = str;
        this.tapToFocus = z;
        this.clickAction = generalClickAction;
    }
}
