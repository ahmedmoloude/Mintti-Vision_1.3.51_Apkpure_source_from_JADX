package com.mintti.visionsdk.ble;

import android.bluetooth.BluetoothDevice;
import java.util.Arrays;

public class BleDevice {
    private BluetoothDevice bluetoothDevice;
    private byte[] data;
    private int rssi;

    public BleDevice(BluetoothDevice bluetoothDevice2) {
        this.bluetoothDevice = bluetoothDevice2;
    }

    public BleDevice(BluetoothDevice bluetoothDevice2, int i) {
        this.bluetoothDevice = bluetoothDevice2;
        this.rssi = i;
    }

    public BleDevice(BluetoothDevice bluetoothDevice2, int i, byte[] bArr) {
        this.bluetoothDevice = bluetoothDevice2;
        this.rssi = i;
        this.data = bArr;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.bluetoothDevice;
    }

    public byte[] getData() {
        return this.data;
    }

    public String getMac() {
        return this.bluetoothDevice.getAddress();
    }

    public String getName() {
        return this.bluetoothDevice.getName();
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice2) {
        this.bluetoothDevice = bluetoothDevice2;
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }

    public void setRssi(int i) {
        this.rssi = i;
    }

    public String toString() {
        return "BleDevice{bluetoothDevice=" + this.bluetoothDevice + ", rssi=" + this.rssi + ", data=" + Arrays.toString(this.data) + '}';
    }
}
