package androidx.test.services.events.run;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class TestRunEvent implements Parcelable {
    public static final Parcelable.Creator<TestRunEvent> CREATOR = new TestRunEventFactory();

    enum EventType {
        STARTED,
        TEST_STARTED,
        TEST_FINISHED,
        TEST_ASSUMPTION_FAILURE,
        TEST_FAILURE,
        TEST_IGNORED,
        FINISHED
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public abstract EventType instanceType();

    TestRunEvent() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(instanceType().name());
    }
}
