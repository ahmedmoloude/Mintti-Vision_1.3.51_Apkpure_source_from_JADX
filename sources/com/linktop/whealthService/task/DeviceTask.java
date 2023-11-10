package com.linktop.whealthService.task;

import com.itextpdf.text.pdf.BidiOrder;
import com.linktop.constant.DeviceInfo;
import com.linktop.infs.OnDeviceInfoListener;
import com.linktop.utils.BleDevLog;
import com.linktop.utils.Translate;
import com.linktop.whealthService.OnBLEService;
import com.linktop.whealthService.util.Communicate;
import com.linktop.whealthService.util.IBleDev;
import java.util.Timer;
import java.util.TimerTask;

public final class DeviceTask extends ModuleTask {
    /* access modifiers changed from: private */

    /* renamed from: h */
    public static final String f1094h = "DeviceTask";
    /* access modifiers changed from: private */

    /* renamed from: a */
    public final IBleDev f1095a;

    /* renamed from: b */
    private final Communicate f1096b;

    /* renamed from: c */
    private final byte[] f1097c = new byte[59];

    /* renamed from: d */
    private int f1098d = 0;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public OnDeviceInfoListener f1099e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public boolean f1100f = false;

    /* renamed from: g */
    private Timer f1101g;

    public DeviceTask(IBleDev iBleDev) {
        this.f1095a = iBleDev;
        this.f1096b = iBleDev.getCommunicate();
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m249b() {
        Timer timer = this.f1101g;
        if (timer != null) {
            timer.cancel();
            this.f1101g = null;
        }
    }

    public void dealData(byte[] bArr) {
        byte[] bArr2 = bArr;
        int length = bArr2.length;
        System.arraycopy(bArr2, 0, this.f1097c, this.f1098d, length);
        int i = this.f1098d + length;
        this.f1098d = i;
        if (i == 59) {
            this.f1098d = 0;
            byte[] bArr3 = new byte[2];
            byte[] bArr4 = new byte[1];
            byte[] bArr5 = new byte[8];
            byte[] bArr6 = new byte[8];
            byte[] bArr7 = new byte[2];
            byte[] bArr8 = new byte[1];
            byte[] bArr9 = new byte[1];
            byte[] bArr10 = new byte[36];
            System.arraycopy(this.f1097c, 0, bArr3, 0, 2);
            System.arraycopy(this.f1097c, 2, bArr4, 0, 1);
            System.arraycopy(this.f1097c, 3, bArr5, 0, 8);
            System.arraycopy(this.f1097c, 11, bArr6, 0, 8);
            System.arraycopy(this.f1097c, 19, bArr7, 0, 2);
            System.arraycopy(this.f1097c, 21, bArr8, 0, 1);
            System.arraycopy(this.f1097c, 22, bArr9, 0, 1);
            System.arraycopy(this.f1097c, 23, bArr10, 0, 36);
            this.f1100f = true;
            m249b();
            DeviceInfo deviceInfo = new DeviceInfo(Translate.m145a(bArr3), Translate.m145a(bArr4), Translate.m145a(bArr5), Translate.m145a(bArr6), Translate.m145a(bArr7), Translate.m145a(bArr8), Translate.m145a(bArr9), Translate.m145a(bArr10));
            OnDeviceInfoListener onDeviceInfoListener = this.f1099e;
            if (onDeviceInfoListener != null) {
                onDeviceInfoListener.onDeviceInfo(deviceInfo);
            }
            IBleDev iBleDev = this.f1095a;
            if (iBleDev instanceof OnBLEService) {
                ((OnBLEService) iBleDev).mo27452d();
            }
        }
    }

    public void getDeviceInfo() {
        this.f1100f = false;
        this.f1096b.mo27588a(BidiOrder.f519BN, new byte[]{-114});
        Timer timer = this.f1101g;
        if (timer != null) {
            timer.cancel();
            this.f1101g = null;
        }
        Timer timer2 = new Timer();
        this.f1101g = timer2;
        timer2.schedule(new TimerTask() {
            public void run() {
                if (DeviceTask.this.f1100f) {
                    boolean unused = DeviceTask.this.f1100f = false;
                } else {
                    if (DeviceTask.this.f1099e != null) {
                        BleDevLog.m141b(DeviceTask.f1094h, "*send failed* 0");
                        DeviceTask.this.f1099e.onReadDeviceInfoFailed();
                    }
                    if (DeviceTask.this.f1095a instanceof OnBLEService) {
                        ((OnBLEService) DeviceTask.this.f1095a).mo27452d();
                    }
                }
                DeviceTask.this.m249b();
            }
        }, 2000);
    }

    public void setOnDeviceInfoListener(OnDeviceInfoListener onDeviceInfoListener) {
        this.f1099e = onDeviceInfoListener;
    }
}
