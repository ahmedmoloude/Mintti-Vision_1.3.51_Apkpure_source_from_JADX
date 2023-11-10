package p035me.yokeyword.fragmentation.helper.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: me.yokeyword.fragmentation.helper.internal.ResultRecord */
public final class ResultRecord implements Parcelable {
    public static final Parcelable.Creator<ResultRecord> CREATOR = new Parcelable.Creator<ResultRecord>() {
        public ResultRecord createFromParcel(Parcel parcel) {
            return new ResultRecord(parcel);
        }

        public ResultRecord[] newArray(int i) {
            return new ResultRecord[i];
        }
    };
    public int requestCode;
    public Bundle resultBundle;
    public int resultCode = 0;

    public int describeContents() {
        return 0;
    }

    public ResultRecord() {
    }

    protected ResultRecord(Parcel parcel) {
        this.requestCode = parcel.readInt();
        this.resultCode = parcel.readInt();
        this.resultBundle = parcel.readBundle(getClass().getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.requestCode);
        parcel.writeInt(this.resultCode);
        parcel.writeBundle(this.resultBundle);
    }
}
