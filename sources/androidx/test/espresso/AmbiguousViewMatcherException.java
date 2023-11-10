package androidx.test.espresso;

import android.view.View;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.ImmutableSet;
import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.internal.platform.util.TestOutputEmitter;
import java.util.Locale;
import org.hamcrest.Matcher;

public final class AmbiguousViewMatcherException extends RuntimeException implements EspressoException {
    /* access modifiers changed from: private */
    public View[] others;
    /* access modifiers changed from: private */
    public View rootView;
    /* access modifiers changed from: private */
    public View view1;
    /* access modifiers changed from: private */
    public View view2;
    /* access modifiers changed from: private */
    public Matcher<? super View> viewMatcher;

    public static class Builder {
        /* access modifiers changed from: private */
        public boolean includeViewHierarchy = true;
        /* access modifiers changed from: private */
        public View[] others;
        /* access modifiers changed from: private */
        public View rootView;
        /* access modifiers changed from: private */
        public View view1;
        /* access modifiers changed from: private */
        public View view2;
        /* access modifiers changed from: private */
        public Matcher<? super View> viewMatcher;

        public AmbiguousViewMatcherException build() {
            Preconditions.checkNotNull(this.viewMatcher);
            Preconditions.checkNotNull(this.rootView);
            Preconditions.checkNotNull(this.view1);
            Preconditions.checkNotNull(this.view2);
            Preconditions.checkNotNull(this.others);
            return new AmbiguousViewMatcherException(this);
        }

        public Builder from(AmbiguousViewMatcherException ambiguousViewMatcherException) {
            this.viewMatcher = ambiguousViewMatcherException.viewMatcher;
            this.rootView = ambiguousViewMatcherException.rootView;
            this.view1 = ambiguousViewMatcherException.view1;
            this.view2 = ambiguousViewMatcherException.view2;
            this.others = ambiguousViewMatcherException.others;
            return this;
        }

        public Builder includeViewHierarchy(boolean z) {
            this.includeViewHierarchy = z;
            return this;
        }

        public Builder withOtherAmbiguousViews(View... viewArr) {
            this.others = viewArr;
            return this;
        }

        public Builder withRootView(View view) {
            this.rootView = view;
            return this;
        }

        public Builder withView1(View view) {
            this.view1 = view;
            return this;
        }

        public Builder withView2(View view) {
            this.view2 = view;
            return this;
        }

        public Builder withViewMatcher(Matcher<? super View> matcher) {
            this.viewMatcher = matcher;
            return this;
        }
    }

    private AmbiguousViewMatcherException(Builder builder) {
        super(getErrorMessage(builder));
        this.viewMatcher = builder.viewMatcher;
        this.rootView = builder.rootView;
        this.view1 = builder.view1;
        this.view2 = builder.view2;
        this.others = builder.others;
    }

    private static String getErrorMessage(Builder builder) {
        if (builder.includeViewHierarchy) {
            ImmutableSet build = ImmutableSet.builder().add((E[]) new View[]{builder.view1, builder.view2}).add((E[]) builder.others).build();
            return HumanReadables.getViewHierarchyErrorMessage(builder.rootView, Lists.newArrayList(build), String.format(Locale.ROOT, "'%s' matches multiple views in the hierarchy.", new Object[]{builder.viewMatcher}), "****MATCHES****");
        }
        return String.format(Locale.ROOT, "Multiple Ambiguous Views found for matcher %s", new Object[]{builder.viewMatcher});
    }

    private AmbiguousViewMatcherException(String str) {
        super(str);
        TestOutputEmitter.dumpThreadStates("ThreadState-AmbiguousViewMatcherException.txt");
    }
}
