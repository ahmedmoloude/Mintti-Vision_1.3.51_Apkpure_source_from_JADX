package p014b.p015a.p016a;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.itextpdf.text.html.HtmlTags;
import com.linktop.infs.OnSpO2ResultListener;
import com.p020kl.commonbase.constants.Constants;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import lib.p033lt.p034ox.LibOX;

/* renamed from: b.a.a.d */
public class C0948d extends Thread {

    /* renamed from: a */
    public final OnSpO2ResultListener f271a;

    /* renamed from: b */
    public final List<int[]> f272b = new ArrayList();

    /* renamed from: c */
    public final double[][] f273c = ((double[][]) Array.newInstance(double.class, new int[]{81, 2}));

    /* renamed from: d */
    public int f274d;

    /* renamed from: e */
    public long f275e;

    /* renamed from: f */
    public boolean f276f;

    /* renamed from: g */
    public final C0950b f277g;

    /* renamed from: h */
    public boolean f278h;

    /* renamed from: b.a.a.d$a */
    public static class C0949a {

        /* renamed from: a */
        public final List<C0947c> f279a = new ArrayList();

        /* renamed from: b */
        public int f280b;

        /* renamed from: c */
        public boolean f281c;

        /* renamed from: d */
        public C0947c f282d;

        /* renamed from: e */
        public int f283e;

        /* renamed from: f */
        public boolean f284f;

        public C0949a(String str) {
        }

        /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
            java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
            	at java.base/java.util.Objects.checkIndex(Objects.java:372)
            	at java.base/java.util.ArrayList.get(ArrayList.java:458)
            	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
            */
        /* renamed from: a */
        public synchronized double[] mo12976a(p014b.p015a.p016a.C0947c r12) {
            /*
                r11 = this;
                monitor-enter(r11)
                r0 = 0
                if (r12 == 0) goto L_0x00ab
                double r1 = r12.f269b     // Catch:{ all -> 0x00a8 }
                r3 = 0
                r5 = -1
                r6 = 1
                int r7 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r7 <= 0) goto L_0x0010
                r1 = 1
                goto L_0x0011
            L_0x0010:
                r1 = -1
            L_0x0011:
                int r2 = r11.f280b     // Catch:{ all -> 0x00a8 }
                if (r2 == 0) goto L_0x00a5
                if (r1 != r2) goto L_0x0020
                if (r1 != r6) goto L_0x00a5
                java.util.List<b.a.a.c> r2 = r11.f279a     // Catch:{ all -> 0x00a8 }
                r2.add(r12)     // Catch:{ all -> 0x00a8 }
                goto L_0x00a5
            L_0x0020:
                if (r1 != r5) goto L_0x00a0
                java.util.List<b.a.a.c> r12 = r11.f279a     // Catch:{ all -> 0x00a8 }
                int r12 = r12.size()     // Catch:{ all -> 0x00a8 }
                r2 = 22
                if (r12 <= r2) goto L_0x00a0
                java.util.List<b.a.a.c> r12 = r11.f279a     // Catch:{ all -> 0x00a8 }
                r2 = 0
                java.lang.Object r12 = r12.get(r2)     // Catch:{ all -> 0x00a8 }
                b.a.a.c r12 = (p014b.p015a.p016a.C0947c) r12     // Catch:{ all -> 0x00a8 }
                r3 = 1
            L_0x0036:
                java.util.List<b.a.a.c> r4 = r11.f279a     // Catch:{ all -> 0x00a8 }
                int r4 = r4.size()     // Catch:{ all -> 0x00a8 }
                if (r3 >= r4) goto L_0x0052
                java.util.List<b.a.a.c> r4 = r11.f279a     // Catch:{ all -> 0x00a8 }
                java.lang.Object r4 = r4.get(r3)     // Catch:{ all -> 0x00a8 }
                b.a.a.c r4 = (p014b.p015a.p016a.C0947c) r4     // Catch:{ all -> 0x00a8 }
                double r7 = r12.f269b     // Catch:{ all -> 0x00a8 }
                double r9 = r4.f269b     // Catch:{ all -> 0x00a8 }
                int r5 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
                if (r5 >= 0) goto L_0x004f
                r12 = r4
            L_0x004f:
                int r3 = r3 + 1
                goto L_0x0036
            L_0x0052:
                boolean r3 = r11.f281c     // Catch:{ all -> 0x00a8 }
                if (r3 == 0) goto L_0x009e
                b.a.a.c r3 = r11.f282d     // Catch:{ all -> 0x00a8 }
                if (r3 == 0) goto L_0x0080
                long r4 = r12.f268a     // Catch:{ all -> 0x00a8 }
                long r7 = r3.f268a     // Catch:{ all -> 0x00a8 }
                monitor-enter(r11)     // Catch:{ all -> 0x00a8 }
                long r4 = r4 - r7
                r7 = 38
                int r3 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
                if (r3 < 0) goto L_0x006e
                r7 = 260(0x104, double:1.285E-321)
                int r3 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
                if (r3 > 0) goto L_0x006e
                r3 = 1
                goto L_0x006f
            L_0x006e:
                r3 = 0
            L_0x006f:
                monitor-exit(r11)     // Catch:{ all -> 0x00a8 }
                if (r3 == 0) goto L_0x0073
                goto L_0x0080
            L_0x0073:
                int r12 = r11.f283e     // Catch:{ all -> 0x00a8 }
                int r12 = r12 + r6
                r11.f283e = r12     // Catch:{ all -> 0x00a8 }
                r2 = 3
                if (r12 != r2) goto L_0x00a0
                r11.f282d = r0     // Catch:{ all -> 0x00a8 }
                r11.f284f = r6     // Catch:{ all -> 0x00a8 }
                goto L_0x00a0
            L_0x0080:
                b.a.a.c r3 = r11.f282d     // Catch:{ all -> 0x00a8 }
                if (r3 == 0) goto L_0x0096
                long r4 = r12.f268a     // Catch:{ all -> 0x00a8 }
                long r7 = r3.f268a     // Catch:{ all -> 0x00a8 }
                long r4 = r4 - r7
                double r3 = (double) r4     // Catch:{ all -> 0x00a8 }
                double r7 = r12.f269b     // Catch:{ all -> 0x00a8 }
                double r9 = r12.f270c     // Catch:{ all -> 0x00a8 }
                double r7 = r7 / r9
                r0 = 2
                double[] r0 = new double[r0]     // Catch:{ all -> 0x00a8 }
                r0[r2] = r3     // Catch:{ all -> 0x00a8 }
                r0[r6] = r7     // Catch:{ all -> 0x00a8 }
            L_0x0096:
                r12.getClass()     // Catch:{ all -> 0x00a8 }
                r11.f282d = r12     // Catch:{ all -> 0x00a8 }
                r11.f283e = r2     // Catch:{ all -> 0x00a8 }
                goto L_0x00a0
            L_0x009e:
                r11.f281c = r6     // Catch:{ all -> 0x00a8 }
            L_0x00a0:
                java.util.List<b.a.a.c> r12 = r11.f279a     // Catch:{ all -> 0x00a8 }
                r12.clear()     // Catch:{ all -> 0x00a8 }
            L_0x00a5:
                r11.f280b = r1     // Catch:{ all -> 0x00a8 }
                goto L_0x00ab
            L_0x00a8:
                r12 = move-exception
                monitor-exit(r11)
                throw r12
            L_0x00ab:
                monitor-exit(r11)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: p014b.p015a.p016a.C0948d.C0949a.mo12976a(b.a.a.c):double[]");
        }
    }

    /* renamed from: b.a.a.d$b */
    public class C0950b extends Thread {

        /* renamed from: a */
        public final List<C0947c[]> f285a = new ArrayList();

        /* renamed from: b */
        public final C0949a f286b = new C0949a("Red");

        /* renamed from: c */
        public final C0949a f287c = new C0949a("Ir");

        /* renamed from: d */
        public boolean f288d;

        /* renamed from: e */
        public double[] f289e;

        /* renamed from: f */
        public double[] f290f;

        /* renamed from: g */
        public final List<Double> f291g = new ArrayList();

        /* renamed from: h */
        public Handler f292h = new C0951a(Looper.getMainLooper());

        /* renamed from: b.a.a.d$b$a */
        public class C0951a extends Handler {
            public C0951a(Looper looper) {
                super(looper);
            }

            public void handleMessage(Message message) {
                Bundle data;
                super.handleMessage(message);
                if (message.what == 99 && C0948d.this.f271a != null && (data = message.getData()) != null) {
                    C0948d.this.f271a.onSpO2Result((int) data.getDouble(Constants.SPO2_TYPE), (int) data.getDouble(HtmlTags.f485HR));
                }
            }
        }

        public C0950b() {
        }

        /* renamed from: a */
        public void mo12977a(double d, double d2, double d3) {
            double[] calSpO2 = LibOX.calSpO2(d, d2, d3);
            double d4 = 0.0d;
            if (calSpO2[0] != 0.0d || calSpO2[1] != 0.0d) {
                double d5 = calSpO2[1];
                if (this.f291g.size() >= 8) {
                    this.f291g.remove(0);
                }
                this.f291g.add(Double.valueOf(calSpO2[0]));
                for (Double doubleValue : this.f291g) {
                    d4 += doubleValue.doubleValue();
                }
                if (this.f292h != null) {
                    Bundle bundle = new Bundle();
                    bundle.putDouble(HtmlTags.f485HR, C0946b.m88a(0, d4 / ((double) this.f291g.size())));
                    bundle.putDouble(Constants.SPO2_TYPE, C0946b.m88a(1, d5));
                    Message message = new Message();
                    message.what = 99;
                    message.setData(bundle);
                    this.f292h.sendMessage(message);
                }
            }
        }

        public void interrupt() {
            this.f288d = false;
            Handler handler = this.f292h;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
                this.f292h = null;
            }
            super.interrupt();
        }

        /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
            java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
            	at java.base/java.util.Objects.checkIndex(Objects.java:372)
            	at java.base/java.util.ArrayList.get(ArrayList.java:458)
            	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:225)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
            */
        public void run() {
            /*
                r13 = this;
                super.run()
            L_0x0003:
                boolean r0 = r13.f288d
                if (r0 == 0) goto L_0x0087
                java.util.List<b.a.a.c[]> r0 = r13.f285a
                monitor-enter(r0)
                java.util.List<b.a.a.c[]> r1 = r13.f285a     // Catch:{ InterruptedException -> 0x0013 }
                r1.wait()     // Catch:{ InterruptedException -> 0x0013 }
                goto L_0x0013
            L_0x0010:
                r1 = move-exception
                goto L_0x0085
            L_0x0013:
                java.util.List<b.a.a.c[]> r1 = r13.f285a     // Catch:{ Exception -> 0x0082 }
                r2 = 0
                java.lang.Object r1 = r1.get(r2)     // Catch:{ Exception -> 0x0082 }
                b.a.a.c[] r1 = (p014b.p015a.p016a.C0947c[]) r1     // Catch:{ Exception -> 0x0082 }
                if (r1 == 0) goto L_0x007d
                b.a.a.d$a r3 = r13.f286b     // Catch:{ Exception -> 0x0082 }
                r4 = r1[r2]     // Catch:{ Exception -> 0x0082 }
                double[] r3 = r3.mo12976a(r4)     // Catch:{ Exception -> 0x0082 }
                b.a.a.d$a r4 = r13.f287c     // Catch:{ Exception -> 0x0082 }
                r5 = 1
                r1 = r1[r5]     // Catch:{ Exception -> 0x0082 }
                double[] r1 = r4.mo12976a(r1)     // Catch:{ Exception -> 0x0082 }
                r4 = 0
                if (r3 == 0) goto L_0x0035
                r13.f289e = r3     // Catch:{ Exception -> 0x0082 }
                goto L_0x0048
            L_0x0035:
                b.a.a.d$a r3 = r13.f286b     // Catch:{ Exception -> 0x0082 }
                monitor-enter(r3)     // Catch:{ Exception -> 0x0082 }
                boolean r6 = r3.f284f     // Catch:{ all -> 0x007a }
                if (r6 == 0) goto L_0x0040
                r3.f284f = r2     // Catch:{ all -> 0x007a }
                r6 = 1
                goto L_0x0041
            L_0x0040:
                r6 = 0
            L_0x0041:
                monitor-exit(r3)     // Catch:{ Exception -> 0x0082 }
                if (r6 == 0) goto L_0x0048
                r13.f290f = r4     // Catch:{ Exception -> 0x0082 }
                r13.f289e = r4     // Catch:{ Exception -> 0x0082 }
            L_0x0048:
                if (r1 == 0) goto L_0x004d
                r13.f290f = r1     // Catch:{ Exception -> 0x0082 }
                goto L_0x0060
            L_0x004d:
                b.a.a.d$a r1 = r13.f287c     // Catch:{ Exception -> 0x0082 }
                monitor-enter(r1)     // Catch:{ Exception -> 0x0082 }
                boolean r3 = r1.f284f     // Catch:{ all -> 0x0077 }
                if (r3 == 0) goto L_0x0058
                r1.f284f = r2     // Catch:{ all -> 0x0077 }
                r3 = 1
                goto L_0x0059
            L_0x0058:
                r3 = 0
            L_0x0059:
                monitor-exit(r1)     // Catch:{ Exception -> 0x0082 }
                if (r3 == 0) goto L_0x0060
                r13.f289e = r4     // Catch:{ Exception -> 0x0082 }
                r13.f290f = r4     // Catch:{ Exception -> 0x0082 }
            L_0x0060:
                double[] r1 = r13.f289e     // Catch:{ Exception -> 0x0082 }
                if (r1 == 0) goto L_0x007d
                double[] r3 = r13.f290f     // Catch:{ Exception -> 0x0082 }
                if (r3 == 0) goto L_0x007d
                r7 = r3[r2]     // Catch:{ Exception -> 0x0082 }
                r9 = r1[r5]     // Catch:{ Exception -> 0x0082 }
                r11 = r3[r5]     // Catch:{ Exception -> 0x0082 }
                r6 = r13
                r6.mo12977a(r7, r9, r11)     // Catch:{ Exception -> 0x0082 }
                r13.f290f = r4     // Catch:{ Exception -> 0x0082 }
                r13.f289e = r4     // Catch:{ Exception -> 0x0082 }
                goto L_0x007d
            L_0x0077:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ Exception -> 0x0082 }
                throw r2     // Catch:{ Exception -> 0x0082 }
            L_0x007a:
                r1 = move-exception
                monitor-exit(r3)     // Catch:{ Exception -> 0x0082 }
                throw r1     // Catch:{ Exception -> 0x0082 }
            L_0x007d:
                java.util.List<b.a.a.c[]> r1 = r13.f285a     // Catch:{ Exception -> 0x0082 }
                r1.remove(r2)     // Catch:{ Exception -> 0x0082 }
            L_0x0082:
                monitor-exit(r0)     // Catch:{ all -> 0x0010 }
                goto L_0x0003
            L_0x0085:
                monitor-exit(r0)     // Catch:{ all -> 0x0010 }
                throw r1
            L_0x0087:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p014b.p015a.p016a.C0948d.C0950b.run():void");
        }

        public synchronized void start() {
            this.f288d = true;
            super.start();
        }
    }

    public C0948d(OnSpO2ResultListener onSpO2ResultListener) {
        LibOX.init();
        this.f271a = onSpO2ResultListener;
        this.f277g = new C0950b();
    }

    /* renamed from: a */
    public final C0947c[] mo12972a() {
        C0947c[] cVarArr = null;
        if (this.f272b.size() > 50) {
            int[] iArr = new int[50];
            int[] iArr2 = new int[50];
            int i = 0;
            while (i < 50) {
                try {
                    int[] iArr3 = this.f272b.get(i);
                    iArr[i] = iArr3[0];
                    iArr2[i] = iArr3[1];
                    i++;
                } catch (Exception unused) {
                    return null;
                }
            }
            double[] dArr = {LibOX.lowPassFilter125(iArr), LibOX.lowPassFilter125(iArr2)};
            int i2 = this.f274d;
            double[][] dArr2 = this.f273c;
            if (i2 < dArr2.length) {
                dArr2[i2] = dArr;
                this.f274d = i2 + 1;
            } else {
                System.arraycopy(dArr2, 1, dArr2, 0, dArr2.length - 1);
                double[][] dArr3 = this.f273c;
                dArr3[dArr3.length - 1] = dArr;
            }
            int i3 = this.f274d;
            double[][] dArr4 = this.f273c;
            if (i3 == dArr4.length) {
                double d = 0.0d;
                double d2 = 0.0d;
                for (double[] dArr5 : dArr4) {
                    d2 += dArr5[0];
                    d += dArr5[1];
                }
                double length = (double) dArr4.length;
                double[] dArr6 = {d2 / length, d / length};
                double[] dArr7 = this.f273c[40];
                long j = this.f275e;
                long j2 = j;
                C0947c cVar = new C0947c(j, dArr7[0] - dArr6[0], dArr6[0], 0);
                C0947c cVar2 = new C0947c(j2, dArr7[1] - dArr6[1], dArr6[1], 0);
                this.f275e = j2 + 1;
                cVarArr = new C0947c[]{cVar, cVar2};
            }
            this.f272b.remove(0);
        }
        return cVarArr;
    }

    public void interrupt() {
        this.f277g.interrupt();
        this.f276f = false;
        super.interrupt();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:5|6|(2:8|9)|10|11|(5:13|(1:15)|16|2b|21)(1:26)|27) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0013 */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x001a  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x003b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r6 = this;
            super.run()
        L_0x0003:
            boolean r0 = r6.f276f
            if (r0 == 0) goto L_0x0042
            java.util.List<int[]> r0 = r6.f272b
            monitor-enter(r0)
            boolean r1 = r6.f278h     // Catch:{ all -> 0x003f }
            if (r1 == 0) goto L_0x0013
            java.util.List<int[]> r1 = r6.f272b     // Catch:{ InterruptedException -> 0x0013 }
            r1.wait()     // Catch:{ InterruptedException -> 0x0013 }
        L_0x0013:
            b.a.a.c[] r1 = r6.mo12972a()     // Catch:{ all -> 0x003f }
            r2 = 1
            if (r1 == 0) goto L_0x003b
            com.linktop.infs.OnSpO2ResultListener r3 = r6.f271a     // Catch:{ all -> 0x003f }
            if (r3 == 0) goto L_0x0027
            r2 = r1[r2]     // Catch:{ all -> 0x003f }
            double r4 = r2.f269b     // Catch:{ all -> 0x003f }
            double r4 = -r4
            int r2 = (int) r4     // Catch:{ all -> 0x003f }
            r3.onSpO2Wave(r2)     // Catch:{ all -> 0x003f }
        L_0x0027:
            b.a.a.d$b r2 = r6.f277g     // Catch:{ all -> 0x003f }
            java.util.List<b.a.a.c[]> r3 = r2.f285a     // Catch:{ all -> 0x003f }
            monitor-enter(r3)     // Catch:{ all -> 0x003f }
            java.util.List<b.a.a.c[]> r4 = r2.f285a     // Catch:{ all -> 0x0038 }
            r4.add(r1)     // Catch:{ all -> 0x0038 }
            java.util.List<b.a.a.c[]> r1 = r2.f285a     // Catch:{ all -> 0x0038 }
            r1.notify()     // Catch:{ all -> 0x0038 }
            monitor-exit(r3)     // Catch:{ all -> 0x0038 }
            goto L_0x003d
        L_0x0038:
            r1 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0038 }
            throw r1     // Catch:{ all -> 0x003f }
        L_0x003b:
            r6.f278h = r2     // Catch:{ all -> 0x003f }
        L_0x003d:
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            goto L_0x0003
        L_0x003f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            throw r1
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p014b.p015a.p016a.C0948d.run():void");
    }

    public synchronized void start() {
        this.f276f = true;
        super.start();
        this.f277g.start();
    }
}
