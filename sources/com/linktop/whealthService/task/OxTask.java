package com.linktop.whealthService.task;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.itextpdf.text.DocWriter;
import com.itextpdf.text.pdf.BidiOrder;
import com.linktop.constant.Pair;
import com.linktop.infs.OnHRVResultListener;
import com.linktop.infs.OnSpO2ResultListener;
import com.linktop.utils.BleDevLog;
import com.linktop.whealthService.util.IBleDev;
import com.p020kl.commonbase.constants.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import kotlin.UByte;
import p014b.p015a.p016a.C0942a;
import p014b.p015a.p016a.C0948d;

public final class OxTask extends HcModuleTask {

    /* renamed from: B */
    private static final String f1134B = OxTask.class.getSimpleName();

    /* renamed from: C */
    private static final double[] f1135C = {0.001024d, 0.001008d, 9.48E-4d, 7.62E-4d, 3.37E-4d, -4.36E-4d, -0.001633d, -0.003248d, -0.005169d, -0.007157d, -0.008847d, -0.009776d, -0.009433d, -0.007321d, -0.003038d, 0.003653d, 0.012764d, 0.02404d, 0.036957d, 0.050754d, 0.064489d, 0.077136d, 0.087688d, 0.095268d, 0.09923d, 0.09923d, 0.095268d, 0.087688d, 0.077136d, 0.064489d, 0.050754d, 0.036957d, 0.02404d, 0.012764d, 0.003653d, -0.003038d, -0.007321d, -0.009433d, -0.009776d, -0.008847d, -0.007157d, -0.005169d, -0.003248d, -0.001633d, -4.36E-4d, 3.37E-4d, 7.62E-4d, 9.48E-4d, 0.001008d, 0.001024d};

    /* renamed from: A */
    private int f1136A;

    /* renamed from: a */
    private final boolean f1137a = false;

    /* renamed from: b */
    private SaveData2File f1138b;

    /* renamed from: c */
    public int f1139c = 125;

    /* renamed from: d */
    private boolean f1140d;

    /* renamed from: e */
    private C0948d f1141e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public ReadThread f1142f;

    /* renamed from: g */
    private C0942a f1143g;

    /* renamed from: h */
    private int f1144h;

    /* renamed from: i */
    private int f1145i;

    /* renamed from: j */
    private int f1146j;

    /* renamed from: k */
    private int f1147k;

    /* renamed from: l */
    private final List<Byte> f1148l = new ArrayList();

    /* renamed from: m */
    private final int f1149m = 1500;

    /* renamed from: n */
    private final int[] f1150n = new int[1500];

    /* renamed from: o */
    private final int[] f1151o = new int[1500];

    /* renamed from: p */
    private int f1152p = 0;

    /* renamed from: q */
    private int f1153q = 0;

    /* renamed from: r */
    private int f1154r = 0;

    /* renamed from: s */
    private boolean f1155s = false;
    /* access modifiers changed from: private */

    /* renamed from: t */
    public OnSpO2ResultListener f1156t;

    /* renamed from: u */
    private OnHRVResultListener f1157u;

    /* renamed from: v */
    private boolean f1158v;

    /* renamed from: w */
    private int f1159w = 230;

    /* renamed from: x */
    private int f1160x = 230;

    /* renamed from: y */
    private final Handler f1161y = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 233 && OxTask.this.f1156t != null) {
                OxTask.this.f1156t.onFingerDetection(((Integer) message.obj).intValue());
            }
        }
    };

    /* renamed from: z */
    private final byte[] f1162z = new byte[6];

    public static class SaveData2File {

        /* renamed from: a */
        private final File f1165a;

        /* renamed from: b */
        private final Map<String, FileOutputStream> f1166b;

        /* renamed from: a */
        public void mo27554a() {
            try {
                for (String str : this.f1166b.keySet()) {
                    this.f1166b.get(str).close();
                }
                this.f1166b.clear();
            } catch (IOException unused) {
            }
        }

        /* renamed from: a */
        public void mo27555a(String str, byte[] bArr) {
            try {
                FileOutputStream fileOutputStream = this.f1166b.get(str);
                if (fileOutputStream != null) {
                    fileOutputStream.write(bArr);
                }
            } catch (IOException unused) {
            }
        }

        /* renamed from: a */
        public void mo27556a(String... strArr) {
            long currentTimeMillis = System.currentTimeMillis();
            for (String str : strArr) {
                try {
                    this.f1166b.put(str, new FileOutputStream(new File(this.f1165a, str + "_" + currentTimeMillis + Constants.SHARE_TEXT_NAME)));
                } catch (FileNotFoundException unused) {
                }
            }
        }
    }

    static {
        System.loadLibrary("oxygen");
    }

    public OxTask(Context context, IBleDev iBleDev) {
        super(iBleDev);
    }

    /* renamed from: a */
    private static int m276a(byte b, byte b2, byte b3) {
        return ((b & UByte.MAX_VALUE) << BidiOrder.f527S) | ((b2 & UByte.MAX_VALUE) << 8) | (b3 & UByte.MAX_VALUE);
    }

    /* renamed from: a */
    private void m279a() {
        this.f1148l.clear();
        this.f1152p = 0;
        this.f1153q = 0;
        this.f1154r = 0;
        this.f1158v = false;
    }

    /* renamed from: a */
    private void m280a(byte[] bArr) {
        boolean z;
        int i;
        int length = bArr.length;
        if (mo27549c()) {
            this.f1136A = 0;
        }
        int i2 = 0;
        while (i2 < length) {
            int i3 = length - i2;
            if (i3 < 6) {
                this.f1136A = i3;
                i = 0;
                z = false;
            } else {
                int i4 = this.f1136A;
                if (i4 > 0) {
                    this.f1136A = 0;
                    i = i4;
                    i3 = 6 - i4;
                } else {
                    i3 = 6;
                    i = 0;
                }
                z = true;
            }
            System.arraycopy(bArr, i2, this.f1162z, i, i3);
            i2 += i3;
            if (z) {
                byte[] bArr2 = this.f1162z;
                int a = m276a(bArr2[0], bArr2[1], bArr2[2]);
                byte[] bArr3 = this.f1162z;
                mo27546a(a, m276a(bArr3[3], bArr3[4], bArr3[5]));
                int i5 = this.f1154r + 1;
                this.f1154r = i5;
                if (i5 == 4500) {
                    stop();
                    OnSpO2ResultListener onSpO2ResultListener = this.f1156t;
                    if (onSpO2ResultListener != null) {
                        onSpO2ResultListener.onSpO2End();
                    }
                }
            }
        }
    }

    /* renamed from: a */
    private void m281a(byte[] bArr, boolean z) {
        boolean z2;
        int i;
        String str;
        String str2;
        int length = bArr.length;
        if (mo27549c()) {
            if (this.f1144h == 1) {
                BleDevLog.m142c(f1134B, "dealDataFast - Last pkg was lost tail.");
                int i2 = this.f1146j + 1;
                this.f1146j = i2;
                Message.obtain(this.f1161y, 233, Integer.valueOf(i2)).sendToTarget();
                this.f1143g.mo12962a(this.f1145i - 6);
                this.f1143g.mo12962a(this.f1145i + 7);
                this.f1143g.mo12962a(this.f1145i - 8);
                if (z) {
                    this.f1143g.mo12962a(this.f1145i + 9);
                    this.f1143g.mo12962a(this.f1145i - 10);
                }
            }
            this.f1136A = 0;
            this.f1144h = 1;
        } else {
            int i3 = this.f1144h;
            if (i3 == 0) {
                str = f1134B;
                str2 = "dealDataFast - No head pkg, skip.";
            } else if (i3 == 2) {
                str = f1134B;
                str2 = "dealDataFast - Last pkg was lost head.";
            } else {
                this.f1144h = 2;
            }
            BleDevLog.m142c(str, str2);
            return;
        }
        if (this.f1138b != null) {
            this.f1138b.mo27555a(Constants.BT_TYPE, (Arrays.toString(bArr) + "\n").getBytes(StandardCharsets.UTF_8));
        }
        int i4 = 0;
        while (i4 < length) {
            int i5 = length - i4;
            if (i5 < 6) {
                this.f1136A = i5;
                i = 0;
                z2 = false;
            } else {
                int i6 = this.f1136A;
                if (i6 > 0) {
                    this.f1136A = 0;
                    i = i6;
                    i5 = 6 - i6;
                } else {
                    i5 = 6;
                    i = 0;
                }
                z2 = true;
            }
            System.arraycopy(bArr, i4, this.f1162z, i, i5);
            i4 += i5;
            if (z2) {
                byte[] bArr2 = this.f1162z;
                int a = m276a(bArr2[0], bArr2[1], bArr2[2]);
                byte[] bArr3 = this.f1162z;
                int a2 = m276a(bArr3[3], bArr3[4], bArr3[5]);
                if (this.f1144h == 1) {
                    this.f1145i = a;
                }
                C0942a aVar = this.f1143g;
                if (aVar != null) {
                    aVar.mo12962a(a);
                    if (z) {
                        this.f1143g.mo12962a(a2);
                    }
                }
                SaveData2File saveData2File = this.f1138b;
                if (saveData2File != null) {
                    saveData2File.mo27555a("red", (a + "\n").getBytes(StandardCharsets.UTF_8));
                    if (z) {
                        SaveData2File saveData2File2 = this.f1138b;
                        saveData2File2.mo27555a("red", (a2 + "\n").getBytes(StandardCharsets.UTF_8));
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public /* synthetic */ void m283d() {
        this.mCommunicate.mo27588a((byte) 4, new byte[]{3, DocWriter.QUOTE, 1, (byte) this.f1159w, (byte) this.f1160x});
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public /* synthetic */ void m284e() {
        this.mCommunicate.mo27588a((byte) 6, new byte[]{3, DocWriter.QUOTE, 1, (byte) this.f1159w, (byte) this.f1160x});
    }

    /* renamed from: f */
    private void m285f() {
        Handler handler;
        Runnable r1;
        if (this.f1142f != null) {
            C0948d dVar = new C0948d(this.f1156t);
            this.f1141e = dVar;
            dVar.start();
            this.f1142f.start();
            return;
        }
        String str = f1134B;
        BleDevLog.m141b(str, "Start PPG test is called, sampleRate = " + mo27548b());
        this.f1144h = 0;
        byte b = 2;
        byte b2 = 4;
        if (mo27548b() == 125) {
            m279a();
            C0948d dVar2 = new C0948d(this.f1156t);
            this.f1141e = dVar2;
            dVar2.start();
            if (this.f1159w == 230 && this.f1160x == 230) {
                if (!this.f1155s) {
                    b = 0;
                }
                this.mCommunicate.mo27588a((byte) 4, new byte[]{b});
                return;
            }
            this.mCommunicate.mo27588a((byte) 4, new byte[]{0});
            this.f1158v = true;
            BleDevLog.m141b(str, "set ppg params is called - ir = " + this.f1159w + ", redBri = " + this.f1160x);
            handler = this.f1161y;
            r1 = new Runnable() {
                public final void run() {
                    OxTask.this.m283d();
                }
            };
        } else if (mo27548b() == 250 || mo27548b() == 500) {
            try {
                Class<?> cls = Class.forName("b.a.a.a");
                Class cls2 = Integer.TYPE;
                this.f1143g = (C0942a) cls.getConstructor(new Class[]{cls2, cls2, OnHRVResultListener.class}).newInstance(new Object[]{Integer.valueOf(mo27548b()), Integer.valueOf(this.f1147k), this.f1157u});
            } catch (Exception unused) {
                this.f1143g = new C0942a(mo27548b(), this.f1147k, this.f1157u);
            }
            this.f1143g.start();
            if (mo27548b() == 250) {
                b2 = 3;
            }
            this.mCommunicate.mo27588a((byte) 6, new byte[]{b2});
            if (this.f1159w != 230 || this.f1160x != 230) {
                String str2 = f1134B;
                BleDevLog.m141b(str2, "set ppg params is called - ir = " + this.f1159w + ", redBri = " + this.f1160x);
                handler = this.f1161y;
                r1 = new Runnable() {
                    public final void run() {
                        OxTask.this.m284e();
                    }
                };
            } else {
                return;
            }
        } else {
            BleDevLog.m141b(str, "No such sample rate for PPG:" + mo27548b() + "Hz");
            return;
        }
        handler.postDelayed(r1, 100);
    }

    /* renamed from: a */
    public void mo27546a(int i, int i2) {
        SaveData2File saveData2File = this.f1138b;
        if (saveData2File != null) {
            saveData2File.mo27555a("red", (i + ", " + i2 + "\n").getBytes(StandardCharsets.UTF_8));
        }
        C0948d dVar = this.f1141e;
        if (dVar != null) {
            int[] iArr = {i, i2};
            synchronized (dVar.f272b) {
                dVar.f272b.add(iArr);
                if (dVar.f278h) {
                    dVar.f278h = false;
                    dVar.f272b.notify();
                }
            }
        }
    }

    /* renamed from: a */
    public void mo27547a(boolean z) {
        this.f1140d = z;
    }

    /* renamed from: b */
    public int mo27548b() {
        return this.f1139c;
    }

    /* renamed from: c */
    public boolean mo27549c() {
        return this.f1140d;
    }

    public void dealData(byte[] bArr) {
        int b = mo27548b();
        if (b == 250 || b == 500) {
            try {
                m281a(bArr, this.f1137a);
            } catch (IOException unused) {
            }
        } else {
            m280a(bArr);
        }
    }

    public void setOnHrvResultListener(OnHRVResultListener onHRVResultListener) {
        this.f1157u = onHRVResultListener;
    }

    public void setOnSpO2ResultListener(OnSpO2ResultListener onSpO2ResultListener) {
        this.f1156t = onSpO2ResultListener;
    }

    public void start(Pair... pairArr) {
        super.start(pairArr);
        this.f1139c = 125;
        this.f1147k = 0;
        this.f1155s = false;
        for (Pair pair : pairArr) {
            int intValue = ((Integer) pair.first).intValue();
            if (intValue == 401) {
                this.f1155s = ((Boolean) pair.second).booleanValue();
            } else if (intValue == 402) {
                this.f1142f = new ReadThread(this, (String) pair.second) {
                    /* renamed from: a */
                    public void mo27553a() {
                        OxTask.this.f1142f.interrupt();
                        ReadThread unused = OxTask.this.f1142f = null;
                    }
                };
            } else if (intValue == 701) {
                this.f1139c = ((Integer) pair.second).intValue();
            } else if (intValue == 702) {
                this.f1147k = ((Integer) pair.second).intValue();
            }
        }
        SaveData2File saveData2File = this.f1138b;
        if (saveData2File != null) {
            saveData2File.mo27556a(Constants.BT_TYPE, "red");
        }
        this.f1146j = 0;
        m285f();
    }

    public void stop() {
        super.stop();
        BleDevLog.m141b(f1134B, "Stop PPG test is called");
        SaveData2File saveData2File = this.f1138b;
        if (saveData2File != null) {
            saveData2File.mo27554a();
        }
        this.f1161y.removeCallbacksAndMessages((Object) null);
        ReadThread readThread = this.f1142f;
        if (readThread != null) {
            readThread.interrupt();
            this.f1142f = null;
        } else if (mo27548b() == 250 || mo27548b() == 500) {
            this.mCommunicate.mo27588a((byte) 6, new byte[]{2});
            C0942a aVar = this.f1143g;
            if (aVar != null) {
                aVar.interrupt();
                this.f1143g = null;
            }
        } else {
            this.mCommunicate.mo27588a((byte) 4, new byte[]{1});
        }
        C0948d dVar = this.f1141e;
        if (dVar != null) {
            dVar.interrupt();
            this.f1141e = null;
        }
    }
}
