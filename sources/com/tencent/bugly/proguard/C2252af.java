package com.tencent.bugly.proguard;

import android.content.Context;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.af */
/* compiled from: BUGLY */
public final class C2252af {

    /* renamed from: a */
    static C2252af f1508a;

    /* renamed from: b */
    protected Context f1509b;

    /* renamed from: c */
    public Map<String, String> f1510c = null;

    C2252af(Context context) {
        this.f1509b = context;
    }

    /* JADX WARNING: Removed duplicated region for block: B:97:0x0177 A[Catch:{ all -> 0x016a, all -> 0x018b }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final byte[] mo27998a(java.lang.String r21, byte[] r22, com.tencent.bugly.proguard.C2262aj r23, java.util.Map<java.lang.String, java.lang.String> r24) {
        /*
            r20 = this;
            r1 = r20
            r2 = r22
            r3 = r23
            r4 = 0
            r5 = 0
            if (r21 != 0) goto L_0x0012
            java.lang.Object[] r0 = new java.lang.Object[r5]
            java.lang.String r2 = "Failed for no URL."
            com.tencent.bugly.proguard.C2265al.m611e(r2, r0)
            return r4
        L_0x0012:
            if (r2 != 0) goto L_0x0017
            r8 = 0
            goto L_0x0019
        L_0x0017:
            int r0 = r2.length
            long r8 = (long) r0
        L_0x0019:
            r0 = 4
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r0[r5] = r21
            java.lang.Long r10 = java.lang.Long.valueOf(r8)
            r11 = 1
            r0[r11] = r10
            int r10 = android.os.Process.myPid()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r12 = 2
            r0[r12] = r10
            r10 = 3
            int r13 = android.os.Process.myTid()
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)
            r0[r10] = r13
            java.lang.String r10 = "request: %s, send: %d (pid=%d | tid=%d)"
            com.tencent.bugly.proguard.C2265al.m609c(r10, r0)
            r10 = r21
            r0 = 0
            r13 = 0
            r14 = 0
        L_0x0045:
            if (r0 > 0) goto L_0x01ae
            if (r13 > 0) goto L_0x01ae
            if (r14 == 0) goto L_0x004e
            r6 = r0
            r14 = 0
            goto L_0x0079
        L_0x004e:
            int r0 = r0 + 1
            if (r0 <= r11) goto L_0x0078
            java.lang.String r15 = java.lang.String.valueOf(r0)
            java.lang.String r6 = "try time: "
            java.lang.String r6 = r6.concat(r15)
            java.lang.Object[] r7 = new java.lang.Object[r5]
            com.tencent.bugly.proguard.C2265al.m609c(r6, r7)
            java.util.Random r6 = new java.util.Random
            long r11 = java.lang.System.currentTimeMillis()
            r6.<init>(r11)
            r11 = 10000(0x2710, float:1.4013E-41)
            int r6 = r6.nextInt(r11)
            long r11 = (long) r6
            r18 = 10000(0x2710, double:4.9407E-320)
            long r11 = r11 + r18
            android.os.SystemClock.sleep(r11)
        L_0x0078:
            r6 = r0
        L_0x0079:
            android.content.Context r0 = r1.f1509b
            java.lang.String r0 = com.tencent.bugly.proguard.C2232ab.m501c(r0)
            if (r0 != 0) goto L_0x0091
            java.lang.Object[] r0 = new java.lang.Object[r5]
            java.lang.String r11 = "Failed to request for network not avail"
            com.tencent.bugly.proguard.C2265al.m610d(r11, r0)
            r11 = r24
            r4 = 0
            r7 = 2
            r18 = 1
            goto L_0x01a7
        L_0x0091:
            r3.mo28014a((long) r8)
            r11 = r24
            java.net.HttpURLConnection r12 = m554a((java.lang.String) r10, (byte[]) r2, (java.lang.String) r0, (java.util.Map<java.lang.String, java.lang.String>) r11)
            if (r12 == 0) goto L_0x0197
            int r0 = r12.getResponseCode()     // Catch:{ IOException -> 0x016d }
            java.lang.String r7 = "response code "
            java.lang.String r15 = java.lang.String.valueOf(r0)     // Catch:{ IOException -> 0x016d }
            java.lang.String r7 = r7.concat(r15)     // Catch:{ IOException -> 0x016d }
            java.lang.Object[] r15 = new java.lang.Object[r5]     // Catch:{ IOException -> 0x016d }
            com.tencent.bugly.proguard.C2265al.m609c(r7, r15)     // Catch:{ IOException -> 0x016d }
            r7 = 200(0xc8, float:2.8E-43)
            if (r0 != r7) goto L_0x00d7
            java.util.Map r0 = m555a(r12)     // Catch:{ IOException -> 0x016d }
            r1.f1510c = r0     // Catch:{ IOException -> 0x016d }
            byte[] r7 = m556b(r12)     // Catch:{ IOException -> 0x016d }
            if (r7 != 0) goto L_0x00c2
            r4 = 0
            goto L_0x00c4
        L_0x00c2:
            int r0 = r7.length     // Catch:{ IOException -> 0x016d }
            long r4 = (long) r0     // Catch:{ IOException -> 0x016d }
        L_0x00c4:
            r3.mo28015b((long) r4)     // Catch:{ IOException -> 0x016d }
            r12.disconnect()     // Catch:{ all -> 0x00cb }
            goto L_0x00d6
        L_0x00cb:
            r0 = move-exception
            r2 = r0
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r2)
            if (r0 != 0) goto L_0x00d6
            r2.printStackTrace()
        L_0x00d6:
            return r7
        L_0x00d7:
            r4 = 301(0x12d, float:4.22E-43)
            if (r0 == r4) goto L_0x00ea
            r4 = 302(0x12e, float:4.23E-43)
            if (r0 == r4) goto L_0x00ea
            r4 = 303(0x12f, float:4.25E-43)
            if (r0 == r4) goto L_0x00ea
            r4 = 307(0x133, float:4.3E-43)
            if (r0 != r4) goto L_0x00e8
            goto L_0x00ea
        L_0x00e8:
            r4 = 0
            goto L_0x00eb
        L_0x00ea:
            r4 = 1
        L_0x00eb:
            if (r4 == 0) goto L_0x0145
            java.lang.String r4 = "Location"
            java.lang.String r4 = r12.getHeaderField(r4)     // Catch:{ IOException -> 0x013f }
            if (r4 != 0) goto L_0x011b
            java.lang.String r4 = "Failed to redirect: %d"
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ IOException -> 0x0117 }
            java.lang.String r0 = r4.concat(r0)     // Catch:{ IOException -> 0x0117 }
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ IOException -> 0x0117 }
            com.tencent.bugly.proguard.C2265al.m611e(r0, r5)     // Catch:{ IOException -> 0x0117 }
            r12.disconnect()     // Catch:{ all -> 0x010a }
        L_0x0108:
            r2 = 0
            goto L_0x0116
        L_0x010a:
            r0 = move-exception
            r2 = r0
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r2)
            if (r0 != 0) goto L_0x0108
            r2.printStackTrace()
            goto L_0x0108
        L_0x0116:
            return r2
        L_0x0117:
            r0 = move-exception
            r7 = 2
            r14 = 1
            goto L_0x016f
        L_0x011b:
            int r13 = r13 + 1
            java.lang.String r5 = "redirect code: %d ,to:%s"
            r7 = 2
            java.lang.Object[] r6 = new java.lang.Object[r7]     // Catch:{ IOException -> 0x0136 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ IOException -> 0x0136 }
            r10 = 0
            r6[r10] = r0     // Catch:{ IOException -> 0x0136 }
            r18 = 1
            r6[r18] = r4     // Catch:{ IOException -> 0x0134 }
            com.tencent.bugly.proguard.C2265al.m609c(r5, r6)     // Catch:{ IOException -> 0x0134 }
            r10 = r4
            r6 = 0
            r14 = 1
            goto L_0x0148
        L_0x0134:
            r0 = move-exception
            goto L_0x013c
        L_0x0136:
            r0 = move-exception
            goto L_0x013a
        L_0x0138:
            r0 = move-exception
            r7 = 2
        L_0x013a:
            r18 = 1
        L_0x013c:
            r10 = r4
            r6 = 0
            goto L_0x0143
        L_0x013f:
            r0 = move-exception
            r7 = 2
            r18 = 1
        L_0x0143:
            r14 = 1
            goto L_0x0171
        L_0x0145:
            r7 = 2
            r18 = 1
        L_0x0148:
            int r0 = r12.getContentLength()     // Catch:{ IOException -> 0x0168 }
            long r4 = (long) r0     // Catch:{ IOException -> 0x0168 }
            r16 = 0
            int r0 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r0 >= 0) goto L_0x0155
            r4 = 0
        L_0x0155:
            r3.mo28015b((long) r4)     // Catch:{ IOException -> 0x0168 }
            r12.disconnect()     // Catch:{ all -> 0x015c }
            goto L_0x01a7
        L_0x015c:
            r0 = move-exception
            r4 = r0
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r4)
            if (r0 != 0) goto L_0x01a7
        L_0x0164:
            r4.printStackTrace()
            goto L_0x01a7
        L_0x0168:
            r0 = move-exception
            goto L_0x0171
        L_0x016a:
            r0 = move-exception
            r2 = r0
            goto L_0x0187
        L_0x016d:
            r0 = move-exception
            r7 = 2
        L_0x016f:
            r18 = 1
        L_0x0171:
            boolean r4 = com.tencent.bugly.proguard.C2265al.m605a(r0)     // Catch:{ all -> 0x016a }
            if (r4 != 0) goto L_0x017a
            r0.printStackTrace()     // Catch:{ all -> 0x016a }
        L_0x017a:
            r12.disconnect()     // Catch:{ all -> 0x017e }
            goto L_0x01a7
        L_0x017e:
            r0 = move-exception
            r4 = r0
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r4)
            if (r0 != 0) goto L_0x01a7
            goto L_0x0164
        L_0x0187:
            r12.disconnect()     // Catch:{ all -> 0x018b }
            goto L_0x0196
        L_0x018b:
            r0 = move-exception
            r3 = r0
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r3)
            if (r0 != 0) goto L_0x0196
            r3.printStackTrace()
        L_0x0196:
            throw r2
        L_0x0197:
            r4 = 0
            r7 = 2
            r18 = 1
            java.lang.Object[] r0 = new java.lang.Object[r4]
            java.lang.String r5 = "Failed to execute post."
            com.tencent.bugly.proguard.C2265al.m609c(r5, r0)
            r4 = 0
            r3.mo28015b((long) r4)
        L_0x01a7:
            r0 = r6
            r4 = 0
            r5 = 0
            r11 = 1
            r12 = 2
            goto L_0x0045
        L_0x01ae:
            r2 = r4
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2252af.mo27998a(java.lang.String, byte[], com.tencent.bugly.proguard.aj, java.util.Map):byte[]");
    }

    /* renamed from: a */
    private static Map<String, String> m555a(HttpURLConnection httpURLConnection) {
        HashMap hashMap = new HashMap();
        Map headerFields = httpURLConnection.getHeaderFields();
        if (headerFields == null || headerFields.size() == 0) {
            return null;
        }
        for (String str : headerFields.keySet()) {
            List list = (List) headerFields.get(str);
            if (list.size() > 0) {
                hashMap.put(str, list.get(0));
            }
        }
        return hashMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x003b A[Catch:{ all -> 0x0049, all -> 0x0050 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0040 A[SYNTHETIC, Splitter:B:24:0x0040] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] m556b(java.net.HttpURLConnection r5) {
        /*
            r0 = 0
            if (r5 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ all -> 0x0033 }
            java.io.InputStream r5 = r5.getInputStream()     // Catch:{ all -> 0x0033 }
            r1.<init>(r5)     // Catch:{ all -> 0x0033 }
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0031 }
            r5.<init>()     // Catch:{ all -> 0x0031 }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch:{ all -> 0x0031 }
        L_0x0016:
            int r3 = r1.read(r2)     // Catch:{ all -> 0x0031 }
            if (r3 <= 0) goto L_0x0021
            r4 = 0
            r5.write(r2, r4, r3)     // Catch:{ all -> 0x0031 }
            goto L_0x0016
        L_0x0021:
            r5.flush()     // Catch:{ all -> 0x0031 }
            byte[] r5 = r5.toByteArray()     // Catch:{ all -> 0x0031 }
            r1.close()     // Catch:{ all -> 0x002c }
            goto L_0x0030
        L_0x002c:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0030:
            return r5
        L_0x0031:
            r5 = move-exception
            goto L_0x0035
        L_0x0033:
            r5 = move-exception
            r1 = r0
        L_0x0035:
            boolean r2 = com.tencent.bugly.proguard.C2265al.m605a(r5)     // Catch:{ all -> 0x0049 }
            if (r2 != 0) goto L_0x003e
            r5.printStackTrace()     // Catch:{ all -> 0x0049 }
        L_0x003e:
            if (r1 == 0) goto L_0x0048
            r1.close()     // Catch:{ all -> 0x0044 }
            goto L_0x0048
        L_0x0044:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0048:
            return r0
        L_0x0049:
            r5 = move-exception
            if (r1 == 0) goto L_0x0054
            r1.close()     // Catch:{ all -> 0x0050 }
            goto L_0x0054
        L_0x0050:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0054:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2252af.m556b(java.net.HttpURLConnection):byte[]");
    }

    /* renamed from: a */
    private static HttpURLConnection m554a(String str, byte[] bArr, String str2, Map<String, String> map) {
        if (str == null) {
            C2265al.m611e("destUrl is null.", new Object[0]);
            return null;
        }
        HttpURLConnection a = m553a(str2, str);
        if (a == null) {
            C2265al.m611e("Failed to get HttpURLConnection object.", new Object[0]);
            return null;
        }
        try {
            a.setRequestProperty("wup_version", "3.0");
            if (map != null) {
                if (map.size() > 0) {
                    for (Map.Entry next : map.entrySet()) {
                        a.setRequestProperty((String) next.getKey(), URLEncoder.encode((String) next.getValue(), "utf-8"));
                    }
                }
            }
            a.setRequestProperty("A37", URLEncoder.encode(str2, "utf-8"));
            a.setRequestProperty("A38", URLEncoder.encode(str2, "utf-8"));
            OutputStream outputStream = a.getOutputStream();
            if (bArr == null) {
                outputStream.write(0);
            } else {
                outputStream.write(bArr);
            }
            return a;
        } catch (Throwable th) {
            if (!C2265al.m605a(th)) {
                th.printStackTrace();
            }
            C2265al.m611e("Failed to upload, please check your network.", new Object[0]);
            return null;
        }
    }

    /* renamed from: a */
    private static HttpURLConnection m553a(String str, String str2) {
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(str2);
            if (C2268an.f1571a != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection(C2268an.f1571a);
            } else if (str == null || !str.toLowerCase(Locale.US).contains("wap")) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.parseInt(System.getProperty("http.proxyPort")))));
            }
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(false);
            return httpURLConnection;
        } catch (Throwable th) {
            if (C2265al.m605a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
