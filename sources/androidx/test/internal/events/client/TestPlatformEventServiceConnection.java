package androidx.test.internal.events.client;

import android.os.RemoteException;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.platform.ITestPlatformEvent;
import androidx.test.services.events.platform.TestPlatformEvent;

public class TestPlatformEventServiceConnection extends TestEventServiceConnectionBase<ITestPlatformEvent> implements TestPlatformEventService {
    TestPlatformEventServiceConnection(String str, TestEventClientConnectListener testEventClientConnectListener) {
        super(str, TestPlatformEventServiceConnection$$Lambda$0.$instance, testEventClientConnectListener);
    }

    public void send(TestPlatformEvent testPlatformEvent) throws TestEventClientException {
        Checks.checkNotNull(testPlatformEvent, "testPlatformEvent cannot be null");
        if (this.service != null) {
            try {
                ((ITestPlatformEvent) this.service).send(testPlatformEvent);
            } catch (RemoteException e) {
                throw new TestEventClientException("Failed to send test platform event", e);
            }
        } else {
            throw new TestEventClientException("Can't send test platform event, service not connected");
        }
    }
}
