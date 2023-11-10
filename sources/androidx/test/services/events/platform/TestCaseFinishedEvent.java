package androidx.test.services.events.platform;

import android.os.Parcel;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.TestCaseInfo;
import androidx.test.services.events.TestStatus;
import androidx.test.services.events.TimeStamp;
import androidx.test.services.events.platform.TestPlatformEvent;

public class TestCaseFinishedEvent extends TestPlatformEvent {
    public final TestCaseInfo testCase;
    public final TestStatus testStatus;
    public final TimeStamp timeStamp;

    public TestCaseFinishedEvent(TestCaseInfo testCaseInfo, TestStatus testStatus2, TimeStamp timeStamp2) {
        this.testCase = (TestCaseInfo) Checks.checkNotNull(testCaseInfo, "testCase cannot be null");
        this.testStatus = (TestStatus) Checks.checkNotNull(testStatus2, "testStatus cannot be null");
        this.timeStamp = (TimeStamp) Checks.checkNotNull(timeStamp2, "timeStamp cannot be null");
    }

    TestCaseFinishedEvent(Parcel parcel) {
        this.testCase = new TestCaseInfo(parcel);
        this.testStatus = new TestStatus(parcel);
        this.timeStamp = new TimeStamp(parcel);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        this.testCase.writeToParcel(parcel, i);
        this.testStatus.writeToParcel(parcel, i);
        this.timeStamp.writeToParcel(parcel, i);
    }

    /* access modifiers changed from: package-private */
    public TestPlatformEvent.EventType instanceType() {
        return TestPlatformEvent.EventType.TEST_CASE_FINISHED;
    }
}
