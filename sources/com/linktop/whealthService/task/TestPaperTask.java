package com.linktop.whealthService.task;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import android.util.SparseArray;
import com.itextpdf.text.pdf.BidiOrder;
import com.linktop.constant.TestPaper;
import com.linktop.infs.OnBtFactoryListener;
import com.linktop.infs.OnSendCodeToDevCallback;
import com.linktop.infs.OnTestPaperResultListener;
import com.linktop.utils.AssetsDatabaseManager;
import com.linktop.utils.BleDevLog;
import com.linktop.whealthService.OnBLEService;
import com.linktop.whealthService.util.IBleDev;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.UByte;

public class TestPaperTask extends HcModuleTask {
    /* access modifiers changed from: private */

    /* renamed from: D */
    public static final String f1170D = "TestPaperTask";
    public static final int EVENT_BLOOD_SAMPLE_DETECTING = 13;
    public static final byte EVENT_PAPER_IN = 1;
    public static final int EVENT_PAPER_READ = 3;
    public static final int EVENT_TEST_RESULT = 5;
    public static final int EVENT_TEST_RESULT_FAC = 255;
    public static final int EXCEPTION_DEVICE_ERROR = -1;
    public static final byte EXCEPTION_PAPER_OUT = 0;
    public static final int EXCEPTION_PAPER_USED = 9;
    public static final int EXCEPTION_TESTING_PAPER_OUT = 6;
    public static final int EXCEPTION_TIMEOUT_FOR_CHECK_PAPER_IN = 2;
    public static final int EXCEPTION_TIMEOUT_FOR_DETECT_BLOOD_SAMPLE = 16;
    public static final int TEST_PAPER_CALIBRATION_RESULT = 8;
    public static final int TEST_PAPER_SET_VER_RESULT = 7;

    /* renamed from: A */
    private boolean f1171A;

    /* renamed from: B */
    private File f1172B;

    /* renamed from: C */
    private FileOutputStream f1173C;

    /* renamed from: a */
    private final ArrayList<double[]> f1174a = new ArrayList<>();

    /* renamed from: b */
    private final Context f1175b;

    /* renamed from: c */
    private int f1176c = 0;

    /* renamed from: d */
    private int[] f1177d;

    /* renamed from: e */
    private int f1178e = 0;

    /* renamed from: f */
    private int f1179f = 0;

    /* renamed from: g */
    private int f1180g = 0;

    /* renamed from: h */
    private int f1181h = 0;

    /* renamed from: i */
    private int f1182i = 0;

    /* renamed from: j */
    private Timer f1183j;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public Timer f1184k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public Timer f1185l;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public int f1186m = 0;

    /* renamed from: n */
    private Cursor f1187n;
    /* access modifiers changed from: private */

    /* renamed from: o */
    public final SparseArray<TestPaper> f1188o = new SparseArray<>();

    /* renamed from: p */
    private int f1189p = 0;
    /* access modifiers changed from: private */

    /* renamed from: q */
    public final SparseArray<OnTestPaperResultListener> f1190q = new SparseArray<>();
    /* access modifiers changed from: private */

    /* renamed from: r */
    public final Handler f1191r;

    /* renamed from: s */
    private OnSendCodeToDevCallback f1192s;
    /* access modifiers changed from: private */

    /* renamed from: t */
    public int f1193t;
    /* access modifiers changed from: private */

    /* renamed from: u */
    public int f1194u;

    /* renamed from: v */
    private int f1195v;
    /* access modifiers changed from: private */

    /* renamed from: w */
    public BtTask f1196w;
    /* access modifiers changed from: private */

    /* renamed from: x */
    public double f1197x;
    /* access modifiers changed from: private */

    /* renamed from: y */
    public double f1198y;
    /* access modifiers changed from: private */

    /* renamed from: z */
    public double f1199z;

    /* renamed from: com.linktop.whealthService.task.TestPaperTask$5 */
    public class C21715 extends TimerTask {

        /* renamed from: a */
        public final /* synthetic */ TestPaperTask f1204a;

        public void run() {
            this.f1204a.m322e();
            Message.obtain(this.f1204a.f1191r, 2).sendToTarget();
        }
    }

    public class NewCalBsThread extends Thread {

        /* renamed from: a */
        private final List<double[]> f1205a;

        /* renamed from: b */
        private final int f1206b;

        public NewCalBsThread(List<double[]> list, int i) {
            ArrayList arrayList = new ArrayList();
            this.f1205a = arrayList;
            arrayList.addAll(list);
            this.f1206b = i;
        }

        public void run() {
            double d;
            super.run();
            TestPaper testPaper = (TestPaper) TestPaperTask.this.f1188o.get(TestPaperTask.this.f1193t);
            String manufacturer = testPaper.getManufacturer();
            String code = testPaper.getCode();
            manufacturer.hashCode();
            double d2 = 0.0d;
            if (!manufacturer.equals(TestPaper.Manufacturer.HMD)) {
                if (!manufacturer.equals(TestPaper.Manufacturer.YI_CHENG)) {
                    int g = TestPaperTask.this.f1194u;
                    double[] dArr = new double[g];
                    for (int i = 0; i < g; i++) {
                        dArr[i] = this.f1205a.get(i)[0];
                    }
                    TestPaperTask testPaperTask = TestPaperTask.this;
                    double unused = testPaperTask.f1198y = TestPaperTask.getValue(testPaperTask.f1193t, manufacturer, code, dArr);
                } else {
                    TestPaperTask testPaperTask2 = TestPaperTask.this;
                    double unused2 = testPaperTask2.f1198y = testPaperTask2.m298a(this.f1205a);
                }
                d = TestPaperTask.this.f1198y;
            } else {
                double[] a = TestPaperTask.m313b(this.f1205a, this.f1206b, TestPaperTask.this.f1197x);
                d2 = a[0];
                double unused3 = TestPaperTask.this.f1198y = a[1];
                double unused4 = TestPaperTask.this.f1199z = a[2];
                d = TestPaperTask.this.f1199z;
            }
            String a2 = TestPaperTask.f1170D;
            BleDevLog.m141b(a2, "mMeasureType = " + TestPaperTask.this.f1193t + ", targetReg:" + d2 + ", result:" + d);
            Message.obtain(TestPaperTask.this.f1191r, 5, Double.valueOf(d)).sendToTarget();
            BleDevLog.m141b(TestPaperTask.f1170D, "stopBsAdc() called  9");
            TestPaperTask.this.stop();
        }
    }

    static {
        System.loadLibrary("bloodsuger");
    }

    public TestPaperTask(Context context, IBleDev iBleDev) {
        super(iBleDev);
        Looper myLooper = Looper.myLooper();
        Objects.requireNonNull(myLooper);
        this.f1191r = new Handler(myLooper) {
            public void handleMessage(Message message) {
                OnTestPaperResultListener onTestPaperResultListener;
                Object obj;
                OnTestPaperResultListener onTestPaperResultListener2;
                OnTestPaperResultListener onTestPaperResultListener3;
                int i;
                int i2 = message.what;
                int i3 = -1;
                if (i2 != -1) {
                    i3 = 16;
                    if (i2 != 16) {
                        int i4 = 255;
                        if (i2 != 255) {
                            int i5 = 1;
                            if (i2 != 1) {
                                i3 = 2;
                                if (i2 != 2) {
                                    i5 = 3;
                                    if (i2 != 3) {
                                        i4 = 12;
                                        if (i2 != 12) {
                                            i5 = 13;
                                            if (i2 != 13) {
                                                switch (i2) {
                                                    case 5:
                                                        if (TestPaperTask.this.f1184k != null) {
                                                            TestPaperTask.this.f1184k.cancel();
                                                            Timer unused = TestPaperTask.this.f1184k = null;
                                                        }
                                                        if (TestPaperTask.this.f1190q.size() > 0) {
                                                            onTestPaperResultListener2 = (OnTestPaperResultListener) TestPaperTask.this.f1190q.get(TestPaperTask.this.f1193t);
                                                            obj = message.obj;
                                                            i4 = 5;
                                                            break;
                                                        } else {
                                                            return;
                                                        }
                                                    case 6:
                                                        if (TestPaperTask.this.f1190q.size() > 0) {
                                                            onTestPaperResultListener3 = (OnTestPaperResultListener) TestPaperTask.this.f1190q.get(TestPaperTask.this.f1193t);
                                                            i = 6;
                                                            break;
                                                        } else {
                                                            return;
                                                        }
                                                    case 7:
                                                        if (TestPaperTask.this.f1190q.size() > 0) {
                                                            onTestPaperResultListener2 = (OnTestPaperResultListener) TestPaperTask.this.f1190q.get(TestPaperTask.this.f1193t);
                                                            obj = message.obj;
                                                            i4 = 7;
                                                            break;
                                                        } else {
                                                            return;
                                                        }
                                                    case 8:
                                                        if (TestPaperTask.this.f1190q.size() > 0) {
                                                            onTestPaperResultListener2 = (OnTestPaperResultListener) TestPaperTask.this.f1190q.get(TestPaperTask.this.f1193t);
                                                            obj = message.obj;
                                                            i4 = 8;
                                                            break;
                                                        } else {
                                                            return;
                                                        }
                                                    case 9:
                                                        if (TestPaperTask.this.f1190q.size() > 0) {
                                                            onTestPaperResultListener3 = (OnTestPaperResultListener) TestPaperTask.this.f1190q.get(TestPaperTask.this.f1193t);
                                                            i = 9;
                                                            break;
                                                        } else {
                                                            return;
                                                        }
                                                    case 10:
                                                        if (TestPaperTask.this.f1190q.size() > 0) {
                                                            onTestPaperResultListener3 = (OnTestPaperResultListener) TestPaperTask.this.f1190q.get(TestPaperTask.this.f1193t);
                                                            i = 10;
                                                            break;
                                                        } else {
                                                            return;
                                                        }
                                                    default:
                                                        return;
                                                }
                                            } else if (TestPaperTask.this.f1190q.size() <= 0) {
                                                return;
                                            }
                                        } else if (TestPaperTask.this.f1190q.size() <= 0) {
                                            return;
                                        }
                                    } else if (TestPaperTask.this.f1190q.size() <= 0) {
                                        return;
                                    }
                                } else if (TestPaperTask.this.f1190q.size() <= 0) {
                                    return;
                                }
                            } else {
                                byte byteValue = ((Byte) message.obj).byteValue();
                                if (TestPaperTask.this.f1190q.size() <= 0) {
                                    return;
                                }
                                if (byteValue != 1) {
                                    onTestPaperResultListener3 = (OnTestPaperResultListener) TestPaperTask.this.f1190q.get(TestPaperTask.this.f1193t);
                                    i = 0;
                                    onTestPaperResultListener3.onTestPaperException(i);
                                    return;
                                }
                            }
                            ((OnTestPaperResultListener) TestPaperTask.this.f1190q.get(TestPaperTask.this.f1193t)).onTestPaperEvent(i5, (Object) null);
                            return;
                        } else if (TestPaperTask.this.f1190q.size() <= 0) {
                            return;
                        }
                        onTestPaperResultListener2 = (OnTestPaperResultListener) TestPaperTask.this.f1190q.get(TestPaperTask.this.f1193t);
                        obj = message.obj;
                        onTestPaperResultListener2.onTestPaperEvent(i4, obj);
                        return;
                    } else if (TestPaperTask.this.f1190q.size() <= 0) {
                        return;
                    }
                    onTestPaperResultListener = (OnTestPaperResultListener) TestPaperTask.this.f1190q.get(TestPaperTask.this.f1193t);
                } else {
                    onTestPaperResultListener = (OnTestPaperResultListener) TestPaperTask.this.f1190q.get(TestPaperTask.this.f1193t);
                    if (onTestPaperResultListener == null) {
                        return;
                    }
                }
                onTestPaperResultListener.onTestPaperException(i3);
            }
        };
        this.f1193t = 3;
        this.f1195v = 1;
        this.f1175b = context;
        SQLiteDatabase a = AssetsDatabaseManager.m136c().mo27411a("BsVal.db");
        if (a == null) {
            AssetsDatabaseManager.m131a();
            AssetsDatabaseManager.m132a(context);
            a = AssetsDatabaseManager.m136c().mo27411a("BsVal.db");
        }
        try {
            this.f1187n = a.query("BsValueAll", (String[]) null, (String) null, (String[]) null, (String) null, (String) null, (String) null);
        } catch (Error | Exception e) {
            IBleDev iBleDev2 = this.mIBleDev;
            if (iBleDev2 != null) {
                iBleDev2.onInnerThrowableCallback(e);
            }
        }
    }

    /* renamed from: a */
    private double m297a(String str, double d) {
        if (str == null) {
            return 0.0d;
        }
        int bgValue = getBgValue(d);
        String str2 = f1170D;
        BleDevLog.m141b(str2, "getBsValueResult() paperCode:" + str + ", reg=" + d + ", index:" + bgValue);
        Cursor cursor = this.f1187n;
        if (cursor != null) {
            cursor.moveToFirst();
            if (bgValue != 300) {
                this.f1187n.move(bgValue);
                return this.f1187n.getDouble(this.f1187n.getColumnIndex(str));
            }
        }
        return 0.0d;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public double m298a(List<double[]> list) {
        double d;
        double d2 = list.get(0)[0];
        double d3 = 0.0d;
        for (int i = 96; i < 105; i++) {
            d3 += list.get(i)[0];
        }
        double a = m297a(this.f1188o.get(3).getCode(), (((d3 / 9.0d) - d2) * 9.25d) + d2);
        if (a >= 10.0d) {
            d = 0.75d;
        } else if (a < 5.0d) {
            return a;
        } else {
            d = 0.8d;
        }
        return a * d;
    }

    /* renamed from: a */
    private static Pair<Integer, Boolean> m299a(double[] dArr, List<double[]> list) {
        if (list.isEmpty()) {
            return null;
        }
        boolean z = true;
        if (dArr.length == 1) {
            double d = list.get(0)[0];
            for (double[] dArr2 : list) {
                double d2 = dArr2[0];
                if (d > d2) {
                    d = d2;
                }
            }
            if (dArr[0] >= d) {
                z = false;
            }
            return Pair.create(0, Boolean.valueOf(z));
        } else if (dArr.length != 2) {
            return null;
        } else {
            double d3 = list.get(0)[0];
            double d4 = list.get(0)[1];
            for (double[] next : list) {
                double d5 = next[0];
                double d6 = next[1];
                if (d3 > d5) {
                    d3 = d5;
                }
                if (d4 > d6) {
                    d4 = d6;
                }
            }
            int i = d3 < d4 ? 0 : 1;
            if (i != 0 ? dArr[1] >= d4 : dArr[0] >= d3) {
                z = false;
            }
            return Pair.create(Integer.valueOf(i), Boolean.valueOf(z));
        }
    }

    /* renamed from: a */
    private void m303a(byte b) {
        String str = f1170D;
        BleDevLog.m141b(str, "data is " + b);
        BleDevLog.m141b(str, "" + this.f1182i);
        int i = this.f1182i;
        if (i == 0) {
            if (b == 0) {
                stop();
            } else {
                m332k();
            }
            BleDevLog.m141b(str, "send_initpaper_status + " + b);
            Message.obtain(this.f1191r, 1, Byte.valueOf(b)).sendToTarget();
        } else if ((i == 1 || i == 2) && b == 1) {
            Timer timer = this.f1183j;
            if (timer != null) {
                timer.cancel();
                this.f1183j = null;
            }
            m332k();
        }
    }

    /* renamed from: a */
    private void m304a(byte[] bArr) {
        int i = this.f1195v;
        double[] dArr = new double[i];
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < this.f1195v; i2++) {
            int i3 = i2 * 2;
            iArr[i2] = (bArr[i3] << 8) + (bArr[i3 + 1] & UByte.MAX_VALUE);
        }
        BleDevLog.m140a(f1170D, "bg_val = " + Arrays.toString(iArr));
        int i4 = this.f1179f + 1;
        this.f1179f = i4;
        if (i4 < 11) {
            for (int i5 = 0; i5 < this.f1195v; i5++) {
                int[] iArr2 = this.f1177d;
                iArr2[i5] = iArr2[i5] + iArr[i5];
            }
        } else if (i4 == 11) {
            for (int i6 = 0; i6 < this.f1195v; i6++) {
                int[] iArr3 = this.f1177d;
                iArr3[i6] = (iArr3[i6] + 4) / 10;
            }
            BleDevLog.m140a(f1170D, "bg_paper_ok=" + Arrays.toString(this.f1177d));
            if (this.f1177d[0] != this.f1178e) {
                for (int i7 = 0; i7 < this.f1195v; i7++) {
                    int i8 = this.f1178e;
                    dArr[i7] = (double) ((((float) i8) * 110.0f) / ((float) (this.f1177d[i7] - i8)));
                }
                String str = f1170D;
                BleDevLog.m141b(str, "check paper is used data : " + Arrays.toString(dArr));
                if (Math.abs(dArr[0]) < 2000.0d) {
                    Message.obtain(this.f1191r, 9).sendToTarget();
                    BleDevLog.m141b(str, "stopBsAdc() called  3");
                    stop();
                    return;
                }
            }
            m329i();
        } else if (i4 < 1200 && this.f1181h == 3) {
            for (int i9 = 0; i9 < this.f1195v; i9++) {
                int i10 = iArr[i9];
                int i11 = this.f1178e;
                if (i10 - i11 != 0) {
                    dArr[i9] = (double) ((((float) i11) * 110.0f) / ((float) (iArr[i9] - i11)));
                }
            }
            BleDevLog.m140a(f1170D, "check_blood_bg_resistance=" + Arrays.toString(dArr));
            if (dArr[0] < 4000.0d && dArr[0] > 0.0d) {
                this.f1181h = 4;
                Message.obtain(this.f1191r, 13).sendToTarget();
                if (this.f1184k == null) {
                    Timer timer = new Timer();
                    this.f1184k = timer;
                    timer.schedule(new TimerTask() {
                        public void run() {
                            TestPaperTask.this.stop();
                            Message.obtain(TestPaperTask.this.f1191r, 16).sendToTarget();
                        }
                    }, 180000);
                }
            }
        } else if (this.f1181h == 4 && !this.f1171A) {
            this.f1180g++;
            for (int i12 = 0; i12 < this.f1195v; i12++) {
                int i13 = iArr[i12];
                int i14 = this.f1178e;
                if (i13 - i14 != 0) {
                    dArr[i12] = (((double) i14) * 110.0d) / ((double) (iArr[i12] - i14));
                }
            }
            m320d();
            m305a(dArr);
            if (dArr[0] < 4000.0d && dArr[0] > 0.0d) {
                BleDevLog.m142c(f1170D, "yyyy_cal_bg_resistance=" + Arrays.toString(dArr));
                Pair<Integer, Boolean> a = m299a(dArr, (List<double[]>) this.f1174a);
                if (this.f1174a.size() > 0 && a != null && ((Boolean) a.second).booleanValue()) {
                    this.f1174a.clear();
                }
                this.f1174a.add(dArr);
                if (this.f1174a.size() == this.f1194u && a != null) {
                    new NewCalBsThread(this.f1174a, ((Integer) a.first).intValue()).start();
                    this.f1171A = true;
                }
            }
        }
    }

    /* renamed from: a */
    private void m305a(double... dArr) {
        try {
            if (this.f1173C != null) {
                StringBuilder sb = new StringBuilder();
                for (double append : dArr) {
                    sb.append(append);
                    sb.append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append("\n");
                byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
                this.f1173C.write(bytes, 0, bytes.length);
            }
        } catch (IOException unused) {
        }
    }

    /* renamed from: b */
    private void m310b() {
        this.f1181h = 2;
        this.mCommunicate.mo27588a((byte) 3, new byte[]{2});
    }

    /* renamed from: b */
    private void m311b(byte b) {
        this.mCommunicate.mo27588a(BidiOrder.f527S, new byte[]{-125, b});
    }

    /* renamed from: b */
    private void m312b(byte[] bArr) {
        int i = this.f1179f + 1;
        this.f1179f = i;
        if (i == 1) {
            String str = f1170D;
            BleDevLog.m141b(str, "stopBsAdc() called  2");
            m334l();
            int i2 = (bArr[0] << 8) + (bArr[1] & UByte.MAX_VALUE);
            BleDevLog.m141b(str, "bg_ver = " + this.f1178e + ", bg_val = " + i2);
            int i3 = this.f1178e;
            int i4 = i2 - i3;
            double d = i4 != 0 ? (((double) i3) * 110.0d) / ((double) i4) : 0.0d;
            BleDevLog.m141b(str, "bg_resistance=" + d);
            Message.obtain(this.f1191r, 8, Double.valueOf(d)).sendToTarget();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static double[] m313b(List<double[]> list, int i, double d) {
        int size = list.size();
        double[] dArr = new double[size];
        for (int i2 = 0; i2 < size; i2++) {
            dArr[i2] = list.get(i2)[i];
        }
        double[] bgValueForHMD = getBgValueForHMD(dArr, d);
        String str = f1170D;
        BleDevLog.m142c(str, "temp:" + d + ", getIqgValue： " + Arrays.toString(bgValueForHMD));
        return bgValueForHMD;
    }

    /* renamed from: c */
    private void m315c() {
        try {
            if (this.f1173C != null) {
                byte[] bytes = ("\n\n\nEnv Temp:" + this.f1197x + "\nResult:" + this.f1198y + "\nCorrect Result:" + this.f1199z).getBytes(StandardCharsets.UTF_8);
                this.f1173C.write(bytes, 0, bytes.length);
                this.f1173C.flush();
                this.f1173C.close();
                this.f1173C = null;
            }
            if (this.f1172B != null) {
                this.f1172B = null;
            }
        } catch (IOException unused) {
        }
    }

    /* renamed from: c */
    private void m316c(byte b) {
        String str = f1170D;
        BleDevLog.m141b(str, "BsStage = " + this.f1181h);
        BleDevLog.m141b(str, "BsStageCheckPaperSub =" + this.f1182i);
        int i = this.f1181h;
        if (i == 2) {
            m303a(b);
        } else if ((i == 3 || i == 4) && b == 0) {
            this.f1181h = 0;
            this.f1182i = 0;
            BleDevLog.m141b(str, "stopBsAdc() called  6");
            stop();
            BleDevLog.m141b(str, "testing paper out stop");
            Message.obtain(this.f1191r, 6).sendToTarget();
        }
    }

    /* renamed from: c */
    private void m318c(byte[] bArr) {
        int i = (bArr[0] << 8) + (bArr[1] & UByte.MAX_VALUE);
        int i2 = this.f1179f + 1;
        this.f1179f = i2;
        if (i2 < 11) {
            this.f1178e += i;
        } else if (i2 == 11) {
            this.f1178e = (this.f1178e + 4) / 10;
            String str = f1170D;
            BleDevLog.m141b(str, "get bg_ver=" + this.f1178e);
            BleDevLog.m141b(str, "stopBsAdc() called  8");
            m334l();
            Message.obtain(this.f1191r, 12, Integer.valueOf(this.f1178e)).sendToTarget();
        }
    }

    /* renamed from: d */
    private void m320d() {
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public void m322e() {
        this.f1181h = 0;
        this.f1182i = 0;
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public void m325g() {
        this.f1181h = 1;
        this.f1186m++;
        this.mCommunicate.mo27588a((byte) 3, new byte[]{1});
        if (this.f1185l == null) {
            this.f1185l = new Timer();
        }
        this.f1185l.schedule(new TimerTask() {
            public void run() {
                Timer unused = TestPaperTask.this.f1185l = null;
                if (TestPaperTask.this.f1186m <= 3) {
                    TestPaperTask.this.m325g();
                }
            }
        }, 500);
        if (this.f1186m == 4) {
            Message.obtain(this.f1191r, 10).sendToTarget();
        }
    }

    public static native int getBgValue(double d);

    public static native double[] getBgValueForHMD(double[] dArr, double d);

    public static native double getValue(int i, String str, String str2, double[] dArr);

    /* renamed from: h */
    private void m327h() {
        this.f1181h = 0;
        this.f1182i = 0;
        this.f1178e = 0;
        this.f1179f = 0;
        this.f1180g = 0;
        this.f1186m = 0;
        this.f1177d = new int[this.f1195v];
        this.f1176c = 0;
        this.f1199z = 0.0d;
        this.f1198y = 0.0d;
        this.f1171A = false;
        this.f1174a.clear();
    }

    /* renamed from: i */
    private void m329i() {
        Message.obtain(this.f1191r, 3).sendToTarget();
    }

    /* renamed from: k */
    private void m332k() {
        this.f1181h = 3;
        this.mCommunicate.mo27588a((byte) 3, new byte[]{3});
    }

    /* renamed from: l */
    private void m334l() {
        this.mCommunicate.mo27588a((byte) 3, new byte[]{4});
    }

    /* renamed from: a */
    public void mo27560a(int i) {
        this.f1189p = i;
        String str = f1170D;
        BleDevLog.m142c(str, "moduleType = " + this.f1189p);
    }

    public void checkModuleExist(OnSendCodeToDevCallback onSendCodeToDevCallback) {
        this.f1192s = onSendCodeToDevCallback;
        this.mCommunicate.mo27588a((byte) 3, new byte[]{0});
    }

    public void dealData(byte[] bArr) {
        byte b = bArr[0];
        if (b == -17) {
            Message.obtain(this.f1191r, 7, Byte.valueOf(bArr[1])).sendToTarget();
        } else if (b == 0) {
            mo27560a((int) bArr[1]);
            IBleDev iBleDev = this.mIBleDev;
            if (iBleDev instanceof OnBLEService) {
                ((OnBLEService) iBleDev).mo27452d();
                return;
            }
            OnSendCodeToDevCallback onSendCodeToDevCallback = this.f1192s;
            if (onSendCodeToDevCallback != null) {
                onSendCodeToDevCallback.onReceived();
            }
        } else if (b == 1) {
            Timer timer = this.f1185l;
            if (timer != null) {
                timer.cancel();
                this.f1185l = null;
            }
            this.f1178e = (bArr[1] << 8) + (bArr[2] & UByte.MAX_VALUE);
            String str = f1170D;
            BleDevLog.m141b(str, "read bg_ver = " + this.f1178e);
            if (this.f1181h == 1) {
                m310b();
            }
        } else if (b == 2) {
            m311b(bArr[1]);
            m316c(bArr[1]);
        } else if (b == 3) {
            int i = this.f1176c;
            if (i == 0) {
                int i2 = this.f1181h;
                if (i2 == 3 || i2 == 4) {
                    int i3 = this.f1195v * 2;
                    byte[] bArr2 = new byte[i3];
                    System.arraycopy(bArr, 1, bArr2, 0, i3);
                    if (this.f1178e == 0) {
                        this.f1178e = 619;
                        BleDevLog.m141b(f1170D, "bg_ver is not read");
                    }
                    m304a(bArr2);
                }
            } else if (i == 1) {
                byte[] bArr3 = new byte[2];
                System.arraycopy(bArr, 1, bArr3, 0, 2);
                m318c(bArr3);
            } else if (i == 2) {
                byte[] bArr4 = new byte[2];
                System.arraycopy(bArr, 1, bArr4, 0, 2);
                m312b(bArr4);
            }
        }
    }

    /* renamed from: f */
    public void mo27562f() {
        if (this.f1193t < 3) {
            this.f1193t = 3;
        }
        m325g();
        this.f1179f = 0;
        this.f1186m = 0;
        this.f1176c = 2;
        this.mCommunicate.mo27588a((byte) 3, new byte[]{3});
    }

    public boolean isDoubleADC() {
        return this.f1189p == 2;
    }

    public boolean isModuleExist() {
        return this.f1189p > 0;
    }

    /* renamed from: j */
    public void mo27565j() {
        if (this.f1193t < 3) {
            this.f1193t = 3;
        }
        this.f1178e = 0;
        this.f1179f = 0;
        this.f1176c = 1;
        this.mCommunicate.mo27588a((byte) 3, new byte[]{3});
    }

    public void setTestPaper(int i, TestPaper testPaper) {
        this.f1188o.put(i, testPaper);
    }

    public void setTestPaperResultListener(int i, OnTestPaperResultListener onTestPaperResultListener) {
        this.f1190q.put(i, onTestPaperResultListener);
    }

    public void start(int i) {
        this.f1193t = i;
        TestPaper testPaper = this.f1188o.get(i);
        boolean z = true;
        if (TestPaper.Manufacturer.HMD.equals(testPaper.getManufacturer())) {
            this.f1195v = 2;
        } else {
            this.f1195v = 1;
            z = false;
        }
        String manufacturer = testPaper.getManufacturer();
        manufacturer.hashCode();
        this.f1194u = !manufacturer.equals(TestPaper.Manufacturer.HMD) ? !manufacturer.equals(TestPaper.Manufacturer.YI_CHENG) ? 200 : 110 : 220;
        if (z) {
            BtTask btTask = getIBleDev().getBtTask();
            this.f1196w = btTask;
            btTask.mo27504a((OnBtFactoryListener) new OnBtFactoryListener() {
                /* renamed from: a */
                public void mo27384a(double d, double d2) {
                    TestPaperTask.this.f1196w.stop();
                    String a = TestPaperTask.f1170D;
                    BleDevLog.m141b(a, "env temp:" + d);
                    if (d < -10.0d || d > 45.0d) {
                        Message.obtain(TestPaperTask.this.f1191r, -1).sendToTarget();
                        return;
                    }
                    double unused = TestPaperTask.this.f1197x = d;
                    TestPaperTask.this.start(new com.linktop.constant.Pair[0]);
                }

                public void onBtResult(double d) {
                }
            });
            this.f1196w.start(new com.linktop.constant.Pair[0]);
            return;
        }
        start(new com.linktop.constant.Pair[0]);
    }

    public void start(com.linktop.constant.Pair... pairArr) {
        super.start(pairArr);
        m327h();
        m325g();
    }

    public void stop() {
        Timer timer;
        super.stop();
        if (this.f1181h == 2 && (timer = this.f1183j) != null) {
            timer.cancel();
            this.f1183j = null;
        }
        this.f1181h = 0;
        this.f1182i = 0;
        m334l();
        m315c();
        BtTask btTask = this.f1196w;
        if (btTask != null) {
            btTask.mo27504a((OnBtFactoryListener) null);
        }
    }
}
