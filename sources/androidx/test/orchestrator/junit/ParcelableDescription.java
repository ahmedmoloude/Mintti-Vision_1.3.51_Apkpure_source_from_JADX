package androidx.test.orchestrator.junit;

import android.os.Parcel;
import android.os.Parcelable;
import org.junit.runner.Description;

public final class ParcelableDescription implements Parcelable {
    public static final Parcelable.Creator<ParcelableDescription> CREATOR = new Parcelable.Creator<ParcelableDescription>() {
        public ParcelableDescription createFromParcel(Parcel parcel) {
            return new ParcelableDescription(parcel);
        }

        public ParcelableDescription[] newArray(int i) {
            return new ParcelableDescription[i];
        }
    };
    private final String className;
    private final String displayName;
    private final String methodName;

    public int describeContents() {
        return 0;
    }

    public ParcelableDescription(Description description) {
        this.className = description.getClassName();
        this.methodName = description.getMethodName();
        this.displayName = description.getDisplayName();
    }

    public ParcelableDescription(String str) {
        String[] split = str.split("#");
        String str2 = "";
        if (split.length > 0) {
            this.className = split[0];
            this.methodName = split.length > 1 ? split[1] : str2;
        } else {
            this.className = str2;
            this.methodName = str2;
        }
        this.displayName = String.format("%s(%s)", new Object[]{this.methodName, this.className});
    }

    private ParcelableDescription(Parcel parcel) {
        this.className = getNonNullString(parcel);
        this.methodName = getNonNullString(parcel);
        this.displayName = getNonNullString(parcel);
    }

    private String getNonNullString(Parcel parcel) {
        String readString = parcel.readString();
        return readString == null ? "" : readString;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.className);
        parcel.writeString(this.methodName);
        parcel.writeString(this.displayName);
    }

    public String getClassName() {
        return this.className;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
