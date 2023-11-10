package p014b.p015a.p016a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.linktop.infs.OnHRVResultListener;
import com.linktop.utils.BleDevLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import lib.p033lt.p034ox.LibOX;

/* renamed from: b.a.a.a */
public class C0942a extends Thread {

    /* renamed from: a */
    public final int f221a;

    /* renamed from: b */
    public final int f222b;

    /* renamed from: c */
    public final int f223c;

    /* renamed from: d */
    public final int f224d;

    /* renamed from: e */
    public final int f225e;

    /* renamed from: f */
    public final int f226f;

    /* renamed from: g */
    public final OnHRVResultListener f227g;

    /* renamed from: h */
    public final List<Integer> f228h = new ArrayList();

    /* renamed from: i */
    public final List<C0947c> f229i = new ArrayList();

    /* renamed from: j */
    public final double[] f230j;

    /* renamed from: k */
    public final C0944b f231k;

    /* renamed from: l */
    public int f232l;

    /* renamed from: m */
    public boolean f233m = false;

    /* renamed from: n */
    public int f234n = 0;

    /* renamed from: o */
    public int f235o = 0;

    /* renamed from: p */
    public boolean f236p = true;

    /* renamed from: q */
    public C0947c f237q;

    /* renamed from: r */
    public C0947c f238r;

    /* renamed from: s */
    public boolean f239s;

    /* renamed from: t */
    public boolean f240t;

    /* renamed from: b.a.a.a$a */
    public static class C0943a {

        /* renamed from: a */
        public final double f241a;

        /* renamed from: b */
        public final double f242b;

        public C0943a(double d, double d2) {
            this.f241a = d;
            this.f242b = d2;
        }
    }

    /* renamed from: b.a.a.a$b */
    public class C0944b extends Thread {

        /* renamed from: a */
        public final LinkedList<C0943a> f243a = new LinkedList<>();

        /* renamed from: b */
        public final OnHRVResultListener f244b;

        /* renamed from: c */
        public boolean f245c;

        /* renamed from: d */
        public double f246d;

        /* renamed from: e */
        public double f247e;

        /* renamed from: f */
        public double f248f;

        /* renamed from: g */
        public double f249g;

        /* renamed from: h */
        public double f250h;

        /* renamed from: i */
        public double f251i;

        /* renamed from: j */
        public double f252j;

        /* renamed from: k */
        public int f253k;

        /* renamed from: l */
        public int f254l;

        /* renamed from: m */
        public int f255m;

        /* renamed from: n */
        public int f256n;

        /* renamed from: o */
        public int f257o;

        /* renamed from: p */
        public final double[] f258p = new double[31];

        /* renamed from: q */
        public final double[] f259q = new double[31];

        /* renamed from: r */
        public final LinkedList<Double> f260r = new LinkedList<>();

        /* renamed from: s */
        public int f261s;

        /* renamed from: t */
        public double f262t;

        /* renamed from: u */
        public double f263u;

        /* renamed from: v */
        public double f264v;

        /* renamed from: w */
        public final Handler f265w = new C0945a(Looper.getMainLooper());

        /* renamed from: b.a.a.a$b$a */
        public class C0945a extends Handler {
            public C0945a(Looper looper) {
                super(looper);
            }

            public void handleMessage(Message message) {
                super.handleMessage(message);
                OnHRVResultListener onHRVResultListener = C0944b.this.f244b;
                if (onHRVResultListener != null) {
                    onHRVResultListener.onHRVResult(message.what, message.obj);
                }
            }
        }

        public C0944b(int i, OnHRVResultListener onHRVResultListener) {
            this.f253k = i;
            this.f244b = onHRVResultListener;
        }

        /* renamed from: a */
        public final void mo12966a(double d, double d2) {
            double d3;
            double d4;
            double d5 = d;
            this.f246d += d5;
            int size = this.f260r.size();
            double d6 = (double) size;
            double d7 = this.f246d / d6;
            int i = C0942a.this.f223c;
            double d8 = this.f247e;
            if (d8 != 0.0d) {
                this.f248f += Math.pow(d5 - d8, 2.0d);
            }
            this.f247e = d5;
            if (size % 5 == 0) {
                int i2 = (int) (((double) C0942a.this.f222b) / d7);
                if (this.f254l != i2) {
                    this.f254l = i2;
                    mo12967a(601, (Object) Integer.valueOf(i2));
                    int i3 = this.f253k;
                    if (i3 >= 40 && i3 <= 120) {
                        double a = C0946b.m88a(2, (((double) i2) - ((double) i3)) / 5.5d);
                        if (a < 0.0d) {
                            a = 0.0d;
                        } else if (a > 3.0d) {
                            a = 3.0d;
                        }
                        mo12967a(605, (Object) Double.valueOf(a));
                    }
                }
                Iterator it = this.f260r.iterator();
                double d9 = 0.0d;
                while (it.hasNext()) {
                    d9 += Math.pow(((Double) it.next()).doubleValue() - d7, 2.0d);
                    d7 = d7;
                }
                int sqrt = (int) (((double) C0942a.this.f223c) * Math.sqrt(d9 / d6));
                if (this.f255m != sqrt) {
                    this.f255m = sqrt;
                    mo12967a(602, (Object) Integer.valueOf(sqrt));
                }
                int sqrt2 = (int) (((double) C0942a.this.f223c) * Math.sqrt(this.f248f / ((double) (size - 1))));
                if (this.f256n != sqrt2) {
                    this.f256n = sqrt2;
                    mo12967a(603, (Object) Integer.valueOf(sqrt2));
                }
                BleDevLog.m142c("HRVCal", "toCalTimeDomain meanHR:" + i2 + ", sdnn:" + sqrt + ", rmssd:" + sqrt2);
            }
            int i4 = this.f261s;
            double[] dArr = this.f258p;
            if (i4 < dArr.length) {
                dArr[i4] = d5;
                this.f259q[i4] = d2;
                this.f261s = i4 + 1;
            } else {
                System.arraycopy(dArr, 1, dArr, 0, dArr.length - 1);
                double[] dArr2 = this.f258p;
                dArr2[dArr2.length - 1] = d5;
                double[] dArr3 = this.f259q;
                System.arraycopy(dArr3, 1, dArr3, 0, dArr3.length - 1);
                double[] dArr4 = this.f259q;
                dArr4[dArr4.length - 1] = d2;
            }
            int i5 = this.f261s;
            double[] dArr5 = this.f258p;
            if (i5 == dArr5.length) {
                double[] dArr6 = (double[]) dArr5.clone();
                double[] dArr7 = (double[]) this.f259q.clone();
                Arrays.sort(dArr6);
                d3 = dArr6[15];
                Arrays.sort(dArr7);
                d4 = dArr7[15];
            } else {
                d4 = 0.0d;
                d3 = 0.0d;
            }
            if (d3 != 0.0d && d4 != 0.0d) {
                double d10 = this.f262t + d3;
                this.f262t = d10;
                double d11 = this.f263u + d4;
                this.f263u = d11;
                double d12 = this.f264v + 1.0d;
                this.f264v = d12;
                double d13 = this.f249g;
                if (d13 == 0.0d || d13 < d3) {
                    this.f249g = d3;
                }
                double d14 = this.f250h;
                if (d14 == 0.0d || d14 > d3) {
                    this.f250h = d3;
                }
                double d15 = this.f251i;
                if (d15 == 0.0d || d15 < d4) {
                    this.f251i = d4;
                }
                double d16 = this.f252j;
                if (d16 == 0.0d || d16 > d4) {
                    this.f252j = d4;
                }
                double d17 = this.f249g;
                double d18 = this.f250h;
                if (d17 != d18) {
                    double d19 = this.f251i;
                    String str = "HRVCal";
                    double d20 = this.f252j;
                    if (d19 != d20) {
                        double d21 = ((d10 / d12) - d18) / (d17 - d18);
                        double d22 = ((d11 / d12) - d20) / (d19 - d20);
                        if (d21 > 0.0d && d22 > 0.0d) {
                            int i6 = (int) (100.0d - (((d22 * 0.7d) + (d21 * 0.3d)) * 100.0d));
                            BleDevLog.m142c(str, "toCalTimeDomain psi:" + i6);
                            if (this.f257o != i6) {
                                this.f257o = i6;
                                mo12967a(604, (Object) Integer.valueOf(i6));
                            }
                        }
                    }
                }
            }
        }

        /* renamed from: a */
        public final void mo12967a(int i, Object obj) {
            Message.obtain(this.f265w, i, obj).sendToTarget();
        }

        public void interrupt() {
            this.f245c = false;
            this.f265w.removeCallbacksAndMessages((Object) null);
            super.interrupt();
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(7:5|6|(2:8|9)|10|11|12|13) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0017 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0036 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r6 = this;
                super.run()
            L_0x0003:
                boolean r0 = r6.f245c
                if (r0 == 0) goto L_0x003b
                java.util.LinkedList<b.a.a.a$a> r0 = r6.f243a
                monitor-enter(r0)
                java.util.LinkedList<b.a.a.a$a> r1 = r6.f243a     // Catch:{ all -> 0x0038 }
                boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0038 }
                if (r1 == 0) goto L_0x0017
                java.util.LinkedList<b.a.a.a$a> r1 = r6.f243a     // Catch:{ InterruptedException -> 0x0017 }
                r1.wait()     // Catch:{ InterruptedException -> 0x0017 }
            L_0x0017:
                java.util.LinkedList<b.a.a.a$a> r1 = r6.f243a     // Catch:{ NoSuchElementException -> 0x0036 }
                java.lang.Object r1 = r1.getFirst()     // Catch:{ NoSuchElementException -> 0x0036 }
                b.a.a.a$a r1 = (p014b.p015a.p016a.C0942a.C0943a) r1     // Catch:{ NoSuchElementException -> 0x0036 }
                java.util.LinkedList<java.lang.Double> r2 = r6.f260r     // Catch:{ NoSuchElementException -> 0x0036 }
                double r3 = r1.f241a     // Catch:{ NoSuchElementException -> 0x0036 }
                java.lang.Double r3 = java.lang.Double.valueOf(r3)     // Catch:{ NoSuchElementException -> 0x0036 }
                r2.add(r3)     // Catch:{ NoSuchElementException -> 0x0036 }
                double r2 = r1.f241a     // Catch:{ NoSuchElementException -> 0x0036 }
                double r4 = r1.f242b     // Catch:{ NoSuchElementException -> 0x0036 }
                r6.mo12966a((double) r2, (double) r4)     // Catch:{ NoSuchElementException -> 0x0036 }
                java.util.LinkedList<b.a.a.a$a> r1 = r6.f243a     // Catch:{ NoSuchElementException -> 0x0036 }
                r1.removeFirst()     // Catch:{ NoSuchElementException -> 0x0036 }
            L_0x0036:
                monitor-exit(r0)     // Catch:{ all -> 0x0038 }
                goto L_0x0003
            L_0x0038:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0038 }
                throw r1
            L_0x003b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p014b.p015a.p016a.C0942a.C0944b.run():void");
        }

        public synchronized void start() {
            this.f245c = true;
            super.start();
        }
    }

    public C0942a(int i, int i2, OnHRVResultListener onHRVResultListener) {
        this.f227g = onHRVResultListener;
        this.f231k = new C0944b(i2, onHRVResultListener);
        this.f222b = i * 60;
        this.f223c = 1000 / i;
        int i3 = 500 / i;
        int i4 = 150 / i3;
        this.f221a = i4;
        this.f224d = 40 / i3;
        this.f225e = 180 / i3;
        this.f226f = 80;
        this.f230j = new double[((i4 * 2) + 1)];
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:61|62|63|64|65|66) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:65:0x0103 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int mo12960a(p014b.p015a.p016a.C0947c r11) {
        /*
            r10 = this;
            double r0 = r11.f269b
            r2 = -1
            r3 = 1
            r4 = 0
            int r6 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r6 <= 0) goto L_0x000c
            r0 = 1
            goto L_0x000d
        L_0x000c:
            r0 = -1
        L_0x000d:
            int r1 = r10.f235o
            if (r1 == 0) goto L_0x0137
            if (r0 != r1) goto L_0x001a
            java.util.List<b.a.a.c> r1 = r10.f229i
            r1.add(r11)
            goto L_0x0137
        L_0x001a:
            java.util.List<b.a.a.c> r11 = r10.f229i
            boolean r11 = r11.isEmpty()
            r1 = 0
            if (r11 == 0) goto L_0x0024
            return r1
        L_0x0024:
            if (r0 != r3) goto L_0x0071
            java.util.List<b.a.a.c> r11 = r10.f229i
            java.lang.Object r11 = r11.get(r1)
            b.a.a.c r11 = (p014b.p015a.p016a.C0947c) r11
            r2 = 1
        L_0x002f:
            java.util.List<b.a.a.c> r4 = r10.f229i
            int r4 = r4.size()
            if (r2 >= r4) goto L_0x004b
            java.util.List<b.a.a.c> r4 = r10.f229i
            java.lang.Object r4 = r4.get(r2)
            b.a.a.c r4 = (p014b.p015a.p016a.C0947c) r4
            double r5 = r11.f269b
            double r7 = r4.f269b
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 <= 0) goto L_0x0048
            r11 = r4
        L_0x0048:
            int r2 = r2 + 1
            goto L_0x002f
        L_0x004b:
            boolean r2 = r10.f236p
            if (r2 == 0) goto L_0x0053
            r10.f236p = r1
            goto L_0x0132
        L_0x0053:
            b.a.a.c r1 = r10.f237q
            if (r1 == 0) goto L_0x0063
            long r4 = r11.f268a
            long r1 = r1.f268a
            long r4 = r4 - r1
            int r1 = r10.f225e
            long r1 = (long) r1
            int r6 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r6 <= 0) goto L_0x0132
        L_0x0063:
            double r1 = r11.f269b
            r4 = -4586634745500139520(0xc059000000000000, double:-100.0)
            int r6 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r6 >= 0) goto L_0x0132
            r10.f237q = r11
            r10.f239s = r3
            goto L_0x0132
        L_0x0071:
            boolean r11 = r10.f239s
            if (r11 == 0) goto L_0x0132
            java.util.List<b.a.a.c> r11 = r10.f229i
            int r11 = r11.size()
            int r4 = r10.f224d
            if (r11 <= r4) goto L_0x0132
            java.util.List<b.a.a.c> r11 = r10.f229i
            java.lang.Object r11 = r11.get(r1)
            b.a.a.c r11 = (p014b.p015a.p016a.C0947c) r11
            r1 = 1
        L_0x0088:
            java.util.List<b.a.a.c> r4 = r10.f229i
            int r4 = r4.size()
            if (r1 >= r4) goto L_0x00a4
            java.util.List<b.a.a.c> r4 = r10.f229i
            java.lang.Object r4 = r4.get(r1)
            b.a.a.c r4 = (p014b.p015a.p016a.C0947c) r4
            double r5 = r11.f269b
            double r7 = r4.f269b
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 >= 0) goto L_0x00a1
            r11 = r4
        L_0x00a1:
            int r1 = r1 + 1
            goto L_0x0088
        L_0x00a4:
            b.a.a.c r1 = r10.f238r
            if (r1 == 0) goto L_0x00b4
            long r4 = r11.f268a
            long r6 = r1.f268a
            long r4 = r4 - r6
            int r6 = r10.f225e
            long r6 = (long) r6
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 <= 0) goto L_0x0132
        L_0x00b4:
            if (r1 == 0) goto L_0x00bd
            long r4 = r11.f268a
            long r6 = r1.f268a
            long r4 = r4 - r6
            int r1 = (int) r4
            goto L_0x00be
        L_0x00bd:
            r1 = -1
        L_0x00be:
            r11.getClass()
            r10.f238r = r11
            if (r1 == r2) goto L_0x0132
            long r4 = r11.f268a
            b.a.a.c r2 = r10.f237q
            long r6 = r2.f268a
            long r4 = r4 - r6
            r6 = 0
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 <= 0) goto L_0x0132
            double r4 = r11.f269b
            double r6 = r2.f269b
            double r4 = r4 - r6
            r11 = 150(0x96, float:2.1E-43)
            if (r1 < r11) goto L_0x011d
            r11 = 750(0x2ee, float:1.051E-42)
            if (r1 > r11) goto L_0x011d
            r6 = 4641240890982006784(0x4069000000000000, double:200.0)
            int r11 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r11 < 0) goto L_0x011d
            r6 = 4684393973593145344(0x41024f8000000000, double:150000.0)
            int r11 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r11 > 0) goto L_0x011d
            b.a.a.a$b r11 = r10.f231k
            double r6 = (double) r1
            java.util.LinkedList<b.a.a.a$a> r2 = r11.f243a
            monitor-enter(r2)
            b.a.a.a$a r8 = new b.a.a.a$a     // Catch:{ all -> 0x011a }
            r8.<init>(r6, r4)     // Catch:{ all -> 0x011a }
            java.util.LinkedList<b.a.a.a$a> r6 = r11.f243a     // Catch:{ all -> 0x011a }
            r6.add(r8)     // Catch:{ all -> 0x011a }
            java.util.LinkedList<b.a.a.a$a> r11 = r11.f243a     // Catch:{ Exception -> 0x0103 }
            r11.notify()     // Catch:{ Exception -> 0x0103 }
        L_0x0103:
            monitor-exit(r2)     // Catch:{ all -> 0x011a }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r2 = "AvailableEle ppi:"
            r11.append(r2)
            r11.append(r1)
            java.lang.String r1 = ", ppga:"
            r11.append(r1)
            r11.append(r4)
            goto L_0x0132
        L_0x011a:
            r11 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x011a }
            throw r11
        L_0x011d:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r2 = "SkipEle ppi:"
            r11.append(r2)
            r11.append(r1)
            java.lang.String r1 = ", ppga:"
            r11.append(r1)
            r11.append(r4)
        L_0x0132:
            java.util.List<b.a.a.c> r11 = r10.f229i
            r11.clear()
        L_0x0137:
            r10.f235o = r0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: p014b.p015a.p016a.C0942a.mo12960a(b.a.a.c):int");
    }

    /* renamed from: a */
    public void mo12962a(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("ppg:");
        sb.append(i);
        synchronized (this.f228h) {
            this.f228h.add(Integer.valueOf(i));
            if (this.f240t) {
                this.f240t = false;
                this.f228h.notify();
            }
        }
    }

    public void interrupt() {
        this.f233m = false;
        this.f231k.interrupt();
        super.interrupt();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:5|6|(2:8|9)|10|11|(1:13)(1:14)|15) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0013 */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0019  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x001d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r2 = this;
            super.run()
        L_0x0003:
            boolean r0 = r2.f233m
            if (r0 == 0) goto L_0x0025
            java.util.List<java.lang.Integer> r0 = r2.f228h
            monitor-enter(r0)
            boolean r1 = r2.f240t     // Catch:{ all -> 0x0022 }
            if (r1 == 0) goto L_0x0013
            java.util.List<java.lang.Integer> r1 = r2.f228h     // Catch:{ InterruptedException -> 0x0013 }
            r1.wait()     // Catch:{ InterruptedException -> 0x0013 }
        L_0x0013:
            b.a.a.c r1 = r2.mo12961a()     // Catch:{ all -> 0x0022 }
            if (r1 != 0) goto L_0x001d
            r1 = 1
            r2.f240t = r1     // Catch:{ all -> 0x0022 }
            goto L_0x0020
        L_0x001d:
            r2.mo12960a((p014b.p015a.p016a.C0947c) r1)     // Catch:{ all -> 0x0022 }
        L_0x0020:
            monitor-exit(r0)     // Catch:{ all -> 0x0022 }
            goto L_0x0003
        L_0x0022:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0022 }
            throw r1
        L_0x0025:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p014b.p015a.p016a.C0942a.run():void");
    }

    public synchronized void start() {
        this.f233m = true;
        super.start();
        this.f231k.start();
    }

    /* renamed from: a */
    public final synchronized C0947c mo12961a() {
        C0947c cVar;
        int size = this.f228h.size();
        int i = this.f226f;
        cVar = null;
        if (size > i) {
            int[] iArr = new int[i];
            int i2 = 0;
            while (i2 < i) {
                try {
                    iArr[i2] = this.f228h.get(i2).intValue();
                    i2++;
                } catch (Exception e) {
                    BleDevLog.m140a("toSignalFilter", "break:" + e.getMessage());
                    return null;
                }
            }
            double lowPassFilter250 = LibOX.lowPassFilter250(iArr);
            int i3 = this.f232l;
            double[] dArr = this.f230j;
            if (i3 < dArr.length) {
                dArr[i3] = lowPassFilter250;
                this.f232l = i3 + 1;
            } else {
                System.arraycopy(dArr, 1, dArr, 0, dArr.length - 1);
                double[] dArr2 = this.f230j;
                dArr2[dArr2.length - 1] = lowPassFilter250;
            }
            int i4 = this.f232l;
            double[] dArr3 = this.f230j;
            if (i4 == dArr3.length) {
                double d = 0.0d;
                for (double d2 : dArr3) {
                    d += d2;
                }
                double length = d / ((double) dArr3.length);
                int i5 = this.f234n;
                double d3 = (double) ((int) (this.f230j[this.f221a] - length));
                C0947c cVar2 = new C0947c((long) i5, d3, 0);
                this.f234n = i5 + 1;
                OnHRVResultListener onHRVResultListener = this.f227g;
                if (onHRVResultListener != null) {
                    onHRVResultListener.onSignalData(1, (int) d3);
                }
                cVar = cVar2;
            }
            this.f228h.remove(0);
        }
        return cVar;
    }
}
