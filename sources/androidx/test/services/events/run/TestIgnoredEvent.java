package androidx.test.services.events.run;

import android.os.Parcel;
import androidx.test.services.events.TestCaseInfo;
import androidx.test.services.events.run.TestRunEvent;

public class TestIgnoredEvent extends TestRunEventWithTestCase {
    public TestIgnoredEvent(TestCaseInfo testCaseInfo) {
        super(testCaseInfo);
    }

    TestIgnoredEvent(Parcel parcel) {
        super(parcel);
    }

    /* access modifiers changed from: package-private */
    public TestRunEvent.EventType instanceType() {
        return TestRunEvent.EventType.TEST_IGNORED;
    }
}
