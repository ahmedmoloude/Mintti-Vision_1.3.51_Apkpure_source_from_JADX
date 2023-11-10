package com.itextpdf.text.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.fonts.FontsResourceAnchor;
import java.io.IOException;
import java.util.HashMap;

class Type1Font extends BaseFont {
    private static final int[] PFB_TYPES = {1, 2, 1};
    private static FontsResourceAnchor resourceAnchor;
    private int Ascender = 800;
    private int CapHeight = 700;
    private HashMap<Object, Object[]> CharMetrics = new HashMap<>();
    private String CharacterSet;
    private int Descender = -200;
    private String EncodingScheme = "FontSpecific";
    private String FamilyName;
    private String FontName;
    private String FullName;
    private boolean IsFixedPitch = false;
    private float ItalicAngle = 0.0f;
    private HashMap<String, Object[]> KernPairs = new HashMap<>();
    private int StdHW;
    private int StdVW = 80;
    private int UnderlinePosition = -100;
    private int UnderlineThickness = 50;
    private String Weight = "";
    private int XHeight = 480;
    private boolean builtinFont = false;
    private String fileName;
    private int llx = -50;
    private int lly = -200;
    protected byte[] pfb;
    private int urx = 1000;
    private int ury = 900;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: com.itextpdf.text.pdf.RandomAccessFileOrArray} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: com.itextpdf.text.pdf.RandomAccessFileOrArray} */
    /* JADX WARNING: type inference failed for: r1v5, types: [com.itextpdf.text.pdf.RandomAccessFileOrArray] */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r1v14, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: type inference failed for: r1v17 */
    /* JADX WARNING: Can't wrap try/catch for region: R(11:18|19|(2:20|(1:100)(2:41|42))|22|(2:24|25)|26|27|28|29|30|31) */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00d9, code lost:
        r4 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x00c6 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00dc A[SYNTHETIC, Splitter:B:37:0x00dc] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00ff A[SYNTHETIC, Splitter:B:49:0x00ff] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0199 A[SYNTHETIC, Splitter:B:94:0x0199] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    Type1Font(java.lang.String r4, java.lang.String r5, boolean r6, byte[] r7, byte[] r8, boolean r9) throws com.itextpdf.text.DocumentException, java.io.IOException {
        /*
            r3 = this;
            r3.<init>()
            java.lang.String r0 = ""
            r3.Weight = r0
            r0 = 0
            r3.ItalicAngle = r0
            r0 = 0
            r3.IsFixedPitch = r0
            r1 = -50
            r3.llx = r1
            r1 = -200(0xffffffffffffff38, float:NaN)
            r3.lly = r1
            r2 = 1000(0x3e8, float:1.401E-42)
            r3.urx = r2
            r2 = 900(0x384, float:1.261E-42)
            r3.ury = r2
            r2 = -100
            r3.UnderlinePosition = r2
            r2 = 50
            r3.UnderlineThickness = r2
            java.lang.String r2 = "FontSpecific"
            r3.EncodingScheme = r2
            r2 = 700(0x2bc, float:9.81E-43)
            r3.CapHeight = r2
            r2 = 480(0x1e0, float:6.73E-43)
            r3.XHeight = r2
            r2 = 800(0x320, float:1.121E-42)
            r3.Ascender = r2
            r3.Descender = r1
            r1 = 80
            r3.StdVW = r1
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            r3.CharMetrics = r1
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            r3.KernPairs = r1
            r3.builtinFont = r0
            if (r6 == 0) goto L_0x0061
            if (r7 == 0) goto L_0x0061
            if (r8 == 0) goto L_0x0052
            goto L_0x0061
        L_0x0052:
            com.itextpdf.text.DocumentException r4 = new com.itextpdf.text.DocumentException
            java.lang.Object[] r5 = new java.lang.Object[r0]
            java.lang.String r6 = "two.byte.arrays.are.needed.if.the.type1.font.is.embedded"
            java.lang.String r5 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r6, (java.lang.Object[]) r5)
            r4.<init>((java.lang.String) r5)
            throw r4
        L_0x0061:
            if (r6 == 0) goto L_0x0067
            if (r7 == 0) goto L_0x0067
            r3.pfb = r8
        L_0x0067:
            r3.encoding = r5
            r3.embedded = r6
            r3.fileName = r4
            r3.fontType = r0
            java.util.HashMap r6 = BuiltinFonts14
            boolean r6 = r6.containsKey(r4)
            java.lang.String r8 = ".afm"
            r1 = 0
            r2 = 1
            if (r6 == 0) goto L_0x0103
            r3.embedded = r0
            r3.builtinFont = r2
            r6 = 1024(0x400, float:1.435E-42)
            byte[] r6 = new byte[r6]
            com.itextpdf.text.pdf.fonts.FontsResourceAnchor r7 = resourceAnchor     // Catch:{ all -> 0x00fc }
            if (r7 != 0) goto L_0x008e
            com.itextpdf.text.pdf.fonts.FontsResourceAnchor r7 = new com.itextpdf.text.pdf.fonts.FontsResourceAnchor     // Catch:{ all -> 0x00fc }
            r7.<init>()     // Catch:{ all -> 0x00fc }
            resourceAnchor = r7     // Catch:{ all -> 0x00fc }
        L_0x008e:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x00fc }
            r7.<init>()     // Catch:{ all -> 0x00fc }
            java.lang.String r9 = "com/itextpdf/text/pdf/fonts/"
            r7.append(r9)     // Catch:{ all -> 0x00fc }
            r7.append(r4)     // Catch:{ all -> 0x00fc }
            r7.append(r8)     // Catch:{ all -> 0x00fc }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00fc }
            com.itextpdf.text.pdf.fonts.FontsResourceAnchor r8 = resourceAnchor     // Catch:{ all -> 0x00fc }
            java.lang.Class r8 = r8.getClass()     // Catch:{ all -> 0x00fc }
            java.lang.ClassLoader r8 = r8.getClassLoader()     // Catch:{ all -> 0x00fc }
            java.io.InputStream r7 = com.itextpdf.text.p018io.StreamUtil.getResourceStream(r7, r8)     // Catch:{ all -> 0x00fc }
            if (r7 == 0) goto L_0x00e7
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x00e4 }
            r4.<init>()     // Catch:{ all -> 0x00e4 }
        L_0x00b7:
            int r8 = r7.read(r6)     // Catch:{ all -> 0x00e4 }
            if (r8 >= 0) goto L_0x00e0
            byte[] r4 = r4.toByteArray()     // Catch:{ all -> 0x00e4 }
            if (r7 == 0) goto L_0x00c6
            r7.close()     // Catch:{ Exception -> 0x00c6 }
        L_0x00c6:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r6 = new com.itextpdf.text.pdf.RandomAccessFileOrArray     // Catch:{ all -> 0x00d9 }
            r6.<init>((byte[]) r4)     // Catch:{ all -> 0x00d9 }
            r3.process(r6)     // Catch:{ all -> 0x00d6 }
            r6.close()     // Catch:{ Exception -> 0x00d3 }
            goto L_0x0163
        L_0x00d3:
            goto L_0x0163
        L_0x00d6:
            r4 = move-exception
            r1 = r6
            goto L_0x00da
        L_0x00d9:
            r4 = move-exception
        L_0x00da:
            if (r1 == 0) goto L_0x00df
            r1.close()     // Catch:{ Exception -> 0x00df }
        L_0x00df:
            throw r4
        L_0x00e0:
            r4.write(r6, r0, r8)     // Catch:{ all -> 0x00e4 }
            goto L_0x00b7
        L_0x00e4:
            r4 = move-exception
            r1 = r7
            goto L_0x00fd
        L_0x00e7:
            java.lang.String r5 = "1.not.found.as.resource"
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ all -> 0x00e4 }
            r6[r0] = r4     // Catch:{ all -> 0x00e4 }
            java.lang.String r4 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r5, (java.lang.Object[]) r6)     // Catch:{ all -> 0x00e4 }
            java.io.PrintStream r5 = java.lang.System.err     // Catch:{ all -> 0x00e4 }
            r5.println(r4)     // Catch:{ all -> 0x00e4 }
            com.itextpdf.text.DocumentException r5 = new com.itextpdf.text.DocumentException     // Catch:{ all -> 0x00e4 }
            r5.<init>((java.lang.String) r4)     // Catch:{ all -> 0x00e4 }
            throw r5     // Catch:{ all -> 0x00e4 }
        L_0x00fc:
            r4 = move-exception
        L_0x00fd:
            if (r1 == 0) goto L_0x0102
            r1.close()     // Catch:{ Exception -> 0x0102 }
        L_0x0102:
            throw r4
        L_0x0103:
            java.lang.String r6 = r4.toLowerCase()
            boolean r6 = r6.endsWith(r8)
            if (r6 == 0) goto L_0x012c
            if (r7 != 0) goto L_0x0118
            com.itextpdf.text.pdf.RandomAccessFileOrArray r6 = new com.itextpdf.text.pdf.RandomAccessFileOrArray     // Catch:{ all -> 0x0125 }
            boolean r7 = com.itextpdf.text.Document.plainRandomAccess     // Catch:{ all -> 0x0125 }
            r6.<init>(r4, r9, r7)     // Catch:{ all -> 0x0125 }
            r1 = r6
            goto L_0x011e
        L_0x0118:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r4 = new com.itextpdf.text.pdf.RandomAccessFileOrArray     // Catch:{ all -> 0x0125 }
            r4.<init>((byte[]) r7)     // Catch:{ all -> 0x0125 }
            r1 = r4
        L_0x011e:
            r3.process(r1)     // Catch:{ all -> 0x0125 }
            r1.close()     // Catch:{ Exception -> 0x00d3 }
            goto L_0x0163
        L_0x0125:
            r4 = move-exception
            if (r1 == 0) goto L_0x012b
            r1.close()     // Catch:{ Exception -> 0x012b }
        L_0x012b:
            throw r4
        L_0x012c:
            java.lang.String r6 = r4.toLowerCase()
            java.lang.String r8 = ".pfm"
            boolean r6 = r6.endsWith(r8)
            if (r6 == 0) goto L_0x019d
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0196 }
            r6.<init>()     // Catch:{ all -> 0x0196 }
            if (r7 != 0) goto L_0x0148
            com.itextpdf.text.pdf.RandomAccessFileOrArray r7 = new com.itextpdf.text.pdf.RandomAccessFileOrArray     // Catch:{ all -> 0x0196 }
            boolean r8 = com.itextpdf.text.Document.plainRandomAccess     // Catch:{ all -> 0x0196 }
            r7.<init>(r4, r9, r8)     // Catch:{ all -> 0x0196 }
            r1 = r7
            goto L_0x014e
        L_0x0148:
            com.itextpdf.text.pdf.RandomAccessFileOrArray r4 = new com.itextpdf.text.pdf.RandomAccessFileOrArray     // Catch:{ all -> 0x0196 }
            r4.<init>((byte[]) r7)     // Catch:{ all -> 0x0196 }
            r1 = r4
        L_0x014e:
            com.itextpdf.text.pdf.Pfm2afm.convert(r1, r6)     // Catch:{ all -> 0x0196 }
            r1.close()     // Catch:{ all -> 0x0196 }
            com.itextpdf.text.pdf.RandomAccessFileOrArray r4 = new com.itextpdf.text.pdf.RandomAccessFileOrArray     // Catch:{ all -> 0x0196 }
            byte[] r6 = r6.toByteArray()     // Catch:{ all -> 0x0196 }
            r4.<init>((byte[]) r6)     // Catch:{ all -> 0x0196 }
            r3.process(r4)     // Catch:{ all -> 0x0192 }
            r4.close()     // Catch:{ Exception -> 0x00d3 }
        L_0x0163:
            java.lang.String r4 = r3.EncodingScheme
            java.lang.String r4 = r4.trim()
            r3.EncodingScheme = r4
            java.lang.String r6 = "AdobeStandardEncoding"
            boolean r4 = r4.equals(r6)
            if (r4 != 0) goto L_0x017d
            java.lang.String r4 = r3.EncodingScheme
            java.lang.String r6 = "StandardEncoding"
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L_0x017f
        L_0x017d:
            r3.fontSpecific = r0
        L_0x017f:
            java.lang.String r4 = r3.encoding
            java.lang.String r6 = "#"
            boolean r4 = r4.startsWith(r6)
            if (r4 != 0) goto L_0x018e
            java.lang.String r4 = " "
            com.itextpdf.text.pdf.PdfEncodings.convertToBytes((java.lang.String) r4, (java.lang.String) r5)
        L_0x018e:
            r3.createEncoding()
            return
        L_0x0192:
            r5 = move-exception
            r1 = r4
            r4 = r5
            goto L_0x0197
        L_0x0196:
            r4 = move-exception
        L_0x0197:
            if (r1 == 0) goto L_0x019c
            r1.close()     // Catch:{ Exception -> 0x019c }
        L_0x019c:
            throw r4
        L_0x019d:
            com.itextpdf.text.DocumentException r5 = new com.itextpdf.text.DocumentException
            java.lang.Object[] r6 = new java.lang.Object[r2]
            r6[r0] = r4
            java.lang.String r4 = "1.is.not.an.afm.or.pfm.font.file"
            java.lang.String r4 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r4, (java.lang.Object[]) r6)
            r5.<init>((java.lang.String) r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.Type1Font.<init>(java.lang.String, java.lang.String, boolean, byte[], byte[], boolean):void");
    }

    /* access modifiers changed from: package-private */
    public int getRawWidth(int i, String str) {
        Object[] objArr;
        if (str == null) {
            objArr = this.CharMetrics.get(Integer.valueOf(i));
        } else if (str.equals(BaseFont.notdef)) {
            return 0;
        } else {
            objArr = this.CharMetrics.get(str);
        }
        if (objArr != null) {
            return ((Integer) objArr[1]).intValue();
        }
        return 0;
    }

    public int getKerning(int i, int i2) {
        String unicodeToName;
        Object[] objArr;
        String unicodeToName2 = GlyphList.unicodeToName(i);
        if (unicodeToName2 == null || (unicodeToName = GlyphList.unicodeToName(i2)) == null || (objArr = this.KernPairs.get(unicodeToName2)) == null) {
            return 0;
        }
        for (int i3 = 0; i3 < objArr.length; i3 += 2) {
            if (unicodeToName.equals(objArr[i3])) {
                return ((Integer) objArr[i3 + 1]).intValue();
            }
        }
        return 0;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void process(com.itextpdf.text.pdf.RandomAccessFileOrArray r15) throws com.itextpdf.text.DocumentException, java.io.IOException {
        /*
            r14 = this;
        L_0x0000:
            java.lang.String r0 = r15.readLine()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x01a3
            java.util.StringTokenizer r3 = new java.util.StringTokenizer
            java.lang.String r4 = " ,\n\r\t\f"
            r3.<init>(r0, r4)
            boolean r0 = r3.hasMoreTokens()
            if (r0 != 0) goto L_0x0016
            goto L_0x0000
        L_0x0016:
            java.lang.String r0 = r3.nextToken()
            java.lang.String r4 = "FontName"
            boolean r4 = r0.equals(r4)
            java.lang.String r5 = "ÿ"
            if (r4 == 0) goto L_0x0030
            java.lang.String r0 = r3.nextToken(r5)
            java.lang.String r0 = r0.substring(r2)
            r14.FontName = r0
            goto L_0x0000
        L_0x0030:
            java.lang.String r4 = "FullName"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x0043
            java.lang.String r0 = r3.nextToken(r5)
            java.lang.String r0 = r0.substring(r2)
            r14.FullName = r0
            goto L_0x0000
        L_0x0043:
            java.lang.String r4 = "FamilyName"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x0056
            java.lang.String r0 = r3.nextToken(r5)
            java.lang.String r0 = r0.substring(r2)
            r14.FamilyName = r0
            goto L_0x0000
        L_0x0056:
            java.lang.String r4 = "Weight"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x0069
            java.lang.String r0 = r3.nextToken(r5)
            java.lang.String r0 = r0.substring(r2)
            r14.Weight = r0
            goto L_0x0000
        L_0x0069:
            java.lang.String r4 = "ItalicAngle"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x007c
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            r14.ItalicAngle = r0
            goto L_0x0000
        L_0x007c:
            java.lang.String r4 = "IsFixedPitch"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x0093
            java.lang.String r0 = r3.nextToken()
            java.lang.String r1 = "true"
            boolean r0 = r0.equals(r1)
            r14.IsFixedPitch = r0
            goto L_0x0000
        L_0x0093:
            java.lang.String r4 = "CharacterSet"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x00a7
            java.lang.String r0 = r3.nextToken(r5)
            java.lang.String r0 = r0.substring(r2)
            r14.CharacterSet = r0
            goto L_0x0000
        L_0x00a7:
            java.lang.String r4 = "FontBBox"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x00dd
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            int r0 = (int) r0
            r14.llx = r0
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            int r0 = (int) r0
            r14.lly = r0
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            int r0 = (int) r0
            r14.urx = r0
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            int r0 = (int) r0
            r14.ury = r0
            goto L_0x0000
        L_0x00dd:
            java.lang.String r4 = "UnderlinePosition"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x00f2
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            int r0 = (int) r0
            r14.UnderlinePosition = r0
            goto L_0x0000
        L_0x00f2:
            java.lang.String r4 = "UnderlineThickness"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x0107
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            int r0 = (int) r0
            r14.UnderlineThickness = r0
            goto L_0x0000
        L_0x0107:
            java.lang.String r4 = "EncodingScheme"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x011b
            java.lang.String r0 = r3.nextToken(r5)
            java.lang.String r0 = r0.substring(r2)
            r14.EncodingScheme = r0
            goto L_0x0000
        L_0x011b:
            java.lang.String r4 = "CapHeight"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x0130
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            int r0 = (int) r0
            r14.CapHeight = r0
            goto L_0x0000
        L_0x0130:
            java.lang.String r4 = "XHeight"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x0145
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            int r0 = (int) r0
            r14.XHeight = r0
            goto L_0x0000
        L_0x0145:
            java.lang.String r4 = "Ascender"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x015a
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            int r0 = (int) r0
            r14.Ascender = r0
            goto L_0x0000
        L_0x015a:
            java.lang.String r4 = "Descender"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x016f
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            int r0 = (int) r0
            r14.Descender = r0
            goto L_0x0000
        L_0x016f:
            java.lang.String r4 = "StdHW"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x0184
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            int r0 = (int) r0
            r14.StdHW = r0
            goto L_0x0000
        L_0x0184:
            java.lang.String r4 = "StdVW"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x0199
            java.lang.String r0 = r3.nextToken()
            float r0 = java.lang.Float.parseFloat(r0)
            int r0 = (int) r0
            r14.StdVW = r0
            goto L_0x0000
        L_0x0199:
            java.lang.String r3 = "StartCharMetrics"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0000
            r0 = 1
            goto L_0x01a4
        L_0x01a3:
            r0 = 0
        L_0x01a4:
            if (r0 == 0) goto L_0x0366
        L_0x01a6:
            java.lang.String r3 = r15.readLine()
            r4 = 2
            if (r3 == 0) goto L_0x027d
            java.util.StringTokenizer r5 = new java.util.StringTokenizer
            r5.<init>(r3)
            boolean r6 = r5.hasMoreTokens()
            if (r6 != 0) goto L_0x01b9
            goto L_0x01a6
        L_0x01b9:
            java.lang.String r5 = r5.nextToken()
            java.lang.String r6 = "EndCharMetrics"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x01c8
            r0 = 0
            goto L_0x027d
        L_0x01c8:
            r5 = -1
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r6 = 250(0xfa, float:3.5E-43)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r7 = 0
            java.util.StringTokenizer r8 = new java.util.StringTokenizer
            java.lang.String r9 = ";"
            r8.<init>(r3, r9)
            java.lang.String r3 = ""
        L_0x01dd:
            boolean r9 = r8.hasMoreTokens()
            r10 = 3
            r11 = 4
            if (r9 == 0) goto L_0x0261
            java.util.StringTokenizer r9 = new java.util.StringTokenizer
            java.lang.String r12 = r8.nextToken()
            r9.<init>(r12)
            boolean r12 = r9.hasMoreTokens()
            if (r12 != 0) goto L_0x01f5
            goto L_0x01dd
        L_0x01f5:
            java.lang.String r12 = r9.nextToken()
            java.lang.String r13 = "C"
            boolean r13 = r12.equals(r13)
            if (r13 == 0) goto L_0x020a
            java.lang.String r5 = r9.nextToken()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            goto L_0x01dd
        L_0x020a:
            java.lang.String r13 = "WX"
            boolean r13 = r12.equals(r13)
            if (r13 == 0) goto L_0x0220
            java.lang.String r6 = r9.nextToken()
            float r6 = java.lang.Float.parseFloat(r6)
            int r6 = (int) r6
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            goto L_0x01dd
        L_0x0220:
            java.lang.String r13 = "N"
            boolean r13 = r12.equals(r13)
            if (r13 == 0) goto L_0x022d
            java.lang.String r3 = r9.nextToken()
            goto L_0x01dd
        L_0x022d:
            java.lang.String r13 = "B"
            boolean r12 = r12.equals(r13)
            if (r12 == 0) goto L_0x01dd
            int[] r7 = new int[r11]
            java.lang.String r11 = r9.nextToken()
            int r11 = java.lang.Integer.parseInt(r11)
            r7[r1] = r11
            java.lang.String r11 = r9.nextToken()
            int r11 = java.lang.Integer.parseInt(r11)
            r7[r2] = r11
            java.lang.String r11 = r9.nextToken()
            int r11 = java.lang.Integer.parseInt(r11)
            r7[r4] = r11
            java.lang.String r9 = r9.nextToken()
            int r9 = java.lang.Integer.parseInt(r9)
            r7[r10] = r9
            goto L_0x01dd
        L_0x0261:
            java.lang.Object[] r8 = new java.lang.Object[r11]
            r8[r1] = r5
            r8[r2] = r6
            r8[r4] = r3
            r8[r10] = r7
            int r4 = r5.intValue()
            if (r4 < 0) goto L_0x0276
            java.util.HashMap<java.lang.Object, java.lang.Object[]> r4 = r14.CharMetrics
            r4.put(r5, r8)
        L_0x0276:
            java.util.HashMap<java.lang.Object, java.lang.Object[]> r4 = r14.CharMetrics
            r4.put(r3, r8)
            goto L_0x01a6
        L_0x027d:
            if (r0 != 0) goto L_0x0354
            java.util.HashMap<java.lang.Object, java.lang.Object[]> r3 = r14.CharMetrics
            java.lang.String r5 = "nonbreakingspace"
            boolean r3 = r3.containsKey(r5)
            if (r3 != 0) goto L_0x029b
            java.util.HashMap<java.lang.Object, java.lang.Object[]> r3 = r14.CharMetrics
            java.lang.String r6 = "space"
            java.lang.Object r3 = r3.get(r6)
            java.lang.Object[] r3 = (java.lang.Object[]) r3
            if (r3 == 0) goto L_0x029b
            java.util.HashMap<java.lang.Object, java.lang.Object[]> r6 = r14.CharMetrics
            r6.put(r5, r3)
        L_0x029b:
            java.lang.String r3 = r15.readLine()
            if (r3 == 0) goto L_0x02c3
            java.util.StringTokenizer r5 = new java.util.StringTokenizer
            r5.<init>(r3)
            boolean r3 = r5.hasMoreTokens()
            if (r3 != 0) goto L_0x02ad
            goto L_0x029b
        L_0x02ad:
            java.lang.String r3 = r5.nextToken()
            java.lang.String r5 = "EndFontMetrics"
            boolean r5 = r3.equals(r5)
            if (r5 == 0) goto L_0x02ba
            return
        L_0x02ba:
            java.lang.String r5 = "StartKernPairs"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x029b
            r0 = 1
        L_0x02c3:
            if (r0 == 0) goto L_0x0342
        L_0x02c5:
            java.lang.String r3 = r15.readLine()
            if (r3 == 0) goto L_0x032a
            java.util.StringTokenizer r5 = new java.util.StringTokenizer
            r5.<init>(r3)
            boolean r3 = r5.hasMoreTokens()
            if (r3 != 0) goto L_0x02d7
            goto L_0x02c5
        L_0x02d7:
            java.lang.String r3 = r5.nextToken()
            java.lang.String r6 = "KPX"
            boolean r6 = r3.equals(r6)
            if (r6 == 0) goto L_0x0321
            java.lang.String r3 = r5.nextToken()
            java.lang.String r6 = r5.nextToken()
            java.lang.String r5 = r5.nextToken()
            float r5 = java.lang.Float.parseFloat(r5)
            int r5 = (int) r5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.util.HashMap<java.lang.String, java.lang.Object[]> r7 = r14.KernPairs
            java.lang.Object r7 = r7.get(r3)
            java.lang.Object[] r7 = (java.lang.Object[]) r7
            if (r7 != 0) goto L_0x030e
            java.util.HashMap<java.lang.String, java.lang.Object[]> r7 = r14.KernPairs
            java.lang.Object[] r8 = new java.lang.Object[r4]
            r8[r1] = r6
            r8[r2] = r5
            r7.put(r3, r8)
            goto L_0x02c5
        L_0x030e:
            int r8 = r7.length
            int r9 = r8 + 2
            java.lang.Object[] r9 = new java.lang.Object[r9]
            java.lang.System.arraycopy(r7, r1, r9, r1, r8)
            r9[r8] = r6
            int r8 = r8 + r2
            r9[r8] = r5
            java.util.HashMap<java.lang.String, java.lang.Object[]> r5 = r14.KernPairs
            r5.put(r3, r9)
            goto L_0x02c5
        L_0x0321:
            java.lang.String r5 = "EndKernPairs"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x02c5
            r0 = 0
        L_0x032a:
            if (r0 != 0) goto L_0x0330
            r15.close()
            return
        L_0x0330:
            com.itextpdf.text.DocumentException r15 = new com.itextpdf.text.DocumentException
            java.lang.Object[] r0 = new java.lang.Object[r2]
            java.lang.String r2 = r14.fileName
            r0[r1] = r2
            java.lang.String r1 = "missing.endkernpairs.in.1"
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r1, (java.lang.Object[]) r0)
            r15.<init>((java.lang.String) r0)
            throw r15
        L_0x0342:
            com.itextpdf.text.DocumentException r15 = new com.itextpdf.text.DocumentException
            java.lang.Object[] r0 = new java.lang.Object[r2]
            java.lang.String r2 = r14.fileName
            r0[r1] = r2
            java.lang.String r1 = "missing.endfontmetrics.in.1"
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r1, (java.lang.Object[]) r0)
            r15.<init>((java.lang.String) r0)
            throw r15
        L_0x0354:
            com.itextpdf.text.DocumentException r15 = new com.itextpdf.text.DocumentException
            java.lang.Object[] r0 = new java.lang.Object[r2]
            java.lang.String r2 = r14.fileName
            r0[r1] = r2
            java.lang.String r1 = "missing.endcharmetrics.in.1"
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r1, (java.lang.Object[]) r0)
            r15.<init>((java.lang.String) r0)
            throw r15
        L_0x0366:
            com.itextpdf.text.DocumentException r15 = new com.itextpdf.text.DocumentException
            java.lang.Object[] r0 = new java.lang.Object[r2]
            java.lang.String r2 = r14.fileName
            r0[r1] = r2
            java.lang.String r1 = "missing.startcharmetrics.in.1"
            java.lang.String r0 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r1, (java.lang.Object[]) r0)
            r15.<init>((java.lang.String) r0)
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.Type1Font.process(com.itextpdf.text.pdf.RandomAccessFileOrArray):void");
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [com.itextpdf.text.pdf.PdfStream, com.itextpdf.text.pdf.RandomAccessFileOrArray] */
    public PdfStream getFullFontStream() throws DocumentException {
        RandomAccessFileOrArray randomAccessFileOrArray;
        ? r1 = 0;
        if (this.builtinFont || !this.embedded) {
            return r1;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String str = this.fileName;
            sb.append(str.substring(0, str.length() - 3));
            sb.append("pfb");
            String sb2 = sb.toString();
            byte[] bArr = this.pfb;
            if (bArr == null) {
                randomAccessFileOrArray = new RandomAccessFileOrArray(sb2, true, Document.plainRandomAccess);
            } else {
                randomAccessFileOrArray = new RandomAccessFileOrArray(bArr);
            }
            byte[] bArr2 = new byte[(((int) randomAccessFileOrArray.length()) - 18)];
            int[] iArr = new int[3];
            int i = 0;
            int i2 = 0;
            while (i < 3) {
                if (randomAccessFileOrArray.read() != 128) {
                    throw new DocumentException(MessageLocalization.getComposedMessage("start.marker.missing.in.1", sb2));
                } else if (randomAccessFileOrArray.read() == PFB_TYPES[i]) {
                    int read = randomAccessFileOrArray.read() + (randomAccessFileOrArray.read() << 8) + (randomAccessFileOrArray.read() << 16) + (randomAccessFileOrArray.read() << 24);
                    iArr[i] = read;
                    while (read != 0) {
                        int read2 = randomAccessFileOrArray.read(bArr2, i2, read);
                        if (read2 >= 0) {
                            i2 += read2;
                            read -= read2;
                        } else {
                            throw new DocumentException(MessageLocalization.getComposedMessage("premature.end.in.1", sb2));
                        }
                    }
                    i++;
                } else {
                    throw new DocumentException(MessageLocalization.getComposedMessage("incorrect.segment.type.in.1", sb2));
                }
            }
            BaseFont.StreamFont streamFont = new BaseFont.StreamFont(bArr2, iArr, this.compressionLevel);
            try {
                randomAccessFileOrArray.close();
            } catch (Exception unused) {
            }
            return streamFont;
        } catch (Exception e) {
            throw new DocumentException(e);
        } catch (Throwable th) {
            if (r1 != 0) {
                try {
                    r1.close();
                } catch (Exception unused2) {
                }
            }
            throw th;
        }
    }

    private PdfDictionary getFontDescriptor(PdfIndirectReference pdfIndirectReference) {
        if (this.builtinFont) {
            return null;
        }
        PdfDictionary pdfDictionary = new PdfDictionary(PdfName.FONTDESCRIPTOR);
        pdfDictionary.put(PdfName.ASCENT, new PdfNumber(this.Ascender));
        pdfDictionary.put(PdfName.CAPHEIGHT, new PdfNumber(this.CapHeight));
        pdfDictionary.put(PdfName.DESCENT, new PdfNumber(this.Descender));
        pdfDictionary.put(PdfName.FONTBBOX, new PdfRectangle((float) this.llx, (float) this.lly, (float) this.urx, (float) this.ury));
        pdfDictionary.put(PdfName.FONTNAME, new PdfName(this.FontName));
        pdfDictionary.put(PdfName.ITALICANGLE, new PdfNumber(this.ItalicAngle));
        pdfDictionary.put(PdfName.STEMV, new PdfNumber(this.StdVW));
        if (pdfIndirectReference != null) {
            pdfDictionary.put(PdfName.FONTFILE, pdfIndirectReference);
        }
        int i = 0;
        if (this.IsFixedPitch) {
            i = 1;
        }
        int i2 = i | (this.fontSpecific ? 4 : 32);
        if (this.ItalicAngle < 0.0f) {
            i2 |= 64;
        }
        if (this.FontName.indexOf("Caps") >= 0 || this.FontName.endsWith("SC")) {
            i2 |= 131072;
        }
        if (this.Weight.equals("Bold")) {
            i2 |= 262144;
        }
        pdfDictionary.put(PdfName.FLAGS, new PdfNumber(i2));
        return pdfDictionary;
    }

    private PdfDictionary getFontBaseType(PdfIndirectReference pdfIndirectReference, int i, int i2, byte[] bArr) {
        PdfDictionary pdfDictionary = new PdfDictionary(PdfName.FONT);
        pdfDictionary.put(PdfName.SUBTYPE, PdfName.TYPE1);
        pdfDictionary.put(PdfName.BASEFONT, new PdfName(this.FontName));
        boolean z = this.encoding.equals("Cp1252") || this.encoding.equals(BaseFont.MACROMAN);
        if (!this.fontSpecific || this.specialMap != null) {
            int i3 = i;
            while (true) {
                if (i3 > i2) {
                    break;
                } else if (!this.differences[i3].equals(BaseFont.notdef)) {
                    i = i3;
                    break;
                } else {
                    i3++;
                }
            }
            if (z) {
                pdfDictionary.put(PdfName.ENCODING, this.encoding.equals("Cp1252") ? PdfName.WIN_ANSI_ENCODING : PdfName.MAC_ROMAN_ENCODING);
            } else {
                PdfDictionary pdfDictionary2 = new PdfDictionary(PdfName.ENCODING);
                PdfArray pdfArray = new PdfArray();
                boolean z2 = true;
                for (int i4 = i; i4 <= i2; i4++) {
                    if (bArr[i4] != 0) {
                        if (z2) {
                            pdfArray.add((PdfObject) new PdfNumber(i4));
                            z2 = false;
                        }
                        pdfArray.add((PdfObject) new PdfName(this.differences[i4]));
                    } else {
                        z2 = true;
                    }
                }
                pdfDictionary2.put(PdfName.DIFFERENCES, pdfArray);
                pdfDictionary.put(PdfName.ENCODING, pdfDictionary2);
            }
        }
        if (this.specialMap != null || this.forceWidthsOutput || !this.builtinFont || (!this.fontSpecific && !z)) {
            pdfDictionary.put(PdfName.FIRSTCHAR, new PdfNumber(i));
            pdfDictionary.put(PdfName.LASTCHAR, new PdfNumber(i2));
            PdfArray pdfArray2 = new PdfArray();
            while (i <= i2) {
                if (bArr[i] == 0) {
                    pdfArray2.add((PdfObject) new PdfNumber(0));
                } else {
                    pdfArray2.add((PdfObject) new PdfNumber(this.widths[i]));
                }
                i++;
            }
            pdfDictionary.put(PdfName.WIDTHS, pdfArray2);
        }
        if (!this.builtinFont && pdfIndirectReference != null) {
            pdfDictionary.put(PdfName.FONTDESCRIPTOR, pdfIndirectReference);
        }
        return pdfDictionary;
    }

    /* access modifiers changed from: package-private */
    public void writeFont(PdfWriter pdfWriter, PdfIndirectReference pdfIndirectReference, Object[] objArr) throws DocumentException, IOException {
        int i = 0;
        int intValue = objArr[0].intValue();
        int intValue2 = objArr[1].intValue();
        byte[] bArr = objArr[2];
        if (!(objArr[3].booleanValue() && this.subset) || !this.embedded) {
            intValue2 = bArr.length - 1;
            for (int i2 = 0; i2 < bArr.length; i2++) {
                bArr[i2] = 1;
            }
        } else {
            i = intValue;
        }
        PdfIndirectReference pdfIndirectReference2 = null;
        PdfStream fullFontStream = getFullFontStream();
        if (fullFontStream != null) {
            pdfIndirectReference2 = pdfWriter.addToBody(fullFontStream).getIndirectReference();
        }
        PdfDictionary fontDescriptor = getFontDescriptor(pdfIndirectReference2);
        if (fontDescriptor != null) {
            pdfIndirectReference2 = pdfWriter.addToBody(fontDescriptor).getIndirectReference();
        }
        pdfWriter.addToBody((PdfObject) getFontBaseType(pdfIndirectReference2, i, intValue2, bArr), pdfIndirectReference);
    }

    public float getFontDescriptor(int i, float f) {
        int i2;
        switch (i) {
            case 1:
            case 9:
                i2 = this.Ascender;
                break;
            case 2:
                i2 = this.CapHeight;
                break;
            case 3:
            case 10:
                i2 = this.Descender;
                break;
            case 4:
                return this.ItalicAngle;
            case 5:
                i2 = this.llx;
                break;
            case 6:
                i2 = this.lly;
                break;
            case 7:
                i2 = this.urx;
                break;
            case 8:
                i2 = this.ury;
                break;
            case 12:
                i2 = this.urx - this.llx;
                break;
            case 13:
                i2 = this.UnderlinePosition;
                break;
            case 14:
                i2 = this.UnderlineThickness;
                break;
            default:
                return 0.0f;
        }
        return (((float) i2) * f) / 1000.0f;
    }

    public void setFontDescriptor(int i, float f) {
        if (i != 1) {
            if (i != 3) {
                if (i != 9) {
                    if (i != 10) {
                        return;
                    }
                }
            }
            this.Descender = (int) f;
            return;
        }
        this.Ascender = (int) f;
    }

    public String getPostscriptFontName() {
        return this.FontName;
    }

    public String[][] getFullFontName() {
        return new String[][]{new String[]{"", "", "", this.FullName}};
    }

    public String[][] getAllNameEntries() {
        return new String[][]{new String[]{"4", "", "", "", this.FullName}};
    }

    public String[][] getFamilyFontName() {
        return new String[][]{new String[]{"", "", "", this.FamilyName}};
    }

    public boolean hasKernPairs() {
        return !this.KernPairs.isEmpty();
    }

    public void setPostscriptFontName(String str) {
        this.FontName = str;
    }

    public boolean setKerning(int i, int i2, int i3) {
        String unicodeToName;
        String unicodeToName2 = GlyphList.unicodeToName(i);
        if (unicodeToName2 == null || (unicodeToName = GlyphList.unicodeToName(i2)) == null) {
            return false;
        }
        Object[] objArr = this.KernPairs.get(unicodeToName2);
        if (objArr == null) {
            this.KernPairs.put(unicodeToName2, new Object[]{unicodeToName, Integer.valueOf(i3)});
            return true;
        }
        for (int i4 = 0; i4 < objArr.length; i4 += 2) {
            if (unicodeToName.equals(objArr[i4])) {
                objArr[i4 + 1] = Integer.valueOf(i3);
                return true;
            }
        }
        int length = objArr.length;
        Object[] objArr2 = new Object[(length + 2)];
        System.arraycopy(objArr, 0, objArr2, 0, length);
        objArr2[length] = unicodeToName;
        objArr2[length + 1] = Integer.valueOf(i3);
        this.KernPairs.put(unicodeToName2, objArr2);
        return true;
    }

    /* access modifiers changed from: protected */
    public int[] getRawCharBBox(int i, String str) {
        Object[] objArr;
        if (str == null) {
            objArr = this.CharMetrics.get(Integer.valueOf(i));
        } else if (str.equals(BaseFont.notdef)) {
            return null;
        } else {
            objArr = this.CharMetrics.get(str);
        }
        if (objArr != null) {
            return (int[]) objArr[3];
        }
        return null;
    }
}
