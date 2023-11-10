package androidx.test.internal.util;

import androidx.core.app.NotificationCompat;
import java.io.File;

public final class ProcSummary {
    public final String cmdline;
    public final String name;
    public final String parent;
    public final String pid;
    public final String realUid;
    public final long startTime;

    private ProcSummary(Builder builder) {
        this.name = (String) Checks.checkNotNull(builder.name);
        this.pid = (String) Checks.checkNotNull(builder.pid);
        this.realUid = (String) Checks.checkNotNull(builder.realUid);
        this.parent = (String) Checks.checkNotNull(builder.parent);
        this.cmdline = (String) Checks.checkNotNull(builder.cmdline);
        this.startTime = builder.startTime;
    }

    public static ProcSummary summarize(String str) {
        return parse(readToString(new File(new File("/proc", str), "stat")), readToString(new File(new File("/proc", str), NotificationCompat.CATEGORY_STATUS)), readToString(new File(new File("/proc", str), "cmdline")));
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0081 A[SYNTHETIC, Splitter:B:28:0x0081] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final java.lang.String readToString(java.io.File r7) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 1024(0x400, float:1.435E-42)
            char[] r2 = new char[r1]
            r3 = 0
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ RuntimeException -> 0x0059, IOException -> 0x0033 }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ RuntimeException -> 0x0059, IOException -> 0x0033 }
            r5.<init>(r7)     // Catch:{ RuntimeException -> 0x0059, IOException -> 0x0033 }
            r4.<init>(r5)     // Catch:{ RuntimeException -> 0x0059, IOException -> 0x0033 }
        L_0x0014:
            r3 = 0
            int r5 = r4.read(r2, r3, r1)     // Catch:{ RuntimeException -> 0x002e, IOException -> 0x002b, all -> 0x0028 }
            r6 = -1
            if (r5 == r6) goto L_0x0020
            r0.append(r2, r3, r5)     // Catch:{ RuntimeException -> 0x002e, IOException -> 0x002b, all -> 0x0028 }
            goto L_0x0014
        L_0x0020:
            java.lang.String r7 = r0.toString()     // Catch:{ RuntimeException -> 0x002e, IOException -> 0x002b, all -> 0x0028 }
            r4.close()     // Catch:{ IOException -> 0x0027 }
        L_0x0027:
            return r7
        L_0x0028:
            r7 = move-exception
            r3 = r4
            goto L_0x007f
        L_0x002b:
            r0 = move-exception
            r3 = r4
            goto L_0x0034
        L_0x002e:
            r0 = move-exception
            r3 = r4
            goto L_0x005a
        L_0x0031:
            r7 = move-exception
            goto L_0x007f
        L_0x0033:
            r0 = move-exception
        L_0x0034:
            androidx.test.internal.util.ProcSummary$SummaryException r1 = new androidx.test.internal.util.ProcSummary$SummaryException     // Catch:{ all -> 0x0031 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x0031 }
            java.lang.String r2 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x0031 }
            int r2 = r2.length()     // Catch:{ all -> 0x0031 }
            int r2 = r2 + 16
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0031 }
            r4.<init>(r2)     // Catch:{ all -> 0x0031 }
            java.lang.String r2 = "Could not read: "
            r4.append(r2)     // Catch:{ all -> 0x0031 }
            r4.append(r7)     // Catch:{ all -> 0x0031 }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x0031 }
            r1.<init>(r7, r0)     // Catch:{ all -> 0x0031 }
            throw r1     // Catch:{ all -> 0x0031 }
        L_0x0059:
            r0 = move-exception
        L_0x005a:
            androidx.test.internal.util.ProcSummary$SummaryException r1 = new androidx.test.internal.util.ProcSummary$SummaryException     // Catch:{ all -> 0x0031 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x0031 }
            java.lang.String r2 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x0031 }
            int r2 = r2.length()     // Catch:{ all -> 0x0031 }
            int r2 = r2 + 15
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0031 }
            r4.<init>(r2)     // Catch:{ all -> 0x0031 }
            java.lang.String r2 = "Error reading: "
            r4.append(r2)     // Catch:{ all -> 0x0031 }
            r4.append(r7)     // Catch:{ all -> 0x0031 }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x0031 }
            r1.<init>(r7, r0)     // Catch:{ all -> 0x0031 }
            throw r1     // Catch:{ all -> 0x0031 }
        L_0x007f:
            if (r3 == 0) goto L_0x0084
            r3.close()     // Catch:{ IOException -> 0x0084 }
        L_0x0084:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.test.internal.util.ProcSummary.readToString(java.io.File):java.lang.String");
    }

    public static class SummaryException extends RuntimeException {
        public SummaryException(String str, Throwable th) {
            super(str, th);
        }

        public SummaryException(String str) {
            super(str);
        }
    }

    static ProcSummary parse(String str, String str2, String str3) {
        String[] split = str.substring(str.lastIndexOf(41) + 2).split(" ", -1);
        String substring = str2.substring(str2.indexOf("\nUid:") + 1);
        return new Builder().withPid(str.substring(0, str.indexOf(32))).withName(str.substring(str.indexOf(40) + 1, str.lastIndexOf(41))).withParent(split[1]).withRealUid(substring.substring(0, substring.indexOf(10)).split("\\s", -1)[1]).withCmdline(str3.trim().replace(0, ' ')).withStartTime(Long.parseLong(split[19])).build();
    }

    static class Builder {
        /* access modifiers changed from: private */
        public String cmdline;
        /* access modifiers changed from: private */
        public String name;
        /* access modifiers changed from: private */
        public String parent;
        /* access modifiers changed from: private */
        public String pid;
        /* access modifiers changed from: private */
        public String realUid;
        /* access modifiers changed from: private */
        public long startTime;

        Builder() {
        }

        /* access modifiers changed from: package-private */
        public Builder withParent(String str) {
            try {
                Integer.parseInt(str);
                this.parent = str;
                return this;
            } catch (NumberFormatException unused) {
                String valueOf = String.valueOf(str);
                throw new IllegalArgumentException(valueOf.length() != 0 ? "not a pid: ".concat(valueOf) : new String("not a pid: "));
            }
        }

        /* access modifiers changed from: package-private */
        public Builder withCmdline(String str) {
            this.cmdline = str;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder withName(String str) {
            this.name = str;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder withPid(String str) {
            try {
                Integer.parseInt(str);
                this.pid = str;
                return this;
            } catch (NumberFormatException unused) {
                String valueOf = String.valueOf(str);
                throw new IllegalArgumentException(valueOf.length() != 0 ? "not a pid: ".concat(valueOf) : new String("not a pid: "));
            }
        }

        /* access modifiers changed from: package-private */
        public Builder withRealUid(String str) {
            try {
                Integer.parseInt(str);
                this.realUid = str;
                return this;
            } catch (NumberFormatException unused) {
                String valueOf = String.valueOf(str);
                throw new IllegalArgumentException(valueOf.length() != 0 ? "not a uid: ".concat(valueOf) : new String("not a uid: "));
            }
        }

        /* access modifiers changed from: package-private */
        public Builder withStartTime(long j) {
            this.startTime = j;
            return this;
        }

        /* access modifiers changed from: package-private */
        public ProcSummary build() {
            return new ProcSummary(this);
        }
    }

    public String toString() {
        return String.format("ProcSummary(name: '%s', cmdline: '%s', pid: '%s', parent: '%s', realUid: '%s', startTime: %d)", new Object[]{this.name, this.cmdline, this.pid, this.parent, this.realUid, Long.valueOf(this.startTime)});
    }

    public int hashCode() {
        return this.pid.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ProcSummary)) {
            return false;
        }
        ProcSummary procSummary = (ProcSummary) obj;
        if (!procSummary.name.equals(this.name) || !procSummary.pid.equals(this.pid) || !procSummary.parent.equals(this.parent) || !procSummary.realUid.equals(this.realUid) || !procSummary.cmdline.equals(this.cmdline) || procSummary.startTime != this.startTime) {
            return false;
        }
        return true;
    }
}
