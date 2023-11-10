package com.linktop.whealthService.task;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import com.itextpdf.text.pdf.BidiOrder;
import com.linktop.infs.OnScanTempListener;
import com.linktop.infs.OnThermInfoListener;
import com.linktop.whealthService.OnBLEService;
import com.linktop.whealthService.util.ParseByte;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Timer;

public final class ThermometerTask {

    /* renamed from: j */
    public static final byte[] f1208j = {BidiOrder.f527S};

    /* renamed from: a */
    private final DecimalFormat f1209a = new DecimalFormat("#.#");

    /* renamed from: b */
    private final OnBLEService f1210b;

    /* renamed from: c */
    private final Handler f1211c = new Handler();

    /* renamed from: d */
    private BluetoothDevice f1212d;

    /* renamed from: e */
    private double f1213e = 0.0d;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public OnScanTempListener f1214f;

    /* renamed from: g */
    private OnThermInfoListener f1215g;

    /* renamed from: h */
    private long f1216h = (OnBLEService.f987P * 30);

    /* renamed from: i */
    private final Runnable f1217i = new Runnable() {
        public void run() {
            if (ThermometerTask.this.f1214f != null) {
                ThermometerTask.this.f1214f.onNoTemp();
            }
            ThermometerTask.this.m343b();
        }
    };

    public ThermometerTask(OnBLEService onBLEService) {
        this.f1210b = onBLEService;
    }

    /* renamed from: a */
    private void m341a(String str, byte b, int i) {
        this.f1211c.removeCallbacks(this.f1217i);
        if (mo27578a()) {
            this.f1211c.postDelayed(this.f1217i, this.f1216h);
        }
        double doubleValue = new BigDecimal(((double) i) / 100.0d).setScale(1, 4).doubleValue();
        if (doubleValue > 33.5d && doubleValue < 36.9d) {
            double d = this.f1213e;
            if (d >= 0.6d) {
                doubleValue += 0.6d;
            } else {
                double d2 = d + 0.1d;
                this.f1213e = d2;
                doubleValue += d2;
            }
            if (doubleValue >= 36.9d) {
                doubleValue = 36.9d;
            }
        }
        double parseDouble = Double.parseDouble(this.f1209a.format(doubleValue));
        if (this.f1214f != null) {
            this.f1211c.post(new Runnable(str, parseDouble, b) {
                public final /* synthetic */ String f$1;
                public final /* synthetic */ double f$2;
                public final /* synthetic */ byte f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r5;
                }

                public final void run() {
                    ThermometerTask.this.m342a(this.f$1, this.f$2, this.f$3);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public /* synthetic */ void m342a(String str, double d, byte b) {
        this.f1214f.onScanTempResult(str, d, b);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m343b() {
        this.f1210b.mo27448a(false);
        this.f1210b.mo27453d(101);
        this.f1212d = null;
        this.f1214f = null;
    }

    /* renamed from: a */
    public void mo27574a(BluetoothDevice bluetoothDevice, OnScanTempListener onScanTempListener) {
        this.f1212d = bluetoothDevice;
        this.f1214f = onScanTempListener;
        if (!mo27578a()) {
            this.f1211c.removeCallbacks(this.f1217i);
        }
    }

    /* renamed from: a */
    public void mo27575a(OnThermInfoListener onThermInfoListener) {
        this.f1215g = onThermInfoListener;
        new Timer().schedule(this.f1210b.mo27457l(), 500);
    }

    /* renamed from: a */
    public void mo27576a(ParseByte parseByte, BluetoothDevice bluetoothDevice) {
        if (this.f1212d != null && bluetoothDevice != null) {
            int c = parseByte.mo27594c();
            byte a = parseByte.mo27592a();
            String address = this.f1212d.getAddress();
            if (address.equals(bluetoothDevice.getAddress())) {
                m341a(address, a, c);
            }
        }
    }

    /* renamed from: a */
    public void mo27577a(String str) {
        OnThermInfoListener onThermInfoListener = this.f1215g;
        if (onThermInfoListener != null) {
            onThermInfoListener.onThermQRCode(str);
        }
    }

    /* renamed from: a */
    public boolean mo27578a() {
        return this.f1212d != null;
    }
}
