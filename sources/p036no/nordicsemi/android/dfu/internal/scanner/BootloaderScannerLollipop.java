package p036no.nordicsemi.android.dfu.internal.scanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* renamed from: no.nordicsemi.android.dfu.internal.scanner.BootloaderScannerLollipop */
public class BootloaderScannerLollipop extends ScanCallback implements BootloaderScanner {
    /* access modifiers changed from: private */
    public String mBootloaderAddress;
    private String mDeviceAddress;
    private String mDeviceAddressIncremented;
    /* access modifiers changed from: private */
    public boolean mFound;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();

    public String searchFor(String str) {
        BluetoothLeScanner bluetoothLeScanner;
        String substring = str.substring(0, 15);
        String substring2 = str.substring(15);
        String format = String.format(Locale.US, "%02X", new Object[]{Integer.valueOf((Integer.valueOf(substring2, 16).intValue() + 1) & 255)});
        this.mDeviceAddress = str;
        this.mDeviceAddressIncremented = substring + format;
        this.mBootloaderAddress = null;
        this.mFound = false;
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(BootloaderScanner.TIMEOUT);
                } catch (InterruptedException unused) {
                }
                if (!BootloaderScannerLollipop.this.mFound) {
                    String unused2 = BootloaderScannerLollipop.this.mBootloaderAddress = null;
                    boolean unused3 = BootloaderScannerLollipop.this.mFound = true;
                    synchronized (BootloaderScannerLollipop.this.mLock) {
                        BootloaderScannerLollipop.this.mLock.notifyAll();
                    }
                }
            }
        }, "Scanner timer").start();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || defaultAdapter.getState() != 12 || (bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner()) == null) {
            return null;
        }
        ScanSettings build = new ScanSettings.Builder().setScanMode(2).build();
        if (!defaultAdapter.isOffloadedFilteringSupported() || Build.VERSION.SDK_INT < 27) {
            bluetoothLeScanner.startScan((List) null, build, this);
        } else {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new ScanFilter.Builder().setDeviceAddress(this.mDeviceAddress).build());
            arrayList.add(new ScanFilter.Builder().setDeviceAddress(this.mDeviceAddressIncremented).build());
            bluetoothLeScanner.startScan(arrayList, build, this);
        }
        try {
            synchronized (this.mLock) {
                while (!this.mFound) {
                    this.mLock.wait();
                }
            }
        } catch (InterruptedException unused) {
        }
        bluetoothLeScanner.stopScan(this);
        return this.mBootloaderAddress;
    }

    public void onScanResult(int i, ScanResult scanResult) {
        String address = scanResult.getDevice().getAddress();
        if (this.mDeviceAddress.equals(address) || this.mDeviceAddressIncremented.equals(address)) {
            this.mBootloaderAddress = address;
            this.mFound = true;
            synchronized (this.mLock) {
                this.mLock.notifyAll();
            }
        }
    }
}
