package androidx.test.espresso.core.internal.deps.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract class BaseStub extends Binder implements IInterface {
    private static TransactionInterceptor globalInterceptor;

    protected BaseStub(String str) {
        attachInterface(this, str);
    }

    public IBinder asBinder() {
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        return false;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (routeToSuperOrEnforceInterface(i, parcel, parcel2, i2)) {
            return true;
        }
        TransactionInterceptor transactionInterceptor = globalInterceptor;
        if (transactionInterceptor == null) {
            return dispatchTransaction(i, parcel, parcel2, i2);
        }
        return transactionInterceptor.interceptTransaction(this, i, parcel, parcel2, i2);
    }

    /* access modifiers changed from: protected */
    public boolean routeToSuperOrEnforceInterface(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i > 16777215) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        parcel.enforceInterface(getInterfaceDescriptor());
        return false;
    }

    static synchronized void installTransactionInterceptorPackagePrivate(TransactionInterceptor transactionInterceptor) {
        synchronized (BaseStub.class) {
            if (transactionInterceptor != null) {
                try {
                    if (globalInterceptor == null) {
                        globalInterceptor = transactionInterceptor;
                    } else {
                        throw new IllegalStateException("Duplicate TransactionInterceptor installation.");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                throw new IllegalArgumentException("null interceptor");
            }
        }
    }
}
