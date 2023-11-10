package androidx.test.core.p011os;

import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: androidx.test.core.os.Parcelables */
public final class Parcelables {
    public static <T extends Parcelable> T forceParcel(T t, Parcelable.Creator<T> creator) {
        Parcel obtain = Parcel.obtain();
        try {
            t.writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            return (Parcelable) creator.createFromParcel(obtain);
        } finally {
            obtain.recycle();
        }
    }

    private Parcelables() {
    }
}
