package androidx.test.core.app;

import android.app.Activity;

/* compiled from: InstrumentationActivityInvoker */
final /* synthetic */ class InstrumentationActivityInvoker$$Lambda$2 implements Runnable {
    private final Activity arg$1;

    private InstrumentationActivityInvoker$$Lambda$2(Activity activity) {
        this.arg$1 = activity;
    }

    static Runnable get$Lambda(Activity activity) {
        return new InstrumentationActivityInvoker$$Lambda$2(activity);
    }

    public void run() {
        this.arg$1.finish();
    }
}
