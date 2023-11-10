package com.p020kl.commonbase.service;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.mintti.visionsdk.ble.BleDevice;
import com.mintti.visionsdk.ble.BleManager;
import com.mintti.visionsdk.ble.DeviceType;
import com.mintti.visionsdk.ble.bean.MeasureType;
import com.mintti.visionsdk.ble.callback.IBleConnectionListener;
import com.mintti.visionsdk.ble.callback.IBleScanCallback;
import com.mintti.visionsdk.ble.callback.IBleWriteResponse;
import com.mintti.visionsdk.ble.callback.IDeviceBatteryCallback;
import com.mintti.visionsdk.ble.callback.IDeviceVersionCallback;
import com.p020kl.commonbase.data.SpManager;
import com.p020kl.commonbase.event.BatteryEvent;
import com.p020kl.commonbase.event.BleStatusEvent;
import com.p020kl.commonbase.utils.EventBusUtil;

/* renamed from: com.kl.commonbase.service.HealthMonitorService */
public class HealthMonitorService {
    private static final String TAG = "HealthMonitorService";
    private final IBleConnectionListener bleConnectionListener;
    /* access modifiers changed from: private */
    public final IDeviceBatteryCallback deviceBatteryCallback;
    private final IDeviceVersionCallback deviceVersionCallback;
    /* access modifiers changed from: private */
    public boolean isDfu;
    private boolean isUnbind;
    /* access modifiers changed from: private */
    public final Handler mHandler;

    public void release() {
    }

    public DeviceType getDeviceType() {
        return BleManager.getInstance().getDeviceType();
    }

    private HealthMonitorService() {
        this.mHandler = new Handler(Looper.getMainLooper());
        this.isDfu = false;
        this.isUnbind = false;
        C15991 r0 = new IBleConnectionListener() {
            public void onConnectSuccess(String str) {
                boolean unused = HealthMonitorService.this.isDfu = false;
                EventBusUtil.sendEvent(new BleStatusEvent(103));
                HealthMonitorService.this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        if (BleManager.getInstance().isConnected()) {
                            BleManager.getInstance().getDeviceBattery(HealthMonitorService.this.deviceBatteryCallback);
                        }
                    }
                }, 30000);
                HealthMonitorService.this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        if (BleManager.getInstance().isConnected()) {
                            BleManager.getInstance().getDeviceBattery(HealthMonitorService.this.deviceBatteryCallback);
                        }
                    }
                }, 60000);
            }

            public void onConnectFailed(String str) {
                EventBusUtil.sendEvent(new BleStatusEvent(107));
                HealthMonitorService.this.mHandler.removeCallbacksAndMessages((Object) null);
            }

            public void onDisconnected(String str, boolean z) {
                if (!z && !HealthMonitorService.this.isDfu) {
                    EventBusUtil.sendEvent(new BleStatusEvent(101));
                }
                HealthMonitorService.this.mHandler.removeCallbacksAndMessages((Object) null);
            }
        };
        this.bleConnectionListener = r0;
        C16022 r1 = new IDeviceVersionCallback() {
            public void onDeviceVersionInfo(String str) {
                Log.e(HealthMonitorService.TAG, "onDeviceVersionInfo: " + str);
                SpManager.setFirmwareVersion(str);
            }
        };
        this.deviceVersionCallback = r1;
        C16033 r2 = new IDeviceBatteryCallback() {
            public void onDeviceBattery(int i) {
                EventBusUtil.sendEvent(new BatteryEvent(false, i, i == 100));
            }
        };
        this.deviceBatteryCallback = r2;
        BleManager.getInstance().addConnectionListener(r0);
        BleManager.getInstance().setBatteryListener(r2);
        BleManager.getInstance().setDeviceVersionCallback(r1);
    }

    public static HealthMonitorService getInstance() {
        return HealthMonitorServiceHolder.healthMonitorService;
    }

    public void setDfu(boolean z) {
        this.isDfu = z;
    }

    public void setUnbind(boolean z) {
        this.isUnbind = z;
    }

    /* renamed from: com.kl.commonbase.service.HealthMonitorService$HealthMonitorServiceHolder */
    private static class HealthMonitorServiceHolder {
        /* access modifiers changed from: private */
        public static HealthMonitorService healthMonitorService = new HealthMonitorService();

        private HealthMonitorServiceHolder() {
        }
    }

    public void scan(boolean z, IBleScanCallback iBleScanCallback) {
        Log.e(TAG, "scan: " + z);
        if (z) {
            BleManager.getInstance().startScan(iBleScanCallback);
        } else {
            BleManager.getInstance().stopScan();
        }
    }

    public void connect(BleDevice bleDevice) {
        BleManager.getInstance().connect(bleDevice);
    }

    public boolean isConnected() {
        return BleManager.getInstance().isConnected();
    }

    public void disconnect(DeviceType deviceType) {
        BleManager.getInstance().disconnect();
    }

    public boolean isMeasuring() {
        return BleManager.getInstance().isMeasuring();
    }

    public void startMeasure(MeasureType measureType, IBleWriteResponse iBleWriteResponse) {
        BleManager.getInstance().startMeasure(measureType, iBleWriteResponse);
    }

    public void stopMeasure(MeasureType measureType, IBleWriteResponse iBleWriteResponse) {
        BleManager.getInstance().stopMeasure(measureType, iBleWriteResponse);
    }

    public boolean isEcgModuleExist() {
        return BleManager.getInstance().isEcgModuleExist();
    }

    public String[] getTestPaperManufacturer() {
        return BleManager.getInstance().getTestPaperManufacturer();
    }

    public String[] getTestPaperCodesByManufacturer(String str) {
        return BleManager.getInstance().getTestPaperCodesByManufacturer(str);
    }

    public void setTestPaper(String str, String str2) {
        BleManager.getInstance().setTestPaper(str, str2);
    }
}
