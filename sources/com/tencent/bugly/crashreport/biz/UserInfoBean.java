package com.tencent.bugly.crashreport.biz;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.bugly.proguard.C2273ap;
import java.util.Map;

/* compiled from: BUGLY */
public class UserInfoBean implements Parcelable {
    public static final Parcelable.Creator<UserInfoBean> CREATOR = new Parcelable.Creator<UserInfoBean>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new UserInfoBean[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new UserInfoBean(parcel);
        }
    };

    /* renamed from: a */
    public long f1291a;

    /* renamed from: b */
    public int f1292b;

    /* renamed from: c */
    public String f1293c;

    /* renamed from: d */
    public String f1294d;

    /* renamed from: e */
    public long f1295e;

    /* renamed from: f */
    public long f1296f;

    /* renamed from: g */
    public long f1297g;

    /* renamed from: h */
    public long f1298h;

    /* renamed from: i */
    public long f1299i;

    /* renamed from: j */
    public String f1300j;

    /* renamed from: k */
    public long f1301k;

    /* renamed from: l */
    public boolean f1302l;

    /* renamed from: m */
    public String f1303m;

    /* renamed from: n */
    public String f1304n;

    /* renamed from: o */
    public int f1305o;

    /* renamed from: p */
    public int f1306p;

    /* renamed from: q */
    public int f1307q;

    /* renamed from: r */
    public Map<String, String> f1308r;

    /* renamed from: s */
    public Map<String, String> f1309s;

    public int describeContents() {
        return 0;
    }

    public UserInfoBean() {
        this.f1301k = 0;
        this.f1302l = false;
        this.f1303m = "unknown";
        this.f1306p = -1;
        this.f1307q = -1;
        this.f1308r = null;
        this.f1309s = null;
    }

    public UserInfoBean(Parcel parcel) {
        this.f1301k = 0;
        boolean z = false;
        this.f1302l = false;
        this.f1303m = "unknown";
        this.f1306p = -1;
        this.f1307q = -1;
        this.f1308r = null;
        this.f1309s = null;
        this.f1292b = parcel.readInt();
        this.f1293c = parcel.readString();
        this.f1294d = parcel.readString();
        this.f1295e = parcel.readLong();
        this.f1296f = parcel.readLong();
        this.f1297g = parcel.readLong();
        this.f1298h = parcel.readLong();
        this.f1299i = parcel.readLong();
        this.f1300j = parcel.readString();
        this.f1301k = parcel.readLong();
        this.f1302l = parcel.readByte() == 1 ? true : z;
        this.f1303m = parcel.readString();
        this.f1306p = parcel.readInt();
        this.f1307q = parcel.readInt();
        this.f1308r = C2273ap.m664b(parcel);
        this.f1309s = C2273ap.m664b(parcel);
        this.f1304n = parcel.readString();
        this.f1305o = parcel.readInt();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1292b);
        parcel.writeString(this.f1293c);
        parcel.writeString(this.f1294d);
        parcel.writeLong(this.f1295e);
        parcel.writeLong(this.f1296f);
        parcel.writeLong(this.f1297g);
        parcel.writeLong(this.f1298h);
        parcel.writeLong(this.f1299i);
        parcel.writeString(this.f1300j);
        parcel.writeLong(this.f1301k);
        parcel.writeByte(this.f1302l ? (byte) 1 : 0);
        parcel.writeString(this.f1303m);
        parcel.writeInt(this.f1306p);
        parcel.writeInt(this.f1307q);
        C2273ap.m666b(parcel, this.f1308r);
        C2273ap.m666b(parcel, this.f1309s);
        parcel.writeString(this.f1304n);
        parcel.writeInt(this.f1305o);
    }
}
