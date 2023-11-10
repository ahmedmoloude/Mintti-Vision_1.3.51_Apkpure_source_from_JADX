package androidx.test.espresso.assertion;

import android.util.Log;
import android.view.View;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.base.Predicate;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.remote.annotation.RemoteMsgConstructor;
import androidx.test.espresso.remote.annotation.RemoteMsgField;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import java.util.ArrayList;
import java.util.Locale;
import junit.framework.AssertionFailedError;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.StringDescription;

public final class ViewAssertions {
    /* access modifiers changed from: private */
    public static final String TAG = "ViewAssertions";

    static class DoesNotExistViewAssertion implements ViewAssertion {
        @RemoteMsgConstructor
        private DoesNotExistViewAssertion() {
        }

        public void check(View view, NoMatchingViewException noMatchingViewException) {
            if (view != null) {
                String valueOf = String.valueOf(HumanReadables.describe(view));
                ViewMatchers.assertThat(valueOf.length() != 0 ? "View is present in the hierarchy: ".concat(valueOf) : new String("View is present in the hierarchy: "), true, Matchers.m1139is(false));
            }
        }
    }

    static class MatchesViewAssertion implements ViewAssertion {
        @RemoteMsgField(order = 0)
        final Matcher<? super View> viewMatcher;

        @RemoteMsgConstructor
        private MatchesViewAssertion(Matcher<? super View> matcher) {
            this.viewMatcher = matcher;
        }

        public void check(View view, NoMatchingViewException noMatchingViewException) {
            StringDescription stringDescription = new StringDescription();
            stringDescription.appendText("'");
            this.viewMatcher.describeTo(stringDescription);
            if (noMatchingViewException == null) {
                stringDescription.appendText("' doesn't match the selected view.");
                ViewMatchers.assertThat(stringDescription.toString(), view, this.viewMatcher);
                return;
            }
            stringDescription.appendText(String.format(Locale.ROOT, "' check could not be performed because view '%s' was not found.\n", new Object[]{noMatchingViewException.getViewMatcherDescription()}));
            Log.e(ViewAssertions.TAG, stringDescription.toString());
            throw noMatchingViewException;
        }

        public String toString() {
            return String.format(Locale.ROOT, "MatchesViewAssertion{viewMatcher=%s}", new Object[]{this.viewMatcher});
        }
    }

    static class SelectedDescendantsMatchViewAssertion implements ViewAssertion {
        @RemoteMsgField(order = 1)
        private final Matcher<View> matcher;
        /* access modifiers changed from: private */
        @RemoteMsgField(order = 0)
        public final Matcher<View> selector;

        @RemoteMsgConstructor
        private SelectedDescendantsMatchViewAssertion(Matcher<View> matcher2, Matcher<View> matcher3) {
            this.selector = matcher2;
            this.matcher = matcher3;
        }

        public void check(View view, NoMatchingViewException noMatchingViewException) {
            Preconditions.checkNotNull(view);
            ArrayList arrayList = new ArrayList();
            for (View next : Iterables.filter(TreeIterables.breadthFirstViewTraversal(view), new Predicate<View>() {
                public boolean apply(View view) {
                    return SelectedDescendantsMatchViewAssertion.this.selector.matches(view);
                }
            })) {
                if (!this.matcher.matches(next)) {
                    arrayList.add(next);
                }
            }
            if (arrayList.size() > 0) {
                throw new AssertionFailedError(HumanReadables.getViewHierarchyErrorMessage(view, arrayList, String.format(Locale.ROOT, "At least one view did not match the required matcher: %s", new Object[]{this.matcher}), "****DOES NOT MATCH****"));
            }
        }

        public String toString() {
            return String.format(Locale.ROOT, "SelectedDescendantsMatchViewAssertion{selector=%s, matcher=%s}", new Object[]{this.selector, this.matcher});
        }
    }

    private ViewAssertions() {
    }

    public static ViewAssertion doesNotExist() {
        return new DoesNotExistViewAssertion();
    }

    public static ViewAssertion matches(Matcher<? super View> matcher) {
        return new MatchesViewAssertion((Matcher) Preconditions.checkNotNull(matcher));
    }

    public static ViewAssertion selectedDescendantsMatch(Matcher<View> matcher, Matcher<View> matcher2) {
        return new SelectedDescendantsMatchViewAssertion(matcher, matcher2);
    }
}
