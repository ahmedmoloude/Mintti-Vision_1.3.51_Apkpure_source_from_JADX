package com.itextpdf.text;

import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.pdf.BaseFont;

public class Font implements Comparable<Font> {
    public static final int BOLD = 1;
    public static final int BOLDITALIC = 3;
    public static final int DEFAULTSIZE = 12;
    public static final int ITALIC = 2;
    public static final int NORMAL = 0;
    public static final int STRIKETHRU = 8;
    public static final int UNDEFINED = -1;
    public static final int UNDERLINE = 4;
    private BaseFont baseFont;
    private BaseColor color;
    private FontFamily family;
    private float size;
    private int style;

    public enum FontFamily {
        COURIER,
        HELVETICA,
        TIMES_ROMAN,
        SYMBOL,
        ZAPFDINGBATS,
        UNDEFINED
    }

    public enum FontStyle {
        NORMAL(HtmlTags.NORMAL),
        BOLD(HtmlTags.BOLD),
        ITALIC(HtmlTags.ITALIC),
        OBLIQUE(HtmlTags.OBLIQUE),
        UNDERLINE(HtmlTags.UNDERLINE),
        LINETHROUGH(HtmlTags.LINETHROUGH);
        
        private String code;

        private FontStyle(String str) {
            this.code = str;
        }

        public String getValue() {
            return this.code;
        }
    }

    public Font(Font font) {
        this.family = FontFamily.UNDEFINED;
        this.size = -1.0f;
        this.style = -1;
        this.color = null;
        this.baseFont = null;
        this.family = font.family;
        this.size = font.size;
        this.style = font.style;
        this.color = font.color;
        this.baseFont = font.baseFont;
    }

    public Font(FontFamily fontFamily, float f, int i, BaseColor baseColor) {
        this.family = FontFamily.UNDEFINED;
        this.size = -1.0f;
        this.style = -1;
        this.color = null;
        this.baseFont = null;
        this.family = fontFamily;
        this.size = f;
        this.style = i;
        this.color = baseColor;
    }

    public Font(BaseFont baseFont2, float f, int i, BaseColor baseColor) {
        this.family = FontFamily.UNDEFINED;
        this.size = -1.0f;
        this.style = -1;
        this.color = null;
        this.baseFont = null;
        this.baseFont = baseFont2;
        this.size = f;
        this.style = i;
        this.color = baseColor;
    }

    public Font(BaseFont baseFont2, float f, int i) {
        this(baseFont2, f, i, (BaseColor) null);
    }

    public Font(BaseFont baseFont2, float f) {
        this(baseFont2, f, -1, (BaseColor) null);
    }

    public Font(BaseFont baseFont2) {
        this(baseFont2, -1.0f, -1, (BaseColor) null);
    }

    public Font(FontFamily fontFamily, float f, int i) {
        this(fontFamily, f, i, (BaseColor) null);
    }

    public Font(FontFamily fontFamily, float f) {
        this(fontFamily, f, -1, (BaseColor) null);
    }

    public Font(FontFamily fontFamily) {
        this(fontFamily, -1.0f, -1, (BaseColor) null);
    }

    public Font() {
        this(FontFamily.UNDEFINED, -1.0f, -1, (BaseColor) null);
    }

    public int compareTo(Font font) {
        if (font == null) {
            return -1;
        }
        try {
            BaseFont baseFont2 = this.baseFont;
            if (baseFont2 != null && !baseFont2.equals(font.getBaseFont())) {
                return -2;
            }
            if (this.family != font.getFamily()) {
                return 1;
            }
            if (this.size != font.getSize()) {
                return 2;
            }
            if (this.style != font.getStyle()) {
                return 3;
            }
            BaseColor baseColor = this.color;
            return baseColor == null ? font.color == null ? 0 : 4 : (font.color != null && baseColor.equals(font.getColor())) ? 0 : 4;
        } catch (ClassCastException unused) {
            return -3;
        }
    }

    public FontFamily getFamily() {
        return this.family;
    }

    /* renamed from: com.itextpdf.text.Font$1 */
    static /* synthetic */ class C14961 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$text$Font$FontFamily;

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.itextpdf.text.Font$FontFamily[] r0 = com.itextpdf.text.Font.FontFamily.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$itextpdf$text$Font$FontFamily = r0
                com.itextpdf.text.Font$FontFamily r1 = com.itextpdf.text.Font.FontFamily.COURIER     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$itextpdf$text$Font$FontFamily     // Catch:{ NoSuchFieldError -> 0x001d }
                com.itextpdf.text.Font$FontFamily r1 = com.itextpdf.text.Font.FontFamily.HELVETICA     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$itextpdf$text$Font$FontFamily     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.itextpdf.text.Font$FontFamily r1 = com.itextpdf.text.Font.FontFamily.TIMES_ROMAN     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$itextpdf$text$Font$FontFamily     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.itextpdf.text.Font$FontFamily r1 = com.itextpdf.text.Font.FontFamily.SYMBOL     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$itextpdf$text$Font$FontFamily     // Catch:{ NoSuchFieldError -> 0x003e }
                com.itextpdf.text.Font$FontFamily r1 = com.itextpdf.text.Font.FontFamily.ZAPFDINGBATS     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.Font.C14961.<clinit>():void");
        }
    }

    public String getFamilyname() {
        int i = C14961.$SwitchMap$com$itextpdf$text$Font$FontFamily[getFamily().ordinal()];
        if (i == 1) {
            return "Courier";
        }
        if (i == 2) {
            return "Helvetica";
        }
        if (i == 3) {
            return "Times-Roman";
        }
        if (i == 4) {
            return "Symbol";
        }
        if (i == 5) {
            return "ZapfDingbats";
        }
        BaseFont baseFont2 = this.baseFont;
        String str = "unknown";
        if (baseFont2 != null) {
            for (String[] strArr : baseFont2.getFamilyFontName()) {
                if ("0".equals(strArr[2])) {
                    return strArr[3];
                }
                if ("1033".equals(strArr[2])) {
                    str = strArr[3];
                }
                if ("".equals(strArr[2])) {
                    str = strArr[3];
                }
            }
        }
        return str;
    }

    public void setFamily(String str) {
        this.family = getFamily(str);
    }

    public static FontFamily getFamily(String str) {
        if (str.equalsIgnoreCase("Courier")) {
            return FontFamily.COURIER;
        }
        if (str.equalsIgnoreCase("Helvetica")) {
            return FontFamily.HELVETICA;
        }
        if (str.equalsIgnoreCase("Times-Roman")) {
            return FontFamily.TIMES_ROMAN;
        }
        if (str.equalsIgnoreCase("Symbol")) {
            return FontFamily.SYMBOL;
        }
        if (str.equalsIgnoreCase("ZapfDingbats")) {
            return FontFamily.ZAPFDINGBATS;
        }
        return FontFamily.UNDEFINED;
    }

    public float getSize() {
        return this.size;
    }

    public float getCalculatedSize() {
        float f = this.size;
        if (f == -1.0f) {
            return 12.0f;
        }
        return f;
    }

    public float getCalculatedLeading(float f) {
        return f * getCalculatedSize();
    }

    public void setSize(float f) {
        this.size = f;
    }

    public int getStyle() {
        return this.style;
    }

    public int getCalculatedStyle() {
        int i = this.style;
        if (i == -1) {
            i = 0;
        }
        return (this.baseFont != null || this.family == FontFamily.SYMBOL || this.family == FontFamily.ZAPFDINGBATS) ? i : i & -4;
    }

    public boolean isBold() {
        int i = this.style;
        return i != -1 && (i & 1) == 1;
    }

    public boolean isItalic() {
        int i = this.style;
        return i != -1 && (i & 2) == 2;
    }

    public boolean isUnderlined() {
        int i = this.style;
        return i != -1 && (i & 4) == 4;
    }

    public boolean isStrikethru() {
        int i = this.style;
        return i != -1 && (i & 8) == 8;
    }

    public void setStyle(int i) {
        this.style = i;
    }

    public void setStyle(String str) {
        if (this.style == -1) {
            this.style = 0;
        }
        this.style = getStyleValue(str) | this.style;
    }

    public static int getStyleValue(String str) {
        int indexOf = str.indexOf(FontStyle.NORMAL.getValue());
        int i = 0;
        if (str.indexOf(FontStyle.BOLD.getValue()) != -1) {
            i = 1;
        }
        if (str.indexOf(FontStyle.ITALIC.getValue()) != -1) {
            i |= 2;
        }
        if (str.indexOf(FontStyle.OBLIQUE.getValue()) != -1) {
            i |= 2;
        }
        if (str.indexOf(FontStyle.UNDERLINE.getValue()) != -1) {
            i |= 4;
        }
        return str.indexOf(FontStyle.LINETHROUGH.getValue()) != -1 ? i | 8 : i;
    }

    public BaseColor getColor() {
        return this.color;
    }

    public void setColor(BaseColor baseColor) {
        this.color = baseColor;
    }

    public void setColor(int i, int i2, int i3) {
        this.color = new BaseColor(i, i2, i3);
    }

    public BaseFont getBaseFont() {
        return this.baseFont;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003d, code lost:
        if (r11 != false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0041, code lost:
        if (r11 != false) goto L_0x0043;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.pdf.BaseFont getCalculatedBaseFont(boolean r11) {
        /*
            r10 = this;
            com.itextpdf.text.pdf.BaseFont r0 = r10.baseFont
            if (r0 == 0) goto L_0x0005
            return r0
        L_0x0005:
            int r0 = r10.style
            r1 = -1
            r2 = 0
            if (r0 != r1) goto L_0x000c
            r0 = 0
        L_0x000c:
            int[] r1 = com.itextpdf.text.Font.C14961.$SwitchMap$com$itextpdf$text$Font$FontFamily
            com.itextpdf.text.Font$FontFamily r3 = r10.family
            int r3 = r3.ordinal()
            r1 = r1[r3]
            java.lang.String r3 = "ZapfDingbats"
            java.lang.String r4 = "Symbol"
            r5 = 2
            r6 = 1
            r7 = 3
            java.lang.String r8 = "Cp1252"
            if (r1 == r6) goto L_0x0059
            if (r1 == r7) goto L_0x0045
            r9 = 4
            if (r1 == r9) goto L_0x0040
            r4 = 5
            if (r1 == r4) goto L_0x003d
            r11 = r0 & 3
            if (r11 == r6) goto L_0x003a
            if (r11 == r5) goto L_0x0037
            if (r11 == r7) goto L_0x0034
            java.lang.String r3 = "Helvetica"
            goto L_0x006c
        L_0x0034:
            java.lang.String r3 = "Helvetica-BoldOblique"
            goto L_0x006c
        L_0x0037:
            java.lang.String r3 = "Helvetica-Oblique"
            goto L_0x006c
        L_0x003a:
            java.lang.String r3 = "Helvetica-Bold"
            goto L_0x006c
        L_0x003d:
            if (r11 == 0) goto L_0x006c
            goto L_0x0043
        L_0x0040:
            r3 = r4
            if (r11 == 0) goto L_0x006c
        L_0x0043:
            r8 = r3
            goto L_0x006c
        L_0x0045:
            r11 = r0 & 3
            if (r11 == r6) goto L_0x0056
            if (r11 == r5) goto L_0x0053
            if (r11 == r7) goto L_0x0050
            java.lang.String r3 = "Times-Roman"
            goto L_0x006c
        L_0x0050:
            java.lang.String r3 = "Times-BoldItalic"
            goto L_0x006c
        L_0x0053:
            java.lang.String r3 = "Times-Italic"
            goto L_0x006c
        L_0x0056:
            java.lang.String r3 = "Times-Bold"
            goto L_0x006c
        L_0x0059:
            r11 = r0 & 3
            if (r11 == r6) goto L_0x006a
            if (r11 == r5) goto L_0x0067
            if (r11 == r7) goto L_0x0064
            java.lang.String r3 = "Courier"
            goto L_0x006c
        L_0x0064:
            java.lang.String r3 = "Courier-BoldOblique"
            goto L_0x006c
        L_0x0067:
            java.lang.String r3 = "Courier-Oblique"
            goto L_0x006c
        L_0x006a:
            java.lang.String r3 = "Courier-Bold"
        L_0x006c:
            com.itextpdf.text.pdf.BaseFont r11 = com.itextpdf.text.pdf.BaseFont.createFont(r3, r8, r2)     // Catch:{ Exception -> 0x0071 }
            return r11
        L_0x0071:
            r11 = move-exception
            com.itextpdf.text.ExceptionConverter r0 = new com.itextpdf.text.ExceptionConverter
            r0.<init>(r11)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.Font.getCalculatedBaseFont(boolean):com.itextpdf.text.pdf.BaseFont");
    }

    public boolean isStandardFont() {
        return this.family == FontFamily.UNDEFINED && this.size == -1.0f && this.style == -1 && this.color == null && this.baseFont == null;
    }

    public Font difference(Font font) {
        if (font == null) {
            return this;
        }
        float f = font.size;
        if (f == -1.0f) {
            f = this.size;
        }
        int i = this.style;
        int style2 = font.getStyle();
        int i2 = -1;
        if (!(i == -1 && style2 == -1)) {
            if (i == -1) {
                i = 0;
            }
            if (style2 == -1) {
                style2 = 0;
            }
            i2 = i | style2;
        }
        BaseColor baseColor = font.color;
        if (baseColor == null) {
            baseColor = this.color;
        }
        BaseFont baseFont2 = font.baseFont;
        if (baseFont2 != null) {
            return new Font(baseFont2, f, i2, baseColor);
        }
        if (font.getFamily() != FontFamily.UNDEFINED) {
            return new Font(font.family, f, i2, baseColor);
        }
        BaseFont baseFont3 = this.baseFont;
        if (baseFont3 == null) {
            return new Font(this.family, f, i2, baseColor);
        }
        if (i2 == i) {
            return new Font(baseFont3, f, i2, baseColor);
        }
        return FontFactory.getFont(getFamilyname(), f, i2, baseColor);
    }
}
