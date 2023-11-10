package androidx.test.espresso.action;

import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.test.espresso.InjectEventSecurityException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import java.util.Locale;

public final class MotionEvents {
    static final int MAX_CLICK_ATTEMPTS = 3;
    private static final String TAG = "MotionEvents";

    public static class DownResultHolder {
        public final MotionEvent down;
        public final boolean longPress;

        DownResultHolder(MotionEvent motionEvent, boolean z) {
            this.down = motionEvent;
            this.longPress = z;
        }
    }

    private MotionEvents() {
    }

    private static MotionEvent downPressICS(long j, float[] fArr, float[] fArr2, int i, int i2) {
        MotionEvent.PointerCoords[] pointerCoordsArr = {new MotionEvent.PointerCoords()};
        MotionEvent.PointerProperties[] pointerProperties = getPointerProperties(i);
        pointerCoordsArr[0].clear();
        pointerCoordsArr[0].x = fArr[0];
        pointerCoordsArr[0].y = fArr[1];
        pointerCoordsArr[0].pressure = 0.0f;
        pointerCoordsArr[0].size = 1.0f;
        return MotionEvent.obtain(j, SystemClock.uptimeMillis(), 0, 1, pointerProperties, pointerCoordsArr, 0, i2, fArr2[0], fArr2[1], 0, 0, i, 0);
    }

    private static MotionEvent.PointerProperties[] getPointerProperties(int i) {
        MotionEvent.PointerProperties[] pointerPropertiesArr = {new MotionEvent.PointerProperties()};
        pointerPropertiesArr[0].clear();
        pointerPropertiesArr[0].id = 0;
        if (i == 4098) {
            pointerPropertiesArr[0].toolType = 1;
        } else if (i == 8194) {
            pointerPropertiesArr[0].toolType = 3;
        } else if (i != 16386) {
            pointerPropertiesArr[0].toolType = 0;
        } else {
            pointerPropertiesArr[0].toolType = 2;
        }
        return pointerPropertiesArr;
    }

    public static MotionEvent obtainDownEvent(float[] fArr, float[] fArr2) {
        return obtainDownEvent(fArr, fArr2, 0, 1);
    }

    public static MotionEvent obtainMovement(long j, long j2, float[] fArr) {
        return MotionEvent.obtain(j, j2, 2, fArr[0], fArr[1], 0);
    }

    public static MotionEvent obtainUpEvent(MotionEvent motionEvent, float[] fArr) {
        Preconditions.checkNotNull(motionEvent);
        Preconditions.checkNotNull(fArr);
        if (Build.VERSION.SDK_INT < 14) {
            return upPressGingerBread(motionEvent, fArr);
        }
        return upPressICS(motionEvent, fArr);
    }

    public static void sendCancel(UiController uiController, MotionEvent motionEvent) {
        Preconditions.checkNotNull(uiController);
        Preconditions.checkNotNull(motionEvent);
        MotionEvent motionEvent2 = null;
        try {
            MotionEvent obtain = MotionEvent.obtain(motionEvent.getDownTime(), SystemClock.uptimeMillis(), 3, motionEvent.getX(), motionEvent.getY(), 0);
            if (!uiController.injectMotionEvent(obtain)) {
                Log.e(TAG, String.format(Locale.ROOT, "Injection of cancel event failed (corresponding down event: %s)", new Object[]{motionEvent}));
                if (obtain != null) {
                    obtain.recycle();
                }
            } else if (obtain != null) {
                obtain.recycle();
            }
        } catch (InjectEventSecurityException e) {
            throw new PerformException.Builder().withActionDescription(String.format(Locale.ROOT, "inject cancel event (corresponding down event: %s)", new Object[]{motionEvent})).withViewDescription("unknown").withCause(e).build();
        } catch (Throwable th) {
            if (motionEvent2 != null) {
                motionEvent2.recycle();
            }
            throw th;
        }
    }

    public static DownResultHolder sendDown(UiController uiController, float[] fArr, float[] fArr2) {
        return sendDown(uiController, fArr, fArr2, 0, 1);
    }

    public static boolean sendMovement(UiController uiController, MotionEvent motionEvent, float[] fArr) {
        Preconditions.checkNotNull(uiController);
        Preconditions.checkNotNull(motionEvent);
        Preconditions.checkNotNull(fArr);
        MotionEvent motionEvent2 = null;
        try {
            MotionEvent obtainMovement = obtainMovement(motionEvent.getDownTime(), fArr);
            if (!uiController.injectMotionEvent(obtainMovement)) {
                Log.e(TAG, String.format(Locale.ROOT, "Injection of motion event failed (corresponding down event: %s)", new Object[]{motionEvent}));
                if (obtainMovement != null) {
                    obtainMovement.recycle();
                }
                return false;
            }
            if (obtainMovement != null) {
                obtainMovement.recycle();
            }
            return true;
        } catch (InjectEventSecurityException e) {
            throw new PerformException.Builder().withActionDescription(String.format(Locale.ROOT, "inject motion event (corresponding down event: %s)", new Object[]{motionEvent})).withViewDescription("unknown").withCause(e).build();
        } catch (Throwable th) {
            if (motionEvent2 != null) {
                motionEvent2.recycle();
            }
            throw th;
        }
    }

    public static boolean sendUp(UiController uiController, MotionEvent motionEvent) {
        return sendUp(uiController, motionEvent, new float[]{motionEvent.getX(), motionEvent.getY()});
    }

    private static MotionEvent upPressICS(MotionEvent motionEvent, float[] fArr) {
        MotionEvent.PointerCoords[] pointerCoordsArr = {new MotionEvent.PointerCoords()};
        MotionEvent.PointerProperties[] pointerProperties = getPointerProperties(motionEvent.getSource());
        pointerCoordsArr[0].clear();
        pointerCoordsArr[0].x = fArr[0];
        pointerCoordsArr[0].y = fArr[1];
        pointerCoordsArr[0].pressure = 0.0f;
        pointerCoordsArr[0].size = 1.0f;
        return MotionEvent.obtain(motionEvent.getDownTime(), SystemClock.uptimeMillis(), 1, 1, pointerProperties, pointerCoordsArr, 0, motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), 0, 0, motionEvent.getSource(), 0);
    }

    private static MotionEvent downPressGingerBread(long j, float[] fArr, float[] fArr2) {
        return MotionEvent.obtain(j, SystemClock.uptimeMillis(), 0, fArr[0], fArr[1], 0.0f, 1.0f, 0, fArr2[0], fArr2[1], 0, 0);
    }

    public static MotionEvent obtainDownEvent(float[] fArr, float[] fArr2, int i, int i2) {
        Preconditions.checkNotNull(fArr);
        Preconditions.checkNotNull(fArr2);
        long uptimeMillis = SystemClock.uptimeMillis();
        if (Build.VERSION.SDK_INT < 14) {
            return downPressGingerBread(uptimeMillis, fArr, fArr2);
        }
        return downPressICS(uptimeMillis, fArr, fArr2, i, i2);
    }

    public static DownResultHolder sendDown(UiController uiController, float[] fArr, float[] fArr2, int i, int i2) {
        UiController uiController2 = uiController;
        Preconditions.checkNotNull(uiController);
        Preconditions.checkNotNull(fArr);
        Preconditions.checkNotNull(fArr2);
        int i3 = 0;
        while (true) {
            boolean z = true;
            if (i3 < 3) {
                try {
                    MotionEvent obtainDownEvent = obtainDownEvent(fArr, fArr2, i, i2);
                    long downTime = obtainDownEvent.getDownTime();
                    long tapTimeout = ((long) (ViewConfiguration.getTapTimeout() / 2)) + downTime;
                    boolean injectMotionEvent = uiController2.injectMotionEvent(obtainDownEvent);
                    while (true) {
                        long uptimeMillis = tapTimeout - SystemClock.uptimeMillis();
                        if (uptimeMillis <= 10) {
                            break;
                        }
                        uiController2.loopMainThreadForAtLeast(uptimeMillis / 4);
                    }
                    if (SystemClock.uptimeMillis() > downTime + ((long) ViewConfiguration.getLongPressTimeout())) {
                        Log.w(TAG, "Overslept and turned a tap into a long press");
                    } else {
                        z = false;
                    }
                    if (injectMotionEvent) {
                        return new DownResultHolder(obtainDownEvent, z);
                    }
                    obtainDownEvent.recycle();
                    i3++;
                } catch (InjectEventSecurityException e) {
                    throw new PerformException.Builder().withActionDescription("Send down motion event").withViewDescription("unknown").withCause(e).build();
                }
            } else {
                throw new PerformException.Builder().withActionDescription(String.format(Locale.ROOT, "click (after %s attempts)", new Object[]{3})).withViewDescription("unknown").build();
            }
        }
    }

    public static boolean sendUp(UiController uiController, MotionEvent motionEvent, float[] fArr) {
        Preconditions.checkNotNull(uiController);
        Preconditions.checkNotNull(motionEvent);
        Preconditions.checkNotNull(fArr);
        MotionEvent motionEvent2 = null;
        try {
            MotionEvent obtainUpEvent = obtainUpEvent(motionEvent, fArr);
            if (!uiController.injectMotionEvent(obtainUpEvent)) {
                Log.e(TAG, String.format(Locale.ROOT, "Injection of up event failed (corresponding down event: %s)", new Object[]{motionEvent}));
                if (obtainUpEvent != null) {
                    obtainUpEvent.recycle();
                }
                return false;
            }
            if (obtainUpEvent != null) {
                obtainUpEvent.recycle();
            }
            return true;
        } catch (InjectEventSecurityException e) {
            throw new PerformException.Builder().withActionDescription(String.format(Locale.ROOT, "inject up event (corresponding down event: %s)", new Object[]{motionEvent})).withViewDescription("unknown").withCause(e).build();
        } catch (Throwable th) {
            if (motionEvent2 != null) {
                motionEvent2.recycle();
            }
            throw th;
        }
    }

    private static MotionEvent upPressGingerBread(MotionEvent motionEvent, float[] fArr) {
        return MotionEvent.obtain(motionEvent.getDownTime(), SystemClock.uptimeMillis(), 1, fArr[0], fArr[1], 0);
    }

    public static MotionEvent obtainMovement(long j, float[] fArr) {
        return MotionEvent.obtain(j, SystemClock.uptimeMillis(), 2, fArr[0], fArr[1], 0);
    }
}
