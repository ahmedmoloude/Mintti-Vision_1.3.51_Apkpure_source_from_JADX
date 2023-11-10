package androidx.test.espresso.base;

import androidx.test.espresso.UiController;

public interface InterruptableUiController extends UiController {
    void interruptEspressoTasks();
}
