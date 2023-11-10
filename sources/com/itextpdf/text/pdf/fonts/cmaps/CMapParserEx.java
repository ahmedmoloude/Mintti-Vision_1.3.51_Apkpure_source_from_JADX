package com.itextpdf.text.pdf.fonts.cmaps;

import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfName;
import java.io.IOException;
import java.util.ArrayList;
import kotlin.UByte;

public class CMapParserEx {
    private static final PdfName CMAPNAME = new PdfName("CMapName");
    private static final String DEF = "def";
    private static final String ENDBFCHAR = "endbfchar";
    private static final String ENDBFRANGE = "endbfrange";
    private static final String ENDCIDCHAR = "endcidchar";
    private static final String ENDCIDRANGE = "endcidrange";
    private static final int MAXLEVEL = 10;
    private static final String USECMAP = "usecmap";

    public static void parseCid(String str, AbstractCMap abstractCMap, CidLocation cidLocation) throws IOException {
        parseCid(str, abstractCMap, cidLocation, 0);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:7|8|9|10|(2:76|12)(2:13|(4:32|33|34|(4:40|(3:42|(2:44|90)(1:91)|45)|83|75)(2:46|(4:52|(3:54|(2:56|(2:58|92)(1:94))(1:93)|59)|84|75)(3:60|(2:66|88)|75)))(3:19|(2:21|78)(2:22|(2:24|79)(2:25|(2:27|80)(2:28|(3:30|31|82)(1:81))))|75))) */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0151, code lost:
        r2 = r2 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0153, code lost:
        if (r2 < 0) goto L_0x0155;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0015 */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0015 A[LOOP:0: B:7:0x0015->B:75:0x0015, LOOP_START, PHI: r2 
      PHI: (r2v1 int) = (r2v0 int), (r2v2 int) binds: [B:6:0x0013, B:75:0x0015] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC, Splitter:B:7:0x0015] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void parseCid(java.lang.String r9, com.itextpdf.text.pdf.fonts.cmaps.AbstractCMap r10, com.itextpdf.text.pdf.fonts.cmaps.CidLocation r11, int r12) throws java.io.IOException {
        /*
            r0 = 10
            if (r12 < r0) goto L_0x0005
            return
        L_0x0005:
            com.itextpdf.text.pdf.PRTokeniser r9 = r11.getLocation(r9)
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0159 }
            r0.<init>()     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfContentParser r1 = new com.itextpdf.text.pdf.PdfContentParser     // Catch:{ all -> 0x0159 }
            r1.<init>(r9)     // Catch:{ all -> 0x0159 }
            r2 = 50
        L_0x0015:
            r1.parse(r0)     // Catch:{ Exception -> 0x0151 }
            boolean r3 = r0.isEmpty()     // Catch:{ all -> 0x0159 }
            if (r3 == 0) goto L_0x0020
            goto L_0x0155
        L_0x0020:
            int r3 = r0.size()     // Catch:{ all -> 0x0159 }
            r4 = 1
            int r3 = r3 - r4
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfObject r3 = (com.itextpdf.text.pdf.PdfObject) r3     // Catch:{ all -> 0x0159 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0159 }
            r5 = 3
            r6 = 0
            if (r12 != 0) goto L_0x00a1
            int r7 = r0.size()     // Catch:{ all -> 0x0159 }
            if (r7 != r5) goto L_0x00a1
            java.lang.String r7 = "def"
            boolean r7 = r3.equals(r7)     // Catch:{ all -> 0x0159 }
            if (r7 == 0) goto L_0x00a1
            java.lang.Object r3 = r0.get(r6)     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfObject r3 = (com.itextpdf.text.pdf.PdfObject) r3     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.REGISTRY     // Catch:{ all -> 0x0159 }
            boolean r5 = r5.equals(r3)     // Catch:{ all -> 0x0159 }
            if (r5 == 0) goto L_0x005e
            java.lang.Object r3 = r0.get(r4)     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfObject r3 = (com.itextpdf.text.pdf.PdfObject) r3     // Catch:{ all -> 0x0159 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0159 }
            r10.setRegistry(r3)     // Catch:{ all -> 0x0159 }
            goto L_0x0015
        L_0x005e:
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.ORDERING     // Catch:{ all -> 0x0159 }
            boolean r5 = r5.equals(r3)     // Catch:{ all -> 0x0159 }
            if (r5 == 0) goto L_0x0074
            java.lang.Object r3 = r0.get(r4)     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfObject r3 = (com.itextpdf.text.pdf.PdfObject) r3     // Catch:{ all -> 0x0159 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0159 }
            r10.setOrdering(r3)     // Catch:{ all -> 0x0159 }
            goto L_0x0015
        L_0x0074:
            com.itextpdf.text.pdf.PdfName r5 = CMAPNAME     // Catch:{ all -> 0x0159 }
            boolean r5 = r5.equals(r3)     // Catch:{ all -> 0x0159 }
            if (r5 == 0) goto L_0x008a
            java.lang.Object r3 = r0.get(r4)     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfObject r3 = (com.itextpdf.text.pdf.PdfObject) r3     // Catch:{ all -> 0x0159 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0159 }
            r10.setName(r3)     // Catch:{ all -> 0x0159 }
            goto L_0x0015
        L_0x008a:
            com.itextpdf.text.pdf.PdfName r5 = com.itextpdf.text.pdf.PdfName.SUPPLEMENT     // Catch:{ all -> 0x0159 }
            boolean r3 = r5.equals(r3)     // Catch:{ all -> 0x0159 }
            if (r3 == 0) goto L_0x0015
            java.lang.Object r3 = r0.get(r4)     // Catch:{ Exception -> 0x0015 }
            com.itextpdf.text.pdf.PdfNumber r3 = (com.itextpdf.text.pdf.PdfNumber) r3     // Catch:{ Exception -> 0x0015 }
            int r3 = r3.intValue()     // Catch:{ Exception -> 0x0015 }
            r10.setSupplement(r3)     // Catch:{ Exception -> 0x0015 }
            goto L_0x0015
        L_0x00a1:
            java.lang.String r4 = "endcidchar"
            boolean r4 = r3.equals(r4)     // Catch:{ all -> 0x0159 }
            r7 = 2
            if (r4 != 0) goto L_0x00b2
            java.lang.String r4 = "endbfchar"
            boolean r4 = r3.equals(r4)     // Catch:{ all -> 0x0159 }
            if (r4 == 0) goto L_0x00db
        L_0x00b2:
            int r4 = r0.size()     // Catch:{ all -> 0x0159 }
            if (r4 < r5) goto L_0x00db
            int r3 = r0.size()     // Catch:{ all -> 0x0159 }
            int r3 = r3 - r7
        L_0x00bd:
            if (r6 >= r3) goto L_0x0015
            java.lang.Object r4 = r0.get(r6)     // Catch:{ all -> 0x0159 }
            boolean r4 = r4 instanceof com.itextpdf.text.pdf.PdfString     // Catch:{ all -> 0x0159 }
            if (r4 == 0) goto L_0x00d8
            java.lang.Object r4 = r0.get(r6)     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfString r4 = (com.itextpdf.text.pdf.PdfString) r4     // Catch:{ all -> 0x0159 }
            int r5 = r6 + 1
            java.lang.Object r5 = r0.get(r5)     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfObject r5 = (com.itextpdf.text.pdf.PdfObject) r5     // Catch:{ all -> 0x0159 }
            r10.addChar(r4, r5)     // Catch:{ all -> 0x0159 }
        L_0x00d8:
            int r6 = r6 + 2
            goto L_0x00bd
        L_0x00db:
            java.lang.String r4 = "endcidrange"
            boolean r4 = r3.equals(r4)     // Catch:{ all -> 0x0159 }
            if (r4 != 0) goto L_0x00eb
            java.lang.String r4 = "endbfrange"
            boolean r4 = r3.equals(r4)     // Catch:{ all -> 0x0159 }
            if (r4 == 0) goto L_0x0125
        L_0x00eb:
            int r4 = r0.size()     // Catch:{ all -> 0x0159 }
            r8 = 4
            if (r4 < r8) goto L_0x0125
            int r3 = r0.size()     // Catch:{ all -> 0x0159 }
            int r3 = r3 - r5
        L_0x00f7:
            if (r6 >= r3) goto L_0x0015
            java.lang.Object r4 = r0.get(r6)     // Catch:{ all -> 0x0159 }
            boolean r4 = r4 instanceof com.itextpdf.text.pdf.PdfString     // Catch:{ all -> 0x0159 }
            if (r4 == 0) goto L_0x0122
            int r4 = r6 + 1
            java.lang.Object r5 = r0.get(r4)     // Catch:{ all -> 0x0159 }
            boolean r5 = r5 instanceof com.itextpdf.text.pdf.PdfString     // Catch:{ all -> 0x0159 }
            if (r5 == 0) goto L_0x0122
            java.lang.Object r5 = r0.get(r6)     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfString r5 = (com.itextpdf.text.pdf.PdfString) r5     // Catch:{ all -> 0x0159 }
            java.lang.Object r4 = r0.get(r4)     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfString r4 = (com.itextpdf.text.pdf.PdfString) r4     // Catch:{ all -> 0x0159 }
            int r7 = r6 + 2
            java.lang.Object r7 = r0.get(r7)     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfObject r7 = (com.itextpdf.text.pdf.PdfObject) r7     // Catch:{ all -> 0x0159 }
            r10.addRange(r5, r4, r7)     // Catch:{ all -> 0x0159 }
        L_0x0122:
            int r6 = r6 + 3
            goto L_0x00f7
        L_0x0125:
            java.lang.String r4 = "usecmap"
            boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x0159 }
            if (r3 == 0) goto L_0x0015
            int r3 = r0.size()     // Catch:{ all -> 0x0159 }
            if (r3 != r7) goto L_0x0015
            java.lang.Object r3 = r0.get(r6)     // Catch:{ all -> 0x0159 }
            boolean r3 = r3 instanceof com.itextpdf.text.pdf.PdfName     // Catch:{ all -> 0x0159 }
            if (r3 == 0) goto L_0x0015
            java.lang.Object r3 = r0.get(r6)     // Catch:{ all -> 0x0159 }
            com.itextpdf.text.pdf.PdfObject r3 = (com.itextpdf.text.pdf.PdfObject) r3     // Catch:{ all -> 0x0159 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0159 }
            java.lang.String r3 = com.itextpdf.text.pdf.PdfName.decodeName(r3)     // Catch:{ all -> 0x0159 }
            int r4 = r12 + 1
            parseCid(r3, r10, r11, r4)     // Catch:{ all -> 0x0159 }
            goto L_0x0015
        L_0x0151:
            int r2 = r2 + -1
            if (r2 >= 0) goto L_0x0015
        L_0x0155:
            r9.close()
            return
        L_0x0159:
            r10 = move-exception
            r9.close()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.fonts.cmaps.CMapParserEx.parseCid(java.lang.String, com.itextpdf.text.pdf.fonts.cmaps.AbstractCMap, com.itextpdf.text.pdf.fonts.cmaps.CidLocation, int):void");
    }

    private static void encodeSequence(int i, byte[] bArr, char c, ArrayList<char[]> arrayList) {
        int i2 = i - 1;
        int i3 = 0;
        char c2 = 0;
        while (i3 < i2) {
            char[] cArr = arrayList.get(c2);
            byte b = bArr[i3] & UByte.MAX_VALUE;
            char c3 = cArr[b];
            if (c3 == 0 || (c3 & 32768) != 0) {
                if (c3 == 0) {
                    arrayList.add(new char[256]);
                    c3 = (char) ((arrayList.size() - 1) | 32768);
                    cArr[b] = c3;
                }
                c2 = c3 & BaseFont.CID_NEWLINE;
                i3++;
            } else {
                throw new RuntimeException(MessageLocalization.getComposedMessage("inconsistent.mapping", new Object[0]));
            }
        }
        char[] cArr2 = arrayList.get(c2);
        byte b2 = bArr[i2] & UByte.MAX_VALUE;
        if ((cArr2[b2] & 32768) == 0) {
            cArr2[b2] = c;
            return;
        }
        throw new RuntimeException(MessageLocalization.getComposedMessage("inconsistent.mapping", new Object[0]));
    }
}
