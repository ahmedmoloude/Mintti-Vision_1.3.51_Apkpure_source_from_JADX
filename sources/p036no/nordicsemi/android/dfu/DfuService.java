package p036no.nordicsemi.android.dfu;

import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import java.io.InputStream;
import p036no.nordicsemi.android.dfu.internal.exception.DeviceDisconnectedException;
import p036no.nordicsemi.android.dfu.internal.exception.DfuException;
import p036no.nordicsemi.android.dfu.internal.exception.UploadAbortedException;

/* renamed from: no.nordicsemi.android.dfu.DfuService */
interface DfuService extends DfuCallback {
    boolean initialize(Intent intent, BluetoothGatt bluetoothGatt, int i, InputStream inputStream, InputStream inputStream2) throws DfuException, DeviceDisconnectedException, UploadAbortedException;

    boolean isClientCompatible(Intent intent, BluetoothGatt bluetoothGatt) throws DfuException, DeviceDisconnectedException, UploadAbortedException;

    void performDfu(Intent intent) throws DfuException, DeviceDisconnectedException, UploadAbortedException;

    void release();
}
