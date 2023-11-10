package androidx.test.espresso;

import android.view.KeyEvent;
import android.view.MotionEvent;

public interface UiController {
    boolean injectKeyEvent(KeyEvent keyEvent) throws InjectEventSecurityException;

    boolean injectMotionEvent(MotionEvent motionEvent) throws InjectEventSecurityException;

    boolean injectMotionEventSequence(Iterable<MotionEvent> iterable) throws InjectEventSecurityException;

    boolean injectString(String str) throws InjectEventSecurityException;

    void loopMainThreadForAtLeast(long j);

    void loopMainThreadUntilIdle();
}
