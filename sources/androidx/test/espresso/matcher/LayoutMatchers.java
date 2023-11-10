package androidx.test.espresso.matcher;

import android.text.Layout;
import android.view.View;
import android.widget.TextView;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public final class LayoutMatchers {
    private LayoutMatchers() {
    }

    public static Matcher<View> hasEllipsizedText() {
        return new TypeSafeMatcher<View>(TextView.class) {
            public void describeTo(Description description) {
                description.appendText("has ellipsized text");
            }

            public boolean matchesSafely(View view) {
                int lineCount;
                Layout layout = ((TextView) view).getLayout();
                if (layout == null || (lineCount = layout.getLineCount()) <= 0 || layout.getEllipsisCount(lineCount - 1) <= 0) {
                    return false;
                }
                return true;
            }
        };
    }

    public static Matcher<View> hasMultilineText() {
        return new TypeSafeMatcher<View>(TextView.class) {
            public void describeTo(Description description) {
                description.appendText("has more than one line of text");
            }

            public boolean matchesSafely(View view) {
                return ((TextView) view).getLineCount() > 1;
            }
        };
    }
}
