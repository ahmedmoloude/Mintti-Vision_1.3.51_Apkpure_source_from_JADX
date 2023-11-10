package androidx.test.services.events.discovery;

import android.os.Parcel;
import androidx.test.internal.util.Checks;
import androidx.test.services.events.TestCaseInfo;
import androidx.test.services.events.discovery.TestDiscoveryEvent;

public class TestFoundEvent extends TestDiscoveryEvent {
    public final TestCaseInfo testCase;

    public TestFoundEvent(TestCaseInfo testCaseInfo) {
        Checks.checkNotNull(testCaseInfo, "testCase cannot be null");
        this.testCase = testCaseInfo;
    }

    TestFoundEvent(Parcel parcel) {
        this.testCase = new TestCaseInfo(parcel);
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        this.testCase.writeToParcel(parcel, i);
    }

    /* access modifiers changed from: package-private */
    public TestDiscoveryEvent.EventType instanceType() {
        return TestDiscoveryEvent.EventType.TEST_FOUND;
    }
}
