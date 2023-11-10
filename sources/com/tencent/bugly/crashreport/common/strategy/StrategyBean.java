package com.tencent.bugly.crashreport.common.strategy;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.bugly.proguard.C2273ap;
import java.util.Map;

/* compiled from: BUGLY */
public class StrategyBean implements Parcelable {
    public static final Parcelable.Creator<StrategyBean> CREATOR = new Parcelable.Creator<StrategyBean>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new StrategyBean[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new StrategyBean(parcel);
        }
    };

    /* renamed from: a */
    public static String f1313a = "https://android.bugly.qq.com/rqd/async";

    /* renamed from: b */
    public static String f1314b = "https://android.bugly.qq.com/rqd/async";

    /* renamed from: c */
    public static String f1315c;

    /* renamed from: d */
    public long f1316d;

    /* renamed from: e */
    public long f1317e;

    /* renamed from: f */
    public boolean f1318f;

    /* renamed from: g */
    public boolean f1319g;

    /* renamed from: h */
    public boolean f1320h;

    /* renamed from: i */
    public boolean f1321i;

    /* renamed from: j */
    public boolean f1322j;

    /* renamed from: k */
    public boolean f1323k;

    /* renamed from: l */
    public boolean f1324l;

    /* renamed from: m */
    public boolean f1325m;

    /* renamed from: n */
    public boolean f1326n;

    /* renamed from: o */
    public long f1327o;

    /* renamed from: p */
    public long f1328p;

    /* renamed from: q */
    public String f1329q;

    /* renamed from: r */
    public String f1330r;

    /* renamed from: s */
    public String f1331s;

    /* renamed from: t */
    public Map<String, String> f1332t;

    /* renamed from: u */
    public int f1333u;

    /* renamed from: v */
    public long f1334v;

    /* renamed from: w */
    public long f1335w;

    public int describeContents() {
        return 0;
    }

    public StrategyBean() {
        this.f1316d = -1;
        this.f1317e = -1;
        this.f1318f = true;
        this.f1319g = true;
        this.f1320h = true;
        this.f1321i = true;
        this.f1322j = false;
        this.f1323k = true;
        this.f1324l = true;
        this.f1325m = true;
        this.f1326n = true;
        this.f1328p = 30000;
        this.f1329q = f1313a;
        this.f1330r = f1314b;
        this.f1333u = 10;
        this.f1334v = 300000;
        this.f1335w = -1;
        this.f1317e = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("S(@L@L@)");
        f1315c = sb.toString();
        sb.setLength(0);
        sb.append("*^@K#K@!");
        this.f1331s = sb.toString();
    }

    public StrategyBean(Parcel parcel) {
        this.f1316d = -1;
        this.f1317e = -1;
        boolean z = true;
        this.f1318f = true;
        this.f1319g = true;
        this.f1320h = true;
        this.f1321i = true;
        this.f1322j = false;
        this.f1323k = true;
        this.f1324l = true;
        this.f1325m = true;
        this.f1326n = true;
        this.f1328p = 30000;
        this.f1329q = f1313a;
        this.f1330r = f1314b;
        this.f1333u = 10;
        this.f1334v = 300000;
        this.f1335w = -1;
        try {
            f1315c = "S(@L@L@)";
            this.f1317e = parcel.readLong();
            this.f1318f = parcel.readByte() == 1;
            this.f1319g = parcel.readByte() == 1;
            this.f1320h = parcel.readByte() == 1;
            this.f1329q = parcel.readString();
            this.f1330r = parcel.readString();
            this.f1331s = parcel.readString();
            this.f1332t = C2273ap.m664b(parcel);
            this.f1321i = parcel.readByte() == 1;
            this.f1322j = parcel.readByte() == 1;
            this.f1325m = parcel.readByte() == 1;
            this.f1326n = parcel.readByte() == 1;
            this.f1328p = parcel.readLong();
            this.f1323k = parcel.readByte() == 1;
            if (parcel.readByte() != 1) {
                z = false;
            }
            this.f1324l = z;
            this.f1327o = parcel.readLong();
            this.f1333u = parcel.readInt();
            this.f1334v = parcel.readLong();
            this.f1335w = parcel.readLong();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.f1317e);
        parcel.writeByte(this.f1318f ? (byte) 1 : 0);
        parcel.writeByte(this.f1319g ? (byte) 1 : 0);
        parcel.writeByte(this.f1320h ? (byte) 1 : 0);
        parcel.writeString(this.f1329q);
        parcel.writeString(this.f1330r);
        parcel.writeString(this.f1331s);
        C2273ap.m666b(parcel, this.f1332t);
        parcel.writeByte(this.f1321i ? (byte) 1 : 0);
        parcel.writeByte(this.f1322j ? (byte) 1 : 0);
        parcel.writeByte(this.f1325m ? (byte) 1 : 0);
        parcel.writeByte(this.f1326n ? (byte) 1 : 0);
        parcel.writeLong(this.f1328p);
        parcel.writeByte(this.f1323k ? (byte) 1 : 0);
        parcel.writeByte(this.f1324l ? (byte) 1 : 0);
        parcel.writeLong(this.f1327o);
        parcel.writeInt(this.f1333u);
        parcel.writeLong(this.f1334v);
        parcel.writeLong(this.f1335w);
    }
}
