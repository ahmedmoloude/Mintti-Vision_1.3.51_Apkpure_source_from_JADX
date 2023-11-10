package androidx.test.services.events.run;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.test.runner.internal.deps.aidl.BaseProxy;
import androidx.test.runner.internal.deps.aidl.BaseStub;
import androidx.test.runner.internal.deps.aidl.Codecs;

public interface ITestRunEvent extends IInterface {
    void send(TestRunEvent testRunEvent) throws RemoteException;

    public static abstract class Stub extends BaseStub implements ITestRunEvent {
        private static final String DESCRIPTOR = "androidx.test.services.events.run.ITestRunEvent";
        static final int TRANSACTION_send = 1;

        public Stub() {
            super(DESCRIPTOR);
        }

        public static ITestRunEvent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface instanceof ITestRunEvent) {
                return (ITestRunEvent) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        /* access modifiers changed from: protected */
        public boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1) {
                return false;
            }
            send((TestRunEvent) Codecs.createParcelable(parcel, TestRunEvent.CREATOR));
            parcel2.writeNoException();
            return true;
        }

        public static class Proxy extends BaseProxy implements ITestRunEvent {
            Proxy(IBinder iBinder) {
                super(iBinder, Stub.DESCRIPTOR);
            }

            public void send(TestRunEvent testRunEvent) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                Codecs.writeParcelable(obtainAndWriteInterfaceToken, testRunEvent);
                transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
            }
        }
    }
}
