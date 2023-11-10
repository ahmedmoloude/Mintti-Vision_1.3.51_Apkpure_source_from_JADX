package androidx.test.services.events.platform;

import android.os.Parcel;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.ErrorInfo;
import androidx.test.services.events.TestRunInfo;
import androidx.test.services.events.TimeStamp;
import androidx.test.services.events.platform.TestPlatformEvent;

public class TestRunErrorEvent extends TestPlatformEvent {
    public final ErrorInfo error;
    public final TestRunInfo testRun;
    public final TimeStamp timeStamp;

    public TestRunErrorEvent(TestRunInfo testRunInfo, ErrorInfo errorInfo, TimeStamp timeStamp2) {
        this.testRun = (TestRunInfo) Checks.checkNotNull(testRunInfo, "testRun cannot be null");
        this.error = (ErrorInfo) Checks.checkNotNull(errorInfo, "error cannot be null");
        this.timeStamp = (TimeStamp) Checks.checkNotNull(timeStamp2, "timeStamp cannot be null");
    }

    TestRunErrorEvent(Parcel parcel) {
        this.testRun = new TestRunInfo(parcel);
        this.error = new ErrorInfo(parcel);
        this.timeStamp = new TimeStamp(parcel);
    }

    /* access modifiers changed from: package-private */
    public TestPlatformEvent.EventType instanceType() {
        return TestPlatformEvent.EventType.TEST_RUN_ERROR;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        this.testRun.writeToParcel(parcel, i);
        this.error.writeToParcel(parcel, i);
        this.timeStamp.writeToParcel(parcel, i);
    }
}
