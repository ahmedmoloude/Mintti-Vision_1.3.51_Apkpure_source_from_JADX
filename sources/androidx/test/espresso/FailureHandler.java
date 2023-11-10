package androidx.test.espresso;

import android.view.View;
import org.hamcrest.Matcher;

public interface FailureHandler {
    void handle(Throwable th, Matcher<View> matcher);
}
