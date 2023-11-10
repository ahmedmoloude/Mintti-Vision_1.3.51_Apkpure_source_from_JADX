package androidx.test.espresso.action;

import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.test.espresso.UiController;
import androidx.test.espresso.action.MotionEvents;
import androidx.test.espresso.action.Tapper;
import androidx.test.espresso.core.internal.deps.guava.base.Preconditions;
import java.lang.reflect.InvocationTargetException;

public enum Tap implements Tapper {
    SINGLE {
        public Tapper.Status sendTap(UiController uiController, float[] fArr, float[] fArr2) {
            return sendTap(uiController, fArr, fArr2, 0, 0);
        }

        public Tapper.Status sendTap(UiController uiController, float[] fArr, float[] fArr2, int i, int i2) {
            Tapper.Status access$100 = Tap.sendSingleTap(uiController, fArr, fArr2, i, i2);
            if (Tapper.Status.SUCCESS == access$100) {
                uiController.loopMainThreadForAtLeast((long) (((float) ViewConfiguration.getTapTimeout()) * 1.5f));
            }
            return access$100;
        }
    },
    LONG {
        public Tapper.Status sendTap(UiController uiController, float[] fArr, float[] fArr2) {
            return sendTap(uiController, fArr, fArr2, 0, 0);
        }

        public Tapper.Status sendTap(UiController uiController, float[] fArr, float[] fArr2, int i, int i2) {
            Preconditions.checkNotNull(uiController);
            Preconditions.checkNotNull(fArr);
            Preconditions.checkNotNull(fArr2);
            MotionEvent motionEvent = MotionEvents.sendDown(uiController, fArr, fArr2, i, i2).down;
            try {
                uiController.loopMainThreadForAtLeast((long) (((float) ViewConfiguration.getLongPressTimeout()) * 1.5f));
                if (!MotionEvents.sendUp(uiController, motionEvent)) {
                    MotionEvents.sendCancel(uiController, motionEvent);
                    return Tapper.Status.FAILURE;
                }
                motionEvent.recycle();
                return Tapper.Status.SUCCESS;
            } finally {
                motionEvent.recycle();
            }
        }
    },
    DOUBLE {
        public Tapper.Status sendTap(UiController uiController, float[] fArr, float[] fArr2) {
            return sendTap(uiController, fArr, fArr2, 0, 0);
        }

        public Tapper.Status sendTap(UiController uiController, float[] fArr, float[] fArr2, int i, int i2) {
            Preconditions.checkNotNull(uiController);
            Preconditions.checkNotNull(fArr);
            Preconditions.checkNotNull(fArr2);
            Tapper.Status access$100 = Tap.sendSingleTap(uiController, fArr, fArr2, i, i2);
            if (access$100 == Tapper.Status.FAILURE) {
                return Tapper.Status.FAILURE;
            }
            if (Tap.DOUBLE_TAP_MIN_TIMEOUT > 0) {
                uiController.loopMainThreadForAtLeast((long) Tap.DOUBLE_TAP_MIN_TIMEOUT);
            }
            Tapper.Status access$1002 = Tap.sendSingleTap(uiController, fArr, fArr2, i, i2);
            if (access$1002 == Tapper.Status.FAILURE) {
                return Tapper.Status.FAILURE;
            }
            if (access$1002 == Tapper.Status.WARNING || access$100 == Tapper.Status.WARNING) {
                return Tapper.Status.WARNING;
            }
            return Tapper.Status.SUCCESS;
        }
    };
    
    /* access modifiers changed from: private */
    public static final int DOUBLE_TAP_MIN_TIMEOUT = 0;
    private static final String TAG = null;

    static {
        int i;
        TAG = Tap.class.getSimpleName();
        if (Build.VERSION.SDK_INT > 18) {
            try {
                i = ((Integer) ViewConfiguration.class.getDeclaredMethod("getDoubleTapMinTime", new Class[0]).invoke((Object) null, new Object[0])).intValue();
            } catch (NoSuchMethodException e) {
                Log.w(TAG, "Expected to find getDoubleTapMinTime", e);
            } catch (InvocationTargetException e2) {
                Log.w(TAG, "Unable to query double tap min time!", e2);
            } catch (IllegalAccessException e3) {
                Log.w(TAG, "Unable to query double tap min time!", e3);
            }
        }
        DOUBLE_TAP_MIN_TIMEOUT = i;
    }

    /* access modifiers changed from: private */
    public static Tapper.Status sendSingleTap(UiController uiController, float[] fArr, float[] fArr2, int i, int i2) {
        Preconditions.checkNotNull(uiController);
        Preconditions.checkNotNull(fArr);
        Preconditions.checkNotNull(fArr2);
        MotionEvents.DownResultHolder sendDown = MotionEvents.sendDown(uiController, fArr, fArr2, i, i2);
        try {
            if (!MotionEvents.sendUp(uiController, sendDown.down)) {
                Log.d(TAG, "Injection of up event as part of the click failed. Send cancel event.");
                MotionEvents.sendCancel(uiController, sendDown.down);
                return Tapper.Status.FAILURE;
            }
            sendDown.down.recycle();
            return sendDown.longPress ? Tapper.Status.WARNING : Tapper.Status.SUCCESS;
        } finally {
            sendDown.down.recycle();
        }
    }
}
