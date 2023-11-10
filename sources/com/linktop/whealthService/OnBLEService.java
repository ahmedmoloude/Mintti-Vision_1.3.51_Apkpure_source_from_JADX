package com.linktop.whealthService;

import android.app.Notification;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.linktop.DeviceType;
import com.linktop.constant.IUserProfile;
import com.linktop.constant.UUIDConfig;
import com.linktop.infs.OnBleConnectListener;
import com.linktop.infs.OnDeviceInfoListener;
import com.linktop.infs.OnDeviceVersionListener;
import com.linktop.infs.OnScanTempListener;
import com.linktop.infs.OnSendCodeToDevCallback;
import com.linktop.receiver.BleEnableStateReceiver;
import com.linktop.utils.AssetsDatabaseManager;
import com.linktop.utils.BleDevLog;
import com.linktop.whealthService.task.AckTask;
import com.linktop.whealthService.task.BatteryTask;
import com.linktop.whealthService.task.BpTask;
import com.linktop.whealthService.task.BtTask;
import com.linktop.whealthService.task.DeviceTask;
import com.linktop.whealthService.task.EcgTask;
import com.linktop.whealthService.task.OxTask;
import com.linktop.whealthService.task.SysErrorTask;
import com.linktop.whealthService.task.TestPaperTask;
import com.linktop.whealthService.task.ThermometerTask;
import com.linktop.whealthService.util.BleScanCompat;
import com.linktop.whealthService.util.Communicate;
import com.linktop.whealthService.util.IBleDev;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Vector;
import kotlin.UByte;

public final class OnBLEService extends Service implements IBleDev, BleScanCompat.BleScanCallback {
    /* access modifiers changed from: private */

    /* renamed from: O */
    public static final String f986O = "OnBLEService";

    /* renamed from: P */
    public static long f987P = 2000;

    /* renamed from: A */
    private OxTask f988A;

    /* renamed from: B */
    public ThermometerTask f989B;
    /* access modifiers changed from: private */

    /* renamed from: C */
    public OnDeviceVersionListener f990C;
    /* access modifiers changed from: private */

    /* renamed from: D */
    public String f991D = DeviceType.HealthMonitor;

    /* renamed from: E */
    private BleScanCompat f992E;
    /* access modifiers changed from: private */

    /* renamed from: F */
    public boolean f993F;

    /* renamed from: G */
    private boolean f994G;
    /* access modifiers changed from: private */

    /* renamed from: H */
    public int f995H = 0;
    /* access modifiers changed from: private */

    /* renamed from: I */
    public Timer f996I;

    /* renamed from: J */
    private boolean f997J;

    /* renamed from: K */
    private boolean f998K;

    /* renamed from: L */
    private IUserProfile f999L;

    /* renamed from: M */
    private OnSDKThrowableCallback f1000M;

    /* renamed from: N */
    private String f1001N = "90:E2:02:D9:15:7D,6C:79:B8:DF:B9:ED";

    /* renamed from: a */
    private IBinder f1002a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public OnBleConnectListener f1003b;

    /* renamed from: c */
    private BleEnableStateReceiver f1004c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public BluetoothAdapter f1005d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public BluetoothGatt f1006e;

    /* renamed from: f */
    private BluetoothGattCallback f1007f;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public BluetoothGattCharacteristic f1008g;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public BluetoothGattCharacteristic f1009h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public int f1010i = 100;

    /* renamed from: j */
    public boolean f1011j;

    /* renamed from: k */
    private Thread f1012k;

    /* renamed from: l */
    private BleDevComparator f1013l;

    /* renamed from: m */
    private Timer f1014m;

    /* renamed from: n */
    private Timer f1015n;
    /* access modifiers changed from: private */

    /* renamed from: o */
    public Timer f1016o;

    /* renamed from: p */
    public final ArrayList<DeviceSort> f1017p = new ArrayList<>();
    /* access modifiers changed from: private */

    /* renamed from: q */
    public final Vector<byte[]> f1018q = new Vector<>();

    /* renamed from: r */
    private AckTask f1019r;
    /* access modifiers changed from: private */

    /* renamed from: s */
    public BatteryTask f1020s;

    /* renamed from: t */
    private DeviceTask f1021t;

    /* renamed from: u */
    private SysErrorTask f1022u;
    /* access modifiers changed from: private */

    /* renamed from: v */
    public Communicate f1023v;

    /* renamed from: w */
    private BpTask f1024w;
    /* access modifiers changed from: private */

    /* renamed from: x */
    public TestPaperTask f1025x;

    /* renamed from: y */
    private BtTask f1026y;
    /* access modifiers changed from: private */

    /* renamed from: z */
    public EcgTask f1027z;

    public static class BleDevComparator implements Comparator<DeviceSort> {
        private BleDevComparator() {
        }

        /* renamed from: a */
        public int compare(DeviceSort deviceSort, DeviceSort deviceSort2) {
            return Integer.compare(deviceSort2.rssi, deviceSort.rssi);
        }
    }

    public class CmdSendRunnable implements Runnable {

        /* renamed from: a */
        private final String f1040a;

        /* renamed from: b */
        private final byte[] f1041b;

        public CmdSendRunnable(byte[] bArr, String str) {
            this.f1041b = bArr;
            this.f1040a = str;
        }

        /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r8 = this;
                byte[] r0 = r8.f1041b
                if (r0 == 0) goto L_0x00e3
                int r0 = r0.length
                if (r0 != 0) goto L_0x0009
                goto L_0x00e3
            L_0x0009:
                r0 = 0
                r1 = 0
            L_0x000b:
                r2 = 1
                if (r1 >= r2) goto L_0x00d3
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                byte[] r4 = r8.f1041b
                int r5 = r4.length
                r6 = 0
            L_0x0017:
                if (r6 >= r5) goto L_0x002c
                byte r7 = r4[r6]
                r7 = r7 & 255(0xff, float:3.57E-43)
                java.lang.String r7 = java.lang.Integer.toHexString(r7)
                r3.append(r7)
                java.lang.String r7 = "  "
                r3.append(r7)
                int r6 = r6 + 1
                goto L_0x0017
            L_0x002c:
                java.lang.String r4 = com.linktop.whealthService.OnBLEService.f986O
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "CMD SEND from:"
                r5.append(r6)
                java.lang.String r6 = r8.f1040a
                r5.append(r6)
                java.lang.String r6 = ", bytes:   "
                r5.append(r6)
                r5.append(r3)
                java.lang.String r3 = r5.toString()
                com.linktop.utils.BleDevLog.m142c(r4, r3)
                com.linktop.whealthService.OnBLEService r3 = com.linktop.whealthService.OnBLEService.this
                android.bluetooth.BluetoothGattCharacteristic r3 = r3.f1009h
                if (r3 == 0) goto L_0x0091
                com.linktop.whealthService.OnBLEService r3 = com.linktop.whealthService.OnBLEService.this
                android.bluetooth.BluetoothGatt r3 = r3.f1006e
                if (r3 == 0) goto L_0x0091
                android.bluetooth.BluetoothAdapter r3 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
                boolean r3 = r3.isEnabled()
                if (r3 == 0) goto L_0x0091
                com.linktop.whealthService.OnBLEService r3 = com.linktop.whealthService.OnBLEService.this     // Catch:{  }
                android.bluetooth.BluetoothGattCharacteristic r3 = r3.f1009h     // Catch:{  }
                r3.setWriteType(r2)     // Catch:{  }
                com.linktop.whealthService.OnBLEService r2 = com.linktop.whealthService.OnBLEService.this     // Catch:{  }
                android.bluetooth.BluetoothGattCharacteristic r2 = r2.f1009h     // Catch:{  }
                byte[] r3 = r8.f1041b     // Catch:{  }
                r2.setValue(r3)     // Catch:{  }
                com.linktop.whealthService.OnBLEService r2 = com.linktop.whealthService.OnBLEService.this     // Catch:{  }
                android.bluetooth.BluetoothGatt r2 = r2.f1006e     // Catch:{  }
                com.linktop.whealthService.OnBLEService r3 = com.linktop.whealthService.OnBLEService.this     // Catch:{  }
                android.bluetooth.BluetoothGattCharacteristic r3 = r3.f1009h     // Catch:{  }
                r2.writeCharacteristic(r3)     // Catch:{  }
                r2 = 1000(0x3e8, double:4.94E-321)
                java.lang.Thread.sleep(r2)     // Catch:{ Exception -> 0x00cf }
                goto L_0x00cf
            L_0x0091:
                java.lang.String r2 = com.linktop.whealthService.OnBLEService.f986O
                java.lang.String r3 = "send error"
                com.linktop.utils.BleDevLog.m141b(r2, r3)
                com.linktop.whealthService.OnBLEService r2 = com.linktop.whealthService.OnBLEService.this
                android.bluetooth.BluetoothGattCharacteristic r2 = r2.f1009h
                if (r2 != 0) goto L_0x00ab
                java.lang.String r2 = com.linktop.whealthService.OnBLEService.f986O
                java.lang.String r3 = "mHRMChara==null"
                com.linktop.utils.BleDevLog.m141b(r2, r3)
            L_0x00ab:
                com.linktop.whealthService.OnBLEService r2 = com.linktop.whealthService.OnBLEService.this
                android.bluetooth.BluetoothGatt r2 = r2.f1006e
                if (r2 != 0) goto L_0x00bc
                java.lang.String r2 = com.linktop.whealthService.OnBLEService.f986O
                java.lang.String r3 = "mBluetoothGatt==null"
                com.linktop.utils.BleDevLog.m141b(r2, r3)
            L_0x00bc:
                android.bluetooth.BluetoothAdapter r2 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
                boolean r2 = r2.isEnabled()
                if (r2 != 0) goto L_0x00cf
                java.lang.String r2 = com.linktop.whealthService.OnBLEService.f986O
                java.lang.String r3 = "BluetoothAdapter not enable"
                com.linktop.utils.BleDevLog.m141b(r2, r3)
            L_0x00cf:
                int r1 = r1 + 1
                goto L_0x000b
            L_0x00d3:
                if (r1 != r2) goto L_0x00e2
                com.linktop.whealthService.OnBLEService r0 = com.linktop.whealthService.OnBLEService.this
                byte[] r1 = r8.f1041b
                r2 = 4
                byte r2 = r1[r2]
                r3 = 6
                byte r1 = r1[r3]
                r0.clearCmdToSend(r2, r1)
            L_0x00e2:
                return
            L_0x00e3:
                java.lang.String r0 = com.linktop.whealthService.OnBLEService.f986O
                java.lang.String r1 = "CmdSendRunnable but cmd is null"
                com.linktop.utils.BleDevLog.m141b(r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.linktop.whealthService.OnBLEService.CmdSendRunnable.run():void");
        }
    }

    public static class DeviceSort {
        public BluetoothDevice bleDevice;
        public int rssi;

        public DeviceSort(BluetoothDevice bluetoothDevice, int i) {
            this.bleDevice = bluetoothDevice;
            this.rssi = i;
        }

        public String toString() {
            return "DeviceSort{bleDevice=" + this.bleDevice.toString() + ", rssi=" + this.rssi + '}';
        }
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        /* renamed from: a */
        public OnBLEService mo27480a() {
            return OnBLEService.this;
        }
    }

    public static class OnBLEInnerService extends Service {
        public IBinder onBind(Intent intent) {
            return null;
        }

        public void onCreate() {
            super.onCreate();
        }

        public int onStartCommand(Intent intent, int i, int i2) {
            startForeground(1001, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, i, i2);
        }
    }

    public interface OnSDKThrowableCallback {
        void onSDKThrowable(Throwable th);
    }

    /* renamed from: a */
    private void m151a(BluetoothDevice bluetoothDevice, int i) {
        boolean z;
        Iterator<DeviceSort> it = this.f1017p.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            DeviceSort next = it.next();
            if (next.bleDevice.getAddress().equals(bluetoothDevice.getAddress())) {
                z = true;
                next.rssi = i;
                break;
            }
        }
        if (!z) {
            this.f1017p.add(new DeviceSort(bluetoothDevice, i));
            Collections.sort(this.f1017p, m174k());
            BleDevLog.m141b("addDevice", bluetoothDevice.getName() + "**" + bluetoothDevice.getAddress());
            OnBleConnectListener onBleConnectListener = this.f1003b;
            if (onBleConnectListener != null) {
                onBleConnectListener.onUpdateDialogBleList();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m152a(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        int properties = bluetoothGattCharacteristic.getProperties();
        mo27450a(bluetoothGattCharacteristic, false);
        this.f1009h = null;
        if ((properties & 2) > 0) {
            mo27450a(bluetoothGattCharacteristic, false);
            this.f1009h = null;
            this.f1006e.readCharacteristic(bluetoothGattCharacteristic);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m155a(UUID uuid, UUID uuid2, byte[] bArr) {
        String str;
        String str2;
        BluetoothGatt bluetoothGatt;
        if (this.f1005d == null || (bluetoothGatt = this.f1006e) == null) {
            str = f986O;
            str2 = "BluetoothAdapter not initialized";
        } else {
            BluetoothGattService service = bluetoothGatt.getService(uuid);
            if (service == null) {
                str = f986O;
                str2 = "mGattService service not found!";
            } else {
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(uuid2);
                if (characteristic != null && bArr != null) {
                    characteristic.setWriteType(1);
                    characteristic.setValue(bArr);
                    this.f1006e.writeCharacteristic(characteristic);
                    return;
                }
                return;
            }
        }
        BleDevLog.m141b(str, str2);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static String m158b(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bArr.length - 1; i++) {
            sb.append(String.valueOf((char) bArr[i]));
        }
        return sb.toString();
    }

    /* renamed from: b */
    private void m160b(final int i) {
        Timer timer = new Timer();
        this.f996I = timer;
        timer.schedule(new TimerTask() {
            public void run() {
                if (i == 0) {
                    if (OnBLEService.this.f1027z != null) {
                        OnBLEService.this.f1027z.checkModuleExist((OnSendCodeToDevCallback) null);
                    }
                } else if (OnBLEService.this.f1025x != null) {
                    OnBLEService.this.f1025x.checkModuleExist((OnSendCodeToDevCallback) null);
                }
            }
        }, 200);
    }

    /* renamed from: c */
    private void m161c() {
        BluetoothGatt bluetoothGatt = this.f1006e;
        if (bluetoothGatt != null) {
            bluetoothGatt.close();
            this.f1006e = null;
        }
    }

    /* renamed from: c */
    private void m162c(final int i) {
        if (this.f1006e != null) {
            Timer timer = new Timer();
            this.f996I = timer;
            timer.schedule(new TimerTask() {
                public void run() {
                    String a;
                    StringBuilder sb;
                    String sb2;
                    if (OnBLEService.this.f1006e != null) {
                        BluetoothGattService service = OnBLEService.this.f1006e.getService(UUIDConfig.getUUID("0000180A-0000-1000-8000-00805f9b34fb"));
                        if (service == null) {
                            a = OnBLEService.f986O;
                            sb2 = "deviceInfoService service not found!";
                        } else {
                            int i = i;
                            BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUIDConfig.getUUID(i != 0 ? i != 1 ? i != 2 ? "" : UUIDConfig.DEV_INFO_SOFTWARE_REV_UUID : UUIDConfig.DEV_INFO_HARDWARE_PCB_UUID : UUIDConfig.DEV_INFO_FIRMWARE_REV_UUID));
                            if (characteristic == null) {
                                a = OnBLEService.f986O;
                                sb = new StringBuilder();
                                sb.append("wareType ");
                                sb.append(i);
                                sb.append(" Characteristic not found!");
                            } else {
                                OnBLEService.this.f1006e.readCharacteristic(characteristic);
                                a = OnBLEService.f986O;
                                sb = new StringBuilder();
                                sb.append("Read wareType ");
                                sb.append(i);
                            }
                            sb2 = sb.toString();
                        }
                        BleDevLog.m141b(a, sb2);
                    }
                }
            }, 200);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public TimerTask m166e() {
        return new TimerTask() {
            public void run() {
                OnBLEService.this.m155a(UUIDConfig.getUUID(UUIDConfig.THERM_SERVICE), UUIDConfig.getUUID(UUIDConfig.THERM_CONNECT_CONFIRM), ThermometerTask.f1208j);
            }
        };
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public void m169g() {
        boolean z;
        String str;
        String str2;
        BluetoothGatt bluetoothGatt = this.f1006e;
        if (bluetoothGatt != null) {
            BluetoothGattService service = bluetoothGatt.getService(UUIDConfig.getUUID(UUIDConfig.HRP_SERVICE));
            if (service == null) {
                str = f986O;
                str2 = "===HRP service not found!";
            } else {
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUIDConfig.getUUID(UUIDConfig.HEART_RATE_WRITE_CHARA));
                this.f1009h = characteristic;
                if (characteristic == null) {
                    str = f986O;
                    str2 = "=======characteristic1 not found!";
                } else {
                    BluetoothGattCharacteristic characteristic2 = service.getCharacteristic(UUIDConfig.getUUID(UUIDConfig.HEART_RATE_MEASUREMENT_CHARA));
                    this.f1008g = characteristic2;
                    if (characteristic2 != null) {
                        z = mo27450a(characteristic2, true);
                        mo27453d(z ? 104 : 105);
                        String str3 = f986O;
                        BleDevLog.m141b(str3, "setCharacteristicNotification Enabled?" + z);
                    } else {
                        BleDevLog.m141b(f986O, "===========characteristic4 not found!");
                        z = false;
                    }
                    if (z) {
                        mo27452d();
                        return;
                    } else {
                        BleDevLog.m141b(f986O, "readDescriptor() is failed");
                        return;
                    }
                }
            }
            BleDevLog.m141b(str, str2);
        }
    }

    /* renamed from: j */
    private BluetoothGattCallback m172j() {
        if (this.f1007f == null) {
            this.f1007f = new BluetoothGattCallback() {
                public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                    super.onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic);
                    byte[] value = bluetoothGattCharacteristic.getValue();
                    if (BleDevLog.f976a) {
                        for (byte b : value) {
                            Integer.toHexString(b & UByte.MAX_VALUE);
                        }
                    }
                    OnBLEService.this.f1023v.packageParse(value);
                }

                public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
                    String a;
                    OnDeviceVersionListener e;
                    int i2;
                    super.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
                    if (i == 0) {
                        UUID uuid = bluetoothGattCharacteristic.getUuid();
                        if (UUIDConfig.getUUID(UUIDConfig.DEV_INFO_SOFTWARE_REV_UUID).equals(uuid)) {
                            a = OnBLEService.m158b(bluetoothGattCharacteristic.getValue());
                            String a2 = OnBLEService.f986O;
                            BleDevLog.m143d(a2, "SoftwareVer:" + a);
                            if (OnBLEService.this.f990C != null) {
                                e = OnBLEService.this.f990C;
                                i2 = 2;
                            }
                            OnBLEService.this.mo27452d();
                            return;
                        } else if (UUIDConfig.getUUID(UUIDConfig.DEV_INFO_HARDWARE_PCB_UUID).equals(uuid)) {
                            a = OnBLEService.m158b(bluetoothGattCharacteristic.getValue());
                            String a3 = OnBLEService.f986O;
                            BleDevLog.m143d(a3, "HardwareVer:" + a);
                            if (OnBLEService.this.f990C != null) {
                                e = OnBLEService.this.f990C;
                                i2 = 1;
                            }
                            OnBLEService.this.mo27452d();
                            return;
                        } else if (UUIDConfig.getUUID(UUIDConfig.DEV_INFO_FIRMWARE_REV_UUID).equals(uuid)) {
                            a = OnBLEService.m158b(bluetoothGattCharacteristic.getValue());
                            String a4 = OnBLEService.f986O;
                            BleDevLog.m143d(a4, "FirmwareVer:" + a);
                            if (OnBLEService.this.f990C != null) {
                                e = OnBLEService.this.f990C;
                                i2 = 0;
                            }
                            OnBLEService.this.mo27452d();
                            return;
                        } else if (UUIDConfig.getUUID(UUIDConfig.THERM_CONNECT_CONFIRM).equals(uuid)) {
                            BleDevLog.m141b("~~~~~~~~~~~~~~~", "温度计连接确认完毕");
                            return;
                        } else if (UUIDConfig.getUUID(UUIDConfig.THERM_QR_CODE).equals(uuid)) {
                            String str = new String(bluetoothGattCharacteristic.getValue());
                            BleDevLog.m141b("~~~~~~~~~~~~~~~", "获取到二维码， qrCode：" + str);
                            ThermometerTask thermometerTask = OnBLEService.this.f989B;
                            if (thermometerTask != null) {
                                thermometerTask.mo27577a(str);
                                return;
                            }
                            return;
                        } else {
                            return;
                        }
                        e.onDeviceVersion(i2, a);
                        OnBLEService.this.mo27452d();
                        return;
                    }
                    BleDevLog.m143d("~~~~~~~~~~~~~~~", " status : " + i);
                }

                public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
                    int i3;
                    OnBLEService onBLEService;
                    BluetoothDevice device = bluetoothGatt.getDevice();
                    BleDevLog.m141b("getBluetoothGattCallback", "onConnectionStateChange - status:" + i + ",newStatus:" + i2);
                    if (i2 == 0) {
                        if (OnBLEService.this.f1008g != null) {
                            OnBLEService onBLEService2 = OnBLEService.this;
                            onBLEService2.m152a(onBLEService2.f1008g);
                        }
                        OnBLEService.this.f1018q.clear();
                        bluetoothGatt.close();
                        BluetoothGatt unused = OnBLEService.this.f1006e = null;
                        if (OnBLEService.this.f1005d.isEnabled()) {
                            onBLEService = OnBLEService.this;
                            i3 = 101;
                        } else {
                            onBLEService = OnBLEService.this;
                            i3 = 100;
                        }
                        onBLEService.mo27453d(i3);
                        if (OnBLEService.this.f1016o != null) {
                            OnBLEService.this.f1016o.cancel();
                            Timer unused2 = OnBLEService.this.f1016o = null;
                        }
                        int unused3 = OnBLEService.this.f995H = 0;
                        OnBLEService.this.f1017p.clear();
                    } else if (i2 == 2) {
                        OnBLEService.this.mo27453d(103);
                        try {
                            Thread.sleep(200);
                            boolean discoverServices = bluetoothGatt.discoverServices();
                            BleDevLog.m141b("Now Ble connect to device", "name:" + device.getName() + ", address:" + device.getAddress() + ", discoverServices ? " + discoverServices);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
                    super.onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i);
                }

                public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
                    Timer s;
                    TimerTask d;
                    long j;
                    BleDevLog.m141b("getBluetoothGattCallback", "onServicesDiscovered - status " + i);
                    if (i == 0) {
                        BleDevLog.m141b("*************onServicesDiscovered*************", "GATT_SUCCESS");
                        Timer unused = OnBLEService.this.f996I = new Timer();
                        if (DeviceType.HealthMonitor.equals(OnBLEService.this.f991D)) {
                            s = OnBLEService.this.f996I;
                            d = new TimerTask() {
                                public void run() {
                                    OnBLEService.this.m169g();
                                }
                            };
                            j = 400;
                        } else if (DeviceType.Thermometer.equals(OnBLEService.this.f991D)) {
                            s = OnBLEService.this.f996I;
                            d = OnBLEService.this.m166e();
                            j = 200;
                        } else {
                            return;
                        }
                        s.schedule(d, j);
                        return;
                    }
                    BleDevLog.m141b("*************onServicesDiscovered*************", "default:" + i);
                }
            };
        }
        return this.f1007f;
    }

    /* renamed from: k */
    private BleDevComparator m174k() {
        if (this.f1013l == null) {
            this.f1013l = new BleDevComparator();
        }
        return this.f1013l;
    }

    /* access modifiers changed from: private */
    /* renamed from: n */
    public void m179n() {
        String str;
        String str2;
        BluetoothGatt bluetoothGatt = this.f1006e;
        if (bluetoothGatt != null) {
            BluetoothGattService service = bluetoothGatt.getService(UUIDConfig.getUUID("0000180A-0000-1000-8000-00805f9b34fb"));
            if (service == null) {
                str = f986O;
                str2 = "deviceInfoService service not found!";
            } else {
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUIDConfig.getUUID(UUIDConfig.THERM_QR_CODE));
                if (characteristic == null) {
                    str = f986O;
                    str2 = "characteristic is not found!";
                } else {
                    this.f1006e.readCharacteristic(characteristic);
                    str = f986O;
                    str2 = "read QR code";
                }
            }
            BleDevLog.m141b(str, str2);
        }
    }

    /* renamed from: o */
    private void m181o() {
        if (mo27455h() == 104) {
            Timer timer = new Timer();
            this.f1016o = timer;
            timer.schedule(new TimerTask() {
                public void run() {
                    if (OnBLEService.this.f1020s != null) {
                        OnBLEService.this.f1020s.batteryQuery();
                    }
                }
            }, 200, 300000);
        }
    }

    /* renamed from: a */
    public void mo27440a(int i) {
        String str = f986O;
        BleDevLog.m141b(str, "onBleScanFailed - errorCode:" + i);
    }

    /* renamed from: a */
    public void mo27441a(long j) {
        if (j < 2000) {
            j = 2000;
        } else if (j > 30000) {
            j = 30000;
        }
        f987P = j;
    }

    /* renamed from: a */
    public void mo27442a(BluetoothDevice bluetoothDevice) {
        String str;
        String str2;
        if (this.f1006e == null) {
            if (this.f1011j) {
                if (this.f994G) {
                    mo27448a(false);
                } else {
                    mo27449a(false, false);
                }
            }
            if (bluetoothDevice == null) {
                str = f986O;
                str2 = "Device not is Null.  Unable to connect.";
            } else {
                mo27453d(102);
                BluetoothGatt connectGatt = bluetoothDevice.connectGatt(this, false, m172j());
                this.f1006e = connectGatt;
                connectGatt.connect();
                str = f986O;
                str2 = "Trying to create a new connection.";
            }
            BleDevLog.m141b(str, str2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005b  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo27443a(android.bluetooth.BluetoothDevice r5, int r6, byte[] r7) {
        /*
            r4 = this;
            r5.getName()
            java.lang.String r0 = r5.getAddress()
            java.lang.String r1 = f986O
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "devMAC:"
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            com.linktop.utils.BleDevLog.m140a((java.lang.String) r1, (java.lang.String) r0)
            java.lang.String r0 = r4.f991D
            int r1 = r0.hashCode()
            r2 = 2
            r3 = -1493605570(0xffffffffa6f9633e, float:-1.7304745E-15)
            if (r1 == r3) goto L_0x0039
            r3 = 600257990(0x23c735c6, float:2.1598397E-17)
            if (r1 == r3) goto L_0x002f
            goto L_0x0043
        L_0x002f:
            java.lang.String r1 = "Thermometer"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0043
            r0 = 2
            goto L_0x0044
        L_0x0039:
            java.lang.String r1 = "HealthMonitor"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0043
            r0 = 1
            goto L_0x0044
        L_0x0043:
            r0 = -1
        L_0x0044:
            if (r0 == r2) goto L_0x005b
            r4.m151a((android.bluetooth.BluetoothDevice) r5, (int) r6)
            java.util.ArrayList<com.linktop.whealthService.OnBLEService$DeviceSort> r6 = r4.f1017p
            boolean r6 = r6.isEmpty()
            if (r6 != 0) goto L_0x0072
            int r6 = r4.f1010i
            r7 = 102(0x66, float:1.43E-43)
            if (r6 != r7) goto L_0x0072
            r4.mo27442a((android.bluetooth.BluetoothDevice) r5)
            goto L_0x0072
        L_0x005b:
            com.linktop.whealthService.util.ParseByte r0 = new com.linktop.whealthService.util.ParseByte
            r0.<init>(r7)
            java.lang.String r7 = r0.mo27593b()
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x0072
            r4.m151a((android.bluetooth.BluetoothDevice) r5, (int) r6)
            com.linktop.whealthService.task.ThermometerTask r6 = r4.f989B
            r6.mo27576a((com.linktop.whealthService.util.ParseByte) r0, (android.bluetooth.BluetoothDevice) r5)
        L_0x0072:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.linktop.whealthService.OnBLEService.mo27443a(android.bluetooth.BluetoothDevice, int, byte[]):void");
    }

    /* renamed from: a */
    public void mo27444a(OnBleConnectListener onBleConnectListener) {
        this.f1003b = onBleConnectListener;
    }

    /* renamed from: a */
    public void mo27445a(OnDeviceInfoListener onDeviceInfoListener) {
        DeviceTask deviceTask = this.f1021t;
        if (deviceTask != null) {
            deviceTask.setOnDeviceInfoListener(onDeviceInfoListener);
        }
    }

    /* renamed from: a */
    public void mo27446a(OnDeviceVersionListener onDeviceVersionListener) {
        this.f990C = onDeviceVersionListener;
    }

    /* renamed from: a */
    public void mo27447a(String str, OnSDKThrowableCallback onSDKThrowableCallback) {
        this.f991D = str;
        this.f1000M = onSDKThrowableCallback;
        str.hashCode();
        if (str.equals(DeviceType.HealthMonitor)) {
            this.f1019r = new AckTask(this);
            this.f1020s = new BatteryTask(this);
            this.f1022u = new SysErrorTask(this);
            this.f1023v = new Communicate(this);
            this.f1021t = new DeviceTask(this);
            this.f1024w = new BpTask(this);
            this.f1025x = new TestPaperTask(getApplicationContext(), this);
            this.f1026y = new BtTask(this);
            EcgTask ecgTask = new EcgTask(getApplicationContext(), this);
            this.f1027z = ecgTask;
            ecgTask.init(new int[0]);
            this.f988A = new OxTask(getBaseContext(), this);
        } else if (str.equals(DeviceType.Thermometer)) {
            this.f989B = new ThermometerTask(this);
        }
    }

    /* renamed from: a */
    public void mo27448a(boolean z) {
        ThermometerTask thermometerTask;
        if (this.f1011j && this.f1014m != null) {
            mo27449a(false, false);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ble");
        sb.append(z ? " start" : " stop");
        sb.append(" auto Scanning");
        BleDevLog.m141b("class *autoScan*", sb.toString());
        this.f1011j = z;
        this.f994G = z;
        if (z) {
            this.f1017p.clear();
            BleScanCompat bleScanCompat = this.f992E;
            if (bleScanCompat != null) {
                bleScanCompat.mo27583c();
            }
            Timer timer = new Timer();
            this.f1015n = timer;
            timer.schedule(new TimerTask() {
                public void run() {
                    if (OnBLEService.this.f1003b != null) {
                        OnBLEService.this.f1003b.onUpdateDialogBleList();
                    }
                }
            }, 0, f987P);
            return;
        }
        BleScanCompat bleScanCompat2 = this.f992E;
        if (bleScanCompat2 != null) {
            bleScanCompat2.mo27584f();
        }
        Timer timer2 = this.f1015n;
        if (timer2 != null) {
            timer2.cancel();
            this.f1015n = null;
        }
        if (this.f991D.equals(DeviceType.Thermometer) && (thermometerTask = this.f989B) != null) {
            thermometerTask.mo27574a((BluetoothDevice) null, (OnScanTempListener) null);
        }
    }

    /* renamed from: a */
    public void mo27449a(boolean z, boolean z2) {
        this.f1011j = z;
        if (z) {
            mo27453d(102);
            BleScanCompat bleScanCompat = this.f992E;
            if (bleScanCompat != null) {
                bleScanCompat.mo27583c();
            }
            Timer timer = new Timer();
            this.f1014m = timer;
            timer.schedule(new TimerTask() {
                public void run() {
                    OnBLEService.this.mo27449a(false, true);
                    if (!OnBLEService.this.f1017p.isEmpty() && OnBLEService.this.f1010i == 101) {
                        OnBLEService.this.mo27442a(OnBLEService.this.f1017p.get(0).bleDevice);
                    } else if (OnBLEService.this.f1017p.isEmpty()) {
                        BleDevLog.m141b("scan()", "Bluetooth device list is empty!");
                    }
                }
            }, 12000);
        } else {
            BleScanCompat bleScanCompat2 = this.f992E;
            if (bleScanCompat2 != null) {
                bleScanCompat2.mo27584f();
            }
            Timer timer2 = this.f1014m;
            if (timer2 != null) {
                timer2.cancel();
                this.f1014m = null;
            }
            BleDevLog.m141b("scan - mBleState", "" + this.f1010i);
            if (z2 && this.f1010i == 102) {
                mo27453d(101);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ble");
        sb.append(this.f1011j ? " start" : " stop");
        BleDevLog.m141b("class *scan*", sb.toString());
    }

    /* renamed from: a */
    public boolean mo27450a(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z) {
        BluetoothGatt bluetoothGatt;
        BluetoothGattDescriptor descriptor;
        if (this.f1005d == null || (bluetoothGatt = this.f1006e) == null) {
            BleDevLog.m141b(f986O, "BluetoothAdapter not initialized");
            return false;
        } else if (!bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, z) || (descriptor = bluetoothGattCharacteristic.getDescriptor(UUIDConfig.getUUID("00002902-0000-1000-8000-00805f9b34fb"))) == null) {
            return false;
        } else {
            if (!UUIDConfig.HEART_RATE_MEASUREMENT_CHARA.equals(bluetoothGattCharacteristic.getUuid().toString()) || !z) {
                descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            } else {
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            }
            return this.f1006e.writeDescriptor(descriptor);
        }
    }

    /* renamed from: b */
    public void mo27451b() {
        BluetoothAdapter bluetoothAdapter = this.f1005d;
        if (bluetoothAdapter == null || !this.f993F) {
            mo27453d(100);
            OnBleConnectListener onBleConnectListener = this.f1003b;
            if (onBleConnectListener != null) {
                onBleConnectListener.onBLENoSupported();
            }
        } else if (!bluetoothAdapter.isEnabled()) {
            mo27453d(100);
            OnBleConnectListener onBleConnectListener2 = this.f1003b;
            if (onBleConnectListener2 != null) {
                onBleConnectListener2.onOpenBLE();
            }
        } else {
            mo27453d(101);
        }
    }

    public void clearCmdToSend(byte b, byte b2) {
        if (!this.f1018q.isEmpty()) {
            byte[] bArr = this.f1018q.get(0);
            byte b3 = bArr[4];
            byte b4 = bArr[6];
            if (b3 == b && b4 == b2) {
                this.f1018q.remove(0);
                this.f1012k.interrupt();
                if (this.f1018q.size() > 0) {
                    Thread thread = new Thread(new CmdSendRunnable(this.f1018q.get(0), "clearCmdToSend, subMsgType" + b + ", subMsgType:" + b2));
                    this.f1012k = thread;
                    thread.start();
                }
            }
        }
    }

    public synchronized void cmdToSend(byte[] bArr) {
        try {
            this.f1018q.add(bArr);
            if (this.f1018q.size() == 1) {
                Thread thread = new Thread(new CmdSendRunnable(bArr, "cmdToSend"));
                this.f1012k = thread;
                thread.start();
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: d */
    public void mo27452d() {
        int i;
        BleDevLog.m141b("dataQueryStep", "start");
        int i2 = this.f995H;
        switch (i2) {
            case 0:
            case 1:
                m160b(i2);
                break;
            case 2:
                if (this.f997J) {
                    BleDevLog.m142c(f986O, "dataQueryStep(), do strongGain");
                    this.f1027z.setParam();
                    break;
                } else {
                    BleDevLog.m142c(f986O, "dataQueryStep(), skip strongGain");
                    this.f995H++;
                    mo27452d();
                    return;
                }
            case 3:
                DeviceTask deviceTask = this.f1021t;
                if (deviceTask != null) {
                    deviceTask.getDeviceInfo();
                    break;
                } else {
                    this.f995H = i2 + 1;
                    mo27452d();
                    return;
                }
            case 4:
                i = 2;
                break;
            case 5:
                m162c(1);
                break;
            case 6:
                i = 0;
                break;
            case 7:
                m181o();
                break;
        }
        m162c(i);
        BleDevLog.m141b("dataQueryStep", "now step " + this.f995H);
        this.f995H = this.f995H + 1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo27453d(int r4) {
        /*
            r3 = this;
            int r0 = r3.f1010i
            if (r0 != r4) goto L_0x0005
            return
        L_0x0005:
            r3.f1010i = r4
            r0 = 103(0x67, float:1.44E-43)
            r1 = 100
            r2 = 0
            if (r4 != r1) goto L_0x001d
            com.linktop.whealthService.task.EcgTask r4 = r3.f1027z
            if (r4 == 0) goto L_0x0015
            r4.setModuleExist(r2)
        L_0x0015:
            com.linktop.whealthService.task.TestPaperTask r4 = r3.f1025x
            if (r4 == 0) goto L_0x002e
            r4.mo27560a((int) r2)
            goto L_0x002e
        L_0x001d:
            if (r4 >= r0) goto L_0x002e
            com.linktop.whealthService.task.EcgTask r4 = r3.f1027z
            if (r4 == 0) goto L_0x0026
            r4.setModuleExist(r2)
        L_0x0026:
            com.linktop.whealthService.task.TestPaperTask r4 = r3.f1025x
            if (r4 == 0) goto L_0x0034
            r4.mo27560a((int) r2)
            goto L_0x0034
        L_0x002e:
            r3.mo27449a((boolean) r2, (boolean) r2)
            r3.mo27448a((boolean) r2)
        L_0x0034:
            int r4 = r3.f1010i
            if (r4 != r1) goto L_0x003b
            java.lang.String r1 = "Ble closed"
            goto L_0x003d
        L_0x003b:
            java.lang.String r1 = "Unknown"
        L_0x003d:
            r2 = 101(0x65, float:1.42E-43)
            if (r4 != r2) goto L_0x0043
            java.lang.String r1 = "Ble opened and disconnect."
        L_0x0043:
            r2 = 102(0x66, float:1.43E-43)
            if (r4 != r2) goto L_0x0049
            java.lang.String r1 = "Ble connecting device."
        L_0x0049:
            if (r4 != r0) goto L_0x004d
            java.lang.String r1 = "Ble connected device."
        L_0x004d:
            r0 = 104(0x68, float:1.46E-43)
            if (r4 != r0) goto L_0x0053
            java.lang.String r1 = "Ble notification enabled."
        L_0x0053:
            r0 = 105(0x69, float:1.47E-43)
            if (r4 != r0) goto L_0x0059
            java.lang.String r1 = "Ble notification disabled."
        L_0x0059:
            java.lang.String r4 = f986O
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "mBleState >> "
            r0.append(r2)
            int r2 = r3.f1010i
            r0.append(r2)
            java.lang.String r2 = ", "
            r0.append(r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.linktop.utils.BleDevLog.m141b(r4, r0)
            com.linktop.infs.OnBleConnectListener r4 = r3.f1003b
            if (r4 == 0) goto L_0x0082
            int r0 = r3.f1010i
            r4.onBleState(r0)
        L_0x0082:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.linktop.whealthService.OnBLEService.mo27453d(int):void");
    }

    /* renamed from: f */
    public void mo27454f() {
        BluetoothGatt bluetoothGatt = this.f1006e;
        if (bluetoothGatt == null) {
            BleDevLog.m141b(f986O, "mBluetoothGatt not initialized");
        } else {
            try {
                bluetoothGatt.disconnect();
                this.f1006e.close();
            } catch (Exception unused) {
            }
            this.f1006e = null;
        }
        Timer timer = this.f996I;
        if (timer != null) {
            timer.cancel();
            this.f996I = null;
        }
        if (this.f1011j) {
            if (this.f994G) {
                mo27448a(false);
            }
            mo27449a(false, false);
        }
        this.f995H = 0;
        this.f1017p.clear();
        mo27453d(101);
    }

    public AckTask getAckTask() {
        return this.f1019r;
    }

    public BatteryTask getBatteryTask() {
        return this.f1020s;
    }

    public BpTask getBpTask() {
        return this.f1024w;
    }

    public BtTask getBtTask() {
        return this.f1026y;
    }

    public Communicate getCommunicate() {
        return this.f1023v;
    }

    public DeviceTask getDeviceTask() {
        return this.f1021t;
    }

    public EcgTask getEcgTask() {
        return this.f1027z;
    }

    public OxTask getOxTask() {
        return this.f988A;
    }

    public SysErrorTask getSysErrorTask() {
        return this.f1022u;
    }

    public TestPaperTask getTestPaperTask() {
        return this.f1025x;
    }

    public IUserProfile getUserProfile() {
        return this.f999L;
    }

    /* renamed from: h */
    public int mo27455h() {
        return this.f1010i;
    }

    /* renamed from: i */
    public BluetoothDevice mo27456i() {
        BluetoothGatt bluetoothGatt = this.f1006e;
        if (bluetoothGatt != null) {
            return bluetoothGatt.getDevice();
        }
        return null;
    }

    public boolean isMeasuring() {
        return this.f998K;
    }

    /* renamed from: l */
    public TimerTask mo27457l() {
        return new TimerTask() {
            public void run() {
                OnBLEService.this.m179n();
            }
        };
    }

    /* renamed from: m */
    public boolean mo27458m() {
        String str;
        String str2;
        boolean hasSystemFeature = getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
        this.f993F = hasSystemFeature;
        if (!hasSystemFeature) {
            OnBleConnectListener onBleConnectListener = this.f1003b;
            if (onBleConnectListener != null) {
                onBleConnectListener.onBLENoSupported();
            }
            return false;
        }
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService("bluetooth");
        if (bluetoothManager == null) {
            str = f986O;
            str2 = "1.Unable to initialize BluetoothManager.";
        } else {
            BluetoothAdapter adapter = bluetoothManager.getAdapter();
            this.f1005d = adapter;
            if (adapter == null) {
                str = f986O;
                str2 = "2.Unable to get BluetoothAdapter.";
            } else {
                BleScanCompat bleScanCompat = new BleScanCompat(adapter);
                this.f992E = bleScanCompat;
                String[] strArr = new String[1];
                strArr[0] = DeviceType.HealthMonitor.equals(this.f991D) ? UUIDConfig.HRP_SERVICE : UUIDConfig.THERM_SERVICE;
                bleScanCompat.mo27582a(strArr);
                this.f992E.mo27581a((BleScanCompat.BleScanCallback) this);
                return true;
            }
        }
        BleDevLog.m141b(str, str2);
        return false;
    }

    public IBinder onBind(Intent intent) {
        return this.f1002a;
    }

    public void onCreate() {
        super.onCreate();
        String str = f986O;
        BleDevLog.m141b(str, "onCreate mDeviceType = " + this.f991D);
        AssetsDatabaseManager.m132a(getApplicationContext());
        this.f1002a = new LocalBinder();
        C21501 r0 = new BleEnableStateReceiver() {
            /* renamed from: b */
            public void mo27406b() {
                if (OnBLEService.this.f993F) {
                    OnBLEService.this.mo27453d(100);
                }
            }

            /* renamed from: c */
            public void mo27407c() {
                if (OnBLEService.this.f993F) {
                    OnBLEService.this.mo27453d(101);
                }
            }

            /* renamed from: d */
            public void mo27408d() {
            }

            /* renamed from: e */
            public void mo27409e() {
            }
        };
        this.f1004c = r0;
        registerReceiver(r0, r0.mo27405a());
    }

    public void onDestroy() {
        EcgTask ecgTask = this.f1027z;
        if (ecgTask != null) {
            ecgTask.unInit();
        }
        AssetsDatabaseManager.m131a();
        m161c();
        BleEnableStateReceiver bleEnableStateReceiver = this.f1004c;
        if (bleEnableStateReceiver != null) {
            unregisterReceiver(bleEnableStateReceiver);
        }
        super.onDestroy();
    }

    public void onInnerThrowableCallback(Throwable th) {
        OnSDKThrowableCallback onSDKThrowableCallback = this.f1000M;
        if (onSDKThrowableCallback != null) {
            onSDKThrowableCallback.onSDKThrowable(th);
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        startService(new Intent(this, OnBLEInnerService.class));
        startForeground(1001, new Notification());
        return super.onStartCommand(intent, i, i2);
    }

    public void setMeasuring(boolean z) {
        this.f998K = z;
    }

    public void setUserProfile(IUserProfile iUserProfile) {
        this.f999L = iUserProfile;
    }

    public void updateBleConnectIntervalFailed() {
    }
}
