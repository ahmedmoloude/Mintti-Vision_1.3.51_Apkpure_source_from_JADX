package androidx.test.espresso;

import android.view.View;
import androidx.test.espresso.base.RootViewPicker;
import androidx.test.espresso.base.ViewFinderImpl;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.remote.RemoteInteraction;
import androidx.test.espresso.remote.RemoteInteractionRegistry;
import java.util.concurrent.atomic.AtomicReference;
import org.hamcrest.Matcher;

class ViewInteractionModule {
    private final AtomicReference<Boolean> needsActivity = new AtomicReference<>(true);
    private final AtomicReference<Matcher<Root>> rootMatcher = new AtomicReference<>(RootMatchers.DEFAULT);
    private final Matcher<View> viewMatcher;

    /* JADX WARNING: type inference failed for: r3v0, types: [org.hamcrest.Matcher<android.view.View>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    ViewInteractionModule(org.hamcrest.Matcher<android.view.View> r3) {
        /*
            r2 = this;
            r2.<init>()
            java.util.concurrent.atomic.AtomicReference r0 = new java.util.concurrent.atomic.AtomicReference
            org.hamcrest.Matcher<androidx.test.espresso.Root> r1 = androidx.test.espresso.matcher.RootMatchers.DEFAULT
            r0.<init>(r1)
            r2.rootMatcher = r0
            java.util.concurrent.atomic.AtomicReference r0 = new java.util.concurrent.atomic.AtomicReference
            r1 = 1
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            r0.<init>(r1)
            r2.needsActivity = r0
            java.lang.Object r3 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r3)
            org.hamcrest.Matcher r3 = (org.hamcrest.Matcher) r3
            r2.viewMatcher = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.ViewInteractionModule.<init>(org.hamcrest.Matcher):void");
    }

    /* access modifiers changed from: package-private */
    public AtomicReference<Boolean> provideNeedsActivity() {
        return this.needsActivity;
    }

    /* access modifiers changed from: package-private */
    public RemoteInteraction provideRemoteInteraction() {
        return RemoteInteractionRegistry.getInstance();
    }

    /* access modifiers changed from: package-private */
    public AtomicReference<Matcher<Root>> provideRootMatcher() {
        return this.rootMatcher;
    }

    public View provideRootView(RootViewPicker rootViewPicker) {
        return rootViewPicker.get();
    }

    /* access modifiers changed from: package-private */
    public ViewFinder provideViewFinder(ViewFinderImpl viewFinderImpl) {
        return viewFinderImpl;
    }

    /* access modifiers changed from: package-private */
    public Matcher<View> provideViewMatcher() {
        return this.viewMatcher;
    }
}
