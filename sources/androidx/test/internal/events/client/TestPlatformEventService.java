package androidx.test.internal.events.client;

import androidx.test.services.events.platform.TestPlatformEvent;

public interface TestPlatformEventService {
    void send(TestPlatformEvent testPlatformEvent) throws TestEventClientException;
}
