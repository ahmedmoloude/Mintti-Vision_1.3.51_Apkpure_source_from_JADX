package com.mintti.visionsdk.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.linktop.DeviceType;
import com.linktop.MonitorDataTransmission;
import com.linktop.MonitorDataTransmissionManager;
import com.linktop.constant.DeviceInfo;
import com.linktop.constant.Pair;
import com.linktop.constant.ResultData;
import com.linktop.constant.TestPaper;
import com.linktop.infs.OnBatteryListener;
import com.linktop.infs.OnBleConnectListener;
import com.linktop.infs.OnBpDataListener;
import com.linktop.infs.OnBpResultListener;
import com.linktop.infs.OnBtResultListener;
import com.linktop.infs.OnDeviceInfoListener;
import com.linktop.infs.OnDeviceVersionListener;
import com.linktop.infs.OnEcgResultListener;
import com.linktop.infs.OnSpO2ResultListener;
import com.linktop.infs.OnTestPaperResultListener;
import com.mintti.visionsdk.AlgoHelper;
import com.mintti.visionsdk.ble.bean.BgEvent;
import com.mintti.visionsdk.ble.bean.MeasureType;
import com.mintti.visionsdk.ble.callback.IBgResultListener;
import com.mintti.visionsdk.ble.callback.IBleConnectionListener;
import com.mintti.visionsdk.ble.callback.IBleInitCallback;
import com.mintti.visionsdk.ble.callback.IBleNotifyResponse;
import com.mintti.visionsdk.ble.callback.IBleReadResponse;
import com.mintti.visionsdk.ble.callback.IBleScanCallback;
import com.mintti.visionsdk.ble.callback.IBleWriteResponse;
import com.mintti.visionsdk.ble.callback.IBpResultListener;
import com.mintti.visionsdk.ble.callback.IBtResultListener;
import com.mintti.visionsdk.ble.callback.IDeviceBatteryCallback;
import com.mintti.visionsdk.ble.callback.IDeviceVersionCallback;
import com.mintti.visionsdk.ble.callback.IEcgFingerDetectionListener;
import com.mintti.visionsdk.ble.callback.IEcgResultListener;
import com.mintti.visionsdk.ble.callback.IManufacturerInfoCallback;
import com.mintti.visionsdk.ble.callback.IRawBgDataCallback;
import com.mintti.visionsdk.ble.callback.IRawBpDataCallback;
import com.mintti.visionsdk.ble.callback.IRawBtDataCallback;
import com.mintti.visionsdk.ble.callback.IRawSpo2DataCallback;
import com.mintti.visionsdk.ble.callback.ISerialNumberCallback;
import com.mintti.visionsdk.ble.callback.ISpo2ResultListener;
import com.mintti.visionsdk.common.LogUtils;
import com.p020kl.vision_ecg.EcgAlgo;
import java.util.ArrayList;
import java.util.List;
import p000a.p001a.p002a.p003a.C0000a;
import p000a.p001a.p002a.p003a.C0005c;
import p000a.p001a.p002a.p003a.p004d.C0011d;

public class BleManager {
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic batteryCharacteristic;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic bgCharacteristic;
    private C0000a bleConnection;
    private final C0000a.C0001a bleConnectionListener;
    /* access modifiers changed from: private */
    public IBleInitCallback bleInitCallback;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic bpCharacteristic;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic btCharacteristic;
    /* access modifiers changed from: private */
    public final List<IBleConnectionListener> connectionListenerList;
    /* access modifiers changed from: private */
    public String curBleMac;
    private DeviceType deviceType;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic deviceVersionCharacteristic;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic ecgCharacteristic;
    /* access modifiers changed from: private */
    public IEcgFingerDetectionListener ecgFingerDetectionListener;
    /* access modifiers changed from: private */
    public final C0011d handleVisionData;
    /* access modifiers changed from: private */
    public int hrv;
    /* access modifiers changed from: private */
    public IBgResultListener iBgResultListener;
    /* access modifiers changed from: private */
    public IBpResultListener iBpResultListener;
    /* access modifiers changed from: private */
    public IBtResultListener iBtResultListener;
    /* access modifiers changed from: private */
    public IDeviceBatteryCallback iDeviceBatteryCallback;
    /* access modifiers changed from: private */
    public IDeviceVersionCallback iDeviceVersionCallback;
    /* access modifiers changed from: private */
    public IEcgResultListener iEcgResultListener;
    /* access modifiers changed from: private */
    public IRawBpDataCallback iRawBpDataCallback;
    /* access modifiers changed from: private */
    public ISpo2ResultListener iSpo2ResultListener;
    /* access modifiers changed from: private */
    public boolean isActiveDisconnect;
    /* access modifiers changed from: private */
    public boolean isLinkTopConnecting;
    /* access modifiers changed from: private */
    public boolean isLowCost;
    /* access modifiers changed from: private */
    public volatile boolean isMeasuring;
    private final OnBleConnectListener linkTopBleConnectListener;
    /* access modifiers changed from: private */
    public final OnDeviceVersionListener linkTopVersionListener;
    private String linktopFirmwate;
    private Context mContext;
    /* access modifiers changed from: private */
    public MeasureType mCurrentMeasureType;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic manufacturerCharacteristic;
    /* access modifiers changed from: private */
    public int maxRRI;
    /* access modifiers changed from: private */
    public int minRRI;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic noSerialNumberCharacteristic;
    private final OnBtResultListener onLinkTopBtResultListener;
    private final OnEcgResultListener onLinkTopEcgResultListener;
    /* access modifiers changed from: private */
    public final OnBatteryListener onLinktopBatteryListner;
    private final OnBpDataListener onLinktopBpDataListener;
    private final OnBpResultListener onLinktopBpResultListener;
    /* access modifiers changed from: private */
    public final OnDeviceInfoListener onLinktopDeviceInfoListener;
    private final OnSpO2ResultListener onLinktopSpO2ResultListener;
    private final OnTestPaperResultListener onTestPaperResultListener;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic serialNumberCharacteristic;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic spo2Characteristic;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic transferCharacteristic;

    /* renamed from: com.mintti.visionsdk.ble.BleManager$a */
    public class C2174a implements IBleReadResponse {

        /* renamed from: a */
        public final /* synthetic */ IManufacturerInfoCallback f1236a;

        public C2174a(BleManager bleManager, IManufacturerInfoCallback iManufacturerInfoCallback) {
            this.f1236a = iManufacturerInfoCallback;
        }

        public void onReadFailed() {
            LogUtils.m378d("BleManager", "onReadFailed: getDeviceManufacturer");
        }

        public void onReadSuccess(byte[] bArr) {
            LogUtils.m378d("BleManager", "onReadSuccess: DeviceManufacturer = " + new String(bArr));
            IManufacturerInfoCallback iManufacturerInfoCallback = this.f1236a;
            if (iManufacturerInfoCallback != null) {
                iManufacturerInfoCallback.onManufacturerInfo(new String(bArr));
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$b */
    public class C2175b implements OnEcgResultListener {
        public C2175b() {
        }

        public void onDrawWave(Object obj) {
            if (obj instanceof int[]) {
                for (int i : (int[]) obj) {
                    if (BleManager.this.iEcgResultListener != null) {
                        BleManager.this.iEcgResultListener.onDrawWave(i);
                    }
                }
            } else if (obj instanceof Integer) {
                int intValue = ((Integer) obj).intValue();
                if (BleManager.this.iEcgResultListener != null) {
                    BleManager.this.iEcgResultListener.onDrawWave(intValue);
                }
            }
        }

        public void onECGValues(int i, int i2) {
            if (i != 65536) {
                if (i == 262145) {
                    if (i2 > 2000 || i2 < 300) {
                        i2 = 0;
                    }
                    if (BleManager.this.maxRRI == 0 && BleManager.this.minRRI == 0 && i2 != 0) {
                        int unused = BleManager.this.maxRRI = i2;
                        int unused2 = BleManager.this.minRRI = i2;
                    }
                    if (i2 > BleManager.this.maxRRI) {
                        int unused3 = BleManager.this.maxRRI = i2;
                    }
                    if (i2 < BleManager.this.minRRI) {
                        int unused4 = BleManager.this.minRRI = i2;
                    }
                    if (BleManager.this.iEcgResultListener == null) {
                        return;
                    }
                } else if (i == 1048576) {
                    int unused5 = BleManager.this.hrv = i2;
                    if (BleManager.this.iEcgResultListener == null) {
                        return;
                    }
                } else if (i == 4194304 && BleManager.this.iEcgResultListener != null) {
                    BleManager.this.iEcgResultListener.onRespiratoryRate(i2);
                    return;
                } else {
                    return;
                }
                BleManager.this.iEcgResultListener.onEcgResult(BleManager.this.maxRRI, BleManager.this.minRRI, BleManager.this.hrv);
            } else if (BleManager.this.iEcgResultListener != null) {
                BleManager.this.iEcgResultListener.onHeartRate(i2);
            }
        }

        public void onFingerDetection(boolean z) {
            if (BleManager.this.ecgFingerDetectionListener != null) {
                BleManager.this.ecgFingerDetectionListener.onFingerDetection(z);
            }
        }

        public void onSignalQuality(int i) {
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$c */
    public class C2176c implements OnTestPaperResultListener {
        public C2176c() {
        }

        public void onTestPaperEvent(int i, Object obj) {
            BgEvent bgEvent;
            IBgResultListener iBgResultListener;
            if (i != 1) {
                if (i == 3) {
                    iBgResultListener = BleManager.this.iBgResultListener;
                    bgEvent = BgEvent.BG_EVENT_WAIT_DRIP_BLOOD;
                } else if (i != 5) {
                    if (i != 13) {
                        LogUtils.m378d("onTestPaperEvent", "eventId:" + i + ", obj:" + obj);
                        return;
                    } else if (BleManager.this.iBgResultListener != null) {
                        iBgResultListener = BleManager.this.iBgResultListener;
                        bgEvent = BgEvent.BG_EVENT_BLOOD_SAMPLE_DETECTING;
                    } else {
                        return;
                    }
                } else if (BleManager.this.iBgResultListener != null) {
                    BleManager.this.iBgResultListener.onBgResult(((Double) obj).doubleValue());
                    return;
                } else {
                    return;
                }
                iBgResultListener.onBgEvent(bgEvent);
            }
        }

        public void onTestPaperException(int i) {
            IBgResultListener iBgResultListener;
            BgEvent bgEvent;
            if (i == 0 || i == 6) {
                if (BleManager.this.iBgResultListener != null) {
                    iBgResultListener = BleManager.this.iBgResultListener;
                    bgEvent = BgEvent.BG_EVENT_WAIT_PAGER_INSERT;
                } else {
                    return;
                }
            } else if (i != 9) {
                if (i != 16) {
                    LogUtils.m378d("onTestPaperException", "exception:" + i);
                    return;
                } else if (BleManager.this.iBgResultListener != null) {
                    iBgResultListener = BleManager.this.iBgResultListener;
                    bgEvent = BgEvent.BG_EVENT_GET_BG_RESULT_TIMEOUT;
                } else {
                    return;
                }
            } else if (BleManager.this.iBgResultListener != null) {
                iBgResultListener = BleManager.this.iBgResultListener;
                bgEvent = BgEvent.BG_EVENT_PAPER_USED;
            } else {
                return;
            }
            iBgResultListener.onBgEvent(bgEvent);
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$d */
    public class C2177d implements OnBtResultListener {
        public C2177d() {
        }

        public void onBtResult(double d) {
            if (BleManager.this.iBtResultListener != null) {
                BleManager.this.iBtResultListener.onBtResult(d);
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$e */
    public class C2178e implements OnSpO2ResultListener {
        public C2178e() {
        }

        public void onFingerDetection(int i) {
        }

        public void onSpO2End() {
            if (BleManager.this.iSpo2ResultListener != null) {
                BleManager.this.iSpo2ResultListener.onSpo2End();
            }
        }

        public void onSpO2Result(int i, int i2) {
            if (BleManager.this.iSpo2ResultListener != null) {
                BleManager.this.iSpo2ResultListener.onSpo2ResultData(i2, (double) i);
            }
        }

        public void onSpO2Wave(int i) {
            if (BleManager.this.iSpo2ResultListener != null) {
                BleManager.this.iSpo2ResultListener.onWaveData(i);
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$f */
    public class C2179f implements OnBpResultListener {
        public C2179f() {
        }

        public void onBpResult(int i, int i2, int i3) {
            if (BleManager.this.iBpResultListener != null) {
                BleManager.this.iBpResultListener.onBpResult(i, i2, i3);
            }
        }

        public void onBpResultError() {
            LogUtils.m378d("BleManager", "onBpResultError: ");
            if (BleManager.this.iBpResultListener != null) {
                BleManager.this.iBpResultListener.onBpError();
            }
        }

        public void onLeakError(int i) {
            LogUtils.m378d("BleManager", "onLeakError: " + i);
            if (BleManager.this.iBpResultListener == null) {
                return;
            }
            if (i == 0) {
                BleManager.this.iBpResultListener.onLeadError();
            } else if (i == 1) {
                BleManager.this.iBpResultListener.onBpError();
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$g */
    public class C2180g implements OnBpDataListener {
        public C2180g() {
        }

        public void onFIRAvgFilter(ResultData resultData, boolean z) {
        }

        public void onRcvPressure(int i) {
            if (BleManager.this.iRawBpDataCallback != null) {
                BleManager.this.iRawBpDataCallback.onPressure((short) (i / 100));
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$h */
    public class C2181h implements OnBatteryListener {
        public C2181h() {
        }

        public void onBatteryCharging() {
        }

        public void onBatteryFull() {
            if (BleManager.this.iDeviceBatteryCallback != null) {
                BleManager.this.iDeviceBatteryCallback.onDeviceBattery(100);
            }
        }

        public void onBatteryQuery(int i) {
            if (BleManager.this.iDeviceBatteryCallback != null) {
                BleManager.this.iDeviceBatteryCallback.onDeviceBattery(i);
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$i */
    public class C2182i implements OnDeviceInfoListener {
        public C2182i(BleManager bleManager) {
        }

        public void onDeviceInfo(DeviceInfo deviceInfo) {
            LogUtils.m378d("BleManager", deviceInfo.toString());
        }

        public void onReadDeviceInfoFailed() {
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$j */
    public class C2183j implements MonitorDataTransmission.OnServiceBindListener {
        public C2183j() {
        }

        public void onSDKThrowable(Throwable th) {
            if (BleManager.this.bleInitCallback != null) {
                BleManager.this.bleInitCallback.onInitFailed();
            }
        }

        public void onServiceBind() {
            if (BleManager.this.bleInitCallback != null) {
                BleManager.this.bleInitCallback.onInitSuccess();
            }
            MonitorDataTransmissionManager.getInstance().setOnBatteryListener(BleManager.this.onLinktopBatteryListner);
            MonitorDataTransmissionManager.getInstance().setOnDeviceVersionListener(BleManager.this.linkTopVersionListener);
            MonitorDataTransmissionManager.getInstance().setOnDevIdAndKeyListener(BleManager.this.onLinktopDeviceInfoListener);
        }

        public void onServiceUnbind() {
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$k */
    public class C2184k implements C0000a.C0001a {
        public C2184k() {
        }

        /* renamed from: a */
        public void mo27657a(String str, boolean z, int i) {
            boolean unused = BleManager.this.isMeasuring = false;
            MeasureType unused2 = BleManager.this.mCurrentMeasureType = null;
            LogUtils.m378d("BleManager", "onDisconnected: " + z);
            for (IBleConnectionListener onDisconnected : BleManager.this.connectionListenerList) {
                onDisconnected.onDisconnected(str, z);
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$l */
    public class C2185l implements OnBleConnectListener {
        public C2185l() {
        }

        public void onBLENoSupported() {
        }

        public void onBleState(int i) {
            switch (i) {
                case 100:
                case 101:
                    if (BleManager.this.isLinkTopConnecting) {
                        boolean unused = BleManager.this.isMeasuring = false;
                        for (IBleConnectionListener onConnectFailed : BleManager.this.connectionListenerList) {
                            onConnectFailed.onConnectFailed(BleManager.this.curBleMac);
                        }
                        return;
                    }
                    boolean unused2 = BleManager.this.isMeasuring = false;
                    for (IBleConnectionListener onDisconnected : BleManager.this.connectionListenerList) {
                        onDisconnected.onDisconnected(BleManager.this.curBleMac, BleManager.this.isActiveDisconnect);
                    }
                    return;
                case 102:
                    boolean unused3 = BleManager.this.isLinkTopConnecting = true;
                    boolean unused4 = BleManager.this.isActiveDisconnect = false;
                    return;
                case 103:
                    boolean unused5 = BleManager.this.isActiveDisconnect = false;
                    boolean unused6 = BleManager.this.isLinkTopConnecting = false;
                    for (IBleConnectionListener onConnectSuccess : BleManager.this.connectionListenerList) {
                        onConnectSuccess.onConnectSuccess(BleManager.this.curBleMac);
                    }
                    return;
                default:
                    return;
            }
        }

        public void onOpenBLE() {
        }

        public void onUpdateDialogBleList() {
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$m */
    public class C2186m implements IBleWriteResponse {

        /* renamed from: a */
        public final /* synthetic */ MeasureType f1247a;

        /* renamed from: b */
        public final /* synthetic */ IBleWriteResponse f1248b;

        public C2186m(MeasureType measureType, IBleWriteResponse iBleWriteResponse) {
            this.f1247a = measureType;
            this.f1248b = iBleWriteResponse;
        }

        public void onWriteFailed() {
            MeasureType unused = BleManager.this.mCurrentMeasureType = null;
            boolean unused2 = BleManager.this.isMeasuring = false;
            LogUtils.m378d("BleManager", this.f1247a.name() + " start measure failed ");
            IBleWriteResponse iBleWriteResponse = this.f1248b;
            if (iBleWriteResponse != null) {
                iBleWriteResponse.onWriteFailed();
            }
        }

        public void onWriteSuccess() {
            MeasureType unused = BleManager.this.mCurrentMeasureType = this.f1247a;
            boolean unused2 = BleManager.this.isMeasuring = true;
            LogUtils.m378d("BleManager", this.f1247a.name() + " start measure success ");
            IBleWriteResponse iBleWriteResponse = this.f1248b;
            if (iBleWriteResponse != null) {
                iBleWriteResponse.onWriteSuccess();
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$n */
    public class C2187n implements IBleWriteResponse {

        /* renamed from: a */
        public final /* synthetic */ MeasureType f1250a;

        /* renamed from: b */
        public final /* synthetic */ IBleWriteResponse f1251b;

        /* renamed from: c */
        public final /* synthetic */ int f1252c;

        public C2187n(MeasureType measureType, IBleWriteResponse iBleWriteResponse, int i) {
            this.f1250a = measureType;
            this.f1251b = iBleWriteResponse;
            this.f1252c = i;
        }

        public void onWriteFailed() {
            boolean unused = BleManager.this.isMeasuring = false;
            MeasureType unused2 = BleManager.this.mCurrentMeasureType = null;
            if (this.f1250a != MeasureType.TYPE_BG) {
                LogUtils.m378d("BleManager", this.f1250a.name() + " start measure failed ");
                IBleWriteResponse iBleWriteResponse = this.f1251b;
                if (iBleWriteResponse != null) {
                    iBleWriteResponse.onWriteFailed();
                    return;
                }
                return;
            }
            LogUtils.m378d("BleManager", this.f1250a.name() + " bg calibration failed ");
        }

        public void onWriteSuccess() {
            MeasureType unused = BleManager.this.mCurrentMeasureType = this.f1250a;
            boolean unused2 = BleManager.this.isMeasuring = true;
            if (this.f1250a != MeasureType.TYPE_BG) {
                LogUtils.m378d("BleManager", this.f1250a.name() + " start measure success ");
                IBleWriteResponse iBleWriteResponse = this.f1251b;
                if (iBleWriteResponse != null) {
                    iBleWriteResponse.onWriteSuccess();
                }
            } else {
                LogUtils.m378d("BleManager", this.f1250a.name() + " start calibration ");
            }
            int i = this.f1252c;
            if (i == 4) {
                BleManager.this.handleVisionData.mo38a();
            } else if (i == 3) {
                C0011d access$2000 = BleManager.this.handleVisionData;
                access$2000.f47G = 0;
                access$2000.f50J.removeMessages(102);
                access$2000.f50J.sendEmptyMessageDelayed(102, 1000);
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$o */
    public class C2188o implements IBleWriteResponse {

        /* renamed from: a */
        public final /* synthetic */ MeasureType f1254a;

        /* renamed from: b */
        public final /* synthetic */ IBleWriteResponse f1255b;

        public C2188o(MeasureType measureType, IBleWriteResponse iBleWriteResponse) {
            this.f1254a = measureType;
            this.f1255b = iBleWriteResponse;
        }

        public void onWriteFailed() {
            LogUtils.m378d("BleManager", this.f1254a.name() + " stop measure failed");
            IBleWriteResponse iBleWriteResponse = this.f1255b;
            if (iBleWriteResponse != null) {
                iBleWriteResponse.onWriteFailed();
            }
        }

        public void onWriteSuccess() {
            int i = 0;
            boolean unused = BleManager.this.isMeasuring = false;
            MeasureType unused2 = BleManager.this.mCurrentMeasureType = null;
            Log.e("BleManager", this.f1254a.name() + " stop measure success");
            MeasureType measureType = this.f1254a;
            if (measureType == MeasureType.TYPE_ECG) {
                C0011d access$2000 = BleManager.this.handleVisionData;
                boolean z = !access$2000.f49I ? access$2000.f66m >= 6000 : access$2000.f66m > 15000;
                if (access$2000.f69p != null && z) {
                    access$2000.f41A.calHrv();
                    int maxRR = access$2000.f41A.getMaxRR();
                    int minRR = access$2000.f41A.getMinRR();
                    int sdnn = access$2000.f41A.getSDNN();
                    if (maxRR < minRR || minRR < 0) {
                        minRR = 0;
                        sdnn = 0;
                    } else {
                        i = maxRR;
                    }
                    access$2000.f69p.onEcgResult(i, minRR, sdnn);
                }
                access$2000.mo42b();
            } else if (measureType == MeasureType.TYPE_SPO2) {
                C0011d access$20002 = BleManager.this.handleVisionData;
                access$20002.f47G = 0;
                if (access$20002.f50J.hasMessages(102)) {
                    access$20002.f50J.removeMessages(102);
                }
            }
            IBleWriteResponse iBleWriteResponse = this.f1255b;
            if (iBleWriteResponse != null) {
                iBleWriteResponse.onWriteSuccess();
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$p */
    public class C2189p implements IBleReadResponse {

        /* renamed from: a */
        public final /* synthetic */ IDeviceBatteryCallback f1257a;

        public C2189p(BleManager bleManager, IDeviceBatteryCallback iDeviceBatteryCallback) {
            this.f1257a = iDeviceBatteryCallback;
        }

        public void onReadFailed() {
            LogUtils.m378d("BleManager", "onReadFailed: getDeviceBattery");
        }

        public void onReadSuccess(byte[] bArr) {
            LogUtils.m378d("BleManager", "onReadSuccess: Battery = " + bArr[0]);
            IDeviceBatteryCallback iDeviceBatteryCallback = this.f1257a;
            if (iDeviceBatteryCallback != null) {
                iDeviceBatteryCallback.onDeviceBattery(bArr[0]);
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$q */
    public class C2190q implements IBleReadResponse {

        /* renamed from: a */
        public final /* synthetic */ IDeviceVersionCallback f1258a;

        public C2190q(BleManager bleManager, IDeviceVersionCallback iDeviceVersionCallback) {
            this.f1258a = iDeviceVersionCallback;
        }

        public void onReadFailed() {
            LogUtils.m378d("BleManager", "onReadFailed: getDeviceVisionInfo");
        }

        public void onReadSuccess(byte[] bArr) {
            LogUtils.m378d("BleManager", "onReadSuccess: DeviceVision = " + new String(bArr));
            IDeviceVersionCallback iDeviceVersionCallback = this.f1258a;
            if (iDeviceVersionCallback != null) {
                iDeviceVersionCallback.onDeviceVersionInfo(new String(bArr));
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$r */
    public class C2191r implements IBleReadResponse {

        /* renamed from: a */
        public final /* synthetic */ ISerialNumberCallback f1259a;

        public C2191r(BleManager bleManager, ISerialNumberCallback iSerialNumberCallback) {
            this.f1259a = iSerialNumberCallback;
        }

        public void onReadFailed() {
            LogUtils.m378d("BleManager", "onReadFailed: getDeviceVisionInfo");
        }

        public void onReadSuccess(byte[] bArr) {
            LogUtils.m378d("BleManager", "onReadSuccess: DeviceVision = " + new String(bArr));
            ISerialNumberCallback iSerialNumberCallback = this.f1259a;
            if (iSerialNumberCallback != null) {
                iSerialNumberCallback.onSerialNumber(new String(bArr));
            }
        }
    }

    /* renamed from: com.mintti.visionsdk.ble.BleManager$s */
    public static class C2192s {

        /* renamed from: a */
        public static final BleManager f1260a = new BleManager((C2183j) null);
    }

    private BleManager() {
        this.connectionListenerList = new ArrayList();
        this.isMeasuring = false;
        this.mCurrentMeasureType = null;
        this.deviceType = null;
        this.isActiveDisconnect = false;
        this.isLinkTopConnecting = false;
        this.curBleMac = "";
        this.linktopFirmwate = "";
        this.isLowCost = false;
        this.bleConnectionListener = new C2184k();
        this.linkTopBleConnectListener = new C2185l();
        this.minRRI = 0;
        this.maxRRI = 0;
        this.hrv = 0;
        this.onLinkTopEcgResultListener = new C2175b();
        this.onTestPaperResultListener = new C2176c();
        this.onLinkTopBtResultListener = new C2177d();
        this.onLinktopSpO2ResultListener = new C2178e();
        this.onLinktopBpResultListener = new C2179f();
        this.onLinktopBpDataListener = new C2180g();
        this.onLinktopBatteryListner = new C2181h();
        this.linkTopVersionListener = new OnDeviceVersionListener() {
            public final void onDeviceVersion(int i, String str) {
                BleManager.this.lambda$new$0$BleManager(i, str);
            }
        };
        this.onLinktopDeviceInfoListener = new C2182i(this);
        this.handleVisionData = new C0011d();
    }

    public /* synthetic */ BleManager(C2183j jVar) {
        this();
    }

    public static BleManager getInstance() {
        return C2192s.f1260a;
    }

    /* access modifiers changed from: private */
    public void indicateBt(IBleNotifyResponse iBleNotifyResponse) {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.btCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo15a(bluetoothGattCharacteristic, iBleNotifyResponse);
        }
    }

    private void initLinktopBleManager(Context context) {
        MonitorDataTransmissionManager.getInstance().bind(DeviceType.HealthMonitor, context, new C2183j());
    }

    /* access modifiers changed from: private */
    public void notifyBG(IBleNotifyResponse iBleNotifyResponse) {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.bgCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo23d(bluetoothGattCharacteristic, iBleNotifyResponse);
        }
    }

    /* access modifiers changed from: private */
    public void notifyBattery() {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.batteryCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo23d(bluetoothGattCharacteristic, this.handleVisionData.f53M);
        }
    }

    /* access modifiers changed from: private */
    public void notifyBp(IBleNotifyResponse iBleNotifyResponse) {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.bpCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo23d(bluetoothGattCharacteristic, iBleNotifyResponse);
        }
    }

    /* access modifiers changed from: private */
    public void notifyEcg(IBleNotifyResponse iBleNotifyResponse) {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.ecgCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo23d(bluetoothGattCharacteristic, iBleNotifyResponse);
        }
    }

    /* access modifiers changed from: private */
    public void notifySpo2(IBleNotifyResponse iBleNotifyResponse) {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.spo2Characteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo23d(bluetoothGattCharacteristic, iBleNotifyResponse);
        }
    }

    private synchronized void startMeasureLinktop(MeasureType measureType) {
        int ordinal = measureType.ordinal();
        if (ordinal == 0) {
            this.maxRRI = 0;
            this.minRRI = 0;
            this.hrv = 0;
            MonitorDataTransmissionManager.getInstance().setOnEcgResultListener(this.onLinkTopEcgResultListener);
            MonitorDataTransmissionManager.getInstance().startMeasure(5, new Pair[0]);
            this.handleVisionData.mo38a();
        } else if (ordinal == 1) {
            MonitorDataTransmissionManager.getInstance().setOnTestPaperResultListener(3, this.onTestPaperResultListener);
            MonitorDataTransmissionManager.getInstance().startMeasure(3, new Pair[0]);
        } else if (ordinal == 2) {
            MonitorDataTransmissionManager.getInstance().setOnSpO2ResultListener(this.onLinktopSpO2ResultListener);
            MonitorDataTransmissionManager.getInstance().startMeasure(4, new Pair[0]);
        } else if (ordinal == 3) {
            MonitorDataTransmissionManager.getInstance().setOnBtResultListener(this.onLinkTopBtResultListener);
            MonitorDataTransmissionManager.getInstance().startMeasure(2, new Pair[0]);
        } else if (ordinal == 4) {
            MonitorDataTransmissionManager.getInstance().setOnBpResultListener(this.onLinktopBpResultListener);
            MonitorDataTransmissionManager.getInstance().setOnBpDataListener(this.onLinktopBpDataListener);
            MonitorDataTransmissionManager.getInstance().startMeasure(1, new Pair[0]);
        }
    }

    private synchronized void startMeasureMinttiVision(MeasureType measureType, IBleWriteResponse iBleWriteResponse) {
        int i = 0;
        if (!isConnected()) {
            setMeasuring(false);
            return;
        }
        if (this.isMeasuring && this.mCurrentMeasureType != null) {
            LogUtils.m378d("BleManager", this.mCurrentMeasureType.name() + " isMeasuring ,stop!!!");
            stopMeasure(this.mCurrentMeasureType, (IBleWriteResponse) null);
        }
        LogUtils.m378d("BleManager", "startMeasure: " + measureType.name());
        int ordinal = measureType.ordinal();
        if (ordinal == 0) {
            C0011d dVar = this.handleVisionData;
            dVar.getClass();
            EcgAlgo ecgAlgo = new EcgAlgo(getInstance().getSampleRate());
            dVar.f41A = ecgAlgo;
            ecgAlgo.setISmctAlgoCallback(dVar);
            dVar.f41A.algoStart();
            AlgoHelper.initFiltFiltWrapper();
            dVar.f42B = 0;
            dVar.f66m = 0;
            i = 4;
        } else if (ordinal == 1) {
            i = 7;
            this.handleVisionData.f75v = new C2186m(measureType, iBleWriteResponse);
        } else if (ordinal == 2) {
            i = 3;
        } else if (ordinal == 3) {
            i = 2;
        } else if (ordinal == 4) {
            C0011d dVar2 = this.handleVisionData;
            dVar2.f58e = 400;
            dVar2.f59f = false;
            dVar2.f60g = 0;
            dVar2.f56c.clear();
            dVar2.f57d.clear();
            dVar2.f55b.clear();
            dVar2.f43C = System.currentTimeMillis();
            i = 1;
        }
        writeOrder(i, (IBleWriteResponse) new C2187n(measureType, iBleWriteResponse, i));
    }

    private synchronized void stopMeasureLinkTop(MeasureType measureType) {
        MonitorDataTransmissionManager.getInstance().stopMeasure();
        if (measureType == MeasureType.TYPE_ECG) {
            this.handleVisionData.mo42b();
        }
    }

    private synchronized void stopMeasureMinttiVision(MeasureType measureType, IBleWriteResponse iBleWriteResponse) {
        writeOrder(6, (IBleWriteResponse) new C2188o(measureType, iBleWriteResponse));
    }

    private void unIndicateBt() {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.btCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo20c(bluetoothGattCharacteristic);
        }
    }

    private void unNotifyBG() {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.bgCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo22d(bluetoothGattCharacteristic);
        }
    }

    private void unNotifyBattery() {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.batteryCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo22d(bluetoothGattCharacteristic);
        }
    }

    private void unNotifyBp() {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.bpCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo22d(bluetoothGattCharacteristic);
        }
    }

    private void unNotifyEcg() {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.ecgCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo22d(bluetoothGattCharacteristic);
        }
    }

    private void unNotifySpo2() {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.spo2Characteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo22d(bluetoothGattCharacteristic);
        }
    }

    public final void addConnectionListener(IBleConnectionListener iBleConnectionListener) {
        if (!this.connectionListenerList.contains(iBleConnectionListener)) {
            this.connectionListenerList.add(iBleConnectionListener);
        }
    }

    public final void connect(BleDevice bleDevice) {
        this.curBleMac = bleDevice.getMac();
        if (bleDevice.getName().contains("Mintti-Vision")) {
            this.deviceType = DeviceType.TYPE_MINTTI_VISION;
            C0000a aVar = this.bleConnection;
            if (aVar != null) {
                aVar.f10k = bleDevice.getBluetoothDevice();
                C0000a aVar2 = this.bleConnection;
                aVar2.f6g = this.bleConnectionListener;
                aVar2.mo18b();
                return;
            }
            return;
        }
        this.deviceType = DeviceType.TYPE_VISION;
        MonitorDataTransmissionManager.getInstance().setOnBleConnectListener(this.linkTopBleConnectListener);
        MonitorDataTransmissionManager.getInstance().connectToBle(bleDevice.getBluetoothDevice());
    }

    public final void disconnect() {
        if (this.deviceType == DeviceType.TYPE_MINTTI_VISION) {
            C0000a aVar = this.bleConnection;
            if (aVar != null) {
                aVar.mo19c();
                return;
            }
            return;
        }
        this.isActiveDisconnect = true;
        MonitorDataTransmissionManager.getInstance().disConnectBle();
    }

    public final BluetoothAdapter getBluetoothAdapter() {
        return this.bluetoothAdapter;
    }

    public BluetoothDevice getBluetoothDevice() {
        if (this.deviceType != DeviceType.TYPE_MINTTI_VISION) {
            return MonitorDataTransmissionManager.getInstance().getBluetoothDevice();
        }
        C0000a aVar = this.bleConnection;
        if (aVar != null) {
            return aVar.f5f.getDevice();
        }
        return null;
    }

    public final BluetoothLeScanner getBluetoothLeScanner() {
        return this.bluetoothLeScanner;
    }

    public void getDeviceBattery(IDeviceBatteryCallback iDeviceBatteryCallback2) {
        C0000a aVar;
        if (this.deviceType == DeviceType.TYPE_MINTTI_VISION) {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = this.batteryCharacteristic;
            if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
                aVar.mo16a(bluetoothGattCharacteristic, (IBleReadResponse) new C2189p(this, iDeviceBatteryCallback2));
            }
        } else if (iDeviceBatteryCallback2 != null) {
            iDeviceBatteryCallback2.onDeviceBattery(MonitorDataTransmissionManager.getInstance().getBatteryValue());
        }
    }

    public void getDeviceManufacturer(IManufacturerInfoCallback iManufacturerInfoCallback) {
        C0000a aVar;
        if (this.deviceType == DeviceType.TYPE_MINTTI_VISION) {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = this.manufacturerCharacteristic;
            if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
                aVar.mo16a(bluetoothGattCharacteristic, (IBleReadResponse) new C2174a(this, iManufacturerInfoCallback));
            }
        } else if (iManufacturerInfoCallback != null) {
            iManufacturerInfoCallback.onManufacturerInfo("MinttiHealth");
        }
    }

    public DeviceType getDeviceType() {
        return this.deviceType;
    }

    public void getDeviceVersionInfo(IDeviceVersionCallback iDeviceVersionCallback2) {
        C0000a aVar;
        if (this.deviceType == DeviceType.TYPE_MINTTI_VISION) {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = this.deviceVersionCharacteristic;
            if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
                aVar.mo16a(bluetoothGattCharacteristic, (IBleReadResponse) new C2190q(this, iDeviceVersionCallback2));
            }
        } else if (iDeviceVersionCallback2 != null) {
            iDeviceVersionCallback2.onDeviceVersionInfo(this.linktopFirmwate);
        }
    }

    public int getSampleRate() {
        return (this.deviceType != DeviceType.TYPE_VISION && !this.isLowCost) ? 200 : 512;
    }

    public void getSerialNumber(ISerialNumberCallback iSerialNumberCallback) {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.serialNumberCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo16a(bluetoothGattCharacteristic, (IBleReadResponse) new C2191r(this, iSerialNumberCallback));
        }
    }

    public String[] getTestPaperCodesByManufacturer(String str) {
        return TestPaper.Code.values(str, 3);
    }

    public String[] getTestPaperManufacturer() {
        return TestPaper.Manufacturer.values();
    }

    public final void init(Context context, IBleInitCallback iBleInitCallback) {
        this.mContext = context;
        this.bleInitCallback = iBleInitCallback;
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService("bluetooth");
        if (bluetoothManager != null) {
            BluetoothAdapter adapter = bluetoothManager.getAdapter();
            this.bluetoothAdapter = adapter;
            if (adapter != null) {
                this.bluetoothLeScanner = adapter.getBluetoothLeScanner();
                this.bleConnection = new C0000a(context);
                int i = C0005c.f21d;
                C0005c.C0006a.f25a.f24c = this.bluetoothLeScanner;
                initLinktopBleManager(context);
                return;
            } else if (iBleInitCallback == null) {
                return;
            }
        } else if (iBleInitCallback == null) {
            return;
        }
        iBleInitCallback.onInitFailed();
    }

    public final boolean isBluetoothEnable() {
        BluetoothAdapter bluetoothAdapter2 = this.bluetoothAdapter;
        return bluetoothAdapter2 != null && bluetoothAdapter2.isEnabled();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0014, code lost:
        r0 = r3.bleConnection;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isConnected() {
        /*
            r3 = this;
            com.mintti.visionsdk.ble.DeviceType r0 = r3.deviceType
            com.mintti.visionsdk.ble.DeviceType r1 = com.mintti.visionsdk.ble.DeviceType.TYPE_VISION
            if (r0 != r1) goto L_0x000f
            com.linktop.MonitorDataTransmissionManager r0 = com.linktop.MonitorDataTransmissionManager.getInstance()
            boolean r0 = r0.isConnected()
            return r0
        L_0x000f:
            com.mintti.visionsdk.ble.DeviceType r1 = com.mintti.visionsdk.ble.DeviceType.TYPE_MINTTI_VISION
            r2 = 0
            if (r0 != r1) goto L_0x001d
            a.a.a.a.a r0 = r3.bleConnection
            if (r0 == 0) goto L_0x001d
            boolean r0 = r0.f1b
            if (r0 == 0) goto L_0x001d
            r2 = 1
        L_0x001d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mintti.visionsdk.ble.BleManager.isConnected():boolean");
    }

    public boolean isEcgModuleExist() {
        DeviceType deviceType2 = DeviceType.TYPE_VISION;
        DeviceType deviceType3 = this.deviceType;
        return deviceType2 == deviceType3 ? MonitorDataTransmissionManager.getInstance().isEcgModuleExist() : DeviceType.TYPE_MINTTI_VISION == deviceType3;
    }

    public boolean isHaveSerialNumber() {
        return this.noSerialNumberCharacteristic == null;
    }

    public boolean isMeasuring() {
        return this.deviceType == DeviceType.TYPE_VISION ? MonitorDataTransmissionManager.getInstance().isMeasuring() : this.isMeasuring;
    }

    public final boolean isSupportBle() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
    }

    public final boolean isSupportBluetooth() {
        return this.bluetoothAdapter != null;
    }

    public /* synthetic */ void lambda$new$0$BleManager(int i, String str) {
        LogUtils.m378d("BleManager", "wareType = " + i + " version = " + str);
        if (i == 0) {
            this.linktopFirmwate = str;
            IDeviceVersionCallback iDeviceVersionCallback2 = this.iDeviceVersionCallback;
            if (iDeviceVersionCallback2 != null) {
                iDeviceVersionCallback2.onDeviceVersionInfo(str);
            }
        }
    }

    public final void openBluetooth(AppCompatActivity appCompatActivity, int i) {
        appCompatActivity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), i);
    }

    public final void openBluetooth(Fragment fragment, int i) {
        fragment.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), i);
    }

    public final void openLocationSetting(AppCompatActivity appCompatActivity, int i) {
        appCompatActivity.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), i);
    }

    public final void openLocationSetting(Fragment fragment, int i) {
        fragment.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), i);
    }

    public void release() {
        MonitorDataTransmissionManager.getInstance().unBind();
        this.mContext = null;
    }

    public final void removeConnectionListener(IBleConnectionListener iBleConnectionListener) {
        if (this.connectionListenerList.contains(iBleConnectionListener)) {
            this.connectionListenerList.remove(iBleConnectionListener);
        }
    }

    public void setBatteryListener(IDeviceBatteryCallback iDeviceBatteryCallback2) {
        this.iDeviceBatteryCallback = iDeviceBatteryCallback2;
        this.handleVisionData.f74u = iDeviceBatteryCallback2;
    }

    public void setBgResultListener(IBgResultListener iBgResultListener2) {
        this.iBgResultListener = iBgResultListener2;
        this.handleVisionData.f73t = iBgResultListener2;
    }

    public void setBpResultListener(IBpResultListener iBpResultListener2) {
        this.iBpResultListener = iBpResultListener2;
        this.handleVisionData.f71r = iBpResultListener2;
    }

    public void setBtResultListener(IBtResultListener iBtResultListener2) {
        this.iBtResultListener = iBtResultListener2;
        this.handleVisionData.f70q = iBtResultListener2;
    }

    public void setDeviceVersionCallback(IDeviceVersionCallback iDeviceVersionCallback2) {
        this.iDeviceVersionCallback = iDeviceVersionCallback2;
    }

    public void setEcgFingerDetectionListener(IEcgFingerDetectionListener iEcgFingerDetectionListener) {
        this.ecgFingerDetectionListener = iEcgFingerDetectionListener;
    }

    public void setEcgResultListener(IEcgResultListener iEcgResultListener2) {
        this.iEcgResultListener = iEcgResultListener2;
        this.handleVisionData.f69p = iEcgResultListener2;
    }

    public void setMeasuring(boolean z) {
        this.isMeasuring = z;
    }

    public void setRawBgDataCallback(IRawBgDataCallback iRawBgDataCallback) {
        this.handleVisionData.f77x = iRawBgDataCallback;
    }

    public void setRawBpDataCallback(IRawBpDataCallback iRawBpDataCallback2) {
        this.iRawBpDataCallback = iRawBpDataCallback2;
        this.handleVisionData.f76w = iRawBpDataCallback2;
    }

    public void setRawBtDataCallback(IRawBtDataCallback iRawBtDataCallback) {
        this.handleVisionData.f78y = iRawBtDataCallback;
    }

    public void setRawSpo2DataCallback(IRawSpo2DataCallback iRawSpo2DataCallback) {
        this.handleVisionData.f79z = iRawSpo2DataCallback;
    }

    public void setSpo2ResultListener(ISpo2ResultListener iSpo2ResultListener2) {
        this.iSpo2ResultListener = iSpo2ResultListener2;
        this.handleVisionData.f72s = iSpo2ResultListener2;
    }

    public void setTestPaper(String str, String str2) {
        MonitorDataTransmissionManager.getInstance().setTestPaper(3, TestPaper.create(str, str2));
    }

    public synchronized void startMeasure(MeasureType measureType, IBleWriteResponse iBleWriteResponse) {
        DeviceType deviceType2 = this.deviceType;
        if (deviceType2 == DeviceType.TYPE_MINTTI_VISION) {
            setMeasuring(true);
            startMeasureMinttiVision(measureType, iBleWriteResponse);
        } else if (deviceType2 == DeviceType.TYPE_VISION) {
            startMeasureLinktop(measureType);
        }
    }

    public final void startScan(IBleScanCallback iBleScanCallback) {
        String str;
        BluetoothAdapter bluetoothAdapter2;
        if (this.bluetoothLeScanner == null && (bluetoothAdapter2 = this.bluetoothAdapter) != null) {
            BluetoothLeScanner bluetoothLeScanner2 = bluetoothAdapter2.getBluetoothLeScanner();
            this.bluetoothLeScanner = bluetoothLeScanner2;
            int i = C0005c.f21d;
            C0005c.C0006a.f25a.f24c = bluetoothLeScanner2;
        }
        if (this.bluetoothLeScanner != null) {
            int i2 = C0005c.f21d;
            C0005c cVar = C0005c.C0006a.f25a;
            if (cVar.f22a) {
                str = "Already scanning";
            } else {
                cVar.f23b = iBleScanCallback;
                BluetoothLeScanner bluetoothLeScanner3 = cVar.f24c;
                if (bluetoothLeScanner3 != null) {
                    bluetoothLeScanner3.startScan(cVar);
                    cVar.f22a = true;
                    str = "startScan";
                } else {
                    throw new RuntimeException("bluetoothLeScanner is null");
                }
            }
            LogUtils.m378d("BleScanner", str);
        }
    }

    public synchronized void stopMeasure(MeasureType measureType, IBleWriteResponse iBleWriteResponse) {
        DeviceType deviceType2 = this.deviceType;
        if (deviceType2 == DeviceType.TYPE_MINTTI_VISION) {
            stopMeasureMinttiVision(measureType, iBleWriteResponse);
        } else if (deviceType2 == DeviceType.TYPE_VISION) {
            stopMeasureLinkTop(measureType);
        }
    }

    public final void stopScan() {
        BluetoothAdapter bluetoothAdapter2;
        if (this.bluetoothLeScanner == null && (bluetoothAdapter2 = this.bluetoothAdapter) != null) {
            BluetoothLeScanner bluetoothLeScanner2 = bluetoothAdapter2.getBluetoothLeScanner();
            this.bluetoothLeScanner = bluetoothLeScanner2;
            int i = C0005c.f21d;
            C0005c.C0006a.f25a.f24c = bluetoothLeScanner2;
        }
        if (this.bluetoothLeScanner != null && isBluetoothEnable()) {
            int i2 = C0005c.f21d;
            C0005c cVar = C0005c.C0006a.f25a;
            cVar.f22a = false;
            cVar.f23b = null;
            BluetoothLeScanner bluetoothLeScanner3 = cVar.f24c;
            if (bluetoothLeScanner3 != null) {
                bluetoothLeScanner3.stopScan(cVar);
            }
            LogUtils.m378d("BleScanner", "stopScan");
        }
    }

    public void writeOrder(int i, IBleWriteResponse iBleWriteResponse) {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.transferCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo14a(bluetoothGattCharacteristic, i, iBleWriteResponse);
        }
    }

    public void writeOrder(byte[] bArr, IBleWriteResponse iBleWriteResponse) {
        C0000a aVar;
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.transferCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo17a(bluetoothGattCharacteristic, bArr, iBleWriteResponse);
        }
    }

    public void writeSerialNumber(String str, IBleWriteResponse iBleWriteResponse) {
        C0000a aVar;
        byte[] bytes = str.getBytes();
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.noSerialNumberCharacteristic;
        if (bluetoothGattCharacteristic != null && (aVar = this.bleConnection) != null) {
            aVar.mo17a(bluetoothGattCharacteristic, bytes, iBleWriteResponse);
        }
    }

    public final void connect(String str) {
        this.curBleMac = str;
        BluetoothDevice remoteDevice = this.bluetoothAdapter.getRemoteDevice(str);
        if (remoteDevice.getName().contains("Mintti-Vision")) {
            this.deviceType = DeviceType.TYPE_MINTTI_VISION;
            C0000a aVar = this.bleConnection;
            if (aVar != null) {
                aVar.f10k = remoteDevice;
                aVar.f6g = this.bleConnectionListener;
                aVar.mo18b();
                return;
            }
            return;
        }
        this.deviceType = DeviceType.TYPE_VISION;
        MonitorDataTransmissionManager.getInstance().setOnBleConnectListener(this.linkTopBleConnectListener);
        MonitorDataTransmissionManager.getInstance().connectToBle(remoteDevice);
    }
}
