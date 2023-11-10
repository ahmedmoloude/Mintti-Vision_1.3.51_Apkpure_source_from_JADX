package com.tencent.bugly.proguard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tencent.bugly.proguard.am */
/* compiled from: BUGLY */
public final class C2266am {
    /* renamed from: a */
    public static boolean m615a(File file, String str, long j, boolean z) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, z));
            BufferedWriter bufferedWriter2 = bufferedWriter;
            boolean a = m616a(bufferedWriter2, str.toCharArray(), str.length(), file.length(), j);
            bufferedWriter.close();
            return a;
        } catch (Throwable th) {
            C2265al.m605a(th);
            return false;
        }
    }

    /* renamed from: a */
    private static boolean m616a(Writer writer, char[] cArr, int i, long j, long j2) {
        if (j >= j2) {
            return false;
        }
        if (((long) (i * 2)) + j <= j2) {
            try {
                writer.write(cArr, 0, i);
            } catch (IOException e) {
                C2265al.m605a(e);
                return false;
            }
        } else {
            writer.write(cArr, 0, (int) ((j2 - j) / 2));
        }
        writer.flush();
        return true;
    }

    /* renamed from: a */
    public static void m614a(String str, String str2, String str3, long j) {
        try {
            int i = 0;
            for (File next : m618b(str, str2, str3, j)) {
                C2265al.m609c("File %s is to be deleted.", next.getName());
                if (next.delete()) {
                    i++;
                }
            }
            C2265al.m609c("Number of overdue trace files that has deleted: ".concat(String.valueOf(i)), new Object[0]);
        } catch (Throwable th) {
            C2265al.m605a(th);
        }
    }

    /* renamed from: b */
    private static List<File> m618b(String str, final String str2, final String str3, long j) {
        ArrayList arrayList = new ArrayList();
        if (str2 == null || str3 == null) {
            C2265al.m610d("prefix %s and/or postfix %s is null.", str2, str3);
            return arrayList;
        }
        long currentTimeMillis = System.currentTimeMillis();
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            return arrayList;
        }
        try {
            File[] listFiles = file.listFiles(new FilenameFilter() {
                public final boolean accept(File file, String str) {
                    return str != null && str.startsWith(str2) && str.endsWith(str3);
                }
            });
            if (listFiles != null) {
                if (listFiles.length != 0) {
                    return m613a(listFiles, str2, str3, currentTimeMillis - j);
                }
            }
            return arrayList;
        } catch (Throwable th) {
            C2265al.m605a(th);
            return arrayList;
        }
    }

    /* renamed from: a */
    private static List<File> m613a(File[] fileArr, String str, String str2, long j) {
        ArrayList arrayList = new ArrayList();
        for (File file : fileArr) {
            long a = m612a(file.getName(), str, str2);
            if (a >= 0 && 0 <= a && a <= j) {
                arrayList.add(file);
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public static long m612a(String str, String str2, String str3) {
        if (str == null) {
            C2265al.m610d("File name is null.", new Object[0]);
            return -1;
        }
        try {
            if (str.startsWith(str2) && str.endsWith(str3)) {
                return Long.parseLong(str.substring(str2.length(), str.indexOf(str3)));
            }
        } catch (Throwable th) {
            C2265al.m605a(th);
        }
        return -1;
    }

    /* renamed from: a */
    public static boolean m617a(String str, String str2, int i) {
        boolean z = true;
        C2265al.m609c("rqdp{  sv sd start} %s", str);
        if (str2 != null && str2.trim().length() > 0) {
            File file = new File(str);
            try {
                if (!file.exists()) {
                    if (file.getParentFile() != null) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                long j = (long) i;
                if (file.length() >= j) {
                    z = false;
                }
                return m615a(file, str2, j, z);
            } catch (Throwable th) {
                if (!C2265al.m605a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return false;
    }
}
