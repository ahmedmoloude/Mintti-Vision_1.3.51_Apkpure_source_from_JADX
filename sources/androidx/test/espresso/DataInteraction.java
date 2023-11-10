package androidx.test.espresso;

import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import androidx.test.espresso.action.AdapterDataLoaderAction;
import androidx.test.espresso.action.AdapterViewProtocol;
import androidx.test.espresso.action.AdapterViewProtocols;
import androidx.test.espresso.core.internal.deps.guava.base.Function;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.remote.ConstructorInvocation;
import androidx.test.espresso.remote.annotation.RemoteMsgConstructor;
import androidx.test.espresso.remote.annotation.RemoteMsgField;
import androidx.test.espresso.util.EspressoOptional;
import java.lang.annotation.Annotation;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

public class DataInteraction {
    private Matcher<View> adapterMatcher = ViewMatchers.isAssignableFrom(AdapterView.class);
    private AdapterViewProtocol adapterViewProtocol = AdapterViewProtocols.standardProtocol();
    private EspressoOptional<Integer> atPosition = EspressoOptional.absent();
    private EspressoOptional<Matcher<View>> childViewMatcher = EspressoOptional.absent();
    private final Matcher<? extends Object> dataMatcher;
    private Matcher<Root> rootMatcher = RootMatchers.DEFAULT;

    DataInteraction(Matcher<? extends Object> matcher) {
        this.dataMatcher = (Matcher) Preconditions.checkNotNull(matcher);
    }

    private Matcher<View> makeTargetMatcher() {
        DisplayDataMatcher displayDataMatcher = DisplayDataMatcher.displayDataMatcher(this.adapterMatcher, this.dataMatcher, this.rootMatcher, this.atPosition, this.adapterViewProtocol);
        return this.childViewMatcher.isPresent() ? Matchers.allOf(this.childViewMatcher.get(), ViewMatchers.isDescendantOfA(displayDataMatcher)) : displayDataMatcher;
    }

    public DataInteraction atPosition(Integer num) {
        this.atPosition = EspressoOptional.m77of((Integer) Preconditions.checkNotNull(num));
        return this;
    }

    public ViewInteraction check(ViewAssertion viewAssertion) {
        return Espresso.onView(makeTargetMatcher()).inRoot(this.rootMatcher).check(viewAssertion);
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [org.hamcrest.Matcher<android.view.View>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.test.espresso.DataInteraction inAdapterView(org.hamcrest.Matcher<android.view.View> r1) {
        /*
            r0 = this;
            java.lang.Object r1 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r1)
            org.hamcrest.Matcher r1 = (org.hamcrest.Matcher) r1
            r0.adapterMatcher = r1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.DataInteraction.inAdapterView(org.hamcrest.Matcher):androidx.test.espresso.DataInteraction");
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [org.hamcrest.Matcher<androidx.test.espresso.Root>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.test.espresso.DataInteraction inRoot(org.hamcrest.Matcher<androidx.test.espresso.Root> r1) {
        /*
            r0 = this;
            java.lang.Object r1 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r1)
            org.hamcrest.Matcher r1 = (org.hamcrest.Matcher) r1
            r0.rootMatcher = r1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.DataInteraction.inRoot(org.hamcrest.Matcher):androidx.test.espresso.DataInteraction");
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [org.hamcrest.Matcher<android.view.View>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.test.espresso.DataInteraction onChildView(org.hamcrest.Matcher<android.view.View> r1) {
        /*
            r0 = this;
            java.lang.Object r1 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r1)
            org.hamcrest.Matcher r1 = (org.hamcrest.Matcher) r1
            androidx.test.espresso.util.EspressoOptional r1 = androidx.test.espresso.util.EspressoOptional.m77of(r1)
            r0.childViewMatcher = r1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.DataInteraction.onChildView(org.hamcrest.Matcher):androidx.test.espresso.DataInteraction");
    }

    public ViewInteraction perform(ViewAction... viewActionArr) {
        return Espresso.onView(makeTargetMatcher()).inRoot(this.rootMatcher).perform(viewActionArr);
    }

    public DataInteraction usingAdapterViewProtocol(AdapterViewProtocol adapterViewProtocol2) {
        this.adapterViewProtocol = (AdapterViewProtocol) Preconditions.checkNotNull(adapterViewProtocol2);
        return this;
    }

    public static final class DisplayDataMatcher extends TypeSafeMatcher<View> {
        private static final String TAG = "DisplayDataMatcher";
        @RemoteMsgField(order = 3)
        private final AdapterDataLoaderAction adapterDataLoaderAction;
        @RemoteMsgField(order = 0)
        private final Matcher<View> adapterMatcher;
        private final AdapterViewProtocol adapterViewProtocol;
        @RemoteMsgField(order = 2)
        private final Class<? extends AdapterViewProtocol> adapterViewProtocolClass;
        @RemoteMsgField(order = 1)
        private final Matcher<? extends Object> dataMatcher;

        /* JADX WARNING: type inference failed for: r1v0, types: [org.hamcrest.Matcher<android.view.View>, java.lang.Object] */
        /* JADX WARNING: type inference failed for: r5v0, types: [java.lang.Object, androidx.test.espresso.core.internal.deps.guava.base.Function<androidx.test.espresso.action.AdapterDataLoaderAction, androidx.test.espresso.ViewInteraction>] */
        /* JADX WARNING: Unknown variable types count: 2 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        DisplayDataMatcher(org.hamcrest.Matcher<android.view.View> r1, org.hamcrest.Matcher<? extends java.lang.Object> r2, androidx.test.espresso.action.AdapterViewProtocol r3, androidx.test.espresso.action.AdapterDataLoaderAction r4, androidx.test.espresso.core.internal.deps.guava.base.Function<androidx.test.espresso.action.AdapterDataLoaderAction, androidx.test.espresso.ViewInteraction> r5) {
            /*
                r0 = this;
                r0.<init>()
                java.lang.Object r1 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r1)
                org.hamcrest.Matcher r1 = (org.hamcrest.Matcher) r1
                r0.adapterMatcher = r1
                java.lang.Object r1 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
                org.hamcrest.Matcher r1 = (org.hamcrest.Matcher) r1
                r0.dataMatcher = r1
                java.lang.Object r1 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r3)
                androidx.test.espresso.action.AdapterViewProtocol r1 = (androidx.test.espresso.action.AdapterViewProtocol) r1
                r0.adapterViewProtocol = r1
                java.lang.Class r1 = r3.getClass()
                r0.adapterViewProtocolClass = r1
                java.lang.Object r1 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r4)
                androidx.test.espresso.action.AdapterDataLoaderAction r1 = (androidx.test.espresso.action.AdapterDataLoaderAction) r1
                r0.adapterDataLoaderAction = r1
                java.lang.Object r1 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r5)
                androidx.test.espresso.core.internal.deps.guava.base.Function r1 = (androidx.test.espresso.core.internal.deps.guava.base.Function) r1
                r1.apply(r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.DataInteraction.DisplayDataMatcher.<init>(org.hamcrest.Matcher, org.hamcrest.Matcher, androidx.test.espresso.action.AdapterViewProtocol, androidx.test.espresso.action.AdapterDataLoaderAction, androidx.test.espresso.core.internal.deps.guava.base.Function):void");
        }

        public static DisplayDataMatcher displayDataMatcher(Matcher<View> matcher, Matcher<? extends Object> matcher2, Matcher<Root> matcher3, EspressoOptional<Integer> espressoOptional, AdapterViewProtocol adapterViewProtocol2) {
            return new DisplayDataMatcher(matcher, matcher2, matcher3, adapterViewProtocol2, new AdapterDataLoaderAction(matcher2, espressoOptional, adapterViewProtocol2));
        }

        public void describeTo(Description description) {
            description.appendText(" displaying data matching: ");
            this.dataMatcher.describeTo(description);
            description.appendText(" within adapter view matching: ");
            this.adapterMatcher.describeTo(description);
        }

        public boolean matchesSafely(View view) {
            Preconditions.checkState(this.adapterViewProtocol != null, "adapterViewProtocol cannot be null!");
            ViewParent parent = view.getParent();
            while (parent != null && !(parent instanceof AdapterView)) {
                parent = parent.getParent();
            }
            if (parent != null && this.adapterMatcher.matches(parent)) {
                EspressoOptional<AdapterViewProtocol.AdaptedData> dataRenderedByView = this.adapterViewProtocol.getDataRenderedByView((AdapterView) parent, view);
                if (dataRenderedByView.isPresent()) {
                    return dataRenderedByView.get().opaqueToken.equals(this.adapterDataLoaderAction.getAdaptedData().opaqueToken);
                }
            }
            return false;
        }

        @RemoteMsgConstructor
        DisplayDataMatcher(Matcher<View> matcher, Matcher<? extends Object> matcher2, Class<? extends AdapterViewProtocol> cls, AdapterDataLoaderAction adapterDataLoaderAction2) throws IllegalAccessException, InstantiationException {
            this(matcher, matcher2, RootMatchers.DEFAULT, (AdapterViewProtocol) cls.cast(new ConstructorInvocation(cls, (Class<? extends Annotation>) null, new Class[0]).invokeConstructor(new Object[0])), adapterDataLoaderAction2);
        }

        private DisplayDataMatcher(final Matcher<View> matcher, Matcher<? extends Object> matcher2, final Matcher<Root> matcher3, AdapterViewProtocol adapterViewProtocol2, AdapterDataLoaderAction adapterDataLoaderAction2) {
            this(matcher, matcher2, adapterViewProtocol2, adapterDataLoaderAction2, (Function<AdapterDataLoaderAction, ViewInteraction>) new Function<AdapterDataLoaderAction, ViewInteraction>() {
                public ViewInteraction apply(AdapterDataLoaderAction adapterDataLoaderAction) {
                    return Espresso.onView(Matcher.this).inRoot(matcher3).perform(adapterDataLoaderAction);
                }
            });
        }
    }
}
