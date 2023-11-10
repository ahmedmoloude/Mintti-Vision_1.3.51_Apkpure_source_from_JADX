package androidx.test.internal.runner.listener;

import android.util.Log;
import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;

public class DelayInjector extends RunListener {
    private final int delayMsec;

    public DelayInjector(int i) {
        this.delayMsec = i;
    }

    public void testRunStarted(Description description) throws Exception {
        delay();
    }

    public void testFinished(Description description) throws Exception {
        delay();
    }

    private void delay() {
        try {
            Thread.sleep((long) this.delayMsec);
        } catch (InterruptedException e) {
            Log.e("DelayInjector", "interrupted", e);
        }
    }
}
