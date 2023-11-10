package androidx.test.services.events.discovery;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.test.runner.internal.deps.aidl.BaseProxy;
import androidx.test.runner.internal.deps.aidl.BaseStub;
import androidx.test.runner.internal.deps.aidl.Codecs;

public interface ITestDiscoveryEvent extends IInterface {
    void send(TestDiscoveryEvent testDiscoveryEvent) throws RemoteException;

    public static abstract class Stub extends BaseStub implements ITestDiscoveryEvent {
        private static final String DESCRIPTOR = "androidx.test.services.events.discovery.ITestDiscoveryEvent";
        static final int TRANSACTION_send = 1;

        public Stub() {
            super(DESCRIPTOR);
        }

        public static ITestDiscoveryEvent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface instanceof ITestDiscoveryEvent) {
                return (ITestDiscoveryEvent) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        /* access modifiers changed from: protected */
        public boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1) {
                return false;
            }
            send((TestDiscoveryEvent) Codecs.createParcelable(parcel, TestDiscoveryEvent.CREATOR));
            parcel2.writeNoException();
            return true;
        }

        public static class Proxy extends BaseProxy implements ITestDiscoveryEvent {
            Proxy(IBinder iBinder) {
                super(iBinder, Stub.DESCRIPTOR);
            }

            public void send(TestDiscoveryEvent testDiscoveryEvent) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                Codecs.writeParcelable(obtainAndWriteInterfaceToken, testDiscoveryEvent);
                transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
            }
        }
    }
}
