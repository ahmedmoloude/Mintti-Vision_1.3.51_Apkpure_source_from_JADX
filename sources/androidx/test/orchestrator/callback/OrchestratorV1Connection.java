package androidx.test.orchestrator.callback;

import android.os.RemoteException;
import androidx.test.internal.events.client.TestDiscoveryEventService;
import androidx.test.internal.events.client.TestEventClientConnectListener;
import androidx.test.internal.events.client.TestEventClientException;
import androidx.test.internal.events.client.TestEventServiceConnectionBase;
import androidx.test.internal.events.client.TestRunEventService;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.discovery.TestDiscoveryEvent;
import androidx.test.services.events.discovery.TestFoundEvent;
import androidx.test.services.events.run.TestRunEvent;

public final class OrchestratorV1Connection extends TestEventServiceConnectionBase<OrchestratorCallback> implements TestRunEventService, TestDiscoveryEventService {
    private static final String ORCHESTRATOR_SERVICE = "androidx.test.orchestrator/.OrchestratorService";

    public OrchestratorV1Connection(TestEventClientConnectListener testEventClientConnectListener) {
        super(ORCHESTRATOR_SERVICE, OrchestratorV1Connection$$Lambda$0.$instance, testEventClientConnectListener);
    }

    public void send(TestRunEvent testRunEvent) throws TestEventClientException {
        Checks.checkNotNull(testRunEvent, "event cannot be null");
        if (this.service != null) {
            try {
                ((OrchestratorCallback) this.service).sendTestNotification(BundleConverter.getBundleFromTestRunEvent(testRunEvent));
            } catch (RemoteException e) {
                String valueOf = String.valueOf(testRunEvent.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32);
                sb.append("Unable to send test run event [");
                sb.append(valueOf);
                sb.append("]");
                throw new TestEventClientException(sb.toString(), e);
            }
        } else {
            throw new TestEventClientException("Unable to send notification, Orchestrator callback is null");
        }
    }

    public void send(TestDiscoveryEvent testDiscoveryEvent) throws TestEventClientException {
        Checks.checkNotNull(testDiscoveryEvent, "event cannot be null");
        if (this.service == null) {
            throw new TestEventClientException("Unable to add test, Orchestrator callback is null");
        } else if (testDiscoveryEvent instanceof TestFoundEvent) {
            String classAndMethodName = ((TestFoundEvent) testDiscoveryEvent).testCase.getClassAndMethodName();
            try {
                ((OrchestratorCallback) this.service).addTest(classAndMethodName);
            } catch (RemoteException e) {
                StringBuilder sb = new StringBuilder(String.valueOf(classAndMethodName).length() + 21);
                sb.append("Failed to add test [");
                sb.append(classAndMethodName);
                sb.append("]");
                throw new TestEventClientException(sb.toString(), e);
            }
        }
    }
}
