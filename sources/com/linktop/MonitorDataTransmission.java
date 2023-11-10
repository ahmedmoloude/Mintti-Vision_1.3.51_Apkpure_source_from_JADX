package com.linktop;

import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.linktop.constant.IUserProfile;
import com.linktop.constant.TestPaper;
import com.linktop.infs.OnBatteryListener;
import com.linktop.infs.OnBleConnectListener;
import com.linktop.infs.OnBpDataListener;
import com.linktop.infs.OnBpResultListener;
import com.linktop.infs.OnBtResultListener;
import com.linktop.infs.OnDeviceInfoListener;
import com.linktop.infs.OnDeviceVersionListener;
import com.linktop.infs.OnEcgResultListener;
import com.linktop.infs.OnHRVResultListener;
import com.linktop.infs.OnScanTempListener;
import com.linktop.infs.OnSpO2ResultListener;
import com.linktop.infs.OnThermInfoListener;
import com.linktop.utils.BleDevLog;
import com.linktop.whealthService.OnBLEService;
import com.linktop.whealthService.util.IBleDev;
import java.util.List;

public class MonitorDataTransmission implements ServiceConnection, OnBLEService.OnSDKThrowableCallback {

    /* renamed from: g */
    private static final String f945g = "com.linktop.MonitorDataTransmission";

    /* renamed from: a */
    public Context f946a;

    /* renamed from: b */
    private OnServiceBindListener f947b;

    /* renamed from: c */
    private OnBLEService f948c;

    /* renamed from: d */
    public int f949d;

    /* renamed from: e */
    public String f950e;

    /* renamed from: f */
    public IBleDev f951f;

    public final class IllegalDeviceTypeException extends IllegalStateException {
        public IllegalDeviceTypeException(String str) {
            super("need DeviceType is '" + str + "' can use this method,current is '" + MonitorDataTransmission.this.f950e + "'");
        }
    }

    public interface OnServiceBindListener {
        void onSDKThrowable(Throwable th);

        void onServiceBind();

        void onServiceUnbind();
    }

    /* renamed from: a */
    private boolean m122a() {
        boolean z = this.f948c != null;
        if (!z) {
            BleDevLog.m139a("'mOnBLEService' is null.");
        }
        return z;
    }

    /* renamed from: a */
    public boolean mo27292a(String str) {
        boolean z = this.f951f != null;
        if (!z) {
            String str2 = f945g;
            BleDevLog.m140a(str2, str + " - 'mIBleDev' is null.");
        }
        return z;
    }

    public void autoScan(boolean z) {
        if (m122a()) {
            this.f948c.mo27448a(z);
        }
    }

    public void bind(String str, Context context, OnServiceBindListener onServiceBindListener) {
        this.f950e = str;
        this.f946a = context;
        this.f947b = onServiceBindListener;
        this.f946a.bindService(new Intent(this.f946a, OnBLEService.class), this, 1);
    }

    public void bleCheckOpen() {
        if (m122a()) {
            this.f948c.mo27451b();
        }
    }

    public void connectToBle(BluetoothDevice bluetoothDevice) {
        if (m122a()) {
            this.f948c.mo27442a(bluetoothDevice);
        }
    }

    public void disConnectBle() {
        if (m122a()) {
            BleDevLog.m141b("MonitorDataTransmissionManager", "disConnectBle");
            this.f948c.mo27454f();
        }
    }

    public int getBatteryValue() {
        if (DeviceType.HealthMonitor.equals(this.f950e)) {
            return this.f948c.getBatteryTask().getPower();
        }
        throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
    }

    public int getBleState() {
        if (m122a()) {
            return this.f948c.mo27455h();
        }
        return -1;
    }

    public BluetoothDevice getBluetoothDevice() {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (m122a()) {
            return this.f948c.mo27456i();
        } else {
            return null;
        }
    }

    public void getDevInfo(OnThermInfoListener onThermInfoListener) {
        if (!DeviceType.Thermometer.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.Thermometer);
        } else if (m122a()) {
            this.f948c.f989B.mo27575a(onThermInfoListener);
        }
    }

    public List<OnBLEService.DeviceSort> getDeviceList() {
        if (m122a()) {
            return this.f948c.f1017p;
        }
        return null;
    }

    public void getTestPaperCalibration() {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (m122a()) {
            this.f948c.getTestPaperTask().mo27562f();
        }
    }

    public IUserProfile getUserProfile() {
        if (mo27292a("getUserProfile")) {
            return this.f951f.getUserProfile();
        }
        return null;
    }

    public boolean isCharging() {
        if (DeviceType.HealthMonitor.equals(this.f950e)) {
            return this.f948c.getBatteryTask().isCharging();
        }
        throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
    }

    public boolean isConnected() {
        return getBleState() >= 103;
    }

    public boolean isEcgModuleExist() {
        if (DeviceType.HealthMonitor.equals(this.f950e)) {
            return mo27292a("isEcgModuleExist") && this.f951f.getEcgTask() != null && this.f951f.getEcgTask().isModuleExist();
        }
        throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
    }

    public boolean isMeasureWrist() {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("isMeasureArm")) {
            return this.f951f.getBpTask().mo27497d();
        } else {
            BleDevLog.m142c(f945g, "Call isMeasureArm failed.");
            return false;
        }
    }

    public boolean isMeasuring() {
        if (DeviceType.HealthMonitor.equals(this.f950e)) {
            return this.f948c.isMeasuring();
        }
        throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
    }

    public boolean isScanning() {
        return m122a() && this.f948c.f1011j;
    }

    public boolean isTestPaperModuleDoubleADC() {
        if (DeviceType.HealthMonitor.equals(this.f950e)) {
            return mo27292a("isBsModuleExist") && this.f951f.getTestPaperTask() != null && this.f951f.getTestPaperTask().isDoubleADC();
        }
        throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
    }

    public boolean isTestPaperModuleExist() {
        if (DeviceType.HealthMonitor.equals(this.f950e)) {
            return mo27292a("isBsModuleExist") && this.f951f.getTestPaperTask() != null && this.f951f.getTestPaperTask().isModuleExist();
        }
        throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
    }

    public void onSDKThrowable(Throwable th) {
        OnServiceBindListener onServiceBindListener = this.f947b;
        if (onServiceBindListener != null) {
            onServiceBindListener.onSDKThrowable(th);
        }
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        OnBLEService a = ((OnBLEService.LocalBinder) iBinder).mo27480a();
        this.f948c = a;
        a.mo27447a(this.f950e, (OnBLEService.OnSDKThrowableCallback) this);
        this.f951f = this.f948c;
        OnServiceBindListener onServiceBindListener = this.f947b;
        if (onServiceBindListener != null) {
            onServiceBindListener.onServiceBind();
        }
        if (!this.f948c.mo27458m()) {
            BleDevLog.m139a("initBLE false");
        } else {
            this.f948c.mo27451b();
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.f948c = null;
        OnServiceBindListener onServiceBindListener = this.f947b;
        if (onServiceBindListener != null) {
            onServiceBindListener.onServiceUnbind();
        }
    }

    public void pressureFromData(boolean z, int i) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("pressureFromData")) {
            this.f951f.getBpTask().mo27495a(z, i);
        } else {
            BleDevLog.m142c(f945g, "Call pressureFromData failed.");
        }
    }

    public void scan(boolean z) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            return;
        }
        if (m122a()) {
            this.f948c.mo27449a(z, false);
            return;
        }
        throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
    }

    public void setAutoScanPeriod(long j) {
        if (m122a()) {
            this.f948c.mo27441a(j);
        }
    }

    public void setBgVer() {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (m122a()) {
            this.f948c.getTestPaperTask().mo27565j();
        }
    }

    public void setMeasurePosition(boolean z) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("setMeasurePosition")) {
            this.f951f.getBpTask().mo27496b(z);
        } else {
            BleDevLog.m142c(f945g, "Call setMeasurePosition failed.");
        }
    }

    public void setOnBatteryListener(OnBatteryListener onBatteryListener) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("setOnBatteryListener")) {
            this.f951f.getBatteryTask().setBatteryStateListener(onBatteryListener);
        }
    }

    public void setOnBleConnectListener(OnBleConnectListener onBleConnectListener) {
        if (m122a()) {
            this.f948c.mo27444a(onBleConnectListener);
        } else {
            BleDevLog.m142c(f945g, "init *OnBleConnectListener* failed.");
        }
    }

    public void setOnBpDataListener(OnBpDataListener onBpDataListener) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("setOnBpDataListener")) {
            this.f951f.getBpTask().setOnBpDataListener(onBpDataListener);
        } else {
            BleDevLog.m142c(f945g, "init *OnBpDataListener* failed.");
        }
    }

    public void setOnBpResultListener(OnBpResultListener onBpResultListener) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("setOnBpResultListener")) {
            this.f951f.getBpTask().setOnBpResultListener(onBpResultListener);
        } else {
            BleDevLog.m142c(f945g, "init *OnBpResultListener* failed.");
        }
    }

    public void setOnBtResultListener(OnBtResultListener onBtResultListener) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("setOnBtResultListener")) {
            this.f951f.getBtTask().setOnBtResultListener(onBtResultListener);
        }
    }

    public void setOnDevIdAndKeyListener(OnDeviceInfoListener onDeviceInfoListener) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (m122a()) {
            this.f948c.mo27445a(onDeviceInfoListener);
        }
    }

    public void setOnDeviceVersionListener(OnDeviceVersionListener onDeviceVersionListener) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (m122a()) {
            this.f948c.mo27446a(onDeviceVersionListener);
        }
    }

    public void setOnEcgResultListener(OnEcgResultListener onEcgResultListener) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("setOnEcgResultListener")) {
            this.f951f.getEcgTask().setOnEcgResultListener(onEcgResultListener);
        } else {
            BleDevLog.m142c(f945g, "init *OnEcgResultListener* failed.");
        }
    }

    public void setOnHrvResultListener(OnHRVResultListener onHRVResultListener) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("OnHRVResultListener")) {
            this.f951f.getOxTask().setOnHrvResultListener(onHRVResultListener);
        } else {
            BleDevLog.m142c(f945g, "init *OnHRVResultListener* failed.");
        }
    }

    public void setOnSpO2ResultListener(OnSpO2ResultListener onSpO2ResultListener) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("setOnSpO2ResultListener")) {
            this.f951f.getOxTask().setOnSpO2ResultListener(onSpO2ResultListener);
        } else {
            BleDevLog.m142c(f945g, "init *setOnSpO2ResultListener* failed.");
        }
    }

    public void setScanDevNamePrefixWhiteList(int i) {
    }

    public void setTestPaper(int i, TestPaper testPaper) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (m122a()) {
            this.f948c.getTestPaperTask().setTestPaper(i, testPaper);
        }
    }

    public void setUserProfile(IUserProfile iUserProfile) {
        if (mo27292a("setUserProfile")) {
            this.f951f.setUserProfile(iUserProfile);
        }
    }

    public void startScanTemp(BluetoothDevice bluetoothDevice, OnScanTempListener onScanTempListener) {
        if (!DeviceType.Thermometer.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.Thermometer);
        } else if (m122a()) {
            this.f948c.f989B.mo27574a(bluetoothDevice, onScanTempListener);
        }
    }

    public void stopMeasure() {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("stopMeasure")) {
            int i = this.f949d;
            if (i == 1) {
                this.f951f.getBpTask().stop();
            } else if (i == 2) {
                this.f951f.getBtTask().stop();
            } else if (i == 3) {
                this.f951f.getTestPaperTask().stop();
            } else if (i == 4) {
                this.f951f.getOxTask().stop();
            } else if (i == 5) {
                this.f951f.getEcgTask().stop();
            }
        }
    }

    public void stopScanTemp() {
        if (!DeviceType.Thermometer.equals(this.f950e)) {
            throw new IllegalDeviceTypeException(DeviceType.Thermometer);
        } else if (m122a()) {
            this.f948c.f989B.mo27574a((BluetoothDevice) null, (OnScanTempListener) null);
        }
    }

    public void unBind() {
        Context context = this.f946a;
        if (context != null) {
            context.unbindService(this);
            this.f946a = null;
        }
    }
}
