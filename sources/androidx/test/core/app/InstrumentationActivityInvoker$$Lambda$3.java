package androidx.test.core.app;

import android.app.Activity;
import java.util.Set;

/* compiled from: InstrumentationActivityInvoker */
final /* synthetic */ class InstrumentationActivityInvoker$$Lambda$3 implements Runnable {
    private final Activity arg$1;
    private final Set arg$2;

    InstrumentationActivityInvoker$$Lambda$3(Activity activity, Set set) {
        this.arg$1 = activity;
        this.arg$2 = set;
    }

    public void run() {
        InstrumentationActivityInvoker.lambda$checkActivityStageIsIn$0$InstrumentationActivityInvoker(this.arg$1, this.arg$2);
    }
}
