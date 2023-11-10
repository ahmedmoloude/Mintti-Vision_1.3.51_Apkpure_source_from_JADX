package androidx.test.services.events;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.test.internal.util.Checks;

public final class ErrorInfo implements Parcelable {
    public static final Parcelable.Creator<ErrorInfo> CREATOR = new Parcelable.Creator<ErrorInfo>() {
        public ErrorInfo createFromParcel(Parcel parcel) {
            return new ErrorInfo(parcel);
        }

        public ErrorInfo[] newArray(int i) {
            return new ErrorInfo[i];
        }
    };
    public final String errorMessage;
    public final String errorType;
    public final String stackTrace;

    public int describeContents() {
        return 0;
    }

    public ErrorInfo(String str, String str2, String str3) {
        this.errorMessage = str;
        this.errorType = str2;
        this.stackTrace = (String) Checks.checkNotNull(str3, "stackTrace cannot be null");
    }

    public ErrorInfo(Parcel parcel) {
        Checks.checkNotNull(parcel, "source cannot be null");
        this.errorMessage = parcel.readString();
        this.errorType = parcel.readString();
        this.stackTrace = (String) Checks.checkNotNull(parcel.readString(), "stackTrace cannot be null");
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.errorMessage);
        parcel.writeString(this.errorType);
        parcel.writeString(this.stackTrace);
    }
}
