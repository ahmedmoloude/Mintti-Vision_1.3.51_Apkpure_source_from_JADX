package androidx.test.internal.events.client;

import android.os.RemoteException;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.run.ITestRunEvent;
import androidx.test.services.events.run.TestRunEvent;

public class TestRunEventServiceConnection extends TestEventServiceConnectionBase<ITestRunEvent> implements TestRunEventService {
    TestRunEventServiceConnection(String str, TestEventClientConnectListener testEventClientConnectListener) {
        super(str, TestRunEventServiceConnection$$Lambda$0.$instance, testEventClientConnectListener);
    }

    public void send(TestRunEvent testRunEvent) throws TestEventClientException {
        Checks.checkNotNull(testRunEvent, "testRunEvent cannot be null");
        if (this.service != null) {
            try {
                ((ITestRunEvent) this.service).send(testRunEvent);
            } catch (RemoteException e) {
                throw new TestEventClientException("Failed to send test run event", e);
            }
        } else {
            throw new TestEventClientException("Can't send test run event, service not connected");
        }
    }
}
