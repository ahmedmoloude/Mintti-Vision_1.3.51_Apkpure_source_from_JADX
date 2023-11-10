package androidx.test.espresso.action;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.AdapterViewFlipper;
import androidx.test.espresso.action.AdapterViewProtocol;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import androidx.test.espresso.core.internal.deps.guava.collect.Lists;
import androidx.test.espresso.core.internal.deps.guava.collect.Range;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.EspressoOptional;
import java.util.ArrayList;

public final class AdapterViewProtocols {
    private static final int FULLY_RENDERED_PERCENTAGE_CUTOFF = 90;
    private static final AdapterViewProtocol STANDARD_PROTOCOL = new StandardAdapterViewProtocol();

    private static final class StandardAdapterViewProtocol implements AdapterViewProtocol {
        private static final String TAG = "StdAdapterViewProtocol";

        private static final class StandardDataFunction implements AdapterViewProtocol.DataFunction {
            private final Object dataAtPosition;
            private final int position;

            private StandardDataFunction(Object obj, int i) {
                Preconditions.checkArgument(i >= 0, "position must be >= 0");
                this.dataAtPosition = obj;
                this.position = i;
            }

            public Object getData() {
                Object obj = this.dataAtPosition;
                if ((obj instanceof Cursor) && !((Cursor) obj).moveToPosition(this.position)) {
                    int i = this.position;
                    StringBuilder sb = new StringBuilder(43);
                    sb.append("Cannot move cursor to position: ");
                    sb.append(i);
                    Log.e(StandardAdapterViewProtocol.TAG, sb.toString());
                }
                return this.dataAtPosition;
            }
        }

        private boolean isElementFullyRendered(AdapterView<? extends Adapter> adapterView, int i) {
            return ViewMatchers.isDisplayingAtLeast(90).matches(adapterView.getChildAt(i));
        }

        public Iterable<AdapterViewProtocol.AdaptedData> getDataInAdapterView(AdapterView<? extends Adapter> adapterView) {
            ArrayList newArrayList = Lists.newArrayList();
            for (int i = 0; i < adapterView.getCount(); i++) {
                newArrayList.add(new AdapterViewProtocol.AdaptedData.Builder().withDataFunction(new StandardDataFunction(adapterView.getItemAtPosition(i), i)).withOpaqueToken(Integer.valueOf(i)).build());
            }
            return newArrayList;
        }

        public EspressoOptional<AdapterViewProtocol.AdaptedData> getDataRenderedByView(AdapterView<? extends Adapter> adapterView, View view) {
            int positionForView;
            if (adapterView != view.getParent() || (positionForView = adapterView.getPositionForView(view)) == -1) {
                return EspressoOptional.absent();
            }
            return EspressoOptional.m77of(new AdapterViewProtocol.AdaptedData.Builder().withDataFunction(new StandardDataFunction(adapterView.getItemAtPosition(positionForView), positionForView)).withOpaqueToken(Integer.valueOf(positionForView)).build());
        }

        public boolean isDataRenderedWithinAdapterView(AdapterView<? extends Adapter> adapterView, AdapterViewProtocol.AdaptedData adaptedData) {
            boolean z;
            Preconditions.checkArgument(adaptedData.opaqueToken instanceof Integer, "Not my data: %s", (Object) adaptedData);
            int intValue = ((Integer) adaptedData.opaqueToken).intValue();
            if (Range.closed(Integer.valueOf(adapterView.getFirstVisiblePosition()), Integer.valueOf(adapterView.getLastVisiblePosition())).contains(Integer.valueOf(intValue))) {
                z = adapterView.getFirstVisiblePosition() == adapterView.getLastVisiblePosition() ? true : isElementFullyRendered(adapterView, intValue - adapterView.getFirstVisiblePosition());
            } else {
                z = false;
            }
            if (z) {
                adapterView.setSelection(intValue);
            }
            return z;
        }

        public void makeDataRenderedWithinAdapterView(AdapterView<? extends Adapter> adapterView, AdapterViewProtocol.AdaptedData adaptedData) {
            Preconditions.checkArgument(adaptedData.opaqueToken instanceof Integer, "Not my data: %s", (Object) adaptedData);
            int intValue = ((Integer) adaptedData.opaqueToken).intValue();
            boolean z = true;
            boolean z2 = false;
            if (Build.VERSION.SDK_INT > 7) {
                if (adapterView instanceof AbsListView) {
                    if (Build.VERSION.SDK_INT > 10) {
                        ((AbsListView) adapterView).smoothScrollToPositionFromTop(intValue, adapterView.getPaddingTop(), 0);
                    } else {
                        ((AbsListView) adapterView).smoothScrollToPosition(intValue);
                    }
                    z2 = true;
                }
                if (Build.VERSION.SDK_INT <= 10 || !(adapterView instanceof AdapterViewAnimator)) {
                    z = z2;
                } else {
                    if (adapterView instanceof AdapterViewFlipper) {
                        ((AdapterViewFlipper) adapterView).stopFlipping();
                    }
                    ((AdapterViewAnimator) adapterView).setDisplayedChild(intValue);
                }
            } else {
                z = false;
            }
            if (!z) {
                adapterView.setSelection(intValue);
            }
        }
    }

    private AdapterViewProtocols() {
    }

    public static AdapterViewProtocol standardProtocol() {
        return STANDARD_PROTOCOL;
    }
}
