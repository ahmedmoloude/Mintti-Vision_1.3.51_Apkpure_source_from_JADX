package androidx.test.services.events;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.test.internal.util.Checks;
import java.util.ArrayList;
import java.util.List;

public final class AnnotationInfo implements Parcelable {
    public static final Parcelable.Creator<AnnotationInfo> CREATOR = new Parcelable.Creator<AnnotationInfo>() {
        public AnnotationInfo createFromParcel(Parcel parcel) {
            return new AnnotationInfo(parcel);
        }

        public AnnotationInfo[] newArray(int i) {
            return new AnnotationInfo[i];
        }
    };
    public final String name;
    public final List<AnnotationValue> values;

    public int describeContents() {
        return 0;
    }

    public AnnotationInfo(String str, List<AnnotationValue> list) {
        Checks.checkNotNull(str, "annotationName cannot be null");
        Checks.checkNotNull(str, "annotationValues cannot be null");
        this.name = str;
        this.values = list;
    }

    private AnnotationInfo(Parcel parcel) {
        this.name = parcel.readString();
        ArrayList arrayList = new ArrayList();
        this.values = arrayList;
        parcel.readTypedList(arrayList, AnnotationValue.CREATOR);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeTypedList(this.values);
    }
}
