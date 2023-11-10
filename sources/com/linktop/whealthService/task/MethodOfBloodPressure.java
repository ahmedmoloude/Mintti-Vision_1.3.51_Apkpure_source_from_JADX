package com.linktop.whealthService.task;

import com.linktop.constant.ResultData;
import com.linktop.utils.BleDevLog;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public final class MethodOfBloodPressure {

    /* renamed from: a */
    private final BpTask f1106a;

    /* renamed from: b */
    private final ResultData f1107b = new ResultData();

    /* renamed from: c */
    public int f1108c = 0;

    /* renamed from: d */
    public int f1109d = 0;

    /* renamed from: e */
    private int[] f1110e;

    /* renamed from: f */
    private int[] f1111f;

    /* renamed from: g */
    private int[] f1112g;

    /* renamed from: h */
    private int f1113h = 0;

    /* renamed from: i */
    private int f1114i = 0;

    /* renamed from: j */
    private int f1115j = 0;

    /* renamed from: k */
    private int f1116k = 0;

    /* renamed from: l */
    private final int[] f1117l = new int[8000];

    /* renamed from: m */
    private final int[] f1118m = new int[8000];

    /* renamed from: n */
    private final int[] f1119n = new int[8000];

    /* renamed from: o */
    private final int[] f1120o = new int[800];

    /* renamed from: p */
    private final int[] f1121p = new int[800];

    /* renamed from: q */
    private final int[] f1122q = new int[8000];

    /* renamed from: r */
    private final int[] f1123r = new int[800];

    /* renamed from: s */
    private int f1124s = 0;

    /* renamed from: t */
    private int f1125t = 0;

    /* renamed from: u */
    private int f1126u = 0;

    /* renamed from: v */
    private int f1127v = 0;

    /* renamed from: w */
    private int f1128w = 0;

    /* renamed from: x */
    private final int[] f1129x = new int[8000];

    /* renamed from: y */
    private final int[] f1130y = new int[8000];

    /* renamed from: z */
    public int[] f1131z = new int[500];

    public static class TempObject {

        /* renamed from: a */
        public int f1132a;

        /* renamed from: b */
        public int f1133b;

        public TempObject(int i, int i2) {
            this.f1132a = i;
            this.f1133b = i2;
        }

        public String toString() {
            return "TempObject{index=" + this.f1132a + ", value=" + this.f1133b + '}';
        }
    }

    static {
        System.loadLibrary("HealthyMonitor");
    }

    public MethodOfBloodPressure(BpTask bpTask) {
        this.f1106a = bpTask;
    }

    /* renamed from: a */
    private double m252a(boolean z, int i) {
        return z ? mo27525a(i) : mo27531b(i);
    }

    /* renamed from: a */
    private void m254a(int[] iArr, int i, int[] iArr2) {
        LinkedList linkedList = new LinkedList();
        StringBuilder sb = new StringBuilder();
        int i2 = i;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            if (iArr[i5] >= 0) {
                if (i4 != iArr[i5]) {
                    i4 = iArr[i5];
                    linkedList.add(new TempObject(i5, iArr[i5]));
                    sb.append(iArr[i5]);
                    sb.append(",");
                }
            } else if (linkedList.size() > 10) {
                sb.setLength(0);
                TempObject tempObject = (TempObject) Collections.max(linkedList, Comparator.comparingInt($$Lambda$MethodOfBloodPressure$dvFydvbbNIZQi0tCA_kzZSF6mw.INSTANCE));
                linkedList.clear();
                if (i3 >= 2) {
                    int i6 = iArr[iArr2[i3 - 2]];
                    int i7 = iArr[iArr2[i3 - 1]];
                    int i8 = iArr[tempObject.f1132a];
                    int i9 = i7 + 3;
                    if (i9 < i6 || i9 < i8) {
                        float f = (float) i6;
                        if (((double) ((((float) i7) * 1.0f) / f)) * 1.0d < 0.6d && ((double) ((((float) i8) * 1.0f) / f)) * 1.0d >= 0.6d) {
                            i3--;
                        }
                    }
                }
                iArr2[i3] = tempObject.f1132a;
                i3++;
            }
        }
        this.f1113h = i3;
        if (i3 != this.f1114i) {
            this.f1115j = 0;
            this.f1114i = i3;
            return;
        }
        this.f1115j++;
    }

    /* renamed from: a */
    private int[] m255a(int[] iArr, int i) {
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }

    /* renamed from: b */
    private double m256b(boolean z, int i) {
        return z ? m257c(i) : m259d(i);
    }

    /* renamed from: c */
    private double m257c(int i) {
        int i2 = i / 100;
        if (i2 > 200) {
            return 0.54d;
        }
        if (i2 > 150) {
            return 0.55d;
        }
        if (i2 > 135) {
            return 0.58d;
        }
        if (i2 > 120) {
            return 0.6d;
        }
        if (i2 > 110) {
            return 0.7d;
        }
        if (i2 > 90) {
            return 0.74d;
        }
        return i2 > 70 ? 0.72d : 0.65d;
    }

    /* renamed from: c */
    private void m258c() {
        this.f1110e = new int[500];
        this.f1111f = new int[500];
        this.f1112g = new int[500];
        for (int i = 0; i < this.f1113h; i++) {
            int[] iArr = this.f1131z;
            if (iArr[i] - 214 >= 0) {
                this.f1110e[i] = this.f1119n[iArr[i] - 214];
                this.f1111f[i] = this.f1130y[iArr[i]];
            }
            for (int i2 = 0; i2 < this.f1113h; i2++) {
                int[] iArr2 = this.f1130y;
                int[] iArr3 = this.f1131z;
                if (iArr2[iArr3[this.f1108c]] < iArr2[iArr3[i2]]) {
                    this.f1108c = i2;
                }
            }
        }
    }

    /* renamed from: d */
    private double m259d(int i) {
        int i2 = i / 100;
        if (i2 > 200) {
            return 0.8d;
        }
        if (i2 > 150) {
            return 0.82d;
        }
        if (i2 <= 135) {
            if (i2 > 120) {
                return 0.88d;
            }
            if (i2 > 110) {
                return 0.94d;
            }
            if (i2 > 90) {
                return 0.96d;
            }
            if (i2 > 70) {
                return 0.9d;
            }
        }
        return 0.85d;
    }

    /* renamed from: a */
    public double mo27525a(int i) {
        int i2 = i / 100;
        if (i2 <= 180) {
            if (i2 > 140 || i2 > 120) {
                return 0.65d;
            }
            if (i2 > 60 && i2 <= 90) {
                return 0.7d;
            }
            if (i2 > 90 && i2 <= 100) {
                return (((double) (100 - (i2 - 90))) * 0.77d) / 100.0d;
            }
            if (i2 > 100) {
                return 0.6160000000000001d;
            }
            return i2 > 50 ? 0.6d : 0.5d;
        }
    }

    /* renamed from: a */
    public int mo27526a() {
        return this.f1115j;
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x0072 A[EDGE_INSN: B:53:0x0072->B:25:0x0072 ?: BREAK  , SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int mo27527a(boolean r10) {
        /*
            r9 = this;
            int[] r0 = r9.f1110e
            int r1 = r9.f1108c
            r0 = r0[r1]
            double r0 = r9.m252a((boolean) r10, (int) r0)
            int[] r2 = r9.f1111f
            int r3 = r9.f1108c
            r2 = r2[r3]
            double r4 = (double) r2
            double r4 = r4 * r0
            int r0 = (int) r4
            r1 = 3
            r2 = -1
            r4 = 2
            r5 = 0
            r6 = 1
            if (r10 != 0) goto L_0x0055
            r7 = 4
            if (r3 > r7) goto L_0x0055
            if (r3 != r7) goto L_0x002c
            int[] r10 = r9.f1110e
            r1 = r10[r6]
            r10 = r10[r4]
            int r1 = r1 + r10
            int r1 = r1 / r4
            r9.mo27529a((int) r4, (int) r1, (int) r0)
            goto L_0x0054
        L_0x002c:
            if (r3 != r1) goto L_0x0036
            int[] r10 = r9.f1110e
            r1 = r10[r6]
            r9.mo27529a((int) r6, (int) r1, (int) r0)
            goto L_0x0054
        L_0x0036:
            int[] r10 = r9.f1110e
            if (r3 != r4) goto L_0x0044
            r1 = r10[r5]
            r10 = r10[r6]
            int r1 = r1 + r10
            int r1 = r1 / r4
        L_0x0040:
            r9.mo27529a((int) r5, (int) r1, (int) r0)
            goto L_0x0054
        L_0x0044:
            if (r3 != 0) goto L_0x0051
            r1 = r10[r5]
            r10 = r10[r5]
            int r10 = r10 / 10
            int r1 = r1 - r10
            r9.mo27529a((int) r2, (int) r1, (int) r0)
            goto L_0x0054
        L_0x0051:
            r1 = r10[r5]
            goto L_0x0040
        L_0x0054:
            return r1
        L_0x0055:
            int[] r7 = r9.f1111f
            r8 = r7[r3]
            if (r8 < r0) goto L_0x0060
            if (r3 <= 0) goto L_0x0060
            int r3 = r3 + -1
            goto L_0x0055
        L_0x0060:
            if (r3 <= 0) goto L_0x0072
            int r8 = r3 + -1
            r7 = r7[r8]
            if (r7 < r0) goto L_0x0072
        L_0x0068:
            int r3 = r3 + r2
            int[] r7 = r9.f1111f
            r7 = r7[r3]
            if (r7 < r0) goto L_0x0072
            if (r3 <= 0) goto L_0x0072
            goto L_0x0068
        L_0x0072:
            int[] r2 = r9.f1111f
            r2 = r2[r3]
            if (r2 <= r0) goto L_0x00be
        L_0x0078:
            int[] r10 = r9.f1111f
            int r1 = r9.f1108c
            int r2 = r1 + r6
            r10 = r10[r2]
            if (r10 <= r0) goto L_0x008b
            int r10 = r2 + 1
            int r3 = r9.f1113h
            if (r10 >= r3) goto L_0x008b
            int r6 = r6 + 1
            goto L_0x0078
        L_0x008b:
            int[] r10 = r9.f1131z
            r3 = r10[r1]
            r2 = r10[r2]
            r10 = r10[r1]
            int r2 = r2 - r10
            int r3 = r3 - r2
            r10 = 214(0xd6, float:3.0E-43)
            if (r3 <= r10) goto L_0x00a6
            int[] r1 = r9.f1119n
            int r3 = r3 - r10
            r10 = r1[r3]
            r9.mo27529a((int) r5, (int) r10, (int) r0)
            int[] r10 = r9.f1119n
            r10 = r10[r3]
            return r10
        L_0x00a6:
            int[] r10 = r9.f1110e
            r2 = r10[r1]
            r10 = r10[r1]
            int r10 = r10 / 10
            int r2 = r2 - r10
            r9.mo27529a((int) r5, (int) r2, (int) r0)
            int[] r10 = r9.f1110e
            int r0 = r9.f1108c
            r1 = r10[r0]
            r10 = r10[r0]
            int r10 = r10 / 10
            int r1 = r1 - r10
            return r1
        L_0x00be:
            if (r10 != 0) goto L_0x00c3
            if (r3 > r4) goto L_0x00c3
            goto L_0x00c4
        L_0x00c3:
            r1 = r3
        L_0x00c4:
            int[] r10 = r9.f1110e
            if (r1 != 0) goto L_0x00dc
            r2 = r10[r1]
            r10 = r10[r1]
            int r10 = r10 / 10
            int r2 = r2 - r10
            r9.mo27529a((int) r1, (int) r2, (int) r0)
            int[] r10 = r9.f1110e
            r0 = r10[r1]
            r10 = r10[r1]
            int r10 = r10 / 10
            int r0 = r0 - r10
            return r0
        L_0x00dc:
            if (r1 != r6) goto L_0x00e9
            int r1 = r1 - r6
            r10 = r10[r1]
            r9.mo27529a((int) r5, (int) r10, (int) r0)
            int[] r10 = r9.f1110e
            r10 = r10[r1]
            return r10
        L_0x00e9:
            int r1 = r1 - r6
            r10 = r10[r1]
            r9.mo27529a((int) r1, (int) r10, (int) r0)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.linktop.whealthService.task.MethodOfBloodPressure.mo27527a(boolean):int");
    }

    /* renamed from: a */
    public int mo27528a(int[] iArr, int[] iArr2, int i) {
        int i2 = i / 1;
        for (int i3 = 0; i3 < i; i3++) {
            iArr[i3 / 1] = iArr2[i3];
        }
        return i2;
    }

    /* renamed from: a */
    public void mo27529a(int i, int i2, int i3) {
        ResultData resultData = this.f1107b;
        resultData.f971n = i3;
        resultData.f960c = i;
        resultData.f962e = i2;
    }

    /* renamed from: a */
    public void mo27530a(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        ResultData resultData = this.f1107b;
        resultData.f969l = i5;
        resultData.f970m = i6;
        resultData.f972o = i7;
        resultData.f968k = i4;
        resultData.f958a = i;
        resultData.f959b = i2;
        resultData.f961d = i3;
    }

    /* renamed from: b */
    public double mo27531b(int i) {
        int i2 = i / 100;
        if (i2 > 180) {
            return 0.4d;
        }
        if (i2 <= 140) {
            if (i2 > 120) {
                return 0.5d;
            }
            if (i2 > 60 && i2 <= 90) {
                return 0.55d;
            }
            if (i2 > 90 && i2 <= 100) {
                return (((double) (100 - (i2 - 90))) * 0.6d) / 100.0d;
            }
            if (i2 > 100) {
                return 0.48d;
            }
            return i2 > 50 ? 0.45d : 0.38d;
        }
    }

    /* renamed from: b */
    public int mo27532b(boolean z) {
        int[] iArr;
        int i;
        int i2;
        if (this.f1113h < 3) {
            return 0;
        }
        double b = m256b(z, this.f1110e[this.f1108c]);
        int[] iArr2 = this.f1111f;
        int i3 = this.f1108c;
        int i4 = (int) (((double) iArr2[i3]) * b);
        if (iArr2[i3] >= 10) {
            int i5 = this.f1113h;
            int i6 = i5 - 1;
            if (iArr2[i6] < i4 && i3 != i6 && i3 + 2 <= i5) {
                int i7 = i5 - 2;
                if (iArr2[i6] <= iArr2[i7] || iArr2[i7] <= iArr2[i5 - 3]) {
                    int i8 = 1;
                    while (true) {
                        iArr = this.f1111f;
                        i = this.f1108c;
                        i2 = i + i8;
                        if (iArr[i2] <= i4 || i2 + 1 >= this.f1113h) {
                            int i9 = i2 + 1;
                            int i10 = this.f1113h;
                        } else {
                            i8++;
                        }
                    }
                    int i92 = i2 + 1;
                    int i102 = this.f1113h;
                    if (i92 == i102) {
                        return 0;
                    }
                    int[] iArr3 = this.f1110e;
                    int i11 = iArr3[i2];
                    mo27530a(i102, i2, i11, i, iArr3[i], iArr[i], i4);
                    return i11;
                }
            }
        }
        return 0;
    }

    /* renamed from: b */
    public int mo27533b(int[] iArr, int[] iArr2, int i) {
        int i2 = i / 10;
        for (int i3 = 0; i3 < i; i3++) {
            if (i3 % 10 == 0) {
                iArr[i3 / 10] = iArr2[i3];
            }
        }
        return i2;
    }

    /* renamed from: b */
    public void mo27534b() {
        int i = this.f1127v;
        if (i == 0) {
            i = 428;
        }
        int i2 = this.f1116k / 1;
        this.f1127v = i2;
        nativeFIRBandPass218(this.f1122q, this.f1118m, i, i2);
        int i3 = this.f1126u;
        if (i3 == 0) {
            i3 = 0;
        }
        int i4 = this.f1116k;
        this.f1126u = i4 / 1;
        int i5 = i3;
        nativeAverageFilter(this.f1129x, this.f1122q, i5, i4, 5);
        nativeAverageFilter(this.f1130y, this.f1122q, i5, this.f1116k, 10);
        m254a(this.f1130y, this.f1116k / 1, this.f1131z);
        this.f1109d = 0;
        for (int i6 : this.f1131z) {
            if (i6 > 0) {
                this.f1109d++;
            }
        }
        m258c();
    }

    /* renamed from: c */
    public int mo27535c(boolean z) {
        int i;
        int i2;
        int i3 = this.f1113h;
        if (i3 >= 0) {
            System.arraycopy(this.f1111f, 0, this.f1112g, 0, i3);
        }
        nativeGaussianProcess(this.f1111f, this.f1108c, this.f1113h);
        int b = (int) (((double) this.f1111f[this.f1108c]) * m256b(z, this.f1110e[this.f1108c]));
        BleDevLog.m141b("Max", this.f1108c + "");
        int i4 = 1;
        while (true) {
            int[] iArr = this.f1111f;
            int i5 = this.f1108c + i;
            if (iArr[i5] <= b || i5 + 1 >= this.f1113h) {
                BleDevLog.m141b("Max1", this.f1108c + "");
                int i6 = this.f1108c + i + 1;
            } else {
                BleDevLog.m141b("temp_b[max+j]", this.f1111f[this.f1108c + i] + "");
                i4 = i + 1;
            }
        }
        BleDevLog.m141b("Max1", this.f1108c + "");
        int i62 = this.f1108c + i + 1;
        if (i62 < this.f1113h && this.f1111f[i62] > b) {
            i++;
            BleDevLog.m141b("Max2", this.f1108c + "");
            while (true) {
                int[] iArr2 = this.f1111f;
                int i7 = this.f1108c + i;
                if (iArr2[i7] <= b || i7 + 1 >= this.f1113h) {
                    break;
                }
                i++;
            }
        }
        int[] iArr3 = this.f1111f;
        int i8 = this.f1108c;
        int i9 = i8 + i;
        if ((iArr3[i9] == b || iArr3[i9] == b + 1) && (i2 = i9 + 1) < this.f1113h && iArr3[i9] - 2 < (iArr3[i9 - 1] + iArr3[i2]) / 2) {
            i++;
        }
        int[] iArr4 = this.f1110e;
        int i10 = i8 + i;
        int i11 = iArr4[i10];
        mo27530a(this.f1113h, i10, i11, i8, iArr4[i8], iArr3[i8], b);
        return i11;
    }

    /* renamed from: d */
    public int mo27536d() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = this.f1113h;
        if (i7 >= 30) {
            i4 = (i7 - 15) * 5860;
            int[] iArr = this.f1131z;
            i5 = iArr[i7 - 5];
            i6 = iArr[10];
        } else if (i7 >= 20) {
            i4 = (i7 - 13) * 5860;
            int[] iArr2 = this.f1131z;
            i5 = iArr2[i7 - 3];
            i6 = iArr2[10];
        } else {
            if (i7 >= 10) {
                i = (i7 - 6) * 5860;
                int[] iArr3 = this.f1131z;
                i2 = iArr3[i7 - 2];
                i3 = iArr3[4];
            } else if (i7 < 4) {
                return 60;
            } else {
                i = (i7 - 3) * 5860;
                int[] iArr4 = this.f1131z;
                i2 = iArr4[i7 - 1];
                i3 = iArr4[2];
            }
            return i / (i2 - i3);
        }
        return i4 / (i5 - i6);
    }

    /* renamed from: e */
    public ResultData mo27537e() {
        this.f1107b.f966i = m255a(this.f1130y, this.f1126u);
        this.f1107b.f965h = m255a(this.f1110e, this.f1113h);
        this.f1107b.f964g = m255a(this.f1111f, this.f1113h);
        this.f1107b.f963f = m255a(this.f1131z, this.f1113h);
        return this.f1107b;
    }

    /* renamed from: f */
    public void mo27538f() {
        this.f1127v = 0;
        this.f1128w = 0;
        this.f1124s = 0;
        this.f1125t = 0;
        this.f1126u = 0;
        this.f1108c = 0;
        this.f1113h = 0;
        this.f1114i = 0;
        this.f1115j = 0;
        this.f1109d = 0;
    }

    /* renamed from: g */
    public int mo27539g() {
        mo27533b(this.f1120o, this.f1118m, this.f1116k);
        int i = this.f1128w;
        if (i == 0) {
            i = 5;
        }
        int i2 = this.f1116k / 10;
        this.f1128w = i2;
        nativeIIRBandPass(this.f1123r, this.f1120o, i, i2);
        for (int i3 = 0; i3 < this.f1128w; i3++) {
            this.f1121p[i3] = this.f1120o[i3] - this.f1123r[i3];
        }
        int i4 = 0;
        for (int i5 = 0; i5 < 10; i5++) {
            i4 += this.f1121p[(this.f1128w - 10) + i5];
        }
        return i4;
    }

    /* renamed from: h */
    public void mo27540h() {
        int i = this.f1106a.f1069j;
        this.f1116k = i;
        int i2 = i / 1;
        int i3 = this.f1125t;
        int i4 = i3 == 0 ? 0 : i3;
        this.f1125t = i2;
        nativeAverageFilter(this.f1118m, BpTask.f1050H, i4, i, 5);
        mo27528a(this.f1119n, this.f1118m, this.f1116k);
    }

    public native int nativeAverageFilter(int[] iArr, int[] iArr2, int i, int i2, int i3);

    public native int nativeFIRBandPass218(int[] iArr, int[] iArr2, int i, int i2);

    public native void nativeGaussianProcess(int[] iArr, int i, int i2);

    public native int nativeIIRBandPass(int[] iArr, int[] iArr2, int i, int i2);
}
