package androidx.test.services.events;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.test.internal.util.Checks;

public final class TimeStamp implements Parcelable {
    public static final Parcelable.Creator<TimeStamp> CREATOR = new Parcelable.Creator<TimeStamp>() {
        public TimeStamp createFromParcel(Parcel parcel) {
            return new TimeStamp(parcel);
        }

        public TimeStamp[] newArray(int i) {
            return new TimeStamp[i];
        }
    };
    public final Integer nanos;
    public final Long seconds;

    public int describeContents() {
        return 0;
    }

    public TimeStamp(Long l, Integer num) {
        this.seconds = (Long) Checks.checkNotNull(l, "seconds cannot be null");
        this.nanos = (Integer) Checks.checkNotNull(num, "nanos cannot be null");
    }

    public TimeStamp(Parcel parcel) {
        Checks.checkNotNull(parcel, "source cannot be null");
        this.seconds = (Long) Checks.checkNotNull(Long.valueOf(parcel.readLong()), "seconds cannot be null");
        this.nanos = (Integer) Checks.checkNotNull(Integer.valueOf(parcel.readInt()), "nanos cannot be null");
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.seconds.longValue());
        parcel.writeInt(this.nanos.intValue());
    }
}
