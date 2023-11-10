package androidx.test.services.events;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.test.internal.util.Checks;

public final class FailureInfo implements Parcelable {
    public static final Parcelable.Creator<FailureInfo> CREATOR = new Parcelable.Creator<FailureInfo>() {
        public FailureInfo createFromParcel(Parcel parcel) {
            return new FailureInfo(parcel);
        }

        public FailureInfo[] newArray(int i) {
            return new FailureInfo[i];
        }
    };
    public final String failureMessage;
    public final String failureType;
    public final String stackTrace;
    public final TestCaseInfo testCase;

    public int describeContents() {
        return 0;
    }

    public FailureInfo(String str, String str2, String str3, TestCaseInfo testCaseInfo) {
        Checks.checkNotNull(str3, "stackTrace cannot be null");
        Checks.checkNotNull(testCaseInfo, "testCase cannot be null");
        this.failureMessage = str;
        this.failureType = str2;
        this.stackTrace = str3;
        this.testCase = testCaseInfo;
    }

    public FailureInfo(Parcel parcel) {
        Checks.checkNotNull(parcel, "source cannot be null");
        this.failureMessage = parcel.readString();
        this.failureType = parcel.readString();
        this.stackTrace = parcel.readString();
        this.testCase = (TestCaseInfo) parcel.readParcelable(TestCaseInfo.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.failureMessage);
        parcel.writeString(this.failureType);
        parcel.writeString(this.stackTrace);
        parcel.writeParcelable(this.testCase, i);
    }
}
