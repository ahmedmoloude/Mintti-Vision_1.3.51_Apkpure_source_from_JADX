package androidx.test.services.events.discovery;

import androidx.test.services.events.discovery.TestDiscoveryEvent;

public class TestDiscoveryFinishedEvent extends TestDiscoveryEvent {
    /* access modifiers changed from: package-private */
    public TestDiscoveryEvent.EventType instanceType() {
        return TestDiscoveryEvent.EventType.FINISHED;
    }
}
