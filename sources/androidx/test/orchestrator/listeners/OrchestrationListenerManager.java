package androidx.test.orchestrator.listeners;

import android.app.Instrumentation;
import android.os.Bundle;
import android.util.Log;
import androidx.test.orchestrator.junit.BundleJUnitUtils;
import androidx.test.orchestrator.junit.ParcelableDescription;
import androidx.test.orchestrator.junit.ParcelableFailure;
import java.util.ArrayList;
import java.util.List;

public final class OrchestrationListenerManager {
    public static final String KEY_TEST_EVENT = "TestEvent";
    private static final String TAG = "ListenerManager";
    private final Instrumentation instrumentation;
    private ParcelableDescription lastDescription;
    private final List<OrchestrationRunListener> listeners = new ArrayList();
    private boolean markTerminationAsFailure = false;

    public enum TestEvent {
        TEST_RUN_STARTED,
        TEST_RUN_FINISHED,
        TEST_STARTED,
        TEST_FINISHED,
        TEST_FAILURE,
        TEST_ASSUMPTION_FAILURE,
        TEST_IGNORED
    }

    public OrchestrationListenerManager(Instrumentation instrumentation2) {
        if (instrumentation2 != null) {
            this.instrumentation = instrumentation2;
            return;
        }
        throw new IllegalArgumentException("Instrumentation must not be null");
    }

    public void addListener(OrchestrationRunListener orchestrationRunListener) {
        orchestrationRunListener.setInstrumentation(this.instrumentation);
        this.listeners.add(orchestrationRunListener);
    }

    public void orchestrationRunStarted(int i) {
        for (OrchestrationRunListener orchestrationRunStarted : this.listeners) {
            orchestrationRunStarted.orchestrationRunStarted(i);
        }
    }

    public void testProcessStarted(ParcelableDescription parcelableDescription) {
        this.lastDescription = parcelableDescription;
        this.markTerminationAsFailure = true;
    }

    public void testProcessFinished(String str) {
        if (this.markTerminationAsFailure) {
            for (OrchestrationRunListener next : this.listeners) {
                ParcelableDescription parcelableDescription = this.lastDescription;
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 56);
                sb.append("Test instrumentation process crashed. Check ");
                sb.append(str);
                sb.append(" for details");
                next.testFailure(new ParcelableFailure(parcelableDescription, new Throwable(sb.toString())));
                next.testFinished(this.lastDescription);
            }
        }
    }

    public void handleNotification(Bundle bundle) {
        bundle.setClassLoader(getClass().getClassLoader());
        cacheStatus(bundle);
        for (OrchestrationRunListener handleNotificationForListener : this.listeners) {
            handleNotificationForListener(handleNotificationForListener, bundle);
        }
    }

    private void cacheStatus(Bundle bundle) {
        if (BundleJUnitUtils.getDescription(bundle) != null) {
            this.lastDescription = BundleJUnitUtils.getDescription(bundle);
        }
        int i = C08181.f214x9b59c564[TestEvent.valueOf(bundle.getString(KEY_TEST_EVENT)).ordinal()];
        if (i == 1) {
            this.markTerminationAsFailure = true;
        } else if (i == 2) {
            this.markTerminationAsFailure = false;
        } else if (i == 3) {
            this.markTerminationAsFailure = false;
        }
    }

    /* renamed from: androidx.test.orchestrator.listeners.OrchestrationListenerManager$1 */
    static /* synthetic */ class C08181 {

        /* renamed from: $SwitchMap$androidx$test$orchestrator$listeners$OrchestrationListenerManager$TestEvent */
        static final /* synthetic */ int[] f214x9b59c564;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|(3:13|14|16)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                androidx.test.orchestrator.listeners.OrchestrationListenerManager$TestEvent[] r0 = androidx.test.orchestrator.listeners.OrchestrationListenerManager.TestEvent.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f214x9b59c564 = r0
                androidx.test.orchestrator.listeners.OrchestrationListenerManager$TestEvent r1 = androidx.test.orchestrator.listeners.OrchestrationListenerManager.TestEvent.TEST_RUN_STARTED     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f214x9b59c564     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.test.orchestrator.listeners.OrchestrationListenerManager$TestEvent r1 = androidx.test.orchestrator.listeners.OrchestrationListenerManager.TestEvent.TEST_FAILURE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f214x9b59c564     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.test.orchestrator.listeners.OrchestrationListenerManager$TestEvent r1 = androidx.test.orchestrator.listeners.OrchestrationListenerManager.TestEvent.TEST_RUN_FINISHED     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = f214x9b59c564     // Catch:{ NoSuchFieldError -> 0x0033 }
                androidx.test.orchestrator.listeners.OrchestrationListenerManager$TestEvent r1 = androidx.test.orchestrator.listeners.OrchestrationListenerManager.TestEvent.TEST_STARTED     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = f214x9b59c564     // Catch:{ NoSuchFieldError -> 0x003e }
                androidx.test.orchestrator.listeners.OrchestrationListenerManager$TestEvent r1 = androidx.test.orchestrator.listeners.OrchestrationListenerManager.TestEvent.TEST_FINISHED     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = f214x9b59c564     // Catch:{ NoSuchFieldError -> 0x0049 }
                androidx.test.orchestrator.listeners.OrchestrationListenerManager$TestEvent r1 = androidx.test.orchestrator.listeners.OrchestrationListenerManager.TestEvent.TEST_ASSUMPTION_FAILURE     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = f214x9b59c564     // Catch:{ NoSuchFieldError -> 0x0054 }
                androidx.test.orchestrator.listeners.OrchestrationListenerManager$TestEvent r1 = androidx.test.orchestrator.listeners.OrchestrationListenerManager.TestEvent.TEST_IGNORED     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.test.orchestrator.listeners.OrchestrationListenerManager.C08181.<clinit>():void");
        }
    }

    private void handleNotificationForListener(OrchestrationRunListener orchestrationRunListener, Bundle bundle) {
        switch (C08181.f214x9b59c564[TestEvent.valueOf(bundle.getString(KEY_TEST_EVENT)).ordinal()]) {
            case 1:
                orchestrationRunListener.testRunStarted(BundleJUnitUtils.getDescription(bundle));
                return;
            case 2:
                orchestrationRunListener.testFailure(BundleJUnitUtils.getFailure(bundle));
                return;
            case 3:
                orchestrationRunListener.testRunFinished(BundleJUnitUtils.getResult(bundle));
                return;
            case 4:
                orchestrationRunListener.testStarted(BundleJUnitUtils.getDescription(bundle));
                return;
            case 5:
                orchestrationRunListener.testFinished(BundleJUnitUtils.getDescription(bundle));
                return;
            case 6:
                orchestrationRunListener.testAssumptionFailure(BundleJUnitUtils.getFailure(bundle));
                return;
            case 7:
                orchestrationRunListener.testIgnored(BundleJUnitUtils.getDescription(bundle));
                return;
            default:
                Log.e(TAG, "Unknown notification type");
                return;
        }
    }
}
