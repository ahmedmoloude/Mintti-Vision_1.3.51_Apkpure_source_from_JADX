package androidx.test.internal.events.client;

import androidx.test.services.events.discovery.TestDiscoveryEvent;

public interface TestDiscoveryEventService {
    void send(TestDiscoveryEvent testDiscoveryEvent) throws TestEventClientException;
}
