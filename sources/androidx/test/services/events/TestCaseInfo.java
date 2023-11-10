package androidx.test.services.events;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.test.internal.util.Checks;
import java.util.ArrayList;
import java.util.List;

public final class TestCaseInfo implements Parcelable {
    public static final Parcelable.Creator<TestCaseInfo> CREATOR = new Parcelable.Creator<TestCaseInfo>() {
        public TestCaseInfo createFromParcel(Parcel parcel) {
            return new TestCaseInfo(parcel);
        }

        public TestCaseInfo[] newArray(int i) {
            return new TestCaseInfo[i];
        }
    };
    public final List<AnnotationInfo> classAnnotations;
    public final String className;
    public final List<AnnotationInfo> methodAnnotations;
    public final String methodName;

    public int describeContents() {
        return 0;
    }

    public TestCaseInfo(Parcel parcel) {
        Checks.checkNotNull(parcel, "source cannot be null");
        this.className = (String) Checks.checkNotNull(parcel.readString(), "className cannot be null");
        this.methodName = (String) Checks.checkNotNull(parcel.readString(), "methodName cannot be null");
        ArrayList arrayList = new ArrayList();
        this.methodAnnotations = arrayList;
        parcel.readTypedList(arrayList, AnnotationInfo.CREATOR);
        ArrayList arrayList2 = new ArrayList();
        this.classAnnotations = arrayList2;
        parcel.readTypedList(arrayList2, AnnotationInfo.CREATOR);
    }

    public TestCaseInfo(String str, String str2, List<AnnotationInfo> list, List<AnnotationInfo> list2) {
        this.className = (String) Checks.checkNotNull(str, "className cannot be null");
        this.methodName = (String) Checks.checkNotNull(str2, "methodName cannot be null");
        this.classAnnotations = (List) Checks.checkNotNull(list2, "classAnnotations cannot be null");
        this.methodAnnotations = (List) Checks.checkNotNull(list, "methodAnnotations cannot be null");
    }

    public String getClassAndMethodName() {
        String str = this.className;
        String str2 = this.methodName;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append("#");
        sb.append(str2);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.className);
        parcel.writeString(this.methodName);
        parcel.writeTypedList(this.methodAnnotations);
        parcel.writeTypedList(this.classAnnotations);
    }
}
