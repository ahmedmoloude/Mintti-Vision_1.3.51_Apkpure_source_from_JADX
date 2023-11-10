package androidx.test.espresso.remote;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.test.espresso.core.internal.deps.aidl.BaseProxy;
import androidx.test.espresso.core.internal.deps.aidl.BaseStub;
import androidx.test.espresso.core.internal.deps.aidl.Codecs;

public interface IInteractionExecutionStatus extends IInterface {

    public static abstract class Stub extends BaseStub implements IInteractionExecutionStatus {
        private static final String DESCRIPTOR = "androidx.test.espresso.remote.IInteractionExecutionStatus";
        static final int TRANSACTION_canExecute = 1;

        public static class Proxy extends BaseProxy implements IInteractionExecutionStatus {
            Proxy(IBinder iBinder) {
                super(iBinder, Stub.DESCRIPTOR);
            }

            public boolean canExecute() throws RemoteException {
                Parcel transactAndReadException = transactAndReadException(1, obtainAndWriteInterfaceToken());
                boolean createBoolean = Codecs.createBoolean(transactAndReadException);
                transactAndReadException.recycle();
                return createBoolean;
            }
        }

        public Stub() {
            super(DESCRIPTOR);
        }

        /* access modifiers changed from: protected */
        public boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1) {
                return false;
            }
            boolean canExecute = canExecute();
            parcel2.writeNoException();
            Codecs.writeBoolean(parcel2, canExecute);
            return true;
        }

        public static IInteractionExecutionStatus asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface instanceof IInteractionExecutionStatus) {
                return (IInteractionExecutionStatus) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }
    }

    boolean canExecute() throws RemoteException;
}
