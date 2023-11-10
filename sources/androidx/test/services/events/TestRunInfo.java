package androidx.test.services.events;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.test.internal.util.Checks;
import java.util.ArrayList;
import java.util.List;

public final class TestRunInfo implements Parcelable {
    public static final Parcelable.Creator<TestRunInfo> CREATOR = new Parcelable.Creator<TestRunInfo>() {
        public TestRunInfo createFromParcel(Parcel parcel) {
            return new TestRunInfo(parcel);
        }

        public TestRunInfo[] newArray(int i) {
            return new TestRunInfo[i];
        }
    };
    public final List<TestCaseInfo> testCases;
    public final String testRunName;

    public int describeContents() {
        return 0;
    }

    public TestRunInfo(String str, List<TestCaseInfo> list) {
        this.testRunName = (String) Checks.checkNotNull(str, "testRunName cannot be null");
        this.testCases = (List) Checks.checkNotNull(list, "testCases cannot be null");
    }

    public TestRunInfo(Parcel parcel) {
        Checks.checkNotNull(parcel, "source cannot be null");
        this.testRunName = (String) Checks.checkNotNull(parcel.readString(), "className cannot be null");
        ArrayList arrayList = new ArrayList();
        this.testCases = arrayList;
        parcel.readTypedList(arrayList, TestCaseInfo.CREATOR);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.testRunName);
        parcel.writeTypedList(this.testCases);
    }
}
