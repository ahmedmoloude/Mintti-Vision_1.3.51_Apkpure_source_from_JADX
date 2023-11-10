package androidx.test.services.events.run;

import android.os.Parcel;
import androidx.test.services.events.TestCaseInfo;
import androidx.test.services.events.run.TestRunEvent;

public class TestRunStartedEvent extends TestRunEventWithTestCase {
    public TestRunStartedEvent(TestCaseInfo testCaseInfo) {
        super(testCaseInfo);
    }

    TestRunStartedEvent(Parcel parcel) {
        super(parcel);
    }

    /* access modifiers changed from: package-private */
    public TestRunEvent.EventType instanceType() {
        return TestRunEvent.EventType.STARTED;
    }
}
