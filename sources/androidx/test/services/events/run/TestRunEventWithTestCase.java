package androidx.test.services.events.run;

import android.os.Parcel;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.TestCaseInfo;

public abstract class TestRunEventWithTestCase extends TestRunEvent {
    public final TestCaseInfo testCase;

    TestRunEventWithTestCase(Parcel parcel) {
        this.testCase = new TestCaseInfo(parcel);
    }

    TestRunEventWithTestCase(TestCaseInfo testCaseInfo) {
        Checks.checkNotNull(testCaseInfo, "testCase cannot be null");
        this.testCase = testCaseInfo;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        this.testCase.writeToParcel(parcel, i);
    }
}
