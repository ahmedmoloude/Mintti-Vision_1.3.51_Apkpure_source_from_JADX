package com.p020kl.healthmonitor.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mintti.visionsdk.ble.BleDevice;
import com.p020kl.healthmonitor.C1624R;
import java.util.List;

/* renamed from: com.kl.healthmonitor.adapter.BleDeviceAdapter */
public class BleDeviceAdapter extends BaseQuickAdapter<BleDevice, BaseViewHolder> {
    public BleDeviceAdapter(int i, List<BleDevice> list) {
        super(i, list);
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder baseViewHolder, BleDevice bleDevice) {
        baseViewHolder.setText((int) C1624R.C1628id.tv_device_name, (CharSequence) bleDevice.getName());
        baseViewHolder.setText((int) C1624R.C1628id.tv_device_mac, (CharSequence) bleDevice.getMac());
        baseViewHolder.setText((int) C1624R.C1628id.tv_device_rssi, (CharSequence) bleDevice.getRssi() + "dBm");
    }
}
