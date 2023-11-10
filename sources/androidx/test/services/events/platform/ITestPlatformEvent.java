package androidx.test.services.events.platform;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.test.runner.internal.deps.aidl.BaseProxy;
import androidx.test.runner.internal.deps.aidl.BaseStub;
import androidx.test.runner.internal.deps.aidl.Codecs;

public interface ITestPlatformEvent extends IInterface {
    void send(TestPlatformEvent testPlatformEvent) throws RemoteException;

    public static abstract class Stub extends BaseStub implements ITestPlatformEvent {
        private static final String DESCRIPTOR = "androidx.test.services.events.platform.ITestPlatformEvent";
        static final int TRANSACTION_send = 1;

        public Stub() {
            super(DESCRIPTOR);
        }

        public static ITestPlatformEvent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface instanceof ITestPlatformEvent) {
                return (ITestPlatformEvent) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        /* access modifiers changed from: protected */
        public boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i != 1) {
                return false;
            }
            send((TestPlatformEvent) Codecs.createParcelable(parcel, TestPlatformEvent.CREATOR));
            parcel2.writeNoException();
            return true;
        }

        public static class Proxy extends BaseProxy implements ITestPlatformEvent {
            Proxy(IBinder iBinder) {
                super(iBinder, Stub.DESCRIPTOR);
            }

            public void send(TestPlatformEvent testPlatformEvent) throws RemoteException {
                Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
                Codecs.writeParcelable(obtainAndWriteInterfaceToken, testPlatformEvent);
                transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
            }
        }
    }
}
