package androidx.test.services.events.platform;

import android.os.Parcel;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.ErrorInfo;
import androidx.test.services.events.TestCaseInfo;
import androidx.test.services.events.TimeStamp;
import androidx.test.services.events.platform.TestPlatformEvent;

public class TestCaseErrorEvent extends TestPlatformEvent {
    public final ErrorInfo error;
    public final TestCaseInfo testCase;
    public final TimeStamp timeStamp;

    public TestCaseErrorEvent(TestCaseInfo testCaseInfo, ErrorInfo errorInfo, TimeStamp timeStamp2) {
        this.testCase = (TestCaseInfo) Checks.checkNotNull(testCaseInfo, "testCase cannot be null");
        this.error = (ErrorInfo) Checks.checkNotNull(errorInfo, "error cannot be null");
        this.timeStamp = (TimeStamp) Checks.checkNotNull(timeStamp2, "timeStamp cannot be null");
    }

    TestCaseErrorEvent(Parcel parcel) {
        this.testCase = new TestCaseInfo(parcel);
        this.error = new ErrorInfo(parcel);
        this.timeStamp = new TimeStamp(parcel);
    }

    /* access modifiers changed from: package-private */
    public TestPlatformEvent.EventType instanceType() {
        return TestPlatformEvent.EventType.TEST_CASE_ERROR;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        this.testCase.writeToParcel(parcel, i);
        this.error.writeToParcel(parcel, i);
        this.timeStamp.writeToParcel(parcel, i);
    }
}
