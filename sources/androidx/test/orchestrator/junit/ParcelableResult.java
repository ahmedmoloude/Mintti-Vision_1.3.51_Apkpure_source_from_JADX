package androidx.test.orchestrator.junit;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public final class ParcelableResult implements Parcelable {
    public static final Parcelable.Creator<ParcelableResult> CREATOR = new Parcelable.Creator<ParcelableResult>() {
        public ParcelableResult createFromParcel(Parcel parcel) {
            return new ParcelableResult(parcel);
        }

        public ParcelableResult[] newArray(int i) {
            return new ParcelableResult[i];
        }
    };
    private final List<ParcelableFailure> failures;

    public int describeContents() {
        return 0;
    }

    public ParcelableResult(List<ParcelableFailure> list) {
        this.failures = list;
    }

    public ParcelableResult(Result result) {
        this.failures = new ArrayList();
        for (Failure parcelableFailure : result.getFailures()) {
            this.failures.add(new ParcelableFailure(parcelableFailure));
        }
    }

    private ParcelableResult(Parcel parcel) {
        this.failures = new ArrayList();
        for (Object obj : parcel.readArray(ParcelableFailure[].class.getClassLoader())) {
            this.failures.add((ParcelableFailure) obj);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeArray(this.failures.toArray());
    }

    public boolean wasSuccessful() {
        return this.failures.isEmpty();
    }

    public List<ParcelableFailure> getFailures() {
        return this.failures;
    }

    public int getFailureCount() {
        return this.failures.size();
    }
}
