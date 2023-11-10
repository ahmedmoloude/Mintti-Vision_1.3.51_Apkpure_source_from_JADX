package androidx.test.orchestrator.junit;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.test.services.events.internal.StackTrimmer;
import org.junit.runner.notification.Failure;

public final class ParcelableFailure implements Parcelable {
    public static final Parcelable.Creator<ParcelableFailure> CREATOR = new Parcelable.Creator<ParcelableFailure>() {
        public ParcelableFailure createFromParcel(Parcel parcel) {
            return new ParcelableFailure(parcel);
        }

        public ParcelableFailure[] newArray(int i) {
            return new ParcelableFailure[i];
        }
    };
    private static final int MAX_STREAM_LENGTH = 16384;
    private static final String TAG = "ParcelableFailure";
    private final ParcelableDescription description;
    private final String trace;

    public int describeContents() {
        return 0;
    }

    public ParcelableFailure(Failure failure) {
        this.description = new ParcelableDescription(failure.getDescription());
        this.trace = StackTrimmer.getTrimmedStackTrace(failure);
    }

    private ParcelableFailure(Parcel parcel) {
        this.description = (ParcelableDescription) parcel.readParcelable(ParcelableDescription.class.getClassLoader());
        this.trace = parcel.readString();
    }

    public ParcelableFailure(ParcelableDescription parcelableDescription, Throwable th) {
        this(parcelableDescription, th.getMessage());
    }

    public ParcelableFailure(ParcelableDescription parcelableDescription, String str) {
        this.description = parcelableDescription;
        this.trace = trimToLength(str);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.description, 0);
        parcel.writeString(this.trace);
    }

    private static String trimToLength(String str) {
        if (!str.endsWith("\n")) {
            str = String.valueOf(str).concat("\n");
        }
        if (str.length() <= 16384) {
            return str;
        }
        Log.i(TAG, String.format("Stack trace too long, trimmed to first %s characters.", new Object[]{16383}));
        return String.valueOf(str.substring(0, 16383)).concat("\n");
    }

    public String getTrace() {
        return this.trace;
    }

    public ParcelableDescription getDescription() {
        return this.description;
    }
}
