package androidx.test.espresso.base;

import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.test.espresso.InjectEventSecurityException;

interface EventInjectionStrategy {
    boolean injectKeyEvent(KeyEvent keyEvent) throws InjectEventSecurityException;

    boolean injectMotionEvent(MotionEvent motionEvent, boolean z) throws InjectEventSecurityException;
}
