package androidx.test.internal.events.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;
import androidx.test.internal.util.Checks;

public class TestEventServiceConnectionBase<T extends IInterface> implements TestEventServiceConnection {
    private static final String TAG = "ConnectionBase";
    private final ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TestEventServiceConnectionBase testEventServiceConnectionBase = TestEventServiceConnectionBase.this;
            testEventServiceConnectionBase.service = testEventServiceConnectionBase.serviceFromBinder.asInterface(iBinder);
            String valueOf = String.valueOf(TestEventServiceConnectionBase.this.serviceName);
            Log.d(TestEventServiceConnectionBase.TAG, valueOf.length() != 0 ? "Connected to ".concat(valueOf) : new String("Connected to "));
            TestEventServiceConnectionBase.this.listener.onTestEventClientConnect();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            TestEventServiceConnectionBase.this.service = null;
            String valueOf = String.valueOf(TestEventServiceConnectionBase.this.serviceName);
            Log.d(TestEventServiceConnectionBase.TAG, valueOf.length() != 0 ? "Disconnected from ".concat(valueOf) : new String("Disconnected from "));
        }
    };
    /* access modifiers changed from: private */
    public final TestEventClientConnectListener listener;
    public T service = null;
    /* access modifiers changed from: private */
    public final ServiceFromBinder<T> serviceFromBinder;
    /* access modifiers changed from: private */
    public final String serviceName;
    private final String servicePackageName;

    public interface ServiceFromBinder<T extends IInterface> {
        T asInterface(IBinder iBinder);
    }

    public TestEventServiceConnectionBase(String str, ServiceFromBinder<T> serviceFromBinder2, TestEventClientConnectListener testEventClientConnectListener) {
        this.serviceName = (String) Checks.checkNotNull(getServiceNameOnly(str), "serviceName cannot be null");
        this.servicePackageName = (String) Checks.checkNotNull(getServicePackage(str), "servicePackageName cannot be null");
        this.listener = (TestEventClientConnectListener) Checks.checkNotNull(testEventClientConnectListener, "listener cannot be null");
        this.serviceFromBinder = (ServiceFromBinder) Checks.checkNotNull(serviceFromBinder2, "serviceFromBinder cannot be null");
    }

    public void connect(Context context) {
        Intent intent = new Intent(this.serviceName);
        intent.setPackage(this.servicePackageName);
        if (!context.bindService(intent, this.connection, 1)) {
            String valueOf = String.valueOf(this.serviceName);
            throw new IllegalStateException(valueOf.length() != 0 ? "Cannot connect to ".concat(valueOf) : new String("Cannot connect to "));
        }
    }

    static String getServiceNameOnly(String str) {
        String[] split = str.split("/");
        if (split.length == 2) {
            if (!split[1].startsWith(".")) {
                return split[1];
            }
            String valueOf = String.valueOf(split[0]);
            String valueOf2 = String.valueOf(split[1]);
            return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        } else if (split.length == 1) {
            return split[0];
        } else {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 22);
            sb.append("Invalid serviceName [");
            sb.append(str);
            sb.append("]");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static String getServicePackage(String str) {
        String[] split = str.split("/");
        if (split.length >= 2) {
            return split[0];
        }
        return null;
    }
}
