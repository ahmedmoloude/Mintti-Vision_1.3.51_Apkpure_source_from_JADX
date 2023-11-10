package androidx.test.services.events.run;

import android.os.Parcel;
import androidx.test.services.events.TestCaseInfo;
import androidx.test.services.events.run.TestRunEvent;

public class TestFinishedEvent extends TestRunEventWithTestCase {
    public TestFinishedEvent(TestCaseInfo testCaseInfo) {
        super(testCaseInfo);
    }

    TestFinishedEvent(Parcel parcel) {
        super(parcel);
    }

    /* access modifiers changed from: package-private */
    public TestRunEvent.EventType instanceType() {
        return TestRunEvent.EventType.TEST_FINISHED;
    }
}
