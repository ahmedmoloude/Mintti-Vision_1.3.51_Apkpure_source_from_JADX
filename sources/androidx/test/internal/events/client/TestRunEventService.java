package androidx.test.internal.events.client;

import androidx.test.services.events.run.TestRunEvent;

public interface TestRunEventService {
    void send(TestRunEvent testRunEvent) throws TestEventClientException;
}
