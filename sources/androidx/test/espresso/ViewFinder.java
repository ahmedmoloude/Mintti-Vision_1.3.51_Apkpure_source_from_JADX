package androidx.test.espresso;

import android.view.View;

public interface ViewFinder {
    View getView() throws AmbiguousViewMatcherException, NoMatchingViewException;
}
