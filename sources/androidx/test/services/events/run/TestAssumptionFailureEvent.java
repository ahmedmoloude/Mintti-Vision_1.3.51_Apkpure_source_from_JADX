package androidx.test.services.events.run;

import android.os.Parcel;
import androidx.test.services.events.FailureInfo;
import androidx.test.services.events.TestCaseInfo;
import androidx.test.services.events.run.TestRunEvent;

public class TestAssumptionFailureEvent extends TestFailureEvent {
    public TestAssumptionFailureEvent(TestCaseInfo testCaseInfo, FailureInfo failureInfo) {
        super(testCaseInfo, failureInfo);
    }

    TestAssumptionFailureEvent(Parcel parcel) {
        super(parcel);
    }

    /* access modifiers changed from: package-private */
    public TestRunEvent.EventType instanceType() {
        return TestRunEvent.EventType.TEST_ASSUMPTION_FAILURE;
    }
}
