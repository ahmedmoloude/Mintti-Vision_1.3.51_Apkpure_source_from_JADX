package androidx.test.internal.platform.p012os;

import android.view.View;

/* renamed from: androidx.test.internal.platform.os.ControlledLooper */
public interface ControlledLooper {
    public static final ControlledLooper NO_OP_CONTROLLED_LOOPER = new ControlledLooper() {
        public void drainMainThreadUntilIdle() {
        }

        public void simulateWindowFocus(View view) {
        }
    };

    void drainMainThreadUntilIdle();

    void simulateWindowFocus(View view);
}
