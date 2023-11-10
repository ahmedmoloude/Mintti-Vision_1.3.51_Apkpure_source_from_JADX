package androidx.test.espresso.base;

import android.os.Build;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.test.espresso.InjectEventSecurityException;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;

final class EventInjector {
    private static final String TAG = "EventInjector";
    private final EventInjectionStrategy injectionStrategy;

    EventInjector(EventInjectionStrategy eventInjectionStrategy) {
        this.injectionStrategy = (EventInjectionStrategy) Preconditions.checkNotNull(eventInjectionStrategy);
    }

    /* access modifiers changed from: package-private */
    public boolean injectKeyEvent(KeyEvent keyEvent) throws InjectEventSecurityException {
        KeyEvent keyEvent2;
        long downTime = keyEvent.getDownTime();
        long eventTime = keyEvent.getEventTime();
        int action = keyEvent.getAction();
        int keyCode = keyEvent.getKeyCode();
        int repeatCount = keyEvent.getRepeatCount();
        int metaState = keyEvent.getMetaState();
        int deviceId = keyEvent.getDeviceId();
        int scanCode = keyEvent.getScanCode();
        int flags = keyEvent.getFlags();
        if (eventTime == 0) {
            eventTime = SystemClock.uptimeMillis();
        }
        long j = eventTime;
        long j2 = downTime == 0 ? j : downTime;
        if (Build.VERSION.SDK_INT < 9) {
            keyEvent2 = new KeyEvent(j2, j, action, keyCode, repeatCount, metaState, deviceId, scanCode, flags | 8);
        } else {
            keyEvent2 = new KeyEvent(j2, j, action, keyCode, repeatCount, metaState, deviceId, scanCode, flags | 8, keyEvent.getSource());
        }
        return this.injectionStrategy.injectKeyEvent(keyEvent2);
    }

    /* access modifiers changed from: package-private */
    public boolean injectMotionEvent(MotionEvent motionEvent) throws InjectEventSecurityException {
        return this.injectionStrategy.injectMotionEvent(motionEvent, true);
    }

    /* access modifiers changed from: package-private */
    public boolean injectMotionEventAsync(MotionEvent motionEvent) throws InjectEventSecurityException {
        return this.injectionStrategy.injectMotionEvent(motionEvent, false);
    }
}
