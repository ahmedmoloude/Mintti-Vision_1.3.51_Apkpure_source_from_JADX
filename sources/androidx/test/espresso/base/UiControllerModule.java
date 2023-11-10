package androidx.test.espresso.base;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.test.espresso.InjectEventSecurityException;
import androidx.test.espresso.UiController$$CC;
import androidx.test.internal.platform.ServiceLoaderWrapper;
import androidx.test.platform.p013ui.UiController;

public class UiControllerModule {

    private static class EspressoUiControllerAdapter implements InterruptableUiController {
        private final UiController platformUiController;

        private EspressoUiControllerAdapter(UiController uiController) {
            this.platformUiController = uiController;
        }

        public boolean injectKeyEvent(KeyEvent keyEvent) throws InjectEventSecurityException {
            try {
                return this.platformUiController.injectKeyEvent(keyEvent);
            } catch (androidx.test.platform.p013ui.InjectEventSecurityException e) {
                throw new InjectEventSecurityException((Throwable) e);
            }
        }

        public boolean injectMotionEvent(MotionEvent motionEvent) throws InjectEventSecurityException {
            try {
                return this.platformUiController.injectMotionEvent(motionEvent);
            } catch (androidx.test.platform.p013ui.InjectEventSecurityException e) {
                throw new InjectEventSecurityException((Throwable) e);
            }
        }

        public boolean injectMotionEventSequence(Iterable iterable) throws InjectEventSecurityException {
            return UiController$$CC.injectMotionEventSequence$$dflt$$(this, iterable);
        }

        public boolean injectString(String str) throws InjectEventSecurityException {
            try {
                return this.platformUiController.injectString(str);
            } catch (androidx.test.platform.p013ui.InjectEventSecurityException e) {
                throw new InjectEventSecurityException((Throwable) e);
            }
        }

        public void interruptEspressoTasks() {
            Log.w("UiController", "interruptEspressoTasks called, no-op");
        }

        public void loopMainThreadForAtLeast(long j) {
            this.platformUiController.loopMainThreadForAtLeast(j);
        }

        public void loopMainThreadUntilIdle() {
            this.platformUiController.loopMainThreadUntilIdle();
        }
    }

    public androidx.test.espresso.UiController provideUiController(UiControllerImpl uiControllerImpl) {
        UiController uiController = (UiController) ServiceLoaderWrapper.loadSingleServiceOrNull(UiController.class);
        if (uiController == null) {
            return uiControllerImpl;
        }
        return new EspressoUiControllerAdapter(uiController);
    }
}
