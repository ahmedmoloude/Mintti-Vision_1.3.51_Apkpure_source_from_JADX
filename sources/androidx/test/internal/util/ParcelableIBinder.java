package androidx.test.internal.util;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableIBinder implements Parcelable {
    public static final Parcelable.Creator<ParcelableIBinder> CREATOR = new Parcelable.Creator<ParcelableIBinder>() {
        public ParcelableIBinder createFromParcel(Parcel parcel) {
            return new ParcelableIBinder(parcel);
        }

        public ParcelableIBinder[] newArray(int i) {
            return new ParcelableIBinder[i];
        }
    };
    private final IBinder iBinder;

    public int describeContents() {
        return 0;
    }

    public ParcelableIBinder(IBinder iBinder2) {
        this.iBinder = (IBinder) Checks.checkNotNull(iBinder2);
    }

    public IBinder getIBinder() {
        return this.iBinder;
    }

    protected ParcelableIBinder(Parcel parcel) {
        this.iBinder = parcel.readStrongBinder();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.iBinder);
    }
}
