package com.itextpdf.text.pdf;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.fonts.otf.Language;
import com.itextpdf.text.pdf.languages.BanglaGlyphRepositioner;
import com.itextpdf.text.pdf.languages.GlyphRepositioner;
import com.itextpdf.text.pdf.languages.IndicCompositeCharacterComparator;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import kotlin.UByte;

class FontDetails {
    BaseFont baseFont;
    CJKFont cjkFont;
    IntHashtable cjkTag;
    PdfName fontName;
    int fontType;
    PdfIndirectReference indirectReference;
    HashMap<Integer, int[]> longTag;
    byte[] shortTag;
    protected boolean subset = true;
    boolean symbolic;
    TrueTypeFontUnicode ttu;

    FontDetails(PdfName pdfName, PdfIndirectReference pdfIndirectReference, BaseFont baseFont2) {
        this.fontName = pdfName;
        this.indirectReference = pdfIndirectReference;
        this.baseFont = baseFont2;
        int fontType2 = baseFont2.getFontType();
        this.fontType = fontType2;
        if (fontType2 == 0 || fontType2 == 1) {
            this.shortTag = new byte[256];
        } else if (fontType2 == 2) {
            this.cjkTag = new IntHashtable();
            this.cjkFont = (CJKFont) baseFont2;
        } else if (fontType2 == 3) {
            this.longTag = new HashMap<>();
            this.ttu = (TrueTypeFontUnicode) baseFont2;
            this.symbolic = baseFont2.isFontSpecific();
        }
    }

    /* access modifiers changed from: package-private */
    public PdfIndirectReference getIndirectReference() {
        return this.indirectReference;
    }

    /* access modifiers changed from: package-private */
    public PdfName getFontName() {
        return this.fontName;
    }

    /* access modifiers changed from: package-private */
    public BaseFont getBaseFont() {
        return this.baseFont;
    }

    /* access modifiers changed from: package-private */
    public Object[] convertToBytesGid(String str) {
        if (this.fontType == 3) {
            try {
                StringBuilder sb = new StringBuilder();
                int i = 0;
                for (char c : str.toCharArray()) {
                    int glyphWidth = this.ttu.getGlyphWidth(c);
                    i += glyphWidth;
                    int GetCharFromGlyphId = this.ttu.GetCharFromGlyphId(c);
                    if (GetCharFromGlyphId != 0) {
                        sb.append(Utilities.convertFromUtf32(GetCharFromGlyphId));
                    }
                    Integer valueOf = Integer.valueOf(c);
                    if (!this.longTag.containsKey(valueOf)) {
                        this.longTag.put(valueOf, new int[]{c, glyphWidth, GetCharFromGlyphId});
                    }
                }
                return new Object[]{str.getBytes("UnicodeBigUnmarked"), sb.toString(), Integer.valueOf(i)};
            } catch (Exception e) {
                throw new ExceptionConverter(e);
            }
        } else {
            throw new IllegalArgumentException("GID require TT Unicode");
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] convertToBytes(String str) {
        int i;
        int i2;
        int i3;
        int i4 = this.fontType;
        if (i4 == 0 || i4 == 1) {
            byte[] convertToBytes = this.baseFont.convertToBytes(str);
            for (byte b : convertToBytes) {
                this.shortTag[b & UByte.MAX_VALUE] = 1;
            }
            return convertToBytes;
        } else if (i4 == 2) {
            int length = str.length();
            if (this.cjkFont.isIdentity()) {
                for (int i5 = 0; i5 < length; i5++) {
                    this.cjkTag.put(str.charAt(i5), 0);
                }
            } else {
                int i6 = 0;
                while (i6 < length) {
                    if (Utilities.isSurrogatePair(str, i6)) {
                        i = Utilities.convertToUtf32(str, i6);
                        i6++;
                    } else {
                        i = str.charAt(i6);
                    }
                    this.cjkTag.put(this.cjkFont.getCidCode(i), 0);
                    i6++;
                }
            }
            return this.cjkFont.convertToBytes(str);
        } else if (i4 == 3) {
            try {
                int length2 = str.length();
                char[] cArr = new char[length2];
                if (this.symbolic) {
                    byte[] convertToBytes2 = PdfEncodings.convertToBytes(str, "symboltt");
                    int length3 = convertToBytes2.length;
                    i2 = 0;
                    for (int i7 = 0; i7 < length3; i7++) {
                        int[] metricsTT = this.ttu.getMetricsTT(convertToBytes2[i7] & UByte.MAX_VALUE);
                        if (metricsTT != null) {
                            this.longTag.put(Integer.valueOf(metricsTT[0]), new int[]{metricsTT[0], metricsTT[1], this.ttu.getUnicodeDifferences(convertToBytes2[i7] & UByte.MAX_VALUE)});
                            cArr[i2] = (char) metricsTT[0];
                            i2++;
                        }
                    }
                } else if (canApplyGlyphSubstitution()) {
                    return convertToBytesAfterGlyphSubstitution(str);
                } else {
                    int i8 = 0;
                    i2 = 0;
                    while (i8 < length2) {
                        if (Utilities.isSurrogatePair(str, i8)) {
                            i3 = Utilities.convertToUtf32(str, i8);
                            i8++;
                        } else {
                            i3 = str.charAt(i8);
                        }
                        int[] metricsTT2 = this.ttu.getMetricsTT(i3);
                        if (metricsTT2 != null) {
                            int i9 = metricsTT2[0];
                            Integer valueOf = Integer.valueOf(i9);
                            if (!this.longTag.containsKey(valueOf)) {
                                this.longTag.put(valueOf, new int[]{i9, metricsTT2[1], i3});
                            }
                            cArr[i2] = (char) i9;
                            i2++;
                        }
                        i8++;
                    }
                }
                return StringUtils.convertCharsToBytes(Utilities.copyOfRange(cArr, 0, i2));
            } catch (UnsupportedEncodingException e) {
                throw new ExceptionConverter(e);
            }
        } else if (i4 == 4) {
            return this.baseFont.convertToBytes(str);
        } else {
            if (i4 != 5) {
                return null;
            }
            return this.baseFont.convertToBytes(str);
        }
    }

    private boolean canApplyGlyphSubstitution() {
        return this.fontType == 3 && this.ttu.getGlyphSubstitutionMap() != null;
    }

    private byte[] convertToBytesAfterGlyphSubstitution(String str) throws UnsupportedEncodingException {
        if (canApplyGlyphSubstitution()) {
            Map<String, Glyph> glyphSubstitutionMap = this.ttu.getGlyphSubstitutionMap();
            TreeSet treeSet = new TreeSet(new IndicCompositeCharacterComparator());
            treeSet.addAll(glyphSubstitutionMap.keySet());
            String[] strArr = new ArrayBasedStringTokenizer((String[]) treeSet.toArray(new String[0])).tokenize(str);
            ArrayList arrayList = new ArrayList(50);
            for (String str2 : strArr) {
                Glyph glyph = glyphSubstitutionMap.get(str2);
                if (glyph != null) {
                    arrayList.add(glyph);
                } else {
                    for (char c : str2.toCharArray()) {
                        int[] metricsTT = this.ttu.getMetricsTT(c);
                        arrayList.add(new Glyph(metricsTT[0], metricsTT[1], String.valueOf(c)));
                    }
                }
            }
            GlyphRepositioner glyphRepositioner = getGlyphRepositioner();
            if (glyphRepositioner != null) {
                glyphRepositioner.repositionGlyphs(arrayList);
            }
            char[] cArr = new char[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                Glyph glyph2 = (Glyph) arrayList.get(i);
                cArr[i] = (char) glyph2.code;
                Integer valueOf = Integer.valueOf(glyph2.code);
                if (!this.longTag.containsKey(valueOf)) {
                    this.longTag.put(valueOf, new int[]{glyph2.code, glyph2.width, glyph2.chars.charAt(0)});
                }
            }
            return new String(cArr).getBytes("UnicodeBigUnmarked");
        }
        throw new IllegalArgumentException("Make sure the font type if TTF Unicode and a valid GlyphSubstitutionTable exists!");
    }

    private GlyphRepositioner getGlyphRepositioner() {
        Language supportedLanguage = this.ttu.getSupportedLanguage();
        if (supportedLanguage == null) {
            throw new IllegalArgumentException("The supported language field cannot be null in " + this.ttu.getClass().getName());
        } else if (C15161.$SwitchMap$com$itextpdf$text$pdf$fonts$otf$Language[supportedLanguage.ordinal()] != 1) {
            return null;
        } else {
            return new BanglaGlyphRepositioner(Collections.unmodifiableMap(this.ttu.cmap31), this.ttu.getGlyphSubstitutionMap());
        }
    }

    /* renamed from: com.itextpdf.text.pdf.FontDetails$1 */
    static /* synthetic */ class C15161 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$text$pdf$fonts$otf$Language;

        static {
            int[] iArr = new int[Language.values().length];
            $SwitchMap$com$itextpdf$text$pdf$fonts$otf$Language = iArr;
            try {
                iArr[Language.BENGALI.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public void writeFont(PdfWriter pdfWriter) {
        try {
            int i = this.fontType;
            if (i == 0 || i == 1) {
                int i2 = 0;
                while (true) {
                    if (i2 >= 256) {
                        break;
                    } else if (this.shortTag[i2] != 0) {
                        break;
                    } else {
                        i2++;
                    }
                }
                int i3 = 255;
                int i4 = 255;
                while (true) {
                    if (i4 < i2) {
                        break;
                    } else if (this.shortTag[i4] != 0) {
                        break;
                    } else {
                        i4--;
                    }
                }
                if (i2 > 255) {
                    i2 = 255;
                } else {
                    i3 = i4;
                }
                this.baseFont.writeFont(pdfWriter, this.indirectReference, new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), this.shortTag, Boolean.valueOf(this.subset)});
            } else if (i == 2) {
                this.baseFont.writeFont(pdfWriter, this.indirectReference, new Object[]{this.cjkTag});
            } else if (i == 3) {
                this.baseFont.writeFont(pdfWriter, this.indirectReference, new Object[]{this.longTag, Boolean.valueOf(this.subset)});
            } else if (i == 5) {
                this.baseFont.writeFont(pdfWriter, this.indirectReference, (Object[]) null);
            }
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public boolean isSubset() {
        return this.subset;
    }

    public void setSubset(boolean z) {
        this.subset = z;
    }
}
