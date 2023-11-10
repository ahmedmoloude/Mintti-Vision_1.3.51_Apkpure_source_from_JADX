package com.itextpdf.text.pdf;

import java.util.HashMap;

public class GlyphList {
    private static HashMap<String, int[]> names2unicode = new HashMap<>();
    private static HashMap<Integer, String> unicode2names = new HashMap<>();

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00c5 A[SYNTHETIC, Splitter:B:37:0x00c5] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00cb A[SYNTHETIC, Splitter:B:41:0x00cb] */
    /* JADX WARNING: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    static {
        /*
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            unicode2names = r0
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            names2unicode = r0
            r0 = 0
            java.lang.String r1 = "com/itextpdf/text/pdf/fonts/glyphlist.txt"
            com.itextpdf.text.pdf.fonts.FontsResourceAnchor r2 = new com.itextpdf.text.pdf.fonts.FontsResourceAnchor     // Catch:{ Exception -> 0x00a8 }
            r2.<init>()     // Catch:{ Exception -> 0x00a8 }
            java.lang.Class r2 = r2.getClass()     // Catch:{ Exception -> 0x00a8 }
            java.lang.ClassLoader r2 = r2.getClassLoader()     // Catch:{ Exception -> 0x00a8 }
            java.io.InputStream r1 = com.itextpdf.text.p018io.StreamUtil.getResourceStream(r1, r2)     // Catch:{ Exception -> 0x00a8 }
            if (r1 == 0) goto L_0x009e
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch:{ Exception -> 0x0099, all -> 0x0094 }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0099, all -> 0x0094 }
            r3.<init>()     // Catch:{ Exception -> 0x0099, all -> 0x0094 }
        L_0x002d:
            int r4 = r1.read(r2)     // Catch:{ Exception -> 0x0099, all -> 0x0094 }
            r5 = 0
            if (r4 >= 0) goto L_0x0090
            r1.close()     // Catch:{ Exception -> 0x0099, all -> 0x0094 }
            byte[] r1 = r3.toByteArray()     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r1 = com.itextpdf.text.pdf.PdfEncodings.convertToString(r1, r0)     // Catch:{ Exception -> 0x00a8 }
            java.util.StringTokenizer r2 = new java.util.StringTokenizer     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r3 = "\r\n"
            r2.<init>(r1, r3)     // Catch:{ Exception -> 0x00a8 }
        L_0x0046:
            boolean r1 = r2.hasMoreTokens()     // Catch:{ Exception -> 0x00a8 }
            if (r1 == 0) goto L_0x00c8
            java.lang.String r1 = r2.nextToken()     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r3 = "#"
            boolean r3 = r1.startsWith(r3)     // Catch:{ Exception -> 0x00a8 }
            if (r3 == 0) goto L_0x0059
            goto L_0x0046
        L_0x0059:
            java.util.StringTokenizer r3 = new java.util.StringTokenizer     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r4 = " ;\r\n\t\f"
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x00a8 }
            boolean r1 = r3.hasMoreTokens()     // Catch:{ Exception -> 0x00a8 }
            if (r1 != 0) goto L_0x0067
            goto L_0x0046
        L_0x0067:
            java.lang.String r1 = r3.nextToken()     // Catch:{ Exception -> 0x00a8 }
            boolean r4 = r3.hasMoreTokens()     // Catch:{ Exception -> 0x00a8 }
            if (r4 != 0) goto L_0x0072
            goto L_0x0046
        L_0x0072:
            java.lang.String r3 = r3.nextToken()     // Catch:{ Exception -> 0x00a8 }
            r4 = 16
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3, r4)     // Catch:{ Exception -> 0x00a8 }
            java.util.HashMap<java.lang.Integer, java.lang.String> r4 = unicode2names     // Catch:{ Exception -> 0x00a8 }
            r4.put(r3, r1)     // Catch:{ Exception -> 0x00a8 }
            java.util.HashMap<java.lang.String, int[]> r4 = names2unicode     // Catch:{ Exception -> 0x00a8 }
            r6 = 1
            int[] r6 = new int[r6]     // Catch:{ Exception -> 0x00a8 }
            int r3 = r3.intValue()     // Catch:{ Exception -> 0x00a8 }
            r6[r5] = r3     // Catch:{ Exception -> 0x00a8 }
            r4.put(r1, r6)     // Catch:{ Exception -> 0x00a8 }
            goto L_0x0046
        L_0x0090:
            r3.write(r2, r5, r4)     // Catch:{ Exception -> 0x0099, all -> 0x0094 }
            goto L_0x002d
        L_0x0094:
            r0 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x00c9
        L_0x0099:
            r0 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x00a9
        L_0x009e:
            java.lang.String r0 = "glyphlist.txt not found as resource. (It must exist as resource in the package com.itextpdf.text.pdf.fonts)"
            java.lang.Exception r2 = new java.lang.Exception     // Catch:{ Exception -> 0x0099, all -> 0x0094 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x0099, all -> 0x0094 }
            throw r2     // Catch:{ Exception -> 0x0099, all -> 0x0094 }
        L_0x00a6:
            r1 = move-exception
            goto L_0x00c9
        L_0x00a8:
            r1 = move-exception
        L_0x00a9:
            java.io.PrintStream r2 = java.lang.System.err     // Catch:{ all -> 0x00a6 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a6 }
            r3.<init>()     // Catch:{ all -> 0x00a6 }
            java.lang.String r4 = "glyphlist.txt loading error: "
            r3.append(r4)     // Catch:{ all -> 0x00a6 }
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x00a6 }
            r3.append(r1)     // Catch:{ all -> 0x00a6 }
            java.lang.String r1 = r3.toString()     // Catch:{ all -> 0x00a6 }
            r2.println(r1)     // Catch:{ all -> 0x00a6 }
            if (r0 == 0) goto L_0x00c8
            r0.close()     // Catch:{ Exception -> 0x00c8 }
        L_0x00c8:
            return
        L_0x00c9:
            if (r0 == 0) goto L_0x00ce
            r0.close()     // Catch:{ Exception -> 0x00ce }
        L_0x00ce:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.GlyphList.<clinit>():void");
    }

    public static int[] nameToUnicode(String str) {
        int[] iArr = names2unicode.get(str);
        if (iArr == null && str.length() == 7 && str.toLowerCase().startsWith("uni")) {
            try {
                return new int[]{Integer.parseInt(str.substring(3), 16)};
            } catch (Exception unused) {
            }
        }
        return iArr;
    }

    public static String unicodeToName(int i) {
        return unicode2names.get(Integer.valueOf(i));
    }
}
