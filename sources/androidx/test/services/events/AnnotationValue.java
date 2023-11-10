package androidx.test.services.events;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.test.internal.util.Checks;
import java.util.ArrayList;
import java.util.List;

public final class AnnotationValue implements Parcelable {
    public static final Parcelable.Creator<AnnotationValue> CREATOR = new Parcelable.Creator<AnnotationValue>() {
        public AnnotationValue createFromParcel(Parcel parcel) {
            return new AnnotationValue(parcel);
        }

        public AnnotationValue[] newArray(int i) {
            return new AnnotationValue[i];
        }
    };
    public final String fieldName;
    public final List<String> fieldValues;
    public final String valueType;

    public int describeContents() {
        return 0;
    }

    public AnnotationValue(String str, List<String> list, String str2) {
        Checks.checkNotNull(str, "fieldName cannot be null");
        Checks.checkNotNull(list, "fieldValues cannot be null");
        Checks.checkNotNull(str2, "valueType cannot be null");
        this.fieldName = str;
        this.fieldValues = list;
        this.valueType = str2;
    }

    private AnnotationValue(Parcel parcel) {
        this.fieldName = parcel.readString();
        ArrayList arrayList = new ArrayList();
        this.fieldValues = arrayList;
        parcel.readStringList(arrayList);
        this.valueType = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fieldName);
        parcel.writeStringList(this.fieldValues);
        parcel.writeString(this.valueType);
    }
}
