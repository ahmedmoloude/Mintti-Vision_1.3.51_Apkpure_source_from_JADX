package com.itextpdf.text;

public final class Version {
    public static String AGPL = " (AGPL-version)";
    private static Version version;
    private String iText = "iText®";
    private String iTextVersion = (this.iText + " " + this.release + " ©2000-2015 iText Group NV");
    private String key = null;
    private String release = "5.5.8";

    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r1 = new java.lang.StringBuilder();
        r2 = version;
        r1.append(r2.iTextVersion);
        r1.append(AGPL);
        r2.iTextVersion = r1.toString();
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x0192 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.itextpdf.text.Version getInstance() {
        /*
            com.itextpdf.text.Version r0 = version
            if (r0 != 0) goto L_0x01ad
            com.itextpdf.text.Version r0 = new com.itextpdf.text.Version
            r0.<init>()
            version = r0
            monitor-enter(r0)
            java.lang.String r1 = "com.itextpdf.license.LicenseKey"
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r2 = "getLicenseeInfo"
            r3 = 0
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ Exception -> 0x0192 }
            java.lang.reflect.Method r2 = r1.getMethod(r2, r4)     // Catch:{ Exception -> 0x0192 }
            java.lang.Object r1 = r1.newInstance()     // Catch:{ Exception -> 0x0192 }
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0192 }
            java.lang.Object r1 = r2.invoke(r1, r4)     // Catch:{ Exception -> 0x0192 }
            java.lang.String[] r1 = (java.lang.String[]) r1     // Catch:{ Exception -> 0x0192 }
            java.lang.String[] r1 = (java.lang.String[]) r1     // Catch:{ Exception -> 0x0192 }
            r2 = 3
            r4 = r1[r2]     // Catch:{ Exception -> 0x0192 }
            if (r4 == 0) goto L_0x0041
            r4 = r1[r2]     // Catch:{ Exception -> 0x0192 }
            java.lang.String r4 = r4.trim()     // Catch:{ Exception -> 0x0192 }
            int r4 = r4.length()     // Catch:{ Exception -> 0x0192 }
            if (r4 <= 0) goto L_0x0041
            com.itextpdf.text.Version r4 = version     // Catch:{ Exception -> 0x0192 }
            r2 = r1[r2]     // Catch:{ Exception -> 0x0192 }
            r4.key = r2     // Catch:{ Exception -> 0x0192 }
            goto L_0x007c
        L_0x0041:
            com.itextpdf.text.Version r2 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r4 = "Trial version "
            r2.key = r4     // Catch:{ Exception -> 0x0192 }
            r2 = 5
            r4 = r1[r2]     // Catch:{ Exception -> 0x0192 }
            if (r4 != 0) goto L_0x0065
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0192 }
            r2.<init>()     // Catch:{ Exception -> 0x0192 }
            com.itextpdf.text.Version r4 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r5 = r4.key     // Catch:{ Exception -> 0x0192 }
            r2.append(r5)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r5 = "unauthorised"
            r2.append(r5)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0192 }
            r4.key = r2     // Catch:{ Exception -> 0x0192 }
            goto L_0x007c
        L_0x0065:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0192 }
            r4.<init>()     // Catch:{ Exception -> 0x0192 }
            com.itextpdf.text.Version r5 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r6 = r5.key     // Catch:{ Exception -> 0x0192 }
            r4.append(r6)     // Catch:{ Exception -> 0x0192 }
            r2 = r1[r2]     // Catch:{ Exception -> 0x0192 }
            r4.append(r2)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r2 = r4.toString()     // Catch:{ Exception -> 0x0192 }
            r5.key = r2     // Catch:{ Exception -> 0x0192 }
        L_0x007c:
            r2 = 4
            r4 = r1[r2]     // Catch:{ Exception -> 0x0192 }
            if (r4 == 0) goto L_0x0095
            r4 = r1[r2]     // Catch:{ Exception -> 0x0192 }
            java.lang.String r4 = r4.trim()     // Catch:{ Exception -> 0x0192 }
            int r4 = r4.length()     // Catch:{ Exception -> 0x0192 }
            if (r4 <= 0) goto L_0x0095
            com.itextpdf.text.Version r3 = version     // Catch:{ Exception -> 0x0192 }
            r1 = r1[r2]     // Catch:{ Exception -> 0x0192 }
            r3.iTextVersion = r1     // Catch:{ Exception -> 0x0192 }
            goto L_0x01a9
        L_0x0095:
            r2 = 2
            r4 = r1[r2]     // Catch:{ Exception -> 0x0192 }
            if (r4 == 0) goto L_0x0111
            r4 = r1[r2]     // Catch:{ Exception -> 0x0192 }
            java.lang.String r4 = r4.trim()     // Catch:{ Exception -> 0x0192 }
            int r4 = r4.length()     // Catch:{ Exception -> 0x0192 }
            if (r4 <= 0) goto L_0x0111
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0192 }
            r3.<init>()     // Catch:{ Exception -> 0x0192 }
            com.itextpdf.text.Version r4 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r5 = r4.iTextVersion     // Catch:{ Exception -> 0x0192 }
            r3.append(r5)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r5 = " ("
            r3.append(r5)     // Catch:{ Exception -> 0x0192 }
            r1 = r1[r2]     // Catch:{ Exception -> 0x0192 }
            r3.append(r1)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r1 = r3.toString()     // Catch:{ Exception -> 0x0192 }
            r4.iTextVersion = r1     // Catch:{ Exception -> 0x0192 }
            com.itextpdf.text.Version r1 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r1 = r1.key     // Catch:{ Exception -> 0x0192 }
            java.lang.String r1 = r1.toLowerCase()     // Catch:{ Exception -> 0x0192 }
            java.lang.String r2 = "trial"
            boolean r1 = r1.startsWith(r2)     // Catch:{ Exception -> 0x0192 }
            if (r1 != 0) goto L_0x00ec
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0192 }
            r1.<init>()     // Catch:{ Exception -> 0x0192 }
            com.itextpdf.text.Version r2 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r3 = r2.iTextVersion     // Catch:{ Exception -> 0x0192 }
            r1.append(r3)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r3 = "; licensed version)"
            r1.append(r3)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0192 }
            r2.iTextVersion = r1     // Catch:{ Exception -> 0x0192 }
            goto L_0x01a9
        L_0x00ec:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0192 }
            r1.<init>()     // Catch:{ Exception -> 0x0192 }
            com.itextpdf.text.Version r2 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r3 = r2.iTextVersion     // Catch:{ Exception -> 0x0192 }
            r1.append(r3)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r3 = "; "
            r1.append(r3)     // Catch:{ Exception -> 0x0192 }
            com.itextpdf.text.Version r3 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r3 = r3.key     // Catch:{ Exception -> 0x0192 }
            r1.append(r3)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r3 = ")"
            r1.append(r3)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0192 }
            r2.iTextVersion = r1     // Catch:{ Exception -> 0x0192 }
            goto L_0x01a9
        L_0x0111:
            r2 = r1[r3]     // Catch:{ Exception -> 0x0192 }
            if (r2 == 0) goto L_0x018a
            r2 = r1[r3]     // Catch:{ Exception -> 0x0192 }
            java.lang.String r2 = r2.trim()     // Catch:{ Exception -> 0x0192 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x0192 }
            if (r2 <= 0) goto L_0x018a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0192 }
            r2.<init>()     // Catch:{ Exception -> 0x0192 }
            com.itextpdf.text.Version r4 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r5 = r4.iTextVersion     // Catch:{ Exception -> 0x0192 }
            r2.append(r5)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r5 = " ("
            r2.append(r5)     // Catch:{ Exception -> 0x0192 }
            r1 = r1[r3]     // Catch:{ Exception -> 0x0192 }
            r2.append(r1)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x0192 }
            r4.iTextVersion = r1     // Catch:{ Exception -> 0x0192 }
            com.itextpdf.text.Version r1 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r1 = r1.key     // Catch:{ Exception -> 0x0192 }
            java.lang.String r1 = r1.toLowerCase()     // Catch:{ Exception -> 0x0192 }
            java.lang.String r2 = "trial"
            boolean r1 = r1.startsWith(r2)     // Catch:{ Exception -> 0x0192 }
            if (r1 != 0) goto L_0x0166
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0192 }
            r1.<init>()     // Catch:{ Exception -> 0x0192 }
            com.itextpdf.text.Version r2 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r3 = r2.iTextVersion     // Catch:{ Exception -> 0x0192 }
            r1.append(r3)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r3 = "; licensed version)"
            r1.append(r3)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0192 }
            r2.iTextVersion = r1     // Catch:{ Exception -> 0x0192 }
            goto L_0x01a9
        L_0x0166:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0192 }
            r1.<init>()     // Catch:{ Exception -> 0x0192 }
            com.itextpdf.text.Version r2 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r3 = r2.iTextVersion     // Catch:{ Exception -> 0x0192 }
            r1.append(r3)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r3 = "; "
            r1.append(r3)     // Catch:{ Exception -> 0x0192 }
            com.itextpdf.text.Version r3 = version     // Catch:{ Exception -> 0x0192 }
            java.lang.String r3 = r3.key     // Catch:{ Exception -> 0x0192 }
            r1.append(r3)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r3 = ")"
            r1.append(r3)     // Catch:{ Exception -> 0x0192 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0192 }
            r2.iTextVersion = r1     // Catch:{ Exception -> 0x0192 }
            goto L_0x01a9
        L_0x018a:
            java.lang.Exception r1 = new java.lang.Exception     // Catch:{ Exception -> 0x0192 }
            r1.<init>()     // Catch:{ Exception -> 0x0192 }
            throw r1     // Catch:{ Exception -> 0x0192 }
        L_0x0190:
            r1 = move-exception
            goto L_0x01ab
        L_0x0192:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0190 }
            r1.<init>()     // Catch:{ all -> 0x0190 }
            com.itextpdf.text.Version r2 = version     // Catch:{ all -> 0x0190 }
            java.lang.String r3 = r2.iTextVersion     // Catch:{ all -> 0x0190 }
            r1.append(r3)     // Catch:{ all -> 0x0190 }
            java.lang.String r3 = AGPL     // Catch:{ all -> 0x0190 }
            r1.append(r3)     // Catch:{ all -> 0x0190 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0190 }
            r2.iTextVersion = r1     // Catch:{ all -> 0x0190 }
        L_0x01a9:
            monitor-exit(r0)     // Catch:{ all -> 0x0190 }
            goto L_0x01ad
        L_0x01ab:
            monitor-exit(r0)     // Catch:{ all -> 0x0190 }
            throw r1
        L_0x01ad:
            com.itextpdf.text.Version r0 = version
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.Version.getInstance():com.itextpdf.text.Version");
    }

    public String getProduct() {
        return this.iText;
    }

    public String getRelease() {
        return this.release;
    }

    public String getVersion() {
        return this.iTextVersion;
    }

    public String getKey() {
        return this.key;
    }

    public static boolean isAGPLVersion() {
        return getInstance().getVersion().indexOf(AGPL) > 0;
    }
}
