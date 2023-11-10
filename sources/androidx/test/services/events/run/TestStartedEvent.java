package androidx.test.services.events.run;

import android.os.Parcel;
import androidx.test.services.events.TestCaseInfo;
import androidx.test.services.events.run.TestRunEvent;

public class TestStartedEvent extends TestRunEventWithTestCase {
    public TestStartedEvent(TestCaseInfo testCaseInfo) {
        super(testCaseInfo);
    }

    TestStartedEvent(Parcel parcel) {
        super(parcel);
    }

    /* access modifiers changed from: package-private */
    public TestRunEvent.EventType instanceType() {
        return TestRunEvent.EventType.TEST_STARTED;
    }
}
