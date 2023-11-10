package com.linktop;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Environment;
import com.linktop.MonitorDataTransmission;
import com.linktop.constant.IUserProfile;
import com.linktop.constant.Pair;
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
import com.linktop.infs.OnTestPaperResultListener;
import com.linktop.infs.OnThermInfoListener;
import com.linktop.utils.BleDevLog;
import com.linktop.whealthService.OnBLEService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class MonitorDataTransmissionManager extends MonitorDataTransmission {

    /* renamed from: i */
    private static final String f953i = "com.linktop.MonitorDataTransmissionManager";

    /* renamed from: j */
    private static MonitorDataTransmissionManager f954j;

    /* renamed from: h */
    private ReadBPFileThread f955h;

    public class ReadBPFileThread extends Thread {

        /* renamed from: a */
        public File f956a;

        public ReadBPFileThread(File file) {
            this.f956a = file;
        }

        public void run() {
            String readLine;
            super.run();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.f956a)));
                while (!isInterrupted() && (readLine = bufferedReader.readLine()) != null) {
                    if (readLine.startsWith("[") && readLine.endsWith("]") && readLine.contains(",")) {
                        String[] split = readLine.replace("[", "").replace("]", "").split(",");
                        int length = split.length;
                        byte[] bArr = new byte[length];
                        for (int i = 0; i < length; i++) {
                            bArr[i] = (byte) Integer.parseInt(split[i].trim());
                        }
                        MonitorDataTransmissionManager.this.f951f.getCommunicate().packageParse(bArr);
                        Thread.sleep(40);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private MonitorDataTransmissionManager() {
    }

    public static MonitorDataTransmissionManager getInstance() {
        if (f954j == null) {
            f954j = new MonitorDataTransmissionManager();
        }
        return f954j;
    }

    public static void isDebug(boolean z) {
        BleDevLog.f976a = z;
    }

    public void autoScan(boolean z) {
        super.autoScan(z);
    }

    public void bind(String str, Context context, MonitorDataTransmission.OnServiceBindListener onServiceBindListener) {
        super.bind(str, context, onServiceBindListener);
    }

    public void bleCheckOpen() {
        super.bleCheckOpen();
    }

    public void connectToBle(BluetoothDevice bluetoothDevice) {
        super.connectToBle(bluetoothDevice);
    }

    public void disConnectBle() {
        super.disConnectBle();
    }

    public int getBatteryValue() {
        return super.getBatteryValue();
    }

    public int getBleState() {
        return super.getBleState();
    }

    public BluetoothDevice getBluetoothDevice() {
        return super.getBluetoothDevice();
    }

    public void getDevInfo(OnThermInfoListener onThermInfoListener) {
        super.getDevInfo(onThermInfoListener);
    }

    public List<OnBLEService.DeviceSort> getDeviceList() {
        return super.getDeviceList();
    }

    public void getTestPaperCalibration() {
        super.getTestPaperCalibration();
    }

    public IUserProfile getUserProfile() {
        return super.getUserProfile();
    }

    public boolean isCharging() {
        return super.isCharging();
    }

    public boolean isConnected() {
        return super.isConnected();
    }

    public boolean isEcgModuleExist() {
        return super.isEcgModuleExist();
    }

    public boolean isMeasureWrist() {
        return super.isMeasureWrist();
    }

    public boolean isMeasuring() {
        return super.isMeasuring();
    }

    public boolean isScanning() {
        return super.isScanning();
    }

    public boolean isTestPaperModuleDoubleADC() {
        return super.isTestPaperModuleDoubleADC();
    }

    public boolean isTestPaperModuleExist() {
        return super.isTestPaperModuleExist();
    }

    public void pressureFromData(boolean z, int i) {
        super.pressureFromData(z, i);
    }

    public void scan(boolean z) {
        super.scan(z);
    }

    public void setAutoScanPeriod(long j) {
        super.setAutoScanPeriod(j);
    }

    public void setBgVer() {
        super.setBgVer();
    }

    public void setMeasurePosition(boolean z) {
        super.setMeasurePosition(z);
    }

    public void setOnBatteryListener(OnBatteryListener onBatteryListener) {
        super.setOnBatteryListener(onBatteryListener);
    }

    public void setOnBleConnectListener(OnBleConnectListener onBleConnectListener) {
        super.setOnBleConnectListener(onBleConnectListener);
    }

    public void setOnBpDataListener(OnBpDataListener onBpDataListener) {
        super.setOnBpDataListener(onBpDataListener);
    }

    public void setOnBpResultListener(OnBpResultListener onBpResultListener) {
        super.setOnBpResultListener(onBpResultListener);
    }

    public void setOnBtResultListener(OnBtResultListener onBtResultListener) {
        super.setOnBtResultListener(onBtResultListener);
    }

    public void setOnDevIdAndKeyListener(OnDeviceInfoListener onDeviceInfoListener) {
        super.setOnDevIdAndKeyListener(onDeviceInfoListener);
    }

    public void setOnDeviceVersionListener(OnDeviceVersionListener onDeviceVersionListener) {
        super.setOnDeviceVersionListener(onDeviceVersionListener);
    }

    public void setOnEcgResultListener(OnEcgResultListener onEcgResultListener) {
        super.setOnEcgResultListener(onEcgResultListener);
    }

    public void setOnHrvResultListener(OnHRVResultListener onHRVResultListener) {
        super.setOnHrvResultListener(onHRVResultListener);
    }

    public void setOnSpO2ResultListener(OnSpO2ResultListener onSpO2ResultListener) {
        super.setOnSpO2ResultListener(onSpO2ResultListener);
    }

    public void setOnTestPaperResultListener(int i, OnTestPaperResultListener onTestPaperResultListener) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new MonitorDataTransmission.IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("setOnBtResultListener")) {
            this.f951f.getTestPaperTask().setTestPaperResultListener(i, onTestPaperResultListener);
        }
    }

    @Deprecated
    public void setScanDevNamePrefixWhiteList(int i) {
        super.setScanDevNamePrefixWhiteList(i);
    }

    public void setTestPaper(int i, TestPaper testPaper) {
        super.setTestPaper(i, testPaper);
    }

    public void setUserProfile(IUserProfile iUserProfile) {
        super.setUserProfile(iUserProfile);
    }

    public void startMeasure(int i, Pair... pairArr) {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new MonitorDataTransmission.IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("startMeasure")) {
            this.f949d = i;
            int i2 = 0;
            this.f951f.getCommunicate().mo27590a(false);
            if (i == 1) {
                if (pairArr != null && pairArr.length > 0) {
                    int length = pairArr.length;
                    boolean z = false;
                    while (i2 < length) {
                        Pair pair = pairArr[i2];
                        int intValue = ((Integer) pair.first).intValue();
                        if (intValue == 101) {
                            z = ((Boolean) pair.second).booleanValue();
                        } else if (intValue == 102) {
                            this.f951f.getCommunicate().mo27590a(true);
                            ReadBPFileThread readBPFileThread = this.f955h;
                            if (readBPFileThread != null) {
                                readBPFileThread.interrupt();
                                this.f955h = null;
                            }
                            this.f951f.getBpTask().start(pairArr);
                            ReadBPFileThread readBPFileThread2 = new ReadBPFileThread((File) pair.second);
                            this.f955h = readBPFileThread2;
                            readBPFileThread2.start();
                            return;
                        }
                        i2++;
                    }
                    i2 = z;
                }
                if (i2 != 0) {
                    this.f951f.getCommunicate().mo27589a(new File(this.f946a.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), new SimpleDateFormat("'BP_ORIG_DATA-'yyyyMMdd-HHmmss'.txt'", Locale.getDefault()).format(new Date())));
                }
                this.f951f.getBpTask().start(pairArr);
            } else if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        this.f951f.getOxTask().start(pairArr);
                        return;
                    } else if (i == 5) {
                        this.f951f.getEcgTask().start(pairArr);
                        return;
                    } else if (!(i == 8 || i == 9)) {
                        return;
                    }
                }
                this.f951f.getTestPaperTask().start(i);
            } else {
                this.f951f.getBtTask().start(pairArr);
            }
        }
    }

    public void startScanTemp(BluetoothDevice bluetoothDevice, OnScanTempListener onScanTempListener) {
        super.startScanTemp(bluetoothDevice, onScanTempListener);
    }

    public void stopMeasure() {
        if (!DeviceType.HealthMonitor.equals(this.f950e)) {
            throw new MonitorDataTransmission.IllegalDeviceTypeException(DeviceType.HealthMonitor);
        } else if (mo27292a("stopMeasure")) {
            int i = this.f949d;
            if (i == 1) {
                this.f951f.getBpTask().stop();
            } else if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        this.f951f.getOxTask().stop();
                        return;
                    } else if (i == 5) {
                        this.f951f.getEcgTask().stop();
                        return;
                    } else if (!(i == 8 || i == 9)) {
                        return;
                    }
                }
                this.f951f.getTestPaperTask().stop();
            } else {
                this.f951f.getBtTask().stop();
            }
        }
    }

    public void stopMeasure(int i) {
        ReadBPFileThread readBPFileThread;
        super.stopMeasure();
        if (i == 1 && (readBPFileThread = this.f955h) != null && !readBPFileThread.isInterrupted()) {
            this.f955h.interrupt();
            this.f955h = null;
        }
    }

    public void stopScanTemp() {
        super.stopScanTemp();
    }

    public void unBind() {
        super.unBind();
    }
}
