package androidx.test.espresso.matcher;

import android.view.View;
import androidx.test.espresso.core.internal.deps.guava.base.Predicate;
import androidx.test.espresso.matcher.ViewMatchers;

/* compiled from: ViewMatchers */
final /* synthetic */ class ViewMatchers$HasDescendantMatcher$$Lambda$0 implements Predicate {
    private final ViewMatchers.HasDescendantMatcher arg$1;
    private final View arg$2;

    ViewMatchers$HasDescendantMatcher$$Lambda$0(ViewMatchers.HasDescendantMatcher hasDescendantMatcher, View view) {
        this.arg$1 = hasDescendantMatcher;
        this.arg$2 = view;
    }

    public boolean apply(Object obj) {
        return this.arg$1.lambda$matchesSafely$0$ViewMatchers$HasDescendantMatcher(this.arg$2, (View) obj);
    }
}
