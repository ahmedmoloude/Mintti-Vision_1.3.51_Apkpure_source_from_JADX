package androidx.test.espresso;

import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import java.util.Iterator;

public abstract /* synthetic */ class UiController$$CC {
    public static boolean injectMotionEventSequence$$dflt$$(UiController uiController, Iterable iterable) throws InjectEventSecurityException {
        Log.w("UIC", "Using default injectMotionEventSeq() - this may not inject a sequence properly. If wrapping UIController please override this method and delegate.");
        Iterator it = iterable.iterator();
        boolean z = true;
        while (it.hasNext()) {
            MotionEvent motionEvent = (MotionEvent) it.next();
            if (motionEvent.getEventTime() - SystemClock.uptimeMillis() > 10) {
                uiController.loopMainThreadForAtLeast(10);
            }
            z &= uiController.injectMotionEvent(motionEvent);
        }
        return z;
    }
}
