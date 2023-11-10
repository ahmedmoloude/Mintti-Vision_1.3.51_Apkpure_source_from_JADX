package com.linktop.whealthService.task;

import com.itextpdf.text.DocWriter;
import com.itextpdf.text.pdf.BidiOrder;
import com.linktop.infs.OnBatteryListener;
import com.linktop.utils.BleDevLog;
import com.linktop.whealthService.util.IBleDev;
import kotlin.UByte;

public final class BatteryTask extends ModuleTask {

    /* renamed from: e */
    private static final String f1045e = "BatteryTask";

    /* renamed from: a */
    private final IBleDev f1046a;

    /* renamed from: b */
    private OnBatteryListener f1047b;

    /* renamed from: c */
    private int f1048c = 0;

    /* renamed from: d */
    private boolean f1049d = false;

    public BatteryTask(IBleDev iBleDev) {
        this.f1046a = iBleDev;
    }

    /* renamed from: a */
    private byte m211a(double d) {
        double d2 = (d / 8191.0d) * 3.3d * 3.0d * 1000.0d;
        if (d2 >= 4196.0d) {
            return 100;
        }
        if (d2 < 4196.0d && d2 >= 4168.0d) {
            return 99;
        }
        if (d2 < 4168.0d && d2 >= 4140.0d) {
            return 98;
        }
        if (d2 < 4140.0d && d2 >= 4112.0d) {
            return 96;
        }
        if (d2 < 4112.0d && d2 >= 4084.0d) {
            return 93;
        }
        if (d2 < 4084.0d && d2 >= 4056.0d) {
            return 90;
        }
        if (d2 < 4056.0d && d2 >= 4028.0d) {
            return 87;
        }
        if (d2 < 4028.0d && d2 >= 4000.0d) {
            return 85;
        }
        if (d2 < 4000.0d && d2 >= 3972.0d) {
            return 81;
        }
        if (d2 < 3972.0d && d2 >= 3944.0d) {
            return 77;
        }
        if (d2 < 3944.0d && d2 >= 3916.0d) {
            return 73;
        }
        if (d2 < 3916.0d && d2 >= 3888.0d) {
            return 69;
        }
        if (d2 < 3888.0d && d2 >= 3860.0d) {
            return 65;
        }
        if (d2 < 3860.0d && d2 > 3832.0d) {
            return DocWriter.EQUALS;
        }
        if (d2 < 3832.0d && d2 > 3804.0d) {
            return 56;
        }
        if (d2 < 3804.0d && d2 > 3776.0d) {
            return 50;
        }
        if (d2 < 3776.0d && d2 > 3748.0d) {
            return 42;
        }
        if (d2 < 3748.0d && d2 > 3720.0d) {
            return 30;
        }
        if (d2 < 3720.0d && d2 > 3692.0d) {
            return 19;
        }
        if (d2 < 3692.0d && d2 > 3664.0d) {
            return BidiOrder.f518B;
        }
        if (d2 < 3664.0d && d2 > 3636.0d) {
            return BidiOrder.f517AN;
        }
        if (d2 < 3636.0d && d2 > 3608.0d) {
            return 8;
        }
        if (d2 < 3608.0d && d2 > 3580.0d) {
            return 7;
        }
        if (d2 < 3580.0d && d2 > 3524.0d) {
            return 6;
        }
        if (d2 >= 3524.0d || d2 <= 3468.0d) {
            return (d2 >= 3468.0d || d2 <= 3300.0d) ? (byte) 0 : 4;
        }
        return 5;
    }

    /* renamed from: a */
    private void m212a(byte[] bArr) {
        byte b = bArr[0];
        if (b == 0) {
            this.f1048c = bArr[1];
            this.f1049d = false;
            String str = f1045e;
            BleDevLog.m141b(str, "BATTERY_QUERY:" + this.f1048c);
            OnBatteryListener onBatteryListener = this.f1047b;
            if (onBatteryListener != null) {
                onBatteryListener.onBatteryQuery(this.f1048c);
            }
        } else if (b == 1) {
            BleDevLog.m141b(f1045e, "BATTERY_CHARGING");
            this.f1049d = true;
            OnBatteryListener onBatteryListener2 = this.f1047b;
            if (onBatteryListener2 != null) {
                onBatteryListener2.onBatteryCharging();
            }
        } else if (b == 2) {
            BleDevLog.m141b(f1045e, "BATTERY_FULLY");
            this.f1048c = 100;
            this.f1049d = false;
            OnBatteryListener onBatteryListener3 = this.f1047b;
            if (onBatteryListener3 != null) {
                onBatteryListener3.onBatteryFull();
            }
        }
    }

    public void batteryQuery() {
        this.f1048c = 0;
        this.f1046a.getCommunicate().mo27588a(BidiOrder.f518B, new byte[]{0});
    }

    public void dealData(byte[] bArr) {
        byte[] bArr2 = {-1, -1};
        byte b = bArr[0];
        if (b == 0) {
            bArr2[0] = 0;
            bArr2[1] = m211a((double) ((bArr[1] << 8) + (bArr[2] & UByte.MAX_VALUE)));
        } else if (b == 1) {
            bArr2[0] = 1;
        } else if (b == 2) {
            bArr2[0] = 2;
        } else {
            return;
        }
        m212a(bArr2);
    }

    public int getPower() {
        return this.f1048c;
    }

    public boolean isCharging() {
        return this.f1049d;
    }

    public void setBatteryStateListener(OnBatteryListener onBatteryListener) {
        this.f1047b = onBatteryListener;
    }
}
