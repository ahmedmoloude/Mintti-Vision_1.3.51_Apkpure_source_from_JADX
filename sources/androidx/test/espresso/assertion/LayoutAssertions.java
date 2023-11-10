package androidx.test.espresso.assertion;

import android.graphics.Rect;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.core.internal.deps.guava.base.Predicate;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.matcher.LayoutMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.remote.annotation.RemoteMsgConstructor;
import androidx.test.espresso.remote.annotation.RemoteMsgField;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import junit.framework.AssertionFailedError;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public final class LayoutAssertions {

    static class NoOverlapsViewAssertion implements ViewAssertion {
        /* access modifiers changed from: private */
        @RemoteMsgField(order = 0)
        public final Matcher<View> selector;

        @RemoteMsgConstructor
        private NoOverlapsViewAssertion(Matcher<View> matcher) {
            this.selector = matcher;
        }

        public void check(View view, NoMatchingViewException noMatchingViewException) {
            C06741 r0 = new Predicate<View>() {
                public boolean apply(View view) {
                    return NoOverlapsViewAssertion.this.selector.matches(view);
                }
            };
            if (noMatchingViewException == null) {
                LinkedList linkedList = new LinkedList();
                StringBuilder sb = new StringBuilder();
                for (View next : Iterables.filter(TreeIterables.breadthFirstViewTraversal(view), r0)) {
                    Rect access$200 = LayoutAssertions.getRect(next);
                    if (!access$200.isEmpty() && (!(next instanceof TextView) || ((TextView) next).getText().length() != 0)) {
                        Iterator it = linkedList.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            View view2 = (View) it.next();
                            if ((!(next instanceof ImageView) || !(view2 instanceof ImageView)) && Rect.intersects(access$200, LayoutAssertions.getRect(view2))) {
                                if (sb.length() > 0) {
                                    sb.append(",\n\n");
                                }
                                sb.append(String.format(Locale.ROOT, "%s overlaps\n%s", new Object[]{HumanReadables.describe(next), HumanReadables.describe(view2)}));
                            }
                        }
                        linkedList.add(next);
                    }
                }
                if (sb.length() > 0) {
                    throw new AssertionFailedError(sb.toString());
                }
                return;
            }
            throw noMatchingViewException;
        }

        public String toString() {
            return String.format(Locale.ROOT, "NoOverlapsViewAssertion{selector=%s}", new Object[]{this.selector});
        }
    }

    private LayoutAssertions() {
    }

    /* access modifiers changed from: private */
    public static Rect getRect(View view) {
        int[] iArr = {0, 0};
        view.getLocationOnScreen(iArr);
        return new Rect(iArr[0], iArr[1], (iArr[0] + view.getWidth()) - 1, (iArr[1] + view.getHeight()) - 1);
    }

    public static ViewAssertion noEllipsizedText() {
        return ViewAssertions.selectedDescendantsMatch(ViewMatchers.isAssignableFrom(TextView.class), Matchers.not(LayoutMatchers.hasEllipsizedText()));
    }

    public static ViewAssertion noMultilineButtons() {
        return ViewAssertions.selectedDescendantsMatch(ViewMatchers.isAssignableFrom(Button.class), Matchers.not(LayoutMatchers.hasMultilineText()));
    }

    public static ViewAssertion noOverlaps() {
        return noOverlaps(Matchers.allOf(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), Matchers.anyOf(ViewMatchers.isAssignableFrom(TextView.class), ViewMatchers.isAssignableFrom(ImageView.class))));
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [org.hamcrest.Matcher<android.view.View>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static androidx.test.espresso.ViewAssertion noOverlaps(org.hamcrest.Matcher<android.view.View> r2) {
        /*
            androidx.test.espresso.assertion.LayoutAssertions$NoOverlapsViewAssertion r0 = new androidx.test.espresso.assertion.LayoutAssertions$NoOverlapsViewAssertion
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1 = 0
            r0.<init>(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.assertion.LayoutAssertions.noOverlaps(org.hamcrest.Matcher):androidx.test.espresso.ViewAssertion");
    }
}
