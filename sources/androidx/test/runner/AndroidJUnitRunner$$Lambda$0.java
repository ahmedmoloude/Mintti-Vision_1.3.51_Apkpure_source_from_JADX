package androidx.test.runner;

import androidx.test.internal.events.client.TestEventClientArgs;
import androidx.test.internal.events.client.TestEventClientConnectListener;
import androidx.test.internal.events.client.TestEventServiceConnection;
import androidx.test.orchestrator.callback.OrchestratorV1Connection;

/* compiled from: AndroidJUnitRunner */
final /* synthetic */ class AndroidJUnitRunner$$Lambda$0 implements TestEventClientArgs.ConnectionFactory {
    static final TestEventClientArgs.ConnectionFactory $instance = new AndroidJUnitRunner$$Lambda$0();

    private AndroidJUnitRunner$$Lambda$0() {
    }

    public TestEventServiceConnection create(TestEventClientConnectListener testEventClientConnectListener) {
        return new OrchestratorV1Connection(testEventClientConnectListener);
    }
}
