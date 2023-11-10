package com.tencent.bugly.proguard;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Process;
import android.text.TextUtils;
import com.itextpdf.text.pdf.PdfBoolean;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/* renamed from: com.tencent.bugly.proguard.aa */
/* compiled from: BUGLY */
public final class C2231aa {

    /* renamed from: W */
    private static final Map<String, String> f1418W = new HashMap();

    /* renamed from: aq */
    private static C2231aa f1419aq = null;

    /* renamed from: A */
    public long f1420A = 0;

    /* renamed from: B */
    public long f1421B = 0;

    /* renamed from: C */
    public long f1422C = 0;

    /* renamed from: D */
    public boolean f1423D = false;

    /* renamed from: E */
    public String f1424E = null;

    /* renamed from: F */
    public String f1425F = null;

    /* renamed from: G */
    public String f1426G = null;

    /* renamed from: H */
    public String f1427H = "";

    /* renamed from: I */
    public boolean f1428I = false;

    /* renamed from: J */
    public boolean f1429J = false;

    /* renamed from: K */
    public HashMap<String, String> f1430K = new HashMap<>();

    /* renamed from: L */
    public List<String> f1431L = new ArrayList();

    /* renamed from: M */
    public boolean f1432M = false;

    /* renamed from: N */
    public C2350q f1433N = null;

    /* renamed from: O */
    public final SharedPreferences f1434O;

    /* renamed from: P */
    public final SharedPreferences f1435P;

    /* renamed from: Q */
    public boolean f1436Q = true;

    /* renamed from: R */
    public boolean f1437R = true;

    /* renamed from: S */
    public boolean f1438S = false;

    /* renamed from: T */
    public final Object f1439T = new Object();

    /* renamed from: U */
    public final Object f1440U = new Object();

    /* renamed from: V */
    public final Object f1441V = new Object();

    /* renamed from: X */
    private final Context f1442X;

    /* renamed from: Y */
    private String f1443Y;

    /* renamed from: Z */
    private String f1444Z;

    /* renamed from: a */
    public final long f1445a = System.currentTimeMillis();

    /* renamed from: aa */
    private String f1446aa;

    /* renamed from: ab */
    private String f1447ab = "unknown";

    /* renamed from: ac */
    private String f1448ac = "";

    /* renamed from: ad */
    private String f1449ad = null;

    /* renamed from: ae */
    private long f1450ae = -1;

    /* renamed from: af */
    private long f1451af = -1;

    /* renamed from: ag */
    private long f1452ag = -1;

    /* renamed from: ah */
    private String f1453ah = null;

    /* renamed from: ai */
    private String f1454ai = null;

    /* renamed from: aj */
    private String f1455aj = null;

    /* renamed from: ak */
    private Map<String, PlugInBean> f1456ak = null;

    /* renamed from: al */
    private String f1457al = null;

    /* renamed from: am */
    private Boolean f1458am = null;

    /* renamed from: an */
    private String f1459an = null;

    /* renamed from: ao */
    private Map<String, PlugInBean> f1460ao = null;

    /* renamed from: ap */
    private Map<String, PlugInBean> f1461ap = null;

    /* renamed from: ar */
    private final Map<String, String> f1462ar = new HashMap();

    /* renamed from: as */
    private final Map<String, String> f1463as = new HashMap();

    /* renamed from: at */
    private final Map<String, String> f1464at = new HashMap();

    /* renamed from: au */
    private final Object f1465au = new Object();

    /* renamed from: av */
    private final Object f1466av = new Object();

    /* renamed from: aw */
    private final Object f1467aw = new Object();

    /* renamed from: ax */
    private final Object f1468ax = new Object();

    /* renamed from: ay */
    private final List<Integer> f1469ay = new ArrayList();

    /* renamed from: b */
    public final byte f1470b;

    /* renamed from: c */
    public String f1471c;

    /* renamed from: d */
    public final String f1472d;

    /* renamed from: e */
    public String f1473e;

    /* renamed from: f */
    public boolean f1474f = true;

    /* renamed from: g */
    public final String f1475g = "com.tencent.bugly";

    /* renamed from: h */
    public String f1476h = "4.1.9";

    /* renamed from: i */
    public final String f1477i = "";
    @Deprecated

    /* renamed from: j */
    public final String f1478j = "";

    /* renamed from: k */
    public final String f1479k;

    /* renamed from: l */
    public String f1480l = "unknown";

    /* renamed from: m */
    public long f1481m = 0;

    /* renamed from: n */
    public boolean f1482n = false;

    /* renamed from: o */
    public String f1483o = null;

    /* renamed from: p */
    public int f1484p;

    /* renamed from: q */
    public String f1485q = null;

    /* renamed from: r */
    public String f1486r = null;

    /* renamed from: s */
    public String f1487s = null;

    /* renamed from: t */
    public String f1488t = null;

    /* renamed from: u */
    public String f1489u = null;

    /* renamed from: v */
    public List<String> f1490v = null;

    /* renamed from: w */
    public int f1491w = -1;

    /* renamed from: x */
    public int f1492x = -1;

    /* renamed from: y */
    public String f1493y = "unknown";

    /* renamed from: z */
    public long f1494z = 0;

    @Deprecated
    /* renamed from: n */
    public static String m460n() {
        return "";
    }

    private C2231aa(Context context) {
        this.f1442X = C2273ap.m635a(context);
        this.f1470b = 1;
        PackageInfo b = C2369z.m1063b(context);
        if (b != null) {
            try {
                String str = b.versionName;
                this.f1483o = str;
                this.f1424E = str;
                this.f1425F = Integer.toString(b.versionCode);
            } catch (Throwable th) {
                if (!C2265al.m605a(th)) {
                    th.printStackTrace();
                }
            }
        }
        this.f1471c = C2369z.m1059a(context);
        this.f1472d = C2369z.m1058a(Process.myPid());
        this.f1485q = C2369z.m1064c(context);
        this.f1479k = "Android " + C2232ab.m499b() + ",level " + C2232ab.m500c();
        Map<String, String> d = C2369z.m1065d(context);
        if (d != null) {
            try {
                this.f1490v = C2369z.m1060a(d);
                String str2 = d.get("BUGLY_APPID");
                if (str2 != null) {
                    this.f1486r = str2;
                    mo27963b("APP_ID", str2);
                }
                String str3 = d.get("BUGLY_APP_VERSION");
                if (str3 != null) {
                    this.f1483o = str3;
                }
                String str4 = d.get("BUGLY_APP_CHANNEL");
                if (str4 != null) {
                    this.f1487s = str4;
                }
                String str5 = d.get("BUGLY_ENABLE_DEBUG");
                if (str5 != null) {
                    this.f1423D = str5.equalsIgnoreCase(PdfBoolean.TRUE);
                }
                String str6 = d.get("com.tencent.rdm.uuid");
                if (str6 != null) {
                    this.f1426G = str6;
                }
                String str7 = d.get("BUGLY_APP_BUILD_NO");
                if (!TextUtils.isEmpty(str7)) {
                    this.f1484p = Integer.parseInt(str7);
                }
                String str8 = d.get("BUGLY_AREA");
                if (str8 != null) {
                    this.f1427H = str8;
                }
            } catch (Throwable th2) {
                if (!C2265al.m605a(th2)) {
                    th2.printStackTrace();
                }
            }
        }
        try {
            if (!context.getDatabasePath("bugly_db_").exists()) {
                this.f1429J = true;
                C2265al.m609c("App is first time to be installed on the device.", new Object[0]);
            }
        } catch (Throwable th3) {
            if (C2349p.f1912c) {
                th3.printStackTrace();
            }
        }
        this.f1434O = C2273ap.m636a("BUGLY_COMMON_VALUES", context);
        this.f1435P = C2273ap.m636a("BUGLY_RESERVED_VALUES", context);
        this.f1455aj = C2232ab.m497a(context);
        m454E();
        C2265al.m609c("com info create end", new Object[0]);
    }

    /* renamed from: E */
    private void m454E() {
        try {
            for (Map.Entry next : this.f1435P.getAll().entrySet()) {
                C2265al.m609c("put reserved request data from sp, key:%s value:%s", next.getKey(), next.getValue());
                m458a((String) next.getKey(), next.getValue().toString(), false);
            }
            for (Map.Entry next2 : f1418W.entrySet()) {
                C2265al.m609c("put reserved request data from cache, key:%s value:%s", next2.getKey(), next2.getValue());
                m458a((String) next2.getKey(), (String) next2.getValue(), true);
            }
            f1418W.clear();
        } catch (Throwable th) {
            C2265al.m608b(th);
        }
    }

    /* renamed from: a */
    public final boolean mo27961a() {
        boolean z = this.f1469ay.size() > 0;
        C2265al.m609c("isAppForeground:%s", Boolean.valueOf(z));
        return z;
    }

    /* renamed from: a */
    public final void mo27958a(int i, boolean z) {
        boolean z2 = false;
        C2265al.m609c("setActivityForeState, hash:%s isFore:%s", Integer.valueOf(i), Boolean.valueOf(z));
        if (z) {
            this.f1469ay.add(Integer.valueOf(i));
        } else {
            this.f1469ay.remove(Integer.valueOf(i));
            this.f1469ay.remove(0);
        }
        C2350q qVar = this.f1433N;
        if (qVar != null) {
            if (this.f1469ay.size() > 0) {
                z2 = true;
            }
            qVar.setNativeIsAppForeground(z2);
        }
    }

    /* renamed from: a */
    public static synchronized C2231aa m457a(Context context) {
        C2231aa aaVar;
        synchronized (C2231aa.class) {
            if (f1419aq == null) {
                f1419aq = new C2231aa(context);
            }
            aaVar = f1419aq;
        }
        return aaVar;
    }

    /* renamed from: b */
    public static synchronized C2231aa m459b() {
        C2231aa aaVar;
        synchronized (C2231aa.class) {
            aaVar = f1419aq;
        }
        return aaVar;
    }

    /* renamed from: c */
    public final void mo27964c() {
        synchronized (this.f1465au) {
            this.f1443Y = UUID.randomUUID().toString();
        }
    }

    /* renamed from: d */
    public final String mo27966d() {
        String str;
        synchronized (this.f1465au) {
            if (this.f1443Y == null) {
                mo27964c();
            }
            str = this.f1443Y;
        }
        return str;
    }

    /* renamed from: e */
    public final String mo27968e() {
        if (!C2273ap.m657a(this.f1473e)) {
            return this.f1473e;
        }
        return this.f1486r;
    }

    /* renamed from: f */
    public final String mo27970f() {
        String str;
        synchronized (this.f1441V) {
            str = this.f1480l;
        }
        return str;
    }

    /* renamed from: g */
    public final String mo27972g() {
        String str = this.f1446aa;
        if (str != null) {
            return str;
        }
        String d = C2273ap.m677d("deviceId", (String) null);
        this.f1446aa = d;
        if (d != null) {
            return d;
        }
        String F = m455F();
        this.f1446aa = F;
        if (TextUtils.isEmpty(F)) {
            this.f1446aa = m456G();
        }
        String str2 = this.f1446aa;
        if (str2 == null) {
            return "";
        }
        C2273ap.m672c("deviceId", str2);
        return this.f1446aa;
    }

    /* renamed from: F */
    private String m455F() {
        if (TextUtils.isEmpty(this.f1449ad)) {
            this.f1449ad = C2273ap.m677d("androidid", (String) null);
        }
        return this.f1449ad;
    }

    /* renamed from: G */
    private static String m456G() {
        String uuid = UUID.randomUUID().toString();
        return !C2273ap.m657a(uuid) ? uuid.replaceAll("-", "") : uuid;
    }

    /* renamed from: a */
    public final void mo27959a(String str) {
        this.f1446aa = str;
        if (!TextUtils.isEmpty(str)) {
            C2273ap.m672c("deviceId", str);
        }
        synchronized (this.f1468ax) {
            this.f1463as.put("E8", str);
        }
    }

    /* renamed from: h */
    public final synchronized String mo27974h() {
        String str = this.f1444Z;
        if (str != null) {
            return str;
        }
        String d = C2273ap.m677d("deviceModel", (String) null);
        this.f1444Z = d;
        if (d != null) {
            C2265al.m609c("collect device model from sp:%s", d);
            return this.f1444Z;
        } else if (!this.f1482n) {
            C2265al.m609c("not allow collect device model", new Object[0]);
            return "fail";
        } else {
            String a = C2232ab.m496a();
            this.f1444Z = a;
            C2265al.m609c("collect device model:%s", a);
            C2273ap.m672c("deviceModel", this.f1444Z);
            return this.f1444Z;
        }
    }

    /* renamed from: b */
    public final void mo27962b(String str) {
        C2265al.m604a("change deviceModel，old:%s new:%s", this.f1444Z, str);
        this.f1444Z = str;
        if (!TextUtils.isEmpty(str)) {
            C2273ap.m672c("deviceModel", str);
        }
    }

    /* renamed from: c */
    public final synchronized void mo27965c(String str) {
        this.f1447ab = String.valueOf(str);
    }

    /* renamed from: i */
    public final synchronized String mo27975i() {
        return this.f1448ac;
    }

    /* renamed from: d */
    public final synchronized void mo27967d(String str) {
        this.f1448ac = String.valueOf(str);
    }

    /* renamed from: j */
    public final long mo27976j() {
        if (this.f1450ae <= 0) {
            this.f1450ae = C2232ab.m504e();
        }
        return this.f1450ae;
    }

    /* renamed from: k */
    public final long mo27977k() {
        if (this.f1451af <= 0) {
            this.f1451af = C2232ab.m510i();
        }
        return this.f1451af;
    }

    /* renamed from: l */
    public final long mo27978l() {
        if (this.f1452ag <= 0) {
            this.f1452ag = C2232ab.m512k();
        }
        return this.f1452ag;
    }

    /* renamed from: m */
    public final String mo27979m() {
        if (!TextUtils.isEmpty(this.f1454ai)) {
            C2265al.m609c("get cpu type from so:%s", this.f1454ai);
            return this.f1454ai;
        } else if (TextUtils.isEmpty(this.f1455aj)) {
            return "unknown";
        } else {
            C2265al.m609c("get cpu type from lib dir:%s", this.f1455aj);
            return this.f1455aj;
        }
    }

    /* renamed from: e */
    public final void mo27969e(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.f1454ai = str.trim();
        }
    }

    /* renamed from: o */
    public final String mo27980o() {
        try {
            Map<String, ?> all = this.f1442X.getSharedPreferences("BuglySdkInfos", 0).getAll();
            if (!all.isEmpty()) {
                synchronized (this.f1439T) {
                    for (Map.Entry next : all.entrySet()) {
                        try {
                            this.f1430K.put(next.getKey(), next.getValue().toString());
                        } catch (Throwable th) {
                            C2265al.m605a(th);
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            C2265al.m605a(th2);
        }
        if (!this.f1430K.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry next2 : this.f1430K.entrySet()) {
                sb.append("[");
                sb.append((String) next2.getKey());
                sb.append(",");
                sb.append((String) next2.getValue());
                sb.append("] ");
            }
            C2265al.m609c("SDK_INFO = %s", sb.toString());
            mo27963b("SDK_INFO", sb.toString());
            return sb.toString();
        }
        C2265al.m609c("SDK_INFO is empty", new Object[0]);
        return null;
    }

    /* renamed from: p */
    public final synchronized Map<String, PlugInBean> mo27981p() {
        Map<String, PlugInBean> map = this.f1456ak;
        if (map != null) {
            if (map.size() > 0) {
                HashMap hashMap = new HashMap(this.f1456ak.size());
                hashMap.putAll(this.f1456ak);
                return hashMap;
            }
        }
        return null;
    }

    /* renamed from: q */
    public final String mo27982q() {
        if (this.f1457al == null) {
            this.f1457al = C2232ab.m514m();
        }
        return this.f1457al;
    }

    /* renamed from: r */
    public final Boolean mo27983r() {
        if (this.f1458am == null) {
            this.f1458am = Boolean.valueOf(C2232ab.m515n());
        }
        return this.f1458am;
    }

    /* renamed from: s */
    public final String mo27984s() {
        if (this.f1459an == null) {
            String str = C2232ab.m503d(this.f1442X);
            this.f1459an = str;
            C2265al.m604a("ROM ID: %s", str);
        }
        return this.f1459an;
    }

    /* renamed from: t */
    public final Map<String, String> mo27985t() {
        synchronized (this.f1466av) {
            if (this.f1462ar.size() <= 0) {
                return null;
            }
            HashMap hashMap = new HashMap(this.f1462ar);
            return hashMap;
        }
    }

    /* renamed from: f */
    public final String mo27971f(String str) {
        String remove;
        if (C2273ap.m657a(str)) {
            C2265al.m610d("key should not be empty %s", String.valueOf(str));
            return null;
        }
        synchronized (this.f1466av) {
            remove = this.f1462ar.remove(str);
        }
        return remove;
    }

    /* renamed from: u */
    public final void mo27986u() {
        synchronized (this.f1466av) {
            this.f1462ar.clear();
        }
    }

    /* renamed from: g */
    public final String mo27973g(String str) {
        String str2;
        if (C2273ap.m657a(str)) {
            C2265al.m610d("key should not be empty %s", String.valueOf(str));
            return null;
        }
        synchronized (this.f1466av) {
            str2 = this.f1462ar.get(str);
        }
        return str2;
    }

    /* renamed from: a */
    public final void mo27960a(String str, String str2) {
        if (C2273ap.m657a(str) || C2273ap.m657a(str2)) {
            C2265al.m610d("key&value should not be empty %s %s", String.valueOf(str), String.valueOf(str2));
            return;
        }
        synchronized (this.f1466av) {
            this.f1462ar.put(str, str2);
        }
    }

    /* renamed from: v */
    public final int mo27987v() {
        int size;
        synchronized (this.f1466av) {
            size = this.f1462ar.size();
        }
        return size;
    }

    /* renamed from: w */
    public final Set<String> mo27988w() {
        Set<String> keySet;
        synchronized (this.f1466av) {
            keySet = this.f1462ar.keySet();
        }
        return keySet;
    }

    /* renamed from: a */
    private void m458a(String str, String str2, boolean z) {
        if (C2273ap.m657a(str)) {
            C2265al.m610d("key should not be empty %s", str);
            return;
        }
        C2265al.m609c("putExtraRequestData key:%s value:%s save:%s", str, str2, Boolean.valueOf(z));
        synchronized (this.f1468ax) {
            if (TextUtils.isEmpty(str2)) {
                this.f1463as.remove(str);
                this.f1435P.edit().remove(str).apply();
            } else {
                this.f1463as.put(str, str2);
                if (z) {
                    this.f1435P.edit().putString(str, str2).apply();
                }
            }
        }
    }

    /* renamed from: x */
    public final Map<String, String> mo27989x() {
        synchronized (this.f1468ax) {
            if (this.f1463as.size() <= 0) {
                return null;
            }
            HashMap hashMap = new HashMap(this.f1463as);
            return hashMap;
        }
    }

    /* renamed from: b */
    public final void mo27963b(String str, String str2) {
        if (C2273ap.m657a(str) || C2273ap.m657a(str2)) {
            C2265al.m610d("server key&value should not be empty %s %s", String.valueOf(str), String.valueOf(str2));
            return;
        }
        synchronized (this.f1467aw) {
            this.f1464at.put(str, str2);
        }
    }

    /* renamed from: y */
    public final Map<String, String> mo27990y() {
        synchronized (this.f1467aw) {
            if (this.f1464at.size() <= 0) {
                return null;
            }
            HashMap hashMap = new HashMap(this.f1464at);
            return hashMap;
        }
    }

    /* renamed from: z */
    public final int mo27991z() {
        int i;
        synchronized (this.f1440U) {
            i = this.f1491w;
        }
        return i;
    }

    /* renamed from: A */
    public final synchronized Map<String, PlugInBean> mo27957A() {
        Map<String, PlugInBean> map;
        map = this.f1460ao;
        Map<String, PlugInBean> map2 = this.f1461ap;
        if (map2 != null) {
            map.putAll(map2);
        }
        return map;
    }

    /* renamed from: B */
    public static int m451B() {
        return C2232ab.m500c();
    }

    @Deprecated
    /* renamed from: C */
    public static boolean m452C() {
        C2265al.m604a("Detect if the emulator is unavailable", new Object[0]);
        return false;
    }

    @Deprecated
    /* renamed from: D */
    public static boolean m453D() {
        C2265al.m604a("Detect if the device hook is unavailable", new Object[0]);
        return false;
    }
}
