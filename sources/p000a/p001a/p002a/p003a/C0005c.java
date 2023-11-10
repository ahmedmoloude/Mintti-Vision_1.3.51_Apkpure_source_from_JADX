package p000a.p001a.p002a.p003a;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import com.mintti.visionsdk.ble.BleDevice;
import com.mintti.visionsdk.ble.callback.IBleScanCallback;

/* renamed from: a.a.a.a.c */
public class C0005c extends ScanCallback {

    /* renamed from: d */
    public static final /* synthetic */ int f21d = 0;

    /* renamed from: a */
    public boolean f22a = false;

    /* renamed from: b */
    public IBleScanCallback f23b;

    /* renamed from: c */
    public BluetoothLeScanner f24c;

    /* renamed from: a.a.a.a.c$a */
    public static class C0006a {

        /* renamed from: a */
        public static final C0005c f25a = new C0005c();
    }

    public void onScanFailed(int i) {
        this.f22a = false;
        IBleScanCallback iBleScanCallback = this.f23b;
        if (iBleScanCallback != null) {
            iBleScanCallback.onScanFailed(i);
        }
    }

    public void onScanResult(int i, ScanResult scanResult) {
        BluetoothDevice device;
        if (scanResult != null && (device = scanResult.getDevice()) != null) {
            BleDevice bleDevice = new BleDevice(device, scanResult.getRssi(), scanResult.getScanRecord().getBytes());
            IBleScanCallback iBleScanCallback = this.f23b;
            if (iBleScanCallback != null) {
                iBleScanCallback.onScanResult(bleDevice);
            }
        }
    }
}
