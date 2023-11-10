package androidx.test.espresso.action;

import android.view.View;
import android.widget.AdapterView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.AdapterViewProtocol;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.EspressoOptional;
import androidx.test.espresso.util.HumanReadables;
import java.util.ArrayList;
import java.util.Locale;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.StringDescription;

public final class AdapterDataLoaderAction implements ViewAction {
    private AdapterViewProtocol.AdaptedData adaptedData;
    final AdapterViewProtocol adapterViewProtocol;
    final EspressoOptional<Integer> atPosition;
    private final Object dataLock = new Object();
    final Matcher<? extends Object> dataToLoadMatcher;
    private boolean performed = false;

    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.Object, androidx.test.espresso.util.EspressoOptional<java.lang.Integer>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AdapterDataLoaderAction(org.hamcrest.Matcher<? extends java.lang.Object> r2, androidx.test.espresso.util.EspressoOptional<java.lang.Integer> r3, androidx.test.espresso.action.AdapterViewProtocol r4) {
        /*
            r1 = this;
            r1.<init>()
            r0 = 0
            r1.performed = r0
            java.lang.Object r0 = new java.lang.Object
            r0.<init>()
            r1.dataLock = r0
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r2)
            org.hamcrest.Matcher r2 = (org.hamcrest.Matcher) r2
            r1.dataToLoadMatcher = r2
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r3)
            androidx.test.espresso.util.EspressoOptional r2 = (androidx.test.espresso.util.EspressoOptional) r2
            r1.atPosition = r2
            java.lang.Object r2 = androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull(r4)
            androidx.test.espresso.action.AdapterViewProtocol r2 = (androidx.test.espresso.action.AdapterViewProtocol) r2
            r1.adapterViewProtocol = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.espresso.action.AdapterDataLoaderAction.<init>(org.hamcrest.Matcher, androidx.test.espresso.util.EspressoOptional, androidx.test.espresso.action.AdapterViewProtocol):void");
    }

    public AdapterViewProtocol.AdaptedData getAdaptedData() {
        AdapterViewProtocol.AdaptedData adaptedData2;
        synchronized (this.dataLock) {
            Preconditions.checkState(this.performed, "perform hasn't beenViewFinderImpl called yet!");
            adaptedData2 = this.adaptedData;
        }
        return adaptedData2;
    }

    public Matcher<View> getConstraints() {
        return Matchers.allOf(ViewMatchers.isAssignableFrom(AdapterView.class), ViewMatchers.isDisplayed());
    }

    public String getDescription() {
        return "load adapter data";
    }

    public void perform(UiController uiController, View view) {
        int i;
        AdapterView adapterView = (AdapterView) view;
        ArrayList newArrayList = Lists.newArrayList();
        for (AdapterViewProtocol.AdaptedData next : this.adapterViewProtocol.getDataInAdapterView(adapterView)) {
            if (this.dataToLoadMatcher.matches(next.getData())) {
                newArrayList.add(next);
            }
        }
        if (newArrayList.size() == 0) {
            StringDescription stringDescription = new StringDescription();
            this.dataToLoadMatcher.describeTo(stringDescription);
            if (newArrayList.isEmpty()) {
                stringDescription.appendText(" contained values: ");
                stringDescription.appendValue(this.adapterViewProtocol.getDataInAdapterView(adapterView));
                PerformException.Builder withViewDescription = new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view));
                String valueOf = String.valueOf(stringDescription);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
                sb.append("No data found matching: ");
                sb.append(valueOf);
                throw withViewDescription.withCause(new RuntimeException(sb.toString())).build();
            }
        }
        synchronized (this.dataLock) {
            Preconditions.checkState(!this.performed, "perform called 2x!");
            this.performed = true;
            i = 0;
            if (this.atPosition.isPresent()) {
                int size = newArrayList.size() - 1;
                if (this.atPosition.get().intValue() <= size) {
                    this.adaptedData = (AdapterViewProtocol.AdaptedData) newArrayList.get(this.atPosition.get().intValue());
                } else {
                    throw new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(new RuntimeException(String.format(Locale.ROOT, "There are only %d elements that matched but requested %d element.", new Object[]{Integer.valueOf(size), this.atPosition.get()}))).build();
                }
            } else if (newArrayList.size() == 1) {
                this.adaptedData = (AdapterViewProtocol.AdaptedData) newArrayList.get(0);
            } else {
                StringDescription stringDescription2 = new StringDescription();
                this.dataToLoadMatcher.describeTo(stringDescription2);
                PerformException.Builder withViewDescription2 = new PerformException.Builder().withActionDescription(getDescription()).withViewDescription(HumanReadables.describe(view));
                String valueOf2 = String.valueOf(stringDescription2);
                String valueOf3 = String.valueOf(newArrayList);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 44 + String.valueOf(valueOf3).length());
                sb2.append("Multiple data elements matched: ");
                sb2.append(valueOf2);
                sb2.append(". Elements: ");
                sb2.append(valueOf3);
                throw withViewDescription2.withCause(new RuntimeException(sb2.toString())).build();
            }
        }
        while (!this.adapterViewProtocol.isDataRenderedWithinAdapterView(adapterView, this.adaptedData)) {
            if (i <= 1) {
                this.adapterViewProtocol.makeDataRenderedWithinAdapterView(adapterView, this.adaptedData);
            } else if (i % 50 == 0) {
                adapterView.invalidate();
                this.adapterViewProtocol.makeDataRenderedWithinAdapterView(adapterView, this.adaptedData);
            }
            uiController.loopMainThreadForAtLeast(100);
            i++;
        }
    }
}
