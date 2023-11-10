package androidx.test.services.events;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.test.internal.util.Checks;

public final class TestStatus implements Parcelable {
    public static final Parcelable.Creator<TestStatus> CREATOR = new Parcelable.Creator<TestStatus>() {
        public TestStatus createFromParcel(Parcel parcel) {
            return new TestStatus(parcel);
        }

        public TestStatus[] newArray(int i) {
            return new TestStatus[i];
        }
    };
    public Status status;

    public enum Status {
        CANCELLED,
        IGNORED,
        SKIPPED,
        ABORTED,
        PASSED,
        FAILED
    }

    public int describeContents() {
        return 0;
    }

    public TestStatus(Status status2) {
        this.status = (Status) Checks.checkNotNull(status2, "status cannot be null");
    }

    public TestStatus(Parcel parcel) {
        Checks.checkNotNull(parcel, "source cannot be null");
        this.status = Status.valueOf((String) Checks.checkNotNull(parcel.readString(), "status cannot be null"));
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.status.name());
    }
}
