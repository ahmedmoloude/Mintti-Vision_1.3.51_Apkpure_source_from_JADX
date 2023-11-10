package androidx.test.espresso.core.internal.deps.aidl;

import android.os.Parcel;
import android.os.RemoteException;

public interface TransactionInterceptor {
    boolean interceptTransaction(BaseStub baseStub, int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException;
}
