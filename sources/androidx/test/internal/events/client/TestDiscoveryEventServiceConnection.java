package androidx.test.internal.events.client;

import android.os.RemoteException;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.discovery.ITestDiscoveryEvent;
import androidx.test.services.events.discovery.TestDiscoveryEvent;

public class TestDiscoveryEventServiceConnection extends TestEventServiceConnectionBase<ITestDiscoveryEvent> implements TestDiscoveryEventService {
    TestDiscoveryEventServiceConnection(String str, TestEventClientConnectListener testEventClientConnectListener) {
        super(str, TestDiscoveryEventServiceConnection$$Lambda$0.$instance, testEventClientConnectListener);
    }

    public void send(TestDiscoveryEvent testDiscoveryEvent) throws TestEventClientException {
        Checks.checkNotNull(testDiscoveryEvent, "testDiscoveryEvent cannot be null");
        if (this.service != null) {
            try {
                ((ITestDiscoveryEvent) this.service).send(testDiscoveryEvent);
            } catch (RemoteException e) {
                throw new TestEventClientException("Failed to send test case", e);
            }
        } else {
            throw new TestEventClientException("Can't add test, service not connected");
        }
    }
}
