package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* renamed from: com.tencent.bugly.proguard.ag */
/* compiled from: BUGLY */
public final class C2253ag {

    /* renamed from: a */
    private final SimpleDateFormat f1511a;

    /* renamed from: b */
    private final C2250ad f1512b;

    /* renamed from: com.tencent.bugly.proguard.ag$a */
    /* compiled from: BUGLY */
    public static class C2255a {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public static final C2253ag f1515a = new C2253ag((byte) 0);
    }

    /* synthetic */ C2253ag(byte b) {
        this();
    }

    private C2253ag() {
        this.f1511a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.US);
        this.f1512b = new C2250ad();
    }

    /* renamed from: a */
    public final void mo28000a(List<C2257c> list) {
        if (list == null || list.isEmpty()) {
            C2265al.m610d("sla batch report event is null", new Object[0]);
            return;
        }
        C2265al.m609c("sla batch report event size:%s", Integer.valueOf(list.size()));
        ArrayList arrayList = new ArrayList();
        for (C2257c b : list) {
            C2256b b2 = m560b(b);
            if (b2 != null) {
                arrayList.add(b2);
            }
        }
        m563e(arrayList);
        mo28001b((List<C2256b>) arrayList);
    }

    /* renamed from: b */
    public final void mo28001b(final List<C2256b> list) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            C2263ak.m595a().mo28017a(new Runnable() {
                public final void run() {
                    C2253ag.m561c(list);
                }
            });
        } else {
            m561c(list);
        }
    }

    /* renamed from: c */
    static void m561c(List<C2256b> list) {
        if (list == null || list.isEmpty()) {
            C2265al.m609c("sla batch report data is empty", new Object[0]);
            return;
        }
        C2265al.m609c("sla batch report list size:%s", Integer.valueOf(list.size()));
        if (list.size() > 30) {
            list = list.subList(0, 29);
        }
        ArrayList arrayList = new ArrayList();
        for (C2256b bVar : list) {
            arrayList.add(bVar.f1518c);
        }
        Pair<Integer, String> a = C2250ad.m544a((List<String>) arrayList);
        C2265al.m609c("sla batch report result, rspCode:%s rspMsg:%s", a.first, a.second);
        if (((Integer) a.first).intValue() == 200) {
            m562d(list);
        }
    }

    /* renamed from: e */
    private static void m563e(List<C2256b> list) {
        for (C2256b next : list) {
            C2265al.m609c("sla save id:%s time:%s msg:%s", next.f1516a, Long.valueOf(next.f1517b), next.f1518c);
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("_id", next.f1516a);
                contentValues.put("_tm", Long.valueOf(next.f1517b));
                contentValues.put("_dt", next.f1518c);
                C2365w.m1033a().mo28164a("t_sla", contentValues, (C2364v) null);
            } catch (Throwable th) {
                C2265al.m608b(th);
            }
        }
    }

    /* renamed from: d */
    public static void m562d(List<C2256b> list) {
        if (list == null || list.isEmpty()) {
            C2265al.m609c("sla batch delete list is null", new Object[0]);
            return;
        }
        C2265al.m609c("sla batch delete list size:%s", Integer.valueOf(list.size()));
        try {
            String str = "_id in (" + m558a(",", list) + ")";
            C2265al.m609c("sla batch delete where:%s", str);
            C2365w.m1033a().mo28163a("t_sla", str);
        } catch (Throwable th) {
            C2265al.m608b(th);
        }
    }

    /* renamed from: a */
    private static String m558a(String str, Iterable<C2256b> iterable) {
        Iterator<C2256b> it = iterable.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("'");
        sb.append(it.next().f1516a);
        sb.append("'");
        while (it.hasNext()) {
            sb.append(str);
            sb.append("'");
            sb.append(it.next().f1516a);
            sb.append("'");
        }
        return sb.toString();
    }

    /* renamed from: a */
    public static List<C2256b> m559a() {
        Cursor a = C2365w.m1033a().mo28166a("t_sla", new String[]{"_id", "_tm", "_dt"}, (String) null, "_tm", "30");
        if (a == null) {
            return null;
        }
        if (a.getCount() <= 0) {
            a.close();
            return null;
        }
        ArrayList arrayList = new ArrayList();
        while (a.moveToNext()) {
            try {
                C2256b bVar = new C2256b();
                bVar.f1516a = a.getString(a.getColumnIndex("_id"));
                bVar.f1517b = a.getLong(a.getColumnIndex("_tm"));
                bVar.f1518c = a.getString(a.getColumnIndex("_dt"));
                C2265al.m609c(bVar.toString(), new Object[0]);
                arrayList.add(bVar);
            } catch (Throwable th) {
                a.close();
                throw th;
            }
        }
        a.close();
        return arrayList;
    }

    /* renamed from: com.tencent.bugly.proguard.ag$c */
    /* compiled from: BUGLY */
    public static class C2257c {

        /* renamed from: a */
        String f1519a;

        /* renamed from: b */
        String f1520b;

        /* renamed from: c */
        long f1521c;

        /* renamed from: d */
        boolean f1522d;

        /* renamed from: e */
        long f1523e;

        /* renamed from: f */
        String f1524f;

        /* renamed from: g */
        String f1525g;

        public C2257c(String str, String str2, long j, boolean z, long j2, String str3, String str4) {
            this.f1519a = str;
            this.f1520b = str2;
            this.f1521c = j;
            this.f1522d = z;
            this.f1523e = j2;
            this.f1524f = str3;
            this.f1525g = str4;
        }

        public C2257c() {
        }
    }

    /* renamed from: com.tencent.bugly.proguard.ag$b */
    /* compiled from: BUGLY */
    public static class C2256b {

        /* renamed from: a */
        String f1516a;

        /* renamed from: b */
        public long f1517b;

        /* renamed from: c */
        public String f1518c;

        public final String toString() {
            return "SLAData{uuid='" + this.f1516a + '\'' + ", time=" + this.f1517b + ", data='" + this.f1518c + '\'' + '}';
        }
    }

    /* renamed from: a */
    public final void mo27999a(C2257c cVar) {
        if (TextUtils.isEmpty(cVar.f1520b)) {
            C2265al.m610d("sla report event is null", new Object[0]);
            return;
        }
        C2265al.m609c("sla report single event", new Object[0]);
        mo28000a((List<C2257c>) Collections.singletonList(cVar));
    }

    /* renamed from: b */
    private C2256b m560b(C2257c cVar) {
        if (cVar == null || TextUtils.isEmpty(cVar.f1520b)) {
            C2265al.m610d("sla convert event is null", new Object[0]);
            return null;
        }
        C2231aa b = C2231aa.m459b();
        if (b == null) {
            C2265al.m610d("sla convert failed because ComInfoManager is null", new Object[0]);
            return null;
        }
        StringBuilder sb = new StringBuilder("&app_version=");
        sb.append(b.f1483o);
        sb.append("&app_name=");
        sb.append(b.f1485q);
        sb.append("&app_bundle_id=");
        sb.append(b.f1471c);
        sb.append("&client_type=android&user_id=");
        sb.append(b.mo27970f());
        sb.append("&sdk_version=");
        sb.append(b.f1476h);
        sb.append("&event_code=");
        sb.append(cVar.f1520b);
        sb.append("&event_result=");
        sb.append(cVar.f1522d ? 1 : 0);
        sb.append("&event_time=");
        sb.append(this.f1511a.format(new Date(cVar.f1521c)));
        sb.append("&event_cost=");
        sb.append(cVar.f1523e);
        sb.append("&device_id=");
        sb.append(b.mo27972g());
        sb.append("&debug=");
        sb.append(b.f1423D ? 1 : 0);
        sb.append("&param_0=");
        sb.append(cVar.f1524f);
        sb.append("&param_1=");
        sb.append(cVar.f1519a);
        sb.append("&param_2=");
        sb.append(b.f1432M ? "rqd" : "ext");
        sb.append("&param_4=");
        sb.append(b.mo27968e());
        String sb2 = sb.toString();
        if (!TextUtils.isEmpty(cVar.f1525g)) {
            sb2 = sb2 + "&param_3=" + cVar.f1525g;
        }
        C2265al.m609c("sla convert eventId:%s eventType:%s, eventTime:%s success:%s cost:%s from:%s uploadMsg:", cVar.f1519a, cVar.f1520b, Long.valueOf(cVar.f1521c), Boolean.valueOf(cVar.f1522d), Long.valueOf(cVar.f1523e), cVar.f1524f, cVar.f1525g);
        C2256b bVar = new C2256b();
        bVar.f1516a = cVar.f1519a + "-" + cVar.f1520b;
        bVar.f1517b = cVar.f1521c;
        bVar.f1518c = sb2;
        return bVar;
    }
}
