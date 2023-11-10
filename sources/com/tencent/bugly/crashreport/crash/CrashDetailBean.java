package com.tencent.bugly.crashreport.crash;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.proguard.C2273ap;
import java.util.Map;
import java.util.UUID;

/* compiled from: BUGLY */
public class CrashDetailBean implements Parcelable, Comparable<CrashDetailBean> {
    public static final Parcelable.Creator<CrashDetailBean> CREATOR = new Parcelable.Creator<CrashDetailBean>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new CrashDetailBean[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new CrashDetailBean(parcel);
        }
    };

    /* renamed from: A */
    public String f1336A;

    /* renamed from: B */
    public String f1337B;

    /* renamed from: C */
    public long f1338C;

    /* renamed from: D */
    public long f1339D;

    /* renamed from: E */
    public long f1340E;

    /* renamed from: F */
    public long f1341F;

    /* renamed from: G */
    public long f1342G;

    /* renamed from: H */
    public long f1343H;

    /* renamed from: I */
    public long f1344I;

    /* renamed from: J */
    public long f1345J;

    /* renamed from: K */
    public long f1346K;

    /* renamed from: L */
    public String f1347L;

    /* renamed from: M */
    public String f1348M;

    /* renamed from: N */
    public String f1349N;

    /* renamed from: O */
    public String f1350O;

    /* renamed from: P */
    public String f1351P;

    /* renamed from: Q */
    public long f1352Q;

    /* renamed from: R */
    public boolean f1353R;

    /* renamed from: S */
    public Map<String, String> f1354S;

    /* renamed from: T */
    public Map<String, String> f1355T;

    /* renamed from: U */
    public int f1356U;

    /* renamed from: V */
    public int f1357V;

    /* renamed from: W */
    public Map<String, String> f1358W;

    /* renamed from: X */
    public Map<String, String> f1359X;

    /* renamed from: Y */
    public byte[] f1360Y;

    /* renamed from: Z */
    public String f1361Z;

    /* renamed from: a */
    public long f1362a;

    /* renamed from: aa */
    public String f1363aa;

    /* renamed from: b */
    public int f1364b;

    /* renamed from: c */
    public String f1365c;

    /* renamed from: d */
    public boolean f1366d;

    /* renamed from: e */
    public String f1367e;

    /* renamed from: f */
    public String f1368f;

    /* renamed from: g */
    public String f1369g;

    /* renamed from: h */
    public Map<String, PlugInBean> f1370h;

    /* renamed from: i */
    public Map<String, PlugInBean> f1371i;

    /* renamed from: j */
    public boolean f1372j;

    /* renamed from: k */
    public boolean f1373k;

    /* renamed from: l */
    public int f1374l;

    /* renamed from: m */
    public String f1375m;

    /* renamed from: n */
    public String f1376n;

    /* renamed from: o */
    public String f1377o;

    /* renamed from: p */
    public String f1378p;

    /* renamed from: q */
    public String f1379q;

    /* renamed from: r */
    public long f1380r;

    /* renamed from: s */
    public String f1381s;

    /* renamed from: t */
    public int f1382t;

    /* renamed from: u */
    public String f1383u;

    /* renamed from: v */
    public String f1384v;

    /* renamed from: w */
    public String f1385w;

    /* renamed from: x */
    public String f1386x;

    /* renamed from: y */
    public byte[] f1387y;

    /* renamed from: z */
    public Map<String, String> f1388z;

    public int describeContents() {
        return 0;
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        int i;
        CrashDetailBean crashDetailBean = (CrashDetailBean) obj;
        if (crashDetailBean == null || this.f1380r - crashDetailBean.f1380r > 0) {
            return 1;
        }
        return i < 0 ? -1 : 0;
    }

    public CrashDetailBean() {
        this.f1362a = -1;
        this.f1364b = 0;
        this.f1365c = UUID.randomUUID().toString();
        this.f1366d = false;
        this.f1367e = "";
        this.f1368f = "";
        this.f1369g = "";
        this.f1370h = null;
        this.f1371i = null;
        this.f1372j = false;
        this.f1373k = false;
        this.f1374l = 0;
        this.f1375m = "";
        this.f1376n = "";
        this.f1377o = "";
        this.f1378p = "";
        this.f1379q = "";
        this.f1380r = -1;
        this.f1381s = null;
        this.f1382t = 0;
        this.f1383u = "";
        this.f1384v = "";
        this.f1385w = null;
        this.f1386x = null;
        this.f1387y = null;
        this.f1388z = null;
        this.f1336A = "";
        this.f1337B = "";
        this.f1338C = -1;
        this.f1339D = -1;
        this.f1340E = -1;
        this.f1341F = -1;
        this.f1342G = -1;
        this.f1343H = -1;
        this.f1344I = -1;
        this.f1345J = -1;
        this.f1346K = -1;
        this.f1347L = "";
        this.f1348M = "";
        this.f1349N = "";
        this.f1350O = "";
        this.f1351P = "";
        this.f1352Q = -1;
        this.f1353R = false;
        this.f1354S = null;
        this.f1355T = null;
        this.f1356U = -1;
        this.f1357V = -1;
        this.f1358W = null;
        this.f1359X = null;
        this.f1360Y = null;
        this.f1361Z = null;
        this.f1363aa = null;
    }

    public CrashDetailBean(Parcel parcel) {
        this.f1362a = -1;
        boolean z = false;
        this.f1364b = 0;
        this.f1365c = UUID.randomUUID().toString();
        this.f1366d = false;
        this.f1367e = "";
        this.f1368f = "";
        this.f1369g = "";
        this.f1370h = null;
        this.f1371i = null;
        this.f1372j = false;
        this.f1373k = false;
        this.f1374l = 0;
        this.f1375m = "";
        this.f1376n = "";
        this.f1377o = "";
        this.f1378p = "";
        this.f1379q = "";
        this.f1380r = -1;
        this.f1381s = null;
        this.f1382t = 0;
        this.f1383u = "";
        this.f1384v = "";
        this.f1385w = null;
        this.f1386x = null;
        this.f1387y = null;
        this.f1388z = null;
        this.f1336A = "";
        this.f1337B = "";
        this.f1338C = -1;
        this.f1339D = -1;
        this.f1340E = -1;
        this.f1341F = -1;
        this.f1342G = -1;
        this.f1343H = -1;
        this.f1344I = -1;
        this.f1345J = -1;
        this.f1346K = -1;
        this.f1347L = "";
        this.f1348M = "";
        this.f1349N = "";
        this.f1350O = "";
        this.f1351P = "";
        this.f1352Q = -1;
        this.f1353R = false;
        this.f1354S = null;
        this.f1355T = null;
        this.f1356U = -1;
        this.f1357V = -1;
        this.f1358W = null;
        this.f1359X = null;
        this.f1360Y = null;
        this.f1361Z = null;
        this.f1363aa = null;
        this.f1364b = parcel.readInt();
        this.f1365c = parcel.readString();
        this.f1366d = parcel.readByte() == 1;
        this.f1367e = parcel.readString();
        this.f1368f = parcel.readString();
        this.f1369g = parcel.readString();
        this.f1372j = parcel.readByte() == 1;
        this.f1373k = parcel.readByte() == 1;
        this.f1374l = parcel.readInt();
        this.f1375m = parcel.readString();
        this.f1376n = parcel.readString();
        this.f1377o = parcel.readString();
        this.f1378p = parcel.readString();
        this.f1379q = parcel.readString();
        this.f1380r = parcel.readLong();
        this.f1381s = parcel.readString();
        this.f1382t = parcel.readInt();
        this.f1383u = parcel.readString();
        this.f1384v = parcel.readString();
        this.f1385w = parcel.readString();
        this.f1388z = C2273ap.m664b(parcel);
        this.f1336A = parcel.readString();
        this.f1337B = parcel.readString();
        this.f1338C = parcel.readLong();
        this.f1339D = parcel.readLong();
        this.f1340E = parcel.readLong();
        this.f1341F = parcel.readLong();
        this.f1342G = parcel.readLong();
        this.f1343H = parcel.readLong();
        this.f1347L = parcel.readString();
        this.f1348M = parcel.readString();
        this.f1349N = parcel.readString();
        this.f1350O = parcel.readString();
        this.f1351P = parcel.readString();
        this.f1352Q = parcel.readLong();
        this.f1353R = parcel.readByte() == 1 ? true : z;
        this.f1354S = C2273ap.m664b(parcel);
        this.f1370h = C2273ap.m651a(parcel);
        this.f1371i = C2273ap.m651a(parcel);
        this.f1356U = parcel.readInt();
        this.f1357V = parcel.readInt();
        this.f1358W = C2273ap.m664b(parcel);
        this.f1359X = C2273ap.m664b(parcel);
        this.f1360Y = parcel.createByteArray();
        this.f1387y = parcel.createByteArray();
        this.f1361Z = parcel.readString();
        this.f1363aa = parcel.readString();
        this.f1386x = parcel.readString();
        this.f1344I = parcel.readLong();
        this.f1345J = parcel.readLong();
        this.f1346K = parcel.readLong();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1364b);
        parcel.writeString(this.f1365c);
        parcel.writeByte(this.f1366d ? (byte) 1 : 0);
        parcel.writeString(this.f1367e);
        parcel.writeString(this.f1368f);
        parcel.writeString(this.f1369g);
        parcel.writeByte(this.f1372j ? (byte) 1 : 0);
        parcel.writeByte(this.f1373k ? (byte) 1 : 0);
        parcel.writeInt(this.f1374l);
        parcel.writeString(this.f1375m);
        parcel.writeString(this.f1376n);
        parcel.writeString(this.f1377o);
        parcel.writeString(this.f1378p);
        parcel.writeString(this.f1379q);
        parcel.writeLong(this.f1380r);
        parcel.writeString(this.f1381s);
        parcel.writeInt(this.f1382t);
        parcel.writeString(this.f1383u);
        parcel.writeString(this.f1384v);
        parcel.writeString(this.f1385w);
        C2273ap.m666b(parcel, this.f1388z);
        parcel.writeString(this.f1336A);
        parcel.writeString(this.f1337B);
        parcel.writeLong(this.f1338C);
        parcel.writeLong(this.f1339D);
        parcel.writeLong(this.f1340E);
        parcel.writeLong(this.f1341F);
        parcel.writeLong(this.f1342G);
        parcel.writeLong(this.f1343H);
        parcel.writeString(this.f1347L);
        parcel.writeString(this.f1348M);
        parcel.writeString(this.f1349N);
        parcel.writeString(this.f1350O);
        parcel.writeString(this.f1351P);
        parcel.writeLong(this.f1352Q);
        parcel.writeByte(this.f1353R ? (byte) 1 : 0);
        C2273ap.m666b(parcel, this.f1354S);
        C2273ap.m653a(parcel, this.f1370h);
        C2273ap.m653a(parcel, this.f1371i);
        parcel.writeInt(this.f1356U);
        parcel.writeInt(this.f1357V);
        C2273ap.m666b(parcel, this.f1358W);
        C2273ap.m666b(parcel, this.f1359X);
        parcel.writeByteArray(this.f1360Y);
        parcel.writeByteArray(this.f1387y);
        parcel.writeString(this.f1361Z);
        parcel.writeString(this.f1363aa);
        parcel.writeString(this.f1386x);
        parcel.writeLong(this.f1344I);
        parcel.writeLong(this.f1345J);
        parcel.writeLong(this.f1346K);
    }
}
