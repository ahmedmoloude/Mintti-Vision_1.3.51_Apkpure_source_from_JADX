package androidx.test.espresso.core.internal.deps.aidl;

import android.os.Parcel;

public class Codecs {
    private static final ClassLoader CLASS_LOADER = Codecs.class.getClassLoader();

    private Codecs() {
    }

    public static boolean createBoolean(Parcel parcel) {
        return parcel.readInt() != 0;
    }

    public static void writeBoolean(Parcel parcel, boolean z) {
        parcel.writeInt(z ? 1 : 0);
    }
}
