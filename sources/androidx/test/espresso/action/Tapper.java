package androidx.test.espresso.action;

import androidx.test.espresso.UiController;

public interface Tapper {

    public enum Status {
        SUCCESS,
        WARNING,
        FAILURE
    }

    @Deprecated
    Status sendTap(UiController uiController, float[] fArr, float[] fArr2);

    Status sendTap(UiController uiController, float[] fArr, float[] fArr2, int i, int i2);
}
