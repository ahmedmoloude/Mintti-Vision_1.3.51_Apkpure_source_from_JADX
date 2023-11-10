package com.tencent.bugly.proguard;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.tencent.bugly.proguard.be */
/* compiled from: BUGLY */
public final class C2316be {

    /* renamed from: a */
    private static List<File> f1743a = new ArrayList();

    /* renamed from: d */
    private static Map<String, Integer> m813d(String str) {
        if (str == null) {
            return null;
        }
        try {
            HashMap hashMap = new HashMap();
            for (String str2 : str.split(",")) {
                String[] split = str2.split(":");
                if (split.length != 2) {
                    C2265al.m611e("error format at %s", str2);
                    return null;
                }
                hashMap.put(split[0], Integer.valueOf(Integer.parseInt(split[1])));
            }
            return hashMap;
        } catch (Exception e) {
            C2265al.m611e("error format intStateStr %s", str);
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    protected static String m803a(String str) {
        if (str == null) {
            return "";
        }
        String[] split = str.split("\n");
        if (split == null || split.length == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (String str2 : split) {
            if (!str2.contains("java.lang.Thread.getStackTrace(")) {
                sb.append(str2);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /* renamed from: a */
    private static <KeyT, ValueT> ValueT m801a(Map<KeyT, ValueT> map, KeyT keyt, ValueT valuet) {
        try {
            ValueT valuet2 = map.get(keyt);
            if (valuet2 != null) {
                return valuet2;
            }
            return valuet;
        } catch (Exception e) {
            C2265al.m605a(e);
        }
    }

    /* renamed from: a */
    private static String m802a(BufferedInputStream bufferedInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream(1024);
            while (true) {
                try {
                    int read = bufferedInputStream.read();
                    if (read == -1) {
                        break;
                    } else if (read == 0) {
                        String str = new String(byteArrayOutputStream.toByteArray(), "UTf-8");
                        byteArrayOutputStream.close();
                        return str;
                    } else {
                        byteArrayOutputStream.write(read);
                    }
                } catch (Throwable th) {
                    th = th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
            try {
                C2265al.m605a(th);
            } finally {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x008b A[SYNTHETIC, Splitter:B:52:0x008b] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0098 A[SYNTHETIC, Splitter:B:60:0x0098] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.tencent.bugly.crashreport.crash.CrashDetailBean m799a(android.content.Context r6, java.lang.String r7, com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler r8) {
        /*
            r0 = 0
            r1 = 0
            if (r6 == 0) goto L_0x00a2
            if (r7 == 0) goto L_0x00a2
            if (r8 != 0) goto L_0x000a
            goto L_0x00a2
        L_0x000a:
            java.io.File r2 = new java.io.File
            java.lang.String r3 = "rqd_record.eup"
            r2.<init>(r7, r3)
            boolean r7 = r2.exists()
            if (r7 == 0) goto L_0x00a1
            boolean r7 = r2.canRead()
            if (r7 != 0) goto L_0x001f
            goto L_0x00a1
        L_0x001f:
            java.io.BufferedInputStream r7 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x0084, all -> 0x0082 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0084, all -> 0x0082 }
            r3.<init>(r2)     // Catch:{ IOException -> 0x0084, all -> 0x0082 }
            r7.<init>(r3)     // Catch:{ IOException -> 0x0084, all -> 0x0082 }
            java.lang.String r2 = m802a((java.io.BufferedInputStream) r7)     // Catch:{ IOException -> 0x0080 }
            r3 = 1
            if (r2 == 0) goto L_0x006e
            java.lang.String r4 = "NATIVE_RQD_REPORT"
            boolean r4 = r2.equals(r4)     // Catch:{ IOException -> 0x0080 }
            if (r4 != 0) goto L_0x0039
            goto L_0x006e
        L_0x0039:
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ IOException -> 0x0080 }
            r2.<init>()     // Catch:{ IOException -> 0x0080 }
        L_0x003e:
            r4 = r1
        L_0x003f:
            java.lang.String r5 = m802a((java.io.BufferedInputStream) r7)     // Catch:{ IOException -> 0x0080 }
            if (r5 == 0) goto L_0x004d
            if (r4 != 0) goto L_0x0049
            r4 = r5
            goto L_0x003f
        L_0x0049:
            r2.put(r4, r5)     // Catch:{ IOException -> 0x0080 }
            goto L_0x003e
        L_0x004d:
            if (r4 == 0) goto L_0x0061
            java.lang.String r6 = "record not pair! drop! %s"
            java.lang.Object[] r8 = new java.lang.Object[r3]     // Catch:{ IOException -> 0x0080 }
            r8[r0] = r4     // Catch:{ IOException -> 0x0080 }
            com.tencent.bugly.proguard.C2265al.m611e(r6, r8)     // Catch:{ IOException -> 0x0080 }
            r7.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x0060
        L_0x005c:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0060:
            return r1
        L_0x0061:
            com.tencent.bugly.crashreport.crash.CrashDetailBean r6 = m800a((android.content.Context) r6, (java.util.Map<java.lang.String, java.lang.String>) r2, (com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler) r8)     // Catch:{ IOException -> 0x0080 }
            r7.close()     // Catch:{ IOException -> 0x0069 }
            goto L_0x006d
        L_0x0069:
            r7 = move-exception
            r7.printStackTrace()
        L_0x006d:
            return r6
        L_0x006e:
            java.lang.String r6 = "record read fail! %s"
            java.lang.Object[] r8 = new java.lang.Object[r3]     // Catch:{ IOException -> 0x0080 }
            r8[r0] = r2     // Catch:{ IOException -> 0x0080 }
            com.tencent.bugly.proguard.C2265al.m611e(r6, r8)     // Catch:{ IOException -> 0x0080 }
            r7.close()     // Catch:{ IOException -> 0x007b }
            goto L_0x007f
        L_0x007b:
            r6 = move-exception
            r6.printStackTrace()
        L_0x007f:
            return r1
        L_0x0080:
            r6 = move-exception
            goto L_0x0086
        L_0x0082:
            r6 = move-exception
            goto L_0x0096
        L_0x0084:
            r6 = move-exception
            r7 = r1
        L_0x0086:
            r6.printStackTrace()     // Catch:{ all -> 0x0094 }
            if (r7 == 0) goto L_0x0093
            r7.close()     // Catch:{ IOException -> 0x008f }
            goto L_0x0093
        L_0x008f:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0093:
            return r1
        L_0x0094:
            r6 = move-exception
            r1 = r7
        L_0x0096:
            if (r1 == 0) goto L_0x00a0
            r1.close()     // Catch:{ IOException -> 0x009c }
            goto L_0x00a0
        L_0x009c:
            r7 = move-exception
            r7.printStackTrace()
        L_0x00a0:
            throw r6
        L_0x00a1:
            return r1
        L_0x00a2:
            java.lang.Object[] r6 = new java.lang.Object[r0]
            java.lang.String r7 = "get eup record file args error"
            com.tencent.bugly.proguard.C2265al.m611e(r7, r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2316be.m799a(android.content.Context, java.lang.String, com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler):com.tencent.bugly.crashreport.crash.CrashDetailBean");
    }

    /* renamed from: b */
    private static String m810b(String str, String str2) {
        BufferedReader b = C2273ap.m662b(str, "reg_record.txt");
        if (b == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String readLine = b.readLine();
            if (readLine != null) {
                if (readLine.startsWith(str2)) {
                    int i = 18;
                    int i2 = 0;
                    int i3 = 0;
                    while (true) {
                        String readLine2 = b.readLine();
                        if (readLine2 == null) {
                            break;
                        }
                        if (i2 % 4 == 0) {
                            if (i2 > 0) {
                                sb.append("\n");
                            }
                            sb.append("  ");
                        } else {
                            if (readLine2.length() > 16) {
                                i = 28;
                            }
                            sb.append("                ".substring(0, i - i3));
                        }
                        i3 = readLine2.length();
                        sb.append(readLine2);
                        i2++;
                    }
                    sb.append("\n");
                    String sb2 = sb.toString();
                    if (b != null) {
                        try {
                            b.close();
                        } catch (Exception e) {
                            C2265al.m605a(e);
                        }
                    }
                    return sb2;
                }
            }
            if (b != null) {
                try {
                    b.close();
                } catch (Exception e2) {
                    C2265al.m605a(e2);
                }
            }
            return null;
        } catch (Throwable th) {
            if (b != null) {
                try {
                    b.close();
                } catch (Exception e3) {
                    C2265al.m605a(e3);
                }
            }
            throw th;
        }
    }

    /* renamed from: c */
    private static String m811c(String str, String str2) {
        BufferedReader b = C2273ap.m662b(str, "map_record.txt");
        if (b == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String readLine = b.readLine();
            if (readLine != null) {
                if (readLine.startsWith(str2)) {
                    while (true) {
                        String readLine2 = b.readLine();
                        if (readLine2 == null) {
                            break;
                        }
                        sb.append("  ");
                        sb.append(readLine2);
                        sb.append("\n");
                    }
                    String sb2 = sb.toString();
                    if (b != null) {
                        try {
                            b.close();
                        } catch (Exception e) {
                            C2265al.m605a(e);
                        }
                    }
                    return sb2;
                }
            }
            if (b != null) {
                try {
                    b.close();
                } catch (Exception e2) {
                    C2265al.m605a(e2);
                }
            }
            return null;
        } catch (Throwable th) {
            if (b != null) {
                try {
                    b.close();
                } catch (Exception e3) {
                    C2265al.m605a(e3);
                }
            }
            throw th;
        }
    }

    /* renamed from: a */
    public static String m805a(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String b = m810b(str, str2);
        if (b != null && !b.isEmpty()) {
            sb.append("Register infos:\n");
            sb.append(b);
        }
        String c = m811c(str, str2);
        if (c != null && !c.isEmpty()) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append("System SO infos:\n");
            sb.append(c);
        }
        return sb.toString();
    }

    /* renamed from: b */
    public static String m809b(String str) {
        if (str == null) {
            return null;
        }
        File file = new File(str, "backup_record.txt");
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    /* renamed from: c */
    public static void m812c(String str) {
        File[] listFiles;
        if (str != null) {
            try {
                File file = new File(str);
                if (file.canRead() && file.isDirectory() && (listFiles = file.listFiles()) != null) {
                    for (File file2 : listFiles) {
                        if (file2.canRead() && file2.canWrite() && file2.length() == 0) {
                            file2.delete();
                            C2265al.m609c("Delete empty record file %s", file2.getAbsoluteFile());
                        }
                    }
                }
            } catch (Throwable th) {
                C2265al.m605a(th);
            }
        }
    }

    /* renamed from: a */
    public static void m807a(boolean z, String str) {
        if (str != null) {
            f1743a.add(new File(str, "rqd_record.eup"));
            f1743a.add(new File(str, "reg_record.txt"));
            f1743a.add(new File(str, "map_record.txt"));
            f1743a.add(new File(str, "backup_record.txt"));
            if (z) {
                m812c(str);
            }
        }
        List<File> list = f1743a;
        if (list != null && list.size() > 0) {
            for (File next : f1743a) {
                if (next.exists() && next.canWrite()) {
                    next.delete();
                    C2265al.m609c("Delete record file %s", next.getAbsoluteFile());
                }
            }
        }
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00df A[SYNTHETIC, Splitter:B:36:0x00df] */
    /* JADX WARNING: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m804a(java.lang.String r6, int r7, java.lang.String r8, boolean r9) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x00ef
            if (r7 > 0) goto L_0x0007
            goto L_0x00ef
        L_0x0007:
            java.io.File r1 = new java.io.File
            r1.<init>(r6)
            boolean r2 = r1.exists()
            if (r2 == 0) goto L_0x00ef
            boolean r2 = r1.canRead()
            if (r2 != 0) goto L_0x001a
            goto L_0x00ef
        L_0x001a:
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]
            long r3 = r1.length()
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r4 = 0
            r2[r4] = r3
            r3 = 1
            java.lang.String r5 = r1.getAbsolutePath()
            r2[r3] = r5
            java.lang.String r3 = "Read system log from native record file(length: %s bytes): %s"
            com.tencent.bugly.proguard.C2265al.m604a((java.lang.String) r3, (java.lang.Object[]) r2)
            java.util.List<java.io.File> r2 = f1743a
            r2.add(r1)
            java.lang.Object[] r2 = new java.lang.Object[r4]
            java.lang.String r3 = "Add this record file to list for cleaning lastly."
            com.tencent.bugly.proguard.C2265al.m609c(r3, r2)
            if (r8 != 0) goto L_0x004d
            java.io.File r8 = new java.io.File
            r8.<init>(r6)
            java.lang.String r6 = com.tencent.bugly.proguard.C2273ap.m644a((java.io.File) r8, (int) r7, (boolean) r9)
            goto L_0x00e2
        L_0x004d:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ all -> 0x00bb }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ all -> 0x00bb }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ all -> 0x00bb }
            r5.<init>(r1)     // Catch:{ all -> 0x00bb }
            java.lang.String r1 = "utf-8"
            r3.<init>(r5, r1)     // Catch:{ all -> 0x00bb }
            r2.<init>(r3)     // Catch:{ all -> 0x00bb }
        L_0x0063:
            java.lang.String r0 = r2.readLine()     // Catch:{ all -> 0x00b8 }
            if (r0 == 0) goto L_0x00ab
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b8 }
            r1.<init>()     // Catch:{ all -> 0x00b8 }
            r1.append(r8)     // Catch:{ all -> 0x00b8 }
            java.lang.String r3 = "[ ]*:"
            r1.append(r3)     // Catch:{ all -> 0x00b8 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00b8 }
            java.util.regex.Pattern r1 = java.util.regex.Pattern.compile(r1)     // Catch:{ all -> 0x00b8 }
            java.util.regex.Matcher r1 = r1.matcher(r0)     // Catch:{ all -> 0x00b8 }
            boolean r1 = r1.find()     // Catch:{ all -> 0x00b8 }
            if (r1 == 0) goto L_0x0090
            r6.append(r0)     // Catch:{ all -> 0x00b8 }
            java.lang.String r0 = "\n"
            r6.append(r0)     // Catch:{ all -> 0x00b8 }
        L_0x0090:
            if (r7 <= 0) goto L_0x0063
            int r0 = r6.length()     // Catch:{ all -> 0x00b8 }
            if (r0 <= r7) goto L_0x0063
            if (r9 == 0) goto L_0x00a2
            int r8 = r6.length()     // Catch:{ all -> 0x00b8 }
            r6.delete(r7, r8)     // Catch:{ all -> 0x00b8 }
            goto L_0x00ab
        L_0x00a2:
            int r0 = r6.length()     // Catch:{ all -> 0x00b8 }
            int r0 = r0 - r7
            r6.delete(r4, r0)     // Catch:{ all -> 0x00b8 }
            goto L_0x0063
        L_0x00ab:
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x00b8 }
            r2.close()     // Catch:{ Exception -> 0x00b3 }
            goto L_0x00e2
        L_0x00b3:
            r7 = move-exception
            com.tencent.bugly.proguard.C2265al.m605a(r7)
            goto L_0x00e2
        L_0x00b8:
            r7 = move-exception
            r0 = r2
            goto L_0x00bc
        L_0x00bb:
            r7 = move-exception
        L_0x00bc:
            com.tencent.bugly.proguard.C2265al.m605a(r7)     // Catch:{ all -> 0x00e3 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e3 }
            java.lang.String r9 = "\n[error:"
            r8.<init>(r9)     // Catch:{ all -> 0x00e3 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00e3 }
            r8.append(r7)     // Catch:{ all -> 0x00e3 }
            java.lang.String r7 = "]"
            r8.append(r7)     // Catch:{ all -> 0x00e3 }
            java.lang.String r7 = r8.toString()     // Catch:{ all -> 0x00e3 }
            r6.append(r7)     // Catch:{ all -> 0x00e3 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x00e3 }
            if (r0 == 0) goto L_0x00e2
            r0.close()     // Catch:{ Exception -> 0x00b3 }
        L_0x00e2:
            return r6
        L_0x00e3:
            r6 = move-exception
            if (r0 == 0) goto L_0x00ee
            r0.close()     // Catch:{ Exception -> 0x00ea }
            goto L_0x00ee
        L_0x00ea:
            r7 = move-exception
            com.tencent.bugly.proguard.C2265al.m605a(r7)
        L_0x00ee:
            throw r6
        L_0x00ef:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2316be.m804a(java.lang.String, int, java.lang.String, boolean):java.lang.String");
    }

    /* renamed from: a */
    private static Map<String, String> m806a(Map<String, String> map) {
        String str = map.get("key-value");
        if (str == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (String split : str.split("\n")) {
            String[] split2 = split.split("=");
            if (split2.length == 2) {
                hashMap.put(split2[0], split2[1]);
            }
        }
        return hashMap;
    }

    /* renamed from: b */
    private static long m808b(Map<String, String> map) {
        String str = map.get("launchTime");
        if (str == null) {
            return -1;
        }
        C2265al.m609c("[Native record info] launchTime: %s", str);
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            if (C2265al.m605a(e)) {
                return -1;
            }
            e.printStackTrace();
            return -1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0037 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ff A[Catch:{ all -> 0x0241 }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0108 A[Catch:{ all -> 0x0241 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x011c A[Catch:{ all -> 0x0241 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0145 A[Catch:{ all -> 0x0241 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x016d A[Catch:{ all -> 0x0241 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01df A[Catch:{ all -> 0x0241 }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.tencent.bugly.crashreport.crash.CrashDetailBean m800a(android.content.Context r25, java.util.Map<java.lang.String, java.lang.String> r26, com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler r27) {
        /*
            r0 = r26
            java.lang.String r1 = "unknown"
            com.tencent.bugly.proguard.aa r2 = com.tencent.bugly.proguard.C2231aa.m457a((android.content.Context) r25)
            java.lang.String r3 = "intStateStr"
            r4 = 1
            r5 = 0
            if (r2 != 0) goto L_0x0017
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r6 = "abnormal com info not created"
            com.tencent.bugly.proguard.C2265al.m611e(r6, r2)
        L_0x0015:
            r2 = 0
            goto L_0x0034
        L_0x0017:
            java.lang.Object r2 = r0.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            if (r2 == 0) goto L_0x002c
            java.lang.String r2 = r2.trim()
            int r2 = r2.length()
            if (r2 > 0) goto L_0x002a
            goto L_0x002c
        L_0x002a:
            r2 = 1
            goto L_0x0034
        L_0x002c:
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r6 = "no intStateStr"
            com.tencent.bugly.proguard.C2265al.m611e(r6, r2)
            goto L_0x0015
        L_0x0034:
            r6 = 0
            if (r2 != 0) goto L_0x0038
            return r6
        L_0x0038:
            java.lang.Object r2 = r0.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            java.util.Map r2 = m813d(r2)
            if (r2 != 0) goto L_0x0056
            java.lang.Object[] r1 = new java.lang.Object[r4]
            int r0 = r26.size()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r1[r5] = r0
            java.lang.String r0 = "parse intSateMap fail"
            com.tencent.bugly.proguard.C2265al.m611e(r0, r1)
            return r6
        L_0x0056:
            java.lang.String r3 = "sino"
            java.lang.Object r3 = r2.get(r3)     // Catch:{ all -> 0x0241 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x0241 }
            r3.intValue()     // Catch:{ all -> 0x0241 }
            java.lang.String r3 = "sud"
            java.lang.Object r3 = r2.get(r3)     // Catch:{ all -> 0x0241 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x0241 }
            r3.intValue()     // Catch:{ all -> 0x0241 }
            java.lang.String r3 = "soVersion"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x0241 }
            r20 = r3
            java.lang.String r20 = (java.lang.String) r20     // Catch:{ all -> 0x0241 }
            boolean r3 = android.text.TextUtils.isEmpty(r20)     // Catch:{ all -> 0x0241 }
            if (r3 == 0) goto L_0x0084
            java.lang.String r0 = "error format at version"
            java.lang.Object[] r1 = new java.lang.Object[r5]     // Catch:{ all -> 0x0241 }
            com.tencent.bugly.proguard.C2265al.m611e(r0, r1)     // Catch:{ all -> 0x0241 }
            return r6
        L_0x0084:
            java.lang.String r3 = "codeMsg"
            java.lang.Object r3 = m801a(r0, r3, r1)     // Catch:{ all -> 0x0241 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x0241 }
            java.lang.String r7 = "signalName"
            java.lang.Object r7 = m801a(r0, r7, r1)     // Catch:{ all -> 0x0241 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ all -> 0x0241 }
            java.lang.String r8 = "errnoMsg"
            r0.get(r8)     // Catch:{ all -> 0x0241 }
            java.lang.String r8 = "stack"
            java.lang.Object r8 = m801a(r0, r8, r1)     // Catch:{ all -> 0x0241 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ all -> 0x0241 }
            java.lang.String r9 = "jstack"
            java.lang.Object r9 = r0.get(r9)     // Catch:{ all -> 0x0241 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ all -> 0x0241 }
            if (r9 == 0) goto L_0x00bf
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0241 }
            r10.<init>()     // Catch:{ all -> 0x0241 }
            r10.append(r8)     // Catch:{ all -> 0x0241 }
            java.lang.String r8 = "java:\n"
            r10.append(r8)     // Catch:{ all -> 0x0241 }
            r10.append(r9)     // Catch:{ all -> 0x0241 }
            java.lang.String r8 = r10.toString()     // Catch:{ all -> 0x0241 }
        L_0x00bf:
            java.lang.String r9 = "sico"
            java.lang.Object r9 = r2.get(r9)     // Catch:{ all -> 0x0241 }
            java.lang.Integer r9 = (java.lang.Integer) r9     // Catch:{ all -> 0x0241 }
            java.lang.String r10 = ")"
            java.lang.String r11 = "("
            if (r9 == 0) goto L_0x00ed
            int r9 = r9.intValue()     // Catch:{ all -> 0x0241 }
            if (r9 <= 0) goto L_0x00ed
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0241 }
            r9.<init>()     // Catch:{ all -> 0x0241 }
            r9.append(r7)     // Catch:{ all -> 0x0241 }
            r9.append(r11)     // Catch:{ all -> 0x0241 }
            r9.append(r3)     // Catch:{ all -> 0x0241 }
            r9.append(r10)     // Catch:{ all -> 0x0241 }
            java.lang.String r3 = r9.toString()     // Catch:{ all -> 0x0241 }
            java.lang.String r7 = "KERNEL"
            r12 = r3
            r15 = r7
            goto L_0x00ef
        L_0x00ed:
            r15 = r3
            r12 = r7
        L_0x00ef:
            java.lang.String r3 = "nativeLog"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x0241 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x0241 }
            if (r3 == 0) goto L_0x0108
            boolean r7 = r3.isEmpty()     // Catch:{ all -> 0x0241 }
            if (r7 != 0) goto L_0x0108
            java.lang.String r7 = "BuglyNativeLog.txt"
            byte[] r3 = com.tencent.bugly.proguard.C2273ap.m659a((java.lang.String) r3, (java.lang.String) r7)     // Catch:{ all -> 0x0241 }
            r21 = r3
            goto L_0x010a
        L_0x0108:
            r21 = r6
        L_0x010a:
            java.lang.String r3 = "sendingProcess"
            java.lang.Object r3 = m801a(r0, r3, r1)     // Catch:{ all -> 0x0241 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x0241 }
            java.lang.String r7 = "spd"
            java.lang.Object r7 = r2.get(r7)     // Catch:{ all -> 0x0241 }
            java.lang.Integer r7 = (java.lang.Integer) r7     // Catch:{ all -> 0x0241 }
            if (r7 == 0) goto L_0x0131
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0241 }
            r9.<init>()     // Catch:{ all -> 0x0241 }
            r9.append(r3)     // Catch:{ all -> 0x0241 }
            r9.append(r11)     // Catch:{ all -> 0x0241 }
            r9.append(r7)     // Catch:{ all -> 0x0241 }
            r9.append(r10)     // Catch:{ all -> 0x0241 }
            java.lang.String r3 = r9.toString()     // Catch:{ all -> 0x0241 }
        L_0x0131:
            r16 = r3
            java.lang.String r3 = "threadName"
            java.lang.Object r3 = m801a(r0, r3, r1)     // Catch:{ all -> 0x0241 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x0241 }
            java.lang.String r7 = "et"
            java.lang.Object r7 = r2.get(r7)     // Catch:{ all -> 0x0241 }
            java.lang.Integer r7 = (java.lang.Integer) r7     // Catch:{ all -> 0x0241 }
            if (r7 == 0) goto L_0x015a
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0241 }
            r9.<init>()     // Catch:{ all -> 0x0241 }
            r9.append(r3)     // Catch:{ all -> 0x0241 }
            r9.append(r11)     // Catch:{ all -> 0x0241 }
            r9.append(r7)     // Catch:{ all -> 0x0241 }
            r9.append(r10)     // Catch:{ all -> 0x0241 }
            java.lang.String r3 = r9.toString()     // Catch:{ all -> 0x0241 }
        L_0x015a:
            r9 = r3
            java.lang.String r3 = "processName"
            java.lang.Object r3 = m801a(r0, r3, r1)     // Catch:{ all -> 0x0241 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x0241 }
            java.lang.String r7 = "ep"
            java.lang.Object r7 = r2.get(r7)     // Catch:{ all -> 0x0241 }
            java.lang.Integer r7 = (java.lang.Integer) r7     // Catch:{ all -> 0x0241 }
            if (r7 == 0) goto L_0x0182
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x0241 }
            r13.<init>()     // Catch:{ all -> 0x0241 }
            r13.append(r3)     // Catch:{ all -> 0x0241 }
            r13.append(r11)     // Catch:{ all -> 0x0241 }
            r13.append(r7)     // Catch:{ all -> 0x0241 }
            r13.append(r10)     // Catch:{ all -> 0x0241 }
            java.lang.String r3 = r13.toString()     // Catch:{ all -> 0x0241 }
        L_0x0182:
            java.util.Map r22 = m806a((java.util.Map<java.lang.String, java.lang.String>) r26)     // Catch:{ all -> 0x0241 }
            java.lang.String r7 = "ets"
            java.lang.Object r7 = r2.get(r7)     // Catch:{ all -> 0x0241 }
            java.lang.Integer r7 = (java.lang.Integer) r7     // Catch:{ all -> 0x0241 }
            int r7 = r7.intValue()     // Catch:{ all -> 0x0241 }
            long r10 = (long) r7     // Catch:{ all -> 0x0241 }
            java.lang.String r7 = "etms"
            java.lang.Object r2 = r2.get(r7)     // Catch:{ all -> 0x0241 }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ all -> 0x0241 }
            int r2 = r2.intValue()     // Catch:{ all -> 0x0241 }
            long r13 = (long) r2     // Catch:{ all -> 0x0241 }
            r17 = 1000(0x3e8, double:4.94E-321)
            long r10 = r10 * r17
            long r13 = r13 / r17
            long r10 = r10 + r13
            java.lang.String r2 = "errorAddr"
            java.lang.Object r2 = m801a(r0, r2, r1)     // Catch:{ all -> 0x0241 }
            r13 = r2
            java.lang.String r13 = (java.lang.String) r13     // Catch:{ all -> 0x0241 }
            java.lang.String r14 = m803a((java.lang.String) r8)     // Catch:{ all -> 0x0241 }
            java.lang.String r2 = "sysLogPath"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ all -> 0x0241 }
            r18 = r2
            java.lang.String r18 = (java.lang.String) r18     // Catch:{ all -> 0x0241 }
            java.lang.String r2 = "jniLogPath"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ all -> 0x0241 }
            r19 = r2
            java.lang.String r19 = (java.lang.String) r19     // Catch:{ all -> 0x0241 }
            java.lang.String r2 = "tombPath"
            java.lang.Object r1 = m801a(r0, r2, r1)     // Catch:{ all -> 0x0241 }
            r17 = r1
            java.lang.String r17 = (java.lang.String) r17     // Catch:{ all -> 0x0241 }
            r23 = 0
            r24 = 0
            r7 = r27
            r8 = r3
            com.tencent.bugly.crashreport.crash.CrashDetailBean r1 = r7.packageCrashDatas(r8, r9, r10, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)     // Catch:{ all -> 0x0241 }
            if (r1 == 0) goto L_0x0240
            java.lang.String r2 = "userId"
            java.lang.String r3 = r1.f1375m     // Catch:{ all -> 0x0241 }
            java.lang.Object r2 = m801a(r0, r2, r3)     // Catch:{ all -> 0x0241 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x0241 }
            r1.f1375m = r2     // Catch:{ all -> 0x0241 }
            java.lang.String r2 = "[Native record info] userId: %s"
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch:{ all -> 0x0241 }
            java.lang.String r7 = r1.f1375m     // Catch:{ all -> 0x0241 }
            r3[r5] = r7     // Catch:{ all -> 0x0241 }
            com.tencent.bugly.proguard.C2265al.m609c(r2, r3)     // Catch:{ all -> 0x0241 }
            java.lang.String r2 = "sysLog"
            java.lang.String r3 = r1.f1385w     // Catch:{ all -> 0x0241 }
            java.lang.Object r2 = m801a(r0, r2, r3)     // Catch:{ all -> 0x0241 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x0241 }
            r1.f1385w = r2     // Catch:{ all -> 0x0241 }
            java.lang.String r2 = "appVersion"
            java.lang.String r3 = r1.f1385w     // Catch:{ all -> 0x0241 }
            java.lang.Object r2 = m801a(r0, r2, r3)     // Catch:{ all -> 0x0241 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x0241 }
            r1.f1368f = r2     // Catch:{ all -> 0x0241 }
            java.lang.String r2 = "[Native record info] appVersion: %s"
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch:{ all -> 0x0241 }
            java.lang.String r7 = r1.f1368f     // Catch:{ all -> 0x0241 }
            r3[r5] = r7     // Catch:{ all -> 0x0241 }
            com.tencent.bugly.proguard.C2265al.m609c(r2, r3)     // Catch:{ all -> 0x0241 }
            java.lang.String r2 = "isAppForeground"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ all -> 0x0241 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x0241 }
            if (r2 == 0) goto L_0x0233
            java.lang.String r3 = "[Native record info] isAppForeground: %s"
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x0241 }
            r7[r5] = r2     // Catch:{ all -> 0x0241 }
            com.tencent.bugly.proguard.C2265al.m609c(r3, r7)     // Catch:{ all -> 0x0241 }
            java.lang.String r3 = "true"
            boolean r2 = r2.equalsIgnoreCase(r3)     // Catch:{ all -> 0x0241 }
            goto L_0x0234
        L_0x0233:
            r2 = 0
        L_0x0234:
            r1.f1353R = r2     // Catch:{ all -> 0x0241 }
            long r2 = m808b((java.util.Map<java.lang.String, java.lang.String>) r26)     // Catch:{ all -> 0x0241 }
            r1.f1352Q = r2     // Catch:{ all -> 0x0241 }
            r1.f1388z = r6     // Catch:{ all -> 0x0241 }
            r1.f1373k = r4     // Catch:{ all -> 0x0241 }
        L_0x0240:
            return r1
        L_0x0241:
            r0 = move-exception
            java.lang.Object[] r1 = new java.lang.Object[r5]
            java.lang.String r2 = "error format"
            com.tencent.bugly.proguard.C2265al.m611e(r2, r1)
            r0.printStackTrace()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.C2316be.m800a(android.content.Context, java.util.Map, com.tencent.bugly.crashreport.crash.jni.NativeExceptionHandler):com.tencent.bugly.crashreport.crash.CrashDetailBean");
    }
}
