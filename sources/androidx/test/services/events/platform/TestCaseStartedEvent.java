package androidx.test.services.events.platform;

import android.os.Parcel;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.TestCaseInfo;
import androidx.test.services.events.TimeStamp;
import androidx.test.services.events.platform.TestPlatformEvent;

public final class TestCaseStartedEvent extends TestPlatformEvent {
    public final TestCaseInfo testCase;
    public final TimeStamp timeStamp;

    public TestCaseStartedEvent(TestCaseInfo testCaseInfo, TimeStamp timeStamp2) {
        this.testCase = (TestCaseInfo) Checks.checkNotNull(testCaseInfo, "testCase cannot be null");
        this.timeStamp = (TimeStamp) Checks.checkNotNull(timeStamp2, "timeStamp cannot be null");
    }

    public TestCaseStartedEvent(Parcel parcel) {
        this.testCase = new TestCaseInfo(parcel);
        this.timeStamp = new TimeStamp(parcel);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        this.testCase.writeToParcel(parcel, i);
        this.timeStamp.writeToParcel(parcel, i);
    }

    public TestPlatformEvent.EventType instanceType() {
        return TestPlatformEvent.EventType.TEST_CASE_STARTED;
    }
}
