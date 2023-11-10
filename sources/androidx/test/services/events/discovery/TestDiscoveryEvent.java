package androidx.test.services.events.discovery;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class TestDiscoveryEvent implements Parcelable {
    public static final Parcelable.Creator<TestDiscoveryEvent> CREATOR = new TestDiscoveryEventFactory();

    enum EventType {
        STARTED,
        TEST_FOUND,
        FINISHED
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public abstract EventType instanceType();

    TestDiscoveryEvent() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(instanceType().name());
    }
}
