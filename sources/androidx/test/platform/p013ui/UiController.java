package androidx.test.platform.p013ui;

import android.view.KeyEvent;
import android.view.MotionEvent;

/* renamed from: androidx.test.platform.ui.UiController */
public interface UiController {
    boolean injectKeyEvent(KeyEvent keyEvent) throws InjectEventSecurityException;

    boolean injectMotionEvent(MotionEvent motionEvent) throws InjectEventSecurityException;

    boolean injectString(String str) throws InjectEventSecurityException;

    void loopMainThreadForAtLeast(long j);

    void loopMainThreadUntilIdle();
}
