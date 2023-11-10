package com.itextpdf.text.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Image;
import com.itextpdf.text.SplitCharacter;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.TabStop;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import java.util.HashMap;
import java.util.HashSet;

public class PdfChunk {
    private static final float ITALIC_ANGLE = 0.21256f;
    private static final String TABSTOP = "TABSTOP";
    public static final float UNDERLINE_OFFSET = -0.33333334f;
    public static final float UNDERLINE_THICKNESS = 0.06666667f;
    private static final HashSet<String> keysAttributes;
    private static final HashSet<String> keysNoStroke;
    private static final char[] singleSpace = {' '};
    protected IAccessibleElement accessibleElement;
    protected HashMap<String, Object> attributes;
    protected BaseFont baseFont;
    protected boolean changeLeading;
    protected String encoding;
    protected PdfFont font;
    protected Image image;
    protected float imageScalePercentage;
    protected float leading;
    protected boolean newlineSplit;
    protected HashMap<String, Object> noStroke;
    protected float offsetX;
    protected float offsetY;
    protected SplitCharacter splitCharacter;
    protected String value;

    public static boolean noPrint(int i) {
        return (i >= 8203 && i <= 8207) || (i >= 8234 && i <= 8238);
    }

    static {
        HashSet<String> hashSet = new HashSet<>();
        keysAttributes = hashSet;
        HashSet<String> hashSet2 = new HashSet<>();
        keysNoStroke = hashSet2;
        hashSet.add(Chunk.ACTION);
        hashSet.add(Chunk.UNDERLINE);
        hashSet.add(Chunk.REMOTEGOTO);
        hashSet.add(Chunk.LOCALGOTO);
        hashSet.add(Chunk.LOCALDESTINATION);
        hashSet.add(Chunk.GENERICTAG);
        hashSet.add(Chunk.NEWPAGE);
        hashSet.add(Chunk.IMAGE);
        hashSet.add(Chunk.BACKGROUND);
        hashSet.add(Chunk.PDFANNOTATION);
        hashSet.add(Chunk.SKEW);
        hashSet.add(Chunk.HSCALE);
        hashSet.add(Chunk.SEPARATOR);
        hashSet.add(Chunk.TAB);
        hashSet.add(Chunk.TABSETTINGS);
        hashSet.add(Chunk.CHAR_SPACING);
        hashSet.add(Chunk.WORD_SPACING);
        hashSet.add(Chunk.LINEHEIGHT);
        hashSet2.add(Chunk.SUBSUPSCRIPT);
        hashSet2.add(Chunk.SPLITCHARACTER);
        hashSet2.add(Chunk.HYPHENATION);
        hashSet2.add(Chunk.TEXTRENDERMODE);
    }

    PdfChunk(String str, PdfChunk pdfChunk) {
        this.value = "";
        this.encoding = "Cp1252";
        this.attributes = new HashMap<>();
        this.noStroke = new HashMap<>();
        this.imageScalePercentage = 1.0f;
        this.changeLeading = false;
        this.leading = 0.0f;
        this.accessibleElement = null;
        this.value = str;
        this.font = pdfChunk.font;
        HashMap<String, Object> hashMap = pdfChunk.attributes;
        this.attributes = hashMap;
        this.noStroke = pdfChunk.noStroke;
        this.baseFont = pdfChunk.baseFont;
        this.changeLeading = pdfChunk.changeLeading;
        this.leading = pdfChunk.leading;
        Object[] objArr = (Object[]) hashMap.get(Chunk.IMAGE);
        if (objArr == null) {
            this.image = null;
        } else {
            this.image = (Image) objArr[0];
            this.offsetX = ((Float) objArr[1]).floatValue();
            this.offsetY = ((Float) objArr[2]).floatValue();
            this.changeLeading = ((Boolean) objArr[3]).booleanValue();
        }
        this.encoding = this.font.getFont().getEncoding();
        SplitCharacter splitCharacter2 = (SplitCharacter) this.noStroke.get(Chunk.SPLITCHARACTER);
        this.splitCharacter = splitCharacter2;
        if (splitCharacter2 == null) {
            this.splitCharacter = DefaultSplitCharacter.DEFAULT;
        }
        this.accessibleElement = pdfChunk.accessibleElement;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    PdfChunk(com.itextpdf.text.Chunk r14, com.itextpdf.text.pdf.PdfAction r15) {
        /*
            r13 = this;
            r13.<init>()
            java.lang.String r0 = ""
            r13.value = r0
            java.lang.String r1 = "Cp1252"
            r13.encoding = r1
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            r13.attributes = r1
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            r13.noStroke = r1
            r1 = 1065353216(0x3f800000, float:1.0)
            r13.imageScalePercentage = r1
            r1 = 0
            r13.changeLeading = r1
            r2 = 0
            r13.leading = r2
            r2 = 0
            r13.accessibleElement = r2
            java.lang.String r3 = r14.getContent()
            r13.value = r3
            com.itextpdf.text.Font r3 = r14.getFont()
            float r4 = r3.getSize()
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r5 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r5 != 0) goto L_0x003c
            r4 = 1094713344(0x41400000, float:12.0)
        L_0x003c:
            com.itextpdf.text.pdf.BaseFont r5 = r3.getBaseFont()
            r13.baseFont = r5
            int r5 = r3.getStyle()
            r6 = -1
            if (r5 != r6) goto L_0x004a
            r5 = 0
        L_0x004a:
            com.itextpdf.text.pdf.BaseFont r6 = r13.baseFont
            r7 = 3
            r8 = 1
            r9 = 2
            if (r6 != 0) goto L_0x0058
            com.itextpdf.text.pdf.BaseFont r5 = r3.getCalculatedBaseFont(r1)
            r13.baseFont = r5
            goto L_0x0087
        L_0x0058:
            r6 = r5 & 1
            if (r6 == 0) goto L_0x0078
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r13.attributes
            java.lang.Object[] r10 = new java.lang.Object[r7]
            java.lang.Integer r11 = java.lang.Integer.valueOf(r9)
            r10[r1] = r11
            java.lang.Float r11 = new java.lang.Float
            r12 = 1106247680(0x41f00000, float:30.0)
            float r12 = r4 / r12
            r11.<init>(r12)
            r10[r8] = r11
            r10[r9] = r2
            java.lang.String r11 = "TEXTRENDERMODE"
            r6.put(r11, r10)
        L_0x0078:
            r5 = r5 & r9
            if (r5 == 0) goto L_0x0087
            java.util.HashMap<java.lang.String, java.lang.Object> r5 = r13.attributes
            float[] r6 = new float[r9]
            r6 = {0, 1046063444} // fill-array
            java.lang.String r10 = "SKEW"
            r5.put(r10, r6)
        L_0x0087:
            com.itextpdf.text.pdf.PdfFont r5 = new com.itextpdf.text.pdf.PdfFont
            com.itextpdf.text.pdf.BaseFont r6 = r13.baseFont
            r5.<init>(r6, r4)
            r13.font = r5
            java.util.HashMap r4 = r14.getAttributes()
            if (r4 == 0) goto L_0x00e9
            java.util.Set r5 = r4.entrySet()
            java.util.Iterator r5 = r5.iterator()
        L_0x009e:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x00d4
            java.lang.Object r6 = r5.next()
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6
            java.lang.Object r10 = r6.getKey()
            java.lang.String r10 = (java.lang.String) r10
            java.util.HashSet<java.lang.String> r11 = keysAttributes
            boolean r11 = r11.contains(r10)
            if (r11 == 0) goto L_0x00c2
            java.util.HashMap<java.lang.String, java.lang.Object> r11 = r13.attributes
            java.lang.Object r6 = r6.getValue()
            r11.put(r10, r6)
            goto L_0x009e
        L_0x00c2:
            java.util.HashSet<java.lang.String> r11 = keysNoStroke
            boolean r11 = r11.contains(r10)
            if (r11 == 0) goto L_0x009e
            java.util.HashMap<java.lang.String, java.lang.Object> r11 = r13.noStroke
            java.lang.Object r6 = r6.getValue()
            r11.put(r10, r6)
            goto L_0x009e
        L_0x00d4:
            java.lang.String r5 = "GENERICTAG"
            java.lang.Object r4 = r4.get(r5)
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00e9
            java.util.HashMap<java.lang.String, java.lang.Object> r0 = r13.attributes
            java.lang.String r4 = r14.getContent()
            r0.put(r5, r4)
        L_0x00e9:
            boolean r0 = r3.isUnderlined()
            r4 = 5
            java.lang.String r5 = "UNDERLINE"
            if (r0 == 0) goto L_0x0110
            java.lang.Object[] r0 = new java.lang.Object[r9]
            r0[r1] = r2
            float[] r6 = new float[r4]
            r6 = {0, 1032358025, 0, -1096111445, 0} // fill-array
            r0[r8] = r6
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r13.attributes
            java.lang.Object r6 = r6.get(r5)
            java.lang.Object[][] r6 = (java.lang.Object[][]) r6
            java.lang.Object[][] r6 = (java.lang.Object[][]) r6
            java.lang.Object[][] r0 = com.itextpdf.text.Utilities.addToArray(r6, r0)
            java.util.HashMap<java.lang.String, java.lang.Object> r6 = r13.attributes
            r6.put(r5, r0)
        L_0x0110:
            boolean r0 = r3.isStrikethru()
            if (r0 == 0) goto L_0x0134
            java.lang.Object[] r0 = new java.lang.Object[r9]
            r0[r1] = r2
            float[] r4 = new float[r4]
            r4 = {0, 1032358025, 0, 1051372203, 0} // fill-array
            r0[r8] = r4
            java.util.HashMap<java.lang.String, java.lang.Object> r4 = r13.attributes
            java.lang.Object r4 = r4.get(r5)
            java.lang.Object[][] r4 = (java.lang.Object[][]) r4
            java.lang.Object[][] r4 = (java.lang.Object[][]) r4
            java.lang.Object[][] r0 = com.itextpdf.text.Utilities.addToArray(r4, r0)
            java.util.HashMap<java.lang.String, java.lang.Object> r4 = r13.attributes
            r4.put(r5, r0)
        L_0x0134:
            if (r15 == 0) goto L_0x013d
            java.util.HashMap<java.lang.String, java.lang.Object> r0 = r13.attributes
            java.lang.String r4 = "ACTION"
            r0.put(r4, r15)
        L_0x013d:
            java.util.HashMap<java.lang.String, java.lang.Object> r15 = r13.noStroke
            com.itextpdf.text.BaseColor r0 = r3.getColor()
            java.lang.String r3 = "COLOR"
            r15.put(r3, r0)
            java.util.HashMap<java.lang.String, java.lang.Object> r15 = r13.noStroke
            com.itextpdf.text.pdf.PdfFont r0 = r13.font
            com.itextpdf.text.pdf.BaseFont r0 = r0.getFont()
            java.lang.String r0 = r0.getEncoding()
            java.lang.String r3 = "ENCODING"
            r15.put(r3, r0)
            java.util.HashMap<java.lang.String, java.lang.Object> r15 = r13.attributes
            java.lang.String r0 = "LINEHEIGHT"
            java.lang.Object r15 = r15.get(r0)
            java.lang.Float r15 = (java.lang.Float) r15
            if (r15 == 0) goto L_0x016d
            r13.changeLeading = r8
            float r15 = r15.floatValue()
            r13.leading = r15
        L_0x016d:
            java.util.HashMap<java.lang.String, java.lang.Object> r15 = r13.attributes
            java.lang.String r0 = "IMAGE"
            java.lang.Object r15 = r15.get(r0)
            java.lang.Object[] r15 = (java.lang.Object[]) r15
            java.lang.Object[] r15 = (java.lang.Object[]) r15
            java.lang.String r0 = "HSCALE"
            if (r15 != 0) goto L_0x0180
            r13.image = r2
            goto L_0x01a9
        L_0x0180:
            java.util.HashMap<java.lang.String, java.lang.Object> r2 = r13.attributes
            r2.remove(r0)
            r1 = r15[r1]
            com.itextpdf.text.Image r1 = (com.itextpdf.text.Image) r1
            r13.image = r1
            r1 = r15[r8]
            java.lang.Float r1 = (java.lang.Float) r1
            float r1 = r1.floatValue()
            r13.offsetX = r1
            r1 = r15[r9]
            java.lang.Float r1 = (java.lang.Float) r1
            float r1 = r1.floatValue()
            r13.offsetY = r1
            r15 = r15[r7]
            java.lang.Boolean r15 = (java.lang.Boolean) r15
            boolean r15 = r15.booleanValue()
            r13.changeLeading = r15
        L_0x01a9:
            java.util.HashMap<java.lang.String, java.lang.Object> r15 = r13.attributes
            java.lang.Object r15 = r15.get(r0)
            java.lang.Float r15 = (java.lang.Float) r15
            if (r15 == 0) goto L_0x01bc
            com.itextpdf.text.pdf.PdfFont r0 = r13.font
            float r15 = r15.floatValue()
            r0.setHorizontalScaling(r15)
        L_0x01bc:
            com.itextpdf.text.pdf.PdfFont r15 = r13.font
            com.itextpdf.text.pdf.BaseFont r15 = r15.getFont()
            java.lang.String r15 = r15.getEncoding()
            r13.encoding = r15
            java.util.HashMap<java.lang.String, java.lang.Object> r15 = r13.noStroke
            java.lang.String r0 = "SPLITCHARACTER"
            java.lang.Object r15 = r15.get(r0)
            com.itextpdf.text.SplitCharacter r15 = (com.itextpdf.text.SplitCharacter) r15
            r13.splitCharacter = r15
            if (r15 != 0) goto L_0x01da
            com.itextpdf.text.SplitCharacter r15 = com.itextpdf.text.pdf.DefaultSplitCharacter.DEFAULT
            r13.splitCharacter = r15
        L_0x01da:
            r13.accessibleElement = r14
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfChunk.<init>(com.itextpdf.text.Chunk, com.itextpdf.text.pdf.PdfAction):void");
    }

    PdfChunk(Chunk chunk, PdfAction pdfAction, TabSettings tabSettings) {
        this(chunk, pdfAction);
        if (tabSettings != null && this.attributes.get(Chunk.TABSETTINGS) == null) {
            this.attributes.put(Chunk.TABSETTINGS, tabSettings);
        }
    }

    public int getUnicodeEquivalent(int i) {
        return this.baseFont.getUnicodeEquivalent(i);
    }

    /* access modifiers changed from: protected */
    public int getWord(String str, int i) {
        int length = str.length();
        while (i < length && Character.isLetter(str.charAt(i))) {
            i++;
        }
        return i;
    }

    /* JADX WARNING: type inference failed for: r10v0 */
    /* JADX WARNING: type inference failed for: r10v6 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x013a, code lost:
        if (r6 != 13) goto L_0x0146;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x013c, code lost:
        r1 = r9 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x013e, code lost:
        if (r1 >= r5) goto L_0x0146;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0142, code lost:
        if (r12[r1] != 10) goto L_0x0146;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0144, code lost:
        r14 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0146, code lost:
        r14 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0147, code lost:
        r1 = r0.value.substring(r14 + r9);
        r2 = r0.value.substring(0, r9);
        r0.value = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x015c, code lost:
        if (r2.length() >= 1) goto L_0x0162;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x015e, code lost:
        r0.value = " ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0167, code lost:
        return new com.itextpdf.text.pdf.PdfChunk(r1, r0);
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=int, code=?, for r10v4, types: [boolean, int] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.pdf.PdfChunk split(float r22) {
        /*
            r21 = this;
            r0 = r21
            r1 = 0
            r0.newlineSplit = r1
            com.itextpdf.text.Image r2 = r0.image
            java.lang.String r3 = ""
            r4 = 0
            if (r2 == 0) goto L_0x002f
            float r1 = r2.getScaledWidth()
            int r1 = (r1 > r22 ? 1 : (r1 == r22 ? 0 : -1))
            if (r1 <= 0) goto L_0x002e
            com.itextpdf.text.pdf.PdfChunk r1 = new com.itextpdf.text.pdf.PdfChunk
            java.lang.String r2 = "￼"
            r1.<init>((java.lang.String) r2, (com.itextpdf.text.pdf.PdfChunk) r0)
            r0.value = r3
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            r0.attributes = r2
            r0.image = r4
            com.itextpdf.text.pdf.PdfFont r2 = com.itextpdf.text.pdf.PdfFont.getDefaultFont()
            r0.font = r2
            return r1
        L_0x002e:
            return r4
        L_0x002f:
            java.util.HashMap<java.lang.String, java.lang.Object> r2 = r0.noStroke
            java.lang.String r5 = "HYPHENATION"
            java.lang.Object r2 = r2.get(r5)
            com.itextpdf.text.pdf.HyphenationEvent r2 = (com.itextpdf.text.pdf.HyphenationEvent) r2
            java.lang.String r5 = r0.value
            int r5 = r5.length()
            java.lang.String r6 = r0.value
            char[] r12 = r6.toCharArray()
            com.itextpdf.text.pdf.PdfFont r6 = r0.font
            com.itextpdf.text.pdf.BaseFont r13 = r6.getFont()
            int r6 = r13.getFontType()
            r14 = 2
            r7 = 0
            r8 = -1
            r15 = 10
            r11 = 32
            r10 = 1
            if (r6 != r14) goto L_0x00d9
            int r6 = r13.getUnicodeEquivalent(r11)
            if (r6 == r11) goto L_0x00d9
            r6 = 0
            r9 = 0
            r14 = -1
        L_0x0062:
            if (r9 >= r5) goto L_0x00d5
            char r4 = r12[r9]
            int r11 = r13.getUnicodeEquivalent(r4)
            char r11 = (char) r11
            if (r11 != r15) goto L_0x008f
            r0.newlineSplit = r10
            java.lang.String r2 = r0.value
            int r3 = r9 + 1
            java.lang.String r2 = r2.substring(r3)
            java.lang.String r3 = r0.value
            java.lang.String r1 = r3.substring(r1, r9)
            r0.value = r1
            int r1 = r1.length()
            if (r1 >= r10) goto L_0x0089
            java.lang.String r1 = "\u0001"
            r0.value = r1
        L_0x0089:
            com.itextpdf.text.pdf.PdfChunk r1 = new com.itextpdf.text.pdf.PdfChunk
            r1.<init>((java.lang.String) r2, (com.itextpdf.text.pdf.PdfChunk) r0)
            return r1
        L_0x008f:
            float r4 = r0.getCharWidth(r4)
            float r4 = r4 + r7
            r7 = 32
            if (r11 != r7) goto L_0x009f
            int r6 = r9 + 1
            r17 = r4
            r18 = r6
            goto L_0x00a3
        L_0x009f:
            r17 = r6
            r18 = r8
        L_0x00a3:
            int r6 = (r4 > r22 ? 1 : (r4 == r22 ? 0 : -1))
            if (r6 <= 0) goto L_0x00ad
            r6 = r17
            r8 = r18
            goto L_0x016a
        L_0x00ad:
            com.itextpdf.text.SplitCharacter r6 = r0.splitCharacter
            r8 = 0
            com.itextpdf.text.pdf.PdfChunk[] r11 = new com.itextpdf.text.pdf.PdfChunk[r10]
            r11[r1] = r0
            r19 = 32
            r7 = r8
            r8 = r9
            r20 = r9
            r9 = r5
            r1 = 1
            r10 = r12
            r1 = 32
            boolean r6 = r6.isSplitCharacter(r7, r8, r9, r10, r11)
            if (r6 == 0) goto L_0x00c8
            int r9 = r20 + 1
            r14 = r9
        L_0x00c8:
            int r9 = r20 + 1
            r7 = r4
            r6 = r17
            r8 = r18
            r1 = 0
            r4 = 0
            r10 = 1
            r11 = 32
            goto L_0x0062
        L_0x00d5:
            r20 = r9
            goto L_0x016a
        L_0x00d9:
            r1 = 32
            r4 = 0
            r9 = 0
            r13 = -1
        L_0x00de:
            if (r9 >= r5) goto L_0x0168
            char r6 = r12[r9]
            r10 = 13
            if (r6 == r10) goto L_0x0137
            if (r6 != r15) goto L_0x00e9
            goto L_0x0137
        L_0x00e9:
            boolean r10 = com.itextpdf.text.Utilities.isSurrogatePair((char[]) r12, (int) r9)
            if (r10 == 0) goto L_0x00fe
            char r11 = r12[r9]
            int r17 = r9 + 1
            char r14 = r12[r17]
            int r11 = com.itextpdf.text.Utilities.convertToUtf32((char) r11, (char) r14)
            float r11 = r0.getCharWidth(r11)
            goto L_0x0102
        L_0x00fe:
            float r11 = r0.getCharWidth(r6)
        L_0x0102:
            float r7 = r7 + r11
            r14 = r7
            if (r6 != r1) goto L_0x010c
            int r4 = r9 + 1
            r17 = r4
            r4 = r14
            goto L_0x010e
        L_0x010c:
            r17 = r8
        L_0x010e:
            if (r10 == 0) goto L_0x0112
            int r9 = r9 + 1
        L_0x0112:
            r19 = r9
            int r6 = (r14 > r22 ? 1 : (r14 == r22 ? 0 : -1))
            if (r6 <= 0) goto L_0x011f
            r6 = r4
            r14 = r13
            r8 = r17
            r9 = r19
            goto L_0x016a
        L_0x011f:
            com.itextpdf.text.SplitCharacter r6 = r0.splitCharacter
            r7 = 0
            r11 = 0
            r8 = r19
            r9 = r5
            r10 = r12
            boolean r6 = r6.isSplitCharacter(r7, r8, r9, r10, r11)
            if (r6 == 0) goto L_0x0130
            int r6 = r19 + 1
            r13 = r6
        L_0x0130:
            int r9 = r19 + 1
            r7 = r14
            r8 = r17
            r14 = 2
            goto L_0x00de
        L_0x0137:
            r1 = 1
            r0.newlineSplit = r1
            if (r6 != r10) goto L_0x0146
            int r1 = r9 + 1
            if (r1 >= r5) goto L_0x0146
            char r1 = r12[r1]
            if (r1 != r15) goto L_0x0146
            r14 = 2
            goto L_0x0147
        L_0x0146:
            r14 = 1
        L_0x0147:
            java.lang.String r1 = r0.value
            int r14 = r14 + r9
            java.lang.String r1 = r1.substring(r14)
            java.lang.String r2 = r0.value
            r3 = 0
            java.lang.String r2 = r2.substring(r3, r9)
            r0.value = r2
            int r2 = r2.length()
            r3 = 1
            if (r2 >= r3) goto L_0x0162
            java.lang.String r2 = " "
            r0.value = r2
        L_0x0162:
            com.itextpdf.text.pdf.PdfChunk r2 = new com.itextpdf.text.pdf.PdfChunk
            r2.<init>((java.lang.String) r1, (com.itextpdf.text.pdf.PdfChunk) r0)
            return r2
        L_0x0168:
            r6 = r4
            r14 = r13
        L_0x016a:
            if (r9 != r5) goto L_0x016e
            r1 = 0
            return r1
        L_0x016e:
            if (r14 >= 0) goto L_0x017a
            java.lang.String r1 = r0.value
            r0.value = r3
            com.itextpdf.text.pdf.PdfChunk r2 = new com.itextpdf.text.pdf.PdfChunk
            r2.<init>((java.lang.String) r1, (com.itextpdf.text.pdf.PdfChunk) r0)
            return r2
        L_0x017a:
            if (r8 <= r14) goto L_0x018f
            com.itextpdf.text.SplitCharacter r15 = r0.splitCharacter
            r16 = 0
            r17 = 0
            r18 = 1
            char[] r19 = singleSpace
            r20 = 0
            boolean r1 = r15.isSplitCharacter(r16, r17, r18, r19, r20)
            if (r1 == 0) goto L_0x018f
            r14 = r8
        L_0x018f:
            if (r2 == 0) goto L_0x01f6
            if (r8 < 0) goto L_0x01f6
            if (r8 >= r9) goto L_0x01f6
            java.lang.String r1 = r0.value
            int r1 = r0.getWord(r1, r8)
            if (r1 <= r8) goto L_0x01f6
            java.lang.String r3 = r0.value
            java.lang.String r3 = r3.substring(r8, r1)
            com.itextpdf.text.pdf.PdfFont r4 = r0.font
            com.itextpdf.text.pdf.BaseFont r4 = r4.getFont()
            com.itextpdf.text.pdf.PdfFont r5 = r0.font
            float r5 = r5.size()
            float r6 = r22 - r6
            java.lang.String r3 = r2.getHyphenatedWordPre(r3, r4, r5, r6)
            java.lang.String r2 = r2.getHyphenatedWordPost()
            int r4 = r3.length()
            if (r4 <= 0) goto L_0x01f6
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r2)
            java.lang.String r2 = r0.value
            java.lang.String r1 = r2.substring(r1)
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = r0.value
            r5 = 0
            java.lang.String r4 = r4.substring(r5, r8)
            r2.append(r4)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r2 = r0.trim(r2)
            r0.value = r2
            com.itextpdf.text.pdf.PdfChunk r2 = new com.itextpdf.text.pdf.PdfChunk
            r2.<init>((java.lang.String) r1, (com.itextpdf.text.pdf.PdfChunk) r0)
            return r2
        L_0x01f6:
            java.lang.String r1 = r0.value
            java.lang.String r1 = r1.substring(r14)
            java.lang.String r2 = r0.value
            r3 = 0
            java.lang.String r2 = r2.substring(r3, r14)
            java.lang.String r2 = r0.trim(r2)
            r0.value = r2
            com.itextpdf.text.pdf.PdfChunk r2 = new com.itextpdf.text.pdf.PdfChunk
            r2.<init>((java.lang.String) r1, (com.itextpdf.text.pdf.PdfChunk) r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfChunk.split(float):com.itextpdf.text.pdf.PdfChunk");
    }

    /* access modifiers changed from: package-private */
    public PdfChunk truncate(float f) {
        float f2;
        Image image2 = this.image;
        if (image2 == null) {
            float f3 = 0.0f;
            int i = 1;
            if (f < this.font.width()) {
                String substring = this.value.substring(1);
                this.value = this.value.substring(0, 1);
                return new PdfChunk(substring, this);
            }
            int length = this.value.length();
            int i2 = 0;
            boolean z = false;
            while (i2 < length) {
                z = Utilities.isSurrogatePair(this.value, i2);
                if (z) {
                    f2 = getCharWidth(Utilities.convertToUtf32(this.value, i2));
                } else {
                    f2 = getCharWidth(this.value.charAt(i2));
                }
                f3 += f2;
                if (f3 > f) {
                    break;
                }
                if (z) {
                    i2++;
                }
                i2++;
            }
            if (i2 == length) {
                return null;
            }
            if (i2 != 0) {
                i = i2;
            } else if (z) {
                i = 2;
            }
            String substring2 = this.value.substring(i);
            this.value = this.value.substring(0, i);
            return new PdfChunk(substring2, this);
        } else if (image2.getScaledWidth() <= f) {
            return null;
        } else {
            if (this.image.isScaleToFitLineWhenOverflow()) {
                setImageScalePercentage(f / this.image.getWidth());
                return null;
            }
            PdfChunk pdfChunk = new PdfChunk("", this);
            this.value = "";
            this.attributes.remove(Chunk.IMAGE);
            this.image = null;
            this.font = PdfFont.getDefaultFont();
            return pdfChunk;
        }
    }

    /* access modifiers changed from: package-private */
    public PdfFont font() {
        return this.font;
    }

    /* access modifiers changed from: package-private */
    public BaseColor color() {
        return (BaseColor) this.noStroke.get(Chunk.COLOR);
    }

    /* access modifiers changed from: package-private */
    public float width() {
        return width(this.value);
    }

    /* access modifiers changed from: package-private */
    public float width(String str) {
        if (isAttribute(Chunk.SEPARATOR)) {
            return 0.0f;
        }
        if (isImage()) {
            return getImageWidth();
        }
        float width = this.font.width(str);
        if (isAttribute(Chunk.CHAR_SPACING)) {
            width += ((float) str.length()) * ((Float) getAttribute(Chunk.CHAR_SPACING)).floatValue();
        }
        if (!isAttribute(Chunk.WORD_SPACING)) {
            return width;
        }
        int i = 0;
        int i2 = -1;
        while (true) {
            i2 = str.indexOf(32, i2 + 1);
            if (i2 < 0) {
                return width + (((float) i) * ((Float) getAttribute(Chunk.WORD_SPACING)).floatValue());
            }
            i++;
        }
    }

    /* access modifiers changed from: package-private */
    public float height() {
        if (isImage()) {
            return getImageHeight();
        }
        return this.font.size();
    }

    public boolean isNewlineSplit() {
        return this.newlineSplit;
    }

    public float getWidthCorrected(float f, float f2) {
        Image image2 = this.image;
        if (image2 != null) {
            return image2.getScaledWidth() + f;
        }
        int i = 0;
        int i2 = -1;
        while (true) {
            i2 = this.value.indexOf(32, i2 + 1);
            if (i2 < 0) {
                return this.font.width(this.value) + (((float) this.value.length()) * f) + (((float) i) * f2);
            }
            i++;
        }
    }

    public float getTextRise() {
        Float f = (Float) getAttribute(Chunk.SUBSUPSCRIPT);
        if (f != null) {
            return f.floatValue();
        }
        return 0.0f;
    }

    public float trimLastSpace() {
        BaseFont font2 = this.font.getFont();
        if (font2.getFontType() != 2 || font2.getUnicodeEquivalent(32) == 32) {
            if (this.value.length() <= 1 || !this.value.endsWith(" ")) {
                return 0.0f;
            }
            String str = this.value;
            this.value = str.substring(0, str.length() - 1);
            return this.font.width(32);
        } else if (this.value.length() <= 1 || !this.value.endsWith("\u0001")) {
            return 0.0f;
        } else {
            String str2 = this.value;
            this.value = str2.substring(0, str2.length() - 1);
            return this.font.width(1);
        }
    }

    public float trimFirstSpace() {
        BaseFont font2 = this.font.getFont();
        if (font2.getFontType() != 2 || font2.getUnicodeEquivalent(32) == 32) {
            if (this.value.length() <= 1 || !this.value.startsWith(" ")) {
                return 0.0f;
            }
            this.value = this.value.substring(1);
            return this.font.width(32);
        } else if (this.value.length() <= 1 || !this.value.startsWith("\u0001")) {
            return 0.0f;
        } else {
            this.value = this.value.substring(1);
            return this.font.width(1);
        }
    }

    /* access modifiers changed from: package-private */
    public Object getAttribute(String str) {
        if (this.attributes.containsKey(str)) {
            return this.attributes.get(str);
        }
        return this.noStroke.get(str);
    }

    /* access modifiers changed from: package-private */
    public boolean isAttribute(String str) {
        if (this.attributes.containsKey(str)) {
            return true;
        }
        return this.noStroke.containsKey(str);
    }

    /* access modifiers changed from: package-private */
    public boolean isStroked() {
        return !this.attributes.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public boolean isSeparator() {
        return isAttribute(Chunk.SEPARATOR);
    }

    /* access modifiers changed from: package-private */
    public boolean isHorizontalSeparator() {
        if (isAttribute(Chunk.SEPARATOR)) {
            return !((Boolean) ((Object[]) getAttribute(Chunk.SEPARATOR))[1]).booleanValue();
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean isTab() {
        return isAttribute(Chunk.TAB);
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public void adjustLeft(float f) {
        Object[] objArr = (Object[]) this.attributes.get(Chunk.TAB);
        if (objArr != null) {
            this.attributes.put(Chunk.TAB, new Object[]{objArr[0], objArr[1], objArr[2], new Float(f)});
        }
    }

    static TabStop getTabStop(PdfChunk pdfChunk, float f) {
        Object[] objArr = (Object[]) pdfChunk.attributes.get(Chunk.TAB);
        if (objArr == null) {
            return null;
        }
        Float f2 = (Float) objArr[0];
        if (Float.isNaN(f2.floatValue())) {
            return TabSettings.getTabStopNewInstance(f, (TabSettings) pdfChunk.attributes.get(Chunk.TABSETTINGS));
        }
        return TabStop.newInstance(f, f2.floatValue());
    }

    /* access modifiers changed from: package-private */
    public TabStop getTabStop() {
        return (TabStop) this.attributes.get(TABSTOP);
    }

    /* access modifiers changed from: package-private */
    public void setTabStop(TabStop tabStop) {
        this.attributes.put(TABSTOP, tabStop);
    }

    /* access modifiers changed from: package-private */
    public boolean isImage() {
        return this.image != null;
    }

    /* access modifiers changed from: package-private */
    public Image getImage() {
        return this.image;
    }

    /* access modifiers changed from: package-private */
    public float getImageHeight() {
        return this.image.getScaledHeight() * this.imageScalePercentage;
    }

    /* access modifiers changed from: package-private */
    public float getImageWidth() {
        return this.image.getScaledWidth() * this.imageScalePercentage;
    }

    public float getImageScalePercentage() {
        return this.imageScalePercentage;
    }

    public void setImageScalePercentage(float f) {
        this.imageScalePercentage = f;
    }

    /* access modifiers changed from: package-private */
    public void setImageOffsetX(float f) {
        this.offsetX = f;
    }

    /* access modifiers changed from: package-private */
    public float getImageOffsetX() {
        return this.offsetX;
    }

    /* access modifiers changed from: package-private */
    public void setImageOffsetY(float f) {
        this.offsetY = f;
    }

    /* access modifiers changed from: package-private */
    public float getImageOffsetY() {
        return this.offsetY;
    }

    /* access modifiers changed from: package-private */
    public void setValue(String str) {
        this.value = str;
    }

    public String toString() {
        return this.value;
    }

    /* access modifiers changed from: package-private */
    public boolean isSpecialEncoding() {
        return this.encoding.equals("UnicodeBigUnmarked") || this.encoding.equals(BaseFont.IDENTITY_H);
    }

    /* access modifiers changed from: package-private */
    public String getEncoding() {
        return this.encoding;
    }

    /* access modifiers changed from: package-private */
    public int length() {
        return this.value.length();
    }

    /* access modifiers changed from: package-private */
    public int lengthUtf32() {
        if (!BaseFont.IDENTITY_H.equals(this.encoding)) {
            return this.value.length();
        }
        int length = this.value.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            if (Utilities.isSurrogateHigh(this.value.charAt(i))) {
                i++;
            }
            i2++;
            i++;
        }
        return i2;
    }

    /* access modifiers changed from: package-private */
    public boolean isExtSplitCharacter(int i, int i2, int i3, char[] cArr, PdfChunk[] pdfChunkArr) {
        return this.splitCharacter.isSplitCharacter(i, i2, i3, cArr, pdfChunkArr);
    }

    /* access modifiers changed from: package-private */
    public String trim(String str) {
        BaseFont font2 = this.font.getFont();
        if (font2.getFontType() != 2 || font2.getUnicodeEquivalent(32) == 32) {
            while (true) {
                if (!str.endsWith(" ") && !str.endsWith("\t")) {
                    break;
                }
                str = str.substring(0, str.length() - 1);
            }
        } else {
            while (str.endsWith("\u0001")) {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }

    public boolean changeLeading() {
        return this.changeLeading;
    }

    public float getLeading() {
        return this.leading;
    }

    /* access modifiers changed from: package-private */
    public float getCharWidth(int i) {
        if (noPrint(i)) {
            return 0.0f;
        }
        if (isAttribute(Chunk.CHAR_SPACING)) {
            return this.font.width(i) + (((Float) getAttribute(Chunk.CHAR_SPACING)).floatValue() * this.font.getHorizontalScaling());
        }
        if (isImage()) {
            return getImageWidth();
        }
        return this.font.width(i);
    }
}
