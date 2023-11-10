package androidx.test.espresso.matcher;

import android.app.Activity;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import androidx.test.espresso.Root;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import androidx.test.espresso.remote.annotation.RemoteMsgConstructor;
import androidx.test.espresso.remote.annotation.RemoteMsgField;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

public final class RootMatchers {
    public static final Matcher<Root> DEFAULT = Matchers.allOf(hasWindowLayoutParams(), Matchers.allOf(Matchers.anyOf(Matchers.allOf(isDialog(), withDecorView(hasWindowFocus())), isSubwindowOfCurrentActivity()), isFocusable()));
    private static final String TAG = "RootMatchers";

    static final class HasWindowFocus extends TypeSafeMatcher<View> {
        public void describeTo(Description description) {
            description.appendText("has window focus");
        }

        public boolean matchesSafely(View view) {
            return view.hasWindowFocus();
        }
    }

    static final class HasWindowLayoutParams extends TypeSafeMatcher<Root> {
        public void describeTo(Description description) {
            description.appendText("has window layout params");
        }

        public boolean matchesSafely(Root root) {
            return root.getWindowLayoutParams().isPresent();
        }
    }

    static final class IsDialog extends TypeSafeMatcher<Root> {
        public void describeTo(Description description) {
            description.appendText("is dialog");
        }

        public boolean matchesSafely(Root root) {
            int i = root.getWindowLayoutParams().get().type;
            if (i == 1 || i >= 99 || root.getDecorView().getWindowToken() != root.getDecorView().getApplicationWindowToken()) {
                return false;
            }
            return true;
        }
    }

    static final class IsFocusable extends TypeSafeMatcher<Root> {
        public void describeTo(Description description) {
            description.appendText("is focusable");
        }

        public boolean matchesSafely(Root root) {
            return (root.getWindowLayoutParams().get().flags & 8) != 8;
        }
    }

    static final class IsPlatformPopup extends TypeSafeMatcher<Root> {
        public void describeTo(Description description) {
            description.appendText("with decor view of type PopupWindow$PopupViewContainer");
        }

        public boolean matchesSafely(Root root) {
            return RootMatchers.withDecorView(ViewMatchers.withClassName(Matchers.m1139is(Build.VERSION.SDK_INT >= 23 ? "android.widget.PopupWindow$PopupDecorView" : "android.widget.PopupWindow$PopupViewContainer"))).matches(root);
        }
    }

    static final class IsSubwindowOfCurrentActivity extends TypeSafeMatcher<Root> {
        public void describeTo(Description description) {
            description.appendText("is subwindow of current activity");
        }

        public boolean matchesSafely(Root root) {
            return RootMatchers.getResumedActivityTokens().contains(root.getDecorView().getApplicationWindowToken());
        }
    }

    static final class IsSystemAlertWindow extends TypeSafeMatcher<Root> {
        public void describeTo(Description description) {
            description.appendText("is system alert window");
        }

        public boolean matchesSafely(Root root) {
            int i = root.getWindowLayoutParams().get().type;
            return i > 2000 && i < 2999 && root.getDecorView().getWindowToken() == root.getDecorView().getApplicationWindowToken();
        }
    }

    static final class IsTouchable extends TypeSafeMatcher<Root> {
        public void describeTo(Description description) {
            description.appendText("is touchable");
        }

        public boolean matchesSafely(Root root) {
            return (root.getWindowLayoutParams().get().flags & 16) != 16;
        }
    }

    static final class WithDecorView extends TypeSafeMatcher<Root> {
        @RemoteMsgField(order = 0)
        private final Matcher<View> decorViewMatcher;

        @RemoteMsgConstructor
        public WithDecorView(Matcher<View> matcher) {
            this.decorViewMatcher = matcher;
        }

        public void describeTo(Description description) {
            description.appendText("with decor view ");
            this.decorViewMatcher.describeTo(description);
        }

        public boolean matchesSafely(Root root) {
            return this.decorViewMatcher.matches(root.getDecorView());
        }
    }

    private RootMatchers() {
    }

    /* access modifiers changed from: private */
    public static List<IBinder> getResumedActivityTokens() {
        Collection<Activity> activitiesInStage = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
        if (activitiesInStage.isEmpty()) {
            Log.w(TAG, "suppressed: NoActivityResumedException(\"At least one activity should be in RESUMED stage.\"");
        }
        ArrayList newArrayList = Lists.newArrayList();
        for (Activity window : activitiesInStage) {
            newArrayList.add(window.getWindow().getDecorView().getApplicationWindowToken());
        }
        return newArrayList;
    }

    private static Matcher<View> hasWindowFocus() {
        return new HasWindowFocus();
    }

    public static Matcher<Root> hasWindowLayoutParams() {
        return new HasWindowLayoutParams();
    }

    public static Matcher<Root> isDialog() {
        return new IsDialog();
    }

    public static Matcher<Root> isFocusable() {
        return new IsFocusable();
    }

    public static Matcher<Root> isPlatformPopup() {
        return new IsPlatformPopup();
    }

    private static Matcher<Root> isSubwindowOfCurrentActivity() {
        return new IsSubwindowOfCurrentActivity();
    }

    public static Matcher<Root> isSystemAlertWindow() {
        return new IsSystemAlertWindow();
    }

    public static Matcher<Root> isTouchable() {
        return new IsTouchable();
    }

    public static Matcher<Root> withDecorView(Matcher<View> matcher) {
        Preconditions.checkNotNull(matcher);
        return new WithDecorView(matcher);
    }
}
