package androidx.test.runner.intent;

import java.util.concurrent.atomic.AtomicReference;

public final class IntentMonitorRegistry {
    private static final AtomicReference<IntentMonitor> monitorRef = new AtomicReference<>((Object) null);

    public static IntentMonitor getInstance() {
        IntentMonitor intentMonitor = monitorRef.get();
        if (intentMonitor != null) {
            return intentMonitor;
        }
        throw new IllegalStateException("No intent monitor registered! Are you running under an Instrumentation which registers intent monitors?");
    }

    public static void registerInstance(IntentMonitor intentMonitor) {
        monitorRef.set(intentMonitor);
    }

    private IntentMonitorRegistry() {
    }
}
