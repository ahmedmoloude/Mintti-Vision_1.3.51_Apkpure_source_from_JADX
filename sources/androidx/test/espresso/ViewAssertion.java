package androidx.test.espresso;

import android.view.View;

public interface ViewAssertion {
    void check(View view, NoMatchingViewException noMatchingViewException);
}
