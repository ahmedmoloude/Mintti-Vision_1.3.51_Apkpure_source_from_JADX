package com.tencent.bugly.crashreport.crash.anr;

import com.tencent.bugly.proguard.C2265al;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* compiled from: BUGLY */
public class TraceFileHelper {

    /* renamed from: com.tencent.bugly.crashreport.crash.anr.TraceFileHelper$a */
    /* compiled from: BUGLY */
    public static class C2227a {

        /* renamed from: a */
        public long f1394a;

        /* renamed from: b */
        public String f1395b;

        /* renamed from: c */
        public long f1396c;

        /* renamed from: d */
        public Map<String, String[]> f1397d;
    }

    /* renamed from: com.tencent.bugly.crashreport.crash.anr.TraceFileHelper$b */
    /* compiled from: BUGLY */
    public interface C2228b {
        /* renamed from: a */
        boolean mo27911a(long j);

        /* renamed from: a */
        boolean mo27912a(long j, long j2, String str);

        /* renamed from: a */
        boolean mo27913a(String str, int i, String str2, String str3);
    }

    public static C2227a readTargetDumpInfo(final String str, String str2, final boolean z) {
        if (!(str == null || str2 == null)) {
            final C2227a aVar = new C2227a();
            readTraceFile(str2, new C2228b() {
                /* renamed from: a */
                public final boolean mo27913a(String str, int i, String str2, String str3) {
                    C2265al.m609c("new thread %s", str);
                    if (aVar.f1394a > 0 && aVar.f1396c > 0 && aVar.f1395b != null) {
                        if (aVar.f1397d == null) {
                            aVar.f1397d = new HashMap();
                        }
                        aVar.f1397d.put(str, new String[]{str2, str3, String.valueOf(i)});
                    }
                    return true;
                }

                /* renamed from: a */
                public final boolean mo27912a(long j, long j2, String str) {
                    C2265al.m609c("new process %s", str);
                    if (!str.equals(str)) {
                        return true;
                    }
                    aVar.f1394a = j;
                    aVar.f1395b = str;
                    aVar.f1396c = j2;
                    return z;
                }

                /* renamed from: a */
                public final boolean mo27911a(long j) {
                    C2265al.m609c("process end %d", Long.valueOf(j));
                    return aVar.f1394a <= 0 || aVar.f1396c <= 0 || aVar.f1395b == null;
                }
            });
            if (aVar.f1394a <= 0 || aVar.f1396c <= 0 || aVar.f1395b == null) {
                return null;
            }
            return aVar;
        }
        return null;
    }

    public static C2227a readFirstDumpInfo(String str, final boolean z) {
        if (str == null) {
            C2265al.m611e("path:%s", str);
            return null;
        }
        final C2227a aVar = new C2227a();
        readTraceFile(str, new C2228b() {
            /* renamed from: a */
            public final boolean mo27913a(String str, int i, String str2, String str3) {
                C2265al.m609c("new thread %s", str);
                if (aVar.f1397d == null) {
                    aVar.f1397d = new HashMap();
                }
                aVar.f1397d.put(str, new String[]{str2, str3, String.valueOf(i)});
                return true;
            }

            /* renamed from: a */
            public final boolean mo27912a(long j, long j2, String str) {
                C2265al.m609c("new process %s", str);
                aVar.f1394a = j;
                aVar.f1395b = str;
                aVar.f1396c = j2;
                return z;
            }

            /* renamed from: a */
            public final boolean mo27911a(long j) {
                C2265al.m609c("process end %d", Long.valueOf(j));
                return false;
            }
        });
        if (aVar.f1394a > 0 && aVar.f1396c > 0 && aVar.f1395b != null) {
            return aVar;
        }
        C2265al.m611e("first dump error %s", aVar.f1394a + " " + aVar.f1396c + " " + aVar.f1395b);
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:67:0x018b A[Catch:{ all -> 0x0181 }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01b4 A[SYNTHETIC, Splitter:B:70:0x01b4] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01c6 A[SYNTHETIC, Splitter:B:78:0x01c6] */
    /* JADX WARNING: Removed duplicated region for block: B:98:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void readTraceFile(java.lang.String r18, com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.C2228b r19) {
        /*
            r0 = r18
            r6 = r19
            java.lang.String r7 = "\\s"
            if (r0 == 0) goto L_0x01d6
            if (r6 != 0) goto L_0x000c
            goto L_0x01d6
        L_0x000c:
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            boolean r0 = r1.exists()
            if (r0 != 0) goto L_0x0018
            return
        L_0x0018:
            r1.lastModified()
            r1.length()
            r2 = 0
            r8 = 2
            r9 = 0
            r10 = 1
            java.io.BufferedReader r11 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0184 }
            java.io.FileReader r0 = new java.io.FileReader     // Catch:{ Exception -> 0x0184 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0184 }
            r11.<init>(r0)     // Catch:{ Exception -> 0x0184 }
            java.lang.String r0 = "-{5}\\spid\\s\\d+\\sat\\s\\d+-\\d+-\\d+\\s\\d{2}:\\d{2}:\\d{2}\\s-{5}"
            java.util.regex.Pattern r12 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r0 = "-{5}\\send\\s\\d+\\s-{5}"
            java.util.regex.Pattern r13 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r0 = "Cmd\\sline:\\s(\\S+)"
            java.util.regex.Pattern r14 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r0 = "\".+\"\\s(daemon\\s){0,1}prio=\\d+\\stid=\\d+\\s.*"
            java.util.regex.Pattern r15 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.text.SimpleDateFormat r5 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r0 = "yyyy-MM-dd HH:mm:ss"
            java.util.Locale r1 = java.util.Locale.US     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r5.<init>(r0, r1)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
        L_0x004d:
            java.util.regex.Pattern[] r0 = new java.util.regex.Pattern[r10]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r0[r9] = r12     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.Object[] r0 = m425a(r11, r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            if (r0 == 0) goto L_0x016a
            java.util.regex.Pattern[] r1 = new java.util.regex.Pattern[r10]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r1[r9] = r14     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.Object[] r1 = m425a(r11, r1)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            if (r1 != 0) goto L_0x0078
            java.lang.String r0 = "Failed to find process name."
            java.lang.Object[] r1 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            com.tencent.bugly.proguard.C2265al.m610d(r0, r1)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r11.close()     // Catch:{ IOException -> 0x006c }
            return
        L_0x006c:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r1)
            if (r0 != 0) goto L_0x0077
            r1.printStackTrace()
        L_0x0077:
            return
        L_0x0078:
            r0 = r0[r10]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String[] r0 = r0.split(r7)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r2 = r0[r8]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            long r2 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r4.<init>()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r16 = 4
            r9 = r0[r16]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r4.append(r9)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r9 = " "
            r4.append(r9)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r9 = 5
            r0 = r0[r9]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r4.append(r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r0 = r4.toString()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.util.Date r0 = r5.parse(r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            long r16 = r0.getTime()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r0 = r1[r10]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.util.regex.Matcher r0 = r14.matcher(r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r0.find()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r0.group(r10)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r9 = r0.group(r10)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r0 = r19
            r1 = r2
            r3 = r16
            r16 = r5
            r5 = r9
            boolean r0 = r0.mo27912a(r1, r3, r5)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            if (r0 != 0) goto L_0x00dd
            r11.close()     // Catch:{ IOException -> 0x00d1 }
            return
        L_0x00d1:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r1)
            if (r0 != 0) goto L_0x00dc
            r1.printStackTrace()
        L_0x00dc:
            return
        L_0x00dd:
            java.util.regex.Pattern[] r0 = new java.util.regex.Pattern[r8]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r1 = 0
            r0[r1] = r15     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r0[r10] = r13     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.Object[] r0 = m425a(r11, r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            if (r0 == 0) goto L_0x0165
            r2 = r0[r1]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            if (r2 != r15) goto L_0x013f
            r0 = r0[r10]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r1 = "\".+\""
            java.util.regex.Pattern r1 = java.util.regex.Pattern.compile(r1)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.util.regex.Matcher r1 = r1.matcher(r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r1.find()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r1 = r1.group()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            int r2 = r1.length()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            int r2 = r2 - r10
            java.lang.String r1 = r1.substring(r10, r2)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r2 = "NATIVE"
            r0.contains(r2)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r2 = "tid=\\d+"
            java.util.regex.Pattern r2 = java.util.regex.Pattern.compile(r2)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.util.regex.Matcher r0 = r2.matcher(r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r0.find()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r0 = r0.group()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r2 = "="
            int r2 = r0.indexOf(r2)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            int r2 = r2 + r10
            java.lang.String r0 = r0.substring(r2)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r2 = m424a(r11)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r3 = m426b(r11)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r6.mo27913a(r1, r0, r2, r3)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            goto L_0x00dd
        L_0x013f:
            r0 = r0[r10]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            java.lang.String[] r0 = r0.split(r7)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            r0 = r0[r8]     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            boolean r0 = r6.mo27911a(r0)     // Catch:{ Exception -> 0x017e, all -> 0x017a }
            if (r0 != 0) goto L_0x0165
            r11.close()     // Catch:{ IOException -> 0x0159 }
            return
        L_0x0159:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r1)
            if (r0 != 0) goto L_0x0164
            r1.printStackTrace()
        L_0x0164:
            return
        L_0x0165:
            r5 = r16
            r9 = 0
            goto L_0x004d
        L_0x016a:
            r11.close()     // Catch:{ IOException -> 0x016e }
            return
        L_0x016e:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r1)
            if (r0 != 0) goto L_0x0179
            r1.printStackTrace()
        L_0x0179:
            return
        L_0x017a:
            r0 = move-exception
            r1 = r0
            r2 = r11
            goto L_0x01c4
        L_0x017e:
            r0 = move-exception
            r2 = r11
            goto L_0x0185
        L_0x0181:
            r0 = move-exception
            r1 = r0
            goto L_0x01c4
        L_0x0184:
            r0 = move-exception
        L_0x0185:
            boolean r1 = com.tencent.bugly.proguard.C2265al.m605a(r0)     // Catch:{ all -> 0x0181 }
            if (r1 != 0) goto L_0x018e
            r0.printStackTrace()     // Catch:{ all -> 0x0181 }
        L_0x018e:
            java.lang.String r1 = "trace open fail:%s : %s"
            java.lang.Object[] r3 = new java.lang.Object[r8]     // Catch:{ all -> 0x0181 }
            java.lang.Class r4 = r0.getClass()     // Catch:{ all -> 0x0181 }
            java.lang.String r4 = r4.getName()     // Catch:{ all -> 0x0181 }
            r5 = 0
            r3[r5] = r4     // Catch:{ all -> 0x0181 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0181 }
            r4.<init>()     // Catch:{ all -> 0x0181 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0181 }
            r4.append(r0)     // Catch:{ all -> 0x0181 }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x0181 }
            r3[r10] = r0     // Catch:{ all -> 0x0181 }
            com.tencent.bugly.proguard.C2265al.m610d(r1, r3)     // Catch:{ all -> 0x0181 }
            if (r2 == 0) goto L_0x01c3
            r2.close()     // Catch:{ IOException -> 0x01b8 }
            return
        L_0x01b8:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r1)
            if (r0 != 0) goto L_0x01c3
            r1.printStackTrace()
        L_0x01c3:
            return
        L_0x01c4:
            if (r2 == 0) goto L_0x01d5
            r2.close()     // Catch:{ IOException -> 0x01ca }
            goto L_0x01d5
        L_0x01ca:
            r0 = move-exception
            r2 = r0
            boolean r0 = com.tencent.bugly.proguard.C2265al.m605a(r2)
            if (r0 != 0) goto L_0x01d5
            r2.printStackTrace()
        L_0x01d5:
            throw r1
        L_0x01d6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.readTraceFile(java.lang.String, com.tencent.bugly.crashreport.crash.anr.TraceFileHelper$b):void");
    }

    /* renamed from: a */
    private static Object[] m425a(BufferedReader bufferedReader, Pattern... patternArr) throws IOException {
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return null;
            }
            int length = patternArr.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    Pattern pattern = patternArr[i];
                    if (pattern.matcher(readLine).matches()) {
                        return new Object[]{pattern, readLine};
                    }
                    i++;
                }
            }
        }
    }

    /* renamed from: a */
    private static String m424a(BufferedReader bufferedReader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return null;
            }
            stringBuffer.append(readLine + "\n");
        }
        return stringBuffer.toString();
    }

    /* renamed from: b */
    private static String m426b(BufferedReader bufferedReader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null && readLine.trim().length() > 0) {
                stringBuffer.append(readLine + "\n");
            }
        }
        return stringBuffer.toString();
    }
}
