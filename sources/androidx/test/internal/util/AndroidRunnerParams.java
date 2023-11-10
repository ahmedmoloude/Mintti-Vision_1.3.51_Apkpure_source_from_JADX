package androidx.test.internal.util;

import android.app.Instrumentation;
import android.os.Bundle;

public class AndroidRunnerParams {
    private final Bundle bundle;
    private final boolean ignoreSuiteMethods;
    private final Instrumentation instrumentation;
    private final long perTestTimeout;
    private final boolean skipExecution;

    @Deprecated
    public AndroidRunnerParams(Instrumentation instrumentation2, Bundle bundle2, boolean z, long j, boolean z2) {
        this.instrumentation = instrumentation2;
        this.bundle = bundle2;
        this.skipExecution = z;
        this.perTestTimeout = j;
        this.ignoreSuiteMethods = z2;
    }

    public AndroidRunnerParams(Instrumentation instrumentation2, Bundle bundle2, long j, boolean z) {
        this.instrumentation = instrumentation2;
        this.bundle = bundle2;
        this.skipExecution = false;
        this.perTestTimeout = j;
        this.ignoreSuiteMethods = z;
    }

    public Instrumentation getInstrumentation() {
        return this.instrumentation;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    @Deprecated
    public boolean isSkipExecution() {
        return this.skipExecution;
    }

    public long getPerTestTimeout() {
        return this.perTestTimeout;
    }

    public boolean isIgnoreSuiteMethods() {
        return this.ignoreSuiteMethods;
    }
}
