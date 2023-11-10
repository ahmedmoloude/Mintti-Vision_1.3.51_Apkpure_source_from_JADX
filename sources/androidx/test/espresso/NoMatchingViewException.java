package androidx.test.espresso;

import android.view.View;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import androidx.test.espresso.util.EspressoOptional;
import androidx.test.espresso.util.HumanReadables;
import java.util.List;
import java.util.Locale;
import org.hamcrest.Matcher;

public final class NoMatchingViewException extends RuntimeException implements EspressoException {
    /* access modifiers changed from: private */
    public EspressoOptional<String> adapterViewWarning;
    /* access modifiers changed from: private */
    public List<View> adapterViews;
    /* access modifiers changed from: private */
    public boolean includeViewHierarchy;
    /* access modifiers changed from: private */
    public View rootView;
    /* access modifiers changed from: private */
    public Matcher<? super View> viewMatcher;

    public static class Builder {
        /* access modifiers changed from: private */
        public EspressoOptional<String> adapterViewWarning = EspressoOptional.absent();
        /* access modifiers changed from: private */
        public List<View> adapterViews = Lists.newArrayList();
        /* access modifiers changed from: private */
        public Throwable cause;
        /* access modifiers changed from: private */
        public boolean includeViewHierarchy = true;
        /* access modifiers changed from: private */
        public View rootView;
        /* access modifiers changed from: private */
        public Matcher<? super View> viewMatcher;

        public NoMatchingViewException build() {
            Preconditions.checkNotNull(this.viewMatcher);
            Preconditions.checkNotNull(this.rootView);
            Preconditions.checkNotNull(this.adapterViews);
            Preconditions.checkNotNull(this.adapterViewWarning);
            return new NoMatchingViewException(this);
        }

        public Builder from(NoMatchingViewException noMatchingViewException) {
            this.viewMatcher = noMatchingViewException.viewMatcher;
            this.rootView = noMatchingViewException.rootView;
            this.adapterViews = noMatchingViewException.adapterViews;
            this.adapterViewWarning = noMatchingViewException.adapterViewWarning;
            this.includeViewHierarchy = noMatchingViewException.includeViewHierarchy;
            return this;
        }

        public Builder includeViewHierarchy(boolean z) {
            this.includeViewHierarchy = z;
            return this;
        }

        public Builder withAdapterViewWarning(EspressoOptional<String> espressoOptional) {
            this.adapterViewWarning = espressoOptional;
            return this;
        }

        public Builder withAdapterViews(List<View> list) {
            this.adapterViews = list;
            return this;
        }

        public Builder withCause(Throwable th) {
            this.cause = th;
            return this;
        }

        public Builder withRootView(View view) {
            this.rootView = view;
            return this;
        }

        public Builder withViewMatcher(Matcher<? super View> matcher) {
            this.viewMatcher = matcher;
            return this;
        }
    }

    private NoMatchingViewException(Builder builder) {
        super(getErrorMessage(builder), builder.cause);
        this.adapterViews = Lists.newArrayList();
        this.includeViewHierarchy = true;
        this.adapterViewWarning = EspressoOptional.absent();
        this.viewMatcher = builder.viewMatcher;
        this.rootView = builder.rootView;
        this.adapterViews = builder.adapterViews;
        this.adapterViewWarning = builder.adapterViewWarning;
        this.includeViewHierarchy = builder.includeViewHierarchy;
    }

    private static String getErrorMessage(Builder builder) {
        if (builder.includeViewHierarchy) {
            String format = String.format(Locale.ROOT, "No views in hierarchy found matching: %s", new Object[]{builder.viewMatcher});
            if (builder.adapterViewWarning.isPresent()) {
                String valueOf = String.valueOf(format);
                String valueOf2 = String.valueOf((String) builder.adapterViewWarning.get());
                format = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            }
            return HumanReadables.getViewHierarchyErrorMessage(builder.rootView, (List<View>) null, format, (String) null);
        }
        return String.format(Locale.ROOT, "Could not find a view that matches %s", new Object[]{builder.viewMatcher});
    }

    public String getViewMatcherDescription() {
        Matcher<? super View> matcher = this.viewMatcher;
        return matcher != null ? matcher.toString() : "unknown";
    }

    private NoMatchingViewException(String str) {
        super(str);
        this.adapterViews = Lists.newArrayList();
        this.includeViewHierarchy = true;
        this.adapterViewWarning = EspressoOptional.absent();
    }
}
