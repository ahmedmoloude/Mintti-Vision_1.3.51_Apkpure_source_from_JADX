package androidx.test.espresso.base;

import androidx.test.espresso.UiController;

public interface IdlingUiController extends UiController {
    IdlingResourceRegistry getIdlingResourceRegistry();
}
