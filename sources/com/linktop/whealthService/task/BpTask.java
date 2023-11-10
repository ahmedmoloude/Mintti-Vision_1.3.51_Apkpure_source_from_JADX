package com.linktop.whealthService.task;

import android.os.Handler;
import com.itextpdf.text.pdf.BidiOrder;
import com.linktop.constant.Pair;
import com.linktop.constant.ResultData;
import com.linktop.infs.OnBpDataListener;
import com.linktop.infs.OnBpResultListener;
import com.linktop.infs.OnCuffLeakTestListener;
import com.linktop.utils.BleDevLog;
import com.linktop.whealthService.util.IBleDev;
import java.math.BigDecimal;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

public final class BpTask extends HcModuleTask {

    /* renamed from: H */
    public static int[] f1050H = new int[8000];

    /* renamed from: I */
    public static int[] f1051I = new int[50];

    /* renamed from: J */
    public static int f1052J = 170;

    /* renamed from: A */
    private long f1053A;

    /* renamed from: B */
    private boolean f1054B;

    /* renamed from: C */
    public int f1055C;

    /* renamed from: D */
    private boolean f1056D;

    /* renamed from: E */
    private OnCuffLeakTestListener f1057E;

    /* renamed from: F */
    private boolean f1058F;

    /* renamed from: G */
    public int f1059G;

    /* renamed from: a */
    private final Handler f1060a = new Handler();
    /* access modifiers changed from: private */

    /* renamed from: b */
    public MethodOfBloodPressure f1061b;

    /* renamed from: c */
    private int f1062c;

    /* renamed from: d */
    private int f1063d;

    /* renamed from: e */
    private int f1064e;

    /* renamed from: f */
    private int f1065f;

    /* renamed from: g */
    private int f1066g;

    /* renamed from: h */
    private int f1067h;

    /* renamed from: i */
    private long f1068i;

    /* renamed from: j */
    public int f1069j = 0;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public int f1070k = 0;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public int f1071l = 0;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public int f1072m = 0;
    /* access modifiers changed from: private */

    /* renamed from: n */
    public int f1073n = 0;

    /* renamed from: o */
    private int f1074o = 0;

    /* renamed from: p */
    private long f1075p = 0;
    /* access modifiers changed from: private */

    /* renamed from: q */
    public boolean f1076q = false;

    /* renamed from: r */
    private final int[] f1077r = new int[5];

    /* renamed from: s */
    private int f1078s = 0;

    /* renamed from: t */
    private int f1079t = 0;

    /* renamed from: u */
    private int f1080u = 0;

    /* renamed from: v */
    private int f1081v = 0;

    /* renamed from: w */
    private int f1082w = 0;

    /* renamed from: x */
    private int f1083x = 0;
    /* access modifiers changed from: private */

    /* renamed from: y */
    public OnBpResultListener f1084y;

    /* renamed from: z */
    private OnBpDataListener f1085z;

    public class countThread extends Thread {
        private countThread() {
        }

        public void run() {
            super.run();
            try {
                BpTask.this.f1061b.mo27540h();
                BpTask.this.f1061b.mo27534b();
                BpTask bpTask = BpTask.this;
                int unused = bpTask.f1073n = bpTask.f1061b.mo27532b(!BpTask.this.f1076q);
                BpTask.this.m218a(false);
                if (BpTask.this.f1073n != 0) {
                    int unused2 = BpTask.this.f1073n;
                    int unused3 = BpTask.this.f1070k = 5;
                } else {
                    int unused4 = BpTask.this.f1070k = 3;
                }
                if (BpTask.this.f1061b.mo27526a() > 3) {
                    int unused5 = BpTask.this.f1070k = 5;
                }
            } catch (Error | Exception e) {
                IBleDev iBleDev = BpTask.this.mIBleDev;
                if (iBleDev != null) {
                    iBleDev.onInnerThrowableCallback(e);
                }
            }
        }
    }

    public BpTask(IBleDev iBleDev) {
        super(iBleDev);
    }

    /* renamed from: a */
    private void m215a() {
        BleDevLog.m142c("BpTask", "bp_start_pwm_arm");
        this.mCommunicate.mo27588a((byte) 1, new byte[]{5, 85});
    }

    /* renamed from: a */
    private void m216a(byte b) {
        BleDevLog.m142c("BpTask", "bp_start_pwm_wrist");
        this.mCommunicate.mo27588a((byte) 1, new byte[]{6, b});
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m218a(boolean z) {
        if (this.f1085z != null) {
            ResultData e = this.f1061b.mo27537e();
            e.f967j = m220a(f1050H, this.f1069j);
            this.f1085z.onFIRAvgFilter(e, z);
        }
    }

    /* renamed from: a */
    private boolean m219a(int i) {
        int i2;
        int i3 = 1;
        if (i > 2800 && this.f1070k == 1) {
            this.f1070k = 2;
            this.f1078s = 0;
            this.f1079t = 0;
            this.f1080u = 0;
            this.f1081v = 0;
            this.f1082w = 0;
            this.f1083x = 0;
            m222b();
        }
        if (i > 3000 && this.f1070k == 2) {
            this.f1070k = 3;
        }
        if (this.f1070k >= 3) {
            int[] iArr = f1050H;
            int i4 = this.f1069j;
            iArr[i4] = i;
            int i5 = i4 + 1;
            this.f1069j = i5;
            if (i5 > 6550 || i > 30000) {
                stop();
            }
        }
        int i6 = this.f1069j;
        if (i6 % 100 == 0 && this.f1070k >= 3) {
            this.f1055C = i6;
            this.f1061b.mo27540h();
            int i7 = f1050H[this.f1055C - 1];
            for (int i8 = 0; i8 < 10; i8++) {
                int[] iArr2 = f1050H;
                int i9 = (this.f1055C - 1) - i8;
                if (i7 < iArr2[i9]) {
                    i7 = iArr2[i9];
                }
            }
            int g = this.f1061b.mo27539g();
            BleDevLog.m143d("BpTask", "pwmadjust sumPressure = " + g);
            int i10 = this.f1079t;
            if (i10 == 0) {
                this.f1079t = g / 10;
            } else {
                int i11 = this.f1081v;
                if (!(i11 == 0 || (i2 = this.f1080u) == 0)) {
                    this.f1082w = i11 - i2;
                }
                this.f1078s = i10;
                int i12 = g / 10;
                this.f1079t = i12;
                this.f1080u = i11;
                this.f1081v = i12 - i10;
            }
            int i13 = this.f1079t - this.f1078s;
            BleDevLog.m143d("BpTask", "pwmadjust preAveragePressure:nowAveragePressure = " + this.f1078s + ":" + this.f1079t);
            BleDevLog.m143d("BpTask", "pwmadjust preSpeedPressure:nowSpeedPressure = " + this.f1080u + ":" + this.f1081v);
            BleDevLog.m141b("BpTask", "pwmadjust a = " + i13 + " prePWM=" + this.f1082w);
            if (this.f1076q) {
                m229c(i13);
            } else {
                m224b(i13);
            }
            if (this.f1076q || ((i13 <= 1200 && i13 >= 50) || this.f1078s == 0 || this.f1079t == 0)) {
                if (i7 > 10000 && this.f1070k == 3) {
                    this.f1070k = 4;
                    new countThread().start();
                    if (this.f1055C > 5550) {
                        this.f1070k = 5;
                    }
                }
                if (this.f1070k == 5) {
                    this.f1070k = 6;
                    stop();
                    this.f1060a.postDelayed(new Runnable() {
                        public void run() {
                            BleDevLog.m141b("cHandler", " cHandler postDelayed");
                            int unused = BpTask.this.f1070k = 0;
                            BpTask.this.f1061b.mo27540h();
                            BleDevLog.m141b("cHandler1", " cHandler postDelayed");
                            BpTask.this.f1061b.mo27534b();
                            BleDevLog.m141b("cHandler2", " cHandler postDelayed");
                            int c = BpTask.this.f1061b.mo27535c(!BpTask.this.f1076q);
                            if (c != 0) {
                                int unused2 = BpTask.this.f1073n = c;
                            }
                            int unused3 = BpTask.this.f1073n = new BigDecimal((double) (((float) BpTask.this.f1073n) / 100.0f)).setScale(0, 4).intValue();
                            BleDevLog.m141b("cHandler3", " cHandler postDelayed");
                            BpTask bpTask = BpTask.this;
                            int unused4 = bpTask.f1071l = bpTask.f1061b.mo27536d();
                            BleDevLog.m141b("cHandler4", " cHandler postDelayed");
                            BpTask bpTask2 = BpTask.this;
                            int unused5 = bpTask2.f1072m = bpTask2.f1061b.mo27527a(!BpTask.this.f1076q);
                            int unused6 = BpTask.this.f1072m = new BigDecimal((double) (((float) BpTask.this.f1072m) / 100.0f)).setScale(0, 4).intValue();
                            BpTask.this.m218a(true);
                            BleDevLog.m141b("BpTask", "  SBP:" + BpTask.this.f1073n + ", DBP:" + BpTask.this.f1072m + "  Heart rate:" + BpTask.this.f1071l);
                            if (BpTask.this.f1073n <= BpTask.this.f1072m || BpTask.this.f1073n - BpTask.this.f1072m < 10) {
                                if (BpTask.this.f1084y != null) {
                                    BpTask.this.f1084y.onBpResultError();
                                }
                            } else if (BpTask.this.f1084y != null) {
                                BpTask.this.f1084y.onBpResult(BpTask.this.f1073n, BpTask.this.f1072m, BpTask.this.f1071l);
                            }
                            BpTask.this.mIBleDev.setMeasuring(false);
                        }
                    }, 1000);
                }
            } else {
                stop();
                if (i13 <= 1200) {
                    i3 = 0;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("-----------");
                sb.append(i3 == 0 ? "存在漏气，请检查气路，重新测量" : "测量过程中请保持安静，此次测量作废，请重新测量");
                BleDevLog.m141b("BpTask", sb.toString());
                OnBpResultListener onBpResultListener = this.f1084y;
                if (onBpResultListener != null) {
                    onBpResultListener.onLeakError(i3);
                }
                return false;
            }
        }
        return true;
    }

    /* renamed from: a */
    private int[] m220a(int[] iArr, int i) {
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }

    /* renamed from: b */
    private void m222b() {
        if (this.f1076q) {
            m216a((byte) f1052J);
        } else {
            m215a();
        }
    }

    /* renamed from: b */
    private void m223b(byte b) {
        BleDevLog.m142c("BpTask", "pwmadjust setPWM:" + b);
        if (b < 0) {
            this.mCommunicate.mo27588a((byte) 1, new byte[]{8, b});
        }
    }

    /* renamed from: b */
    private void m224b(int i) {
        int i2;
        byte b;
        int i3 = this.f1078s;
        if (i3 != 0 && this.f1079t != 0 && this.f1080u == 0) {
            int i4 = ((i - 500) * 2) / 100;
            b = (byte) i4;
            this.f1059G = i4;
            if (i4 != 0) {
                this.f1083x = 1;
            } else {
                return;
            }
        } else if (i3 != 0 && this.f1079t != 0 && (i2 = this.f1080u) != 0) {
            if (i <= 400) {
                int i5 = (((this.f1081v * 2) + i2) - 1400) / 100;
                byte b2 = (byte) i5;
                this.f1059G = i5;
                if (i5 != 0) {
                    m223b(b2);
                }
            }
            if (i > 400 && i <= 500) {
                int i6 = this.f1081v;
                int i7 = this.f1080u;
                int i8 = ((((i6 - i7) + this.f1082w) * 4) / 200) * 2;
                int i9 = (i6 * 2) + i7;
                b = (byte) (((i9 - 1650) / 100) + i8);
                int i10 = ((i9 - 1600) / 100) + i8;
                this.f1059G = i10;
                if (i10 == 0) {
                    return;
                }
            } else if (i > 500 && i <= 680) {
                int i11 = this.f1081v - this.f1080u;
                int i12 = this.f1082w;
                int i13 = ((i11 + i12) / 50) * 2;
                this.f1059G = i13;
                byte b3 = (byte) i13;
                if (i > 550 && i13 < 0) {
                    int i14 = (((i11 * 2) + i12) / 75) * 2;
                    this.f1059G = i14;
                    b3 = (byte) i14;
                }
                if (i < 525 && this.f1059G >= 0) {
                    int i15 = (((i11 * 2) + i12) / 50) * 2;
                    this.f1059G = i15;
                    b3 = (byte) i15;
                }
                if (this.f1083x != 0 || this.f1059G == 0) {
                    this.f1083x = 0;
                    return;
                }
                this.f1083x = 1;
                m223b(b3);
                return;
            } else if (i > 680) {
                int i16 = (((this.f1081v * 2) + this.f1080u) - 1400) / 100;
                b = (byte) i16;
                this.f1059G = i16;
                if (i16 == 0) {
                    return;
                }
            } else {
                return;
            }
        } else {
            return;
        }
        m223b(b);
    }

    /* renamed from: c */
    private void m228c() {
        BleDevLog.m142c("BpTask", "getPressureZero");
        this.mCommunicate.mo27588a((byte) 1, new byte[]{3});
    }

    /* renamed from: c */
    private void m229c(int i) {
        int i2;
        byte b;
        int i3 = this.f1078s;
        if (i3 != 0 && this.f1079t != 0 && this.f1080u == 0) {
            int i4 = ((i - 700) * 2) / 100;
            b = (byte) i4;
            this.f1059G = i4;
            StringBuilder sb = new StringBuilder();
            sb.append("pwm=");
            sb.append(b);
            sb.append(" pwmLog=");
            sb.append(this.f1059G);
            if (this.f1059G == 0) {
                return;
            }
        } else if (i3 != 0 && this.f1079t != 0 && (i2 = this.f1080u) != 0) {
            if (i <= 600) {
                int i5 = (((this.f1081v * 2) + i2) - 2200) / 100;
                byte b2 = (byte) i5;
                this.f1059G = i5;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("pwm=");
                sb2.append(b2);
                sb2.append(" pwmLog=");
                sb2.append(this.f1059G);
                if (this.f1059G != 0) {
                    m223b(b2);
                }
            }
            if (i > 600 && i <= 700) {
                int i6 = this.f1081v;
                int i7 = this.f1080u;
                int i8 = ((((i6 - i7) + (this.f1082w / 3)) * 4) / 200) * 2;
                int i9 = (i6 * 2) + i7;
                b = (byte) (((i9 - 2450) / 100) + i8);
                this.f1059G = ((i9 - 2400) / 100) + i8;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("pwm=");
                sb3.append(b);
                sb3.append(" pwmLog=");
                sb3.append(this.f1059G);
                sb3.append(" diff=");
                sb3.append(i8);
                if (this.f1059G == 0) {
                    return;
                }
                m223b(b);
            } else if (i > 700 && i <= 880) {
                int i10 = (((this.f1081v - this.f1080u) + (this.f1082w / 3)) / 50) * 2;
                this.f1059G = i10;
                b = (byte) i10;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("pwm=");
                sb4.append(b);
                sb4.append(" pwmLog=");
                sb4.append(this.f1059G);
                if (i > 750 && this.f1059G < 0) {
                    int i11 = ((((this.f1081v - this.f1080u) * 2) + (this.f1082w / 3)) / 75) * 2;
                    this.f1059G = i11;
                    b = (byte) i11;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("pwm=");
                    sb5.append(b);
                    sb5.append(" pwmLog=");
                    sb5.append(this.f1059G);
                }
                if (i < 725 && this.f1059G >= 0) {
                    int i12 = ((((this.f1081v - this.f1080u) * 2) + (this.f1082w / 3)) / 50) * 2;
                    this.f1059G = i12;
                    b = (byte) i12;
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("pwm=");
                    sb6.append(b);
                    sb6.append(" pwmLog=");
                    sb6.append(this.f1059G);
                }
                if (this.f1083x != 0 || this.f1059G == 0) {
                    this.f1083x = 0;
                    return;
                }
                StringBuilder sb7 = new StringBuilder();
                sb7.append("pwm=");
                sb7.append(b);
                sb7.append(" pwmLog=");
                sb7.append(this.f1059G);
            } else if (i > 880) {
                int i13 = (((this.f1081v * 2) + this.f1080u) - 2200) / 100;
                b = (byte) i13;
                this.f1059G = i13;
                StringBuilder sb8 = new StringBuilder();
                sb8.append("pwm=");
                sb8.append(b);
                sb8.append(" pwmLog=");
                sb8.append(this.f1059G);
                if (this.f1059G == 0) {
                    return;
                }
                m223b(b);
            } else {
                return;
            }
        } else {
            return;
        }
        this.f1083x = 1;
        m223b(b);
    }

    /* renamed from: e */
    private void m233e() {
        BleDevLog.m142c("BpTask", "readCalibration");
        this.f1054B = false;
        this.f1053A = System.currentTimeMillis();
        this.mCommunicate.mo27588a((byte) 1, new byte[]{1});
    }

    /* renamed from: f */
    private void m235f() {
        BleDevLog.m142c("BpTask", "sendTemperatureCompensateReq");
        this.mCommunicate.mo27588a((byte) 1, new byte[]{2});
    }

    /* renamed from: g */
    private void m236g() {
        BleDevLog.m142c("BpTask", "startTestPressure");
        if (this.f1061b != null) {
            this.f1061b = null;
        }
        this.f1061b = new MethodOfBloodPressure(this);
        this.mCommunicate.mo27588a((byte) 1, new byte[]{4});
    }

    /* renamed from: a */
    public void mo27495a(boolean z, int i) {
        if (z) {
            this.f1070k = 1;
            this.f1069j = 0;
            this.f1061b = null;
            MethodOfBloodPressure methodOfBloodPressure = new MethodOfBloodPressure(this);
            this.f1061b = methodOfBloodPressure;
            methodOfBloodPressure.mo27538f();
        }
        m219a(i);
    }

    /* renamed from: b */
    public void mo27496b(boolean z) {
        this.f1076q = z;
    }

    /* renamed from: d */
    public boolean mo27497d() {
        return this.f1076q;
    }

    public void dealData(byte[] bArr) {
        byte[] bArr2 = bArr;
        byte b = bArr2[0];
        int i = 9;
        String str = "BpTask";
        int i2 = 2;
        if (b == 1) {
            String str2 = str;
            if (bArr2.length < 7) {
                BleDevLog.m141b(str2, "bp calibration parameter length < 7");
                return;
            }
            this.f1062c = ((bArr2[1] & UByte.MAX_VALUE) << 6) + ((bArr2[2] & UByte.MAX_VALUE) >> 2);
            this.f1063d = ((bArr2[2] & 3) << 4) + ((bArr2[3] & UByte.MAX_VALUE) >> 4);
            this.f1064e = ((bArr2[3] & BidiOrder.f518B) << 9) + ((bArr2[4] & UByte.MAX_VALUE) << 1) + ((bArr2[5] & UByte.MAX_VALUE) >> 7);
            this.f1065f = ((bArr2[5] & ByteCompanionObject.MAX_VALUE) << 2) + ((bArr2[6] & UByte.MAX_VALUE) >> 6);
            this.f1066g = bArr2[6] & 63;
            BleDevLog.m141b("C1 ", this.f1062c + "  C2 " + this.f1063d + "  C3 " + this.f1064e + "  C4 " + this.f1065f + "  C5 " + this.f1066g);
            m235f();
        } else if (b == 2) {
            String str3 = str;
            if (bArr2.length < 3) {
                BleDevLog.m141b(str3, "bp calibration temperature length < 3");
                return;
            }
            long j = (long) ((bArr2[1] & UByte.MAX_VALUE) + ((bArr2[2] & UByte.MAX_VALUE) << 8));
            long pow = (long) ((Math.pow(2.0d, 14.0d) * 50.0d * ((double) this.f1066g)) + 2.62144E7d);
            long j2 = (long) ((((this.f1064e - 4096) * 196600) / 8192) + 322150);
            long j3 = (long) ((this.f1065f * 40) + 30720);
            long j4 = (10 * j) - j2;
            long j5 = (j3 * j4) + pow;
            BleDevLog.m141b("d2", j + "  trefc " + pow + "  d2ref " + j2 + "  stc " + j3 + "  d2c " + j4);
            long round = Math.round(((double) j5) / Math.pow(2.0d, 20.0d));
            long round2 = Math.round(((double) pow) / Math.pow(2.0d, 20.0d));
            this.f1068i = (((long) ((this.f1062c + 24576) * 13312)) / ((long) (Math.pow(2.0d, 16.0d) + ((double) ((((long) ((this.f1063d + 32) * -36)) * ((long) (((double) (((long) ((int) (j5 + (((round - (500 - round2)) * 84) * (round - round2))))) - round2)) / Math.pow(2.0d, 20.0d)))) / 160))))) * 25;
            this.f1067h = 32016;
            m228c();
            this.f1070k = 1;
            this.f1069j = 0;
            this.f1074o = 0;
            this.f1075p = 0;
            MethodOfBloodPressure methodOfBloodPressure = this.f1061b;
            if (methodOfBloodPressure != null) {
                methodOfBloodPressure.mo27538f();
            }
        } else if (b == 3) {
            if (!this.f1056D && this.f1054B) {
                return;
            }
            if (bArr2.length < 11) {
                BleDevLog.m141b(str, "bp pressure value length < 11");
                return;
            }
            int i3 = 0;
            int i4 = 0;
            while (true) {
                int[] iArr = this.f1077r;
                if (i3 < iArr.length) {
                    int i5 = i3 * 2;
                    iArr[i3] = ((bArr2[i5 + 1] & UByte.MAX_VALUE) << 8) + (bArr2[i5 + i2] & UByte.MAX_VALUE);
                    int i6 = this.f1074o;
                    String str4 = str;
                    if (i6 < 30) {
                        if (i6 > i) {
                            this.f1075p += (long) iArr[i3];
                        }
                        this.f1067h = iArr[i3];
                    } else if (i6 == 30) {
                        m236g();
                        this.f1067h = (int) (this.f1075p / 20);
                    }
                    int[] iArr2 = this.f1077r;
                    iArr2[i3] = (int) (((double) (this.f1068i * ((long) Math.abs(iArr2[i3] - this.f1067h)))) / Math.pow(2.0d, 16.0d));
                    i4 = this.f1077r[i3];
                    OnBpDataListener onBpDataListener = this.f1085z;
                    if (onBpDataListener != null) {
                        onBpDataListener.onRcvPressure(i4);
                    }
                    int i7 = this.f1074o + 1;
                    this.f1074o = i7;
                    if (this.f1056D || this.f1058F || i7 <= 30 || m219a(i4)) {
                        i3++;
                        str = str4;
                        i = 9;
                        i2 = 2;
                    } else {
                        return;
                    }
                } else {
                    String str5 = str;
                    OnCuffLeakTestListener onCuffLeakTestListener = this.f1057E;
                    if (onCuffLeakTestListener != null) {
                        onCuffLeakTestListener.mo27386a(i4 / 100);
                    }
                    if (System.currentTimeMillis() - this.f1053A >= 8000 && i4 < 500 && !this.f1058F) {
                        BleDevLog.m141b(str5, "No-load leak");
                        stop();
                        OnBpResultListener onBpResultListener = this.f1084y;
                        if (onBpResultListener != null) {
                            onBpResultListener.onLeakError(0);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
        }
    }

    public void setOnBpDataListener(OnBpDataListener onBpDataListener) {
        this.f1085z = onBpDataListener;
    }

    public void setOnBpResultListener(OnBpResultListener onBpResultListener) {
        this.f1084y = onBpResultListener;
    }

    public void start(Pair... pairArr) {
        super.start(pairArr);
        if (pairArr.length > 0 && 666 == ((Integer) pairArr[0].first).intValue() && (pairArr[0].second instanceof Boolean)) {
            this.f1058F = ((Boolean) pairArr[0].second).booleanValue();
        }
        m233e();
    }

    public void stop() {
        super.stop();
        this.f1054B = true;
        this.mCommunicate.mo27588a((byte) 1, new byte[]{7});
        this.mCommunicate.mo27587a();
        BleDevLog.m142c("BpTask", "stop");
    }
}
