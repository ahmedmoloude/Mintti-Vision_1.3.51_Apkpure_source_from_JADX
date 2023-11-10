package androidx.test.espresso.remote;

import android.os.IBinder;

public interface Bindable {
    IBinder getIBinder();

    String getId();

    void setIBinder(IBinder iBinder);
}
