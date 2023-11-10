package p000a.p001a.p002a.p003a;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.mintti.visionsdk.ble.BleManager;
import com.mintti.visionsdk.ble.bean.MeasureType;
import com.mintti.visionsdk.ble.callback.IBleConnectionListener;
import com.mintti.visionsdk.ble.callback.IBleNotifyResponse;
import com.mintti.visionsdk.ble.callback.IBleReadResponse;
import com.mintti.visionsdk.ble.callback.IBleWriteResponse;
import com.mintti.visionsdk.common.BleConstants;
import com.mintti.visionsdk.common.LogUtils;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/* renamed from: a.a.a.a.a */
public class C0000a implements Handler.Callback {

    /* renamed from: a */
    public Context f0a;

    /* renamed from: b */
    public boolean f1b = false;

    /* renamed from: c */
    public boolean f2c = false;

    /* renamed from: d */
    public boolean f3d = false;

    /* renamed from: e */
    public final Map<String, IBleNotifyResponse> f4e = new HashMap();

    /* renamed from: f */
    public BluetoothGatt f5f = null;

    /* renamed from: g */
    public C0001a f6g = null;

    /* renamed from: h */
    public IBleReadResponse f7h = null;

    /* renamed from: i */
    public IBleWriteResponse f8i = null;

    /* renamed from: j */
    public IBleNotifyResponse f9j = null;

    /* renamed from: k */
    public BluetoothDevice f10k;

    /* renamed from: l */
    public int f11l = -1;

    /* renamed from: m */
    public String f12m = "";

    /* renamed from: n */
    public Handler f13n;

    /* renamed from: o */
    public Handler f14o;

    /* renamed from: p */
    public HandlerThread f15p = null;

    /* renamed from: q */
    public final Object f16q = new Object();

    /* renamed from: a.a.a.a.a$a */
    public interface C0001a {
    }

    /* renamed from: a.a.a.a.a$b */
    public class C0002b extends BluetoothGattCallback {

        /* renamed from: a.a.a.a.a$b$a */
        public class C0003a implements Runnable {

            /* renamed from: a */
            public final /* synthetic */ BluetoothGatt f18a;

            public C0003a(BluetoothGatt bluetoothGatt) {
                this.f18a = bluetoothGatt;
            }

            public void run() {
                C0001a aVar = C0000a.this.f6g;
                String address = this.f18a.getDevice().getAddress();
                List<BluetoothGattService> services = this.f18a.getServices();
                BleManager.C2184k kVar = (BleManager.C2184k) aVar;
                BluetoothGattCharacteristic unused = BleManager.this.noSerialNumberCharacteristic = null;
                for (BluetoothGattService characteristics : services) {
                    for (BluetoothGattCharacteristic next : characteristics.getCharacteristics()) {
                        if (next.getUuid().toString().equals(BleConstants.CHARA_BATTERY)) {
                            BluetoothGattCharacteristic unused2 = BleManager.this.batteryCharacteristic = next;
                        } else if (next.getUuid().toString().equals(BleConstants.CHARA_MANUFACTURER)) {
                            BluetoothGattCharacteristic unused3 = BleManager.this.manufacturerCharacteristic = next;
                        } else if (next.getUuid().toString().equals(BleConstants.CHARA_DEVICE_VERSION)) {
                            BluetoothGattCharacteristic unused4 = BleManager.this.deviceVersionCharacteristic = next;
                        } else if (next.getUuid().toString().equals(BleConstants.CHARA_SPO2)) {
                            BluetoothGattCharacteristic unused5 = BleManager.this.spo2Characteristic = next;
                        } else if (next.getUuid().toString().equals(BleConstants.CHARA_BG)) {
                            BluetoothGattCharacteristic unused6 = BleManager.this.bgCharacteristic = next;
                        } else if (next.getUuid().toString().equals(BleConstants.CHARA_ECG)) {
                            BluetoothGattCharacteristic unused7 = BleManager.this.ecgCharacteristic = next;
                        } else if (next.getUuid().toString().equals(BleConstants.CHARA_BP)) {
                            BluetoothGattCharacteristic unused8 = BleManager.this.bpCharacteristic = next;
                        } else if (next.getUuid().toString().equals(BleConstants.CHARA_TRANSFER)) {
                            BluetoothGattCharacteristic unused9 = BleManager.this.transferCharacteristic = next;
                        } else if (next.getUuid().toString().equals(BleConstants.CHARA_BT)) {
                            BluetoothGattCharacteristic unused10 = BleManager.this.btCharacteristic = next;
                        } else if (next.getUuid().toString().equals(BleConstants.SERIAL_NUMBER)) {
                            BluetoothGattCharacteristic unused11 = BleManager.this.serialNumberCharacteristic = next;
                        } else if (next.getUuid().toString().equals(BleConstants.NO_SERIAL_NUMBER)) {
                            BluetoothGattCharacteristic unused12 = BleManager.this.noSerialNumberCharacteristic = next;
                        }
                    }
                }
                BleManager bleManager = BleManager.this;
                bleManager.notifyBG(bleManager.handleVisionData.f51K);
                BleManager bleManager2 = BleManager.this;
                bleManager2.notifyBp(bleManager2.handleVisionData);
                BleManager bleManager3 = BleManager.this;
                bleManager3.notifyEcg(bleManager3.handleVisionData);
                BleManager bleManager4 = BleManager.this;
                bleManager4.notifySpo2(bleManager4.handleVisionData);
                BleManager bleManager5 = BleManager.this;
                bleManager5.indicateBt(bleManager5.handleVisionData.f52L);
                BleManager.this.notifyBattery();
                BleManager.this.getDeviceVersionInfo(new C0004b(kVar));
                for (IBleConnectionListener onConnectSuccess : BleManager.this.connectionListenerList) {
                    onConnectSuccess.onConnectSuccess(address);
                }
            }
        }

        public C0002b() {
        }

        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            C0000a aVar = C0000a.this;
            aVar.f9j = aVar.f4e.get(bluetoothGattCharacteristic.getUuid().toString());
            IBleNotifyResponse iBleNotifyResponse = C0000a.this.f9j;
            if (iBleNotifyResponse != null) {
                iBleNotifyResponse.onCharacteristicChanged(bluetoothGattCharacteristic.getValue());
            }
        }

        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            super.onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, i);
            if (i == 0) {
                IBleReadResponse iBleReadResponse = C0000a.this.f7h;
                if (iBleReadResponse != null) {
                    iBleReadResponse.onReadSuccess(bluetoothGattCharacteristic.getValue());
                }
            } else {
                IBleReadResponse iBleReadResponse2 = C0000a.this.f7h;
                if (iBleReadResponse2 != null) {
                    iBleReadResponse2.onReadFailed();
                }
            }
            C0000a.this.f14o.removeMessages(1);
            C0000a.m0a(C0000a.this);
        }

        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            super.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i);
            C0000a aVar = C0000a.this;
            if (aVar.f11l != 14) {
                if (i == 0) {
                    IBleWriteResponse iBleWriteResponse = aVar.f8i;
                    if (iBleWriteResponse != null) {
                        iBleWriteResponse.onWriteSuccess();
                    }
                } else {
                    IBleWriteResponse iBleWriteResponse2 = aVar.f8i;
                    if (iBleWriteResponse2 != null) {
                        iBleWriteResponse2.onWriteFailed();
                    }
                }
                C0000a.this.f14o.removeMessages(1);
                C0000a.m0a(C0000a.this);
            }
        }

        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            super.onConnectionStateChange(bluetoothGatt, i, i2);
            LogUtils.m378d("BleConnection", "onConnectionStateChange: status: " + i + " newState: " + i2);
            C0000a aVar = C0000a.this;
            aVar.f5f = bluetoothGatt;
            aVar.mo13a(i, i2, bluetoothGatt.getDevice().getAddress());
        }

        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            super.onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i);
            C0000a.this.f14o.removeMessages(1);
            C0000a.m0a(C0000a.this);
        }

        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            super.onDescriptorWrite(bluetoothGatt, bluetoothGattDescriptor, i);
            String uuid = bluetoothGattDescriptor.getCharacteristic().getUuid().toString();
            C0000a aVar = C0000a.this;
            aVar.f9j = aVar.f4e.get(uuid);
            if (i == 0) {
                C0000a aVar2 = C0000a.this;
                int i2 = aVar2.f11l;
                if (i2 == 12) {
                    IBleNotifyResponse iBleNotifyResponse = aVar2.f9j;
                    if (iBleNotifyResponse != null) {
                        iBleNotifyResponse.onNotifySuccess();
                    }
                } else if (i2 == 13) {
                    aVar2.f4e.remove(uuid);
                }
            } else {
                IBleNotifyResponse iBleNotifyResponse2 = C0000a.this.f9j;
                if (iBleNotifyResponse2 != null) {
                    iBleNotifyResponse2.onNotifyFailed();
                }
            }
            C0000a.this.f14o.removeMessages(1);
            C0000a.m0a(C0000a.this);
        }

        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            super.onServicesDiscovered(bluetoothGatt, i);
            C0000a aVar = C0000a.this;
            if (aVar.f6g != null) {
                aVar.f14o.postDelayed(new C0003a(bluetoothGatt), 200);
            }
        }
    }

    public C0000a(Context context) {
        this.f0a = context;
        mo21d();
    }

    /* renamed from: a */
    public static void m0a(C0000a aVar) {
        synchronized (aVar.f16q) {
            aVar.f16q.notify();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public /* synthetic */ void m1a(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.f11l = 13;
        this.f12m = bluetoothGattCharacteristic.getUuid().toString();
        if (this.f5f.setCharacteristicNotification(bluetoothGattCharacteristic, false)) {
            BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
            descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            if (this.f5f.writeDescriptor(descriptor)) {
                this.f14o.sendEmptyMessageDelayed(1, 200);
                mo26i();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public /* synthetic */ void m2a(IBleReadResponse iBleReadResponse, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.f11l = 10;
        this.f7h = iBleReadResponse;
        if (this.f5f.readCharacteristic(bluetoothGattCharacteristic)) {
            this.f14o.sendEmptyMessageDelayed(1, 200);
            mo26i();
            return;
        }
        iBleReadResponse.onReadFailed();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public /* synthetic */ void m3a(IBleWriteResponse iBleWriteResponse, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        this.f11l = 11;
        this.f8i = iBleWriteResponse;
        bluetoothGattCharacteristic.setValue(i, 20, 0);
        if (this.f5f.writeCharacteristic(bluetoothGattCharacteristic)) {
            this.f14o.sendEmptyMessageDelayed(1, 200);
            mo26i();
        } else if (iBleWriteResponse != null) {
            iBleWriteResponse.onWriteFailed();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public /* synthetic */ void m4a(IBleWriteResponse iBleWriteResponse, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr) {
        this.f11l = 11;
        this.f8i = iBleWriteResponse;
        bluetoothGattCharacteristic.setValue(bArr);
        if (this.f5f.writeCharacteristic(bluetoothGattCharacteristic)) {
            this.f14o.sendEmptyMessageDelayed(1, 200);
            mo26i();
        } else if (iBleWriteResponse != null) {
            iBleWriteResponse.onWriteFailed();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m5b(int i, int i2, String str) {
        if (i != 0) {
            this.f3d = false;
            if (this.f1b) {
                C0001a aVar = this.f6g;
                if (aVar != null) {
                    ((BleManager.C2184k) aVar).mo27657a(str, this.f2c, i);
                    this.f1b = false;
                    this.f3d = false;
                    return;
                }
                return;
            }
            C0001a aVar2 = this.f6g;
            if (aVar2 != null) {
                BleManager.C2184k kVar = (BleManager.C2184k) aVar2;
                boolean unused = BleManager.this.isMeasuring = false;
                MeasureType unused2 = BleManager.this.mCurrentMeasureType = null;
                for (IBleConnectionListener onConnectFailed : BleManager.this.connectionListenerList) {
                    onConnectFailed.onConnectFailed(str);
                }
            }
            BluetoothGatt bluetoothGatt = this.f5f;
            if (bluetoothGatt != null) {
                bluetoothGatt.disconnect();
                mo24h();
                this.f5f.close();
            }
            this.f1b = false;
        } else if (i2 == 2) {
            this.f1b = true;
            this.f3d = false;
            this.f2c = false;
            C0001a aVar3 = this.f6g;
            if (aVar3 != null) {
                ((BleManager.C2184k) aVar3).getClass();
            }
            this.f14o.postDelayed(new Runnable() {
                public final void run() {
                    C0000a.this.m11g();
                }
            }, 100);
        } else if (i2 == 0) {
            this.f1b = false;
            this.f3d = false;
            C0001a aVar4 = this.f6g;
            if (aVar4 != null) {
                ((BleManager.C2184k) aVar4).mo27657a(str, this.f2c, i2);
                mo12a();
            }
            if (this.f5f != null) {
                mo24h();
                this.f5f.close();
                this.f5f = null;
            }
        } else if (i2 == 1) {
            this.f3d = true;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public /* synthetic */ void m6b(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.f11l = 13;
        this.f12m = bluetoothGattCharacteristic.getUuid().toString();
        if (this.f5f.setCharacteristicNotification(bluetoothGattCharacteristic, false)) {
            BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
            descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            if (this.f5f.writeDescriptor(descriptor)) {
                this.f14o.sendEmptyMessageDelayed(1, 200);
                mo26i();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public /* synthetic */ void m7b(BluetoothGattCharacteristic bluetoothGattCharacteristic, IBleNotifyResponse iBleNotifyResponse) {
        this.f11l = 12;
        this.f4e.put(bluetoothGattCharacteristic.getUuid().toString(), iBleNotifyResponse);
        this.f12m = bluetoothGattCharacteristic.getUuid().toString();
        if (this.f5f.setCharacteristicNotification(bluetoothGattCharacteristic, true)) {
            BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
            if (this.f5f.writeDescriptor(descriptor)) {
                this.f14o.sendEmptyMessageDelayed(1, 200);
                mo26i();
                return;
            } else if (iBleNotifyResponse == null) {
                return;
            }
        } else if (iBleNotifyResponse == null) {
            return;
        }
        iBleNotifyResponse.onNotifyFailed();
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public /* synthetic */ void m8c(BluetoothGattCharacteristic bluetoothGattCharacteristic, IBleNotifyResponse iBleNotifyResponse) {
        this.f11l = 12;
        this.f4e.put(bluetoothGattCharacteristic.getUuid().toString(), iBleNotifyResponse);
        this.f12m = bluetoothGattCharacteristic.getUuid().toString();
        if (this.f5f.setCharacteristicNotification(bluetoothGattCharacteristic, true)) {
            BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            if (this.f5f.writeDescriptor(descriptor)) {
                this.f14o.sendEmptyMessageDelayed(1, 200);
                mo26i();
                return;
            } else if (iBleNotifyResponse == null) {
                return;
            }
        } else if (iBleNotifyResponse == null) {
            return;
        }
        iBleNotifyResponse.onNotifyFailed();
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public /* synthetic */ void m9e() {
        this.f2c = false;
        this.f5f = Build.VERSION.SDK_INT >= 23 ? this.f10k.connectGatt(this.f0a, false, new C0002b(), 2) : this.f10k.connectGatt(this.f0a, false, new C0002b());
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public /* synthetic */ void m10f() {
        mo24h();
        this.f5f.disconnect();
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public /* synthetic */ void m11g() {
        this.f5f.discoverServices();
    }

    /* renamed from: a */
    public final void mo13a(int i, int i2, String str) {
        this.f14o.post(new Runnable(i, i2, str) {
            public final /* synthetic */ int f$1;
            public final /* synthetic */ int f$2;
            public final /* synthetic */ String f$3;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
            }

            public final void run() {
                C0000a.this.m5b(this.f$1, this.f$2, this.f$3);
            }
        });
    }

    /* renamed from: a */
    public void mo14a(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i, IBleWriteResponse iBleWriteResponse) {
        this.f13n.postDelayed(new Runnable(iBleWriteResponse, bluetoothGattCharacteristic, i) {
            public final /* synthetic */ IBleWriteResponse f$1;
            public final /* synthetic */ BluetoothGattCharacteristic f$2;
            public final /* synthetic */ int f$3;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
            }

            public final void run() {
                C0000a.this.m3a(this.f$1, this.f$2, this.f$3);
            }
        }, 100);
    }

    /* renamed from: a */
    public void mo15a(BluetoothGattCharacteristic bluetoothGattCharacteristic, IBleNotifyResponse iBleNotifyResponse) {
        this.f13n.postDelayed(new Runnable(bluetoothGattCharacteristic, iBleNotifyResponse) {
            public final /* synthetic */ BluetoothGattCharacteristic f$1;
            public final /* synthetic */ IBleNotifyResponse f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                C0000a.this.m7b(this.f$1, this.f$2);
            }
        }, 100);
    }

    /* renamed from: a */
    public void mo16a(BluetoothGattCharacteristic bluetoothGattCharacteristic, IBleReadResponse iBleReadResponse) {
        this.f13n.postDelayed(new Runnable(iBleReadResponse, bluetoothGattCharacteristic) {
            public final /* synthetic */ IBleReadResponse f$1;
            public final /* synthetic */ BluetoothGattCharacteristic f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                C0000a.this.m2a(this.f$1, this.f$2);
            }
        }, 100);
    }

    /* renamed from: a */
    public void mo17a(BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr, IBleWriteResponse iBleWriteResponse) {
        this.f13n.postDelayed(new Runnable(iBleWriteResponse, bluetoothGattCharacteristic, bArr) {
            public final /* synthetic */ IBleWriteResponse f$1;
            public final /* synthetic */ BluetoothGattCharacteristic f$2;
            public final /* synthetic */ byte[] f$3;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
            }

            public final void run() {
                C0000a.this.m4a(this.f$1, this.f$2, this.f$3);
            }
        }, 100);
    }

    /* renamed from: b */
    public void mo18b() {
        mo12a();
        mo21d();
        BluetoothGatt bluetoothGatt = this.f5f;
        if (bluetoothGatt != null) {
            bluetoothGatt.close();
            this.f5f = null;
        }
        Objects.requireNonNull(this.f10k, "bluetoothDevice is null");
        if (!this.f1b) {
            this.f14o.postDelayed(new Runnable() {
                public final void run() {
                    C0000a.this.m9e();
                }
            }, 100);
        }
    }

    /* renamed from: c */
    public void mo19c() {
        mo12a();
        if (this.f1b || this.f3d) {
            this.f1b = false;
            this.f2c = true;
        }
        if (this.f5f != null) {
            this.f14o.post(new Runnable() {
                public final void run() {
                    C0000a.this.m10f();
                }
            });
        }
    }

    /* renamed from: c */
    public void mo20c(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.f13n.postDelayed(new Runnable(bluetoothGattCharacteristic) {
            public final /* synthetic */ BluetoothGattCharacteristic f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                C0000a.this.m1a(this.f$1);
            }
        }, 100);
    }

    /* renamed from: d */
    public final void mo21d() {
        HandlerThread handlerThread = this.f15p;
        if (handlerThread == null || !handlerThread.isAlive()) {
            HandlerThread handlerThread2 = new HandlerThread("BleConnectionThread");
            this.f15p = handlerThread2;
            handlerThread2.start();
            this.f13n = new Handler(this.f15p.getLooper());
            this.f14o = new Handler(Looper.getMainLooper(), this);
        }
    }

    /* renamed from: d */
    public void mo22d(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.f13n.postDelayed(new Runnable(bluetoothGattCharacteristic) {
            public final /* synthetic */ BluetoothGattCharacteristic f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                C0000a.this.m6b(this.f$1);
            }
        }, 100);
    }

    /* renamed from: d */
    public void mo23d(BluetoothGattCharacteristic bluetoothGattCharacteristic, IBleNotifyResponse iBleNotifyResponse) {
        this.f13n.postDelayed(new Runnable(bluetoothGattCharacteristic, iBleNotifyResponse) {
            public final /* synthetic */ BluetoothGattCharacteristic f$1;
            public final /* synthetic */ IBleNotifyResponse f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                C0000a.this.m8c(this.f$1, this.f$2);
            }
        }, 100);
    }

    /* renamed from: h */
    public final synchronized void mo24h() {
        BluetoothGatt bluetoothGatt;
        try {
            Method method = BluetoothGatt.class.getMethod("refresh", new Class[0]);
            if (!(method == null || (bluetoothGatt = this.f5f) == null)) {
                ((Boolean) method.invoke(bluetoothGatt, new Object[0])).booleanValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public boolean handleMessage(Message message) {
        if (message.what == 1) {
            switch (this.f11l) {
                case 10:
                    IBleReadResponse iBleReadResponse = this.f7h;
                    if (iBleReadResponse != null) {
                        iBleReadResponse.onReadFailed();
                        break;
                    }
                    break;
                case 11:
                    IBleWriteResponse iBleWriteResponse = this.f8i;
                    if (iBleWriteResponse != null) {
                        iBleWriteResponse.onWriteFailed();
                        break;
                    }
                    break;
                case 12:
                    IBleNotifyResponse iBleNotifyResponse = this.f4e.get(this.f12m);
                    this.f9j = iBleNotifyResponse;
                    if (iBleNotifyResponse != null) {
                        iBleNotifyResponse.onNotifyFailed();
                        break;
                    }
                    break;
            }
            synchronized (this.f16q) {
                this.f16q.notify();
            }
        }
        return true;
    }

    /* renamed from: i */
    public final void mo26i() {
        synchronized (this.f16q) {
            try {
                this.f16q.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: a */
    public final void mo12a() {
        this.f4e.clear();
        this.f7h = null;
        this.f8i = null;
        this.f9j = null;
        this.f12m = "";
        this.f11l = -1;
        Handler handler = this.f13n;
        if (handler != null) {
            handler.removeCallbacksAndMessages((Object) null);
        }
        Handler handler2 = this.f14o;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages((Object) null);
        }
        HandlerThread handlerThread = this.f15p;
        if (handlerThread != null) {
            handlerThread.quit();
            this.f15p = null;
        }
    }
}
