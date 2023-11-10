package p000a.p001a.p002a.p003a.p004d;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.mintti.visionsdk.ble.BleManager;
import com.mintti.visionsdk.ble.bean.BgEvent;
import com.mintti.visionsdk.ble.bean.MeasureType;
import com.mintti.visionsdk.ble.callback.IBgResultListener;
import com.mintti.visionsdk.ble.callback.IBleNotifyResponse;
import com.mintti.visionsdk.ble.callback.IBleWriteResponse;
import com.mintti.visionsdk.ble.callback.IBpResultListener;
import com.mintti.visionsdk.ble.callback.IBtResultListener;
import com.mintti.visionsdk.ble.callback.IDeviceBatteryCallback;
import com.mintti.visionsdk.ble.callback.IEcgResultListener;
import com.mintti.visionsdk.ble.callback.IRawBgDataCallback;
import com.mintti.visionsdk.ble.callback.IRawBpDataCallback;
import com.mintti.visionsdk.ble.callback.IRawBtDataCallback;
import com.mintti.visionsdk.ble.callback.IRawSpo2DataCallback;
import com.mintti.visionsdk.ble.callback.ISpo2ResultListener;
import com.mintti.visionsdk.common.ArrayUtils;
import com.mintti.visionsdk.common.LogUtils;
import com.p020kl.vision_ecg.EcgAlgo;
import com.p020kl.vision_ecg.ISmctAlgoCallback;
import java.util.ArrayList;
import java.util.Arrays;

/* renamed from: a.a.a.a.d.d */
public class C0011d implements C0010c, IBleNotifyResponse, ISmctAlgoCallback {

    /* renamed from: A */
    public EcgAlgo f41A;

    /* renamed from: B */
    public int f42B = 0;

    /* renamed from: C */
    public long f43C = 0;

    /* renamed from: D */
    public int f44D = 0;

    /* renamed from: E */
    public int f45E = 0;

    /* renamed from: F */
    public double f46F;

    /* renamed from: G */
    public int f47G = 0;

    /* renamed from: H */
    public int f48H = 0;

    /* renamed from: I */
    public boolean f49I = false;

    /* renamed from: J */
    public final Handler f50J = new C0012a(Looper.getMainLooper());

    /* renamed from: K */
    public final IBleNotifyResponse f51K = new C0013b();

    /* renamed from: L */
    public final IBleNotifyResponse f52L = new C0014c();

    /* renamed from: M */
    public final IBleNotifyResponse f53M = new C0015d();

    /* renamed from: a */
    public final C0008b f54a = new C0008b(this);

    /* renamed from: b */
    public final ArrayList<Short> f55b = new ArrayList<>();

    /* renamed from: c */
    public final ArrayList<Short> f56c = new ArrayList<>();

    /* renamed from: d */
    public final ArrayList<Short> f57d = new ArrayList<>();

    /* renamed from: e */
    public int f58e = 400;

    /* renamed from: f */
    public boolean f59f = false;

    /* renamed from: g */
    public int f60g = 0;

    /* renamed from: h */
    public final C0017f f61h = new C0017f(256);

    /* renamed from: i */
    public final C0017f f62i = new C0017f(800);

    /* renamed from: j */
    public final C0017f f63j = new C0017f(256);

    /* renamed from: k */
    public final C0017f f64k = new C0017f(800);

    /* renamed from: l */
    public int f65l = 0;

    /* renamed from: m */
    public int f66m = 0;

    /* renamed from: n */
    public final int[] f67n = new int[210];

    /* renamed from: o */
    public final int[] f68o = new int[512];

    /* renamed from: p */
    public IEcgResultListener f69p = null;

    /* renamed from: q */
    public IBtResultListener f70q = null;

    /* renamed from: r */
    public IBpResultListener f71r = null;

    /* renamed from: s */
    public ISpo2ResultListener f72s = null;

    /* renamed from: t */
    public IBgResultListener f73t = null;

    /* renamed from: u */
    public IDeviceBatteryCallback f74u = null;

    /* renamed from: v */
    public IBleWriteResponse f75v = null;

    /* renamed from: w */
    public IRawBpDataCallback f76w = null;

    /* renamed from: x */
    public IRawBgDataCallback f77x = null;

    /* renamed from: y */
    public IRawBtDataCallback f78y = null;

    /* renamed from: z */
    public IRawSpo2DataCallback f79z = null;

    /* renamed from: a.a.a.a.d.d$a */
    public class C0012a extends Handler {
        public C0012a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 101:
                    C0011d dVar = C0011d.this;
                    int i = dVar.f42B + 1;
                    dVar.f42B = i;
                    IEcgResultListener iEcgResultListener = dVar.f69p;
                    if (iEcgResultListener != null) {
                        iEcgResultListener.onEcgDuration(i, false);
                    }
                    C0011d.this.f50J.sendEmptyMessageDelayed(101, 1000);
                    return;
                case 102:
                    C0011d dVar2 = C0011d.this;
                    int i2 = dVar2.f47G + 1;
                    dVar2.f47G = i2;
                    if (i2 != 40) {
                        dVar2.f50J.removeMessages(102);
                        dVar2.f50J.sendEmptyMessageDelayed(102, 1000);
                        return;
                    } else if (dVar2.f72s != null) {
                        BleManager.getInstance().stopMeasure(MeasureType.TYPE_SPO2, (IBleWriteResponse) null);
                        C0011d dVar3 = C0011d.this;
                        dVar3.f45E = 0;
                        dVar3.f46F = 0.0d;
                        dVar3.f63j.mo50a();
                        C0011d.this.f61h.mo50a();
                        C0011d.this.f64k.mo50a();
                        C0011d.this.f62i.mo50a();
                        C0011d.this.f72s.onSpo2End();
                        return;
                    } else {
                        return;
                    }
                case 103:
                    C0011d dVar4 = C0011d.this;
                    int i3 = dVar4.f48H + 1;
                    dVar4.f48H = i3;
                    if (i3 == 60) {
                        BleManager.getInstance().stopMeasure(MeasureType.TYPE_BG, (IBleWriteResponse) null);
                        BleManager.getInstance().setMeasuring(false);
                        IBgResultListener iBgResultListener = C0011d.this.f73t;
                        if (iBgResultListener != null) {
                            iBgResultListener.onBgEvent(BgEvent.BG_EVENT_GET_BG_RESULT_TIMEOUT);
                            return;
                        }
                        return;
                    }
                    dVar4.f50J.sendEmptyMessageDelayed(103, 1000);
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: a.a.a.a.d.d$b */
    public class C0013b implements IBleNotifyResponse {
        public C0013b() {
        }

        public void onCharacteristicChanged(byte[] bArr) {
            byte[] bArr2 = new byte[4];
            byte[] bArr3 = new byte[4];
            byte[] bArr4 = new byte[2];
            byte[] bArr5 = new byte[2];
            System.arraycopy(bArr, 0, bArr2, 0, 4);
            System.arraycopy(bArr, 4, bArr3, 0, 4);
            System.arraycopy(bArr, 8, bArr4, 0, 2);
            System.arraycopy(bArr, 10, bArr5, 0, 2);
            int bytes2Int = ArrayUtils.bytes2Int(bArr2);
            int bytes2Int2 = ArrayUtils.bytes2Int(bArr3);
            short bytes2short = ArrayUtils.bytes2short(bArr4);
            short bytes2short2 = ArrayUtils.bytes2short(bArr5);
            LogUtils.m378d("HandleVisionData", "bgNotifyResponse:status = " + bytes2Int + " result = " + bytes2short2);
            if (bytes2Int == 4) {
                if (bytes2short2 <= 600 || bytes2short2 >= 610) {
                    LogUtils.m378d("HandleVisionData", " blood glucose calibration failed");
                    IBgResultListener iBgResultListener = C0011d.this.f73t;
                    if (iBgResultListener != null) {
                        iBgResultListener.onBgEvent(BgEvent.BG_EVENT_CALIBRATION_FAILED);
                        return;
                    }
                    return;
                }
                LogUtils.m378d("HandleVisionData", " blood glucose calibration success");
                BleManager.getInstance().writeOrder(5, C0011d.this.f75v);
            } else if (bytes2Int == 8) {
                C0011d.this.mo43c();
                IBgResultListener iBgResultListener2 = C0011d.this.f73t;
                if (iBgResultListener2 != null) {
                    iBgResultListener2.onBgEvent(BgEvent.BG_EVENT_WAIT_PAGER_INSERT);
                }
            } else if (bytes2Int == 2) {
                C0011d.this.mo43c();
                IBgResultListener iBgResultListener3 = C0011d.this.f73t;
                if (iBgResultListener3 != null) {
                    iBgResultListener3.onBgEvent(BgEvent.BG_EVENT_WAIT_DRIP_BLOOD);
                }
            } else if (bytes2Int == 3) {
                C0011d dVar = C0011d.this;
                dVar.f48H = 0;
                dVar.f50J.sendEmptyMessageDelayed(103, 1000);
                IBgResultListener iBgResultListener4 = C0011d.this.f73t;
                if (iBgResultListener4 != null) {
                    iBgResultListener4.onBgEvent(BgEvent.BG_EVENT_BLOOD_SAMPLE_DETECTING);
                }
            } else if (bytes2Int == 5) {
                C0011d.this.mo43c();
                double round = ((double) Math.round(((double) (((float) bytes2short2) / 10.0f)) * 10.0d)) / 10.0d;
                if (round < 0.0d || round > 33.0d) {
                    round = 0.0d;
                }
                IBgResultListener iBgResultListener5 = C0011d.this.f73t;
                if (iBgResultListener5 != null) {
                    iBgResultListener5.onBgResult(round);
                    C0011d.this.f73t.onBgEvent(BgEvent.BG_EVENT_MEASURE_END);
                }
                IRawBgDataCallback iRawBgDataCallback = C0011d.this.f77x;
                if (iRawBgDataCallback != null) {
                    iRawBgDataCallback.onBgRawData(bytes2Int2, bytes2short, round);
                }
                BleManager.getInstance().setMeasuring(false);
            } else if (bytes2Int == 6) {
                C0011d.this.mo43c();
                IBgResultListener iBgResultListener6 = C0011d.this.f73t;
                if (iBgResultListener6 != null) {
                    iBgResultListener6.onBgEvent(BgEvent.BG_EVENT_PAPER_USED);
                }
            }
        }

        public void onNotifyFailed() {
        }

        public void onNotifySuccess() {
        }
    }

    /* renamed from: a.a.a.a.d.d$c */
    public class C0014c implements IBleNotifyResponse {
        public C0014c() {
        }

        public void onCharacteristicChanged(byte[] bArr) {
            int[] bytes2ints = ArrayUtils.bytes2ints(bArr);
            LogUtils.m378d("HandleVisionData", "btNotifyResponse: " + bArr.length);
            if (C0011d.this.f70q != null) {
                C0011d.this.f70q.onBtResult(((double) Math.round((((double) bytes2ints[2]) / 100.0d) * 10.0d)) / 10.0d);
            }
            if (C0011d.this.f78y != null) {
                LogUtils.m378d("HandleVisionData", "temperature = " + Arrays.toString(bytes2ints));
                C0011d.this.f78y.onBtRawData(bytes2ints[2], bytes2ints[1], bytes2ints[0], bytes2ints.length < 4 ? 0 : bytes2ints[3]);
            }
            BleManager.getInstance().setMeasuring(false);
        }

        public void onNotifyFailed() {
        }

        public void onNotifySuccess() {
        }
    }

    /* renamed from: a.a.a.a.d.d$d */
    public class C0015d implements IBleNotifyResponse {
        public C0015d() {
        }

        public void onCharacteristicChanged(byte[] bArr) {
            IDeviceBatteryCallback iDeviceBatteryCallback = C0011d.this.f74u;
            if (iDeviceBatteryCallback != null) {
                iDeviceBatteryCallback.onDeviceBattery(bArr[0]);
            }
        }

        public void onNotifyFailed() {
        }

        public void onNotifySuccess() {
        }
    }

    /* renamed from: a */
    public void mo38a() {
        this.f42B = 0;
        this.f66m = 0;
        this.f50J.removeMessages(101);
        this.f50J.sendEmptyMessageDelayed(101, 1000);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v14, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r13v0 */
    /* JADX WARNING: type inference failed for: r13v15 */
    /* JADX WARNING: type inference failed for: r13v18, types: [int] */
    /* JADX WARNING: type inference failed for: r13v20 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo39a(int r19, byte[] r20, byte[] r21) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            java.lang.String r3 = "heartRate: "
            r4 = 4636737291354636288(0x4059000000000000, double:100.0)
            r6 = 30
            r7 = 5
            r8 = 4
            r9 = 200(0xc8, float:2.8E-43)
            r10 = 2
            java.lang.String r11 = "HandleVisionData"
            r12 = 1
            r13 = 0
            if (r1 == r12) goto L_0x0404
            r14 = 8
            if (r1 == r14) goto L_0x03ee
            r15 = 3
            if (r1 == r15) goto L_0x0348
            if (r1 == r8) goto L_0x0173
            if (r1 == r7) goto L_0x014f
            r2 = 16
            if (r1 == r2) goto L_0x00cd
            r2 = 17
            if (r1 == r2) goto L_0x002c
            goto L_0x0562
        L_0x002c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "onBleData:BP_DE_LAST_PKG  "
            r1.append(r2)
            java.util.ArrayList<java.lang.Short> r2 = r0.f55b
            int r2 = r2.size()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.mintti.visionsdk.common.LogUtils.m378d(r11, r1)
            java.util.ArrayList<java.lang.Short> r1 = r0.f55b
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x00c5
            java.util.ArrayList<java.lang.Short> r1 = r0.f55b
            int r1 = r1.size()
            r2 = 1000(0x3e8, float:1.401E-42)
            if (r1 >= r2) goto L_0x0059
            goto L_0x00c5
        L_0x0059:
            java.util.ArrayList<java.lang.Short> r1 = r0.f55b
            int r1 = r1.size()
            double[] r1 = new double[r1]
            r2 = 0
        L_0x0062:
            java.util.ArrayList<java.lang.Short> r7 = r0.f55b
            int r7 = r7.size()
            if (r2 >= r7) goto L_0x007d
            java.util.ArrayList<java.lang.Short> r7 = r0.f55b
            java.lang.Object r7 = r7.get(r2)
            java.lang.Short r7 = (java.lang.Short) r7
            short r7 = r7.shortValue()
            double r7 = (double) r7
            double r7 = r7 / r4
            r1[r2] = r7
            int r2 = r2 + 1
            goto L_0x0062
        L_0x007d:
            int[] r2 = new int[r15]
            com.mintti.visionsdk.AlgoHelper.calcBloodPressure(r1, r2)
            com.mintti.visionsdk.ble.callback.IBpResultListener r1 = r0.f71r
            if (r1 == 0) goto L_0x03fa
            r1 = r2[r10]
            if (r1 < r6) goto L_0x008c
            if (r1 <= r9) goto L_0x008d
        L_0x008c:
            r1 = 0
        L_0x008d:
            r4 = r2[r13]
            r2 = r2[r12]
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            r5.append(r1)
            java.lang.String r3 = " systolic: "
            r5.append(r3)
            r5.append(r4)
            java.lang.String r3 = " diastolic: "
            r5.append(r3)
            r5.append(r2)
            java.lang.String r3 = r5.toString()
            java.lang.String r5 = "CalcBloodPressureByDe"
            com.mintti.visionsdk.common.LogUtils.m378d(r5, r3)
            if (r2 < r4) goto L_0x00be
            com.mintti.visionsdk.ble.callback.IBpResultListener r1 = r0.f71r
            r1.onBpError()
            goto L_0x03fa
        L_0x00be:
            com.mintti.visionsdk.ble.callback.IBpResultListener r3 = r0.f71r
            r3.onBpResult(r4, r2, r1)
            goto L_0x03fa
        L_0x00c5:
            com.mintti.visionsdk.ble.callback.IBpResultListener r1 = r0.f71r
            if (r1 == 0) goto L_0x00cc
            r1.onLeadError()
        L_0x00cc:
            return
        L_0x00cd:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "onBleData:BP_ADD_LAST_PKG  "
            r1.append(r2)
            java.util.ArrayList<java.lang.Short> r2 = r0.f57d
            int r2 = r2.size()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.mintti.visionsdk.common.LogUtils.m378d(r11, r1)
            java.util.ArrayList<java.lang.Short> r1 = r0.f57d
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x00fe
            com.mintti.visionsdk.ble.callback.IBpResultListener r1 = r0.f71r
            if (r1 == 0) goto L_0x00f6
            r1.onLeadError()
        L_0x00f6:
            com.mintti.visionsdk.ble.BleManager r1 = com.mintti.visionsdk.ble.BleManager.getInstance()
            r1.setMeasuring(r13)
            return
        L_0x00fe:
            java.util.ArrayList<java.lang.Short> r1 = r0.f57d
            int r2 = r1.size()
            int r2 = r2 - r12
            java.lang.Object r1 = r1.get(r2)
            java.lang.Short r1 = (java.lang.Short) r1
            short r1 = r1.shortValue()
            r2 = 10000(0x2710, float:1.4013E-41)
            if (r1 >= r2) goto L_0x0562
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "min = "
            r1.append(r2)
            java.util.ArrayList<java.lang.Short> r2 = r0.f57d
            java.lang.Object r2 = r2.get(r13)
            r1.append(r2)
            java.lang.String r2 = " max = "
            r1.append(r2)
            java.util.ArrayList<java.lang.Short> r2 = r0.f57d
            int r3 = r2.size()
            int r3 = r3 - r12
            java.lang.Object r2 = r2.get(r3)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.mintti.visionsdk.common.LogUtils.m378d(r11, r1)
            com.mintti.visionsdk.ble.callback.IBpResultListener r1 = r0.f71r
            if (r1 == 0) goto L_0x0147
            r1.onLeadError()
        L_0x0147:
            com.mintti.visionsdk.ble.BleManager r1 = com.mintti.visionsdk.ble.BleManager.getInstance()
            r1.setMeasuring(r13)
            return
        L_0x014f:
            short[] r1 = com.mintti.visionsdk.common.ArrayUtils.bytes2ShortArray(r20)
            int r2 = r1.length
        L_0x0154:
            if (r13 >= r2) goto L_0x0562
            short r3 = r1[r13]
            java.util.ArrayList<java.lang.Short> r4 = r0.f55b
            java.lang.Short r5 = java.lang.Short.valueOf(r3)
            r4.add(r5)
            com.mintti.visionsdk.ble.callback.IRawBpDataCallback r4 = r0.f76w
            if (r4 == 0) goto L_0x0170
            r4.onDecompressionData(r3)
            com.mintti.visionsdk.ble.callback.IRawBpDataCallback r4 = r0.f76w
            int r3 = r3 / 100
            short r3 = (short) r3
            r4.onPressure(r3)
        L_0x0170:
            int r13 = r13 + 1
            goto L_0x0154
        L_0x0173:
            long r3 = java.lang.System.currentTimeMillis()
            long r5 = r0.f43C
            long r3 = r3 - r5
            r5 = 90000(0x15f90, double:4.4466E-319)
            r1 = 0
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x01a5
            com.mintti.visionsdk.ble.BleManager r2 = com.mintti.visionsdk.ble.BleManager.getInstance()
            com.mintti.visionsdk.ble.bean.MeasureType r3 = com.mintti.visionsdk.ble.bean.MeasureType.TYPE_BP
            r2.stopMeasure(r3, r1)
            java.lang.String r1 = "429"
            com.mintti.visionsdk.common.LogUtils.m378d(r11, r1)
            com.mintti.visionsdk.ble.BleManager r1 = com.mintti.visionsdk.ble.BleManager.getInstance()
            r1.setMeasuring(r13)
            com.mintti.visionsdk.ble.callback.IBpResultListener r1 = r0.f71r
            if (r1 == 0) goto L_0x019e
            r1.onLeadError()
        L_0x019e:
            long r1 = java.lang.System.currentTimeMillis()
            r0.f43C = r1
            return
        L_0x01a5:
            short[] r2 = com.mintti.visionsdk.common.ArrayUtils.bytes2ShortArray(r20)
            int r3 = r2.length
            r4 = 0
        L_0x01ab:
            if (r4 >= r3) goto L_0x0562
            short r5 = r2[r4]
            java.util.ArrayList<java.lang.Short> r6 = r0.f57d
            java.lang.Short r7 = java.lang.Short.valueOf(r5)
            r6.add(r7)
            com.mintti.visionsdk.ble.callback.IRawBpDataCallback r6 = r0.f76w
            if (r6 == 0) goto L_0x01c7
            r6.onPressurizationData(r5)
            com.mintti.visionsdk.ble.callback.IRawBpDataCallback r6 = r0.f76w
            int r7 = r5 / 100
            short r7 = (short) r7
            r6.onPressure(r7)
        L_0x01c7:
            r6 = 22000(0x55f0, float:3.0829E-41)
            if (r5 < r6) goto L_0x01f9
            long r6 = java.lang.System.currentTimeMillis()
            long r14 = r0.f43C
            long r6 = r6 - r14
            r14 = 10000(0x2710, double:4.9407E-320)
            int r8 = (r6 > r14 ? 1 : (r6 == r14 ? 0 : -1))
            if (r8 >= 0) goto L_0x01f9
            com.mintti.visionsdk.ble.BleManager r2 = com.mintti.visionsdk.ble.BleManager.getInstance()
            com.mintti.visionsdk.ble.bean.MeasureType r3 = com.mintti.visionsdk.ble.bean.MeasureType.TYPE_BP
            r2.stopMeasure(r3, r1)
            java.lang.String r1 = "448"
            com.mintti.visionsdk.common.LogUtils.m378d(r11, r1)
            com.mintti.visionsdk.ble.BleManager r1 = com.mintti.visionsdk.ble.BleManager.getInstance()
            r1.setMeasuring(r13)
            com.mintti.visionsdk.ble.callback.IBpResultListener r1 = r0.f71r
            if (r1 == 0) goto L_0x01f4
            r1.onBpError()
        L_0x01f4:
            r1 = 0
            r0.f43C = r1
            return
        L_0x01f9:
            r6 = 16000(0x3e80, float:2.2421E-41)
            if (r5 < r6) goto L_0x0327
            java.util.ArrayList<java.lang.Short> r6 = r0.f56c
            int r6 = r6.size()
            int r7 = r0.f58e
            if (r6 < r7) goto L_0x0327
            boolean r6 = r0.f59f
            if (r6 != 0) goto L_0x0327
            java.util.ArrayList<java.lang.Short> r6 = r0.f56c
            int r6 = r6.size()
            double[] r6 = new double[r6]
            java.util.ArrayList<java.lang.Short> r7 = r0.f56c
            java.lang.Object r7 = r7.get(r13)
            java.lang.Short r7 = (java.lang.Short) r7
            short r7 = r7.shortValue()
            double r7 = (double) r7
            java.util.ArrayList<java.lang.Short> r14 = r0.f56c
            java.lang.Object r14 = r14.get(r13)
            java.lang.Short r14 = (java.lang.Short) r14
            short r14 = r14.shortValue()
            double r14 = (double) r14
            r1 = 0
        L_0x022e:
            java.util.ArrayList<java.lang.Short> r9 = r0.f56c
            int r9 = r9.size()
            if (r1 >= r9) goto L_0x0287
            java.util.ArrayList<java.lang.Short> r9 = r0.f56c
            java.lang.Object r9 = r9.get(r1)
            java.lang.Short r9 = (java.lang.Short) r9
            short r9 = r9.shortValue()
            double r12 = (double) r9
            r6[r1] = r12
            java.util.ArrayList<java.lang.Short> r9 = r0.f56c
            java.lang.Object r9 = r9.get(r1)
            java.lang.Short r9 = (java.lang.Short) r9
            short r9 = r9.shortValue()
            double r12 = (double) r9
            int r9 = (r12 > r7 ? 1 : (r12 == r7 ? 0 : -1))
            if (r9 <= 0) goto L_0x0263
            java.util.ArrayList<java.lang.Short> r7 = r0.f56c
            java.lang.Object r7 = r7.get(r1)
            java.lang.Short r7 = (java.lang.Short) r7
            short r7 = r7.shortValue()
            double r7 = (double) r7
        L_0x0263:
            java.util.ArrayList<java.lang.Short> r9 = r0.f56c
            java.lang.Object r9 = r9.get(r1)
            java.lang.Short r9 = (java.lang.Short) r9
            short r9 = r9.shortValue()
            double r12 = (double) r9
            int r9 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r9 >= 0) goto L_0x0282
            java.util.ArrayList<java.lang.Short> r9 = r0.f56c
            java.lang.Object r9 = r9.get(r1)
            java.lang.Short r9 = (java.lang.Short) r9
            short r9 = r9.shortValue()
            double r12 = (double) r9
            r14 = r12
        L_0x0282:
            int r1 = r1 + 1
            r12 = 1
            r13 = 0
            goto L_0x022e
        L_0x0287:
            r12 = 4670021707350671360(0x40cf400000000000, double:16000.0)
            int r1 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r1 > 0) goto L_0x0308
            double r7 = r7 - r14
            r12 = 4662219572839972864(0x40b3880000000000, double:5000.0)
            int r1 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1))
            if (r1 >= 0) goto L_0x029b
            goto L_0x0308
        L_0x029b:
            double[] r1 = new double[r10]
            int r6 = com.mintti.visionsdk.AlgoHelper.calcStopPressure(r6, r1)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "加压停止标记  "
            r7.append(r8)
            r7.append(r6)
            java.lang.String r8 = " addShort = "
            r7.append(r8)
            r7.append(r5)
            java.lang.String r8 = " stopPressure[0] = "
            r7.append(r8)
            r8 = 0
            r12 = r1[r8]
            r7.append(r12)
            java.lang.String r9 = " stopPressure[1] =  "
            r7.append(r9)
            r9 = 1
            r12 = r1[r9]
            r7.append(r12)
            java.lang.String r7 = r7.toString()
            com.mintti.visionsdk.common.LogUtils.m378d(r11, r7)
            if (r6 != 0) goto L_0x02fa
            r6 = r1[r8]
            int r6 = (int) r6
            r12 = r1[r9]
            int r1 = (int) r12
            r0.f59f = r9
            r7 = 3
            int[] r12 = new int[r7]
            r13 = 8
            r12[r8] = r13
            r12[r9] = r6
            r12[r10] = r1
            byte[] r8 = com.mintti.visionsdk.common.ArrayUtils.ints2ByteArray(r12)
            com.mintti.visionsdk.ble.BleManager r9 = com.mintti.visionsdk.ble.BleManager.getInstance()
            a.a.a.a.d.e r12 = new a.a.a.a.d.e
            r12.<init>(r0, r6, r1)
            r9.writeOrder((byte[]) r8, (com.mintti.visionsdk.ble.callback.IBleWriteResponse) r12)
            goto L_0x02fb
        L_0x02fa:
            r7 = 3
        L_0x02fb:
            java.util.ArrayList<java.lang.Short> r1 = r0.f56c
            int r1 = r1.size()
            r6 = 200(0xc8, float:2.8E-43)
            int r1 = r1 + r6
            r0.f58e = r1
            r6 = 0
            goto L_0x0329
        L_0x0308:
            com.mintti.visionsdk.ble.BleManager r1 = com.mintti.visionsdk.ble.BleManager.getInstance()
            com.mintti.visionsdk.ble.bean.MeasureType r2 = com.mintti.visionsdk.ble.bean.MeasureType.TYPE_BP
            r6 = 0
            r1.stopMeasure(r2, r6)
            java.lang.String r1 = "478"
            com.mintti.visionsdk.common.LogUtils.m378d(r11, r1)
            com.mintti.visionsdk.ble.BleManager r1 = com.mintti.visionsdk.ble.BleManager.getInstance()
            r2 = 0
            r1.setMeasuring(r2)
            com.mintti.visionsdk.ble.callback.IBpResultListener r1 = r0.f71r
            if (r1 == 0) goto L_0x0326
            r1.onBpError()
        L_0x0326:
            return
        L_0x0327:
            r6 = r1
            r7 = 3
        L_0x0329:
            int r1 = r0.f60g
            r8 = 1
            int r1 = r1 + r8
            r0.f60g = r1
            r8 = 200(0xc8, float:2.8E-43)
            if (r1 < r8) goto L_0x033c
            java.util.ArrayList<java.lang.Short> r1 = r0.f56c
            java.lang.Short r5 = java.lang.Short.valueOf(r5)
            r1.add(r5)
        L_0x033c:
            int r4 = r4 + 1
            r1 = r6
            r9 = 200(0xc8, float:2.8E-43)
            r12 = 1
            r13 = 0
            r14 = 8
            r15 = 3
            goto L_0x01ab
        L_0x0348:
            boolean r1 = r0.f49I
            if (r1 == 0) goto L_0x03a0
            short[] r1 = com.mintti.visionsdk.common.ArrayUtils.bytes2ShortArray(r20)
            int r2 = r1.length
            int[] r3 = new int[r2]
            r4 = 0
        L_0x0354:
            int r5 = r1.length
            if (r4 >= r5) goto L_0x035e
            short r5 = r1[r4]
            r3[r4] = r5
            int r4 = r4 + 1
            goto L_0x0354
        L_0x035e:
            int r1 = r0.f65l
            r4 = 8
            if (r1 < r4) goto L_0x0395
            int[] r1 = r0.f68o
            com.mintti.visionsdk.AlgoHelper.filtFiltWrapperLowCost(r1)
            int[] r1 = r0.f68o
            int r4 = r1.length
            r5 = 0
        L_0x036d:
            if (r5 >= r4) goto L_0x0391
            r6 = r1[r5]
            com.mintti.visionsdk.ble.BleManager r7 = com.mintti.visionsdk.ble.BleManager.getInstance()
            boolean r7 = r7.isMeasuring()
            if (r7 == 0) goto L_0x038e
            com.kl.vision_ecg.EcgAlgo r7 = r0.f41A
            int r6 = -r6
            r7.addECGData(r6)
            int r7 = r0.f66m
            r8 = 1
            int r7 = r7 + r8
            r0.f66m = r7
            com.mintti.visionsdk.ble.callback.IEcgResultListener r7 = r0.f69p
            if (r7 == 0) goto L_0x038e
            r7.onDrawWave(r6)
        L_0x038e:
            int r5 = r5 + 1
            goto L_0x036d
        L_0x0391:
            r5 = 0
            r0.f65l = r5
            goto L_0x0396
        L_0x0395:
            r5 = 0
        L_0x0396:
            int[] r1 = r0.f68o
            int r4 = r0.f65l
            int r4 = r4 * r2
            java.lang.System.arraycopy(r3, r5, r1, r4, r2)
            goto L_0x03e6
        L_0x03a0:
            int[] r1 = com.mintti.visionsdk.common.ArrayUtils.bytes2ints(r20)
            int r2 = r0.f65l
            r3 = 14
            if (r2 < r3) goto L_0x03da
            int[] r2 = r0.f67n
            com.mintti.visionsdk.AlgoHelper.filtFiltWrapper(r2)
            int[] r2 = r0.f67n
            int r3 = r2.length
            r4 = 0
        L_0x03b3:
            if (r4 >= r3) goto L_0x03d6
            r5 = r2[r4]
            com.mintti.visionsdk.ble.BleManager r6 = com.mintti.visionsdk.ble.BleManager.getInstance()
            boolean r6 = r6.isMeasuring()
            if (r6 == 0) goto L_0x03d3
            com.kl.vision_ecg.EcgAlgo r6 = r0.f41A
            r6.addECGData(r5)
            int r6 = r0.f66m
            r7 = 1
            int r6 = r6 + r7
            r0.f66m = r6
            com.mintti.visionsdk.ble.callback.IEcgResultListener r6 = r0.f69p
            if (r6 == 0) goto L_0x03d3
            r6.onDrawWave(r5)
        L_0x03d3:
            int r4 = r4 + 1
            goto L_0x03b3
        L_0x03d6:
            r4 = 0
            r0.f65l = r4
            goto L_0x03db
        L_0x03da:
            r4 = 0
        L_0x03db:
            int[] r2 = r0.f67n
            int r3 = r0.f65l
            int r5 = r1.length
            int r3 = r3 * r5
            int r5 = r1.length
            java.lang.System.arraycopy(r1, r4, r2, r3, r5)
        L_0x03e6:
            int r1 = r0.f65l
            r2 = 1
            int r1 = r1 + r2
            r0.f65l = r1
            goto L_0x0562
        L_0x03ee:
            java.lang.String r1 = "onBleData:BP_LEAK_TYPE"
            com.mintti.visionsdk.common.LogUtils.m378d(r11, r1)
            com.mintti.visionsdk.ble.callback.IBpResultListener r1 = r0.f71r
            if (r1 == 0) goto L_0x03fa
            r1.onLeadError()
        L_0x03fa:
            com.mintti.visionsdk.ble.BleManager r1 = com.mintti.visionsdk.ble.BleManager.getInstance()
            r2 = 0
            r1.setMeasuring(r2)
            goto L_0x0562
        L_0x0404:
            int r1 = r2.length
            int r1 = r1 / r10
            byte[] r1 = new byte[r1]
            int r9 = r2.length
            int r9 = r9 / r10
            byte[] r9 = new byte[r9]
            r12 = 0
            r13 = 0
        L_0x040e:
            int r14 = r2.length
            if (r12 >= r14) goto L_0x0449
            byte r14 = r2[r12]
            r1[r13] = r14
            int r14 = r13 + 1
            int r15 = r12 + 1
            byte r15 = r2[r15]
            r1[r14] = r15
            int r15 = r13 + 2
            int r16 = r12 + 2
            byte r16 = r2[r16]
            r1[r15] = r16
            int r16 = r13 + 3
            int r17 = r12 + 3
            byte r17 = r2[r17]
            r1[r16] = r17
            int r17 = r12 + 4
            byte r17 = r2[r17]
            r9[r13] = r17
            int r17 = r12 + 5
            byte r17 = r2[r17]
            r9[r14] = r17
            int r14 = r12 + 6
            byte r14 = r2[r14]
            r9[r15] = r14
            int r14 = r12 + 7
            byte r14 = r2[r14]
            r9[r16] = r14
            int r12 = r12 + 8
            int r13 = r13 + r8
            goto L_0x040e
        L_0x0449:
            com.mintti.visionsdk.ble.callback.IRawSpo2DataCallback r2 = r0.f79z
            if (r2 == 0) goto L_0x0450
            r2.onSpo2RawData(r1, r9)
        L_0x0450:
            int[] r2 = com.mintti.visionsdk.common.ArrayUtils.bytes2ints(r9)
            int r8 = r2.length
            r9 = 0
        L_0x0456:
            if (r9 >= r8) goto L_0x0464
            r12 = r2[r9]
            com.mintti.visionsdk.ble.callback.ISpo2ResultListener r13 = r0.f72s
            if (r13 == 0) goto L_0x0461
            r13.onWaveData(r12)
        L_0x0461:
            int r9 = r9 + 1
            goto L_0x0456
        L_0x0464:
            int[] r1 = com.mintti.visionsdk.common.ArrayUtils.bytes2ints(r1)
            a.a.a.a.d.f r8 = r0.f61h
            int[] r8 = r8.mo51a(r1)
            a.a.a.a.d.f r9 = r0.f63j
            int[] r9 = r9.mo51a(r2)
            a.a.a.a.d.f r12 = r0.f62i
            double[] r1 = r12.mo52b(r1)
            a.a.a.a.d.f r12 = r0.f64k
            double[] r2 = r12.mo52b(r2)
            if (r8 == 0) goto L_0x04f7
            if (r9 == 0) goto L_0x04f7
            double[] r12 = new double[r10]
            r13 = 256(0x100, float:3.59E-43)
            com.mintti.visionsdk.AlgoHelper.calcSpo2(r9, r13, r8, r12)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "血氧算法结果 spo[0] = "
            r8.append(r9)
            r9 = 0
            r13 = r12[r9]
            r8.append(r13)
            java.lang.String r9 = " spo[1] = "
            r8.append(r9)
            r9 = 1
            r13 = r12[r9]
            r8.append(r13)
            java.lang.String r8 = r8.toString()
            com.mintti.visionsdk.common.LogUtils.m378d(r11, r8)
            r13 = r12[r9]
            r8 = 0
            int r15 = (r13 > r8 ? 1 : (r13 == r8 ? 0 : -1))
            if (r15 == 0) goto L_0x04f7
            com.mintti.visionsdk.ble.callback.ISpo2ResultListener r13 = r0.f72s
            if (r13 == 0) goto L_0x04f7
            r13 = 0
            r14 = r12[r13]
            r12 = 4621819117588971520(0x4024000000000000, double:10.0)
            double r14 = r14 * r12
            long r14 = java.lang.Math.round(r14)
            double r14 = (double) r14
            double r14 = r14 / r12
            r0.f46F = r14
            r12 = 4634626229029306368(0x4051800000000000, double:70.0)
            int r16 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r16 < 0) goto L_0x04d6
            int r12 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r12 <= 0) goto L_0x04d8
        L_0x04d6:
            r0.f46F = r8
        L_0x04d8:
            com.mintti.visionsdk.ble.callback.ISpo2ResultListener r4 = r0.f72s
            int r5 = r0.f45E
            double r8 = r0.f46F
            r4.onSpo2ResultData(r5, r8)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = " Spo2: "
            r4.append(r5)
            double r8 = r0.f46F
            r4.append(r8)
            java.lang.String r4 = r4.toString()
            com.mintti.visionsdk.common.LogUtils.m378d(r11, r4)
        L_0x04f7:
            if (r1 == 0) goto L_0x0562
            if (r2 == 0) goto L_0x0562
            int[] r4 = new int[r10]
            r5 = 800(0x320, float:1.121E-42)
            com.mintti.visionsdk.AlgoHelper.calcHeartRate(r2, r5, r1, r4)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "血氧算法结果 spo[0] =  hr[0] = "
            r1.append(r2)
            r2 = 0
            r5 = r4[r2]
            r1.append(r5)
            java.lang.String r2 = " hr[1] = "
            r1.append(r2)
            r2 = 1
            r5 = r4[r2]
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            com.mintti.visionsdk.common.LogUtils.m378d(r11, r1)
            r1 = r4[r2]
            if (r1 == 0) goto L_0x0562
            com.mintti.visionsdk.ble.callback.ISpo2ResultListener r1 = r0.f72s
            if (r1 == 0) goto L_0x0562
            r1 = 0
            r2 = r4[r1]
            if (r2 < r6) goto L_0x0539
            r4 = 200(0xc8, float:2.8E-43)
            if (r2 <= r4) goto L_0x0537
            goto L_0x0539
        L_0x0537:
            r13 = r2
            goto L_0x053a
        L_0x0539:
            r13 = 0
        L_0x053a:
            int r1 = r0.f44D
            int r1 = r1 - r13
            int r1 = java.lang.Math.abs(r1)
            if (r1 >= r7) goto L_0x0545
            r0.f45E = r13
        L_0x0545:
            com.mintti.visionsdk.ble.callback.ISpo2ResultListener r1 = r0.f72s
            int r2 = r0.f45E
            double r4 = r0.f46F
            r1.onSpo2ResultData(r2, r4)
            r0.f44D = r13
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r1.append(r13)
            java.lang.String r1 = r1.toString()
            com.mintti.visionsdk.common.LogUtils.m378d(r11, r1)
        L_0x0562:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p000a.p001a.p002a.p003a.p004d.C0011d.mo39a(int, byte[], byte[]):void");
    }

    public void algoData(int i, int i2) {
        if (i != 0) {
            if (i == 1 && this.f69p != null) {
                if (i2 < 0 || i2 > 120) {
                    i2 = 0;
                }
                LogUtils.m378d("HandleVisionData", "ecg respiratoryRate: " + i2);
                this.f69p.onRespiratoryRate(i2);
            }
        } else if (this.f69p != null) {
            if (i2 < 30 || i2 > 200) {
                i2 = 0;
            }
            LogUtils.m378d("HandleVisionData", "ecg heartRate: " + i2);
            this.f69p.onHeartRate(i2);
        }
    }

    public void algoData(int i, int i2, int i3) {
    }

    /* renamed from: b */
    public void mo42b() {
        this.f50J.removeMessages(101);
        this.f69p.onEcgDuration(this.f42B, true);
        this.f42B = 0;
        this.f66m = 0;
    }

    /* renamed from: c */
    public void mo43c() {
        if (this.f50J.hasMessages(103)) {
            this.f50J.removeMessages(103);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x01e6  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0193  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCharacteristicChanged(byte[] r24) {
        /*
            r23 = this;
            r0 = r23
            r1 = r24
            a.a.a.a.d.b r2 = r0.f54a
            r2.getClass()
            int r3 = r1.length
            p000a.p001a.p002a.p003a.p004d.C0008b.f28c = r3
            r4 = 0
            byte r5 = r1[r4]
            r6 = 255(0xff, float:3.57E-43)
            r5 = r5 & r6
            r8 = 7
            r9 = 6
            r10 = 5
            r11 = 4
            r12 = 3
            r13 = 8
            r14 = 250(0xfa, float:3.5E-43)
            r15 = 2
            r7 = 1
            if (r5 != r14) goto L_0x0073
            byte r5 = r1[r7]
            r5 = r5 & r6
            if (r5 != r14) goto L_0x0073
            byte r5 = r1[r15]
            byte r5 = r1[r11]
            int r5 = r5 << r13
            byte r11 = r1[r12]
            r11 = r11 & r6
            r5 = r5 | r11
            byte[] r11 = new byte[r12]
            byte r18 = r1[r10]
            r11[r4] = r18
            byte r18 = r1[r9]
            r11[r7] = r18
            byte r18 = r1[r8]
            r11[r15] = r18
            byte[] r8 = new byte[r15]
            byte r19 = r1[r13]
            r8[r4] = r19
            r16 = 9
            byte r19 = r1[r16]
            r8[r7] = r19
            r17 = 10
            int r5 = r5 + 10
            int r19 = r5 % r3
            int r3 = r5 / r3
            if (r19 != 0) goto L_0x0052
            goto L_0x0053
        L_0x0052:
            int r3 = r3 + r7
        L_0x0053:
            a.a.a.a.d.b$a r13 = new a.a.a.a.d.b$a
            r13.<init>()
            p000a.p001a.p002a.p003a.p004d.C0008b.f29d = r13
            r13.f35d = r5
            r13.f33b = r11
            r13.f32a = r3
            r13.f34c = r8
            byte[] r3 = new byte[r5]
            r13.f36e = r3
            r3 = 0
        L_0x0067:
            byte[] r5 = r13.f36e
            int r8 = r5.length
            if (r3 >= r8) goto L_0x0071
            r5[r3] = r4
            int r3 = r3 + 1
            goto L_0x0067
        L_0x0071:
            r13.f39h = r4
        L_0x0073:
            a.a.a.a.d.b$a r3 = p000a.p001a.p002a.p003a.p004d.C0008b.f29d
            if (r3 == 0) goto L_0x01f9
            int r5 = r1.length
            int r8 = r3.f39h
            int r11 = r3.f32a
            int r11 = r11 - r7
            if (r8 != r11) goto L_0x0086
            int r5 = r3.f35d
            int r11 = p000a.p001a.p002a.p003a.p004d.C0008b.f28c
            int r11 = r11 * r8
            int r5 = r5 - r11
        L_0x0086:
            int r11 = p000a.p001a.p002a.p003a.p004d.C0008b.f28c
            int r8 = r8 * r11
            r11 = 0
        L_0x008b:
            if (r11 >= r5) goto L_0x0098
            byte[] r13 = r3.f36e
            int r20 = r8 + r11
            byte r21 = r1[r11]
            r13[r20] = r21
            int r11 = r11 + 1
            goto L_0x008b
        L_0x0098:
            int r5 = r3.f39h
            int r5 = r5 + r7
            r3.f39h = r5
            a.a.a.a.d.b$a r3 = p000a.p001a.p002a.p003a.p004d.C0008b.f29d
            int r5 = r3.f39h
            int r8 = r3.f32a
            if (r5 != r8) goto L_0x01f9
            byte[] r5 = r3.f36e
            int r5 = r5.length
            r8 = 10
            int r5 = r5 - r8
            r8 = 0
            r11 = 0
        L_0x00ad:
            int r13 = r5 + -2
            if (r8 >= r13) goto L_0x00d4
            byte[] r13 = r3.f36e
            int r20 = r8 + 10
            byte r9 = r13[r20]
            r9 = r9 & r6
            if (r9 != r14) goto L_0x00d0
            int r9 = r8 + 1
            r17 = 10
            int r9 = r9 + 10
            byte r9 = r13[r9]
            r9 = r9 & r6
            if (r9 != 0) goto L_0x00d0
            int r9 = r8 + 2
            int r9 = r9 + 10
            byte r9 = r13[r9]
            r9 = r9 & r6
            if (r9 != r14) goto L_0x00d0
            int r11 = r11 + 1
        L_0x00d0:
            int r8 = r8 + 1
            r9 = 6
            goto L_0x00ad
        L_0x00d4:
            int r8 = r5 - r11
            r9 = 10
            int r8 = r8 + r9
            byte[] r8 = new byte[r8]
            r3.f37f = r8
            r8 = 11
            r11 = 1
        L_0x00e0:
            int r13 = r5 + -1
            if (r11 >= r13) goto L_0x010f
            byte[] r13 = r3.f36e
            int r17 = r11 + -1
            int r17 = r17 + 10
            byte r10 = r13[r17]
            r10 = r10 & r6
            if (r10 != r14) goto L_0x00fe
            int r10 = r11 + 10
            byte r10 = r13[r10]
            r10 = r10 & r6
            if (r10 != 0) goto L_0x00fe
            int r10 = r11 + 1
            int r10 = r10 + r9
            byte r9 = r13[r10]
            r9 = r9 & r6
            if (r9 == r14) goto L_0x0109
        L_0x00fe:
            byte[] r9 = r3.f37f
            int r10 = r8 + 1
            int r22 = r11 + 10
            byte r13 = r13[r22]
            r9[r8] = r13
            r8 = r10
        L_0x0109:
            int r11 = r11 + 1
            r9 = 10
            r10 = 5
            goto L_0x00e0
        L_0x010f:
            byte[] r9 = r3.f37f
            byte[] r10 = r3.f36e
            r13 = 10
            byte r14 = r10[r13]
            r9[r13] = r14
            if (r5 <= r7) goto L_0x0120
            int r11 = r11 + r13
            byte r5 = r10[r11]
            r9[r8] = r5
        L_0x0120:
            r5 = 0
        L_0x0121:
            if (r5 >= r13) goto L_0x012e
            byte[] r8 = r3.f37f
            byte[] r9 = r3.f36e
            byte r9 = r9[r5]
            r8[r5] = r9
            int r5 = r5 + 1
            goto L_0x0121
        L_0x012e:
            a.a.a.a.d.b$a r3 = p000a.p001a.p002a.p003a.p004d.C0008b.f29d
            byte[] r5 = r3.f37f
            int r8 = r5.length
            int r8 = r8 - r13
            r9 = 0
            if (r8 <= 0) goto L_0x014e
            int r5 = r5.length
            int r5 = r5 - r13
            byte[] r5 = new byte[r5]
            r3.f38g = r5
            r5 = 0
        L_0x013e:
            byte[] r8 = r3.f38g
            int r10 = r8.length
            if (r5 >= r10) goto L_0x0150
            byte[] r10 = r3.f37f
            int r11 = r5 + 10
            byte r10 = r10[r11]
            r8[r5] = r10
            int r5 = r5 + 1
            goto L_0x013e
        L_0x014e:
            r3.f38g = r9
        L_0x0150:
            byte[] r5 = r3.f38g
            if (r5 == 0) goto L_0x0190
            a.a.a.a.d.b r8 = p000a.p001a.p002a.p003a.p004d.C0008b.this
            a.a.a.a.d.a r8 = r8.f31b
            int r10 = r5.length
            r8.getClass()
            byte[] r11 = new byte[r15]
            r9 = 255(0xff, float:3.57E-43)
            r13 = 0
            r14 = 255(0xff, float:3.57E-43)
        L_0x0163:
            if (r13 >= r10) goto L_0x0179
            byte r22 = r5[r13]
            r14 = r14 ^ r22
            r14 = r14 & r6
            byte[] r6 = r8.f26a
            byte r6 = r6[r14]
            r6 = r6 ^ r9
            byte[] r9 = r8.f27b
            byte r9 = r9[r14]
            int r13 = r13 + 1
            r14 = r6
            r6 = 255(0xff, float:3.57E-43)
            goto L_0x0163
        L_0x0179:
            byte r5 = (byte) r9
            r11[r4] = r5
            byte r5 = (byte) r14
            r11[r7] = r5
            byte[] r3 = r3.f34c
            byte r5 = r3[r4]
            byte r6 = r11[r4]
            if (r5 != r6) goto L_0x018e
            byte r3 = r3[r7]
            byte r5 = r11[r7]
            if (r3 != r5) goto L_0x018e
            goto L_0x0190
        L_0x018e:
            r3 = 0
            goto L_0x0191
        L_0x0190:
            r3 = 1
        L_0x0191:
            if (r3 == 0) goto L_0x01e6
            a.a.a.a.d.b$a r3 = p000a.p001a.p002a.p003a.p004d.C0008b.f29d
            byte[] r5 = r3.f33b
            byte r6 = r5[r4]
            if (r6 != r7) goto L_0x019d
            r4 = 1
            goto L_0x01d5
        L_0x019d:
            byte r6 = r5[r4]
            if (r6 != r12) goto L_0x01a3
            r4 = 3
            goto L_0x01d5
        L_0x01a3:
            byte r6 = r5[r4]
            r8 = 4
            if (r6 != r8) goto L_0x01b1
            byte r4 = r5[r15]
            if (r4 != r7) goto L_0x01af
            r4 = 16
            goto L_0x01d5
        L_0x01af:
            r4 = 4
            goto L_0x01d5
        L_0x01b1:
            byte r6 = r5[r4]
            r8 = 5
            if (r6 != r8) goto L_0x01bf
            byte r4 = r5[r15]
            if (r4 != r7) goto L_0x01bd
            r4 = 17
            goto L_0x01d5
        L_0x01bd:
            r4 = 5
            goto L_0x01d5
        L_0x01bf:
            byte r6 = r5[r4]
            r7 = 6
            if (r6 != r7) goto L_0x01c6
            r4 = 6
            goto L_0x01d5
        L_0x01c6:
            byte r6 = r5[r4]
            r7 = 7
            if (r6 != r7) goto L_0x01cd
            r4 = 7
            goto L_0x01d5
        L_0x01cd:
            byte r5 = r5[r4]
            r6 = 8
            if (r5 != r6) goto L_0x01d5
            r4 = 8
        L_0x01d5:
            byte[] r3 = r3.f38g
            if (r3 == 0) goto L_0x01e2
            a.a.a.a.d.c r2 = r2.f30a
            if (r2 == 0) goto L_0x01e2
            a.a.a.a.d.d r2 = (p000a.p001a.p002a.p003a.p004d.C0011d) r2
            r2.mo39a(r4, r3, r1)
        L_0x01e2:
            r1 = 0
            p000a.p001a.p002a.p003a.p004d.C0008b.f29d = r1
            goto L_0x01f9
        L_0x01e6:
            r1 = 0
            p000a.p001a.p002a.p003a.p004d.C0008b.f29d = r1
            java.lang.String r3 = "HandleBleData"
            java.lang.String r4 = "---------verify CRC failed------------"
            android.util.Log.i(r3, r4)
            a.a.a.a.d.c r2 = r2.f30a
            a.a.a.a.d.d r2 = (p000a.p001a.p002a.p003a.p004d.C0011d) r2
            r3 = 9
            r2.mo39a(r3, r1, r1)
        L_0x01f9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p000a.p001a.p002a.p003a.p004d.C0011d.onCharacteristicChanged(byte[]):void");
    }

    public void onNotifyFailed() {
    }

    public void onNotifySuccess() {
        this.f54a.getClass();
        C0008b.f29d = null;
    }
}
