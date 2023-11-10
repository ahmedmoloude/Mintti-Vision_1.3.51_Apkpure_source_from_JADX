package androidx.test.espresso.action;

import android.view.View;

public interface CoordinatesProvider {
    float[] calculateCoordinates(View view);
}
