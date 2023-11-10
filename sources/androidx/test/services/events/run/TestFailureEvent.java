package androidx.test.services.events.run;

import android.os.Parcel;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.FailureInfo;
import androidx.test.services.events.TestCaseInfo;
import androidx.test.services.events.run.TestRunEvent;

public class TestFailureEvent extends TestRunEventWithTestCase {
    public final FailureInfo failure;

    public TestFailureEvent(TestCaseInfo testCaseInfo, FailureInfo failureInfo) {
        super(testCaseInfo);
        Checks.checkNotNull(failureInfo, "failure cannot be null");
        this.failure = failureInfo;
    }

    TestFailureEvent(Parcel parcel) {
        super(parcel);
        this.failure = new FailureInfo(parcel);
    }

    /* access modifiers changed from: package-private */
    public TestRunEvent.EventType instanceType() {
        return TestRunEvent.EventType.TEST_FAILURE;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, 0);
        this.failure.writeToParcel(parcel, 0);
    }
}
