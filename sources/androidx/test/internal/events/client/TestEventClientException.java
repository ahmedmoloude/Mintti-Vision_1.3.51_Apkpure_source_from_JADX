package androidx.test.internal.events.client;

import androidx.test.services.events.TestEventException;

public final class TestEventClientException extends TestEventException {
    public TestEventClientException(String str) {
        super(str);
    }

    public TestEventClientException(String str, Throwable th) {
        super(str, th);
    }
}
