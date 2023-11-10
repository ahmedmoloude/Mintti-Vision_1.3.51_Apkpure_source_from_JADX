package androidx.test.core.app;

import androidx.test.internal.platform.ServiceLoaderWrapper;
import androidx.test.internal.platform.p012os.ControlledLooper;

/* compiled from: ActivityScenario */
final /* synthetic */ class ActivityScenario$$Lambda$1 implements ServiceLoaderWrapper.Factory {
    static final ServiceLoaderWrapper.Factory $instance = new ActivityScenario$$Lambda$1();

    private ActivityScenario$$Lambda$1() {
    }

    public Object create() {
        return ControlledLooper.NO_OP_CONTROLLED_LOOPER;
    }
}
