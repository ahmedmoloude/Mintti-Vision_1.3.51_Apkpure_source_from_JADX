package com.tencent.bugly.proguard;

import android.content.Context;
import android.text.TextUtils;
import com.linktop.constant.TestPaper;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.ae */
/* compiled from: BUGLY */
public final class C2251ae {
    /* renamed from: a */
    public static C2333bu m549a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        C2333bu buVar = new C2333bu();
        buVar.f1857a = userInfoBean.f1295e;
        buVar.f1861e = userInfoBean.f1300j;
        buVar.f1860d = userInfoBean.f1293c;
        buVar.f1859c = userInfoBean.f1294d;
        buVar.f1864h = userInfoBean.f1305o == 1;
        int i = userInfoBean.f1292b;
        if (i == 1) {
            buVar.f1858b = 1;
        } else if (i == 2) {
            buVar.f1858b = 4;
        } else if (i == 3) {
            buVar.f1858b = 2;
        } else if (i == 4) {
            buVar.f1858b = 3;
        } else if (i == 8) {
            buVar.f1858b = 8;
        } else if (userInfoBean.f1292b < 10 || userInfoBean.f1292b >= 20) {
            C2265al.m611e("unknown uinfo type %d ", Integer.valueOf(userInfoBean.f1292b));
            return null;
        } else {
            buVar.f1858b = (byte) userInfoBean.f1292b;
        }
        buVar.f1862f = new HashMap();
        if (userInfoBean.f1306p >= 0) {
            Map<String, String> map = buVar.f1862f;
            StringBuilder sb = new StringBuilder();
            sb.append(userInfoBean.f1306p);
            map.put(TestPaper.Code.C01, sb.toString());
        }
        if (userInfoBean.f1307q >= 0) {
            Map<String, String> map2 = buVar.f1862f;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(userInfoBean.f1307q);
            map2.put(TestPaper.Code.C02, sb2.toString());
        }
        if (userInfoBean.f1308r != null && userInfoBean.f1308r.size() > 0) {
            for (Map.Entry next : userInfoBean.f1308r.entrySet()) {
                Map<String, String> map3 = buVar.f1862f;
                map3.put("C03_" + ((String) next.getKey()), next.getValue());
            }
        }
        if (userInfoBean.f1309s != null && userInfoBean.f1309s.size() > 0) {
            for (Map.Entry next2 : userInfoBean.f1309s.entrySet()) {
                Map<String, String> map4 = buVar.f1862f;
                map4.put("C04_" + ((String) next2.getKey()), next2.getValue());
            }
        }
        Map<String, String> map5 = buVar.f1862f;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(!userInfoBean.f1302l);
        map5.put("A36", sb3.toString());
        Map<String, String> map6 = buVar.f1862f;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(userInfoBean.f1297g);
        map6.put("F02", sb4.toString());
        Map<String, String> map7 = buVar.f1862f;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(userInfoBean.f1298h);
        map7.put("F03", sb5.toString());
        Map<String, String> map8 = buVar.f1862f;
        map8.put("F04", userInfoBean.f1300j);
        Map<String, String> map9 = buVar.f1862f;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(userInfoBean.f1299i);
        map9.put("F05", sb6.toString());
        Map<String, String> map10 = buVar.f1862f;
        map10.put("F06", userInfoBean.f1303m);
        Map<String, String> map11 = buVar.f1862f;
        StringBuilder sb7 = new StringBuilder();
        sb7.append(userInfoBean.f1301k);
        map11.put("F10", sb7.toString());
        C2265al.m609c("summary type %d vm:%d", Byte.valueOf(buVar.f1858b), Integer.valueOf(buVar.f1862f.size()));
        return buVar;
    }

    /* renamed from: a */
    public static <T extends C2346m> T m550a(byte[] bArr, Class<T> cls) {
        if (bArr != null && bArr.length > 0) {
            try {
                T t = (C2346m) cls.newInstance();
                C2343k kVar = new C2343k(bArr);
                kVar.mo28116a("utf-8");
                t.mo28088a(kVar);
                return t;
            } catch (Throwable th) {
                if (!C2265al.m608b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    /* renamed from: a */
    public static C2329bq m547a(Context context, int i, byte[] bArr) {
        C2231aa b = C2231aa.m459b();
        StrategyBean c = C2248ac.m533a().mo27996c();
        if (b == null || c == null) {
            C2265al.m611e("Can not create request pkg for parameters is invalid.", new Object[0]);
            return null;
        }
        try {
            C2329bq bqVar = new C2329bq();
            synchronized (b) {
                bqVar.f1805a = b.f1470b;
                bqVar.f1806b = b.mo27968e();
                bqVar.f1807c = b.f1471c;
                bqVar.f1808d = b.f1483o;
                bqVar.f1809e = b.f1487s;
                bqVar.f1810f = b.f1476h;
                bqVar.f1811g = i;
                if (bArr == null) {
                    bArr = "".getBytes();
                }
                bqVar.f1812h = bArr;
                bqVar.f1813i = b.mo27974h();
                bqVar.f1814j = b.f1479k;
                bqVar.f1815k = new HashMap();
                bqVar.f1816l = b.mo27966d();
                bqVar.f1817m = c.f1327o;
                bqVar.f1819o = b.mo27972g();
                bqVar.f1820p = C2232ab.m501c(context);
                bqVar.f1821q = System.currentTimeMillis();
                bqVar.f1823s = b.mo27975i();
                bqVar.f1826v = b.mo27972g();
                bqVar.f1827w = bqVar.f1820p;
                b.getClass();
                bqVar.f1818n = "com.tencent.bugly";
                Map<String, String> map = bqVar.f1815k;
                map.put("A26", b.mo27984s());
                Map<String, String> map2 = bqVar.f1815k;
                StringBuilder sb = new StringBuilder();
                sb.append(C2231aa.m452C());
                map2.put("A62", sb.toString());
                Map<String, String> map3 = bqVar.f1815k;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(C2231aa.m453D());
                map3.put("A63", sb2.toString());
                Map<String, String> map4 = bqVar.f1815k;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(b.f1429J);
                map4.put("F11", sb3.toString());
                Map<String, String> map5 = bqVar.f1815k;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(b.f1428I);
                map5.put("F12", sb4.toString());
                Map<String, String> map6 = bqVar.f1815k;
                map6.put("D3", b.f1485q);
                if (C2349p.f1911b != null) {
                    for (C2348o next : C2349p.f1911b) {
                        if (!(next.versionKey == null || next.version == null)) {
                            bqVar.f1815k.put(next.versionKey, next.version);
                        }
                    }
                }
                bqVar.f1815k.put("G15", C2273ap.m677d("G15", ""));
                bqVar.f1815k.put("G10", C2273ap.m677d("G10", ""));
                bqVar.f1815k.put("D4", C2273ap.m677d("D4", "0"));
            }
            Map<String, String> x = b.mo27989x();
            if (x != null) {
                for (Map.Entry next2 : x.entrySet()) {
                    if (!TextUtils.isEmpty((CharSequence) next2.getValue())) {
                        bqVar.f1815k.put(next2.getKey(), next2.getValue());
                    }
                }
            }
            return bqVar;
        } catch (Throwable th) {
            if (!C2265al.m608b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* renamed from: a */
    public static byte[] m552a(Object obj) {
        try {
            C2337e eVar = new C2337e();
            eVar.mo28099b();
            eVar.mo28094a("utf-8");
            eVar.mo28101c();
            eVar.mo28100b("RqdServer");
            eVar.mo28102c("sync");
            eVar.mo28095a("detail", obj);
            return eVar.mo28097a();
        } catch (Throwable th) {
            if (C2265al.m608b(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    public static C2330br m548a(byte[] bArr) {
        if (bArr != null) {
            try {
                C2337e eVar = new C2337e();
                eVar.mo28099b();
                eVar.mo28094a("utf-8");
                eVar.mo28096a(bArr);
                Object b = eVar.mo28098b("detail", new C2330br());
                if (C2330br.class.isInstance(b)) {
                    return C2330br.class.cast(b);
                }
                return null;
            } catch (Throwable th) {
                if (!C2265al.m608b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    /* renamed from: a */
    public static byte[] m551a(C2346m mVar) {
        try {
            C2345l lVar = new C2345l();
            lVar.mo28126a("utf-8");
            mVar.mo28089a(lVar);
            byte[] bArr = new byte[lVar.f1905a.position()];
            System.arraycopy(lVar.f1905a.array(), 0, bArr, 0, lVar.f1905a.position());
            return bArr;
        } catch (Throwable th) {
            if (C2265al.m608b(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
