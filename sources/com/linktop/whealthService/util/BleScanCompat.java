package com.linktop.whealthService.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import com.linktop.constant.UUIDConfig;
import com.linktop.utils.BleDevLog;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class BleScanCompat {

    /* renamed from: i */
    private static final String f1219i = "BleScanCompat";

    /* renamed from: a */
    private final BluetoothAdapter f1220a;

    /* renamed from: b */
    private final boolean f1221b;

    /* renamed from: c */
    private List<ScanFilter> f1222c;

    /* renamed from: d */
    private ScanSettings f1223d;

    /* renamed from: e */
    private UUID[] f1224e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public BleScanCallback f1225f;

    /* renamed from: g */
    private BluetoothAdapter.LeScanCallback f1226g;

    /* renamed from: h */
    private ScanCallback f1227h;

    public interface BleScanCallback {
        /* renamed from: a */
        void mo27440a(int i);

        /* renamed from: a */
        void mo27443a(BluetoothDevice bluetoothDevice, int i, byte[] bArr);
    }

    public BleScanCompat(BluetoothAdapter bluetoothAdapter) {
        this.f1221b = Build.VERSION.SDK_INT >= 21;
        this.f1220a = bluetoothAdapter;
    }

    /* renamed from: a */
    private void m351a() {
        this.f1226g = new BluetoothAdapter.LeScanCallback() {
            public final void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                BleScanCompat.this.m352a(bluetoothDevice, i, bArr);
            }
        };
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public /* synthetic */ void m352a(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        BleScanCallback bleScanCallback = this.f1225f;
        if (bleScanCallback != null) {
            bleScanCallback.mo27443a(bluetoothDevice, i, bArr);
        }
    }

    /* renamed from: b */
    private void m353b() {
        this.f1227h = new ScanCallback() {
            public void onScanFailed(int i) {
                if (BleScanCompat.this.f1225f != null) {
                    BleScanCompat.this.f1225f.mo27440a(i);
                }
            }

            public void onScanResult(int i, ScanResult scanResult) {
                BluetoothDevice device = scanResult.getDevice();
                int rssi = scanResult.getRssi();
                byte[] bytes = scanResult.getScanRecord() != null ? scanResult.getScanRecord().getBytes() : null;
                if (BleScanCompat.this.f1225f != null) {
                    BleScanCompat.this.f1225f.mo27443a(device, rssi, bytes);
                }
            }
        };
    }

    /* renamed from: d */
    private void m354d() {
        String str;
        String str2;
        BluetoothAdapter bluetoothAdapter = this.f1220a;
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            if (this.f1220a == null) {
                str2 = f1219i;
                str = "startScanNew(), param *mBluetoothAdapter* is null.";
            } else {
                str2 = f1219i;
                str = "startScanNew(), param *mBluetoothAdapter.isEnable()* = " + this.f1220a.isEnabled() + ".";
            }
            BleDevLog.m143d(str2, str);
            return;
        }
        BleDevLog.m143d(f1219i, "startScanNew(), done.");
        this.f1220a.getBluetoothLeScanner().startScan(this.f1222c, this.f1223d, this.f1227h);
    }

    /* renamed from: e */
    private void m355e() {
        String str;
        String str2;
        BluetoothAdapter bluetoothAdapter = this.f1220a;
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            if (this.f1220a == null) {
                str2 = f1219i;
                str = "startScanOld(), param *mBluetoothAdapter* is null";
            } else {
                str2 = f1219i;
                str = "startScanOld(), param *mBluetoothAdapter.isEnable()* = " + this.f1220a.isEnabled();
            }
            BleDevLog.m143d(str2, str);
            return;
        }
        BleDevLog.m143d(f1219i, "startScanOld(), done.");
        UUID[] uuidArr = this.f1224e;
        if (uuidArr != null) {
            this.f1220a.startLeScan(uuidArr, this.f1226g);
        } else {
            this.f1220a.startLeScan(this.f1226g);
        }
    }

    /* renamed from: g */
    private void m356g() {
        String str;
        String str2;
        BluetoothAdapter bluetoothAdapter = this.f1220a;
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            if (this.f1220a == null) {
                str2 = f1219i;
                str = "stopScanNew(), param *mBluetoothAdapter* is null.";
            } else {
                str2 = f1219i;
                str = "stopScanNew(), param *mBluetoothAdapter.isEnable()* = " + this.f1220a.isEnabled() + ".";
            }
            BleDevLog.m143d(str2, str);
            return;
        }
        BleDevLog.m143d(f1219i, "stopScanNew(), done.");
        this.f1220a.getBluetoothLeScanner().stopScan(this.f1227h);
    }

    /* renamed from: h */
    private void m357h() {
        String str;
        String str2;
        BluetoothAdapter bluetoothAdapter = this.f1220a;
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            if (this.f1220a == null) {
                str2 = f1219i;
                str = "stopScanOld(), param *mBluetoothAdapter* is null";
            } else {
                str2 = f1219i;
                str = "stopScanOld(), param *mBluetoothAdapter.isEnable()* = " + this.f1220a.isEnabled();
            }
            BleDevLog.m143d(str2, str);
            return;
        }
        BleDevLog.m143d(f1219i, "stopScanOld(), done.");
        this.f1220a.stopLeScan(this.f1226g);
    }

    /* renamed from: a */
    public void mo27581a(BleScanCallback bleScanCallback) {
        this.f1225f = bleScanCallback;
    }

    /* renamed from: a */
    public void mo27582a(String... strArr) {
        int i = 0;
        if (this.f1221b) {
            this.f1222c = new ArrayList();
            int length = strArr.length;
            while (i < length) {
                this.f1222c.add(new ScanFilter.Builder().setServiceUuid(UUIDConfig.getParcelUuid(strArr[i])).build());
                i++;
            }
            this.f1223d = new ScanSettings.Builder().setScanMode(2).build();
            m353b();
            return;
        }
        this.f1224e = new UUID[strArr.length];
        while (i < strArr.length) {
            this.f1224e[i] = UUID.fromString(strArr[i]);
            i++;
        }
        m351a();
    }

    /* renamed from: c */
    public void mo27583c() {
        if (this.f1221b) {
            m354d();
        } else {
            m355e();
        }
    }

    /* renamed from: f */
    public void mo27584f() {
        if (this.f1221b) {
            m356g();
        } else {
            m357h();
        }
    }
}
