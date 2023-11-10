package com.tencent.bugly.crashreport.common.info;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: BUGLY */
public class PlugInBean implements Parcelable {
    public static final Parcelable.Creator<PlugInBean> CREATOR = new Parcelable.Creator<PlugInBean>() {
        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new PlugInBean[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new PlugInBean(parcel);
        }
    };

    /* renamed from: a */
    public final String f1310a;

    /* renamed from: b */
    public final String f1311b;

    /* renamed from: c */
    public final String f1312c;

    public int describeContents() {
        return 0;
    }

    public PlugInBean(String str, String str2, String str3) {
        this.f1310a = str;
        this.f1311b = str2;
        this.f1312c = str3;
    }

    public String toString() {
        return "plid:" + this.f1310a + " plV:" + this.f1311b + " plUUID:" + this.f1312c;
    }

    public PlugInBean(Parcel parcel) {
        this.f1310a = parcel.readString();
        this.f1311b = parcel.readString();
        this.f1312c = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1310a);
        parcel.writeString(this.f1311b);
        parcel.writeString(this.f1312c);
    }
}
