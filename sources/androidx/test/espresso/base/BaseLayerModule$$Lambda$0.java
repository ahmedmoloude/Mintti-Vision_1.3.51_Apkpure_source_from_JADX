package androidx.test.espresso.base;

import androidx.test.internal.platform.ServiceLoaderWrapper;
import androidx.test.internal.platform.p012os.ControlledLooper;

/* compiled from: BaseLayerModule */
final /* synthetic */ class BaseLayerModule$$Lambda$0 implements ServiceLoaderWrapper.Factory {
    static final ServiceLoaderWrapper.Factory $instance = new BaseLayerModule$$Lambda$0();

    private BaseLayerModule$$Lambda$0() {
    }

    public Object create() {
        return ControlledLooper.NO_OP_CONTROLLED_LOOPER;
    }
}
