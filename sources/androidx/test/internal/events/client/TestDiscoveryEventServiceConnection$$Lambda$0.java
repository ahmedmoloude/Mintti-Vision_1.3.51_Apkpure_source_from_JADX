package androidx.test.internal.events.client;

import android.os.IBinder;
import android.os.IInterface;
import androidx.test.internal.events.client.TestEventServiceConnectionBase;
import androidx.test.services.events.discovery.ITestDiscoveryEvent;

/* compiled from: TestDiscoveryEventServiceConnection */
final /* synthetic */ class TestDiscoveryEventServiceConnection$$Lambda$0 implements TestEventServiceConnectionBase.ServiceFromBinder {
    static final TestEventServiceConnectionBase.ServiceFromBinder $instance = new TestDiscoveryEventServiceConnection$$Lambda$0();

    private TestDiscoveryEventServiceConnection$$Lambda$0() {
    }

    public IInterface asInterface(IBinder iBinder) {
        return ITestDiscoveryEvent.Stub.asInterface(iBinder);
    }
}
