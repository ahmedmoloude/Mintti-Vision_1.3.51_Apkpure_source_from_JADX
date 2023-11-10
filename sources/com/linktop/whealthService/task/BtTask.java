package com.linktop.whealthService.task;

import com.linktop.constant.Pair;
import com.linktop.infs.OnBtFactoryListener;
import com.linktop.infs.OnBtResultListener;
import com.linktop.utils.BleDevLog;
import com.linktop.whealthService.util.IBleDev;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import kotlin.UByte;

public final class BtTask extends HcModuleTask {

    /* renamed from: a */
    private final List<Integer> f1088a = new ArrayList();

    /* renamed from: b */
    private final List<Integer> f1089b = new ArrayList();

    /* renamed from: c */
    private OnBtResultListener f1090c;

    /* renamed from: d */
    private OnBtFactoryListener f1091d;

    /* renamed from: e */
    private boolean f1092e;

    /* renamed from: f */
    private boolean f1093f;

    public BtTask(IBleDev iBleDev) {
        super(iBleDev);
    }

    /* renamed from: a */
    private float m240a(int i) {
        return (((float) i) * 0.02f) - 273.15f;
    }

    /* renamed from: a */
    private static int m241a(List<Integer> list) {
        int[] iArr = new int[100];
        for (int i = 2; i < list.size(); i++) {
            iArr[i - 2] = list.get(i).intValue();
        }
        for (int i2 = 0; i2 < list.size() - 2; i2++) {
            int i3 = 0;
            while (i3 < ((list.size() - 2) - 1) - i2) {
                int i4 = i3 + 1;
                if (iArr[i3] > iArr[i4]) {
                    int i5 = iArr[i3];
                    iArr[i3] = iArr[i4];
                    iArr[i4] = i5;
                }
                i3 = i4;
            }
        }
        return iArr[((list.size() - 2) / 2) + 1];
    }

    /* renamed from: a */
    private void m242a() {
        this.f1093f = true;
        this.f1092e = true;
        this.mCommunicate.mo27588a((byte) 2, new byte[]{2});
        this.mIBleDev.setMeasuring(true);
    }

    /* renamed from: a */
    private void m243a(boolean z, double d) {
        double doubleValue = new BigDecimal(d).setScale(1, RoundingMode.HALF_UP).doubleValue();
        OnBtResultListener onBtResultListener = this.f1090c;
        if (onBtResultListener != null) {
            onBtResultListener.onBtResult(doubleValue);
        }
    }

    /* renamed from: a */
    public void mo27504a(OnBtFactoryListener onBtFactoryListener) {
        this.f1091d = onBtFactoryListener;
    }

    public void dealData(byte[] bArr) {
        Class<BtTask> cls = BtTask.class;
        if (this.f1092e) {
            int i = ((bArr[3] & UByte.MAX_VALUE) << 8) + (bArr[2] & UByte.MAX_VALUE);
            float a = m240a(((bArr[1] & UByte.MAX_VALUE) << 8) + (bArr[0] & UByte.MAX_VALUE));
            float a2 = m240a(i);
            String simpleName = cls.getSimpleName();
            BleDevLog.m142c(simpleName, "tempBT = " + a + " ,tempET = " + a2);
            OnBtFactoryListener onBtFactoryListener = this.f1091d;
            if (onBtFactoryListener != null) {
                onBtFactoryListener.mo27384a((double) a2, (double) a);
            } else {
                m243a(true, TempTranslate.getBodyTemp((double) a2, (double) a));
            }
            if (this.f1093f) {
                m242a();
                return;
            }
            return;
        }
        int i2 = ((bArr[1] & UByte.MAX_VALUE) << 8) + (bArr[0] & UByte.MAX_VALUE);
        int i3 = ((bArr[3] & UByte.MAX_VALUE) << 8) + (bArr[2] & UByte.MAX_VALUE);
        int i4 = ((bArr[5] & UByte.MAX_VALUE) << 8) + (bArr[4] & UByte.MAX_VALUE);
        int i5 = ((bArr[7] & UByte.MAX_VALUE) << 8) + (bArr[6] & UByte.MAX_VALUE);
        this.f1089b.add(Integer.valueOf(i3));
        this.f1088a.add(Integer.valueOf(i2));
        this.f1089b.add(Integer.valueOf(i5));
        this.f1088a.add(Integer.valueOf(i4));
        if (this.f1089b.size() == 6) {
            int a3 = m241a(this.f1088a);
            int a4 = m241a(this.f1089b);
            float a5 = m240a(a3);
            float a6 = m240a(a4);
            this.mIBleDev.setMeasuring(false);
            BleDevLog.m138a((Class<?>) cls, "tempBT = " + a5 + " ,tempET = " + a6);
            OnBtFactoryListener onBtFactoryListener2 = this.f1091d;
            if (onBtFactoryListener2 != null) {
                onBtFactoryListener2.mo27384a((double) a6, (double) a5);
            } else {
                m243a(true, TempTranslate.getBodyTemp((double) a6, (double) a5));
            }
        }
    }

    public void setOnBtResultListener(OnBtResultListener onBtResultListener) {
        if (onBtResultListener instanceof OnBtFactoryListener) {
            this.f1091d = (OnBtFactoryListener) onBtResultListener;
        } else {
            this.f1090c = onBtResultListener;
        }
    }

    public void start(Pair... pairArr) {
        boolean z;
        super.start(pairArr);
        if (pairArr.length > 0) {
            z = false;
            for (Pair pair : pairArr) {
                if (((Integer) pair.first).intValue() == 201) {
                    z = ((Boolean) pair.second).booleanValue();
                }
            }
        } else {
            z = false;
        }
        this.f1089b.clear();
        this.f1088a.clear();
        if (z) {
            m242a();
            return;
        }
        this.f1092e = false;
        this.mCommunicate.mo27588a((byte) 2, new byte[]{0});
    }

    public void stop() {
        super.stop();
        this.f1093f = false;
    }
}
