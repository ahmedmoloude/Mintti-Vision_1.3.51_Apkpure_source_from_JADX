package androidx.test.services.events.platform;

import android.os.Parcel;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.TestRunInfo;
import androidx.test.services.events.TestStatus;
import androidx.test.services.events.TimeStamp;
import androidx.test.services.events.platform.TestPlatformEvent;

public class TestRunFinishedEvent extends TestPlatformEvent {
    public final TestStatus runStatus;
    public final TestRunInfo testRun;
    public final TimeStamp timeStamp;

    public TestRunFinishedEvent(TestRunInfo testRunInfo, TestStatus testStatus, TimeStamp timeStamp2) {
        this.testRun = (TestRunInfo) Checks.checkNotNull(testRunInfo, "testRun cannot be null");
        this.runStatus = (TestStatus) Checks.checkNotNull(testStatus, "runStatus cannot be null");
        this.timeStamp = (TimeStamp) Checks.checkNotNull(timeStamp2, "timeStamp cannot be null");
    }

    TestRunFinishedEvent(Parcel parcel) {
        this.testRun = new TestRunInfo(parcel);
        this.runStatus = new TestStatus(parcel);
        this.timeStamp = new TimeStamp(parcel);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        this.testRun.writeToParcel(parcel, i);
        this.runStatus.writeToParcel(parcel, i);
        this.timeStamp.writeToParcel(parcel, i);
    }

    /* access modifiers changed from: package-private */
    public TestPlatformEvent.EventType instanceType() {
        return TestPlatformEvent.EventType.TEST_RUN_FINISHED;
    }
}
